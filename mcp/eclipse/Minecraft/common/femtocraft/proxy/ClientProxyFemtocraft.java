package femtocraft.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.cooking.blocks.cuttingBoardRenderer;
import femtocraft.industry.render.VacuumTubeRenderer;
import femtocraft.power.render.FemtopowerCableRenderer;
import femtocraft.power.render.FemtopowerChargingBaseRenderer;
import femtocraft.render.SimpleMachineRenderer;

public class ClientProxyFemtocraft extends CommonProxyFemtocraft {
	public static int FemtopowerCableRenderID;
	public static int FemtopowerMicroCubeRenderID;
	public static int FemtocraftVacuumTubeRenderID;
	public static int FemtocraftChargingBaseRenderID;
	
	public static int CuttingBoardRenderPass;
	public static int cuttingBoardRenderType;
	
	public static void setCustomRenderers()
	{
		cuttingBoardRenderType = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(new cuttingBoardRenderer());
	}
	
	@Override
	public void registerRendering() {
		super.registerRendering();
		// MinecraftForgeClient.preloadTexture(texture);
		// RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
		// registerBlockHandler
	}
	
	@Override
	public void registerTileEntities() {
		super.registerTileEntities();
	}
	
	@Override
	public void registerBlockRenderers() {
		super.registerBlockRenderers();
		
		SimpleMachineRenderer.renderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(SimpleMachineRenderer.renderID, new SimpleMachineRenderer());
		
		
		
		FemtopowerCableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtopowerCableRenderID, new FemtopowerCableRenderer());
		
//		FemtopowerMicroCubeRenderID = RenderingRegistry.getNextAvailableRenderId();
//		RenderingRegistry.registerBlockHandler(FemtopowerMicroCubeRenderID, new FemtopowerMicroCubeRenderer());
		
		FemtocraftVacuumTubeRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtocraftVacuumTubeRenderID, new VacuumTubeRenderer());	
		
		FemtocraftChargingBaseRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtocraftChargingBaseRenderID, new FemtopowerChargingBaseRenderer());
		
	}
}