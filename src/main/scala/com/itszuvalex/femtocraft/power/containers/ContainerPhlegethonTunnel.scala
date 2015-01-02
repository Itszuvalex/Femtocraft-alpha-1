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
package com.itszuvalex.femtocraft.power.containers

import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.power.containers.ContainerPhlegethonTunnel._
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

object ContainerPhlegethonTunnel {
  private val powerIndex = 0
}

class ContainerPhlegethonTunnel(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, inventory: TileEntityPhlegethonTunnelCore) extends ContainerInv[TileEntityPhlegethonTunnelCore](player, inventory, 0, 0) {
  private var lastPower = 0

  this.addSlotToContainer(new Slot(inventory, 0, 22, 25))
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, powerIndex, inventory.getCurrentPower)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()

    for (crafter <- this.crafters) {
      val icrafting = crafter.asInstanceOf[ICrafting]
      if (this.lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, powerIndex, inventory.getCurrentPower)
      }
    }
    lastPower = inventory.getCurrentPower
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case `powerIndex` => this.inventory.setCurrentStorage(par2)
      case _            =>
    }
  }

  def eligibleForInput(item: ItemStack) = false
}


