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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.core.Configurable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.core.MagnetRegistry;
import com.itszuvalex.femtocraft.render.RenderUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Random;

@Configurable
public class TileEntityMagneticInductionGenerator extends TileEntityPowerProducer {
    @Configurable
    public static int POWER_MAX = 1000;
    @Configurable
    public static EnumTechLevel POWER_LEVEL = EnumTechLevel.MICRO;
    @Configurable
    public static float MAG_DIFFERENCE_POWER_MULTIPLIER = .1f;
    @Saveable
    private int[] neighborMagnetStrength = new int[6];

    @SideOnly(Side.CLIENT)
    private Random random = new Random();

    @Override
    public void updateEntity() {
        super.updateEntity();
        if (worldObj.isRemote) {
            boolean charged = false;
            for (int i = 0; i < 6; ++i) {
                int val = magStrengthOfNeighborForDir(i);
                int old = neighborMagnetStrength[i];
                if (val != old) {
                    charged = true;
                    neighborMagnetStrength[i] = val;
                }
            }
            if (charged) {
                for (int i = 0; i < 4; ++i) {
                    spawnParticle();
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void spawnParticle() {
        float x = xCoord;
        float y = yCoord;
        float z = zCoord;
        int side = random.nextInt(6);
        ForgeDirection dir = ForgeDirection.getOrientation(side);
        float xOffset = dir.offsetX;
        float yOffset = dir.offsetY;
        float zOffset = dir.offsetZ;

        if (xOffset == 0) {
            xOffset = random.nextFloat();
        }
        if (yOffset == 0) {
            yOffset = random.nextFloat();
        }
        if (zOffset == 0) {
            zOffset = random.nextFloat();
        }

        if (xOffset < 0) xOffset = 0;
        if (yOffset < 0) yOffset = 0;
        if (zOffset < 0) zOffset = 0;

        RenderUtils.spawnParticle(worldObj, RenderUtils.MICRO_POWER_PARTICLE(),
                x + xOffset,
                y + yOffset,
                z + zOffset
        );
    }


    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        for (int i = 0; i < 6; ++i) {
            int val = magStrengthOfNeighborForDir(i);
            int old = neighborMagnetStrength[i];
            if (val != old) {
                charge(ForgeDirection.UNKNOWN, (int) Math.abs((val - old) * MAG_DIFFERENCE_POWER_MULTIPLIER));
                neighborMagnetStrength[i] = val;
            }
        }
    }

    private int magStrengthOfNeighborForDir(int i) {
        ForgeDirection dir = ForgeDirection.getOrientation(i);
        Block block = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
        return MagnetRegistry.isMagnet(block) ? MagnetRegistry.getMagnetStrength(block) : 0;
    }

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(POWER_LEVEL, POWER_MAX);
    }
}

