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

package com.itszuvalex.femtocraft.power;

import com.itszuvalex.femtocraft.api.power.IPowerBlockContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Chris on 5/13/2014.
 */
public class FemtocraftPowerUtils {
    public static final float distributionBuffer = .01f;
    public static final float maxPowerPerTick = .05f;

    /**
     * This algorithm distributes temp from the given container, located in the given world, at the givne coordinates,
     * in a manner consistent with intended behaviors.  It distributes temp to IPowerBlockContainers, where the
     * difference in the given container's currentPowerPercentageForOutput() and the adjacent container's
     * currentPowerPercentageForInput() is greater than the distributionBuffer.  It will attempt to distribute at most
     * maxPowerPerTick*currentPower() temp from the given container.
     *
     * @param container   IPowerBlockContainer to pull temp from
     * @param connections Array of size 6, where true represents that this algorithm should attempt to distribute temp
     *                    in this ForgeDirection.ordinal()
     * @param world       World for the algorithm to search for IPowerBlockContainers in
     * @param x           X coordinate for the algorithm to center its distribution search upon
     * @param y           Y coordinate for the algorithm to center its distribution search upon
     * @param z           Z coordinate for the algorithm to center its distribution search upon
     */
    public static void distributePower(IPowerBlockContainer container, boolean[] connections, World world, int x,
                                       int y, int z) {
        // Don't do anything for empty containers
        if (container.getCurrentPower() <= 0) {
            return;
        }
        if (connections.length != 6) {
            return;
        }
        IPowerBlockContainer[] cons = new IPowerBlockContainer[6];

        int numConnections = 0;
        for (int i = 0; i < 6; ++i) {
            if (!connections[i]) {
                continue;
            }
            ForgeDirection dir = ForgeDirection.getOrientation(i);

            if (world.getChunkProvider().chunkExists((x + dir.offsetX) >> 4,
                    (z + dir.offsetZ) >> 4)) {
                TileEntity te = world.getBlockTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ);
                if (te instanceof IPowerBlockContainer) {
                    cons[i] = (IPowerBlockContainer) te;
                    ++numConnections;
                }
            }
        }

        float percentDifferenceTotal = 0.f;
        int maxSpreadThisTick = (int) ((float) container.getCurrentPower() * maxPowerPerTick) * numConnections;
        float[] fillPercentages = new float[6];
        for (int i = 0; i < 6; ++i) {
            fillPercentages[i] = container.getFillPercentageForOutput(ForgeDirection.getOrientation(i));
        }

        //Sum % differences
        for (int i = 0; i < 6; ++i) {
            if (!connections[i] || cons[i] == null) {
                continue;
            }
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            float percentDif = fillPercentages[i] - cons[i].getFillPercentageForCharging(dir.getOpposite());
            if (percentDif > distributionBuffer) {
                percentDifferenceTotal += percentDif;
            }

        }

        //Distribute
        for (int i = 0; i < 6; ++i) {
            if (!connections[i] || cons[i] == null) {
                continue;
            }
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            float percentDif = fillPercentages[i] - cons[i].getFillPercentageForCharging(dir.getOpposite());
            if (percentDif > distributionBuffer) {
                int amountToCharge = (int) Math.ceil(maxSpreadThisTick * percentDif / percentDifferenceTotal);
                container.consume(cons[i].charge(dir.getOpposite(), amountToCharge));
            }
        }
    }

    public static void distributePower(IPowerBlockContainer container, IPowerBlockContainer[] contacts) {
        float percentDifferenceTotal = 0.f;
        int maxSpreadThisTick = (int) ((float) container.getCurrentPower() * maxPowerPerTick) * contacts.length;
        float[] fillPercentages = new float[contacts.length];
        for (int i = 0; i < fillPercentages.length; ++i) {
            fillPercentages[i] = container.getFillPercentageForOutput(ForgeDirection.UNKNOWN);
        }

        //Sum % differences
        for (int i = 0; i < fillPercentages.length; ++i) {
            if (contacts[i] == null) {
                continue;
            }
            float percentDif = fillPercentages[i] - contacts[i].getFillPercentageForCharging(ForgeDirection.UNKNOWN);
            if (percentDif > distributionBuffer) {
                percentDifferenceTotal += percentDif;
            }
        }

        //Distribute
        for (int i = 0; i < fillPercentages.length; ++i) {
            if (contacts[i] == null) {
                continue;
            }
            float percentDif = fillPercentages[i] - contacts[i].getFillPercentageForCharging(ForgeDirection.UNKNOWN);
            if (percentDif > distributionBuffer) {
                int amountToCharge = (int) Math.ceil(maxSpreadThisTick * percentDif / percentDifferenceTotal);
                container.consume(contacts[i].charge(ForgeDirection.UNKNOWN, amountToCharge));
            }
        }
    }
}
