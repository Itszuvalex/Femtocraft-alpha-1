package femtocraft.power.blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.core.blocks.TileContainer;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.power.multiblock.MultiBlockFemtoCube;
import femtocraft.power.tiles.TileEntityFemtoCubeChassis;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

public class BlockFemtoCubeChassis extends TileContainer {
    public Icon[][] icons;

    public BlockFemtoCubeChassis(int par1) {
        super(par1, Material.iron);
        setCreativeTab(Femtocraft.femtocraftTab);
        setUnlocalizedName("BlockFemtoCubeChassis");

        icons = new Icon[3][];
        for (int i = 0; i < icons.length; ++i) {
            icons[i] = new Icon[3];
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess par1iBlockAccess, int par2,
                                int par3, int par4, int par5) {
        TileEntity te = par1iBlockAccess.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityFemtoCubeChassis) {
            TileEntityFemtoCubeChassis chassis = (TileEntityFemtoCubeChassis) te;
            if (chassis.isValidMultiBlock()) {
                MultiBlockInfo info = chassis.getInfo();
                ForgeDirection dir = ForgeDirection.getOrientation(par5);
                return iconForSide(info, dir, par2, par3, par4);
            }
        }
        return super.getBlockTexture(par1iBlockAccess, par2, par3, par4, par5);
    }

    private Icon iconForSide(MultiBlockInfo info, ForgeDirection dir, int x,
                             int y, int z) {
        int xdif = x - info.x();
        int ydif = y - info.y() - 2;
        int zdif = z - info.z();

        switch (dir) {
            case UP:
                return iconFromGrid(xdif, -zdif);
            case DOWN:
                return iconFromGrid(xdif, -zdif);
            case NORTH:
                return iconFromGrid(-xdif, ydif);
            case SOUTH:
                return iconFromGrid(xdif, ydif);
            case EAST:
                return iconFromGrid(-zdif, ydif);
            case WEST:
                return iconFromGrid(zdif, ydif);
            default:
                return this.blockIcon;
        }
    }

    private Icon iconFromGrid(int xdif, int ydif) {
        // int i = (Math.abs(xdif +1)) % icons.length;
        // int k = (Math.abs(ydif +1)) % icons[i].length;
        try {
            int i = xdif + 1;
            int k = ydif + 1;
            return icons[k][i];
        } catch (ArrayIndexOutOfBoundsException e) {
            return this.blockIcon;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister) {
        blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase()
                                                          + ":" + "BlockFemtoCubeChassis_unformed");

        for (int x = 0; x < icons.length; ++x) {
            for (int y = 0; y < icons[x].length; ++y) {
                if (x > 0 && x < icons.length - 1 && y > 0
                        && y < icons[x].length - 1) {
                    continue;
                }

                icons[x][y] = par1IconRegister.registerIcon(Femtocraft.ID
                                                                    .toLowerCase()
                                                                    + ":"
                                                                    + "BlockFemtoCubeChassis_"
                                                                    + x
                                                                    + "_" + y);
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world) {
        return new TileEntityFemtoCubeChassis();
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
        MultiBlockFemtoCube.instance.formMultiBlockWithBlock(par1World, par2,
                                                             par3, par4);
        super.onPostBlockPlaced(par1World, par2, par3, par4, par5);
    }

    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4,
                           int par5, int par6) {
        TileEntity te = par1World.getBlockTileEntity(par2, par3, par4);
        if (te instanceof TileEntityFemtoCubeChassis) {
            MultiBlockInfo info = ((TileEntityFemtoCubeChassis) te).getInfo();
            MultiBlockFemtoCube.instance.breakMultiBlock(par1World, info.x(),
                                                         info.y(), info.z());
        }
        super.breakBlock(par1World, par2, par3, par4, par5, par6);
    }

}
