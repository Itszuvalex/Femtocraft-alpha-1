package femtocraft.managers.assembler;

import femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;

import java.util.Comparator;

public class ComparatorAssemblerOutput implements Comparator<ItemStack> {

    @Override
    public int compare(ItemStack arg0, ItemStack arg1) {
        return FemtocraftUtils.compareItem(arg0, arg1);
    }

}
