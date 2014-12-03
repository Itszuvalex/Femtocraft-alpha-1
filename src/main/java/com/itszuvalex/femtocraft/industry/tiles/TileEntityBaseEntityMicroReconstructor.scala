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

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.industry.IAssemblerSchematic
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.{Inventory, MassTank}
import com.itszuvalex.femtocraft.industry.traits.IndustryBehavior
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import com.itszuvalex.femtocraft.utils.{BaseInventory, FemtocraftUtils}
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.item.ItemStack
import net.minecraftforge.fluids._

object TileEntityBaseEntityMicroReconstructor {
  @Configurable(comment = "Power tech level.")                          val TECH_LEVEL           = EnumTechLevel.MICRO
  @Configurable(comment = "Power storage maximum.")                     val POWER_STORAGE        = 800
  @Configurable(comment = "Mass storage maximum.")                      val MASS_STORAGE         = 600
  @Configurable(comment = "Power per item to begin processing.")        val POWER_TO_COOK        = 40
  @Configurable(comment = "Ticks required to process.")                 val TICKS_TO_COOK        = 100
  @Configurable(comment = "Maximum number of items allowed at a time.") val MAX_SMELT            = 1
  @Configurable(comment = "Assembler recipe tech level maximum.")       val ASSEMBLER_TECH_LEVEL = EnumTechLevel.MICRO
}

@Configurable class TileEntityBaseEntityMicroReconstructor extends TileEntityBase with IndustryBehavior with Inventory with MassTank with PowerConsumer {
  /**
   * The number of ticks that the current item has been cooking for
   */
  @Saveable var cookTime            : Int              = 0
  @Saveable var currentPower        : Int              = 0
  @Saveable var reconstructingStacks: Array[ItemStack] = null
  private var hasItems: Boolean = true

  @Saveable private var field_94130_e: String = null

  override def defaultContainer = new PowerContainer(TileEntityBaseEntityMicroReconstructor.TECH_LEVEL, TileEntityBaseEntityMicroReconstructor.POWER_STORAGE)

  /**
   * Slots 0-8 are for recipe area - these are dummy items, and should never be touched except when setting for
   * display purposes Slot 9 is for output Slot 10 is for schematic card Slots 11-28 are internal inventory, to pull
   * from when building
   */
  override def defaultInventory = new BaseInventory(29)

  override def defaultTank = new FluidTank(TileEntityBaseEntityMicroReconstructor.MASS_STORAGE)

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.MicroReconstructorGuiID

  def getMassCapacity = massTank.getCapacity

  /**
   * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
   * new stack.
   */
  override def decrStackSize(par1: Int, par2: Int): ItemStack = {
    if (par1 < 9) return null
    super.decrStackSize(par1, par2)
  }

  override def getStackInSlotOnClosing(par1: Int): ItemStack = {
    if (par1 < 9) {
      return null
    }
    super.getStackInSlotOnClosing(par1)
  }

  override def getInventoryName = if (this.hasCustomInventoryName) this.field_94130_e else "Microtech Reconstructor"

  override def hasCustomInventoryName = this.field_94130_e != null && this.field_94130_e.length > 0

  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = i > 10 || (i == 10 && itemstack.getItem.isInstanceOf[IAssemblerSchematic])

  def func_94129_a(par1Str: String) {
    this.field_94130_e = par1Str
  }

  @SideOnly(Side.CLIENT) def getCookProgressScaled(par1: Int): Int = {
    if (getTicksToCook == 0) return 0
    this.cookTime * par1 / getTicksToCook
  }

  protected def getTicksToCook = TileEntityBaseEntityMicroReconstructor.TICKS_TO_COOK

  override def isWorking = reconstructingStacks != null

  override def getAccessibleSlotsFromSide(var1: Int): Array[Int] = var1 match {
    case 5 =>
      Array[Int](9)
    case _ =>
      (11 until getSizeInventory).toArray
  }

  override def canInsertItem(i: Int, itemstack: ItemStack, j: Int) = i > 10

  override def canExtractItem(i: Int, itemstack: ItemStack, j: Int) = i == 9 || i > 10

  def setFluidAmount(amount: Int) {
    if (massTank.getFluid != null) {
      massTank.setFluid(new FluidStack(massTank.getFluid.getFluid, amount))
    }
    else {
      massTank.setFluid(new FluidStack(Femtocraft.fluidMass, amount))
    }
  }

  def clearFluid() {
    massTank.setFluid(null)
  }

  override protected def canStartWork: Boolean = {
    if (reconstructingStacks != null || !(0 until 9).exists(getStackInSlot(_) != null) || getSchematic == null || this.getCurrentPower < getPowerToCook || cookTime > 0) {
      false
    }
    else {
      if (!hasItems) {
        return false
      }
      val recipe = getSchematic.getRecipe(getStackInSlot(10))
      if (recipe == null) {
        return false
      }
      if (!hasItems(recipe.input)) {
        hasItems = false
        return false
      }
      if (recipe.enumTechLevel.tier > getAssemblerTech.tier) {
        return false
      }
      massTank.getFluidAmount >= recipe.mass && roomForItem(recipe.output)
    }
  }

  private def getSchematic: IAssemblerSchematic = {
    val is = getStackInSlot(10)
    if (is == null) {
      return null
    }
    is.getItem match {
      case schematic: IAssemblerSchematic =>
        return schematic
      case _                              =>
    }
    null
  }

  protected def getPowerToCook = TileEntityBaseEntityMicroReconstructor.POWER_TO_COOK

  private def hasItems(items: Array[ItemStack]): Boolean = {
    val offset = 10
    val size = getSizeInventory
    val inv = new Array[ItemStack](size - offset)
    (offset until size).map(i => (i, getStackInSlot(i))).foreach { case (i, it) => inv(i - offset) = if (it == null) null else it.copy}
    items.forall(FemtocraftUtils.removeItem(_, inv, null))
  }

  protected def getAssemblerTech = TileEntityBaseEntityMicroReconstructor.ASSEMBLER_TECH_LEVEL

  private def roomForItem(item: ItemStack): Boolean = {
    val fake = new Array[ItemStack](1)
    val output = getStackInSlot(9)
    fake(0) = if (output == null) null else output.copy
    FemtocraftUtils.placeItem(item, fake, null)
  }

  override protected def startWork() {
    var as = getSchematic
    var recipe = as.getRecipe(getStackInSlot(10))
    if (recipe == null) {
      return
    }
    val icopy = new Array[ItemStack](9)
    for (i <- 0 until recipe.input.length) {
      icopy(i) = if (recipe.input(i) == null) null else recipe.input(i).copy
    }

    var maxSimul = (if (getStackInSlot(9) == null) recipe.output.getMaxStackSize else getStackInSlot(9).stackSize) / recipe.output.stackSize
    for (i <- 0 until icopy.length) {
      {
        val is = icopy(i)
        if (is != null) {
          val iter = is.getMaxStackSize / is.stackSize
          maxSimul = Math.min(iter, maxSimul)
        }
      }
    }

    reconstructingStacks = null
    var i: Int = 0
    val exclusions: Array[Int] = Array[Int](0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
    var continue = true
    do {
      as = getSchematic
      if (as == null) {
        continue = false
      }
      if (continue) {
        recipe = as.getRecipe(getStackInSlot(10))
        if (recipe == null) {
          continue = false
        }
        if (as.usesRemaining(getStackInSlot(10)) == 0) {
          continue = false
        }
        if (getCurrentPower < getPowerToCook) {
          continue = false
        }
        if (continue) {
          if (getMassAmount < recipe.mass) {
            continue = false
          }
          if (!hasItems(recipe.input)) {
            continue = false
          }
          if (continue) {
            if (i == 0) {
              reconstructingStacks = icopy
            }
            else {
              for (j <- 0 until recipe.input.length) {
                if (recipe.input(j) != null) {
                  reconstructingStacks(j).stackSize += recipe.input(j).stackSize
                }
              }
            }
            for (item <- recipe.input) {
              FemtocraftUtils.removeItem(item, inventory.getInventory, exclusions)
              markDirty()
            }
            consume(getPowerToCook)
            massTank.drain(recipe.mass, true)
            if (!as.onAssemble(getStackInSlot(10))) {
              setInventorySlotContents(10, as.resultOfBreakdown(getStackInSlot(10)))
              markDirty()
            }
            i += 1
          }
        }
      }
    } while (continue && i < getMaxSimultaneousSmelt && i < maxSimul)
    if (reconstructingStacks != null) {
      cookTime = 0
    }
  }

  override def markDirty() {
    val as = getSchematic
    if (as != null) {
      val recipe = as.getRecipe(getStackInSlot(10))
      if (recipe == null) (0 until 9).foreach(inventory.setInventorySlotContents(_, null))
      else (0 until recipe.input.length).map((i: Int) => (i, recipe.input(i))).foreach { case (i, item) => inventory.setInventorySlotContents(i, if (item == null) null else item.copy)}
    }
    else {
      for (i <- 0 until 9) {
        inventory.setInventorySlotContents(i, null)
      }
    }
    hasItems = true
    super.markDirty()
  }

  def getMassAmount = massTank.getFluidAmount

  protected def getMaxSimultaneousSmelt = TileEntityBaseEntityMicroReconstructor.MAX_SMELT

  override protected def continueWork() {
    cookTime += 1
  }

  override protected def canFinishWork = cookTime >= getTicksToCook

  override protected def finishWork() {
    var empty = false

    val placeRestrictions = ((0 until getSizeInventory).toBuffer - 9).toArray
    var continue = true
    while (continue && reconstructingStacks != null && !empty) {
      val recipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(reconstructingStacks)
      if (recipe != null) {
        empty = true
        for (i <- 0 until math.min(9, recipe.input.length)) {
          if (reconstructingStacks(i) != null) {
            if ( {reconstructingStacks(i).stackSize -= recipe.input(i).stackSize; reconstructingStacks(i).stackSize} <= 0) {
              reconstructingStacks(i) = null
            }
            else {
              empty = false
            }
          }
        }

        FemtocraftUtils.placeItem(recipe.output, inventory.getInventory, placeRestrictions)
        markDirty()
      }
      else {
        continue = false
      }
    }
    reconstructingStacks = null
    cookTime = 0
  }
}
