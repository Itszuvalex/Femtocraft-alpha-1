package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.TileEntity.MicroDeconstructorTile;
import femtocraft.industry.TileEntity.MicroFurnaceTile;
import femtocraft.industry.TileEntity.MicroReconstructorTile;
import femtocraft.industry.TileEntity.VacuumTubeTile;
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
		GameRegistry.registerTileEntity(MicroFurnaceTile.class,
				"FemtocraftMicroFurnace");
		GameRegistry.registerTileEntity(MicroDeconstructorTile.class,
				"FemtocraftMicroDeconstructor");
		GameRegistry.registerTileEntity(MicroReconstructorTile.class,
				"FemtocraftMicroReconstructor");
		GameRegistry.registerTileEntity(FemtopowerCableTile.class,
				"FemtopowerCable");
		GameRegistry.registerTileEntity(FemtopowerMicroCubeTile.class,
				"FemtocraftMicroCube");
		GameRegistry.registerTileEntity(VacuumTubeTile.class, "VacuumTube");
		GameRegistry.registerTileEntity(MicroChargingBaseTile.class,
				"MicroChargingBase");
	}

	public void registerBlockRenderers() {
	}

	public void registerTickHandlers() {
	}
}
