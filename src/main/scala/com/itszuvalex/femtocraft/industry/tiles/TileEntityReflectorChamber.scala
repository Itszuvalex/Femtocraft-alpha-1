package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.industry.IPhotonEmitterReflectionChamber
import com.itszuvalex.femtocraft.industry.tiles.TileEntityReflectorChamber._
import net.minecraft.tileentity.TileEntity

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
object TileEntityReflectorChamber {
  @Configurable val DISTANCE_BONUS    = 4
  @Configurable val STRENGTH_BONUS    = 10
  @Configurable val POWER_REQUIREMENT = 4
}

@Configurable class TileEntityReflectorChamber extends TileEntity with IPhotonEmitterReflectionChamber {
  /**
   *
   * @return Additional length added to the laser.
   */
  override def getDistanceBonus = DISTANCE_BONUS

  /**
   *
   * @return Additional strength added to the laser.
   */
  override def getStrengthBonus = STRENGTH_BONUS

  /**
   *
   * @return Amount of additional power required for the laser per tick.
   */
  override def getPowerPerTick = POWER_REQUIREMENT
}
