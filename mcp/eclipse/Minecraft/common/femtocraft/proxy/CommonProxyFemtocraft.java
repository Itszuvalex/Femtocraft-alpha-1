package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.power.TileEntity.FemtopowerCableTile;
import femtocraft.power.TileEntity.FemtopowerConsumer;
import femtocraft.power.TileEntity.FemtopowerProducer;

public class CommonProxyFemtocraft {
	public void registerRendering() {
		
	}
	
	public void registerTileEntities() {
		 //Tile Entities
//		 GameRegistry.registerTileEntity(FemtopowerTile.class, "FemtopowerTile");
		 GameRegistry.registerTileEntity(FemtopowerProducer.class, "FemtopowerProducer");
		 GameRegistry.registerTileEntity(FemtopowerConsumer.class, "FemtopowerConsumer");
		 GameRegistry.registerTileEntity(FemtopowerCableTile.class, "FemtopowerCable");
	}
	
	public void registerBlockRenderers() {
	}
}
