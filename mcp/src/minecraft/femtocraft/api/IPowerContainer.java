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

package femtocraft.api;

import femtocraft.managers.research.EnumTechLevel;

/**
 * @author Itszuvalex
 */
public interface IPowerContainer {

    /**
     * @param level EnumTechLevel of power of container
     * @return True if can accept power of that level
     */
    public boolean canAcceptPowerOfLevel(EnumTechLevel level);

    /**
     * @return EnumTechLevel of container
     */
    public EnumTechLevel getTechLevel();

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
     * @return Fill percentage for purposes of charging - allows tanks and
     * whatnot to trick pipes into filling them I.E. return
     * getFillPercentage() < .25f ? getFillPercentage() : .25f;
     */
    public float getFillPercentageForCharging();

    /**
     * @return Fill percentage for purposes of output - allows tanks and other
     * TileEntities to trick pipes into not pulling all of their power.
     */
    public float getFillPercentageForOutput();

    /**
     * @return True if container has room and can accept charging from direction
     * @from false otherwise
     */
    public boolean canCharge();

    /**
     * @param amount Amount attempting to charge.
     * @return Total amount of @amount used to fill internal tank.
     */
    public int charge(int amount);

    /**
     * @param amount Amount of power to drain from internal storage
     * @return True if all power was consumed, false otherwise. This anticipates
     * all or nothing behavior.
     */
    public boolean consume(int amount);

}
