package femtocraft;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import femtocraft.blocks.BlockFemtoStone;
import femtocraft.blocks.BlockMicroStone;
import femtocraft.blocks.BlockNanoStone;
import femtocraft.blocks.BlockUnidentifiedAlloy;
import femtocraft.consumables.farming.items.ItemSeedTomato;
import femtocraft.consumables.items.ItemTomato;
import femtocraft.consumables.processing.blocks.BlockCuttingBoard;
import femtocraft.core.items.ItemBoard;
import femtocraft.core.items.ItemConductivePowder;
import femtocraft.core.items.ItemDeconstructedGold;
import femtocraft.core.items.ItemDeconstructedIron;
import femtocraft.core.items.ItemDeconstructedPlatinum;
import femtocraft.core.items.ItemDeconstructedThorium;
import femtocraft.core.items.ItemDeconstructedTitanium;
import femtocraft.core.items.ItemDopedBoard;
import femtocraft.core.items.ItemFemtoInterfaceDevice;
import femtocraft.core.items.ItemIngotFarenite;
import femtocraft.core.items.ItemIngotMalenite;
import femtocraft.core.items.ItemIngotPlatinum;
import femtocraft.core.items.ItemIngotTemperedTitanium;
import femtocraft.core.items.ItemIngotThorium;
import femtocraft.core.items.ItemIngotTitanium;
import femtocraft.core.items.ItemMicroCircuitBoard;
import femtocraft.core.items.ItemMicroInterfaceDevice;
import femtocraft.core.items.ItemMicroPlating;
import femtocraft.core.items.ItemNanoInterfaceDevice;
import femtocraft.core.items.ItemPrimedBoard;
import femtocraft.core.items.ItemSpool;
import femtocraft.core.items.decomposition.ItemConductiveAlloy;
import femtocraft.core.items.decomposition.ItemCrystallite;
import femtocraft.core.items.decomposition.ItemCubit;
import femtocraft.core.items.decomposition.ItemElectrite;
import femtocraft.core.items.decomposition.ItemFaunite;
import femtocraft.core.items.decomposition.ItemFibrousStrand;
import femtocraft.core.items.decomposition.ItemFlorite;
import femtocraft.core.items.decomposition.ItemFungalSpores;
import femtocraft.core.items.decomposition.ItemHardenedBulb;
import femtocraft.core.items.decomposition.ItemIonicChunk;
import femtocraft.core.items.decomposition.ItemMetalComposite;
import femtocraft.core.items.decomposition.ItemMetallite;
import femtocraft.core.items.decomposition.ItemMicroCrystal;
import femtocraft.core.items.decomposition.ItemMineralLattice;
import femtocraft.core.items.decomposition.ItemMineralite;
import femtocraft.core.items.decomposition.ItemMorphicChannel;
import femtocraft.core.items.decomposition.ItemNerveCluster;
import femtocraft.core.items.decomposition.ItemOrganometallicPlate;
import femtocraft.core.items.decomposition.ItemPlaneoid;
import femtocraft.core.items.decomposition.ItemProteinChain;
import femtocraft.core.items.decomposition.ItemRectangulon;
import femtocraft.core.items.decomposition.ItemReplicatingMaterial;
import femtocraft.core.items.decomposition.ItemSpinyFilament;
import femtocraft.core.items.decomposition.ItemSynthesizedFiber;
import femtocraft.core.liquids.BlockFluidMass;
import femtocraft.core.liquids.FluidMass;
import femtocraft.core.ore.BlockOreFarenite;
import femtocraft.core.ore.BlockOreMalenite;
import femtocraft.core.ore.BlockOrePlatinum;
import femtocraft.core.ore.BlockOreThorium;
import femtocraft.core.ore.BlockOreTitanium;
import femtocraft.industry.blocks.BlockMicroDeconstructor;
import femtocraft.industry.blocks.BlockMicroFurnace;
import femtocraft.industry.blocks.BlockMicroReconstructor;
import femtocraft.industry.items.ItemPaperSchematic;
import femtocraft.managers.ManagerRecipe;
import femtocraft.managers.research.ManagerResearch;
import femtocraft.player.PropertiesNanite;
import femtocraft.power.blocks.BlockBaseMicroCharging;
import femtocraft.power.blocks.BlockCoilMicroCharging;
import femtocraft.power.blocks.BlockConsumer;
import femtocraft.power.blocks.BlockFemtoCable;
import femtocraft.power.blocks.BlockGenerator;
import femtocraft.power.blocks.BlockMicroCable;
import femtocraft.power.blocks.BlockMicroCube;
import femtocraft.power.blocks.BlockNanoCable;
import femtocraft.power.blocks.BlockNanoCubeFrame;
import femtocraft.power.blocks.BlockNanoCubePort;
import femtocraft.power.blocks.BlockNullEqualizer;
import femtocraft.power.blocks.BlockOrbitalEqualizer;
import femtocraft.power.items.ItemBlockMicroCube;
import femtocraft.power.items.ItemSpoolGold;
import femtocraft.proxy.ProxyClient;
import femtocraft.proxy.ProxyCommon;
import femtocraft.research.blocks.BlockResearchComputer;
import femtocraft.research.items.ItemFemtoTechnology;
import femtocraft.research.items.ItemMicroTechnology;
import femtocraft.research.items.ItemNanoTechnology;
import femtocraft.transport.items.blocks.BlockVacuumTube;
import femtocraft.transport.items.tiles.TileEntityVacuumTube;
import femtocraft.transport.liquids.blocks.BlockSuctionPipe;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)
@NetworkMod(channels = { Femtocraft.ID, PropertiesNanite.PACKET_CHANNEL,
		ManagerResearch.RESEARCH_CHANNEL, TileEntityVacuumTube.packetChannel }, packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Femtocraft {
	public static final String ID = "Femtocraft";
	public static final String VERSION = "0.1.0";

	@Instance(ID)
	public static Femtocraft instance;

	@SidedProxy(clientSide = "femtocraft.proxy.ProxyClient", serverSide = "femtocraft.proxy.ProxyCommon")
	public static ProxyCommon proxy;

	public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab(
			"Femtocraft");

	public static Logger logger;

	public static ManagerRecipe recipeManager;
	public static ManagerResearch researchManager;

	// blocks
	public static Block oreTitanium;
	public static Block orePlatinum;
	public static Block oreThorium;
	public static Block oreFarenite;
	public static Block oreMalenite;
	public static Block microStone;
	public static Block nanoStone;
	public static Block femtoStone;
	public static Block unidentifiedAlloy;
	public static Block researchComputer;
	public static Block generatorTest;
	public static Block consumerTest;
	public static Block microFurnaceUnlit;
	public static Block microFurnaceLit;
	public static Block microDeconstructor;
	public static Block microReconstructor;
	public static Block microCube;
	public static Block nanoCubeFrame;
	public static Block nanoCubePort;
	public static Block vacuumTube;
	public static Block suctionPipe;
	public static Block microChargingBase;
	public static Block microChargingCoil;
	public static Block orbitalEqualizer;
	public static Block nullEqualizer;

	// cables
	public static Block blockMicroCable;
	public static Block blockNanoCable;
	public static Block blockFemtoCable;

	// liquids
	public static Fluid mass;
	public static BlockFluidMass mass_block;

	// items
	public static Item ingotTitanium;
	public static Item ingotPlatinum;
	public static Item ingotThorium;
	public static Item ingotFarenite;
	public static Item ingotMalenite;
	public static Item ingotTemperedTitanium;

	public static Item deconstructedIron;
	public static Item deconstructedGold;
	public static Item deconstructedTitanium;
	public static Item deconstructedThorium;
	public static Item deconstructedPlatinum;

	public static Item conductivePowder;
	public static Item board;
	public static Item primedBoard;
	public static Item dopedBoard;
	public static Item microCircuitBoard;

	public static Item spool;
	public static Item spoolGold;

	public static Item itemMicroTechnology;
	public static Item itemNanoTechnology;
	public static Item itemFemtoTechnology;

	public static Item paperSchematic;

	public static Item microInterfaceDevice;
	public static Item nanoInterfaceDevice;
	public static Item femtoInterfaceDevice;

	// Decomp items
	// Femto
	public static Item itemCubit;
	public static Item itemRectangulon;
	public static Item itemPlaneoid;
	// Nano
	public static Item itemCrystallite;
	public static Item itemMineralite;
	public static Item itemMetallite;
	public static Item itemFaunite;
	public static Item itemElectrite;
	public static Item itemFlorite;
	// Micro
	public static Item itemMicroCrystal;
	public static Item itemProteinChain;
	public static Item itemNerveCluster;
	public static Item itemConductiveAlloy;
	public static Item itemMetalComposite;
	public static Item itemFibrousStrand;
	public static Item itemMineralLattice;
	public static Item itemFungalSpores;
	public static Item itemIonicChunk;
	public static Item itemReplicatingMaterial;
	public static Item itemSpinyFilament;
	public static Item itemHardenedBulb;
	public static Item itemMorphicChannel;
	public static Item itemSynthesizedFiber;
	public static Item itemOrganometallicPlate;

	public static Item itemMicroPlating;

	// Produce
	public static Item tomatoSeed;
	public static Item tomato;

	// Cooking
	public static Block cuttingBoard;

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		logger = Logger.getLogger(ID);
		logger.setParent(FMLLog.getLogger());

		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		FemtocraftConfigs.load(config);

		Femtocraft.proxy.registerTileEntities();
		Femtocraft.proxy.registerRendering();
		Femtocraft.proxy.registerBlockRenderers();
		Femtocraft.proxy.registerTickHandlers();

		NetworkRegistry.instance().registerGuiHandler(this,
				new FemtocraftGuiHandler());
		NetworkRegistry.instance().registerConnectionHandler(
				new FemtocraftConnectionHandler());
		MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRendering();

		if (FemtocraftConfigs.worldGen) {
			GameRegistry.registerWorldGenerator(new WorldGenerator());
		}

		// Change the creative tab name
		LanguageRegistry.instance().addStringLocalization(
				"itemGroup.Femtocraft", "en_US", "Femtocraft");

		// item = new Item(Configs.itemId);

		// blocks

		oreTitanium = new BlockOreTitanium(FemtocraftConfigs.oreTitaniumID);
		MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		if (FemtocraftConfigs.registerTitaniumOreInOreDictionary)
			OreDictionary
					.registerOre("oreTitanium", new ItemStack(oreTitanium));

		orePlatinum = new BlockOrePlatinum(FemtocraftConfigs.orePlatinumID);
		MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		if (FemtocraftConfigs.registerPlatinumOreInOreDictionary)
			OreDictionary
					.registerOre("orePlatinum", new ItemStack(orePlatinum));

		oreThorium = new BlockOreThorium(FemtocraftConfigs.oreThoriumID);
		MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		GameRegistry.registerBlock(oreThorium, "oreThorium");
		LanguageRegistry.addName(oreThorium, "Thorium Ore");
		if (FemtocraftConfigs.registerThoriumOreInOreDictionary)
			OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));

		oreFarenite = new BlockOreFarenite(FemtocraftConfigs.oreFareniteID);
		MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		if (FemtocraftConfigs.registerFareniteOreInOreDictionary)
			OreDictionary
					.registerOre("oreFarenite", new ItemStack(oreFarenite));

		oreMalenite = new BlockOreMalenite(FemtocraftConfigs.oreMaleniteID);
		MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 3);
		GameRegistry.registerBlock(oreMalenite, "oreMalenite");
		LanguageRegistry.addName(oreMalenite, "Malenite Ore");
		if (FemtocraftConfigs.registerMaleniteOreInOreDictionary)
			OreDictionary
					.registerOre("oreMalenite", new ItemStack(oreMalenite));

		nanoStone = new BlockNanoStone(FemtocraftConfigs.nanoStoneID);
		GameRegistry.registerBlock(nanoStone, "nanoStone");
		LanguageRegistry.addName(nanoStone, "Nanostone");

		microStone = new BlockMicroStone(FemtocraftConfigs.microStoneID);
		GameRegistry.registerBlock(microStone, "microStone");
		LanguageRegistry.addName(microStone, "Microstone");

		femtoStone = new BlockFemtoStone(FemtocraftConfigs.femtoStoneID);
		GameRegistry.registerBlock(femtoStone, "femtoStone");
		LanguageRegistry.addName(femtoStone, "Femtostone");

		unidentifiedAlloy = new BlockUnidentifiedAlloy(
				FemtocraftConfigs.unidentifiedAlloyID);
		GameRegistry.registerBlock(unidentifiedAlloy, "unidentifiedAlloy");
		LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");

		researchComputer = new BlockResearchComputer(
				FemtocraftConfigs.FemtocraftResearchComputerID);
		GameRegistry.registerBlock(researchComputer, "researchComputer");
		LanguageRegistry.addName(researchComputer, "Research Computer");

		blockMicroCable = new BlockMicroCable(FemtocraftConfigs.microCableID,
				Material.rock);
		GameRegistry.registerBlock(blockMicroCable, "microCable");
		LanguageRegistry.addName(blockMicroCable, "Micro-Cable");

		blockNanoCable = new BlockNanoCable(FemtocraftConfigs.nanoCableID,
				Material.rock);
		GameRegistry.registerBlock(blockNanoCable, "nanoCable");
		LanguageRegistry.addName(blockNanoCable, "Nano-Cable");

		blockFemtoCable = new BlockFemtoCable(FemtocraftConfigs.femtoCableID,
				Material.rock);
		GameRegistry.registerBlock(blockFemtoCable, "femtoCable");
		LanguageRegistry.addName(blockFemtoCable, "Femto-Cable");

		generatorTest = new BlockGenerator(
				FemtocraftConfigs.FemtopowerGeneratorTestID, Material.rock)
				.setUnlocalizedName("BlockGenerator").setHardness(3.5f)
				.setStepSound(Block.soundStoneFootstep);
		GameRegistry.registerBlock(generatorTest, "BlockGenerator");
		LanguageRegistry.addName(generatorTest, "Generator");

		consumerTest = new BlockConsumer(
				FemtocraftConfigs.FemtopowerConsumerTestBlockID, Material.rock)
				.setUnlocalizedName("BlockConsumer").setHardness(3.5f)
				.setStepSound(Block.soundStoneFootstep);
		GameRegistry.registerBlock(consumerTest, "BlockConsumer");
		LanguageRegistry.addName(consumerTest, "Consumer");

		microFurnaceUnlit = new BlockMicroFurnace(
				FemtocraftConfigs.FemtocraftMicroFurnaceUnlitID, false);
		GameRegistry.registerBlock(microFurnaceUnlit, "BlockMicroFurnace");
		LanguageRegistry.addName(microFurnaceUnlit, "Micro-Furnace");

		microFurnaceLit = new BlockMicroFurnace(
				FemtocraftConfigs.FemtocraftMicroFurnaceLitID, true);

		microDeconstructor = new BlockMicroDeconstructor(
				FemtocraftConfigs.FemtocraftMicroDeconstructorID);
		GameRegistry.registerBlock(microDeconstructor,
				"BlockMicroDeconstructor");
		LanguageRegistry.addName(microDeconstructor, "Microtech Deconstructor");

		microReconstructor = new BlockMicroReconstructor(
				FemtocraftConfigs.FemtocraftMicroReconstructorID);
		GameRegistry.registerBlock(microReconstructor,
				"BlockMicroReconstructor");
		LanguageRegistry.addName(microReconstructor, "Microtech Reconstructor");

		microCube = new BlockMicroCube(FemtocraftConfigs.FemtopowerMicroCubeID);
		GameRegistry.registerBlock(microCube, ItemBlockMicroCube.class,
				"BlockMicroCube");
		LanguageRegistry.addName(microCube, "Micro-Cube");

		nanoCubeFrame = new BlockNanoCubeFrame(
				FemtocraftConfigs.FemtopowerNanoCubeFrameID);
		GameRegistry.registerBlock(nanoCubeFrame, "BlockNanoCubeFrame");
		LanguageRegistry.addName(nanoCubeFrame, "Nano-Cube Frame");

		nanoCubePort = new BlockNanoCubePort(
				FemtocraftConfigs.FemtopowerNanoCubePortID);
		GameRegistry.registerBlock(nanoCubePort, "BlockNanoCubePort");
		LanguageRegistry.addName(nanoCubePort, "Nano-Cube Port");

		vacuumTube = new BlockVacuumTube(
				FemtocraftConfigs.FemtocraftVacuumTubeID);
		GameRegistry.registerBlock(vacuumTube, "BlockVacuumTube");
		LanguageRegistry.addName(vacuumTube, "Vacuum Tube");

		suctionPipe = new BlockSuctionPipe(
				FemtocraftConfigs.FemtocraftSuctionPipeID);
		GameRegistry.registerBlock(suctionPipe, "BlockSuctionPipe");
		LanguageRegistry.addName(suctionPipe, "Suction Pipe");

		microChargingBase = new BlockBaseMicroCharging(
				FemtocraftConfigs.FemtopowerMicroChargingBaseID);
		GameRegistry.registerBlock(microChargingBase, "BlockBaseMicroCharging");
		LanguageRegistry.addName(microChargingBase,
				"Electrostatic Charging Base");

		microChargingCoil = new BlockCoilMicroCharging(
				FemtocraftConfigs.FemtopowerMicroChargingCoilID);
		GameRegistry.registerBlock(microChargingCoil, "BlockCoilMicroCharging");
		LanguageRegistry.addName(microChargingCoil,
				"Electrostatic Charging Coil");

		orbitalEqualizer = new BlockOrbitalEqualizer(
				FemtocraftConfigs.orbitalEqualizerID);
		GameRegistry.registerBlock(orbitalEqualizer, "BlockOrbitalEqualizer");
		LanguageRegistry.addName(orbitalEqualizer, "Orbital Equalizer");

		nullEqualizer = new BlockNullEqualizer(
				FemtocraftConfigs.nullEqualizerID);
		GameRegistry.registerBlock(nullEqualizer, "BlockNullEqualizer");
		LanguageRegistry.addName(nullEqualizer, "Null-Energy Equalizer");

		// Liquids
		mass = new FluidMass();
		FluidRegistry.registerFluid(mass);

		mass_block = new BlockFluidMass(FemtocraftConfigs.FemtocraftMassBlock);
		GameRegistry.registerBlock(mass_block, "Mass");
		LanguageRegistry.addName(mass_block, "Mass");

		// items
		ingotTitanium = new ItemIngotTitanium(FemtocraftConfigs.ingotTitaniumID)
				.setUnlocalizedName("ingotTitanium");
		LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary)
			OreDictionary.registerOre("ingotTitanium", new ItemStack(
					ingotTitanium));

		ingotPlatinum = new ItemIngotPlatinum(FemtocraftConfigs.ingotPlatinumID)
				.setUnlocalizedName("ingotPlatinum");
		LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary)
			OreDictionary.registerOre("ingotPlatinum", new ItemStack(
					ingotPlatinum));

		ingotThorium = new ItemIngotThorium(FemtocraftConfigs.ingotThoriumID)
				.setUnlocalizedName("ingotThorium");
		LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		if (FemtocraftConfigs.registerThoriumIngotInOreDictionary)
			OreDictionary.registerOre("ingotThorium", new ItemStack(
					ingotThorium));

		ingotFarenite = new ItemIngotFarenite(FemtocraftConfigs.ingotFareniteID)
				.setUnlocalizedName("ingotFarenite");
		LanguageRegistry.addName(ingotFarenite, "Farenite");
		OreDictionary
				.registerOre("ingotFarenite", new ItemStack(ingotFarenite));

		ingotMalenite = new ItemIngotMalenite(FemtocraftConfigs.ingotMaleniteID)
				.setUnlocalizedName("ingotMalenite");
		LanguageRegistry.addName(ingotMalenite, "Malenite");
		OreDictionary
				.registerOre("ingotMalenite", new ItemStack(ingotMalenite));

		ingotTemperedTitanium = new ItemIngotTemperedTitanium(
				FemtocraftConfigs.ingotTemperedTitaniumID);
		LanguageRegistry.addName(ingotTemperedTitanium,
				"Tempered Titanium Ingot");
		OreDictionary.registerOre("ingotTemperedTitanium", new ItemStack(
				ingotTemperedTitanium));

		deconstructedIron = new ItemDeconstructedIron(
				FemtocraftConfigs.deconstructedIronID)
				.setUnlocalizedName("deconstructedIron");
		LanguageRegistry.addName(deconstructedIron, "Deconstructed Iron");
		OreDictionary.registerOre("dustIron", new ItemStack(deconstructedIron));

		deconstructedGold = new ItemDeconstructedGold(
				FemtocraftConfigs.deconstructedGoldID)
				.setUnlocalizedName("deconstructedGold");
		LanguageRegistry.addName(deconstructedGold, "Deconstructed Gold");
		OreDictionary.registerOre("dustGold", new ItemStack(deconstructedGold));

		deconstructedTitanium = new ItemDeconstructedTitanium(
				FemtocraftConfigs.deconstructedTitaniumID)
				.setUnlocalizedName("deconstructedTitanium");
		LanguageRegistry.addName(deconstructedTitanium,
				"Deconstructed Titanium");
		if (FemtocraftConfigs.registerTitaniumDustInOreDictionary)
			OreDictionary.registerOre("dustTitanium", new ItemStack(
					deconstructedTitanium));

		deconstructedThorium = new ItemDeconstructedThorium(
				FemtocraftConfigs.deconstructedThoriumID)
				.setUnlocalizedName("deconstructedThorium");
		LanguageRegistry.addName(deconstructedThorium, "Deconstructed Thorium");
		if (FemtocraftConfigs.registerThoriumDustInOreDictionary)
			OreDictionary.registerOre("dustThorium", new ItemStack(
					deconstructedThorium));

		deconstructedPlatinum = new ItemDeconstructedPlatinum(
				FemtocraftConfigs.deconstructedPlatinumID)
				.setUnlocalizedName("deconstructedPlatinum");
		LanguageRegistry.addName(deconstructedPlatinum,
				"Deconstructed Platinum");
		if (FemtocraftConfigs.registerPlatinumDustInOreDictionary)
			OreDictionary.registerOre("dustPlatinum", new ItemStack(
					deconstructedPlatinum));

		//

		conductivePowder = new ItemConductivePowder(
				FemtocraftConfigs.conductivePowderID)
				.setUnlocalizedName("conductivePowder");
		LanguageRegistry.addName(conductivePowder, "Conductive Powder");

		board = new ItemBoard(FemtocraftConfigs.boardID)
				.setUnlocalizedName("board");
		LanguageRegistry.addName(board, "Board");

		primedBoard = new ItemPrimedBoard(FemtocraftConfigs.primedBoardID)
				.setUnlocalizedName("primedBoard");
		LanguageRegistry.addName(primedBoard, "Primed Board");

		dopedBoard = new ItemDopedBoard(FemtocraftConfigs.dopedBoardID)
				.setUnlocalizedName("dopedBoard");
		LanguageRegistry.addName(dopedBoard, "Doped Board");

		microCircuitBoard = new ItemMicroCircuitBoard(
				FemtocraftConfigs.microCircuitID)
				.setUnlocalizedName("microCircuitBoard");
		LanguageRegistry.addName(microCircuitBoard, "Micro Circuit Board");

		spool = new ItemSpool(FemtocraftConfigs.spoolID)
				.setUnlocalizedName("spool");
		LanguageRegistry.addName(spool, "Spool");

		spoolGold = new ItemSpoolGold(FemtocraftConfigs.spoolGoldID)
				.setUnlocalizedName("spoolGold");
		LanguageRegistry.addName(spoolGold, "Gold Wire Spool");

		itemMicroTechnology = new ItemMicroTechnology(
				FemtocraftConfigs.itemMicroTechnologyID);
		LanguageRegistry.addName(itemMicroTechnology, "Micro Technology");

		itemNanoTechnology = new ItemNanoTechnology(
				FemtocraftConfigs.itemNanoTechnologyID);
		LanguageRegistry.addName(itemNanoTechnology, "Nano Technology");

		itemFemtoTechnology = new ItemFemtoTechnology(
				FemtocraftConfigs.itemFemtoTechnologyID);
		LanguageRegistry.addName(itemFemtoTechnology, "Femto Technology");

		// Schematics
		paperSchematic = new ItemPaperSchematic(
				FemtocraftConfigs.paperSchematicID);
		LanguageRegistry.addName(paperSchematic, "Paper Schematic");

		microInterfaceDevice = new ItemMicroInterfaceDevice(
				FemtocraftConfigs.microInterfaceDeviceID)
				.setUnlocalizedName("microInterfaceDevice");
		LanguageRegistry.addName(microInterfaceDevice, "MicroInterface Device");

		nanoInterfaceDevice = new ItemNanoInterfaceDevice(
				FemtocraftConfigs.nanoInterfaceDeviceID)
				.setUnlocalizedName("nanoInterfaceDevice");
		LanguageRegistry.addName(nanoInterfaceDevice, "NanoInterface Device");

		femtoInterfaceDevice = new ItemFemtoInterfaceDevice(
				FemtocraftConfigs.femtoInterfaceDeviceID)
				.setUnlocalizedName("femtoInterfaceDevice");
		LanguageRegistry.addName(femtoInterfaceDevice, "FemtoInterface Device");

		// Decomp
		// Femto
		itemCubit = new ItemCubit(FemtocraftConfigs.CubitID)
				.setUnlocalizedName("itemCubit");
		LanguageRegistry.addName(itemCubit, "Cubit");
		GameRegistry.registerItem(itemCubit, "Cubit");

		itemRectangulon = new ItemRectangulon(FemtocraftConfigs.RectangulonID)
				.setUnlocalizedName("itemRectangulon");
		LanguageRegistry.addName(itemRectangulon, "Rectangulon");
		GameRegistry.registerItem(itemRectangulon, "Rectangulon");

		itemPlaneoid = new ItemPlaneoid(FemtocraftConfigs.PlaneoidID)
				.setUnlocalizedName("itemPlaneoid");
		LanguageRegistry.addName(itemPlaneoid, "Planeoid");
		GameRegistry.registerItem(itemPlaneoid, "Planeoid");

		// Nano
		itemCrystallite = new ItemCrystallite(FemtocraftConfigs.CrystalliteID)
				.setUnlocalizedName("itemCrystallite");
		LanguageRegistry.addName(itemCrystallite, "Crystallite");
		GameRegistry.registerItem(itemCrystallite, "Crystallite");

		itemMineralite = new ItemMineralite(FemtocraftConfigs.MineraliteID)
				.setUnlocalizedName("itemMineralite");
		LanguageRegistry.addName(itemMineralite, "Mineralite");
		GameRegistry.registerItem(itemMineralite, "Mineralite");

		itemMetallite = new ItemMetallite(FemtocraftConfigs.MetalliteID)
				.setUnlocalizedName("itemMetallite");
		LanguageRegistry.addName(itemMetallite, "Metallite");
		GameRegistry.registerItem(itemMetallite, "Metallite");

		itemFaunite = new ItemFaunite(FemtocraftConfigs.FauniteID)
				.setUnlocalizedName("itemFaunite");
		LanguageRegistry.addName(itemFaunite, "Faunite");
		GameRegistry.registerItem(itemFaunite, "Faunite");

		itemElectrite = new ItemElectrite(FemtocraftConfigs.ElectriteID)
				.setUnlocalizedName("itemElectrite");
		LanguageRegistry.addName(itemElectrite, "Electrite");
		GameRegistry.registerItem(itemElectrite, "Electrite");

		itemFlorite = new ItemFlorite(FemtocraftConfigs.FloriteID)
				.setUnlocalizedName("itemFlorite");
		LanguageRegistry.addName(itemFlorite, "Florite");
		GameRegistry.registerItem(itemFlorite, "Florite");

		// Micro
		itemMicroCrystal = new ItemMicroCrystal(
				FemtocraftConfigs.MicroCrystalID)
				.setUnlocalizedName("itemMicroCrystal");
		LanguageRegistry.addName(itemMicroCrystal, "Micro Crystal");
		GameRegistry.registerItem(itemMicroCrystal, "Micro Crystal");

		itemProteinChain = new ItemProteinChain(
				FemtocraftConfigs.ProteinChainID)
				.setUnlocalizedName("itemProteinChain");
		LanguageRegistry.addName(itemProteinChain, "Protein Chain");
		GameRegistry.registerItem(itemProteinChain, "Protein Chain");

		itemNerveCluster = new ItemNerveCluster(
				FemtocraftConfigs.NerveClusterID)
				.setUnlocalizedName("itemNerveCluster");
		LanguageRegistry.addName(itemNerveCluster, "Nerve Cluster");
		GameRegistry.registerItem(itemNerveCluster, "Nerve Cluster");

		itemConductiveAlloy = new ItemConductiveAlloy(
				FemtocraftConfigs.ConductiveAlloyID)
				.setUnlocalizedName("itemConductiveAlloy");
		LanguageRegistry.addName(itemConductiveAlloy, "Conductive Alloy");
		GameRegistry.registerItem(itemConductiveAlloy, "Conductive Alloy");

		itemMetalComposite = new ItemMetalComposite(
				FemtocraftConfigs.MetalCompositeID)
				.setUnlocalizedName("itemMetalComposite");
		LanguageRegistry.addName(itemMetalComposite, "Metal Composite");
		GameRegistry.registerItem(itemMetalComposite, "Metal Composite");

		itemFibrousStrand = new ItemFibrousStrand(
				FemtocraftConfigs.FibrousStrandID)
				.setUnlocalizedName("itemFibrousStrand");
		LanguageRegistry.addName(itemFibrousStrand, "Fibrous Strand");
		GameRegistry.registerItem(itemFibrousStrand, "Fibrous Strand");

		itemMineralLattice = new ItemMineralLattice(
				FemtocraftConfigs.MineralLatticeID)
				.setUnlocalizedName("itemMineralLattice");
		LanguageRegistry.addName(itemMineralLattice, "Mineral Lattice");
		GameRegistry.registerItem(itemMineralLattice, "Mineral Lattice");

		itemFungalSpores = new ItemFungalSpores(
				FemtocraftConfigs.FungalSporesID)
				.setUnlocalizedName("itemFungalSpores");
		LanguageRegistry.addName(itemFungalSpores, "Fungal Spores");
		GameRegistry.registerItem(itemFungalSpores, "Fungal Spores");

		itemIonicChunk = new ItemIonicChunk(FemtocraftConfigs.IonicChunkID)
				.setUnlocalizedName("itemIonicChunk");
		LanguageRegistry.addName(itemIonicChunk, "Ionic Chunk");
		GameRegistry.registerItem(itemIonicChunk, "Ionic Chunk");

		itemReplicatingMaterial = new ItemReplicatingMaterial(
				FemtocraftConfigs.ReplicatingMaterialID)
				.setUnlocalizedName("itemReplicatingMaterial");
		LanguageRegistry.addName(itemReplicatingMaterial,
				"Replicating Material");
		GameRegistry.registerItem(itemReplicatingMaterial,
				"Replicating Material");

		itemSpinyFilament = new ItemSpinyFilament(
				FemtocraftConfigs.SpinyFilamentID)
				.setUnlocalizedName("itemSpinyFilament");
		LanguageRegistry.addName(itemSpinyFilament, "Spiny Filament");
		GameRegistry.registerItem(itemSpinyFilament, "Spiny Filament");

		itemHardenedBulb = new ItemHardenedBulb(
				FemtocraftConfigs.HardenedBulbID)
				.setUnlocalizedName("itemHardenedBulb");
		LanguageRegistry.addName(itemHardenedBulb, "Hardened Bulb");
		GameRegistry.registerItem(itemHardenedBulb, "Hardened Bulb");

		itemMorphicChannel = new ItemMorphicChannel(
				FemtocraftConfigs.MorphicChannelID)
				.setUnlocalizedName("itemMorphicChannel");
		LanguageRegistry.addName(itemMorphicChannel, "Morphic Channel");
		GameRegistry.registerItem(itemMorphicChannel, "Morphic Channel");

		itemSynthesizedFiber = new ItemSynthesizedFiber(
				FemtocraftConfigs.SynthesizedFiberID)
				.setUnlocalizedName("itemSynthesizedFiber");
		LanguageRegistry.addName(itemSynthesizedFiber, "Synthesized Fiber");
		GameRegistry.registerItem(itemSynthesizedFiber, "Synthesized Fiber");

		itemOrganometallicPlate = new ItemOrganometallicPlate(
				FemtocraftConfigs.OrganometallicPlateID)
				.setUnlocalizedName("itemOrganometallicPlate");
		LanguageRegistry.addName(itemOrganometallicPlate,
				"Organometallic Plate");
		GameRegistry.registerItem(itemOrganometallicPlate,
				"Organometallic Plate");

		itemMicroPlating = new ItemMicroPlating(
				FemtocraftConfigs.microPlatingID);
		LanguageRegistry.addName(itemMicroPlating, "Micro Plating");
		GameRegistry.registerItem(itemMicroPlating, "Micro Plating");

		// Produce
		tomatoSeed = new ItemSeedTomato(FemtocraftConfigs.tomatoSeedID)
				.setUnlocalizedName("tomatoSeed");
		LanguageRegistry.addName(tomatoSeed, "Tomato Seeds");
		GameRegistry.registerItem(tomatoSeed, "Tomato Seeds");

		tomato = new ItemTomato(FemtocraftConfigs.tomatoID)
				.setUnlocalizedName("tomato");
		LanguageRegistry.addName(tomato, "Tomato");
		GameRegistry.registerItem(tomato, "Tomato");

		// Cooking
		cuttingBoard = new BlockCuttingBoard(FemtocraftConfigs.cuttingBoardID)
				.setUnlocalizedName("cuttingBoard");
		LanguageRegistry.addName(cuttingBoard, "Cutting Board");
		GameRegistry.registerBlock(cuttingBoard, "Cutting Board");

		ProxyClient.setCustomRenderers();
		// GameRegistry.registerTileEntity(TileEntity.class, "myTile");
		// GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});
		// EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this,
		// 32, 10, true)
		recipeManager = new ManagerRecipe();
		researchManager = new ManagerResearch();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		ManagerRecipe.assemblyRecipes.registerDefaultRecipes();
	}
}
