/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft.power.multiblock;

import com.itszuvalex.femtocraft.FemtocraftConfigs;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlock;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class MultiBlockNanoCube implements IMultiBlock {
    public static MultiBlockNanoCube instance = new MultiBlockNanoCube();

    private MultiBlockNanoCube() {
    }

    @Override
    public boolean canForm(World world, int x, int y, int z) {
        return this.canForm(world, x, y, z, false);
    }

    @Override
    public boolean canFormStrict(World world, int x, int y, int z) {
        return this.canForm(world, x, y, z, true);
    }

    @Override
    public boolean isBlockInMultiBlock(World world, int x, int y, int z,
                                       int c_x, int c_y, int c_z) {
        return y >= c_y && y <= (c_y + 2) &&
                x >= (c_x - 1) && x <= (c_x + 1) &&
                z >= (c_z - 1) && z <= (c_z + 1);

    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = true;

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = 0; k <= 2; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    if (te instanceof IMultiBlockComponent) {
                        result = ((IMultiBlockComponent) te).formMultiBlock(
                                world, x, y, z) && result;
                    }
                    else {
                        if (!(i == 0 && j == 0 && k == 1)) {
                            result = false;
                        }
                    }
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
                for (int k = -2; k <= 0; ++k) {
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
                for (int k = 0; k <= 2; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    if (te instanceof IMultiBlockComponent) {
                        result = ((IMultiBlockComponent) te).breakMultiBlock(
                                world, x, y, z) && result;
                    }
                    else {
                        if (!(i == 0 && j == 0 && k == 1)) {
                            result = false;
                        }
                    }
                }
            }
        }
        return result;
    }

    private boolean canForm(World world, int x, int y, int z, boolean strict) {
        return checkGroundLevel(world, x, y, z, strict) &&
                checkMidLevel(world, x, y, z, strict) &&
                checkTopLevel(world, x, y, z, strict);

    }

    private boolean checkGroundLevel(World world, int x, int y, int z,
                                     boolean strict) {

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y, z + j, x, y, z)) {
                    return false;
                }

                if (i == 0 && j == 0) {
                    if (world.getBlockId(x + i, y, z + j) != FemtocraftConfigs.BlockNanoCubePortID) {
                        return false;
                    }
                }
                else {
                    if (world.getBlockId(x + i, y, z + j) != FemtocraftConfigs.BlockNanoCubeFrameID) {
                        return false;
                    }
                }

                if (strict) {
                    IMultiBlockComponent mbc = (IMultiBlockComponent) world
                            .getBlockTileEntity(x + i, y, z + j);
                    if (mbc == null) {
                        return false;
                    }
                    if (mbc.isValidMultiBlock()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkMidLevel(World world, int x, int y, int z,
                                  boolean strict) {
        if (world.getBlockId(x, y + 1, z) != FemtocraftConfigs.BlockMicroCubeID) {
            return false;
        }

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y + 1, z + j, x, y, z)) {
                    return false;
                }

                if (i == 0 && j == 0) {
                    continue;
                }
                if (i == 0 || j == 0) {
                    if (world.getBlockId(x + i, y + 1, z + j) != FemtocraftConfigs.BlockNanoCubePortID) {
                        return false;
                    }
                }
                else {
                    if (world.getBlockId(x + i, y + 1, z + j) != FemtocraftConfigs.BlockNanoCubeFrameID) {
                        return false;
                    }
                }

                if (strict) {
                    IMultiBlockComponent mbc = (IMultiBlockComponent) world
                            .getBlockTileEntity(x + i, y + 1, z + j);
                    if (mbc == null) {
                        return false;
                    }
                    if (mbc.isValidMultiBlock()) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private boolean checkTopLevel(World world, int x, int y, int z,
                                  boolean strict) {

        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y + 2, z + j, x, y, z)) {
                    return false;
                }

                if (i == 0 && j == 0) {
                    if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.BlockNanoCubePortID) {
                        return false;
                    }
                }
                else {
                    if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.BlockNanoCubeFrameID) {
                        return false;
                    }
                }

                if (strict) {
                    IMultiBlockComponent mbc = (IMultiBlockComponent) world
                            .getBlockTileEntity(x + i, y + 2, z + j);
                    if (mbc == null) {
                        return false;
                    }
                    if (mbc.isValidMultiBlock()) {
                        return false;
                    }
                }

            }
        }
        return true;
    }
}
