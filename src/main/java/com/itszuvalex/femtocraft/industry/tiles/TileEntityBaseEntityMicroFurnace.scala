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

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.Inventory
import com.itszuvalex.femtocraft.industry.blocks.BlockMicroFurnace
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace._
import com.itszuvalex.femtocraft.industry.traits.IndustryBehavior
import com.itszuvalex.femtocraft.power.traits.PowerConsumer
import com.itszuvalex.femtocraft.utils.BaseInventory
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.FurnaceRecipes

object TileEntityBaseEntityMicroFurnace {
  @Configurable(comment = "Power tech level.")                          val TECH_LEVEL    = EnumTechLevel.MICRO
  @Configurable(comment = "Power storage maximum.")                     val POWER_STORAGE = 800
  @Configurable(comment = "Power per item to begin processing.")        val POWER_TO_COOK = 40
  @Configurable(comment = "Ticks required to process.")                 val TICKS_TO_COOK = 100
  @Configurable(comment = "Maximum number of items allowed at a time.") val MAX_SMELT     = 1
}

@Configurable class TileEntityBaseEntityMicroFurnace extends TileEntityBase with IndustryBehavior with Inventory with PowerConsumer {
  override def defaultContainer = new PowerContainer(TECH_LEVEL, POWER_STORAGE)

  override def defaultInventory = new BaseInventory(2)

  /**
   * The number of ticks that the current item has been cooking for
   */
  @Saveable var furnaceCookTime          = 0
  @Saveable var currentPower             = 0
  @Saveable var smeltingStack: ItemStack = null
  /**
   * The ItemStacks that hold the items currently being used in the furnace
   */
  @Saveable private var field_94130_e: String = null

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.MicroFurnaceGuiID


  /**
   * Returns the name of the inventory.
   */
  override def getInventoryName = if (this.hasCustomInventoryName) this.field_94130_e else "MicroFurnace"

  /**
   * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
   * language. Otherwise it will be used directly.
   */
  override def hasCustomInventoryName = this.field_94130_e != null && this.field_94130_e.length > 0

  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = i match {
    case (1) =>
      false
    case _ =>
      true
  }

  def func_94129_a(par1Str: String) {
    this.field_94130_e = par1Str
  }

  @SideOnly(Side.CLIENT) def getCookProgressScaled(par1: Int): Int = {
    if (getTicksToCook == 0) {
      return 0
    }
    this.furnaceCookTime * par1 / getTicksToCook
  }

  protected def getTicksToCook = TICKS_TO_COOK

  override def isWorking = smeltingStack != null

  override protected def canStartWork: Boolean = {
    if (getStackInSlot(0) == null) {
      return false
    }
    if (smeltingStack != null) false
    else if (getCurrentPower < getPowerToCook) {
      false
    }
    else {
      val itemstack = FurnaceRecipes.smelting.getSmeltingResult(getStackInSlot(0))
      if (itemstack == null) {
        return false
      }
      if (getStackInSlot(1) == null) {
        return true
      }
      if (!getStackInSlot(1).isItemEqual(itemstack)) {
        return false
      }
      val result = getStackInSlot(1).stackSize + itemstack.stackSize
      result <= getInventoryStackLimit && result <= itemstack.getMaxStackSize
    }
  }

  protected def getPowerToCook = POWER_TO_COOK

  override protected def startWork() {
    smeltingStack = getStackInSlot(0).copy
    smeltingStack.stackSize = 0
    var continue = true
    var i = 0
    do {
      if (getStackInSlot(1) != null && ((smeltingStack.stackSize + i) > getStackInSlot(1).getMaxStackSize)) {
        continue = false
      }
      if (getStackInSlot(0) == null) {
        continue = false
      }
      if (!consume(getPowerToCook)) {
        continue = false
      }
      if (continue) {
        smeltingStack.stackSize += 1
        getStackInSlot(0).stackSize -= 1
        if (getStackInSlot(0).stackSize <= 0) {
          setInventorySlotContents(0, null)
        }
      }
    } while (continue && {i += 1; i} < getMaxSimultaneousSmelt)
    updateBlockState(true)
    this.onInventoryChanged()
    furnaceCookTime = 0
  }

  protected def getMaxSimultaneousSmelt = MAX_SMELT

  protected def updateBlockState(working: Boolean) {
    BlockMicroFurnace.updateFurnaceBlockState(working, this.worldObj, this.xCoord, this.yCoord, this.zCoord)
  }

  override protected def continueWork() {
    furnaceCookTime += 1
  }

  override protected def canFinishWork = furnaceCookTime == getTicksToCook

  override protected def finishWork() {
    val itemstack = FurnaceRecipes.smelting.getSmeltingResult(smeltingStack)
    if (itemstack != null) {
      if (getStackInSlot(1) == null) {
        setInventorySlotContents(1, itemstack.copy)
        getStackInSlot(1).stackSize = smeltingStack.stackSize
      }
      else if (getStackInSlot(1).isItemEqual(itemstack)) {
        getStackInSlot(1).stackSize += smeltingStack.stackSize
      }
      smeltingStack = null
      updateBlockState(false)
      this.onInventoryChanged()
    }
    furnaceCookTime = 0
  }

  override def getAccessibleSlotsFromSide(var1: Int) = var1 match {
    case 1 => Array[Int](0)
    case _ => Array[Int](1)
  }

  override def canInsertItem(i: Int, itemstack: ItemStack, j: Int) = i match {
    case 1 => false
    case _ => true
  }

  override def canExtractItem(i: Int, itemstack: ItemStack, j: Int) = true
}
