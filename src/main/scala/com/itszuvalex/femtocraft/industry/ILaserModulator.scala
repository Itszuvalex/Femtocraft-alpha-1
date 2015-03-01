package com.itszuvalex.femtocraft.industry

import net.minecraft.item.Item

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
trait ILaserModulator extends Item {

  /**
   *
   * @return When hosted in a Laser Modulator block, this will turn the laser into this modulation.
   */
  def getModulation: String

  /**
   *
   * @return When hosted in a Laser Modulator block, this will multiply the strength of the laser by this amount.
   */
  def getStrengthMultiplier: Float

  /**
   *
   * @return When hosted in a Laser Modulator block, this will multiply the remaining distance of the laser by this amount.
   */
  def getDistanceMultiplier: Float

}
