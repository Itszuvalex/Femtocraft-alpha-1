package com.itszuvalex.femtocraft.api.multiblock

import com.itszuvalex.femtocraft.api.core.ISaveable
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

class MultiBlockInfo extends IMultiBlockComponent with ISaveable {
  private var isMultiBlock = false
  private var controller_x = 0
  private var controller_y = 0
  private var controller_z = 0

  def x = controller_x

  def y = controller_y

  def z = controller_z

  def isController(x: Int, y: Int,
                   z: Int) = isValidMultiBlock && x == controller_x && y == controller_y && z == controller_z

  override def isValidMultiBlock = isMultiBlock

  override def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    if (isMultiBlock) {
      if (controller_x != x || controller_y != y || controller_z != z) {
        return false
      }
    }
    isMultiBlock = true
    controller_x = x
    controller_y = y
    controller_z = z
    true
  }

  override def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    if (isMultiBlock) {
      if (controller_x != x || controller_y != y || controller_z != z) {
        return false
      }
    }
    isMultiBlock = false
    true
  }

  override def getInfo = this

  override def saveToNBT(compound: NBTTagCompound) {
    compound.setBoolean("isFormed", isMultiBlock)
    compound.setInteger("c_x", controller_x)
    compound.setInteger("c_y", controller_y)
    compound.setInteger("c_z", controller_z)
  }

  override def loadFromNBT(compound: NBTTagCompound) {
    isMultiBlock = compound.getBoolean("isFormed")
    controller_x = compound.getInteger("c_x")
    controller_y = compound.getInteger("c_y")
    controller_z = compound.getInteger("c_z")
  }
}
