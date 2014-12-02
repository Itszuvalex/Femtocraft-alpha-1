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
package com.itszuvalex.femtocraft.managers.research

import com.itszuvalex.femtocraft.managers.research.ResearchStatus._
import net.minecraft.nbt.NBTTagCompound

object ResearchStatus {
  private val techKey     = "tech"
  private val researchKey = "researched"
}

class ResearchStatus(var tech: String, var researched: Boolean) {

  def this(name: String) = this(name, false)

  private def this() = this(null)

  def saveToNBTTagCompound(compound: NBTTagCompound) {
    compound.setString(techKey, tech)
    compound.setBoolean(researchKey, researched)
  }

  def loadFromNBTTagCompound(compound: NBTTagCompound) {
    tech = compound.getString(techKey)
    researched = compound.getBoolean(researchKey)
  }
}
