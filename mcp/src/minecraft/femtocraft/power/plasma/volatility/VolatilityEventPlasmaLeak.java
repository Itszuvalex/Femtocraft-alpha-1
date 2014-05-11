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

package femtocraft.power.plasma.volatility;

import femtocraft.Femtocraft;
import femtocraft.power.plasma.*;
import femtocraft.utils.WorldLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEventPlasmaLeak extends VolatilityEvent {
    public static int plasmaDuration = 50;
    public static int energyToSegmentsDividend = 500;
    private Random random = new Random();

    public VolatilityEventPlasmaLeak(IPlasmaFlow creator,
                                     int volatilityLevel,
                                     long volatilityEnergy) {
        super(creator, volatilityLevel, volatilityEnergy);
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y, int z) {
        core.removeFlow(creator);
        extractFlowsAndPurge(core, world, x, y, z);
    }

    @Override
    public void interact(IFusionReactorComponent component, World world, int x, int y, int z) {
        component.removeFlow(creator);
        extractFlowsAndPurge(component, world, x, y, z);
    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x, int y, int z) {
        container.removeFlow(creator);
        extractFlowsAndPurge(container, world, x, y, z);
    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {

    }

    private void extractFlowsAndPurge(IPlasmaContainer container,
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

        int segments = (int) (volatilityEnergy / energyToSegmentsDividend);

        WorldLocation segLoc = new WorldLocation(world, x, y, z);
        for (int i = 0; i < segments; i++) {
            if (!placeSegment(segLoc)) {
                return;
            }
        }
    }

    private boolean placeSegment(WorldLocation loc) {
        ForgeDirection dir = ForgeDirection.UNKNOWN;
        int[] dirs = new int[6];
        for (int i = 0; i < dirs.length; i++) {
            dirs[i] = i;
        }
        //shuffle
        for (int i = 0; i < 500; ++i) {
            int a = random.nextInt(dirs.length);
            int b = random.nextInt(dirs.length);
            if (a == b) {
                continue;
            }
            int d = dirs[a];
            dirs[a] = dirs[b];
            dirs[b] = d;
        }

        for (int i = 0; i < dirs.length; i++) {
            dir = ForgeDirection.getOrientation(dirs[i]);
            if (loc.world.isAirBlock(loc.x + dir.offsetX, loc.y + dir.offsetY,
                                     loc.z + dir.offsetZ)) {
                break;
            }
        }
        if (dir == ForgeDirection.UNKNOWN) return false;

        loc.world.setBlock(loc.x + dir.offsetX, loc.y + dir.offsetY,
                           loc.z + dir.offsetZ, Femtocraft.blockPlasma.blockID);
        loc.world.setBlockMetadataWithNotify(loc.x, loc.y, loc.z,
                                             dir.ordinal(), 2);

        loc.x += dir.offsetX;
        loc.y += dir.offsetY;
        loc.z += dir.offsetZ;

        TileEntityPlasma te = (TileEntityPlasma) loc.world.getBlockTileEntity(loc.x, loc.y,
                                                                              loc.z);
        te.setDuration(plasmaDuration);

        return true;
    }

}
