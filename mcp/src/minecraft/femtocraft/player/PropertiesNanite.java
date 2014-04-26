/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.player;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.io.*;

public class PropertiesNanite implements IExtendedEntityProperties {
    public final static String PROP_TAG = "femtocraft.nanite.properties";
    public final static String PACKET_CHANNEL = "Femtocraft" + "." + "NProp";

    private final EntityPlayer player;

    private int currentGoo, maxGoo;

    public PropertiesNanite(EntityPlayer player) {
        currentGoo = maxGoo = 0;
        this.player = player;
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PROP_TAG,
                                          new PropertiesNanite(player));
    }

    public static PropertiesNanite get(EntityPlayer player) {
        return (PropertiesNanite) player.getExtendedProperties(PROP_TAG);
    }

    public int getCurrentGoo() {
        return currentGoo;
    }

    public void setCurrentGoo(int amount) {
        currentGoo = amount;
        sync();
    }

    public int getMaxGoo() {
        return maxGoo;
    }

    public void setMaxGoo(int amount) {
        maxGoo = amount;
        sync();
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        properties.setInteger("maxGoo", maxGoo);
        properties.setInteger("currentGoo", currentGoo);

        compound.setTag(PROP_TAG, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = compound.getCompoundTag(PROP_TAG);
        currentGoo = properties.getInteger("currentGoo");
        maxGoo = properties.getInteger("maxGoo");

    }

    public void sync() {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return;
        }

        int size = 8;
        ByteArrayOutputStream bos = new ByteArrayOutputStream(size);
        DataOutputStream dos = new DataOutputStream(bos);

        try {
            dos.writeInt(maxGoo);
            dos.writeInt(currentGoo);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Packet250CustomPayload packet = new Packet250CustomPayload(
                PACKET_CHANNEL, bos.toByteArray());
        PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
    }

    public void handlePacket(Packet250CustomPayload packet) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(
                packet.data));

        try {
            maxGoo = dis.readInt();
            currentGoo = dis.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void init(Entity entity, World world) {
    }

}
