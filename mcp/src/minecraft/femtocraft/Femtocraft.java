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
import femtocraft.blocks.femtoStone;
import femtocraft.blocks.microStone;
import femtocraft.blocks.nanoStone;
import femtocraft.blocks.unidentifiedAlloy;
import femtocraft.cooking.blocks.CuttingBoard;
import femtocraft.core.items.Board;
import femtocraft.core.items.ConductivePowder;
import femtocraft.core.items.DeconstructedGold;
import femtocraft.core.items.DeconstructedIron;
import femtocraft.core.items.DeconstructedPlatinum;
import femtocraft.core.items.DeconstructedThorium;
import femtocraft.core.items.DeconstructedTitanium;
import femtocraft.core.items.DopedBoard;
import femtocraft.core.items.FemtoInterfaceDevice;
import femtocraft.core.items.MicroCircuitBoard;
import femtocraft.core.items.MicroInterfaceDevice;
import femtocraft.core.items.NanoInterfaceDevice;
import femtocraft.core.items.PrimedBoard;
import femtocraft.core.items.ingotFarenite;
import femtocraft.core.items.ingotMalenite;
import femtocraft.core.items.ingotPlatinum;
import femtocraft.core.items.ingotThorium;
import femtocraft.core.items.ingotTitanium;
import femtocraft.core.items.decomposition.ConductiveAlloy;
import femtocraft.core.items.decomposition.Crystallite;
import femtocraft.core.items.decomposition.Cubit;
import femtocraft.core.items.decomposition.Electrite;
import femtocraft.core.items.decomposition.Faunite;
import femtocraft.core.items.decomposition.FibrousStrand;
import femtocraft.core.items.decomposition.Florite;
import femtocraft.core.items.decomposition.FungalSpores;
import femtocraft.core.items.decomposition.HardenedBulb;
import femtocraft.core.items.decomposition.IonicChunk;
import femtocraft.core.items.decomposition.MetalComposite;
import femtocraft.core.items.decomposition.Metallite;
import femtocraft.core.items.decomposition.MicroCrystal;
import femtocraft.core.items.decomposition.MineralLattice;
import femtocraft.core.items.decomposition.Mineralite;
import femtocraft.core.items.decomposition.MorphicChannel;
import femtocraft.core.items.decomposition.NerveCluster;
import femtocraft.core.items.decomposition.OrganometallicPlate;
import femtocraft.core.items.decomposition.Planeoid;
import femtocraft.core.items.decomposition.ProteinChain;
import femtocraft.core.items.decomposition.Rectangulon;
import femtocraft.core.items.decomposition.ReplicatingMaterial;
import femtocraft.core.items.decomposition.SpinyFilament;
import femtocraft.core.items.decomposition.SynthesizedFiber;
import femtocraft.core.liquids.Mass;
import femtocraft.core.liquids.MassBlock;
import femtocraft.core.ore.oreFarenite;
import femtocraft.core.ore.oreMalenite;
import femtocraft.core.ore.orePlatinum;
import femtocraft.core.ore.oreThorium;
import femtocraft.core.ore.oreTitanium;
import femtocraft.farming.produce.Tomato;
import femtocraft.farming.seeds.tomatoSeed;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.industry.blocks.BlockMicroDeconstructor;
import femtocraft.industry.blocks.BlockMicroFurnace;
import femtocraft.industry.blocks.BlockMicroReconstructor;
import femtocraft.industry.blocks.VacuumTube;
import femtocraft.industry.items.PaperSchematic;
import femtocraft.managers.FemtocraftRecipeManager;
import femtocraft.managers.FemtocraftResearchManager;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
import femtocraft.power.blocks.FemtopowerCable;
import femtocraft.power.blocks.FemtopowerConsumerBlock;
import femtocraft.power.blocks.FemtopowerGenerator;
import femtocraft.power.blocks.FemtopowerMicroCube;
import femtocraft.power.blocks.MicroChargingBase;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.proxy.CommonProxyFemtocraft;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)

@NetworkMod(channels = { Femtocraft.ID, 
						FemtopowerMicroCubeTile.packetChannel,
						VacuumTubeTile.packetChannel}, 
						packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Femtocraft {
	public static final String ID = "Femtocraft";
	public static final String VERSION = "0.1.0";

	@Instance(ID)
	public static Femtocraft instance;

	@SidedProxy(clientSide = "femtocraft.proxy.ClientProxyFemtocraft", serverSide = "femtocraft.proxy.CommonProxyFemtocraft")
	public static CommonProxyFemtocraft proxy;

	public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab("Femtocraft");
	
	public static Logger logger;
	
	public static FemtocraftRecipeManager recipeManager;
	public static FemtocraftResearchManager researchManager;
	
	//blocks
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
	
	//liquids
	public static Fluid mass;
	public static MassBlock mass_block;
	
	//items
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
	
	public static Item paperSchematic;
	
	public static Item microInterfaceDevice;
	public static Item nanoInterfaceDevice;
	public static Item femtoInterfaceDevice;
	
	//Decomp items
	//Femto
	public static Item Cubit;
	public static Item Rectangulon;
	public static Item Planeoid;
	//Nano
	public static Item Crystallite;
	public static Item Mineralite;
	public static Item Metallite;
	public static Item Faunite;
	public static Item Electrite;
	public static Item Florite;
	//Micro
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
	
	//Produce
	public static Item tomatoSeed;
	public static Item tomato;
	
	//Cooking
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
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandlerFemtocraft());
		MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRendering();
		
		if(FemtocraftConfigs.worldGen) {
			GameRegistry.registerWorldGenerator(new FemtocraftWorldGenerator());
		}
		
		//Change the creative tab name
		LanguageRegistry.instance().addStringLocalization("itemGroup.Femtocraft", "en_US", "Femtocraft");
		
		// item = new Item(Configs.itemId);

		//blocks
		
		oreTitanium = new oreTitanium(FemtocraftConfigs.oreTitaniumID);
		 MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		 LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		 if(FemtocraftConfigs.registerTitaniumOreInOreDictionary) OreDictionary.registerOre("oreTitanium", new ItemStack(oreTitanium));
		 
		 orePlatinum = new orePlatinum(FemtocraftConfigs.orePlatinumID);
		 MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		 GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		 LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		 if(FemtocraftConfigs.registerPlatinumOreInOreDictionary) OreDictionary.registerOre("orePlatinum", new ItemStack(orePlatinum));
		 
		 oreThorium = new oreThorium(FemtocraftConfigs.oreThoriumID);
		 MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreThorium, "oreThorium");
		 LanguageRegistry.addName(oreThorium, "Thorium Ore");
		 if(FemtocraftConfigs.registerThoriumOreInOreDictionary) OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));
		 
		 oreFarenite = new oreFarenite(FemtocraftConfigs.oreFareniteID);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		 GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		 LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		 OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));
		 
		 oreMalenite = new oreMalenite(FemtocraftConfigs.oreMaleniteID);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite,  "pickaxe",  3);
		 GameRegistry.registerBlock(oreMalenite, "oreMalenite");
		 LanguageRegistry.addName(oreMalenite,  "Malenite Ore");
		 OreDictionary.registerOre("oreMalenite", new ItemStack(oreMalenite));
		 
		 nanoStone = new nanoStone(FemtocraftConfigs.nanoStoneID);
		 GameRegistry.registerBlock(nanoStone, "nanoStone");
		 LanguageRegistry.addName(nanoStone, "Nanostone");
		 
		 microStone = new microStone(FemtocraftConfigs.microStoneID);
		 GameRegistry.registerBlock(microStone, "microStone");
		 LanguageRegistry.addName(microStone,  "Microstone");
		 
		 femtoStone = new femtoStone(FemtocraftConfigs.femtoStoneID);
		 GameRegistry.registerBlock(femtoStone, "femtoStone");
		 LanguageRegistry.addName(femtoStone, "Femtostone");
		 
		 unidentifiedAlloy = new unidentifiedAlloy(FemtocraftConfigs.unidentifiedAlloyID);
		 GameRegistry.registerBlock(unidentifiedAlloy, "unidentifiedAlloy");
		 LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");
		 
		 FemtopowerCable = (FemtopowerCable) new FemtopowerCable(FemtocraftConfigs.FemtopowerCableID, Material.rock);
		 GameRegistry.registerBlock(FemtopowerCable, "FemtopowerCable");
		 LanguageRegistry.addName(FemtopowerCable, "Femtopower Cable");
		 
		 FemtopowerGeneratorTest = new FemtopowerGenerator(FemtocraftConfigs.FemtopowerGeneratorTestID, Material.rock).setUnlocalizedName("FemtopowerGenerator").setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		 GameRegistry.registerBlock(FemtopowerGeneratorTest, "FemtopowerGenerator");
		 LanguageRegistry.addName(FemtopowerGeneratorTest, "Femtopower Generator");
		 
		 FemtopowerConsumerTest = new FemtopowerConsumerBlock(FemtocraftConfigs.FemtopowerConsumerTestBlockID, Material.rock).setUnlocalizedName("FemtopowerConsumer").setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		 GameRegistry.registerBlock(FemtopowerConsumerTest, "FemtopowerConsumer");
		 LanguageRegistry.addName(FemtopowerConsumerTest, "Femtopower Consumer");
		 
		 FemtocraftMicroFurnaceUnlit = new BlockMicroFurnace(FemtocraftConfigs.FemtocraftMicroFurnaceUnlitID, false);
		 GameRegistry.registerBlock(FemtocraftMicroFurnaceUnlit, "FemtocraftMicroFurnace");
		 LanguageRegistry.addName(FemtocraftMicroFurnaceUnlit, "Micro-Furnace");
		 
		 FemtocraftMicroFurnaceLit = new BlockMicroFurnace(FemtocraftConfigs.FemtocraftMicroFurnaceLitID, true);
		 
		 FemtocraftMicroDeconstructor = new BlockMicroDeconstructor(FemtocraftConfigs.FemtocraftMicroDeconstructorID);
		 GameRegistry.registerBlock(FemtocraftMicroDeconstructor, "FemtocraftMicroDeconstructor");
		 LanguageRegistry.addName(FemtocraftMicroDeconstructor, "Microtech Deconstructor");
		 
		 FemtocraftMicroReconstructor = new BlockMicroReconstructor(FemtocraftConfigs.FemtocraftMicroReconstructorID);
		 GameRegistry.registerBlock(FemtocraftMicroReconstructor, "FemtocraftMicroReconstructor");
		 LanguageRegistry.addName(FemtocraftMicroReconstructor, "Microtech Reconstructor");
		 
		 FemtopowerMicroCube = new FemtopowerMicroCube(FemtocraftConfigs.FemtopowerMicroCubeID);
		 GameRegistry.registerBlock(FemtopowerMicroCube, "FemtopowerMicroCube");
		 LanguageRegistry.addName(FemtopowerMicroCube, "Micro-Cube");
		 
		 FemtocraftVacuumTube = new VacuumTube(FemtocraftConfigs.FemtocraftVacuumTubeID);
		 GameRegistry.registerBlock(FemtocraftVacuumTube, "FemtocraftVacuumTube");
		 LanguageRegistry.addName(FemtocraftVacuumTube, "Vacuum Tube");
		 
		 FemtopowerMicroChargingBase = new MicroChargingBase(FemtocraftConfigs.FemtopowerMicroChargingBaseID);
		 GameRegistry.registerBlock(FemtopowerMicroChargingBase, "FemtopowerMicroChargingBase");
		 LanguageRegistry.addName(FemtopowerMicroChargingBase, "Electrostatic Charging Base");
		 
		 //Liquids
		 mass = new Mass();
		 FluidRegistry.registerFluid(mass);
		 
		 mass_block = new MassBlock(FemtocraftConfigs.FemtocraftMassBlock);
		 GameRegistry.registerBlock(mass_block, "Mass");
		 LanguageRegistry.addName(mass_block, "Mass");
		 
		 //items
		 
		 ingotTitanium = new ingotTitanium(FemtocraftConfigs.ingotTitaniumID).setUnlocalizedName("ingotTitanium");
		 LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		 if(FemtocraftConfigs.registerTitaniumIngotInOreDictionary) OreDictionary.registerOre("ingotTitanium", new ItemStack(ingotTitanium));
		
		 ingotPlatinum = new ingotPlatinum(FemtocraftConfigs.ingotPlatinumID).setUnlocalizedName("ingotPlatinum");
		 LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		 if(FemtocraftConfigs.registerPlatinumIngotInOreDictionary) OreDictionary.registerOre("ingotPlatinum", new ItemStack(ingotPlatinum));
		 
		 ingotThorium = new ingotThorium(FemtocraftConfigs.ingotThoriumID).setUnlocalizedName("ingotThorium");
		 LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		 if(FemtocraftConfigs.registerThoriumIngotInOreDictionary) OreDictionary.registerOre("ingotThorium", new ItemStack(ingotThorium));
		 
		 ingotFarenite = new ingotFarenite(FemtocraftConfigs.ingotFareniteID).setUnlocalizedName("ingotFarenite");
		 LanguageRegistry.addName(ingotFarenite, "Farenite");
		 OreDictionary.registerOre("ingotFarenite", new ItemStack(ingotFarenite));
		 
		 ingotMalenite = new ingotMalenite(FemtocraftConfigs.ingotMaleniteID).setUnlocalizedName("ingotMalenite");
		 LanguageRegistry.addName(ingotMalenite, "Malenite");
		 OreDictionary.registerOre("ingotMalenite", new ItemStack(ingotMalenite));
		 
		 deconstructedIron = new DeconstructedIron(FemtocraftConfigs.deconstructedIronID).setUnlocalizedName("deconstructedIron");
		 LanguageRegistry.addName(deconstructedIron, "Deconstructed Iron");
		 OreDictionary.registerOre("dustIron", new ItemStack(deconstructedIron));
		 
		 deconstructedGold = new DeconstructedGold(FemtocraftConfigs.deconstructedGoldID).setUnlocalizedName("deconstructedGold");
		 LanguageRegistry.addName(deconstructedGold, "Deconstructed Gold");
		 OreDictionary.registerOre("dustGold", new ItemStack(deconstructedGold));
		 
		 deconstructedTitanium = new DeconstructedTitanium(FemtocraftConfigs.deconstructedTitaniumID).setUnlocalizedName("deconstructedTitanium");
		 LanguageRegistry.addName(deconstructedTitanium, "Deconstructed Titanium");
		 if(FemtocraftConfigs.registerTitaniumDustInOreDictionary) OreDictionary.registerOre("dustTitanium", new ItemStack(deconstructedTitanium));
		 
		 deconstructedThorium = new DeconstructedThorium(FemtocraftConfigs.deconstructedThoriumID).setUnlocalizedName("deconstructedThorium");
		 LanguageRegistry.addName(deconstructedThorium, "Deconstructed Thorium");
		 if(FemtocraftConfigs.registerThoriumDustInOreDictionary) OreDictionary.registerOre("dustThorium", new ItemStack(deconstructedThorium));
		 
		 deconstructedPlatinum = new DeconstructedPlatinum(FemtocraftConfigs.deconstructedPlatinumID).setUnlocalizedName("deconstructedPlatinum");
		 LanguageRegistry.addName(deconstructedPlatinum, "Deconstructed Platinum");
		 if(FemtocraftConfigs.registerPlatinumDustInOreDictionary) OreDictionary.registerOre("dustPlatinum", new ItemStack(deconstructedPlatinum));
		 
		 //
		 
		 conductivePowder = new ConductivePowder(FemtocraftConfigs.conductivePowderID).setUnlocalizedName("conductivePowder");
		 LanguageRegistry.addName(conductivePowder, "Conductive Powder");
		 
		 board = new Board(FemtocraftConfigs.boardID).setUnlocalizedName("board");
		 LanguageRegistry.addName(board, "Board");
		 
		 primedBoard = new PrimedBoard(FemtocraftConfigs.primedBoardID).setUnlocalizedName("primedBoard");
		 LanguageRegistry.addName(primedBoard, "Primed Board");
		 
		 dopedBoard = new DopedBoard(FemtocraftConfigs.dopedBoardID).setUnlocalizedName("dopedBoard");
		 LanguageRegistry.addName(dopedBoard, "Doped Board");
		 
		 microCircuitBoard = new MicroCircuitBoard(FemtocraftConfigs.microCircuitID).setUnlocalizedName("microCircuitBoard");
		 LanguageRegistry.addName(microCircuitBoard, "Micro Circuit Board");
		 
		 //Schematics
		 
		 paperSchematic = new PaperSchematic(FemtocraftConfigs.paperSchematicID);
		 LanguageRegistry.addName(paperSchematic, "Paper Schematic");
		 
		 microInterfaceDevice = new MicroInterfaceDevice(FemtocraftConfigs.microInterfaceDeviceID).setUnlocalizedName("microInterfaceDevice");
		 LanguageRegistry.addName(microInterfaceDevice, "MicroInterface Device");
		 
		 nanoInterfaceDevice = new NanoInterfaceDevice(FemtocraftConfigs.nanoInterfaceDeviceID).setUnlocalizedName("nanoInterfaceDevice");
		 LanguageRegistry.addName(nanoInterfaceDevice, "NanoInterface Device");
		 
		 femtoInterfaceDevice = new FemtoInterfaceDevice(FemtocraftConfigs.femtoInterfaceDeviceID).setUnlocalizedName("femtoInterfaceDevice");
		 LanguageRegistry.addName(femtoInterfaceDevice, "FemtoInterface Device");
		 
		 //Decomp
		 //Femto
		 Cubit = new Cubit(FemtocraftConfigs.CubitID).setUnlocalizedName("Cubit");
		 LanguageRegistry.addName(Cubit, "Cubit");
		 GameRegistry.registerItem(Cubit, "Cubit");
		 
		 Rectangulon = new Rectangulon(FemtocraftConfigs.RectangulonID).setUnlocalizedName("Rectangulon");
		 LanguageRegistry.addName(Rectangulon, "Rectangulon");
		 GameRegistry.registerItem(Rectangulon, "Rectangulon");
		 
		 Planeoid = new Planeoid(FemtocraftConfigs.PlaneoidID).setUnlocalizedName("Planeoid");
		 LanguageRegistry.addName(Planeoid, "Planeoid");
		 GameRegistry.registerItem(Planeoid, "Planeoid");
		 
		 //Nano
		 Crystallite = new Crystallite(FemtocraftConfigs.CrystalliteID).setUnlocalizedName("Crystallite");
		 LanguageRegistry.addName(Crystallite, "Crystallite");
		 GameRegistry.registerItem(Crystallite, "Crystallite");
		 
		 Mineralite = new Mineralite(FemtocraftConfigs.MineraliteID).setUnlocalizedName("Mineralite");
		 LanguageRegistry.addName(Mineralite, "Mineralite");
		 GameRegistry.registerItem(Mineralite, "Mineralite");
		 
		 Metallite = new Metallite(FemtocraftConfigs.MetalliteID).setUnlocalizedName("Metallite");
		 LanguageRegistry.addName(Metallite, "Metallite");
		 GameRegistry.registerItem(Metallite, "Metallite");
		 
		 Faunite = new Faunite(FemtocraftConfigs.FauniteID).setUnlocalizedName("Faunite");
		 LanguageRegistry.addName(Faunite, "Faunite");
		 GameRegistry.registerItem(Faunite, "Faunite");
		 
		 Electrite = new Electrite(FemtocraftConfigs.ElectriteID).setUnlocalizedName("Electrite");
		 LanguageRegistry.addName(Electrite, "Electrite");
		 GameRegistry.registerItem(Electrite, "Electrite");
		 
		 Florite = new Florite(FemtocraftConfigs.FloriteID).setUnlocalizedName("Florite");
		 LanguageRegistry.addName(Florite, "Florite");
		 GameRegistry.registerItem(Florite, "Florite");
		 
		 //Micro
		 MicroCrystal = new MicroCrystal(FemtocraftConfigs.MicroCrystalID).setUnlocalizedName("MicroCrystal");
		 LanguageRegistry.addName(MicroCrystal, "Micro Crystal");
		 GameRegistry.registerItem(MicroCrystal, "Micro Crystal");

		 ProteinChain = new ProteinChain(FemtocraftConfigs.ProteinChainID).setUnlocalizedName("ProteinChain");
		 LanguageRegistry.addName(ProteinChain, "Protein Chain");
		 GameRegistry.registerItem(ProteinChain, "Protein Chain");
		 
		 NerveCluster = new NerveCluster(FemtocraftConfigs.NerveClusterID).setUnlocalizedName("NerveCluster");
		 LanguageRegistry.addName(NerveCluster, "Nerve Cluster");
		 GameRegistry.registerItem(NerveCluster, "Nerve Cluster");
		 
		 ConductiveAlloy = new ConductiveAlloy(FemtocraftConfigs.ConductiveAlloyID).setUnlocalizedName("ConductiveAlloy");
		 LanguageRegistry.addName(ConductiveAlloy, "Conductive Alloy");
		 GameRegistry.registerItem(ConductiveAlloy, "Conductive Alloy");
		 
		 MetalComposite = new MetalComposite(FemtocraftConfigs.MetalCompositeID).setUnlocalizedName("MetalComposite");
		 LanguageRegistry.addName(MetalComposite, "Metal Composite");
		 GameRegistry.registerItem(MetalComposite, "Metal Composite");
		 
		 FibrousStrand = new FibrousStrand(FemtocraftConfigs.FibrousStrandID).setUnlocalizedName("FibrousStrand");
		 LanguageRegistry.addName(FibrousStrand, "Fibrous Strand");
		 GameRegistry.registerItem(FibrousStrand, "Fibrous Strand");
		 
		 MineralLattice = new MineralLattice(FemtocraftConfigs.MineralLatticeID).setUnlocalizedName("MineralLattice");
		 LanguageRegistry.addName(MineralLattice, "Mineral Lattice");
		 GameRegistry.registerItem(MineralLattice, "Mineral Lattice");
		 
		 FungalSpores = new FungalSpores(FemtocraftConfigs.FungalSporesID).setUnlocalizedName("FungalSpores");
		 LanguageRegistry.addName(FungalSpores, "Fungal Spores");
		 GameRegistry.registerItem(FungalSpores, "Fungal Spores");
		 
		 IonicChunk = new IonicChunk(FemtocraftConfigs.IonicChunkID).setUnlocalizedName("IonicChunk");
		 LanguageRegistry.addName(IonicChunk, "Ionic Chunk");
		 GameRegistry.registerItem(IonicChunk, "Ionic Chunk");
		 
		 ReplicatingMaterial = new ReplicatingMaterial(FemtocraftConfigs.ReplicatingMaterialID).setUnlocalizedName("ReplicatingMaterial");
		 LanguageRegistry.addName(ReplicatingMaterial, "Replicating Material");
		 GameRegistry.registerItem(ReplicatingMaterial, "Replicating Material");
		 
		 SpinyFilament = new SpinyFilament(FemtocraftConfigs.SpinyFilamentID).setUnlocalizedName("SpinyFilament");
		 LanguageRegistry.addName(SpinyFilament, "Spiny Filament");
		 GameRegistry.registerItem(SpinyFilament, "Spiny Filament");
		 
		 HardenedBulb = new HardenedBulb(FemtocraftConfigs.HardenedBulbID).setUnlocalizedName("HardenedBulb");
		 LanguageRegistry.addName(HardenedBulb, "Hardened Bulb");
		 GameRegistry.registerItem(HardenedBulb, "Hardened Bulb");
		 
		 MorphicChannel = new MorphicChannel(FemtocraftConfigs.MorphicChannelID).setUnlocalizedName("MorphicChannel");
		 LanguageRegistry.addName(MorphicChannel, "Morphic Channel");
		 GameRegistry.registerItem(MorphicChannel, "Morphic Channel");
		 
		 SynthesizedFiber = new SynthesizedFiber(FemtocraftConfigs.SynthesizedFiberID).setUnlocalizedName("SynthesizedFiber");
		 LanguageRegistry.addName(SynthesizedFiber, "Synthesized Fiber");
		 GameRegistry.registerItem(SynthesizedFiber, "Synthesized Fiber");
		 
		 OrganometallicPlate = new OrganometallicPlate(FemtocraftConfigs.OrganometallicPlateID).setUnlocalizedName("OrganometallicPlate");
		 LanguageRegistry.addName(OrganometallicPlate, "Organometallic Plate");
		 GameRegistry.registerItem(OrganometallicPlate, "Organometallic Plate");
		 
		 
		 
		 //Produce
		 tomatoSeed = new tomatoSeed(FemtocraftConfigs.tomatoSeedID).setUnlocalizedName("tomatoSeed");
		 LanguageRegistry.addName(tomatoSeed, "Tomato Seeds");
		 GameRegistry.registerItem(tomatoSeed, "Tomato Seeds");
		 
		 tomato = new Tomato(FemtocraftConfigs.tomatoID).setUnlocalizedName("tomato");
		 LanguageRegistry.addName(tomato, "Tomato");
		 GameRegistry.registerItem(tomato, "Tomato");
		 
		 //Cooking
		 cuttingBoard = new CuttingBoard(FemtocraftConfigs.cuttingBoardID).setUnlocalizedName("cuttingBoard");
		 LanguageRegistry.addName(cuttingBoard, "Cutting Board");
		 GameRegistry.registerBlock(cuttingBoard, "Cutting Board");
		 
		 
		 registerRecipes();
		 
		 ClientProxyFemtocraft.setCustomRenderers();
		 
		// GameRegistry.registerTileEntity(TileEntity.class, "myTile");

		// GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});

		// EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this, 32, 10, true)
		 
		 
		 //HURP....DURP
		 recipeManager = new FemtocraftRecipeManager();
		 researchManager = new FemtocraftResearchManager();
	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		recipeManager.assemblyRecipes.registerDefaultRecipes();
	}
	
	private void registerRecipes() {
		GameRegistry.addSmelting(oreTitanium.blockID, new ItemStack(ingotTitanium), 0.1f);
		GameRegistry.addSmelting(orePlatinum.blockID, new ItemStack(ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(oreThorium.blockID, new ItemStack(ingotThorium), 0.1f);
		GameRegistry.addSmelting(deconstructedIron.itemID, new ItemStack(Item.ingotIron), 0.1f);
		GameRegistry.addSmelting(deconstructedGold.itemID, new ItemStack(Item.ingotGold), 0.1f);
		GameRegistry.addSmelting(deconstructedTitanium.itemID, new ItemStack(ingotTitanium), 0.1f);
		GameRegistry.addSmelting(deconstructedThorium.itemID, new ItemStack(ingotThorium), 0.1f);
		GameRegistry.addSmelting(deconstructedPlatinum.itemID, new ItemStack(ingotPlatinum), 0.1f);
		
		GameRegistry.addSmelting(primedBoard.itemID, new ItemStack(dopedBoard), 0.1f);

		GameRegistry.addShapedRecipe(new ItemStack(primedBoard), new Object[]{"#", "$", '#', conductivePowder, '$', board});
		GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.paperSchematic, 3),new Object[] {"###", "###", "###", '#', Item.paper});
		GameRegistry.addShapedRecipe(new ItemStack(board), new Object[]{"###", '#', Item.stick});
		
		GameRegistry.addShapelessRecipe(new ItemStack(conductivePowder, 2), new Object[]{new ItemStack(ingotFarenite), new ItemStack(Item.dyePowder, 1, 4)});

	}

}