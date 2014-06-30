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

public class MultiBlockFemtoCube implements IMultiBlock {
    public static MultiBlockFemtoCube instance = new MultiBlockFemtoCube();

    private MultiBlockFemtoCube() {
    }

    @Override
    public boolean canForm(World world, int x, int y, int z) {
        return canForm(world, x, y, z, false);
    }

    @Override
    public boolean canFormStrict(World world, int x, int y, int z) {
        return canForm(world, x, y, z, true);
    }

    private boolean canForm(World world, int x, int y, int z, boolean strict) {
        // If interior would not contain a valid NanoCube, then this cannot
        // form.
        return MultiBlockNanoCube.instance.canForm(world, x, y + 1, z) &&
                checkBotTopLayer(world, x, y, z, 0, strict) &&
                checkBetweenLayer(world, x, y, z, 1, strict) &&
                checkMiddleLayer(world, x, y, z, strict) &&
                checkBetweenLayer(world, x, y, z, 3, strict) &&
                checkBotTopLayer(world, x, y, z, 4, strict);
    }

    private boolean checkBotTopLayer(World world, int x, int y, int z,
                                     int yoffset, boolean strict) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y + yoffset, z + j, x,
                                         y, z)) {
                    return false;
                }

                if (i == -2 || j == -2 || i == 2 || j == 2) {
                    if (world.getBlockId(x + i, y + yoffset, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeFrameID) {
                        return false;
                    }
                }
                else if (i == 0 && j == 0) {
                    if (world.getBlockId(x + i, y + yoffset, z + j) != FemtocraftConfigs.FemtopowerFemtoCubePortID) {
                        return false;
                    }
                }
                else {
                    if (world.getBlockId(x + i, y + yoffset, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeChassisID) {
                        return false;
                    }
                }

                if (strict) {
                    IMultiBlockComponent mbc = (IMultiBlockComponent) world
                            .getBlockTileEntity(x + i, y + yoffset, z + j);
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

    private boolean checkMiddleLayer(World world, int x, int y, int z,
                                     boolean strict) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y + 2, z + j, x, y, z)) {
                    return false;
                }

                if (i > -2 && i < 2 && j > -2 && j < 2) {
                    continue;
                }

                if (((i == -2 || i == 2) && (j > -2 && j < 2))
                        || (j == -2 || j == 2) && (i > -2 && i < 2)) {
                    if ((i == 0) || (j == 0)) {
                        if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerFemtoCubePortID) {
                            return false;
                        }
                    }
                    else {
                        if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeChassisID) {
                            return false;
                        }
                    }
                }
                else {

                    if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeFrameID) {
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

    private boolean checkBetweenLayer(World world, int x, int y, int z,
                                      int yoffset, boolean strict) {
        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                if (!isBlockInMultiBlock(world, x + i, y + yoffset, z + j, x,
                                         y, z)) {
                    return false;
                }

                if (i > -2 && i < 2 && j > -2 && j < 2) {
                    continue;
                }

                if (((i == -2 || i == 2) && (j > -2 && j < 2))
                        || (j == -2 || j == 2) && (i > -2 && i < 2)) {
                    if (world.getBlockId(x + i, y + yoffset, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeChassisID) {
                        return false;
                    }
                }
                else {
                    if (world.getBlockId(x + i, y + yoffset, z + j) != FemtocraftConfigs.FemtopowerFemtoCubeFrameID) {
                        return false;
                    }
                }

                if (strict) {
                    IMultiBlockComponent mbc = (IMultiBlockComponent) world
                            .getBlockTileEntity(x + i, y + yoffset, z + j);
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

    @Override
    public boolean isBlockInMultiBlock(World world, int x, int y, int z,
                                       int c_x, int c_y, int c_z) {
        if (y < c_y || y > (c_y + 4)) {
            return false;
        }
        if (x < (c_x - 2) || x > (c_x + 2)) {
            return false;
        }
        if (z < (c_z - 2) || z > (c_z + 2)) {
            return false;
        }

        return true;
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = true;

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = 0; k <= 4; ++k) {
                    // Ignore center
                    if (i > -2 && i < 2 && j > -2 && j < 2
                            && k > 0 && k < 4) {
                        continue;
                    }

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
        for (int i = -2; i <= 2; ++i) {
            // z
            for (int j = -2; j <= 2; ++j) {
                // y
                for (int k = -4; k <= 0; ++k) {
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

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = 0; k <= 4; ++k) {
                    // Ignore center
                    if (i > -2 && i < 2 && j > -2 && j < 2
                            && k > 0 && k < 4) {
                        continue;
                    }

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

}
