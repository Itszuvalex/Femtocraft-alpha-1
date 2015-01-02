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
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCable._
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer

object TileEntityMicroCable {
  @Configurable(comment = "Power storage maximum.") private val storage   = 250
  @Configurable(comment = "Tech level of power.") private   val techLevel = EnumTechLevel.MICRO
}

@Configurable class TileEntityMicroCable extends TileEntityBase with PowerTileContainer {
  def defaultContainer: PowerContainer = new PowerContainer(techLevel, storage)

  def connectedAcross: Boolean = {
    if (numConnections == 2) {
      if (connections(0) && connections(1) || connections(2) && connections(3) || connections(4) && connections(5)) {
        return true
      }
    }
    return false
  }
}
