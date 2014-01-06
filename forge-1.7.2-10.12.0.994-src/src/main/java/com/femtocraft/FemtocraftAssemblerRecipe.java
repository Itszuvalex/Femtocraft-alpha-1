package femtocraft;

import net.minecraft.item.ItemStack;

public class FemtocraftAssemblerRecipe {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	
	public enum TechLevel
	{
		MACRO,
		MICRO,
		NANO,
		FEMTO
	}
	
	public FemtocraftAssemblerRecipe(ItemStack[] input_, Integer mass_, ItemStack output_, TechLevel tech)
	{
		input = input_;
		mass = mass_;
		output_ = output;
	}
}
