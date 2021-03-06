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

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.{IPowerTileContainer, PowerContainer}
import com.itszuvalex.femtocraft.api.{EnumTechLevel, IInterfaceDevice}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.MultiBlockComponent
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoCubePort._
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

object TileEntityFemtoCubePort {
  @Configurable(comment = "Maximum power storage.") private val storage   = 50000000
  @Configurable(comment = "TechLevel of power.") private    val techLevel = EnumTechLevel.FEMTO
}

@Configurable class TileEntityFemtoCubePort extends TileEntityBase with PowerTileContainer with MultiBlockComponent {
  @Saveable(desc = true) var output = false

  override def defaultContainer = new PowerContainer(techLevel, storage)

  override def consume(amount: Int): Boolean = {
    if (isValidMultiBlock && output) {
      if (isController) {
        return super.consume(amount)
      }
      val fc: TileEntityFemtoCubePort = worldObj
                                        .getTileEntity(info.x, info.y, info.z)
                                        .asInstanceOf[TileEntityFemtoCubePort]
      if (fc != null) {
        return fc.controllerConsume(amount)
      }
    }
    false
  }

  private def controllerConsume(amount: Int) = isController && super.consume(amount)

  override def getFillPercentageForCharging(from: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      return if (output) 1f else 0f
    }
    1f
  }

  override def charge(from: ForgeDirection, amount: Int): Int = {
    if (isValidMultiBlock && !output) {
      if (isController) {
        return super.charge(from, amount)
      }
      val fc = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[TileEntityFemtoCubePort]
      if (fc != null) {
        return fc.controllerCharge(from, amount)
      }
    }
    0
  }

  private def controllerCharge(from: ForgeDirection, amount: Int) = if (isController) super.charge(from, amount) else 0

  override def getMaxPower: Int = {
    if (isValidMultiBlock) {
      if (isController) {
        return storage
      }
      val fc = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IPowerTileContainer]
      if (fc != null) {
        return fc.getMaxPower
      }
    }
    0
  }

  override def getFillPercentageForOutput(to: ForgeDirection): Float = {
    if (isValidMultiBlock) {
      return if (output) 1f else 0f
    }
    0f
  }

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection) =
    isValidMultiBlock && super.canAcceptPowerOfLevel(level, from)

  override def getCurrentPower: Int = {
    if (isValidMultiBlock) {
      if (isController) {
        return super.getCurrentPower
      }
      val fc = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IPowerTileContainer]
      if (fc != null) {
        return fc.getCurrentPower
      }
    }
    0
  }

  override def getFillPercentage: Float = {
    if (isValidMultiBlock) {
      if (isController) {
        return super.getFillPercentage
      }
      val fc = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IPowerTileContainer]
      if (fc != null) {
        return fc.getFillPercentage
      }
    }
    super.getFillPercentage
  }

  private def isController: Boolean =
    isValidMultiBlock && ((info.x == xCoord) && (info.y == yCoord) && (info.z == zCoord))

  override def canCharge(from: ForgeDirection) = !(!isValidMultiBlock || output) && super.canCharge(from)

  override def canConnect(from: ForgeDirection) = isValidMultiBlock && super.canConnect(from)

  override def handleDescriptionNBT(compound: NBTTagCompound) {
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (canPlayerUse(par5EntityPlayer) && info.isValidMultiBlock) {
      val item: ItemStack = par5EntityPlayer.getCurrentEquippedItem
      if (item != null && item.getItem.isInstanceOf[IInterfaceDevice] &&
          item.getItem.asInstanceOf[IInterfaceDevice].getInterfaceLevel.tier >= EnumTechLevel.FEMTO.tier) {
        output = !output
        setUpdate()
        return true
      } else {
        par5EntityPlayer.openGui(getMod, getGuiID, worldObj, info.x, info.y, info.z)
        return true
      }
    }
    false
  }

  override def getGuiID = FemtocraftGuiConstants.FemtoCubeGuiID

  override def hasGUI = isValidMultiBlock
}