package femtocraft.managers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftAssemblerRecipe;
import femtocraft.research.TechLevel;

public class FemtocraftAssemblerRecipeManager {
	private Map<ItemStack[], FemtocraftAssemblerRecipe> inputToRecipeMap;
	private Map<ItemStack, FemtocraftAssemblerRecipe> outputToRecipeMap;
	
	public FemtocraftAssemblerRecipeManager()
	{
		inputToRecipeMap = new HashMap<ItemStack[], FemtocraftAssemblerRecipe>();
		outputToRecipeMap = new HashMap<ItemStack, FemtocraftAssemblerRecipe>();
		
		registerRecipes();
	}
	
	private void registerRecipes()
	{
		//Femto-tier components
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Crystallite), TechLevel.FEMTO, null));	//Crystallite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Mineralite), TechLevel.FEMTO, null));	//Mineralite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Metallite), TechLevel.FEMTO, null));	//Metallite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Faunite), TechLevel.FEMTO, null));	//Faunite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Electrite), TechLevel.FEMTO, null));	//Electrite
		addReversableRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Florite), TechLevel.FEMTO, null));	//Florite
		
		//Micro-tier components
	}
	
	public void addReversableRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		inputToRecipeMap.put(normalizedInput(recipe), recipe);
		outputToRecipeMap.put(normalizedOutput(recipe), recipe);
	}
	
	public void addDecompositionRecipe(FemtocraftAssemblerRecipe recipe) throws IllegalArgumentException
	{
		if(recipe.input.length != 9)
		{
			throw new IllegalArgumentException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		inputToRecipeMap.put(normalizedInput(recipe), recipe);
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
		return (inputToRecipeMap.remove(normalizedInput(recipe)) != null);
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
			if(!itemMatches(rec, input[i])) return false;
		}
		
		return true;
	}
	
	public boolean canCraft(ItemStack input)
	{
		FemtocraftAssemblerRecipe recipe = getRecipe(input);
		if(recipe == null) return false;
		
		if(input.stackSize < recipe.output.stackSize) return false;
		if(!itemMatches(recipe.output, input)) return false;
		
		return true;
	}
	
	private boolean itemMatches(ItemStack original, ItemStack compare)
	{
		return (original.isItemEqual(compare) || ((original.itemID == compare.itemID) && (original.getItemDamage() == OreDictionary.WILDCARD_VALUE)));
	}
	
	public FemtocraftAssemblerRecipe getRecipe(ItemStack[] input)
	{
		return inputToRecipeMap.get(normalizedInput(input));
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
