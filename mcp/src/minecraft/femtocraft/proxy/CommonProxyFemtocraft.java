package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.industry.tiles.TileEntityVacuumTube;
import femtocraft.power.tiles.*;

public class CommonProxyFemtocraft {
	public void registerRendering() {

	}

	public void registerTileEntities() {
		// Tile Entities
		GameRegistry.registerTileEntity(TileEntityPowerBase.class, "TileEntityPowerBase");
		GameRegistry.registerTileEntity(TileEntityPowerProducerTest.class,
				"TileEntityPowerProducerTest");
		GameRegistry.registerTileEntity(TileEntityPowerConsumerTest.class,
				"TileEntityPowerConsumerTest");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroFurnace.class,
				"FemtocraftMicroFurnace");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroDeconstructor.class,
				"FemtocraftMicroDeconstructor");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroReconstructor.class,
				"FemtocraftMicroReconstructor");
		GameRegistry.registerTileEntity(TileEntityBaseEntityCable.class,
				"BlockCable");
		GameRegistry.registerTileEntity(TileEntityPowerMicroCube.class,
				"FemtocraftMicroCube");
		GameRegistry.registerTileEntity(TileEntityVacuumTube.class, "BlockVacuumTube");
		GameRegistry.registerTileEntity(TileEntityPowerMicroChargingBase.class,
				"BlockBaseMicroCharging");
	}

	public void registerBlockRenderers() {
	}

	public void registerTickHandlers() {
	}
}
