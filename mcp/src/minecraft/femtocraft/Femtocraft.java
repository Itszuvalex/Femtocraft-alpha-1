package femtocraft;

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
import femtocraft.cooking.blocks.CuttingBoard;
import femtocraft.core.items.*;
import femtocraft.core.items.decomposition.*;
import femtocraft.core.liquids.Mass;
import femtocraft.core.liquids.MassBlock;
import femtocraft.core.ore.*;
import femtocraft.farming.produce.Tomato;
import femtocraft.farming.seeds.tomatoSeed;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.industry.blocks.BlockMicroDeconstructor;
import femtocraft.industry.blocks.BlockMicroFurnace;
import femtocraft.industry.blocks.BlockMicroReconstructor;
import femtocraft.industry.blocks.VacuumTube;
import femtocraft.industry.items.PaperSchematic;
import femtocraft.managers.FemtocraftRecipeManager;
import femtocraft.managers.research.FemtocraftResearchManager;
import femtocraft.player.FemtocraftNaniteProperties;
import femtocraft.power.blocks.*;
import femtocraft.power.items.FemtopowerMicroCubeItemBlock;
import femtocraft.power.items.SpoolGold;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.proxy.CommonProxyFemtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.logging.Logger;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)
@NetworkMod(channels = { Femtocraft.ID,
		FemtocraftNaniteProperties.PACKET_CHANNEL, VacuumTubeTile.packetChannel }, packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Femtocraft {
	public static final String ID = "Femtocraft";
	public static final String VERSION = "0.1.0";

	@Instance(ID)
	public static Femtocraft instance;

	@SidedProxy(clientSide = "femtocraft.proxy.ClientProxyFemtocraft", serverSide = "femtocraft.proxy.CommonProxyFemtocraft")
	public static CommonProxyFemtocraft proxy;

	public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab(
			"Femtocraft");

	public static Logger logger;

	public static FemtocraftRecipeManager recipeManager;
	public static FemtocraftResearchManager researchManager;

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
	public static FemtopowerCable FemtopowerCable;
	public static Block FemtopowerGeneratorTest;
	public static Block FemtopowerConsumerTest;
	public static Block FemtocraftMicroFurnaceUnlit;
	public static Block FemtocraftMicroFurnaceLit;
	public static Block FemtocraftMicroDeconstructor;
	public static Block FemtocraftMicroReconstructor;
	public static Block FemtopowerMicroCube;
	public static Block FemtocraftVacuumTube;
	public static Block FemtopowerMicroChargingBase;
	public static Block FemtopowerMicroChargingCoil;

	// liquids
	public static Fluid mass;
	public static MassBlock mass_block;

	// items
	public static Item ingotTitanium;
	public static Item ingotPlatinum;
	public static Item ingotThorium;
	public static Item ingotFarenite;
	public static Item ingotMalenite;

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

	public static Item paperSchematic;

	public static Item microInterfaceDevice;
	public static Item nanoInterfaceDevice;
	public static Item femtoInterfaceDevice;

	// Decomp items
	// Femto
	public static Item Cubit;
	public static Item Rectangulon;
	public static Item Planeoid;
	// Nano
	public static Item Crystallite;
	public static Item Mineralite;
	public static Item Metallite;
	public static Item Faunite;
	public static Item Electrite;
	public static Item Florite;
	// Micro
	public static Item MicroCrystal;
	public static Item ProteinChain;
	public static Item NerveCluster;
	public static Item ConductiveAlloy;
	public static Item MetalComposite;
	public static Item FibrousStrand;
	public static Item MineralLattice;
	public static Item FungalSpores;
	public static Item IonicChunk;
	public static Item ReplicatingMaterial;
	public static Item SpinyFilament;
	public static Item HardenedBulb;
	public static Item MorphicChannel;
	public static Item SynthesizedFiber;
	public static Item OrganometallicPlate;

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
				new GuiHandlerFemtocraft());
		MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer());
	}

	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRendering();

		if (FemtocraftConfigs.worldGen) {
			GameRegistry.registerWorldGenerator(new FemtocraftWorldGenerator());
		}

		// Change the creative tab name
		LanguageRegistry.instance().addStringLocalization(
				"itemGroup.Femtocraft", "en_US", "Femtocraft");

		// item = new Item(Configs.itemId);

		// blocks

		oreTitanium = new oreTitanium(FemtocraftConfigs.oreTitaniumID);
		MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		if (FemtocraftConfigs.registerTitaniumOreInOreDictionary)
			OreDictionary
					.registerOre("oreTitanium", new ItemStack(oreTitanium));

		orePlatinum = new orePlatinum(FemtocraftConfigs.orePlatinumID);
		MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		if (FemtocraftConfigs.registerPlatinumOreInOreDictionary)
			OreDictionary
					.registerOre("orePlatinum", new ItemStack(orePlatinum));

		oreThorium = new oreThorium(FemtocraftConfigs.oreThoriumID);
		MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		GameRegistry.registerBlock(oreThorium, "oreThorium");
		LanguageRegistry.addName(oreThorium, "Thorium Ore");
		if (FemtocraftConfigs.registerThoriumOreInOreDictionary)
			OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));

		oreFarenite = new oreFarenite(FemtocraftConfigs.oreFareniteID);
		MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));

		oreMalenite = new oreMalenite(FemtocraftConfigs.oreMaleniteID);
		MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 3);
		GameRegistry.registerBlock(oreMalenite, "oreMalenite");
		LanguageRegistry.addName(oreMalenite, "Malenite Ore");
		OreDictionary.registerOre("oreMalenite", new ItemStack(oreMalenite));

		nanoStone = new BlockNanoStone(FemtocraftConfigs.nanoStoneID);
		GameRegistry.registerBlock(nanoStone, "BlockNanoStone");
		LanguageRegistry.addName(nanoStone, "Nanostone");

		microStone = new BlockMicroStone(FemtocraftConfigs.microStoneID);
		GameRegistry.registerBlock(microStone, "BlockMicroStone");
		LanguageRegistry.addName(microStone, "Microstone");

		femtoStone = new BlockFemtoStone(FemtocraftConfigs.femtoStoneID);
		GameRegistry.registerBlock(femtoStone, "BlockFemtoStone");
		LanguageRegistry.addName(femtoStone, "Femtostone");

		unidentifiedAlloy = new BlockUnidentifiedAlloy(
				FemtocraftConfigs.unidentifiedAlloyID);
		GameRegistry.registerBlock(unidentifiedAlloy, "BlockUnidentifiedAlloy");
		LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");

		FemtopowerCable = new FemtopowerCable(
				FemtocraftConfigs.FemtopowerCableID, Material.rock);
		GameRegistry.registerBlock(FemtopowerCable, "FemtopowerCable");
		LanguageRegistry.addName(FemtopowerCable, "Femtopower Cable");

		FemtopowerGeneratorTest = new FemtopowerGenerator(
				FemtocraftConfigs.FemtopowerGeneratorTestID, Material.rock)
				.setUnlocalizedName("FemtopowerGenerator").setHardness(3.5f)
				.setStepSound(Block.soundStoneFootstep);
		GameRegistry.registerBlock(FemtopowerGeneratorTest,
				"FemtopowerGenerator");
		LanguageRegistry.addName(FemtopowerGeneratorTest,
				"Femtopower Generator");

		FemtopowerConsumerTest = new FemtopowerConsumerBlock(
				FemtocraftConfigs.FemtopowerConsumerTestBlockID, Material.rock)
				.setUnlocalizedName("FemtopowerConsumer").setHardness(3.5f)
				.setStepSound(Block.soundStoneFootstep);
		GameRegistry
				.registerBlock(FemtopowerConsumerTest, "FemtopowerConsumer");
		LanguageRegistry.addName(FemtopowerConsumerTest, "Femtopower Consumer");

		FemtocraftMicroFurnaceUnlit = new BlockMicroFurnace(
				FemtocraftConfigs.FemtocraftMicroFurnaceUnlitID, false);
		GameRegistry.registerBlock(FemtocraftMicroFurnaceUnlit,
				"FemtocraftMicroFurnace");
		LanguageRegistry.addName(FemtocraftMicroFurnaceUnlit, "Micro-Furnace");

		FemtocraftMicroFurnaceLit = new BlockMicroFurnace(
				FemtocraftConfigs.FemtocraftMicroFurnaceLitID, true);

		FemtocraftMicroDeconstructor = new BlockMicroDeconstructor(
				FemtocraftConfigs.FemtocraftMicroDeconstructorID);
		GameRegistry.registerBlock(FemtocraftMicroDeconstructor,
				"FemtocraftMicroDeconstructor");
		LanguageRegistry.addName(FemtocraftMicroDeconstructor,
				"Microtech Deconstructor");

		FemtocraftMicroReconstructor = new BlockMicroReconstructor(
				FemtocraftConfigs.FemtocraftMicroReconstructorID);
		GameRegistry.registerBlock(FemtocraftMicroReconstructor,
				"FemtocraftMicroReconstructor");
		LanguageRegistry.addName(FemtocraftMicroReconstructor,
				"Microtech Reconstructor");

		FemtopowerMicroCube = new FemtopowerMicroCube(
				FemtocraftConfigs.FemtopowerMicroCubeID);
		GameRegistry.registerBlock(FemtopowerMicroCube,
				FemtopowerMicroCubeItemBlock.class, "FemtopowerMicroCube");
		LanguageRegistry.addName(FemtopowerMicroCube, "Micro-Cube");

		FemtocraftVacuumTube = new VacuumTube(
				FemtocraftConfigs.FemtocraftVacuumTubeID);
		GameRegistry
				.registerBlock(FemtocraftVacuumTube, "FemtocraftVacuumTube");
		LanguageRegistry.addName(FemtocraftVacuumTube, "Vacuum Tube");

		FemtopowerMicroChargingBase = new MicroChargingBase(
				FemtocraftConfigs.FemtopowerMicroChargingBaseID);
		GameRegistry.registerBlock(FemtopowerMicroChargingBase,
				"FemtopowerMicroChargingBase");
		LanguageRegistry.addName(FemtopowerMicroChargingBase,
				"Electrostatic Charging Base");

		FemtopowerMicroChargingCoil = new MicroChargingCoil(
				FemtocraftConfigs.FemtopowerMicroChargingCoilID);
		GameRegistry.registerBlock(FemtopowerMicroChargingCoil,
				"FemtopowerMicroChargingCoil");
		LanguageRegistry.addName(FemtopowerMicroChargingCoil,
				"Electrostatic Charging Coil");

		// Liquids
		mass = new Mass();
		FluidRegistry.registerFluid(mass);

		mass_block = new MassBlock(FemtocraftConfigs.FemtocraftMassBlock);
		GameRegistry.registerBlock(mass_block, "Mass");
		LanguageRegistry.addName(mass_block, "Mass");

		// items

		ingotTitanium = new ItemIngotTitanium(FemtocraftConfigs.ingotTitaniumID)
				.setUnlocalizedName("ItemIngotTitanium");
		LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary)
			OreDictionary.registerOre("ItemIngotTitanium", new ItemStack(
					ingotTitanium));

		ingotPlatinum = new ItemIngotPlatinum(FemtocraftConfigs.ingotPlatinumID)
				.setUnlocalizedName("ItemIngotPlatinum");
		LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary)
			OreDictionary.registerOre("ItemIngotPlatinum", new ItemStack(
					ingotPlatinum));

		ingotThorium = new ItemIngotThorium(FemtocraftConfigs.ingotThoriumID)
				.setUnlocalizedName("ItemIngotThorium");
		LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		if (FemtocraftConfigs.registerThoriumIngotInOreDictionary)
			OreDictionary.registerOre("ItemIngotThorium", new ItemStack(
					ingotThorium));

		ingotFarenite = new ItemIngotFarenite(FemtocraftConfigs.ingotFareniteID)
				.setUnlocalizedName("ItemIngotFarenite");
		LanguageRegistry.addName(ingotFarenite, "Farenite");
		OreDictionary
				.registerOre("ItemIngotFarenite", new ItemStack(ingotFarenite));

		ingotMalenite = new ItemIngotMalenite(FemtocraftConfigs.ingotMaleniteID)
				.setUnlocalizedName("ItemIngotMalenite");
		LanguageRegistry.addName(ingotMalenite, "Malenite");
		OreDictionary
				.registerOre("ItemIngotMalenite", new ItemStack(ingotMalenite));

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
		LanguageRegistry.addName(board, "ItemBoard");

		primedBoard = new ItemPrimedBoard(FemtocraftConfigs.primedBoardID)
				.setUnlocalizedName("primedBoard");
		LanguageRegistry.addName(primedBoard, "Primed ItemBoard");

		dopedBoard = new ItemDopedBoard(FemtocraftConfigs.dopedBoardID)
				.setUnlocalizedName("dopedBoard");
		LanguageRegistry.addName(dopedBoard, "Doped ItemBoard");

		microCircuitBoard = new ItemMicroCircuitBoard(
				FemtocraftConfigs.microCircuitID)
				.setUnlocalizedName("microCircuitBoard");
		LanguageRegistry.addName(microCircuitBoard, "Micro Circuit ItemBoard");

		spool = new ItemSpool(FemtocraftConfigs.spoolID)
				.setUnlocalizedName("spool");
		LanguageRegistry.addName(spool, "ItemSpool");

		spoolGold = new SpoolGold(FemtocraftConfigs.spoolGoldID)
				.setUnlocalizedName("spoolGold");
		LanguageRegistry.addName(spoolGold, "Gold Wire ItemSpool");

		// Schematics

		paperSchematic = new PaperSchematic(FemtocraftConfigs.paperSchematicID);
		LanguageRegistry.addName(paperSchematic, "Paper Schematic");

		microInterfaceDevice = new ItemMicroInterfaceDevice(
				FemtocraftConfigs.microInterfaceDeviceID)
				.setUnlocalizedName("microInterfaceDevice");
		LanguageRegistry.addName(microInterfaceDevice, "MicroInterface Device");

		nanoInterfaceDevice = new ItemNanoInterfaceDevice(
				FemtocraftConfigs.nanoInterfaceDeviceID)
				.setUnlocalizedName("nanoInterfaceDevice");
		LanguageRegistry.addName(nanoInterfaceDevice, "NanoInterface Device");

		femtoInterfaceDevice = new CoreInterfaceDevice(
				FemtocraftConfigs.femtoInterfaceDeviceID)
				.setUnlocalizedName("femtoInterfaceDevice");
		LanguageRegistry.addName(femtoInterfaceDevice, "FemtoInterface Device");

		// Decomp
		// Femto
		Cubit = new ItemCubit(FemtocraftConfigs.CubitID)
				.setUnlocalizedName("ItemCubit");
		LanguageRegistry.addName(Cubit, "ItemCubit");
		GameRegistry.registerItem(Cubit, "ItemCubit");

		Rectangulon = new ItemRectangulon(FemtocraftConfigs.RectangulonID)
				.setUnlocalizedName("ItemRectangulon");
		LanguageRegistry.addName(Rectangulon, "ItemRectangulon");
		GameRegistry.registerItem(Rectangulon, "ItemRectangulon");

		Planeoid = new ItemPlaneoid(FemtocraftConfigs.PlaneoidID)
				.setUnlocalizedName("ItemPlaneoid");
		LanguageRegistry.addName(Planeoid, "ItemPlaneoid");
		GameRegistry.registerItem(Planeoid, "ItemPlaneoid");

		// Nano
		Crystallite = new ItemCrystallite(FemtocraftConfigs.CrystalliteID)
				.setUnlocalizedName("ItemCrystallite");
		LanguageRegistry.addName(Crystallite, "ItemCrystallite");
		GameRegistry.registerItem(Crystallite, "ItemCrystallite");

		Mineralite = new ItemMineralite(FemtocraftConfigs.MineraliteID)
				.setUnlocalizedName("ItemMineralite");
		LanguageRegistry.addName(Mineralite, "ItemMineralite");
		GameRegistry.registerItem(Mineralite, "ItemMineralite");

		Metallite = new ItemMetallite(FemtocraftConfigs.MetalliteID)
				.setUnlocalizedName("ItemMetallite");
		LanguageRegistry.addName(Metallite, "ItemMetallite");
		GameRegistry.registerItem(Metallite, "ItemMetallite");

		Faunite = new ItemFaunite(FemtocraftConfigs.FauniteID)
				.setUnlocalizedName("ItemFaunite");
		LanguageRegistry.addName(Faunite, "ItemFaunite");
		GameRegistry.registerItem(Faunite, "ItemFaunite");

		Electrite = new ItemElectrite(FemtocraftConfigs.ElectriteID)
				.setUnlocalizedName("ItemElectrite");
		LanguageRegistry.addName(Electrite, "ItemElectrite");
		GameRegistry.registerItem(Electrite, "ItemElectrite");

		Florite = new ItemFlorite(FemtocraftConfigs.FloriteID)
				.setUnlocalizedName("ItemFlorite");
		LanguageRegistry.addName(Florite, "ItemFlorite");
		GameRegistry.registerItem(Florite, "ItemFlorite");

		// Micro
		MicroCrystal = new ItemMicroCrystal(FemtocraftConfigs.MicroCrystalID)
				.setUnlocalizedName("ItemMicroCrystal");
		LanguageRegistry.addName(MicroCrystal, "Micro Crystal");
		GameRegistry.registerItem(MicroCrystal, "Micro Crystal");

		ProteinChain = new ItemProteinChain(FemtocraftConfigs.ProteinChainID)
				.setUnlocalizedName("ItemProteinChain");
		LanguageRegistry.addName(ProteinChain, "Protein Chain");
		GameRegistry.registerItem(ProteinChain, "Protein Chain");

		NerveCluster = new ItemNerveCluster(FemtocraftConfigs.NerveClusterID)
				.setUnlocalizedName("ItemNerveCluster");
		LanguageRegistry.addName(NerveCluster, "Nerve Cluster");
		GameRegistry.registerItem(NerveCluster, "Nerve Cluster");

		ConductiveAlloy = new ItemConductiveAlloy(
				FemtocraftConfigs.ConductiveAlloyID)
				.setUnlocalizedName("ItemConductiveAlloy");
		LanguageRegistry.addName(ConductiveAlloy, "Conductive Alloy");
		GameRegistry.registerItem(ConductiveAlloy, "Conductive Alloy");

		MetalComposite = new ItemMetalComposite(FemtocraftConfigs.MetalCompositeID)
				.setUnlocalizedName("ItemMetalComposite");
		LanguageRegistry.addName(MetalComposite, "Metal Composite");
		GameRegistry.registerItem(MetalComposite, "Metal Composite");

		FibrousStrand = new ItemFibrousStrand(FemtocraftConfigs.FibrousStrandID)
				.setUnlocalizedName("ItemFibrousStrand");
		LanguageRegistry.addName(FibrousStrand, "Fibrous Strand");
		GameRegistry.registerItem(FibrousStrand, "Fibrous Strand");

		MineralLattice = new ItemMineralLattice(FemtocraftConfigs.MineralLatticeID)
				.setUnlocalizedName("ItemMineralLattice");
		LanguageRegistry.addName(MineralLattice, "Mineral Lattice");
		GameRegistry.registerItem(MineralLattice, "Mineral Lattice");

		FungalSpores = new ItemFungalSpores(FemtocraftConfigs.FungalSporesID)
				.setUnlocalizedName("ItemFungalSpores");
		LanguageRegistry.addName(FungalSpores, "Fungal Spores");
		GameRegistry.registerItem(FungalSpores, "Fungal Spores");

		IonicChunk = new ItemIonicChunk(FemtocraftConfigs.IonicChunkID)
				.setUnlocalizedName("ItemIonicChunk");
		LanguageRegistry.addName(IonicChunk, "Ionic Chunk");
		GameRegistry.registerItem(IonicChunk, "Ionic Chunk");

		ReplicatingMaterial = new ItemReplicatingMaterial(
				FemtocraftConfigs.ReplicatingMaterialID)
				.setUnlocalizedName("ItemReplicatingMaterial");
		LanguageRegistry.addName(ReplicatingMaterial, "Replicating Material");
		GameRegistry.registerItem(ReplicatingMaterial, "Replicating Material");

		SpinyFilament = new ItemSpinyFilament(FemtocraftConfigs.SpinyFilamentID)
				.setUnlocalizedName("ItemSpinyFilament");
		LanguageRegistry.addName(SpinyFilament, "Spiny Filament");
		GameRegistry.registerItem(SpinyFilament, "Spiny Filament");

		HardenedBulb = new ItemHardenedBulb(FemtocraftConfigs.HardenedBulbID)
				.setUnlocalizedName("ItemHardenedBulb");
		LanguageRegistry.addName(HardenedBulb, "Hardened Bulb");
		GameRegistry.registerItem(HardenedBulb, "Hardened Bulb");

		MorphicChannel = new ItemMorphicChannel(FemtocraftConfigs.MorphicChannelID)
				.setUnlocalizedName("ItemMorphicChannel");
		LanguageRegistry.addName(MorphicChannel, "Morphic Channel");
		GameRegistry.registerItem(MorphicChannel, "Morphic Channel");

		SynthesizedFiber = new ItemSynthesizedFiber(
				FemtocraftConfigs.SynthesizedFiberID)
				.setUnlocalizedName("ItemSynthesizedFiber");
		LanguageRegistry.addName(SynthesizedFiber, "Synthesized Fiber");
		GameRegistry.registerItem(SynthesizedFiber, "Synthesized Fiber");

		OrganometallicPlate = new ItemOrganometallicPlate(
				FemtocraftConfigs.OrganometallicPlateID)
				.setUnlocalizedName("ItemOrganometallicPlate");
		LanguageRegistry.addName(OrganometallicPlate, "Organometallic Plate");
		GameRegistry.registerItem(OrganometallicPlate, "Organometallic Plate");

		// Produce
		tomatoSeed = new tomatoSeed(FemtocraftConfigs.tomatoSeedID)
				.setUnlocalizedName("tomatoSeed");
		LanguageRegistry.addName(tomatoSeed, "Tomato Seeds");
		GameRegistry.registerItem(tomatoSeed, "Tomato Seeds");

		tomato = new Tomato(FemtocraftConfigs.tomatoID)
				.setUnlocalizedName("tomato");
		LanguageRegistry.addName(tomato, "Tomato");
		GameRegistry.registerItem(tomato, "Tomato");

		// Cooking
		cuttingBoard = new CuttingBoard(FemtocraftConfigs.cuttingBoardID)
				.setUnlocalizedName("cuttingBoard");
		LanguageRegistry.addName(cuttingBoard, "Cutting ItemBoard");
		GameRegistry.registerBlock(cuttingBoard, "Cutting ItemBoard");

		registerRecipes();

		ClientProxyFemtocraft.setCustomRenderers();

		// GameRegistry.registerTileEntity(TileEntity.class, "myTile");

		// GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});

		// EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this,
		// 32, 10, true)

		// HURP....DURP
		recipeManager = new FemtocraftRecipeManager();
		researchManager = new FemtocraftResearchManager();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		FemtocraftRecipeManager.assemblyRecipes.registerDefaultRecipes();
	}

	private void registerRecipes() {
		GameRegistry.addSmelting(oreTitanium.blockID, new ItemStack(
				ingotTitanium), 0.1f);
		GameRegistry.addSmelting(orePlatinum.blockID, new ItemStack(
				ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(oreThorium.blockID,
				new ItemStack(ingotThorium), 0.1f);
		GameRegistry.addSmelting(deconstructedIron.itemID, new ItemStack(
				Item.ingotIron), 0.1f);
		GameRegistry.addSmelting(deconstructedGold.itemID, new ItemStack(
				Item.ingotGold), 0.1f);
		GameRegistry.addSmelting(deconstructedTitanium.itemID, new ItemStack(
				ingotTitanium), 0.1f);
		GameRegistry.addSmelting(deconstructedThorium.itemID, new ItemStack(
				ingotThorium), 0.1f);
		GameRegistry.addSmelting(deconstructedPlatinum.itemID, new ItemStack(
				ingotPlatinum), 0.1f);

		GameRegistry.addSmelting(primedBoard.itemID, new ItemStack(dopedBoard),
				0.1f);

		GameRegistry.addShapedRecipe(new ItemStack(primedBoard), "#", "$", '#', conductivePowder, '$', board);
		GameRegistry.addShapedRecipe(new ItemStack(paperSchematic, 3),
                "###", "###", "###", '#', Item.paper);
		GameRegistry.addShapedRecipe(new ItemStack(board), "###", '#', Item.stick);
		GameRegistry.addShapedRecipe(new ItemStack(microCircuitBoard),
                "#", "$", '#', spoolGold, '$', dopedBoard);

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(spool),
                        "# #", "#-#", "# #", '#', "plankWood",
                        '-', "stickWood"));

		GameRegistry.addShapedRecipe(new ItemStack(spoolGold, 8),
                "###", "#-#", "###", '#', Item.ingotGold, '-',
                Femtocraft.spool);

		GameRegistry.addShapelessRecipe(new ItemStack(conductivePowder, 2),
                new ItemStack(ingotFarenite),
                new ItemStack(Item.dyePowder, 1, 4));

	}

}
