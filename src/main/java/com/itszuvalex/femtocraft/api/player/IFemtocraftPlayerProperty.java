package com.itszuvalex.femtocraft.api.player;

import com.itszuvalex.femtocraft.api.core.ISaveable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Chris on 9/10/2014.
 */
public interface IFemtocraftPlayerProperty extends ISaveable {
    public void toDescriptionPacket(NBTTagCompound nbt);

    public void loadFromDescription(NBTTagCompound nbt);
}
