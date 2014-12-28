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

import java.io.{File, FileInputStream, FileOutputStream, FilenameFilter}
import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.events.EventTechnology
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.configuration.{TechnologyXMLLoader, XMLTechnology}
import com.itszuvalex.femtocraft.managers.research.ManagerResearch._
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.research.gui.graph.{TechNode, TechnologyGraph}
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound, NBTTagList}
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable

object ManagerResearch {
  private val playerDataKey = "playerData"
  private val dataKey       = "data"
  private val userKey       = "username"
  @Configurable(comment = "Set to true to have all technologies researched by default.")
  private val debug         = false
  private val DIRECTORY     = "Research"
}

@Configurable class ManagerResearch {
  private val technologies                     = new mutable.HashMap[String, ITechnology]
  private val playerData                       = new mutable.HashMap[String, PlayerResearch]
  private var graph          : TechnologyGraph = null
  private var lastWorldLoaded: String          = ""

  def init() {
    registerTechnologies()
  }

  private def registerTechnologies(): Unit = {
    val custom = new TechnologyXMLLoader(new File(FemtocraftFileUtils.customConfigPath + "Technologies/")).loadItems()
    custom.view.foreach(addTechnology)

    val techs = new XMLTechnology(new File(FemtocraftFileUtils.configFolder, "Technologies.xml"))
    if (techs.initialized) {
      techs.loadCustomRecipes().view.foreach(addTechnology)
    }
    else {
      val defs = FemtocraftTechnologies.defaultTechnologies.asInstanceOf[util.List[Technology]]
      techs.seedInitialRecipes(defs)
      defs.view.foreach(addTechnology)
    }
  }

  def addTechnology(tech: ITechnology): Boolean = !MinecraftForge.EVENT_BUS.post(new EventTechnology.TechnologyAddedEvent(tech)) && technologies.put(tech.getName, tech) != null

  /**
   * Create and calculate the DAG for technologies.
   */
  def calculateGraph() {
    graph = new TechnologyGraph(technologies.asJava)
    graph.computePlacements()
  }

  def getTechnologies: util.Collection[ITechnology] = technologies.values

  def removeTechnology(tech: ITechnology) = technologies.remove(tech.getName) != null

  def getTechnology(name: String) = technologies.get(name).orNull

  def addPlayerResearch(username: String): PlayerResearch = playerData.getOrElse(username, {
    val r = new PlayerResearch(username)
    if (debug) {
      addAllResearches(r)
    }
    else {
      r.discoverNewTechs(false)
    }
    playerData.put(username, r)
    r
  })


  private def addAllResearches(research: PlayerResearch) {
    technologies.values.foreach { t => research.researchTechnology(t.getName, true)}
  }

  def removePlayerResearch(username: String): Boolean = playerData.remove(username) != null

  def getPlayerResearch(username: String): PlayerResearch = playerData.get(username).orNull

  def hasPlayerDiscoveredTechnology(username: String, tech: ITechnology): Boolean = tech == null || hasPlayerDiscoveredTechnology(username, tech.getName)

  def hasPlayerDiscoveredTechnology(username: String, tech: String): Boolean = {
    val pr = playerData.get(username).orNull
    pr != null && pr.hasDiscoveredTechnology(tech)
  }

  def hasPlayerResearchedTechnology(username: String, tech: ITechnology): Boolean = tech == null || hasPlayerResearchedTechnology(username, tech.getName)

  def hasPlayerResearchedTechnology(username: String, tech: String): Boolean = {
    val pr = playerData.get(username).orNull
    pr != null && pr.hasResearchedTechnology(tech)
  }

  def saveToNBTTagCompound(compound: NBTTagCompound) {
    val list: NBTTagList = new NBTTagList
    playerData.values.foreach { status =>
      val cs = new NBTTagCompound
      cs.setString(userKey, status.username)
      val data = new NBTTagCompound
      status.saveToNBTTagCompound(data)
      cs.setTag(dataKey, data)
      list.appendTag(cs)
                              }
    compound.setTag(playerDataKey, list)
  }

  def loadFromNBTTagCompound(compound: NBTTagCompound) {
    val list = compound.getTagList(playerDataKey, 10)
    for (i <- 0 until list.tagCount) {
      val cs = list.getCompoundTagAt(i)
      val username = cs.getString(userKey)
      val data = cs.getCompoundTag(dataKey)
      val status = new PlayerResearch(username)
      status.loadFromNBTTagCompound(data)
      playerData.put(username, status)
    }

  }

  def save(world: World): Boolean = {
    if (world.isRemote) return true
    try {
      val folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY)
      if (!folder.exists) {
        folder.mkdirs
      }
      playerData.values.foreach { pdata =>
        try {
          val file: File = new File(folder, pdata.username + ".dat")
          if (!file.exists) {
            file.createNewFile
          }
          val fileoutputstream = new FileOutputStream(file)
          val data = new NBTTagCompound
          pdata.saveToNBTTagCompound(data)
          CompressedStreamTools.writeCompressed(data, fileoutputstream)
          fileoutputstream.close()
        }
        catch {
          case exception: Exception =>
            Femtocraft.log(Level.ERROR, "Failed to save data for player " + pdata.username + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
            exception.printStackTrace()
        }
                                }
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "Failed to create folder " + FemtocraftFileUtils.savePathFemtocraft(world) + File.pathSeparator + DIRECTORY + ".")
        e.printStackTrace()
        return false
    }
    true
  }

  def load(world: World): Boolean = {
    if (world.isRemote) return true
    val worldName = world.getWorldInfo.getWorldName
    if (lastWorldLoaded == worldName) {
      return false
    }
    lastWorldLoaded = worldName
    playerData.clear()
    try {
      val folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY)
      if (!folder.exists) {
        Femtocraft.log(Level.WARN, "No " + DIRECTORY + " folder found for world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        return false
      }
      folder.listFiles(new FilenameFilter {
        def accept(dir: File, name: String) = name.endsWith(".dat")
      }).foreach { pdata =>
        try {
          val fileinputstream = new FileInputStream(pdata)
          val data = CompressedStreamTools.readCompressed(fileinputstream)
          val username = pdata.getName.substring(0, pdata.getName.length - 4)
          val file = new PlayerResearch(username)
          file.loadFromNBTTagCompound(data)
          fileinputstream.close()
          file.discoverNewTechs(false)
          playerData.put(username, file)
        }
        catch {
          case e: Exception =>
            Femtocraft.log(Level.ERROR, "Failed to load data from file " + pdata.getName + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
            e.printStackTrace()
        }
                 }
    }
    catch {
      case exception: Exception =>
        Femtocraft.log(Level.ERROR, "Failed to load data from folder " + DIRECTORY + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + ".")
        exception.printStackTrace()
        return false
    }
    true
  }

  def syncResearch(rp: PlayerResearch) {
    Femtocraft.log(Level.TRACE, "Syncing research for player: " + rp.username)
    playerData.put(rp.username, rp)
  }

  def getNode(pr: ITechnology): TechNode = getNode(pr.getName)

  def getNode(name: String): TechNode = graph.getNode(name).asInstanceOf[TechNode]
}
