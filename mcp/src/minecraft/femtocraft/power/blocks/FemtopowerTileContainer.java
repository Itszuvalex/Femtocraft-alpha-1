/**
 * 
 */
package femtocraft.power.blocks;

import femtocraft.core.blocks.TileContainer;
import femtocraft.power.TileEntity.FemtopowerTile;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * @author Itszuvalex
 * 
 */
public class FemtopowerTileContainer extends TileContainer {

	public FemtopowerTileContainer(int par1, Material par2Material) {
		super(par1, par2Material);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new FemtopowerTile();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#onNeighborBlockChange(net.minecraft.world.World
	 * , int, int, int, int)
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3,
			int par4, int par5) {
		super.onNeighborBlockChange(par1World, par2, par3, par4, par5);
		FemtopowerTile container = (FemtopowerTile) par1World
				.getBlockTileEntity(par2, par3, par4);

		if (container != null)
			container.checkConnections();
	}

	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4,
			EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack) {
		// TODO Auto-generated method stub
		super.onBlockPlacedBy(par1World, par2, par3, par4,
				par5EntityLivingBase, par6ItemStack);

		FemtopowerTile container = (FemtopowerTile) par1World
				.getBlockTileEntity(par2, par3, par4);

		if (container != null)
			container.checkConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#onPostBlockPlaced(net.minecraft.world.World,
	 * int, int, int, int)
	 */
	@Override
	public void onPostBlockPlaced(World par1World, int par2, int par3,
			int par4, int par5) {
		// TODO Auto-generated method stub
		super.onPostBlockPlaced(par1World, par2, par3, par4, par5);

		par1World.notifyBlocksOfNeighborChange(par2, par3, par4,
				par1World.getBlockId(par2, par3, par4));

		FemtopowerTile container = (FemtopowerTile) par1World
				.getBlockTileEntity(par2, par3, par4);

		if (container != null)
			container.checkConnections();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.block.Block#onBlockPlaced(net.minecraft.world.World,
	 * int, int, int, int, float, float, float, int)
	 */
	@Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4,
			int par5, float par6, float par7, float par8, int par9) {
		// TODO Auto-generated method stub
		int val = super.onBlockPlaced(par1World, par2, par3, par4, par5, par6,
				par7, par8, par9);
		FemtopowerTile container = (FemtopowerTile) par1World
				.getBlockTileEntity(par2, par3, par4);

		if (container != null)
			container.checkConnections();

		par1World.notifyBlocksOfNeighborChange(par2, par3, par4,
				par1World.getBlockId(par2, par3, par4));

		return val;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraft.block.Block#onBlockActivated(net.minecraft.world.World,
	 * int, int, int, net.minecraft.entity.player.EntityPlayer, int, float,
	 * float, float)
	 */
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3,
			int par4, EntityPlayer par5EntityPlayer, int par6, float par7,
			float par8, float par9) {

		FemtopowerTile container = (FemtopowerTile) par1World
				.getBlockTileEntity(par2, par3, par4);

		if (container != null)
			par5EntityPlayer.addChatMessage(String.valueOf(container
					.getCurrentPower()));

		return true;
	}
}
