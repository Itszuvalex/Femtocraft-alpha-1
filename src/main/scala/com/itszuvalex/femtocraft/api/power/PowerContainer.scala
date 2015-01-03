package com.itszuvalex.femtocraft.api.power

import java.util

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.ISaveable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting

object PowerContainer {
  def createFromNBT(nbt: NBTTagCompound): PowerContainer = {
    val cont = new PowerContainer
    cont.loadFromNBT(nbt)
    cont
  }
}

class PowerContainer(private var level: EnumTechLevel = EnumTechLevel.MACRO, private var maxPower: Int = 0)
  extends IPowerContainer with ISaveable {
  private var currentPower: Int = 0

  override def canAcceptPowerOfLevel(level: EnumTechLevel) = this.level == level

  override def getTechLevel = level

  def setTechLevel(level: EnumTechLevel) {
    this.level = level
  }

  override def getCurrentPower = currentPower

  def setCurrentPower(currentPower: Int) {
    this.currentPower = currentPower
  }

  override def getMaxPower = maxPower

  def setMaxPower(maxPower: Int) {
    this.maxPower = maxPower
  }

  override def getFillPercentageForCharging = getFillPercentage

  override def getFillPercentage = currentPower.toFloat / maxPower.toFloat

  override def getFillPercentageForOutput = getFillPercentage

  override def canCharge = true

  override def charge(amount: Int): Int = {
    val room = maxPower - currentPower
    val ret = if (room < amount) room else amount
    currentPower += ret
    ret
  }

  override def consume(amount: Int): Boolean = {
    if (amount > currentPower) {
      return false
    }
    currentPower -= amount
    true
  }

  override def saveToNBT(nbt: NBTTagCompound) {
    nbt.setInteger("maxPower", maxPower)
    nbt.setInteger("currentPower", currentPower)
    nbt.setString("enumTechLevel", level.key)
  }

  override def loadFromNBT(nbt: NBTTagCompound) {
    maxPower = nbt.getInteger("maxPower")
    currentPower = nbt.getInteger("currentPower")
    level = EnumTechLevel.getTech(nbt.getString("enumTechLevel"))
  }

  def addInformationToTooltip(tooltip: util.List[_]) {
    tooltip.asInstanceOf[util.List[String]]
    .add(level.getTooltipEnum + "Power: " + EnumChatFormatting.RESET + currentPower + "/" + maxPower + " OP")
  }
}
