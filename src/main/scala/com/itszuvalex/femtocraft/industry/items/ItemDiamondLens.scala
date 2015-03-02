package com.itszuvalex.femtocraft.industry.items

import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.industry.ILaserModulator
import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.industry.items.ItemDiamondLens._
import com.itszuvalex.femtocraft.industry.LaserRegistry

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
object ItemDiamondLens {
  @Configurable val DISTANCE_MULTIPLIER = .5f
  @Configurable val MODULATION          = LaserRegistry.MODULATION_CUTTING
  @Configurable val STRENGTH_MULTIPLIER = .99f
}

@Configurable class ItemDiamondLens extends ItemBase("ItemDiamondLens") with ILaserModulator {
  setMaxStackSize(1)

  /**
   *
   * @return When hosted in a Laser Modulator block, this will multiply the remaining distance of the laser by this amount.
   */
  override def getDistanceMultiplier = DISTANCE_MULTIPLIER

  /**
   *
   * @return When hosted in a Laser Modulator block, this will turn the laser into this modulation.
   */
  override def getModulation = MODULATION

  /**
   *
   * @return When hosted in a Laser Modulator block, this will multiply the strength of the laser by this amount.
   */
  override def getStrengthMultiplier = STRENGTH_MULTIPLIER
}
