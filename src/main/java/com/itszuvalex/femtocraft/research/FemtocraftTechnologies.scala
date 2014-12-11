package com.itszuvalex.femtocraft.research

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import com.itszuvalex.femtocraft.managers.assembler.ComponentRegistry
import com.itszuvalex.femtocraft.managers.research.Technology
import com.itszuvalex.femtocraft.research.gui.technology.{GuiTechnology, GuiTechnologyAdvancedChemistry, GuiTechnologyAppliedParticlePhysics, GuiTechnologyMacroscopicStructure}
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

/**
 * Created by Chris on 10/4/2014.
 */
object FemtocraftTechnologies {
  val MACROSCOPIC_STRUCTURES      = "Macroscopic Structures"
  val MOLECULAR_MANIPULATION      = "Molecular Manipulation"
  val NETHER_STAR_FABRICATION     = "Nether Star Fabrication"
  val MATTER_CONVERSION           = "Matter Conversion"
  val ENERGY_CONVERSION           = "Energy Conversion"
  val STELLAR_MIMICRY             = "Stellar Mimicry"
  val CORRUPTION_STABILIZATION    = "Corruption Stabilization"
  val SPONTANEOUS_GENERATION      = "Spontaneous Generation"
  val SPIN_RETENTION              = "Spin Retention"
  val PARTICLE_MANIPULATION       = "Particle Manipulation"
  val PARTICLE_EXCITATION         = "Particle Excitation"
  val DEMONIC_PARTICULATES        = "Demonic Particulates"
  val CAUSALITY_SINGULARITY       = "Causality Singularity"
  val TEMPORAL_THREADING          = "Temporal Threading"
  val MULTI_DIMENSIONAL_INDUSTRY  = "Multi-Dimensional Industry"
  val DIMENSIONAL_SUPERPOSITIONS  = "Dimensional Superpositions"
  val ELEMENT_MANUFACTURING       = "Element Manufacturing"
  val QUANTUM_ROBOTICS            = "Quantum Robotics"
  val QUANTUM_COMPUTING           = "Quantum Computing"
  val APPLIED_PARTICLE_PHYSICS    = "Applied Particle Physics"
  val HARNESSED_NUCLEAR_DECAY     = "Harnessed Nuclear Decay"
  val THORIUM_FISSIBILITY         = "Thorium Fissibility"
  val POTENTIAL_HARVESTING        = "Potential Harvesting"
  val QUANTUM_INTERACTIVITY       = "Quantum Interactivity"
  val SPACETIME_EXPLOITATION      = "Spacetime Exploitation"
  val REALITY_OVERCLOCKER         = "Reality Overclocker"
  val TEMPORAL_PIPELINING         = "Temporal Pipelining"
  val DIMENSIONAL_TRANSFORMATION  = "Dimensional Transformation"
  val LOCALITY_ENTANGLER          = "Locality Entangler"
  val DIMENSIONAL_BRAIDING        = "Dimensional Braiding"
  val SPACETIME_MANIPULATION      = "Spacetime Manipulation"
  val DIGITIZED_WORKLOADS         = "Digitized Workloads"
  val ATOMIC_MANIPULATION         = "Atomic Manipulation"
  val KINETIC_DISSOCIATION        = "Kinetic Dissociation"
  val INDUSTRIAL_STORAGE          = "Industrial Storage"
  val POTENTIALITY_TRANSFORMATION = "Potentiality Transformation"
  val GEOTHERMAL_HARNESSING       = "Geothermal Harnessing"
  val FARENITE_STABILIZATION      = "Farenite Stabilization"
  val ARTIFICIAL_MATERIALS        = "Artificial Materials"
  val ADVANCED_CHEMISTRY          = "Advanced Chemistry"
  val POTENTIALITY_GENERATION     = "Potentiality Generation"
  val POTENTIALITY_HARNESSING     = "Potentiality Harnessing"
  val POTENTIALITY_STORAGE        = "Potentiality Storage"
  val POTENTIALITY                = "Potentiality"
  val BASIC_CHEMISTRY             = "Basic Chemistry"
  val RESOURCE_OPTIMIZATION       = "Resource Optimization"
  val DISCRIMINATING_VACUUM_TUBE  = "Discriminating Vacuum Tube"
  val PATTERN_RECOGNITION         = "Pattern Recognition"
  val WORKLOAD_SCHEDULING         = "Workload Scheduling"
  val ADVANCED_PROGRAMMING        = "Advanced Programming"
  val NANO_CIRCUITS               = "Nano Circuits"
  val SUCTION_PIPES               = "Suction Pipes"
  val VACUUM_TUBE_HUB             = "VacuumTube Hub"
  val VACUUM_TUBES                = "Vacuum Tubes"
  val POWER_OF_NOTHING            = "Power of Nothing"
  val MECHANICAL_PRECISION        = "Mechanical Precision"
  val ALGORITHMS                  = "Algorithms"
  val SCIENTIFIC_THEORY           = "Scientific Theory"
  val MACHINING                   = "Machining"
  val BASIC_CIRCUITS              = "Basic Circuits"
  val METALLURGY                  = "Metallurgy"

  def defaultTechnologies: util.List[ITechnology] = {
    new util.ArrayList[ITechnology](util.Arrays.
                                    asList(

        new Technology(METALLURGY, "The metals that populate your world.", EnumTechLevel.MACRO, Array[String](MACROSCOPIC_STRUCTURES), new ItemStack(Femtocraft.itemIngotTemperedTitanium), true, null, null, null, "    The world is full of many interesting things.  Animals roam the fields, " + "monsters rule the night, and eldritch energies course throughout the land.  Perhaps most" + " " + "beneficial to you, however, are the various ores that inhabit the ground.\n    Iron and " + "coal" + " are plentiful, but you know both are merely the tip of the iceberg when it comes to " + "structural integrity and fuel." + (if (FemtocraftConfigs.titaniumGen) "\n\n            Titanium\n\n    This silvery metal quickly corrodes when exposed" + " to " + "air, resulting in a dark oxidation on its surface.  It is most commonly found at" + " " + "depths of " + FemtocraftConfigs.titaniumOreYHeightMax + " to " + FemtocraftConfigs.titaniumOreYHeightMin + ".  It has extremely high durability, making it suitable for encasing mechanisms " + "and " + "circuitry.  You theorize that you can also temper this metal by running it under" + " " + "intense heat for extra durability." else "") + (if (FemtocraftConfigs.platinumGen) "\n\n            Platinum\n\n    A rare, shiny metal, " + "renowned for its resistance to corrosion.  Most commonly found between depths of" + " " + FemtocraftConfigs.platinumOreYHeightMax + " and " + FemtocraftConfigs.platinumOreYHeightMin + ", it is useful for electronics where exposure to hostile chemicals is a certainty." else "") + (if (FemtocraftConfigs.thoriumGen) "\n\n            Thorium\n\n    A heavy metal, with uses in nuclear decay chains." + "  " + "Found in depths of " + FemtocraftConfigs.thoriumOreYHeightMax + " to " + FemtocraftConfigs.thoriumOreYHeightMin + ", it's only truly useful once you figure out how to harness the power of its " + "nuclear" + " decay." else ""), false, true),

        new Technology(BASIC_CIRCUITS, "Basic logic for basic machines.", EnumTechLevel.MACRO, Array[String](MACROSCOPIC_STRUCTURES), new ItemStack(Femtocraft.itemMicrochip), true, getInput(new ItemStack(Femtocraft.itemConductivePowder)), new ItemStack(Femtocraft.itemConductivePowder), null, "    Machines and mechanisms are worthless heaps of metal unless there is a purpose " + "behind them.  You have deduced a simple set of logical mechanisms and a simple means" + " of producing them.__Recipe.Crafting:Femtocraft:item.ItemConductivePowder--This powder balances the volatility of farenite with the stability of lapis, making it useful for conduction.____Recipe.Crafting:Femtocraft:item.ItemBoard--A " + "simple assembly of sticks, side-by-side, gives a base for mounting logic assemblies" + ".____Recipe.Crafting:Femtocraft:item.ItemPrimedBoard--Some conductive powder on the " + "board, when run through an oven, creates a foundation for circuitry.____Recipe" + ".Crafting:Femtocraft:item.ItemSpool--Just a thin rod of wood with end caps, " + "it makes an excellent storage unit for wires.____Recipe.Crafting:Femtocraft:item" + ".ItemSpoolGold--Your studies show gold is an excellent conductor, " + "and you plan on using nothing but the highest quality for your machines.____Recipe" + ".Crafting:Femtocraft:item.ItemSpoolPlatinum--Some thin platinum wire on a spool.  " + "Useful for corrosive environments.____Recipe.Crafting:Femtocraft:item" + ".ItemMicrochip--Thin wiring on a solid board, these multipurpose devices enable your" + " machines to perform basic logic.__", false, true),

        new Technology(MACHINING, "Start your industry!", EnumTechLevel.MICRO, Array[String](METALLURGY, BASIC_CIRCUITS), new ItemStack(Femtocraft.itemMicroPlating), false, getInput(new ItemStack(Femtocraft.itemMicroPlating)), new ItemStack(Femtocraft.itemMicroPlating), "", "", false, false),
        new Technology(SCIENTIFIC_THEORY, "Gentlemen, start your research!", EnumTechLevel.MACRO, Array[String](MACROSCOPIC_STRUCTURES), new ItemStack(Femtocraft.blockResearchConsole), true, null, null, null, "What is a scientist without the scientific method?  Luckily for you, " + "you no longer have to experiment.  This handy Research Computer analyzes all of your " + "knowledge and will theorize prototypes for you to make.  Stick these prototypes into the" + " " + "Research Console to have it analyze the potential uses and to store the standardized " + "blueprints into your knowledge store. __Recipe.Assembler:Femtocraft:tile" + ".BlockResearchComputer--Allows visual access to your personalized knowledge store.__" + "__Recipe" + ".Assembler:Femtocraft:tile.BlockResearchConsole--Analyzes your prototypes and generates " + "standardized blueprints.__", false, true),
        new Technology(ALGORITHMS, "", EnumTechLevel.MICRO, Array[String](MACHINING), new ItemStack(Femtocraft.blockEncoder), false, getInput(new ItemStack(Femtocraft.blockEncoder)), new ItemStack(Femtocraft.blockEncoder)),
        new Technology(MECHANICAL_PRECISION, "", EnumTechLevel.MICRO, Array[String](MACHINING), new ItemStack(Femtocraft.blockMicroFurnaceUnlit), false, getInput(new ItemStack(Femtocraft.itemHeatingElement)), new ItemStack(Femtocraft.itemHeatingElement)),
        new Technology(POWER_OF_NOTHING, "\"Poof!\" It's nothing!", EnumTechLevel.MICRO, Array[String](MECHANICAL_PRECISION), new ItemStack(Femtocraft.itemVacuumCore), false, getInput(new ItemStack(Femtocraft.itemVacuumCore)), new ItemStack(Femtocraft.itemVacuumCore)),
        new Technology(VACUUM_TUBES, "These tubes contain nothing!", EnumTechLevel.MICRO, Array[String](POWER_OF_NOTHING), new ItemStack(Femtocraft.blockVacuumTube), false, getInput(new ItemStack(Femtocraft.blockVacuumTube)), new ItemStack(Femtocraft.blockVacuumTube)),
        new Technology(VACUUM_TUBE_HUB, "A place for nothing to congregate.", EnumTechLevel.MICRO, Array[String](VACUUM_TUBES), null, false, null),
        new Technology(SUCTION_PIPES, "These pipes suck!", EnumTechLevel.MICRO, Array[String](POWER_OF_NOTHING), new ItemStack(Femtocraft.blockSuctionPipe), false, getInput(new ItemStack(Femtocraft.blockSuctionPipe)), new ItemStack(Femtocraft.blockSuctionPipe)),
        new Technology(NANO_CIRCUITS, "", EnumTechLevel.NANO, Array[String](MECHANICAL_PRECISION, BASIC_CIRCUITS), new ItemStack(Femtocraft.itemNanochip), true, getInput(new ItemStack(Femtocraft.itemNanochip)), new ItemStack(Femtocraft.itemNanochip)),
        new Technology(ADVANCED_PROGRAMMING, "", EnumTechLevel.NANO, Array[String](NANO_CIRCUITS), new ItemStack(Femtocraft.itemBasicAICore), false, getInput(new ItemStack(Femtocraft.itemBasicAICore)), new ItemStack(Femtocraft.itemBasicAICore)),
        new Technology(WORKLOAD_SCHEDULING, "", EnumTechLevel.NANO, Array[String](ADVANCED_PROGRAMMING), new ItemStack(Femtocraft.itemSchedulerCore), false, getInput(new ItemStack(Femtocraft.itemSchedulerCore)), new ItemStack(Femtocraft.itemSchedulerCore)),
        new Technology(PATTERN_RECOGNITION, "", EnumTechLevel.NANO, Array[String](ADVANCED_PROGRAMMING), new ItemStack(Femtocraft.itemLearningCore), false, getInput(new ItemStack(Femtocraft.itemLearningCore)), new ItemStack(Femtocraft.itemLearningCore)),
        new Technology(DISCRIMINATING_VACUUM_TUBE, "", EnumTechLevel.NANO, Array[String](VACUUM_TUBE_HUB, PATTERN_RECOGNITION), new ItemStack(Femtocraft.blockVacuumTube), false, null),
        new Technology(RESOURCE_OPTIMIZATION, "", EnumTechLevel.NANO, Array[String](ADVANCED_PROGRAMMING), new ItemStack(Femtocraft.itemManagerCore), false, getInput(new ItemStack(Femtocraft.itemManagerCore)), new ItemStack(Femtocraft.itemManagerCore)),
        new Technology(BASIC_CHEMISTRY, "Composition of Matter", EnumTechLevel.MACRO, Array[String](MACROSCOPIC_STRUCTURES), new ItemStack(Femtocraft.itemMineralLattice), true, null, null, null, "    Inspecting the materials around you leads you to the conclusion that several of these materials share similarities with each other.  Long nights of study and experiments have shown that, in fact, these objects are created out of complex combinations of several, smaller components." + ComponentRegistry.getComponentsInAssemblerRecipeDisplayString(EnumTechLevel.MICRO), false, true),
        new Technology(POTENTIALITY, "", EnumTechLevel.MICRO, Array[String](BASIC_CIRCUITS, BASIC_CHEMISTRY), new ItemStack(Femtocraft.blockMicroCable), false, getInput(new ItemStack(Femtocraft.itemMicroCoil)), new ItemStack(Femtocraft.itemMicroCoil)),
        new Technology(POTENTIALITY_STORAGE, "", EnumTechLevel.MICRO, Array[String](POTENTIALITY, MACHINING), new ItemStack(Femtocraft.blockMicroCube), false, getInput(new ItemStack(Femtocraft.itemBattery)), new ItemStack(Femtocraft.itemBattery)),
        new Technology(POTENTIALITY_HARNESSING, "", EnumTechLevel.MICRO, Array[String](POTENTIALITY), new ItemStack(Femtocraft.blockMicroChargingBase), false, getInput(new ItemStack(Femtocraft.blockMicroChargingBase)), new ItemStack(Femtocraft.blockMicroChargingBase)),
        new Technology(POTENTIALITY_GENERATION, "Build your potential!", EnumTechLevel.MICRO, Array[String](MECHANICAL_PRECISION, POTENTIALITY_HARNESSING), new ItemStack(Femtocraft.blockMagneticInductionGenerator), false, getInput(new ItemStack(Femtocraft.blockMagneticInductionGenerator)), new ItemStack(Femtocraft.blockMagneticInductionGenerator)),
        new Technology(ADVANCED_CHEMISTRY, "", EnumTechLevel.NANO, Array[String](POTENTIALITY_GENERATION, BASIC_CHEMISTRY), new ItemStack(Femtocraft.itemCrystallite), true, null, null) {
          override def getGuiClass: Class[_ <: GuiTechnology] = {
            classOf[GuiTechnologyAdvancedChemistry]
          }
        },
        new Technology(ARTIFICIAL_MATERIALS, "Make what you need.", EnumTechLevel.NANO, Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY), new ItemStack(Femtocraft.itemNanoPlating), false, getInput(new ItemStack(Femtocraft.itemNanoPlating)), new ItemStack(Femtocraft.itemNanoPlating)),
        new Technology(FARENITE_STABILIZATION, "", EnumTechLevel.NANO, Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY, ARTIFICIAL_MATERIALS), new ItemStack(Femtocraft.blockNanoCable), false, getInput(new ItemStack(Femtocraft.itemFluidicConductor)), new ItemStack(Femtocraft.itemFluidicConductor)),
        new Technology(GEOTHERMAL_HARNESSING, "Harness the geological thermal energy.", EnumTechLevel.NANO, Array[String](FARENITE_STABILIZATION, ADVANCED_CHEMISTRY, POTENTIALITY_HARNESSING), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), false, getInput(new ItemStack(Femtocraft.blockCryoEndothermalChargingBase)), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase)),
        new Technology(POTENTIALITY_TRANSFORMATION, "", EnumTechLevel.NANO, Array[String](FARENITE_STABILIZATION), new ItemStack(Femtocraft.blockOrbitalEqualizer), false, getInput(new ItemStack(Femtocraft.blockOrbitalEqualizer)), new ItemStack(Femtocraft.blockOrbitalEqualizer)),
        new Technology(INDUSTRIAL_STORAGE, "", EnumTechLevel.NANO, Array[String](POTENTIALITY_TRANSFORMATION, RESOURCE_OPTIMIZATION), new ItemStack(Femtocraft.blockNanoCubeFrame), false, getInput(new ItemStack(Femtocraft.blockNanoCubeFrame)), new ItemStack(Femtocraft.blockNanoCubeFrame)),
        new Technology(KINETIC_DISSOCIATION, "", EnumTechLevel.NANO, Array[String](WORKLOAD_SCHEDULING, ARTIFICIAL_MATERIALS, RESOURCE_OPTIMIZATION), new ItemStack(Femtocraft.blockNanoInnervatorUnlit), false, getInput(new ItemStack(Femtocraft.blockNanoInnervatorUnlit)), new ItemStack(Femtocraft.blockNanoInnervatorUnlit)),
        new Technology(ATOMIC_MANIPULATION, "", EnumTechLevel.NANO, Array[String](ARTIFICIAL_MATERIALS, WORKLOAD_SCHEDULING, RESOURCE_OPTIMIZATION), new ItemStack(Femtocraft.blockNanoDismantler), false, getInput(new ItemStack(Femtocraft.blockNanoDismantler)), new ItemStack(Femtocraft.blockNanoDismantler)),
        new Technology(DIGITIZED_WORKLOADS, "", EnumTechLevel.NANO, Array[String](ATOMIC_MANIPULATION, RESOURCE_OPTIMIZATION), new ItemStack(Femtocraft.itemDigitalSchematic), false, getInput(new ItemStack(Femtocraft.itemDigitalSchematic)), new ItemStack(Femtocraft.itemDigitalSchematic)),
        new Technology(SPACETIME_MANIPULATION, "The question is: When did you ACTUALLY finish researching this?", EnumTechLevel.NANO, Array[String](ADVANCED_CHEMISTRY), new ItemStack(Femtocraft.itemDimensionalMonopole), true, getInput(new ItemStack(Femtocraft.itemDimensionalMonopole)), new ItemStack(Femtocraft.itemDimensionalMonopole)),
        new Technology(DIMENSIONAL_BRAIDING, "Interweave reality", EnumTechLevel.DIMENSIONAL, Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION), new ItemStack(Femtocraft.blockNanoEnmesher), false, getInput(new ItemStack(Femtocraft.blockNanoEnmesher)), new ItemStack(Femtocraft.blockNanoEnmesher)),
        new Technology(LOCALITY_ENTANGLER, "", EnumTechLevel.DIMENSIONAL, Array[String](DIMENSIONAL_BRAIDING), new ItemStack(Femtocraft.itemPanLocationalComputer), false, getInput(new ItemStack(Femtocraft.itemPanLocationalComputer)), new ItemStack(Femtocraft.itemPanLocationalComputer)),
        new Technology(DIMENSIONAL_TRANSFORMATION, "", EnumTechLevel.FEMTO, Array[String](DIMENSIONAL_BRAIDING), new ItemStack(Femtocraft.itemIngotMalenite), true, null),
        new Technology(TEMPORAL_PIPELINING, "Start working on the next second, before it begins.", EnumTechLevel.TEMPORAL, Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION), new ItemStack(Femtocraft.blockNanoHorologe), false, getInput(new ItemStack(Femtocraft.blockNanoHorologe)), new ItemStack(Femtocraft.blockNanoHorologe)),
        new Technology(REALITY_OVERCLOCKER, "", EnumTechLevel.TEMPORAL, Array[String](TEMPORAL_PIPELINING), new ItemStack(Femtocraft.itemInfallibleEstimator), false, null),
        new Technology(SPACETIME_EXPLOITATION, "Exploiting reality itself.", EnumTechLevel.FEMTO, Array[String](LOCALITY_ENTANGLER, REALITY_OVERCLOCKER), new ItemStack(Femtocraft.itemPandoraCube), true, null),
        new Technology(QUANTUM_INTERACTIVITY, "", EnumTechLevel.FEMTO, Array[String](DIMENSIONAL_TRANSFORMATION, NANO_CIRCUITS, DIMENSIONAL_BRAIDING, TEMPORAL_PIPELINING), new ItemStack(Femtocraft.itemCharosGate), true, getInput(new ItemStack(Femtocraft.itemCharosGate)), new ItemStack(Femtocraft.itemCharosGate)),
        new Technology(POTENTIAL_HARVESTING, "Headhunters Unite!", EnumTechLevel.MICRO, Array[String](POTENTIALITY_STORAGE, POTENTIALITY_HARNESSING), new ItemStack(Femtocraft.blockMicroChargingCapacitor), false, getInput(new ItemStack(Femtocraft.blockMicroChargingCapacitor)), new ItemStack(Femtocraft.blockMicroChargingCapacitor)),
        new Technology(THORIUM_FISSIBILITY, "", EnumTechLevel.NANO, Array[String](POTENTIAL_HARVESTING, ADVANCED_CHEMISTRY), new ItemStack(Femtocraft.itemIngotThFaSalt), false, getInput(new ItemStack(Femtocraft.itemIngotThFaSalt)), new ItemStack(Femtocraft.itemIngotThFaSalt)),
        new Technology(HARNESSED_NUCLEAR_DECAY, "", EnumTechLevel.NANO, Array[String](THORIUM_FISSIBILITY, RESOURCE_OPTIMIZATION, GEOTHERMAL_HARNESSING, PATTERN_RECOGNITION), new ItemStack(Femtocraft.blockFissionReactorCore), false, getInput(new ItemStack(Femtocraft.itemFissionReactorPlating)), new ItemStack(Femtocraft.itemFissionReactorPlating)),
        new Technology(APPLIED_PARTICLE_PHYSICS, "Like theoretical particle physics.", EnumTechLevel.FEMTO, Array[String](HARNESSED_NUCLEAR_DECAY, ADVANCED_CHEMISTRY), new ItemStack(Femtocraft.itemCubit), true, null, null) {
          override def getGuiClass: Class[_ <: GuiTechnology] = {
            classOf[GuiTechnologyAppliedParticlePhysics]
          }
        },
        new Technology(QUANTUM_COMPUTING, "", EnumTechLevel.FEMTO, Array[String](QUANTUM_INTERACTIVITY, ADVANCED_PROGRAMMING, APPLIED_PARTICLE_PHYSICS, LOCALITY_ENTANGLER, REALITY_OVERCLOCKER), new ItemStack(Femtocraft.itemErinyesCircuit), false, getInput(new ItemStack(Femtocraft.itemErinyesCircuit)), new ItemStack(Femtocraft.itemErinyesCircuit)),
        new Technology(QUANTUM_ROBOTICS, "", EnumTechLevel.FEMTO, Array[String](QUANTUM_COMPUTING, SPACETIME_EXPLOITATION), new ItemStack(Femtocraft.itemHerculesDrive), false, getInput(new ItemStack(Femtocraft.itemHerculesDrive)), new ItemStack(Femtocraft.itemHerculesDrive)),
        new Technology(ELEMENT_MANUFACTURING, "Create circles, spheres, and ovals!", EnumTechLevel.FEMTO, Array[String](QUANTUM_ROBOTICS), new ItemStack(Femtocraft.itemFemtoPlating), false, getInput(new ItemStack(Femtocraft.itemFemtoPlating)), new ItemStack(Femtocraft.itemFemtoPlating)),
        new Technology(DIMENSIONAL_SUPERPOSITIONS, "It's a layer effect.", EnumTechLevel.DIMENSIONAL, Array[String](SPACETIME_EXPLOITATION, ELEMENT_MANUFACTURING), new ItemStack(Femtocraft.blockFemtoEntangler), false, getInput(new ItemStack(Femtocraft.blockFemtoEntangler)), new ItemStack(Femtocraft.blockFemtoEntangler)),
        new Technology(MULTI_DIMENSIONAL_INDUSTRY, "Share the load.", EnumTechLevel.DIMENSIONAL, Array[String](DIMENSIONAL_SUPERPOSITIONS), new ItemStack(Femtocraft.itemInfiniteVolumePolychora), false, null),
        new Technology(TEMPORAL_THREADING, "Having multiple timelines do work at once.", EnumTechLevel.TEMPORAL, Array[String](SPACETIME_EXPLOITATION, ELEMENT_MANUFACTURING), new ItemStack(Femtocraft.blockFemtoChronoshifter), false, getInput(new ItemStack(Femtocraft.blockFemtoChronoshifter)), new ItemStack(Femtocraft.blockFemtoChronoshifter)),
        new Technology(CAUSALITY_SINGULARITY, "If you researched this, you never would have researched this.", EnumTechLevel.TEMPORAL, Array[String](TEMPORAL_THREADING), new ItemStack(Femtocraft.itemInfinitelyRecursiveALU), false, null),
        new Technology(DEMONIC_PARTICULATES, "", EnumTechLevel.FEMTO, Array[String](DIMENSIONAL_TRANSFORMATION, ELEMENT_MANUFACTURING), new ItemStack(Femtocraft.blockFemtoCable), false, getInput(new ItemStack(Femtocraft.itemStyxValve)), new ItemStack(Femtocraft.itemStyxValve)),
        new Technology(PARTICLE_EXCITATION, "", EnumTechLevel.FEMTO, Array[String](ELEMENT_MANUFACTURING), new ItemStack(Femtocraft.blockFemtoImpulserUnlit), false, getInput(new ItemStack(Femtocraft.blockFemtoImpulserUnlit)), new ItemStack(Femtocraft.blockFemtoImpulserUnlit)),
        new Technology(PARTICLE_MANIPULATION, "", EnumTechLevel.FEMTO, Array[String](ELEMENT_MANUFACTURING), new ItemStack(Femtocraft.blockFemtoRepurposer), false, getInput(new ItemStack(Femtocraft.blockFemtoRepurposer)), new ItemStack(Femtocraft.blockFemtoRepurposer)),
        new Technology(SPIN_RETENTION, "", EnumTechLevel.FEMTO, Array[String](PARTICLE_MANIPULATION), new ItemStack(Femtocraft.itemQuantumSchematic), false, getInput(new ItemStack(Femtocraft.itemQuantumSchematic)), new ItemStack(Femtocraft.itemQuantumSchematic)),
        new Technology(SPONTANEOUS_GENERATION, "", EnumTechLevel.FEMTO, Array[String](QUANTUM_ROBOTICS, DEMONIC_PARTICULATES), new ItemStack(Femtocraft.blockNullEqualizer), false, getInput(new ItemStack(Femtocraft.blockNullEqualizer)), new ItemStack(Femtocraft.blockNullEqualizer)),
        new Technology(CORRUPTION_STABILIZATION, "", EnumTechLevel.FEMTO, Array[String](SPONTANEOUS_GENERATION), new ItemStack(Femtocraft.blockFemtoCubeFrame), false, getInput(new ItemStack(Femtocraft.blockFemtoCubeFrame)), new ItemStack(Femtocraft.blockFemtoCubeFrame)),
        new Technology(STELLAR_MIMICRY, "Make your own pet star!", EnumTechLevel.FEMTO, Array[String](DEMONIC_PARTICULATES, ELEMENT_MANUFACTURING, CAUSALITY_SINGULARITY, MULTI_DIMENSIONAL_INDUSTRY), new ItemStack(Femtocraft.blockStellaratorCore), false, getInput(new ItemStack(Femtocraft.itemStellaratorPlating)), new ItemStack(Femtocraft.itemStellaratorPlating)),
        new Technology(ENERGY_CONVERSION, "Plasma to Energy", EnumTechLevel.FEMTO, Array[String](STELLAR_MIMICRY), new ItemStack(Femtocraft.blockPlasmaTurbine), false, getInput(new ItemStack(Femtocraft.blockPlasmaTurbine)), new ItemStack(Femtocraft.blockPlasmaTurbine)),
        new Technology(MATTER_CONVERSION, "Plasma to Mass", EnumTechLevel.FEMTO, Array[String](STELLAR_MIMICRY), new ItemStack(Femtocraft.blockPlasmaCondenser), false, getInput(new ItemStack(Femtocraft.blockPlasmaCondenser)), new ItemStack(Femtocraft.blockPlasmaCondenser)),
        new Technology(NETHER_STAR_FABRICATION, "Creating the unimaginable.", EnumTechLevel.FEMTO, Array[String](STELLAR_MIMICRY, CORRUPTION_STABILIZATION, PARTICLE_MANIPULATION), new ItemStack(Items.nether_star), true, getInput(new ItemStack(Items.nether_star)), new ItemStack(Items.nether_star)),
        new Technology(MOLECULAR_MANIPULATION, "", EnumTechLevel.MICRO, Array[String](ALGORITHMS, MECHANICAL_PRECISION, BASIC_CHEMISTRY), new ItemStack(Femtocraft.blockMicroDeconstructor), false, getInput(new ItemStack(Femtocraft.itemKineticPulverizer)), new ItemStack(Femtocraft.itemKineticPulverizer)),
        new Technology(MACROSCOPIC_STRUCTURES, "The patterns everything take.", EnumTechLevel.MACRO, null, new ItemStack(Items.iron_pickaxe), true, null, null) {
          override def getGuiClass: Class[_ <: GuiTechnology] = {
            classOf[GuiTechnologyMacroscopicStructure]
          }
        }))
  }

  private def getInput(itemStack: ItemStack): Array[ItemStack] = {
    val recipe: AssemblerRecipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(itemStack)
    if (recipe == null) null else recipe.input
  }
}

