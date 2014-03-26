package femtocraft.managers.assembler;

import femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ComparatorAssemblerInput implements Comparator<ItemStack[]> {

	@Override
	public int compare(ItemStack[] o1, ItemStack[] o2) {
		boolean no1 = o1 == null;
		boolean no2 = o2 == null;

		if (no1 && !no2)
			return -1;
		if (!no1 && no2)
			return 1;
		if (no1 && no2)
			return 0;

		for (int i = 0; i < o1.length; i++) {
			int comp = FemtocraftUtils.compareItem(o1[i], o2[i]);
			if (comp != 0)
				return comp;
		}

		return 0;
	}

}
