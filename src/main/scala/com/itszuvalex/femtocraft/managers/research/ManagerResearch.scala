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

import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.events.EventTechnology
import com.itszuvalex.femtocraft.api.managers.research.IResearchManager
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.utils.FemtocraftFileUtils
import com.itszuvalex.femtocraft.configuration.{AutoGenConfig, TechnologyXMLLoader, XMLTechnology}
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.research.gui.graph.{TechNode, TechnologyGraph}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.event.entity.player.PlayerEvent

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable

@Configurable object ManagerResearch extends IResearchManager {
  @Configurable(comment = "Set to true to have all technologies researched by default.")
  private val debug                      = false
  private val DIRECTORY                  = "Research"
  private val technologies               = new mutable.HashMap[String, ITechnology]
  private val playerData                 = new mutable.HashMap[String, PlayerResearch]
  private var graph    : TechnologyGraph = null
  private var lastWorld: World           = null
  @Configurable
  private val debugMessages              = false

  def init() {
    registerTechnologies()
  }

  def setLastWorld(world: World) = {
    if (lastWorld == null || (lastWorld.getProviderName != world.getProviderName)) {
      playerData.clear()
      lastWorld = world
    }
  }

  def playerSavePath(uuid: String, world: World): File = new File(FemtocraftFileUtils.savePathFemtocraft(world) +File.separatorChar + DIRECTORY, uuid + ".xml")

  def playerSavePath(uuid: String): File = playerSavePath(uuid, lastWorld)

  def playerSavePath(uuid: UUID, world: World): File = playerSavePath(uuid.toString, world)

  def playerSavePath(uuid: UUID): File = playerSavePath(uuid.toString, lastWorld)

  def playerSavePath(player: EntityPlayer, world: World): File = playerSavePath(player.getUniqueID, world)

  def playerSavePath(player: EntityPlayer): File = playerSavePath(player.getUniqueID, lastWorld)

  def onPlayerLogin(player: EntityPlayer): Unit = {
    getOrMake(player.getUniqueID).sync()
  }

  private def getOrMake(uuid: String): PlayerResearch = {
    playerData.getOrElseUpdate(uuid, {
      val research = new PlayerResearch(uuid)
      research.setFile(playerSavePath(uuid))
      if (debug) addAllResearches(research)
      else
        research.discoverNewTechs(false)
      research
    })
  }

  private def getOrMake(uuid: UUID): PlayerResearch = getOrMake(uuid.toString)

  def onPlayerLoadFromFile(event: PlayerEvent.LoadFromFile): Unit = {

  }

  def onPlayerSaveToFile(event: PlayerEvent.SaveToFile): Unit = {

  }

  private def registerTechnologies(): Unit = {
    val custom = new TechnologyXMLLoader(new File(FemtocraftFileUtils.customConfigPath + "Technologies/")).loadItems()
    custom.view.foreach(addTechnology)

    val techs = new XMLTechnology(new File(FemtocraftFileUtils.autogenConfigPath, "Technologies.xml"))
    if (!AutoGenConfig.shouldRegenFile(techs.file) && techs.initialized) {
      techs.loadCustomRecipes().view.foreach(addTechnology)
    } else {
      val defs = FemtocraftTechnologies.defaultTechnologies.asInstanceOf[util.List[Technology]]
      techs.seedInitialRecipes(defs)
      defs.view.foreach(addTechnology)
      AutoGenConfig.markFileRegenerated(techs.file)
    }
  }

  override def addTechnology(tech: ITechnology): Boolean = !MinecraftForge.EVENT_BUS.post(new
                                                                                              EventTechnology.Added(tech)) && technologies
                                                                                                                              .put(
      tech.getName,
      tech) != null

  /**
   * Create and calculate the DAG for technologies.
   */
  def calculateGraph() {
    graph = new TechnologyGraph(technologies.asJava)
    graph.computePlacements()
  }

  override def getTechnologies: util.Collection[ITechnology] = technologies.values

  override def removeTechnology(tech: ITechnology) = technologies.remove(tech.getName) != null

  def getTechnology(name: String) = technologies.get(name).orNull

  def addPlayerResearch(uuid: String): PlayerResearch = playerData.getOrElse(uuid, {
    val r = new PlayerResearch(uuid)
    if (debug) {
      addAllResearches(r)
    } else {
      r.discoverNewTechs(false)
    }
    playerData.put(uuid, r)
    r
  })


  private def addAllResearches(research: PlayerResearch) {
    technologies.values.foreach { t => research.researchTechnology(t.getName, true)}
    research.save()
  }

  override def removePlayerResearch(username: String): Boolean = {
    playerData.remove(username) match {
      case Some(p) => p.save()
      case None =>
    }
    true
  }

  override def getPlayerResearch(uuid: String): PlayerResearch = playerData.get(uuid).orNull

  override def getPlayerResearch(player: EntityPlayer): PlayerResearch = playerData.get(player.getUniqueID.toString).orNull

  override def hasPlayerDiscoveredTechnology(uuid: String,
                                             tech: ITechnology): Boolean = tech == null || hasPlayerDiscoveredTechnology(uuid,
                                                                                                                         tech
                                                                                                                         .getName)

  override def hasPlayerDiscoveredTechnology(uuid: String, tech: String): Boolean = {
    val pr = playerData.get(uuid).orNull
    pr != null && pr.hasDiscoveredTechnology(tech)
  }

  override def hasPlayerResearchedTechnology(uuid: String,
                                             tech: ITechnology): Boolean = tech == null || hasPlayerResearchedTechnology(uuid,
                                                                                                                         tech
                                                                                                                         .getName)

  override def hasPlayerResearchedTechnology(uuid: String, tech: String): Boolean = {
    val pr = playerData.get(uuid).orNull
    pr != null && pr.hasResearchedTechnology(tech)
  }

  def syncLocal(pr: PlayerResearch): Unit = {
    playerData.put(pr.getUUID, pr)
  }

  def getNode(pr: ITechnology): TechNode = getNode(pr.getName)

  def getNode(name: String): TechNode = graph.getNode(name).asInstanceOf[TechNode]
}
