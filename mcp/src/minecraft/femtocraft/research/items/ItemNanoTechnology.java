package femtocraft.research.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemStack;

public class ItemNanoTechnology extends ItemTechnologyCarrier {

    public ItemNanoTechnology(int par1) {
        super(par1);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("itemNanoTechnology");
    }

    @Override
    public EnumTechLevel getTechnologyLevel(ItemStack stack) {
        return EnumTechLevel.NANO;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                         + ":" + "ItemNanoTechnology");
    }
}
