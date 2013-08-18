package net.minecraft.block;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeDirection;

public class BlockTrapDoor extends Block
{
    /** Set this to allow trapdoors to remain free-floating */
    public static boolean disableValidation = false;

    protected BlockTrapDoor(int par1, Material par2Material)
    {
        super(par1, par2Material);
        float f = 0.5F;
        float f1 = 1.0F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }

    /**
     * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
     * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
     */
    public boolean isOpaqueCube()
    {
        return false;
    }

    /**
     * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
     */
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return !isTrapdoorOpen(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * The type of render function that is called for this block
     */
    public int getRenderType()
    {
        return 0;
    }

    @SideOnly(Side.CLIENT)

    /**
     * Returns the bounding box of the wired rectangular prism to render.
     */
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
     * cleared to be reused)
     */
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.getCollisionBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        this.setBlockBoundsForBlockRender(par1IBlockAccess.getBlockMetadata(par2, par3, par4));
    }

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        float f = 0.1875F;
        this.setBlockBounds(0.0F, 0.5F - f / 2.0F, 0.0F, 1.0F, 0.5F + f / 2.0F, 1.0F);
    }

    public void setBlockBoundsForBlockRender(int par1)
    {
        float f = 0.1875F;

        if ((par1 & 8) != 0)
        {
            this.setBlockBounds(0.0F, 1.0F - f, 0.0F, 1.0F, 1.0F, 1.0F);
        }
        else
        {
            this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
        }

        if (isTrapdoorOpen(par1))
        {
            if ((par1 & 3) == 0)
            {
                this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 1)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
            }

            if ((par1 & 3) == 2)
            {
                this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            }

            if ((par1 & 3) == 3)
            {
                this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
            }
        }
    }

    /**
     * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
     */
    public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {}

    /**
     * Called upon block activation (right click on the block.)
     */
    public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
    {
        if (this.blockMaterial == Material.iron)
        {
            return true;
        }
        else
        {
            int i1 = par1World.getBlockMetadata(par2, par3, par4);
            par1World.setBlockMetadataWithNotify(par2, par3, par4, i1 ^ 4, 2);
            par1World.playAuxSFXAtEntity(par5EntityPlayer, 1003, par2, par3, par4, 0);
            return true;
        }
    }

    public void onPoweredBlockChange(World par1World, int par2, int par3, int par4, boolean par5)
    {
        int l = par1World.getBlockMetadata(par2, par3, par4);
        boolean flag1 = (l & 4) > 0;

        if (flag1 != par5)
        {
            par1World.setBlockMetadataWithNotify(par2, par3, par4, l ^ 4, 2);
            par1World.playAuxSFXAtEntity((EntityPlayer)null, 1003, par2, par3, par4, 0);
        }
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
        if (!par1World.isRemote)
        {
            int i1 = par1World.getBlockMetadata(par2, par3, par4);
            int j1 = par2;
            int k1 = par4;

            if ((i1 & 3) == 0)
            {
                k1 = par4 + 1;
            }

            if ((i1 & 3) == 1)
            {
                --k1;
            }

            if ((i1 & 3) == 2)
            {
                j1 = par2 + 1;
            }

            if ((i1 & 3) == 3)
            {
                --j1;
            }

            if (!(isValidSupportBlock(par1World.getBlockId(j1, par3, k1)) || par1World.isBlockSolidOnSide(j1, par3, k1, ForgeDirection.getOrientation((i1 & 3) + 2))))
            {
                par1World.setBlockToAir(par2, par3, par4);
                this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
            }

            boolean flag = par1World.isBlockIndirectlyGettingPowered(par2, par3, par4);

            if (flag || par5 > 0 && Block.blocksList[par5].canProvidePower())
            {
                this.onPoweredBlockChange(par1World, par2, par3, par4, flag);
            }
        }
    }

    /**
     * Ray traces through the blocks collision from start vector to end vector returning a ray trace hit. Args: world,
     * x, y, z, startVec, endVec
     */
    public MovingObjectPosition collisionRayTrace(World par1World, int par2, int par3, int par4, Vec3 par5Vec3, Vec3 par6Vec3)
    {
        this.setBlockBoundsBasedOnState(par1World, par2, par3, par4);
        return super.collisionRayTrace(par1World, par2, par3, par4, par5Vec3, par6Vec3);
    }

    /**
     * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
     */
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        int j1 = 0;

        if (par5 == 2)
        {
            j1 = 0;
        }

        if (par5 == 3)
        {
            j1 = 1;
        }

        if (par5 == 4)
        {
            j1 = 2;
        }

        if (par5 == 5)
        {
            j1 = 3;
        }

        if (par5 != 1 && par5 != 0 && par7 > 0.5F)
        {
            j1 |= 8;
        }

        return j1;
    }

    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        if (disableValidation)
        {
            return true;
        }
        if (par5 == 0)
        {
            return false;
        }
        else if (par5 == 1)
        {
            return false;
        }
        else
        {
            if (par5 == 2)
            {
                ++par4;
            }

            if (par5 == 3)
            {
                --par4;
            }

            if (par5 == 4)
            {
                ++par2;
            }

            if (par5 == 5)
            {
                --par2;
            }

            return isValidSupportBlock(par1World.getBlockId(par2, par3, par4)) || par1World.isBlockSolidOnSide(par2, par3, par4, ForgeDirection.UP);
        }
    }

    public static boolean isTrapdoorOpen(int par0)
    {
        return (par0 & 4) != 0;
    }

    /**
     * Checks if the block ID is a valid support block for the trap door to connect with. If it is not the trapdoor is
     * dropped into the world.
     */
    private static boolean isValidSupportBlock(int par0)
    {
        if (disableValidation)
        {
            return true;
        }
        if (par0 <= 0)
        {
            return false;
        }
        else
        {
            Block block = Block.blocksList[par0];
            return block != null && block.blockMaterial.isOpaque() && block.renderAsNormalBlock() || block == Block.glowStone || block instanceof BlockHalfSlab || block instanceof BlockStairs;
        }
    }
}
