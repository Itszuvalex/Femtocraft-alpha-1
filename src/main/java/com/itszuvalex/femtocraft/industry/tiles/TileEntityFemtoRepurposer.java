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

public class TileEntityFemtoRepurposer extends
                                       TileEntityBaseEntityMicroDeconstructor {
    public static int powerStorage = 100000;
    public static int maxSmelt_default = 32;
    public static int ticksToCook_default = 20;
    public static int powerToCook_default = 160;

    public TileEntityFemtoRepurposer() {
        super();
        this.setTechLevel(EnumTechLevel.FEMTO);
        // TODO: Pull number from configs
        this.setMaxStorage(powerStorage);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoRepurposerGuiID;
    }

    @Override
    protected int getTicksToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return ticksToCook_default;
    }

    @Override
    protected int getPowerToCook() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return powerToCook_default;
    }

    @Override
    protected EnumTechLevel getAssemblerTech() {
        return EnumTechLevel.FEMTO;
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        // TODO: Check for modifying researches
        // TODO: Pull number from configs
        return maxSmelt_default;
    }
}