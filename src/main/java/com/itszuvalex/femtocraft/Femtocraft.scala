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
import com.itszuvalex.femtocraft.configuration.{FemtocraftAssemblerConfig, FemtocraftConfigs, FemtocraftTechnologyConfig}
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
import com.itszuvalex.femtocraft.proxy.ProxyCommon
import com.itszuvalex.femtocraft.research.blocks.{BlockResearchComputer, BlockResearchConsole}
import com.itszuvalex.femtocraft.research.items.{ItemFemtoTechnology, ItemMicroTechnology, ItemNanoTechnology, ItemPortableResearchComputer}
import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager
import com.itszuvalex.femtocraft.transport.items.blocks.BlockVacuumTube
import com.itszuvalex.femtocraft.transport.liquids.blocks.BlockSuctionPipe
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import cpw.mods.fml.common.Mod.EventHandler
import cpw.mods.fml.common.event.{FMLInitializationEvent, FMLPostInitializationEvent, FMLPreInitializationEvent, FMLServerStartingEvent}
import cpw.mods.fml.common.network.NetworkRegistry
import cpw.mods.fml.common.registry.{GameRegistry, LanguageRegistry}
import cpw.mods.fml.common.{FMLCommonHandler, Mod, SidedProxy}
import cpw.mods.fml.relauncher.Side
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.creativetab.CreativeTabs
import net.minecraft.item.{Item, ItemStack}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.config.Configuration
import net.minecraftforge.fluids.{BlockFluidBase, Fluid, FluidRegistry}
import net.minecraftforge.oredict.OreDictionary
import org.apache.logging.log4j.{Level, Logger}

/**
 * Created by Christopher Harris (Itszuvalex) on 10/6/14.
 */
@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION,
     modLanguage = "scala")
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
  var proxy                                 : ProxyCommon               = null
  var femtocraftTab                         : CreativeTabs              = new FemtocraftCreativeTab("Femtocraft")
  var config                                : Configuration             = null
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
  var blockPlasmaTurbine                    : Block                     = null
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
  var itemIngotFarenite                     : Item                      = null
  var itemIngotMalenite                     : Item                      = null
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
    val suggestedConfig = event.getSuggestedConfigurationFile
    config = new Configuration(suggestedConfig)
    FemtocraftConfigs.load(config)
    val suggestConfigName = suggestedConfig.getName.split("\\.")
    technologyConfigFile = new File(suggestedConfig.getParentFile,
                                    suggestConfigName(0) + TECH_CONFIG_APPEND +
                                    "." + (if (FemtocraftConfigs.useXMLFile) "xml" else suggestConfigName(1)))
    recipeConfigFile = new File(suggestedConfig.getParentFile,
                                suggestConfigName(0) + RECIPE_CONFIG_APPEND +
                                "." + suggestConfigName(1))
    recipeConfig = new Configuration(recipeConfigFile)

    FemtocraftPacketHandler.init()

    Femtocraft.proxy.registerTileEntities()
    Femtocraft.proxy.registerRendering()
    Femtocraft.proxy.registerBlockRenderers()
    Femtocraft.proxy.registerTickHandlers()
    if (event.getSide eq Side.CLIENT) {
      soundManager = new FemtocraftSoundManager
    }
    NetworkRegistry.INSTANCE.registerGuiHandler(this, new FemtocraftGuiHandler)
    FMLCommonHandler.instance().bus().register(new FemtocraftPlayerTracker)
    MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer)
    MinecraftForge.EVENT_BUS.register(new FemtocraftOreRetrogenHandler)
    proxy.registerRendering()

    if (FemtocraftConfigs.worldGen) {
      GameRegistry.registerWorldGenerator(new FemtocraftOreGenerator, FemtocraftConfigs.GENERATION_WEIGHT)
    }

    LanguageRegistry.instance.addStringLocalization("itemGroup.Femtocraft", "en_US", "Femtocraft")
    blockOreTitanium = new BlockOreTitanium().setBlockName("BlockOreTitanium")
    blockOreTitanium.setHarvestLevel("pickaxe", 2)
    GameRegistry.registerBlock(blockOreTitanium, "BlockOreTitanium")
    LanguageRegistry.addName(blockOreTitanium, "Titanium Ore")
    if (FemtocraftConfigs.registerTitaniumOreInOreDictionary) {
      OreDictionary.registerOre("oreTitanium", new ItemStack(blockOreTitanium))
    }
    blockOrePlatinum = new BlockOrePlatinum().setBlockName("BlockOrePlatinum")
    blockOrePlatinum.setHarvestLevel("pickaxe", 2)
    GameRegistry.registerBlock(blockOrePlatinum, "BlockOrePlatinum")
    LanguageRegistry.addName(blockOrePlatinum, "Platinum Ore")
    if (FemtocraftConfigs.registerPlatinumOreInOreDictionary) {
      OreDictionary.registerOre("orePlatinum", new ItemStack(blockOrePlatinum))
    }
    blockOreThorium = new BlockOreThorium().setBlockName("BlockOreThorium")
    blockOreThorium.setHarvestLevel("pickaxe", 2)
    GameRegistry.registerBlock(blockOreThorium, "BlockOreThorium")
    LanguageRegistry.addName(blockOreThorium, "Thorium Ore")
    if (FemtocraftConfigs.registerThoriumOreInOreDictionary) {
      OreDictionary.registerOre("oreThorium", new ItemStack(blockOreThorium))
    }
    blockOreFarenite = new BlockOreFarenite().setBlockName("BlockOreFarenite")
    blockOreFarenite.setHarvestLevel("pickaxe", 2)
    GameRegistry.registerBlock(blockOreFarenite, "BlockOreFarenite")
    LanguageRegistry.addName(blockOreFarenite, "Farenite Ore")
    if (FemtocraftConfigs.registerFareniteOreInOreDictionary) {
      OreDictionary.registerOre("oreFarenite", new ItemStack(blockOreFarenite))
    }
    blockOreMalenite = new BlockOreMalenite().setBlockName("BlockOreMalenite")
    blockOreMalenite.setHarvestLevel("pickaxe", 3)
    GameRegistry.registerBlock(blockOreMalenite, "BlockOreMalenite")
    LanguageRegistry.addName(blockOreMalenite, "Malenite Ore")
    if (FemtocraftConfigs.registerMaleniteOreInOreDictionary) {
      OreDictionary.registerOre("oreMalenite", new ItemStack(blockOreMalenite))
    }
    blockOreLodestone = new BlockOreLodestone().setBlockName("BlockOreLodestone")
    blockOreLodestone.setHarvestLevel("pickaxe", 2)
    GameRegistry.registerBlock(blockOreLodestone, "BlockOreLodestone")
    LanguageRegistry.addName(blockOreLodestone, "Lodestone Ore")
    if (FemtocraftConfigs.registerLodestoneOreInOreDictionary) {
      OreDictionary
      .registerOre("oreLodestone", new ItemStack(blockOreLodestone))
    }
    nanoStone = new BlockNanoStone().setBlockName("BlockNanoStone")
    GameRegistry.registerBlock(nanoStone, "BlockNanoStone")
    LanguageRegistry.addName(nanoStone, "Nanostone")
    microStone = new BlockMicroStone().setBlockName("BlockMicroStone")
    GameRegistry.registerBlock(microStone, "BlockMicroStone")
    LanguageRegistry.addName(microStone, "Microstone")
    femtoStone = new BlockFemtoStone().setBlockName("BlockFemtoStone")
    GameRegistry.registerBlock(femtoStone, "BlockFemtoStone")
    LanguageRegistry.addName(femtoStone, "Femtostone")
    unidentifiedAlloy = new BlockUnidentifiedAlloy().setBlockName("BlockUnidentifiedAlloy")
    GameRegistry.registerBlock(unidentifiedAlloy, "BlockUnidentifiedAlloy")
    LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy")
    blockResearchComputer = new BlockResearchComputer().setBlockName("BlockResearchComputer")
    GameRegistry.registerBlock(blockResearchComputer, "BlockResearchComputer")
    LanguageRegistry.addName(blockResearchComputer, "Research Computer")
    blockResearchConsole = new BlockResearchConsole().setBlockName("BlockResearchConsole")
    GameRegistry.registerBlock(blockResearchConsole, "BlockResearchConsole")
    LanguageRegistry.addName(blockResearchConsole, "Research Console")
    blockMicroCable = new BlockMicroCable(Material.iron).setBlockName("BlockMicroCable")
    GameRegistry.registerBlock(blockMicroCable, "BlockMicroCable")
    LanguageRegistry.addName(blockMicroCable, "Micro-Cable")
    blockNanoCable = new BlockNanoCable(Material.iron).setBlockName("BlockNanoCable")
    GameRegistry.registerBlock(blockNanoCable, "BlockNanoCable")
    LanguageRegistry.addName(blockNanoCable, "Nano-Cable")
    blockFemtoCable = new BlockFemtoCable(Material.iron).setBlockName("BlockFemtoCable")
    GameRegistry.registerBlock(blockFemtoCable, "BlockFemtoCable")
    LanguageRegistry.addName(blockFemtoCable, "Femto-Cable")
    generatorTest = new BlockGenerator(Material.iron).setBlockName("BlockGenerator").setHardness(3.5f)
    GameRegistry.registerBlock(generatorTest, "BlockGenerator")
    LanguageRegistry.addName(generatorTest, "Generator")
    consumerTest = new BlockConsumer(Material.iron).setBlockName("BlockConsumer").setHardness(3.5f)
    GameRegistry.registerBlock(consumerTest, "BlockConsumer")
    LanguageRegistry.addName(consumerTest, "Consumer")
    blockMicroFurnaceUnlit = new BlockMicroFurnace(false).setBlockName("BlockMicroFurnace")
    GameRegistry.registerBlock(blockMicroFurnaceUnlit, "BlockMicroFurnace")
    LanguageRegistry.addName(blockMicroFurnaceUnlit, "Micro-Furnace")
    blockMicroFurnaceLit = new BlockMicroFurnace(true).setBlockName("BlockMicroFurnace_lit")
    GameRegistry.registerBlock(blockMicroFurnaceLit, "BlockMicroFurnace_lit")
    blockMicroDeconstructor = new BlockMicroDeconstructor().setBlockName("BlockMicroDeconstructor")
    GameRegistry.registerBlock(blockMicroDeconstructor, "BlockMicroDeconstructor")
    LanguageRegistry.addName(blockMicroDeconstructor, "Microtech Deconstructor")
    blockMicroReconstructor = new BlockMicroReconstructor().setBlockName("BlockMicroReconstructor")
    GameRegistry.registerBlock(blockMicroReconstructor, "BlockMicroReconstructor")
    LanguageRegistry.addName(blockMicroReconstructor, "Microtech Reconstructor")
    blockEncoder = new BlockEncoder().setBlockName("BlockEncoder")
    GameRegistry.registerBlock(blockEncoder, "BlockEncoder")
    LanguageRegistry.addName(blockEncoder, "Schematic Encoder")
    blockNanoInnervatorUnlit = new BlockNanoInnervator(false).setBlockName("BlockNanoInnervator")
    GameRegistry.registerBlock(blockNanoInnervatorUnlit, "BlockNanoInnervator")
    LanguageRegistry.addName(blockNanoInnervatorUnlit, "Nano Innervator")
    blockNanoInnervatorLit = new BlockNanoInnervator(true).setBlockName("BlockNanoInnervator_lit")
    GameRegistry.registerBlock(blockNanoInnervatorLit, "BlockNanoInnervator_lit")
    blockNanoDismantler = new BlockNanoDismantler().setBlockName("BlockNanoDismantler")
    GameRegistry.registerBlock(blockNanoDismantler, "BlockNanoDismantler")
    LanguageRegistry.addName(blockNanoDismantler, "Nano Dismantler")
    blockNanoFabricator = new BlockNanoFabricator().setBlockName("BlockNanoFabricator")
    GameRegistry.registerBlock(blockNanoFabricator, "BlockNanoFabricator")
    LanguageRegistry.addName(blockNanoFabricator, "Nano Fabricator")
    blockNanoEnmesher = new BlockNanoEnmesher().setBlockName("BlockNanoEnmesher")
    GameRegistry.registerBlock(blockNanoEnmesher, "BlockNanoEnmesher")
    LanguageRegistry.addName(blockNanoEnmesher, "Nano Enmesher")
    blockNanoHorologe = new BlockNanoHorologe().setBlockName("BlockNanoHorologe")
    GameRegistry.registerBlock(blockNanoHorologe, "BlockNanoHorologe")
    LanguageRegistry.addName(blockNanoHorologe, "Nano Horologe")
    blockFemtoImpulserUnlit = new BlockFemtoImpulser(false).setBlockName("BlockFemtoImpulser")
    GameRegistry.registerBlock(blockFemtoImpulserUnlit, "BlockFemtoImpulser")
    LanguageRegistry.addName(blockFemtoImpulserUnlit, "Femto Impulser")
    blockFemtoImpulserLit = new BlockFemtoImpulser(true).setBlockName("BlockFemtoImpulser_lit")
    GameRegistry.registerBlock(blockFemtoImpulserLit, "BlockFemtoImpulser_lit")
    blockFemtoRepurposer = new BlockFemtoRepurposer().setBlockName("BlockFemtoRepurposer")
    GameRegistry.registerBlock(blockFemtoRepurposer, "BlockFemtoRepurposer")
    LanguageRegistry.addName(blockFemtoRepurposer, "Femto Repurposer")
    blockFemtoCoagulator = new BlockFemtoCoagulator().setBlockName("BlockFemtoCoagulator")
    GameRegistry.registerBlock(blockFemtoCoagulator, "BlockFemtoCoagulator")
    LanguageRegistry.addName(blockFemtoCoagulator, "Femto Coagulator")
    blockFemtoEntangler = new BlockFemtoEntangler().setBlockName("BlockFemtoEntangler")
    GameRegistry.registerBlock(blockFemtoEntangler, "BlockFemtoEntangler")
    LanguageRegistry.addName(blockFemtoEntangler, "Femto Entangler")
    blockFemtoChronoshifter = new BlockFemtoChronoshifter().setBlockName("BlockFemtoChronoshifter")
    GameRegistry.registerBlock(blockFemtoChronoshifter, "BlockFemtoChronoshifter")
    LanguageRegistry.addName(blockFemtoChronoshifter, "Femto Chronoshifter")
    blockMicroCube = new BlockMicroCube().setBlockName("BlockMicroCube")
    GameRegistry.registerBlock(blockMicroCube, classOf[ItemBlockMicroCube], "BlockMicroCube")
    LanguageRegistry.addName(blockMicroCube, "Micro-Cube")
    blockNanoCubeFrame = new BlockNanoCubeFrame().setBlockName("BlockNanoCubeFrame")
    GameRegistry.registerBlock(blockNanoCubeFrame, "BlockNanoCubeFrame")
    LanguageRegistry.addName(blockNanoCubeFrame, "Nano-Cube Frame")
    blockNanoCubePort = new BlockNanoCubePort().setBlockName("BlockNanoCubePort")
    GameRegistry.registerBlock(blockNanoCubePort, "BlockNanoCubePort")
    LanguageRegistry.addName(blockNanoCubePort, "Nano-Cube Port")
    blockFemtoCubePort = new BlockFemtoCubePort().setBlockName("BlockFemtoCubePort")
    GameRegistry.registerBlock(blockFemtoCubePort, "BlockFemtoCubePort")
    LanguageRegistry.addName(blockFemtoCubePort, "Femto-Cube Port")
    blockFemtoCubeFrame = new BlockFemtoCubeFrame().setBlockName("BlockFemtoCubeFrame")
    GameRegistry.registerBlock(blockFemtoCubeFrame, "BlockFemtoCubeFrame")
    LanguageRegistry.addName(blockFemtoCubeFrame, "Femto-Cube Frame")
    blockFemtoCubeChassis = new BlockFemtoCubeChassis().setBlockName("BlockFemtoCubeChassis")
    GameRegistry.registerBlock(blockFemtoCubeChassis, "BlockFemtoCubeChassis")
    LanguageRegistry.addName(blockFemtoCubeChassis, "Femto-Cube Chassis")
    blockVacuumTube = new BlockVacuumTube().setBlockName("BlockVacuumTube")
    GameRegistry.registerBlock(blockVacuumTube, "BlockVacuumTube")
    LanguageRegistry.addName(blockVacuumTube, "Vacuum Tube")
    blockSuctionPipe = new BlockSuctionPipe().setBlockName("BlockSuctionPipe")
    GameRegistry.registerBlock(blockSuctionPipe, "BlockSuctionPipe")
    LanguageRegistry.addName(blockSuctionPipe, "Suction Pipe")
    blockMicroChargingBase = new BlockAtmosphericChargingBase().setBlockName("BlockBaseMicroCharging")
    GameRegistry.registerBlock(blockMicroChargingBase, "BlockBaseMicroCharging")
    LanguageRegistry.addName(blockMicroChargingBase, "Electrostatic Charging Base")
    blockMicroChargingCoil = new BlockAtmosphericChargingCoil().setBlockName("BlockCoilMicroCharging")
    GameRegistry.registerBlock(blockMicroChargingCoil, "BlockCoilMicroCharging")
    LanguageRegistry.addName(blockMicroChargingCoil, "Electrostatic Charging Coil")
    blockMicroChargingCapacitor = new BlockAtmosphericChargingCapacitor().setBlockName("BlockAtmosphericChargingCapacitor")
    GameRegistry.registerBlock(blockMicroChargingCapacitor, "BlockAtmosphericChargingCapacitor")
    LanguageRegistry.addName(blockMicroChargingCapacitor, "Electrostatic Charging Capacitor")
    blockMagneticInductionGenerator = new BlockMagneticInductionGenerator().setBlockName("BlockMagneticInductionGenerator")
    GameRegistry.registerBlock(blockMagneticInductionGenerator, "BlockMagneticInductionGenerator")
    LanguageRegistry.addName(blockMagneticInductionGenerator, "Magnetic Induction Generator")
    blockOrbitalEqualizer = new BlockOrbitalEqualizer().setBlockName("BlockOrbitalEqualizer")
    GameRegistry.registerBlock(blockOrbitalEqualizer, "BlockOrbitalEqualizer")
    LanguageRegistry.addName(blockOrbitalEqualizer, "Orbital Equalizer")
    blockCryoEndothermalChargingBase = new BlockCryoEndothermalChargingBase().setBlockName("BlockCryoEndothermalChargingBase")
    GameRegistry.registerBlock(blockCryoEndothermalChargingBase, "BlockCryoEndothermalChargingBase")
    LanguageRegistry.addName(blockCryoEndothermalChargingBase, "CryoEndothermal Charging Base")
    blockCryoEndothermalChargingCoil = new BlockCryoEndothermalChargingCoil().setBlockName("BlockCryoEndothermalChargingCoil")
    GameRegistry.registerBlock(blockCryoEndothermalChargingCoil, "BlockCryoEndothermalChargingCoil")
    LanguageRegistry.addName(blockCryoEndothermalChargingCoil, "CryoEndothermal Charging Coil")
    blockFissionReactorCore = new BlockNanoFissionReactorCore().setBlockName("BlockFissionReactorCore")
    GameRegistry.registerBlock(blockFissionReactorCore, "BlockFissionReactorCore")
    LanguageRegistry.addName(blockFissionReactorCore, "Fission Reactor Core")
    blockFissionReactorHousing = new BlockNanoFissionReactorHousing().setBlockName("BlockFissionReactorHousing")
    GameRegistry.registerBlock(blockFissionReactorHousing, "BlockFissionReactorHousing")
    LanguageRegistry.addName(blockFissionReactorHousing, "Fission Reactor Housing")
    blockMagnetohydrodynamicGenerator = new BlockMagnetohydrodynamicGenerator().setBlockName("BlockMagnetohydrodynamicGenerator")
    GameRegistry.registerBlock(blockMagnetohydrodynamicGenerator, "BlockMagnetohydrodynamicGenerator")
    LanguageRegistry.addName(blockMagnetohydrodynamicGenerator, "Magnetohydrodynamic Generator")
    blockSteamGenerator = new BlockSteamGenerator().setBlockName("BlockSteamGenerator")
    GameRegistry.registerBlock(blockSteamGenerator, "BlockSteamGenerator")
    LanguageRegistry.addName(blockSteamGenerator, "Steam Generator")
    blockDecontaminationChamber = new BlockDecontaminationChamber().setBlockName("BlockDecontaminationChamber")
    GameRegistry.registerBlock(blockDecontaminationChamber, "BlockDecontaminationChamber")
    LanguageRegistry.addName(blockDecontaminationChamber, "Decontamination Chamber")
    blockPhlegethonTunnelCore = new BlockPhlegethonTunnelCore().setBlockName("BlockPhlegethonTunnelCore")
    GameRegistry.registerBlock(blockPhlegethonTunnelCore, "BlockPhlegethonTunnelCore")
    LanguageRegistry.addName(blockPhlegethonTunnelCore, "Phlegethon Tunnel Core")
    blockPhlegethonTunnelFrame = new BlockPhlegethonTunnelFrame().setBlockName("BlockPhlegethonTunnelFrame")
    GameRegistry.registerBlock(blockPhlegethonTunnelFrame, "BlockPhlegethonTunnelFrame")
    LanguageRegistry.addName(blockPhlegethonTunnelFrame, "Phlegethon Tunnel Frame")
    blockSisyphusStabilizer = new BlockSisyphusStabilizer().setBlockName("BlockSisyphusStabilizer")
    GameRegistry.registerBlock(blockSisyphusStabilizer, "BlockSisyphusStabilizer")
    LanguageRegistry.addName(blockSisyphusStabilizer, "Sisyphus Stabilizer")
    blockNullEqualizer = new BlockNullEqualizer().setBlockName("BlockNullEqualizer")
    GameRegistry.registerBlock(blockNullEqualizer, "BlockNullEqualizer")
    LanguageRegistry.addName(blockNullEqualizer, "Null-Energy Equalizer")
    blockStellaratorCore = new BlockFemtoStellaratorCore().setBlockName("BlockStellaratorCore")
    GameRegistry.registerBlock(blockStellaratorCore, "BlockStellaratorCore")
    LanguageRegistry.addName(blockStellaratorCore, "Stellarator Core")
    blockStellaratorFocus = new BlockFemtoStellaratorFocus().setBlockName("BlockStellaratorFocus")
    GameRegistry.registerBlock(blockStellaratorFocus, "BlockStellaratorFocus")
    LanguageRegistry.addName(blockStellaratorFocus, "Stellarator Focus")
    blockStellaratorOpticalMaser = new BlockFemtoStellaratorOpticalMaser().setBlockName("BlockStellaratorOpticalMaser")
    GameRegistry.registerBlock(blockStellaratorOpticalMaser, "BlockStellaratorOpticalMaser")
    LanguageRegistry.addName(blockStellaratorOpticalMaser, "Stellarator Optical Maser")
    blockStellaratorHousing = new BlockFemtoStellaratorHousing().setBlockName("BlockStellaratorHousing")
    GameRegistry.registerBlock(blockStellaratorHousing, "BlockStellaratorHousing")
    LanguageRegistry.addName(blockStellaratorHousing, "Stellarator Housing")
    blockPlasmaConduit = new BlockPlasmaConduit().setBlockName("BlockPlasmaConduit")
    GameRegistry.registerBlock(blockPlasmaConduit, "BlockPlasmaConduit")
    LanguageRegistry.addName(blockPlasmaConduit, "Plasma Conduit")
    blockPlasmaVent = new BlockPlasmaVent().setBlockName("BlockPlasmaVent")
    GameRegistry.registerBlock(blockPlasmaVent, "BlockPlasmaVent")
    LanguageRegistry.addName(blockPlasmaVent, "Plasma Vent")
    blockPlasmaTurbine = new BlockPlasmaTurbine().setBlockName("BlockPlasmaTurbine")
    GameRegistry.registerBlock(blockPlasmaTurbine, "BlockPlasmaTurbine")
    LanguageRegistry.addName(blockPlasmaTurbine, "Plasma Turbine")
    blockPlasmaCondenser = new BlockPlasmaCondenser().setBlockName("BlockPlasmaCondenser")
    GameRegistry.registerBlock(blockPlasmaCondenser, "BlockPlasmaCondenser")
    LanguageRegistry.addName(blockPlasmaCondenser, "Plasma Condenser")
    fluidMass = new FluidMass
    FluidRegistry.registerFluid(fluidMass)
    blockFluidMass = new BlockFluidMass()
    blockFluidMass.setBlockName("BlockMass")
    GameRegistry.registerBlock(blockFluidMass, "BlockMass")
    LanguageRegistry.addName(blockFluidMass, "Mass")
    fluidMoltenSalt = new FluidMoltenSalt
    FluidRegistry.registerFluid(fluidMoltenSalt)
    blockFluidMoltenSalt = new BlockFluidMoltenSalt()
    blockFluidMoltenSalt.setBlockName("BlockFluidMoltenSalt")
    GameRegistry.registerBlock(blockFluidMoltenSalt, "BlockFluidMoltenSalt")
    LanguageRegistry.addName(blockFluidMoltenSalt, "Molten Salt")
    fluidCooledMoltenSalt = new FluidCooledMoltenSalt
    FluidRegistry.registerFluid(fluidCooledMoltenSalt)
    blockFluidCooledMoltenSalt = new BlockFluidCooledMoltenSalt()
    blockFluidCooledMoltenSalt.setBlockName("BlockFluidCooledMoltenSalt")
    GameRegistry.registerBlock(blockFluidCooledMoltenSalt, "BlockFluidCooledMoltenSalt")
    LanguageRegistry.addName(blockFluidCooledMoltenSalt, "Cooled Molten Salt")
    fluidCooledContaminatedMoltenSalt = new FluidCooledContaminatedMoltenSalt
    FluidRegistry.registerFluid(fluidCooledContaminatedMoltenSalt)
    blockFluidCooledContaminatedMoltenSalt = new BlockFluidCooledContaminatedMoltenSalt()
    blockFluidCooledContaminatedMoltenSalt.setBlockName("BlockFluidCooledContaminatedMoltenSalt")
    GameRegistry.registerBlock(blockFluidCooledContaminatedMoltenSalt, "BlockFluidCooledContaminatedMoltenSalt")
    LanguageRegistry.addName(blockFluidCooledContaminatedMoltenSalt, "Cooled Contaminated Molten Salt")
    blockPlasma = new BlockPlasma()
    itemIngotTitanium = registerItem("ItemIngotTitanium", "Titanium Ingot")
    if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) OreDictionary.registerOre("ingotTitanium", new ItemStack(itemIngotTitanium))

    itemIngotPlatinum = registerItem("ItemIngotPlatinum", "Platinum Ingot")
    if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) OreDictionary.registerOre("ingotPlatinum", new ItemStack(itemIngotPlatinum))

    itemIngotThorium = registerItem("ItemIngotThorium", "Thorium Ingot")
    if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) OreDictionary.registerOre("ingotThorium", new ItemStack(itemIngotThorium))

    itemIngotFarenite = registerItem("ItemIngotFarenite", "Farenite")
    OreDictionary.registerOre("ingotFarenite", new ItemStack(itemIngotFarenite))
    itemIngotMalenite = registerItem("ItemIngotMalenite", "Malenite")
    OreDictionary.registerOre("ingotMalenite", new ItemStack(itemIngotMalenite))
    itemIngotTemperedTitanium = registerItem("ItemIngotTemperedTitanium", "Tempered Titanium Ingot")
    OreDictionary.registerOre("ingotTemperedTitanium", new ItemStack(itemIngotTemperedTitanium))
    itemIngotThFaSalt = registerItem("ItemIngotThFaSalt", "ThFa Salt Ingot")
    OreDictionary.registerOre("ingotThFaSalt", new ItemStack(itemIngotThFaSalt))
    itemNuggetLodestone = registerItem("ItemNuggetLodestone", "Lodestone Nugget")
    OreDictionary.registerOre("nuggetLodestone", new ItemStack(itemNuggetLodestone))
    itemChunkLodestone = registerItem("ItemChunkLodestone", "Lodestone Chunk")
    OreDictionary.registerOre("chunkLodestone", new ItemStack(itemChunkLodestone))
    itemConductivePowder = registerItem("ItemConductivePowder", "Conductive Powder")
    itemBoard = registerItem("ItemBoard", "Board")
    itemPrimedBoard = registerItem("ItemPrimedBoard", "Primed Board")
    itemDopedBoard = registerItem("ItemDopedBoard", "Doped Board")
    itemMicrochip = registerItem("ItemMicrochip", "Microchip")
    itemSpool = registerItem("ItemSpool", "Spool")
    itemSpoolGold = registerItem("ItemSpoolGold", "Gold Wire Spool")
    itemSpoolPlatinum = registerItem("ItemSpoolPlatinum", "Platinum Wire Spool")
    itemMicroCoil = registerItem("ItemMicroCoil", "Micro Coil")
    itemBattery = registerItem("ItemBattery", "Battery")
    itemArticulatingArm = registerItem("ItemArticulatingArm", "Articulating Arm")
    itemDissassemblyArray = registerItem("ItemDissassemblyArray", "Dissassembly Array")
    itemAssemblyArray = registerItem("ItemAssemblyArray", "Assembly Array")
    itemVacuumCore = registerItem("ItemVacuumCore", "Vacuum Core")
    itemMicroLogicCore = registerItem("ItemMicroLogicCore", "Micro Logic Core")
    itemKineticPulverizer = registerItem("ItemKineticPulverizer", "Kinetic Pulverizer")
    itemHeatingElement = registerItem("ItemHeatingCoil", "Heating Coil")
    itemPortableResearchComputer = new ItemPortableResearchComputer().setUnlocalizedName("ItemPortableResearchComputer")
    GameRegistry.registerItem(itemPortableResearchComputer, "ItemPortableResearchComputer")
    LanguageRegistry.addName(itemPortableResearchComputer, "Portable Research Computer")
    itemNanochip = registerItem("ItemNanochip", "Nanochip")
    itemNanoCalculator = registerItem("ItemNanoCalculator", "Nano Calculator")
    itemNanoRegulator = registerItem("ItemNanoRegulator", "Nano Regulator")
    itemNanoSimulator = registerItem("ItemNanoSimulator", "Nano Simulator")
    itemBasicAICore = registerItem("ItemBasicAICore", "Basic AI Core")
    itemLearningCore = registerItem("ItemLearningCore", "Learning Core")
    itemSchedulerCore = registerItem("ItemSchedulerCore", "Scheduler Core")
    itemManagerCore = registerItem("ItemManagerCore", "Manager Core")
    itemFluidicConductor = registerItem("ItemFluidicConductor", "Fluidic Conductor")
    itemNanoCoil = registerItem("ItemNanoCoil", "Nano Coil")
    itemNanoPlating = registerItem("ItemNanoPlating", "Nano Plating")
    itemTemporalResonator = registerItem("ItemTemporalResonator", "Temporal Resonator")
    itemDimensionalMonopole = registerItem("ItemDimensionalMonopole", "Dimensional Monopole")
    itemSelfFulfillingOracle = registerItem("ItemSelfFulfillingOracle", "Self Fulfilling Oracle")
    itemCrossDimensionalCommunicator = registerItem("ItemCrossDimensionalCommunicator", "Cross Dimensional Communicator")
    itemInfallibleEstimator = registerItem("ItemInfallibleEstimator", "Infallible Estimator")
    itemPanLocationalComputer = registerItem("ItemPanLocationalComputer", "Pan Locational Computer")
    itemPandoraCube = registerItem("ItemPandoraCube", "Pandora Cube")
    itemFissionReactorPlating = registerItem("ItemFissionReactorPlating", "Fission Reactor Plating")
    itemDigitalSchematic = new ItemDigitalSchematic("ItemDigitalSchematic")
    GameRegistry.registerItem(itemDigitalSchematic, "ItemDigitalSchematic")
    LanguageRegistry.addName(itemDigitalSchematic, "Digital Schematic")
    itemMinosGate = registerItem("ItemMinosGate", "Minos Gate")
    itemCharosGate = registerItem("ItemCharosGate", "Charos Gate")
    itemCerberusGate = registerItem("ItemCerberusGate", "Cerberus Gate")
    itemErinyesCircuit = registerItem("ItemErinyesCircuit", "Erinyes Circuit")
    itemMinervaComplex = registerItem("ItemMinervaComplex", "Minerva Complex")
    itemAtlasMount = registerItem("ItemAtlasMount", "Atlas Mount")
    itemHermesBus = registerItem("ItemHermesBus", "Hermes Bus")
    itemHerculesDrive = registerItem("ItemHerculesDrive", "Hercules Drive")
    itemOrpheusProcessor = registerItem("ItemOrpheusProcessor", "Orpheus Processor")
    itemFemtoPlating = registerItem("ItemFemtoPlating", "Femto Plating")
    itemStyxValve = registerItem("ItemStyxValve", "Styx Valve")
    itemFemtoCoil = registerItem("ItemFemtoCoil", "Femto Coil")
    itemPhlegethonTunnelPrimer = registerItem("ItemPhlegethonTunnelPrimer", "Phlegethon Tunnel Primer")
    itemStellaratorPlating = registerItem("ItemStellaratorPlating", "Stellarator Plating")
    itemInfinitelyRecursiveALU = registerItem("ItemInfinitelyRecursiveALU", "Infinitely Recursive ALU")
    itemInfiniteVolumePolychora = registerItem("ItemInfiniteVolumePolychora", "Infinite Volume Polychora")
    itemNucleationCore = new ItemNucleationCore("ItemNucleationCore")
    GameRegistry.registerItem(itemNucleationCore, "ItemNucleationCore")
    LanguageRegistry.addName(itemNucleationCore, "Nucleation Core")
    itemInhibitionCore = new ItemInhibitionCore("ItemInhibitionCore")
    GameRegistry.registerItem(itemInhibitionCore, "ItemInhibitionCore")
    LanguageRegistry.addName(itemInhibitionCore, "Inhibition Core")
    itemQuantumSchematic = new ItemQuantumSchematic("ItemQuantumSchematic")
    GameRegistry.registerItem(itemQuantumSchematic, "ItemQuantumSchematic")
    LanguageRegistry.addName(itemQuantumSchematic, "Quantum Schematic")
    itemMicroTechnology = new ItemMicroTechnology().setUnlocalizedName("ItemMicroTechnology")
    GameRegistry.registerItem(itemMicroTechnology, "ItemMicroTechnology")
    LanguageRegistry.addName(itemMicroTechnology, "Micro Technology")
    itemNanoTechnology = new ItemNanoTechnology().setUnlocalizedName("ItemNanoTechnology")
    GameRegistry.registerItem(itemNanoTechnology, "ItemNanoTechnology")
    LanguageRegistry.addName(itemNanoTechnology, "Nano Technology")
    itemFemtoTechnology = new ItemFemtoTechnology().setUnlocalizedName("ItemFemtoTechnology")
    GameRegistry.registerItem(itemFemtoTechnology, "ItemFemtoTechnology")
    LanguageRegistry.addName(itemFemtoTechnology, "Femto Technology")
    itemPaperSchematic = new ItemPaperSchematic("ItemPaperSchematic")
    GameRegistry.registerItem(itemPaperSchematic, "ItemPaperSchematic")
    LanguageRegistry.addName(itemPaperSchematic, "Paper Schematic")
    itemInterfaceDeviceMicro = new ItemMicroInterfaceDevice().setUnlocalizedName("ItemInterfaceDeviceMicro")
    GameRegistry.registerItem(itemInterfaceDeviceMicro, "ItemInterfaceDeviceMicro")
    LanguageRegistry.addName(itemInterfaceDeviceMicro, "MicroInterface Device")
    itemInterfaceDeviceNano = new ItemNanoInterfaceDevice().setUnlocalizedName("ItemInterfaceDeviceNano")
    GameRegistry.registerItem(itemInterfaceDeviceNano, "ItemInterfaceDeviceNano")
    LanguageRegistry.addName(itemInterfaceDeviceNano, "NanoInterface Device")
    itemInterfaceDeviceFemto = new ItemFemtoInterfaceDevice().setUnlocalizedName("ItemInterfaceDeviceFemto")
    GameRegistry.registerItem(itemInterfaceDeviceFemto, "ItemInterfaceDeviceFemto")
    LanguageRegistry.addName(itemInterfaceDeviceFemto, "FemtoInterface Device")
    itemCubit = registerItem("ItemCubit", FemtocraftUtils.orangeify("Cubit"))
    ComponentRegistry.registerComponent(itemCubit, EnumTechLevel.FEMTO)
    itemRectangulon = registerItem("ItemRectangulon", FemtocraftUtils.orangeify("Rectangulon"))
    ComponentRegistry.registerComponent(itemRectangulon, EnumTechLevel.FEMTO)
    itemPlaneoid = registerItem("ItemPlaneoid", FemtocraftUtils.orangeify("Planeoid"))
    ComponentRegistry.registerComponent(itemPlaneoid, EnumTechLevel.FEMTO)
    itemCrystallite = registerItem("ItemCrystallite", FemtocraftUtils.greenify("Crystallite"))
    ComponentRegistry.registerComponent(itemCrystallite, EnumTechLevel.NANO)
    itemMineralite = registerItem("ItemMineralite", FemtocraftUtils.greenify("Mineralite"))
    ComponentRegistry.registerComponent(itemMineralite, EnumTechLevel.NANO)
    itemMetallite = registerItem("ItemMetallite", FemtocraftUtils.greenify("Metallite"))
    ComponentRegistry.registerComponent(itemMetallite, EnumTechLevel.NANO)
    itemFaunite = registerItem("ItemFaunite", FemtocraftUtils.greenify("Faunite"))
    ComponentRegistry.registerComponent(itemFaunite, EnumTechLevel.NANO)
    itemElectrite = registerItem("ItemElectrite", FemtocraftUtils.greenify("Electrite"))
    ComponentRegistry.registerComponent(itemElectrite, EnumTechLevel.NANO)
    itemFlorite = registerItem("ItemFlorite", FemtocraftUtils.greenify("Florite"))
    ComponentRegistry.registerComponent(itemFlorite, EnumTechLevel.NANO)
    itemMicroCrystal = registerItem("ItemMicroCrystal", FemtocraftUtils.blueify("Micro Crystal"))
    ComponentRegistry.registerComponent(itemMicroCrystal, EnumTechLevel.MICRO)
    itemProteinChain = registerItem("ItemProteinChain", FemtocraftUtils.blueify("Protein Chain"))
    ComponentRegistry.registerComponent(itemProteinChain, EnumTechLevel.MICRO)
    itemNerveCluster = registerItem("ItemNerveCluster", FemtocraftUtils.blueify("Nerve Cluster"))
    ComponentRegistry.registerComponent(itemNerveCluster, EnumTechLevel.MICRO)
    itemConductiveAlloy = registerItem("ItemConductiveAlloy", FemtocraftUtils.blueify("Conductive Alloy"))
    ComponentRegistry.registerComponent(itemConductiveAlloy, EnumTechLevel.MICRO)
    itemMetalComposite = registerItem("ItemMetalComposite", FemtocraftUtils.blueify("Metal Composite"))
    ComponentRegistry.registerComponent(itemMetalComposite, EnumTechLevel.MICRO)
    itemFibrousStrand = registerItem("ItemFibrousStrand", FemtocraftUtils.blueify("Fibrous Strand"))
    ComponentRegistry.registerComponent(itemFibrousStrand, EnumTechLevel.MICRO)
    itemMineralLattice = registerItem("ItemMineralLattice", FemtocraftUtils.blueify("Mineral Lattice"))
    ComponentRegistry.registerComponent(itemMineralLattice, EnumTechLevel.MICRO)
    itemFungalSpores = registerItem("ItemFungalSpores", FemtocraftUtils.blueify("Fungal Spores"))
    ComponentRegistry.registerComponent(itemFungalSpores, EnumTechLevel.MICRO)
    itemIonicChunk = registerItem("ItemIonicChunk", FemtocraftUtils.blueify("Ionic Chunk"))
    ComponentRegistry.registerComponent(itemIonicChunk, EnumTechLevel.MICRO)
    itemReplicatingMaterial = registerItem("ItemReplicatingMaterial", FemtocraftUtils.blueify("Replicating Material"))
    ComponentRegistry.registerComponent(itemReplicatingMaterial, EnumTechLevel.MICRO)
    itemSpinyFilament = registerItem("ItemSpinyFilament", FemtocraftUtils.blueify("Spiny Filament"))
    ComponentRegistry.registerComponent(itemSpinyFilament, EnumTechLevel.MICRO)
    itemHardenedBulb = registerItem("ItemHardenedBulb", FemtocraftUtils.blueify("Hardened Bulb"))
    ComponentRegistry.registerComponent(itemHardenedBulb, EnumTechLevel.MICRO)
    itemMorphicChannel = registerItem("ItemMorphicChannel", FemtocraftUtils.blueify("Morphic Channel"))
    ComponentRegistry.registerComponent(itemMorphicChannel, EnumTechLevel.MICRO)
    itemSynthesizedFiber = registerItem("ItemSynthesizedFiber", FemtocraftUtils.blueify("Synthesized Fiber"))
    ComponentRegistry.registerComponent(itemSynthesizedFiber, EnumTechLevel.MICRO)
    itemOrganometallicPlate = registerItem("ItemOrganometallicPlate", FemtocraftUtils.blueify("Organometallic Plate"))
    ComponentRegistry.registerComponent(itemOrganometallicPlate, EnumTechLevel.MICRO)
    itemMicroPlating = registerItem("ItemMicroPlating", "Micro Plating")
  }

  private def registerItem(unlocalizedName: String, name: String): Item = {
    val it = new ItemBase(unlocalizedName)
    LanguageRegistry.addName(it, name)
    GameRegistry.registerItem(it, unlocalizedName)
    it
  }

  def log(level: Level, msg: String) = FMLCommonHandler.instance().getFMLLogger.log(level, "[Femtocraft]: " + msg)

  @EventHandler def load(event: FMLInitializationEvent) {
  }

  @EventHandler def postInit(event: FMLPostInitializationEvent) {
    assemblerConfigs = new FemtocraftAssemblerConfig(recipeConfig)
    recipeManager.init()
    recipeManager.assemblyRecipes.registerDefaultRecipes()
    MagnetRegistry.init()
    new FemtocraftTechnologyConfig(technologyConfigFile).loadTechnologies()
    if (event.getSide eq Side.CLIENT) {
      researchManager.calculateGraph()
    }
  }

  @EventHandler def serverLoad(event: FMLServerStartingEvent) {
    event.registerServerCommand(femtocraftServerCommand)
  }
}


