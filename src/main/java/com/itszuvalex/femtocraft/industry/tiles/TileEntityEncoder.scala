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
package com.itszuvalex.femtocraft.industry.tiles

import java.util

import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.industry.IAssemblerSchematic
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.{Inventory, MassTank}
import com.itszuvalex.femtocraft.industry.traits.IndustryBehavior
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import com.itszuvalex.femtocraft.utils.BaseInventory
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids._

object TileEntityEncoder {
  @Configurable(comment = "Power tech level.")                   val TECH_LEVEL      = EnumTechLevel.MICRO
  @Configurable(comment = "Mass storage maximum.")               val MASS_STORAGE    = 1000
  @Configurable(comment = "Power storage maximum.")              val POWER_STORAGE   = 1200
  @Configurable(comment = "Power per item to begin processing.") val POWER_TO_ENCODE = 100
  @Configurable(comment = "Ticks required to process.")          val TICKS_TO_ENCODE = 200
}

@Configurable class TileEntityEncoder extends TileEntityBase with IndustryBehavior with Inventory with MassTank with PowerConsumer {
  @Saveable var timeWorked = 0
  @Saveable private var encodingRecipe   : AssemblerRecipe = null
  @Saveable private var encodingSchematic: ItemStack       = null

  override def defaultInventory = new BaseInventory(12)

  override def defaultTank = new FluidTank(TileEntityEncoder.MASS_STORAGE)

  override def defaultContainer = new PowerContainer(TileEntityEncoder.TECH_LEVEL, TileEntityEncoder.POWER_STORAGE)

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.EncoderGuiID


  override def getStackInSlotOnClosing(i: Int) = if (i == 9) null else super.getStackInSlotOnClosing(i)

  override def getInventoryName = Femtocraft.ID.toLowerCase + "." + "InventoryEncoder"

  override def hasCustomInventoryName = false


  override def isItemValidForSlot(i: Int, itemstack: ItemStack): Boolean = i match {
    case 9 => false
    case 10 => itemstack.getItem.isInstanceOf[IAssemblerSchematic]
    case _ => super.isItemValidForSlot(i, itemstack)
  }

  override def isWorking = encodingRecipe != null

  override protected def canStartWork = {
    val recipe = getRecipe
    recipe != null && getStackInSlot(11) == null && getStackInSlot(10) != null && getCurrentPower >= TileEntityEncoder.POWER_TO_ENCODE && massTank.getFluidAmount >= getStackInSlot(10).getItem.asInstanceOf[IAssemblerSchematic].massRequired(recipe) && getStackInSlot(10).getItem.isInstanceOf[IAssemblerSchematic]
  }

  private def getRecipe: AssemblerRecipe = {
    val recipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(util.Arrays.copyOfRange(inventory.getInventory, 0, 9))
    if (recipe == null) {
      return null
    }
    val researched = Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, recipe.tech)
    if (researched) recipe else null
  }

  override protected def startWork() {
    encodingSchematic = decrStackSize(10, 1)
    encodingRecipe = getRecipe
    timeWorked = 0
    consume(TileEntityEncoder.POWER_TO_ENCODE)
    massTank.drain(encodingSchematic.getItem.asInstanceOf[IAssemblerSchematic].massRequired(encodingRecipe), true)
  }

  override def markDirty() {
    val recipe = getRecipe
    inventory.setInventorySlotContents(9, if (recipe == null) null else recipe.output.copy)
  }

  override protected def continueWork() {
    timeWorked += 1
  }

  override protected def canFinishWork = timeWorked >= TileEntityEncoder.TICKS_TO_ENCODE

  override protected def finishWork() {
    timeWorked = 0
    encodingSchematic.getItem.asInstanceOf[IAssemblerSchematic].setRecipe(encodingSchematic, encodingRecipe)
    setInventorySlotContents(11, encodingSchematic)
    encodingSchematic = null
    encodingRecipe = null
  }

  def getProgressScaled(i: Int) = (timeWorked * i) / TileEntityEncoder.TICKS_TO_ENCODE

  def getMassAmount = massTank.getFluidAmount

  def setFluidAmount(amount: Int) {
    if (massTank.getFluid != null) {
      massTank.setFluid(new FluidStack(massTank.getFluid.fluidID, amount))
    }
    else {
      massTank.setFluid(new FluidStack(Femtocraft.fluidMass, amount))
    }
  }

  def clearFluid() {
    massTank.setFluid(null)
  }

  def getMassCapacity = massTank.getCapacity
}
