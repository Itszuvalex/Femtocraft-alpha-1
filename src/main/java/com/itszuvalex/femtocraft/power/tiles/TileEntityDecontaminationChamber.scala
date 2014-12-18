package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.{IPowerTileContainer, PowerContainer}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.MultiBlockComponent
import com.itszuvalex.femtocraft.power.tiles.TileEntityDecontaminationChamber._
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._
import net.minecraftforge.fluids._

/**
 * Created by Chris on 12/17/2014.
 */
object TileEntityDecontaminationChamber {
  @Configurable(comment = "Power maximum")
  val POWER_MAX                       = 5000
  @Configurable(comment = "Power Tech level")
  val TECH_LEVEL                      = EnumTechLevel.NANO
  @Configurable(comment = "Contaminated Salt Tank Capacity")
  val CONTAMINATED_SALT_TANK_CAPACITY = 20000
  @Configurable(comment = "Cooled Salt Tank Capacity")
  val COOLED_SALT_TANK_CAPACITY       = 5000
  @Configurable(comment = "Amount of contaminated salt to process per tick")
  val PROCESS_RATE                    = 20
  @Configurable(comment = "Amount of power used per process")
  val POWER_COST                      = 10
  @Configurable(comment = "Ratio of contamianted salt turned into cooled salt.")
  val CONVERSION_RATIO                = .95
}


@Configurable class TileEntityDecontaminationChamber
  extends TileEntityBase with PowerConsumer with MultiBlockComponent with IFluidHandler {
  @Saveable val contaminatedSaltTank = new FluidTank(CONTAMINATED_SALT_TANK_CAPACITY)
  @Saveable val cooledSaltTank       = new FluidTank(COOLED_SALT_TANK_CAPACITY)


  override def femtocraftServerUpdate() = {
    if (contaminatedSaltTank.getFluidAmount >= PROCESS_RATE && getCurrentPower >= POWER_COST &&
        ((cooledSaltTank.getCapacity - cooledSaltTank.getFluidAmount) >= PROCESS_RATE * CONVERSION_RATIO)) {
      contaminatedSaltTank.drain(PROCESS_RATE, true)
      consume(POWER_COST)
      cooledSaltTank
      .fill(new FluidStack(Femtocraft.fluidCooledMoltenSalt, (PROCESS_RATE * CONVERSION_RATIO).toInt), true)
      setModified()
    }
  }

  override def consume(amount: Int): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.consume(amount)
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.consume(amount)
          case _                              =>
        }
      }
    }
    false
  }

  override def getCurrentPower: Int = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getCurrentPower
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getCurrentPower
          case _                              =>
        }
      }
    }
    0
  }

  override def getFillPercentageForCharging(from: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.getFillPercentageForCharging(from)
      } else {
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
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getFillPercentageForOutput(to)
          case _                              =>
        }
      }
    }
    0f
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection): Boolean = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.canAcceptPowerOfLevel(level, from)
      } else {
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
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getFillPercentage
          case _                              =>
        }
      }
    }
    1f
  }

  override def charge(from: ForgeDirection, amount: Int): Int = {
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        return super.charge(from, amount)
      } else {
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
        return super.getMaxPower
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case container: IPowerTileContainer =>
            return container.getMaxPower
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
      } else {
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
      } else {
        from match {
          case UP    => if (yCoord == info.y) return false
          case DOWN  => if (yCoord == info.y + 1) return false
          case NORTH => if (zCoord == info.z + 1) return false
          case SOUTH => if (zCoord == info.z) return false
          case EAST  => if (xCoord == info.x) return false
          case WEST  => if (xCoord == info.x + 1) return false
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

  def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    if (resource.getFluid ne Femtocraft.fluidCooledContaminatedMoltenSalt) {
      return 0
    }
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        val ret: Int = contaminatedSaltTank.fill(resource, doFill)
        if (ret > 0) {
          setModified()
        }
        return ret
      } else {
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
    if (resource.getFluid ne Femtocraft.fluidCooledMoltenSalt) {
      return null
    }
    if (isValidMultiBlock) {
      if (info.isController(xCoord, yCoord, zCoord)) {
        val ret: FluidStack = cooledSaltTank.drain(resource.amount, doDrain)
        if (ret != null) {
          setModified()
        }
        return ret
      } else {
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
        val ret: FluidStack = cooledSaltTank.drain(maxDrain, doDrain)
        if (ret != null) {
          setModified()
        }
        return ret
      } else {
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
        return fluid eq Femtocraft.fluidCooledContaminatedMoltenSalt
      } else {
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
        return fluid eq Femtocraft.fluidCooledMoltenSalt
      } else {
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
        return Array[FluidTankInfo](cooledSaltTank.getInfo, contaminatedSaltTank.getInfo)
      } else {
        worldObj.getTileEntity(info.x, info.y, info.z) match {
          case handler: IFluidHandler =>
            return handler.getTankInfo(from)
          case _                      =>
        }
      }
    }
    new Array[FluidTankInfo](0)
  }

  override def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_MAX)
}
