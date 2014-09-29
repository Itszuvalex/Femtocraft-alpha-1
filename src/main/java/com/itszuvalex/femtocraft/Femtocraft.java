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
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.blocks.BlockFemtoStone;
import com.itszuvalex.femtocraft.blocks.BlockMicroStone;
import com.itszuvalex.femtocraft.blocks.BlockNanoStone;
import com.itszuvalex.femtocraft.blocks.BlockUnidentifiedAlloy;
import com.itszuvalex.femtocraft.command.CommandBase;
import com.itszuvalex.femtocraft.command.CommandFemtocraft;
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigAssembler;
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigTechnology;
import com.itszuvalex.femtocraft.configuration.FemtocraftConfigs;
import com.itszuvalex.femtocraft.core.MagnetRegistry;
import com.itszuvalex.femtocraft.core.fluids.BlockFluidMass;
import com.itszuvalex.femtocraft.core.fluids.FluidMass;
import com.itszuvalex.femtocraft.core.items.ItemBase;
import com.itszuvalex.femtocraft.core.items.ItemFemtoInterfaceDevice;
import com.itszuvalex.femtocraft.core.items.ItemMicroInterfaceDevice;
import com.itszuvalex.femtocraft.core.items.ItemNanoInterfaceDevice;
import com.itszuvalex.femtocraft.core.ore.*;
import com.itszuvalex.femtocraft.industry.blocks.*;
import com.itszuvalex.femtocraft.industry.items.ItemDigitalSchematic;
import com.itszuvalex.femtocraft.industry.items.ItemPaperSchematic;
import com.itszuvalex.femtocraft.industry.items.ItemQuantumSchematic;
import com.itszuvalex.femtocraft.managers.ManagerRecipe;
import com.itszuvalex.femtocraft.managers.assistant.ManagerAssistant;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
import com.itszuvalex.femtocraft.player.PlayerProperties;
import com.itszuvalex.femtocraft.power.blocks.*;
import com.itszuvalex.femtocraft.power.fluids.FluidCooledContaminatedMoltenSalt;
import com.itszuvalex.femtocraft.power.fluids.FluidCooledMoltenSalt;
import com.itszuvalex.femtocraft.power.fluids.FluidMoltenSalt;
import com.itszuvalex.femtocraft.power.items.ItemBlockMicroCube;
import com.itszuvalex.femtocraft.power.items.ItemInhibitionCore;
import com.itszuvalex.femtocraft.power.items.ItemNucleationCore;
import com.itszuvalex.femtocraft.power.plasma.BlockPlasma;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore;
import com.itszuvalex.femtocraft.power.tiles.TileEntityPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.proxy.ProxyClient;
import com.itszuvalex.femtocraft.proxy.ProxyCommon;
import com.itszuvalex.femtocraft.research.blocks.BlockResearchComputer;
import com.itszuvalex.femtocraft.research.blocks.BlockResearchConsole;
import com.itszuvalex.femtocraft.research.items.ItemFemtoTechnology;
import com.itszuvalex.femtocraft.research.items.ItemMicroTechnology;
import com.itszuvalex.femtocraft.research.items.ItemNanoTechnology;
import com.itszuvalex.femtocraft.research.items.ItemPortableResearchComputer;
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole;
import com.itszuvalex.femtocraft.transport.items.blocks.BlockVacuumTube;
import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube;
import com.itszuvalex.femtocraft.transport.liquids.blocks.BlockSuctionPipe;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.io.File;
import java.util.logging.Logger;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)
@NetworkMod(channels = {Femtocraft.ID, FemtocraftGuiHandler.PACKET_CHANNEL, FemtocraftSoundManager.PACKET_CHANNEL,
        PlayerProperties.PACKET_CHANNEL,
        ManagerResearch.RESEARCH_CHANNEL,
        TileEntityResearchConsole.PACKET_CHANNEL,
        TileEntityVacuumTube.PACKET_CHANNEL, TileEntityNanoFissionReactorCore.PACKET_CHANNEL,
        TileEntityPhlegethonTunnelCore.PACKET_CHANNEL},
        packetHandler = FemtocraftPacketHandler.class,
        clientSideRequired = true, serverSideRequired = true)
public class Femtocraft {
    public static final String ID = "Femtocraft";
    public static final String VERSION = "0.1.0";
    public static final String TECH_CONFIG_APPEND = "Technology";
    public static final String RECIPE_CONFIG_APPEND = "Recipes";

    @Instance(ID)
    public static Femtocraft instance;

    @SidedProxy(clientSide = "com.itszuvalex.femtocraft.proxy.ProxyClient",
            serverSide = "com.itszuvalex.femtocraft.proxy.ProxyCommon")
    public static ProxyCommon proxy;

    public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab(
            "Femtocraft");

    public static Logger logger;

    public static Configuration configFile;
    public static Configuration technologyConfigFile;
    public static Configuration recipeConfigFile;

    public static FemtocraftConfigAssembler assemblerConfigs;

    public static ManagerRecipe recipeManager;
    public static ManagerResearch researchManager;
    public static ManagerAssistant assistantManager;

    public static FemtocraftSoundManager soundManager;

    public static CommandBase femtocraftServerCommand;

    // blocks
    public static Block blockOreTitanium;
    public static Block blockOrePlatinum;
    public static Block blockOreThorium;
    public static Block blockOreFarenite;
    public static Block blockOreMalenite;
    public static Block blockOreLodestone;
    public static Block microStone;
    public static Block nanoStone;
    public static Block femtoStone;
    public static Block unidentifiedAlloy;
    public static Block blockResearchComputer;
    public static Block blockResearchConsole;
    public static Block generatorTest;
    public static Block consumerTest;
    public static Block blockMicroFurnaceUnlit;
    public static Block blockMicroFurnaceLit;
    public static Block blockMicroDeconstructor;
    public static Block blockMicroReconstructor;
    public static Block blockEncoder;
    public static Block blockNanoInnervatorUnlit;
    public static Block blockNanoInnervatorLit;
    public static Block blockNanoDismantler;
    public static Block blockNanoFabricator;
    public static Block blockNanoEnmesher;
    public static Block blockNanoHorologe;
    public static Block blockFemtoImpulserLit;
    public static Block blockFemtoImpulserUnlit;
    public static Block blockFemtoRepurposer;
    public static Block blockFemtoCoagulator;
    public static Block blockFemtoEntangler;
    public static Block blockFemtoChronoshifter;
    public static Block blockMicroCube;
    public static Block blockNanoCubeFrame;
    public static Block blockNanoCubePort;
    public static Block blockFemtoCubePort;
    public static Block blockFemtoCubeFrame;
    public static Block blockFemtoCubeChassis;
    public static Block blockVacuumTube;
    public static Block blockSuctionPipe;
    public static Block blockMicroChargingBase;
    public static Block blockMicroChargingCoil;
    public static Block blockMicroChargingCapacitor;
    public static Block blockMagneticInductionGenerator;
    public static Block blockOrbitalEqualizer;
    public static Block blockNullEqualizer;
    public static Block blockCryoEndothermalChargingBase;
    public static Block blockCryoEndothermalChargingCoil;
    public static Block blockFissionReactorCore;
    public static Block blockFissionReactorHousing;
    public static Block blockMagnetohydrodynamicGenerator;
    public static Block blockSteamGenerator;
    public static Block blockDecontaminationChamber;
    public static Block blockPhlegethonTunnelCore;
    public static Block blockPhlegethonTunnelFrame;
    public static Block blockSisyphusStabilizer;
    public static Block blockStellaratorCore;
    public static Block blockStellaratorFocus;
    public static Block blockStellaratorOpticalMaser;
    public static Block blockStellaratorHousing;
    public static Block blockPlasmaConduit;
    public static Block blockPlasmaVent;
    public static Block blockPlasmaTurbine;
    public static Block blockPlasmaCondenser;

    // cables
    public static Block blockMicroCable;
    public static Block blockNanoCable;
    public static Block blockFemtoCable;

    // liquids
    public static Fluid fluidMass;
    public static BlockFluidMass blockFluidMass;

    public static Fluid fluidMoltenSalt;
    public static BlockFluidMoltenSalt blockFluidMoltenSalt;

    public static Fluid fluidCooledMoltenSalt;
    public static BlockFluidCooledMoltenSalt blockFluidCooledMoltenSalt;

    public static Fluid fluidCooledContaminatedMoltenSalt;
    public static BlockFluidCooledContaminatedMoltenSalt blockFluidCooledContaminatedMoltenSalt;

    // plasma
    public static Block blockPlasma;

    // items

    //ores
    public static Item itemIngotTitanium;
    public static Item itemIngotPlatinum;
    public static Item itemIngotThorium;
    public static Item itemIngotFarenite;
    public static Item itemIngotMalenite;
    public static Item itemIngotTemperedTitanium;
    public static Item itemIngotThFaSalt;
    public static Item itemNuggetLodestone;
    public static Item itemChunkLodestone;

    //micro
    public static Item itemConductivePowder;
    public static Item itemBoard;
    public static Item itemPrimedBoard;
    public static Item itemDopedBoard;
    public static Item itemMicrochip;

    public static Item itemSpool;
    public static Item itemSpoolGold;
    public static Item itemSpoolPlatinum;

    public static Item itemMicroCoil;
    public static Item itemBattery;

    public static Item itemMicroPlating;

    public static Item itemMicroLogicCore;
    public static Item itemKineticPulverizer;
    public static Item itemHeatingElement;
    public static Item itemArticulatingArm;
    public static Item itemDissassemblyArray;
    public static Item itemAssemblyArray;

    public static Item itemVacuumCore;

    public static Item itemNucleationCore;
    public static Item itemInhibitionCore;

    public static Item itemPortableResearchComputer;

    public static Item itemMicroTechnology;
    public static Item itemNanoTechnology;
    public static Item itemFemtoTechnology;

    public static Item itemPaperSchematic;

    public static Item itemInterfaceDeviceMicro;

    //nano
    public static Item itemNanochip;
    public static Item itemNanoCalculator;
    public static Item itemNanoRegulator;
    public static Item itemNanoSimulator;

    public static Item itemBasicAICore;
    public static Item itemLearningCore;
    public static Item itemSchedulerCore;
    public static Item itemManagerCore;

    public static Item itemFluidicConductor;
    public static Item itemNanoCoil;

    public static Item itemNanoPlating;

    public static Item itemTemporalResonator;
    public static Item itemDimensionalMonopole;

    public static Item itemSelfFulfillingOracle;
    public static Item itemCrossDimensionalCommunicator;
    public static Item itemInfallibleEstimator;
    public static Item itemPanLocationalComputer;
    public static Item itemPandoraCube;

    public static Item itemFissionReactorPlating;

    public static Item itemDigitalSchematic;

    public static Item itemInterfaceDeviceNano;

    //femto

    public static Item itemMinosGate;
    public static Item itemCharosGate;
    public static Item itemCerberusGate;

    public static Item itemErinyesCircuit;
    public static Item itemMinervaComplex;

    public static Item itemAtlasMount;
    public static Item itemHermesBus;
    public static Item itemHerculesDrive;
    public static Item itemOrpheusProcessor;

    public static Item itemFemtoPlating;

    public static Item itemStyxValve;
    public static Item itemFemtoCoil;

    public static Item itemPhlegethonTunnelPrimer;

    public static Item itemStellaratorPlating;

    public static Item itemInfinitelyRecursiveALU;
    public static Item itemInfiniteVolumePolychora;

    public static Item itemQuantumSchematic;

    public static Item itemInterfaceDeviceFemto;

    // Decomp items
    // Femto
    public static Item itemCubit;
    public static Item itemRectangulon;
    public static Item itemPlaneoid;
    // Nano
    public static Item itemCrystallite;
    public static Item itemMineralite;
    public static Item itemMetallite;
    public static Item itemFaunite;
    public static Item itemElectrite;
    public static Item itemFlorite;
    // Micro
    public static Item itemMicroCrystal;
    public static Item itemProteinChain;
    public static Item itemNerveCluster;
    public static Item itemConductiveAlloy;
    public static Item itemMetalComposite;
    public static Item itemFibrousStrand;
    public static Item itemMineralLattice;
    public static Item itemFungalSpores;
    public static Item itemIonicChunk;
    public static Item itemReplicatingMaterial;
    public static Item itemSpinyFilament;
    public static Item itemHardenedBulb;
    public static Item itemMorphicChannel;
    public static Item itemSynthesizedFiber;
    public static Item itemOrganometallicPlate;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = Logger.getLogger(ID);
        logger.setParent(FMLLog.getLogger());

        recipeManager = new ManagerRecipe();
        researchManager = new ManagerResearch();
        assistantManager = new ManagerAssistant();

        femtocraftServerCommand = new CommandFemtocraft();

        File suggestedConfig = event.getSuggestedConfigurationFile();
        configFile = new Configuration(
                suggestedConfig);
        FemtocraftConfigs.load(configFile);
        String[] suggestConfigName = suggestedConfig.getName().split("\\.");
        technologyConfigFile = new Configuration(new File(suggestedConfig.getParentFile(),
                suggestConfigName[0] + TECH_CONFIG_APPEND + "." + suggestConfigName[1]));
        recipeConfigFile = new Configuration(new File(suggestedConfig.getParentFile(), suggestConfigName[0] +
                RECIPE_CONFIG_APPEND + "." +
                suggestConfigName[1]));

        Femtocraft.proxy.registerTileEntities();
        Femtocraft.proxy.registerRendering();
        Femtocraft.proxy.registerBlockRenderers();
        Femtocraft.proxy.registerTickHandlers();


        if (event.getSide() == Side.CLIENT) {
            soundManager = new FemtocraftSoundManager();
            MinecraftForge.EVENT_BUS.register(soundManager);
        }

        GameRegistry.registerPlayerTracker(new FemtocraftPlayerTracker());

        NetworkRegistry.instance().registerGuiHandler(this,
                new FemtocraftGuiHandler());
        NetworkRegistry.instance().registerConnectionHandler(
                new FemtocraftConnectionHandler());
        MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer());
        MinecraftForge.EVENT_BUS.register(new FemtocraftOreRetrogenHandler());


        //Do normal load things here, to allow followup mods to access this.

        proxy.registerRendering();

        if (FemtocraftConfigs.worldGen) {
            GameRegistry.registerWorldGenerator(new FemtocraftOreGenerator());
        }

        // Change the creative tab name
        LanguageRegistry.instance().addStringLocalization(
                "itemGroup.Femtocraft", "en_US", "Femtocraft");

        // blocks

        blockOreTitanium = new BlockOreTitanium(FemtocraftConfigs.BlockOreTitaniumID).setUnlocalizedName
                ("BlockOreTitanium");
        MinecraftForge.setBlockHarvestLevel(blockOreTitanium, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreTitanium, "BlockOreTitanium");
        LanguageRegistry.addName(blockOreTitanium, "Titanium Ore");
        if (FemtocraftConfigs.registerTitaniumOreInOreDictionary) {
            OreDictionary
                    .registerOre("oreTitanium", new ItemStack(blockOreTitanium));
        }

        blockOrePlatinum = new BlockOrePlatinum(FemtocraftConfigs.BlockOrePlatinumID).setUnlocalizedName
                ("BlockOrePlatinum");
        MinecraftForge.setBlockHarvestLevel(blockOrePlatinum, "pickaxe", 2);
        GameRegistry.registerBlock(blockOrePlatinum, "BlockOrePlatinum");
        LanguageRegistry.addName(blockOrePlatinum, "Platinum Ore");
        if (FemtocraftConfigs.registerPlatinumOreInOreDictionary) {
            OreDictionary
                    .registerOre("orePlatinum", new ItemStack(blockOrePlatinum));
        }

        blockOreThorium = new BlockOreThorium(FemtocraftConfigs.BlockOreThoriumID).setUnlocalizedName
                ("BlockOreThorium");
        MinecraftForge.setBlockHarvestLevel(blockOreThorium, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreThorium, "BlockOreThorium");
        LanguageRegistry.addName(blockOreThorium, "Thorium Ore");
        if (FemtocraftConfigs.registerThoriumOreInOreDictionary) {
            OreDictionary.registerOre("oreThorium", new ItemStack(blockOreThorium));
        }

        blockOreFarenite = new BlockOreFarenite(FemtocraftConfigs.BlockOreFareniteID).setUnlocalizedName
                ("BlockOreFarenite");
        MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreFarenite, "BlockOreFarenite");
        LanguageRegistry.addName(blockOreFarenite, "Farenite Ore");
        if (FemtocraftConfigs.registerFareniteOreInOreDictionary) {
            OreDictionary
                    .registerOre("oreFarenite", new ItemStack(blockOreFarenite));
        }

        blockOreMalenite = new BlockOreMalenite(FemtocraftConfigs.BlockOreMaleniteID).setUnlocalizedName
                ("BlockOreMalenite");
        MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 3);
        GameRegistry.registerBlock(blockOreMalenite, "BlockOreMalenite");
        LanguageRegistry.addName(blockOreMalenite, "Malenite Ore");
        if (FemtocraftConfigs.registerMaleniteOreInOreDictionary) {
            OreDictionary
                    .registerOre("oreMalenite", new ItemStack(blockOreMalenite));
        }

        blockOreLodestone = new BlockOreLodestone(FemtocraftConfigs.BlockOreLodestoneID).setUnlocalizedName
                ("BlockOreLodestone");
        MinecraftForge.setBlockHarvestLevel(blockOreLodestone, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreLodestone, "BlockOreLodestone");
        LanguageRegistry.addName(blockOreLodestone, "Lodestone Ore");
        if (FemtocraftConfigs.registerLodestoneOreInOreDictionary) {
            OreDictionary.registerOre("oreLodestone", new ItemStack(blockOreLodestone));
        }

        nanoStone = new BlockNanoStone(FemtocraftConfigs.BlockNanoStoneID).setUnlocalizedName("BlockNanoStone");
        GameRegistry.registerBlock(nanoStone, "BlockNanoStone");
        LanguageRegistry.addName(nanoStone, "Nanostone");

        microStone = new BlockMicroStone(FemtocraftConfigs.BlockMicroStoneID).setUnlocalizedName("BlockMicroStone");
        GameRegistry.registerBlock(microStone, "BlockMicroStone");
        LanguageRegistry.addName(microStone, "Microstone");

        femtoStone = new BlockFemtoStone(FemtocraftConfigs.BlockFemtoStoneID).setUnlocalizedName("BlockFemtoStone");
        GameRegistry.registerBlock(femtoStone, "BlockFemtoStone");
        LanguageRegistry.addName(femtoStone, "Femtostone");

        unidentifiedAlloy = new BlockUnidentifiedAlloy(
                FemtocraftConfigs.BlockUnidentifiedAlloyID).setUnlocalizedName("BlockUnidentifiedAlloy");
        GameRegistry.registerBlock(unidentifiedAlloy, "BlockUnidentifiedAlloy");
        LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");

        blockResearchComputer = new BlockResearchComputer(
                FemtocraftConfigs.BlockResearchComputerID).setUnlocalizedName("BlockResearchComputer");
        GameRegistry.registerBlock(blockResearchComputer, "BlockResearchComputer");
        LanguageRegistry.addName(blockResearchComputer, "Research Computer");

        blockResearchConsole = new BlockResearchConsole(
                FemtocraftConfigs.BlockResearchConsoleID).setUnlocalizedName("BlockResearchConsole");
        GameRegistry.registerBlock(blockResearchConsole, "BlockResearchConsole");
        LanguageRegistry.addName(blockResearchConsole, "Research Console");

        blockMicroCable = new BlockMicroCable(FemtocraftConfigs.BlockMicroCableID,
                Material.iron).setUnlocalizedName("BlockMicroCable");
        GameRegistry.registerBlock(blockMicroCable, "BlockMicroCable");
        LanguageRegistry.addName(blockMicroCable, "Micro-Cable");

        blockNanoCable = new BlockNanoCable(FemtocraftConfigs.BlockNanoCableID,
                Material.iron).setUnlocalizedName("BlockNanoCable");
        GameRegistry.registerBlock(blockNanoCable, "BlockNanoCable");
        LanguageRegistry.addName(blockNanoCable, "Nano-Cable");

        blockFemtoCable = new BlockFemtoCable(FemtocraftConfigs.BlockFemtoCableID,
                Material.iron).setUnlocalizedName("BlockFemtoCable");
        GameRegistry.registerBlock(blockFemtoCable, "BlockFemtoCable");
        LanguageRegistry.addName(blockFemtoCable, "Femto-Cable");

        generatorTest = new BlockGenerator(
                FemtocraftConfigs.BlockGeneratorTestID, Material.iron)
                .setUnlocalizedName("BlockGenerator").setHardness(3.5f)
                .setStepSound(Block.soundStoneFootstep);
        GameRegistry.registerBlock(generatorTest, "BlockGenerator");
        LanguageRegistry.addName(generatorTest, "Generator");

        consumerTest = new BlockConsumer(
                FemtocraftConfigs.BlockConsumerTestID, Material.iron)
                .setUnlocalizedName("BlockConsumer").setHardness(3.5f)
                .setStepSound(Block.soundStoneFootstep);
        GameRegistry.registerBlock(consumerTest, "BlockConsumer");
        LanguageRegistry.addName(consumerTest, "Consumer");

        blockMicroFurnaceUnlit = new BlockMicroFurnace(
                FemtocraftConfigs.BlockMicroFurnaceUnlitID, false).setUnlocalizedName("BlockMicroFurnace");
        GameRegistry.registerBlock(blockMicroFurnaceUnlit, "BlockMicroFurnace");
        LanguageRegistry.addName(blockMicroFurnaceUnlit, "Micro-Furnace");

        blockMicroFurnaceLit = new BlockMicroFurnace(
                FemtocraftConfigs.BlockMicroFurnaceLitID, true);

        blockMicroDeconstructor = new BlockMicroDeconstructor(
                FemtocraftConfigs.BlockMicroDeconstructorID).setUnlocalizedName("BlockMicroDeconstructor");
        GameRegistry.registerBlock(blockMicroDeconstructor,
                "BlockMicroDeconstructor");
        LanguageRegistry.addName(blockMicroDeconstructor, "Microtech Deconstructor");

        blockMicroReconstructor = new BlockMicroReconstructor(
                FemtocraftConfigs.BlockMicroReconstructorID).setUnlocalizedName("BlockMicroReconstructor");
        GameRegistry.registerBlock(blockMicroReconstructor,
                "BlockMicroReconstructor");
        LanguageRegistry.addName(blockMicroReconstructor, "Microtech Reconstructor");

        blockEncoder = new BlockEncoder(FemtocraftConfigs.BlockMicroEncoderID).setUnlocalizedName("BlockEncoder");
        GameRegistry.registerBlock(blockEncoder, "BlockEncoder");
        LanguageRegistry.addName(blockEncoder, "Schematic Encoder");

        blockNanoInnervatorUnlit = new BlockNanoInnervator(
                FemtocraftConfigs.BlockNanoInnervatorUnlitID, false).setUnlocalizedName("BlockNanoInnervator");
        GameRegistry.registerBlock(blockNanoInnervatorUnlit, "BlockNanoInnervator");
        LanguageRegistry.addName(blockNanoInnervatorUnlit, "Nano Innervator");

        blockNanoInnervatorLit = new BlockNanoInnervator(
                FemtocraftConfigs.BlockNanoInnervatorLitID, true);

        blockNanoDismantler = new BlockNanoDismantler(
                FemtocraftConfigs.BlockNanoDismantlerID).setUnlocalizedName("BlockNanoDismantler");
        GameRegistry.registerBlock(blockNanoDismantler, "BlockNanoDismantler");
        LanguageRegistry.addName(blockNanoDismantler, "Nano Dismantler");

        blockNanoFabricator = new BlockNanoFabricator(FemtocraftConfigs
                .BlockNanoFabricatorID).setUnlocalizedName("BlockNanoFabricator");
        GameRegistry.registerBlock(blockNanoFabricator, "BlockNanoFabricator");
        LanguageRegistry.addName(blockNanoFabricator, "Nano Fabricator");

        blockNanoEnmesher = new BlockNanoEnmesher(FemtocraftConfigs
                .BlockNanoEnmesherID).setUnlocalizedName("BlockNanoEnmesher");
        GameRegistry.registerBlock(blockNanoEnmesher, "BlockNanoEnmesher");
        LanguageRegistry.addName(blockNanoEnmesher, "Nano Enmesher");

        blockNanoHorologe = new BlockNanoHorologe(FemtocraftConfigs
                .BlockNanoHorologeID).setUnlocalizedName("BlockNanoHorologe");
        GameRegistry.registerBlock(blockNanoHorologe, "BlockNanoHorologe");
        LanguageRegistry.addName(blockNanoHorologe, "Nano Horologe");

        blockFemtoImpulserUnlit = new BlockFemtoImpulser(
                FemtocraftConfigs.BlockFemtoImpulserUnlitID, false).setUnlocalizedName("BlockFemtoImpulser");
        GameRegistry.registerBlock(blockFemtoImpulserUnlit, "BlockFemtoImpulser");
        LanguageRegistry.addName(blockFemtoImpulserUnlit, "Femto Impulser");

        blockFemtoImpulserLit = new BlockFemtoImpulser(
                FemtocraftConfigs.BlockFemtoImpulserLitID, true);

        blockFemtoRepurposer = new BlockFemtoRepurposer(
                FemtocraftConfigs.BlockFemtoRepurposerID).setUnlocalizedName("BlockFemtoRepurposer");
        GameRegistry.registerBlock(blockFemtoRepurposer, "BlockFemtoRepurposer");
        LanguageRegistry.addName(blockFemtoRepurposer, "Femto Repurposer");

        blockFemtoCoagulator = new BlockFemtoCoagulator(FemtocraftConfigs
                .BlockFemtoCoagulatorID).setUnlocalizedName("BlockFemtoCoagulator");
        GameRegistry.registerBlock(blockFemtoCoagulator, "BlockFemtoCoagulator");
        LanguageRegistry.addName(blockFemtoCoagulator, "Femto Coagulator");

        blockFemtoEntangler = new BlockFemtoEntangler(FemtocraftConfigs
                .BlockFemtoEntanglerID).setUnlocalizedName("BlockFemtoEntangler");
        GameRegistry.registerBlock(blockFemtoEntangler, "BlockFemtoEntangler");
        LanguageRegistry.addName(blockFemtoEntangler, "Femto Entangler");

        blockFemtoChronoshifter = new BlockFemtoChronoshifter(FemtocraftConfigs
                .BlockFemtoChronoshifterID).setUnlocalizedName("BlockFemtoChronoshifter");
        GameRegistry.registerBlock(blockFemtoChronoshifter, "BlockFemtoChronoshifter");
        LanguageRegistry.addName(blockFemtoChronoshifter, "Femto Chronoshifter");

        blockMicroCube = new BlockMicroCube(FemtocraftConfigs.BlockMicroCubeID).setUnlocalizedName("BlockMicroCube");
        GameRegistry.registerBlock(blockMicroCube, ItemBlockMicroCube.class,
                "BlockMicroCube");
        LanguageRegistry.addName(blockMicroCube, "Micro-Cube");

        blockNanoCubeFrame = new BlockNanoCubeFrame(
                FemtocraftConfigs.BlockNanoCubeFrameID).setUnlocalizedName("BlockNanoCubeFrame");
        GameRegistry.registerBlock(blockNanoCubeFrame, "BlockNanoCubeFrame");
        LanguageRegistry.addName(blockNanoCubeFrame, "Nano-Cube Frame");

        blockNanoCubePort = new BlockNanoCubePort(
                FemtocraftConfigs.BlockNanoCubePortID).setUnlocalizedName("BlockNanoCubePort");
        GameRegistry.registerBlock(blockNanoCubePort, "BlockNanoCubePort");
        LanguageRegistry.addName(blockNanoCubePort, "Nano-Cube Port");

        blockFemtoCubePort = new BlockFemtoCubePort(
                FemtocraftConfigs.BlockFemtoCubePortID).setUnlocalizedName("BlockFemtoCubePort");
        GameRegistry.registerBlock(blockFemtoCubePort, "BlockFemtoCubePort");
        LanguageRegistry.addName(blockFemtoCubePort, "Femto-Cube Port");

        blockFemtoCubeFrame = new BlockFemtoCubeFrame(
                FemtocraftConfigs.BlockFemtoCubeFrameID).setUnlocalizedName("BlockFemtoCubeFrame");
        GameRegistry.registerBlock(blockFemtoCubeFrame, "BlockFemtoCubeFrame");
        LanguageRegistry.addName(blockFemtoCubeFrame, "Femto-Cube Frame");

        blockFemtoCubeChassis = new BlockFemtoCubeChassis(
                FemtocraftConfigs.BlockFemtoCubeChassisID).setUnlocalizedName("BlockFemtoCubeChassis");
        GameRegistry.registerBlock(blockFemtoCubeChassis, "BlockFemtoCubeChassis");
        LanguageRegistry.addName(blockFemtoCubeChassis, "Femto-Cube Chassis");

        blockVacuumTube = new BlockVacuumTube(
                FemtocraftConfigs.BlockVacuumTubeID).setUnlocalizedName("BlockVacuumTube");
        GameRegistry.registerBlock(blockVacuumTube, "BlockVacuumTube");
        LanguageRegistry.addName(blockVacuumTube, "Vacuum Tube");

        blockSuctionPipe = new BlockSuctionPipe(
                FemtocraftConfigs.BlockSuctionPipeID).setUnlocalizedName("BlockSuctionPipe");
        GameRegistry.registerBlock(blockSuctionPipe, "BlockSuctionPipe");
        LanguageRegistry.addName(blockSuctionPipe, "Suction Pipe");

        blockMicroChargingBase = new BlockAtmosphericChargingBase(
                FemtocraftConfigs.BlockMicroChargingBaseID).setUnlocalizedName("BlockBaseMicroCharging");
        GameRegistry.registerBlock(blockMicroChargingBase, "BlockBaseMicroCharging");
        LanguageRegistry.addName(blockMicroChargingBase,
                "Electrostatic Charging Base");

        blockMicroChargingCoil = new BlockAtmosphericChargingCoil(
                FemtocraftConfigs.BlockMicroChargingCoilID).setUnlocalizedName("BlockCoilMicroCharging");
        GameRegistry.registerBlock(blockMicroChargingCoil, "BlockCoilMicroCharging");
        LanguageRegistry.addName(blockMicroChargingCoil,
                "Electrostatic Charging Coil");

        blockMicroChargingCapacitor = new BlockAtmosphericChargingCapacitor
                (FemtocraftConfigs.BlockMicroChargingCapacitorID).setUnlocalizedName
                ("BlockAtmosphericChargingCapacitor");
        GameRegistry.registerBlock(blockMicroChargingCapacitor,
                "BlockAtmosphericChargingCapacitor");
        LanguageRegistry.addName(blockMicroChargingCapacitor,
                "Electrostatic Charging Capacitor");

        blockMagneticInductionGenerator = new BlockMagneticInductionGenerator(FemtocraftConfigs
                .BlockMagneticInductionGeneratorID).setUnlocalizedName("BlockMagneticInductionGenerator");
        GameRegistry.registerBlock(blockMagneticInductionGenerator,
                "BlockMagneticInductionGenerator");
        LanguageRegistry.addName(blockMagneticInductionGenerator, "Magnetic Induction Generator");

        blockOrbitalEqualizer = new BlockOrbitalEqualizer(
                FemtocraftConfigs.BlockOrbitalEqualizerID).setUnlocalizedName("BlockOrbitalEqualizer");
        GameRegistry.registerBlock(blockOrbitalEqualizer, "BlockOrbitalEqualizer");
        LanguageRegistry.addName(blockOrbitalEqualizer, "Orbital Equalizer");

        blockCryoEndothermalChargingBase = new BlockCryoEndothermalChargingBase
                (FemtocraftConfigs.BlockCryoEndothermalChargingBaseID).setUnlocalizedName
                ("BlockCryoEndothermalChargingBase");
        GameRegistry.registerBlock(blockCryoEndothermalChargingBase,
                "BlockCryoEndothermalChargingBase");
        LanguageRegistry.addName(blockCryoEndothermalChargingBase,
                "CryoEndothermal Charging Base");

        blockCryoEndothermalChargingCoil = new BlockCryoEndothermalChargingCoil
                (FemtocraftConfigs.BlockCryoEndothermalChargingCoilID).setUnlocalizedName
                ("BlockCryoEndothermalChargingCoil");
        GameRegistry.registerBlock(blockCryoEndothermalChargingCoil,
                "BlockCryoEndothermalChargingCoil");
        LanguageRegistry.addName(blockCryoEndothermalChargingCoil,
                "CryoEndothermal Charging Coil");

        blockFissionReactorCore = new BlockNanoFissionReactorCore
                (FemtocraftConfigs.BlockFissionReactorCoreID).setUnlocalizedName("BlockFissionReactorCore");
        GameRegistry.registerBlock(blockFissionReactorCore,
                "BlockFissionReactorCore");
        LanguageRegistry.addName(blockFissionReactorCore,
                "Fission Reactor Core");

        blockFissionReactorHousing = new BlockNanoFissionReactorHousing
                (FemtocraftConfigs.BlockFissionReactorHousingID).setUnlocalizedName("BlockFissionReactorHousing");
        GameRegistry.registerBlock(blockFissionReactorHousing,
                "BlockFissionReactorHousing");
        LanguageRegistry.addName(blockFissionReactorHousing,
                "Fission Reactor Housing");

        blockMagnetohydrodynamicGenerator = new BlockMagnetohydrodynamicGenerator(FemtocraftConfigs
                .BlockMagnetohydrodynamicGeneratorID).setUnlocalizedName("BlockMagnetohydrodynamicGenerator");
        GameRegistry.registerBlock(blockMagnetohydrodynamicGenerator, "BlockMagnetohydrodynamicGenerator");
        LanguageRegistry.addName(blockMagnetohydrodynamicGenerator, "Magnetohydrodynamic Generator");

        blockSteamGenerator = new BlockSteamGenerator(FemtocraftConfigs.BlockSteamGeneratorID).setUnlocalizedName
                ("BlockSteamGenerator");
        GameRegistry.registerBlock(blockSteamGenerator, "BlockSteamGenerator");
        LanguageRegistry.addName(blockSteamGenerator, "Steam Generator");

        blockDecontaminationChamber = new BlockDecontaminationChamber
                (FemtocraftConfigs.BlockDecontaminationChamberID).setUnlocalizedName("BlockDecontaminationChamber");
        GameRegistry.registerBlock(blockDecontaminationChamber,
                "BlockDecontaminationChamber");
        LanguageRegistry.addName(blockDecontaminationChamber,
                "Decontamination Chamber");

        blockPhlegethonTunnelCore = new BlockPhlegethonTunnelCore
                (FemtocraftConfigs.BlockPhlegethonTunnelCoreID).setUnlocalizedName("BlockPhlegethonTunnelCore");
        GameRegistry.registerBlock(blockPhlegethonTunnelCore,
                "BlockPhlegethonTunnelCore");
        LanguageRegistry.addName(blockPhlegethonTunnelCore,
                "Phlegethon Tunnel Core");

        blockPhlegethonTunnelFrame = new BlockPhlegethonTunnelFrame
                (FemtocraftConfigs.BlockPhlegethonTunnelFrameID).setUnlocalizedName("BlockPhlegethonTunnelFrame");
        GameRegistry.registerBlock(blockPhlegethonTunnelFrame,
                "BlockPhlegethonTunnelFrame");
        LanguageRegistry.addName(blockPhlegethonTunnelFrame,
                "Phlegethon Tunnel Frame");

        blockSisyphusStabilizer = new BlockSisyphusStabilizer
                (FemtocraftConfigs.BlockSisyphusStabilizerID).setUnlocalizedName("BlockSisyphusStabilizer");
        GameRegistry.registerBlock(blockSisyphusStabilizer,
                "BlockSisyphusStabilizer");
        LanguageRegistry.addName(blockSisyphusStabilizer,
                "Sisyphus Stabilizer");

        blockNullEqualizer = new BlockNullEqualizer(
                FemtocraftConfigs.BlockNullEqualizerID).setUnlocalizedName("BlockNullEqualizer");
        GameRegistry.registerBlock(blockNullEqualizer, "BlockNullEqualizer");
        LanguageRegistry.addName(blockNullEqualizer, "Null-Energy Equalizer");

        blockStellaratorCore = new BlockFemtoStellaratorCore(FemtocraftConfigs
                .BlockFemtoStelleratorCoreID).setUnlocalizedName("BlockStellaratorCore");
        GameRegistry.registerBlock(blockStellaratorCore, "BlockStellaratorCore");
        LanguageRegistry.addName(blockStellaratorCore, "Stellarator Core");

        blockStellaratorFocus = new BlockFemtoStellaratorFocus(FemtocraftConfigs
                .BlockFemtoStelleratorFocusID).setUnlocalizedName("BlockStellaratorFocus");
        GameRegistry.registerBlock(blockStellaratorFocus, "BlockStellaratorFocus");
        LanguageRegistry.addName(blockStellaratorFocus, "Stellarator Focus");

        blockStellaratorOpticalMaser = new BlockFemtoStellaratorOpticalMaser
                (FemtocraftConfigs.BlockFemtoStellaratorOpticalMaserID).setUnlocalizedName
                ("BlockStellaratorOpticalMaser");
        GameRegistry.registerBlock(blockStellaratorOpticalMaser,
                "BlockStellaratorOpticalMaser");
        LanguageRegistry.addName(blockStellaratorOpticalMaser,
                "Stellarator Optical Maser");

        blockStellaratorHousing = new BlockFemtoStellaratorHousing
                (FemtocraftConfigs.BlockFemtoStellaratorHousingID).setUnlocalizedName("BlockStellaratorHousing");
        GameRegistry.registerBlock(blockStellaratorHousing,
                "BlockStellaratorHousing");
        LanguageRegistry.addName(blockStellaratorHousing, "Stellarator Housing");

        blockPlasmaConduit = new BlockPlasmaConduit(FemtocraftConfigs
                .BlockPlasmaConduitID).setUnlocalizedName("BlockPlasmaConduit");
        GameRegistry.registerBlock(blockPlasmaConduit, "BlockPlasmaConduit");
        LanguageRegistry.addName(blockPlasmaConduit, "Plasma Conduit");

        blockPlasmaVent = new BlockPlasmaVent(FemtocraftConfigs
                .BlockPlasmaVentID).setUnlocalizedName("BlockPlasmaVent");
        GameRegistry.registerBlock(blockPlasmaVent, "BlockPlasmaVent");
        LanguageRegistry.addName(blockPlasmaVent, "Plasma Vent");

        blockPlasmaTurbine = new BlockPlasmaTurbine(FemtocraftConfigs
                .BlockPlasmaTurbineID).setUnlocalizedName("BlockPlasmaTurbine");
        GameRegistry.registerBlock(blockPlasmaTurbine, "BlockPlasmaTurbine");
        LanguageRegistry.addName(blockPlasmaTurbine, "Plasma Turbine");

        blockPlasmaCondenser = new BlockPlasmaCondenser(FemtocraftConfigs
                .BlockPlasmaCondenserID).setUnlocalizedName("BlockPlasmaCondenser");
        GameRegistry.registerBlock(blockPlasmaCondenser,
                "BlockPlasmaCondenser");
        LanguageRegistry.addName(blockPlasmaCondenser, "Plasma Condenser");

        // Liquids
        fluidMass = new FluidMass();
        FluidRegistry.registerFluid(fluidMass);

        blockFluidMass = new BlockFluidMass(FemtocraftConfigs.BlockMassID);
        blockFluidMass.setUnlocalizedName("BlockMass");
        GameRegistry.registerBlock(blockFluidMass, "BlockMass");
        LanguageRegistry.addName(blockFluidMass, "Mass");

        fluidMoltenSalt = new FluidMoltenSalt();
        FluidRegistry.registerFluid(fluidMoltenSalt);

        blockFluidMoltenSalt = new BlockFluidMoltenSalt(FemtocraftConfigs.BlockFluidMoltenSaltID);
        blockFluidMoltenSalt.setUnlocalizedName("BlockFluidMoltenSalt");
        GameRegistry.registerBlock(blockFluidMoltenSalt, "BlockFluidMoltenSalt");
        LanguageRegistry.addName(blockFluidMoltenSalt, "Molten Salt");

        fluidCooledMoltenSalt = new FluidCooledMoltenSalt();
        FluidRegistry.registerFluid(fluidCooledMoltenSalt);

        blockFluidCooledMoltenSalt = new BlockFluidCooledMoltenSalt(FemtocraftConfigs.BlockFluidCooledMoltenSaltID);
        blockFluidCooledMoltenSalt.setUnlocalizedName("BlockFluidCooledMoltenSalt");
        GameRegistry.registerBlock(blockFluidCooledMoltenSalt, "BlockFluidCooledMoltenSalt");
        LanguageRegistry.addName(blockFluidCooledMoltenSalt, "Cooled Molten Salt");

        fluidCooledContaminatedMoltenSalt = new FluidCooledContaminatedMoltenSalt();
        FluidRegistry.registerFluid(fluidCooledContaminatedMoltenSalt);

        blockFluidCooledContaminatedMoltenSalt = new BlockFluidCooledContaminatedMoltenSalt(FemtocraftConfigs
                .BlockFluidCooledContaminatedMoltenSaltID);
        blockFluidCooledContaminatedMoltenSalt.setUnlocalizedName("BlockFluidCooledContaminatedMoltenSalt");
        GameRegistry.registerBlock(blockFluidCooledContaminatedMoltenSalt, "BlockFluidCooledContaminatedMoltenSalt");
        LanguageRegistry.addName(blockFluidCooledContaminatedMoltenSalt, "Cooled Contaminated Molten Salt");

        //plasma
        blockPlasma = new BlockPlasma(FemtocraftConfigs.BlockPlasmaID);

        // items
        itemIngotTitanium = registerItem(FemtocraftConfigs.ItemIngotTitaniumID,
                "ItemIngotTitanium",
                "Titanium Ingot");
        if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) {
            OreDictionary.registerOre("ingotTitanium", new ItemStack(
                    itemIngotTitanium));
        }

        itemIngotPlatinum = registerItem(FemtocraftConfigs.ItemIngotPlatinumID,
                "ItemIngotPlatinum",
                "Platinum Ingot");
        if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) {
            OreDictionary.registerOre("ingotPlatinum", new ItemStack(
                    itemIngotPlatinum));
        }

        itemIngotThorium = registerItem(FemtocraftConfigs.ItemIngotThoriumID,
                "ItemIngotThorium",
                "Thorium Ingot");
        if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) {
            OreDictionary.registerOre("ingotThorium", new ItemStack(
                    itemIngotThorium));
        }

        itemIngotFarenite = registerItem(FemtocraftConfigs.ItemIngotFareniteID,
                "ItemIngotFarenite",
                "Farenite");
        OreDictionary
                .registerOre("ingotFarenite", new ItemStack(itemIngotFarenite));

        itemIngotMalenite = registerItem(FemtocraftConfigs.ItemIngotMaleniteID,
                "ItemIngotMalenite",
                "Malenite");
        OreDictionary
                .registerOre("ingotMalenite", new ItemStack(itemIngotMalenite));

        itemIngotTemperedTitanium = registerItem(
                FemtocraftConfigs.ItemIngotTemperedTitaniumID,
                "ItemIngotTemperedTitanium",
                "Tempered Titanium Ingot");
        OreDictionary.registerOre("ingotTemperedTitanium", new ItemStack(
                itemIngotTemperedTitanium));

        itemIngotThFaSalt = registerItem(FemtocraftConfigs.ItemIngotThFaSaltID, "ItemIngotThFaSalt",
                "ThFa Salt Ingot");
        OreDictionary.registerOre("ingotThFaSalt", new ItemStack(itemIngotThFaSalt));

        itemNuggetLodestone = registerItem(FemtocraftConfigs.ItemNuggetLodestoneID, "ItemNuggetLodestone",
                "Lodestone Nugget");
        OreDictionary.registerOre("nuggetLodestone", new ItemStack(itemNuggetLodestone));

        itemChunkLodestone = registerItem(FemtocraftConfigs.ItemChunkLodestoneID, "ItemChunkLodestone",
                "Lodestone Chunk");
        OreDictionary.registerOre("chunkLodestone", new ItemStack(itemChunkLodestone));

        //

        itemConductivePowder = registerItem(
                FemtocraftConfigs.ItemConductivePowderID, "ItemConductivePowder",
                "Conductive Powder");

        itemBoard = registerItem(FemtocraftConfigs.ItemBoardID, "ItemBoard",
                "Board");

        itemPrimedBoard = registerItem(FemtocraftConfigs.ItemPrimedBoardID,
                "ItemPrimedBoard",
                "Primed Board");

        itemDopedBoard = registerItem(FemtocraftConfigs.ItemDopedBoardID,
                "ItemDopedBoard",
                "Doped Board");

        itemMicrochip = registerItem(
                FemtocraftConfigs.ItemMicrochipID, "ItemMicrochip",
                "Microchip");

        itemSpool = registerItem(FemtocraftConfigs.ItemSpoolID, "ItemSpool",
                "Spool");

        itemSpoolGold = registerItem(FemtocraftConfigs.ItemSpoolGoldID,
                "ItemSpoolGold",
                "Gold Wire Spool");

        itemSpoolPlatinum = registerItem(FemtocraftConfigs.ItemSpoolPlatinumID,
                "ItemSpoolPlatinum",
                "Platinum Wire Spool");

        itemMicroCoil = registerItem(FemtocraftConfigs.ItemMicroCoilID,
                "ItemMicroCoil",
                "Micro Coil");

        itemBattery = registerItem(FemtocraftConfigs.ItemBatteryID,
                "ItemBattery",
                "Battery");

        itemArticulatingArm = registerItem(FemtocraftConfigs.ItemArticutingArmID,
                "ItemArticulatingArm",
                "Articulating Arm");

        itemDissassemblyArray = registerItem(FemtocraftConfigs.ItemDissassemblyArrayID, "ItemDissassemblyArray",
                "Dissassembly Array");

        itemAssemblyArray = registerItem(FemtocraftConfigs.ItemAssemblyArrayID,
                "ItemAssemblyArray",
                "Assembly Array");

        itemVacuumCore = registerItem(FemtocraftConfigs.ItemVacuumCoreID, "ItemVacuumCore",
                "Vacuum Core");

        itemMicroLogicCore = registerItem(FemtocraftConfigs
                        .ItemMicroLogicCoreID, "ItemMicroLogicCore",
                "Micro Logic Core");

        itemKineticPulverizer = registerItem(FemtocraftConfigs
                        .ItemKineticPulverizerID, "ItemKineticPulverizer",
                "Kinetic Pulverizer");

        itemHeatingElement = registerItem(FemtocraftConfigs
                        .ItemHeatingCoilID, "ItemHeatingCoil",
                "Heating Coil");

        itemPortableResearchComputer = new ItemPortableResearchComputer
                (FemtocraftConfigs.ItemPortableResearchComputerID).setUnlocalizedName("ItemPortableResearchComputer");
        GameRegistry.registerItem(itemPortableResearchComputer, "ItemPortableResearchComputer");
        LanguageRegistry.addName(itemPortableResearchComputer,
                "Portable Research Computer");

        itemNanochip = registerItem(FemtocraftConfigs.ItemNanochipID, "ItemNanochip",
                "Nanochip");

        itemNanoCalculator = registerItem(FemtocraftConfigs.ItemNanoCalculatorID, "ItemNanoCalculator",
                "Nano Calculator");
        itemNanoRegulator = registerItem(FemtocraftConfigs.ItemNanoRegulatorID, "ItemNanoRegulator",
                "Nano Regulator");
        itemNanoSimulator = registerItem(FemtocraftConfigs.ItemNanoSimulatorID, "ItemNanoSimulator",
                "Nano Simulator");

        itemBasicAICore = registerItem(FemtocraftConfigs.ItemBasicAICoreID, "ItemBasicAICore",
                "Basic AI Core");
        itemLearningCore = registerItem(FemtocraftConfigs.ItemLearningCoreID, "ItemLearningCore",
                "Learning Core");
        itemSchedulerCore = registerItem(FemtocraftConfigs.ItemSchedulerCoreID, "ItemSchedulerCore",
                "Scheduler Core");
        itemManagerCore = registerItem(FemtocraftConfigs.ItemManagerCoreID, "ItemManagerCore",
                "Manager Core");

        itemFluidicConductor = registerItem(FemtocraftConfigs.ItemFluidicConductorID, "ItemFluidicConductor",
                "Fluidic Conductor");
        itemNanoCoil = registerItem(FemtocraftConfigs.ItemNanoCoilID, "ItemNanoCoil",
                "Nano Coil");
        itemNanoPlating = registerItem(FemtocraftConfigs.ItemNanoPlatingID, "ItemNanoPlating",
                "Nano Plating");

        itemTemporalResonator = registerItem(FemtocraftConfigs.ItemTemporalResonatorID, "ItemTemporalResonator",
                "Temporal Resonator");
        itemDimensionalMonopole = registerItem(FemtocraftConfigs.ItemDimensionalMonopoleID, "ItemDimensionalMonopole",
                "Dimensional Monopole");

        itemSelfFulfillingOracle = registerItem(FemtocraftConfigs.ItemSelfFulfillingOracleID,
                "ItemSelfFulfillingOracle",
                "Self Fulfilling Oracle");
        itemCrossDimensionalCommunicator = registerItem(FemtocraftConfigs.ItemCrossDimensionalCommunicatorID,
                "ItemCrossDimensionalCommunicator",
                "Cross Dimensional Communicator");
        itemInfallibleEstimator = registerItem(FemtocraftConfigs.ItemInfallibleEstimatorID, "ItemInfallibleEstimator",
                "Infallible Estimator");
        itemPanLocationalComputer = registerItem(FemtocraftConfigs.ItemPanLocationalComputerID,
                "ItemPanLocationalComputer",
                "Pan Locational Computer");
        itemPandoraCube = registerItem(FemtocraftConfigs.ItemPandoraCubeID, "ItemPandoraCube",
                "Pandora Cube");

        itemFissionReactorPlating = registerItem(FemtocraftConfigs.ItemFissionReactorPlatingID,
                "ItemFissionReactorPlating",
                "Fission Reactor Plating");

        itemDigitalSchematic = new ItemDigitalSchematic(FemtocraftConfigs
                .ItemDigitalSchematicID, "ItemDigitalSchematic");
        GameRegistry.registerItem(itemDigitalSchematic, "ItemDigitalSchematic");
        LanguageRegistry.addName(itemDigitalSchematic, "Digital Schematic");

        itemMinosGate = registerItem(FemtocraftConfigs.ItemMinosGateID, "ItemMinosGate",
                "Minos Gate");
        itemCharosGate = registerItem(FemtocraftConfigs.ItemCharosGateID, "ItemCharosGate",
                "Charos Gate");
        itemCerberusGate = registerItem(FemtocraftConfigs.ItemCerberusGateID, "ItemCerberusGate",
                "Cerberus Gate");

        itemErinyesCircuit = registerItem(FemtocraftConfigs.ItemErinyesCircuitID, "ItemErinyesCircuit",
                "Erinyes Circuit");
        itemMinervaComplex = registerItem(FemtocraftConfigs.ItemMinervaComplexID, "ItemMinervaComplex",
                "Minerva Complex");

        itemAtlasMount = registerItem(FemtocraftConfigs.ItemAtlasMountID, "ItemAtlasMount",
                "Atlas Mount");
        itemHermesBus = registerItem(FemtocraftConfigs.ItemHermesBusID, "ItemHermesBus",
                "Hermes Bus");
        itemHerculesDrive = registerItem(FemtocraftConfigs.ItemHerculesDriveID, "ItemHerculesDrive",
                "Hercules Drive");
        itemOrpheusProcessor = registerItem(FemtocraftConfigs.ItemOrpheusProcessorID, "ItemOrpheusProcessor",
                "Orpheus Processor");

        itemFemtoPlating = registerItem(FemtocraftConfigs.ItemFemtoPlatingID, "ItemFemtoPlating",
                "Femto Plating");

        itemStyxValve = registerItem(FemtocraftConfigs.ItemStyxValveID, "ItemStyxValve",
                "Styx Valve");
        itemFemtoCoil = registerItem(FemtocraftConfigs.ItemFemtoCoilID, "ItemFemtoCoil",
                "Femto Coil");

        itemPhlegethonTunnelPrimer = registerItem(FemtocraftConfigs.ItemPhlegethonTunnelPrimerID,
                "ItemPhlegethonTunnelPrimer",
                "Phlegethon Tunnel Primer");

        itemStellaratorPlating = registerItem(FemtocraftConfigs.ItemStellaratorPlatingID, "ItemStellaratorPlating",
                "Stellarator Plating");

        itemInfinitelyRecursiveALU = registerItem(FemtocraftConfigs.ItemInfinitelyRecursiveALUID,
                "ItemInfinitelyRecursiveALU",
                "Infinitely Recursive ALU");

        itemInfiniteVolumePolychora = registerItem(FemtocraftConfigs.ItemInfiniteVolumePolychoraID,
                "ItemInfiniteVolumePolychora",
                "Infinite Volume Polychora");

        itemNucleationCore = new ItemNucleationCore(FemtocraftConfigs.ItemNucleationCoreID, "ItemNucleationCore");
        GameRegistry.registerItem(itemNucleationCore, "ItemNucleationCore");
        LanguageRegistry.addName(itemNucleationCore, "Nucleation Core");

        itemInhibitionCore = new ItemInhibitionCore(FemtocraftConfigs.ItemInhibitionCoreID, "ItemInhibitionCore");
        GameRegistry.registerItem(itemInhibitionCore, "ItemInhibitionCore");
        LanguageRegistry.addName(itemInhibitionCore, "Inhibition Core");

        itemQuantumSchematic = new ItemQuantumSchematic(FemtocraftConfigs
                .ItemQuantumSchematicID, "ItemQuantumSchematic");
        GameRegistry.registerItem(itemQuantumSchematic, "ItemQuantumSchematic");
        LanguageRegistry.addName(itemQuantumSchematic, "Quantum Schematic");


        itemMicroTechnology = new ItemMicroTechnology(
                FemtocraftConfigs.ItemMicroTechnologyID).setUnlocalizedName("ItemMicroTechnology");
        GameRegistry.registerItem(itemMicroTechnology, "ItemMicroTechnology");
        LanguageRegistry.addName(itemMicroTechnology, "Micro Technology");

        itemNanoTechnology = new ItemNanoTechnology(
                FemtocraftConfigs.ItemNanoTechnologyID).setUnlocalizedName("ItemNanoTechnology");
        GameRegistry.registerItem(itemNanoTechnology, "ItemNanoTechnology");
        LanguageRegistry.addName(itemNanoTechnology, "Nano Technology");

        itemFemtoTechnology = new ItemFemtoTechnology(
                FemtocraftConfigs.ItemFemtoTechnologyID).setUnlocalizedName("ItemFemtoTechnology");
        GameRegistry.registerItem(itemFemtoTechnology, "ItemFemtoTechnology");
        LanguageRegistry.addName(itemFemtoTechnology, "Femto Technology");

        // Schematics
        itemPaperSchematic = new ItemPaperSchematic(
                FemtocraftConfigs.ItemPaperSchematicID, "ItemPaperSchematic");
        GameRegistry.registerItem(itemPaperSchematic, "ItemPaperSchematic");
        LanguageRegistry.addName(itemPaperSchematic, "Paper Schematic");

        itemInterfaceDeviceMicro = new ItemMicroInterfaceDevice(
                FemtocraftConfigs.ItemMicroInterfaceDeviceID)
                .setUnlocalizedName("ItemInterfaceDeviceMicro");
        GameRegistry.registerItem(itemInterfaceDeviceMicro, "ItemInterfaceDeviceMicro");
        LanguageRegistry.addName(itemInterfaceDeviceMicro, "MicroInterface Device");

        itemInterfaceDeviceNano = new ItemNanoInterfaceDevice(
                FemtocraftConfigs.ItemNanoInterfaceDeviceID)
                .setUnlocalizedName("ItemInterfaceDeviceNano");
        GameRegistry.registerItem(itemInterfaceDeviceNano, "ItemInterfaceDeviceNano");
        LanguageRegistry.addName(itemInterfaceDeviceNano, "NanoInterface Device");

        itemInterfaceDeviceFemto = new ItemFemtoInterfaceDevice(
                FemtocraftConfigs.ItemFemtoInterfaceDeviceID)
                .setUnlocalizedName("ItemInterfaceDeviceFemto");
        GameRegistry.registerItem(itemInterfaceDeviceFemto, "ItemInterfaceDeviceFemto");
        LanguageRegistry.addName(itemInterfaceDeviceFemto, "FemtoInterface Device");

        // Decomp
        // Femto
        itemCubit = registerItem(FemtocraftConfigs.ItemCubitID, "ItemCubit",
                FemtocraftUtils.orangeify("Cubit"));

        itemRectangulon = registerItem(FemtocraftConfigs.ItemRectangulonID,
                "ItemRectangulon",
                FemtocraftUtils.orangeify("Rectangulon"));

        itemPlaneoid = registerItem(FemtocraftConfigs.ItemPlaneoidID,
                "ItemPlaneoid",
                FemtocraftUtils.orangeify("Planeoid"));

        // Nano
        itemCrystallite = registerItem(FemtocraftConfigs.ItemCrystalliteID,
                "ItemCrystallite",
                FemtocraftUtils.greenify("Crystallite"));

        itemMineralite = registerItem(FemtocraftConfigs.ItemMineraliteID,
                "ItemMineralite",
                FemtocraftUtils.greenify("Mineralite"));

        itemMetallite = registerItem(FemtocraftConfigs.ItemMetalliteID,
                "ItemMetallite",
                FemtocraftUtils.greenify("Metallite"));

        itemFaunite = registerItem(FemtocraftConfigs.ItemFauniteID, "ItemFaunite",
                FemtocraftUtils.greenify("Faunite"));

        itemElectrite = registerItem(FemtocraftConfigs.ItemElectriteID,
                "ItemElectrite",
                FemtocraftUtils.greenify("Electrite"));

        itemFlorite = registerItem(FemtocraftConfigs.ItemFloriteID, "ItemFlorite",
                FemtocraftUtils.greenify("Florite"));

        // Micro
        itemMicroCrystal = registerItem(
                FemtocraftConfigs.ItemMicroCrystalID, "ItemMicroCrystal",
                FemtocraftUtils.blueify("Micro Crystal"));

        itemProteinChain = registerItem(
                FemtocraftConfigs.ItemProteinChainID, "ItemProteinChain",
                FemtocraftUtils.blueify("Protein Chain"));

        itemNerveCluster = registerItem(
                FemtocraftConfigs.ItemNerveClusterID, "ItemNerveCluster",
                FemtocraftUtils.blueify("Nerve Cluster"));

        itemConductiveAlloy = registerItem(
                FemtocraftConfigs.ItemConductiveAlloyID, "ItemConductiveAlloy",
                FemtocraftUtils.blueify("Conductive Alloy"));

        itemMetalComposite = registerItem(
                FemtocraftConfigs.ItemMetalCompositeID, "ItemMetalComposite",
                FemtocraftUtils.blueify("Metal Composite"));

        itemFibrousStrand = registerItem(
                FemtocraftConfigs.ItemFibrousStrandID, "ItemFibrousStrand",
                FemtocraftUtils.blueify("Fibrous Strand"));

        itemMineralLattice = registerItem(
                FemtocraftConfigs.ItemMineralLatticeID, "ItemMineralLattice",
                FemtocraftUtils.blueify("Mineral Lattice"));

        itemFungalSpores = registerItem(
                FemtocraftConfigs.ItemFungalSporesID, "ItemFungalSpores",
                FemtocraftUtils.blueify("Fungal Spores"));

        itemIonicChunk = registerItem(FemtocraftConfigs.ItemIonicChunkID,
                "ItemIonicChunk",
                FemtocraftUtils.blueify("Ionic Chunk"));

        itemReplicatingMaterial = registerItem(
                FemtocraftConfigs.ItemReplicatingMaterialID,
                "ItemReplicatingMaterial",
                FemtocraftUtils.blueify("Replicating Material"));

        itemSpinyFilament = registerItem(
                FemtocraftConfigs.ItemSpinyFilamentID, "ItemSpinyFilament",
                FemtocraftUtils.blueify("Spiny Filament"));

        itemHardenedBulb = registerItem(
                FemtocraftConfigs.ItemHardenedBulbID, "ItemHardenedBulb",
                FemtocraftUtils.blueify("Hardened Bulb"));

        itemMorphicChannel = registerItem(
                FemtocraftConfigs.ItemMorphicChannelID, "ItemMorphicChannel",
                FemtocraftUtils.blueify("Morphic Channel"));

        itemSynthesizedFiber = registerItem(
                FemtocraftConfigs.ItemSynthesizedFiberID, "ItemSynthesizedFiber",
                FemtocraftUtils.blueify("Synthesized Fiber"));

        itemOrganometallicPlate = registerItem(
                FemtocraftConfigs.ItemOrganometallicPlateID,
                "ItemOrganometallicPlate",
                FemtocraftUtils.blueify("Organometallic Plate"));

        // End Decomp

        itemMicroPlating = registerItem(
                FemtocraftConfigs.ItemMicroPlatingID, "ItemMicroPlating", "Micro Plating");
    }

    private Item registerItem(int id, String unlocalizedName, String name) {
        Item it = new ItemBase(id, unlocalizedName);
        LanguageRegistry.addName(it, name);
        GameRegistry.registerItem(it, unlocalizedName);
        return it;
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        ProxyClient.setCustomRenderers();
        // GameRegistry.registerTileEntity(TileEntity.class, "myTile");
        // GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});
        // EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this,
        // 32, 10, true)


    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        assemblerConfigs = new FemtocraftConfigAssembler(recipeConfigFile);
        recipeManager.init();
        recipeManager.assemblyRecipes.registerDefaultRecipes();
        MagnetRegistry.init();
        new FemtocraftConfigTechnology(technologyConfigFile).loadTechnologies();

        if (event.getSide() == Side.CLIENT) {
            researchManager.calculateGraph();
        }
    }

    @EventHandler
    public void serverLoad(FMLServerStartingEvent event) {
        event.registerServerCommand(femtocraftServerCommand);
    }
}
