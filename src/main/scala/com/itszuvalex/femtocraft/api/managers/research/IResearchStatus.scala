package com.itszuvalex.femtocraft.api.managers.research

import com.itszuvalex.femtocraft.api.research.ITechnology

/**
 * Created by Chris on 1/2/2015.
 */
trait IResearchStatus {

  /**
   *
   * @return True if this technology is marked as researched for the player.  False if only discovered.
   */
  def isResearched: Boolean

  def getTechName: String

  def getTech: ITechnology

}
