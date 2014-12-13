package com.itszuvalex.femtocraft.research

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.RecipeType
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import com.itszuvalex.femtocraft.implicits.StringImplicits._
import com.itszuvalex.femtocraft.managers.assembler.ComponentRegistry
import com.itszuvalex.femtocraft.managers.research.Technology
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

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
    val ret = new ArrayBuffer[ITechnology]
    ret += new Technology(METALLURGY,
                          "The metals that populate your world.",
                          EnumTechLevel.MACRO,
                          Array[String](MACROSCOPIC_STRUCTURES),
                          new ItemStack(Femtocraft.itemIngotTemperedTitanium),
                          true,
                          null,
                          null,
                          null,
                          "    The world is full of many interesting " + "things.  Animals roam the fields, " + "monsters rule the night, and eldritch " + "energies course throughout the land.  Perhaps " + "most" + " " + "beneficial to you, however, are" + " the various ores that inhabit the ground.\n  " + "  Iron and " + "coal" + " are plentiful, but " + "you know both are merely the tip of the " + "iceberg when it comes to " + "structural " +
                          "integrity and fuel." + (if (FemtocraftConfigs.titaniumGen) {
                            "\n\n            Titanium\n\n    This silvery" +
                            " metal quickly corrodes when exposed" + " to" +
                            " " + "air, resulting in a dark oxidation on " +
                            "its surface.  It is most commonly found at" + " " +
                            "depths of " +
                            FemtocraftConfigs.titaniumOreYHeightMax + " to " +
                            FemtocraftConfigs.titaniumOreYHeightMin + ".  It has extremely high " +
                            "durability, making it suitable for encasing mechanisms " +
                            "" + "and " + "circuitry.  You theorize that you can " +
                            "also temper this metal by running it under" + " " +
                            "intense heat for extra durability."
                          } else {
                            ""
                          }) + (if (FemtocraftConfigs.platinumGen) {
                            "\n\n            Platinum\n\n    A rare, " +
                            "shiny metal, " + "renowned for its " +
                            "resistance to corrosion.  Most commonly " +
                            "found between depths of" + " " +
                            FemtocraftConfigs.platinumOreYHeightMax + " and " +
                            FemtocraftConfigs.platinumOreYHeightMin +
                            ", it is useful for electronics where " +
                            "exposure to hostile chemicals is a " +
                            "certainty."
                          } else {
                            ""
                          }) + (if (FemtocraftConfigs.thoriumGen) {
                            "\n\n            Thorium\n\n    A heavy " +
                            "metal, with uses in nuclear decay chains." +
                            "  " + "Found in depths of " + FemtocraftConfigs.thoriumOreYHeightMax + "" +
                            " to " +
                            FemtocraftConfigs.thoriumOreYHeightMin + ", it's only truly " +
                            "useful once you figure out how to harness " +
                            "the power of its " + "nuclear" + " decay."
                          } else {
                            ""
                          }),
                          false,
                          true)
    ret += new
        Technology(BASIC_CIRCUITS,
                   "Basic logic for basic machines.",
                   EnumTechLevel.MACRO,
                   Array[String](MACROSCOPIC_STRUCTURES),
                   new ItemStack(Femtocraft.itemMicrochip),
                   true,
                   getInput(new ItemStack(Femtocraft.itemConductivePowder)),
                   new ItemStack(Femtocraft.itemConductivePowder),
                   null,
                   "    Machines and mechanisms are worthless " +
                   "heaps of metal unless there is a purpose " +
                   "behind them.  You have deduced a simple set of" +
                   " logical mechanisms and a simple means" +
                   " of producing them." +
                   Femtocraft.itemConductivePowder.toRecipeWithInfoString(RecipeType.CRAFTING, "This powder balances " +
                                                                                               "the volatility of " +
                                                                                               "farenite with the " +
                                                                                               "stability of lapis, " +
                                                                                               "making it useful for " +
                                                                                               "conduction.") +
                   Femtocraft.itemBoard.toRecipeWithInfoString(RecipeType.CRAFTING, "A simple assembly of " +
                                                                                    "sticks, side-by-side, " +
                                                                                    "gives a base for " +
                                                                                    "mounting logic " +
                                                                                    "assemblies.") +
                   Femtocraft.itemSpool.toRecipeWithInfoString(RecipeType.CRAFTING, "Just a thin rod of " +
                                                                                    "wood with end caps, it" +
                                                                                    " makes an excellent " +
                                                                                    "storage unit for wires" +
                                                                                    ".") +
                   Femtocraft.itemSpoolGold.toRecipeWithInfoString(RecipeType.CRAFTING, "Your studies show " +
                                                                                        "gold is an excellent " +
                                                                                        "conductor, and you " +
                                                                                        "plan on using nothing " +
                                                                                        "but the highest " +
                                                                                        "quality for your " +
                                                                                        "machines.") +
                   Femtocraft.itemSpoolPlatinum.toRecipeWithInfoString(RecipeType.CRAFTING, "Some thin platinum " +
                                                                                            "wire on a spool.  " +
                                                                                            "Useful for corrosive " +
                                                                                            "environments.") +
                   Femtocraft.itemMicrochip.toRecipeWithInfoString(RecipeType.CRAFTING, "Thin wiring on a " +
                                                                                        "solid board, these " +
                                                                                        "multipurpose devices " +
                                                                                        "enable your machines " +
                                                                                        "to perform basic logic" +
                                                                                        "."),
                   false,
                   true)
    ret += new Technology(MACHINING,
                          "Start your industry!",
                          EnumTechLevel.MICRO,
                          Array[String](METALLURGY, BASIC_CIRCUITS),
                          new ItemStack(Femtocraft.itemMicroPlating),
                          false,
                          getInput(new ItemStack(Femtocraft.itemMicroPlating)),
                          new ItemStack(Femtocraft.itemMicroPlating),
                          " If you're going to invest resources in expensive machines, you figure it's only best that you create something to encase the fragile inner workings with.  Better yet if these cases also have ports to enable easy input/output and power sockets.  A little bit of slime can go a long way in that instance.",
                          Femtocraft.itemMicroPlating.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                             "Tempered Titanium siding, some basic logic for input/output and power flow regulation, and a slimeball to ensure plugs stick.  Simple yet effective.")
                          ,
                          false,
                          false)
    ret += new Technology(SCIENTIFIC_THEORY,
                          "Gentlemen, start your research!",
                          EnumTechLevel.MACRO,
                          Array[String](MACROSCOPIC_STRUCTURES),
                          new ItemStack(Femtocraft.blockResearchConsole),
                          true,
                          null,
                          null,
                          null,
                          "What is a scientist without the scientific " +
                          "method?  Luckily for you, " + "you no longer " +
                          "have to experiment.  This handy Research " +
                          "Computer analyzes all of your " + "knowledge " +
                          "and will theorize prototypes for you to make. " +
                          " Stick these prototypes into the" + " " +
                          "Research Console to have it analyze the " +
                          "potential uses and to store the standardized "
                          + "blueprints into your knowledge store. " +
                          Femtocraft
                          .blockResearchComputer
                          .toRecipeWithInfoString(RecipeType.CRAFTING,
                                                  "Allows visual access to your personalized knowledge store.") +
                          Femtocraft.blockResearchConsole
                          .toRecipeWithInfoString(RecipeType.CRAFTING,
                                                  "Analyzes your prototypes and generates standardized blueprints."),
                          false,
                          true)
    ret += new Technology(ALGORITHMS,
                          "Ordering machines to do your bidding.",
                          EnumTechLevel.MICRO,
                          Array[String](MACHINING),
                          new ItemStack(Femtocraft.blockEncoder),
                          false,
                          getInput(new ItemStack(Femtocraft.blockEncoder)),
                          new ItemStack(Femtocraft.blockEncoder),
                          " These machines and ideas are starting to get too complicated!  Time to get some scratch space for jotting it all down! *You read off your digital screen.  Speaking of, you realize you never found a keyboard for it.",
                          " A simple set of rules and methods that is capable of governing an entire realm of machines. \n" +
                          Femtocraft.itemPaperSchematic.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                               "A thick durable sheet of paper.") +
                          Femtocraft.itemMicroLogicCore.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                               "While formulating your logic for the ports, you conceive of a large set of circuits that is capable of forming the logical core of a machine.") +
                          Femtocraft.blockEncoder.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                         "A simple machine that it simply encodes the layout of items into a schematic."),
                          false,
                          false)
    ret += new Technology(MECHANICAL_PRECISION,
                          "Mathematically Precise",
                          EnumTechLevel.MICRO,
                          Array[String](MACHINING, POTENTIALITY_HARNESSING, ALGORITHMS),
                          new ItemStack(Femtocraft.blockMicroFurnaceUnlit),
                          false,
                          getInput(new ItemStack(Femtocraft.itemHeatingElement)),
                          new ItemStack(Femtocraft.itemHeatingElement),
                          " Now that you have the power and the logic, time to apply!  The simplest application of the power you have stored is immediate transformation back into energy, in the form of heat.",
                          " The simplest starts can lead to the largest things.\n" +
                          Femtocraft.itemHeatingElement.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                               "A simple setup that turns stored energy back into heat.  This coil heats up hot enough to melt metal.") +
                          Femtocraft.blockMicroFurnaceUnlit.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                                   "Wrapping up the Heating Coil in the safe confines of some plating, along with a logic core to make sure your base doesn't go up in flames."),
                          false,
                          false)
    ret += new Technology(POWER_OF_NOTHING,
                          "\"Poof!\" It's nothing!",
                          EnumTechLevel.MICRO,
                          Array[String](MECHANICAL_PRECISION),
                          new ItemStack(Femtocraft.itemVacuumCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemVacuumCore)),
                          new ItemStack(Femtocraft.itemVacuumCore))
    ret += new Technology(VACUUM_TUBES,
                          "These tubes contain nothing!",
                          EnumTechLevel.MICRO,
                          Array[String](POWER_OF_NOTHING),
                          new ItemStack(Femtocraft.blockVacuumTube),
                          false,
                          getInput(new ItemStack(Femtocraft.blockVacuumTube)),
                          new ItemStack(Femtocraft.blockVacuumTube))
    ret += new Technology(VACUUM_TUBE_HUB,
                          "A place for nothing to congregate.",
                          EnumTechLevel.MICRO,
                          Array[String](VACUUM_TUBES),
                          null,
                          false,
                          null)
    ret += new Technology(SUCTION_PIPES,
                          "These pipes suck!",
                          EnumTechLevel.MICRO,
                          Array[String](POWER_OF_NOTHING),
                          new ItemStack(Femtocraft.blockSuctionPipe),
                          false,
                          getInput(new ItemStack(Femtocraft.blockSuctionPipe)),
                          new ItemStack(Femtocraft.blockSuctionPipe))
    ret += new Technology(NANO_CIRCUITS,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](MECHANICAL_PRECISION, BASIC_CIRCUITS),
                          new ItemStack(Femtocraft.itemNanochip),
                          true,
                          getInput(new ItemStack(Femtocraft.itemNanochip)),
                          new ItemStack(Femtocraft.itemNanochip))
    ret += new Technology(ADVANCED_PROGRAMMING,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS),
                          new ItemStack(Femtocraft.itemBasicAICore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemBasicAICore)),
                          new ItemStack(Femtocraft.itemBasicAICore))
    ret += new Technology(WORKLOAD_SCHEDULING,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemSchedulerCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemSchedulerCore)),
                          new ItemStack(Femtocraft.itemSchedulerCore))
    ret += new Technology(PATTERN_RECOGNITION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemLearningCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemLearningCore)),
                          new ItemStack(Femtocraft.itemLearningCore))
    ret += new Technology(DISCRIMINATING_VACUUM_TUBE,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](VACUUM_TUBE_HUB, PATTERN_RECOGNITION),
                          new ItemStack(Femtocraft.blockVacuumTube),
                          false,
                          null)
    ret += new Technology(RESOURCE_OPTIMIZATION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemManagerCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemManagerCore)),
                          new ItemStack(Femtocraft.itemManagerCore))
    ret += new Technology(BASIC_CHEMISTRY,
                          "Composition of Matter",
                          EnumTechLevel.MACRO,
                          Array[String](MACROSCOPIC_STRUCTURES),
                          new ItemStack(Femtocraft.itemMineralLattice),
                          true,
                          null,
                          null,
                          null,
                          "    Inspecting the materials around you leads " +
                          "you to the conclusion that several of these " +
                          "materials share similarities with each other. " +
                          " Long nights of study and experiments have " +
                          "shown that, in fact, these objects are created" +
                          " out of complex combinations of several, " +
                          "smaller components." + ComponentRegistry
                                                  .getComponentsInAssemblerRecipeDisplayString(EnumTechLevel.MICRO),
                          false,
                          true)
    ret += new Technology(POTENTIALITY,
                          "Power Overwhelming",
                          EnumTechLevel.MICRO,
                          Array[String](BASIC_CIRCUITS, BASIC_CHEMISTRY),
                          new ItemStack(Femtocraft.blockMicroCable),
                          false,
                          getInput(new ItemStack(Femtocraft.itemMicroCoil)),
                          new ItemStack(Femtocraft.itemMicroCoil),
                          "  Through experimentation, you know Farenite has tremendous power storage potential.  A simple assembly of wires and conducting powder may be able to provide a buffer for storage and transfer of energy potential.",
                          "  Eureka!  You finally found a power source worthy of the name.  Through various processes, Farenite has the potential to store and release energy in the form of oscillating photons.  Though your efforts are crude now, you know that you can only get better!\n" +
                          Femtocraft.itemMicroCoil.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                          "A simple block of condensed conductive powder, this induces photon oscillation acting as a solenoid for potential energy.  You have a feeling this will have widespread uses.") +
                          Femtocraft.blockMicroCable.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                            "A rickety assembly, the MicroCable is composed of many micro coils to move power around.  The wires will act to equalize power among each other and they have an internal buffer that collects and moves energy."), false, false)
    ret += new Technology(POTENTIALITY_STORAGE,
                          "Power, Cubed",
                          EnumTechLevel.MICRO,
                          Array[String](POTENTIALITY, MACHINING),
                          new ItemStack(Femtocraft.blockMicroCube),
                          false,
                          getInput(new ItemStack(Femtocraft.itemBattery)),
                          new ItemStack(Femtocraft.itemBattery),
                          " Though micro coils can store some power, you know there's a better way to cram more power into a smaller space.  Forming a metal case to hold the conductive powder might be a better way to ensure that the inherent volatility is contained.\n",
                          Femtocraft.itemBattery.toRecipeWithInfoString(RecipeType.CRAFTING, "Though not much use for anything else, these batteries can store a large amount of power in a small space.") +
                          Femtocraft.blockMicroCube.toRecipeWithInfoString(RecipeType.CRAFTING, "Nothing more than a big box of batteries and some basic input/output circuits.  Each side is individually toggleable with an interface device to determine whether it accepts power or outputs it."),
                          false,
                          false)
    ret += new Technology(POTENTIALITY_HARNESSING,
                          "Harness the Potential all around you.",
                          EnumTechLevel.MICRO,
                          Array[String](POTENTIALITY_STORAGE),
                          new ItemStack(Femtocraft.blockMicroChargingBase),
                          false,
                          getInput(new ItemStack(Femtocraft.blockMicroChargingBase)),
                          new ItemStack(Femtocraft.blockMicroChargingBase),
                          " On a particularly windy day, you touched one of the micro coils you had lying around only to receive a large shock.  You realize that the air itself contains power just waiting for the micro coils to yank it out.",
                          " In order to use the atmospheric power, you need something built to take this amount of energy.  If it could store some amount as well, all the better.\n" +
                          Femtocraft.blockMicroChargingBase.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                                   "A battery box with input cables and a manager core to handle the various stresses of environmental effects.") +
                          Femtocraft.blockMicroChargingCoil.toRecipeWithInfoString(RecipeType.CRAFTING,
                                                                                   "Nothing more than micro coils and a cover intended to grab as much energy from the air as possible."),
                          false, false)
    ret += new Technology(POTENTIALITY_GENERATION,
                          "Build your potential!",
                          EnumTechLevel.MICRO,
                          Array[String](MECHANICAL_PRECISION, POTENTIALITY_HARNESSING),
                          new ItemStack(Femtocraft.blockMagneticInductionGenerator),
                          false,
                          getInput(new ItemStack(Femtocraft.blockMagneticInductionGenerator)),
                          new ItemStack(Femtocraft.blockMagneticInductionGenerator))
    ret += new Technology(ADVANCED_CHEMISTRY,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIALITY_GENERATION, BASIC_CHEMISTRY, MOLECULAR_MANIPULATION),
                          new ItemStack(Femtocraft.itemCrystallite),
                          true,
                          null,
                          null)
    ret += new Technology(ARTIFICIAL_MATERIALS,
                          "Make what you need.",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemNanoPlating),
                          false,
                          getInput(new ItemStack(Femtocraft.itemNanoPlating)),
                          new ItemStack(Femtocraft.itemNanoPlating))
    ret += new Technology(FARENITE_STABILIZATION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY, ARTIFICIAL_MATERIALS),
                          new ItemStack(Femtocraft.blockNanoCable),
                          false,
                          getInput(new ItemStack(Femtocraft.itemFluidicConductor)),
                          new ItemStack(Femtocraft.itemFluidicConductor))
    ret += new Technology(GEOTHERMAL_HARNESSING,
                          "Harness the geological thermal energy.",
                          EnumTechLevel.NANO,
                          Array[String](FARENITE_STABILIZATION, ADVANCED_CHEMISTRY, POTENTIALITY_HARNESSING),
                          new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                          false,
                          getInput(new ItemStack(Femtocraft.blockCryoEndothermalChargingBase)),
                          new ItemStack(Femtocraft.blockCryoEndothermalChargingBase))
    ret += new Technology(POTENTIALITY_TRANSFORMATION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](FARENITE_STABILIZATION),
                          new ItemStack(Femtocraft.blockOrbitalEqualizer),
                          false,
                          getInput(new ItemStack(Femtocraft.blockOrbitalEqualizer)),
                          new ItemStack(Femtocraft.blockOrbitalEqualizer))
    ret += new Technology(INDUSTRIAL_STORAGE,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIALITY_TRANSFORMATION, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoCubeFrame),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoCubeFrame)),
                          new ItemStack(Femtocraft.blockNanoCubeFrame))
    ret += new Technology(KINETIC_DISSOCIATION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](WORKLOAD_SCHEDULING, ARTIFICIAL_MATERIALS, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoInnervatorUnlit),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoInnervatorUnlit)),
                          new ItemStack(Femtocraft.blockNanoInnervatorUnlit))
    ret += new Technology(ATOMIC_MANIPULATION,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](ARTIFICIAL_MATERIALS, WORKLOAD_SCHEDULING, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoDismantler),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoDismantler)),
                          new ItemStack(Femtocraft.blockNanoDismantler))
    ret += new Technology(DIGITIZED_WORKLOADS,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](ATOMIC_MANIPULATION, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.itemDigitalSchematic),
                          false,
                          getInput(new ItemStack(Femtocraft.itemDigitalSchematic)),
                          new ItemStack(Femtocraft.itemDigitalSchematic))
    ret += new Technology(SPACETIME_MANIPULATION,
                          "The question is: When did you ACTUALLY finish " + "researching this?",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemDimensionalMonopole),
                          true,
                          getInput(new ItemStack(Femtocraft.itemDimensionalMonopole)),
                          new ItemStack(Femtocraft.itemDimensionalMonopole))
    ret += new Technology(DIMENSIONAL_BRAIDING,
                          "Interweave reality",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION),
                          new ItemStack(Femtocraft.blockNanoEnmesher),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoEnmesher)),
                          new ItemStack(Femtocraft.blockNanoEnmesher))
    ret += new Technology(LOCALITY_ENTANGLER,
                          "",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](DIMENSIONAL_BRAIDING),
                          new ItemStack(Femtocraft.itemPanLocationalComputer),
                          false,
                          getInput(new ItemStack(Femtocraft.itemPanLocationalComputer)),
                          new ItemStack(Femtocraft.itemPanLocationalComputer))
    ret += new Technology(DIMENSIONAL_TRANSFORMATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](DIMENSIONAL_BRAIDING),
                          new ItemStack(Femtocraft.itemIngotMalenite),
                          true,
                          null)
    ret += new Technology(TEMPORAL_PIPELINING,
                          "Start working on the next second, before it begins.",
                          EnumTechLevel.TEMPORAL,
                          Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION),
                          new ItemStack(Femtocraft.blockNanoHorologe),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoHorologe)),
                          new ItemStack(Femtocraft.blockNanoHorologe))
    ret += new Technology(REALITY_OVERCLOCKER,
                          "",
                          EnumTechLevel.TEMPORAL,
                          Array[String](TEMPORAL_PIPELINING),
                          new ItemStack(Femtocraft.itemInfallibleEstimator),
                          false,
                          null)
    ret += new Technology(SPACETIME_EXPLOITATION,
                          "Exploiting reality itself.",
                          EnumTechLevel.FEMTO,
                          Array[String](LOCALITY_ENTANGLER, REALITY_OVERCLOCKER),
                          new ItemStack(Femtocraft.itemPandoraCube),
                          true,
                          null)
    ret += new Technology(QUANTUM_INTERACTIVITY,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](DIMENSIONAL_TRANSFORMATION,
                                        NANO_CIRCUITS,
                                        DIMENSIONAL_BRAIDING,
                                        TEMPORAL_PIPELINING),
                          new ItemStack(Femtocraft.itemCharosGate),
                          true,
                          getInput(new ItemStack(Femtocraft.itemCharosGate)),
                          new ItemStack(Femtocraft.itemCharosGate))
    ret += new Technology(POTENTIAL_HARVESTING,
                          "Headhunters Unite!",
                          EnumTechLevel.MICRO,
                          Array[String](POTENTIALITY_STORAGE, POTENTIALITY_HARNESSING),
                          new ItemStack(Femtocraft.blockMicroChargingCapacitor),
                          false,
                          getInput(new ItemStack(Femtocraft.blockMicroChargingCapacitor)),
                          new ItemStack(Femtocraft.blockMicroChargingCapacitor))
    ret += new Technology(THORIUM_FISSIBILITY,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIAL_HARVESTING, ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemIngotThFaSalt),
                          false,
                          getInput(new ItemStack(Femtocraft.itemIngotThFaSalt)),
                          new ItemStack(Femtocraft.itemIngotThFaSalt))
    ret += new Technology(HARNESSED_NUCLEAR_DECAY,
                          "",
                          EnumTechLevel.NANO,
                          Array[String](THORIUM_FISSIBILITY,
                                        RESOURCE_OPTIMIZATION,
                                        GEOTHERMAL_HARNESSING,
                                        PATTERN_RECOGNITION),
                          new ItemStack(Femtocraft.blockFissionReactorCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemFissionReactorPlating)),
                          new ItemStack(Femtocraft.itemFissionReactorPlating))
    ret += new Technology(APPLIED_PARTICLE_PHYSICS,
                          "Like theoretical particle physics.",
                          EnumTechLevel.FEMTO,
                          Array[String](HARNESSED_NUCLEAR_DECAY, ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemCubit),
                          true,
                          null,
                          null)
    ret += new Technology(QUANTUM_COMPUTING,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](QUANTUM_INTERACTIVITY,
                                        ADVANCED_PROGRAMMING,
                                        APPLIED_PARTICLE_PHYSICS,
                                        LOCALITY_ENTANGLER,
                                        REALITY_OVERCLOCKER),
                          new ItemStack(Femtocraft.itemErinyesCircuit),
                          false,
                          getInput(new ItemStack(Femtocraft.itemErinyesCircuit)),
                          new ItemStack(Femtocraft.itemErinyesCircuit))
    ret += new Technology(QUANTUM_ROBOTICS,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](QUANTUM_COMPUTING, SPACETIME_EXPLOITATION),
                          new ItemStack(Femtocraft.itemHerculesDrive),
                          false,
                          getInput(new ItemStack(Femtocraft.itemHerculesDrive)),
                          new ItemStack(Femtocraft.itemHerculesDrive))
    ret += new Technology(ELEMENT_MANUFACTURING,
                          "Create circles, spheres, and ovals!",
                          EnumTechLevel.FEMTO,
                          Array[String](QUANTUM_ROBOTICS),
                          new ItemStack(Femtocraft.itemFemtoPlating),
                          false,
                          getInput(new ItemStack(Femtocraft.itemFemtoPlating)),
                          new ItemStack(Femtocraft.itemFemtoPlating))
    ret += new Technology(DIMENSIONAL_SUPERPOSITIONS,
                          "It's a layer effect.",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](SPACETIME_EXPLOITATION, ELEMENT_MANUFACTURING),
                          new ItemStack(Femtocraft.blockFemtoEntangler),
                          false,
                          getInput(new ItemStack(Femtocraft.blockFemtoEntangler)),
                          new ItemStack(Femtocraft.blockFemtoEntangler))
    ret += new Technology(MULTI_DIMENSIONAL_INDUSTRY,
                          "Share the load.",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](DIMENSIONAL_SUPERPOSITIONS),
                          new ItemStack(Femtocraft.itemInfiniteVolumePolychora),
                          false,
                          null)
    ret += new Technology(TEMPORAL_THREADING,
                          "Having multiple timelines do work at once.",
                          EnumTechLevel.TEMPORAL,
                          Array[String](SPACETIME_EXPLOITATION, ELEMENT_MANUFACTURING),
                          new ItemStack(Femtocraft.blockFemtoChronoshifter),
                          false,
                          getInput(new ItemStack(Femtocraft.blockFemtoChronoshifter)),
                          new ItemStack(Femtocraft.blockFemtoChronoshifter))
    ret += new Technology(CAUSALITY_SINGULARITY,
                          "If you researched this, you never would have researched " + "this.",
                          EnumTechLevel.TEMPORAL,
                          Array[String](TEMPORAL_THREADING),
                          new ItemStack(Femtocraft.itemInfinitelyRecursiveALU),
                          false,
                          null)
    ret += new Technology(DEMONIC_PARTICULATES,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](DIMENSIONAL_TRANSFORMATION, ELEMENT_MANUFACTURING),
                          new ItemStack(Femtocraft.blockFemtoCable),
                          false,
                          getInput(new ItemStack(Femtocraft.itemStyxValve)),
                          new ItemStack(Femtocraft.itemStyxValve))
    ret += new Technology(PARTICLE_EXCITATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](ELEMENT_MANUFACTURING),
                          new ItemStack(Femtocraft.blockFemtoImpulserUnlit),
                          false,
                          getInput(new ItemStack(Femtocraft.blockFemtoImpulserUnlit)),
                          new ItemStack(Femtocraft.blockFemtoImpulserUnlit))
    ret += new Technology(PARTICLE_MANIPULATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](ELEMENT_MANUFACTURING),
                          new ItemStack(Femtocraft.blockFemtoRepurposer),
                          false,
                          getInput(new ItemStack(Femtocraft.blockFemtoRepurposer)),
                          new ItemStack(Femtocraft.blockFemtoRepurposer))
    ret += new Technology(SPIN_RETENTION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](PARTICLE_MANIPULATION),
                          new ItemStack(Femtocraft.itemQuantumSchematic),
                          false,
                          getInput(new ItemStack(Femtocraft.itemQuantumSchematic)),
                          new ItemStack(Femtocraft.itemQuantumSchematic))
    ret += new Technology(SPONTANEOUS_GENERATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](QUANTUM_ROBOTICS, DEMONIC_PARTICULATES),
                          new ItemStack(Femtocraft.blockNullEqualizer),
                          false,
                          getInput(new ItemStack(Femtocraft.blockPhlegethonTunnelCore)),
                          new ItemStack(Femtocraft.blockPhlegethonTunnelCore))
    ret += new Technology(CORRUPTION_STABILIZATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](SPONTANEOUS_GENERATION),
                          new ItemStack(Femtocraft.blockFemtoCubeFrame),
                          false,
                          getInput(new ItemStack(Femtocraft.blockFemtoCubeFrame)),
                          new ItemStack(Femtocraft.blockFemtoCubeFrame))
    ret += new Technology(STELLAR_MIMICRY,
                          "Make your own pet star!",
                          EnumTechLevel.FEMTO,
                          Array[String](DEMONIC_PARTICULATES,
                                        ELEMENT_MANUFACTURING,
                                        CAUSALITY_SINGULARITY,
                                        MULTI_DIMENSIONAL_INDUSTRY),
                          new ItemStack(Femtocraft.blockStellaratorCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemStellaratorPlating)),
                          new ItemStack(Femtocraft.itemStellaratorPlating))
    ret += new Technology(ENERGY_CONVERSION,
                          "Plasma to Energy",
                          EnumTechLevel.FEMTO,
                          Array[String](STELLAR_MIMICRY),
                          new ItemStack(Femtocraft.blockPlasmaTurbine),
                          false,
                          getInput(new ItemStack(Femtocraft.blockPlasmaTurbine)),
                          new ItemStack(Femtocraft.blockPlasmaTurbine))
    ret += new Technology(MATTER_CONVERSION,
                          "Plasma to Mass",
                          EnumTechLevel.FEMTO,
                          Array[String](STELLAR_MIMICRY),
                          new ItemStack(Femtocraft.blockPlasmaCondenser),
                          false,
                          getInput(new ItemStack(Femtocraft.blockPlasmaCondenser)),
                          new ItemStack(Femtocraft.blockPlasmaCondenser))
    ret += new Technology(NETHER_STAR_FABRICATION,
                          "Creating the unimaginable.",
                          EnumTechLevel.FEMTO,
                          Array[String](STELLAR_MIMICRY, CORRUPTION_STABILIZATION, PARTICLE_MANIPULATION),
                          new ItemStack(Items.nether_star),
                          true,
                          getInput(new ItemStack(Items.nether_star)),
                          new ItemStack(Items.nether_star))
    ret += new Technology(MOLECULAR_MANIPULATION,
                          "",
                          EnumTechLevel.MICRO,
                          Array[String](MECHANICAL_PRECISION, BASIC_CHEMISTRY),
                          new ItemStack(Femtocraft.blockMicroDeconstructor),
                          false,
                          getInput(new ItemStack(Femtocraft.itemKineticPulverizer)),
                          new ItemStack(Femtocraft.itemKineticPulverizer))
    ret += new Technology(MACROSCOPIC_STRUCTURES,
                          "The patterns everything take.",
                          EnumTechLevel.MACRO,
                          null,
                          new ItemStack(Items.iron_pickaxe),
                          true,
                          null,
                          null)
    ret
  }

  private def getInput(itemStack: ItemStack): Array[ItemStack] = {
    val recipe: AssemblerRecipe = Femtocraft.recipeManager.assemblyRecipes.getRecipe(itemStack)
    if (recipe == null) null else recipe.input
  }
}

