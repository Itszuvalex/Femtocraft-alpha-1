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

import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.core.MagnetRegistry;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.block.Block;
import net.minecraftforge.common.ForgeDirection;

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

    public TileEntityMagneticInductionGenerator() {
        super();
        setTechLevel(POWER_LEVEL);
        setMaxStorage(POWER_MAX);
    }

//    @java.lang.Override
//    public boolean hasGUI() {
//        return true;
//    }
//
//    @Override
//    public int getGuiID() {
//        return FemtocraftGuiHandler.MicroEngineGuiID;
//    }

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
        Block block = Block.blocksList[worldObj.getBlockId(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)];
        return MagnetRegistry.isMagnet(block) ? MagnetRegistry.getMagnetStrength(block) : 0;
    }
}

