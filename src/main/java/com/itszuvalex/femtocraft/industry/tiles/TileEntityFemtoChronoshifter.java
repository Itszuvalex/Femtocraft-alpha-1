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
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.api.EnumTechLevel;

@Configurable
public class TileEntityFemtoChronoshifter extends TileEntityBaseEntityNanoHorologe {
    public static final int outputSlot = 7;
    public static final int inventorySize = 8;
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel TECH_LEVEL = EnumTechLevel.FEMTO;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 100000;
    @Configurable(comment = "Power per item to begin processing.")
    public static int POWER_TO_COOK = 160;

    @Configurable(comment = "Multiplier for tick processing time of Temporal Recipes.")
    public static float TICKS_TO_COOK_MULTIPLIER = .5f;

    public TileEntityFemtoChronoshifter() {
        super();
        inventory.setInventorySize(inventorySize);
    }

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(TECH_LEVEL, POWER_STORAGE);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoChronoshifterGuiID();
    }

    @Override
    protected float getTickMultiplier() {
        return TICKS_TO_COOK_MULTIPLIER;
    }

    @Override
    protected int getOutputSlotIndex() {
        return outputSlot;
    }

    @Override
    protected int getPowerToCook() {
        return POWER_TO_COOK;
    }

    @Override
    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.FEMTO;
    }
}
