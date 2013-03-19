package femtocraft.power;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.power.TileEntity.FemtopowerTile;

public class FemtopowerCable extends FemtopowerContainer {

	public FemtopowerCable(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerTile(1000);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

}
