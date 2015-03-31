package com.itszuvalex.femtocraft.api.managers.research

import java.util
import java.util.UUID

import com.itszuvalex.femtocraft.api.research.ITechnology
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Chris on 1/2/2015.
 */
trait IResearchManager {

  /**
   *
   * @param tech Technology to register add to global tech list.
   * @return True if technology successfully added.
   */
  def addTechnology(tech: ITechnology): Boolean

  /**
   *
   * @return A collection of all ITechnologies in the tech list.
   */
  def getTechnologies: util.Collection[ITechnology]

  /**
   *
   * @param tech Technology to remove from the list.
   * @return
   */
  def removeTechnology(tech: ITechnology): Boolean

  /**
   * Used to allow for indirect reference to technologies.
   *
   * @param name Name of the technology you are looking for.
   * @return The technology or null.
   */
  def getTechnology(name: String): ITechnology

  /**
   *
   * @param uuid
   * @param tech
   * @return True if the player file found from username has the technology discovered
   */
  def hasPlayerDiscoveredTechnology(uuid: UUID, tech: ITechnology): Boolean

  /**
   *
   * @param uuid
   * @param tech
   * @return True if the player file found from username has the technology discovered
   */
  def hasPlayerDiscoveredTechnology(uuid: UUID, tech: String): Boolean

  /**
   *
   * @param uuid
   * @param tech
   * @return True if the player file found from username has the technology researched
   */
  def hasPlayerResearchedTechnology(uuid: UUID, tech: ITechnology): Boolean

  /**
   *
   * @param uuid
   * @param tech
   * @return True if the player file found from username has the technology researched
   */
  def hasPlayerResearchedTechnology(uuid: UUID, tech: String): Boolean

  /**
   * Use at risk.  Does not delete the player file from disc, so it will be reloaded on next server startup.
   *
   * @param uuid
   * @return Removes all research for the player with the given name.
   */
  def removePlayerResearch(uuid: UUID): Boolean

  /**
   *
   * @param uuid
   * @return Get the accompanying playerResearch data structure for the player.
   */
  def getPlayerResearch(uuid: UUID): IPlayerResearch

  def getPlayerResearch(player: EntityPlayer): IPlayerResearch

}
