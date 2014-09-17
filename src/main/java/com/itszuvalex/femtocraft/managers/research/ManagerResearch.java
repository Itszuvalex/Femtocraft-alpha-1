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
import com.itszuvalex.femtocraft.managers.research.EventTechnology.TechnologyAddedEvent;
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

//TODO:  Separate players out into their own files
public class ManagerResearch {
    public static final String RESEARCH_CHANNEL = "Femtocraft" + ".rman";
    public static final String METALLURGY = "Metallurgy";
    public static final String BASIC_CIRCUITS = "Basic Circuits";
    public static final String MACHINING = "Machining";
    public static final String SCIENTIFIC_THEORY = "Scientific Theory";
    public static final String ALGORITHMS = "Algorithms";
    public static final String MECHANICAL_PRECISION = "Mechanical Precision";
    public static final String POWER_OF_NOTHING = "Power of Nothing";
    public static final String VACUUM_TUBES = "Vacuum Tubes";
    public static final String VACUUM_TUBE_HUB = "VacuumTube Hub";
    public static final String SUCTION_PIPES = "Suction Pipes";
    public static final String NANO_CIRCUITS = "Nano Circuits";
    public static final String ADVANCED_PROGRAMMING = "Advanced Programming";
    public static final String WORKLOAD_SCHEDULING = "Workload Scheduling";
    public static final String PATTERN_RECOGNITION = "Pattern Recognition";
    public static final String DISCRIMINATING_VACUUM_TUBE = "Discriminating Vacuum Tube";
    public static final String RESOURCE_OPTIMIZATION = "Resource Optimization";
    public static final String BASIC_CHEMISTRY = "Basic Chemistry";
    public static final String POTENTIALITY = "Potentiality";
    public static final String POTENTIALITY_STORAGE = "Potentiality Storage";
    public static final String POTENTIALITY_HARNESSING = "Potentiality Harnessing";
    public static final String POTENTIALITY_GENERATION = "Potentiality Generation";
    public static final String ADVANCED_CHEMISTRY = "Advanced Chemistry";
    public static final String ARTIFICIAL_MATERIALS = "Artificial Materials";
    public static final String FARENITE_STABILIZATION = "Farenite Stabilization";
    public static final String GEOTHERMAL_HARNESSING = "Geothermal Harnessing";
    public static final String POTENTIALITY_TRANSFORMATION = "Potentiality Transformation";
    public static final String INDUSTRIAL_STORAGE = "Industrial Storage";
    public static final String KINETIC_DISSOCIATION = "Kinetic Dissociation";
    public static final String ATOMIC_MANIPULATION = "Atomic Manipulation";
    public static final String DIGITIZED_WORKLOADS = "Digitized Workloads";
    public static final String SPACETIME_MANIPULATION = "Spacetime Manipulation";
    public static final String DIMENSIONAL_BRAIDING = "Dimensional Braiding";
    public static final String LOCALITY_ENTANGLER = "Locality Entangler";
    public static final String DIMENSIONAL_TRANSFORMATION = "Dimensional Transformation";
    public static final String TEMPORAL_PIPELINING = "Temporal Pipelining";
    public static final String REALITY_OVERCLOCKER = "Reality Overclocker";
    public static final String SPACETIME_EXPLOITATION = "Spacetime Exploitation";
    public static final String QUANTUM_INTERACTIVITY = "Quantum Interactivity";
    public static final String POTENTIAL_HARVESTING = "Potential Harvesting";
    public static final String THORIUM_FISSIBILITY = "Thorium Fissibility";
    public static final String HARNESSED_NUCLEAR_DECAY = "Harnessed Nuclear Decay";
    public static final String APPLIED_PARTICLE_PHYSICS = "Applied Particle Physics";
    public static final String QUANTUM_COMPUTING = "Quantum Computing";
    public static final String QUANTUM_ROBOTICS = "Quantum Robotics";
    public static final String ELEMENT_MANUFACTURING = "Element Manufacturing";
    public static final String DIMENSIONAL_SUPERPOSITIONS = "Dimensional Superpositions";
    public static final String MULTI_DIMENSIONAL_INDUSTRY = "Multi-Dimensional Industry";
    public static final String TEMPORAL_THREADING = "Temporal Threading";
    public static final String CAUSALITY_SINGULARITY = "Causality Singularity";
    public static final String DEMONIC_PARTICULATES = "Demonic Particulates";
    public static final String PARTICLE_EXCITATION = "Particle Excitation";
    public static final String PARTICLE_MANIPULATION = "Particle Manipulation";
    public static final String SPIN_RETENTION = "Spin Retention";
    public static final String SPONTANEOUS_GENERATION = "Spontaneous Generation";
    public static final String CORRUPTION_STABILIZATION = "Corruption Stabilization";
    public static final String STELLAR_MIMICRY = "Stellar Mimicry";
    public static final String ENERGY_CONVERSION = "Energy Conversion";
    public static final String MATTER_CONVERSION = "Matter Conversion";
    public static final String NETHER_STAR_FABRICATION = "Nether Star Fabrication";
    public static final String MOLECULAR_MANIPULATION = "Molecular Manipulation";
    public static final String MACROSCOPIC_STRUCTURES = "Macroscopic Structures";
    private static final String playerDataKey = "playerData";
    private static final String dataKey = "data";
    private static final String userKey = "username";
    ;
    // TODO: REMOVE ME
    private static boolean debug = true;
    private static final String DIRECTORY = "Research";
    private HashMap<String, ResearchTechnology> technologies;
    private HashMap<String, ResearchPlayer> playerData;
    private TechnologyGraph graph;
    private String lastWorldLoaded = "";

    public ManagerResearch() {
        technologies = new HashMap<String, ResearchTechnology>();
        playerData = new HashMap<String, ResearchPlayer>();
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


    public boolean addTechnology(ResearchTechnology tech) {
        TechnologyAddedEvent event = new TechnologyAddedEvent(tech);
        if (!MinecraftForge.EVENT_BUS.post(event)) {
            return technologies.put(tech.name, tech) != null;
        }
        return false;
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

    public Collection<ResearchTechnology> getTechnologies() {
        return technologies.values();
    }

    // --------------------------------------------------

    public boolean removeTechnology(ResearchTechnology tech) {
        return technologies.remove(tech.name) != null;
    }

    public ResearchTechnology getTechnology(String name) {
        return technologies.get(name);
    }

    public ResearchPlayer addPlayerResearch(String username) {
        // Return playerData for a player. If it doesn't exist, makes it.
        ResearchPlayer rp = playerData.get(username);
        if (rp != null) {
            return rp;
        }
        ResearchPlayer r = new ResearchPlayer(username);

        addFreeResearches(r);
        if (debug) {
            addAllResearches(r);
        }

        playerData.put(username, r);
        return r;
    }

    private void addKnownTechnologies(ResearchPlayer rp) {
        for (ResearchTechnology t : technologies.values()) {
            if (!rp.hasDiscoveredTechnology(t)) {
                if (t.researchedByDefault) {
                    rp.researchTechnology(t.name, true);
                    continue;
                }
                if (t.discoveredByDefault) {
                    rp.discoverTechnology(t.name);
                    continue;
                }
                if (t.prerequisites != null && t.prerequisites.length > 0) {
                    boolean shouldDiscover = true;
                    for (String pre : t.prerequisites) {
                        if (!shouldDiscover) continue;
                        if (!rp.hasResearchedTechnology(pre)) {
                            shouldDiscover = false;
                        }
                    }
                    if (shouldDiscover && rp.canDiscoverTechnology(t)) {
                        rp.discoverTechnology(t.name);
                    }
                }
                else {
                    rp.researchTechnology(t.name, true);
                }
            }
        }
    }

    // --------------------------------------------------

    private void addFreeResearches(ResearchPlayer research) {
        for (ResearchTechnology t : technologies.values()) {
            if (t.prerequisites == null) {
                research.researchTechnology(t.name, true);
                // research.discoverTechnology(t.name);
            }
        }
    }

    private void addAllResearches(ResearchPlayer research) {
        for (ResearchTechnology t : technologies.values()) {
            research.researchTechnology(t.name, true);
        }
    }

    public boolean removePlayerResearch(String username) {
        return playerData.remove(username) != null;
    }

    // --------------------------------------------------

    public ResearchPlayer getPlayerResearch(String username) {
        return playerData.get(username);
    }

    public boolean hasPlayerDiscoveredTechnology(String username,
                                                 ResearchTechnology tech) {
        return hasPlayerDiscoveredTechnology(username, tech.name);
    }

    // --------------------------------------------------

    public boolean hasPlayerDiscoveredTechnology(String username, String tech) {
        ResearchPlayer pr = playerData.get(username);
        return pr != null && pr.hasDiscoveredTechnology(tech);
    }

    public boolean hasPlayerResearchedTechnology(String username,
                                                 ResearchTechnology tech) {
        if (tech == null) return true;
        return hasPlayerResearchedTechnology(username, tech.name);
    }

    // --------------------------------------------------

    public boolean hasPlayerResearchedTechnology(String username, String tech) {
        ResearchPlayer pr = playerData.get(username);
        return pr != null && pr.hasResearchedTechnology(tech);
    }

    public void saveToNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = new NBTTagList();

        for (ResearchPlayer status : playerData.values()) {
            NBTTagCompound cs = new NBTTagCompound();
            cs.setString(userKey, status.username);

            NBTTagCompound data = new NBTTagCompound();
            status.saveToNBTTagCompound(data);

            cs.setCompoundTag(dataKey, data);
            list.appendTag(cs);
        }

        compound.setTag(playerDataKey, list);
    }

    // --------------------------------------------------

    public void loadFromNBTTagCompound(NBTTagCompound compound) {
        NBTTagList list = compound.getTagList(playerDataKey);

        for (int i = 0; i < list.tagCount(); ++i) {
            NBTTagCompound cs = (NBTTagCompound) list.tagAt(i);
            String username = cs.getString(userKey);

            NBTTagCompound data = cs.getCompoundTag(dataKey);
            ResearchPlayer status = new ResearchPlayer(username);
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

            for (ResearchPlayer pdata : playerData.values()) {
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
                    Femtocraft.logger.log(Level.SEVERE,
                            "Failed to save data for player " + pdata.username
                                    + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                    );
                    exception.printStackTrace();
                    continue;
                }
            }

        } catch (Exception e) {
            Femtocraft.logger.log(Level.SEVERE, "Failed to create folder "
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
                Femtocraft.logger.log(Level.WARNING, "No " + DIRECTORY
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
                    ResearchPlayer file = new ResearchPlayer(username);
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
                    Femtocraft.logger.log(Level.SEVERE,
                            "Failed to load data from file " + pdata.getName()
                                    + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
                    );
                    e.printStackTrace();
                }
            }

        } catch (Exception exception) {
            Femtocraft.logger.log(Level.SEVERE,
                    "Failed to load data from folder " + DIRECTORY
                            + " in world - " + FemtocraftFileUtils.savePathFemtocraft(world) + "."
            );
            exception.printStackTrace();
            return false;
        }

        return true;
    }

    public void syncResearch(ResearchPlayer rp) {
        playerData.put(rp.username, rp);
    }

    public TechNode getNode(ResearchTechnology pr) {
        return (TechNode) graph.getNode(pr.name);
    }
}
