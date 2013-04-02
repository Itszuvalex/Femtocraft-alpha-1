package femtocraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.power.render.FemtopowerCableRenderer;

public class ClientProxyFemtocraft extends CommonProxyFemtocraft {
	public static int FemtopowerCableRenderID;
	
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
	}
}