package com.itszuvalex.femtocraft.power.traits

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.power.{IPowerBlockContainer, PowerContainer}
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 10/9/14.
 */

trait PowerBlockContainer extends IPowerBlockContainer {
  @Saveable
  protected val container: PowerContainer = defaultContainer

  override def getTechLevel(to: ForgeDirection): EnumTechLevel = container.getTechLevel

  override def getMaxPower: Int = container.getMaxPower

  def defaultContainer: PowerContainer

  def setMaxStorage(maxStorage: Int) = container.setMaxPower(maxStorage)

  def setCurrentStorage(currentStorage: Int) = container.setCurrentPower(currentStorage)

  def setTechLevel(level: EnumTechLevel) = container.setTechLevel(level)

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
