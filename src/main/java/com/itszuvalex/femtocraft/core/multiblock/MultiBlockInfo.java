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

package com.itszuvalex.femtocraft.core.multiblock;

import com.itszuvalex.femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class MultiBlockInfo implements IMultiBlockComponent, ISaveable {
    private boolean isMultiBlock;
    private int controller_x;
    private int controller_y;
    private int controller_z;

    public MultiBlockInfo() {
        isMultiBlock = false;
    }

    @Override
    public boolean isValidMultiBlock() {
        return isMultiBlock;
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        if (isMultiBlock) {
            if (controller_x != x || controller_y != y || controller_z != z) {
                return false;
            }
        }

        isMultiBlock = true;
        controller_x = x;
        controller_y = y;
        controller_z = z;
        return true;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        if (isMultiBlock) {
            if (controller_x != x || controller_y != y || controller_z != z) {
                return false;
            }
        }

        isMultiBlock = false;

        return true;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return this;
    }

    public int x() {
        return controller_x;
    }

    public int y() {
        return controller_y;
    }

    public int z() {
        return controller_z;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setBoolean("isFormed", isMultiBlock);
        compound.setInteger("c_x", controller_x);
        compound.setInteger("c_y", controller_y);
        compound.setInteger("c_z", controller_z);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        isMultiBlock = compound.getBoolean("isFormed");
        controller_x = compound.getInteger("c_x");
        controller_y = compound.getInteger("c_y");
        controller_z = compound.getInteger("c_z");
    }
}
