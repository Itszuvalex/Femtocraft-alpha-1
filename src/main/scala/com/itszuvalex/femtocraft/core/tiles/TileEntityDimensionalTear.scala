package com.itszuvalex.femtocraft.core.tiles

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
import com.itszuvalex.femtocraft.utility.{BlockAndTileSnapshot, SpatialRelocation}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity

/**
 * Created by Christopher on 2/6/2015.
 */
class TileEntityDimensionalTear extends TileEntity {
  @Saveable var duration                       = 100
  @Saveable var snapshot: BlockAndTileSnapshot = null

  def setDuration(int: Int) = duration = int

  def setSnapshot(snap: BlockAndTileSnapshot) = snapshot = snap

  override def updateEntity(): Unit = {
    if (worldObj.isRemote) return

    duration -= 1
    if (duration <= 0) {
      if (snapshot == null) worldObj.setBlockToAir(xCoord, yCoord, zCoord)
      else {
        SpatialRelocation.applySnapshot(snapshot)
        snapshot = null
      }
    }
  }

  override def writeToNBT(p_145841_1_ : NBTTagCompound): Unit = {
    super.writeToNBT(p_145841_1_)
    FemtocraftDataUtils.saveObjectToNBT(p_145841_1_, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  override def readFromNBT(p_145839_1_ : NBTTagCompound): Unit = {
    super.readFromNBT(p_145839_1_)
    FemtocraftDataUtils.loadObjectFromNBT(p_145839_1_, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }
}
