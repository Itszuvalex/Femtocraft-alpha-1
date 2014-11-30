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
import com.itszuvalex.femtocraft.FemtocraftGuiConstants;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.api.core.Configurable;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;

public class TileEntityNanoFabricator extends TileEntityBaseEntityMicroReconstructor {
    @Configurable(comment = "Assembler recipe tech level maximum.")
    public static EnumTechLevel ASSEMBLER_TECH_LEVEL = EnumTechLevel.NANO;
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel TECH_LEVEL = EnumTechLevel.NANO;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 10000;
    @Configurable(comment = "Maximum number of items allowed at a time, pre upgrade.")
    public static int MAX_SMELT_PRE = 8;
    @Configurable(comment = "Ticks required to process, pre upgrade.")
    public static int TICKS_TO_COOK_PRE = 60;
    @Configurable(comment = "Power per item to begin processing, pre upgrade.")
    public static int POWER_TO_COOK_PRE = 80;

    @Configurable(comment = "Name of technology for max smelt upgrade.")
    public static String MAX_SMELT_UPGRADE = FemtocraftTechnologies.LOCALITY_ENTANGLER;
    @Configurable(comment = "Name of technology for ticks to cook upgrade.")
    public static String TICKS_TO_COOK_UPGRADE = FemtocraftTechnologies.REALITY_OVERCLOCKER;
    @Configurable(comment = "Name of technology for power to cook upgrade.")
    public static String POWER_TO_COOK_UPGRADE = "";

    @Configurable(comment = "Maximum number of items allowed at a time, post upgrade.")
    public static int MAX_SMELT_POST = 16;
    @Configurable(comment = "Ticks required to process, post upgrade.")
    public static int TICKS_TO_COOK_POST = 30;
    @Configurable(comment = "Power per item to begin processing, post upgrade.")
    public static int POWER_TO_COOK_POST = 80;

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(TECH_LEVEL, POWER_STORAGE);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiConstants.NanoFabricatorGuiID();
    }

    @Override
    protected int getTicksToCook() {
        return Femtocraft.researchManager().hasPlayerResearchedTechnology(getOwner(), TICKS_TO_COOK_UPGRADE) ?
                TICKS_TO_COOK_POST :
                TICKS_TO_COOK_PRE;
    }

    @Override
    protected int getPowerToCook() {
        return Femtocraft.researchManager().hasPlayerResearchedTechnology(getOwner(),
                POWER_TO_COOK_UPGRADE) ? POWER_TO_COOK_POST : POWER_TO_COOK_PRE;
    }

    @Override
    protected EnumTechLevel getAssemblerTech() {
        return ASSEMBLER_TECH_LEVEL;
    }

    @Override
    protected int getMaxSimultaneousSmelt() {
        return Femtocraft.researchManager().hasPlayerResearchedTechnology(getOwner(),
                MAX_SMELT_UPGRADE) ? MAX_SMELT_POST : MAX_SMELT_PRE;
    }
}
