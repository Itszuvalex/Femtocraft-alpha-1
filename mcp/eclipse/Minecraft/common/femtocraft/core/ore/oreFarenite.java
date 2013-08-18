package femtocraft.core.ore;

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
		func_111022_d(Femtocraft.ID.toLowerCase() +":" + "oreFarenite");
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "oreFarenite");
    }
	
	public int idDropped(int par1, Random random, int par2) {
		return Femtocraft.ingotFarenite.itemID;
	}
	
	public int quantityDropped(Random random) {
		return 4;
	}
}
