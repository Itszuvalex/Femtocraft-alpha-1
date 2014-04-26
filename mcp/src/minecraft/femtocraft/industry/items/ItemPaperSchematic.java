package femtocraft.industry.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemPaperSchematic extends ItemAssemblySchematic {

    public ItemPaperSchematic(int itemID) {
        super(itemID);
        setMaxDamage(16);
        setUnlocalizedName("ItemPaperSchematic");
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                              .toLowerCase() + ":" + "ItemPaperSchematic");
        this.keyedIcon = par1IconRegister.registerIcon(Femtocraft.ID
                                                               .toLowerCase() + ":" + "ItemPaperSchematicKeyed");
    }

}
