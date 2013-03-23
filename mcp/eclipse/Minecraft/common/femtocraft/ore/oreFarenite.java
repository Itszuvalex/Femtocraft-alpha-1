package femtocraft.ore;

import java.util.Random;

import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;

public class oreFarenite extends BlockOre{

	public oreFarenite(int id, int texture) {
		super(id);
		setCreativeTab(Femtocraft.femtocraftTab);
	}
	
	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
        this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:oreFarenite");
    }
	
	public int idDropped(int par1, Random random, int par2) {
		return Femtocraft.ingotFarenite.itemID;
	}
	
	public int quantityDropped(Random random) {
		return 4;
	}
}
