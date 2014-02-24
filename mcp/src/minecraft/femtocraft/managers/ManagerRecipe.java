package femtocraft.managers;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.ShapedOreRecipe;
import cpw.mods.fml.common.registry.GameRegistry;
import femtocraft.Femtocraft;
import femtocraft.managers.assembler.ManagerAssemblerRecipe;

public class ManagerRecipe {
	public static ManagerAssemblerRecipe assemblyRecipes = new ManagerAssemblerRecipe();

	public ManagerRecipe() {
		registerRecipes();
	}

	private void registerRecipes() {
		GameRegistry.addSmelting(Femtocraft.oreTitanium.blockID, new ItemStack(
				Femtocraft.ingotTitanium), 0.1f);
		GameRegistry.addSmelting(Femtocraft.orePlatinum.blockID, new ItemStack(
				Femtocraft.ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(Femtocraft.oreThorium.blockID, new ItemStack(
				Femtocraft.ingotThorium), 0.1f);
		GameRegistry.addSmelting(Femtocraft.deconstructedIron.itemID,
				new ItemStack(Item.ingotIron), 0.1f);
		GameRegistry.addSmelting(Femtocraft.deconstructedGold.itemID,
				new ItemStack(Item.ingotGold), 0.1f);
		GameRegistry.addSmelting(Femtocraft.deconstructedTitanium.itemID,
				new ItemStack(Femtocraft.ingotTitanium), 0.1f);
		GameRegistry.addSmelting(Femtocraft.deconstructedThorium.itemID,
				new ItemStack(Femtocraft.ingotThorium), 0.1f);
		GameRegistry.addSmelting(Femtocraft.deconstructedPlatinum.itemID,
				new ItemStack(Femtocraft.ingotPlatinum), 0.1f);
		GameRegistry.addSmelting(Femtocraft.ingotTitanium.itemID,
				new ItemStack(Femtocraft.ingotTemperedTitanium), 0.1f);

		GameRegistry.addSmelting(Femtocraft.primedBoard.itemID, new ItemStack(
				Femtocraft.dopedBoard), 0.1f);

		GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.primedBoard),
				"#", "$", '#', Femtocraft.conductivePowder, '$',
				Femtocraft.board);
		GameRegistry.addShapedRecipe(
				new ItemStack(Femtocraft.paperSchematic, 3), "###", "###",
				"###", '#', Item.paper);
		GameRegistry.addShapedRecipe(new ItemStack(Femtocraft.board), "###",
				'#', Item.stick);
		GameRegistry.addShapedRecipe(
				new ItemStack(Femtocraft.microCircuitBoard), "#", "$", '#',
				Femtocraft.spoolGold, '$', Femtocraft.dopedBoard);

		CraftingManager
				.getInstance()
				.getRecipeList()
				.add(new ShapedOreRecipe(new ItemStack(Femtocraft.spool),
						"# #", "#-#", "# #", '#', "plankWood", '-', "stickWood"));

		GameRegistry
				.addShapedRecipe(new ItemStack(Femtocraft.spoolGold, 8), "###",
						"#-#", "###", '#', Item.ingotGold, '-',
						Femtocraft.spool);

		GameRegistry.addShapelessRecipe(new ItemStack(
				Femtocraft.conductivePowder, 2), new ItemStack(
				Femtocraft.ingotFarenite), new ItemStack(Item.dyePowder, 1, 4));

		GameRegistry.addShapedRecipe(
				new ItemStack(Femtocraft.itemMicroPlating), "TCT", "CPC",
				"TCT", 'T', Femtocraft.ingotTemperedTitanium, 'C',
				Femtocraft.microCircuitBoard, 'P', Femtocraft.conductivePowder);

	}
}
