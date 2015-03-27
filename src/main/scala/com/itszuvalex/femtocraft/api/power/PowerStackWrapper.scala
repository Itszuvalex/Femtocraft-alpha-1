package com.itszuvalex.femtocraft.api.power

import java.util

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.item.ItemStack

/**
 * Created by Chris on 12/30/2014.
 */
object PowerStackWrapper {
  def getWrapperForStack(stack: ItemStack): PowerStackWrapper = getWrapperForStack(stack, true)

  def getWrapperForStack(stack: ItemStack, forceNBT: Boolean): PowerStackWrapper = if (stack == null) null else new PowerStackWrapper(stack, forceNBT)
}

/**
 * @param itemStack    ItemStack to wrap around.
 * @param forceNBT If true, will generate the nbt data structure on the item when it is attempted to be accessed and
 *                 not found.  Otherwise, will return empty values in its stead.
 */
class PowerStackWrapper(val itemStack: ItemStack, val forceNBT: Boolean) extends IPowerContainer {
  final val nbt = if (itemStack == null) {
    null
  } else {
    PowerNBTWrapper.wrapperFromNBT(itemStack.getTagCompound, forceNBT)
  }

  def this(stack: ItemStack) = this(stack, true)

  override def canAcceptPowerOfLevel(level: EnumTechLevel) = if(nbt != null) nbt.canAcceptPowerOfLevel(level) else false

  override def getTechLevel = if(nbt != null) nbt.getTechLevel else null

  override def getFillPercentageForCharging = if(nbt != null) nbt.getFillPercentageForCharging else 1

  override def getFillPercentage = if(nbt != null) nbt.getFillPercentage else 0

  override def getCurrentPower = if(nbt != null) nbt.getCurrentPower else 0

  override def getMaxPower = if(nbt != null) nbt.getMaxPower else 0

  override def getFillPercentageForOutput = if(nbt != null) nbt.getFillPercentageForOutput else 0

  override def canCharge = if(nbt != null) nbt.canCharge else false

  override def charge(amount: Int) = if(nbt != null) nbt.charge(amount) else 0

  override def consume(amount: Int) = if(nbt != null) nbt.consume(amount) else false

  def setCurrentPower(amount: Int) = if(nbt != null) nbt.setCurrentPower(amount)

  def setMaxPower(amount: Int) = if(nbt != null) nbt.setMaxPower(amount)

  def setTechLevel(level: EnumTechLevel) = if(nbt != null) nbt.setTechLevel(level)

  def copyFromPowerContainer(container: IPowerContainer) = if(nbt != null) nbt.copyFromPowerContainer(container)

  def attemptCopyToPowerContainer(container: IPowerContainer) =
    if(nbt != null) nbt.attemptCopyToPowerContainer(container)

  def copyToPowerContainer(container: PowerContainer) {
    if(nbt != null) nbt.copyToPowerContainer(container)
  }

  def toContainer = if(nbt != null) nbt.toContainer else null

  def hasPowerData = if(nbt != null) nbt.hasPowerData else false

  def addInformationToTooltip(tooltip: util.List[_]) {
    if(nbt != null) nbt.addInformationToTooltip(tooltip)
  }
}
