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

package com.itszuvalex.femtocraft.industry.tiles;

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoFabricator extends TileEntityBaseEntityMicroReconstructor {
    public static int maxSmelt_default = 8;
    public static int ticksToCook_default = 60;
    public static int powerToCook_default = 80;

    public TileEntityNanoFabricator() {
        super();
        this.setTechLevel(EnumTechLevel.NANO);
        // TODO: Pull number from configs
        this.setMaxStorage(10000);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoFabricatorGuiID;
    }

    @Override
    protected int getPowerToCook() {
        return powerToCook_default;
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        return maxSmelt_default;
    }

    @Override
    protected int getTicksToCook() {
        return ticksToCook_default;
    }

    @Override
    protected EnumTechLevel getAssemblerTech() {
        return EnumTechLevel.NANO;
    }
}
