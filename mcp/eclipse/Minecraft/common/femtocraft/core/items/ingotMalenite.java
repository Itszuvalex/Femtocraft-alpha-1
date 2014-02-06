package femtocraft.core.items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import femtocraft.Femtocraft;

public class ingotMalenite extends Item {

	public ingotMalenite(int par1) {
		super(par1);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() +":" + "ingotMalenite");
	}
	
	public void updateIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "ingotMalenite");
    }
}
