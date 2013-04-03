package femtocraft.cooking.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.proxy.ClientProxyFemtocraft;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class cuttingBoard extends Block {
	@SideOnly(Side.CLIENT)
	private Icon cuttingBoardTop;
	@SideOnly(Side.CLIENT)
	private Icon cuttingBoardBottom;
	
	public cuttingBoard(int id) {
		super(id, Material.cake);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
		setCreativeTab(Femtocraft.femtocraftTab);
//		this.func_96478_d(0);
	}

	@Override
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	/**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
	@Override
    public boolean isOpaqueCube()
    {
        return false;
    }

	@Override
	public int getRenderType()
	{
		return ClientProxyFemtocraft.cuttingBoardRenderType;
	}
	
	@Override
    public boolean canRenderInPass(int pass)
	{
		//For now the block can always be rendered
		ClientProxyFemtocraft.CuttingBoardRenderPass = pass;
		return true;
	}
	
	@Override
    public int getRenderBlockPass()
	{
		return 1;
	}
  
	
//    /**
//     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
//     * cleared to be reused)
//     */
//    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
//    {
//        int l = par1World.getBlockMetadata(par2, par3, par4) & 7;
//        float f = 0.125F;
//        return AxisAlignedBB.getAABBPool().getAABB((double)par2 + this.minX, (double)par3 + this.minY, (double)par4 + this.minZ, (double)par2 + this.maxX, (double)((float)par3 + (float)l * f), (double)par4 + this.maxZ);
//    }
//    
//    /**
//     * Sets the block's bounds for rendering it as an item
//     */
//    public void setBlockBoundsForItemRender()
//    {
//        this.func_96478_d(0);
//    }
//
//    /**
//     * Updates the blocks bounds based on its current state. Args: world, x, y, z
//     */
//    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
//    {
//        this.func_96478_d(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
//    }
//	
//    protected void func_96478_d(int par1)
//    {
//        int j = par1 & 7;
//        float f = (float)(2 * (1 + j)) / 16.0F;
//        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
//    }
//    
//	/**
//     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
//     */
//    public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
//    {
//        int l = par1World.getBlockId(par2, par3 - 1, par4);        
//        Block block = Block.blocksList[l];
//        if (block == null) return false;
//        if (block == this && (par1World.getBlockMetadata(par2, par3 - 1, par4) & 7) == 7) return true;
//        if (block.isLeaves(par1World, par2, par3 - 1, par4) && Block.blocksList[l].isOpaqueCube()) return false;
//        return par1World.getBlockMaterial(par2, par3 - 1, par4).blocksMovement();
//    }
//	
//    @SideOnly(Side.CLIENT)
//    /**
//     * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
//     * coordinates.  Args: blockAccess, x, y, z, side
//     */
//    public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
//    {
//        return par5 == 1 ? true : super.shouldSideBeRendered(par1IBlockAccess, par2, par3, par4, par5);
//    }
	
	@SideOnly(Side.CLIENT)
    public void func_94332_a(IconRegister par1IconRegister)
    {
        this.field_94336_cN = par1IconRegister.func_94245_a("Femtocraft:cuttingBoard");
    }
	
}
