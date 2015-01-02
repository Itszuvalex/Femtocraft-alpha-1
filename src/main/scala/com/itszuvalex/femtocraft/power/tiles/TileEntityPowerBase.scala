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


import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.power.IPowerTileContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import net.minecraftforge.common.util.ForgeDirection

abstract class TileEntityPowerBase extends TileEntityBase with PowerTileContainer {

  override def setMaxStorage(maxStorage: Int) {
    super.setMaxStorage(maxStorage)
    setModified()
  }

  override def setCurrentStorage(currentStorage: Int) {
    super.setCurrentStorage(currentStorage)
    setModified()
  }

  override def setTechLevel(level: EnumTechLevel) {
    super.setTechLevel(level)
    setModified()
  }

  override def updateEntity() {
    checkConnections()
    super.updateEntity()
  }



  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    FemtocraftPowerUtils.distributePower(this, connections, worldObj, xCoord, yCoord, zCoord)
    setModified()
  }

  override def charge(from: ForgeDirection, amount: Int) = {
    val ret = super.charge(from, amount)
    if (ret > 0) {
      setModified()
    }
    ret
  }

  override def consume(amount: Int): Boolean = {
    if (super.consume(amount)) {
      setModified()
      return true
    }
    false
  }
}

