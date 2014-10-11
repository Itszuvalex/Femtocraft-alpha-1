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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

@Configurable
public class TileEntityNullEqualizer extends TileEntityPowerBase {

    @Configurable(comment = "Maximum power storage.")
    public static int POWER_STORAGE = 10000;
    @Configurable(comment = "Power level to distribute energy on first.")
    public static EnumTechLevel PRIMARY_POWER_LEVEL = EnumTechLevel.FEMTO;
    @Configurable(comment = "Power level to distribute energy on second.")
    public static EnumTechLevel SECONDARY_POWER_LEVEL = EnumTechLevel.NANO;

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return to == ForgeDirection.UNKNOWN ? PRIMARY_POWER_LEVEL : container().getTechLevel();
    }

    @Override
    public int getMaxPower() {
        return POWER_STORAGE;
    }

    @Override
    public void femtocraftServerUpdate() {
        if (getTechLevel(ForgeDirection.UNKNOWN) == PRIMARY_POWER_LEVEL) {
            setTechLevel(SECONDARY_POWER_LEVEL);
        } else if (getTechLevel(ForgeDirection.UNKNOWN) == SECONDARY_POWER_LEVEL) {
            setTechLevel(PRIMARY_POWER_LEVEL);
        }
        super.femtocraftServerUpdate();
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level,
                                         ForgeDirection from) {
        return (level == SECONDARY_POWER_LEVEL || level == PRIMARY_POWER_LEVEL);
    }
}
