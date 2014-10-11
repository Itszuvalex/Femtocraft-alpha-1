package com.itszuvalex.femtocraft.core.container;

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.network.packet.Packet250CustomPayload;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Created by Chris on 8/29/2014.
 */
public abstract class ContainerBase extends Container {
    protected void sendUpdateToCrafter(Container container, ICrafting crafter, int index, int value) {
        if (crafter instanceof EntityPlayerMP) {
            PacketDispatcher.sendPacketToPlayer(updatePacket(index, value), (Player) crafter);
        } else {
            crafter.sendProgressBarUpdate(container, index, value);
        }
    }

    private Packet250CustomPayload updatePacket(int index, int value) {
        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = FemtocraftGuiHandler.PACKET_CHANNEL();

        ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
        DataOutputStream outputStream = new DataOutputStream(bos);
        try {
            outputStream.writeInt(index);
            outputStream.writeInt(value);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        packet.data = bos.toByteArray();
        packet.length = bos.size();

        return packet;
    }

}
