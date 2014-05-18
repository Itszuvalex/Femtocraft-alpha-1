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

package femtocraft.power.multiblock;

import femtocraft.core.multiblock.IMultiBlock;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.power.plasma.IFusionReactorComponent;
import femtocraft.power.plasma.IFusionReactorCore;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class MultiBlockFemtoStellarator implements IMultiBlock {
    public static MultiBlockFemtoStellarator instance = new
            MultiBlockFemtoStellarator();

    private MultiBlockFemtoStellarator() {
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
        return x >= (c_x - 2) && x <= (c_x + 2) &&
                y >= (c_y - 2) && y <= (c_y + 2) &&
                z >= (c_z - 2) && z <= (c_z + 2);
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = true;

        for (int i = -2; i <= 2; ++i) {
            for (int j = -2; j <= 2; ++j) {
                for (int k = -2; k <= 2; ++k) {
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
        for (int i = -2; i <= 2; ++i) {
            // z
            for (int j = -2; j <= 2; ++j) {
                // y
                for (int k = -2; k <= 2; ++k) {
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
                for (int k = -2; k <= 2; ++k) {
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
                    if (i == 0 && j == 0 & k == 0 ? te instanceof
                            IFusionReactorCore : (te
                            instanceof IFusionReactorComponent && !(te
                            instanceof
                            IFusionReactorCore)) && te instanceof
                            IMultiBlockComponent) {

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
