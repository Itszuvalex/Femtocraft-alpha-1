package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
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
    public static final String SECTION_KEY = "technologies";
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
        }
        else {
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
            String[] name = cc.getQualifiedName().split("\\" + Configuration.CATEGORY_SPLITTER);
            ret.add(loadTechnology(name[name.length - 1]));
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
                        new String[]{
                                ManagerResearch.METALLURGY, ManagerResearch.BASIC_CIRCUITS},
                        new ItemStack(Femtocraft.itemMicroPlating), false,
                        new ItemStack[]{new ItemStack(
                                Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                                Femtocraft.itemMicrochip), new ItemStack(
                                Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                                Femtocraft.itemMicrochip), new ItemStack(
                                Femtocraft.itemConductivePowder), new ItemStack(
                                Femtocraft.itemMicrochip), new ItemStack(
                                Femtocraft.itemIngotTemperedTitanium), new ItemStack(
                                Femtocraft.itemMicrochip), new ItemStack(
                                Femtocraft.itemIngotTemperedTitanium)},
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
                        null, new ItemStack(Femtocraft.blockResearchConsole), true, null, null, null, "What is a scientist without the scientific method?  Luckily for you, you no longer have to experiment.  This handy Research Computer analyzes all of your knowledge and will theorize prototypes for you to make.  Stick these prototypes into the Research Console to have it analyze the potential uses and to store the standardized blueprints into your knowledge store. __Recipe.Assembler:tile.BlockResearchComputer:Allows visual access to your personalized knowledge store.__ __Recipe.Assembler:tile.BlockResearchConsole:Analyzes your prototypes and generates standardized blueprints.__", false, false
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ALGORITHMS, "", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.MACHINING}, new ItemStack(
                        Femtocraft.blockEncoder), false,
                        null
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MECHANICAL_PRECISION, "", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.MACHINING}, new ItemStack(
                        Femtocraft.blockMicroFurnaceUnlit), false,
                        null
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POWER_OF_NOTHING, "\"Poof!\" It's nothing!", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.MECHANICAL_PRECISION},
                        new ItemStack(Femtocraft.itemVacuumCore),
                        false, null
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.VACUUM_TUBES, "These tubes contain nothing!", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.POWER_OF_NOTHING}, new ItemStack(
                        Femtocraft.blockVacuumTube), false,
                        null
                )),
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.VACUUM_TUBE_HUB, "A place for nothing to congregate.",
                        EnumTechLevel.MICRO, new String[]{
                        ManagerResearch.VACUUM_TUBES}, null, false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SUCTION_PIPES, "These pipes suck!", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.POWER_OF_NOTHING}
                        , new ItemStack(Femtocraft.blockSuctionPipe),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.NANO_CIRCUITS, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.MECHANICAL_PRECISION, ManagerResearch.BASIC_CIRCUITS},
                        new ItemStack(Femtocraft.itemNanochip), true,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ADVANCED_PROGRAMMING, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.NANO_CIRCUITS},
                        new ItemStack(Femtocraft.itemBasicAICore), false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.WORKLOAD_SCHEDULING, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.ADVANCED_PROGRAMMING},
                        new ItemStack(Femtocraft.itemSchedulerCore),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PATTERN_RECOGNITION, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.ADVANCED_PROGRAMMING},
                        new ItemStack(Femtocraft.itemLearningCore),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DISCRIMINATING_VACUUM_TUBE, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.VACUUM_TUBE_HUB, ManagerResearch.PATTERN_RECOGNITION},
                        new ItemStack(Femtocraft.blockVacuumTube), false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.RESOURCE_OPTIMIZATION, "", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.ADVANCED_PROGRAMMING},
                        new ItemStack(Femtocraft.itemManagerCore),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(ManagerResearch.BASIC_CHEMISTRY,
                                               "Composition of Matter", EnumTechLevel.MACRO, null,
                                               new ItemStack(Femtocraft.itemMineralLattice), true, null, null) {
                                           @Override
                                           public Class<? extends GuiTechnology> getGuiClass() {
                                               return GuiTechnologyBasicChemistry.class;
                                           }
                                       }

                ),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY, "", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.BASIC_CIRCUITS, ManagerResearch.BASIC_CHEMISTRY
                        }, new ItemStack(Femtocraft.blockMicroCable), false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_STORAGE, "", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.POTENTIALITY, ManagerResearch.MACHINING
                        }, new ItemStack(Femtocraft.blockMicroCube), false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_HARNESSING, "", EnumTechLevel.MICRO,
                        new String[]{ManagerResearch.POTENTIALITY}, new
                        ItemStack(Femtocraft.blockMicroChargingBase),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_GENERATION, "Build your potential!",
                        EnumTechLevel.MICRO, new String[]{
                        ManagerResearch.MECHANICAL_PRECISION,
                        ManagerResearch.POTENTIALITY_HARNESSING}
                        , new ItemStack(Femtocraft.blockCryoGenerator), false, null
                )),
                loadResearchTechnology(new ResearchTechnology(ManagerResearch.ADVANCED_CHEMISTRY, "",
                                               EnumTechLevel.NANO, new String[]{ManagerResearch
                                               .POTENTIALITY_GENERATION, ManagerResearch.BASIC_CHEMISTRY
                                       }, new ItemStack(Femtocraft.itemCrystallite), true, null, null
                                       ) {
                                           @Override
                                           public Class<? extends GuiTechnology> getGuiClass() {
                                               return GuiTechnologyAdvancedChemistry.class;
                                           }
                                       }

                ),

                // NANO
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ARTIFICIAL_MATERIALS, "Make what you need.", EnumTechLevel.NANO,
                        new String[]{ManagerResearch.NANO_CIRCUITS, ManagerResearch.ADVANCED_CHEMISTRY},
                        new ItemStack(Femtocraft.itemNanoPlating), false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.FARENITE_STABILIZATION, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.NANO_CIRCUITS, ManagerResearch.ADVANCED_CHEMISTRY,
                                ManagerResearch.ARTIFICIAL_MATERIALS}
                        , new ItemStack(Femtocraft.itemNanoCoil),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.GEOTHERMAL_HARNESSING, "Harness the geological thermal energy.",
                        EnumTechLevel.NANO, new String[]{ManagerResearch.FARENITE_STABILIZATION,
                        ManagerResearch.ADVANCED_CHEMISTRY,
                        ManagerResearch.POTENTIALITY_HARNESSING}, new
                        ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIALITY_TRANSFORMATION, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.FARENITE_STABILIZATION},
                        new ItemStack(Femtocraft.blockOrbitalEqualizer),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.INDUSTRIAL_STORAGE, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.POTENTIALITY_TRANSFORMATION,
                                ManagerResearch.RESOURCE_OPTIMIZATION
                        }, new ItemStack(Femtocraft.blockNanoCubeFrame),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.KINETIC_DISSOCIATION, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.WORKLOAD_SCHEDULING,
                                ManagerResearch.ARTIFICIAL_MATERIALS, ManagerResearch.RESOURCE_OPTIMIZATION}
                        , new ItemStack(Femtocraft.blockNanoInnervatorUnlit),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ATOMIC_MANIPULATION, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.ARTIFICIAL_MATERIALS,
                                ManagerResearch.WORKLOAD_SCHEDULING, ManagerResearch.RESOURCE_OPTIMIZATION},
                        new ItemStack(Femtocraft.blockNanoDismantler),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIGITIZED_WORKLOADS, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.ATOMIC_MANIPULATION,
                                ManagerResearch.RESOURCE_OPTIMIZATION
                        },
                        new ItemStack(Femtocraft.itemDigitalSchematic),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPACETIME_MANIPULATION,
                        "The question is: When did you ACTUALLY finish researching this?",
                        EnumTechLevel.NANO, new String[]{
                        ManagerResearch.ADVANCED_CHEMISTRY
                }, new ItemStack(Femtocraft.itemDimensionalMonopole),
                        true, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_BRAIDING, "Interweave reality",
                        EnumTechLevel.DIMENSIONAL, new String[]{
                        ManagerResearch.ARTIFICIAL_MATERIALS,
                        ManagerResearch.SPACETIME_MANIPULATION, ManagerResearch.ATOMIC_MANIPULATION}
                        , new ItemStack(Femtocraft.blockNanoEnmesher),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.LOCALITY_ENTANGLER, "", EnumTechLevel.DIMENSIONAL,
                        new String[]{
                                ManagerResearch.DIMENSIONAL_BRAIDING
                        }, new ItemStack(Femtocraft.itemPanLocationalComputer),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_TRANSFORMATION, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.DIMENSIONAL_BRAIDING
                        },
                        new ItemStack(Femtocraft.itemIngotMalenite),
                        true,
                        null
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
                        EnumTechLevel.TEMPORAL, new String[]{
                        ManagerResearch.ARTIFICIAL_MATERIALS,
                        ManagerResearch.SPACETIME_MANIPULATION,
                        ManagerResearch.ATOMIC_MANIPULATION
                }, new ItemStack(Femtocraft.blockNanoHorologe),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                                               ManagerResearch.REALITY_OVERCLOCKER, "", EnumTechLevel.TEMPORAL,
                                               new String[]{
                                                       ManagerResearch.TEMPORAL_PIPELINING
                                               },
                                               new ItemStack(Femtocraft.itemInfallibleEstimator),
                                               false, null
                                       )

                                       {
                                           @Override
                                           public Class<? extends GuiTechnology> getGuiClass() {
                                               return GuiTechnologyRealityOverlocker.class;
                                           }
                                       }

                ),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPACETIME_EXPLOITATION, "Exploiting reality itself.",
                        EnumTechLevel.FEMTO, new String[]{
                        ManagerResearch.LOCALITY_ENTANGLER,
                        ManagerResearch.REALITY_OVERCLOCKER
                }, new ItemStack(Femtocraft.itemPandoraCube),
                        true,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_INTERACTIVITY, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.DIMENSIONAL_TRANSFORMATION,
                                ManagerResearch.NANO_CIRCUITS,
                                ManagerResearch.DIMENSIONAL_BRAIDING,
                                ManagerResearch.TEMPORAL_PIPELINING
                        }, new ItemStack(Femtocraft.itemCharosGate),
                        true,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.POTENTIAL_HARVESTING, "Headhunters Unite!", EnumTechLevel.MICRO,
                        new String[]{
                                ManagerResearch.POTENTIALITY_STORAGE,
                                ManagerResearch.POTENTIALITY_HARNESSING
                        },
                        new ItemStack(Femtocraft.blockMicroChargingCapacitor),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.THORIUM_FISSIBILITY,
                        "",
                        EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.POTENTIAL_HARVESTING,
                                ManagerResearch.ADVANCED_CHEMISTRY
                        },
                        new ItemStack(Femtocraft.itemIngotThFaSalt),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.HARNESSED_NUCLEAR_DECAY, "", EnumTechLevel.NANO,
                        new String[]{
                                ManagerResearch.THORIUM_FISSIBILITY,
                                ManagerResearch.RESOURCE_OPTIMIZATION,
                                ManagerResearch.GEOTHERMAL_HARNESSING,
                                ManagerResearch.PATTERN_RECOGNITION
                        },
                        new ItemStack(Femtocraft.blockFissionReactorCore),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                                               ManagerResearch.APPLIED_PARTICLE_PHYSICS,
                                               "Like theoretical particle physics.",
                                               EnumTechLevel.FEMTO, new String[]{
                                               ManagerResearch.HARNESSED_NUCLEAR_DECAY,
                                               ManagerResearch.ADVANCED_CHEMISTRY
                                       }, new ItemStack(Femtocraft.itemCubit),
                                               true,
                                               null,
                                               null
                                       ) {
                                           @Override
                                           public Class<? extends GuiTechnology> getGuiClass() {
                                               return GuiTechnologyAppliedParticlePhysics.class;
                                           }
                                       }

                ),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_COMPUTING, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.QUANTUM_INTERACTIVITY,
                                ManagerResearch.ADVANCED_PROGRAMMING,
                                ManagerResearch.APPLIED_PARTICLE_PHYSICS,
                                ManagerResearch.LOCALITY_ENTANGLER,
                                ManagerResearch.REALITY_OVERCLOCKER
                        }, new ItemStack(Femtocraft.itemErinyesCircuit),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.QUANTUM_ROBOTICS, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.QUANTUM_COMPUTING,
                                ManagerResearch.SPACETIME_EXPLOITATION
                        },
                        new ItemStack(Femtocraft.itemHerculesDrive),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ELEMENT_MANUFACTURING, "Create circles, spheres, and ovals!",
                        EnumTechLevel.FEMTO, new String[]{
                        ManagerResearch.QUANTUM_ROBOTICS
                }, new ItemStack(Femtocraft.itemFemtoPlating),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DIMENSIONAL_SUPERPOSITIONS, "It's a layer effect.",
                        EnumTechLevel.DIMENSIONAL, new String[]{
                        ManagerResearch.SPACETIME_EXPLOITATION,
                        ManagerResearch.ELEMENT_MANUFACTURING
                }, new ItemStack(Femtocraft.blockFemtoEntangler),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MULTI_DIMENSIONAL_INDUSTRY, "Share the load.",
                        EnumTechLevel.DIMENSIONAL, new String[]{
                        ManagerResearch.DIMENSIONAL_SUPERPOSITIONS
                }, new ItemStack(Femtocraft.itemInfiniteVolumePolychora),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.TEMPORAL_THREADING, "Having multiple timelines do work at once.",
                        EnumTechLevel.TEMPORAL, new String[]{
                        ManagerResearch.SPACETIME_EXPLOITATION,
                        ManagerResearch.ELEMENT_MANUFACTURING
                }, new ItemStack(Femtocraft.blockFemtoChronoshifter),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.CAUSALITY_SINGULARITY,
                        "If you researched this, you never would have researched this.",
                        EnumTechLevel.TEMPORAL, new String[]{
                        ManagerResearch.TEMPORAL_THREADING
                }, new ItemStack(Femtocraft.itemInfinitelyRecursiveALU),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.DEMONIC_PARTICULATES, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.DIMENSIONAL_TRANSFORMATION,
                                ManagerResearch.ELEMENT_MANUFACTURING
                        },
                        new ItemStack(Femtocraft.itemFemtoCoil),
                        false,
                        null
                )),

                //    public static ResearchTechnology technologyPerfectScheduling = new ResearchTechnology(
//            "Perfect Scheduling", "Never miss a meeting!", EnumTechLevel.FEMTO,
//            new ArrayList<ResearchTechnology>(Arrays
//                    .asList(technologyQuantumComputing)), null,
//            false, new ArrayList<ItemStack>()
//    );
                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PARTICLE_EXCITATION, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.ELEMENT_MANUFACTURING
                        }, new ItemStack(
                        Femtocraft.blockFemtoImpulserUnlit),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.PARTICLE_MANIPULATION, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.ELEMENT_MANUFACTURING
                        }, new ItemStack(
                        Femtocraft.blockFemtoRepurposer),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPIN_RETENTION,
                        "",
                        EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.PARTICLE_MANIPULATION
                        },
                        new ItemStack(Femtocraft.itemQuantumSchematic),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.SPONTANEOUS_GENERATION, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.QUANTUM_ROBOTICS,
                                ManagerResearch.DEMONIC_PARTICULATES
                        }, new ItemStack(Femtocraft.blockNullEqualizer),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.CORRUPTION_STABILIZATION, "", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.SPONTANEOUS_GENERATION
                        }, new ItemStack(Femtocraft.blockFemtoCubeFrame),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.STELLAR_MIMICRY, "Make your own pet star!", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.DEMONIC_PARTICULATES,
                                ManagerResearch.ELEMENT_MANUFACTURING
                        },
                        new ItemStack(Femtocraft.blockStellaratorCore),
                        false,
                        null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.ENERGY_CONVERSION, "Plasma to Energy", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.STELLAR_MIMICRY}
                        , new ItemStack(Femtocraft.blockPlasmaTurbine),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MATTER_CONVERSION, "Plasma to Mass", EnumTechLevel.FEMTO,
                        new String[]{
                                ManagerResearch.STELLAR_MIMICRY}
                        , new ItemStack(Femtocraft.blockPlasmaCondenser),
                        false, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.NETHER_STAR_FABRICATION, "Creating the unimaginable.",
                        EnumTechLevel.FEMTO, new String[]{
                        ManagerResearch.STELLAR_MIMICRY,
                        ManagerResearch.CORRUPTION_STABILIZATION,
                        ManagerResearch.PARTICLE_MANIPULATION}
                        , new ItemStack(Item.netherStar),
                        true, null
                )),

                loadResearchTechnology(new ResearchTechnology(
                        ManagerResearch.MOLECULAR_MANIPULATION, "", EnumTechLevel.MICRO,
                        new String[]{
                                ManagerResearch.ALGORITHMS, ManagerResearch.MECHANICAL_PRECISION,
                                ManagerResearch.BASIC_CHEMISTRY}
                        , new ItemStack(Femtocraft.blockMicroDeconstructor),
                        false,
                        null
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

                                               true, null, null)

                                       {
                                           @Override
                                           public Class<? extends GuiTechnology> getGuiClass() {
                                               return GuiTechnologyMacroscopicStructure.class;
                                           }
                                       }

                )
        ));
    }

    private ResearchTechnology loadTechnology(String name) {
        ResearchTechnology ret = new ResearchTechnology(name, "DEFAULT DESCRIPTION", EnumTechLevel.MACRO,
                null, null, false, null);
        return loadResearchTechnology(ret);
    }

    public ResearchTechnology loadResearchTechnology(ResearchTechnology ret) {
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
