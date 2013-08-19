package femtocraft.managers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import femtocraft.FemtocraftAssemblerRecipe;

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
		
	}
	
	public void addRecipe(FemtocraftAssemblerRecipe recipe)
	{
		if(recipe.input.length != 9)
		{
			throw new RuntimeException("FemtocraftAssemblerRecipe - Invalid Input Array Length!  Must be 9!");
		}
		
		inputToRecipeMap.put(recipe.input, recipe);
		outputToRecipeMap.put(recipe.output, recipe);
	}
	
	public void removeRecipe(FemtocraftAssemblerRecipe recipe)
	{
		inputToRecipeMap.remove(recipe.input);
		outputToRecipeMap.remove(recipe.output);
	}
	
	public FemtocraftAssemblerRecipe getRecipe(ItemStack[] input)
	{
		return inputToRecipeMap.get(input);
	}
	
	public FemtocraftAssemblerRecipe getRecipe(ItemStack output)
	{
		return outputToRecipeMap.get(output);
	}
}
