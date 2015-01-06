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

  /**
   *
   * @return Name of the associated technology.
   */
  def getTechName: String

  /**
   *
   * @return Actual reference to the technology.  Piped through ResearchManager's getTech(getTechName).
   */
  def getTech: ITechnology

}
