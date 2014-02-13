package femtocraft.industry.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.client.renderer.texture.IconRegister;

public class PaperSchematic extends AssemblySchematic {

	public PaperSchematic(int itemID) {
		super(itemID);
		setMaxDamage(16);
		setUnlocalizedName("PaperSchematic");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "PaperSchematic");
		this.keyedIcon = par1IconRegister.registerIcon(Femtocraft.ID
				.toLowerCase() + ":" + "PaperSchematicKeyed");
	}

}
