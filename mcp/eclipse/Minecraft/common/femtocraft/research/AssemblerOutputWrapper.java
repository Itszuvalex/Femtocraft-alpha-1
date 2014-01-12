package femtocraft.research;

import net.minecraft.item.ItemStack;
import femtocraft.FemtocraftAssemblerRecipe;

public class AssemblerOutputWrapper implements Comparable {
	public ItemStack output;
	public FemtocraftAssemblerRecipe recipe;
	
	public AssemblerOutputWrapper(ItemStack output, FemtocraftAssemblerRecipe recipe)
	{
		this.output = output;
		this.recipe = recipe;
	}

	@Override
	public int compareTo(Object o) {
		AssemblerOutputComparator comp = new AssemblerOutputComparator();
		return comp.compare(output, ((AssemblerOutputWrapper)o).output);
	}
}
