/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.managers.research


import java.io.File
import java.util
import java.util.UUID

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.api.events.EventTechnology
import com.itszuvalex.femtocraft.api.implicits.SugarImplicits._
import com.itszuvalex.femtocraft.api.managers.research.{IPlayerResearch, IResearchStatus}
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.configuration.XMLLoaderWriter
import com.itszuvalex.femtocraft.managers.research.PlayerResearch._
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler
import com.itszuvalex.femtocraft.network.messages.MessageResearchPlayer
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.common.MinecraftForge
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable

object PlayerResearch {
  private val mapKey      = "techMap"
  private val techNameKey = "techname"
  private val dataKey     = "data"
}

class PlayerResearch(private val uuid: UUID) extends IPlayerResearch with ISaveable {
  private val techStatus           = new mutable.HashMap[String, ResearchStatus]
  private var xml: XMLLoaderWriter = null

  def setFile(file: File): Unit = {
    xml = new XMLLoaderWriter(file)
    load()
  }

  /**
   * @param name  Name of researchTechnology to mark as researched
   * @param force Pass true if you want the named researchTechnology to be added if it isn't already discovered. This
   *              will bypass discover checks. This will not post an event.
   * @return True if researchTechnology successfully marked as researched. False otherwise.
   */
  override def researchTechnology(name: String, force: Boolean): Boolean = researchTechnology(name, force, true)

  override def researchTechnology(name: String, force: Boolean, notify: Boolean): Boolean = {
    val rtech = Femtocraft.researchManager.getTechnology(name)
    if (rtech == null) return true
    val tech = techStatus.get(name).orNull
    if (tech == null && !force) {
      return false
    }
    if (tech == null) {
      techStatus.put(name, new ResearchStatus(name, true))
      if (notify) {
        val player = FemtocraftUtils.getServerPlayer(uuid)
        if (player != null) {
          val techno = Femtocraft.researchManager.getTechnology(name)
          if (techno != null) {
            FemtocraftUtils
            .sendMessageToPlayer(player,
                                 techno.getLevel.getTooltipEnum + name + EnumChatFormatting
                                                                         .RESET + " successfully researched.")
          }
        }
      }
      discoverNewTechs(false)
      save()
      sync()
      return true
    }
    val event = new EventTechnology.Researched(uuid, rtech)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      tech.researched = true
      if (notify) {
        val player = FemtocraftUtils.getServerPlayer(uuid)
        if (player != null) {
          val techno = Femtocraft.researchManager.getTechnology(name)
          if (techno != null) {
            FemtocraftUtils
            .sendMessageToPlayer(player,
                                 techno.getLevel.getTooltipEnum + name + EnumChatFormatting
                                                                         .RESET + " successfully researched.")
          }
        }
      }
      discoverNewTechs(true)
      save()
      sync()
      return true
    }
    false
  }

  def discoverNewTechs(notify: Boolean): Boolean = {
    Femtocraft.researchManager.getTechnologies.filterNot(hasDiscoveredTechnology).filterNot(hasResearchedTechnology)
    .foreach { case t if t.isResearchedByDefault => return researchTechnology(t.getName, true, notify)
    case t if t.isDiscoveredByDefault => discoverTechnology(t.getName, notify)
    case t if canDiscoverTechnology(t) => discoverTechnology(t.getName, notify)
    case _ =>
             }
    true
  }

  override def discoverTechnology(name: String): Boolean = discoverTechnology(name, true)

  override def discoverTechnology(name: String, notify: Boolean): Boolean = {
    if (techStatus.containsKey(name)) return true
    val event = new EventTechnology.Discovered(uuid, Femtocraft.researchManager.getTechnology(name))
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      techStatus.put(name, new ResearchStatus(name))
      if (notify) {
        val player = FemtocraftUtils.getServerPlayer(uuid)
        if (player != null) {
          val techno = Femtocraft.researchManager.getTechnology(name)
          if (techno != null) {
            FemtocraftUtils
            .sendMessageToPlayer(player,
                                 "New technology " + techno.getLevel.getTooltipEnum + name + EnumChatFormatting
                                                                                             .RESET + " discovered.")
          }
        }
      }
      save()
      sync()
      return true
    }
    false
  }

  override def sync() = {
    val player = FemtocraftUtils.getServerPlayer(uuid)
    player IfNotNull sync(player)
  }

  override def sync(player: EntityPlayerMP) {
    if (ManagerResearch.debugMessages) Femtocraft.log(Level.INFO, "Sending research data to player: " + player.getCommandSenderName)
    FemtocraftPacketHandler.INSTANCE.sendTo(new MessageResearchPlayer(this), player)
  }

  override def removeTechnology(name: String): ResearchStatus = techStatus.remove(name).orNull

  override def canDiscoverTechnology(tech: ITechnology): Boolean = {
    if (tech.getPrerequisites == null || tech.getPrerequisites.size() == 0) {
      return true
    }
    tech.getPrerequisites.map(Femtocraft.researchManager.getTechnology).forall(hasResearchedTechnology)
  }

  override def hasDiscoveredTechnology(tech: ITechnology): Boolean = tech == null || hasDiscoveredTechnology(tech
                                                                                                             .getName)

  override def hasDiscoveredTechnology(tech: String): Boolean = techStatus.get(tech).orNull != null

  override def hasResearchedTechnology(tech: ITechnology): Boolean = tech == null || hasResearchedTechnology(tech
                                                                                                             .getName)

  override def hasResearchedTechnology(tech: String): Boolean = {
    if (tech == null || (tech == "")) {
      return true
    }
    val ts = techStatus.get(tech).orNull
    ts != null && ts.researched
  }

  override def saveToNBT(compound: NBTTagCompound) {
    val list = new NBTTagList
    techStatus.values.foreach { status =>
      val cs = new NBTTagCompound
      cs.setString(techNameKey, status.tech)
      val data = new NBTTagCompound
      status.saveToNBT(data)
      cs.setTag(dataKey, data)
      list.appendTag(cs)
                              }
    compound.setTag(mapKey, list)
  }

  override def loadFromNBT(compound: NBTTagCompound): Unit = {
    techStatus.clear()

    val list = compound.getTagList(mapKey, 10)
    (0 until list.tagCount).map(list.getCompoundTagAt).foreach { cs =>
      val techname = cs.getString(techNameKey)
      val data = cs.getCompoundTag(dataKey)
      val status = new ResearchStatus(techname)
      status.loadFromNBT(data)
      techStatus.put(techname, status)
                                                               }
  }

  def save(): Unit = {
    if (xml == null) {
      if (ManagerResearch.debugMessages) Femtocraft.log(Level.INFO, "No xml loader found when attempting to save.")
      return
    }
    xml.xml = <xml>
      {for (techStatus <- techStatus) yield techStatus._2.saveAsNode}
    </xml>
    xml.save()
    if (ManagerResearch.debugMessages) Femtocraft.log(Level.INFO, "Saving " + uuid + "'s research data to " + xml.file.getPath + ".")
  }

  def load(): Unit = {
    if (xml == null) {
      if (ManagerResearch.debugMessages) Femtocraft.log(Level.INFO, "No xml loader found when attempting to load.")
      return
    }
    if (ManagerResearch.debugMessages) Femtocraft.log(Level.INFO, "Loading " + uuid + "'s research data from " + xml.file.getPath + ".")
    techStatus.clear()
    xml.load()
    (xml.xml \ ResearchStatus.XMLTag).foreach(node => {
      val status = ResearchStatus(node)
      techStatus.put(status.getTechName, status)
    })
    discoverNewTechs(false)
    sync()
  }

  /**
   *
   * @return Username of the player associated with this research.
   */
  override def getUsername = Femtocraft.uuidManager.getUsername(uuid)

  /**
   *
   * @return All research statuses mapped for this player.
   */
  override def getTechnologies: util.Collection[IResearchStatus] = techStatus.values

  /**
   *
   * @param name
   * @return Returns the IResearchStatus struct from this object for the given technology.
   */
  override def getTechnology(name: String): ResearchStatus = techStatus.get(name).orNull

  /**
   *
   * @return Return uuid of the player associated with this research.
   */
  override def getUUID: UUID = uuid
}
