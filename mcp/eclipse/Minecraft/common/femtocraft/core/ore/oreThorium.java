package femtocraft.core.ore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class oreThorium extends BlockOre{

	public oreThorium(int id, int texture) {
		super(id);
		this.setCreativeTab(Femtocraft.femtocraftTab);
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "oreThorium");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "oreThorium");
    }
}