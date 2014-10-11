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

package com.itszuvalex.femtocraft.api.multiblock;

import net.minecraft.world.World;

/**
 * @author Itszuvalex
 * @description Interface for MultiBlock components for easy implementation.
 */
public interface IMultiBlockComponent {
    /**
     * @return True if this is in valid MultiBlock
     */
    boolean isValidMultiBlock();

    /**
     * @param x
     * @param y
     * @param z
     * @return True if correctly forms, given controller block at x,y,z.
     */
    boolean formMultiBlock(World world, int x, int y, int z);

    /**
     * @param x
     * @param y
     * @param z
     * @return True if breaks without errors, given controller block at x,y,z.
     */
    boolean breakMultiBlock(World world, int x, int y, int z);

    /**
     * @return MultiBlockInfo associated with this MultiBlockComponent
     */
    MultiBlockInfo getInfo();
}
