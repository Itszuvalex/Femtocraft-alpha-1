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

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.{IPhlegethonTunnelAddon, IPhlegethonTunnelComponent, IPhlegethonTunnelCore, PowerContainer}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.{Inventory, MultiBlockComponent}
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler
import com.itszuvalex.femtocraft.network.messages.MessagePhlegethonTunnelCore
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore._
import com.itszuvalex.femtocraft.power.traits.PowerProducer
import com.itszuvalex.femtocraft.utils.BaseInventory
import net.minecraft.init.Blocks
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
object TileEntityPhlegethonTunnelCore {
  @Configurable val TECH_LEVEL          = EnumTechLevel.FEMTO
  @Configurable val PowerGenBase: Float = 150
  @Configurable val POWER_MAX   : Int   = 100000
}

@Configurable class TileEntityPhlegethonTunnelCore
  extends TileEntityBase with PowerProducer with IPhlegethonTunnelCore with Inventory with MultiBlockComponent {
  //  private                        val sound : ISound  = FemtocraftSoundManager.makePhlegethonSound(xCoord, yCoord, zCoord)
  @Saveable(desc = true) private var active: Boolean = false

  override def defaultInventory = new BaseInventory(1)

  def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_MAX)

  override def handleDescriptionNBT(compound: NBTTagCompound) {
    val wasActive: Boolean = isActive
    val wasMultiblock: Boolean = isValidMultiBlock
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
    if ((wasActive && !isActive) || (wasMultiblock && !isValidMultiBlock)) {
      //      Femtocraft.soundManager.stopSound(sound)
    } else if (!wasActive && isActive) {
      //      Femtocraft.soundManager.playSound(sound)
    }
  }

  override def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    val `val`: Boolean = super.breakMultiBlock(world, x, y, z)
    if (`val` && isActive) {
      if (isActive) {
        worldObj.setBlock(xCoord, yCoord, zCoord, Blocks.lava)
      }
    }
    `val`
  }

  def isActive = active

  override def invalidate() {
    super.invalidate()
    if (worldObj.isRemote && isActive) {
      //      Femtocraft.soundManager.stopSound(sound)
    }
  }

  override def onChunkUnload() {
    if (worldObj.isRemote && isActive) {
      //      Femtocraft.soundManager.stopSound(sound)
    }
    super.onChunkUnload()
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel) = this.canAcceptPowerOfLevel(level, ForgeDirection.UNKNOWN)

  override def getTechLevel = super.getTechLevel(ForgeDirection.UNKNOWN)

  override def getFillPercentageForCharging = super.getFillPercentageForCharging(ForgeDirection.UNKNOWN)

  override def getFillPercentageForOutput: Float = super.getFillPercentageForOutput(ForgeDirection.UNKNOWN)

  override def canCharge = isValidMultiBlock && super.canCharge(ForgeDirection.UNKNOWN)

  override def femtocraftServerUpdate() {
    if (isActive) {
      charge(getTotalPowerGen.toInt)
    }
  }

  def getTotalPowerGen: Float = {
    var power: Float = getPowerGenBase
    for (dir <- ForgeDirection.VALID_DIRECTIONS) {
      worldObj.getTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ) match {
        case addon: IPhlegethonTunnelAddon =>
          power += addon.getPowerContribution(this)
        case _                             =>
      }
    }
    power
  }

  override def getPowerGenBase: Float = (PowerGenBase / Math.log1p(getHeight)).toFloat

  override def getHeight: Int = yCoord

  def charge(amount: Int): Int = this.charge(ForgeDirection.UNKNOWN, amount)

  override def canCharge(from: ForgeDirection) = isValidMultiBlock && super.canCharge(from)

  override def canConnect(from: ForgeDirection) = isValidMultiBlock && super.canConnect(from)

  def onCoreActivityChange(active: Boolean) {
    setUpdate()
  }

  override def getInventoryStackLimit = 1

  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = !isActive && super.isItemValidForSlot(i, itemstack)

  def onActivateClick() {
    FemtocraftPacketHandler.INSTANCE.sendToServer(getMessage(true))
  }

  private def getMessage(active: Boolean) = new
      MessagePhlegethonTunnelCore(xCoord, yCoord, zCoord, worldObj.provider.dimensionId, active)

  def onDeactivateClick() {
    FemtocraftPacketHandler.INSTANCE.sendToServer(getMessage(false))
  }

  def handlePacket(active: Boolean) {
    if (active) {
      activate
    } else {
      deactivate
    }
  }

  def activate: Boolean = {
    if (isActive) {
      return false
    }
    if (worldObj.isRemote) {
      return false
    }
    val item: ItemStack = inventory.getStackInSlot(0)
    if (item == null) return false
    if (item.getItem ne Femtocraft.itemPhlegethonTunnelPrimer) {
      return false
    }
    inventory.setInventorySlotContents(0, null)
    active = true
    setModified()
    setUpdate()
    notifyTunnelOfChange(active)
    true
  }

  def deactivate: Boolean = {
    if (!isActive) {
      return false
    }
    if (worldObj.isRemote) {
      return false
    }
    active = false
    setModified()
    setUpdate()
    notifyTunnelOfChange(active)
    true
  }

  private def notifyTunnelOfChange(status: Boolean) {
    for (x <- -1 to 1) {
      for (y <- -1 to 1) {
        for (z <- -1 to 1) {
          worldObj.getTileEntity(xCoord + x, yCoord + y, zCoord + z) match {
            case component: IPhlegethonTunnelComponent =>
              component.onCoreActivityChange(status)
            case _                                     =>
          }
        }
      }
    }
  }
}
