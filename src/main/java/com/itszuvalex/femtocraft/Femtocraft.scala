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
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 *  MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft

import java.io.File

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.blocks._
import com.itszuvalex.femtocraft.command.{CommandBase, CommandFemtocraft}
import com.itszuvalex.femtocraft.configuration.{FemtocraftAssemblerConfig, FemtocraftConfigs}
import com.itszuvalex.femtocraft.core.MagnetRegistry
import com.itszuvalex.femtocraft.core.fluids._
import com.itszuvalex.femtocraft.core.items.{ItemBase, ItemFemtoInterfaceDevice, ItemMicroInterfaceDevice, ItemNanoInterfaceDevice}
import com.itszuvalex.femtocraft.core.ore._
import com.itszuvalex.femtocraft.industry.blocks._
import com.itszuvalex.femtocraft.industry.items.{ItemDigitalSchematic, ItemPaperSchematic, ItemQuantumSchematic}
import com.itszuvalex.femtocraft.managers.ManagerRecipe
import com.itszuvalex.femtocraft.managers.assembler.ComponentRegistry
import com.itszuvalex.femtocraft.managers.assistant.ManagerAssistant
import com.itszuvalex.femtocraft.managers.research.ManagerResearch
import com.itszuvalex.femtocraft.network.FemtocraftPacketHandler
import com.itszuvalex.femtocraft.power.blocks._
import com.itszuvalex.femtocraft.power.fluids._
import com.itszuvalex.femtocraft.power.items._
import com.itszuvalex.femtocraft.power.plasma.BlockPlasma
import com.itszuvalex.femtocraft.proxy.{ProxyCommon, ProxyGuiCommon}
import com.itszuvalex.femtocraft.research.blocks.{BlockResearchComputer, BlockResearchConsole}
import com.itszuvalex.femtocraft.research.items.{ItemFemtoTechnology, ItemMicroTechnology, ItemNanoTechnology, ItemPortableResearchComputer}
import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager
import com.itszuvalex.femtocraft.transport.items.blocks.BlockVacuumTube
import com.itszuvalex.femtocraft.transport.liquids.blocks.BlockSuctionPipe
import com.itszuvalex.femtocraft.utils.{FemtocraftFileUtils, FemtocraftUtils}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fluids.{BlockFluidBase, Fluid, FluidRegistry}
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.{Level, LogManager, Logger}

/**
 * Created by Christopher Harris (Itszuvalex) on 10/6/14.
 */
@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION,
     modLanguage = "scala")
object Femtocraft {

  final val ID                   = "Femtocraft"
  final val VERSION              = "0.1.0"
  final val TECH_CONFIG_APPEND   = "Technology"
  final val RECIPE_CONFIG_APPEND = "AssemblerRecipes"

  //  /* Packet Channels */
  //  final val GUI_CHANNEL               = ID + ".gui"
  //  final val SOUND_CHANNEL             = ID + ".snd"
  //  final val PLAYER_PROP_CHANNEL       = ID + ".pprop"
  //  final val RESEARCH_CHANNEL          = ID + ".rman"
  //  final val RESEARCH_CONSOLE_CHANNEL  = ID + ".rcon"
  //  final val VACUUM_TUBE_CHANNEL       = ID + ".vtube"
  //  final val FISSION_REACTOR_CHANNEL   = ID + ".fiss"
  //  final val PHLEGETHON_TUNNEL_CHANNEL = ID + ".phleg"

  @SidedProxy(clientSide = "com.itszuvalex.femtocraft.proxy.ProxyClient",
              serverSide = "com.itszuvalex.femtocraft.proxy.ProxyCommon")
  var proxy                                 : ProxyCommon               = null
  @SidedProxy(clientSide = "com.itszuvalex.femtocraft.proxy.ProxyGuiClient",
              serverSide = "com.itszuvalex.femtocraft.proxy.ProxyGuiCommon")
  var guiProxy                              : ProxyGuiCommon            = null
  var femtocraftTab                         : CreativeTabs              = new FemtocraftCreativeTab("Femtocraft")
  var config                                : Configuration             = null
  var logger                                : Logger                    = LogManager.getLogger("Femtocraft")
  var technologyConfigFile                  : File                      = null
  var recipeConfigFile                      : File                      = null
  var recipeConfig                          : Configuration             = null
  var assemblerConfigs                      : FemtocraftAssemblerConfig = null
  var recipeManager                         : ManagerRecipe             = null
  var researchManager                       : ManagerResearch           = null
  var assistantManager                      : ManagerAssistant          = null
  var soundManager                          : FemtocraftSoundManager    = null
  var femtocraftServerCommand               : CommandBase               = null
  /*blocks*/
  var blockOreTitanium                      : Block                     = null
  var blockOrePlatinum                      : Block                     = null
  var blockOreThorium                       : Block                     = null
  var blockOreFarenite                      : Block                     = null
  var blockOreMalenite                      : Block                     = null
  var blockOreLodestone                     : Block                     = null
  var microStone                            : Block                     = null
  var nanoStone                             : Block                     = null
  var femtoStone                            : Block                     = null
  var unidentifiedAlloy                     : Block                     = null
  var blockResearchComputer                 : Block                     = null
  var blockResearchConsole                  : Block                     = null
  var generatorTest                         : Block                     = null
  var consumerTest                          : Block                     = null
  var blockMicroFurnaceUnlit                : Block                     = null
  var blockMicroFurnaceLit                  : Block                     = null
  var blockMicroDeconstructor               : Block                     = null
  var blockMicroReconstructor               : Block                     = null
  var blockEncoder                          : Block                     = null
  var blockNanoInnervatorUnlit              : Block                     = null
  var blockNanoInnervatorLit                : Block                     = null
  var blockNanoDismantler                   : Block                     = null
  var blockNanoFabricator                   : Block                     = null
  var blockNanoEnmesher                     : Block                     = null
  var blockNanoHorologe                     : Block                     = null
  var blockFemtoImpulserLit                 : Block                     = null
  var blockFemtoImpulserUnlit               : Block                     = null
  var blockFemtoRepurposer                  : Block                     = null
  var blockFemtoCoagulator                  : Block                     = null
  var blockFemtoEntangler                   : Block                     = null
  var blockFemtoChronoshifter               : Block                     = null
  var blockMicroCube                        : Block                     = null
  var blockNanoCubeFrame                    : Block                     = null
  var blockNanoCubePort                     : Block                     = null
  var blockFemtoCubePort                    : Block                     = null
  var blockFemtoCubeFrame                   : Block                     = null
  var blockFemtoCubeChassis                 : Block                     = null
  var blockVacuumTube                       : Block                     = null
  var blockSuctionPipe                      : Block                     = null
  var blockMicroChargingBase                : Block                     = null
  var blockMicroChargingCoil                : Block                     = null
  var blockMicroChargingCapacitor           : Block                     = null
  var blockMagneticInductionGenerator       : Block                     = null
  var blockOrbitalEqualizer                 : Block                     = null
  var blockNullEqualizer                    : Block                     = null
  var blockCryoEndothermalChargingBase      : Block                     = null
  var blockCryoEndothermalChargingCoil      : Block                     = null
  var blockFissionReactorCore               : Block                     = null
  var blockFissionReactorHousing            : Block                     = null
  var blockMagnetohydrodynamicGenerator     : Block                     = null
  var blockSteamGenerator                   : Block                     = null
  var blockDecontaminationChamber           : Block                     = null
  var blockPhlegethonTunnelCore             : Block                     = null
  var blockPhlegethonTunnelFrame            : Block                     = null
  var blockSisyphusStabilizer               : Block                     = null
  var blockStellaratorCore                  : Block                     = null
  var blockStellaratorFocus                 : Block                     = null
  var blockStellaratorOpticalMaser          : Block                     = null
  var blockStellaratorHousing               : Block                     = null
  var blockPlasmaConduit                    : Block                     = null
  var blockPlasmaVent                       : Block                     = null
  var blockPlasmaSolenoid                    : Block                     = null
  var blockPlasmaCondenser                  : Block                     = null
  var blockMicroCable                       : Block                     = null
  var blockNanoCable                        : Block                     = null
  var blockFemtoCable                       : Block                     = null
  var fluidMass                             : Fluid                     = null
  var blockFluidMass                        : BlockFluidBase            = null
  var fluidMoltenSalt                       : Fluid                     = null
  var blockFluidMoltenSalt                  : BlockFluidBase            = null
  var fluidCooledMoltenSalt                 : Fluid                     = null
  var blockFluidCooledMoltenSalt            : BlockFluidBase            = null
  var fluidCooledContaminatedMoltenSalt     : Fluid                     = null
  var blockFluidCooledContaminatedMoltenSalt: BlockFluidBase            = null
  var blockPlasma                           : Block                     = null
  /* items */
  var itemIngotTitanium                     : Item                      = null
  var itemIngotPlatinum                     : Item                      = null
  var itemIngotThorium                      : Item                      = null
  var itemDustFarenite                      : Item                      = null
  var itemDustMalenite                      : Item                      = null
  var itemIngotTemperedTitanium             : Item                      = null
  var itemIngotThFaSalt                     : Item                      = null
  var itemNuggetLodestone                   : Item                      = null
  var itemChunkLodestone                    : Item                      = null
  var itemConductivePowder                  : Item                      = null
  var itemBoard                             : Item                      = null
  var itemPrimedBoard                       : Item                      = null
  var itemDopedBoard                        : Item                      = null
  var itemMicrochip                         : Item                      = null
  var itemSpool                             : Item                      = null
  var itemSpoolGold                         : Item                      = null
  var itemSpoolPlatinum                     : Item                      = null
  var itemMicroCoil                         : Item                      = null
  var itemBattery                           : Item                      = null
  var itemMicroPlating                      : Item                      = null
  var itemMicroLogicCore                    : Item                      = null
  var itemKineticPulverizer                 : Item                      = null
  var itemHeatingElement                    : Item                      = null
  var itemArticulatingArm                   : Item                      = null
  var itemDissassemblyArray                 : Item                      = null
  var itemAssemblyArray                     : Item                      = null
  var itemVacuumCore                        : Item                      = null
  var itemNucleationCore                    : Item                      = null
  var itemInhibitionCore                    : Item                      = null
  var itemPortableResearchComputer          : Item                      = null
  var itemMicroTechnology                   : Item                      = null
  var itemNanoTechnology                    : Item                      = null
  var itemFemtoTechnology                   : Item                      = null
  var itemPaperSchematic                    : Item                      = null
  var itemInterfaceDeviceMicro              : Item                      = null
  var itemNanochip                          : Item                      = null
  var itemNanoCalculator                    : Item                      = null
  var itemNanoRegulator                     : Item                      = null
  var itemNanoSimulator                     : Item                      = null
  var itemBasicAICore                       : Item                      = null
  var itemLearningCore                      : Item                      = null
  var itemSchedulerCore                     : Item                      = null
  var itemManagerCore                       : Item                      = null
  var itemFluidicConductor                  : Item                      = null
  var itemNanoCoil                          : Item                      = null
  var itemNanoPlating                       : Item                      = null
  var itemTemporalResonator                 : Item                      = null
  var itemDimensionalMonopole               : Item                      = null
  var itemSelfFulfillingOracle              : Item                      = null
  var itemCrossDimensionalCommunicator      : Item                      = null
  var itemInfallibleEstimator               : Item                      = null
  var itemPanLocationalComputer             : Item                      = null
  var itemPandoraCube                       : Item                      = null
  var itemFissionReactorPlating             : Item                      = null
  var itemDigitalSchematic                  : Item                      = null
  var itemInterfaceDeviceNano               : Item                      = null
  var itemMinosGate                         : Item                      = null
  var itemCharosGate                        : Item                      = null
  var itemCerberusGate                      : Item                      = null
  var itemErinyesCircuit                    : Item                      = null
  var itemMinervaComplex                    : Item                      = null
  var itemAtlasMount                        : Item                      = null
  var itemHermesBus                         : Item                      = null
  var itemHerculesDrive                     : Item                      = null
  var itemOrpheusProcessor                  : Item                      = null
  var itemFemtoPlating                      : Item                      = null
  var itemStyxValve                         : Item                      = null
  var itemFemtoCoil                         : Item                      = null
  var itemPhlegethonTunnelPrimer            : Item                      = null
  var itemStellaratorPlating                : Item                      = null
  var itemInfinitelyRecursiveALU            : Item                      = null
  var itemInfiniteVolumePolychora           : Item                      = null
  var itemQuantumSchematic                  : Item                      = null
  var itemInterfaceDeviceFemto              : Item                      = null
  var itemCubit                             : Item                      = null
  var itemRectangulon                       : Item                      = null
  var itemPlaneoid                          : Item                      = null
  var itemCrystallite                       : Item                      = null
  var itemMineralite                        : Item                      = null
  var itemMetallite                         : Item                      = null
  var itemFaunite                           : Item                      = null
  var itemElectrite                         : Item                      = null
  var itemFlorite                           : Item                      = null
  var itemMicroCrystal                      : Item                      = null
  var itemProteinChain                      : Item                      = null
  var itemNerveCluster                      : Item                      = null
  var itemConductiveAlloy                   : Item                      = null
  var itemMetalComposite                    : Item                      = null
  var itemFibrousStrand                     : Item                      = null
  var itemMineralLattice                    : Item                      = null
  var itemFungalSpores                      : Item                      = null
  var itemIonicChunk                        : Item                      = null
  var itemReplicatingMaterial               : Item                      = null
  var itemSpinyFilament                     : Item                      = null
  var itemHardenedBulb                      : Item                      = null
  var itemMorphicChannel                    : Item                      = null
  var itemSynthesizedFiber                  : Item                      = null
  var itemOrganometallicPlate               : Item                      = null

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    recipeManager = new ManagerRecipe
    researchManager = new ManagerResearch
    assistantManager = new ManagerAssistant
    femtocraftServerCommand = new CommandFemtocraft
    config = new Configuration(new File(FemtocraftFileUtils.configFolder, ID + ".cfg"))
    FemtocraftConfigs.load(config)
    technologyConfigFile = new File(FemtocraftFileUtils.configFolder, TECH_CONFIG_APPEND + ".xml")
    recipeConfigFile = new File(FemtocraftFileUtils.configFolder, RECIPE_CONFIG_APPEND + ".cfg")
    recipeConfig = new Configuration(recipeConfigFile)

    FemtocraftPacketHandler.init()

    proxy.registerTileEntities()
    proxy.registerRendering()
    proxy.registerBlockRenderers()
    proxy.registerTickHandlers()
    if (event.getSide eq Side.CLIENT) {
      soundManager = new FemtocraftSoundManager
    }
    NetworkRegistry.INSTANCE.registerGuiHandler(this, guiProxy)
    FMLCommonHandler.instance().bus().register(new FemtocraftPlayerTracker)
    MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer)
    MinecraftForge.EVENT_BUS.register(new FemtocraftOreRetrogenHandler)

    if (FemtocraftConfigs.worldGen) {
      GameRegistry.registerWorldGenerator(new FemtocraftOreGenerator, FemtocraftConfigs.GENERATION_WEIGHT)
    }

    blockOreTitanium = registerBlock(new BlockOreTitanium(), "BlockOreTitanium", { block: Block =>
      block.setHarvestLevel("pickaxe", 2)
      if (FemtocraftConfigs.registerTitaniumOreInOreDictionary) {
        OreDictionary.registerOre("oreTitanium", new ItemStack(block))
      }
    })
    blockOrePlatinum = registerBlock(new BlockOrePlatinum(), "BlockOrePlatinum", { block: Block =>
      block.setHarvestLevel("pickaxe", 2)
      if (FemtocraftConfigs.registerPlatinumOreInOreDictionary) {
        OreDictionary.registerOre("orePlatinum", new ItemStack(block))
      }
    })
    blockOreThorium = registerBlock(new BlockOreThorium(), "BlockOreThorium", { block: Block =>
      block.setHarvestLevel("pickaxe", 2)
      if (FemtocraftConfigs.registerThoriumOreInOreDictionary) {
        OreDictionary.registerOre("oreThorium", new ItemStack(block))
      }
    })

    blockOreFarenite = registerBlock(new BlockOreFarenite(), "BlockOreFarenite", { block: Block =>
      block.setHarvestLevel("pickaxe", 2)
      if (FemtocraftConfigs.registerFareniteOreInOreDictionary) {
        OreDictionary.registerOre("oreFarenite", new ItemStack(block))
      }
    })

    blockOreMalenite = registerBlock(new BlockOreMalenite(), "BlockOreMalenite", { block: Block =>
      block.setHarvestLevel("pickaxe", 3)
      if (FemtocraftConfigs.registerMaleniteOreInOreDictionary) {
        OreDictionary.registerOre("oreMalenite", new ItemStack(block))
      }
    })

    blockOreLodestone = registerBlock(new BlockOreLodestone(), "BlockOreLodestone", { block: Block =>
      block.setHarvestLevel("pickaxe", 2)
      if (FemtocraftConfigs.registerLodestoneOreInOreDictionary) {
        OreDictionary.registerOre("oreLodestone", new ItemStack(block))
      }
    })

    nanoStone = registerBlock(new BlockNanoStone, "BlockNanoStone")
    microStone = registerBlock(new BlockMicroStone, "BlockMicroStone")
    femtoStone = registerBlock(new BlockFemtoStone, "BlockFemtoStone")
    unidentifiedAlloy = registerBlock(new BlockUnidentifiedAlloy, "BlockUnidentifiedAlloy")
    blockResearchComputer = registerBlock(new BlockResearchComputer, "BlockResearchComputer")
    blockResearchConsole = registerBlock(new BlockResearchConsole, "BlockResearchConsole")
    blockMicroCable = registerBlock(new BlockMicroCable, "BlockMicroCable")
    blockNanoCable = registerBlock(new BlockNanoCable, "BlockNanoCable")
    blockFemtoCable = registerBlock(new BlockFemtoCable, "BlockFemtoCable")
    generatorTest = registerBlock(new BlockGenerator, "BlockGenerator", { block: Block => block.setHardness(3.5f)})
    consumerTest = registerBlock(new BlockConsumer, "BlockConsumer", { block: Block => block.setHardness(3.5f)})
    blockMicroFurnaceUnlit = registerBlock(new BlockMicroFurnace(false), "BlockMicroFurnace")
    blockMicroFurnaceLit = registerBlock(new BlockMicroFurnace(true), "BlockMicroFurnace_lit")
    blockMicroDeconstructor = registerBlock(new BlockMicroDeconstructor, "BlockMicroDeconstructor")
    blockMicroReconstructor = registerBlock(new BlockMicroReconstructor, "BlockMicroReconstructor")
    blockEncoder = registerBlock(new BlockEncoder, "BlockEncoder")
    blockNanoInnervatorUnlit = registerBlock(new BlockNanoInnervator(false), "BlockNanoInnervator")
    blockNanoInnervatorLit = registerBlock(new BlockNanoInnervator(true), "BlockNanoInnervator_lit")
    blockNanoDismantler = registerBlock(new BlockNanoDismantler, "BlockNanoDismantler")
    blockNanoFabricator = registerBlock(new BlockNanoFabricator, "BlockNanoFabricator")
    blockNanoEnmesher = registerBlock(new BlockNanoEnmesher, "BlockNanoEnmesher")
    blockNanoHorologe = registerBlock(new BlockNanoHorologe, "BlockNanoHorologe")
    blockFemtoImpulserUnlit = registerBlock(new BlockFemtoImpulser(false), "BlockFemtoImpulser")
    blockFemtoImpulserLit = registerBlock(new BlockFemtoImpulser(true), "BlockFemtoImpulser_lit")
    blockFemtoRepurposer = registerBlock(new BlockFemtoRepurposer, "BlockFemtoRepurposer")
    blockFemtoCoagulator = registerBlock(new BlockFemtoCoagulator, "BlockFemtoCoagulator")
    blockFemtoEntangler = registerBlock(new BlockFemtoEntangler, "BlockFemtoEntangler")
    blockFemtoChronoshifter = registerBlock(new BlockFemtoChronoshifter, "BlockFemtoChronoshifter")
    blockMicroCube = new BlockMicroCube().setBlockName("BlockMicroCube")
    GameRegistry.registerBlock(blockMicroCube, classOf[ItemBlockMicroCube], "BlockMicroCube")
    blockNanoCubeFrame = registerBlock(new BlockNanoCubeFrame, "BlockNanoCubeFrame")
    blockNanoCubePort = registerBlock(new BlockNanoCubePort, "BlockNanoCubePort")
    blockFemtoCubePort = registerBlock(new BlockFemtoCubePort, "BlockFemtoCubePort")
    blockFemtoCubeFrame = registerBlock(new BlockFemtoCubeFrame, "BlockFemtoCubeFrame")
    blockFemtoCubeChassis = registerBlock(new BlockFemtoCubeChassis, "BlockFemtoCubeChassis")
    blockVacuumTube = registerBlock(new BlockVacuumTube, "BlockVacuumTube")
    blockSuctionPipe = registerBlock(new BlockSuctionPipe, "BlockSuctionPipe")
    blockMicroChargingBase = registerBlock(new BlockAtmosphericChargingBase, "BlockBaseMicroCharging")
    blockMicroChargingCoil = registerBlock(new BlockAtmosphericChargingCoil, "BlockCoilMicroCharging")
    blockMicroChargingCapacitor = registerBlock(new BlockAtmosphericChargingCapacitor,
                                                "BlockCapacitorMicroCharging")
    blockMagneticInductionGenerator = registerBlock(new BlockMagneticInductionGenerator,
                                                    "BlockMagneticInductionGenerator")
    blockOrbitalEqualizer = registerBlock(new BlockOrbitalEqualizer, "BlockOrbitalEqualizer")
    blockCryoEndothermalChargingBase = registerBlock(new BlockCryoEndothermalChargingBase,
                                                     "BlockCryoEndothermalChargingBase")
    blockCryoEndothermalChargingCoil = registerBlock(new BlockCryoEndothermalChargingCoil,
                                                     "BlockCryoEndothermalChargingCoil")
    blockFissionReactorCore = registerBlock(new BlockNanoFissionReactorCore, "BlockFissionReactorCore")
    blockFissionReactorHousing = registerBlock(new BlockNanoFissionReactorHousing, "BlockFissionReactorHousing")
    blockMagnetohydrodynamicGenerator = registerBlock(new BlockMagnetohydrodynamicGenerator,
                                                      "BlockMagnetohydrodynamicGenerator")
    blockSteamGenerator = registerBlock(new BlockSteamGenerator, "BlockSteamGenerator")
    blockDecontaminationChamber = registerBlock(new BlockDecontaminationChamber, "BlockDecontaminationChamber")
    blockPhlegethonTunnelCore = registerBlock(new BlockPhlegethonTunnelCore, "BlockPhlegethonTunnelCore")
    blockPhlegethonTunnelFrame = registerBlock(new BlockPhlegethonTunnelFrame, "BlockPhlegethonTunnelFrame")
    blockSisyphusStabilizer = registerBlock(new BlockSisyphusStabilizer, "BlockSisyphusStabilizer")
    blockNullEqualizer = registerBlock(new BlockNullEqualizer, "BlockNullEqualizer")
    blockStellaratorCore = registerBlock(new BlockFemtoStellaratorCore, "BlockStellaratorCore")
    blockStellaratorFocus = registerBlock(new BlockFemtoStellaratorFocus, "BlockStellaratorFocus")
    blockStellaratorOpticalMaser = registerBlock(new BlockFemtoStellaratorOpticalMaser, "BlockStellaratorOpticalMaser")
    blockStellaratorHousing = registerBlock(new BlockFemtoStellaratorHousing, "BlockStellaratorHousing")
    blockPlasmaConduit = registerBlock(new BlockPlasmaConduit, "BlockPlasmaConduit")
    blockPlasmaVent = registerBlock(new BlockPlasmaVent, "BlockPlasmaVent")
    blockPlasmaSolenoid = registerBlock(new BlockPlasmaSolenoid, "BlockPlasmaSolenoid")
    blockPlasmaCondenser = registerBlock(new BlockPlasmaCondenser, "BlockPlasmaCondenser")
    fluidMass = new FluidMass
    FluidRegistry.registerFluid(fluidMass)
    blockFluidMass = registerBlock(new BlockFluidMass, "BlockMass")
    fluidMoltenSalt = new FluidMoltenSalt
    FluidRegistry.registerFluid(fluidMoltenSalt)
    blockFluidMoltenSalt = registerBlock(new BlockFluidMoltenSalt, "BlockFluidMoltenSalt")
    fluidCooledMoltenSalt = new FluidCooledMoltenSalt
    FluidRegistry.registerFluid(fluidCooledMoltenSalt)
    blockFluidCooledMoltenSalt = registerBlock(new BlockFluidCooledMoltenSalt, "BlockFluidCooledMoltenSalt")
    fluidCooledContaminatedMoltenSalt = new FluidCooledContaminatedMoltenSalt
    FluidRegistry.registerFluid(fluidCooledContaminatedMoltenSalt)
    blockFluidCooledContaminatedMoltenSalt = registerBlock(new BlockFluidCooledContaminatedMoltenSalt, "BlockFluidCooledContaminatedMoltenSalt")
    blockPlasma = registerBlock(new BlockPlasma(), "BlockPlasma")

    itemIngotTitanium = registerBaseItem("ItemIngotTitanium", "Titanium Ingot", { item: Item =>
      if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotTitanium", new ItemStack(item))
      }
    })

    itemIngotPlatinum = registerBaseItem("ItemIngotPlatinum", "Platinum Ingot", { item: Item =>
      if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotPlatinum", new ItemStack(item))
      }
    })

    itemIngotThorium = registerBaseItem("ItemIngotThorium", "Thorium Ingot", { item: Item =>
      if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotThorium", new ItemStack(item))
      }
    })

    itemDustFarenite = registerBaseItem("ItemDustFarenite", "Farenite", { item: Item =>
      OreDictionary.registerOre("dustFarenite", new ItemStack(item))
    })
    itemDustMalenite = registerBaseItem("ItemDustMalenite", "Malenite", { item: Item =>
      OreDictionary.registerOre("dustMalenite", new ItemStack(item))
    })
    itemIngotTemperedTitanium = registerBaseItem("ItemIngotTemperedTitanium", "Tempered Titanium Ingot", { item: Item =>
      OreDictionary.registerOre("ingotTemperedTitanium", new ItemStack(item))
    })
    itemIngotThFaSalt = registerBaseItem("ItemIngotThFaSalt", "ThFa Salt Ingot", { item: Item =>
      OreDictionary.registerOre("ingotThFaSalt", new ItemStack(item))
    })
    itemNuggetLodestone = registerBaseItem("ItemNuggetLodestone", "Lodestone Nugget", { item: Item =>
      OreDictionary.registerOre("nuggetLodestone", new ItemStack(item))
    })
    itemChunkLodestone = registerBaseItem("ItemChunkLodestone", "Lodestone Chunk", { item: Item =>
      OreDictionary.registerOre("chunkLodestone", new ItemStack(item))
    })
    itemConductivePowder = registerBaseItem("ItemConductivePowder", "Conductive Powder")
    itemBoard = registerBaseItem("ItemBoard", "Board")
    itemPrimedBoard = registerBaseItem("ItemPrimedBoard", "Primed Board")
    itemDopedBoard = registerBaseItem("ItemDopedBoard", "Doped Board")
    itemMicrochip = registerBaseItem("ItemMicrochip", "Microchip")
    itemSpool = registerBaseItem("ItemSpool", "Spool")
    itemSpoolGold = registerBaseItem("ItemSpoolGold", "Gold Wire Spool")
    itemSpoolPlatinum = registerBaseItem("ItemSpoolPlatinum", "Platinum Wire Spool")
    itemMicroCoil = registerBaseItem("ItemMicroCoil", "Micro Coil")
    itemBattery = registerBaseItem("ItemBattery", "Battery")
    itemArticulatingArm = registerBaseItem("ItemArticulatingArm", "Articulating Arm")
    itemDissassemblyArray = registerBaseItem("ItemDissassemblyArray", "Dissassembly Array")
    itemAssemblyArray = registerBaseItem("ItemAssemblyArray", "Assembly Array")
    itemVacuumCore = registerBaseItem("ItemVacuumCore", "Vacuum Core")
    itemMicroLogicCore = registerBaseItem("ItemMicroLogicCore", "Micro Logic Core")
    itemKineticPulverizer = registerBaseItem("ItemKineticPulverizer", "Kinetic Pulverizer")
    itemHeatingElement = registerBaseItem("ItemHeatingCoil", "Heating Coil")
    itemPortableResearchComputer = registerItem(new ItemPortableResearchComputer, "ItemPortableResearchComputer")
    itemNanochip = registerBaseItem("ItemNanochip", "Nanochip")
    itemNanoCalculator = registerBaseItem("ItemNanoCalculator", "Nano Calculator")
    itemNanoRegulator = registerBaseItem("ItemNanoRegulator", "Nano Regulator")
    itemNanoSimulator = registerBaseItem("ItemNanoSimulator", "Nano Simulator")
    itemBasicAICore = registerBaseItem("ItemBasicAICore", "Basic AI Core")
    itemLearningCore = registerBaseItem("ItemLearningCore", "Learning Core")
    itemSchedulerCore = registerBaseItem("ItemSchedulerCore", "Scheduler Core")
    itemManagerCore = registerBaseItem("ItemManagerCore", "Manager Core")
    itemFluidicConductor = registerBaseItem("ItemFluidicConductor", "Fluidic Conductor")
    itemNanoCoil = registerBaseItem("ItemNanoCoil", "Nano Coil")
    itemNanoPlating = registerBaseItem("ItemNanoPlating", "Nano Plating")
    itemTemporalResonator = registerBaseItem("ItemTemporalResonator", "Temporal Resonator")
    itemDimensionalMonopole = registerBaseItem("ItemDimensionalMonopole", "Dimensional Monopole")
    itemSelfFulfillingOracle = registerBaseItem("ItemSelfFulfillingOracle", "Self Fulfilling Oracle")
    itemCrossDimensionalCommunicator = registerBaseItem("ItemCrossDimensionalCommunicator",
                                                        "Cross Dimensional Communicator")
    itemInfallibleEstimator = registerBaseItem("ItemInfallibleEstimator", "Infallible Estimator")
    itemPanLocationalComputer = registerBaseItem("ItemPanLocationalComputer", "Pan Locational Computer")
    itemPandoraCube = registerBaseItem("ItemPandoraCube", "Pandora Cube")
    itemFissionReactorPlating = registerBaseItem("ItemFissionReactorPlating", "Fission Reactor Plating")
    itemDigitalSchematic = registerItem(new ItemDigitalSchematic("ItemDigitalSchematic"), "ItemDigitalSchematic")
    itemMinosGate = registerBaseItem("ItemMinosGate", "Minos Gate")
    itemCharosGate = registerBaseItem("ItemCharosGate", "Charos Gate")
    itemCerberusGate = registerBaseItem("ItemCerberusGate", "Cerberus Gate")
    itemErinyesCircuit = registerBaseItem("ItemErinyesCircuit", "Erinyes Circuit")
    itemMinervaComplex = registerBaseItem("ItemMinervaComplex", "Minerva Complex")
    itemAtlasMount = registerBaseItem("ItemAtlasMount", "Atlas Mount")
    itemHermesBus = registerBaseItem("ItemHermesBus", "Hermes Bus")
    itemHerculesDrive = registerBaseItem("ItemHerculesDrive", "Hercules Drive")
    itemOrpheusProcessor = registerBaseItem("ItemOrpheusProcessor", "Orpheus Processor")
    itemFemtoPlating = registerBaseItem("ItemFemtoPlating", "Femto Plating")
    itemStyxValve = registerBaseItem("ItemStyxValve", "Styx Valve")
    itemFemtoCoil = registerBaseItem("ItemFemtoCoil", "Femto Coil")
    itemPhlegethonTunnelPrimer = registerBaseItem("ItemPhlegethonTunnelPrimer", "Phlegethon Tunnel Primer")
    itemStellaratorPlating = registerBaseItem("ItemStellaratorPlating", "Stellarator Plating")
    itemInfinitelyRecursiveALU = registerBaseItem("ItemInfinitelyRecursiveALU", "Infinitely Recursive ALU")
    itemInfiniteVolumePolychora = registerBaseItem("ItemInfiniteVolumePolychora", "Infinite Volume Polychora")
    itemNucleationCore = registerItem(new ItemNucleationCore("ItemNucleationCore"), "ItemNucleationCore")
    itemInhibitionCore = registerItem(new ItemInhibitionCore("ItemInhibitionCore"), "ItemInhibitionCore")
    itemQuantumSchematic = registerItem(new ItemQuantumSchematic("ItemQuantumSchematic"), "ItemQuantumSchematic")
    itemMicroTechnology = registerItem(new ItemMicroTechnology, "ItemMicroTechnology")
    itemNanoTechnology = registerItem(new ItemNanoTechnology, "ItemNanoTechnology")
    itemFemtoTechnology = registerItem(new ItemFemtoTechnology, "ItemFemtoTechnology")
    itemPaperSchematic = registerItem(new ItemPaperSchematic("ItemPaperSchematic"), "ItemPaperSchematic")
    itemInterfaceDeviceMicro = registerItem(new ItemMicroInterfaceDevice, "ItemInterfaceDeviceMicro")
    itemInterfaceDeviceNano = registerItem(new ItemNanoInterfaceDevice, "ItemInterfaceDeviceNano")
    itemInterfaceDeviceFemto = registerItem(new ItemFemtoInterfaceDevice, "ItemInterfaceDeviceFemto")
    itemCubit = registerBaseItem("ItemCubit", FemtocraftUtils.orangeify("Cubit"))
    ComponentRegistry.registerComponent(itemCubit, EnumTechLevel.FEMTO)
    itemRectangulon = registerBaseItem("ItemRectangulon", FemtocraftUtils.orangeify("Rectangulon"))
    ComponentRegistry.registerComponent(itemRectangulon, EnumTechLevel.FEMTO)
    itemPlaneoid = registerBaseItem("ItemPlaneoid", FemtocraftUtils.orangeify("Planeoid"))
    ComponentRegistry.registerComponent(itemPlaneoid, EnumTechLevel.FEMTO)
    itemCrystallite = registerBaseItem("ItemCrystallite", FemtocraftUtils.greenify("Crystallite"))
    ComponentRegistry.registerComponent(itemCrystallite, EnumTechLevel.NANO)
    itemMineralite = registerBaseItem("ItemMineralite", FemtocraftUtils.greenify("Mineralite"))
    ComponentRegistry.registerComponent(itemMineralite, EnumTechLevel.NANO)
    itemMetallite = registerBaseItem("ItemMetallite", FemtocraftUtils.greenify("Metallite"))
    ComponentRegistry.registerComponent(itemMetallite, EnumTechLevel.NANO)
    itemFaunite = registerBaseItem("ItemFaunite", FemtocraftUtils.greenify("Faunite"))
    ComponentRegistry.registerComponent(itemFaunite, EnumTechLevel.NANO)
    itemElectrite = registerBaseItem("ItemElectrite", FemtocraftUtils.greenify("Electrite"))
    ComponentRegistry.registerComponent(itemElectrite, EnumTechLevel.NANO)
    itemFlorite = registerBaseItem("ItemFlorite", FemtocraftUtils.greenify("Florite"))
    ComponentRegistry.registerComponent(itemFlorite, EnumTechLevel.NANO)
    itemMicroCrystal = registerBaseItem("ItemMicroCrystal", FemtocraftUtils.blueify("Micro Crystal"))
    ComponentRegistry.registerComponent(itemMicroCrystal, EnumTechLevel.MICRO)
    itemProteinChain = registerBaseItem("ItemProteinChain", FemtocraftUtils.blueify("Protein Chain"))
    ComponentRegistry.registerComponent(itemProteinChain, EnumTechLevel.MICRO)
    itemNerveCluster = registerBaseItem("ItemNerveCluster", FemtocraftUtils.blueify("Nerve Cluster"))
    ComponentRegistry.registerComponent(itemNerveCluster, EnumTechLevel.MICRO)
    itemConductiveAlloy = registerBaseItem("ItemConductiveAlloy", FemtocraftUtils.blueify("Conductive Alloy"))
    ComponentRegistry.registerComponent(itemConductiveAlloy, EnumTechLevel.MICRO)
    itemMetalComposite = registerBaseItem("ItemMetalComposite", FemtocraftUtils.blueify("Metal Composite"))
    ComponentRegistry.registerComponent(itemMetalComposite, EnumTechLevel.MICRO)
    itemFibrousStrand = registerBaseItem("ItemFibrousStrand", FemtocraftUtils.blueify("Fibrous Strand"))
    ComponentRegistry.registerComponent(itemFibrousStrand, EnumTechLevel.MICRO)
    itemMineralLattice = registerBaseItem("ItemMineralLattice", FemtocraftUtils.blueify("Mineral Lattice"))
    ComponentRegistry.registerComponent(itemMineralLattice, EnumTechLevel.MICRO)
    itemFungalSpores = registerBaseItem("ItemFungalSpores", FemtocraftUtils.blueify("Fungal Spores"))
    ComponentRegistry.registerComponent(itemFungalSpores, EnumTechLevel.MICRO)
    itemIonicChunk = registerBaseItem("ItemIonicChunk", FemtocraftUtils.blueify("Ionic Chunk"))
    ComponentRegistry.registerComponent(itemIonicChunk, EnumTechLevel.MICRO)
    itemReplicatingMaterial = registerBaseItem("ItemReplicatingMaterial", FemtocraftUtils.blueify("Replicating Material"))
    ComponentRegistry.registerComponent(itemReplicatingMaterial, EnumTechLevel.MICRO)
    itemSpinyFilament = registerBaseItem("ItemSpinyFilament", FemtocraftUtils.blueify("Spiny Filament"))
    ComponentRegistry.registerComponent(itemSpinyFilament, EnumTechLevel.MICRO)
    itemHardenedBulb = registerBaseItem("ItemHardenedBulb", FemtocraftUtils.blueify("Hardened Bulb"))
    ComponentRegistry.registerComponent(itemHardenedBulb, EnumTechLevel.MICRO)
    itemMorphicChannel = registerBaseItem("ItemMorphicChannel", FemtocraftUtils.blueify("Morphic Channel"))
    ComponentRegistry.registerComponent(itemMorphicChannel, EnumTechLevel.MICRO)
    itemSynthesizedFiber = registerBaseItem("ItemSynthesizedFiber", FemtocraftUtils.blueify("Synthesized Fiber"))
    ComponentRegistry.registerComponent(itemSynthesizedFiber, EnumTechLevel.MICRO)
    itemOrganometallicPlate = registerBaseItem("ItemOrganometallicPlate", FemtocraftUtils.blueify("Organometallic Plate"))
    ComponentRegistry.registerComponent(itemOrganometallicPlate, EnumTechLevel.MICRO)
    itemMicroPlating = registerBaseItem("ItemMicroPlating", "Micro Plating")
  }

  private def registerBaseItem(unlocalizedName: String, name: String, fun: (ItemBase) => Unit = { a: Item =>}) = registerItem(new ItemBase(unlocalizedName), unlocalizedName, fun)

  private def registerItem[I1 <: Item](item: I1, name: String, fun: (I1) => Unit = { a: Item =>}): I1 = {
    if (true /*register item? */ ) {
      item.setUnlocalizedName(name)
      GameRegistry.registerItem(item, name)
      fun(item)
    }
    item
  }

  private def registerBlock[B1 <: Block](block: B1, name: String, fun: (B1) => Unit = { b: Block =>}): B1 = {
    if (true /*register block? */ ) {
      block.setBlockName(name)
      GameRegistry.registerBlock(block, name)
      fun(block)
    }
    block
  }

  def log(level: Level, msg: String) = logger.log(level, msg)

  @EventHandler def load(event: FMLInitializationEvent) {
  }

  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    assemblerConfigs = new FemtocraftAssemblerConfig(recipeConfig)
    recipeManager.init()
    MagnetRegistry.init()
    researchManager.init()
    if (event.getSide == Side.CLIENT) {
      researchManager.calculateGraph()
    }
  }

  @EventHandler def serverLoad(event: FMLServerStartingEvent) {
    event.registerServerCommand(femtocraftServerCommand)
  }
}


