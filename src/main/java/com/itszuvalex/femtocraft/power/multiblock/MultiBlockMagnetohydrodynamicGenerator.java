/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.power.multiblock;

import com.itszuvalex.femtocraft.core.multiblock.IMultiBlock;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
public class MultiBlockMagnetohydrodynamicGenerator implements IMultiBlock {
    public MultiBlockMagnetohydrodynamicGenerator instance = new MultiBlockMagnetohydrodynamicGenerator();

    private MultiBlockMagnetohydrodynamicGenerator() {

    }

    @Override
    public boolean canForm(World world, int x, int y, int z) {
        return checkComponents(world, x, y, z, false);
    }

    private boolean checkComponents(World world, int x, int y, int z, boolean strict) {
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    TileEntity te = world.getBlockTileEntity(x + i, y + k, z
                            + j);
                    if (te instanceof TileEntityMagnetohydrodynamicGenerator || te instanceof TileEntitySteamGenerator) {
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

    @Override
    public boolean canFormStrict(World world, int x, int y, int z) {
        return checkComponents(world, x, y, z, false);
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
}
