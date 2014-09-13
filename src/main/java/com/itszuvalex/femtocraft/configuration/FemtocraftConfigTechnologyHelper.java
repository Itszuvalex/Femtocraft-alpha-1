package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.research.gui.GuiTechnology;
import com.itszuvalex.femtocraft.research.gui.technology.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Chris on 9/11/2014.
 */
public class FemtocraftConfigTechnologyHelper {
    public static final String SECTION_KEY = "Technologies";
    private final Configuration config;

    public FemtocraftConfigTechnologyHelper(Configuration config) {
        this.config = config;
    }

    public void loadTechnologies() {
        List<ResearchTechnology> loadedTechnologies;
        if (config.get(SECTION_KEY, "Use custom classes", false, "Set to true if you define new technologies in this " +
                                                                 "section.  If false, " +
                                                                 "Femtocraft will only look for technologies of " +
                                                                 "certain names, specifically the ones bundled with " +
                                                                 "the vanilla version.  If true, " +
                                                                 "it will instead look at all keys in this section " +
                                                                 "and attempt to load each as a distinct Technology, " +
                                                                 "and will not load ANY of the original technologies" +
                                                                 ".").getBoolean(false)) {
            loadedTechnologies = loadCustomTechnologies();
        } else {
            loadedTechnologies = loadDefaultTechnologies();
        }

        if (config.hasChanged()) {
            config.save();
        }

        registerTechnologies(loadedTechnologies);
    }

    private List<ResearchTechnology> loadCustomTechnologies() {
        List<ResearchTechnology> ret = new ArrayList<ResearchTechnology>();
        ConfigCategory cat = config.getCategory(SECTION_KEY);
        Set<ConfigCategory> techs = cat.getChildren();
        for (ConfigCategory cc : techs) {
            ret.add(loadTechnology(cc.getQualifiedName()));
        }
        return ret;
    }

    private List<ResearchTechnology> loadDefaultTechnologies() {
        return new ArrayList<ResearchTechnology>(Arrays.asList(
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.METALLURGY, "Titanium, Thorium, Platinum", EnumTechLevel.MACRO,
                        null, new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                        true, null)),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.BASIC_CIRCUITS, "Farenite, Circuit Boards", EnumTechLevel.MACRO,
                        null, new ItemStack(Femtocraft.itemMicrochip), true,
                        null, null) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {
                        return GuiTechnologyBasicCircuits.class;
                    }
                }),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MACHINING, "Start your industry!", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.METALLURGY, ManagerResearch.BASIC_CIRCUITS)),
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
                }),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SCIENTIFIC_THEORY, "Gentlemen, start your research!", EnumTechLevel.MACRO,
                        null, new ItemStack(Femtocraft.blockResearchConsole), true, null
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ALGORITHMS, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.MACHINING)), new ItemStack(
                        Femtocraft.blockEncoder), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MECHANICAL_PRECISION, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.MACHINING)), new ItemStack(
                        Femtocraft.blockMicroFurnaceUnlit), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POWER_OF_NOTHING, "\"Poof!\" It's nothing!", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.MECHANICAL_PRECISION)),
                        new ItemStack(Femtocraft.itemVacuumCore),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.VACUUM_TUBES, "These tubes contain nothing!", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.POWER_OF_NOTHING)), new ItemStack(
                        Femtocraft.blockVacuumTube), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.VACUUM_TUBE_HUB, "A place for nothing to congregate.",
                        EnumTechLevel.MICRO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.VACUUM_TUBES)), null, false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SUCTION_PIPES, "These pipes suck!", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.POWER_OF_NOTHING)), new ItemStack(
                        Femtocraft.blockSuctionPipe), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.NANO_CIRCUITS, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.MECHANICAL_PRECISION, ManagerResearch.BASIC_CIRCUITS)),
                        new ItemStack(Femtocraft.itemNanochip), true,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ADVANCED_PROGRAMMING, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.NANO_CIRCUITS)),
                        new ItemStack(Femtocraft.itemBasicAICore), false,
                        new ArrayList<ItemStack>()
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.WORKLOAD_SCHEDULING, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ADVANCED_PROGRAMMING)),
                        new ItemStack(Femtocraft.itemSchedulerCore),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PATTERN_RECOGNITION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ADVANCED_PROGRAMMING)),
                        new ItemStack(Femtocraft.itemLearningCore),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DISCRIMINATING_VACUUM_TUBE, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.VACUUM_TUBE_HUB, ManagerResearch.PATTERN_RECOGNITION)),
                        new ItemStack(Femtocraft.blockVacuumTube), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.RESOURCE_OPTIMIZATION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ADVANCED_PROGRAMMING)),
                        new ItemStack(Femtocraft.itemManagerCore),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.BASIC_CHEMISTRY, "Composition of Matter", EnumTechLevel.MACRO,
                        null, new ItemStack(Femtocraft.itemMineralLattice), true,
                        null, null) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {
                        return GuiTechnologyBasicChemistry.class;
                    }
                }),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.BASIC_CIRCUITS, ManagerResearch.BASIC_CHEMISTRY)),
                        new ItemStack(Femtocraft.blockMicroCable), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_STORAGE, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.POTENTIALITY, ManagerResearch.MACHINING)),
                        new ItemStack(Femtocraft.blockMicroCube), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_HARNESSING, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.POTENTIALITY)), new ItemStack(
                        Femtocraft.blockMicroChargingBase), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_GENERATION, "Build your potential!",
                        EnumTechLevel.MICRO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.MECHANICAL_PRECISION,
                                ManagerResearch.POTENTIALITY_HARNESSING)
                ), new ItemStack(Femtocraft.blockCryoGenerator),
                        false, new ArrayList<ItemStack>()
                )),
                //    @Technology
//    public static ResearchTechnology technologyOreRefining = new ResearchTechnology(
//            "Ore Refining", "Build your potential!", EnumTechLevel.MICRO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyMetallurgy, technologyMolecularManipulation)),
//            null, false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ADVANCED_CHEMISTRY, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.POTENTIALITY_GENERATION,
                                ManagerResearch.BASIC_CHEMISTRY/*,
                    technologyOreRefining*/)), new ItemStack(Femtocraft
                        .itemCrystallite), true,
                        new ArrayList<ItemStack>(),
                        null
                ) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {
                        return GuiTechnologyAdvancedChemistry.class;
                    }
                }),
                // NANO
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ARTIFICIAL_MATERIALS, "Make what you need.", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.NANO_CIRCUITS, ManagerResearch.ADVANCED_CHEMISTRY)),
                        new ItemStack(Femtocraft.itemNanoPlating), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.FARENITE_STABILIZATION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.NANO_CIRCUITS, ManagerResearch.ADVANCED_CHEMISTRY,
                                ManagerResearch.ARTIFICIAL_MATERIALS)), new ItemStack(Femtocraft
                        .itemNanoCoil), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.GEOTHERMAL_HARNESSING, "Harness the geological thermal energy.",
                        EnumTechLevel.NANO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.FARENITE_STABILIZATION,
                                ManagerResearch.ADVANCED_CHEMISTRY,
                                ManagerResearch.POTENTIALITY_HARNESSING)
                ), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_TRANSFORMATION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.FARENITE_STABILIZATION)),
                        new ItemStack(Femtocraft.blockOrbitalEqualizer), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.INDUSTRIAL_STORAGE, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.POTENTIALITY_TRANSFORMATION,
                                ManagerResearch.RESOURCE_OPTIMIZATION)), new ItemStack(
                        Femtocraft.blockNanoCubeFrame), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.KINETIC_DISSOCIATION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.WORKLOAD_SCHEDULING,
                                        ManagerResearch.ARTIFICIAL_MATERIALS, ManagerResearch.RESOURCE_OPTIMIZATION))
                        , new ItemStack(
                        Femtocraft.blockNanoInnervatorUnlit), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ATOMIC_MANIPULATION, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ARTIFICIAL_MATERIALS,
                                        ManagerResearch.WORKLOAD_SCHEDULING, ManagerResearch.RESOURCE_OPTIMIZATION)),
                        new ItemStack(
                        Femtocraft.blockNanoDismantler), false,
                        new ArrayList<ItemStack>()
                )),
                //    public static ResearchTechnology technologyMatterExtension = new ResearchTechnology(
//            "Matter Extension", "", EnumTechLevel.NANO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyAtomicManipulation)), null,
//            false, new ArrayList<ItemStack>()
//    );
//    public static ResearchTechnology technologyEndothermicReactionReversal = new ResearchTechnology(
//            "Endothermic Reaction Reversal",
//            "Undo those pesky combustion reactions.", EnumTechLevel.NANO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyAtomicManipulation)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIGITIZED_WORKLOADS, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.ATOMIC_MANIPULATION,
                                ManagerResearch.RESOURCE_OPTIMIZATION)),
                        new ItemStack(Femtocraft.itemDigitalSchematic), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPACETIME_MANIPULATION,
                        "The question is: When did you ACTUALLY finish researching this?",
                        EnumTechLevel.NANO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.ADVANCED_CHEMISTRY)),
                        new ItemStack(Femtocraft.itemDimensionalMonopole),
                        true, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_BRAIDING, "Interweave reality",
                        EnumTechLevel.DIMENSIONAL, new ArrayList<String>(
                        Arrays.asList("Artifical Materials",
                                ManagerResearch.SPACETIME_MANIPULATION, ManagerResearch.ATOMIC_MANIPULATION)
                ), new ItemStack(Femtocraft.blockNanoEnmesher),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.LOCALITY_ENTANGLER, "", EnumTechLevel.DIMENSIONAL,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.DIMENSIONAL_BRAIDING)),
                        new ItemStack(Femtocraft.itemPanLocationalComputer),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_TRANSFORMATION, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.DIMENSIONAL_BRAIDING)),
                        new ItemStack(Femtocraft.itemIngotMalenite), true,
                        new ArrayList<ItemStack>()
                )),
                //    public static ResearchTechnology technologyStarMatterRecollection = new ResearchTechnology(
//            "Star Matter Recollection", "Recollecting what has been spread.",
//            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
//            Arrays.asList(technologyDimensionalTransformation)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.TEMPORAL_PIPELINING,
                        "Start working on the next second, before it begins.",
                        EnumTechLevel.TEMPORAL, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.ARTIFICIAL_MATERIALS,
                                ManagerResearch.SPACETIME_MANIPULATION, ManagerResearch.ATOMIC_MANIPULATION)
                ), new ItemStack(Femtocraft.blockNanoHorologe),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.REALITY_OVERCLOCKER, "", EnumTechLevel.TEMPORAL,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.TEMPORAL_PIPELINING)),
                        new ItemStack(Femtocraft.itemInfallibleEstimator),
                        false, new ArrayList<ItemStack>()
                ) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {return GuiTechnologyRealityOverlocker.class;}
                }),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPACETIME_EXPLOITATION, "Exploiting reality itself.",
                        EnumTechLevel.FEMTO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.LOCALITY_ENTANGLER,
                                ManagerResearch.REALITY_OVERCLOCKER)
                ), new ItemStack(Femtocraft.itemPandoraCube), true,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_INTERACTIVITY, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.DIMENSIONAL_TRANSFORMATION,
                                ManagerResearch.NANO_CIRCUITS, ManagerResearch.DIMENSIONAL_BRAIDING,
                                ManagerResearch.TEMPORAL_PIPELINING)), new ItemStack(Femtocraft
                        .itemCharosGate), true,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIAL_HARVESTING, "Headhunters Unite!", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.POTENTIALITY_STORAGE,
                                ManagerResearch.POTENTIALITY_HARNESSING)),
                        new ItemStack(Femtocraft.blockMicroChargingCapacitor), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.THORIUM_FISSIBILITY,
                        "",
                        EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.POTENTIAL_HARVESTING, ManagerResearch.ADVANCED_CHEMISTRY)),
                        new ItemStack(Femtocraft.itemIngotThFaSalt), false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.HARNESSED_NUCLEAR_DECAY, "", EnumTechLevel.NANO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.THORIUM_FISSIBILITY,
                                ManagerResearch.RESOURCE_OPTIMIZATION,
                                ManagerResearch.GEOTHERMAL_HARNESSING, ManagerResearch.PATTERN_RECOGNITION)),
                        new ItemStack(Femtocraft.blockFissionReactorCore), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.APPLIED_PARTICLE_PHYSICS, "Like theoretical particle physics.",
                        EnumTechLevel.FEMTO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.HARNESSED_NUCLEAR_DECAY,
                                ManagerResearch.ADVANCED_CHEMISTRY)
                ), new ItemStack(Femtocraft.itemCubit), true,
                        new ArrayList<ItemStack>(),
                        null
                ) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {
                        return GuiTechnologyAppliedParticlePhysics.class;
                    }
                }),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_COMPUTING, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.QUANTUM_INTERACTIVITY,
                                ManagerResearch.ADVANCED_PROGRAMMING,
                                ManagerResearch.APPLIED_PARTICLE_PHYSICS, ManagerResearch.LOCALITY_ENTANGLER,
                                ManagerResearch.REALITY_OVERCLOCKER)),
                        new ItemStack(Femtocraft.itemErinyesCircuit), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_ROBOTICS, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(ManagerResearch.QUANTUM_COMPUTING,
                                ManagerResearch.SPACETIME_EXPLOITATION)),
                        new ItemStack(Femtocraft.itemHerculesDrive), false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ELEMENT_MANUFACTURING, "Create circles, spheres, and ovals!",
                        EnumTechLevel.FEMTO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.QUANTUM_ROBOTICS)),
                        new ItemStack(Femtocraft.itemFemtoPlating),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_SUPERPOSITIONS, "It's a layer effect.",
                        EnumTechLevel.DIMENSIONAL, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.SPACETIME_EXPLOITATION,
                                ManagerResearch.ELEMENT_MANUFACTURING)
                ), new ItemStack(Femtocraft.blockFemtoEntangler),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MULTI_DIMENSIONAL_INDUSTRY, "Share the load.",
                        EnumTechLevel.DIMENSIONAL, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.DIMENSIONAL_SUPERPOSITIONS)),
                        new ItemStack(Femtocraft.itemInfiniteVolumePolychora),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.TEMPORAL_THREADING, "Having multiple timelines do work at once.",
                        EnumTechLevel.TEMPORAL, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.SPACETIME_EXPLOITATION,
                                ManagerResearch.ELEMENT_MANUFACTURING)
                ), new ItemStack(Femtocraft.blockFemtoChronoshifter),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.CAUSALITY_SINGULARITY,
                        "If you researched this, you never would have researched this.",
                        EnumTechLevel.TEMPORAL, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.TEMPORAL_THREADING)),
                        new ItemStack(Femtocraft.itemInfinitelyRecursiveALU),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DEMONIC_PARTICULATES, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.DIMENSIONAL_TRANSFORMATION,
                                ManagerResearch.ELEMENT_MANUFACTURING)),
                        new ItemStack(Femtocraft.itemFemtoCoil), false,
                        new ArrayList<ItemStack>()
                )),
                //    public static ResearchTechnology technologyPerfectScheduling = new ResearchTechnology(
//            "Perfect Scheduling", "Never miss a meeting!", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PARTICLE_EXCITATION, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ELEMENT_MANUFACTURING)), new ItemStack(
                        Femtocraft.blockFemtoImpulserUnlit), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PARTICLE_MANIPULATION, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.ELEMENT_MANUFACTURING)), new ItemStack(
                        Femtocraft.blockFemtoRepurposer), false,
                        new ArrayList<ItemStack>()
                )),

                //    public static ResearchTechnology technologyNBodySimulations = new ResearchTechnology(
//            "N-Body Simulations", "Simulate everything.", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPIN_RETENTION,
                        "",
                        EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.PARTICLE_MANIPULATION)),
                        new ItemStack(Femtocraft.itemQuantumSchematic), false, new ArrayList<ItemStack>()
                )),
                //    public static ResearchTechnology technologyQuantumComputing = new ResearchTechnology(
//            "Quantum Computing",
//            "",
//            EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyQuantumInteractivity, technologyAppliedParticlePhysics)),
//            new ItemStack(Femtocraft.itemHerculesDrive), false, new ArrayList<ItemStack>()
//    );
//    public static ResearchTechnology technologySingularityCalculator = new ResearchTechnology(
//            "Singularity Calculator",
//            "What are the odds you can't calculate the odds?",
//            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
//            Arrays.asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPONTANEOUS_GENERATION, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.QUANTUM_ROBOTICS,
                                ManagerResearch.DEMONIC_PARTICULATES)), new ItemStack(
                        Femtocraft.blockNullEqualizer), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.CORRUPTION_STABILIZATION, "", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.SPONTANEOUS_GENERATION)), new ItemStack(
                        Femtocraft.blockFemtoCubeFrame), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.STELLAR_MIMICRY, "Make your own pet star!", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays.asList(
//                    technologyStarMatterRecollection,
                                ManagerResearch.DEMONIC_PARTICULATES,
                                ManagerResearch.ELEMENT_MANUFACTURING)),
                        new ItemStack(Femtocraft.blockStellaratorCore), false,
                        new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ENERGY_CONVERSION, "Plasma to Energy", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.STELLAR_MIMICRY)),
                        new ItemStack(Femtocraft.blockPlasmaTurbine),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MATTER_CONVERSION, "Plasma to Mass", EnumTechLevel.FEMTO,
                        new ArrayList<String>(Arrays
                                .asList(ManagerResearch.STELLAR_MIMICRY)),
                        new ItemStack(Femtocraft.blockPlasmaCondenser),
                        false, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.NETHER_STAR_FABRICATION, "Creating the unimaginable.",
                        EnumTechLevel.FEMTO, new ArrayList<String>(
                        Arrays.asList(ManagerResearch.STELLAR_MIMICRY,
                                ManagerResearch.CORRUPTION_STABILIZATION,
                                ManagerResearch.PARTICLE_MANIPULATION)
                ), new ItemStack(
                        Item.netherStar), true, new ArrayList<ItemStack>()
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MOLECULAR_MANIPULATION, "", EnumTechLevel.MICRO,
                        new ArrayList<String>(Arrays.asList(
                                ManagerResearch.ALGORITHMS, ManagerResearch.MECHANICAL_PRECISION,
                                ManagerResearch.BASIC_CHEMISTRY)), new ItemStack(
                        Femtocraft.blockMicroDeconstructor), false,
                        new ArrayList<ItemStack>()
                )),
                //    @Technology
//    public static ResearchTechnology technologyParticleDestruction = new ResearchTechnology(
//            "Particle Destruction", "", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays.asList(
//                    technologyParticleManipulation,
//                    technologySingularityCalculator)), null, false,
//            new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MACROSCOPIC_STRUCTURES, "The patterns everything take.",
                        EnumTechLevel.MACRO, null, new ItemStack(Item.pickaxeIron),
                        true, null, null) {
                    @Override
                    public Class<? extends GuiTechnology> getGuiClass() {
                        return GuiTechnologyMacroscopicStructure.class;
                    }
                })
        ));
    }

    private ResearchTechnology loadTechnology(String name) {
        ResearchTechnology ret = new ResearchTechnology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                null, null, false, null);
        return loadResearchTechnology(ret);
    }

    private ResearchTechnology loadResearchTechnology(ResearchTechnology ret) {
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, ret.name,
                ResearchTechnology.class, ret);
        return ret;
    }

    private void registerTechnologies(List<ResearchTechnology> loadedTechnologies) {
        for (ResearchTechnology tech : loadedTechnologies) {
            Femtocraft.researchManager.addTechnology(tech);
        }
        loadedTechnologies.clear();
    }
}
