package femtocraft.api;

import net.minecraft.item.ItemStack;
import femtocraft.managers.research.EnumTechLevel;

public interface ITechnologyCarrier {

	void setTechnology(ItemStack stack, String name);
	
	String getTechnology(ItemStack stack);
	
	EnumTechLevel getTechnologyLevel(ItemStack stack);
}
