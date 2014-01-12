package femtocraft.research;

import java.util.Comparator;

import net.minecraft.item.ItemStack;
import femtocraft.FemtocraftUtils;

public class AssemblerInputComparator implements Comparator<ItemStack[]> {

	@Override
	public int compare(ItemStack[] o1, ItemStack[] o2) {
		for(int i = 0; i < o1.length; i++)
		{
			int comp = FemtocraftUtils.compareItem(o1[i], o2[i]);
			if(comp != 0) return comp;
		}
		
		return 0;
	}

}
