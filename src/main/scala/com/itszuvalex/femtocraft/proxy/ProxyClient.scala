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
package com.itszuvalex.femtocraft.proxy

import com.itszuvalex.femtocraft.RenderConstants
import com.itszuvalex.femtocraft.core.tiles.TileEntityDimensionalTear
import com.itszuvalex.femtocraft.power.render._
import com.itszuvalex.femtocraft.render.RenderSimpleMachine
import com.itszuvalex.femtocraft.transport.items.render.RenderVacuumTube
import com.itszuvalex.femtocraft.transport.liquids.render.RenderSuctionPipe
import cpw.mods.fml.client.registry.{ClientRegistry, RenderingRegistry}
import net.minecraft.client.renderer.tileentity.RenderDimensionalTear



class ProxyClient extends ProxyCommon {
  override def registerRendering() {
    super.registerRendering()
  }

  override def registerBlockRenderers() {
    super.registerBlockRenderers()
    RenderSimpleMachine.renderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderSimpleMachine.renderID, new RenderSimpleMachine)
    RenderConstants.microCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.microCableRenderID, new RenderMicroCable)
    RenderConstants.nanoCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.nanoCableRenderID, new RenderNanoCable)
    RenderConstants.femtoCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.femtoCableRenderID, new RenderFemtoCable)
    RenderConstants.FemtocraftVacuumTubeRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftVacuumTubeRenderID, new RenderVacuumTube)
    RenderConstants.FemtocraftChargingBaseRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftChargingBaseRenderID, new RenderChargingBase)
    RenderConstants.FemtocraftChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftChargingCoilRenderID, new RenderChargingCoil)
    RenderConstants.FemtocraftChargingCapacitorRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftChargingCapacitorRenderID, new RenderChargingCapacitor)
    RenderConstants.FemtocraftCryoEndothermalChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftCryoEndothermalChargingCoilRenderID, new RenderCryoEndothermalChargingCoil)
    RenderConstants.FemtocraftStellaratorCoreRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftStellaratorCoreRenderID, new RenderStellaratorCore)
    RenderConstants.FemtocraftStellaratorFocusRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftStellaratorFocusRenderID, new RenderStellaratorFocus)
    RenderConstants.FemtocraftSuctionPipeRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftSuctionPipeRenderID, new RenderSuctionPipe)
    RenderConstants.FemtocraftPlasmaConduitID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftPlasmaConduitID, new RenderPlasmaConduit)
    RenderConstants.FemtocraftLaserRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderConstants.FemtocraftLaserRenderID, new RenderLaser)

    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityDimensionalTear], new RenderDimensionalTear)
  }
}