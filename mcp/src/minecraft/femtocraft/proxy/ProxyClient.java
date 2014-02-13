package femtocraft.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.cooking.blocks.RenderCuttingBoard;
import femtocraft.industry.render.RenderVacuumTube;
import femtocraft.power.render.RenderCable;
import femtocraft.power.render.RenderChargingBase;
import femtocraft.power.render.RenderChargingCoil;
import femtocraft.render.RenderSimpleMachine;

public class ProxyClient extends ProxyCommon {
	public static int FemtopowerCableRenderID;
	public static int FemtopowerMicroCubeRenderID;
	public static int FemtocraftVacuumTubeRenderID;
	public static int FemtocraftChargingBaseRenderID;
	public static int FemtocraftChargingCoilRenderID;

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

		RenderSimpleMachine.renderID = RenderingRegistry
				.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(RenderSimpleMachine.renderID,
				new RenderSimpleMachine());

		FemtopowerCableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtopowerCableRenderID,
				new RenderCable());

		// FemtopowerMicroCubeRenderID =
		// RenderingRegistry.getNextAvailableRenderId();
		// RenderingRegistry.registerBlockHandler(FemtopowerMicroCubeRenderID,
		// new FemtopowerMicroCubeRenderer());

		FemtocraftVacuumTubeRenderID = RenderingRegistry
				.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtocraftVacuumTubeRenderID,
				new RenderVacuumTube());

		FemtocraftChargingBaseRenderID = RenderingRegistry
				.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtocraftChargingBaseRenderID,
				new RenderChargingBase());

		FemtocraftChargingCoilRenderID = RenderingRegistry
				.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtocraftChargingCoilRenderID,
				new RenderChargingCoil());

	}
}