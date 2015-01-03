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
package com.itszuvalex.femtocraft.render

import com.itszuvalex.femtocraft.api.core.{ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by Christopher Harris (Itszuvalex) on 5/16/14.
 */
class RenderVector3(@Saveable var x: Double, @Saveable var y: Double, @Saveable var z: Double) extends ISaveable {

  def this(a: RenderPoint, b: RenderPoint) =
    this(a.x - b.x, a.y - b.y, a.z - b.z)

  def normalized: RenderVector3 = {
    val ret: RenderVector3 = new RenderVector3(x, y, z)
    val mag: Double = ret.magnitude
    ret.x = ret.x / mag
    ret.y = ret.y / mag
    ret.z = ret.z / mag
    ret
  }

  def magnitude = Math.sqrt((x * x) + (y * y) + (z * z))

  def crossProduct(vector: RenderVector3): RenderVector3 = {
    val ret = new RenderVector3
    ret.x = y * vector.z - z * vector.y
    ret.y = z * vector.x - x * vector.z
    ret.z = x * vector.y - y * vector.x
    ret
  }

  def this() =
    this(0, 0, 0)

  def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }
}
