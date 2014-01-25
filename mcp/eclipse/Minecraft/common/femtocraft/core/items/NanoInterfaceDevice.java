package femtocraft.core.items;

import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.research.TechLevel;

public class NanoInterfaceDevice extends InterfaceDevice {

	public NanoInterfaceDevice(int par1) {
		super(par1, TechLevel.NANO);
		setMaxStackSize(1);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() + ":" + "NanoInterfaceDevice");
	}

}
