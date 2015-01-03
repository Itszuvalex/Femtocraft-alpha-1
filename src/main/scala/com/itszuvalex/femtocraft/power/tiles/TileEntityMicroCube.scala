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
package com.itszuvalex.femtocraft.power.tiles

import java.util

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCube._
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

object TileEntityMicroCube {
  @Configurable(comment = "Maximum power storage.") val maxStorage      = 10000
  @Configurable(comment = "Tech level of power.")   val ENUM_TECH_LEVEL = EnumTechLevel.MICRO

  def getDefaultContainer = new PowerContainer(ENUM_TECH_LEVEL, maxStorage)
}

@Configurable class TileEntityMicroCube extends TileEntityBase with PowerTileContainer {
  var outputs: Array[Boolean] = new Array[Boolean](6)

  util.Arrays.fill(outputs, false)

  override def defaultContainer = TileEntityMicroCube.getDefaultContainer

  override def getFillPercentageForCharging(from: ForgeDirection): Float = if (outputs(from.ordinal)) 1f else 0f

  override def charge(from: ForgeDirection, amount: Int): Int = {
    if (!outputs(from.ordinal)) {
      return super.charge(from, amount)
    }
    0
  }

  override def getFillPercentageForOutput(to: ForgeDirection) = if (outputs(to.ordinal)) 1f else 0f

  override def canCharge(from: ForgeDirection) = !outputs(from.ordinal) && super.canCharge(from)

  override def handleDescriptionNBT(compound: NBTTagCompound) {
    super.handleDescriptionNBT(compound)
    parseOutputMask(compound.getByte("outputs"))
  }

  def parseOutputMask(mask: Byte) {
    var temp: Byte = 0
    for (i <- 0 until 6) {
      temp = mask
      outputs(i) = ((temp >> i) & 1) == 1
    }
    if (worldObj != null) {
      setRenderUpdate()
    }
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val item = par5EntityPlayer.getCurrentEquippedItem
    if (item != null && item.getItem.isInstanceOf[IInterfaceDevice]) {
      if (!canPlayerUse(par5EntityPlayer)) {
        return false
      }
      var dir: ForgeDirection = ForgeDirection.getOrientation(side)
      if (par5EntityPlayer.isSneaking) {
        dir = dir.getOpposite
      }
      val s = dir.ordinal
      outputs(s) = !outputs(s)
      setUpdate()
      return true
    }
    super.onSideActivate(par5EntityPlayer, side)
  }

  override def writeToNBT(par1nbtTagCompound: NBTTagCompound) {
    super.writeToNBT(par1nbtTagCompound)
    par1nbtTagCompound.setByte("outputs", generateOutputMask)
  }

  override def readFromNBT(par1nbtTagCompound: NBTTagCompound) {
    super.readFromNBT(par1nbtTagCompound)
    parseOutputMask(par1nbtTagCompound.getByte("outputs"))
  }

  override def hasGUI = true

  override def getGuiID = FemtocraftGuiConstants.MicroCubeGuiID

  override def saveToDescriptionCompound(compound: NBTTagCompound) {
    super.saveToDescriptionCompound(compound)
    compound.setByte("outputs", generateOutputMask)
  }

  def generateOutputMask: Byte = {
    var output = 0
    for (i <- 0 until 6) {
      if (outputs(i)) {
        output += 1 << i
      }
    }
    output.toByte
  }
}
