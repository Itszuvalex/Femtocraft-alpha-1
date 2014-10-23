/** *****************************************************************************
  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
  * Itszuvalex@gmail.com
  *
  * This program is free software; you can redistribute it and/or
  * modify it under the terms of the GNU General Public License
  * as published by the Free Software Foundation; either version 2
  * of the License, or (at your option) any later version.
  *
  * This program is distributed in the hope that it will be useful,
  * but WITHOUT ANY WARRANTY; without even the implied warranty of
  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
  * GNU General Public License for more details.
  *
  * You should have received a copy of the GNU General Public License
  * along with this program; if not, write to the Free Software
  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
  * *****************************************************************************/
package com.itszuvalex.femtocraft.network

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.network.messages._
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.relauncher.Side

object FemtocraftPacketHandler {
  val INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Femtocraft.ID.toLowerCase)

  def init() {
    INSTANCE.registerMessage(classOf[MessageResearchPlayer], classOf[MessageResearchPlayer], 0, Side.CLIENT)
    INSTANCE.registerMessage(classOf[MessageResearchConsoleStart], classOf[MessageResearchConsoleStart], 1, Side.SERVER)
    INSTANCE.registerMessage(classOf[MessageContainerUpdate], classOf[MessageContainerUpdate], 2, Side.CLIENT)
    INSTANCE.registerMessage(classOf[MessagePlayerProperty], classOf[MessagePlayerProperty], 3, Side.CLIENT)
    INSTANCE.registerMessage(classOf[MessageFissionReactorCore], classOf[MessageFissionReactorCore], 4, Side.SERVER)
    INSTANCE.registerMessage(classOf[MessagePhlegethonTunnelCore], classOf[MessagePhlegethonTunnelCore], 5, Side.SERVER)
  }
}

