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
import java.util.UUID

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.blocks._
import com.itszuvalex.femtocraft.command.{CommandBase, CommandFemtocraft}
import com.itszuvalex.femtocraft.configuration.{AutoGenConfig, FemtocraftAssemblerConfig, FemtocraftConfigs}
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
import com.itszuvalex.femtocraft.utility.blocks.BlockSpatialAlternator
import com.itszuvalex.femtocraft.utility.items.ItemPocketPocket
import com.itszuvalex.femtocraft.utils.FemtocraftFileUtils
import com.mojang.authlib.GameProfile
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.GameRegistry
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side
import net.minecraft.block.Block
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.world.WorldServer
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.common.util.FakePlayerFactory
import net.minecraftforge.fluids.{BlockFluidBase, Fluid, FluidRegistry}
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.{Level, LogManager, Logger}

/**
 * Created by Christopher Harris (Itszuvalex) on 10/6/14.
 */
@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION, modLanguage = "scala")
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
  val fakePlayerGameProfile                 : GameProfile               = new
      GameProfile(UUID.fromString("b730f1c0-018b-40be-9515-48c3f4c2f745"), "[Femtocraft]")
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
  var blockPlasmaSolenoid                   : Block                     = null
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
  var blockSpatialAlternator                : Block                     = null
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
  var itemPocketPocket                      : Item                      = null

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    recipeManager = new ManagerRecipe
    researchManager = new ManagerResearch
    assistantManager = new ManagerAssistant
    femtocraftServerCommand = new CommandFemtocraft

    AutoGenConfig.init(new Configuration(new File(FemtocraftFileUtils.configFolder, "AutoGenConfig.cfg")))

    config = new Configuration(new File(FemtocraftFileUtils.configFolder, ID + ".cfg"))
    FemtocraftConfigs.load(config)
    technologyConfigFile = new File(FemtocraftFileUtils.autogenConfigPath, TECH_CONFIG_APPEND + ".xml")
    recipeConfigFile = new File(FemtocraftFileUtils.autogenConfigPath, RECIPE_CONFIG_APPEND + ".cfg")
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
    blockMicroChargingCapacitor = registerBlock(new BlockAtmosphericChargingCapacitor, "BlockCapacitorMicroCharging")
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
    blockFluidCooledContaminatedMoltenSalt = registerBlock(new BlockFluidCooledContaminatedMoltenSalt,
                                                           "BlockFluidCooledContaminatedMoltenSalt")
    blockPlasma = registerBlock(new BlockPlasma(), "BlockPlasma")

    blockSpatialAlternator = registerBlock(new BlockSpatialAlternator(), "BlockSpatialAlternator")

    itemIngotTitanium = registerBaseItem("ItemIngotTitanium", { item: Item =>
      if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotTitanium", new ItemStack(item))
      }
    })

    itemIngotPlatinum = registerBaseItem("ItemIngotPlatinum", { item: Item =>
      if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotPlatinum", new ItemStack(item))
      }
    })

    itemIngotThorium = registerBaseItem("ItemIngotThorium", { item: Item =>
      if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) {
        OreDictionary.registerOre("ingotThorium", new ItemStack(item))
      }
    })

    itemDustFarenite = registerBaseItem("ItemDustFarenite", { item: Item =>
      OreDictionary.registerOre("dustFarenite", new ItemStack(item))
    })
    itemDustMalenite = registerBaseItem("ItemDustMalenite", { item: Item =>
      OreDictionary.registerOre("dustMalenite", new ItemStack(item))
    })
    itemIngotTemperedTitanium = registerBaseItem("ItemIngotTemperedTitanium", { item: Item =>
      OreDictionary.registerOre("ingotTemperedTitanium", new ItemStack(item))
    })
    itemIngotThFaSalt = registerBaseItem("ItemIngotThFaSalt", { item: Item =>
      OreDictionary.registerOre("ingotThFaSalt", new ItemStack(item))
    })
    itemNuggetLodestone = registerBaseItem("ItemNuggetLodestone", { item: Item =>
      OreDictionary.registerOre("nuggetLodestone", new ItemStack(item))
    })
    itemChunkLodestone = registerBaseItem("ItemChunkLodestone", { item: Item =>
      OreDictionary.registerOre("chunkLodestone", new ItemStack(item))
    })
    itemConductivePowder = registerBaseItem("ItemConductivePowder")
    itemBoard = registerBaseItem("ItemBoard")
    itemPrimedBoard = registerBaseItem("ItemPrimedBoard")
    itemDopedBoard = registerBaseItem("ItemDopedBoard")
    itemMicrochip = registerBaseItem("ItemMicrochip")
    itemSpool = registerBaseItem("ItemSpool")
    itemSpoolGold = registerBaseItem("ItemSpoolGold")
    itemSpoolPlatinum = registerBaseItem("ItemSpoolPlatinum")
    itemMicroCoil = registerBaseItem("ItemMicroCoil")
    itemBattery = registerBaseItem("ItemBattery")
    itemArticulatingArm = registerBaseItem("ItemArticulatingArm")
    itemDissassemblyArray = registerBaseItem("ItemDissassemblyArray")
    itemAssemblyArray = registerBaseItem("ItemAssemblyArray")
    itemVacuumCore = registerBaseItem("ItemVacuumCore")
    itemMicroLogicCore = registerBaseItem("ItemMicroLogicCore")
    itemKineticPulverizer = registerBaseItem("ItemKineticPulverizer")
    itemHeatingElement = registerBaseItem("ItemHeatingCoil")
    itemPortableResearchComputer = registerItem(new ItemPortableResearchComputer, "ItemPortableResearchComputer")
    itemNanochip = registerBaseItem("ItemNanochip")
    itemNanoCalculator = registerBaseItem("ItemNanoCalculator")
    itemNanoRegulator = registerBaseItem("ItemNanoRegulator")
    itemNanoSimulator = registerBaseItem("ItemNanoSimulator")
    itemBasicAICore = registerBaseItem("ItemBasicAICore")
    itemLearningCore = registerBaseItem("ItemLearningCore")
    itemSchedulerCore = registerBaseItem("ItemSchedulerCore")
    itemManagerCore = registerBaseItem("ItemManagerCore")
    itemFluidicConductor = registerBaseItem("ItemFluidicConductor")
    itemNanoCoil = registerBaseItem("ItemNanoCoil")
    itemNanoPlating = registerBaseItem("ItemNanoPlating")
    itemTemporalResonator = registerBaseItem("ItemTemporalResonator")
    itemDimensionalMonopole = registerBaseItem("ItemDimensionalMonopole")
    itemSelfFulfillingOracle = registerBaseItem("ItemSelfFulfillingOracle")
    itemCrossDimensionalCommunicator = registerBaseItem("ItemCrossDimensionalCommunicator")
    itemInfallibleEstimator = registerBaseItem("ItemInfallibleEstimator")
    itemPanLocationalComputer = registerBaseItem("ItemPanLocationalComputer")
    itemPandoraCube = registerBaseItem("ItemPandoraCube")
    itemFissionReactorPlating = registerBaseItem("ItemFissionReactorPlating")
    itemDigitalSchematic = registerItem(new ItemDigitalSchematic("ItemDigitalSchematic"), "ItemDigitalSchematic")
    itemMinosGate = registerBaseItem("ItemMinosGate")
    itemCharosGate = registerBaseItem("ItemCharosGate")
    itemCerberusGate = registerBaseItem("ItemCerberusGate")
    itemErinyesCircuit = registerBaseItem("ItemErinyesCircuit")
    itemMinervaComplex = registerBaseItem("ItemMinervaComplex")
    itemAtlasMount = registerBaseItem("ItemAtlasMount")
    itemHermesBus = registerBaseItem("ItemHermesBus")
    itemHerculesDrive = registerBaseItem("ItemHerculesDrive")
    itemOrpheusProcessor = registerBaseItem("ItemOrpheusProcessor")
    itemFemtoPlating = registerBaseItem("ItemFemtoPlating")
    itemStyxValve = registerBaseItem("ItemStyxValve")
    itemFemtoCoil = registerBaseItem("ItemFemtoCoil")
    itemPhlegethonTunnelPrimer = registerBaseItem("ItemPhlegethonTunnelPrimer")
    itemStellaratorPlating = registerBaseItem("ItemStellaratorPlating")
    itemInfinitelyRecursiveALU = registerBaseItem("ItemInfinitelyRecursiveALU")
    itemInfiniteVolumePolychora = registerBaseItem("ItemInfiniteVolumePolychora")
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
    itemPocketPocket = registerItem(new ItemPocketPocket, "ItemPocketPocket")
    //Decomposition Items
    itemCubit = registerBaseItem("ItemCubit")
    ComponentRegistry.registerComponent(itemCubit, EnumTechLevel.FEMTO)
    itemRectangulon = registerBaseItem("ItemRectangulon")
    ComponentRegistry.registerComponent(itemRectangulon, EnumTechLevel.FEMTO)
    itemPlaneoid = registerBaseItem("ItemPlaneoid")
    ComponentRegistry.registerComponent(itemPlaneoid, EnumTechLevel.FEMTO)
    itemCrystallite = registerBaseItem("ItemCrystallite")
    ComponentRegistry.registerComponent(itemCrystallite, EnumTechLevel.NANO)
    itemMineralite = registerBaseItem("ItemMineralite")
    ComponentRegistry.registerComponent(itemMineralite, EnumTechLevel.NANO)
    itemMetallite = registerBaseItem("ItemMetallite")
    ComponentRegistry.registerComponent(itemMetallite, EnumTechLevel.NANO)
    itemFaunite = registerBaseItem("ItemFaunite")
    ComponentRegistry.registerComponent(itemFaunite, EnumTechLevel.NANO)
    itemElectrite = registerBaseItem("ItemElectrite")
    ComponentRegistry.registerComponent(itemElectrite, EnumTechLevel.NANO)
    itemFlorite = registerBaseItem("ItemFlorite")
    ComponentRegistry.registerComponent(itemFlorite, EnumTechLevel.NANO)
    itemMicroCrystal = registerBaseItem("ItemMicroCrystal")
    ComponentRegistry.registerComponent(itemMicroCrystal, EnumTechLevel.MICRO)
    itemProteinChain = registerBaseItem("ItemProteinChain")
    ComponentRegistry.registerComponent(itemProteinChain, EnumTechLevel.MICRO)
    itemNerveCluster = registerBaseItem("ItemNerveCluster")
    ComponentRegistry.registerComponent(itemNerveCluster, EnumTechLevel.MICRO)
    itemConductiveAlloy = registerBaseItem("ItemConductiveAlloy")
    ComponentRegistry.registerComponent(itemConductiveAlloy, EnumTechLevel.MICRO)
    itemMetalComposite = registerBaseItem("ItemMetalComposite")
    ComponentRegistry.registerComponent(itemMetalComposite, EnumTechLevel.MICRO)
    itemFibrousStrand = registerBaseItem("ItemFibrousStrand")
    ComponentRegistry.registerComponent(itemFibrousStrand, EnumTechLevel.MICRO)
    itemMineralLattice = registerBaseItem("ItemMineralLattice")
    ComponentRegistry.registerComponent(itemMineralLattice, EnumTechLevel.MICRO)
    itemFungalSpores = registerBaseItem("ItemFungalSpores")
    ComponentRegistry.registerComponent(itemFungalSpores, EnumTechLevel.MICRO)
    itemIonicChunk = registerBaseItem("ItemIonicChunk")
    ComponentRegistry.registerComponent(itemIonicChunk, EnumTechLevel.MICRO)
    itemReplicatingMaterial = registerBaseItem("ItemReplicatingMaterial")
    ComponentRegistry.registerComponent(itemReplicatingMaterial, EnumTechLevel.MICRO)
    itemSpinyFilament = registerBaseItem("ItemSpinyFilament")
    ComponentRegistry.registerComponent(itemSpinyFilament, EnumTechLevel.MICRO)
    itemHardenedBulb = registerBaseItem("ItemHardenedBulb")
    ComponentRegistry.registerComponent(itemHardenedBulb, EnumTechLevel.MICRO)
    itemMorphicChannel = registerBaseItem("ItemMorphicChannel")
    ComponentRegistry.registerComponent(itemMorphicChannel, EnumTechLevel.MICRO)
    itemSynthesizedFiber = registerBaseItem("ItemSynthesizedFiber")
    ComponentRegistry.registerComponent(itemSynthesizedFiber, EnumTechLevel.MICRO)
    itemOrganometallicPlate = registerBaseItem("ItemOrganometallicPlate")
    ComponentRegistry.registerComponent(itemOrganometallicPlate, EnumTechLevel.MICRO)
    itemMicroPlating = registerBaseItem("ItemMicroPlating")
  }

  private def registerBaseItem(unlocalizedName: String, fun: (ItemBase) => Unit = { a: Item =>}) = registerItem(new
                                                                                                                    ItemBase(unlocalizedName),
                                                                                                                unlocalizedName,
                                                                                                                fun)

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

  def getFakePlayer(world: WorldServer) = FakePlayerFactory.get(world, fakePlayerGameProfile)

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


