package femtocraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.client.renderer.texture.IconRegister;

public class ItemNanoInterfaceDevice extends InterfaceDevice {

	public ItemNanoInterfaceDevice(int par1) {
		super(par1, EnumTechLevel.NANO);
		setMaxStackSize(1);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "ItemNanoInterfaceDevice");
	}

}
