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
   * @return Return uuid of the player associated with this research.
   */
  def getUUID: String

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


  /**
   * @param name  Name of researchTechnology to mark as researched
   * @param force Pass true if you want the named researchTechnology to be added if it isn't already discovered. This
   *              will bypass discover checks. This will not post an event.
   * @param notify Set to true to send chat messages to the player about research completion on this tech, and all newly discovered techs.
   * @return True if researchTechnology successfully marked as researched. False otherwise.
   */

  def researchTechnology(name: String, force: Boolean, notify: Boolean): Boolean

  /**
   * @param name  Name of researchTechnology to mark as discovered
   * @return True if researchTechnology successfully marked as discovered. False otherwise.
   */
  def discoverTechnology(name: String): Boolean

  /**
   * @param name  Name of researchTechnology to mark as discovered
   * @param notify Set to true to send a chat message to the player about discovering this technology.
   * @return True if researchTechnology successfully marked as discovered. False otherwise.
   */
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

  /**
   *
   * @param name
   * @return Returns the IResearchStatus struct from this object for the given technology.
   */
  def getTechnology(name: String): IResearchStatus

  /**
   *
   * @param name
   * @return Removes all research status about a tech.  If the server saves, the player's file will be written over, and this info will be lost.
   */
  def removeTechnology(name: String): IResearchStatus

  /**
   *
   * @param tech
   * @return Returns true if the player can discover the given technology.  Usually true if getTechnology == null
   */
  def canDiscoverTechnology(tech: ITechnology): Boolean

  /**
   *
   * @param tech
   * @return Returns true if this contains a research status for the technology, and it is marked as not researched.
   */
  def hasDiscoveredTechnology(tech: ITechnology): Boolean

  /**
   *
   * @param tech
   * @return Returns true if this contains a research status for the technology, and it is marked as not researched.
   */
  def hasDiscoveredTechnology(tech: String): Boolean

  /**
   *
   * @param tech
   * @return Returns true if this contains a research status for the technology, and it is marked as researched.
   */
  def hasResearchedTechnology(tech: ITechnology): Boolean

  /**
   *
   * @param tech
   * @return Returns true if this contains a research status for the technology, and it is marked as researched.
   */
  def hasResearchedTechnology(tech: String): Boolean
}