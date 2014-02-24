package femtocraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemMicroPlating extends Item {

	public ItemMicroPlating(int par1) {
		super(par1);
		setCreativeTab(Femtocraft.femtocraftTab);
		setMaxStackSize(64);
		setUnlocalizedName("ItemMicroPlating");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
				+ ":" + "ItemMicroPlating");
	}

}
