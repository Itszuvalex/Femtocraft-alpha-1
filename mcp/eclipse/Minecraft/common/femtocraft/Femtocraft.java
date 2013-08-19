package femtocraft;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
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
import femtocraft.core.ore.oreFarenite;
import femtocraft.core.ore.orePlatinum;
import femtocraft.core.ore.oreThorium;
import femtocraft.core.ore.oreTitanium;
import femtocraft.farming.produce.Tomato;
import femtocraft.farming.seeds.tomatoSeed;
import femtocraft.industry.blocks.BlockMicroFurnace;
import femtocraft.managers.FemtocraftRecipeManager;
import femtocraft.power.FemtopowerCable;
import femtocraft.power.FemtopowerConsumerBlock;
import femtocraft.power.FemtopowerGenerator;
import femtocraft.proxy.ClientProxyFemtocraft;
import femtocraft.proxy.CommonProxyFemtocraft;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)

@NetworkMod(channels = { Femtocraft.ID }, packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Femtocraft {
	public static final String ID = "Femtocraft";
	public static final String VERSION = "0.1.0";

	@Instance(ID)
	public static Femtocraft instance;

	@SidedProxy(clientSide = "femtocraft.proxy.ClientProxyFemtocraft", serverSide = "femtocraft.proxy.CommonProxyFemtocraft")
	public static CommonProxyFemtocraft proxy;

	public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab("Femtocraft");
	
	public static Logger logger;
	
	public static FemtocraftRecipeManager recipeManager = new FemtocraftRecipeManager();

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
		Configs.load(config);
		
		Femtocraft.proxy.registerTileEntities();
		Femtocraft.proxy.registerRendering();
		Femtocraft.proxy.registerBlockRenderers();
		
		NetworkRegistry.instance().registerGuiHandler(this, new GuiHandlerFemtocraft());
	}
	
	@EventHandler
	public void load(FMLInitializationEvent event) {
		proxy.registerRendering();
		
		if(Configs.worldGen) {
			GameRegistry.registerWorldGenerator(new FemtocraftWorldGenerator());
		}
		
		//Change the creative tab name
		LanguageRegistry.instance().addStringLocalization("itemGroup.Femtocraft", "en_US", "Femtocraft");
		
		// item = new Item(Configs.itemId);

		//blocks
		
		oreTitanium = new oreTitanium(Configs.oreTitaniumID, 0).setUnlocalizedName("oreTitanium").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		 LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		 OreDictionary.registerOre("oreTitanium", new ItemStack(oreTitanium));
		 
		 orePlatinum = new orePlatinum(Configs.orePlatinumID, 1).setUnlocalizedName("orePlatinum").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		 GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		 LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		 OreDictionary.registerOre("orePlatinum", new ItemStack(orePlatinum));
		 
		 oreThorium = new oreThorium(Configs.oreThoriumID, 2).setUnlocalizedName("oreThorium").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreThorium, "oreThorium");
		 LanguageRegistry.addName(oreThorium, "Thorium Ore");
		 OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));
		 
		 oreFarenite = new oreFarenite(Configs.oreFareniteID, 3).setUnlocalizedName("oreFarenite").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		 GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		 LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		 OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));
		 
		 nanoStone = new nanoStone(Configs.nanoStoneID, 4).setUnlocalizedName("nanoStone").setHardness(7.0f).setStepSound(Block.soundMetalFootstep).setResistance(12f);
		 GameRegistry.registerBlock(nanoStone, "nanoStone");
		 LanguageRegistry.addName(nanoStone, "Nanostone");
		 
		 microStone = new microStone(Configs.microStoneID, 5).setUnlocalizedName("microStone").setHardness(6.0f).setStepSound(Block.soundMetalFootstep).setResistance(9f);
		 GameRegistry.registerBlock(microStone, "microStone");
		 LanguageRegistry.addName(microStone,  "Microstone");
		 
		 femtoStone = new femtoStone(Configs.femtoStoneID, 6).setUnlocalizedName("femtoStone").setHardness(8.0f).setStepSound(Block.soundMetalFootstep).setResistance(15f);
		 GameRegistry.registerBlock(femtoStone, "femtoStone");
		 LanguageRegistry.addName(femtoStone, "Femtostone");
		 
		 unidentifiedAlloy = new unidentifiedAlloy(Configs.unidentifiedAlloyID, 7).setUnlocalizedName("unidentifiedAlloy").setBlockUnbreakable().setStepSound(Block.soundMetalFootstep).setResistance(20f);
		 GameRegistry.registerBlock(unidentifiedAlloy, "unidentifiedAlloy");
		 LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");
		 
		 FemtopowerCable = (FemtopowerCable) new FemtopowerCable(Configs.FemtopowerCableID, Material.rock).setUnlocalizedName("FemtopowerCable").setHardness(1.0f).setStepSound(Block.soundStoneFootstep);
		 GameRegistry.registerBlock(FemtopowerCable, "FemtopowerCable");
		 LanguageRegistry.addName(FemtopowerCable, "Femtopower Cable");
		 
		 FemtopowerGeneratorTest = new FemtopowerGenerator(Configs.FemtopowerGeneratorTestID, Material.rock).setUnlocalizedName("FemtopowerGenerator").setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		 GameRegistry.registerBlock(FemtopowerGeneratorTest, "FemtopowerGenerator");
		 LanguageRegistry.addName(FemtopowerGeneratorTest, "Femtopower Generator");
		 
		 FemtopowerConsumerTest = new FemtopowerConsumerBlock(Configs.FemtopowerConsumerTestBlockID, Material.rock).setUnlocalizedName("FemtopowerConsumer").setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		 GameRegistry.registerBlock(FemtopowerConsumerTest, "FemtopowerConsumer");
		 LanguageRegistry.addName(FemtopowerConsumerTest, "Femtopower Consumer");
		 
		 FemtocraftMicroFurnaceUnlit = new BlockMicroFurnace(Configs.FemtocraftMicroFurnaceUnlitID, false).setUnlocalizedName("FemtocraftMicroFurnace").setHardness(3.5f).setStepSound(Block.soundStoneFootstep).setCreativeTab(femtocraftTab);
		 GameRegistry.registerBlock(FemtocraftMicroFurnaceUnlit, "FemtocraftMicroFurnace");
		 LanguageRegistry.addName(FemtocraftMicroFurnaceUnlit, "Micro-Furnace");
		 
		 FemtocraftMicroFurnaceLit = new BlockMicroFurnace(Configs.FemtocraftMicroFurnaceLitID, true).setLightValue(0.875F).setUnlocalizedName("FemtocraftMicroFurnace").setHardness(3.5f).setStepSound(Block.soundStoneFootstep);
		 
		 //items
		 
		 ingotTitanium = new ingotTitanium(Configs.ingotTitaniumID).setUnlocalizedName("ingotTitanium");
		 LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		 OreDictionary.registerOre("ingotTitanium", new ItemStack(ingotTitanium));
		
		 ingotPlatinum = new ingotPlatinum(Configs.ingotPlatinumID).setUnlocalizedName("ingotPlatinum");
		 LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		 OreDictionary.registerOre("ingotPlatinum", new ItemStack(ingotPlatinum));
		 
		 ingotThorium = new ingotThorium(Configs.ingotThoriumID).setUnlocalizedName("ingotThorium");
		 LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		 OreDictionary.registerOre("ingotThorium", new ItemStack(ingotThorium));
		 
		 ingotFarenite = new ingotFarenite(Configs.ingotFareniteID).setUnlocalizedName("ingotFarenite");
		 LanguageRegistry.addName(ingotFarenite, "Farenite");
		 OreDictionary.registerOre("ingotFarenite", new ItemStack(ingotFarenite));
		 
		 //Decomp
		 //Femto
		 Cubit = new Cubit(Configs.CubitID).setUnlocalizedName("Cubit");
		 LanguageRegistry.addName(Cubit, "Cubit");
		 GameRegistry.registerItem(Cubit, "Cubit");
		 
		 Rectangulon = new Rectangulon(Configs.RectangulonID).setUnlocalizedName("Rectangulon");
		 LanguageRegistry.addName(Rectangulon, "Rectangulon");
		 GameRegistry.registerItem(Rectangulon, "Rectangulon");
		 
		 Planeoid = new Planeoid(Configs.PlaneoidID).setUnlocalizedName("Planeoid");
		 LanguageRegistry.addName(Planeoid, "Planeoid");
		 GameRegistry.registerItem(Planeoid, "Planeoid");
		 
		 //Nano
		 Crystallite = new Crystallite(Configs.CrystalliteID).setUnlocalizedName("Crystallite");
		 LanguageRegistry.addName(Crystallite, "Crystallite");
		 GameRegistry.registerItem(Crystallite, "Crystallite");
		 
		 Mineralite = new Mineralite(Configs.MineraliteID).setUnlocalizedName("Mineralite");
		 LanguageRegistry.addName(Mineralite, "Mineralite");
		 GameRegistry.registerItem(Mineralite, "Mineralite");
		 
		 Metallite = new Metallite(Configs.MetalliteID).setUnlocalizedName("Metallite");
		 LanguageRegistry.addName(Metallite, "Metallite");
		 GameRegistry.registerItem(Metallite, "Metallite");
		 
		 Faunite = new Faunite(Configs.FauniteID).setUnlocalizedName("Faunite");
		 LanguageRegistry.addName(Faunite, "Faunite");
		 GameRegistry.registerItem(Faunite, "Faunite");
		 
		 Electrite = new Electrite(Configs.ElectriteID).setUnlocalizedName("Electrite");
		 LanguageRegistry.addName(Electrite, "Electrite");
		 GameRegistry.registerItem(Electrite, "Electrite");
		 
		 Florite = new Florite(Configs.FloriteID).setUnlocalizedName("Florite");
		 LanguageRegistry.addName(Florite, "Florite");
		 GameRegistry.registerItem(Florite, "Florite");
		 
		 //Micro
		 MicroCrystal = new MicroCrystal(Configs.MicroCrystalID).setUnlocalizedName("MicroCrystal");
		 LanguageRegistry.addName(MicroCrystal, "Micro Crystal");
		 GameRegistry.registerItem(MicroCrystal, "Micro Crystal");

		 ProteinChain = new ProteinChain(Configs.ProteinChainID).setUnlocalizedName("ProteinChain");
		 LanguageRegistry.addName(ProteinChain, "Protein Chain");
		 GameRegistry.registerItem(ProteinChain, "Protein Chain");
		 
		 NerveCluster = new NerveCluster(Configs.NerveClusterID).setUnlocalizedName("NerveCluster");
		 LanguageRegistry.addName(NerveCluster, "Nerve Cluster");
		 GameRegistry.registerItem(NerveCluster, "Nerve Cluster");
		 
		 ConductiveAlloy = new ConductiveAlloy(Configs.ConductiveAlloyID).setUnlocalizedName("ConductiveAlloy");
		 LanguageRegistry.addName(ConductiveAlloy, "Conductive Alloy");
		 GameRegistry.registerItem(ConductiveAlloy, "Conductive Alloy");
		 
		 MetalComposite = new MetalComposite(Configs.MetalCompositeID).setUnlocalizedName("MetalComposite");
		 LanguageRegistry.addName(MetalComposite, "Metal Composite");
		 GameRegistry.registerItem(MetalComposite, "Metal Composite");
		 
		 FibrousStrand = new FibrousStrand(Configs.FibrousStrandID).setUnlocalizedName("FibrousStrand");
		 LanguageRegistry.addName(FibrousStrand, "Fibrous Strand");
		 GameRegistry.registerItem(FibrousStrand, "Fibrous Strand");
		 
		 MineralLattice = new MineralLattice(Configs.MineralLatticeID).setUnlocalizedName("MineralLattice");
		 LanguageRegistry.addName(MineralLattice, "Mineral Lattice");
		 GameRegistry.registerItem(MineralLattice, "Mineral Lattice");
		 
		 FungalSpores = new FungalSpores(Configs.FungalSporesID).setUnlocalizedName("FungalSpores");
		 LanguageRegistry.addName(FungalSpores, "Fungal Spores");
		 GameRegistry.registerItem(FungalSpores, "Fungal Spores");
		 
		 IonicChunk = new IonicChunk(Configs.IonicChunkID).setUnlocalizedName("IonicChunk");
		 LanguageRegistry.addName(IonicChunk, "Ionic Chunk");
		 GameRegistry.registerItem(IonicChunk, "Ionic Chunk");
		 
		 ReplicatingMaterial = new ReplicatingMaterial(Configs.ReplicatingMaterialID).setUnlocalizedName("ReplicatingMaterial");
		 LanguageRegistry.addName(ReplicatingMaterial, "Replicating Material");
		 GameRegistry.registerItem(ReplicatingMaterial, "Replicating Material");
		 
		 SpinyFilament = new SpinyFilament(Configs.SpinyFilamentID).setUnlocalizedName("SpinyFilament");
		 LanguageRegistry.addName(SpinyFilament, "Spiny Filament");
		 GameRegistry.registerItem(SpinyFilament, "Spiny Filament");
		 
		 HardenedBulb = new HardenedBulb(Configs.HardenedBulbID).setUnlocalizedName("HardenedBulb");
		 LanguageRegistry.addName(HardenedBulb, "Hardened Bulb");
		 GameRegistry.registerItem(HardenedBulb, "Hardened Bulb");
		 
		 MorphicChannel = new MorphicChannel(Configs.MorphicChannelID).setUnlocalizedName("MorphicChannel");
		 LanguageRegistry.addName(MorphicChannel, "Morphic Channel");
		 GameRegistry.registerItem(MorphicChannel, "Morphic Channel");
		 
		 SynthesizedFiber = new SynthesizedFiber(Configs.SynthesizedFiberID).setUnlocalizedName("SynthesizedFiber");
		 LanguageRegistry.addName(SynthesizedFiber, "Synthesized Fiber");
		 GameRegistry.registerItem(SynthesizedFiber, "Synthesized Fiber");
		 
		 OrganometallicPlate = new OrganometallicPlate(Configs.OrganometallicPlateID).setUnlocalizedName("OrganometallicPlate");
		 LanguageRegistry.addName(OrganometallicPlate, "Organometallic Plate");
		 GameRegistry.registerItem(OrganometallicPlate, "Organometallic Plate");
		 
		 
		 
		 //Produce
		 tomatoSeed = new tomatoSeed(Configs.tomatoSeedID).setUnlocalizedName("tomatoSeed");
		 LanguageRegistry.addName(tomatoSeed, "Tomato Seeds");
		 GameRegistry.registerItem(tomatoSeed, "Tomato Seeds");
		 
		 tomato = new Tomato(Configs.tomatoID).setUnlocalizedName("tomato");
		 LanguageRegistry.addName(tomato, "Tomato");
		 GameRegistry.registerItem(tomato, "Tomato");
		 
		 //Cooking
		 cuttingBoard = new CuttingBoard(Configs.cuttingBoardID).setUnlocalizedName("cuttingBoard");
		 LanguageRegistry.addName(cuttingBoard, "Cutting Board");
		 GameRegistry.registerBlock(cuttingBoard, "Cutting Board");
		 
		 
		 registerRecipes();
		 
		 ClientProxyFemtocraft.setCustomRenderers();
		 
		// GameRegistry.registerTileEntity(TileEntity.class, "myTile");

		// GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});

		// EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this, 32, 10, true)
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
