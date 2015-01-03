package com.itszuvalex.femtocraft.api.player

import com.itszuvalex.femtocraft.api.core.ISaveable
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by Chris on 9/10/2014.
 */
trait IFemtocraftPlayerProperty extends ISaveable {
  def saveToDescriptionPacket(nbt: NBTTagCompound): Unit

  def loadFromDescription(nbt: NBTTagCompound): Unit
}
