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
import com.itszuvalex.femtocraft.power.containers.ContainerNanoFissionReactor._
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

object ContainerNanoFissionReactor {
  private val heatIndex                = 0
  private val cooledMoltenSaltIndex    = 1
  private val moltenSaltIndex          = 2
  private val thoriumIndex             = 3
  private val concentrationTargetIndex = 4
  private val concentrationMultiplier  = 10000
}

class ContainerNanoFissionReactor(player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, inventory: TileEntityNanoFissionReactorCore) extends ContainerInv[TileEntityNanoFissionReactorCore](player, inventory, 0, 2) {
  private val lastThorium: Int        = 0
  private var lastHeat                = 0
  private var lastCooledMoltenSalt    = 0
  private var lastMoltenSalt          = 0
  private var lastConcentrationTarget = 0

  this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.heatSlot, 8, 8))
  this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.saltSlot, 112, 8))
  this.addSlotToContainer(new Slot(inventory, TileEntityNanoFissionReactorCore.thoriumSlot, 112, 28))
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, heatIndex, inventory.getTemperatureCurrent.toInt)
    sendUpdateToCrafter(this, par1ICrafting, cooledMoltenSaltIndex, inventory.getCooledSaltAmount)
    sendUpdateToCrafter(this, par1ICrafting, moltenSaltIndex, inventory.getMoltenSaltAmount)
    sendUpdateToCrafter(this, par1ICrafting, thoriumIndex, inventory.getThoriumStoreCurrent)
    sendUpdateToCrafter(this, par1ICrafting, concentrationTargetIndex, (inventory.getThoriumConcentrationTarget * concentrationMultiplier).toInt)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()

    for (crafter <- crafters) {
      val icrafting: ICrafting = crafter.asInstanceOf[ICrafting]
      if (lastHeat != inventory.getTemperatureCurrent.toInt) {
        sendUpdateToCrafter(this, icrafting, heatIndex, inventory.getTemperatureCurrent.toInt)
      }
      if (lastCooledMoltenSalt != inventory.getCooledSaltAmount) {
        sendUpdateToCrafter(this, icrafting, cooledMoltenSaltIndex, inventory.getCooledSaltAmount)
      }
      if (lastMoltenSalt != inventory.getMoltenSaltAmount) {
        sendUpdateToCrafter(this, icrafting, moltenSaltIndex, inventory.getMoltenSaltAmount)
      }
      if (lastThorium != inventory.getTemperatureCurrent) {
        sendUpdateToCrafter(this, icrafting, thoriumIndex, inventory.getThoriumStoreCurrent)
      }
      if (lastConcentrationTarget != (inventory.getThoriumConcentrationTarget * concentrationMultiplier).toInt) {
        sendUpdateToCrafter(this, icrafting, concentrationTargetIndex, (inventory.getThoriumConcentrationTarget * concentrationMultiplier).toInt)
      }
    }
    lastHeat = inventory.getTemperatureCurrent.toInt
    lastCooledMoltenSalt = inventory.getCooledSaltAmount
    lastMoltenSalt = inventory.getMoltenSaltAmount
    lastHeat = inventory.getThoriumStoreCurrent
    lastConcentrationTarget = (inventory.getThoriumConcentrationTarget * concentrationMultiplier).toInt
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case `heatIndex`                => inventory.setTemperatureCurrent(par2)
      case `cooledMoltenSaltIndex`    => inventory.setCooledMoltenSalt(par2)
      case `moltenSaltIndex`          => inventory.setMoltenSalt(par2)
      case `thoriumIndex`             => inventory.setThoriumStoreCurrent(par2)
      case `concentrationTargetIndex` => inventory.setThoriumConcentrationTarget(par2.toFloat / concentrationMultiplier.toFloat)
      case _                          =>
    }
  }

  def eligibleForInput(item: ItemStack) = false
}


