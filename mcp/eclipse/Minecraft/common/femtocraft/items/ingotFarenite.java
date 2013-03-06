package femtocraft.items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ingotFarenite extends Item{
	public ingotFarenite(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(CreativeTabs.tabMisc);
	}
	
	public String getTextureFile() {
		return "/femtocraft/femtocraft_items.png";
	}
}
