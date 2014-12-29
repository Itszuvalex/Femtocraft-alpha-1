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

import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.{Inventory, MassTank}
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor._
import com.itszuvalex.femtocraft.industry.traits.IndustryBehavior
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import com.itszuvalex.femtocraft.utils.{BaseInventory, FemtocraftUtils}
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids._

import scala.collection.JavaConversions._
import scala.collection.mutable._

object TileEntityBaseEntityMicroDeconstructor {
  @Configurable(comment = "Assembler recipe tech level maximum.")       val ASSEMBLER_TECH_LEVEL = EnumTechLevel.MICRO
  @Configurable(comment = "Power tech level.")                          val POWER_LEVEL          = EnumTechLevel.MICRO
  @Configurable(comment = "Power storage maximum.")                     val POWER_STORAGE        = 800
  @Configurable(comment = "Mass storage maximum.")                      val MASS_STORAGE         = 600
  @Configurable(comment = "Power per item to begin processing.")        val POWER_TO_COOK        = 40
  @Configurable(comment = "Ticks required to process.")                 val TICKS_TO_COOK        = 100
  @Configurable(comment = "Maximum number of items allowed at a time.") val MAX_SMELT            = 1
}

@Configurable class TileEntityBaseEntityMicroDeconstructor
  extends TileEntityBase with IndustryBehavior with PowerConsumer with Inventory with MassTank {
  /**
   * The number of ticks that the current item has been cooking for
   */
  @Saveable var cookTime                       = 0
  @Saveable var currentPower                   = 0
  @Saveable var deconstructingStack: ItemStack = null
  @Saveable private var field_94130_e: String = null

  override def defaultInventory = new BaseInventory(10)

  override def defaultTank = new FluidTank(MASS_STORAGE)

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.MicroDeconstructorGuiID

  def getMassAmount = massTank.getFluidAmount

  def getMassCapacity = massTank.getCapacity


  /**
   * Returns the name of the inventory.
   */
  override def getInventoryName = if (this.hasCustomInventoryName) this.field_94130_e else "Microtech Deconstructor"

  override def hasCustomInventoryName = this.field_94130_e != null && this.field_94130_e.length > 0

  /**
   * ******************************************************************************** This function is here for
   * compatibilities sake, Modders should Check for Sided before ContainerWorldly, Vanilla Minecraft does not follow
   * the sided standard that Modding has for a while.
   * <p/>
   * In vanilla:
   * <p/>
   * Top: Ores Sides: Fuel Bottom: Output
   * <p/>
   * Standard Modding: Top: Ores Sides: Output Bottom: Fuel
   * <p/>
   * The Modding one is designed after the GUI, the vanilla one is designed because its intended use is for the
   * hopper, which logically would take things in from the top.
   * <p/>
   * This will possibly be removed in future updates, and make vanilla the definitive standard.
   */
  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = i == 0

  def func_94129_a(par1Str: String) {
    this.field_94130_e = par1Str
  }

  @SideOnly(Side.CLIENT) def getCookProgressScaled(par1: Int): Int = {
    if (getTicksToCook == 0) {
      return 0
    }
    this.cookTime * par1 / getTicksToCook
  }

  override def isWorking = deconstructingStack != null

  override def getAccessibleSlotsFromSide(var1: Int) = {
    var1 match {
      case 1                                            => Array[Int](0)
      case 0 if getResearchGatedAssemblerRecipe == null => Array[Int](0)
      case 0                                            => Array[Int]()
      case _                                            => (1 until getSizeInventory).toArray
    }
  }

  override def canInsertItem(i: Int, itemstack: ItemStack, j: Int) = i == 0

  override def canExtractItem(i: Int, itemstack: ItemStack, j: Int) = true

  override def fill(from: ForgeDirection, resource: FluidStack, doFill: Boolean) = 0

  override def canFill(from: ForgeDirection, fluid: Fluid) = false

  def setFluidAmount(amount: Int) {
    if (massTank.getFluid != null) {
      massTank.setFluid(new FluidStack(massTank.getFluid.fluidID, amount))
    } else {
      massTank.setFluid(new FluidStack(Femtocraft.fluidMass, amount))
    }
  }

  def clearFluid() {
    massTank.setFluid(null)
  }

  override def defaultContainer = new PowerContainer(POWER_LEVEL, POWER_STORAGE)

  override protected def canStartWork = if (getStackInSlot(0) == null || deconstructingStack != null || this
                                                                                                        .getCurrentPower < getPowerToCook) {
    false
  } else {
    val recipe: AssemblerRecipe = getResearchGatedAssemblerRecipe
    recipe != null && (massTank.getCapacity - massTank.getFluidAmount) >= recipe.mass && getStackInSlot(0)
                                                                                         .stackSize >= recipe
                                                                                                       .output
                                                                                                       .stackSize && roomForItems(recipe
                                                                                                                                  .input)
  }

  def getResearchGatedAssemblerRecipe: AssemblerRecipe = {
    val recipe = getAssemblerRecipe
    if (recipe == null) return null
    if (recipe.enumTechLevel.tier > getAssemblerTech.tier) return null
    if (!Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, recipe.tech)) return null
    recipe
  }

  protected def getAssemblerTech = ASSEMBLER_TECH_LEVEL

  override protected def startWork() {
    deconstructingStack = getStackInSlot(0).copy
    val recipe = getAssemblerRecipe
    deconstructingStack.stackSize = 0
    var massReq = 0
    val items = new ArrayBuffer[ItemStack]
    var continue = true
    var i = 0
    do {
      if (deconstructingStack.stackSize >= this.getInventoryStackLimit) {
        continue = false
      }
      if (getStackInSlot(0) == null || getStackInSlot(0).stackSize < recipe.output.stackSize) {
        continue = false
      }
      if (continue) {
        items.addAll(recipe.input.toList)
        val ita = items.toArray
        if (!roomForItems(ita)) {
          continue = false
        }
        if ( {massReq += recipe.mass; massReq} > (massTank.getCapacity - massTank.getFluidAmount)) {
          continue = false
        }
        if (!consume(getPowerToCook)) {
          continue = false
        }
        if (continue) {
          deconstructingStack.stackSize += recipe.output.stackSize
          decrStackSize(0, recipe.output.stackSize)
        }
      }
    } while (continue && ({i += 1; i} < getMaxSimultaneousSmelt))
    cookTime = 0
    markDirty()
  }

  def getAssemblerRecipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(getStackInSlot(0))

  protected def getPowerToCook = POWER_TO_COOK

  private def roomForItems(items: Array[ItemStack]): Boolean = {
    val fake = new Array[ItemStack](getSizeInventory)
    for (i <- 0 until fake.length) {
      val it = getStackInSlot(i)
      fake(i) = if (it == null) null else it.copy
    }

    items.forall(FemtocraftUtils.placeItem(_, fake, null))
  }

  protected def getMaxSimultaneousSmelt = MAX_SMELT

  override protected def continueWork() {
    cookTime += 1
  }

  override protected def canFinishWork = cookTime >= getTicksToCook

  protected def getTicksToCook = TICKS_TO_COOK

  override protected def finishWork() {
    val recipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(deconstructingStack)
    val placementRestrictionArray: Array[Int] = Array[Int](0)
    var i: Int = 0
    while (i < deconstructingStack.stackSize) {
      if (recipe != null) {
        for (item <- recipe.input) {
          FemtocraftUtils.placeItem(item, inventory.getInventory, placementRestrictionArray)
        }
        if (massTank.getFluid == null) {
          massTank.setFluid(new FluidStack(Femtocraft.fluidMass, recipe.mass))
        } else {
          massTank.getFluid.amount += recipe.mass
        }
      }
      i += recipe.output.stackSize
    }

    deconstructingStack = null
    cookTime = 0
    markDirty()
  }
}
