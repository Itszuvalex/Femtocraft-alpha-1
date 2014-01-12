package femtocraft.research;

import net.minecraft.item.ItemStack;
import femtocraft.FemtocraftAssemblerRecipe;

public class AssemblerInputWrapper implements Comparable {
	public ItemStack[] input;
	public FemtocraftAssemblerRecipe recipe;
	
	public AssemblerInputWrapper(ItemStack[] input, FemtocraftAssemblerRecipe recipe)
	{
		this.input = input;
		this.recipe = recipe;
	}

	@Override
	public int compareTo(Object o) {
		AssemblerInputComparator comp = new AssemblerInputComparator();
		return comp.compare(input, ((AssemblerInputWrapper)o).input);
	}

}
