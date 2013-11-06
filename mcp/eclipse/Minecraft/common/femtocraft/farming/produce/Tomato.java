package femtocraft.farming.produce;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import femtocraft.Femtocraft;

public class Tomato extends ItemFood{
	public Tomato(int id) {

		super(id, 2, 0.6f, false);
		setMaxStackSize(64);
		setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() +":" + "tomato");
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemFood#getMaxItemUseDuration(net.minecraft.item.ItemStack)
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		// TODO Auto-generated method stub
		return super.getMaxItemUseDuration(par1ItemStack);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemFood#getHealAmount()
	 */
	@Override
	public int getHealAmount() {
		// TODO Auto-generated method stub
		return super.getHealAmount();
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.ItemFood#getSaturationModifier()
	 */
	@Override
	public float getSaturationModifier() {
		// TODO Auto-generated method stub
		return super.getSaturationModifier();
	}

	public void updateIcons(IconRegister iconRegister)
	{
		this.itemIcon = iconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "tomato");
	}

}
