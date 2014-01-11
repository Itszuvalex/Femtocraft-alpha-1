package femtocraft;

import net.minecraft.item.ItemStack;

public class FemtocraftAssemblerRecipe {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	public TechLevel tech;
	
	public enum TechLevel
	{
		MACRO, 			//Vanilla level
		MICRO, 			//1st Tier
		NANO,  			//2nd Tier
		FEMTO, 			//3rd Tier
		TEMPORAL,		//Specialty Tier 1
		DIMENSIONAL		//Specialty Tier 2
	}
	
	public FemtocraftAssemblerRecipe(ItemStack[] input_, Integer mass_, ItemStack output_, TechLevel tech_)
	{
		input = input_;
		mass = mass_;
		output = output_;
		tech = tech_;
	}
}
