package femtocraft.power;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.power.TileEntity.FemtopowerTile;

public class FemtopowerCable extends BlockContainer {

	/* (non-Javadoc)
	 * @see net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World, int, int, int, net.minecraft.entity.player.EntityPlayer, int, float, float, float)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {
		
		FemtopowerTile container = (FemtopowerTile)par1World.getBlockTileEntity(par2, par3, par4);
		par5EntityPlayer.addChatMessage(String.valueOf(container.getCurrentPower()));
		
		return true;
	}

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
