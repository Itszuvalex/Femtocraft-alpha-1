package com.itszuvalex.femtocraft.player;

import com.itszuvalex.femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Chris on 9/10/2014.
 */
public interface IFemtocraftPlayerProperty extends ISaveable {
    public void toDescriptionPacket(NBTTagCompound nbt);

    public void loadFromDescription(NBTTagCompound nbt);
}
