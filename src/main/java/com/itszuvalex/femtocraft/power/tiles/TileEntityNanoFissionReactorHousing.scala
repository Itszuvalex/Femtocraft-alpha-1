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
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.MultiBlockComponent
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.IInventory
import net.minecraft.item.ItemStack
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.{Fluid, FluidStack, FluidTankInfo, IFluidHandler}

class TileEntityNanoFissionReactorHousing extends TileEntityBase with MultiBlockComponent with IFluidHandler with IInventory {
  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (isValidMultiBlock) {
      val te: TileEntity = worldObj.getTileEntity(info.x, info.y, info.z)
      if (te == null) {
        return false
      }
      par5EntityPlayer.openGui(getMod, getGuiID, worldObj, info.x, info.y, info.z)
      return true
    }
    false
  }

  override def getGuiID: Int = FemtocraftGuiConstants.NanoFissionReactorGuiID

  override def hasGUI: Boolean = isValidMultiBlock

  def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean): Int = {
    if (isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      if (core == null) return 0
      val result: Int = core.fill(from, resource, doFill)
      if (result > 0) {
        setModified()
      }
      return result
    }
    0
  }

  def drain(from: ForgeDirection, resource: FluidStack, doDrain: Boolean): FluidStack = {
    if (isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      if (core == null) return null
      val result: FluidStack = core.drain(from, resource, doDrain)
      if (result != null) {
        setModified()
      }
      return result
    }
    null
  }

  def drain(from: ForgeDirection, maxDrain: Int, doDrain: Boolean): FluidStack = {
    if (isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      if (core == null) return null
      val result: FluidStack = core.drain(from, maxDrain, doDrain)
      if (result != null) {
        setModified()
      }
      return result
    }
    null
  }

  def canFill(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (info.isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      return core != null && core.canFill(from, fluid)
    }
    false
  }

  def canDrain(from: ForgeDirection, fluid: Fluid): Boolean = {
    if (isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      return core != null && core.canDrain(from, fluid)
    }
    false
  }

  def getTankInfo(from: ForgeDirection): Array[FluidTankInfo] = {
    if (isValidMultiBlock) {
      val core: IFluidHandler = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IFluidHandler]
      if (core == null) return null
      return core.getTankInfo(from)
    }
    new Array[FluidTankInfo](0)
  }

  def getSizeInventory: Int = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return 0
      return core.getSizeInventory
    }
    0
  }

  def getStackInSlot(i: Int): ItemStack = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return null
      val result: ItemStack = core.getStackInSlot(i)
      if (result != null) {
        setModified()
        markDirty()
      }
      return result
    }
    null
  }

  def decrStackSize(i: Int, j: Int): ItemStack = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return null
      val result: ItemStack = core.decrStackSize(i, j)
      if (result != null) {
        setModified()
        markDirty()
      }
      return result
    }
    null
  }

  def getStackInSlotOnClosing(i: Int): ItemStack = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return null
      return core.getStackInSlotOnClosing(i)
    }
    null
  }

  def setInventorySlotContents(i: Int, itemstack: ItemStack) {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return
      core.setInventorySlotContents(i, itemstack)
      setModified()
      markDirty()
    }
  }

  def getInventoryName: String = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return null
      return core.getInventoryName
    }
    null
  }

  def hasCustomInventoryName: Boolean = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      return core != null && core.hasCustomInventoryName
    }
    false
  }

  def getInventoryStackLimit: Int = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return 0
      return core.getInventoryStackLimit
    }
    0
  }

  def isUseableByPlayer(entityplayer: EntityPlayer): Boolean = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      return core != null && core.isUseableByPlayer(entityplayer)
    }
    false
  }

  def openInventory() {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return
      core.openInventory()
    }
  }

  def closeInventory() {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      if (core == null) return
      core.closeInventory()
    }
  }

  def isItemValidForSlot(i: Int, itemstack: ItemStack): Boolean = {
    if (isValidMultiBlock) {
      val core: IInventory = worldObj.getTileEntity(info.x, info.y, info.z).asInstanceOf[IInventory]
      return core != null && core.isItemValidForSlot(i, itemstack)
    }
    false
  }
}
