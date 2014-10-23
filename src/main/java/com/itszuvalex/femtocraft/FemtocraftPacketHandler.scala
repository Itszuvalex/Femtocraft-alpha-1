///*
// * ******************************************************************************
// *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
// *  * Itszuvalex@gmail.com
// *  *
// *  * This program is free software; you can redistribute it and/or
// *  * modify it under the terms of the GNU General Public License
// *  * as published by the Free Software Foundation; either version 2
// *  * of the License, or (at your option) any later version.
// *  *
// *  * This program is distributed in the hope that it will be useful,
// *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
// *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *  * GNU General Public License for more details.
// *  *
// *  * You should have received a copy of the GNU General Public License
// *  * along with this program; if not, write to the Free Software
// *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
// *  *****************************************************************************
// */
//package com.itszuvalex.femtocraft
//
//import java.io.{ByteArrayInputStream, DataInputStream}
//import java.util.logging.Level
//
//import com.itszuvalex.femtocraft.managers.research.{ManagerResearch, ResearchPlayer}
//import com.itszuvalex.femtocraft.player.PlayerProperties
//import com.itszuvalex.femtocraft.power.tiles.{TileEntityNanoFissionReactorCore, TileEntityPhlegethonTunnelCore}
//import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
//import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager
//import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube
//import cpw.mods.fml.common.network.{IPacketHandler, Player}
//import ibxm.Player
//import net.minecraft.client.entity.EntityClientPlayerMP
//import net.minecraft.entity.player.EntityPlayer
//import net.minecraft.nbt.CompressedStreamTools
//import net.minecraft.network.INetworkManager
//import net.minecraft.network.packet.Packet250CustomPayload
//import net.minecraftforge.common.DimensionManager
//
//class FemtocraftPacketHandler extends IPacketHandler {
//  def onPacketData(manager: INetworkManager, packet: Packet250CustomPayload, playerEntity: Player) {
//    try {
//      if (packet.channel.equalsIgnoreCase(PlayerProperties.PACKET_CHANNEL)) {
//        PlayerProperties.get(playerEntity.asInstanceOf[EntityPlayer]).handlePacket(packet)
//        return
//      }
//      if (packet.channel.equalsIgnoreCase(ManagerResearch.RESEARCH_CHANNEL)) {
//        handleResearchPacket(packet, playerEntity)
//        return
//      }
//      val inputStream = new DataInputStream(new ByteArrayInputStream(packet.data))
//
//      packet.channel match {
//        case FemtocraftGuiHandler.PACKET_CHANNEL             => handleGUIPacket(inputStream, playerEntity)
//        case FemtocraftSoundManager.PACKET_CHANNEL           => handleSoundPacket(inputStream, playerEntity)
//        case TileEntityVacuumTube.PACKET_CHANNEL             => handleVacuumTube(inputStream, playerEntity)
//        case TileEntityResearchConsole.PACKET_CHANNEL        => handleResearchConsole(inputStream, playerEntity)
//        case TileEntityNanoFissionReactorCore.PACKET_CHANNEL => handleFissionReactorPacket(inputStream, playerEntity)
//        case TileEntityPhlegethonTunnelCore.PACKET_CHANNEL   => handlePhlegethonTunnelPacket(inputStream, playerEntity)
//        case s: String                                       => Femtocraft.log(Level.SEVERE, "Received packet to channel " + s + " which is not under any mapping.")
//        case _                                               => Femtocraft.log(Level.SEVERE, "Received packet to null channel.")
//      }
//    } catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  private def handleSoundPacket(inputStream: DataInputStream, playerEntity: Player) {
//    Femtocraft.soundManager.handlePacket(inputStream)
//  }
//
//  private def handlePhlegethonTunnelPacket(inputStream: DataInputStream, playerEntity: Player) {
//    try {
//      val x = inputStream.readInt
//      val y = inputStream.readInt
//      val z = inputStream.readInt
//      val dim = inputStream.readInt
//      val action = inputStream.readBoolean
//      val world = DimensionManager.getWorld(dim)
//      (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
//        case (null, _)                                 => Femtocraft.log(Level.SEVERE, "Received PhlegethonTunnel Packet for nonexistent World")
//        case (w, core: TileEntityPhlegethonTunnelCore) => core.handlePacket(action)
//        case (_, _)                                    =>
//      }
//    }
//    catch {
//      case ex: Exception => ex.printStackTrace()
//    }
//  }
//
//  private def handleGUIPacket(stream: DataInputStream, playerEntity: Player) {
//    try {
//      val player: EntityPlayer = playerEntity.asInstanceOf[EntityPlayer]
//      val id: Int = stream.readInt
//      val value: Int = stream.readInt
//      player.openContainer.updateProgressBar(id, value)
//    }
//    catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  private def handleFissionReactorPacket(inputStream: DataInputStream, playerEntity: Player) {
//    try {
//      val x = inputStream.readInt
//      val y = inputStream.readInt
//      val z = inputStream.readInt
//      val dim = inputStream.readInt
//      val action = inputStream.readByte
//      val world = DimensionManager.getWorld(dim)
//      (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
//        case (null, _)                                   => Femtocraft.log(Level.SEVERE, "Received FissionReactor Packet for nonexistent World")
//        case (w, core: TileEntityNanoFissionReactorCore) => core.handleAction(action)
//        case (_, _)                                      =>
//      }
//    }
//    catch {
//      case ex: Exception => ex.printStackTrace()
//    }
//  }
//
//  private def handleResearchPacket(packet: Packet250CustomPayload, player: Player) {
//    try {
//      val data = CompressedStreamTools.decompress(packet.data)
//      val rp: ResearchPlayer = new ResearchPlayer(player.asInstanceOf[EntityPlayer].username)
//      rp.loadFromNBTTagCompound(data)
//      Femtocraft.researchManager.syncResearch(rp)
//    }
//    catch {
//      case e: Exception =>
//        e.printStackTrace()
//        Femtocraft.log(Level.SEVERE, "Error loading PlayerResearch data from packet.  This client will not be able to detect its research.")
//    }
//  }
//
//  /*TODO - Unbind it to player, bind it to world instead. */
//  private def handleVacuumTube(stream: DataInputStream, player: Player) {
//    try {
//      val x = stream.readInt
//      val y = stream.readInt
//      val z = stream.readInt
//      val items = stream.readByte
//      val connections = stream.readByte
//      if (!player.isInstanceOf[EntityClientPlayerMP]) {
//        return
//      }
//      val cp = player.asInstanceOf[EntityClientPlayerMP]
//      val tile = cp.worldObj.getBlockTileEntity(x, y, z)
//      if (tile == null) {
//        return
//      }
//      if (!tile.isInstanceOf[TileEntityVacuumTube]) {
//        return
//      }
//      val tube = tile.asInstanceOf[TileEntityVacuumTube]
//      tube.parseItemMask(items)
//      tube.parseConnectionMask(connections)
//      cp.worldObj.markBlockForRenderUpdate(x, y, z)
//    }
//    catch {
//      case e: Exception => e.printStackTrace()
//    }
//  }
//
//  private def handleResearchConsole(inputStream: DataInputStream, playerEntity: Player) {
//    try {
//      val x = inputStream.readInt
//      val y = inputStream.readInt
//      val z = inputStream.readInt
//      val dim = inputStream.readInt
//      val world = DimensionManager.getWorld(dim)
//      (world, if (world != null) world.getBlockTileEntity(x, y, z)) match {
//        case (null, _)                          => Femtocraft.log(Level.SEVERE, "Received ResearchConsole Packet for nonexistent World")
//        case (w, te: TileEntityResearchConsole) => te.startWork()
//        case (_, _)                             =>
//      }
//    }
//    catch {
//      case ex: Exception => ex.printStackTrace()
//    }
//  }
//}
