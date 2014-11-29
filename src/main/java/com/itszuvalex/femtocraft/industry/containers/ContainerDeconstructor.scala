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

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.common.gui.OutputSlot
import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.industry.containers.ContainerDeconstructor._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.{ICrafting, Slot}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */
object ContainerDeconstructor {
  private val cookTimeIndex     = 0
  private val currentPowerIndex = 1
  private val massAmountIndex   = 2
}


class ContainerDeconstructor[T <: TileEntityBaseEntityMicroDeconstructor](player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, inv: T) extends ContainerInv[T](player, inv, 0, 9) {
  private var lastCookTime = 0
  private var lastPower    = 0
  private var lastMass     = 0

  addSlotToContainer(new Slot(inventory, 0, 38, 36))

  for (y <- 0 until 3) {
    for (x <- 0 until 3) {
      addSlotToContainer(new OutputSlot(inventory, x + y * 3 + 1, 88 + x * 18, 18 + y * 18))
    }
  }
  addPlayerInventorySlots(par1InventoryPlayer)

  override def addCraftingToCrafters(par1ICrafting: ICrafting) {
    super.addCraftingToCrafters(par1ICrafting)
    sendUpdateToCrafter(this, par1ICrafting, cookTimeIndex, inventory.cookTime)
    sendUpdateToCrafter(this, par1ICrafting, currentPowerIndex, inventory.getCurrentPower)
    sendUpdateToCrafter(this, par1ICrafting, massAmountIndex, inventory.getMassAmount)
  }

  /**
   * Looks for changes made in the container, sends them to every listener.
   */
  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    crafters.foreach { case icrafting: ICrafting =>
      if (lastCookTime != inventory.cookTime) {
        sendUpdateToCrafter(this, icrafting, cookTimeIndex, inventory.cookTime)
      }
      if (lastPower != inventory.getCurrentPower) {
        sendUpdateToCrafter(this, icrafting, currentPowerIndex, inventory.getCurrentPower)
      }
      if (lastMass != inventory.getMassAmount) {
        sendUpdateToCrafter(this, icrafting, massAmountIndex, inventory.getMassAmount)
      }
                     }

    lastCookTime = inventory.cookTime
    lastPower = inventory.getCurrentPower
    lastMass = inventory.getMassAmount
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    par1 match {
      case `cookTimeIndex`     => inventory.cookTime = par2
      case `currentPowerIndex` => inventory.currentPower = par2
      case `massAmountIndex`   => if (par2 > 0) inventory.setFluidAmount(par2) else inventory.clearFluid()
      case _                   =>
    }
  }

  def eligibleForInput(item: ItemStack) = Femtocraft.recipeManager.assemblyRecipes.getRecipe(item) != null
}

