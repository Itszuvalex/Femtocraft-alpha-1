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
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.api.{DimensionalRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.Inventory
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityNanoEnmesher._
import com.itszuvalex.femtocraft.industry.traits.IndustryBehavior
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import com.itszuvalex.femtocraft.utils.{BaseInventory, FemtocraftUtils}
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.ForgeDirection

@Configurable object TileEntityBaseEntityNanoEnmesher {
  val inputSlot                = 0
  val outputSlot               = 5
  val inventorySize            = 6
  @Configurable(comment = "Power tech level.")
  val TECH_LEVEL               = EnumTechLevel.NANO
  @Configurable(comment = "Recipe tech level.")
  val RECIPE_TECH_LEVEL        = EnumTechLevel.NANO
  @Configurable(comment = "Power storage maximum.")
  val POWER_STORAGE            = 10000
  @Configurable(comment = "Power per item to begin processing.")
  val POWER_TO_COOK            = 80
  @Configurable(comment = "Multiplier for tick processing time of Dimensional Recipes.")
  val TICKS_TO_COOK_MULTIPLIER = 1f
}

@Configurable class TileEntityBaseEntityNanoEnmesher extends TileEntityBase with IndustryBehavior with Inventory with PowerConsumer {
  @Saveable var meshConfigStacks: Array[ItemStack] = null
  @Saveable var meshStack       : ItemStack        = null
  @Saveable private var cookTime   : Int = 0
  @Saveable private var ticksToCook: Int = 0

  override def defaultInventory = new BaseInventory(inventorySize)

  override def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_STORAGE)

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.NanoEnmesherGuiID

  def getCookProgressScaled(i: Int): Int = {
    if (ticksToCook == 0) {
      return 0
    }
    (cookTime * i) / ticksToCook
  }

  override def getAccessibleSlotsFromSide(var1: Int): Array[Int] = if (ForgeDirection.getOrientation(var1) eq ForgeDirection.UP) Array[Int](inputSlot) else Array[Int](getOutputSlotIndex)

  override def canInsertItem(i: Int, itemstack: ItemStack, j: Int) = i != getOutputSlotIndex

  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = i != getOutputSlotIndex

  def getProgress = cookTime

  def setProgress(progress: Int) {
    cookTime = progress
  }

  def getProgressMax = ticksToCook

  def setProgressMax(progressMax: Int) {
    ticksToCook = progressMax
  }

  override protected def canStartWork: Boolean = {
    if (isWorking) {
      return false
    }
    if (getCurrentPower < getPowerToCook) {
      return false
    }
    val dr = Femtocraft.recipeManager.dimensionalRecipes.getRecipe(inventory.getStackInSlot(inputSlot), getConfigurators)
    if (dr == null) {
      return false
    }
    if (dr.techLevel.tier > getTechLevel.tier) {
      return false
    }
    if (!Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, dr.getTechnology)) {
      return false
    }
    if (inventory.getStackInSlot(inputSlot).stackSize < dr.input.stackSize) {
      return false
    }
    val output = inventory.getStackInSlot(getOutputSlotIndex)
    if (output != null) {
      if (!output.isItemEqual(dr.output)) {
        return false
      }
      if ((output.getMaxStackSize - output.stackSize) < dr.output.stackSize) {
        return false
      }
    }
    true
  }

  protected def getTechLevel = RECIPE_TECH_LEVEL

  override def isWorking = meshStack != null

  override protected def startWork() {
    val dr: DimensionalRecipe = Femtocraft.recipeManager.dimensionalRecipes.getRecipe(inventory.getStackInSlot(inputSlot), getConfigurators)
    meshStack = inventory.decrStackSize(inputSlot, dr.input.stackSize)
    val configurators: Array[ItemStack] = getConfigurators
    meshConfigStacks = new Array[ItemStack](configurators.length)
    for (i <- 0 until meshConfigStacks.length) {
      meshConfigStacks(i) = if (configurators(i) == null) null else configurators(i).copy
    }

    consume(getPowerToCook)
    ticksToCook = (dr.ticks * getTickMultiplier).toInt
    cookTime = 0
    markDirty()
  }

  protected def getTickMultiplier = TICKS_TO_COOK_MULTIPLIER

  protected def getPowerToCook = POWER_TO_COOK

  protected def getConfigurators = util.Arrays.copyOfRange(inventory.getInventory, inputSlot + 1, getOutputSlotIndex)

  protected def getOutputSlotIndex = outputSlot

  override protected def continueWork() {
    cookTime += 1
  }

  override protected def canFinishWork = cookTime >= ticksToCook

  override protected def finishWork() {
    val dr: DimensionalRecipe = Femtocraft.recipeManager.dimensionalRecipes.getRecipe(meshStack, meshConfigStacks)
    if (dr != null) {
      val placeRestrictions: Array[Int] = new Array[Int](inventory.getSizeInventory - 1)
      for (i <- 0 until placeRestrictions.length) {
        placeRestrictions(i) = i + (if (i > getOutputSlotIndex) 1 else 0)
      }

      FemtocraftUtils.placeItem(dr.output, inventory.getInventory, placeRestrictions)
      markDirty()
    }
    cookTime = 0
    ticksToCook = 0
    meshStack = null
    meshConfigStacks = null
  }
}
