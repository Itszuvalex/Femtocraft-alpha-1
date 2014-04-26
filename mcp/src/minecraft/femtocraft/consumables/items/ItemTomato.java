package femtocraft.consumables.items;

import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemTomato extends ItemFood {
    public ItemTomato(int id) {

        super(id, 2, 0.6f, false);
        setMaxStackSize(64);
        setCreativeTab(Femtocraft.femtocraftTab);
        setTextureName(Femtocraft.ID.toLowerCase() + ":" + "ItemTomato");
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * net.minecraft.item.ItemFood#getMaxItemUseDuration(net.minecraft.item.
     * ItemStack)
     */
    @Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack) {
        return super.getMaxItemUseDuration(par1ItemStack);
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.item.ItemFood#getHealAmount()
     */
    @Override
    public int getHealAmount() {
        return super.getHealAmount();
    }

    /*
     * (non-Javadoc)
     *
     * @see net.minecraft.item.ItemFood#getSaturationModifier()
     */
    @Override
    public float getSaturationModifier() {
        return super.getSaturationModifier();
    }

    public void updateIcons(IconRegister iconRegister) {
        this.itemIcon = iconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "ItemTomato");
    }

}
