package femtocraft.ore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class oreTitanium extends BlockOre{

	public oreTitanium(int id, int texture) {
		super(id);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	//public String getTextureFile() {
	//	return "/femtocraft/femtocraft_terrain.png";
	//}
	
	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
        this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:oreTitanium");
    }
}
