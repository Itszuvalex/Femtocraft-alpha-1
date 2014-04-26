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

package femtocraft.core.multiblock;

import net.minecraft.world.World;

/**
 * @author Itszuvalex Helper
 * @explanation interface for better structure of MultiBlock behavior classes
 */
public interface IMultiBlock {

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this MultiBlock can form in the given world, with the
     * block at x,y,z as its controller block.
     */
    boolean canForm(World world, int x, int y, int z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this MultiBlock can form in the given world, with the
     * block at x,y,z as its controller block. This will return false if
     * any blocks that would be used are already in a MultiBlock.
     */
    boolean canFormStrict(World world, int x, int y, int z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @param c_x
     * @param c_y
     * @param c_z
     * @return True if the block at x, y, z is in the MultiBlock with the
     * controller at c_x, c_y, c_z
     */
    boolean isBlockInMultiBlock(World world, int x, int y, int z, int c_x,
                                int c_y, int c_z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this MultiBlock correctly forms in the given world, with
     * the block at x,y,z as the controller block.
     */
    boolean formMultiBlock(World world, int x, int y, int z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this MultiBlock correctly forms in the given world, using
     * the block given at x,y,z anywhere in the MultiBlock
     */
    boolean formMultiBlockWithBlock(World world, int x, int y, int z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this MultiBlock breaks with no errors in the given world,
     * using the block at x,y,z as the controller block.
     */
    boolean breakMultiBlock(World world, int x, int y, int z);
}
