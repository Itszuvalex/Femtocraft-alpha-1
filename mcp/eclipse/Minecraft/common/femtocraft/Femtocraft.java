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
import femtocraft.core.items.ConductiveAlloy;
import femtocraft.core.items.Crystallite;
import femtocraft.core.items.Cubit;
import femtocraft.core.items.Electrite;
import femtocraft.core.items.Faunite;
import femtocraft.core.items.FibrousStrand;
import femtocraft.core.items.Florite;
import femtocraft.core.items.FungalSpores;
import femtocraft.core.items.HardenedBulb;
import femtocraft.core.items.IonicChunk;
import femtocraft.core.items.MetalComposite;
import femtocraft.core.items.Metallite;
import femtocraft.core.items.MicroCrystal;
import femtocraft.core.items.MineralLattice;
import femtocraft.core.items.Mineralite;
import femtocraft.core.items.MorphicChannel;
import femtocraft.core.items.NerveCluster;
import femtocraft.core.items.OrganometallicPlate;
import femtocraft.core.items.Planeoid;
import femtocraft.core.items.ProteinChain;
import femtocraft.core.items.Rectangulon;
import femtocraft.core.items.ReplicatingMaterial;
import femtocraft.core.items.SpinyFilament;
import femtocraft.core.items.SynthesizedFiber;
import femtocraft.core.items.ingotFarenite;
import femtocraft.core.items.ingotPlatinum;
import femtocraft.core.items.ingotThorium;
import femtocraft.core.items.ingotTitanium;
import femtocraft.core.liquids.Mass;
import femtocraft.core.liquids.MassBlock;
import femtocraft.core.ore.oreFarenite;
import femtocraft.core.ore.orePlatinum;
import femtocraft.core.ore.oreThorium;
import femtocraft.core.ore.oreTitanium;
import femtocraft.farming.produce.Tomato;
import femtocraft.farming.seeds.tomatoSeed;
import femtocraft.industry.TileEntity.VacuumTubeTile;
import femtocraft.industry.blocks.BlockMicroDeconstructor;
import femtocraft.industry.blocks.BlockMicroFurnace;
import femtocraft.industry.blocks.VacuumTube;
import femtocraft.managers.FemtocraftRecipeManager;
import femtocraft.managers.FemtocraftResearchManager;
import femtocraft.power.FemtopowerCable;
import femtocraft.power.FemtopowerConsumerBlock;
import femtocraft.power.FemtopowerGenerator;
import femtocraft.power.FemtopowerMicroCube;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;
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
	public static Block FemtopowerMicroCube;
	public static Block FemtocraftVacuumTube;
	
	//liquids
	public static Fluid mass;
	public static MassBlock mass_block;
	
	//items
	public static Item ingotTitanium;
	public static Item ingotPlatinum;
	public static Item ingotThorium;
	public static Item ingotFarenite;
	
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
		 OreDictionary.registerOre("oreTitanium", new ItemStack(oreTitanium));
		 
		 orePlatinum = new orePlatinum(FemtocraftConfigs.orePlatinumID);
		 MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		 GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		 LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		 OreDictionary.registerOre("orePlatinum", new ItemStack(orePlatinum));
		 
		 oreThorium = new oreThorium(FemtocraftConfigs.oreThoriumID);
		 MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreThorium, "oreThorium");
		 LanguageRegistry.addName(oreThorium, "Thorium Ore");
		 OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));
		 
		 oreFarenite = new oreFarenite(FemtocraftConfigs.oreFareniteID);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		 GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		 LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		 OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));
		 
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
		 
		 FemtopowerMicroCube = new FemtopowerMicroCube(FemtocraftConfigs.FemtopowerMicroCubeID);
		 GameRegistry.registerBlock(FemtopowerMicroCube, "FemtopowerMicroCube");
		 LanguageRegistry.addName(FemtopowerMicroCube, "Micro-Cube");
		 
		 FemtocraftVacuumTube = new VacuumTube(FemtocraftConfigs.FemtocraftVacuumTubeID);
		 GameRegistry.registerBlock(FemtocraftVacuumTube, "FemtocraftVacuumTube");
		 LanguageRegistry.addName(FemtocraftVacuumTube, "Vacuum Tube");
		 
		 //Liquids
		 mass = new Mass();
		 FluidRegistry.registerFluid(mass);
		 
		 mass_block = new MassBlock(FemtocraftConfigs.FemtocraftMassBlock);
		 GameRegistry.registerBlock(mass_block, "Mass");
		 LanguageRegistry.addName(mass_block, "Mass");
		 
		 //items
		 
		 ingotTitanium = new ingotTitanium(FemtocraftConfigs.ingotTitaniumID).setUnlocalizedName("ingotTitanium");
		 LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		 OreDictionary.registerOre("ingotTitanium", new ItemStack(ingotTitanium));
		
		 ingotPlatinum = new ingotPlatinum(FemtocraftConfigs.ingotPlatinumID).setUnlocalizedName("ingotPlatinum");
		 LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		 OreDictionary.registerOre("ingotPlatinum", new ItemStack(ingotPlatinum));
		 
		 ingotThorium = new ingotThorium(FemtocraftConfigs.ingotThoriumID).setUnlocalizedName("ingotThorium");
		 LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		 OreDictionary.registerOre("ingotThorium", new ItemStack(ingotThorium));
		 
		 ingotFarenite = new ingotFarenite(FemtocraftConfigs.ingotFareniteID).setUnlocalizedName("ingotFarenite");
		 LanguageRegistry.addName(ingotFarenite, "Farenite");
		 OreDictionary.registerOre("ingotFarenite", new ItemStack(ingotFarenite));
		 
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
		
	}
	
	private void registerRecipes() {
		GameRegistry.addSmelting(oreTitanium.blockID, new ItemStack(ingotTitanium), 0.1f);
		GameRegistry.addSmelting(orePlatinum.blockID, new ItemStack(ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(oreThorium.blockID, new ItemStack(ingotThorium), 0.1f);
	}

}
