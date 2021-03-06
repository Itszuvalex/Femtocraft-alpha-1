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

package com.itszuvalex.femtocraft.power.plasma.volatility;

import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorComponent;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.VolatilityEvent;
import com.itszuvalex.femtocraft.power.plasma.FemtocraftPlasmaUtils;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEventPlasmaLeak extends VolatilityEvent {
    public static int plasmaDuration = 50;
    public static int energyToSegmentsDividend = 1000;
    private Random random = new Random();

    public VolatilityEventPlasmaLeak(IPlasmaFlow creator,
                                     int volatilityLevel,
                                     long volatilityEnergy) {
        super(creator, volatilityLevel, volatilityEnergy);
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y, int z) {
        core.removeFlow(creator);
        FemtocraftPlasmaUtils.extractFlowsAndPurge(core, volatilityEnergy,
                volatilityLevel,
                energyToSegmentsDividend,
                plasmaDuration,
                world, x, y, z);
    }

    @Override
    public void interact(IFusionReactorComponent component, World world, int x, int y, int z) {
        component.removeFlow(creator);
        FemtocraftPlasmaUtils.extractFlowsAndPurge(component, volatilityEnergy,
                volatilityLevel,
                energyToSegmentsDividend,
                plasmaDuration,
                world, x, y, z);
    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x, int y, int z) {
        container.removeFlow(creator);
        FemtocraftPlasmaUtils.extractFlowsAndPurge(container, volatilityEnergy,
                volatilityLevel,
                energyToSegmentsDividend,
                plasmaDuration,
                world, x, y, z);
    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {

    }


}
