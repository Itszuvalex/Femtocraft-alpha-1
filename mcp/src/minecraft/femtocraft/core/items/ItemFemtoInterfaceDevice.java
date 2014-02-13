package femtocraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemFemtoInterfaceDevice extends InterfaceDevice {

	public ItemFemtoInterfaceDevice(int par1) {
		super(par1, EnumTechLevel.FEMTO);
		setMaxStackSize(1);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "ItemFemtoInterfaceDevice");
	}

}
