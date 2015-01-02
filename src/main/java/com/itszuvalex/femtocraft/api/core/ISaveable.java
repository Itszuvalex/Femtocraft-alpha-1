package com.itszuvalex.femtocraft.api.core;

import net.minecraft.nbt.NBTTagCompound;

public interface ISaveable {

    public void saveToNBT(NBTTagCompound compound);

    public void loadFromNBT(NBTTagCompound compound);
}
