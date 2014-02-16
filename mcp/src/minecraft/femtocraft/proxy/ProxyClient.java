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