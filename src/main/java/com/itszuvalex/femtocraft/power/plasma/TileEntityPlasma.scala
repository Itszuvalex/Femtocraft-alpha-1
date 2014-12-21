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
package com.itszuvalex.femtocraft.power.plasma

import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

/**
 * Created by Christopher Harris (Itszuvalex) on 5/9/14.
 */
object TileEntityPlasma {
  private val durationKey = "duration"
}

class TileEntityPlasma extends TileEntity {
  private var duration: Int = 0

  def getDuration = duration

  def setDuration(duration: Int) {
    this.duration = duration
  }

  override def readFromNBT(par1NBTTagCompound: NBTTagCompound) {
    super.readFromNBT(par1NBTTagCompound)
    par1NBTTagCompound.setInteger(TileEntityPlasma.durationKey, duration)
  }

  override def writeToNBT(par1NBTTagCompound: NBTTagCompound) {
    super.writeToNBT(par1NBTTagCompound)
    duration = par1NBTTagCompound.getInteger(TileEntityPlasma.durationKey)
  }

  override def updateEntity() {
    super.updateEntity()
    if (worldObj.isRemote) {
      return
    }
    if ( {duration -= 1; duration + 1} <= 0) {
      worldObj.setBlockToAir(xCoord, yCoord, zCoord)
    }
  }
}
