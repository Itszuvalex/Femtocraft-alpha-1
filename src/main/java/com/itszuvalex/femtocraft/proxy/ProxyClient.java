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

package com.itszuvalex.femtocraft.proxy;

import com.itszuvalex.femtocraft.consumables.processing.blocks.RenderCuttingBoard;
import com.itszuvalex.femtocraft.power.render.*;
import com.itszuvalex.femtocraft.render.RenderSimpleMachine;
import com.itszuvalex.femtocraft.transport.items.render.RenderVacuumTube;
import com.itszuvalex.femtocraft.transport.liquids.render.RenderSuctionPipe;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ProxyClient extends ProxyCommon {
    public static int microCableRenderID;
    public static int nanoCableRenderID;
    public static int femtoCableRenderID;

    public static int FemtopowerMicroCubeRenderID;
    public static int FemtocraftVacuumTubeRenderID;
    public static int FemtocraftChargingBaseRenderID;
    public static int FemtocraftChargingCoilRenderID;
    public static int FemtocraftChargingCapacitorRenderID;
    public static int FemtocraftStellaratorCoreRenderID;
    public static int FemtocraftStellaratorFocusRenderID;
    public static int FemtocraftSuctionPipeRenderID;
    public static int FemtocraftPlasmaConduitID;

    public static int CuttingBoardRenderPass;
    public static int cuttingBoardRenderType;
    public static int FemtocraftCryoEndothermalChargingCoilRenderID;


    public static void setCustomRenderers() {
        cuttingBoardRenderType = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(new RenderCuttingBoard());
    }

    @Override
    public void registerRendering() {
        super.registerRendering();
        // MinecraftForgeClient.preloadTexture(texture);
        // RenderingRegistry.registerEntityRenderingHandler(entityClass,
        // renderer);
        // registerBlockHandler
//        ClientRegistry.bindTileEntitySpecialRenderer
//                (TileEntityFemtoStellaratorCore.class, new RenderStellaratorCore());
    }


    @Override
    public void registerBlockRenderers() {
        super.registerBlockRenderers();
//
//        ClientRegistry.bindTileEntitySpecialRenderer(
//                TileEntityOrbitalEqualizer.class, new RenderOrbitalEqualizer());
//        ClientRegistry.bindTileEntitySpecialRenderer(
//                TileEntityNullEqualizer.class, new RenderNullEqualizer());

        RenderSimpleMachine.renderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(RenderSimpleMachine.renderID, new RenderSimpleMachine());

        microCableRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(microCableRenderID, new RenderMicroCable());

        nanoCableRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(nanoCableRenderID, new RenderNanoCable());

        femtoCableRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(femtoCableRenderID, new RenderFemtoCable());


        // FemtopowerMicroCubeRenderID =
        // RenderingRegistry.getNextAvailableRenderId();
        // RenderingRegistry.registerBlockHandler(FemtopowerMicroCubeRenderID,
        // new FemtopowerMicroCubeRenderer());

        FemtocraftVacuumTubeRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftVacuumTubeRenderID, new RenderVacuumTube());

        FemtocraftChargingBaseRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftChargingBaseRenderID, new RenderChargingBase());

        FemtocraftChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftChargingCoilRenderID, new RenderChargingCoil());

        FemtocraftChargingCapacitorRenderID = RenderingRegistry
                .getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler
                (FemtocraftChargingCapacitorRenderID,
                        new RenderChargingCapacitor());

        FemtocraftCryoEndothermalChargingCoilRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftCryoEndothermalChargingCoilRenderID,
                new RenderCryoEndothermalChargingCoil());

        FemtocraftStellaratorCoreRenderID = RenderingRegistry
                .getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler
                (FemtocraftStellaratorCoreRenderID, new RenderStellaratorCore());

        FemtocraftStellaratorFocusRenderID = RenderingRegistry
                .getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler
                (FemtocraftStellaratorFocusRenderID, new RenderStellaratorFocus());

        FemtocraftSuctionPipeRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftSuctionPipeRenderID, new RenderSuctionPipe());

        FemtocraftPlasmaConduitID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftPlasmaConduitID, new RenderPlasmaConduit());

    }
}