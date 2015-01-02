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
package com.itszuvalex.femtocraft.core.container

import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{IInventory, Slot}
import net.minecraft.item.ItemStack

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */
abstract class ContainerInv[T <: TileEntityBase](parPlayer: EntityPlayer, inv: T, input: Int, output: Int) extends ContainerBase {
  protected final val inventory   : T            = inv
  protected final val player      : EntityPlayer = parPlayer
  protected final val INPUT_SLOT  : Int          = input
  protected final val OUTPUT_SLOT : Int          = output
  protected final val INV_SIZE    : Int          = inventory match {
    case inventory1: IInventory => inventory1.getSizeInventory - 1
    case _                      => OUTPUT_SLOT
  }
  protected final val INV_START   : Int          = INV_SIZE + 1
  protected final val INV_END     : Int          = INV_START + 26
  protected final val HOTBAR_START: Int          = INV_END + 1
  protected final val HOTBAR_END  : Int          = HOTBAR_START + 8

  def canInteractWith(entityplayer: EntityPlayer) = inventory.canPlayerUse(entityplayer)

  /**
   * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
   */
  override def transferStackInSlot(par1EntityPlayer: EntityPlayer, par2: Int): ItemStack = {
    var itemstack: ItemStack = null
    val slot = this.inventorySlots.get(par2).asInstanceOf[Slot]
    if (slot != null && slot.getHasStack) {
      val itemstack1 = slot.getStack
      itemstack = itemstack1.copy
      if (par2 < INV_START) {
        if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, false)) {
          return null
        }
        slot.onSlotChange(itemstack1, itemstack)
      }
      else {
        if (eligibleForInput(itemstack1)) {
          if (!this.mergeItemStack(itemstack1, INPUT_SLOT, INPUT_SLOT + 1, false)) {
            return null
          }
        }
        else if (par2 >= INV_START && par2 <= INV_END) {
          if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
            return null
          }
        }
        else if (par2 >= HOTBAR_START && par2 <= HOTBAR_END) {
          if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
            return null
          }
        }
      }
      if (itemstack1.stackSize == 0) {
        slot.putStack(null)
      }
      else {
        slot.onSlotChanged()
      }
      if (itemstack1.stackSize == itemstack.stackSize) {
        return null
      }
      slot.onPickupFromSlot(par1EntityPlayer, itemstack1)
    }
    itemstack
  }

  def eligibleForInput(item: ItemStack): Boolean

  protected def addPlayerInventorySlots(inventoryPlayer: InventoryPlayer) {
    addPlayerInventorySlots(inventoryPlayer, 8, 84)
  }

  protected def addPlayerInventorySlots(inventoryPlayer: InventoryPlayer, inventoryXStart: Int, inventoryYStart: Int) {
    for (i <- 0 until 3) {
      for (j <- 0 until 9) {
        this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, inventoryXStart + j * 18, inventoryYStart + i * 18))
      }
    }
    for (i <- 0 until 9) {
      this.addSlotToContainer(new Slot(inventoryPlayer, i, inventoryXStart + i * 18, inventoryYStart + 58))
    }
  }
}
