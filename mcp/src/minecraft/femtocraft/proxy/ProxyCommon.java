package femtocraft.proxy;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import femtocraft.power.tiles.*;
import femtocraft.render.RenderOrbitalEqualizer;
import femtocraft.transport.items.tiles.TileEntityVacuumTube;
import femtocraft.transport.liquids.tiles.TileEntitySuctionPipe;

public class ProxyCommon {
	public void registerRendering() {
		ClientRegistry.bindTileEntitySpecialRenderer(TileEntityPowerTest.class, new RenderOrbitalEqualizer());
	}

	public void registerTileEntities() {
		// Tile Entities
		GameRegistry.registerTileEntity(TileEntityPowerBase.class,
				"TileEntityPowerBase");
		GameRegistry.registerTileEntity(TileEntityPowerProducerTest.class,
				"TileEntityPowerProducerTest");
		GameRegistry.registerTileEntity(TileEntityPowerConsumerTest.class,
				"TileEntityPowerConsumerTest");
		GameRegistry.registerTileEntity(TileEntityPowerTest.class,
				"TileEntityPowerTest");
		GameRegistry.registerTileEntity(TileEntityBaseEntityMicroFurnace.class,
				"FemtocraftMicroFurnace");
		GameRegistry.registerTileEntity(
				TileEntityBaseEntityMicroDeconstructor.class,
				"microDeconstructor");
		GameRegistry.registerTileEntity(
				TileEntityBaseEntityMicroReconstructor.class,
				"microReconstructor");
		GameRegistry.registerTileEntity(TileEntityMicroCable.class,
				"blockMicroCable");
		GameRegistry.registerTileEntity(TileEntityNanoCable.class,
				"blockNanoCable");
		GameRegistry.registerTileEntity(TileEntityFemtoCable.class,
				"blockFemtoCable");
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
