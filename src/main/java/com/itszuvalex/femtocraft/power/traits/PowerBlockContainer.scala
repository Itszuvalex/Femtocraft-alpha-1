package com.itszuvalex.femtocraft.power.traits

import com.itszuvalex.femtocraft.api.power.{IPowerBlockContainer, PowerContainer}
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel
import net.minecraftforge.common.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 10/9/14.
 */

trait PowerBlockContainer extends IPowerBlockContainer {
  protected def container: PowerContainer

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection) = container.getTechLevel equals level

  override def consume(amount: Int) = container.consume(amount)

  override def getFillPercentageForCharging(from: ForgeDirection) = container.getFillPercentageForCharging

  override def charge(from: ForgeDirection, amount: Int) = container.charge(amount)

  override def getFillPercentageForOutput(to: ForgeDirection) = container.getFillPercentageForOutput

  override def getCurrentPower = container.getCurrentPower

  override def getFillPercentage = container.getFillPercentage

  override def canCharge(from: ForgeDirection) = true

  override def canConnect(from: ForgeDirection) = true
}
