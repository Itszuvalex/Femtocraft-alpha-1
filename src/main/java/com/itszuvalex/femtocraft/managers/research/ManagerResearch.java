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
import com.itszuvalex.femtocraft.api.events.EventTechnology.TechnologyAddedEvent;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.research.gui.graph.TechNode;
import com.itszuvalex.femtocraft.research.gui.graph.TechnologyGraph;
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

public class ManagerResearch {
    public static final String RESEARCH_CHANNEL = Femtocraft.RESEARCH_CHANNEL();
    private static final String playerDataKey = "playerData";
    private static final String dataKey = "data";
    private static final String userKey = "username";
    ;
    // TODO: REMOVE ME
    private static boolean debug = true;
    private static final String DIRECTORY = "Research";
    private HashMap<String, ITechnology> technologies;
    private HashMap<String, PlayerResearch> playerData;
    private TechnologyGraph graph;
    private String lastWorldLoaded = "";

    public ManagerResearch() {
        technologies = new HashMap<>();
        playerData = new HashMap<>();
    }

    // public static ResearchTechnology technologyPaperSchematic = new
    // ResearchTechnology(
    // "Paper Schematic", "Like IKEA for reality!", EnumTechLevel.MICRO,
    // new ArrayList<ResearchTechnology>(Arrays.asList(
    // technologyWorldStructure, technologyMachining)),
    // new ItemStack(Femtocraft.itemPaperSchematic), 1, -1, false,
    // new ArrayList<ItemStack>(Arrays.asList(new ItemStack(Item.paper),
    // new ItemStack(Item.paper), new ItemStack(Item.paper))),
    // GuiTechnologyPaperSchematic.class, new ItemStack(
    // Femtocraft.itemPaperSchematic, 1));


    public boolean addTechnology(ITechnology tech) {
        return !MinecraftForge.EVENT_BUS.post(new TechnologyAddedEvent(tech)) &&
               technologies.put(tech.getName(), tech) != null;
    }

    /**
     * Create and calculate the DAG for technologies.
     */
    public void calculateGraph() {
        graph = new TechnologyGraph(technologies);
        graph.computePlacements();
    }

    //
    // public void getDummyTechs() {
    // return graph.getDummyTechs();
    // }

    public Collection<ITechnology> getTechnologies() {
        return technologies.values();
    }

    // --------------------------------------------------

    public boolean removeTechnology(ITechnology tech) {
        return technologies.remove(tech.getName()) != null;
    }

    public ITechnology getTechnology(String name) {
        return technologies.get(name);
    }

    public PlayerResearch addPlayerResearch(String username) {
        // Return playerData for a player. If it doesn't exist, makes it.
        PlayerResearch rp = playerData.get(username);
        if (rp != null) {
            return rp;
        }
        PlayerResearch r = new PlayerResearch(username);

        addFreeResearches(r);
        if (debug) {
            addAllResearches(r);
        }

        playerData.put(username, r);
        return r;
    }

    private void addKnownTechnologies(PlayerResearch rp) {
        for (ITechnology t : technologies.values()) {
            if (!rp.hasDiscoveredTechnology(t)) {
                if (t.isResearchedByDefault()) {
                    rp.researchTechnology(t.getName(), true);
                    continue;
                }
                if (t.isDiscoveredByDefault()) {
                    rp.discoverTechnology(t.getName());
                    continue;
                }
                if (t.getPrerequisites() != null && t.getPrerequisites().size() > 0) {
                    boolean shouldDiscover = true;
                    for (String pre : t.getPrerequisites()) {
                        if (!shouldDiscover) continue;
                        if (!rp.hasResearchedTechnology(pre)) {
                            shouldDiscover = false;
                        }
                    }
                    if (shouldDiscover && rp.canDiscoverTechnology(t)) {
                        rp.discoverTechnology(t.getName());
                    }
                } else {
                    rp.researchTechnology(t.getName(), true);
                }
            }
        }
    }

    // --------------------------------------------------

    private void addFreeResearches(PlayerResearch research) {
        for (ITechnology t : technologies.values()) {
            if (t.getPrerequisites() == null) {
                research.researchTechnology(t.getName(), true);
                // research.discoverTechnology(t.name);
            }
        }
    }

    private void addAllResearches(PlayerResearch research) {
        for (ITechnology t : technologies.values()) {
            research.researchTechnology(t.getName(), true);
        }
    }

    public boolean removePlayerResearch(String username) {
        return playerData.remove(username) != null;
    }

    // --------------------------------------------------

    public PlayerResearch getPlayerResearch(String username) {
        return playerData.get(username);
    }

    public boolean hasPlayerDiscoveredTechnology(String username,
                                                 ITechnology tech) {
        return hasPlayerDiscoveredTechnology(username, tech.getName());
    }

    // --------------------------------------------------

    public boolean hasPlayerDiscoveredTechnology(String username, String tech) {
        PlayerResearch pr = playerData.get(username);
        return pr != null && pr.hasDiscoveredTechnology(tech);
    }

    public boolean hasPlayerResearchedTechnology(String username,
                                                 ITechnology tech) {
        if (tech == null) return true;
        return hasPlayerResearchedTechnology(username, tech.getName());
    }

    // --------------------------------------------------

    public boolean hasPlayerResearchedTechnology(String username, String tech) {
        PlayerResearch pr = playerData.get(username);
        return pr != null && pr.hasResearchedTechnology(tech);
    }

    public void saveToNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for (PlayerResearch status : playerData.values()) {
            NBTTagCompound cs = new NBTTagCompound();
            cs.setString(userKey, status.username);

            NBTTagCompound data = new NBTTagCompound();
            status.saveToNBTTagCompound(data);

            cs.setTag(dataKey, data);
            list.appendTag(cs);
        }

        compound.setTag(playerDataKey, list);
    }

    // --------------------------------------------------

    public void loadFromNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList(playerDataKey, 10);

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound cs = list.getCompoundTagAt(i);
            String username = cs.getString(userKey);

            NBTTagCompound data = cs.getCompoundTag(dataKey);
            PlayerResearch status = new PlayerResearch(username);
            status.loadFromNBTTagCompound(data);

            playerData.put(username, status);
        }
    }

    public boolean save(World world) {
        if (world.isRemote) return true;

        try {
            File folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY);
            if (!folder.exists()) {
                folder.mkdirs();
            }

            for (PlayerResearch pdata : playerData.values()) {
                try {
                    File file = new File(folder, pdata.username + ".dat");
                    if (!file.exists()) {
                        file.createNewFile();
                    }

                    FileOutputStream fileoutputstream = new FileOutputStream(
                            file);
                    NBTTagCompound data = new NBTTagCompound();
                    pdata.saveToNBTTagCompound(data);
                    CompressedStreamTools.writeCompressed(data,
                            fileoutputstream);
                    fileoutputstream.close();
                } catch (Exception exception) {
                    Femtocraft.log(Level.SEVERE,
                            "Failed to save data for player " + pdata.username
                            + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                    );
                    exception.printStackTrace();
                    continue;
                }
            }

        } catch (Exception e) {
            Femtocraft.log(Level.SEVERE, "Failed to create folder "
                                         + FemtocraftFileUtils.savePathFemtocraft(world) + File.pathSeparator +
                                         DIRECTORY + ".");
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean load(World world) {
        if (world.isRemote) return true;

        String worldName = world.getWorldInfo().getWorldName();
        if (lastWorldLoaded.equals(worldName)) {
            return false;
        }

        lastWorldLoaded = worldName;
        playerData.clear();

        try {
            File folder = new File(FemtocraftFileUtils.savePathFemtocraft(world), DIRECTORY);
            if (!folder.exists()) {
                Femtocraft.log(Level.WARNING, "No " + DIRECTORY
                                              + " folder found for world - " +
                                              FemtocraftFileUtils.savePathFemtocraft(world) + ".");
                return false;
            }

            for (File pdata : folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return name.endsWith(".dat");
                }
            })) {
                try {
                    FileInputStream fileinputstream = new FileInputStream(pdata);
                    NBTTagCompound data = CompressedStreamTools
                            .readCompressed(fileinputstream);
                    String username = pdata.getName().substring(0,
                            pdata.getName().length() - 4);
                    // NBTTagCompound data = CompressedStreamTools.read(file);
                    PlayerResearch file = new PlayerResearch(username);
                    file.loadFromNBTTagCompound(data);
                    fileinputstream.close();
                    //If another mod is added since last startup, we won't have known to discover its technologies.
                    // This
                    // scans the research tree for technologies the player hasn't discovered,
                    // but should have.  Additionally,
                    // it'll automatically research technologies with no prerequisites.
                    addKnownTechnologies(file);
                    playerData.put(username, file);
                } catch (Exception e) {
                    Femtocraft.log(Level.SEVERE,
                            "Failed to load data from file " + pdata.getName()
                            + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                    );
                    e.printStackTrace();
                }
            }

        } catch (Exception exception) {
            Femtocraft.log(Level.SEVERE,
                    "Failed to load data from folder " + DIRECTORY
                    + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public void syncResearch(PlayerResearch rp) {
        Femtocraft.log(Level.FINE, "Syncing research for player: " + rp.username);
        playerData.put(rp.username, rp);
    }

    public TechNode getNode(ITechnology pr) {
        return (TechNode) graph.getNode(pr.getName());
    }
}
