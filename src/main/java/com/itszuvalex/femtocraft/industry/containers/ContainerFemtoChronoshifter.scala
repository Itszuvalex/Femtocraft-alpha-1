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

import com.itszuvalex.femtocraft.common.gui.OutputSlot
import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.industry.containers.ContainerFemtoChronoshifter._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoChronoshifter
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

object ContainerFemtoChronoshifter {
  private val cookTimeID    = 0
  private val cookTimeMaxID = 1
  private val powerID       = 2
}

class ContainerFemtoChronoshifter(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, par2TileEntityFurnace: TileEntityFemtoChronoshifter) extends ContainerInv[TileEntityFemtoChronoshifter](player, par2TileEntityFurnace, 0, 1) {
  private var lastCookTime = 0
  private var lastCookMax  = 0
  private var lastPower    = 0

  addSlotToContainer(new Slot(inventory, 0, 33, 35))
  addSlotToContainer(new Slot(inventory, 1, 66, 21))
  addSlotToContainer(new Slot(inventory, 2, 87, 21))
  addSlotToContainer(new Slot(inventory, 3, 108, 21))
  addSlotToContainer(new Slot(inventory, 4, 66, 49))
  addSlotToContainer(new Slot(inventory, 5, 87, 49))
  addSlotToContainer(new Slot(inventory, 6, 108, 49))
  addSlotToContainer(new OutputSlot(inventory, 7, 143, 35))
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, cookTimeID, inventory.getProgress)
    sendUpdateToCrafter(this, par1ICrafting, cookTimeMaxID, inventory.getProgressMax)
    sendUpdateToCrafter(this, par1ICrafting, powerID, inventory.getCurrentPower)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastCookTime != inventory.getProgress) {
        sendUpdateToCrafter(this, icrafting, cookTimeID, inventory.getProgress)
      }
      if (lastCookMax != inventory.getProgressMax) {
        sendUpdateToCrafter(this, icrafting, cookTimeMaxID, inventory.getProgressMax)
      }
      if (lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, powerID, inventory.getCurrentPower)
      }
                     }
    lastCookTime = inventory.getProgress
    lastCookMax = inventory.getProgressMax
    lastPower = inventory.getCurrentPower
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case `cookTimeID`    => inventory.setProgress(par2)
      case `cookTimeMaxID` => inventory.setProgressMax(par2)
      case `powerID`       => inventory.setCurrentStorage(par2)
    }
  }

  override def eligibleForInput(item: ItemStack) = true
}

