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
		
		oreTitanium = new oreTitanium(Configs.oreTitaniumID, 0).setBlockName("oreTitanium").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreTitanium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreTitanium, "oreTitanium");
		 LanguageRegistry.addName(oreTitanium, "Titanium Ore");
		 OreDictionary.registerOre("oreTitanium", new ItemStack(oreTitanium));
		 
		 orePlatinum = new orePlatinum(Configs.orePlatinumID, 1).setBlockName("orePlatinum").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(orePlatinum, "pickaxe", 2);
		 GameRegistry.registerBlock(orePlatinum, "orePlatinum");
		 LanguageRegistry.addName(orePlatinum, "Platinum Ore");
		 OreDictionary.registerOre("orePlatinum", new ItemStack(orePlatinum));
		 
		 oreThorium = new oreThorium(Configs.oreThoriumID, 2).setBlockName("oreThorium").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreThorium, "pickaxe", 2);
		 GameRegistry.registerBlock(oreThorium, "oreThorium");
		 LanguageRegistry.addName(oreThorium, "Thorium Ore");
		 OreDictionary.registerOre("oreThorium", new ItemStack(oreThorium));
		 
		 oreFarenite = new oreFarenite(Configs.oreFareniteID, 3).setBlockName("oreFarenite").setHardness(3.0f).setStepSound(Block.soundStoneFootstep).setResistance(1f);
		 MinecraftForge.setBlockHarvestLevel(oreFarenite, "pickaxe", 2);
		 GameRegistry.registerBlock(oreFarenite, "oreFarenite");
		 LanguageRegistry.addName(oreFarenite, "Farenite Ore");
		 OreDictionary.registerOre("oreFarenite", new ItemStack(oreFarenite));
		 
		 //items
		 
		 ingotTitanium = new ingotTitanium(Configs.ingotTitaniumID).setIconIndex(0).setItemName("ingotTitanium");
		 LanguageRegistry.addName(ingotTitanium, "Titanium Ingot");
		 OreDictionary.registerOre("ingotTitanium", new ItemStack(ingotTitanium));
		
		 ingotPlatinum = new ingotPlatinum(Configs.ingotPlatinumID).setIconIndex(1).setItemName("ingotPlatinum");
		 LanguageRegistry.addName(ingotPlatinum, "Platinum Ingot");
		 OreDictionary.registerOre("ingotPlatinum", new ItemStack(ingotPlatinum));
		 
		 ingotThorium = new ingotThorium(Configs.ingotThoriumID).setIconIndex(2).setItemName("ingotThorium");
		 LanguageRegistry.addName(ingotThorium, "Thorium Ingot");
		 OreDictionary.registerOre("ingotThorium", new ItemStack(ingotThorium));
		 
		 ingotFarenite = new ingotFarenite(Configs.ingotFareniteID).setIconIndex(3).setItemName("Farenite");
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
