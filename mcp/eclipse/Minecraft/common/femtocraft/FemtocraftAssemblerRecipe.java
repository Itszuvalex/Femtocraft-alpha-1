package femtocraft;

import net.minecraft.item.ItemStack;

public class FemtocraftAssemblerRecipe {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	
	FemtocraftAssemblerRecipe(ItemStack[] input_, Integer mass_, ItemStack output_)
	{
		input = input_;
		mass = mass_;
		output_ = output;
	}
}
