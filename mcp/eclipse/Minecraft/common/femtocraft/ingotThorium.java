package femtocraft;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ingotThorium  extends Item{
	public ingotThorium(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_items.png";
	}
}