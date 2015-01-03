package com.itszuvalex.femtocraft.api.core

/**
 * Created by Chris on 1/2/2015.
 */

import net.minecraft.nbt.NBTTagCompound

trait ISaveable {
  def saveToNBT(compound: NBTTagCompound)

  def loadFromNBT(compound: NBTTagCompound)
}

