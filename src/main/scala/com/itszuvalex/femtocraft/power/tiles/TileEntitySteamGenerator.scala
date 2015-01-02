package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.power.IPowerTileContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.MultiBlockComponent
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._
import net.minecraftforge.fluids.{Fluid, FluidStack, FluidTankInfo, IFluidHandler}

/**
 * Created by Chris on 8/26/2014.
 */
object TileEntitySteamGenerator {
  @Configurable val steamGeneratorPercentageMultiplier: Float = 1f / 3f
}

@Configurable class TileEntitySteamGenerator extends TileEntityBase with MultiBlockComponent with IPowerTileContainer with IFluidHandler {
  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (isValidMultiBlock && canPlayerUse(par5EntityPlayer)) {
      par5EntityPlayer.openGui(getMod, getGuiID, worldObj, info.x, info.y, info.z)
      return true
    }
    false
  }

  override def getGuiID = FemtocraftGuiConstants.NanoMagnetohydrodynamicGeneratorGuiID

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    FemtocraftPowerUtils.distributePower(this, null, worldObj, xCoord, yCoord, zCoord)
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.canAcceptPowerOfLevel(level, from)
        case _                              =>
      }
    }
    false
  }

  override def getTechLevel(to: ForgeDirection): EnumTechLevel = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getTechLevel(to)
        case _                              =>
      }
    }
    null
  }

  override def getCurrentPower: Int = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getCurrentPower
        case _                              =>
      }
    }
    0
  }

  override def getMaxPower: Int = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getMaxPower
        case _                              =>
      }
    }
    0
  }

  override def getFillPercentage: Float = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getFillPercentage
        case _                              =>
      }
    }
    1f
  }

  override def getFillPercentageForCharging(from: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getFillPercentageForCharging(from)
        case _                              =>
      }
    }
    1f
  }

  override def getFillPercentageForOutput(to: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.getFillPercentageForOutput(to)
        case _                              =>
      }
    }
    0f
  }

  override def canCharge(from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.canCharge(from)
        case _                              =>
      }
    }
    false
  }

  override def canConnect(from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
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
    false
  }

  override def charge(from: ForgeDirection, amount: Int): Int = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.charge(from, amount)
        case _                              =>
      }
    }
    0
  }

  override def consume(amount: Int): Boolean = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case container: IPowerTileContainer =>
          return container.consume(amount)
        case _                              =>
      }
    }
    false
  }

  override def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.fill(from, resource, doFill)
        case _                      =>
      }
    }
    0
  }

  override def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.drain(from, resource, doDrain)
        case _                      =>
      }
    }
    null
  }

  override def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.drain(from, maxDrain, doDrain)
        case _                      =>
      }
    }
    null
  }

  override def canFill(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.canFill(from, fluid)
        case _                      =>
      }
    }
    false
  }

  override def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.canFill(from, fluid)
        case _                      =>
      }
    }
    false
  }

  override def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = {
    if (isValidMultiBlock) {
      worldObj.getTileEntity(info.x, info.y, info.z) match {
        case handler: IFluidHandler =>
          return handler.getTankInfo(from)
        case _                      =>
      }
    }
    new Array[FluidTankInfo](0)
  }
}

