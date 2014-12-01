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
package com.itszuvalex.femtocraft.industry.containers

import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot, SlotFurnace}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */
class ContainerFurnace[T <: TileEntityBaseEntityMicroFurnace](player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, par2TileEntityFurnace: T) extends ContainerInv[T](player, par2TileEntityFurnace, 0, 1) {
  private var lastCookTime = 0
  private var lastPower    = 0

  addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 35))
  addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player, par2TileEntityFurnace, 1, 116, 35))
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, 0, inventory.furnaceCookTime)
    sendUpdateToCrafter(this, par1ICrafting, 1, inventory.getCurrentPower)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastCookTime != inventory.furnaceCookTime) {
        sendUpdateToCrafter(this, icrafting, 0, inventory.furnaceCookTime)
      }
      if (lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, 1, inventory.getCurrentPower)
      }
                     }
    lastCookTime = inventory.furnaceCookTime
    lastPower = inventory.getCurrentPower
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    if (par1 == 0) {
      inventory.furnaceCookTime = par2
    }
    if (par1 == 1) {
      inventory.currentPower = par2
    }
  }

  def eligibleForInput(item: ItemStack) = FurnaceRecipes.smelting.getSmeltingResult(item) != null
}
