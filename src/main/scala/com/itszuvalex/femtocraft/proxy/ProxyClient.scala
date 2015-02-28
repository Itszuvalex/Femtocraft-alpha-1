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

import com.itszuvalex.femtocraft.core.tiles.TileEntityDimensionalTear
import com.itszuvalex.femtocraft.power.render._
import com.itszuvalex.femtocraft.render.RenderSimpleMachine
import com.itszuvalex.femtocraft.transport.items.render.RenderVacuumTube
import com.itszuvalex.femtocraft.transport.liquids.render.RenderSuctionPipe
import cpw.mods.fml.client.registry.{ClientRegistry, RenderingRegistry}
import net.minecraft.client.renderer.tileentity.RenderDimensionalTear

object ProxyClient {
  var microCableRenderID                            = 0
  var nanoCableRenderID                             = 0
  var femtoCableRenderID                            = 0
  var FemtopowerMicroCubeRenderID                   = 0
  var FemtocraftVacuumTubeRenderID                  = 0
  var FemtocraftChargingBaseRenderID                = 0
  var FemtocraftChargingCoilRenderID                = 0
  var FemtocraftChargingCapacitorRenderID           = 0
  var FemtocraftStellaratorCoreRenderID             = 0
  var FemtocraftStellaratorFocusRenderID            = 0
  var FemtocraftSuctionPipeRenderID                 = 0
  var FemtocraftPlasmaConduitID                     = 0
  var CuttingBoardRenderPass                        = 0
  var cuttingBoardRenderType                        = 0
  var FemtocraftCryoEndothermalChargingCoilRenderID = 0
  var FemtocraftLaserRenderID                       = 0
}

class ProxyClient extends ProxyCommon {
  override def registerRendering() {
    super.registerRendering()
  }

  override def registerBlockRenderers() {
    super.registerBlockRenderers()
    RenderSimpleMachine.renderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(RenderSimpleMachine.renderID, new RenderSimpleMachine)
    ProxyClient.microCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.microCableRenderID, new RenderMicroCable)
    ProxyClient.nanoCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.nanoCableRenderID, new RenderNanoCable)
    ProxyClient.femtoCableRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.femtoCableRenderID, new RenderFemtoCable)
    ProxyClient.FemtocraftVacuumTubeRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftVacuumTubeRenderID, new RenderVacuumTube)
    ProxyClient.FemtocraftChargingBaseRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftChargingBaseRenderID, new RenderChargingBase)
    ProxyClient.FemtocraftChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftChargingCoilRenderID, new RenderChargingCoil)
    ProxyClient.FemtocraftChargingCapacitorRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftChargingCapacitorRenderID, new RenderChargingCapacitor)
    ProxyClient.FemtocraftCryoEndothermalChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftCryoEndothermalChargingCoilRenderID, new RenderCryoEndothermalChargingCoil)
    ProxyClient.FemtocraftStellaratorCoreRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftStellaratorCoreRenderID, new RenderStellaratorCore)
    ProxyClient.FemtocraftStellaratorFocusRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftStellaratorFocusRenderID, new RenderStellaratorFocus)
    ProxyClient.FemtocraftSuctionPipeRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftSuctionPipeRenderID, new RenderSuctionPipe)
    ProxyClient.FemtocraftPlasmaConduitID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftPlasmaConduitID, new RenderPlasmaConduit)
    ProxyClient.FemtocraftLaserRenderID = RenderingRegistry.getNextAvailableRenderId
    RenderingRegistry.registerBlockHandler(ProxyClient.FemtocraftLaserRenderID, new RenderLaser)

    ClientRegistry.bindTileEntitySpecialRenderer(classOf[TileEntityDimensionalTear], new RenderDimensionalTear)
  }
}