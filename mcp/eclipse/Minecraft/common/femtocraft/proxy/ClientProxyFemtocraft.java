package femtocraft.proxy;

import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.cooking.blocks.cuttingBoardRenderer;
import femtocraft.power.render.FemtopowerCableRenderer;
import femtocraft.power.render.FemtopowerMicroCubeRenderer;

public class ClientProxyFemtocraft extends CommonProxyFemtocraft {
	public static int FemtopowerCableRenderID;
	public static int FemtopowerMicroCubeRenderID;
	
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
		FemtopowerCableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtopowerCableRenderID, new FemtopowerCableRenderer());
		
		FemtopowerMicroCubeRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtopowerMicroCubeRenderID, new FemtopowerMicroCubeRenderer());
		
	}
}