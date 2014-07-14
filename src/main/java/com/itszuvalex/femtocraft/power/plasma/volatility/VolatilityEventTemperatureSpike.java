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

import com.itszuvalex.femtocraft.power.plasma.*;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;
import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEventTemperatureSpike extends
                                             VolatilityEvent {
    public static double volatilityToRadiusDividend = 500.d;
    public static double volatilityEnergyToDurationDividend = 500.d;
    public static double coreMultiplier = 1.5d;
    private Random random = new Random();

    public VolatilityEventTemperatureSpike(IPlasmaFlow creator,
                                           int volatilityLevel,
                                           long volatilityEnergy) {
        super(creator, volatilityLevel, volatilityEnergy);
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y, int z) {
        double radius = volatilityLevel / volatilityToRadiusDividend * coreMultiplier;
        int duration = (int) (volatilityEnergy / volatilityEnergyToDurationDividend
                * coreMultiplier);

        igniteEntitiesAndWorld(world, x, y, z, radius, duration);
    }

    @Override
    public void interact(IFusionReactorComponent component, World world, int x, int y, int z) {

    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x, int y, int z) {
        double radius = volatilityLevel / volatilityToRadiusDividend;
        int duration = (int) (volatilityEnergy / volatilityEnergyToDurationDividend);

        igniteEntitiesAndWorld(world, x, y, z, radius, duration);
    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {
        int volatilityDif = volatilityLevel - flow.getVolatility();
        //If this event is more volatile than that flow
        if (volatilityDif > 0) {
            //Add some proportion of this event's temperature
            double temp = volatilityEnergy /
                    FemtocraftPlasmaUtils
                            .temperatureToEnergy;
            if (flow.getTemperature() < temp) {
                flow.setTemperature((long) (flow.getTemperature() + random.nextDouble
                        () * temp));
            }
        }
    }

    private void igniteEntitiesAndWorld(World world, int x, int y, int z, double radius, int duration) {
        List<Entity> entities = world.getEntitiesWithinAABB(Entity.class,
                AxisAlignedBB
                        .getAABBPool().getAABB(x - radius, y - radius, z - radius, x + radius + 1., y + radius + 1., z + radius + 1.)
        );
        for (Entity entity : entities) {
            entity.setFire(duration);
        }

        //FIRE
        int wradius = (int) radius;
        for (int wx = -wradius; wx < wradius; ++wx) {
            for (int wy = -wradius; wy < wradius; ++wy) {
                for (int wz = -wradius; wz < wradius; ++wz) {
                    if (world.isAirBlock(x + wx, y + wy, z + wz)) {
                        world.setBlock(x + wx, y + wy, z + wz,
                                Block.fire.blockID);
                    }
                }
            }
        }
    }
}
