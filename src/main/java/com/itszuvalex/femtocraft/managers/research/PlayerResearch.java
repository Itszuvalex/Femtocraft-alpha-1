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
import com.itszuvalex.femtocraft.api.events.EventTechnology;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler;
import com.itszuvalex.femtocraft.network.messages.MessageResearchPlayer;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;

import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

public class PlayerResearch {
    private final static String mapKey = "techMap";
    private final static String techNameKey = "techname";
    private final static String dataKey = "data";

    public final String username;
    private final HashMap<String, ResearchStatus> techStatus;

    public PlayerResearch(String username) {
        this.username = username;
        techStatus = new HashMap<String, ResearchStatus>();
    }

    public Collection<ResearchStatus> getTechnologies() {
        return techStatus.values();
    }

    /**
     * @param name  Name of researchTechnology to mark as researched
     * @param force Pass true if you want the named researchTechnology to be added if it isn't already discovered. This
     *              will bypass discover checks. This will not post an event.
     * @return True if researchTechnology successfully marked as researched. False otherwise.
     */
    public boolean researchTechnology(String name, boolean force) {
        ITechnology rtech = Femtocraft.researchManager()
                .getTechnology(name);
        ResearchStatus tech = techStatus.get(name);
        if (tech == null && !force) {
            return false;
        }

        if (tech == null) {
            techStatus.put(name, new ResearchStatus(name, true));
            discoverNewTechs(rtech, false);
            sync();
            return true;
        }

        EventTechnology.TechnologyResearchedEvent event = new EventTechnology.TechnologyResearchedEvent(
                username, rtech);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            tech.researched = true;
            EntityPlayerMP player = MinecraftServer.getServer()
                    .getConfigurationManager().func_152612_a(username);
            if (player != null) {
                ITechnology techno = Femtocraft.researchManager()
                        .getTechnology(name);
                if (techno != null) {
                    FemtocraftUtils.sendMessageToPlayer(player, techno.getLevel().getTooltipEnum() + name
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

    private void discoverNewTechs(ITechnology discoverer, boolean notify) {
        for (ITechnology t : Femtocraft.researchManager()
                .getTechnologies()) {
            if (t.getPrerequisites() != null) {
                ResearchStatus ts = techStatus.get(t.getName());
                if (ts != null && ts.researched) {
                    continue;
                }

                boolean discovererPrereq = false;
                boolean shouldDiscover = true;
                for (String st : t.getPrerequisites()) {
                    ITechnology pt = Femtocraft.researchManager().getTechnology(st);

                    if (pt == discoverer && discoverer == null) {
                        discovererPrereq = true;
                    }
                    if (pt == null) continue;

                    ResearchStatus rts = techStatus.get(pt.getName());
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
                    discoverTechnology(t.getName());
                    if (notify) {
                        EntityPlayerMP player = MinecraftServer.getServer()
                                .getConfigurationManager()
                                .func_152612_a(username);
                        if (player != null) {
                            ITechnology techno = Femtocraft.researchManager()
                                    .getTechnology(t.getName());
                            if (techno != null) {
                                FemtocraftUtils.sendMessageToPlayer(player, "New technology "
                                                                            + techno.getLevel().getTooltipEnum()
                                                                            + t.getName() + EnumChatFormatting.RESET
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
            techStatus.put(name, new ResearchStatus(name));
            sync();
            return true;
        }
        return false;
    }

    public ResearchStatus getTechnology(String name) {
        return techStatus.get(name);
    }

    public ResearchStatus removeTechnology(String name) {
        return techStatus.remove(name);
    }

    public boolean canDiscoverTechnology(ITechnology tech) {
        if (tech.getPrerequisites() == null) {
            return true;
        }

        for (String str : tech.getPrerequisites()) {
            ITechnology prereq = Femtocraft.researchManager().getTechnology(str);
            if (prereq == null) continue;
            ResearchStatus ts = techStatus.get(prereq.getName());
            if (ts == null) {
                return false;
            }
            if (!ts.researched) {
                return false;
            }
        }

        return true;
    }

    public boolean hasDiscoveredTechnology(ITechnology tech) {
        return hasDiscoveredTechnology(tech.getName());
    }

    public boolean hasDiscoveredTechnology(String tech) {
        ResearchStatus ts = techStatus.get(tech);
        return ts != null;
    }

    public boolean hasResearchedTechnology(ITechnology tech) {
        return tech == null || hasResearchedTechnology(tech.getName());
    }

    public boolean hasResearchedTechnology(String tech) {
        if (tech == null || tech.equals("")) {
            return false;
        }

        ResearchStatus ts = techStatus.get(tech);
        return ts != null && ts.researched;
    }

    public void sync() {
        EntityPlayerMP player = MinecraftServer.getServer()
                .getConfigurationManager().func_152612_a(username);
        if (player == null) {
            return;
        }

        sync(player);
    }

    // ---------------------------------------------------------

    public void sync(EntityPlayerMP player) {
        Femtocraft.log(Level.FINE, "Sending research data to player: " + player.getCommandSenderName());
        FemtocraftPacketHandler.INSTANCE().sendTo(new MessageResearchPlayer(this), player);
    }

    public void saveToNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for (ResearchStatus status : techStatus.values()) {
            NBTTagCompound cs = new NBTTagCompound();
            cs.setString(techNameKey, status.tech);

            NBTTagCompound data = new NBTTagCompound();
            status.saveToNBTTagCompound(data);

            cs.setTag(dataKey, data);
            list.appendTag(cs);
        }

        compound.setTag(mapKey, list);
    }

    // ---------------------------------------------------------

    public void loadFromNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList(mapKey, 10);

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound cs = (NBTTagCompound) list.getCompoundTagAt(i);
            String techname = cs.getString(techNameKey);

            NBTTagCompound data = cs.getCompoundTag(dataKey);
            ResearchStatus status = new ResearchStatus(
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
