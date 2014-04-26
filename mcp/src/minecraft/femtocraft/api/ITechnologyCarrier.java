package femtocraft.api;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.item.ItemStack;

public interface ITechnologyCarrier {

    void setTechnology(ItemStack stack, String name);

    String getTechnology(ItemStack stack);

    EnumTechLevel getTechnologyLevel(ItemStack stack);
}
