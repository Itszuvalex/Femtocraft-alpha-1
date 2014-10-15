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


import com.itszuvalex.femtocraft.api.power.IPowerBlockContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils
import com.itszuvalex.femtocraft.power.traits.PowerBlockContainer
import net.minecraftforge.common.ForgeDirection

abstract class TileEntityPowerBase() extends TileEntityBase() with PowerBlockContainer {
  val connections = Array.fill[Boolean](6)(false)

  /**
   * Have to implement the generated() function to allow Java inheritance.
   */

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

  def isConnected(i: Int) = connections(i)

  def numConnections = connections.count(b => b)


  override def updateEntity() {
    checkConnections()
    super.updateEntity()
  }

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    FemtocraftPowerUtils.distributePower(this, connections, worldObj, xCoord, yCoord, zCoord)
    setModified()
  }

  def checkConnections() {
    var changed = false
    ForgeDirection.VALID_DIRECTIONS.foreach(offset => {
      val prev = connections(offset.ordinal())
      connections(offset.ordinal()) = false
      val locx = this.xCoord + offset.offsetX
      val locy = this.yCoord + offset.offsetY
      val locz = this.zCoord + offset.offsetZ
      val checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz)
      checkTile match {
        case fc: IPowerBlockContainer =>
          if (fc.canConnect(offset.getOpposite) && fc.canAcceptPowerOfLevel(getTechLevel(offset.getOpposite), offset.getOpposite)) {
            connections(offset.ordinal()) = true
            if (prev != connections(offset.ordinal())) {
              changed = true
            }
          }
        case _                        =>
      }
    })
    if (changed) {
      setModified()
      setRenderUpdate()
    }
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

