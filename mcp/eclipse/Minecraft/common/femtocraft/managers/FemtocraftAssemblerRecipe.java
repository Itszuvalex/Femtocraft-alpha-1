package femtocraft.managers;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import femtocraft.FemtocraftUtils;
import femtocraft.research.TechLevel;
import femtocraft.research.Technology;

public class FemtocraftAssemblerRecipe implements Comparable {
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

	@Override
	public int compareTo(Object o) {
		FemtocraftAssemblerRecipe ir = (FemtocraftAssemblerRecipe) o;
		for(int i = 0; i < 9; i++)
		{
			int comp = FemtocraftUtils.compareItem(input[i], ir.input[i]);
			if(comp != 0) return comp;
		}
		
		if(mass < ir.mass) return -1;
		if(mass > ir.mass) return 1;
		
		int comp = FemtocraftUtils.compareItem(output, ir.output);
		if(comp != 0) return comp;
		
		return 0;
	}
}
