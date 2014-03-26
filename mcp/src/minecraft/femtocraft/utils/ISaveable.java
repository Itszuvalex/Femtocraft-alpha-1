package femtocraft.utils;

import net.minecraft.nbt.NBTTagCompound;

public interface ISaveable {

	public void saveToNBT(NBTTagCompound compound);

	public void loadFromNBT(NBTTagCompound compound);
}
