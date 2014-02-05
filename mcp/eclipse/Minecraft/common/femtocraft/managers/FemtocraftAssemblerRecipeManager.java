package femtocraft.managers;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftConfigs;
import femtocraft.FemtocraftUtils;
import femtocraft.research.TechLevel;

/**
 * 
 * @author chris
 *
 * @category Manager
 * 
 * @info
 * This manager is responsible for all Femtocraft AssemblerRecipes.  All Assembler/Dissassemblers look to this manager for recipe lookup.
 * Recipes can be specified to only be disassemble-able, or only reassemble-able.  Dissassemblers simply break down items, Reassembles must use
 * schematics to specify the recipe to follow.
 * <br>
 * All recipes are ordered according to their signature in the inventory.  The entire 9 slots are used for the input signature.  ItemStack stackSize
 * does not matter for ordering.  Exceptions will be thrown when attempting to add recipes when their signature is already associated with a recipe (no check
 * is performed to see if the recipes are actually equal or not.)  When reconstructing, items must conform to the input signature, and all 9 slots are important.
 * Slots that are null in the recipe must not contain any items, and vice versa.  This will be separately enforced in the schematic-creating TileEntities, but it
 * it is also stated here for reference.
 * 
 */
public class FemtocraftAssemblerRecipeManager {
	public static class AssemblerRecipeFoundException extends Exception
	{
		public String errMsg;
		
		public AssemblerRecipeFoundException(String message)
		{
			errMsg = message;
		}
	}
	
	private SortedMap<ItemStack[], FemtocraftAssemblerRecipe> inputToRecipeMap;
	private SortedMap<ItemStack, FemtocraftAssemblerRecipe> outputToRecipeMap;
	
	public FemtocraftAssemblerRecipeManager()
	{
		inputToRecipeMap = new TreeMap<ItemStack[], FemtocraftAssemblerRecipe>(new AssemblerInputComparator());
		outputToRecipeMap = new TreeMap<ItemStack, FemtocraftAssemblerRecipe>( new AssemblerOutputComparator());
		
		registerRecipes();
	}
	
	private void registerRecipes()
	{
		registerFemtoDecompositionRecipes();
		registerNanoDecompositionRecipes();
		registerMicroDecompositionRecipes();
		registerMacroDecompositionRecipes();
		
		registerFemtocraftAssemblerRecipes();
	}
	
	private void registerFemtoDecompositionRecipes()
	{
		try
		{
		if(configRegisterRecipe("Crystallite"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Crystallite), TechLevel.FEMTO, null));	//Crystallite
		if(configRegisterRecipe("Mineralite"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Mineralite), TechLevel.FEMTO, null));	//Mineralite
		if(configRegisterRecipe("Metallite"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Metallite), TechLevel.FEMTO, null));	//Metallite
		if(configRegisterRecipe("Faunite"))			addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Faunite), TechLevel.FEMTO, null));	//Faunite
		if(configRegisterRecipe("Electrite"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Electrite), TechLevel.FEMTO, null));	//Electrite
		if(configRegisterRecipe("Florite"))			addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Florite), TechLevel.FEMTO, null));	//Florite
		}
		catch(AssemblerRecipeFoundException e)
		{
			Femtocraft.logger.log(Level.SEVERE, e.errMsg);
			Femtocraft.logger.log(Level.SEVERE, "Femtocraft failed to load Femto-tier Assembler Recipes!");
		}
	}
	
	private void registerNanoDecompositionRecipes()
	{
		try
		{
		if(configRegisterRecipe("MicroCrystal"))
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{	new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2), 
																			new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), 
																			new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2)}, 
																			2, new ItemStack(Femtocraft.MicroCrystal), TechLevel.NANO, null));	//MicroCrystal
		
		if(configRegisterRecipe("ProteinChain"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Mineralite),new ItemStack(Femtocraft.Faunite),null,null,null}, 2, new ItemStack(Femtocraft.ProteinChain), TechLevel.NANO, null));	//ProteinChain
		if(configRegisterRecipe("NerveCluster"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Electrite),new ItemStack(Femtocraft.Faunite),null,null,null}, 2, new ItemStack(Femtocraft.NerveCluster), TechLevel.NANO, null));	//NerveCluster
		if(configRegisterRecipe("ConductiveAlloy"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Electrite),new ItemStack(Femtocraft.Electrite),null,new ItemStack(Femtocraft.Metallite),null}, 2, new ItemStack(Femtocraft.ConductiveAlloy), TechLevel.NANO, null));	//ConductiveAlloy
		if(configRegisterRecipe("MetalComposite"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Mineralite),null,new ItemStack(Femtocraft.Metallite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Mineralite),null}, 2, new ItemStack(Femtocraft.MetalComposite), TechLevel.NANO, null));	//MetalComposite
		if(configRegisterRecipe("FibrousStrand"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite),null, new ItemStack(Femtocraft.Mineralite),null,null,null}, 2, new ItemStack(Femtocraft.FibrousStrand), TechLevel.NANO, null));	//FibrousStrand
		if(configRegisterRecipe("MineralLattice"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Mineralite),null, new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.MineralLattice), TechLevel.NANO, null));	//MineralLattice
		if(configRegisterRecipe("FungalSpores"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Crystallite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.FungalSpores), TechLevel.NANO, null));	//FungalSpores
		if(configRegisterRecipe("IonicChunk"))			addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Mineralite),new ItemStack(Femtocraft.Electrite),null,null,null}, 2, new ItemStack(Femtocraft.IonicChunk), TechLevel.NANO, null));	//IonicChunk
		if(configRegisterRecipe("ReplicatingMaterial"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.ReplicatingMaterial), TechLevel.NANO, null));	//ReplicatingMaterial
		if(configRegisterRecipe("SpinyFilament"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Crystallite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.SpinyFilament), TechLevel.NANO, null));	//SpinyFilament
		if(configRegisterRecipe("HardenedBulb"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Crystallite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.HardenedBulb), TechLevel.NANO, null));	//HardenedBulb
		if(configRegisterRecipe("MorphicChannel"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Florite),new ItemStack(Femtocraft.Electrite),null,null,null}, 2, new ItemStack(Femtocraft.MorphicChannel), TechLevel.NANO, null));	//MorphicChannel
		if(configRegisterRecipe("SynthesizedFiber"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.SynthesizedFiber), TechLevel.NANO, null));	//SynthesizedFiber
		if(configRegisterRecipe("OrganometallicPlate"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Faunite),null,new ItemStack(Femtocraft.Metallite),null}, 2, new ItemStack(Femtocraft.OrganometallicPlate), TechLevel.NANO, null));	//OrganometallicPlate
		}
		catch(AssemblerRecipeFoundException e)
		{
			Femtocraft.logger.log(Level.SEVERE, e.errMsg);
			Femtocraft.logger.log(Level.SEVERE, "Femtocraft failed to load Nano-tier Assembler Recipes!");
		}
	}
	
	private void registerMicroDecompositionRecipes()
	{
		try
		{
			if(configRegisterRecipe("Stone"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.MineralLattice),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.stone), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Grass"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FibrousStrand),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.grass), TechLevel.MICRO, null));
			if(configRegisterRecipe("Dirt"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, new ItemStack(Femtocraft.MineralLattice),null,null,null,null,null,null}, 1, new ItemStack(Block.dirt), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Cobblestone"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.MineralLattice),null,null,null,null,null,null,null}, 1, new ItemStack(Block.cobblestone), TechLevel.MICRO, null));	
			if(configRegisterRecipe("WoodPlank"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FibrousStrand),new ItemStack(Femtocraft.FibrousStrand),null,null,null,null,null,null,null}, 1, new ItemStack(Block.planks), TechLevel.MICRO, null));
			if(configRegisterRecipe("Sapling"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FibrousStrand),null,null,new ItemStack(Femtocraft.FibrousStrand),null,null,new ItemStack(Femtocraft.FibrousStrand),null,null}, 1, new ItemStack(Block.sapling), TechLevel.MICRO, null));
			if(configRegisterRecipe("Sand"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.MicroCrystal),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.sand), TechLevel.MICRO, null));
			if(configRegisterRecipe("Leaves"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.FibrousStrand),null,null,null,null,null,null,null}, 1, new ItemStack(Block.leaves), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Cobweb"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.SpinyFilament),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.web), TechLevel.MICRO, null));	
			if(configRegisterRecipe("DeadBush"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, new ItemStack(Femtocraft.FibrousStrand),null,null,null,null,null,null}, 1, new ItemStack(Block.deadBush), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Dandelion"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FibrousStrand),null,null,new ItemStack(Femtocraft.MorphicChannel),null,null,null,null,null}, 1, new ItemStack(Block.plantYellow), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Rose"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.FibrousStrand),null,null,new ItemStack(Femtocraft.MorphicChannel),null,null,null,null}, 1, new ItemStack(Block.plantRed), TechLevel.MICRO, null));	
			if(configRegisterRecipe("MushroomBrown"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FungalSpores),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.mushroomCapBrown), TechLevel.MICRO, null));
			if(configRegisterRecipe("MushroomRed"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.FungalSpores),null,null,null,null,null,null,null}, 1, new ItemStack(Block.mushroomRed), TechLevel.MICRO, null));	
			if(configRegisterRecipe("MossStone"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.MineralLattice),new ItemStack(Femtocraft.FungalSpores),null,null,null,null,null,null,null}, 1, new ItemStack(Block.cobblestoneMossy), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Obsidian"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.HardenedBulb),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.obsidian), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Ice"))			addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, new ItemStack(Femtocraft.MicroCrystal),null,null,null,null,null,null,null}, 1, new ItemStack(Block.ice), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Cactus"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.SpinyFilament),null,null,new ItemStack(Femtocraft.FibrousStrand),null,null,null,null,null}, 1, new ItemStack(Block.cactus), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Pumpkin"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.FibrousStrand),new ItemStack(Femtocraft.MorphicChannel),null,null,null,null,null,null,null}, 1, new ItemStack(Block.pumpkin), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Netherrack"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.MineralLattice),null,null,null,null,null}, 1, new ItemStack(Block.netherrack), TechLevel.MICRO, null));	
			if(configRegisterRecipe("SoulSand"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.MicroCrystal),new ItemStack(Femtocraft.IonicChunk),null,null,null,null,null,null,null}, 1, new ItemStack(Block.slowSand), TechLevel.MICRO, null));	
			if(configRegisterRecipe("Glowstone"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Femtocraft.MineralLattice),null,null,null,null,null,null,null,null}, 1, new ItemStack(Block.stone), TechLevel.MICRO, null));	
		}
		catch(AssemblerRecipeFoundException e)
		{
			Femtocraft.logger.log(Level.SEVERE, e.errMsg);
			Femtocraft.logger.log(Level.SEVERE, "Femtocraft failed to load Nano-tier Assembler Recipes!");
		}
	}
	
	private void registerMacroDecompositionRecipes()
	{
		
	}
	
	public void registerDefaultRecipes()
	{
		//Does not use Ore Dictionary values - this is why things like crafting tables don't work.
		
		
		Femtocraft.logger.log(Level.WARNING, "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take awhile ._.");
		
		List<IRecipe> recipes = CraftingManager.getInstance().getRecipeList();
		List<ShapelessRecipes> shapelessRecipes = new ArrayList<ShapelessRecipes>();
		List<ShapedOreRecipe> shapedOre = new ArrayList<ShapedOreRecipe>();
		List<ShapelessOreRecipe> shapelessOre = new ArrayList<ShapelessOreRecipe>();
		
		Femtocraft.logger.log(Level.WARNING, "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.");
		for(IRecipe recipe : recipes)
		{
			if(getRecipe(recipe.getRecipeOutput()) != null)
			{
				Femtocraft.logger.log(Level.CONFIG, "Assembler recipe already found for " + recipe.getRecipeOutput().getDisplayName() + ".");
				continue;
			}
			
			if(recipe instanceof ShapelessRecipes)
			{
				shapelessRecipes.add((ShapelessRecipes)recipe);
				continue;
			}
			
			if(recipe instanceof ShapedOreRecipe)
			{
				shapedOre.add((ShapedOreRecipe) recipe);
			}
			
			if(recipe instanceof ShapelessOreRecipe)
			{
				shapelessOre.add((ShapelessOreRecipe) recipe);
			}
			
			if(!(recipe instanceof ShapedRecipes))
			{
				//When I figure out how to do the other recipes...WITHOUT iterating through every conceivable combination of items and damages in the scope of minecraft
				//It will go here.
				continue;
			}
			ShapedRecipes sr = (ShapedRecipes)recipe;
			
			Femtocraft.logger.log(Level.CONFIG, "Attempting to register shaped assembler recipe for " + sr.getRecipeOutput().getDisplayName() + ".");
			boolean valid = registerShapedRecipe(sr.recipeItems, sr.getRecipeOutput(), sr.recipeWidth, sr.recipeHeight);
			if(!valid)
			{
				Femtocraft.logger.log(Level.WARNING, "Failed to register shaped assembler recipe for " + sr.getRecipeOutput().getDisplayName()+"!");
			}
			else
			{
				Femtocraft.logger.log(Level.CONFIG, "Loaded Vanilla Minecraft shaped recipe as assembler recipe for " + sr.getRecipeOutput().getDisplayName()+".");
			}
		}
		
		Femtocraft.logger.log(Level.WARNING, "Registering shaped ore recipes from Forge.");
		for(ShapedOreRecipe orecipe : shapedOre)
		{
			Femtocraft.logger.log(Level.CONFIG, "Attempting to register shaped assembler recipe for " + orecipe.getRecipeOutput().getDisplayName() + ".");
			boolean valid = registerShapedOreRecipe(orecipe.getInput(), orecipe.getRecipeOutput());
			if(!valid)
			{
				Femtocraft.logger.log(Level.WARNING, "Failed to register shaped assembler recipe for " + orecipe.getRecipeOutput().getDisplayName()+"!");
			}
			else
			{
				Femtocraft.logger.log(Level.CONFIG, "LoadedForge shaped ore recipe as assembler recipe for " + orecipe.getRecipeOutput().getDisplayName()+".");
			}
		}
		
		Femtocraft.logger.log(Level.WARNING, "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.");
		for(ShapelessRecipes recipe : shapelessRecipes)
		{
			if(getRecipe(recipe.getRecipeOutput()) != null)
			{
				Femtocraft.logger.log(Level.CONFIG, "Assembler recipe already found for " + recipe.getRecipeOutput().getDisplayName() + ".");
				continue;
			}
			
			Femtocraft.logger.log(Level.CONFIG, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput().getDisplayName() + ".");
			
			boolean valid = registerShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput());
			
			if(!valid)
			{
				Femtocraft.logger.log(Level.WARNING, "Failed to register shapeless assembler recipe for " + recipe.getRecipeOutput().getDisplayName()+"!");
				Femtocraft.logger.log(Level.WARNING, "I have no clue how this would happen...as the search space is literally thousands of configurations.  Sorry for the wait.");
			}
			else
			{
				Femtocraft.logger.log(Level.CONFIG, "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + " + recipe.getRecipeOutput().getDisplayName()+".");
			}
		}
		
		Femtocraft.logger.log(Level.WARNING, "Registering shapeless ore recipes from Forge.");
		for(ShapelessOreRecipe recipe : shapelessOre)
		{
			if(getRecipe(recipe.getRecipeOutput()) != null)
			{
				Femtocraft.logger.log(Level.CONFIG, "Assembler recipe already found for " + recipe.getRecipeOutput().getDisplayName() + ".");
				continue;
			}
			
			Femtocraft.logger.log(Level.CONFIG, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput().getDisplayName() + ".");
			
			boolean valid = registerShapelessOreRecipe(recipe.getInput(), recipe.getRecipeOutput());
			
			if(!valid)
			{
				Femtocraft.logger.log(Level.WARNING, "Failed to register shapeless ore assembler recipe for " + recipe.getRecipeOutput().getDisplayName()+"!");
				Femtocraft.logger.log(Level.WARNING, "I have no clue how this would happen...as the search space is literally thousands of configurations.  Sorry for the wait.");
			}
			else
			{
				Femtocraft.logger.log(Level.CONFIG, "Loaded Forge shapeless ore recipe as assembler recipe for + " + recipe.getRecipeOutput().getDisplayName()+".");
			}
		}
	}
	
	private boolean registerShapedOreRecipe(Object[] recipeInput, ItemStack recipeOutput) {
		boolean done = false;
		int xoffset = 0;
		int yoffset = 0;
		while((!done) && (xoffset <= 3) && (yoffset<= 3))
		{
			ItemStack[] input = new ItemStack[9];
			Arrays.fill(input, null);
			for(int i = 0; (i < recipeInput.length) && (i < 9); i++)
			{
				try{
					ItemStack item;
					Object obj = recipeInput[i];
					
					if(obj instanceof ArrayList<?>)
					{
						item = ((ArrayList<ItemStack>)obj).get(0);
					}
					else
					{
						item = (ItemStack) obj;
					}
					input[i + xoffset + 3*yoffset] = item == null ? null : item.copy();
				}
				catch(ArrayIndexOutOfBoundsException e)
				{
					if(++xoffset > 3)
					{
						xoffset = 0;
						++yoffset;
					}
					
				}
			}
			
			ItemStack output = recipeOutput.copy();
			if(output.getItemDamage() == OreDictionary.WILDCARD_VALUE)
			{
				output.setItemDamage(0);
			}
			
			try
			{
				addReversableRecipe(new FemtocraftAssemblerRecipe(input, 0, output, TechLevel.MACRO, null));
				done = true;
			}
			catch(AssemblerRecipeFoundException e)
			{
				//Attempt to offset, while staying inside crafting grid
				if((++xoffset) > 3)
				{
					xoffset = 0;
					++yoffset;
				}
				done = false;
			}
		}
		
		return done;
	}

	private boolean registerShapedRecipe(ItemStack[] recipeItems, ItemStack recipeOutput, int recipeWidth, int recipeHeight)
	{
		boolean done = false;
		int xoffset = 0;
		int yoffset = 0;
		while((!done) && ((xoffset + recipeWidth) <= 3) && ((yoffset + recipeHeight) <= 3))
		{
			ItemStack[] input = new ItemStack[9];
			Arrays.fill(input, null);
			for(int i = 0; (i < recipeItems.length) && (i < 9); i++)
			{
				ItemStack item = recipeItems[i];
				input[i + xoffset + 3*yoffset] = item == null ? null : item.copy();
			}
			
			try
			{
				addReversableRecipe(new FemtocraftAssemblerRecipe(input, 0, recipeOutput.copy(), TechLevel.MACRO, null));
				done = true;
			}
			catch(AssemblerRecipeFoundException e)
			{
				//Attempt to offset, while staying inside crafting grid
				if((++xoffset + recipeWidth) > 3)
				{
					xoffset = 0;
					++yoffset;
				}
				done = false;
			}
		}
		
		return done;
	}
	
	private boolean registerShapelessOreRecipe(List recipeItems, ItemStack recipeOutput)
	{
		boolean valid = false;
		int[] slots = new int[recipeItems.size()];
		
		//Exhaustively find a configuration that works - this should NEVER have to go the full distance
		//but I don't want to half-ass the attempt in case there are MANY collisions
		int offset = 0;
		while(!valid && ((offset + recipeItems.size()) <= 9))
		{
			for(int i = 0; i < slots.length; ++i)
			{
				slots[i] = i;
			}
			
			while(!valid)
			{
				ItemStack[] input = new ItemStack[9];
				Arrays.fill(input, null);
				
				for(int i = 0; (i < slots.length) && (i < 9); ++i)
				{
					ItemStack item;
					Object obj = recipeItems.get(i);
					
					if(obj instanceof ArrayList<?>)
					{
						item = ((ArrayList<ItemStack>)obj).get(0);
					}
					else
					{
						item = (ItemStack) obj;
					}
					
					input[slots[i] + offset] = item == null ? null : item.copy();
				}
				
				ItemStack output = recipeOutput.copy();
				if(output.getItemDamage() == OreDictionary.WILDCARD_VALUE)
				{
					output.setItemDamage(0);
				}
				
				try
				{
					addReversableRecipe(new FemtocraftAssemblerRecipe(input, 0, output, TechLevel.MACRO, null));
					valid = true;
				}
				catch(AssemblerRecipeFoundException e)
				{
					//Permute the slots
					slots = permute(slots);
					
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	private boolean registerShapelessRecipe(List recipeItems, ItemStack recipeOutput)
	{
		boolean valid = false;
		int[] slots = new int[recipeItems.size()];
		
		//Exhaustively find a configuration that works - this should NEVER have to go the full distance
		//but I don't want to half-ass the attempt in case there are MANY collisions
		int offset = 0;
		while(!valid && ((offset + recipeItems.size()) <= 9))
		{
			for(int i = 0; i < slots.length; ++i)
			{
				slots[i] = i;
			}
			
			while(!valid)
			{
				ItemStack[] input = new ItemStack[9];
				Arrays.fill(input, null);
				
				for(int i = 0; (i < slots.length) && (i < 9); ++i)
				{
					ItemStack item = (ItemStack)recipeItems.get(i);
					input[slots[i] + offset] = item == null ? null : item.copy();
				}
				
				try
				{
					addReversableRecipe(new FemtocraftAssemblerRecipe(input, 0, recipeOutput.copy(), TechLevel.MACRO, null));
					valid = true;
				}
				catch(AssemblerRecipeFoundException e)
				{
					//Permute the slots
					slots = permute(slots);
					
					valid = false;
				}
			}
		}
		
		return valid;
	}
	
	private int[] permute(int[] slots)
	{
		int k = findHighestK(slots);
		int i = findHigherI(slots, k);
		
		//Switch k and i
		int prev = slots[k];
		slots[k] = slots[i];
		slots[i] = prev;
		
		//Reverse ordering of k+1 to end
		int remaining = (int) Math.ceil((slots.length - k + 1)/2.);
		for(int r = k + 1, n = 0; (r < slots.length) && (n < remaining); ++r, ++n)
		{
			int pr = slots[r];
			slots[r] = slots[slots.length - n - 1];
			slots[slots.length - n - 1] = pr;
		}
		
		return slots;
	}
	
	private int findHighestK(int[] slots)
	{
		int ret = 0;
		for(int i = 0; i < slots.length - 1; ++i)
		{
			if((slots[i] < slots[i + 1]) && (ret < i)) ret = i;
		}
		return ret;
	}
	
	private int findHigherI(int[] slots, int k)
	{
		int ret = 0;
		
		for(int i = 0; i < slots.length; ++i)
		{
			if((slots[k] < slots[i]) && (ret < i)) ret = i;
		}
		return ret;
	}
	
	private void registerFemtocraftAssemblerRecipes()
	{
		try
		{
			if(configRegisterRecipe("IronOre"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,null,new ItemStack(Femtocraft.deconstructedIron,2),null,null,null,null}, 0, new ItemStack(Block.oreIron), TechLevel.MACRO, null));
			if(configRegisterRecipe("GoldOre"))		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,null,new ItemStack(Femtocraft.deconstructedGold,2),null,null,null,null}, 0, new ItemStack(Block.oreGold), TechLevel.MACRO, null));
			if(configRegisterRecipe("TitaniumOre"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,null,new ItemStack(Femtocraft.deconstructedTitanium,2),null,null,null,null}, 0, new ItemStack(Femtocraft.oreTitanium), TechLevel.MACRO, null));
			if(configRegisterRecipe("ThoriumOre"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,null,new ItemStack(Femtocraft.deconstructedThorium,2),null,null,null,null}, 0, new ItemStack(Femtocraft.oreThorium), TechLevel.MACRO, null));
			if(configRegisterRecipe("PlatinumOre"))	addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,null,new ItemStack(Femtocraft.deconstructedPlatinum,2),null,null,null,null}, 0, new ItemStack(Femtocraft.orePlatinum), TechLevel.MACRO, null));
		
			addDecompositionRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{new ItemStack(Item.paper, 3),null,null,null,null,null,null,null,null}, 0, new ItemStack(Femtocraft.paperSchematic), TechLevel.MACRO, null));
			
		}
		catch(AssemblerRecipeFoundException e)
		{
			Femtocraft.logger.log(Level.SEVERE, e.errMsg);
			Femtocraft.logger.log(Level.SEVERE, "Femtocraft failed to load Femtocraft Assembler Recipes!");
		}
	}
	
	private boolean configRegisterRecipe(String name)
	{
		boolean register = false;
		boolean found = false;
		
		Field[] fields = FemtocraftConfigs.class.getFields();
		for(Field field : fields)
		{
			if(field.getName().equalsIgnoreCase("recipe"+name))
			{
				found = true;
				
				try
				{
					register = field.getBoolean(null);
					
					Level logLevel = FemtocraftConfigs.silentRecipeLoadAlerts ? Level.CONFIG : Level.INFO;
					
					if(register)
					{
						Femtocraft.logger.log(logLevel, "Loading default AssemblerRecipe for " + name + ".");
					}
					else
					{
						Femtocraft.logger.log(logLevel, "Not loading AssemblerRecipe for " + name + ".");
					}
				}
				catch(Exception e)
				{
					Femtocraft.logger.log(Level.WARNING, "Exception - " + e.getLocalizedMessage() + " thrown while loading AssemblerRecipe " + name + ".");
				}
			}
		}
		
		if(!found)
			Femtocraft.logger.log(Level.WARNING, "No configuration option for AssemblerRecipe " + name + " has been found.  Please report this to Femtocraft developers immediately.");
		
		return register;
	}
	
	private void testRecipes()
	{
		FemtocraftAssemblerRecipe test = getRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null});
		Femtocraft.logger.log(Level.WARNING, "Recipe " + (test != null ? "found" : "not found") + ".");
		if(test != null)
			Femtocraft.logger.log(Level.WARNING, "Output " + (test.output.isItemEqual(new ItemStack(Femtocraft.Florite)) ? "matches" : "does not match") + ".");
		
		test = getRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null});
		Femtocraft.logger.log(Level.WARNING, "Recipe " + (test != null ? "found" : "not found") + ".");
		
		test = getRecipe(new ItemStack(Femtocraft.Florite));
		Femtocraft.logger.log(Level.WARNING, "Recipe " + (test != null ? "found" : "not found") + ".");
	}
	
	public void addReversableRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException, AssemblerRecipeFoundException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		ItemStack[] normalArray = normalizedInput(recipe);
		if(normalArray == null) return;
		
		ItemStack normal = normalizedOutput(recipe);
		
		checkDecomposition(normal, recipe);
		checkRecomposition(normalArray, recipe);
		
		inputToRecipeMap.put(normalArray, recipe);
		outputToRecipeMap.put(normal, recipe);
	}
	
	public void addRecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException, AssemblerRecipeFoundException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return;
		
		checkRecomposition(normal, recipe);
		
		inputToRecipeMap.put(normal, recipe);
	}
	
	public void addDecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException, AssemblerRecipeFoundException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		ItemStack normal = normalizedOutput(recipe);
		
		checkDecomposition(normal, recipe);
		
		outputToRecipeMap.put(normal, recipe);
	}
	
	private void checkDecomposition(ItemStack normal, FemtocraftAssemblerRecipe recipe) throws AssemblerRecipeFoundException
	{
		if(outputToRecipeMap.containsKey(normal)) 
		{
			throw new AssemblerRecipeFoundException("AssemblerRecipe found for Decomposition of item - " + recipe.output.getDisplayName() + ".");
		}
	}
	
	private void checkRecomposition(ItemStack[] normal, FemtocraftAssemblerRecipe recipe) throws AssemblerRecipeFoundException
	{
		if(inputToRecipeMap.containsKey(normal)) 
		{
			throw new AssemblerRecipeFoundException("AssemblerRecipe found for Recomposition of item - " + recipe.output.getDisplayName() + ".");
		}
	}
	
	public boolean removeAnyRecipe(FemtocraftAssemblerRecipe recipe)
	{
		return removeDecompositionRecipe(recipe) || removeRecompositionRecipe(recipe);
	}
	
	public boolean removeReversableRecipe(FemtocraftAssemblerRecipe recipe)
	{
		return removeDecompositionRecipe(recipe) && removeRecompositionRecipe(recipe);
	}
	
	public boolean removeRecompositionRecipe(FemtocraftAssemblerRecipe recipe)
	{
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return false;
		return (inputToRecipeMap.remove(normal) != null);
	}
	
	public boolean removeDecompositionRecipe(FemtocraftAssemblerRecipe recipe)
	{
		return (outputToRecipeMap.remove(normalizedOutput(recipe)) != null);
	}
	
	public boolean canCraft(ItemStack[] input)
	{
		if(input.length != 9) return false;
		FemtocraftAssemblerRecipe recipe = getRecipe(input);
		if(recipe == null) return false;
		
		for(int i = 0; i < 9; ++i)
		{
			ItemStack rec = recipe.input[i];
			if(input[i] == null || rec == null) continue;
			
			if(input[i].stackSize < input[i].stackSize) return false;
			if(FemtocraftUtils.compareItem(rec, input[i]) != 0) return false;
		}
		
		return true;
	}
	
	public boolean canCraft(ItemStack input)
	{
		FemtocraftAssemblerRecipe recipe = getRecipe(input);
		if(recipe == null) return false;
		
		if(input.stackSize < recipe.output.stackSize) return false;
		if(FemtocraftUtils.compareItem(recipe.output, input) != 0) return false;
		
		return true;
	}
	
	public FemtocraftAssemblerRecipe getRecipe(ItemStack[] input)
	{
		ItemStack[] normal = normalizedInput(input);
		if(normal == null) return null;
		return inputToRecipeMap.get(normal);
	}
	
	public FemtocraftAssemblerRecipe getRecipe(ItemStack output)
	{
		return outputToRecipeMap.get(normalizedItem(output));
	}
	
	public boolean hasResearchedRecipe(FemtocraftAssemblerRecipe recipe, String username)
	{
		return Femtocraft.researchManager.playerHasResearchedTechnology(username, recipe.tech);
	}
	
	private ItemStack normalizedOutput(FemtocraftAssemblerRecipe recipe)
	{
		return normalizedItem(recipe.output);
	}
	
	private ItemStack[] normalizedInput(FemtocraftAssemblerRecipe recipe)
	{
		return normalizedInput(recipe.input);
	}
	
	private ItemStack[] normalizedInput(ItemStack[] input)
	{
		if(input.length != 9) return null;
		
		ItemStack[] ret = new ItemStack[9];
		
		for(int i = 0; i < 9; i++)
		{
			ret[i] = normalizedItem(input[i]);
		}
		return ret;
	}
	
	private ItemStack normalizedItem(ItemStack original)
	{
		if(original == null) return null;
		return new ItemStack(original.itemID, 1, original.getItemDamage());
	}
}
