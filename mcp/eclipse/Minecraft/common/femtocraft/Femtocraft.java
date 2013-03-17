package femtocraft;

import java.util.logging.Logger;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.Init;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.Mod.PostInit;
import cpw.mods.fml.common.Mod.PreInit;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import femtocraft.blocks.femtoStone;
import femtocraft.blocks.microStone;
import femtocraft.blocks.nanoStone;
import femtocraft.blocks.unidentifiedAlloy;
import femtocraft.items.ingotFarenite;
import femtocraft.items.ingotPlatinum;
import femtocraft.items.ingotThorium;
import femtocraft.items.ingotTitanium;
import femtocraft.ore.oreFarenite;
import femtocraft.ore.orePlatinum;
import femtocraft.ore.oreThorium;
import femtocraft.ore.oreTitanium;
import femtocraft.proxy.CommonProxyFemtocraft;

@Mod(modid = Femtocraft.ID, version = Femtocraft.VERSION)
@NetworkMod(channels = { Femtocraft.ID }, packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = false)
public class Femtocraft {
	public static final String ID = "Femtocraft";
	public static final String VERSION = "0.1.0";

	@Instance(ID)
	public static Femtocraft instance;

	@SidedProxy(clientSide = "femtocraft.proxy.ClientProxyFemtocraft", serverSide = "femtocraft.proxy.CommonProxyFemtocraft")
	public static CommonProxyFemtocraft proxy;

	public static Logger logger;

	//blocks
	public static Block oreTitanium;
	public static Block orePlatinum;
	public static Block oreThorium;
	public static Block oreFarenite;
	public static Block microStone;
	public static Block nanoStone;
	public static Block femtoStone;
	public static Block unidentifiedAlloy;
	
	//items
	public static Item ingotTitanium;
	public static Item ingotPlatinum;
	public static Item ingotThorium;
	public static Item ingotFarenite;
	
	@PreInit
	public void preInit(FMLPreInitializationEvent event) {
		logger = Logger.getLogger(ID);
		logger.setParent(FMLLog.getLogger());

		Configuration config = new Configuration(
				event.getSuggestedConfigurationFile());
		Configs.load(config);
	}
	
	@Init
	public void init(FMLInitializationEvent event) {
		proxy.registerRendering();
		
		if(Configs.worldGen) {
			GameRegistry.registerWorldGenerator(new FemtocraftWorldGenerator());
		}
		// item = new Item(Configs.itemId);

		//blocks
		
		oreTitanium = new oreTitanium(Configs.oreTitaniumID, 0).setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		 LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		 OreDictionary.registerOre("oreTitanium", new ItemStack(oreTitanium));
		 
		 orePlatinum = new orePlatinum(Configs.orePlatinumID, 1).setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		 GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		 LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		 OreDictionary.registerOre("orePlatinum", new ItemStack(orePlatinum));
		 
		 oreThorium = new oreThorium(Configs.oreThoriumID, 2).setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreThorium, "oreThorium");
		 LanguageRegistry.addName(oreThorium, "Thorium Ore");
		 OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));
		 
		 oreFarenite = new oreFarenite(Configs.oreFareniteID, 3).setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		 GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		 LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		 OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));
		 
		 microStone = new microStone(Configs.microStoneID, 5).setHardness(6.0f).setStepSound(Block.soundMetalFootstep).setResistance(9f);
		 GameRegistry.registerBlock(microStone, "microStone");
		 LanguageRegistry.addName(microStone,  "Microstone");
		 
		 nanoStone = new microStone(Configs.nanoStoneID, 4).setHardness(7.0f).setStepSound(Block.soundMetalFootstep).setResistance(12f);
		 GameRegistry.registerBlock(nanoStone, "nanoStone");
		 LanguageRegistry.addName(nanoStone, "Nanostone");
		 
		 femtoStone = new femtoStone(Configs.femtoStoneID, 6).setHardness(8.0f).setStepSound(Block.soundMetalFootstep).setResistance(15f);
		 GameRegistry.registerBlock(femtoStone, "femtoStone");
		 LanguageRegistry.addName(femtoStone, "Femtostone");
		 
		 unidentifiedAlloy = new unidentifiedAlloy(Configs.unidentifiedAlloyID, 7).setBlockUnbreakable().setStepSound(Block.soundMetalFootstep).setResistance(20f);
		 GameRegistry.registerBlock(unidentifiedAlloy, "unidentifiedAlloy");
		 LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");
		 
		 //items
		 
		 ingotTitanium = new ingotTitanium(Configs.ingotTitaniumID);
		 LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		 OreDictionary.registerOre("ingotTitanium", new ItemStack(ingotTitanium));
		
		 ingotPlatinum = new ingotPlatinum(Configs.ingotPlatinumID);
		 LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		 OreDictionary.registerOre("ingotPlatinum", new ItemStack(ingotPlatinum));
		 
		 ingotThorium = new ingotThorium(Configs.ingotThoriumID);
		 LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		 OreDictionary.registerOre("ingotThorium", new ItemStack(ingotThorium));
		 
		 ingotFarenite = new ingotFarenite(Configs.ingotFareniteID);
		 LanguageRegistry.addName(ingotFarenite, "Farenite");
		 OreDictionary.registerOre("ingotFarenite", new ItemStack(ingotFarenite));
		 
		 registerRecipes();
		 
		// GameRegistry.registerTileEntity(TileEntity.class, "myTile");

		// GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});

		// EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this, 32, 10, true)
	}

	@PostInit
	public void postInit(FMLPostInitializationEvent event) {

	}
	
	private void registerRecipes() {
		GameRegistry.addSmelting(oreTitanium.blockID, new ItemStack(ingotTitanium), 0.1f);
		GameRegistry.addSmelting(orePlatinum.blockID, new ItemStack(ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(oreThorium.blockID, new ItemStack(ingotThorium), 0.1f);
	}

}
