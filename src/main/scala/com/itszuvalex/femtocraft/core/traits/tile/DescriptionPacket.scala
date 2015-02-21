package com.itszuvalex.femtocraft.core.traits.tile

import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.tileentity.TileEntity

/**
 * Created by Christopher on 2/20/2015.
 */
trait DescriptionPacket extends TileEntity {
  override def getDescriptionPacket: Packet = {
    if (!hasDescription) {
      return null
    }
    val compound: NBTTagCompound = new NBTTagCompound
    saveToDescriptionCompound(compound)
    new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, compound)
  }

  def hasDescription = true

  def saveToDescriptionCompound(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.DESCRIPTION)
  }

  override def onDataPacket(net: NetworkManager, pkt: S35PacketUpdateTileEntity) {
    super.onDataPacket(net, pkt)
    handleDescriptionNBT(pkt.func_148857_g)
  }

  def handleDescriptionNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.DESCRIPTION)
  }
}
