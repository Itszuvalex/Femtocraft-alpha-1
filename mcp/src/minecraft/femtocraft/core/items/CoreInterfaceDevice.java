package femtocraft.core.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.managers.research.TechLevel;
import net.minecraft.client.renderer.texture.IconRegister;

public class CoreInterfaceDevice extends InterfaceDevice {

	public CoreInterfaceDevice(int par1) {
		super(par1, TechLevel.FEMTO);
		setMaxStackSize(1);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "CoreInterfaceDevice");
	}

}