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
import com.itszuvalex.femtocraft.power.tiles.TileEntityOrbitalEqualizer._
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import net.minecraftforge.common.util.ForgeDirection

object TileEntityOrbitalEqualizer {
  @Configurable(comment = "Maximum power storage.")                      val POWER_STORAGE        : Int           = 2000
  @Configurable(comment = "Power level to distribute energy on first.")  val PRIMARY_POWER_LEVEL  : EnumTechLevel = EnumTechLevel.NANO
  @Configurable(comment = "Power level to distribute energy on second.") var SECONDARY_POWER_LEVEL: EnumTechLevel = EnumTechLevel.MICRO
}

@Configurable class TileEntityOrbitalEqualizer extends TileEntityBase with PowerTileContainer {
  def defaultContainer = new PowerContainer(PRIMARY_POWER_LEVEL, POWER_STORAGE)

  override def canAcceptPowerOfLevel(level: EnumTechLevel, from: ForgeDirection) = level == SECONDARY_POWER_LEVEL || level == PRIMARY_POWER_LEVEL

  override def femtocraftServerUpdate() {
    if (getTechLevel(ForgeDirection.UNKNOWN) eq PRIMARY_POWER_LEVEL) {
      setTechLevel(SECONDARY_POWER_LEVEL)
    }
    else {
      setTechLevel(PRIMARY_POWER_LEVEL)
    }
    super.femtocraftServerUpdate()
  }
}
