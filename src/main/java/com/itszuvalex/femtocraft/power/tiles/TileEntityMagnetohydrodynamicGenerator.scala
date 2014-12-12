/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.{IPowerTileContainer, PowerContainer}
import com.itszuvalex.femtocraft.core.traits.tile.MultiBlockComponent
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator._
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._
import net.minecraftforge.fluids._

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
object TileEntityMagnetohydrodynamicGenerator {
  @Configurable(comment = "Power generated per mB of molten salt.")
  val powerPerMoltenSaltMB                   : Int     = 10
  @Configurable(comment = "Amount of contaminated salt storage.")
  val contaminatedSaltTankStorage            : Int     = 100000
  @Configurable(comment = "Amount of molten salt storage.")
  val moltenSaltTankStorage                  : Int     = 100000
  @Configurable(comment = "Amount of power that can be stored.")
  val powerStorage                           : Int     = 200000
  @Configurable(comment = "Maximum amount of molten salt to process per tick, in mB.")
  val maxMoltenSaltProcessingPerTick         : Int     = 10
  @Configurable(comment = "This ratio of molten salt is turned into contaminated salt.")
  val moltenSaltToContaminatedProcessingRatio: Double  = .9
  @Configurable(comment = "Set to true to continue to have fluid throughput when power is full, false otherwise.")
  val processFluidsWithFullPower             : Boolean = true
}

@Configurable class TileEntityMagnetohydrodynamicGenerator extends TileEntityPowerProducer with MultiBlockComponent with PowerTileContainer with IFluidHandler {
  @Saveable private val moltenSaltTank       = new FluidTank(moltenSaltTankStorage)
  @Saveable private val contaminatedSaltTank = new FluidTank(contaminatedSaltTankStorage)

  override def getFillPercentageForCharging(from: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getFillPercentageForCharging(from)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getFillPercentageForCharging(from)
          case _                              =>
        }
      }
    }
    1f
  }

  override def getFillPercentageForOutput(to: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getFillPercentageForOutput(to)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getFillPercentageForOutput(to)
          case _                              =>
        }
      }
    }
    0f
  }

  override def consume(amount: Int): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.consume(amount)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.consume(amount)
          case _                              =>
        }
      }
    }
    false
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.canAcceptPowerOfLevel(level, from)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.canAcceptPowerOfLevel(level, from)
          case _                              =>
        }
      }
    }
    false
  }

  override def getFillPercentage: Float = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getFillPercentage
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getFillPercentage
          case _                              =>
        }
      }
    }
    1f
  }

  override def femtocraftServerUpdate() {
    if (!isValidMultiBlock) {
      return
    }
    if (info.isController(xCoord, yCoord, zCoord)) {
      if (moltenSaltTank.getFluidAmount > 0) {
        var amount: Int = Math.min(moltenSaltTank.getFluidAmount, maxMoltenSaltProcessingPerTick)
        amount = Math.min(amount * moltenSaltToContaminatedProcessingRatio, (contaminatedSaltTank.getCapacity - contaminatedSaltTank.getFluidAmount) / moltenSaltToContaminatedProcessingRatio).toInt
        if (!processFluidsWithFullPower) {
          amount = Math.min(amount, (getMaxPower - getCurrentPower) / powerPerMoltenSaltMB)
        }
        moltenSaltTank.drain(amount, true)
        contaminatedSaltTank.fill(new FluidStack(Femtocraft.fluidCooledContaminatedMoltenSalt, (amount * moltenSaltToContaminatedProcessingRatio).toInt), true)
        var steamGenerators: Float = 0f
        for (i <- 0 until 6) {

          val dir: ForgeDirection = getOrientation(i)
          val te: TileEntity = worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
          if (te.isInstanceOf[TileEntitySteamGenerator]) {
            steamGenerators += TileEntitySteamGenerator.steamGeneratorPercentageMultiplier
          }
        }

        charge(UNKNOWN, (amount * powerPerMoltenSaltMB * (1f + steamGenerators)).toInt)
      }
    }
    else {
      super.femtocraftServerUpdate()
    }
  }

  override def charge(from: ForgeDirection, amount: Int): Int = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.charge(from, amount)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.charge(from, amount)
          case _                              =>
        }
      }
    }
    0
  }

  override def getMaxPower: Int = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return powerStorage
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getMaxPower
          case _                              =>
        }
      }
    }
    0
  }

  override def getCurrentPower: Int = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getCurrentPower
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getCurrentPower
          case _                              =>
        }
      }
    }
    0
  }

  override def canCharge(from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.canCharge(from)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.canCharge(from)
          case _                              =>
        }
      }
    }
    false
  }

  override def canConnect(from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.canConnect(from)
      }
      else {
        from match {
          case UP    => if (yCoord - info.y != 1) return false
          case DOWN  => if (yCoord - info.y != -1) return false
          case NORTH => if (zCoord - info.z != -1) return false
          case SOUTH => if (zCoord - info.z != 1) return false
          case EAST  => if (xCoord - info.x != 1) return false
          case WEST  => if (xCoord - info.x != -1) return false
          case _     =>
        }
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.canConnect(from)
          case _                              =>
        }
      }
    }
    false
  }

  override def defaultContainer = new PowerContainer(EnumTechLevel.NANO, powerStorage)

  def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    if (resource.getFluid ne Femtocraft.fluidMoltenSalt) {
      return 0
    }
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        val ret: Int = moltenSaltTank.fill(resource, doFill)
        if (ret > 0) {
          setModified()
        }
        return ret
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.fill(from, resource, doFill)
          case _                      =>
        }
      }
    }
    0
  }

  def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = {
    if (resource.getFluid ne Femtocraft.fluidCooledContaminatedMoltenSalt) {
      return null
    }
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        val ret: FluidStack = contaminatedSaltTank.drain(resource.amount, doDrain)
        if (ret != null) {
          setModified
        }
        return ret
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.drain(from, resource, doDrain)
          case _                      =>
        }
      }
    }
    null
  }

  def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        val ret: FluidStack = contaminatedSaltTank.drain(maxDrain, doDrain)
        if (ret != null) {
          setModified()
        }
        return ret
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.drain(from, maxDrain, doDrain)
          case _                      =>
        }
      }
    }
    null
  }

  def canFill(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return fluid eq Femtocraft.fluidMoltenSalt
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.canFill(from, fluid)
          case _                      =>
        }
      }
    }
    false
  }

  def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return fluid eq Femtocraft.fluidCooledContaminatedMoltenSalt
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.canFill(from, fluid)
          case _                      =>
        }
      }
    }
    false
  }

  def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return Array[FluidTankInfo](moltenSaltTank.getInfo, contaminatedSaltTank.getInfo)
      }
      else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.getTankInfo(from)
          case _                      =>
        }
      }
    }
    new Array[FluidTankInfo](0)
  }

  def getMoltenSaltTank: IFluidTank = moltenSaltTank

  def getContaminatedSaltTank: IFluidTank = contaminatedSaltTank

  def setMoltenSalt(moltenSalt: Int) {
    if (moltenSaltTank.getFluid != null) {
      moltenSaltTank.getFluid.amount = moltenSalt
    }
    else {
      moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt, moltenSalt), true)
    }
  }

  def setContaminatedSalt(contaminatedSalt: Int) {
    if (contaminatedSaltTank.getFluid != null) {
      contaminatedSaltTank.getFluid.amount = contaminatedSalt
    }
    else {
      contaminatedSaltTank.fill(new FluidStack(Femtocraft.fluidCooledContaminatedMoltenSalt, contaminatedSalt), true)
    }
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (isValidMultiBlock && canPlayerUse(par5EntityPlayer)) {
      par5EntityPlayer.openGui(getMod, getGuiID, worldObj, info.x, info.y, info.z)
      return true
    }
    false
  }


  override def getGuiID = FemtocraftGuiConstants.NanoMagnetohydrodynamicGeneratorGuiID
}
