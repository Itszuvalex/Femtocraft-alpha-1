package com.itszuvalex.femtocraft.power.traits

import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Chris on 10/9/2014.
 */
trait PowerConsumer extends PowerBlockContainer {
  override def getFillPercentageForCharging(from: ForgeDirection): Float = math.min(super.getFillPercentageForCharging(from), .25f)

  override def getFillPercentageForOutput(to: ForgeDirection): Float = math.min(super.getFillPercentageForOutput(to), .25f)
}
