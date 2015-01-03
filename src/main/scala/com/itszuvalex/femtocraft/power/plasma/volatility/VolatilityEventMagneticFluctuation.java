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
import com.itszuvalex.femtocraft.power.plasma.FemtocraftPlasmaManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEventMagneticFluctuation extends
        VolatilityEvent {
    public static double volatilityToRadiusDividend = 1000.d;
    public static double volatilityEnergyToVelocityDividend = 1000.d;
    public static double coreMultiplier = 1.5d;
    private Random random = new Random();

    public VolatilityEventMagneticFluctuation(IPlasmaFlow creator,
                                              int volatilityLevel,
                                              long volatilityEnergy) {
        super(creator, volatilityLevel, volatilityEnergy);
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y, int z) {
        double radius = volatilityLevel() / volatilityToRadiusDividend * coreMultiplier;
        double force = volatilityEnergy() / volatilityEnergyToVelocityDividend
                       * coreMultiplier;

        scrambleEntities(world, x, y, z, radius, force);
    }

    @Override
    public void interact(IFusionReactorComponent component, World world, int x, int y, int z) {

    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x, int y, int z) {
        double radius = volatilityLevel() / volatilityToRadiusDividend;
        double force = volatilityEnergy() / volatilityEnergyToVelocityDividend;

        scrambleEntities(world, x, y, z, radius, force);
    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {
        int volatilityDif = volatilityLevel() - flow.getVolatility();
        //If this flow is more volatile than that flow
        if (volatilityDif > 0) {
            //% chance of the time, where % is volatilityEnergy/flow's
            // energy, make that flow unstable.
            if (
                    random.nextInt((int) (flow.getTemperature() * FemtocraftPlasmaManager
                            .temperatureToEnergy())) < volatilityEnergy()) {
                flow.setUnstable(true);
            }
        }
    }

    private void scrambleEntities(World world, int x, int y, int z, double radius, double force) {
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB
                        .getBoundingBox(
                                x - radius, y - radius, z - radius, x + radius + 1., y + radius + 1., z + radius + 1.)
        );
        for (Entity entity : entities) {
            double tforce = force;
            double forcex = random.nextFloat() * tforce;
            tforce -= forcex;
            double forcey = random.nextFloat() * tforce;
            tforce -= forcey;
            double forcez = tforce;
            entity.addVelocity(forcex, forcey, forcez);
        }
    }
}
