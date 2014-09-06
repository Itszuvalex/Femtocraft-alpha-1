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
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import com.itszuvalex.femtocraft.research.gui.graph.TechNode;
import com.itszuvalex.femtocraft.research.gui.graph.TechnologyGraph;
import com.itszuvalex.femtocraft.research.gui.technology.*;
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.logging.Level;

//TODO:  Separate players out into their own files
public class ManagerResearch {
    public static final String RESEARCH_CHANNEL = "Femtocraft" + ".rman";
    private static final String playerDataKey = "playerData";
    private static final String dataKey = "data";
    private static final String userKey = "username";
    @Technology
    public static ResearchTechnology technologyMetallurgy = new ResearchTechnology(
            "Metallurgy", "Titanium, Thorium, Platinum", EnumTechLevel.MACRO,
            null, new ItemStack(Femtocraft.itemIngotTemperedTitanium),
            true, null);
    @Technology
    public static ResearchTechnology technologyBasicCircuits = new ResearchTechnology(
            "Basic Circuits", "Farenite, Circuit Boards", EnumTechLevel.MACRO,
            null, new ItemStack(Femtocraft.itemMicrochip), true,
            null, null) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyBasicCircuits.class;
        }
    };
    @Technology
    // TODO: replace machining icon with micro machine chassis item
    public static ResearchTechnology technologyMachining = new ResearchTechnology(
            "Machining", "Start your industry!", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyMetallurgy, technologyBasicCircuits)),
            new ItemStack(Femtocraft.itemMicroPlating), false,
            new ArrayList<ItemStack>(Arrays.asList(new ItemStack(
                    Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                    Femtocraft.itemMicrochip), new ItemStack(
                    Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                    Femtocraft.itemMicrochip), new ItemStack(
                    Femtocraft.itemConductivePowder), new ItemStack(
                    Femtocraft.itemMicrochip), new ItemStack(
                    Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                    Femtocraft.itemMicrochip), new ItemStack(
                    Femtocraft.itemIngotTemperedTitanium))),
            new ItemStack(
                    Femtocraft.itemMicroPlating)
    ) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyMachining.class;
        }
    };
    @Technology
    public static ResearchTechnology technologyAlgorithms = new ResearchTechnology(
            "Algorithms", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyMachining)), new ItemStack(
            Femtocraft.blockEncoder), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyMechanicalPrecision = new ResearchTechnology(
            "Mechanical Precision", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyMachining)), new ItemStack(
            Femtocraft.blockMicroFurnaceUnlit), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPowerOfNothing = new ResearchTechnology(
            "Power of Nothing", "\"Poof!\" It's nothing!", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyMechanicalPrecision)),
            new ItemStack(Femtocraft.itemVacuumCore),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyVacuumTubes = new ResearchTechnology(
            "Vacuum Tubes", "These tubes contain nothing!", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyPowerOfNothing)), new ItemStack(
            Femtocraft.blockVacuumTube), false,
            new ArrayList<ItemStack>()
    );

    // MACRO
    @Technology
    public static ResearchTechnology technologyVacuumTubeHub = new ResearchTechnology(
            "VacuumTube Hub", "A place for nothing to congregate.",
            EnumTechLevel.MICRO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyVacuumTubes)), null, false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologySuctionPipe = new ResearchTechnology(
            "Suction Pipes", "These pipes suck!", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyPowerOfNothing)), new ItemStack(
            Femtocraft.blockSuctionPipe), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyNanoCircuits = new ResearchTechnology(
            "Nano Circuits", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyMechanicalPrecision, technologyBasicCircuits)),
            new ItemStack(Femtocraft.itemNanochip), true,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyAdvancedProgramming = new ResearchTechnology(
            "Advanced Programming", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyNanoCircuits)),
            new ItemStack(Femtocraft.itemBasicAICore), false,
            new ArrayList<ItemStack>()
    );

    // MICRO
    @Technology
    public static ResearchTechnology technologyWorkloadScheduling = new ResearchTechnology(
            "Workload Scheduling", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyAdvancedProgramming)),
            new ItemStack(Femtocraft.itemSchedulerCore),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPatternRecognition = new ResearchTechnology(
            "Pattern Recognition", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyAdvancedProgramming)),
            new ItemStack(Femtocraft.itemLearningCore),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyDiscriminatingVacuumTube = new ResearchTechnology(
            "Discriminating Vacuum Tube", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyVacuumTubeHub, technologyPatternRecognition)),
            new ItemStack(Femtocraft.blockVacuumTube), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyResourceOptimization = new ResearchTechnology(
            "Resource Optimization", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyAdvancedProgramming)),
            new ItemStack(Femtocraft.itemManagerCore),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyBasicChemistry = new ResearchTechnology(
            "Basic Chemistry", "Composition of Matter", EnumTechLevel.MACRO,
            null, new ItemStack(Femtocraft.itemMineralLattice), true,
            null, null) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyBasicChemistry.class;
        }
    };
    @Technology
    // TODO: replace icon with micro coil
    public static ResearchTechnology technologyPotentiality = new ResearchTechnology(
            "Potentiality", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyBasicCircuits, technologyBasicChemistry)),
            new ItemStack(Femtocraft.blockMicroCable), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    // TODO: replace with Capacitor
    public static ResearchTechnology technologyPotentialityStorage = new ResearchTechnology(
            "Potentiality Storage", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyPotentiality, technologyMachining)),
            new ItemStack(Femtocraft.blockMicroCube), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPotentialityHarnessing = new ResearchTechnology(
            "Potentiality Harnessing", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyPotentiality)), new ItemStack(
            Femtocraft.blockMicroChargingBase), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPotentialityGeneration = new ResearchTechnology(
            "Potentiality Generation", "Build your potential!",
            EnumTechLevel.MICRO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyMechanicalPrecision,
                    technologyPotentialityHarnessing)
    ), new ItemStack(Femtocraft.blockCryoGenerator),
            false, new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyOreRefining = new ResearchTechnology(
//            "Ore Refining", "Build your potential!", EnumTechLevel.MICRO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyMetallurgy, technologyMolecularManipulation)),
//            null, false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologyAdvancedChemistry = new ResearchTechnology(
            "Advanced Chemistry", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyPotentialityGeneration,
                    technologyBasicChemistry/*,
                    technologyOreRefining*/)), new ItemStack(Femtocraft
            .itemCrystallite), true,
            new ArrayList<ItemStack>(),
            null
    ) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyAdvancedChemistry.class;
        }
    };
    // NANO
    @Technology
    public static ResearchTechnology technologyArtificialMaterials = new ResearchTechnology(
            "Artificial Materials", "Make what you need.", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyNanoCircuits, technologyAdvancedChemistry)),
            new ItemStack(Femtocraft.itemNanoPlating), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyFareniteStabilization = new ResearchTechnology(
            "Farenite Stabilization", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyNanoCircuits, technologyAdvancedChemistry,
                    technologyArtificialMaterials)), new ItemStack(Femtocraft
            .itemNanoCoil), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyGeothermalHarnessing = new ResearchTechnology(
            "Geothermal Harnessing", "Harness the geological thermal energy.",
            EnumTechLevel.NANO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyFareniteStabilization,
                    technologyAdvancedChemistry,
                    technologyPotentialityHarnessing)
    ), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPotentialityTransformation = new ResearchTechnology(
            "Potentiality Transformation", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyFareniteStabilization)),
            new ItemStack(Femtocraft.blockOrbitalEqualizer), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyIndustrialStorage = new ResearchTechnology(
            "Industrial Storage", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyPotentialityTransformation,
                    technologyResourceOptimization)), new ItemStack(
            Femtocraft.blockNanoCubeFrame), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyKineticDissociation = new ResearchTechnology(
            "Kinetic Dissociation", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyWorkloadScheduling,
                            technologyArtificialMaterials)), new ItemStack(
            Femtocraft.blockNanoInnervatorUnlit), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyAtomicManipulation = new ResearchTechnology(
            "Atomic Manipulation", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyArtificialMaterials,
                            technologyWorkloadScheduling)), new ItemStack(
            Femtocraft.blockNanoDismantler), false,
            new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyMatterExtension = new ResearchTechnology(
//            "Matter Extension", "", EnumTechLevel.NANO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyAtomicManipulation)), null,
//            false, new ArrayList<ItemStack>()
//    );
//    @Technology
//    public static ResearchTechnology technologyEndothermicReactionReversal = new ResearchTechnology(
//            "Endothermic Reaction Reversal",
//            "Undo those pesky combustion reactions.", EnumTechLevel.NANO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyAtomicManipulation)), null,
//            false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologyDigitizedWorkloads = new ResearchTechnology(
            "Digitized Workloads", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyAtomicManipulation,
                    technologyResourceOptimization)),
            new ItemStack(Femtocraft.itemDigitalSchematic), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologySpacetimeManipulation = new ResearchTechnology(
            "Spacetime Manipulation",
            "The question is: When did you ACTUALLY finish researching this?",
            EnumTechLevel.NANO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyAdvancedChemistry)),
            new ItemStack(Femtocraft.itemDimensionalMonopole),
            true, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyDimensionalBraiding = new ResearchTechnology(
            "Dimensional Braiding", "Interweave reality",
            EnumTechLevel.DIMENSIONAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyArtificialMaterials,
                    technologySpacetimeManipulation, technologyAtomicManipulation)
    ), new ItemStack(Femtocraft.blockNanoEnmesher),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyLocalityEntangler = new ResearchTechnology(
            "Locality Entangler", "", EnumTechLevel.DIMENSIONAL,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyDimensionalBraiding)),
            new ItemStack(Femtocraft.itemInfallibleEstimator),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyDimensionalTransformation = new ResearchTechnology(
            "Dimensional Transformation", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyDimensionalBraiding)),
            new ItemStack(Femtocraft.itemIngotMalenite), true,
            new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyStarMatterRecollection = new ResearchTechnology(
//            "Star Matter Recollection", "Recollecting what has been spread.",
//            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
//            Arrays.asList(technologyDimensionalTransformation)), null,
//            false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologyTemporalPipelining = new ResearchTechnology(
            "Temporal Pipelining",
            "Start working on the next second, before it begins.",
            EnumTechLevel.TEMPORAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyArtificialMaterials,
                    technologySpacetimeManipulation, technologyAtomicManipulation)
    ), new ItemStack(Femtocraft.blockNanoHorologe),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyRealityOverclocker = new ResearchTechnology(
            "Reality Overclocker", "", EnumTechLevel.TEMPORAL,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyTemporalPipelining)),
            new ItemStack(Femtocraft.itemInfallibleEstimator),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologySpacetimeExploitation = new ResearchTechnology(
            "Spacetime Exploitation", "Exploiting reality itself.",
            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyLocalityEntangler,
                    technologyRealityOverclocker)
    ), new ItemStack(Femtocraft.itemPandoraCube), true,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyQuantumInteractivity = new ResearchTechnology(
            "Quantum Interactivity", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyDimensionalTransformation,
                    technologyNanoCircuits, technologyDimensionalBraiding,
                    technologyTemporalPipelining)), new ItemStack(Femtocraft
            .itemCharosGate), true,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyPotentialHarvesting = new ResearchTechnology(
            "Potential Harvesting", "Headhunters Unite!", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyPotentialityStorage,
                    technologyPotentialityHarnessing)),
            new ItemStack(Femtocraft.blockMicroChargingCapacitor), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyThoriumFissibility = new ResearchTechnology(
            "Thorium Fissibility",
            "",
            EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyPotentialHarvesting, technologyAdvancedChemistry)),
            new ItemStack(Femtocraft.itemThoriumRod), false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyHarnessedNuclearDecay = new ResearchTechnology(
            "Harnessed Nuclear Decay", "", EnumTechLevel.NANO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyThoriumFissibility,
                    technologyResourceOptimization,
                    technologyGeothermalHarnessing)),
            new ItemStack(Femtocraft.blockFissionReactorCore), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyAppliedParticlePhysics = new ResearchTechnology(
            "Applied Particle Physics", "Like theoretical particle physics.",
            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyHarnessedNuclearDecay,
                    technologyAdvancedChemistry)
    ), new ItemStack(Femtocraft.itemCubit), true,
            new ArrayList<ItemStack>(),
            null
    ) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyAppliedParticlePhysics.class;
        }
    };
    @Technology
    public static ResearchTechnology technologyQuantumComputing = new ResearchTechnology(
            "Quantum Computing", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyQuantumInteractivity,
                    technologyAdvancedProgramming,
                    technologyAppliedParticlePhysics)), new ItemStack(Femtocraft.itemHerculesDrive), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyQuantumRobotics = new ResearchTechnology(
            "Quantum Robotics", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(technologyQuantumComputing)),
            new ItemStack(Femtocraft.itemErinyesCircuit), false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyElementManufacturing = new ResearchTechnology(
            "Element Manufacturing", "Create circles, spheres, and ovals!",
            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyQuantumRobotics)),
            new ItemStack(Femtocraft.itemFemtoPlating),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyDimensionalSuperpositions = new ResearchTechnology(
            "Dimensional Superpositions", "It's a layer effect.",
            EnumTechLevel.DIMENSIONAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologySpacetimeExploitation,
                    technologyElementManufacturing)
    ), new ItemStack(Femtocraft.blockFemtoEntangler),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyMultiDimensionalIndustry = new ResearchTechnology(
            "Multi-Dimensional Industry", "Share the load.",
            EnumTechLevel.DIMENSIONAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyDimensionalSuperpositions)),
            new ItemStack(Femtocraft.itemInfiniteVolumePolychora),
            false, new ArrayList<ItemStack>()
    );
    // FEMTO
    @Technology
    public static ResearchTechnology technologyTemporalThreading = new ResearchTechnology(
            "Temporal Threading", "Having multiple timelines do work at once.",
            EnumTechLevel.TEMPORAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologySpacetimeExploitation,
                    technologyElementManufacturing)
    ), new ItemStack(Femtocraft.blockFemtoChronoshifter),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyCausalitySingularity = new ResearchTechnology(
            "Causality Singularity",
            "If you researched this, you never would have researched this.",
            EnumTechLevel.TEMPORAL, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyTemporalThreading)),
            new ItemStack(Femtocraft.itemInfinitelyRecursiveALU),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyDemonicParticulates = new ResearchTechnology(
            "Demonic Particulates", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyDimensionalTransformation,
                    technologyElementManufacturing)),
            new ItemStack(Femtocraft.itemFemtoCoil), false,
            new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyPerfectScheduling = new ResearchTechnology(
//            "Perfect Scheduling", "Never miss a meeting!", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologyParticleExcitation = new ResearchTechnology(
            "Particle Excitation", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyElementManufacturing)), new ItemStack(
            Femtocraft.blockFemtoImpulserUnlit), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyParticleManipulation = new ResearchTechnology(
            "Particle Manipulation", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyElementManufacturing)), new ItemStack(
            Femtocraft.blockFemtoRepurposer), false,
            new ArrayList<ItemStack>()
    );

    //    @Technology
//    public static ResearchTechnology technologyNBodySimulations = new ResearchTechnology(
//            "N-Body Simulations", "Simulate everything.", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologySpinRetention = new ResearchTechnology(
            "Spin Retention",
            "",
            EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyParticleManipulation)),
            new ItemStack(Femtocraft.itemQuantumSchematic), false, new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyQuantumComputing = new ResearchTechnology(
//            "Quantum Computing",
//            "",
//            EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyQuantumInteractivity, technologyAppliedParticlePhysics)),
//            new ItemStack(Femtocraft.itemHerculesDrive), false, new ArrayList<ItemStack>()
//    );
    //    @Technology
//    public static ResearchTechnology technologySingularityCalculator = new ResearchTechnology(
//            "Singularity Calculator",
//            "What are the odds you can't calculate the odds?",
//            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
//            Arrays.asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologySpontaneousGeneration = new ResearchTechnology(
            "Spontaneous Generation", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyQuantumRobotics,
                    technologyDemonicParticulates)), new ItemStack(
            Femtocraft.blockNullEqualizer), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyCorruptionStabilization = new ResearchTechnology(
            "Corruption Stabilization", "", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologySpontaneousGeneration)), new ItemStack(
            Femtocraft.blockFemtoCubeFrame), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyStellarMimicry = new ResearchTechnology(
            "Stellar Mimicry", "Make your own pet star!", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyStarMatterRecollection,
                    technologyDemonicParticulates,
                    technologyElementManufacturing)),
            new ItemStack(Femtocraft.blockStellaratorCore), false,
            new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyEnergyConversion = new ResearchTechnology(
            "Energy Conversion", "Plasma to Energy", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyStellarMimicry)),
            new ItemStack(Femtocraft.blockPlasmaTurbine),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyMatterConversion = new ResearchTechnology(
            "Matter Conversion", "Plasma to Mass", EnumTechLevel.FEMTO,
            new ArrayList<ResearchTechnology>(Arrays
                    .asList(technologyStellarMimicry)),
            new ItemStack(Femtocraft.blockPlasmaCondenser),
            false, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyNetherStarFabrication = new ResearchTechnology(
            "Nether Star Fabrication", "Creating the unimaginable.",
            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
            Arrays.asList(technologyStellarMimicry,
                    technologyCorruptionStabilization,
                    technologyParticleManipulation)
    ), new ItemStack(
            Item.netherStar), true, new ArrayList<ItemStack>()
    );
    @Technology
    public static ResearchTechnology technologyMolecularManipulation = new ResearchTechnology(
            "Molecular Manipulation", "", EnumTechLevel.MICRO,
            new ArrayList<ResearchTechnology>(Arrays.asList(
                    technologyAlgorithms, technologyMechanicalPrecision,
                    technologyBasicChemistry)), new ItemStack(
            Femtocraft.blockMicroDeconstructor), false,
            new ArrayList<ItemStack>()
    );
    //    @Technology
//    public static ResearchTechnology technologyParticleDestruction = new ResearchTechnology(
//            "Particle Destruction", "", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyParticleManipulation,
//                    technologySingularityCalculator)), null, false,
//            new ArrayList<ItemStack>()
//    );
    @Technology
    public static ResearchTechnology technologyMacroscopicStructure = new ResearchTechnology(
            "Macroscopic Structures", "The patterns everything take.",
            EnumTechLevel.MACRO, null, new ItemStack(Item.pickaxeIron),
            true, null, null) {
        @Override
        public Class<? extends GuiTechnology> getGuiClass() {
            return GuiTechnologyMacroscopicStructure.class;
        }
    };
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

        loadTechnologies();
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

    private void loadTechnologies() {
        Field[] fields = ManagerResearch.class.getFields();
        for (Field field : fields) {
            if (field.getAnnotation(Technology.class) != null) {
                try {
                    ResearchTechnology tech = (ResearchTechnology) field.get(null);
                    if (tech == null) {
                        Femtocraft.logger.log(Level.SEVERE,
                                field.getName() + " failed to initialize and cannot be added to Technologies list.");
                        continue;
                    }
                    addTechnology(tech);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

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
                if (t.prerequisites != null && !t.prerequisites.isEmpty()) {
                    boolean shouldDiscover = true;
                    for (ResearchTechnology pre : t.prerequisites) {
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

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Technology {
    }
}
