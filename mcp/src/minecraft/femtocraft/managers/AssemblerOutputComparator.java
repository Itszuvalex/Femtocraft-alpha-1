package femtocraft.managers;

import java.util.Comparator;

import femtocraft.FemtocraftUtils;

import net.minecraft.item.ItemStack;

public class AssemblerOutputComparator implements Comparator<ItemStack> {

	@Override
	public int compare(ItemStack arg0, ItemStack arg1) {
		return FemtocraftUtils.compareItem(arg0,arg1);
	}

}
