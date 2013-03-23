package femtocraft.power;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.Femtocraft;
import femtocraft.power.TileEntity.FemtopowerTile;

public class FemtopowerCable extends FemtopowerContainer {

	public FemtopowerCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(Femtocraft.femtocraftTab);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerTile();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
        this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:FemtopowerCable");
    }
}
