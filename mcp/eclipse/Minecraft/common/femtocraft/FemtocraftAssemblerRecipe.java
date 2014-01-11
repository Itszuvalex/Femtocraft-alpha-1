package femtocraft;

import net.minecraft.item.ItemStack;
import femtocraft.research.TechLevel;
import femtocraft.research.Technology;

public class FemtocraftAssemblerRecipe {
	public ItemStack[] input;
	public Integer mass;
	public ItemStack output;
	public TechLevel techLevel;
	public Technology tech;
	
	public FemtocraftAssemblerRecipe(ItemStack[] input, Integer mass, ItemStack output, TechLevel techLevel, Technology tech)
	{
		this.input = input;
		this.mass = mass;
		this.output = output;
		this.techLevel = techLevel;
		this.tech = tech;
	}
}
