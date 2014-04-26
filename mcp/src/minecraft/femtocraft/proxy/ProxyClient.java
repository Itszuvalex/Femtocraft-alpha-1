/*******************************************************************************
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
 ******************************************************************************/

package femtocraft.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.consumables.processing.blocks.RenderCuttingBoard;
import femtocraft.power.render.*;
import femtocraft.render.RenderSimpleMachine;
import femtocraft.transport.items.render.RenderVacuumTube;
import femtocraft.transport.liquids.render.RenderSuctionPipe;

public class ProxyClient extends ProxyCommon {
    public static int microCableRenderID;
    public static int nanoCableRenderID;
    public static int femtoCableRenderID;

    public static int FemtopowerMicroCubeRenderID;
    public static int FemtocraftVacuumTubeRenderID;
    public static int FemtocraftChargingBaseRenderID;
    public static int FemtocraftChargingCoilRenderID;
    public static int FemtocraftSuctionPipeRenderID;

    public static int CuttingBoardRenderPass;
    public static int cuttingBoardRenderType;

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
    }


    @Override
    public void registerBlockRenderers() {
        super.registerBlockRenderers();

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

        FemtocraftSuctionPipeRenderID = RenderingRegistry.getNextAvailableRenderId();
        RenderingRegistry.registerBlockHandler(FemtocraftSuctionPipeRenderID, new RenderSuctionPipe());

    }
}