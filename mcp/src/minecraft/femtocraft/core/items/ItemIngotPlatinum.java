package femtocraft.core.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemIngotPlatinum extends Item {
    public ItemIngotPlatinum(int id) {
        super(id);
        setMaxStackSize(64);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "ItemIngotPlatinum");
    }

    public void updateIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                              .toLowerCase() + ":" + "ItemIngotPlatinum");
    }
}
