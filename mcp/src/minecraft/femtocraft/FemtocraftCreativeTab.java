package femtocraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class FemtocraftCreativeTab extends CreativeTabs {

    public FemtocraftCreativeTab(String label) {
        super(label);
    }

    @Override
    public ItemStack getIconItemStack() {
        return new ItemStack(Item.netherStar);
    }

}
