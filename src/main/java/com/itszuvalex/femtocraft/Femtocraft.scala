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
import java.util.logging.{Level, Logger}

import com.itszuvalex.femtocraft.blocks.{BlockFemtoStone, BlockMicroStone, BlockNanoStone, BlockUnidentifiedAlloy}
import com.itszuvalex.femtocraft.core.ore._
import com.itszuvalex.femtocraft.industry.blocks._
import com.itszuvalex.femtocraft.managers.ManagerRecipe
import com.itszuvalex.femtocraft.managers.assistant.ManagerAssistant
import com.itszuvalex.femtocraft.managers.research.ManagerResearch
import com.itszuvalex.femtocraft.power.blocks._
import com.itszuvalex.femtocraft.research.blocks.{BlockResearchComputer, BlockResearchConsole}
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import command.{CommandBase, CommandFemtocraft}
import configuration.{FemtocraftConfigAssembler, FemtocraftConfigTechnology, FemtocraftConfigs}
import core.MagnetRegistry
import core.fluids.{BlockFluidMass, FluidMass}
import core.items.{ItemBase, ItemFemtoInterfaceDevice, ItemMicroInterfaceDevice, ItemNanoInterfaceDevice}
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.network.{NetworkMod, NetworkRegistry}
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import cpw.mods.fml.common.{FMLLog, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side
import industry.items.{ItemDigitalSchematic, ItemPaperSchematic, ItemQuantumSchematic}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.common.{Configuration, MinecraftForge}
import net.minecraftforge.fluids.{Fluid, FluidRegistry}
import net.minecraftforge.oredict.OreDictionary
import power.fluids.{FluidCooledContaminatedMoltenSalt, FluidCooledMoltenSalt, FluidMoltenSalt}
import power.items.{ItemBlockMicroCube, ItemInhibitionCore, ItemNucleationCore}
import power.plasma.BlockPlasma
import proxy.ProxyCommon
import research.items.{ItemFemtoTechnology, ItemMicroTechnology, ItemNanoTechnology, ItemPortableResearchComputer}
import transport.items.blocks.BlockVacuumTube
import transport.liquids.blocks.BlockSuctionPipe

/**
 * Created by Christopher Harris (Itszuvalex) on 10/6/14.
 */
@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION,
     modLanguage = "scala")
@NetworkMod(channels = Array(Femtocraft.ID, Femtocraft.GUI_CHANNEL,
                             Femtocraft.SOUND_CHANNEL,
                             Femtocraft.PLAYER_PROP_CHANNEL,
                             Femtocraft.RESEARCH_CHANNEL,
                             Femtocraft.RESEARCH_CONSOLE_CHANNEL,
                             Femtocraft.VACUUM_TUBE_CHANNEL,
                             Femtocraft.FISSION_REACTOR_CHANNEL,
                             Femtocraft.PHLEGETHON_TUNNEL_CHANNEL),
            packetHandler = classOf[FemtocraftPacketHandler],
            clientSideRequired = true, serverSideRequired = true)
object Femtocraft {
  final val ID                   = "Femtocraft"
  final val VERSION              = "0.1.0"
  final val TECH_CONFIG_APPEND   = "Technology"
  final val RECIPE_CONFIG_APPEND = "Recipes"

  /* Packet Channels */
  final val GUI_CHANNEL               = ID + ".gui"
  final val SOUND_CHANNEL             = ID + ".snd"
  final val PLAYER_PROP_CHANNEL       = ID + ".pprop"
  final val RESEARCH_CHANNEL          = ID + ".rman"
  final val RESEARCH_CONSOLE_CHANNEL  = ID + ".rcon"
  final val VACUUM_TUBE_CHANNEL       = ID + ".vtube"
  final val FISSION_REACTOR_CHANNEL   = ID + ".fiss"
  final val PHLEGETHON_TUNNEL_CHANNEL = ID + ".phleg"

  @SidedProxy(clientSide = "com.itszuvalex.femtocraft.proxy.ProxyClient",
              serverSide = "com.itszuvalex.femtocraft.proxy.ProxyCommon")
  var proxy                                 : ProxyCommon                            = null
  var femtocraftTab                         : CreativeTabs                           = new FemtocraftCreativeTab("Femtocraft")
  var logger                                : Logger                                 = null
  var configFile                            : Configuration                          = null
  var technologyConfigFile                  : File                                   = null
  var recipeConfigFile                      : File                                   = null
  var recipeConfig                          : Configuration                          = null
  var assemblerConfigs                      : FemtocraftConfigAssembler              = null
  var recipeManager                         : ManagerRecipe                          = null
  var researchManager                       : ManagerResearch                        = null
  var assistantManager                      : ManagerAssistant                       = null
  var soundManager                          : FemtocraftSoundManager                 = null
  var femtocraftServerCommand               : CommandBase                            = null
  /*blocks*/
  var blockOreTitanium                      : Block                                  = null
  var blockOrePlatinum                      : Block                                  = null
  var blockOreThorium                       : Block                                  = null
  var blockOreFarenite                      : Block                                  = null
  var blockOreMalenite                      : Block                                  = null
  var blockOreLodestone                     : Block                                  = null
  var microStone                            : Block                                  = null
  var nanoStone                             : Block                                  = null
  var femtoStone                            : Block                                  = null
  var unidentifiedAlloy                     : Block                                  = null
  var blockResearchComputer                 : Block                                  = null
  var blockResearchConsole                  : Block                                  = null
  var generatorTest                         : Block                                  = null
  var consumerTest                          : Block                                  = null
  var blockMicroFurnaceUnlit                : Block                                  = null
  var blockMicroFurnaceLit                  : Block                                  = null
  var blockMicroDeconstructor               : Block                                  = null
  var blockMicroReconstructor               : Block                                  = null
  var blockEncoder                          : Block                                  = null
  var blockNanoInnervatorUnlit              : Block                                  = null
  var blockNanoInnervatorLit                : Block                                  = null
  var blockNanoDismantler                   : Block                                  = null
  var blockNanoFabricator                   : Block                                  = null
  var blockNanoEnmesher                     : Block                                  = null
  var blockNanoHorologe                     : Block                                  = null
  var blockFemtoImpulserLit                 : Block                                  = null
  var blockFemtoImpulserUnlit               : Block                                  = null
  var blockFemtoRepurposer                  : Block                                  = null
  var blockFemtoCoagulator                  : Block                                  = null
  var blockFemtoEntangler                   : Block                                  = null
  var blockFemtoChronoshifter               : Block                                  = null
  var blockMicroCube                        : Block                                  = null
  var blockNanoCubeFrame                    : Block                                  = null
  var blockNanoCubePort                     : Block                                  = null
  var blockFemtoCubePort                    : Block                                  = null
  var blockFemtoCubeFrame                   : Block                                  = null
  var blockFemtoCubeChassis                 : Block                                  = null
  var blockVacuumTube                       : Block                                  = null
  var blockSuctionPipe                      : Block                                  = null
  var blockMicroChargingBase                : Block                                  = null
  var blockMicroChargingCoil                : Block                                  = null
  var blockMicroChargingCapacitor           : Block                                  = null
  var blockMagneticInductionGenerator       : Block                                  = null
  var blockOrbitalEqualizer                 : Block                                  = null
  var blockNullEqualizer                    : Block                                  = null
  var blockCryoEndothermalChargingBase      : Block                                  = null
  var blockCryoEndothermalChargingCoil      : Block                                  = null
  var blockFissionReactorCore               : Block                                  = null
  var blockFissionReactorHousing            : Block                                  = null
  var blockMagnetohydrodynamicGenerator     : Block                                  = null
  var blockSteamGenerator                   : Block                                  = null
  var blockDecontaminationChamber           : Block                                  = null
  var blockPhlegethonTunnelCore             : Block                                  = null
  var blockPhlegethonTunnelFrame            : Block                                  = null
  var blockSisyphusStabilizer               : Block                                  = null
  var blockStellaratorCore                  : Block                                  = null
  var blockStellaratorFocus                 : Block                                  = null
  var blockStellaratorOpticalMaser          : Block                                  = null
  var blockStellaratorHousing               : Block                                  = null
  var blockPlasmaConduit                    : Block                                  = null
  var blockPlasmaVent                       : Block                                  = null
  var blockPlasmaTurbine                    : Block                                  = null
  var blockPlasmaCondenser                  : Block                                  = null
  var blockMicroCable                       : Block                                  = null
  var blockNanoCable                        : Block                                  = null
  var blockFemtoCable                       : Block                                  = null
  var fluidMass                             : Fluid                                  = null
  var blockFluidMass                        : BlockFluidMass                         = null
  var fluidMoltenSalt                       : Fluid                                  = null
  var blockFluidMoltenSalt                  : BlockFluidMoltenSalt                   = null
  var fluidCooledMoltenSalt                 : Fluid                                  = null
  var blockFluidCooledMoltenSalt            : BlockFluidCooledMoltenSalt             = null
  var fluidCooledContaminatedMoltenSalt     : Fluid                                  = null
  var blockFluidCooledContaminatedMoltenSalt: BlockFluidCooledContaminatedMoltenSalt = null
  var blockPlasma                           : Block                                  = null
  var itemIngotTitanium                     : Item                                   = null
  var itemIngotPlatinum                     : Item                                   = null
  var itemIngotThorium                      : Item                                   = null
  var itemIngotFarenite                     : Item                                   = null
  var itemIngotMalenite                     : Item                                   = null
  var itemIngotTemperedTitanium             : Item                                   = null
  var itemIngotThFaSalt                     : Item                                   = null
  var itemNuggetLodestone                   : Item                                   = null
  var itemChunkLodestone                    : Item                                   = null
  var itemConductivePowder                  : Item                                   = null
  var itemBoard                             : Item                                   = null
  var itemPrimedBoard                       : Item                                   = null
  var itemDopedBoard                        : Item                                   = null
  var itemMicrochip                         : Item                                   = null
  var itemSpool                             : Item                                   = null
  var itemSpoolGold                         : Item                                   = null
  var itemSpoolPlatinum                     : Item                                   = null
  var itemMicroCoil                         : Item                                   = null
  var itemBattery                           : Item                                   = null
  var itemMicroPlating                      : Item                                   = null
  var itemMicroLogicCore                    : Item                                   = null
  var itemKineticPulverizer                 : Item                                   = null
  var itemHeatingElement                    : Item                                   = null
  var itemArticulatingArm                   : Item                                   = null
  var itemDissassemblyArray                 : Item                                   = null
  var itemAssemblyArray                     : Item                                   = null
  var itemVacuumCore                        : Item                                   = null
  var itemNucleationCore                    : Item                                   = null
  var itemInhibitionCore                    : Item                                   = null
  var itemPortableResearchComputer          : Item                                   = null
  var itemMicroTechnology                   : Item                                   = null
  var itemNanoTechnology                    : Item                                   = null
  var itemFemtoTechnology                   : Item                                   = null
  var itemPaperSchematic                    : Item                                   = null
  var itemInterfaceDeviceMicro              : Item                                   = null
  var itemNanochip                          : Item                                   = null
  var itemNanoCalculator                    : Item                                   = null
  var itemNanoRegulator                     : Item                                   = null
  var itemNanoSimulator                     : Item                                   = null
  var itemBasicAICore                       : Item                                   = null
  var itemLearningCore                      : Item                                   = null
  var itemSchedulerCore                     : Item                                   = null
  var itemManagerCore                       : Item                                   = null
  var itemFluidicConductor                  : Item                                   = null
  var itemNanoCoil                          : Item                                   = null
  var itemNanoPlating                       : Item                                   = null
  var itemTemporalResonator                 : Item                                   = null
  var itemDimensionalMonopole               : Item                                   = null
  var itemSelfFulfillingOracle              : Item                                   = null
  var itemCrossDimensionalCommunicator      : Item                                   = null
  var itemInfallibleEstimator               : Item                                   = null
  var itemPanLocationalComputer             : Item                                   = null
  var itemPandoraCube                       : Item                                   = null
  var itemFissionReactorPlating             : Item                                   = null
  var itemDigitalSchematic                  : Item                                   = null
  var itemInterfaceDeviceNano               : Item                                   = null
  var itemMinosGate                         : Item                                   = null
  var itemCharosGate                        : Item                                   = null
  var itemCerberusGate                      : Item                                   = null
  var itemErinyesCircuit                    : Item                                   = null
  var itemMinervaComplex                    : Item                                   = null
  var itemAtlasMount                        : Item                                   = null
  var itemHermesBus                         : Item                                   = null
  var itemHerculesDrive                     : Item                                   = null
  var itemOrpheusProcessor                  : Item                                   = null
  var itemFemtoPlating                      : Item                                   = null
  var itemStyxValve                         : Item                                   = null
  var itemFemtoCoil                         : Item                                   = null
  var itemPhlegethonTunnelPrimer            : Item                                   = null
  var itemStellaratorPlating                : Item                                   = null
  var itemInfinitelyRecursiveALU            : Item                                   = null
  var itemInfiniteVolumePolychora           : Item                                   = null
  var itemQuantumSchematic                  : Item                                   = null
  var itemInterfaceDeviceFemto              : Item                                   = null
  var itemCubit                             : Item                                   = null
  var itemRectangulon                       : Item                                   = null
  var itemPlaneoid                          : Item                                   = null
  var itemCrystallite                       : Item                                   = null
  var itemMineralite                        : Item                                   = null
  var itemMetallite                         : Item                                   = null
  var itemFaunite                           : Item                                   = null
  var itemElectrite                         : Item                                   = null
  var itemFlorite                           : Item                                   = null
  var itemMicroCrystal                      : Item                                   = null
  var itemProteinChain                      : Item                                   = null
  var itemNerveCluster                      : Item                                   = null
  var itemConductiveAlloy                   : Item                                   = null
  var itemMetalComposite                    : Item                                   = null
  var itemFibrousStrand                     : Item                                   = null
  var itemMineralLattice                    : Item                                   = null
  var itemFungalSpores                      : Item                                   = null
  var itemIonicChunk                        : Item                                   = null
  var itemReplicatingMaterial               : Item                                   = null
  var itemSpinyFilament                     : Item                                   = null
  var itemHardenedBulb                      : Item                                   = null
  var itemMorphicChannel                    : Item                                   = null
  var itemSynthesizedFiber                  : Item                                   = null
  var itemOrganometallicPlate               : Item                                   = null

  @EventHandler def preInit(event: FMLPreInitializationEvent) {
    logger = Logger.getLogger(ID)
    logger.setParent(FMLLog.getLogger)
    recipeManager = new ManagerRecipe
    researchManager = new ManagerResearch
    assistantManager = new ManagerAssistant
    femtocraftServerCommand = new CommandFemtocraft
    val suggestedConfig: File = event.getSuggestedConfigurationFile
    configFile = new Configuration(suggestedConfig)
    FemtocraftConfigs.load(configFile)
    val suggestConfigName: Array[String] = suggestedConfig.getName.split("\\.")
    technologyConfigFile = new File(suggestedConfig.getParentFile,
                                    suggestConfigName(0) + TECH_CONFIG_APPEND +
                                      "." + suggestConfigName(1))
    recipeConfigFile = new File(suggestedConfig.getParentFile,
                                suggestConfigName(0) + RECIPE_CONFIG_APPEND +
                                  "." + suggestConfigName(1))
    recipeConfig = new Configuration(recipeConfigFile)
    Femtocraft.proxy.registerTileEntities()
    Femtocraft.proxy.registerRendering()
    Femtocraft.proxy.registerBlockRenderers()
    Femtocraft.proxy.registerTickHandlers()
    if (event.getSide eq Side.CLIENT) {
      soundManager = new FemtocraftSoundManager
      MinecraftForge.EVENT_BUS.register(soundManager)
    }
    GameRegistry.registerPlayerTracker(new FemtocraftPlayerTracker)
    NetworkRegistry.instance.registerGuiHandler(this, new FemtocraftGuiHandler)
    NetworkRegistry.instance
    .registerConnectionHandler(new FemtocraftConnectionHandler)
    MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer)
    MinecraftForge.EVENT_BUS.register(new FemtocraftOreRetrogenHandler)
    proxy.registerRendering()

    if (FemtocraftConfigs.worldGen) {
      GameRegistry.registerWorldGenerator(new FemtocraftOreGenerator)
    }

    LanguageRegistry.instance
    .addStringLocalization("itemGroup.Femtocraft", "en_US", "Femtocraft")
    blockOreTitanium = new BlockOreTitanium(FemtocraftConfigs
                                            .BlockOreTitaniumID)
                       .setUnlocalizedName("BlockOreTitanium")
    MinecraftForge.setBlockHarvestLevel(blockOreTitanium, "pickaxe", 2)
    GameRegistry.registerBlock(blockOreTitanium, "BlockOreTitanium")
    LanguageRegistry.addName(blockOreTitanium, "Titanium Ore")
    if (FemtocraftConfigs.registerTitaniumOreInOreDictionary) {
      OreDictionary.registerOre("oreTitanium", new ItemStack(blockOreTitanium))
    }
    blockOrePlatinum = new BlockOrePlatinum(FemtocraftConfigs
                                            .BlockOrePlatinumID)
                       .setUnlocalizedName("BlockOrePlatinum")
    MinecraftForge.setBlockHarvestLevel(blockOrePlatinum, "pickaxe", 2)
    GameRegistry.registerBlock(blockOrePlatinum, "BlockOrePlatinum")
    LanguageRegistry.addName(blockOrePlatinum, "Platinum Ore")
    if (FemtocraftConfigs.registerPlatinumOreInOreDictionary) {
      OreDictionary.registerOre("orePlatinum", new ItemStack(blockOrePlatinum))
    }
    blockOreThorium = new BlockOreThorium(FemtocraftConfigs.BlockOreThoriumID)
                      .setUnlocalizedName("BlockOreThorium")
    MinecraftForge.setBlockHarvestLevel(blockOreThorium, "pickaxe", 2)
    GameRegistry.registerBlock(blockOreThorium, "BlockOreThorium")
    LanguageRegistry.addName(blockOreThorium, "Thorium Ore")
    if (FemtocraftConfigs.registerThoriumOreInOreDictionary) {
      OreDictionary.registerOre("oreThorium", new ItemStack(blockOreThorium))
    }
    blockOreFarenite = new BlockOreFarenite(FemtocraftConfigs
                                            .BlockOreFareniteID)
                       .setUnlocalizedName("BlockOreFarenite")
    MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 2)
    GameRegistry.registerBlock(blockOreFarenite, "BlockOreFarenite")
    LanguageRegistry.addName(blockOreFarenite, "Farenite Ore")
    if (FemtocraftConfigs.registerFareniteOreInOreDictionary) {
      OreDictionary.registerOre("oreFarenite", new ItemStack(blockOreFarenite))
    }
    blockOreMalenite = new BlockOreMalenite(FemtocraftConfigs
                                            .BlockOreMaleniteID)
                       .setUnlocalizedName("BlockOreMalenite")
    MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 3)
    GameRegistry.registerBlock(blockOreMalenite, "BlockOreMalenite")
    LanguageRegistry.addName(blockOreMalenite, "Malenite Ore")
    if (FemtocraftConfigs.registerMaleniteOreInOreDictionary) {
      OreDictionary.registerOre("oreMalenite", new ItemStack(blockOreMalenite))
    }
    blockOreLodestone = new BlockOreLodestone(FemtocraftConfigs
                                              .BlockOreLodestoneID)
                        .setUnlocalizedName("BlockOreLodestone")
    MinecraftForge.setBlockHarvestLevel(blockOreLodestone, "pickaxe", 2)
    GameRegistry.registerBlock(blockOreLodestone, "BlockOreLodestone")
    LanguageRegistry.addName(blockOreLodestone, "Lodestone Ore")
    if (FemtocraftConfigs.registerLodestoneOreInOreDictionary) {
      OreDictionary
      .registerOre("oreLodestone", new ItemStack(blockOreLodestone))
    }
    nanoStone = new BlockNanoStone(FemtocraftConfigs.BlockNanoStoneID)
                .setUnlocalizedName("BlockNanoStone")
    GameRegistry.registerBlock(nanoStone, "BlockNanoStone")
    LanguageRegistry.addName(nanoStone, "Nanostone")
    microStone = new BlockMicroStone(FemtocraftConfigs.BlockMicroStoneID)
                 .setUnlocalizedName("BlockMicroStone")
    GameRegistry.registerBlock(microStone, "BlockMicroStone")
    LanguageRegistry.addName(microStone, "Microstone")
    femtoStone = new BlockFemtoStone(FemtocraftConfigs.BlockFemtoStoneID)
                 .setUnlocalizedName("BlockFemtoStone")
    GameRegistry.registerBlock(femtoStone, "BlockFemtoStone")
    LanguageRegistry.addName(femtoStone, "Femtostone")
    unidentifiedAlloy = new BlockUnidentifiedAlloy(FemtocraftConfigs
                                                   .BlockUnidentifiedAlloyID)
                        .setUnlocalizedName("BlockUnidentifiedAlloy")
    GameRegistry.registerBlock(unidentifiedAlloy, "BlockUnidentifiedAlloy")
    LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy")
    blockResearchComputer = new BlockResearchComputer(FemtocraftConfigs
                                                      .BlockResearchComputerID)
                            .setUnlocalizedName("BlockResearchComputer")
    GameRegistry.registerBlock(blockResearchComputer, "BlockResearchComputer")
    LanguageRegistry.addName(blockResearchComputer, "Research Computer")
    blockResearchConsole = new BlockResearchConsole(FemtocraftConfigs
                                                    .BlockResearchConsoleID)
                           .setUnlocalizedName("BlockResearchConsole")
    GameRegistry.registerBlock(blockResearchConsole, "BlockResearchConsole")
    LanguageRegistry.addName(blockResearchConsole, "Research Console")
    blockMicroCable = new BlockMicroCable(FemtocraftConfigs.BlockMicroCableID,
                                          Material.iron)
                      .setUnlocalizedName("BlockMicroCable")
    GameRegistry.registerBlock(blockMicroCable, "BlockMicroCable")
    LanguageRegistry.addName(blockMicroCable, "Micro-Cable")
    blockNanoCable = new BlockNanoCable(FemtocraftConfigs.BlockNanoCableID,
                                        Material.iron)
                     .setUnlocalizedName("BlockNanoCable")
    GameRegistry.registerBlock(blockNanoCable, "BlockNanoCable")
    LanguageRegistry.addName(blockNanoCable, "Nano-Cable")
    blockFemtoCable = new BlockFemtoCable(FemtocraftConfigs.BlockFemtoCableID,
                                          Material.iron)
                      .setUnlocalizedName("BlockFemtoCable")
    GameRegistry.registerBlock(blockFemtoCable, "BlockFemtoCable")
    LanguageRegistry.addName(blockFemtoCable, "Femto-Cable")
    generatorTest = new BlockGenerator(FemtocraftConfigs.BlockGeneratorTestID,
                                       Material.iron)
                    .setUnlocalizedName("BlockGenerator").setHardness(3.5f)
                    .setStepSound(Block.soundStoneFootstep)
    GameRegistry.registerBlock(generatorTest, "BlockGenerator")
    LanguageRegistry.addName(generatorTest, "Generator")
    consumerTest = new BlockConsumer(FemtocraftConfigs.BlockConsumerTestID,
                                     Material.iron)
                   .setUnlocalizedName("BlockConsumer").setHardness(3.5f)
                   .setStepSound(Block.soundStoneFootstep)
    GameRegistry.registerBlock(consumerTest, "BlockConsumer")
    LanguageRegistry.addName(consumerTest, "Consumer")
    blockMicroFurnaceUnlit = new BlockMicroFurnace(FemtocraftConfigs
                                                   .BlockMicroFurnaceUnlitID,
                                                   false)
                             .setUnlocalizedName("BlockMicroFurnace")
    GameRegistry.registerBlock(blockMicroFurnaceUnlit, "BlockMicroFurnace")
    LanguageRegistry.addName(blockMicroFurnaceUnlit, "Micro-Furnace")
    blockMicroFurnaceLit = new BlockMicroFurnace(FemtocraftConfigs
                                                 .BlockMicroFurnaceLitID, true)
    blockMicroDeconstructor = new BlockMicroDeconstructor(FemtocraftConfigs
                                                          .BlockMicroDeconstructorID)
                              .setUnlocalizedName("BlockMicroDeconstructor")
    GameRegistry
    .registerBlock(blockMicroDeconstructor, "BlockMicroDeconstructor")
    LanguageRegistry.addName(blockMicroDeconstructor, "Microtech Deconstructor")
    blockMicroReconstructor = new BlockMicroReconstructor(FemtocraftConfigs
                                                          .BlockMicroReconstructorID)
                              .setUnlocalizedName("BlockMicroReconstructor")
    GameRegistry
    .registerBlock(blockMicroReconstructor, "BlockMicroReconstructor")
    LanguageRegistry.addName(blockMicroReconstructor, "Microtech Reconstructor")
    blockEncoder = new BlockEncoder(FemtocraftConfigs.BlockMicroEncoderID)
                   .setUnlocalizedName("BlockEncoder")
    GameRegistry.registerBlock(blockEncoder, "BlockEncoder")
    LanguageRegistry.addName(blockEncoder, "Schematic Encoder")
    blockNanoInnervatorUnlit = new BlockNanoInnervator(FemtocraftConfigs
                                                       .BlockNanoInnervatorUnlitID,
                                                       false)
                               .setUnlocalizedName("BlockNanoInnervator")
    GameRegistry.registerBlock(blockNanoInnervatorUnlit, "BlockNanoInnervator")
    LanguageRegistry.addName(blockNanoInnervatorUnlit, "Nano Innervator")
    blockNanoInnervatorLit = new BlockNanoInnervator(FemtocraftConfigs
                                                     .BlockNanoInnervatorLitID,
                                                     true)
    blockNanoDismantler = new BlockNanoDismantler(FemtocraftConfigs
                                                  .BlockNanoDismantlerID)
                          .setUnlocalizedName("BlockNanoDismantler")
    GameRegistry.registerBlock(blockNanoDismantler, "BlockNanoDismantler")
    LanguageRegistry.addName(blockNanoDismantler, "Nano Dismantler")
    blockNanoFabricator = new BlockNanoFabricator(FemtocraftConfigs
                                                  .BlockNanoFabricatorID)
                          .setUnlocalizedName("BlockNanoFabricator")
    GameRegistry.registerBlock(blockNanoFabricator, "BlockNanoFabricator")
    LanguageRegistry.addName(blockNanoFabricator, "Nano Fabricator")
    blockNanoEnmesher = new BlockNanoEnmesher(FemtocraftConfigs
                                              .BlockNanoEnmesherID)
                        .setUnlocalizedName("BlockNanoEnmesher")
    GameRegistry.registerBlock(blockNanoEnmesher, "BlockNanoEnmesher")
    LanguageRegistry.addName(blockNanoEnmesher, "Nano Enmesher")
    blockNanoHorologe = new BlockNanoHorologe(FemtocraftConfigs
                                              .BlockNanoHorologeID)
                        .setUnlocalizedName("BlockNanoHorologe")
    GameRegistry.registerBlock(blockNanoHorologe, "BlockNanoHorologe")
    LanguageRegistry.addName(blockNanoHorologe, "Nano Horologe")
    blockFemtoImpulserUnlit = new BlockFemtoImpulser(FemtocraftConfigs
                                                     .BlockFemtoImpulserUnlitID,
                                                     false)
                              .setUnlocalizedName("BlockFemtoImpulser")
    GameRegistry.registerBlock(blockFemtoImpulserUnlit, "BlockFemtoImpulser")
    LanguageRegistry.addName(blockFemtoImpulserUnlit, "Femto Impulser")
    blockFemtoImpulserLit = new BlockFemtoImpulser(FemtocraftConfigs
                                                   .BlockFemtoImpulserLitID,
                                                   true)
    blockFemtoRepurposer = new BlockFemtoRepurposer(FemtocraftConfigs
                                                    .BlockFemtoRepurposerID)
                           .setUnlocalizedName("BlockFemtoRepurposer")
    GameRegistry.registerBlock(blockFemtoRepurposer, "BlockFemtoRepurposer")
    LanguageRegistry.addName(blockFemtoRepurposer, "Femto Repurposer")
    blockFemtoCoagulator = new BlockFemtoCoagulator(FemtocraftConfigs
                                                    .BlockFemtoCoagulatorID)
                           .setUnlocalizedName("BlockFemtoCoagulator")
    GameRegistry.registerBlock(blockFemtoCoagulator, "BlockFemtoCoagulator")
    LanguageRegistry.addName(blockFemtoCoagulator, "Femto Coagulator")
    blockFemtoEntangler = new BlockFemtoEntangler(FemtocraftConfigs
                                                  .BlockFemtoEntanglerID)
                          .setUnlocalizedName("BlockFemtoEntangler")
    GameRegistry.registerBlock(blockFemtoEntangler, "BlockFemtoEntangler")
    LanguageRegistry.addName(blockFemtoEntangler, "Femto Entangler")
    blockFemtoChronoshifter = new BlockFemtoChronoshifter(FemtocraftConfigs
                                                          .BlockFemtoChronoshifterID)
                              .setUnlocalizedName("BlockFemtoChronoshifter")
    GameRegistry
    .registerBlock(blockFemtoChronoshifter, "BlockFemtoChronoshifter")
    LanguageRegistry.addName(blockFemtoChronoshifter, "Femto Chronoshifter")
    blockMicroCube = new BlockMicroCube(FemtocraftConfigs.BlockMicroCubeID)
                     .setUnlocalizedName("BlockMicroCube")
    GameRegistry.registerBlock(blockMicroCube, classOf[ItemBlockMicroCube],
                               "BlockMicroCube")
    LanguageRegistry.addName(blockMicroCube, "Micro-Cube")
    blockNanoCubeFrame = new BlockNanoCubeFrame(FemtocraftConfigs
                                                .BlockNanoCubeFrameID)
                         .setUnlocalizedName("BlockNanoCubeFrame")
    GameRegistry.registerBlock(blockNanoCubeFrame, "BlockNanoCubeFrame")
    LanguageRegistry.addName(blockNanoCubeFrame, "Nano-Cube Frame")
    blockNanoCubePort = new BlockNanoCubePort(FemtocraftConfigs
                                              .BlockNanoCubePortID)
                        .setUnlocalizedName("BlockNanoCubePort")
    GameRegistry.registerBlock(blockNanoCubePort, "BlockNanoCubePort")
    LanguageRegistry.addName(blockNanoCubePort, "Nano-Cube Port")
    blockFemtoCubePort = new BlockFemtoCubePort(FemtocraftConfigs
                                                .BlockFemtoCubePortID)
                         .setUnlocalizedName("BlockFemtoCubePort")
    GameRegistry.registerBlock(blockFemtoCubePort, "BlockFemtoCubePort")
    LanguageRegistry.addName(blockFemtoCubePort, "Femto-Cube Port")
    blockFemtoCubeFrame = new BlockFemtoCubeFrame(FemtocraftConfigs
                                                  .BlockFemtoCubeFrameID)
                          .setUnlocalizedName("BlockFemtoCubeFrame")
    GameRegistry.registerBlock(blockFemtoCubeFrame, "BlockFemtoCubeFrame")
    LanguageRegistry.addName(blockFemtoCubeFrame, "Femto-Cube Frame")
    blockFemtoCubeChassis = new BlockFemtoCubeChassis(FemtocraftConfigs
                                                      .BlockFemtoCubeChassisID)
                            .setUnlocalizedName("BlockFemtoCubeChassis")
    GameRegistry.registerBlock(blockFemtoCubeChassis, "BlockFemtoCubeChassis")
    LanguageRegistry.addName(blockFemtoCubeChassis, "Femto-Cube Chassis")
    blockVacuumTube = new BlockVacuumTube(FemtocraftConfigs.BlockVacuumTubeID)
                      .setUnlocalizedName("BlockVacuumTube")
    GameRegistry.registerBlock(blockVacuumTube, "BlockVacuumTube")
    LanguageRegistry.addName(blockVacuumTube, "Vacuum Tube")
    blockSuctionPipe = new BlockSuctionPipe(FemtocraftConfigs
                                            .BlockSuctionPipeID)
                       .setUnlocalizedName("BlockSuctionPipe")
    GameRegistry.registerBlock(blockSuctionPipe, "BlockSuctionPipe")
    LanguageRegistry.addName(blockSuctionPipe, "Suction Pipe")
    blockMicroChargingBase = new BlockAtmosphericChargingBase(FemtocraftConfigs
                                                              .BlockMicroChargingBaseID)
                             .setUnlocalizedName("BlockBaseMicroCharging")
    GameRegistry.registerBlock(blockMicroChargingBase, "BlockBaseMicroCharging")
    LanguageRegistry
    .addName(blockMicroChargingBase, "Electrostatic Charging Base")
    blockMicroChargingCoil = new BlockAtmosphericChargingCoil(FemtocraftConfigs
                                                              .BlockMicroChargingCoilID)
                             .setUnlocalizedName("BlockCoilMicroCharging")
    GameRegistry.registerBlock(blockMicroChargingCoil, "BlockCoilMicroCharging")
    LanguageRegistry
    .addName(blockMicroChargingCoil, "Electrostatic Charging Coil")
    blockMicroChargingCapacitor = new BlockAtmosphericChargingCapacitor(FemtocraftConfigs
                                                                        .BlockMicroChargingCapacitorID)
                                  .setUnlocalizedName(
        "BlockAtmosphericChargingCapacitor")
    GameRegistry.registerBlock(blockMicroChargingCapacitor,
                               "BlockAtmosphericChargingCapacitor")
    LanguageRegistry
    .addName(blockMicroChargingCapacitor, "Electrostatic Charging Capacitor")
    blockMagneticInductionGenerator = new BlockMagneticInductionGenerator(FemtocraftConfigs
                                                                          .BlockMagneticInductionGeneratorID)
                                      .setUnlocalizedName(
        "BlockMagneticInductionGenerator")
    GameRegistry.registerBlock(blockMagneticInductionGenerator,
                               "BlockMagneticInductionGenerator")
    LanguageRegistry
    .addName(blockMagneticInductionGenerator, "Magnetic Induction Generator")
    blockOrbitalEqualizer = new BlockOrbitalEqualizer(FemtocraftConfigs
                                                      .BlockOrbitalEqualizerID)
                            .setUnlocalizedName("BlockOrbitalEqualizer")
    GameRegistry.registerBlock(blockOrbitalEqualizer, "BlockOrbitalEqualizer")
    LanguageRegistry.addName(blockOrbitalEqualizer, "Orbital Equalizer")
    blockCryoEndothermalChargingBase = new BlockCryoEndothermalChargingBase(FemtocraftConfigs
                                                                            .BlockCryoEndothermalChargingBaseID)
                                       .setUnlocalizedName(
        "BlockCryoEndothermalChargingBase")
    GameRegistry.registerBlock(blockCryoEndothermalChargingBase,
                               "BlockCryoEndothermalChargingBase")
    LanguageRegistry
    .addName(blockCryoEndothermalChargingBase, "CryoEndothermal Charging Base")
    blockCryoEndothermalChargingCoil = new BlockCryoEndothermalChargingCoil(FemtocraftConfigs
                                                                            .BlockCryoEndothermalChargingCoilID)
                                       .setUnlocalizedName(
        "BlockCryoEndothermalChargingCoil")
    GameRegistry.registerBlock(blockCryoEndothermalChargingCoil,
                               "BlockCryoEndothermalChargingCoil")
    LanguageRegistry
    .addName(blockCryoEndothermalChargingCoil, "CryoEndothermal Charging Coil")
    blockFissionReactorCore = new BlockNanoFissionReactorCore(FemtocraftConfigs
                                                              .BlockFissionReactorCoreID)
                              .setUnlocalizedName("BlockFissionReactorCore")
    GameRegistry
    .registerBlock(blockFissionReactorCore, "BlockFissionReactorCore")
    LanguageRegistry.addName(blockFissionReactorCore, "Fission Reactor Core")
    blockFissionReactorHousing = new BlockNanoFissionReactorHousing(FemtocraftConfigs
                                                                    .BlockFissionReactorHousingID)
                                 .setUnlocalizedName(
        "BlockFissionReactorHousing")
    GameRegistry
    .registerBlock(blockFissionReactorHousing, "BlockFissionReactorHousing")
    LanguageRegistry
    .addName(blockFissionReactorHousing, "Fission Reactor Housing")
    blockMagnetohydrodynamicGenerator = new BlockMagnetohydrodynamicGenerator(FemtocraftConfigs
                                                                              .BlockMagnetohydrodynamicGeneratorID)
                                        .setUnlocalizedName(
        "BlockMagnetohydrodynamicGenerator")
    GameRegistry.registerBlock(blockMagnetohydrodynamicGenerator,
                               "BlockMagnetohydrodynamicGenerator")
    LanguageRegistry
    .addName(blockMagnetohydrodynamicGenerator, "Magnetohydrodynamic Generator")
    blockSteamGenerator = new BlockSteamGenerator(FemtocraftConfigs
                                                  .BlockSteamGeneratorID)
                          .setUnlocalizedName("BlockSteamGenerator")
    GameRegistry.registerBlock(blockSteamGenerator, "BlockSteamGenerator")
    LanguageRegistry.addName(blockSteamGenerator, "Steam Generator")
    blockDecontaminationChamber = new BlockDecontaminationChamber(FemtocraftConfigs
                                                                  .BlockDecontaminationChamberID)
                                  .setUnlocalizedName(
        "BlockDecontaminationChamber")
    GameRegistry
    .registerBlock(blockDecontaminationChamber, "BlockDecontaminationChamber")
    LanguageRegistry
    .addName(blockDecontaminationChamber, "Decontamination Chamber")
    blockPhlegethonTunnelCore = new BlockPhlegethonTunnelCore(FemtocraftConfigs
                                                              .BlockPhlegethonTunnelCoreID)
                                .setUnlocalizedName("BlockPhlegethonTunnelCore")
    GameRegistry
    .registerBlock(blockPhlegethonTunnelCore, "BlockPhlegethonTunnelCore")
    LanguageRegistry
    .addName(blockPhlegethonTunnelCore, "Phlegethon Tunnel Core")
    blockPhlegethonTunnelFrame = new BlockPhlegethonTunnelFrame(FemtocraftConfigs
                                                                .BlockPhlegethonTunnelFrameID)
                                 .setUnlocalizedName(
        "BlockPhlegethonTunnelFrame")
    GameRegistry
    .registerBlock(blockPhlegethonTunnelFrame, "BlockPhlegethonTunnelFrame")
    LanguageRegistry
    .addName(blockPhlegethonTunnelFrame, "Phlegethon Tunnel Frame")
    blockSisyphusStabilizer = new BlockSisyphusStabilizer(FemtocraftConfigs
                                                          .BlockSisyphusStabilizerID)
                              .setUnlocalizedName("BlockSisyphusStabilizer")
    GameRegistry
    .registerBlock(blockSisyphusStabilizer, "BlockSisyphusStabilizer")
    LanguageRegistry.addName(blockSisyphusStabilizer, "Sisyphus Stabilizer")
    blockNullEqualizer = new BlockNullEqualizer(FemtocraftConfigs
                                                .BlockNullEqualizerID)
                         .setUnlocalizedName("BlockNullEqualizer")
    GameRegistry.registerBlock(blockNullEqualizer, "BlockNullEqualizer")
    LanguageRegistry.addName(blockNullEqualizer, "Null-Energy Equalizer")
    blockStellaratorCore = new BlockFemtoStellaratorCore(FemtocraftConfigs
                                                         .BlockFemtoStelleratorCoreID)
                           .setUnlocalizedName("BlockStellaratorCore")
    GameRegistry.registerBlock(blockStellaratorCore, "BlockStellaratorCore")
    LanguageRegistry.addName(blockStellaratorCore, "Stellarator Core")
    blockStellaratorFocus = new BlockFemtoStellaratorFocus(FemtocraftConfigs
                                                           .BlockFemtoStelleratorFocusID)
                            .setUnlocalizedName("BlockStellaratorFocus")
    GameRegistry.registerBlock(blockStellaratorFocus, "BlockStellaratorFocus")
    LanguageRegistry.addName(blockStellaratorFocus, "Stellarator Focus")
    blockStellaratorOpticalMaser = new BlockFemtoStellaratorOpticalMaser(FemtocraftConfigs
                                                                         .BlockFemtoStellaratorOpticalMaserID)
                                   .setUnlocalizedName(
        "BlockStellaratorOpticalMaser")
    GameRegistry
    .registerBlock(blockStellaratorOpticalMaser, "BlockStellaratorOpticalMaser")
    LanguageRegistry
    .addName(blockStellaratorOpticalMaser, "Stellarator Optical Maser")
    blockStellaratorHousing = new BlockFemtoStellaratorHousing(FemtocraftConfigs
                                                               .BlockFemtoStellaratorHousingID)
                              .setUnlocalizedName("BlockStellaratorHousing")
    GameRegistry
    .registerBlock(blockStellaratorHousing, "BlockStellaratorHousing")
    LanguageRegistry.addName(blockStellaratorHousing, "Stellarator Housing")
    blockPlasmaConduit = new BlockPlasmaConduit(FemtocraftConfigs
                                                .BlockPlasmaConduitID)
                         .setUnlocalizedName("BlockPlasmaConduit")
    GameRegistry.registerBlock(blockPlasmaConduit, "BlockPlasmaConduit")
    LanguageRegistry.addName(blockPlasmaConduit, "Plasma Conduit")
    blockPlasmaVent = new BlockPlasmaVent(FemtocraftConfigs.BlockPlasmaVentID)
                      .setUnlocalizedName("BlockPlasmaVent")
    GameRegistry.registerBlock(blockPlasmaVent, "BlockPlasmaVent")
    LanguageRegistry.addName(blockPlasmaVent, "Plasma Vent")
    blockPlasmaTurbine = new BlockPlasmaTurbine(FemtocraftConfigs
                                                .BlockPlasmaTurbineID)
                         .setUnlocalizedName("BlockPlasmaTurbine")
    GameRegistry.registerBlock(blockPlasmaTurbine, "BlockPlasmaTurbine")
    LanguageRegistry.addName(blockPlasmaTurbine, "Plasma Turbine")
    blockPlasmaCondenser = new BlockPlasmaCondenser(FemtocraftConfigs
                                                    .BlockPlasmaCondenserID)
                           .setUnlocalizedName("BlockPlasmaCondenser")
    GameRegistry.registerBlock(blockPlasmaCondenser, "BlockPlasmaCondenser")
    LanguageRegistry.addName(blockPlasmaCondenser, "Plasma Condenser")
    fluidMass = new FluidMass
    FluidRegistry.registerFluid(fluidMass)
    blockFluidMass = new BlockFluidMass(FemtocraftConfigs.BlockMassID)
    blockFluidMass.setUnlocalizedName("BlockMass")
    GameRegistry.registerBlock(blockFluidMass, "BlockMass")
    LanguageRegistry.addName(blockFluidMass, "Mass")
    fluidMoltenSalt = new FluidMoltenSalt
    FluidRegistry.registerFluid(fluidMoltenSalt)
    blockFluidMoltenSalt = new BlockFluidMoltenSalt(FemtocraftConfigs
                                                    .BlockFluidMoltenSaltID)
    blockFluidMoltenSalt.setUnlocalizedName("BlockFluidMoltenSalt")
    GameRegistry.registerBlock(blockFluidMoltenSalt, "BlockFluidMoltenSalt")
    LanguageRegistry.addName(blockFluidMoltenSalt, "Molten Salt")
    fluidCooledMoltenSalt = new FluidCooledMoltenSalt
    FluidRegistry.registerFluid(fluidCooledMoltenSalt)
    blockFluidCooledMoltenSalt = new BlockFluidCooledMoltenSalt(FemtocraftConfigs
                                                                .BlockFluidCooledMoltenSaltID)
    blockFluidCooledMoltenSalt.setUnlocalizedName("BlockFluidCooledMoltenSalt")
    GameRegistry
    .registerBlock(blockFluidCooledMoltenSalt, "BlockFluidCooledMoltenSalt")
    LanguageRegistry.addName(blockFluidCooledMoltenSalt, "Cooled Molten Salt")
    fluidCooledContaminatedMoltenSalt = new FluidCooledContaminatedMoltenSalt
    FluidRegistry.registerFluid(fluidCooledContaminatedMoltenSalt)
    blockFluidCooledContaminatedMoltenSalt = new
        BlockFluidCooledContaminatedMoltenSalt(FemtocraftConfigs
                                               .BlockFluidCooledContaminatedMoltenSaltID)
    blockFluidCooledContaminatedMoltenSalt
    .setUnlocalizedName("BlockFluidCooledContaminatedMoltenSalt")
    GameRegistry.registerBlock(blockFluidCooledContaminatedMoltenSalt,
                               "BlockFluidCooledContaminatedMoltenSalt")
    LanguageRegistry.addName(blockFluidCooledContaminatedMoltenSalt,
                             "Cooled Contaminated Molten Salt")
    blockPlasma = new BlockPlasma(FemtocraftConfigs.BlockPlasmaID)
    itemIngotTitanium = registerItem(FemtocraftConfigs.ItemIngotTitaniumID,
                                     "ItemIngotTitanium", "Titanium Ingot")
    if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) {
      OreDictionary
      .registerOre("ingotTitanium", new ItemStack(itemIngotTitanium))
    }
    itemIngotPlatinum = registerItem(FemtocraftConfigs.ItemIngotPlatinumID,
                                     "ItemIngotPlatinum", "Platinum Ingot")
    if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) {
      OreDictionary
      .registerOre("ingotPlatinum", new ItemStack(itemIngotPlatinum))
    }
    itemIngotThorium = registerItem(FemtocraftConfigs.ItemIngotThoriumID,
                                    "ItemIngotThorium", "Thorium Ingot")
    if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) {
      OreDictionary.registerOre("ingotThorium", new ItemStack(itemIngotThorium))
    }
    itemIngotFarenite = registerItem(FemtocraftConfigs.ItemIngotFareniteID,
                                     "ItemIngotFarenite", "Farenite")
    OreDictionary.registerOre("ingotFarenite", new ItemStack(itemIngotFarenite))
    itemIngotMalenite = registerItem(FemtocraftConfigs.ItemIngotMaleniteID,
                                     "ItemIngotMalenite", "Malenite")
    OreDictionary.registerOre("ingotMalenite", new ItemStack(itemIngotMalenite))
    itemIngotTemperedTitanium = registerItem(FemtocraftConfigs
                                             .ItemIngotTemperedTitaniumID,
                                             "ItemIngotTemperedTitanium",
                                             "Tempered Titanium Ingot")
    OreDictionary.registerOre("ingotTemperedTitanium",
                              new ItemStack(itemIngotTemperedTitanium))
    itemIngotThFaSalt = registerItem(FemtocraftConfigs.ItemIngotThFaSaltID,
                                     "ItemIngotThFaSalt", "ThFa Salt Ingot")
    OreDictionary.registerOre("ingotThFaSalt", new ItemStack(itemIngotThFaSalt))
    itemNuggetLodestone = registerItem(FemtocraftConfigs.ItemNuggetLodestoneID,
                                       "ItemNuggetLodestone",
                                       "Lodestone Nugget")
    OreDictionary
    .registerOre("nuggetLodestone", new ItemStack(itemNuggetLodestone))
    itemChunkLodestone = registerItem(FemtocraftConfigs.ItemChunkLodestoneID,
                                      "ItemChunkLodestone", "Lodestone Chunk")
    OreDictionary
    .registerOre("chunkLodestone", new ItemStack(itemChunkLodestone))
    itemConductivePowder = registerItem(FemtocraftConfigs
                                        .ItemConductivePowderID,
                                        "ItemConductivePowder",
                                        "Conductive Powder")
    itemBoard = registerItem(FemtocraftConfigs.ItemBoardID, "ItemBoard",
                             "Board")
    itemPrimedBoard = registerItem(FemtocraftConfigs.ItemPrimedBoardID,
                                   "ItemPrimedBoard", "Primed Board")
    itemDopedBoard = registerItem(FemtocraftConfigs.ItemDopedBoardID,
                                  "ItemDopedBoard", "Doped Board")
    itemMicrochip = registerItem(FemtocraftConfigs.ItemMicrochipID,
                                 "ItemMicrochip", "Microchip")
    itemSpool = registerItem(FemtocraftConfigs.ItemSpoolID, "ItemSpool",
                             "Spool")
    itemSpoolGold = registerItem(FemtocraftConfigs.ItemSpoolGoldID,
                                 "ItemSpoolGold", "Gold Wire Spool")
    itemSpoolPlatinum = registerItem(FemtocraftConfigs.ItemSpoolPlatinumID,
                                     "ItemSpoolPlatinum", "Platinum Wire Spool")
    itemMicroCoil = registerItem(FemtocraftConfigs.ItemMicroCoilID,
                                 "ItemMicroCoil", "Micro Coil")
    itemBattery = registerItem(FemtocraftConfigs.ItemBatteryID, "ItemBattery",
                               "Battery")
    itemArticulatingArm = registerItem(FemtocraftConfigs.ItemArticutingArmID,
                                       "ItemArticulatingArm",
                                       "Articulating Arm")
    itemDissassemblyArray = registerItem(FemtocraftConfigs
                                         .ItemDissassemblyArrayID,
                                         "ItemDissassemblyArray",
                                         "Dissassembly Array")
    itemAssemblyArray = registerItem(FemtocraftConfigs.ItemAssemblyArrayID,
                                     "ItemAssemblyArray", "Assembly Array")
    itemVacuumCore = registerItem(FemtocraftConfigs.ItemVacuumCoreID,
                                  "ItemVacuumCore", "Vacuum Core")
    itemMicroLogicCore = registerItem(FemtocraftConfigs.ItemMicroLogicCoreID,
                                      "ItemMicroLogicCore", "Micro Logic Core")
    itemKineticPulverizer = registerItem(FemtocraftConfigs
                                         .ItemKineticPulverizerID,
                                         "ItemKineticPulverizer",
                                         "Kinetic Pulverizer")
    itemHeatingElement = registerItem(FemtocraftConfigs.ItemHeatingCoilID,
                                      "ItemHeatingCoil", "Heating Coil")
    itemPortableResearchComputer = new ItemPortableResearchComputer(FemtocraftConfigs
                                                                    .ItemPortableResearchComputerID)
                                   .setUnlocalizedName(
        "ItemPortableResearchComputer")
    GameRegistry
    .registerItem(itemPortableResearchComputer, "ItemPortableResearchComputer")
    LanguageRegistry
    .addName(itemPortableResearchComputer, "Portable Research Computer")
    itemNanochip = registerItem(FemtocraftConfigs.ItemNanochipID,
                                "ItemNanochip", "Nanochip")
    itemNanoCalculator = registerItem(FemtocraftConfigs.ItemNanoCalculatorID,
                                      "ItemNanoCalculator", "Nano Calculator")
    itemNanoRegulator = registerItem(FemtocraftConfigs.ItemNanoRegulatorID,
                                     "ItemNanoRegulator", "Nano Regulator")
    itemNanoSimulator = registerItem(FemtocraftConfigs.ItemNanoSimulatorID,
                                     "ItemNanoSimulator", "Nano Simulator")
    itemBasicAICore = registerItem(FemtocraftConfigs.ItemBasicAICoreID,
                                   "ItemBasicAICore", "Basic AI Core")
    itemLearningCore = registerItem(FemtocraftConfigs.ItemLearningCoreID,
                                    "ItemLearningCore", "Learning Core")
    itemSchedulerCore = registerItem(FemtocraftConfigs.ItemSchedulerCoreID,
                                     "ItemSchedulerCore", "Scheduler Core")
    itemManagerCore = registerItem(FemtocraftConfigs.ItemManagerCoreID,
                                   "ItemManagerCore", "Manager Core")
    itemFluidicConductor = registerItem(FemtocraftConfigs
                                        .ItemFluidicConductorID,
                                        "ItemFluidicConductor",
                                        "Fluidic Conductor")
    itemNanoCoil = registerItem(FemtocraftConfigs.ItemNanoCoilID,
                                "ItemNanoCoil", "Nano Coil")
    itemNanoPlating = registerItem(FemtocraftConfigs.ItemNanoPlatingID,
                                   "ItemNanoPlating", "Nano Plating")
    itemTemporalResonator = registerItem(FemtocraftConfigs
                                         .ItemTemporalResonatorID,
                                         "ItemTemporalResonator",
                                         "Temporal Resonator")
    itemDimensionalMonopole = registerItem(FemtocraftConfigs
                                           .ItemDimensionalMonopoleID,
                                           "ItemDimensionalMonopole",
                                           "Dimensional Monopole")
    itemSelfFulfillingOracle = registerItem(FemtocraftConfigs
                                            .ItemSelfFulfillingOracleID,
                                            "ItemSelfFulfillingOracle",
                                            "Self Fulfilling Oracle")
    itemCrossDimensionalCommunicator = registerItem(FemtocraftConfigs
                                                    .ItemCrossDimensionalCommunicatorID,
                                                    "ItemCrossDimensionalCommunicator",
                                                    "Cross Dimensional " +
                                                      "Communicator")
    itemInfallibleEstimator = registerItem(FemtocraftConfigs
                                           .ItemInfallibleEstimatorID,
                                           "ItemInfallibleEstimator",
                                           "Infallible Estimator")
    itemPanLocationalComputer = registerItem(FemtocraftConfigs
                                             .ItemPanLocationalComputerID,
                                             "ItemPanLocationalComputer",
                                             "Pan Locational Computer")
    itemPandoraCube = registerItem(FemtocraftConfigs.ItemPandoraCubeID,
                                   "ItemPandoraCube", "Pandora Cube")
    itemFissionReactorPlating = registerItem(FemtocraftConfigs
                                             .ItemFissionReactorPlatingID,
                                             "ItemFissionReactorPlating",
                                             "Fission Reactor Plating")
    itemDigitalSchematic = new ItemDigitalSchematic(FemtocraftConfigs
                                                    .ItemDigitalSchematicID,
                                                    "ItemDigitalSchematic")
    GameRegistry.registerItem(itemDigitalSchematic, "ItemDigitalSchematic")
    LanguageRegistry.addName(itemDigitalSchematic, "Digital Schematic")
    itemMinosGate = registerItem(FemtocraftConfigs.ItemMinosGateID,
                                 "ItemMinosGate", "Minos Gate")
    itemCharosGate = registerItem(FemtocraftConfigs.ItemCharosGateID,
                                  "ItemCharosGate", "Charos Gate")
    itemCerberusGate = registerItem(FemtocraftConfigs.ItemCerberusGateID,
                                    "ItemCerberusGate", "Cerberus Gate")
    itemErinyesCircuit = registerItem(FemtocraftConfigs.ItemErinyesCircuitID,
                                      "ItemErinyesCircuit", "Erinyes Circuit")
    itemMinervaComplex = registerItem(FemtocraftConfigs.ItemMinervaComplexID,
                                      "ItemMinervaComplex", "Minerva Complex")
    itemAtlasMount = registerItem(FemtocraftConfigs.ItemAtlasMountID,
                                  "ItemAtlasMount", "Atlas Mount")
    itemHermesBus = registerItem(FemtocraftConfigs.ItemHermesBusID,
                                 "ItemHermesBus", "Hermes Bus")
    itemHerculesDrive = registerItem(FemtocraftConfigs.ItemHerculesDriveID,
                                     "ItemHerculesDrive", "Hercules Drive")
    itemOrpheusProcessor = registerItem(FemtocraftConfigs
                                        .ItemOrpheusProcessorID,
                                        "ItemOrpheusProcessor",
                                        "Orpheus Processor")
    itemFemtoPlating = registerItem(FemtocraftConfigs.ItemFemtoPlatingID,
                                    "ItemFemtoPlating", "Femto Plating")
    itemStyxValve = registerItem(FemtocraftConfigs.ItemStyxValveID,
                                 "ItemStyxValve", "Styx Valve")
    itemFemtoCoil = registerItem(FemtocraftConfigs.ItemFemtoCoilID,
                                 "ItemFemtoCoil", "Femto Coil")
    itemPhlegethonTunnelPrimer = registerItem(FemtocraftConfigs
                                              .ItemPhlegethonTunnelPrimerID,
                                              "ItemPhlegethonTunnelPrimer",
                                              "Phlegethon Tunnel Primer")
    itemStellaratorPlating = registerItem(FemtocraftConfigs
                                          .ItemStellaratorPlatingID,
                                          "ItemStellaratorPlating",
                                          "Stellarator Plating")
    itemInfinitelyRecursiveALU = registerItem(FemtocraftConfigs
                                              .ItemInfinitelyRecursiveALUID,
                                              "ItemInfinitelyRecursiveALU",
                                              "Infinitely Recursive ALU")
    itemInfiniteVolumePolychora = registerItem(FemtocraftConfigs
                                               .ItemInfiniteVolumePolychoraID,
                                               "ItemInfiniteVolumePolychora",
                                               "Infinite Volume Polychora")
    itemNucleationCore = new ItemNucleationCore(FemtocraftConfigs
                                                .ItemNucleationCoreID,
                                                "ItemNucleationCore")
    GameRegistry.registerItem(itemNucleationCore, "ItemNucleationCore")
    LanguageRegistry.addName(itemNucleationCore, "Nucleation Core")
    itemInhibitionCore = new ItemInhibitionCore(FemtocraftConfigs
                                                .ItemInhibitionCoreID,
                                                "ItemInhibitionCore")
    GameRegistry.registerItem(itemInhibitionCore, "ItemInhibitionCore")
    LanguageRegistry.addName(itemInhibitionCore, "Inhibition Core")
    itemQuantumSchematic = new ItemQuantumSchematic(FemtocraftConfigs
                                                    .ItemQuantumSchematicID,
                                                    "ItemQuantumSchematic")
    GameRegistry.registerItem(itemQuantumSchematic, "ItemQuantumSchematic")
    LanguageRegistry.addName(itemQuantumSchematic, "Quantum Schematic")
    itemMicroTechnology = new ItemMicroTechnology(FemtocraftConfigs
                                                  .ItemMicroTechnologyID)
                          .setUnlocalizedName("ItemMicroTechnology")
    GameRegistry.registerItem(itemMicroTechnology, "ItemMicroTechnology")
    LanguageRegistry.addName(itemMicroTechnology, "Micro Technology")
    itemNanoTechnology = new ItemNanoTechnology(FemtocraftConfigs
                                                .ItemNanoTechnologyID)
                         .setUnlocalizedName("ItemNanoTechnology")
    GameRegistry.registerItem(itemNanoTechnology, "ItemNanoTechnology")
    LanguageRegistry.addName(itemNanoTechnology, "Nano Technology")
    itemFemtoTechnology = new ItemFemtoTechnology(FemtocraftConfigs
                                                  .ItemFemtoTechnologyID)
                          .setUnlocalizedName("ItemFemtoTechnology")
    GameRegistry.registerItem(itemFemtoTechnology, "ItemFemtoTechnology")
    LanguageRegistry.addName(itemFemtoTechnology, "Femto Technology")
    itemPaperSchematic = new ItemPaperSchematic(FemtocraftConfigs
                                                .ItemPaperSchematicID,
                                                "ItemPaperSchematic")
    GameRegistry.registerItem(itemPaperSchematic, "ItemPaperSchematic")
    LanguageRegistry.addName(itemPaperSchematic, "Paper Schematic")
    itemInterfaceDeviceMicro = new ItemMicroInterfaceDevice(FemtocraftConfigs
                                                            .ItemMicroInterfaceDeviceID)
                               .setUnlocalizedName("ItemInterfaceDeviceMicro")
    GameRegistry
    .registerItem(itemInterfaceDeviceMicro, "ItemInterfaceDeviceMicro")
    LanguageRegistry.addName(itemInterfaceDeviceMicro, "MicroInterface Device")
    itemInterfaceDeviceNano = new ItemNanoInterfaceDevice(FemtocraftConfigs
                                                          .ItemNanoInterfaceDeviceID)
                              .setUnlocalizedName("ItemInterfaceDeviceNano")
    GameRegistry
    .registerItem(itemInterfaceDeviceNano, "ItemInterfaceDeviceNano")
    LanguageRegistry.addName(itemInterfaceDeviceNano, "NanoInterface Device")
    itemInterfaceDeviceFemto = new ItemFemtoInterfaceDevice(FemtocraftConfigs
                                                            .ItemFemtoInterfaceDeviceID)
                               .setUnlocalizedName("ItemInterfaceDeviceFemto")
    GameRegistry
    .registerItem(itemInterfaceDeviceFemto, "ItemInterfaceDeviceFemto")
    LanguageRegistry.addName(itemInterfaceDeviceFemto, "FemtoInterface Device")
    itemCubit = registerItem(FemtocraftConfigs.ItemCubitID, "ItemCubit",
                             FemtocraftUtils.orangeify("Cubit"))
    itemRectangulon = registerItem(FemtocraftConfigs.ItemRectangulonID,
                                   "ItemRectangulon",
                                   FemtocraftUtils.orangeify("Rectangulon"))
    itemPlaneoid = registerItem(FemtocraftConfigs.ItemPlaneoidID,
                                "ItemPlaneoid",
                                FemtocraftUtils.orangeify("Planeoid"))
    itemCrystallite = registerItem(FemtocraftConfigs.ItemCrystalliteID,
                                   "ItemCrystallite",
                                   FemtocraftUtils.greenify("Crystallite"))
    itemMineralite = registerItem(FemtocraftConfigs.ItemMineraliteID,
                                  "ItemMineralite",
                                  FemtocraftUtils.greenify("Mineralite"))
    itemMetallite = registerItem(FemtocraftConfigs.ItemMetalliteID,
                                 "ItemMetallite",
                                 FemtocraftUtils.greenify("Metallite"))
    itemFaunite = registerItem(FemtocraftConfigs.ItemFauniteID, "ItemFaunite",
                               FemtocraftUtils.greenify("Faunite"))
    itemElectrite = registerItem(FemtocraftConfigs.ItemElectriteID,
                                 "ItemElectrite",
                                 FemtocraftUtils.greenify("Electrite"))
    itemFlorite = registerItem(FemtocraftConfigs.ItemFloriteID, "ItemFlorite",
                               FemtocraftUtils.greenify("Florite"))
    itemMicroCrystal = registerItem(FemtocraftConfigs.ItemMicroCrystalID,
                                    "ItemMicroCrystal",
                                    FemtocraftUtils.blueify("Micro Crystal"))
    itemProteinChain = registerItem(FemtocraftConfigs.ItemProteinChainID,
                                    "ItemProteinChain",
                                    FemtocraftUtils.blueify("Protein Chain"))
    itemNerveCluster = registerItem(FemtocraftConfigs.ItemNerveClusterID,
                                    "ItemNerveCluster",
                                    FemtocraftUtils.blueify("Nerve Cluster"))
    itemConductiveAlloy = registerItem(FemtocraftConfigs.ItemConductiveAlloyID,
                                       "ItemConductiveAlloy", FemtocraftUtils
                                                              .blueify(
          "Conductive Alloy"))
    itemMetalComposite = registerItem(FemtocraftConfigs.ItemMetalCompositeID,
                                      "ItemMetalComposite", FemtocraftUtils
                                                            .blueify(
          "Metal Composite"))
    itemFibrousStrand = registerItem(FemtocraftConfigs.ItemFibrousStrandID,
                                     "ItemFibrousStrand",
                                     FemtocraftUtils.blueify("Fibrous Strand"))
    itemMineralLattice = registerItem(FemtocraftConfigs.ItemMineralLatticeID,
                                      "ItemMineralLattice", FemtocraftUtils
                                                            .blueify(
          "Mineral Lattice"))
    itemFungalSpores = registerItem(FemtocraftConfigs.ItemFungalSporesID,
                                    "ItemFungalSpores",
                                    FemtocraftUtils.blueify("Fungal Spores"))
    itemIonicChunk = registerItem(FemtocraftConfigs.ItemIonicChunkID,
                                  "ItemIonicChunk",
                                  FemtocraftUtils.blueify("Ionic Chunk"))
    itemReplicatingMaterial = registerItem(FemtocraftConfigs
                                           .ItemReplicatingMaterialID,
                                           "ItemReplicatingMaterial",
                                           FemtocraftUtils
                                           .blueify("Replicating Material"))
    itemSpinyFilament = registerItem(FemtocraftConfigs.ItemSpinyFilamentID,
                                     "ItemSpinyFilament",
                                     FemtocraftUtils.blueify("Spiny Filament"))
    itemHardenedBulb = registerItem(FemtocraftConfigs.ItemHardenedBulbID,
                                    "ItemHardenedBulb",
                                    FemtocraftUtils.blueify("Hardened Bulb"))
    itemMorphicChannel = registerItem(FemtocraftConfigs.ItemMorphicChannelID,
                                      "ItemMorphicChannel", FemtocraftUtils
                                                            .blueify(
          "Morphic Channel"))
    itemSynthesizedFiber = registerItem(FemtocraftConfigs
                                        .ItemSynthesizedFiberID,
                                        "ItemSynthesizedFiber", FemtocraftUtils
                                                                .blueify(
          "Synthesized Fiber"))
    itemOrganometallicPlate = registerItem(FemtocraftConfigs
                                           .ItemOrganometallicPlateID,
                                           "ItemOrganometallicPlate",
                                           FemtocraftUtils
                                           .blueify("Organometallic Plate"))
    itemMicroPlating = registerItem(FemtocraftConfigs.ItemMicroPlatingID,
                                    "ItemMicroPlating", "Micro Plating")
  }

  private def registerItem(
                            id: Int, unlocalizedName: String, name: String
                          ): Item = {
    val it: Item = new ItemBase(id, unlocalizedName)
    LanguageRegistry.addName(it, name)
    GameRegistry.registerItem(it, unlocalizedName)
    it
  }

  def log(level: Level, msg: String) = logger.log(level, msg)

  @EventHandler def load(event: FMLInitializationEvent) {
  }

  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    assemblerConfigs = new FemtocraftConfigAssembler(recipeConfig)
    recipeManager.init()
    recipeManager.assemblyRecipes.registerDefaultRecipes()
    MagnetRegistry.init()
    new FemtocraftConfigTechnology(technologyConfigFile).loadTechnologies()
    if (event.getSide eq Side.CLIENT) {
      researchManager.calculateGraph()
    }
  }

  @EventHandler def serverLoad(event: FMLServerStartingEvent) {
    event.registerServerCommand(femtocraftServerCommand)
  }
}


