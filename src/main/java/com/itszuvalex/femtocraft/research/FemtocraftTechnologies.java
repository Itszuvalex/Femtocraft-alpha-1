//package com.itszuvalex.femtocraft.research;
//
//import com.itszuvalex.femtocraft.Femtocraft;
//import com.itszuvalex.femtocraft.api.AssemblerRecipe;
//import com.itszuvalex.femtocraft.api.EnumTechLevel;
//import com.itszuvalex.femtocraft.api.research.ITechnology;
//import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs;
//import com.itszuvalex.femtocraft.managers.assembler.ComponentRegistry;
//import com.itszuvalex.femtocraft.managers.research.Technology;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnology;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyAdvancedChemistry;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyAppliedParticlePhysics;
//import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyMacroscopicStructure;
//import net.minecraft.init.Items;
//import net.minecraft.item.ItemStack;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by Chris on 10/4/2014.
// */
//public class FemtocraftTechnologies {
//    public static final String MACROSCOPIC_STRUCTURES = "Macroscopic Structures";
//    public static final String MOLECULAR_MANIPULATION = "Molecular Manipulation";
//    public static final String NETHER_STAR_FABRICATION = "Nether Star Fabrication";
//    public static final String MATTER_CONVERSION = "Matter Conversion";
//    public static final String ENERGY_CONVERSION = "Energy Conversion";
//    public static final String STELLAR_MIMICRY = "Stellar Mimicry";
//    public static final String CORRUPTION_STABILIZATION = "Corruption Stabilization";
//    public static final String SPONTANEOUS_GENERATION = "Spontaneous Generation";
//    public static final String SPIN_RETENTION = "Spin Retention";
//    public static final String PARTICLE_MANIPULATION = "Particle Manipulation";
//    public static final String PARTICLE_EXCITATION = "Particle Excitation";
//    public static final String DEMONIC_PARTICULATES = "Demonic Particulates";
//    public static final String CAUSALITY_SINGULARITY = "Causality Singularity";
//    public static final String TEMPORAL_THREADING = "Temporal Threading";
//    public static final String MULTI_DIMENSIONAL_INDUSTRY = "Multi-Dimensional Industry";
//    public static final String DIMENSIONAL_SUPERPOSITIONS = "Dimensional Superpositions";
//    public static final String ELEMENT_MANUFACTURING = "Element Manufacturing";
//    public static final String QUANTUM_ROBOTICS = "Quantum Robotics";
//    public static final String QUANTUM_COMPUTING = "Quantum Computing";
//    public static final String APPLIED_PARTICLE_PHYSICS = "Applied Particle Physics";
//    public static final String HARNESSED_NUCLEAR_DECAY = "Harnessed Nuclear Decay";
//    public static final String THORIUM_FISSIBILITY = "Thorium Fissibility";
//    public static final String POTENTIAL_HARVESTING = "Potential Harvesting";
//    public static final String QUANTUM_INTERACTIVITY = "Quantum Interactivity";
//    public static final String SPACETIME_EXPLOITATION = "Spacetime Exploitation";
//    public static final String REALITY_OVERCLOCKER = "Reality Overclocker";
//    public static final String TEMPORAL_PIPELINING = "Temporal Pipelining";
//    public static final String DIMENSIONAL_TRANSFORMATION = "Dimensional Transformation";
//    public static final String LOCALITY_ENTANGLER = "Locality Entangler";
//    public static final String DIMENSIONAL_BRAIDING = "Dimensional Braiding";
//    public static final String SPACETIME_MANIPULATION = "Spacetime Manipulation";
//    public static final String DIGITIZED_WORKLOADS = "Digitized Workloads";
//    public static final String ATOMIC_MANIPULATION = "Atomic Manipulation";
//    public static final String KINETIC_DISSOCIATION = "Kinetic Dissociation";
//    public static final String INDUSTRIAL_STORAGE = "Industrial Storage";
//    public static final String POTENTIALITY_TRANSFORMATION = "Potentiality Transformation";
//    public static final String GEOTHERMAL_HARNESSING = "Geothermal Harnessing";
//    public static final String FARENITE_STABILIZATION = "Farenite Stabilization";
//    public static final String ARTIFICIAL_MATERIALS = "Artificial Materials";
//    public static final String ADVANCED_CHEMISTRY = "Advanced Chemistry";
//    public static final String POTENTIALITY_GENERATION = "Potentiality Generation";
//    public static final String POTENTIALITY_HARNESSING = "Potentiality Harnessing";
//    public static final String POTENTIALITY_STORAGE = "Potentiality Storage";
//    public static final String POTENTIALITY = "Potentiality";
//    public static final String BASIC_CHEMISTRY = "Basic Chemistry";
//    public static final String RESOURCE_OPTIMIZATION = "Resource Optimization";
//    public static final String DISCRIMINATING_VACUUM_TUBE = "Discriminating Vacuum Tube";
//    public static final String PATTERN_RECOGNITION = "Pattern Recognition";
//    public static final String WORKLOAD_SCHEDULING = "Workload Scheduling";
//    public static final String ADVANCED_PROGRAMMING = "Advanced Programming";
//    public static final String NANO_CIRCUITS = "Nano Circuits";
//    public static final String SUCTION_PIPES = "Suction Pipes";
//    public static final String VACUUM_TUBE_HUB = "VacuumTube Hub";
//    public static final String VACUUM_TUBES = "Vacuum Tubes";
//    public static final String POWER_OF_NOTHING = "Power of Nothing";
//    public static final String MECHANICAL_PRECISION = "Mechanical Precision";
//    public static final String ALGORITHMS = "Algorithms";
//    public static final String SCIENTIFIC_THEORY = "Scientific Theory";
//    public static final String MACHINING = "Machining";
//    public static final String BASIC_CIRCUITS = "Basic Circuits";
//    public static final String METALLURGY = "Metallurgy";
//
//    public static List<ITechnology> defaultTechnologies() {
//        return new ArrayList<ITechnology>(Arrays.asList(
//                (new Technology(
//                        METALLURGY, "The metals that populate your world.", EnumTechLevel.MACRO,
//                        new String[]{MACROSCOPIC_STRUCTURES},
//                        new ItemStack(Femtocraft.itemIngotTemperedTitanium()),
//                        true, null, null, null,
//                        "    The world is full of many interesting things.  Animals roam the fields, " +
//                        "monsters rule the night, and eldritch energies course throughout the land.  Perhaps most" +
//                        " " +
//                        "beneficial to you, however, are the various ores that inhabit the ground.\n    Iron and " +
//                        "coal" +
//                        " are plentiful, but you know both are merely the tip of the iceberg when it comes to " +
//                        "structural integrity and fuel." +
//                        (FemtocraftConfigs.titaniumGen ?
//                                "\n\n            Titanium\n\n    This silvery metal quickly corrodes when exposed" +
//                                " to " +
//                                "air, resulting in a dark oxidation on its surface.  It is most commonly found at" +
//                                " " +
//                                "depths of " +
//                                FemtocraftConfigs.titaniumOreYHeightMax + " to " +
//                                FemtocraftConfigs.titaniumOreYHeightMin +
//                                ".  It has extremely high durability, making it suitable for encasing mechanisms " +
//                                "and " +
//                                "circuitry.  You theorize that you can also temper this metal by running it under" +
//                                " " +
//                                "intense heat for extra durability." : "") +
//                        (FemtocraftConfigs.platinumGen ?
//                                "\n\n            Platinum\n\n    A rare, shiny metal, " +
//                                "renowned for its resistance to corrosion.  Most commonly found between depths of" +
//                                " " +
//                                FemtocraftConfigs.platinumOreYHeightMax + " and " +
//                                FemtocraftConfigs.platinumOreYHeightMin +
//                                ", it is useful for electronics where exposure to hostile chemicals is a certainty."
//                                : "") +
//                        (FemtocraftConfigs.thoriumGen ?
//                                "\n\n            Thorium\n\n    A heavy metal, with uses in nuclear decay chains." +
//                                "  " +
//                                "Found in depths of " +
//                                FemtocraftConfigs.thoriumOreYHeightMax + " to " +
//                                FemtocraftConfigs.thoriumOreYHeightMin +
//                                ", it's only truly useful once you figure out how to harness the power of its " +
//                                "nuclear" +
//                                " decay." : ""), false, true)),
//                (new Technology(
//                        BASIC_CIRCUITS, "Basic logic for basic machines.", EnumTechLevel.MACRO,
//                        new String[]{MACROSCOPIC_STRUCTURES},
//                        new ItemStack(Femtocraft.itemMicrochip()), true,
//                        getInput(new ItemStack(Femtocraft.itemConductivePowder())),
//                        new ItemStack(Femtocraft.itemConductivePowder()), null,
//                        "    Machines and mechanisms are worthless heaps of metal unless there is a purpose " +
//                        "behind them.  You have deduced a simple set of logical mechanisms and a simple means" +
//                        " of producing them.__Recipe.Crafting:Femtocraft:item.ItemConductivePowder--This " +
//                        "powder balances the volatility of farenite with the stability of lapis, " +
//                        "making it useful for conduction.____Recipe.Crafting:Femtocraft:item.ItemBoard--A " +
//                        "simple assembly of sticks, side-by-side, gives a base for mounting logic assemblies" +
//                        ".____Recipe.Crafting:Femtocraft:item.ItemPrimedBoard--Some conductive powder on the " +
//                        "board, when run through an oven, creates a foundation for circuitry.____Recipe" +
//                        ".Crafting:Femtocraft:item.ItemSpool--Just a thin rod of wood with end caps, " +
//                        "it makes an excellent storage unit for wires.____Recipe.Crafting:Femtocraft:item" +
//                        ".ItemSpoolGold--Your studies show gold is an excellent conductor, " +
//                        "and you plan on using nothing but the highest quality for your machines.____Recipe" +
//                        ".Crafting:Femtocraft:item.ItemSpoolPlatinum--Some thin platinum wire on a spool.  " +
//                        "Useful for corrosive environments.____Recipe.Crafting:Femtocraft:item" +
//                        ".ItemMicrochip--Thin wiring on a solid board, these multipurpose devices enable your" +
//                        " machines to perform basic logic.__", false, true)
//                ),
//                (new Technology(
//                        MACHINING, "Start your industry!", EnumTechLevel.MICRO,
//                        new String[]{
//                                METALLURGY, BASIC_CIRCUITS},
//                        new ItemStack(Femtocraft.itemMicroPlating()), false,
//                        getInput(new ItemStack(Femtocraft.itemMicroPlating())),
//                        new ItemStack(Femtocraft.itemMicroPlating()), "", "", false, false
//                )
//                ),
//                (new Technology(
//                        SCIENTIFIC_THEORY, "Gentlemen, start your research!", EnumTechLevel.MACRO,
//                        new String[]{MACROSCOPIC_STRUCTURES},
//                        new ItemStack(Femtocraft.blockResearchConsole()), true, null, null, null,
//                        "What is a scientist without the scientific method?  Luckily for you, " +
//                        "you no longer have to experiment.  This handy Research Computer analyzes all of your " +
//                        "knowledge and will theorize prototypes for you to make.  Stick these prototypes into the" +
//                        " " +
//                        "Research Console to have it analyze the potential uses and to store the standardized " +
//                        "blueprints into your knowledge store. __Recipe.Assembler:Femtocraft:tile" +
//                        ".BlockResearchComputer--Allows visual access to your personalized knowledge store.__" +
//                        "__Recipe" +
//                        ".Assembler:Femtocraft:tile.BlockResearchConsole--Analyzes your prototypes and generates " +
//                        "standardized blueprints.__", false, true
//                )),
//                (new Technology(
//                        ALGORITHMS, "", EnumTechLevel.MICRO,
//                        new String[]{MACHINING}, new ItemStack(
//                        Femtocraft.blockEncoder()), false,
//                        getInput(new ItemStack(Femtocraft.blockEncoder())), new ItemStack(Femtocraft.blockEncoder())
//                )),
//                (new Technology(
//                        MECHANICAL_PRECISION, "", EnumTechLevel.MICRO,
//                        new String[]{MACHINING}, new ItemStack(
//                        Femtocraft.blockMicroFurnaceUnlit()), false,
//                        getInput(new ItemStack(Femtocraft.itemHeatingElement())),
//                        new ItemStack(Femtocraft.itemHeatingElement())
//                )),
//                (new Technology(
//                        POWER_OF_NOTHING, "\"Poof!\" It's nothing!", EnumTechLevel.MICRO,
//                        new String[]{MECHANICAL_PRECISION},
//                        new ItemStack(Femtocraft.itemVacuumCore()),
//                        false, getInput(new ItemStack(Femtocraft.itemVacuumCore())),
//                        new ItemStack(Femtocraft.itemVacuumCore())
//                )),
//                (new Technology(
//                        VACUUM_TUBES, "These tubes contain nothing!", EnumTechLevel.MICRO,
//                        new String[]{POWER_OF_NOTHING}, new ItemStack(
//                        Femtocraft.blockVacuumTube()), false,
//                        getInput(new ItemStack(Femtocraft.blockVacuumTube())),
//                        new ItemStack(Femtocraft.blockVacuumTube())
//                )),
//                (new Technology(
//                        VACUUM_TUBE_HUB, "A place for nothing to congregate.",
//                        EnumTechLevel.MICRO, new String[]{
//                        VACUUM_TUBES}, null, false,
//                        null
//                )),
//
//                (new Technology(
//                        SUCTION_PIPES, "These pipes suck!", EnumTechLevel.MICRO,
//                        new String[]{POWER_OF_NOTHING}
//                        , new ItemStack(Femtocraft.blockSuctionPipe()),
//                        false, getInput(new ItemStack(Femtocraft.blockSuctionPipe())),
//                        new ItemStack(Femtocraft.blockSuctionPipe())
//                )),
//
//                (new Technology(
//                        NANO_CIRCUITS, "", EnumTechLevel.NANO,
//                        new String[]{MECHANICAL_PRECISION, BASIC_CIRCUITS},
//                        new ItemStack(Femtocraft.itemNanochip()), true,
//                        getInput(new ItemStack(Femtocraft.itemNanochip())), new ItemStack(Femtocraft.itemNanochip())
//                )),
//
//                (new Technology(
//                        ADVANCED_PROGRAMMING, "", EnumTechLevel.NANO,
//                        new String[]{NANO_CIRCUITS},
//                        new ItemStack(Femtocraft.itemBasicAICore()), false,
//                        getInput(new ItemStack(Femtocraft.itemBasicAICore())),
//                        new ItemStack(Femtocraft.itemBasicAICore())
//                )),
//
//                (new Technology(
//                        WORKLOAD_SCHEDULING, "", EnumTechLevel.NANO,
//                        new String[]{ADVANCED_PROGRAMMING},
//                        new ItemStack(Femtocraft.itemSchedulerCore()),
//                        false, getInput(new ItemStack(Femtocraft.itemSchedulerCore())),
//                        new ItemStack(Femtocraft.itemSchedulerCore())
//                )),
//
//                (new Technology(
//                        PATTERN_RECOGNITION, "", EnumTechLevel.NANO,
//                        new String[]{ADVANCED_PROGRAMMING},
//                        new ItemStack(Femtocraft.itemLearningCore()),
//                        false, getInput(new ItemStack(Femtocraft.itemLearningCore())),
//                        new ItemStack(Femtocraft.itemLearningCore())
//                )),
//
//                (new Technology(
//                        DISCRIMINATING_VACUUM_TUBE, "", EnumTechLevel.NANO,
//                        new String[]{VACUUM_TUBE_HUB, PATTERN_RECOGNITION},
//                        new ItemStack(Femtocraft.blockVacuumTube()), false,
//                        null
//                )),
//
//                (new Technology(
//                        RESOURCE_OPTIMIZATION, "", EnumTechLevel.NANO,
//                        new String[]{ADVANCED_PROGRAMMING},
//                        new ItemStack(Femtocraft.itemManagerCore()),
//                        false, getInput(new ItemStack(Femtocraft.itemManagerCore())),
//                        new ItemStack(Femtocraft.itemManagerCore())
//                )),
//
//                (new Technology(BASIC_CHEMISTRY,
//                        "Composition of Matter", EnumTechLevel.MACRO,
//                        new String[]{MACROSCOPIC_STRUCTURES},
//                        new ItemStack(Femtocraft.itemMineralLattice()), true, null, null, null,
//                        "    Inspecting the materials around you leads you to the conclusion that several of these " +
//                        "materials share similarities with each other.  Long nights of study and experiments have " +
//                        "shown that, in fact, these objects are created out of complex combinations of several, " +
//                        "smaller components." +
//                        ComponentRegistry.getComponentsInAssemblerRecipeDisplayString(EnumTechLevel.MICRO), false, true)
//                ),
//
//                (new Technology(
//                        POTENTIALITY, "", EnumTechLevel.MICRO,
//                        new String[]{BASIC_CIRCUITS, BASIC_CHEMISTRY
//                        }, new ItemStack(Femtocraft.blockMicroCable()), false,
//                        getInput(new ItemStack(Femtocraft.itemMicroCoil())), new ItemStack(Femtocraft.itemMicroCoil())
//                )),
//
//                (new Technology(
//                        POTENTIALITY_STORAGE, "", EnumTechLevel.MICRO,
//                        new String[]{POTENTIALITY, MACHINING
//                        }, new ItemStack(Femtocraft.blockMicroCube()), false,
//                        getInput(new ItemStack(Femtocraft.itemBattery())), new ItemStack(Femtocraft.itemBattery())
//                )),
//
//                (new Technology(
//                        POTENTIALITY_HARNESSING, "", EnumTechLevel.MICRO,
//                        new String[]{POTENTIALITY}, new
//                        ItemStack(Femtocraft.blockMicroChargingBase()),
//                        false, getInput(new ItemStack(Femtocraft.blockMicroChargingBase())),
//                        new ItemStack(Femtocraft.blockMicroChargingBase())
//                )),
//
//                (new Technology(
//                        POTENTIALITY_GENERATION, "Build your potential!",
//                        EnumTechLevel.MICRO, new String[]{
//                        MECHANICAL_PRECISION,
//                        POTENTIALITY_HARNESSING}
//                        , new ItemStack(Femtocraft.blockMagneticInductionGenerator()), false,
//                        getInput(new ItemStack(Femtocraft.blockMagneticInductionGenerator())),
//                        new ItemStack(Femtocraft.blockMagneticInductionGenerator())
//                )),
//                (new Technology(ADVANCED_CHEMISTRY, "",
//                        EnumTechLevel.NANO, new String[]{
//                        POTENTIALITY_GENERATION, BASIC_CHEMISTRY
//                }, new ItemStack(Femtocraft.itemCrystallite()), true, null, null
//                ) {
//                    @Override
//                    public Class<? extends GuiTechnology> getGuiClass() {
//                        return GuiTechnologyAdvancedChemistry.class;
//                    }
//                }
//
//                ),
//
//                // NANO
//                (new Technology(
//                        ARTIFICIAL_MATERIALS, "Make what you need.", EnumTechLevel.NANO,
//                        new String[]{NANO_CIRCUITS, ADVANCED_CHEMISTRY},
//                        new ItemStack(Femtocraft.itemNanoPlating()), false,
//                        getInput(new ItemStack(Femtocraft.itemNanoPlating())),
//                        new ItemStack(Femtocraft.itemNanoPlating())
//                )),
//
//                (new Technology(
//                        FARENITE_STABILIZATION, "", EnumTechLevel.NANO,
//                        new String[]{
//                                NANO_CIRCUITS, ADVANCED_CHEMISTRY,
//                                ARTIFICIAL_MATERIALS}
//                        , new ItemStack(Femtocraft.blockNanoCable()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemFluidicConductor())),
//                        new ItemStack(Femtocraft.itemFluidicConductor())
//                )),
//
//                (new Technology(
//                        GEOTHERMAL_HARNESSING, "Harness the geological thermal energy.",
//                        EnumTechLevel.NANO, new String[]{FARENITE_STABILIZATION,
//                        ADVANCED_CHEMISTRY,
//                        POTENTIALITY_HARNESSING}, new
//                        ItemStack(Femtocraft.blockCryoEndothermalChargingBase()),
//                        false, getInput(new ItemStack(Femtocraft.blockCryoEndothermalChargingBase())),
//                        new ItemStack(Femtocraft.blockCryoEndothermalChargingBase())
//                )),
//
//                (new Technology(
//                        POTENTIALITY_TRANSFORMATION, "", EnumTechLevel.NANO,
//                        new String[]{
//                                FARENITE_STABILIZATION},
//                        new ItemStack(Femtocraft.blockOrbitalEqualizer()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockOrbitalEqualizer())),
//                        new ItemStack(Femtocraft.blockOrbitalEqualizer())
//                )),
//
//                (new Technology(
//                        INDUSTRIAL_STORAGE, "", EnumTechLevel.NANO,
//                        new String[]{
//                                POTENTIALITY_TRANSFORMATION,
//                                RESOURCE_OPTIMIZATION
//                        }, new ItemStack(Femtocraft.blockNanoCubeFrame()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockNanoCubeFrame())),
//                        new ItemStack(Femtocraft.blockNanoCubeFrame())
//                )),
//
//                (new Technology(
//                        KINETIC_DISSOCIATION, "", EnumTechLevel.NANO,
//                        new String[]{
//                                WORKLOAD_SCHEDULING,
//                                ARTIFICIAL_MATERIALS, RESOURCE_OPTIMIZATION}
//                        , new ItemStack(Femtocraft.blockNanoInnervatorUnlit()),
//                        false, getInput(new ItemStack(Femtocraft.blockNanoInnervatorUnlit())),
//                        new ItemStack(Femtocraft.blockNanoInnervatorUnlit())
//                )),
//
//                (new Technology(
//                        ATOMIC_MANIPULATION, "", EnumTechLevel.NANO,
//                        new String[]{
//                                ARTIFICIAL_MATERIALS,
//                                WORKLOAD_SCHEDULING, RESOURCE_OPTIMIZATION},
//                        new ItemStack(Femtocraft.blockNanoDismantler()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockNanoDismantler())),
//                        new ItemStack(Femtocraft.blockNanoDismantler())
//                )),
//
//                (new Technology(
//                        DIGITIZED_WORKLOADS, "", EnumTechLevel.NANO,
//                        new String[]{
//                                ATOMIC_MANIPULATION,
//                                RESOURCE_OPTIMIZATION
//                        },
//                        new ItemStack(Femtocraft.itemDigitalSchematic()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemDigitalSchematic())),
//                        new ItemStack(Femtocraft.itemDigitalSchematic())
//                )),
//
//                (new Technology(
//                        SPACETIME_MANIPULATION,
//                        "The question is: When did you ACTUALLY finish researching this?",
//                        EnumTechLevel.NANO, new String[]{
//                        ADVANCED_CHEMISTRY
//                }, new ItemStack(Femtocraft.itemDimensionalMonopole()),
//                        true, getInput(new ItemStack(Femtocraft.itemDimensionalMonopole())),
//                        new ItemStack(Femtocraft.itemDimensionalMonopole())
//                )),
//
//                (new Technology(
//                        DIMENSIONAL_BRAIDING, "Interweave reality",
//                        EnumTechLevel.DIMENSIONAL, new String[]{
//                        ARTIFICIAL_MATERIALS,
//                        SPACETIME_MANIPULATION, ATOMIC_MANIPULATION}
//                        , new ItemStack(Femtocraft.blockNanoEnmesher()),
//                        false, getInput(new ItemStack(Femtocraft.blockNanoEnmesher())),
//                        new ItemStack(Femtocraft.blockNanoEnmesher())
//                )),
//
//                (new Technology(
//                        LOCALITY_ENTANGLER, "", EnumTechLevel.DIMENSIONAL,
//                        new String[]{
//                                DIMENSIONAL_BRAIDING
//                        }, new ItemStack(Femtocraft.itemPanLocationalComputer()),
//                        false, getInput(new ItemStack(Femtocraft.itemPanLocationalComputer())),
//                        new ItemStack(Femtocraft.itemPanLocationalComputer())
//                )),
//
//                (new Technology(
//                        DIMENSIONAL_TRANSFORMATION, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                DIMENSIONAL_BRAIDING
//                        },
//                        new ItemStack(Femtocraft.itemIngotMalenite()),
//                        true,
//                        null
//                )),
//
//                //    public static ResearchTechnology technologyStarMatterRecollection = new ResearchTechnology(
////            "Star Matter Recollection", "Recollecting what has been spread.",
////            EnumTechLevel.FEMTO, new ArrayList<ResearchTechnology>(
////            Arrays.asList(technologyDimensionalTransformation)), null,
////            false, new ArrayList<ItemStack>()
////    );
//                (new Technology(
//                        TEMPORAL_PIPELINING,
//                        "Start working on the next second, before it begins.",
//                        EnumTechLevel.TEMPORAL, new String[]{
//                        ARTIFICIAL_MATERIALS,
//                        SPACETIME_MANIPULATION,
//                        ATOMIC_MANIPULATION
//                }, new ItemStack(Femtocraft.blockNanoHorologe()),
//                        false, getInput(new ItemStack(Femtocraft.blockNanoHorologe())),
//                        new ItemStack(Femtocraft.blockNanoHorologe())
//                )),
//
//                (new Technology(
//                        REALITY_OVERCLOCKER, "", EnumTechLevel.TEMPORAL,
//                        new String[]{
//                                TEMPORAL_PIPELINING
//                        },
//                        new ItemStack(Femtocraft.itemInfallibleEstimator()),
//                        false, null
//                )
//                ),
//
//                (new Technology(
//                        SPACETIME_EXPLOITATION, "Exploiting reality itself.",
//                        EnumTechLevel.FEMTO, new String[]{
//                        LOCALITY_ENTANGLER,
//                        REALITY_OVERCLOCKER
//                }, new ItemStack(Femtocraft.itemPandoraCube()),
//                        true,
//                        null
//                )),
//
//                (new Technology(
//                        QUANTUM_INTERACTIVITY, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                DIMENSIONAL_TRANSFORMATION,
//                                NANO_CIRCUITS,
//                                DIMENSIONAL_BRAIDING,
//                                TEMPORAL_PIPELINING
//                        }, new ItemStack(Femtocraft.itemCharosGate()),
//                        true,
//                        getInput(new ItemStack(Femtocraft.itemCharosGate())), new ItemStack(Femtocraft.itemCharosGate())
//                )),
//
//                (new Technology(
//                        POTENTIAL_HARVESTING, "Headhunters Unite!", EnumTechLevel.MICRO,
//                        new String[]{
//                                POTENTIALITY_STORAGE,
//                                POTENTIALITY_HARNESSING
//                        },
//                        new ItemStack(Femtocraft.blockMicroChargingCapacitor()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockMicroChargingCapacitor())),
//                        new ItemStack(Femtocraft.blockMicroChargingCapacitor())
//                )),
//
//                (new Technology(
//                        THORIUM_FISSIBILITY,
//                        "",
//                        EnumTechLevel.NANO,
//                        new String[]{
//                                POTENTIAL_HARVESTING,
//                                ADVANCED_CHEMISTRY
//                        },
//                        new ItemStack(Femtocraft.itemIngotThFaSalt()),
//                        false, getInput(new ItemStack(Femtocraft.itemIngotThFaSalt())),
//                        new ItemStack(Femtocraft.itemIngotThFaSalt())
//                )),
//
//                (new Technology(
//                        HARNESSED_NUCLEAR_DECAY, "", EnumTechLevel.NANO,
//                        new String[]{
//                                THORIUM_FISSIBILITY,
//                                RESOURCE_OPTIMIZATION,
//                                GEOTHERMAL_HARNESSING,
//                                PATTERN_RECOGNITION
//                        },
//                        new ItemStack(Femtocraft.blockFissionReactorCore()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemFissionReactorPlating())),
//                        new ItemStack(Femtocraft.itemFissionReactorPlating())
//                )),
//
//                (new Technology(
//                        APPLIED_PARTICLE_PHYSICS,
//                        "Like theoretical particle physics.",
//                        EnumTechLevel.FEMTO, new String[]{
//                        HARNESSED_NUCLEAR_DECAY,
//                        ADVANCED_CHEMISTRY
//                }, new ItemStack(Femtocraft.itemCubit()),
//                        true,
//                        null,
//                        null
//                ) {
//                    @Override
//                    public Class<? extends GuiTechnology> getGuiClass() {
//                        return GuiTechnologyAppliedParticlePhysics.class;
//                    }
//                }
//
//                ),
//
//                (new Technology(
//                        QUANTUM_COMPUTING, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                QUANTUM_INTERACTIVITY,
//                                ADVANCED_PROGRAMMING,
//                                APPLIED_PARTICLE_PHYSICS,
//                                LOCALITY_ENTANGLER,
//                                REALITY_OVERCLOCKER
//                        }, new ItemStack(Femtocraft.itemErinyesCircuit()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemErinyesCircuit())),
//                        new ItemStack(Femtocraft.itemErinyesCircuit())
//                )),
//
//                (new Technology(
//                        QUANTUM_ROBOTICS, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                QUANTUM_COMPUTING,
//                                SPACETIME_EXPLOITATION
//                        },
//                        new ItemStack(Femtocraft.itemHerculesDrive()),
//                        false, getInput(new ItemStack(Femtocraft.itemHerculesDrive())),
//                        new ItemStack(Femtocraft.itemHerculesDrive())
//                )),
//
//                (new Technology(
//                        ELEMENT_MANUFACTURING, "Create circles, spheres, and ovals!",
//                        EnumTechLevel.FEMTO, new String[]{
//                        QUANTUM_ROBOTICS
//                }, new ItemStack(Femtocraft.itemFemtoPlating()),
//                        false, getInput(new ItemStack(Femtocraft.itemFemtoPlating())),
//                        new ItemStack(Femtocraft.itemFemtoPlating())
//                )),
//
//                (new Technology(
//                        DIMENSIONAL_SUPERPOSITIONS, "It's a layer effect.",
//                        EnumTechLevel.DIMENSIONAL, new String[]{
//                        SPACETIME_EXPLOITATION,
//                        ELEMENT_MANUFACTURING
//                }, new ItemStack(Femtocraft.blockFemtoEntangler()),
//                        false, getInput(new ItemStack(Femtocraft.blockFemtoEntangler())),
//                        new ItemStack(Femtocraft.blockFemtoEntangler())
//                )),
//
//                (new Technology(
//                        MULTI_DIMENSIONAL_INDUSTRY, "Share the load.",
//                        EnumTechLevel.DIMENSIONAL, new String[]{
//                        DIMENSIONAL_SUPERPOSITIONS
//                }, new ItemStack(Femtocraft.itemInfiniteVolumePolychora()),
//                        false, null
//                )),
//
//                (new Technology(
//                        TEMPORAL_THREADING, "Having multiple timelines do work at once.",
//                        EnumTechLevel.TEMPORAL, new String[]{
//                        SPACETIME_EXPLOITATION,
//                        ELEMENT_MANUFACTURING
//                }, new ItemStack(Femtocraft.blockFemtoChronoshifter()),
//                        false, getInput(new ItemStack(Femtocraft.blockFemtoChronoshifter())),
//                        new ItemStack(Femtocraft.blockFemtoChronoshifter())
//                )),
//
//                (new Technology(
//                        CAUSALITY_SINGULARITY,
//                        "If you researched this, you never would have researched this.",
//                        EnumTechLevel.TEMPORAL, new String[]{
//                        TEMPORAL_THREADING
//                }, new ItemStack(Femtocraft.itemInfinitelyRecursiveALU()),
//                        false, null
//                )),
//
//                (new Technology(
//                        DEMONIC_PARTICULATES, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                DIMENSIONAL_TRANSFORMATION,
//                                ELEMENT_MANUFACTURING
//                        },
//                        new ItemStack(Femtocraft.blockFemtoCable()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemStyxValve())), new ItemStack(Femtocraft.itemStyxValve())
//                )),
//
//                //    public static ResearchTechnology technologyPerfectScheduling = new ResearchTechnology(
////            "Perfect Scheduling", "Never miss a meeting!", EnumTechLevel.FEMTO,
////            new ArrayList<ResearchTechnology>(Arrays
////                    .asList(technologyQuantumComputing)), null,
////            false, new ArrayList<ItemStack>()
////    );
//                (new Technology(
//                        PARTICLE_EXCITATION, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                ELEMENT_MANUFACTURING
//                        }, new ItemStack(
//                        Femtocraft.blockFemtoImpulserUnlit()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockFemtoImpulserUnlit())),
//                        new ItemStack(Femtocraft.blockFemtoImpulserUnlit())
//                )),
//
//                (new Technology(
//                        PARTICLE_MANIPULATION, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                ELEMENT_MANUFACTURING
//                        }, new ItemStack(
//                        Femtocraft.blockFemtoRepurposer()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockFemtoRepurposer())),
//                        new ItemStack(Femtocraft.blockFemtoRepurposer())
//                )),
//
//                (new Technology(
//                        SPIN_RETENTION,
//                        "",
//                        EnumTechLevel.FEMTO,
//                        new String[]{
//                                PARTICLE_MANIPULATION
//                        },
//                        new ItemStack(Femtocraft.itemQuantumSchematic()),
//                        false, getInput(new ItemStack(Femtocraft.itemQuantumSchematic())),
//                        new ItemStack(Femtocraft.itemQuantumSchematic())
//                )),
//
//                (new Technology(
//                        SPONTANEOUS_GENERATION, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                QUANTUM_ROBOTICS,
//                                DEMONIC_PARTICULATES
//                        }, new ItemStack(Femtocraft.blockNullEqualizer()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockNullEqualizer())),
//                        new ItemStack(Femtocraft.blockNullEqualizer())
//                )),
//
//                (new Technology(
//                        CORRUPTION_STABILIZATION, "", EnumTechLevel.FEMTO,
//                        new String[]{
//                                SPONTANEOUS_GENERATION
//                        }, new ItemStack(Femtocraft.blockFemtoCubeFrame()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.blockFemtoCubeFrame())),
//                        new ItemStack(Femtocraft.blockFemtoCubeFrame())
//                )),
//
//                (new Technology(
//                        STELLAR_MIMICRY, "Make your own pet star!", EnumTechLevel.FEMTO,
//                        new String[]{
//                                DEMONIC_PARTICULATES,
//                                ELEMENT_MANUFACTURING,
//                                CAUSALITY_SINGULARITY,
//                                MULTI_DIMENSIONAL_INDUSTRY
//                        },
//                        new ItemStack(Femtocraft.blockStellaratorCore()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemStellaratorPlating())),
//                        new ItemStack(Femtocraft.itemStellaratorPlating())
//                )),
//
//                (new Technology(
//                        ENERGY_CONVERSION, "Plasma to Energy", EnumTechLevel.FEMTO,
//                        new String[]{
//                                STELLAR_MIMICRY}
//                        , new ItemStack(Femtocraft.blockPlasmaTurbine()),
//                        false, getInput(new ItemStack(Femtocraft.blockPlasmaTurbine())),
//                        new ItemStack(Femtocraft.blockPlasmaTurbine())
//                )),
//
//                (new Technology(
//                        MATTER_CONVERSION, "Plasma to Mass", EnumTechLevel.FEMTO,
//                        new String[]{
//                                STELLAR_MIMICRY}
//                        , new ItemStack(Femtocraft.blockPlasmaCondenser()),
//                        false, getInput(new ItemStack(Femtocraft.blockPlasmaCondenser())),
//                        new ItemStack(Femtocraft.blockPlasmaCondenser())
//                )),
//
//                (new Technology(
//                        NETHER_STAR_FABRICATION, "Creating the unimaginable.",
//                        EnumTechLevel.FEMTO, new String[]{
//                        STELLAR_MIMICRY,
//                        CORRUPTION_STABILIZATION,
//                        PARTICLE_MANIPULATION}
//                        , new ItemStack(Items.nether_star),
//                        true, getInput(new ItemStack(Items.nether_star)), new ItemStack(Items.nether_star)
//                )),
//
//                (new Technology(
//                        MOLECULAR_MANIPULATION, "", EnumTechLevel.MICRO,
//                        new String[]{
//                                ALGORITHMS, MECHANICAL_PRECISION,
//                                BASIC_CHEMISTRY}
//                        , new ItemStack(Femtocraft.blockMicroDeconstructor()),
//                        false,
//                        getInput(new ItemStack(Femtocraft.itemKineticPulverizer())),
//                        new ItemStack(Femtocraft.itemKineticPulverizer())
//                )),
//
//                //    @Technology
////    public static ResearchTechnology technologyParticleDestruction = new ResearchTechnology(
////            "Particle Destruction", "", EnumTechLevel.FEMTO,
////            new ArrayList<ResearchTechnology>(Arrays.asList(
////                    technologyParticleManipulation,
////                    technologySingularityCalculator)), null, false,
////            new ArrayList<ItemStack>()
////    );
//                (new Technology(
//                        MACROSCOPIC_STRUCTURES, "The patterns everything take.",
//                        EnumTechLevel.MACRO, null, new ItemStack(Items.iron_pickaxe),
//
//                        true, null, null)
//
//                {
//                    @Override
//                    public Class<? extends GuiTechnology> getGuiClass() {
//                        return GuiTechnologyMacroscopicStructure.class;
//                    }
//                }
//
//                )
//        ));
//    }
//
//    private static ItemStack[] getInput(ItemStack itemStack) {
//        AssemblerRecipe recipe = Femtocraft.recipeManager().assemblyRecipes.getRecipe(itemStack);
//        return recipe == null ? null : recipe.input;
//    }
//}
