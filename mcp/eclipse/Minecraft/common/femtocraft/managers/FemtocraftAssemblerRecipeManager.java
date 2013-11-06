package femtocraft.managers;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;
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
		//Femto-tier components
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Crystallite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Crystallite
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Mineralite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Mineralite
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), null, null, null}, 3, new ItemStack(Femtocraft.Metallite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Metallite
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Rectangulon), null, null, null}, 3, new ItemStack(Femtocraft.Faunite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Faunite
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Cubit), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Electrite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Electrite
//		addRecipe(new FemtocraftAssemblerRecipe(new ItemStack[]{null, null, null, new ItemStack(Femtocraft.Planeoid), new ItemStack(Femtocraft.Rectangulon), new ItemStack(Femtocraft.Planeoid), null, null, null}, 3, new ItemStack(Femtocraft.Florite), FemtocraftAssemblerRecipe.TechLevel.FEMTO));	//Florite
//		
		//Micro-tier components
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
