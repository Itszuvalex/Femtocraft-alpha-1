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

package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.world.World;

/**
 * Implemented by the Block class, not the TileEntity.
 */
public interface IAtmosphericChargingAddon {

    /**
     * Returns the amount of power generated this tick from this block.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    float powerPerTick(World world, int x, int y, int z);

    /**
     * What techlevel this addon is.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    EnumTechLevel techLevel(World world, int x, int y, int z);

    /**
     * Returns true if this can support addon, false otherwise.
     *
     * @param addon
     * @return
     */
    boolean canSupportAddon(IAtmosphericChargingAddon addon, World world,
                            int x, int y, int z);
}
