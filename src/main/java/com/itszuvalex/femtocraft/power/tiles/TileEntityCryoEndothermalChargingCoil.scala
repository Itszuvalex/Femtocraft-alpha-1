package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.api.power.{ICryoEndothermalChargingAddon, ICryoEndothermalChargingBase}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.CryogenRegistry
import com.itszuvalex.femtocraft.power.tiles.TileEntityCryoEndothermalChargingCoil._
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable
import net.minecraftforge.common.util.ForgeDirection

import scala.util.Random

/**
 * Created by Chris on 9/8/2014.
 */

object TileEntityCryoEndothermalChargingCoil {
  val activeGenTickMin = 20
  val activeGenTickMax = 200
  val activeGenRange   = 5
}


class TileEntityCryoEndothermalChargingCoil extends TileEntityBase with ICryoEndothermalChargingAddon {
  @Saveable var ticksToActive = 0f
  @Saveable var powerStored   = 0f

  override def femtocraftServerUpdate(): Unit = {
    Array(ForgeDirection.NORTH, ForgeDirection.SOUTH, ForgeDirection.EAST, ForgeDirection.WEST).foreach(dir => {
      powerStored += CryogenRegistry.getPassivePower(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
    })

    if (ticksToActive == 0) {
      ticksToActive = Random.nextInt(activeGenTickMax - activeGenTickMin) + activeGenTickMin
      powerStored += CryogenRegistry.getActivePower(worldObj,
                                                    xCoord + Random.nextInt(2 * activeGenRange + 1) - activeGenRange,
                                                    yCoord + Random.nextInt(2 * activeGenRange + 1) - activeGenRange,
                                                    zCoord + Random.nextInt(2 * activeGenRange + 1) - activeGenRange)
    }
    else {
      ticksToActive-=1
    }

    val propagate = powerStored.toInt

    propagatePower(propagate)
    powerStored -= propagate.toFloat
  }

  override def propagatePower(power: Int) = {
    worldObj.getTileEntity(xCoord, yCoord + 1, zCoord) match {
      case addon: ICryoEndothermalChargingAddon => addon.propagatePower(power)
      case base: ICryoEndothermalChargingBase   => base.addPower(power)
      case _                                    =>
    }
  }
}
