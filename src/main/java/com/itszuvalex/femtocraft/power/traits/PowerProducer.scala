package com.itszuvalex.femtocraft.power.traits

import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 10/9/14.
 */
trait PowerProducer extends PowerBlockDistributor {
  override def getFillPercentageForCharging(from: ForgeDirection): Float = math.max(super.getFillPercentageForCharging(from), .75f)

  override def getFillPercentageForOutput(to: ForgeDirection): Float = math.max(super.getFillPercentageForOutput(to), .75f)
}
