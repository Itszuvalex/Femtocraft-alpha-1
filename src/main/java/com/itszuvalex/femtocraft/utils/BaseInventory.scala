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
package com.itszuvalex.femtocraft.utils

import java.util

import com.itszuvalex.femtocraft.api.core.{ISaveable, Saveable}
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.EnumSaveType
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.{Container, IInventory}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

class BaseInventory(size: Int) extends IInventory with ISaveable {
  @Saveable private var inventory = new Array[ItemStack](size)

  def this() = this(0)

  /**
   * @return ItemStack[] that backs this inventory class. Modifications to it modify this.
   */
  def getInventory: Array[ItemStack] = inventory

  def getSizeInventory = inventory.length

  def getStackInSlot(i: Int) = inventory(i)

  def decrStackSize(i: Int, amount: Int): ItemStack = {
    if (inventory(i) != null) {
      var itemstack: ItemStack = null
      if (inventory(i).stackSize <= amount) {
        itemstack = inventory(i)
        inventory(i) = null
        itemstack
      }
      else {
        itemstack = inventory(i).splitStack(amount)
        if (inventory(i).stackSize == 0) {
          inventory(i) = null
        }
        itemstack
      }
    }
    else null
  }

  def getStackInSlotOnClosing(i: Int) = inventory(i)

  def setInventorySlotContents(i: Int, itemstack: ItemStack) {
    inventory(i) = itemstack
  }

  def getInventoryName = "femto.BaseInventory.ImLazyAndDidntCodeThis"

  def hasCustomInventoryName = false

  def getInventoryStackLimit = 64

  def markDirty() {
  }

  def isUseableByPlayer(entityplayer: EntityPlayer) = true

  def openInventory() {
  }

  def closeInventory() {
  }

  def isItemValidForSlot(i: Int, itemstack: ItemStack): Boolean = true

  def saveToNBT(compound: NBTTagCompound) = FemtocraftDataUtils.saveObjectToNBT(compound, this, EnumSaveType.WORLD)

  def loadFromNBT(compound: NBTTagCompound) = FemtocraftDataUtils.loadObjectFromNBT(compound, this, EnumSaveType.WORLD)

  /**
   * Changes size of the inventory to be equal to size.  Keeps current inventory from slots 0 -> (size-1), and will
   * drop extra itemstacks.
   *
   * @param size new size of inventory
   */
  def setInventorySize(size: Int) = inventory = util.Arrays.copyOfRange(inventory, 0, size)

  def getComparatorInputOverride = Container.calcRedstoneFromInventory(this)
}
