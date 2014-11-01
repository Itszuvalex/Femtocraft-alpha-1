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
import net.minecraftforge.common.util.ForgeDirection;

/**
 * @author Itszuvalex
 */
public interface IPowerBlockContainer {

    /**
     * @param level EnumTechLevel of power
     * @param from  Direction power will be coming from
     * @return True if can accept power of that level from the given direciton
     */
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from);

    /**
     * @param to Direction from this Container
     * @return EnumTechLevel of power this machine will give to the given direciton
     */
    public EnumTechLevel getTechLevel(ForgeDirection to);

    /**
     * @return Current storage amount of container
     */
    public int getCurrentPower();

    /**
     * @return Max storage amount of container - used for percentage
     * approximations during charging
     */
    public int getMaxPower();

    /**
     * @return Actual fill percentage - for things like damage values, etc.
     */
    public float getFillPercentage();

    /**
     * @param from Direction attempting to charge from.
     * @return Fill percentage for purposes of charging - allows tanks and
     * whatnot to trick pipes into filling them I.E. return
     * getFillPercentage() < .25f ? getFillPercentage() : .25f;
     */
    public float getFillPercentageForCharging(ForgeDirection from);

    /**
     * @param to Direction attempting to output to
     * @return Fill percentage for purposes of output - allows tanks and other
     * TileEntities to trick pipes into not pulling all of their power.
     */
    public float getFillPercentageForOutput(ForgeDirection to);

    /**
     * @param from Direction attempting to input power from
     * @return True if container has room and can accept charging from direction
     * @from false otherwise
     */
    public boolean canCharge(ForgeDirection from);

    /**
     * @param from Direction attempting to
     * @return True if container can be connected to from a given direction
     */
    public boolean canConnect(ForgeDirection from);

    /**
     * @param from   Direction charge is coming from.
     * @param amount Amount attempting to charge.
     * @return Total amount of @amount used to fill internal tank.
     */
    public int charge(ForgeDirection from, int amount);

    /**
     * @param amount Amount of power to drain from internal storage
     * @return True if all power was consumed, false otherwise. This anticipates
     * all or nothing behavior.
     */
    public boolean consume(int amount);

}
