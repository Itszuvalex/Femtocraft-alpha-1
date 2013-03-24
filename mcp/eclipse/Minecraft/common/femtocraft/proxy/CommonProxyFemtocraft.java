package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.power.TileEntity.FemtopowerConsumer;
import femtocraft.power.TileEntity.FemtopowerProducer;
import femtocraft.power.TileEntity.FemtopowerTile;

public class CommonProxyFemtocraft {
	public void registerRendering() {
		
	}
	
	public void registerTileEntities() {
		 //Tile Entities
		 GameRegistry.registerTileEntity(FemtopowerTile.class, "FemtopowerTile");
		 GameRegistry.registerTileEntity(FemtopowerProducer.class, "FemtopowerProducer");
		 GameRegistry.registerTileEntity(FemtopowerConsumer.class, "FemtopowerConsumer");
	}
	
	public void registerBlockRenderers() {
	}
}
