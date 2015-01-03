package com.itszuvalex.femtocraft.api.power

import java.util

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.item.ItemStack

/**
 * Created by Chris on 12/30/2014.
 */
object PowerStackWrapper {
  def getWrapperForStack(stack: ItemStack): PowerStackWrapper = {
    return getWrapperForStack(stack, true)
  }

  def getWrapperForStack(stack: ItemStack, forceNBT: Boolean): PowerStackWrapper = {
    return if (stack == null) null else new PowerStackWrapper(stack, forceNBT)
  }
}

/**
 * @param itemStack    ItemStack to wrap around.
 * @param forceNBT If true, will generate the nbt data structure on the item when it is attempted to be accessed and
 *                 not found.  Otherwise, will return empty values in its stead.
 */
class PowerStackWrapper(val itemStack: ItemStack, val forceNBT: Boolean) extends IPowerContainer {
  final val nbtPowerContainerWrapper = if (itemStack == null) {
    null
  } else {
    PowerNBTWrapper.wrapperFromNBT(itemStack.getTagCompound, forceNBT)
  }

  def this(stack: ItemStack) = this(stack, true)

  override def canAcceptPowerOfLevel(level: EnumTechLevel) = nbtPowerContainerWrapper.canAcceptPowerOfLevel(level)

  override def getTechLevel = nbtPowerContainerWrapper.getTechLevel

  override def getFillPercentageForCharging = nbtPowerContainerWrapper.getFillPercentageForCharging

  override def getFillPercentage = nbtPowerContainerWrapper.getFillPercentage

  override def getCurrentPower = nbtPowerContainerWrapper.getCurrentPower

  override def getMaxPower = nbtPowerContainerWrapper.getMaxPower

  override def getFillPercentageForOutput = nbtPowerContainerWrapper.getFillPercentageForOutput

  override def canCharge = nbtPowerContainerWrapper.canCharge

  override def charge(amount: Int) = nbtPowerContainerWrapper.charge(amount)

  override def consume(amount: Int) = nbtPowerContainerWrapper.consume(amount)

  def setCurrentPower(amount: Int) = nbtPowerContainerWrapper.setCurrentPower(amount)

  def setMaxPower(amount: Int) = nbtPowerContainerWrapper.setMaxPower(amount)

  def setTechLevel(level: EnumTechLevel) = nbtPowerContainerWrapper.setTechLevel(level)

  def copyFromPowerContainer(container: IPowerContainer) = nbtPowerContainerWrapper.copyFromPowerContainer(container)

  def attemptCopyToPowerContainer(container: IPowerContainer) =
    nbtPowerContainerWrapper.attemptCopyToPowerContainer(container)

  def copyToPowerContainer(container: PowerContainer) {
    nbtPowerContainerWrapper.copyToPowerContainer(container)
  }

  def toContainer = nbtPowerContainerWrapper.toContainer

  def hasPowerData = nbtPowerContainerWrapper.hasPowerData

  def addInformationToTooltip(tooltip: util.List[_]) {
    nbtPowerContainerWrapper.addInformationToTooltip(tooltip)
  }
}
