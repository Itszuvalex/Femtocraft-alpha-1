package femtocraft.core.items.decomposition;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;

public class ItemFibrousStrand extends Item {
    public ItemFibrousStrand(int id) {
        super(id);
        setMaxStackSize(64);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "ItemFibrousStrand");
    }

    public void updateIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                              .toLowerCase() + ":" + "ItemFibrousStrand");
    }
}
