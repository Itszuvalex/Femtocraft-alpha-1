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
package com.itszuvalex.femtocraft

import java.io.{ByteArrayInputStream, DataInputStream, IOException}
import java.util.logging.Level

import com.itszuvalex.femtocraft.managers.research.{ManagerResearch, ResearchPlayer}
import com.itszuvalex.femtocraft.player.PlayerProperties
import com.itszuvalex.femtocraft.power.tiles.{TileEntityNanoFissionReactorCore, TileEntityPhlegethonTunnelCore}
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube
import cpw.mods.fml.common.network.{IPacketHandler, Player}
import net.minecraft.client.entity.EntityClientPlayerMP
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound}
import net.minecraft.network.INetworkManager
import net.minecraft.network.packet.Packet250CustomPayload
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.WorldServer
import net.minecraftforge.common.DimensionManager

class FemtocraftPacketHandler extends IPacketHandler {
  def onPacketData(manager: INetworkManager, packet: Packet250CustomPayload, playerEntity: Player) {
    if (packet.channel.equalsIgnoreCase(PlayerProperties.PACKET_CHANNEL)) {
      PlayerProperties.get(playerEntity.asInstanceOf[EntityPlayer]).handlePacket(packet)
      return
    }
    if (packet.channel.equalsIgnoreCase(ManagerResearch.RESEARCH_CHANNEL)) {
      handleResearchPacket(packet, playerEntity)
      return
    }
    val inputStream = new DataInputStream(new ByteArrayInputStream(packet.data))

    packet.channel match {
      case FemtocraftGuiHandler.PACKET_CHANNEL             => handleGUIPacket(inputStream, playerEntity)
      case FemtocraftSoundManager.PACKET_CHANNEL           => handleSoundPacket(inputStream, playerEntity)
      case TileEntityVacuumTube.PACKET_CHANNEL             => handleVacuumTube(inputStream, playerEntity)
      case TileEntityResearchConsole.PACKET_CHANNEL        => handleResearchConsole(inputStream, playerEntity)
      case TileEntityNanoFissionReactorCore.PACKET_CHANNEL => handleFissionReactorPacket(inputStream, playerEntity)
      case TileEntityPhlegethonTunnelCore.PACKET_CHANNEL   => handlePhlegethonTunnelPacket(inputStream, playerEntity)
    }
  }

  private def handleSoundPacket(inputStream: DataInputStream, playerEntity: Player) {
    Femtocraft.soundManager.handlePacket(inputStream)
  }

  private def handlePhlegethonTunnelPacket(inputStream: DataInputStream, playerEntity: Player) {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var dim: Int = 0
    var action: Boolean = false
    try {
      x = inputStream.readInt
      y = inputStream.readInt
      z = inputStream.readInt
      dim = inputStream.readInt
      action = inputStream.readBoolean
    }
    catch {
      case ex: Exception => ex.printStackTrace(); return
    }

    val world = DimensionManager.getWorld(dim)
    (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
      case (null, _)                                 => Femtocraft.log(Level.SEVERE, "Received PhlegethonTunnel Packet for nonexistent World")
      case (w, core: TileEntityPhlegethonTunnelCore) => core.handlePacket(action)
      case (_, _)                                    =>
    }
  }

  private def handleGUIPacket(stream: DataInputStream, playerEntity: Player) {
    val player: EntityPlayer = playerEntity.asInstanceOf[EntityPlayer]
    try {
      val id: Int = stream.readInt
      val value: Int = stream.readInt
      player.openContainer.updateProgressBar(id, value)
    }
    catch {
      case e: IOException          => e.printStackTrace()
      case e: NullPointerException => e.printStackTrace()
    }
  }

  private def handleFissionReactorPacket(inputStream: DataInputStream, playerEntity: Player) {
    var x = 0
    var y = 0
    var z = 0
    var dim = 0
    var action: Byte = -1
    try {
      x = inputStream.readInt
      y = inputStream.readInt
      z = inputStream.readInt
      dim = inputStream.readInt
      action = inputStream.readByte
    }
    catch {
      case ex: Exception => ex.printStackTrace(); return
    }
    val world = DimensionManager.getWorld(dim)
    (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
      case (null, _)                                   => Femtocraft.log(Level.SEVERE, "Received FissionReactor Packet for nonexistent World")
      case (w, core: TileEntityNanoFissionReactorCore) => core.handleAction(action)
      case (_, _)                                      =>
    }
  }

  private def handleResearchPacket(packet: Packet250CustomPayload, player: Player) {
    if (!player.isInstanceOf[EntityPlayer]) {
      return
    }
    val cp = player.asInstanceOf[EntityPlayer]
    var data: NBTTagCompound = null
    try {
      data = CompressedStreamTools.decompress(packet.data)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Femtocraft.log(Level.SEVERE, "Error decompressing PlayerResearch data from packet.  This client will not be able to detect its research.")
        return
    }
    val rp: ResearchPlayer = new ResearchPlayer(cp.username)
    rp.loadFromNBTTagCompound(data)
    Femtocraft.researchManager.syncResearch(rp)
  }

  /*TODO - Unbind it to player, bind it to world instead. */
  private def handleVacuumTube(stream: DataInputStream, player: Player) {
    try {
      val x = stream.readInt
      val y = stream.readInt
      val z = stream.readInt
      val items = stream.readByte
      val connections = stream.readByte
      if (!player.isInstanceOf[EntityClientPlayerMP]) {
        return
      }
      val cp = player.asInstanceOf[EntityClientPlayerMP]
      val tile: TileEntity = cp.worldObj.getBlockTileEntity(x, y, z)
      if (tile == null) {
        return
      }
      if (!tile.isInstanceOf[TileEntityVacuumTube]) {
        return
      }
      val tube: TileEntityVacuumTube = tile.asInstanceOf[TileEntityVacuumTube]
      tube.parseItemMask(items)
      tube.parseConnectionMask(connections)
      cp.worldObj.markBlockForRenderUpdate(x, y, z)
    }
    catch {
      case e: IOException => e.printStackTrace()
    }
  }

  private def handleResearchConsole(inputStream: DataInputStream, playerEntity: Player) {
    var x: Int = 0
    var y: Int = 0
    var z: Int = 0
    var dim: Int = 0
    try {
      x = inputStream.readInt
      y = inputStream.readInt
      z = inputStream.readInt
      dim = inputStream.readInt
    }
    catch {
      case ex: Exception => ex.printStackTrace(); return
    }
    val world: WorldServer = DimensionManager.getWorld(dim)
    (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
      case (null, _)                          => Femtocraft.log(Level.SEVERE, "Received ResearchConsole Packet for nonexistent World")
      case (w, te: TileEntityResearchConsole) => te.startWork()
      case (_, _)                             =>
    }
  }
}

