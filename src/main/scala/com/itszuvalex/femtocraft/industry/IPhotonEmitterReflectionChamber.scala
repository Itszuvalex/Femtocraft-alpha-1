package com.itszuvalex.femtocraft.industry

/**
 * Created by Christopher Harris (Itszuvalex) on 2/28/15.
 */
trait IPhotonEmitterReflectionChamber {

  /**
   *
   * @return Amount of additional power required for the laser per tick.
   */
  def getPowerPerTick: Int

  /**
   *
   * @return Additional strength added to the laser.
   */
  def getStrengthBonus: Int

  /**
   *
   * @return Additional length added to the laser.
   */
  def getDistanceBonus: Int

}
