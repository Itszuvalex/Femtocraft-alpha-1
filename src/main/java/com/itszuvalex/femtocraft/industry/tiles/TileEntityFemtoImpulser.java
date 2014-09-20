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

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.industry.blocks.BlockFemtoImpulser;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;

@Configurable
public class TileEntityFemtoImpulser extends TileEntityBaseEntityMicroFurnace {
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel TECH_LEVEL = EnumTechLevel.FEMTO;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 100000;
    @Configurable(comment = "Maximum number of items allowed at a time, pre upgrade.")
    public static int MAX_SMELT_PRE = 32;
    @Configurable(comment = "Ticks required to process, pre upgrade.")
    public static int TICKS_TO_COOK_PRE = 20;
    @Configurable(comment = "Power per item to begin processing, pre upgrade.")
    public static int POWER_TO_COOK_PRE = 160;

    @Configurable(comment = "Name of technology for max smelt upgrade.")
    public static String MAX_SMELT_UPGRADE = ManagerResearch.MULTI_DIMENSIONAL_INDUSTRY;
    @Configurable(comment = "Name of technology for ticks to cook upgrade.")
    public static String TICKS_TO_COOK_UPGRADE = ManagerResearch.CAUSALITY_SINGULARITY;
    @Configurable(comment = "Name of technology for power to cook upgrade.")
    public static String POWER_TO_COOK_UPGRADE = "";

    @Configurable(comment = "Maximum number of items allowed at a time, post upgrade.")
    public static int MAX_SMELT_POST = 64;
    @Configurable(comment = "Ticks required to process, post upgrade.")
    public static int TICKS_TO_COOK_POST = 0;
    @Configurable(comment = "Power per item to begin processing, post upgrade.")
    public static int POWER_TO_COOK_POST = 160;

    public TileEntityFemtoImpulser() {
        super();
        this.setTechLevel(TECH_LEVEL);
        this.setMaxStorage(POWER_STORAGE);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.FemtoImpulserGuiID;
    }

    @Override
    protected int getTicksToCook() {
        return Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner(), TICKS_TO_COOK_UPGRADE) ?
                TICKS_TO_COOK_POST :
                TICKS_TO_COOK_PRE;
    }

    @Override
    protected int getPowerToCook() {
        return Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner(),
                POWER_TO_COOK_UPGRADE) ? POWER_TO_COOK_POST : POWER_TO_COOK_PRE;
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        return Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner(),
                MAX_SMELT_UPGRADE) ? MAX_SMELT_POST : MAX_SMELT_PRE;
    }

    @Override
    protected void updateBlockState(boolean working) {
        BlockFemtoImpulser.updateFurnaceBlockState(working, worldObj, xCoord,
                yCoord, zCoord);
    }
}