package femtocraft.core.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class HardenedBulb extends Item{
	public HardenedBulb(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		func_111206_d(Femtocraft.ID.toLowerCase() +":" + "HardenedBulb");
	}
	
	public void updateIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "HardenedBulb");
    }
}
