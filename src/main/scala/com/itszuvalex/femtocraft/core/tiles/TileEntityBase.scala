/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.core.tiles

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import com.itszuvalex.femtocraft.api.utils.{FemtocraftDataUtils, FemtocraftUtils}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.network.play.server.S35PacketUpdateTileEntity
import net.minecraft.network.{NetworkManager, Packet}
import net.minecraft.server.MinecraftServer
import net.minecraft.tileentity.TileEntity

class TileEntityBase extends TileEntity {
  @Saveable(desc = true, item = true) private var owner: String = null

  override def readFromNBT(par1nbtTagCompound: NBTTagCompound) {
    super.readFromNBT(par1nbtTagCompound)
    FemtocraftDataUtils.loadObjectFromNBT(par1nbtTagCompound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  override def writeToNBT(par1nbtTagCompound: NBTTagCompound) {
    super.writeToNBT(par1nbtTagCompound)
    FemtocraftDataUtils.saveObjectToNBT(par1nbtTagCompound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  override def updateEntity() {
    super.updateEntity()
    if (worldObj.isRemote) {
      return
    }
    if (!shouldTick) {
      return
    }
    femtocraftServerUpdate()
  }

  def shouldTick = !FemtocraftConfigs.requirePlayersOnlineForTileEntityTicks || FemtocraftUtils.isPlayerOnline(owner)

  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  def femtocraftServerUpdate() {
  }

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

  def loadInfoFromItemNBT(compound: NBTTagCompound) {
    if (compound == null) {
      return
    }
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.ITEM)
  }

  def saveInfoToItemNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.ITEM)
  }

  def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (hasGUI) {
      par5EntityPlayer.openGui(getMod, getGuiID, worldObj, xCoord, yCoord, zCoord)
      return true
    }
    false
  }

  def hasGUI = false

  def getMod: AnyRef = Femtocraft

  /**
   * @return GuiID, if GUI handler uses ids and not checking instanceof
   */
  def getGuiID = -1

  def canPlayerUse(player: EntityPlayer): Boolean = {
    player != null && (getOwner == null || (getOwner == player.getCommandSenderName) || Femtocraft.assistantManager.isPlayerAssistant(getOwner, player.getCommandSenderName) || (MinecraftServer.getServer != null && MinecraftServer.getServer.getConfigurationManager.func_152596_g(player.getGameProfile)) || player.capabilities.isCreativeMode)
  }

  def getOwner = owner

  def setOwner(newOwner: String) = owner = newOwner

  @Deprecated def onInventoryChanged() = setModified()

  def setModified() = if (worldObj != null) worldObj.markTileEntityChunkModified(xCoord, yCoord, zCoord, this)

  def setRenderUpdate() = if (worldObj != null) worldObj.markBlockRangeForRenderUpdate(xCoord, yCoord, zCoord, xCoord, yCoord, zCoord)

  def setUpdate() = if (worldObj != null) worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)

  def notifyNeighborsOfChange() = if (worldObj != null) worldObj.func_147453_f(xCoord, yCoord, zCoord, getBlockType)


}
