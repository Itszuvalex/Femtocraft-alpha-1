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

package com.itszuvalex.femtocraft.power.plasma;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorComponent;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.utils.WorldLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;
import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/8/14.
 */
public class FemtocraftPlasmaUtils {
    public static final int temperatureToEnergy = 10000;
    public static final double energyToTemperature = 1.d / temperatureToEnergy;

    public static final void applyEventToContainer(IPlasmaContainer container,
                                                   IVolatilityEvent event,
                                                   World world, int x, int y,
                                                   int z) {
        if (event == null) {
            return;
        }
        if (container == null) {
            return;
        }

        container.onVolatilityEvent(event);

        if (container instanceof IFusionReactorCore) {
            event.interact((IFusionReactorCore) container, world, x, y, z);
        } else if (container instanceof IFusionReactorComponent) {
            event.interact((IFusionReactorComponent) container, world, x, y, z);
        } else {
            event.interact(container, world, x, y, z);
        }

        Collection<IPlasmaFlow> flows = container.getFlows();
        if (flows != null) {
            for (IPlasmaFlow flow : flows) {
                flow.onVolatilityEvent(event);
                event.interact(flow, world, x, y, z);
                flow.onPostVolatilityEvent(event);
            }
        }

        container.onPostVolatilityEvent(event);
    }

    public static void extractFlowsAndPurge(IPlasmaContainer container,
                                            long volatilityEnergy,
                                            int volatilityLevel,
                                            int energyToSegmentsDividend,
                                            int plasmaDuration,
                                            World world, int x, int y, int z) {
        long totalEnergy = volatilityEnergy;
        if (container.getFlows() != null) {

            for (IPlasmaFlow flow : container.getFlows()) {
                if (flow.isUnstable() || flow.getVolatility() > volatilityLevel) {
                    totalEnergy += flow.getTemperature() * FemtocraftPlasmaUtils
                            .temperatureToEnergy;
                    container.removeFlow(flow);
                }
            }
        }

        int segments = (int) (totalEnergy / energyToSegmentsDividend);

        WorldLocation segLoc = new WorldLocation(world, x, y, z);
        for (int i = 0; i < segments; i++) {
            if (!placeSegment(segLoc, plasmaDuration)) {
                return;
            }
        }
    }

    private static boolean placeSegment(WorldLocation loc, int plasmaDuration) {
        Random random = new Random();
        ForgeDirection dir = ForgeDirection.getOrientation(random.nextInt(6));

        int newX = loc.x + dir.offsetX;
        int newY = loc.y + dir.offsetY;
        int newZ = loc.z + dir.offsetZ;

        if (loc.world.isAirBlock(newX, newY, newZ)) {
            loc.world.setBlock(newX, newY, newZ, Femtocraft.blockPlasma());
            loc.world.setBlockMetadataWithNotify(newX, newY, newZ, dir.ordinal(), 2);
            TileEntityPlasma te = (TileEntityPlasma) loc.world.getTileEntity(newX, newY, newZ);
            if (te != null) {
                te.setDuration(plasmaDuration);
            }
        }

        loc.x += dir.offsetX;
        loc.y += dir.offsetY;
        loc.z += dir.offsetZ;


        return true;
    }

    public static void purgeFlow(long volatilityEnergy,
                                 int energyToSegmentsDividend,
                                 int plasmaDuration, World world, int x,
                                 int y, int z) {
        int segments = (int) (volatilityEnergy / energyToSegmentsDividend);

        WorldLocation segLoc = new WorldLocation(world, x, y, z);
        for (int i = 0; i < segments; i++) {
            if (!placeSegment(segLoc, plasmaDuration)) {
                return;
            }
        }
    }

}
