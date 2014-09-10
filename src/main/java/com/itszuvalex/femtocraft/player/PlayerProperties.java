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

package com.itszuvalex.femtocraft.player;

import com.itszuvalex.femtocraft.Femtocraft;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;

public class PlayerProperties implements IExtendedEntityProperties {
    public final static String PROP_TAG = "femtocraft.player.properties";
    public final static String PACKET_CHANNEL = "Femtocraft" + "." + "PProp";

    protected final EntityPlayer player;

    private Map<String, IFemtocraftPlayerProperty> properties = new TreeMap<String, IFemtocraftPlayerProperty>();

    private static Map<String, Class<? extends IFemtocraftPlayerProperty>> propertiesClasses = new TreeMap<String,
            Class<?
                    extends IFemtocraftPlayerProperty>>();

    public static boolean registerPlayerProperty(String name, Class<? extends IFemtocraftPlayerProperty>
            propertyClass) {
        if (propertiesClasses.containsKey(name)) return false;
        return propertiesClasses.put(name, propertyClass) != null;
    }

    public IFemtocraftPlayerProperty getProperty(String name) {
        return properties.get(name);
    }

    public PlayerProperties(EntityPlayer player) {
        this.player = player;
        for (Map.Entry<String, Class<? extends IFemtocraftPlayerProperty>> entry : propertiesClasses
                .entrySet()) {

            try {
                properties.put(entry.getKey(), entry.getValue().newInstance());
            } catch (InstantiationException e) {
                Femtocraft.logger.log(Level.SEVERE, "Failed to create new instance of " + entry.getKey() +
                                                    " on creating PlayerProperties for player: " + player + " name: " +
                                                    player.getCommandSenderName());
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    public static void register(EntityPlayer player) {
        player.registerExtendedProperties(PROP_TAG,
                new PlayerProperties(player));
    }

    public static PlayerProperties get(EntityPlayer player) {
        return (PlayerProperties) player.getExtendedProperties(PROP_TAG);
    }

    public void sync() {
        if (FMLCommonHandler.instance().getEffectiveSide().isClient()) {
            return;
        }

        NBTTagCompound compound = new NBTTagCompound();

        for (Map.Entry<String, IFemtocraftPlayerProperty> entry : properties.entrySet()) {
            NBTTagCompound entryComp = new NBTTagCompound();
            entry.getValue().toDescriptionPacket(entryComp);
            compound.setCompoundTag(entry.getKey(), entryComp);
        }

        try {
            Packet250CustomPayload packet = new Packet250CustomPayload(
                    PACKET_CHANNEL, CompressedStreamTools.compress(compound));
            PacketDispatcher.sendPacketToPlayer(packet, (Player) player);
        } catch (IOException e) {
            Femtocraft.logger.log(Level.SEVERE, "Failed to send PlayerProperties packet to " + player);
            e.printStackTrace();
        }
    }

    @Override
    public void saveNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = new NBTTagCompound();
        for (Map.Entry<String, IFemtocraftPlayerProperty> entry : this.properties.entrySet()) {
            NBTTagCompound entryComp = new NBTTagCompound();
            entry.getValue().saveToNBT(entryComp);
            properties.setCompoundTag(entry.getKey(), entryComp);
        }

        compound.setTag(PROP_TAG, properties);
    }

    @Override
    public void loadNBTData(NBTTagCompound compound) {
        NBTTagCompound properties = compound.getCompoundTag(PROP_TAG);
        for (Map.Entry<String, IFemtocraftPlayerProperty> entry : this.properties.entrySet()) {
            entry.getValue().loadFromNBT(properties.getCompoundTag(entry.getKey()));
        }
    }

    @Override
    public void init(Entity entity, World world) {
    }

    public void handlePacket(Packet250CustomPayload packet) {
        try {
            NBTTagCompound compound = CompressedStreamTools.decompress(packet.data);
            for (Map.Entry<String, IFemtocraftPlayerProperty> entry : this.properties.entrySet()) {
                entry.getValue().loadFromDescription(compound.getCompoundTag(entry.getKey()));
            }
        } catch (IOException e) {
            Femtocraft.logger.log(Level.SEVERE, "Error occured while handling PlayerProperties packet.");
            e.printStackTrace();
        }
    }

}
