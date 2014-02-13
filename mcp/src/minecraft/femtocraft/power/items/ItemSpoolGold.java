package femtocraft.power.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemSpoolGold extends Item {

	public ItemSpoolGold(int par1) {
		super(par1);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "ItemSpoolGold");
	}

}
