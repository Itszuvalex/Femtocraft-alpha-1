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
import com.itszuvalex.femtocraft.industry.containers.ContainerEncoder._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

object ContainerEncoder {
  private val timeWorkedIndex   = 0
  private val currentPowerIndex = 1
  private val currentMassIndex  = 2
}

class ContainerEncoder(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, par2Encoder: TileEntityEncoder) extends ContainerInv[TileEntityEncoder](player, par2Encoder, 10, 11) {
  val recipeOutput   = new DisplaySlot(inventory, 9, 87, 34)
  val schematicInput = new Slot(inventory, 10, 120, 8)
  var y              = 0
  private var lastCookTime = 0
  private var lastPower    = 0
  private var lastMass     = 0

  for (y <- 0 until 3) {
    for (x <- 0 until 3) {
      this.addSlotToContainer(new Slot(inventory, x + 3 * y, 31 + 18 * x, 16 + 18 * y))
    }
  }

  recipeOutput.setBackgroundIcon(DisplaySlot.noPlaceDisplayIcon)
  addSlotToContainer(recipeOutput)

  schematicInput.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon)
  addSlotToContainer(schematicInput)
  addSlotToContainer(new OutputSlot(inventory, 11, 120, 50))
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, timeWorkedIndex, inventory.timeWorked)
    sendUpdateToCrafter(this, par1ICrafting, currentPowerIndex, inventory.getCurrentPower)
    sendUpdateToCrafter(this, par1ICrafting, currentMassIndex, inventory.getMassAmount)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastCookTime != inventory.timeWorked) {
        sendUpdateToCrafter(this, icrafting, timeWorkedIndex, inventory.timeWorked)
      }
      if (lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, currentPowerIndex, inventory.getCurrentPower)
      }
      if (lastMass != inventory.getMassAmount) {
        sendUpdateToCrafter(this, icrafting, currentMassIndex, inventory.getMassAmount)
      }

                     }
    lastCookTime = inventory.timeWorked
    lastPower = inventory.getCurrentPower
    lastMass = inventory.getMassAmount
  }

  def eligibleForInput(item: ItemStack) = item.getItem.isInstanceOf[IAssemblerSchematic]

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case `timeWorkedIndex`   => inventory.timeWorked = par2
      case `currentPowerIndex` => inventory.setCurrentStorage(par2)
      case `currentMassIndex`  => if (par2 > 0) inventory.setFluidAmount(par2) else inventory.clearFluid()
      case _                   =>
    }
  }
}
