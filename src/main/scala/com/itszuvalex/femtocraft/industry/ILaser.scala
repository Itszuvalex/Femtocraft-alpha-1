package com.itszuvalex.femtocraft.industry

import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher on 2/20/2015.
 */
trait ILaser {

  /**
   *
   * @return Strength of the laser.
   */
  def getStrength: Int

  /**
   *
   * @return Modulation of the laser.
   */
  def getModulation: String

  /**
   *
   * @return Direction of laser propagation.
   */
  def getDirection: ForgeDirection
}
