package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.industry.tiles.TileEntityVacuumTube;
import femtocraft.power.tiles.*;

public class ProxyCommon {
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
				"microDeconstructor");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroReconstructor.class,
				"microReconstructor");
		GameRegistry.registerTileEntity(TileEntityBaseEntityCable.class,
				"blockCable");
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
