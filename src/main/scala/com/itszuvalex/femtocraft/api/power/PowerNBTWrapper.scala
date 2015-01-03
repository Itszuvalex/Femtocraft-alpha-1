package com.itszuvalex.femtocraft.api.power

import java.util

import com.itszuvalex.femtocraft.api.EnumTechLevel
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

/**
 * Created by Chris on 12/30/2014.
 */
object PowerNBTWrapper {
  val powerCompoundTag = "FemtocraftPower"
  val powerCurrentTag  = "CurrentPower"
  val powerMaximumTag  = "MaximumPower"
  val powerLevelTag    = "PowerLevel"

  def wrapperFromNBT(parent: NBTTagCompound): PowerNBTWrapper = wrapperFromNBT(parent, true)

  def wrapperFromNBT(parent: NBTTagCompound, forceNBT: Boolean): PowerNBTWrapper = if (parent == null) {
    null
  } else {
    new PowerNBTWrapper(parent, forceNBT)
  }
}

class PowerNBTWrapper(val parent: NBTTagCompound, val forceNBT: Boolean) extends IPowerContainer {

  def this(parent: NBTTagCompound) = this(parent, true)

  override def canAcceptPowerOfLevel(level: EnumTechLevel) = level == getTechLevel

  override def getTechLevel: EnumTechLevel = {
    if (!hasPowerData) return null
    EnumTechLevel.getTech(getPowerTagCompound.getString(PowerNBTWrapper.powerLevelTag))
  }

  override def getCurrentPower: Int = {
    if (!hasPowerData) return 0
    getPowerTagCompound.getInteger(PowerNBTWrapper.powerCurrentTag)
  }

  override def getMaxPower: Int = {
    if (!hasPowerData) return 0
    getPowerTagCompound.getInteger(PowerNBTWrapper.powerMaximumTag)
  }

  override def getFillPercentage: Float = {
    val current = getCurrentPower
    val max = getMaxPower
    if (max == 0) return 0
    current.toFloat / max.toFloat
  }

  override def getFillPercentageForCharging = getFillPercentage

  override def getFillPercentageForOutput = getFillPercentage

  override def canCharge = hasPowerData

  def hasPowerData = getPowerTagCompound != null && !getPowerTagCompound.hasNoTags

  def getPowerTagCompound: NBTTagCompound = {
    var powerCompound: NBTTagCompound = parent.getCompoundTag(PowerNBTWrapper.powerCompoundTag)
    if ((powerCompound == null || powerCompound.hasNoTags) && forceNBT) {
      powerCompound = new NBTTagCompound
      parent.setTag(PowerNBTWrapper.powerCompoundTag, powerCompound)
    }
    powerCompound
  }

  override def charge(amount: Int): Int = {
    val room = getMaxPower - getCurrentPower
    val ret = if (room < amount) room else amount
    setCurrentPower(getCurrentPower + ret)
    ret
  }

  override def consume(amount: Int): Boolean = {
    if (amount > getCurrentPower) return false
    setCurrentPower(getCurrentPower - amount)
    true
  }

  def setCurrentPower(amount: Int): Boolean = {
    if (!hasPowerData && !forceNBT) return false
    if (amount < 0 || amount > getMaxPower) return false
    getPowerTagCompound.setInteger(PowerNBTWrapper.powerCurrentTag, amount)
    true
  }

  def setMaxPower(amount: Int): Boolean = {
    if (!hasPowerData && !forceNBT) return false
    getPowerTagCompound.setInteger(PowerNBTWrapper.powerMaximumTag, amount)
    if (getCurrentPower > getMaxPower) setCurrentPower(getMaxPower)
    true
  }

  def setTechLevel(level: EnumTechLevel): Boolean = {
    if (!hasPowerData && !forceNBT) return false
    getPowerTagCompound.setString(PowerNBTWrapper.powerLevelTag, level.key)
    true
  }

  def copyFromPowerContainer(container: IPowerContainer): Boolean = {
    var result = setTechLevel(container.getTechLevel)
    result = setMaxPower(container.getMaxPower) && result
    setCurrentPower(container.getCurrentPower) && result
  }

  def attemptCopyToPowerContainer(container: IPowerContainer): Boolean = {
    if (getTechLevel != container.getTechLevel) return false
    var result = container.consume(container.getCurrentPower)
    result = (container.charge(getCurrentPower) == getCurrentPower) && result
    result
  }

  def copyToPowerContainer(container: PowerContainer) {
    container.setTechLevel(getTechLevel)
    container.setMaxPower(getMaxPower)
    container.setCurrentPower(getCurrentPower)
  }

  def toContainer = {
    val container = new PowerContainer
    copyToPowerContainer(container)
    container
  }

  @SuppressWarnings(Array("unchecked")) def addInformationToTooltip(tooltip: util.List[_]) {
    if (!hasPowerData) return
    tooltip.asInstanceOf[util.List[String]]
    .add(getTechLevel.getTooltipEnum + "Power: " + EnumChatFormatting
                                                   .RESET + getCurrentPower + "/" + getMaxPower + " OP")
  }
}
