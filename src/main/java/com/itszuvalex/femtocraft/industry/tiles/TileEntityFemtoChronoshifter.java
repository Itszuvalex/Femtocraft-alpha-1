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

package com.itszuvalex.femtocraft.industry.tiles;

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;

public class TileEntityFemtoChronoshifter extends TileEntityBaseEntityNanoHorologe {
    public static final int outputSlot = 7;
    public static final int inventorySize = 8;
    public static int powerStorage = 100000;
    public static int powerToCook_default = 160;
    public static float tickMultiplier_default = .5f;

    public TileEntityFemtoChronoshifter() {
        super();
        setTechLevel(EnumTechLevel.FEMTO);
        setMaxStorage(powerStorage);
        inventory.setInventorySize(inventorySize);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoChronoshifterGuiID;
    }

    @Override
    protected float getTickMultiplier() {
        return tickMultiplier_default;
    }

    @Override
    protected int getOutputSlotIndex() {
        return outputSlot;
    }

    @Override
    protected int getPowerToCook() {
        return powerToCook_default;
    }

    @Override
    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.FEMTO;
    }
}
