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
//
//package com.itszuvalex.femtocraft;
//
//import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
//import com.itszuvalex.femtocraft.managers.research.ResearchPlayer;
//import com.itszuvalex.femtocraft.player.PlayerProperties;
//import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore;
//import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore;
//import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole;
//import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube;
//import cpw.mods.fml.common.network.IPacketHandler;
//import cpw.mods.fml.common.network.Player;
//import net.minecraft.client.entity.EntityClientPlayerMP;
//import net.minecraft.entity.player.EntityPlayer;
//import net.minecraft.nbt.CompressedStreamTools;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.network.INetworkManager;
//import net.minecraft.network.packet.Packet250CustomPayload;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.world.WorldServer;
//import net.minecraftforge.common.DimensionManager;
//
//import java.io.ByteArrayInputStream;
//import java.io.DataInputStream;
//import java.io.IOException;
//import java.util.logging.Level;
//
//public class FemtocraftPacketHandler implements IPacketHandler {
//
//    @Override
//    public void onPacketData(INetworkManager manager,
//                             Packet250CustomPayload packet, Player playerEntity) {
//        if (packet.channel.equalsIgnoreCase(PlayerProperties.PACKET_CHANNEL)) {
//            PlayerProperties.get((EntityPlayer) playerEntity).handlePacket(
//                    packet);
//            return;
//        }
//
//        if (packet.channel.equalsIgnoreCase(ManagerResearch.RESEARCH_CHANNEL)) {
//            handleResearchPacket(packet, playerEntity);
//            return;
//        }
//
//        DataInputStream inputStream = new DataInputStream(
//                new ByteArrayInputStream(packet.data));
//
//        if (packet.channel.equalsIgnoreCase(FemtocraftGuiHandler.PACKET_CHANNEL)) {
//            handleGUIPacket(inputStream, playerEntity);
//        }
//
//        if (packet.channel.equalsIgnoreCase(FemtocraftSoundManager.PACKET_CHANNEL)) {
//            handleSoundPacket(inputStream, playerEntity);
//        }
//
//        if (packet.channel.equalsIgnoreCase(TileEntityVacuumTube.PACKET_CHANNEL)) {
//            handleVacuumTube(inputStream, playerEntity);
//            return;
//        }
//        if (packet.channel
//                .equalsIgnoreCase(TileEntityResearchConsole.PACKET_CHANNEL)) {
//            handleResearchConsole(inputStream, playerEntity);
//            return;
//        }
//
//        if (packet.channel.equalsIgnoreCase(TileEntityNanoFissionReactorCore.PACKET_CHANNEL)) {
//            handleFissionReactorPacket(inputStream, playerEntity);
//            return;
//        }
//
//        if (packet.channel.equalsIgnoreCase(TileEntityPhlegethonTunnelCore.PACKET_CHANNEL)) {
//            handlePhlegethonTunnelPacket(inputStream, playerEntity);
//            return;
//        }
//
//    }
//
//    private void handleSoundPacket(DataInputStream inputStream, Player playerEntity) {
//        Femtocraft.soundManager().handlePacket(inputStream);
//    }
//
//    private void handlePhlegethonTunnelPacket(DataInputStream inputStream, Player playerEntity) {
//        int x = 0, y = 0, z = 0, dim = 0;
//        boolean action = false;
//
//        try {
//            x = inputStream.readInt();
//            y = inputStream.readInt();
//            z = inputStream.readInt();
//            dim = inputStream.readInt();
//            action = inputStream.readBoolean();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        WorldServer world = DimensionManager.getWorld(dim);
//        if (world == null) {
//            Femtocraft.log(Level.SEVERE,
//                    "Received PhlegethonTunnel Packet for nonexistent World");
//        }
//        else {
//            TileEntity te = world.getBlockTileEntity(x, y, z);
//            if (te instanceof TileEntityPhlegethonTunnelCore) {
//                ((TileEntityPhlegethonTunnelCore) te).handlePacket(action);
//            }
//        }
//    }
//
//
//    private void handleGUIPacket(DataInputStream stream, Player playerEntity) {
//        EntityPlayer player = (EntityPlayer) playerEntity;
//        try {
//            int id = stream.readInt();
//            int value = stream.readInt();
//            player.openContainer.updateProgressBar(id, value);
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (NullPointerException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleFissionReactorPacket(DataInputStream inputStream, Player playerEntity) {
//        int x = 0, y = 0, z = 0, dim = 0;
//        byte action = -1;
//
//        try {
//            x = inputStream.readInt();
//            y = inputStream.readInt();
//            z = inputStream.readInt();
//            dim = inputStream.readInt();
//            action = inputStream.readByte();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        WorldServer world = DimensionManager.getWorld(dim);
//        if (world == null) {
//            Femtocraft.log(Level.SEVERE,
//                    "Received FissionReactor Packet for nonexistent World");
//        }
//        else {
//            TileEntity te = world.getBlockTileEntity(x, y, z);
//            if (te instanceof TileEntityNanoFissionReactorCore) {
//                ((TileEntityNanoFissionReactorCore) te).handleAction(action);
//            }
//        }
//    }
//
//    private void handleResearchPacket(Packet250CustomPayload packet,
//                                      Player player) {
//        if (!(player instanceof EntityPlayer)) {
//            return;
//        }
//
//        EntityPlayer cp = (EntityPlayer) player;
//        NBTTagCompound data;
//        try {
//            data = CompressedStreamTools.decompress(packet.data);
//        } catch (IOException e) {
//            e.printStackTrace();
//            Femtocraft
//                    .log(Level.SEVERE,
//                            "Error decompressing PlayerResearch data from packet.  This client will not be able to " +
//                            "detect its research.");
//            return;
//        }
//        ResearchPlayer rp = new ResearchPlayer(cp.username);
//        rp.loadFromNBTTagCompound(data);
//        Femtocraft.researchManager().syncResearch(rp);
//    }
//
//    private void handleVacuumTube(DataInputStream stream, Player player) {
//        try {
//            int x = stream.readInt();
//            int y = stream.readInt();
//            int z = stream.readInt();
//
//            byte items = stream.readByte();
//            byte connections = stream.readByte();
//
//            if (!(player instanceof EntityClientPlayerMP)) {
//                return;
//            }
//
//            EntityClientPlayerMP cp = (EntityClientPlayerMP) player;
//
//            TileEntity tile = cp.worldObj.getBlockTileEntity(x, y, z);
//            if (tile == null) {
//                return;
//            }
//            if (!(tile instanceof TileEntityVacuumTube)) {
//                return;
//            }
//            TileEntityVacuumTube tube = (TileEntityVacuumTube) tile;
//            tube.parseItemMask(items);
//            tube.parseConnectionMask(connections);
//            cp.worldObj.markBlockForRenderUpdate(x, y, z);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    private void handleResearchConsole(DataInputStream inputStream,
//                                       Player playerEntity) {
//        int x = 0, y = 0, z = 0, dim = 0;
//
//        try {
//            x = inputStream.readInt();
//            y = inputStream.readInt();
//            z = inputStream.readInt();
//            dim = inputStream.readInt();
//        } catch (Exception ex) {
//            ex.printStackTrace();
//        }
//
//        WorldServer world = DimensionManager.getWorld(dim);
//        if (world == null) {
//            Femtocraft.log(Level.SEVERE,
//                    "Received ResearchConsole Packet for nonexistent World");
//        }
//        else {
//            TileEntity te = world.getBlockTileEntity(x, y, z);
//            if (te instanceof TileEntityResearchConsole) {
//                ((TileEntityResearchConsole) te).startWork();
//            }
//        }
//    }
//}
