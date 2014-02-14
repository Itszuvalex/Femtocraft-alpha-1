package femtocraft.proxy;

import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.power.tiles.TileEntityBaseEntityCable;
import femtocraft.power.tiles.TileEntityPowerBase;
import femtocraft.power.tiles.TileEntityPowerConsumerTest;
import femtocraft.power.tiles.TileEntityPowerMicroChargingBase;
import femtocraft.power.tiles.TileEntityPowerMicroCube;
import femtocraft.power.tiles.TileEntityPowerProducerTest;
import femtocraft.transport.items.tiles.TileEntityVacuumTube;
import femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;

public class ProxyCommon {
	public void registerRendering() {

	}

	public void registerTileEntities() {
		// Tile Entities
		GameRegistry.registerTileEntity(TileEntityPowerBase.class,
				"TileEntityPowerBase");
		GameRegistry.registerTileEntity(TileEntityPowerProducerTest.class,
				"TileEntityPowerProducerTest");
		GameRegistry.registerTileEntity(TileEntityPowerConsumerTest.class,
				"TileEntityPowerConsumerTest");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroFurnace.class,
				"FemtocraftMicroFurnace");
		GameRegistry.registerTileEntity(
				TileEntityBaseEntityMicroDeconstructor.class,
				"microDeconstructor");
		GameRegistry.registerTileEntity(
				TileEntityBaseEntityMicroReconstructor.class,
				"microReconstructor");
		GameRegistry.registerTileEntity(TileEntityBaseEntityCable.class,
				"blockCable");
		GameRegistry.registerTileEntity(TileEntityPowerMicroCube.class,
				"FemtocraftMicroCube");
		GameRegistry.registerTileEntity(TileEntityVacuumTube.class,
				"BlockVacuumTube");
		GameRegistry.registerTileEntity(TileEntityPowerMicroChargingBase.class,
				"BlockBaseMicroCharging");
		GameRegistry.registerTileEntity(TileEntitySuctionPipe.class,
				"TileEntitySuctionPipe");
	}

	public void registerBlockRenderers() {
	}

	public void registerTickHandlers() {
	}
}
