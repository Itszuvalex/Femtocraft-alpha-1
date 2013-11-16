package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.TileEntity.MicroFurnaceTile;
import femtocraft.power.TileEntity.FemtopowerCableTile;
import femtocraft.power.TileEntity.FemtopowerConsumerTest;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
import femtocraft.power.TileEntity.FemtopowerProducerTest;

public class CommonProxyFemtocraft {
	public void registerRendering() {
		
	}
	
	public void registerTileEntities() {
		 //Tile Entities
//		 GameRegistry.registerTileEntity(FemtopowerTile.class, "FemtopowerTile");
		 GameRegistry.registerTileEntity(FemtopowerProducerTest.class, "FemtopowerProducerTest");
		 GameRegistry.registerTileEntity(FemtopowerConsumerTest.class, "FemtopowerConsumerTest");
		 GameRegistry.registerTileEntity(MicroFurnaceTile.class, "FemtocraftMicroFurnace");
		 GameRegistry.registerTileEntity(FemtopowerCableTile.class, "FemtopowerCable");
		 GameRegistry.registerTileEntity(FemtopowerMicroCubeTile.class, "FemtocraftMicroCube");
	}
	
	public void registerBlockRenderers() {
	}
}
