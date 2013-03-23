package femtocraft.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ingotFarenite extends Item{
	public ingotFarenite(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
	}
	
	public void func_94581_a(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.func_94245_a("Femtocraft:ingotFarenite");
    }
}
