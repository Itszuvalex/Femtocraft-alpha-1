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
package com.itszuvalex.femtocraft.research.containers

import com.itszuvalex.femtocraft.common.gui.OutputSlot
import com.itszuvalex.femtocraft.core.container.ContainerBase
import com.itszuvalex.femtocraft.research.containers.ContainerResearchConsole._
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes

import scala.collection.JavaConversions._

object ContainerResearchConsole {
  private val progressID    = 0
  private val progressMaxID = 1
}

class ContainerResearchConsole(par1InventoryPlayer: InventoryPlayer, private val console: TileEntityResearchConsole) extends ContainerBase {
  private var lastProgress    = 0
  private var lastProgressMax = 0

  addSlotToContainer(new OutputSlot(console, 9, 147, 60))
  for (i <- 0 until 9) {
    addSlotToContainer(new Slot(console, i, 8 + 18 * (i % 3), 16 + 18 * (i / 3)))
  }

  for (i <- 0 until 3) {
    for (j <- 0 until 9) {
      addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18))
    }
  }
  for (i <- 0 until 9) {
    addSlotToContainer(new Slot(par1InventoryPlayer, i, 8 + i * 18, 142))
  }


  override def addCraftingToCrafters(par1iCrafting: ICrafting) {
    super.addCraftingToCrafters(par1iCrafting)
    sendUpdateToCrafter(this, par1iCrafting, progressID, console.getResearchProgress)
    sendUpdateToCrafter(this, par1iCrafting, progressMaxID, console.getResearchMax)
  }

  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastProgress != console.getResearchProgress) {
        sendUpdateToCrafter(this, icrafting, progressID, console.getResearchProgress)
      }
      if (lastProgressMax != console.getResearchMax) {
        sendUpdateToCrafter(this, icrafting, progressMaxID, console.getResearchMax)
      }
                     }
    lastProgress = console.getResearchProgress
    lastProgressMax = console.getResearchMax
  }

  /**
   * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
   */
  override def transferStackInSlot(par1EntityPlayer: EntityPlayer, par2: Int): ItemStack = {
    var itemstack: ItemStack = null
    val slot = inventorySlots.get(par2).asInstanceOf[Slot]
    if (slot != null && slot.getHasStack) {
      val itemstack1 = slot.getStack
      itemstack = itemstack1.copy
      if (par2 == 1) {
        if (!mergeItemStack(itemstack1, 2, 38, true)) {
          return null
        }
        slot.onSlotChange(itemstack1, itemstack)
      }
      else if (par2 != 0) {
        if (FurnaceRecipes.smelting.getSmeltingResult(itemstack1) != null) {
          if (!mergeItemStack(itemstack1, 0, 1, false)) {
            return null
          }
        }
        else if (par2 >= 2 && par2 < 29) {
          if (!mergeItemStack(itemstack1, 29, 38, false)) {
            return null
          }
        }
        else if (par2 >= 29 && par2 < 38 && !mergeItemStack(itemstack1, 2, 29, false)) {
          return null
        }
      }
      else if (!mergeItemStack(itemstack1, 2, 38, false)) {
        return null
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

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    super.updateProgressBar(par1, par2)
    par1 match {
      case `progressID` => console.setResearchProgress(par2)
      case `progressMaxID` => console.setResearchMax(par2)
    }
  }

  def canInteractWith(entityplayer: EntityPlayer) = console.isUseableByPlayer(entityplayer)
}
