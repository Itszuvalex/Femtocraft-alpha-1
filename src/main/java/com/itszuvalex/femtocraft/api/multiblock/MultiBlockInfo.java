package com.itszuvalex.femtocraft.api.multiblock;

import com.itszuvalex.femtocraft.api.core.ISaveable;
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

    public int x() {
        return controller_x;
    }

    public int y() {
        return controller_y;
    }

    public int z() {
        return controller_z;
    }

    public boolean isController(int x, int y, int z) {
        return isValidMultiBlock() && x == controller_x && y == controller_y && z == controller_z;
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
