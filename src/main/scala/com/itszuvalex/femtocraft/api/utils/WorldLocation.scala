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
package com.itszuvalex.femtocraft.api.utils

import com.itszuvalex.femtocraft.api.core.ISaveable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager

/**
 * Created by Christopher Harris (Itszuvalex) on 5/9/14.
 */
class WorldLocation(var world: World, var x: Int, var y: Int, var z: Int) extends ISaveable with Comparable[WorldLocation] {

  def this() = this(null, 0, 0, 0)

  override def equals(o: scala.Any): Boolean = {
    if (o == this) return true
    if (!o.isInstanceOf[WorldLocation]) return false
    val that: WorldLocation = o.asInstanceOf[WorldLocation]
    if (x != that.x) return false
    if (y != that.y) return false
    if (z != that.z) return false
    if (world == null && that.world != null) return false
    if (that.world == null && world != null) return false
    if (world == null) return true
    if (world.provider.dimensionId != that.world.provider.dimensionId) return false
    true
  }

  override def hashCode: Int = {
    var result: Int = if (world == null) 0 else world.provider.dimensionId
    result = 31 * result + x
    result = 31 * result + y
    result = 31 * result + z
    result
  }

  def saveToNBT(compound: NBTTagCompound) {
    compound.setInteger("x", x)
    compound.setInteger("y", y)
    compound.setInteger("z", z)
    if (world != null && !world.isRemote) compound.setInteger("dim", world.provider.dimensionId)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    x = compound.getInteger("x")
    y = compound.getInteger("y")
    z = compound.getInteger("z")
    world = DimensionManager.getWorld(compound.getInteger("dim"))
  }

  def getTileEntity = if (world == null) null else world.getTileEntity(x, y, z)

  def getBlock = if (world == null) null else world.getBlock(x, y, z)

  def compareTo(o: WorldLocation): Int = {
    if (x < o.x) return -1
    if (x > o.x) return 1
    if (y < o.y) return -1
    if (y > o.y) return 1
    if (z < o.z) return -1
    if (z > o.z) return 1
    if (world == null && o.world != null) return -1
    if (world != null && o.world == null) return 1
    if (world == null) return 0
    if (world.provider.dimensionId < o.world.provider.dimensionId) return -1
    if (world.provider.dimensionId > o.world.provider.dimensionId) return 1
    0
  }
}
