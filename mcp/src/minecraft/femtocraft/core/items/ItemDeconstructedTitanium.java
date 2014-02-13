package femtocraft.core.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemDeconstructedTitanium extends Item {

	public ItemDeconstructedTitanium(int par1) {
		super(par1);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() + ":"
				+ "ItemDeconstructedTitanium");
	}

	public void updateIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "ItemDeconstructedTitanium");
	}

}
