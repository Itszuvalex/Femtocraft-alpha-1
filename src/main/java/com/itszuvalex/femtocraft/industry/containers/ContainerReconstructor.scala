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

import com.itszuvalex.femtocraft.api.industry.IAssemblerSchematic
import com.itszuvalex.femtocraft.api.items.ItemAssemblySchematic
import com.itszuvalex.femtocraft.common.gui.{DisplaySlot, OutputSlot}
import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack
import net.minecraft.util.IIcon

import scala.collection.JavaConversions._

class ContainerReconstructor[T <: TileEntityBaseEntityMicroReconstructor](player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, inventory: T) extends ContainerInv[T](player, inventory, 10, 9) {
  val schematic: Slot = new Slot(inventory, 10, 93, 54) {
    override def isItemValid(par1ItemStack: ItemStack) = par1ItemStack.getItem.isInstanceOf[IAssemblerSchematic]
  }
  private var lastCookTime = 0
  private var lastPower    = 0


  private var lastMass = 0
  schematic.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon)

  for (y <- 0 until 3) {
    for (x <- 0 until 3) {
      addSlotToContainer(new DisplaySlot(inventory, x + y * 3, 32 + x * 18, 18 + y * 18) {
        @SideOnly(Side.CLIENT) override def getBackgroundIconIndex: IIcon = if (this.inventory.getStackInSlot(10) != null) null else DisplaySlot.noPlaceDisplayIcon
      })
    }
  }
  addSlotToContainer(new OutputSlot(inventory, 9, 122, 18))
  addSlotToContainer(schematic)
  for (y <- 0 until 2) {
    for (x <- 0 until 9) {
      addSlotToContainer(new Slot(inventory, 11 + x + y * 9, 8 + x * 18, 77 + y * 18))
    }
  }
  addPlayerInventorySlots(par1InventoryPlayer, 8, 122)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, 0, inventory.cookTime)
    sendUpdateToCrafter(this, par1ICrafting, 1, inventory.getCurrentPower)
    sendUpdateToCrafter(this, par1ICrafting, 2, inventory.getMassAmount)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastCookTime != inventory.cookTime) {
        sendUpdateToCrafter(this, icrafting, 0, inventory.cookTime)
      }
      if (lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, 1, inventory.getCurrentPower)
      }
      if (lastMass != inventory.getMassAmount) {
        sendUpdateToCrafter(this, icrafting, 2, inventory.getMassAmount)
      }
                     }
    lastCookTime = inventory.cookTime
    lastPower = inventory.getCurrentPower
    lastMass = inventory.getMassAmount
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case 0 => inventory.cookTime = par2
      case 1 => inventory.currentPower = par2
      case 2 => if (par2 > 0) inventory.setFluidAmount(par2) else this.inventory.clearFluid()
      case _ =>
    }
  }

  override def eligibleForInput(item: ItemStack) = item.getItem.isInstanceOf[IAssemblerSchematic]
}