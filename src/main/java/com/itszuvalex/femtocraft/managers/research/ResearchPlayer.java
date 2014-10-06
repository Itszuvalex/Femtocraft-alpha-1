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

package com.itszuvalex.femtocraft.managers.research;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

public class ResearchPlayer {
    private final static String mapKey = "techMap";
    private final static String techNameKey = "techname";
    private final static String dataKey = "data";

    public final String username;
    private final HashMap<String, ResearchTechnologyStatus> techStatus;

    public ResearchPlayer(String username) {
        this.username = username;
        techStatus = new HashMap<String, ResearchTechnologyStatus>();
    }

    public Collection<ResearchTechnologyStatus> getTechnologies() {
        return techStatus.values();
    }

    /**
     * @param name  Name of researchTechnology to mark as researched
     * @param force Pass true if you want the named researchTechnology to be added if it isn't already discovered. This
     *              will bypass discover checks. This will not post an event.
     * @return True if researchTechnology successfully marked as researched. False otherwise.
     */
    public boolean researchTechnology(String name, boolean force) {
        ResearchTechnology rtech = Femtocraft.researchManager()
                                             .getTechnology(name);
        ResearchTechnologyStatus tech = techStatus.get(name);
        if (tech == null && !force) {
            return false;
        }

        if (tech == null) {
            techStatus.put(name, new ResearchTechnologyStatus(name, true));
            discoverNewTechs(rtech, false);
            sync();
            return true;
        }

        EventTechnology.TechnologyResearchedEvent event = new EventTechnology.TechnologyResearchedEvent(
                username, rtech);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            tech.researched = true;
            EntityPlayerMP player = MinecraftServer.getServer()
                                                   .getConfigurationManager().getPlayerForUsername(username);
            if (player != null) {
                ResearchTechnology techno = Femtocraft.researchManager()
                                                      .getTechnology(name);
                if (techno != null) {
                    FemtocraftUtils.sendMessageToPlayer(player, techno.level.getTooltipEnum() + name
                            + EnumChatFormatting.RESET
                            + " successfully researched.");
                }
            }
            discoverNewTechs(rtech, true);
            sync();

            return true;
        }
        return false;
    }

    // ---------------------------------------------------------

    private void discoverNewTechs(ResearchTechnology discoverer, boolean notify) {
        for (ResearchTechnology t : Femtocraft.researchManager()
                                              .getTechnologies()) {
            if (t.prerequisites != null) {
                ResearchTechnologyStatus ts = techStatus.get(t.name);
                if (ts != null && ts.researched) {
                    continue;
                }

                boolean discovererPrereq = false;
                boolean shouldDiscover = true;
                for (String st : t.prerequisites) {
                    ResearchTechnology pt = Femtocraft.researchManager().getTechnology(st);

                    if (pt == discoverer && discoverer == null) {
                        discovererPrereq = true;
                    }
                    if (pt == null) continue;

                    ResearchTechnologyStatus rts = techStatus.get(pt.name);
                    if (rts == null) {
                        shouldDiscover = false;
                        break;
                    }
                    if (!rts.researched) {
                        shouldDiscover = false;
                        break;
                    }
                }

                if (shouldDiscover && discovererPrereq) {
                    discoverTechnology(t.name);
                    if (notify) {
                        EntityPlayerMP player = MinecraftServer.getServer()
                                                               .getConfigurationManager()
                                                               .getPlayerForUsername(username);
                        if (player != null) {
                            ResearchTechnology techno = Femtocraft.researchManager()
                                                                  .getTechnology(t.name);
                            if (techno != null) {
                                FemtocraftUtils.sendMessageToPlayer(player, "New technology "
                                        + techno.level.getTooltipEnum()
                                        + t.name + EnumChatFormatting.RESET
                                        + " discovered.");
                            }
                        }
                    }
                }
            }
        }
    }

    public boolean discoverTechnology(String name) {
        if (techStatus.containsKey(name)) return true;

        EventTechnology.TechnologyDiscoveredEvent event = new EventTechnology.TechnologyDiscoveredEvent(
                username, Femtocraft.researchManager().getTechnology(name));
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            techStatus.put(name, new ResearchTechnologyStatus(name));
            sync();
            return true;
        }
        return false;
    }

    public ResearchTechnologyStatus getTechnology(String name) {
        return techStatus.get(name);
    }

    public ResearchTechnologyStatus removeTechnology(String name) {
        return techStatus.remove(name);
    }

    public boolean canDiscoverTechnology(ResearchTechnology tech) {
        if (tech.prerequisites == null) {
            return true;
        }

        for (String str : tech.prerequisites) {
            ResearchTechnology prereq = Femtocraft.researchManager().getTechnology(str);
            if (prereq == null) continue;
            ResearchTechnologyStatus ts = techStatus.get(prereq.name);
            if (ts == null) {
                return false;
            }
            if (!ts.researched) {
                return false;
            }
        }

        return true;
    }

    public boolean hasDiscoveredTechnology(ResearchTechnology tech) {
        return hasDiscoveredTechnology(tech.name);
    }

    public boolean hasDiscoveredTechnology(String tech) {
        ResearchTechnologyStatus ts = techStatus.get(tech);
        return ts != null;
    }

    public boolean hasResearchedTechnology(ResearchTechnology tech) {
        return tech == null || hasResearchedTechnology(tech.name);
    }

    public boolean hasResearchedTechnology(String tech) {
        if (tech == null || tech.equals("")) {
            return false;
        }

        ResearchTechnologyStatus ts = techStatus.get(tech);
        return ts != null && ts.researched;
    }

    public void sync() {
        EntityPlayerMP player = MinecraftServer.getServer()
                                               .getConfigurationManager().getPlayerForUsername(username);
        if (player == null) {
            return;
        }

        sync((Player) player);
    }

    // ---------------------------------------------------------

    public void sync(Player player) {
        NBTTagCompound data = new NBTTagCompound();
        saveToNBTTagCompound(data);

        Packet250CustomPayload packet = new Packet250CustomPayload();
        packet.channel = ManagerResearch.RESEARCH_CHANNEL;
        try {
            packet.data = CompressedStreamTools.compress(data);
        } catch (IOException e) {
            e.printStackTrace();
            Femtocraft.logger()
                      .log(Level.SEVERE,
                              "Error writing "
                                      + username
                                      + "'s PlayerResearch to packet data.  It will fail to sync to his client."
                      );
            return;
        }
        packet.length = packet.data.length;
        PacketDispatcher.sendPacketToPlayer(packet, player);
    }

    public void saveToNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for (ResearchTechnologyStatus status : techStatus.values()) {
            NBTTagCompound cs = new NBTTagCompound();
            cs.setString(techNameKey, status.tech);

            NBTTagCompound data = new NBTTagCompound();
            status.saveToNBTTagCompound(data);

            cs.setCompoundTag(dataKey, data);
            list.appendTag(cs);
        }

        compound.setTag(mapKey, list);
    }

    // ---------------------------------------------------------

    public void loadFromNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList(mapKey);

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
            String techname = cs.getString(techNameKey);

            NBTTagCompound data = cs.getCompoundTag(dataKey);
            ResearchTechnologyStatus status = new ResearchTechnologyStatus(
                    techname);
            status.loadFromNBTTagCompound(data);

            techStatus.put(techname, status);
        }
    }

    public static class TechnologyNotFoundException extends Exception {
        public String errMsg;

        public TechnologyNotFoundException(String message) {
            errMsg = message;
        }
    }
}
