package com.itszuvalex.femtocraft.research

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.RecipeType
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs
import com.itszuvalex.femtocraft.implicits.StringImplicits._
import com.itszuvalex.femtocraft.industry.tiles.TileEntityNanoInnervator
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
                          "Mathematically precise.",
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
                          new ItemStack(Femtocraft.itemVacuumCore),
                          "Enderpearls exert a strange effect, seemingly bending space around them.  You wonder what the effect of Lodestone would be when augmented with the reality-warping properties of this astouding item.",
                          "The world is stranger than you ever thought it would be.  Simple contraptions are capable of altering the foundations of reality.  And you have the knowledge to capitalize on it." +
                          Femtocraft.itemVacuumCore.toRecipeWithInfoString(RecipeType.CRAFTING, "The addition of the enderpearl seems to have both amplified and generalized the magnetic strength of Lodestone."),
                          false, false)
    ret += new Technology(VACUUM_TUBES,
                          "These tubes blow!",
                          EnumTechLevel.MICRO,
                          Array[String](POWER_OF_NOTHING),
                          new ItemStack(Femtocraft.blockVacuumTube),
                          false,
                          getInput(new ItemStack(Femtocraft.blockVacuumTube)),
                          new ItemStack(Femtocraft.blockVacuumTube),
                          "With some clever leapfrogging and metal channels, you think that you can use your newfound Vacuum Cores to enable item transfer.",
                          "Simple but fast item transfer." +
                          Femtocraft.blockVacuumTube.toRecipeWithInfoString(RecipeType.ASSEMBLER, "With four slots per block, this 1-way item transfer works at blazing speeds.") +
                          "Vacuum tubes will glow red when they contain too many items.  Additionally, they will suck items in from the world, or blow them back out.",
                          false, false)
    //    ret += new Technology(VACUUM_TUBE_HUB,
    //                          "A place for nothing to congregate.",
    //                          EnumTechLevel.MICRO,
    //                          Array[String](VACUUM_TUBES),
    //                          null,
    //                          false,
    //                          null)
    ret += new Technology(SUCTION_PIPES,
                          "These pipes suck!",
                          EnumTechLevel.MICRO,
                          Array[String](POWER_OF_NOTHING),
                          new ItemStack(Femtocraft.blockSuctionPipe),
                          false,
                          getInput(new ItemStack(Femtocraft.blockSuctionPipe)),
                          new ItemStack(Femtocraft.blockSuctionPipe),
                          "With a watertight tube and a Vacuum Tube, it's dead simple to induce liquid transfer.",
                          "Simple but fast liquid distribution." +
                          Femtocraft.blockSuctionPipe.toRecipeWithInfoString(RecipeType.CRAFTING, "These 2-mode pipes will either distribute or pull liquid from adjacent tanks.") +
                          "Suction pipes will attempt to distribute liquid evenly throughout all connected pipes.  Shift-right-click with an interface device turns the pipe to blackout mode, stopping all packet updates and blocking out liquid rendering.",
                          false, false)
    ret += new Technology(NANO_CIRCUITS,
                          "Machine test, machine approved.",
                          EnumTechLevel.NANO,
                          Array[String](MECHANICAL_PRECISION, BASIC_CIRCUITS),
                          new ItemStack(Femtocraft.itemNanochip),
                          true,
                          getInput(new ItemStack(Femtocraft.itemNanochip)),
                          new ItemStack(Femtocraft.itemNanochip),
                          " Your first machine-fabricated circuitry.  Let's see just how small the machines can go.",
                          Femtocraft.itemNanochip.toRecipeWithInfoString(RecipeType.ASSEMBLER, "A direct improvement on the microchip.  Key farenite trails allow for blazing fast logic propagation.") +
                          "With the addition of redstone logic, the general nanochip can become specialized for use in key circuit designs." +
                          Femtocraft.itemNanoCalculator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The calculator is great for complex mathematical operations that are too difficult for applications without a computer.") +
                          Femtocraft.itemNanoRegulator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The regulator has applications in multi-variable resource management problems.") +
                          Femtocraft.itemNanoSimulator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The simulator mimics material processes for use in predictive analysis."),
                          false, false)
    ret += new Technology(ADVANCED_PROGRAMMING,
                          "Technology++",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS),
                          new ItemStack(Femtocraft.itemBasicAICore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemBasicAICore)),
                          new ItemStack(Femtocraft.itemBasicAICore),
                          " Much like your previous machines had a micro logic core, your new machines are going to need a controller to manage all their processes.",
                          " You doubt that there exists one core that is capable of handling all of the different mechanisms required, but you can at least devise a starting point." +
                          Femtocraft.itemBasicAICore.toRecipeWithInfoString(RecipeType.ASSEMBLER, "This core provides a starting framework for plugging in additional logic."),
                          false, false)
    ret += new Technology(WORKLOAD_SCHEDULING,
                          "Never be late again!",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemSchedulerCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemSchedulerCore)),
                          new ItemStack(Femtocraft.itemSchedulerCore),
                          " One of the most applicable problems is the correct scheduling of jobs while having limited resources.  Making a circuit capable of solving problems of this sort means useful applications everywhere.",
                          " Whether scheduling I/O operations, threading priority, or merely planning your next day in the workshop, the Scheduler Core has you covered." +
                          Femtocraft.itemSchedulerCore.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Advanced goal seeking with backtracking and pattern matching capabilities, with a nice UI to boot."),
                          false, false)
    ret += new Technology(PATTERN_RECOGNITION,
                          "Do you recognize the pattern?",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemLearningCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemLearningCore)),
                          new ItemStack(Femtocraft.itemLearningCore),
                          "A useful application for the basic AI core would be some sort of algorithm to recognize and report on patterns.",
                          "Useful for catching and fixing errors in random or fluctuating environments.  The Learning Core maintains its knowledge and incorporates new information into its world view." +
                          Femtocraft.itemLearningCore.toRecipeWithInfoString(RecipeType.ASSEMBLER, "A collection of memory and processing circuits, the core learns from new information and applies its knowledge towards a goal."),
                          false, false)
    //    ret += new Technology(DISCRIMINATING_VACUUM_TUBE,
    //                          "",
    //                          EnumTechLevel.NANO,
    //                          Array[String](VACUUM_TUBE_HUB, PATTERN_RECOGNITION),
    //                          new ItemStack(Femtocraft.blockVacuumTube),
    //                          false,
    //                          null)
    ret += new Technology(RESOURCE_OPTIMIZATION,
                          "Waste not want not.",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_PROGRAMMING),
                          new ItemStack(Femtocraft.itemManagerCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemManagerCore)),
                          new ItemStack(Femtocraft.itemManagerCore),
                          " As your rapidly dwindling stockpiles show, you may not have been prudent in your resource consumption.  Perhaps your shiny new processor cores could help you with this problem in the future?",
                          " Magically finding corners to cut all over the place, the Manager Core appears to bend the laws of reality in its stinginess." +
                          Femtocraft.itemManagerCore.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Curiously, when applied to designing new circuits, the Manager Core refuses work on improving any design but its own."),
                          false, false)
    ret += new Technology(BASIC_CHEMISTRY,
                          "Composition of matter.",
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
                          "Power overwhelming.",
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
                          "Power, cubed.",
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
                          new ItemStack(Femtocraft.blockMagneticInductionGenerator),
                          " If Lodestone exerts a force upon other metals, you figure that you must be able to use this force to generate power.",
                          " The curious arrangement of metal in Heating Coils leads to the interesting property that, instead of consuming power, moving a Lodestone-interactive material near it produces power." +
                          Femtocraft.blockMagneticInductionGenerator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Generates power based on the movement of neighboring magnetic blocks."),
                          false, false)
    ret += new Technology(ADVANCED_CHEMISTRY,
                          "Curiouser and curiouser!",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIALITY_GENERATION, BASIC_CHEMISTRY, MOLECULAR_MANIPULATION),
                          new ItemStack(Femtocraft.itemCrystallite),
                          true,
                          Array[ItemStack](new ItemStack(Femtocraft.itemArticulatingArm), new ItemStack(Femtocraft.itemAssemblyArray), new ItemStack(Femtocraft.itemArticulatingArm), new ItemStack(Femtocraft.itemDissassemblyArray), new ItemStack(Femtocraft.blockMicroDeconstructor), new ItemStack(Femtocraft.itemDissassemblyArray), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.itemMicroLogicCore), new ItemStack(Femtocraft.itemMicrochip)),
                          null,
                          " Noticing common behavior even among as small of things as your discovered \"Molecules\", you can't help but wonder that maybe there exists something smaller than them.  After all, you would never have thought Molecules existed before you began your experiments.  Your new machinery may possibly be able uncover the answer for you.",
                          " These \"Atoms\", though fewer in number, appear to completely describe the behaviors of molecules.  Though not all combinations of atoms are used, it appears extremely difficult to create alternate combinations." + ComponentRegistry.getComponentsAssemblerRecipeDisplayString(EnumTechLevel.MICRO),
                          false, false)
    ret += new Technology(ARTIFICIAL_MATERIALS,
                          "Artificial Improvements.",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemNanoPlating),
                          false,
                          getInput(new ItemStack(Femtocraft.itemNanoPlating)),
                          new ItemStack(Femtocraft.itemNanoPlating),
                          "Through micro plating has proliferated throughout all of your machines, it simply is't technologically advanced enough to support some of these newer electronics.  Time to create the casing of the future!",
                          "With power flow regulators and plugins for the new nano circuitry, Nano Plating will prove to be just as useful as its predecessor." +
                          Femtocraft.itemNanoPlating.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Stylish and functional.  Resistant to corrosion, spider venom, and rogue AIs."),
                          false, false)
    ret += new Technology(FARENITE_STABILIZATION,
                          "...somewhat.",
                          EnumTechLevel.NANO,
                          Array[String](NANO_CIRCUITS, ADVANCED_CHEMISTRY, ARTIFICIAL_MATERIALS, THORIUM_FISSIBILITY),
                          new ItemStack(Femtocraft.blockNanoCable),
                          false,
                          getInput(new ItemStack(Femtocraft.itemFluidicConductor)),
                          new ItemStack(Femtocraft.itemFluidicConductor),
                          "With your study of the composition of Farenite and its properties, you think that it's possible you no longer have to cut pure Farenite dust with Lapis in order to make it usable.",
                          "Pure Farenite driven Proton Oscillation Induction promotes greater potential transfer and storage.  Farenite = more power, faster." +
                          Femtocraft.itemFluidicConductor.toRecipeWithInfoString(RecipeType.ASSEMBLER, "With mathematically placed grooves, excess Farenite energy is absorbed by the Quartz and released as energy pulses that counteract the volatility wave.") +
                          Femtocraft.itemNanoCoil.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Acting upon the same principle of Micro Coils, except without the calming factor of Lapis.") +
                          Femtocraft.blockNanoCable.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Lean, green, powering machines."),
                          false, false)
    ret += new Technology(GEOTHERMAL_HARNESSING,
                          "Harness the geological thermal energy.",
                          EnumTechLevel.NANO,
                          Array[String](FARENITE_STABILIZATION, ADVANCED_CHEMISTRY, POTENTIALITY_HARNESSING),
                          new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                          false,
                          getInput(new ItemStack(Femtocraft.blockCryoEndothermalChargingBase)),
                          new ItemStack(Femtocraft.blockCryoEndothermalChargingBase),
                          "The earth under your feet has plenty of energy in the form of heat.  It's never cold, you notice.  It should be cold.  Make it so.",
                          "The cause of Farenite's volatility has been found! It appears a reverse tunneling effect can occur under proper stimulation.  Heat from the surrounding environment tunnels into Farenite and is then released in the form of Farenite's infamous spontaneous energy waves." +
                          Femtocraft.blockCryoEndothermalChargingBase.toRecipeWithInfoString(RecipeType.ASSEMBLER, "A series of tubes capable of absorbing and changeling Farenite's natural energy wavelengths. Capable of accepting a chain of up to 15 coils from the bottom.") +
                          Femtocraft.blockCryoEndothermalChargingCoil.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Designed to provoke spontaneous Farenite reactions.  Cold objects nearby generate power, as they naturally give Thermal energy a place to go, and thus tunnel into Farenite.  Occasionally, the tunneling effect can occur on objects further away, generating spontaneous energy bursts."),
                          false, false)
    ret += new Technology(POTENTIALITY_TRANSFORMATION,
                          "Photonic Oscillation Converter.",
                          EnumTechLevel.NANO,
                          Array[String](FARENITE_STABILIZATION),
                          new ItemStack(Femtocraft.blockOrbitalEqualizer),
                          false,
                          getInput(new ItemStack(Femtocraft.blockOrbitalEqualizer)),
                          new ItemStack(Femtocraft.blockOrbitalEqualizer),
                          "The photons, as well as the slurry of photon-generating particles, produced by the different tiers of machines are completely incompatable.  Perhaps there is a way to convert between the two?",
                          "By equalizing the electron orbitals of similar particles in the accompanying particle slurry, the photons from one energy level can be absorbed and turned into photons of the other energy level." +
                          Femtocraft.blockOrbitalEqualizer.toRecipeWithInfoString(RecipeType.ASSEMBLER, "This machine alternates which power level it converts to as fast as it can."),
                          false, false)
    ret += new Technology(INDUSTRIAL_STORAGE,
                          "No longer portable.",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIALITY_TRANSFORMATION, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoCubeFrame),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoCubeFrame)),
                          new ItemStack(Femtocraft.blockNanoCubeFrame))
    ret += new Technology(KINETIC_DISSOCIATION,
                          "Better materials, better furnace.",
                          EnumTechLevel.NANO,
                          Array[String](WORKLOAD_SCHEDULING, ARTIFICIAL_MATERIALS, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoInnervatorUnlit),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoInnervatorUnlit)),
                          new ItemStack(Femtocraft.blockNanoInnervatorUnlit),
                          "Though handy and efficient, the Micro Furnace is simply too slow to keep up with current processing demands.  Time to design the next iteration!",
                          "Capable of smelting " + TileEntityNanoInnervator.MAX_SMELT_PRE + " loads at a time, and at a much faster rate than the Micro Furnace, the Nano Innervator is definitely the furnace to beat." +
                          Femtocraft.blockNanoInnervatorUnlit.toRecipeWithInfoString(RecipeType.ASSEMBLER, "A feat of modern engineering.  Never has smelting been faster."),
                          false, false)
    ret += new Technology(ATOMIC_MANIPULATION,
                          "The natural progression.",
                          EnumTechLevel.NANO,
                          Array[String](ARTIFICIAL_MATERIALS, WORKLOAD_SCHEDULING, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.blockNanoDismantler),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoDismantler)),
                          new ItemStack(Femtocraft.blockNanoDismantler),
                          "Manipulating \"Molecules\" is not enough.  If you really want to gain control over all elements of the universe, you must be able to manipulate these \"Atoms\" as well.",
                          "Your logic circuits and AI cores make short work of this problem.  Shrinking down the tools and using faster circuits, it becomes all too easy." +
                          Femtocraft.blockNanoDismantler.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Capable of everything its predecessor could do and more.  It can work on multiple items at a time, as well as working faster.") +
                          Femtocraft.blockNanoFabricator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Capable of working from the nano scale up to the macro scale.  Its many cores and tools allow it to assemble items in parallel."),
                          false, false)
    ret += new Technology(DIGITIZED_WORKLOADS,
                          "Made for computers, by computers.",
                          EnumTechLevel.NANO,
                          Array[String](ATOMIC_MANIPULATION, RESOURCE_OPTIMIZATION),
                          new ItemStack(Femtocraft.itemDigitalSchematic),
                          false,
                          getInput(new ItemStack(Femtocraft.itemDigitalSchematic)),
                          new ItemStack(Femtocraft.itemDigitalSchematic),
                          "Your paper schematics are woefully stone age.  Circuits and computers are more elegant, more efficient, and more durable.",
                          "Though impressive, these schematics are surprisingly affected by the intense industrial conditions of your machines." +
                          Femtocraft.itemDigitalSchematic.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The read-write circuits, though extremely precise and stable, still burn out after repeated use."),
                          false, false)
    ret += new Technology(SPACETIME_MANIPULATION,
                          "The question is: When did you ACTUALLY finish researching this?",
                          EnumTechLevel.NANO,
                          Array[String](ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemDimensionalMonopole),
                          true,
                          getInput(new ItemStack(Femtocraft.itemDimensionalMonopole)),
                          new ItemStack(Femtocraft.itemDimensionalMonopole),
                          "It's undeniable that creatures like Endermen have control over space.  Nether quartz has remarkable properties with respect to time.  Put energy through and they resonate at precise intervals.  Combine them with some other processes and they seem to alter the flow of time itself.  How can you magnify these effects?",
                          "This is by far the greatest experimental success ever accomplished by Steve.  You have discovered how to manipulate space and time itself." +
                          Femtocraft.itemDimensionalMonopole.toRecipeWithInfoString(RecipeType.ASSEMBLER, "When placing a series of enderpearls at key localities around a compass, you find that the compass no longer points towards magnetic North.  Instead, it appears to point toward key points in space.") +
                          Femtocraft.itemCrossDimensionalCommunicator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Placing a dimensional monopole in a simple assembly allows for transportation of minute amounts of material, such as vibrating air waves.") +
                          Femtocraft.itemTemporalResonator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Some quartz, hooked up to special circuits, and attached to a time keeping clock.  This appears to warp time in the area around the item, with special effects in the epicenter.") +
                          Femtocraft.itemSelfFulfillingOracle.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The self-fulfilling oracle uses predictive analyses while in the event horizon of the temporal resonator.  These predictions propagate out, and have so far all come true seconds later."),
                          false, false)
    ret += new Technology(DIMENSIONAL_BRAIDING,
                          "Interweave reality.",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION),
                          new ItemStack(Femtocraft.blockNanoEnmesher),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoEnmesher)),
                          new ItemStack(Femtocraft.blockNanoEnmesher),
                          "Now that you have real working cross-dimensional manipulators, can you possibly compute their effects and automate use?",
                          "You invent a process called \"Braiding\".  This process involves leaving together the effects of multiple \"configurators\", or specific key devices " +
                          "placed around a center catalyst.  Whether for their effects, or for extra needed computation, these configurators are required to complete the specified process but are not consumed." +
                          Femtocraft.blockNanoEnmesher.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Braiding together the effects results in items that can affect multiple realities simultaneously.") +
                          Femtocraft.itemPanLocationalComputer.toRecipeWithInfoString(RecipeType.DIMENSIONAL, "Your first Braid, this computer can have multiple processes running in separate realities, and supports register swap operations with alternate dimensions."),
                          false, false)
    ret += new Technology(LOCALITY_ENTANGLER,
                          "Integrate your machine's workspaces with other yous' other machines' workspaces.",
                          EnumTechLevel.DIMENSIONAL,
                          Array[String](DIMENSIONAL_BRAIDING),
                          new ItemStack(Femtocraft.itemPanLocationalComputer),
                          false,
                          Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), new ItemStack(Femtocraft.itemNanochip),
                                           new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemDimensionalMonopole),
                                           new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemNanoRegulator)),
                          new ItemStack(Femtocraft.itemPanLocationalComputer),
                          "How inefficient your machines are!  They merely use all the space available to them in THIS dimension.  It's about time you did something about that.",
                          "Your machines are now successfully outfitted to utilize the space of all unused machines of the same type in all other dimensions.  This means that, on average, your machines can handle twice as many materials as normal.  You figured it would be more, but it appears that a new branch of mathematics involving statistics and pan-dimensional probabilities shows a weird application of a sort of pigenhole principle leads to this conclusion." +
                          Femtocraft.itemPandoraCube.toRecipeWithInfoString(RecipeType.DIMENSIONAL, "Additionally, your next Braid begins as an empty box and is capable of cross-dimensionally storings its items.  Or spontaneously generating new ones.  Or losing things put into it already."),
                          false, false)
    ret += new Technology(DIMENSIONAL_TRANSFORMATION,
                          "",
                          EnumTechLevel.FEMTO,
                          Array[String](DIMENSIONAL_BRAIDING),
                          new ItemStack(Femtocraft.itemDustMalenite),
                          true,
                          null)
    ret += new Technology(TEMPORAL_PIPELINING,
                          "Start working in the next second, before it begins.",
                          EnumTechLevel.TEMPORAL,
                          Array[String](ARTIFICIAL_MATERIALS, SPACETIME_MANIPULATION, ATOMIC_MANIPULATION),
                          new ItemStack(Femtocraft.blockNanoHorologe),
                          false,
                          getInput(new ItemStack(Femtocraft.blockNanoHorologe)),
                          new ItemStack(Femtocraft.blockNanoHorologe),
                          "Your Temporal Resonators all synchronized as you began working on your next prototype.  No matter what you you do, they refuse to de-synchronize.  You better hurry up and finish before something terrible happens.",
                          "This arcane process appears to chronoshift the effects of these \"configurator\" items to spontaneously apply to the catalyst item at arbitrary times.  However, this continues even when the catalyst is taken far away from them.  Perhaps, in time, you will find a use for this." +
                          Femtocraft.blockNanoHorologe.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The temporal field of the internal Resonator appears to sweep across the configurator items, impregnating the catalyst with all of their potential effects.") +
                          Femtocraft.itemInfallibleEstimator.toRecipeWithInfoString(RecipeType.TEMPORAL, "You found this sitting on your desk one day.  You vaguely remember taking it out of the machine later that day.  Or was it yesterday?  What machine?"),
                          false, false)
    ret += new Technology(REALITY_OVERCLOCKER,
                          "Time flies when you're you!",
                          EnumTechLevel.TEMPORAL,
                          Array[String](TEMPORAL_PIPELINING),
                          new ItemStack(Femtocraft.itemInfallibleEstimator),
                          false,
                          Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemSelfFulfillingOracle), new ItemStack(Femtocraft.itemNanochip),
                                           new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemTemporalResonator),
                                           new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.itemNanoSimulator)),
                          null,
                          "More inefficiency!  You can time shift items, why not machines?",
                          "Your machines now temporarily back-shift in time every time they start a job.  Thus, they always finish twice as fast as you expect.",
                          false, false)
    ret += new Technology(SPACETIME_EXPLOITATION,
                          "Imagination is the only weapon in the war against reality.",
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
                          "Headhunters unite!",
                          EnumTechLevel.MICRO,
                          Array[String](POTENTIALITY_STORAGE, POTENTIALITY_HARNESSING),
                          new ItemStack(Femtocraft.blockMicroChargingCapacitor),
                          false,
                          getInput(new ItemStack(Femtocraft.blockMicroChargingCapacitor)),
                          new ItemStack(Femtocraft.blockMicroChargingCapacitor),
                          "While walking past your charging coils on a particularly humid day, you couldn't help but notice that the coils couldn't keep up with all of the energy in the air.  Perhaps you could make something that would allow the coils to perform better in stormy cnoditions.",
                          Femtocraft.blockMicroChargingCapacitor.toRecipeWithInfoString(RecipeType.ASSEMBLER, "This block temporarily stores the excess power generated by the coils, releasing it once the power surges abate.") +
                          "By placing a capacitive setup at the top of the charging coil, you notice significant improvement in power generation in all weather.  However, during rain storms this block becomes significantly more cost effective.  During thunderstorms it shows that it is outright one of your best inventions.",
                          false, false)
    ret += new Technology(THORIUM_FISSIBILITY,
                          "Split the Atom.",
                          EnumTechLevel.NANO,
                          Array[String](POTENTIAL_HARVESTING, ADVANCED_CHEMISTRY),
                          new ItemStack(Femtocraft.itemIngotThFaSalt),
                          false,
                          getInput(new ItemStack(Femtocraft.itemIngotThFaSalt)),
                          new ItemStack(Femtocraft.itemIngotThFaSalt),
                          "Thorium, that mysteriously useless metal that you find all over the ground, apparently reacts with Farenite to form a semi-stable isotope that radiates energy.  Could you harness this in some way?",
                          "Through further inspection of the structure of this Thorium Farenite salt, you figure out the method to safely dissassemble Farenite and discover its components." + Femtocraft.itemDustFarenite.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Though highly unstable, this material is nothing different than any other, other than requiring better machines to process.") +
                          "Additionally, this Thorium Farenite salt compound appears to let off radiant energy in a manner similar to Thorium itself.  Perhaps you can harness this stable compound in a machine to gather this energy." + Femtocraft.itemIngotThFaSalt.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Farenite appears to naturally bond with any Thorium it comes in contact with."),
                          false, false)
    ret += new Technology(HARNESSED_NUCLEAR_DECAY,
                          "Do you have what it takes?",
                          EnumTechLevel.NANO,
                          Array[String](THORIUM_FISSIBILITY,
                                        RESOURCE_OPTIMIZATION,
                                        GEOTHERMAL_HARNESSING,
                                        PATTERN_RECOGNITION),
                          new ItemStack(Femtocraft.blockFissionReactorCore),
                          false,
                          getInput(new ItemStack(Femtocraft.itemFissionReactorPlating)),
                          new ItemStack(Femtocraft.itemFissionReactorPlating),
                          "You think you can design a system that can harness the natural decay of Thorium.  This will not be easy, but it should generate as much power as you could ever need.",
                          "The molten Thorium-Farenite Salt allows for easy transfer of the energy-generating material to prevent heat over-saturation.  Seeding this molten slurry with additional pure Thorium allows for scaling the energy output." +
                          Femtocraft.itemFissionReactorPlating.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Required to lock in stray radiation and equipped with additional sensors.") +
                          "\nThe Fission Reactor is a large, 3x3x3 structure.  The center is the Fission Reactor Core, with the remaining blocks made out of Fission Reactor Housing.  " +
                          "The reactor accepts 3 main types of items -> Salt, Thorium, and thermal reagants.\n\n" +
                          "    Salt    - ThFa Salt Ingots\n" +
                          "    Thorium - Pure Thorium Ingots\n" +
                          "    Thermal - Ice/Lava/Fire Charges.\n" +
                          "\nThe reactor uses heat to melt ThFa into molten salt, and to melt Thorium to meet the user-designated target Thorium percentage.  Molten salt and cooled salt generate heat passively, multiplied by the current Thorium percentage.  Molten salt generates more heat than cooled salt.\n\n" +
                          Femtocraft.blockFissionReactorCore.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Stores molten and cooled salt in separate containers.  Contains a shielded mixing core with emergency dump capabilities.") +
                          Femtocraft.blockFissionReactorHousing.toRecipeWithInfoString(RecipeType.ASSEMBLER, "A solid housing to lock in heat and radiation.") +
                          "\nThe Magnetohydrodynamic Generator is also a large 3x3x3 structure, in order to house the complex mechanisms required.  As Molten salt passes through the generator, it is converted into power, cooling the salt.  Additionally, the natural breakdown of Thorium in the salt leads to a small amount of contaminants." +
                          "\n\n    Contaminated Salt can be pumped directly back into the Fission Reactor to be reheated, but this drains the Thorium concentration, as the contaminants have to be drowned out." +
                          Femtocraft.blockMagnetohydrodynamicGenerator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "The flow of molten ThFa Salt through the generator generates power.") +
                          Femtocraft.blockSteamGenerator.toRecipeWithInfoString(RecipeType.ASSEMBLER, "An upgrade to the MHD Generator.  It can be placed in the center of any of the 6 sides to dramatically increase power output.") +
                          "\n\nA 2x2x2 structure, the Decontamination Chamber uses a moderate amount of power to remove the contaminants from Contaminated Salt.  A miniscule amount of loss is expected.  The clean salt can be pumped back into the reactor for reheating without requiring additional Thorium." +
                          Femtocraft.blockDecontaminationChamber.toRecipeWithInfoString(RecipeType.ASSEMBLER, "Chemical scrubbers charged by small amounts of power.")
                          ,
                          false, false)
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
                          "If you researched this, you never would have researched this.",
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
                          "Like a top.",
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
                          "Plasma to Energy.",
                          EnumTechLevel.FEMTO,
                          Array[String](STELLAR_MIMICRY),
                          new ItemStack(Femtocraft.blockPlasmaTurbine),
                          false,
                          getInput(new ItemStack(Femtocraft.blockPlasmaTurbine)),
                          new ItemStack(Femtocraft.blockPlasmaTurbine))
    ret += new Technology(MATTER_CONVERSION,
                          "Plasma to Mass.",
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
                          "Many machines mathematically manipulate molecules, many more merely make massive messes.",
                          EnumTechLevel.MICRO,
                          Array[String](MECHANICAL_PRECISION, BASIC_CHEMISTRY),
                          new ItemStack(Femtocraft.blockMicroDeconstructor),
                          false,
                          getInput(new ItemStack(Femtocraft.itemKineticPulverizer)),
                          new ItemStack(Femtocraft.itemKineticPulverizer),
                          " With dutiful application of all the principles you have so learned, theoretically it should be possible to manipulate the underlying structures of matter.",
                          " With firm resolve, you start the path down the rabbit hole." +
                          Femtocraft.itemKineticPulverizer.toRecipeWithInfoString(RecipeType.CRAFTING, "A reinforced piston with special circuitry to prevent over enthusiastic crushing.") +
                          Femtocraft.itemDissassemblyArray.toRecipeWithInfoString(RecipeType.CRAFTING, "A series of pulverizers together ensures maximum controlled destruction.") +
                          Femtocraft.blockMicroDeconstructor.toRecipeWithInfoString(RecipeType.CRAFTING, "This machine takes in any item and breaks it down into its micro-tier components.") +
                          "With deconstructing items out of the way, it's probably a good idea to figure out a way to reconstruct them.  Building is always harder than destroying, but somehow you've made it happen." +
                          Femtocraft.itemArticulatingArm.toRecipeWithInfoString(RecipeType.CRAFTING, "A long assembly of metal with multiple degrees of freedom, ending with a precision implement.") +
                          Femtocraft.itemAssemblyArray.toRecipeWithInfoString(RecipeType.CRAFTING, "Two arms working in tandem allow for the precise joining of components.") +
                          Femtocraft.blockMicroReconstructor.toRecipeWithInfoString(RecipeType.CRAFTING, "Given a schematic and proper materials, this can turn micro-tier components back into useful things."),
                          false, false)
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

