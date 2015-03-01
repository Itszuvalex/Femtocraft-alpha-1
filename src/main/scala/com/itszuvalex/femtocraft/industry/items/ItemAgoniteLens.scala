package com.itszuvalex.femtocraft.industry.items

import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.industry.items.ItemAgoniteLens._
import com.itszuvalex.femtocraft.industry.{ILaserModulator, LaserRegistry}

object ItemAgoniteLens {
  @Configurable val DISTANCE_MULTIPLIER = .2f
  @Configurable val MODULATION          = LaserRegistry.MODULATION_THERMAL
  @Configurable val STRENGTH_MULTIPLIER = 1.2f
}

@Configurable class ItemAgoniteLens extends ItemBase("ItemAgoniteLens") with ILaserModulator {
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
