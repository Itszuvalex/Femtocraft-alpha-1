package femtocraft.managers;

import java.lang.reflect.Field;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		
	}
	
	private void registerMacroDecompositionRecipes()
	{
		
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
		
		checkDecomposition(normalArray, recipe);
		checkRecomposition(normal, recipe);
		
		inputToRecipeMap.put(normalArray, recipe);
		outputToRecipeMap.put(normal, recipe);
	}
	
	public void addDecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException, AssemblerRecipeFoundException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return;
		
		checkDecomposition(normal, recipe);
		
		inputToRecipeMap.put(normal, recipe);
	}
	
	public void addRecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException, AssemblerRecipeFoundException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		ItemStack normal = normalizedOutput(recipe);
		
		checkRecomposition(normal, recipe);
		
		outputToRecipeMap.put(normal, recipe);
	}
	
	private void checkDecomposition(ItemStack[] normal, FemtocraftAssemblerRecipe recipe) throws AssemblerRecipeFoundException
	{
		if(inputToRecipeMap.containsKey(normal)) 
		{
			throw new AssemblerRecipeFoundException("AssemblerRecipe found for Decomposition of item - " + recipe.output.getDisplayName() + ".");
		}
	}
	
	private void checkRecomposition(ItemStack normal, FemtocraftAssemblerRecipe recipe) throws AssemblerRecipeFoundException
	{
		if(outputToRecipeMap.containsKey(normal)) 
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
	
	public boolean removeDecompositionRecipe(FemtocraftAssemblerRecipe recipe)
	{
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return false;
		return (inputToRecipeMap.remove(normal) != null);
	}
	
	public boolean removeRecompositionRecipe(FemtocraftAssemblerRecipe recipe)
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
