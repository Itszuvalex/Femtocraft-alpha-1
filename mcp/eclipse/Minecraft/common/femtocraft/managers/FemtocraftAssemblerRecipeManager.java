package femtocraft.managers;

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.research.TechLevel;

public class FemtocraftAssemblerRecipeManager {
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
		//Femto-tier components
		registerFemtoDecompositionRecipes();
		
		registerNanoDecompositionRecipes();
		
		//Micro-tier components
		
//		testRecipes();
	}
	
	private void registerFemtoDecompositionRecipes()
	{
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Crystallite), TechLevel.FEMTO, null));	//Crystallite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Mineralite), TechLevel.FEMTO, null));	//Mineralite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Metallite), TechLevel.FEMTO, null));	//Metallite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Faunite), TechLevel.FEMTO, null));	//Faunite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Electrite), TechLevel.FEMTO, null));	//Electrite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Florite), TechLevel.FEMTO, null));	//Florite
	}
	
	private void registerNanoDecompositionRecipes()
	{
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{	new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2), 
																			new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), 
																			new ItemStack(Femtocraft.Crystallite, 2), new ItemStack(Femtocraft.Electrite, 2), new ItemStack(Femtocraft.Crystallite, 2)}, 
																			2, new ItemStack(Femtocraft.MicroCrystal), TechLevel.NANO, null));	//MicroCrystal
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Mineralite),new ItemStack(Femtocraft.Faunite),null,null,null}, 2, new ItemStack(Femtocraft.ProteinChain), TechLevel.NANO, null));	//ProteinChain
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Electrite),new ItemStack(Femtocraft.Faunite),null,null,null}, 2, new ItemStack(Femtocraft.NerveCluster), TechLevel.NANO, null));	//NerveCluster
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Electrite),new ItemStack(Femtocraft.Electrite),null,new ItemStack(Femtocraft.Metallite),null}, 2, new ItemStack(Femtocraft.ConductiveAlloy), TechLevel.NANO, null));	//ConductiveAlloy
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Mineralite),null,new ItemStack(Femtocraft.Metallite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Mineralite),null}, 2, new ItemStack(Femtocraft.MetalComposite), TechLevel.NANO, null));	//MetalComposite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite),null, new ItemStack(Femtocraft.Mineralite),null,null,null}, 2, new ItemStack(Femtocraft.FibrousStrand), TechLevel.NANO, null));	//FibrousStrand
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Mineralite),null, new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.MineralLattice), TechLevel.NANO, null));	//MineralLattice
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Crystallite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.FungalSpores), TechLevel.NANO, null));	//FungalSpores
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Mineralite),new ItemStack(Femtocraft.Electrite),null,null,null}, 2, new ItemStack(Femtocraft.IonicChunk), TechLevel.NANO, null));	//IonicChunk
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.ReplicatingMaterial), TechLevel.NANO, null));	//ReplicatingMaterial
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Crystallite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.SpinyFilament), TechLevel.NANO, null));	//SpinyFilament
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Crystallite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Crystallite),null,null,null}, 2, new ItemStack(Femtocraft.HardenedBulb), TechLevel.NANO, null));	//HardenedBulb
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Electrite), new ItemStack(Femtocraft.Florite),new ItemStack(Femtocraft.Electrite),null,null,null}, 2, new ItemStack(Femtocraft.MorphicChannel), TechLevel.NANO, null));	//MorphicChannel
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,null,null,new ItemStack(Femtocraft.Florite), new ItemStack(Femtocraft.Metallite),new ItemStack(Femtocraft.Florite),null,null,null}, 2, new ItemStack(Femtocraft.SynthesizedFiber), TechLevel.NANO, null));	//SynthesizedFiber
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null,new ItemStack(Femtocraft.Metallite),null,new ItemStack(Femtocraft.Faunite), new ItemStack(Femtocraft.Faunite),new ItemStack(Femtocraft.Faunite),null,new ItemStack(Femtocraft.Metallite),null}, 2, new ItemStack(Femtocraft.OrganometallicPlate), TechLevel.NANO, null));	//OrganometallicPlate
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
	
	public void addReversableRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return;
		inputToRecipeMap.put(normal, recipe);
		outputToRecipeMap.put(normalizedOutput(recipe), recipe);
	}
	
	public void addDecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		ItemStack[] normal = normalizedInput(recipe);
		if(normal == null) return;
		inputToRecipeMap.put(normal, recipe);
	}
	
	public void addRecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		outputToRecipeMap.put(normalizedOutput(recipe), recipe);
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
