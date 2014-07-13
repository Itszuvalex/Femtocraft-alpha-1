package com.itszuvalex.femtocraft.power.multiblock;

import com.itszuvalex.femtocraft.api.IPhlegethonTunnelComponent;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlock;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public class MultiBlockPhlegethonTunnel implements IMultiBlock {
    public static MultiBlockPhlegethonTunnel instance = new MultiBlockPhlegethonTunnel();

    private MultiBlockPhlegethonTunnel() {

    }

    @Override
    public boolean canForm(World world, int x, int y, int z) {
        return checkComponents(world, x, y, z, false);
    }

    @Override
    public boolean canFormStrict(World world, int x, int y, int z) {
        return checkComponents(world, x, y, z, true);
    }

    @Override
    public boolean isBlockInMultiBlock(World world, int x, int y, int z, int c_x, int c_y, int c_z) {
        return x >= (c_x - 1) && x <= (c_x + 1) &&
                y >= (c_y - 1) && y <= (c_y + 1) &&
                z >= (c_z - 1) && z <= (c_z + 1);
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = true;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    result = te instanceof IMultiBlockComponent && ((IMultiBlockComponent) te).formMultiBlock(world, x, y, z) && result;
                }
            }
        }
        return result;
    }

    @Override
    public boolean formMultiBlockWithBlock(World world, int x, int y, int z) {
        // x
        for (int i = -1; i <= 1; ++i) {
            // z
            for (int j = -1; j <= 1; ++j) {
                // y
                for (int k = -1; k <= 1; ++k) {
                    if (canFormStrict(world, x + i, y + k, z + j)) {
                        return formMultiBlock(world, x + i, y + k, z + j);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean result = true;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    result = te instanceof IMultiBlockComponent && ((IMultiBlockComponent) te).breakMultiBlock(world, x, y, z) && result;
                }
            }
        }
        return result;
    }

    private boolean checkComponents(World world, int x, int y, int z,
                                    boolean strict) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -2; k <= 2; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    if ((i == 0 && j == 0 & k == 0 && te instanceof
                            IPhlegethonTunnelCore) || te instanceof IPhlegethonTunnelComponent) {
                        if (strict) {
                            if (((IMultiBlockComponent) te).getInfo()
                                                           .isValidMultiBlock()) {
                                return false;
                            }
                        }
                    }
                    else {
                        return false;
                    }
                }
            }
        }
        return true;
    }
}
