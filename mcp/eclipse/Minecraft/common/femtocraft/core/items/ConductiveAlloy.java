package femtocraft.core.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class ConductiveAlloy extends Item{
	public ConductiveAlloy(int id) {
		super(id);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		func_111206_d(Femtocraft.ID.toLowerCase() +":" + "ConductiveAlloy");
	}
	
	public void updateIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "ConductiveAlloy");
    }
}