package com.itszuvalex.femtocraft.api.managers.research

import java.util

import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.api.research.ITechnology
import net.minecraft.entity.player.EntityPlayerMP

/**
 * Created by Chris on 1/2/2015.
 */
trait IPlayerResearch extends ISaveable {
  /**
   *
   * @return Username of the player associated with this research.
   */
  def getUsername: String

  /**
   *
   * @return All research statuses mapped for this player.
   */
  def getTechnologies: util.Collection[IResearchStatus]

  /**
   * @param name  Name of researchTechnology to mark as researched
   * @param force Pass true if you want the named researchTechnology to be added if it isn't already discovered. This
   *              will bypass discover checks. This will not post an event.
   * @return True if researchTechnology successfully marked as researched. False otherwise.
   */
  def researchTechnology(name: String, force: Boolean): Boolean


  def researchTechnology(name: String, force: Boolean, notify: Boolean): Boolean

  def discoverTechnology(name: String): Boolean

  def discoverTechnology(name: String, notify: Boolean): Boolean

  /**
   * Attempts to find correct player and sync.
   */
  def sync(): Unit

  /**
   *
   * @param player Player to send this research data to.  It will be under the owning players name, not the parameter players'.
   */
  def sync(player: EntityPlayerMP): Unit

  def getTechnology(name: String): IResearchStatus

  def removeTechnology(name: String): IResearchStatus

  def canDiscoverTechnology(tech: ITechnology): Boolean

  def hasDiscoveredTechnology(tech: ITechnology): Boolean

  def hasDiscoveredTechnology(tech: String): Boolean

  def hasResearchedTechnology(tech: ITechnology): Boolean

  def hasResearchedTechnology(tech: String): Boolean
}