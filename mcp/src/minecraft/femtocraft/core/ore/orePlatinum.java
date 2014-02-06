package femtocraft.core.ore;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;

public class orePlatinum extends BlockOre{

	public orePlatinum(int id) {
		super(id);
		this.setCreativeTab(Femtocraft.femtocraftTab);
		setTextureName(Femtocraft.ID.toLowerCase() +":" + "orePlatinum");
		setUnlocalizedName("orePlatinum");
		setHardness(3.0f);
		setStepSound(Block.soundStoneFootstep);
		setResistance(1f);
	}
	
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase() +":" + "orePlatinum");
    }
}