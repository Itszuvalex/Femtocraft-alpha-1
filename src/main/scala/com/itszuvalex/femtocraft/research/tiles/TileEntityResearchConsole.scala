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
package com.itszuvalex.femtocraft.research.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel._
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.items.ITechnologyCarrier
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.Inventory
import com.itszuvalex.femtocraft.utils.BaseInventory
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.oredict.OreDictionary

import scala.collection.JavaConversions._

object TileEntityResearchConsole {
  private val ticksToResearch: Int = 400
}

class TileEntityResearchConsole extends TileEntityBase with Inventory {
  @Saveable(desc = true) var displayTech: String = null
  @Saveable(desc = true) private var researchingTech: String = null
  @Saveable private              var progress                = 0
  @Saveable private              var progressMax             = 0


  override def defaultInventory = new BaseInventory(10)

  def getResearchProgress = progress

  def setResearchProgress(progress: Int) {
    this.progress = progress
  }

  def getResearchProgressScaled(scale: Int): Int = {
    if (progressMax == 0) {
      return 0
    }
    (progress * scale) / progressMax
  }

  def isResearching = researchingTech != null

  def getResearchMax = progressMax

  def setResearchMax(progressMax: Int) {
    this.progressMax = progressMax
  }

  def getResearchingName = researchingTech

  override def readFromNBT(par1nbtTagCompound: NBTTagCompound) {
    super.readFromNBT(par1nbtTagCompound)
    checkForTechnology()
  }

  private def checkForTechnology() {
    if (worldObj == null) {
      return
    }
    if (worldObj.isRemote) {
      return
    }
    val hadTech = displayTech != null
    displayTech = null
    Femtocraft.researchManager.getTechnologies.
    filter(Femtocraft.researchManager.hasPlayerDiscoveredTechnology(getOwner, _)).
    filterNot(Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner, _)).
    filter(matchesTechnology).
    foreach { tech =>
      displayTech = tech.getName
      if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
      return
            }
    if (hadTech && displayTech == null) {
      if (worldObj != null) {
        worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
      }
    }
  }

  private def matchesTechnology(tech: ITechnology): Boolean = {
    if (tech == null) {
      return false
    }
    if (tech.getResearchMaterials == null || tech.getResearchMaterials.length == 0) {
      return false
    }
    for (i <- 0 until Math.min(9, tech.getResearchMaterials.length)) {
      if (!compareItemStack(tech.getResearchMaterials(i), getStackInSlot(i))) {
        return false
      }
    }
    true
  }

  private def compareItemStack(tech: ItemStack, inv: ItemStack): Boolean = {
    tech == null && inv == null || tech != null && inv != null && tech.stackSize <= inv.stackSize && tech.getItem == inv.getItem && (tech.getItemDamage == inv.getItemDamage || tech.getItemDamage == OreDictionary.WILDCARD_VALUE || inv.getItemDamage == OreDictionary.WILDCARD_VALUE)
  }

  override def writeToNBT(par1nbtTagCompound: NBTTagCompound) {
    super.writeToNBT(par1nbtTagCompound)
  }

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    if (researchingTech != null) {
      if ( {progress += 1; progress - 1} >= progressMax) {
        finishWork()
      }
    }
  }

  private def finishWork() {
    progress = 0
    progressMax = 0
    val tech = Femtocraft.researchManager.getTechnology(researchingTech)
    if (tech == null) {
      researchingTech = null
      return
    }
    var techItem: Item = null
    tech.getLevel match {
      case MACRO | MICRO                  =>
        techItem = Femtocraft.itemMicroTechnology
      case NANO                           =>
        techItem = Femtocraft.itemNanoTechnology
      case FEMTO | DIMENSIONAL | TEMPORAL =>
        techItem = Femtocraft.itemFemtoTechnology
    }
    val techstack = new ItemStack(techItem, 1)
    techItem.asInstanceOf[ITechnologyCarrier].setTechnology(techstack, researchingTech)
    researchingTech = null
    setInventorySlotContents(getSizeInventory - 1, techstack)
    markDirty()
    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
  }

  override def markDirty() {
    super.markDirty()
    checkForTechnology()
  }

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.ResearchConsoleGuiID

  def startWork() {
    if (!canWork) {
      return
    }
    researchingTech = displayTech
    progressMax = TileEntityResearchConsole.ticksToResearch
    progress = 0
    val tech = Femtocraft.researchManager.getTechnology(displayTech)
    for (i <- 0 until Math.min(9, tech.getResearchMaterials.length)) {
      if (tech.getResearchMaterials(i) != null) {
        decrStackSize(i, tech.getResearchMaterials(i).stackSize)
      }
    }
    markDirty()
  }

  override def getInventoryName = "Research Console"

  override def hasCustomInventoryName = false

  override def isItemValidForSlot(i: Int, itemstack: ItemStack) = i match {
    case 9 => false
    case _ => true
  }

  private def canWork: Boolean = {
    if (displayTech == null || displayTech.isEmpty) {
      return false
    }
    if (researchingTech != null && !researchingTech.isEmpty) {
      return false
    }
    val tech = Femtocraft.researchManager.getTechnology(displayTech)
    matchesTechnology(tech)
  }
}
