package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.TileEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityMicroReconstructor;
import femtocraft.industry.tiles.TileEntityVacuumTube;
import femtocraft.power.TileEntity.*;

public class CommonProxyFemtocraft {
	public void registerRendering() {

	}

	public void registerTileEntities() {
		// Tile Entities
		GameRegistry.registerTileEntity(FemtopowerTile.class, "FemtopowerTile");
		GameRegistry.registerTileEntity(FemtopowerProducerTest.class,
				"FemtopowerProducerTest");
		GameRegistry.registerTileEntity(FemtopowerConsumerTest.class,
				"FemtopowerConsumerTest");
		GameRegistry.registerTileEntity(TileEntityMicroFurnace.class,
				"FemtocraftMicroFurnace");
		GameRegistry.registerTileEntity(TileEntityMicroDeconstructor.class,
				"FemtocraftMicroDeconstructor");
		GameRegistry.registerTileEntity(TileEntityMicroReconstructor.class,
				"FemtocraftMicroReconstructor");
		GameRegistry.registerTileEntity(FemtopowerCableTile.class,
				"FemtopowerCable");
		GameRegistry.registerTileEntity(FemtopowerMicroCubeTile.class,
				"FemtocraftMicroCube");
		GameRegistry.registerTileEntity(TileEntityVacuumTube.class, "BlockVacuumTube");
		GameRegistry.registerTileEntity(MicroChargingBaseTile.class,
				"MicroChargingBase");
	}

	public void registerBlockRenderers() {
	}

	public void registerTickHandlers() {
	}
}
