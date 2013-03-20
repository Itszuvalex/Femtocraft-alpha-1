package femtocraft.proxy;

import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class ClientProxyFemtocraft extends CommonProxyFemtocraft {
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
	
	
}