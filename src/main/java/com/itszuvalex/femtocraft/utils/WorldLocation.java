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

package com.itszuvalex.femtocraft.utils;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/9/14.
 */
public class WorldLocation implements ISaveable {
    public World world;
    public int x;
    public int y;
    public int z;

    public WorldLocation() {
        this(null, 0, 0, 0);
    }


    public WorldLocation(World world, int x, int y, int z) {
        this.world = world;
        this.x = x;
        this.y = y;
        this.z = z;
    }


    @Override
    public void saveToNBT(NBTTagCompound compound) {
        compound.setInteger("x", x);
        compound.setInteger("y", y);
        compound.setInteger("z", z);
        if (world != null && !world.isRemote) {
            compound.setInteger("dim", world.provider.dimensionId);
        }
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        x = compound.getInteger("x");
        y = compound.getInteger("y");
        z = compound.getInteger("z");
        world = DimensionManager.getWorld(compound.getInteger("dim"));
    }

    public TileEntity getTileEntity() {
        return world == null ? null : world.getBlockTileEntity(x, y, z);
    }

    public Block getBlock() {
        return world == null ? null : Block
                .blocksList[getBlockID()];
    }

    public int getBlockID() {
        return world == null ? 0 : world.getBlockId(x, y, z);
    }
}
