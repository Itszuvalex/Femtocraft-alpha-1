package femtocraft.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerCableTile;
import femtocraft.power.render.FemtopowerCableRenderer;

public class ClientProxyFemtocraft extends CommonProxyFemtocraft {
	public static int FemtopowerCableRenderID;
	
	@Override
	public void registerRendering() {
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
		FemtopowerCableRenderID = RenderingRegistry.getNextAvailableRenderId();
		RenderingRegistry.registerBlockHandler(FemtopowerCableRenderID, new FemtopowerCableRenderer());
	}
}