/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft;

import com.itszuvalex.femtocraft.blocks.BlockFemtoStone;
import com.itszuvalex.femtocraft.blocks.BlockMicroStone;
import com.itszuvalex.femtocraft.blocks.BlockNanoStone;
import com.itszuvalex.femtocraft.blocks.BlockUnidentifiedAlloy;
import com.itszuvalex.femtocraft.core.items.ItemBase;
import com.itszuvalex.femtocraft.core.items.ItemFemtoInterfaceDevice;
import com.itszuvalex.femtocraft.core.items.ItemMicroInterfaceDevice;
import com.itszuvalex.femtocraft.core.items.ItemNanoInterfaceDevice;
import com.itszuvalex.femtocraft.core.liquids.BlockFluidMass;
import com.itszuvalex.femtocraft.core.liquids.FluidMass;
import com.itszuvalex.femtocraft.core.ore.*;
import com.itszuvalex.femtocraft.industry.blocks.*;
import com.itszuvalex.femtocraft.industry.items.ItemPaperSchematic;
import com.itszuvalex.femtocraft.managers.ManagerRecipe;
import com.itszuvalex.femtocraft.managers.research.ManagerResearch;
import com.itszuvalex.femtocraft.player.PropertiesNanite;
import com.itszuvalex.femtocraft.power.blocks.*;
import com.itszuvalex.femtocraft.power.items.ItemBlockMicroCube;
import com.itszuvalex.femtocraft.power.plasma.BlockPlasma;
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
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
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

import java.util.logging.Logger;

@Mod(modid = Femtocraft.ID, name = "Femtocraft", version = Femtocraft.VERSION)
@NetworkMod(channels = {Femtocraft.ID, PropertiesNanite.PACKET_CHANNEL,
        ManagerResearch.RESEARCH_CHANNEL,
        TileEntityResearchConsole.PACKET_CHANNEL,
        TileEntityVacuumTube.packetChannel}, packetHandler = FemtocraftPacketHandler.class, clientSideRequired = true, serverSideRequired = true)
public class Femtocraft {
    public static final String ID = "Femtocraft";
    public static final String VERSION = "0.1.0";

    @Instance(ID)
    public static Femtocraft instance;

    @SidedProxy(clientSide = "com.itszuvalex.femtocraft.proxy.ProxyClient",
            serverSide = "com.itszuvalex.femtocraft.proxy.ProxyCommon")
    public static ProxyCommon proxy;

    public static CreativeTabs femtocraftTab = new FemtocraftCreativeTab(
            "Femtocraft");

    public static Logger logger;

    public static ManagerRecipe recipeManager;
    public static ManagerResearch researchManager;

    // blocks
    public static Block blockOreTitanium;
    public static Block blockOrePlatinum;
    public static Block blockOreThorium;
    public static Block blockOreFarenite;
    public static Block blockOreMalenite;
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
    public static Block blockOrbitalEqualizer;
    public static Block blockNullEqualizer;
    public static Block blockStellaratorCore;
    public static Block blockStellaratorFocus;
    public static Block blockStellaratorOpticalMaser;
    public static Block blockStellaratorHousing;

    // cables
    public static Block blockMicroCable;
    public static Block blockNanoCable;
    public static Block blockFemtoCable;

    // liquids
    public static Fluid fluidMass;
    public static BlockFluidMass mass_block;

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

    public static Item itemMicroLogicCore;
    public static Item itemHeatingElement;
    public static Item itemArticulatingArm;
    public static Item itemDissassemblyArray;
    public static Item itemAssemblyArray;

    public static Item itemVacuumCore;

    public static Item itemPortableResearchComputer;

    public static Item itemMicroTechnology;
    public static Item itemNanoTechnology;
    public static Item itemFemtoTechnology;

    public static Item itemPaperSchematic;

    public static Item itemInterfaceDeviceMicro;

    //nano
    public static Item itemThoriumRod;

    public static Item itemNanochip;
    public static Item itemNanoCalculator;
    public static Item itemNanoRegulator;
    public static Item itemNanoSimulator;

    public static Item itemBasicAICore;
    public static Item itemLearningCore;
    public static Item itemSchedulerCore;
    public static Item itemManagerCore;

    public static Item itemFluidConductor;
    public static Item itemNanoCoil;

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

    public static Item itemMicroPlating;


    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = Logger.getLogger(ID);
        logger.setParent(FMLLog.getLogger());

        Configuration config = new Configuration(
                event.getSuggestedConfigurationFile());
        FemtocraftConfigs.load(config);

        Femtocraft.proxy.registerTileEntities();
        Femtocraft.proxy.registerRendering();
        Femtocraft.proxy.registerBlockRenderers();
        Femtocraft.proxy.registerTickHandlers();

        NetworkRegistry.instance().registerGuiHandler(this,
                new FemtocraftGuiHandler());
        NetworkRegistry.instance().registerConnectionHandler(
                new FemtocraftConnectionHandler());
        MinecraftForge.EVENT_BUS.register(new FemtocraftEventHookContainer());
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        proxy.registerRendering();

        if (FemtocraftConfigs.worldGen) {
            GameRegistry.registerWorldGenerator(new WorldGeneratorOre());
        }

        // Change the creative tab name
        LanguageRegistry.instance().addStringLocalization(
                "itemGroup.Femtocraft", "en_US", "Femtocraft");

        // item = new Item(Configs.itemId);

        // blocks

        blockOreTitanium = new BlockOreTitanium(FemtocraftConfigs.oreTitaniumID);
        MinecraftForge.setBlockHarvestLevel(blockOreTitanium, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreTitanium, "blockOreTitanium");
        LanguageRegistry.addName(blockOreTitanium, "Titanium Ore");
        if (FemtocraftConfigs.registerTitaniumOreInOreDictionary) {
            OreDictionary
                    .registerOre("blockOreTitanium", new ItemStack(blockOreTitanium));
        }

        blockOrePlatinum = new BlockOrePlatinum(FemtocraftConfigs.orePlatinumID);
        MinecraftForge.setBlockHarvestLevel(blockOrePlatinum, "pickaxe", 2);
        GameRegistry.registerBlock(blockOrePlatinum, "blockOrePlatinum");
        LanguageRegistry.addName(blockOrePlatinum, "Platinum Ore");
        if (FemtocraftConfigs.registerPlatinumOreInOreDictionary) {
            OreDictionary
                    .registerOre("blockOrePlatinum", new ItemStack(blockOrePlatinum));
        }

        blockOreThorium = new BlockOreThorium(FemtocraftConfigs.oreThoriumID);
        MinecraftForge.setBlockHarvestLevel(blockOreThorium, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreThorium, "blockOreThorium");
        LanguageRegistry.addName(blockOreThorium, "Thorium Ore");
        if (FemtocraftConfigs.registerThoriumOreInOreDictionary) {
            OreDictionary.registerOre("blockOreThorium", new ItemStack(blockOreThorium));
        }

        blockOreFarenite = new BlockOreFarenite(FemtocraftConfigs.oreFareniteID);
        MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 2);
        GameRegistry.registerBlock(blockOreFarenite, "blockOreFarenite");
        LanguageRegistry.addName(blockOreFarenite, "Farenite Ore");
        if (FemtocraftConfigs.registerFareniteOreInOreDictionary) {
            OreDictionary
                    .registerOre("blockOreFarenite", new ItemStack(blockOreFarenite));
        }

        blockOreMalenite = new BlockOreMalenite(FemtocraftConfigs.oreMaleniteID);
        MinecraftForge.setBlockHarvestLevel(blockOreFarenite, "pickaxe", 3);
        GameRegistry.registerBlock(blockOreMalenite, "blockOreMalenite");
        LanguageRegistry.addName(blockOreMalenite, "Malenite Ore");
        if (FemtocraftConfigs.registerMaleniteOreInOreDictionary) {
            OreDictionary
                    .registerOre("blockOreMalenite", new ItemStack(blockOreMalenite));
        }

        nanoStone = new BlockNanoStone(FemtocraftConfigs.nanoStoneID);
        GameRegistry.registerBlock(nanoStone, "nanoStone");
        LanguageRegistry.addName(nanoStone, "Nanostone");

        microStone = new BlockMicroStone(FemtocraftConfigs.microStoneID);
        GameRegistry.registerBlock(microStone, "microStone");
        LanguageRegistry.addName(microStone, "Microstone");

        femtoStone = new BlockFemtoStone(FemtocraftConfigs.femtoStoneID);
        GameRegistry.registerBlock(femtoStone, "femtoStone");
        LanguageRegistry.addName(femtoStone, "Femtostone");

        unidentifiedAlloy = new BlockUnidentifiedAlloy(
                FemtocraftConfigs.unidentifiedAlloyID);
        GameRegistry.registerBlock(unidentifiedAlloy, "unidentifiedAlloy");
        LanguageRegistry.addName(unidentifiedAlloy, "Unidentified Alloy");

        blockResearchComputer = new BlockResearchComputer(
                FemtocraftConfigs.FemtocraftResearchComputerID);
        GameRegistry.registerBlock(blockResearchComputer, "blockResearchComputer");
        LanguageRegistry.addName(blockResearchComputer, "Research Computer");

        blockResearchConsole = new BlockResearchConsole(
                FemtocraftConfigs.FemtocraftResearchConsoleID);
        GameRegistry.registerBlock(blockResearchConsole, "blockResearchConsole");
        LanguageRegistry.addName(blockResearchConsole, "Research Console");

        blockMicroCable = new BlockMicroCable(FemtocraftConfigs.microCableID,
                Material.rock);
        GameRegistry.registerBlock(blockMicroCable, "microCable");
        LanguageRegistry.addName(blockMicroCable, "Micro-Cable");

        blockNanoCable = new BlockNanoCable(FemtocraftConfigs.nanoCableID,
                Material.rock);
        GameRegistry.registerBlock(blockNanoCable, "nanoCable");
        LanguageRegistry.addName(blockNanoCable, "Nano-Cable");

        blockFemtoCable = new BlockFemtoCable(FemtocraftConfigs.femtoCableID,
                Material.rock);
        GameRegistry.registerBlock(blockFemtoCable, "femtoCable");
        LanguageRegistry.addName(blockFemtoCable, "Femto-Cable");

        generatorTest = new BlockGenerator(
                FemtocraftConfigs.FemtopowerGeneratorTestID, Material.rock)
                .setUnlocalizedName("BlockGenerator").setHardness(3.5f)
                .setStepSound(Block.soundStoneFootstep);
        GameRegistry.registerBlock(generatorTest, "BlockGenerator");
        LanguageRegistry.addName(generatorTest, "Generator");

        consumerTest = new BlockConsumer(
                FemtocraftConfigs.FemtopowerConsumerTestBlockID, Material.rock)
                .setUnlocalizedName("BlockConsumer").setHardness(3.5f)
                .setStepSound(Block.soundStoneFootstep);
        GameRegistry.registerBlock(consumerTest, "BlockConsumer");
        LanguageRegistry.addName(consumerTest, "Consumer");

        blockMicroFurnaceUnlit = new BlockMicroFurnace(
                FemtocraftConfigs.FemtocraftMicroFurnaceUnlitID, false);
        GameRegistry.registerBlock(blockMicroFurnaceUnlit, "BlockMicroFurnace");
        LanguageRegistry.addName(blockMicroFurnaceUnlit, "Micro-Furnace");

        blockMicroFurnaceLit = new BlockMicroFurnace(
                FemtocraftConfigs.FemtocraftMicroFurnaceLitID, true);

        blockMicroDeconstructor = new BlockMicroDeconstructor(
                FemtocraftConfigs.FemtocraftMicroDeconstructorID);
        GameRegistry.registerBlock(blockMicroDeconstructor,
                "BlockMicroDeconstructor");
        LanguageRegistry.addName(blockMicroDeconstructor, "Microtech Deconstructor");

        blockMicroReconstructor = new BlockMicroReconstructor(
                FemtocraftConfigs.FemtocraftMicroReconstructorID);
        GameRegistry.registerBlock(blockMicroReconstructor,
                "BlockMicroReconstructor");
        LanguageRegistry.addName(blockMicroReconstructor, "Microtech Reconstructor");

        blockEncoder = new BlockEncoder(FemtocraftConfigs.FemtocraftMicroEncoderID);
        GameRegistry.registerBlock(blockEncoder, "BlockEncoder");
        LanguageRegistry.addName(blockEncoder, "Schematic Encoder");

        blockNanoInnervatorUnlit = new BlockNanoInnervator(
                FemtocraftConfigs.FemtocraftNanoInnervatorUnlitID, false);
        GameRegistry.registerBlock(blockNanoInnervatorUnlit, "BlockNanoInnervator");
        LanguageRegistry.addName(blockNanoInnervatorUnlit, "Nano Innervator");

        blockNanoInnervatorLit = new BlockNanoInnervator(
                FemtocraftConfigs.FemtocraftNanoInnervatorLitID, true);

        blockNanoDismantler = new BlockNanoDismantler(
                FemtocraftConfigs.FemtocraftNanoDismantlerID);
        GameRegistry.registerBlock(blockNanoDismantler, "BlockNanoDismantler");
        LanguageRegistry.addName(blockNanoDismantler, "Nano Dismantler");

        blockNanoFabricator = new BlockNanoFabricator(FemtocraftConfigs
                .FemtocraftNanoFabricatorID);
        GameRegistry.registerBlock(blockNanoFabricator, "BlockNanoFabricator");
        LanguageRegistry.addName(blockNanoFabricator, "Nano Fabricator");

        blockNanoEnmesher = new BlockNanoEnmesher(FemtocraftConfigs
                .FemtocraftNanoEnmesherID);
        GameRegistry.registerBlock(blockNanoEnmesher, "BlockNanoEnmesher");
        LanguageRegistry.addName(blockNanoEnmesher, "Nano Enmesher");

        blockNanoHorologe = new BlockNanoHorologe(FemtocraftConfigs
                .FemtocraftNanoHorologeID);
        GameRegistry.registerBlock(blockNanoHorologe, "BlockNanoHorologe");
        LanguageRegistry.addName(blockNanoHorologe, "Nano Horologe");

        blockFemtoImpulserUnlit = new BlockFemtoImpulser(
                FemtocraftConfigs.FemtocraftFemtoImpulserUnlitID, false);
        GameRegistry.registerBlock(blockFemtoImpulserUnlit, "BlockFemtoImpulser");
        LanguageRegistry.addName(blockFemtoImpulserUnlit, "Femto Impulser");

        blockFemtoImpulserLit = new BlockFemtoImpulser(
                FemtocraftConfigs.FemtocraftFemtoImpulserLitID, true);

        blockFemtoRepurposer = new BlockFemtoRepurposer(
                FemtocraftConfigs.FemtocraftFemtoRepurposerID);
        GameRegistry.registerBlock(blockFemtoRepurposer, "BlockFemtoRepurposer");
        LanguageRegistry.addName(blockFemtoRepurposer, "Femto Repurposer");

        blockFemtoCoagulator = new BlockFemtoCoagulator(FemtocraftConfigs
                .FemtocraftFemtoCoagulatorID);
        GameRegistry.registerBlock(blockFemtoCoagulator, "BlockFemtoCoagulator");
        LanguageRegistry.addName(blockFemtoCoagulator, "Femto Coagulator");

        blockFemtoEntangler = new BlockFemtoEntangler(FemtocraftConfigs
                .FemtocraftFemtoEntanglerID);
        GameRegistry.registerBlock(blockFemtoEntangler, "BlockFemtoEntangler");
        LanguageRegistry.addName(blockFemtoEntangler, "Femto Entangler");

        blockFemtoChronoshifter = new BlockFemtoChronoshifter(FemtocraftConfigs
                .FemtocraftFemtoChronoshifterID);
        GameRegistry.registerBlock(blockFemtoChronoshifter, "BlockFemtoChronoshifter");
        LanguageRegistry.addName(blockFemtoChronoshifter, "Femto Chronoshifter");

        blockMicroCube = new BlockMicroCube(FemtocraftConfigs.FemtopowerMicroCubeID);
        GameRegistry.registerBlock(blockMicroCube, ItemBlockMicroCube.class,
                "BlockMicroCube");
        LanguageRegistry.addName(blockMicroCube, "Micro-Cube");

        blockNanoCubeFrame = new BlockNanoCubeFrame(
                FemtocraftConfigs.FemtopowerNanoCubeFrameID);
        GameRegistry.registerBlock(blockNanoCubeFrame, "BlockNanoCubeFrame");
        LanguageRegistry.addName(blockNanoCubeFrame, "Nano-Cube Frame");

        blockNanoCubePort = new BlockNanoCubePort(
                FemtocraftConfigs.FemtopowerNanoCubePortID);
        GameRegistry.registerBlock(blockNanoCubePort, "BlockNanoCubePort");
        LanguageRegistry.addName(blockNanoCubePort, "Nano-Cube Port");

        blockFemtoCubePort = new BlockFemtoCubePort(
                FemtocraftConfigs.FemtopowerFemtoCubePortID);
        GameRegistry.registerBlock(blockFemtoCubePort, "BlockFemtoCubePort");
        LanguageRegistry.addName(blockFemtoCubePort, "Femto-Cube Port");

        blockFemtoCubeFrame = new BlockFemtoCubeFrame(
                FemtocraftConfigs.FemtopowerFemtoCubeFrameID);
        GameRegistry.registerBlock(blockFemtoCubeFrame, "BlockFemtoCubeFrame");
        LanguageRegistry.addName(blockFemtoCubeFrame, "Femto-Cube Frame");

        blockFemtoCubeChassis = new BlockFemtoCubeChassis(
                FemtocraftConfigs.FemtopowerFemtoCubeChassisID);
        GameRegistry.registerBlock(blockFemtoCubeChassis, "BlockFemtoCubeChassis");
        LanguageRegistry.addName(blockFemtoCubeChassis, "Femto-Cube Chassis");

        blockVacuumTube = new BlockVacuumTube(
                FemtocraftConfigs.FemtocraftVacuumTubeID);
        GameRegistry.registerBlock(blockVacuumTube, "BlockVacuumTube");
        LanguageRegistry.addName(blockVacuumTube, "Vacuum Tube");

        blockSuctionPipe = new BlockSuctionPipe(
                FemtocraftConfigs.FemtocraftSuctionPipeID);
        GameRegistry.registerBlock(blockSuctionPipe, "BlockSuctionPipe");
        LanguageRegistry.addName(blockSuctionPipe, "Suction Pipe");

        blockMicroChargingBase = new BlockAtmosphericChargingBase(
                FemtocraftConfigs.FemtopowerMicroChargingBaseID);
        GameRegistry.registerBlock(blockMicroChargingBase, "BlockBaseMicroCharging");
        LanguageRegistry.addName(blockMicroChargingBase,
                "Electrostatic Charging Base");

        blockMicroChargingCoil = new BlockAtmosphericChargingCoil(
                FemtocraftConfigs.FemtopowerMicroChargingCoilID);
        GameRegistry.registerBlock(blockMicroChargingCoil, "BlockCoilMicroCharging");
        LanguageRegistry.addName(blockMicroChargingCoil,
                "Electrostatic Charging Coil");

        blockMicroChargingCapacitor = new BlockAtmosphericChargingCapacitor
                (FemtocraftConfigs.FemtopowerMicroChargingCapacitorID);
        GameRegistry.registerBlock(blockMicroChargingCapacitor,
                "BlockAtmosphericChargingCapacitor");
        LanguageRegistry.addName(blockMicroChargingCapacitor,
                "Electrostatic Charging Capacitor");

        blockOrbitalEqualizer = new BlockOrbitalEqualizer(
                FemtocraftConfigs.orbitalEqualizerID);
        GameRegistry.registerBlock(blockOrbitalEqualizer, "BlockOrbitalEqualizer");
        LanguageRegistry.addName(blockOrbitalEqualizer, "Orbital Equalizer");

        blockNullEqualizer = new BlockNullEqualizer(
                FemtocraftConfigs.nullEqualizerID);
        GameRegistry.registerBlock(blockNullEqualizer, "BlockNullEqualizer");
        LanguageRegistry.addName(blockNullEqualizer, "Null-Energy Equalizer");

        blockStellaratorCore = new BlockFemtoStellaratorCore(FemtocraftConfigs
                .FemtopowerFemtoStelleratorCoreID);
        GameRegistry.registerBlock(blockStellaratorCore, "BlockStellaratorCore");
        LanguageRegistry.addName(blockStellaratorCore, "Stellarator Core");

        blockStellaratorFocus = new BlockFemtoStellaratorFocus(FemtocraftConfigs
                .FemtopowerFemtoStelleratorFocusID);
        GameRegistry.registerBlock(blockStellaratorFocus, "BlockStellaratorFocus");
        LanguageRegistry.addName(blockStellaratorFocus, "Stellarator Focus");

        blockStellaratorOpticalMaser = new BlockFemtoStellaratorOpticalMaser
                (FemtocraftConfigs.FemtopowerFemtoStellaratorOpticalMaserID);
        GameRegistry.registerBlock(blockStellaratorOpticalMaser,
                "BlockStellaratorOpticalMaser");
        LanguageRegistry.addName(blockStellaratorOpticalMaser,
                "Stellarator Optical Maser");

        blockStellaratorHousing = new BlockFemtoStellaratorHousing
                (FemtocraftConfigs.FemtopowerFemtoStellaratorHousingID);
        GameRegistry.registerBlock(blockStellaratorHousing,
                "BlockStellaratorHousing");
        LanguageRegistry.addName(blockStellaratorHousing, "Stellarator Housing");

        // Liquids
        fluidMass = new FluidMass();
        FluidRegistry.registerFluid(fluidMass);

        mass_block = new BlockFluidMass(FemtocraftConfigs.FemtocraftMassBlockID);
        GameRegistry.registerBlock(mass_block, "Mass");
        LanguageRegistry.addName(mass_block, "Mass");

        //plasma
        blockPlasma = new BlockPlasma(FemtocraftConfigs.BlockPlasmaID);

        // items
        itemIngotTitanium = new ItemBase(FemtocraftConfigs.ingotTitaniumID,
                "ItemIngotTitanium");
        LanguageRegistry.addName(itemIngotTitanium, "Titanium Ingot");
        if (FemtocraftConfigs.registerTitaniumIngotInOreDictionary) {
            OreDictionary.registerOre("itemIngotTitanium", new ItemStack(
                    itemIngotTitanium));
        }

        itemIngotPlatinum = new ItemBase(FemtocraftConfigs.ingotPlatinumID,
                "ItemIngotPlatinum");
        LanguageRegistry.addName(itemIngotPlatinum, "Platinum Ingot");
        if (FemtocraftConfigs.registerPlatinumIngotInOreDictionary) {
            OreDictionary.registerOre("itemIngotPlatinum", new ItemStack(
                    itemIngotPlatinum));
        }

        itemIngotThorium = new ItemBase(FemtocraftConfigs.ingotThoriumID,
                "ItemIngotThorium");
        LanguageRegistry.addName(itemIngotThorium, "Thorium Ingot");
        if (FemtocraftConfigs.registerThoriumIngotInOreDictionary) {
            OreDictionary.registerOre("itemIngotThorium", new ItemStack(
                    itemIngotThorium));
        }

        itemIngotFarenite = new ItemBase(FemtocraftConfigs.ingotFareniteID,
                "ItemIngotFarenite");
        LanguageRegistry.addName(itemIngotFarenite, "Farenite");
        OreDictionary
                .registerOre("itemIngotFarenite", new ItemStack(itemIngotFarenite));

        itemIngotMalenite = new ItemBase(FemtocraftConfigs.ingotMaleniteID,
                "ItemIngotMalenite");
        LanguageRegistry.addName(itemIngotMalenite, "Malenite");
        OreDictionary
                .registerOre("itemIngotMalenite", new ItemStack(itemIngotMalenite));

        itemIngotTemperedTitanium = new ItemBase(
                FemtocraftConfigs.ingotTemperedTitaniumID,
                "ItemIngotTemperedTitanium");
        LanguageRegistry.addName(itemIngotTemperedTitanium,
                "Tempered Titanium Ingot");
        OreDictionary.registerOre("itemIngotTemperedTitanium", new ItemStack(
                itemIngotTemperedTitanium));

        //

        itemConductivePowder = new ItemBase(
                FemtocraftConfigs.conductivePowderID, "ItemConductivePowder");
        LanguageRegistry.addName(itemConductivePowder, "Conductive Powder");

        itemBoard = new ItemBase(FemtocraftConfigs.boardID, "ItemBoard");
        LanguageRegistry.addName(itemBoard, "Board");

        itemPrimedBoard = new ItemBase(FemtocraftConfigs.primedBoardID,
                "ItemPrimedBoard");
        LanguageRegistry.addName(itemPrimedBoard, "Primed Board");

        itemDopedBoard = new ItemBase(FemtocraftConfigs.dopedBoardID,
                "ItemDopedBoard");
        LanguageRegistry.addName(itemDopedBoard, "Doped Board");

        itemMicrochip = new ItemBase(
                FemtocraftConfigs.microCircuitID, "ItemMicrochip");
        LanguageRegistry.addName(itemMicrochip, "Microchip");

        itemSpool = new ItemBase(FemtocraftConfigs.spoolID, "ItemSpool");
        LanguageRegistry.addName(itemSpool, "Spool");

        itemSpoolGold = new ItemBase(FemtocraftConfigs.spoolGoldID,
                "ItemSpoolGold");
        LanguageRegistry.addName(itemSpoolGold, "Gold Wire Spool");

        itemSpoolPlatinum = new ItemBase(FemtocraftConfigs.spoolPlatinumID,
                "ItemSpoolPlatinum");
        LanguageRegistry.addName(itemSpoolPlatinum, "Platinum Wire Spool");

        itemMicroCoil = new ItemBase(FemtocraftConfigs.itemMicroCoilID,
                "ItemMicroCoil");
        LanguageRegistry.addName(itemMicroCoil, "Micro Coil");

        itemMicroLogicCore = new ItemBase(FemtocraftConfigs
                .itemMicroLogicCoreID, "ItemMicroLogicCore");
        LanguageRegistry.addName(itemMicroLogicCore, "Micro Logic Core");

        itemHeatingElement = new ItemBase(FemtocraftConfigs
                .itemHeatingCoilID, "itemHeatingElement");
        LanguageRegistry.addName(itemHeatingElement, "Heating Coil");

        itemPortableResearchComputer = new ItemPortableResearchComputer
                (FemtocraftConfigs.itemPortableResearchComputerID);
        LanguageRegistry.addName(itemPortableResearchComputer,
                "Portable Research Computer");

        itemMicroTechnology = new ItemMicroTechnology(
                FemtocraftConfigs.itemMicroTechnologyID);
        LanguageRegistry.addName(itemMicroTechnology, "Micro Technology");

        itemNanoTechnology = new ItemNanoTechnology(
                FemtocraftConfigs.itemNanoTechnologyID);
        LanguageRegistry.addName(itemNanoTechnology, "Nano Technology");

        itemFemtoTechnology = new ItemFemtoTechnology(
                FemtocraftConfigs.itemFemtoTechnologyID);
        LanguageRegistry.addName(itemFemtoTechnology, "Femto Technology");

        // Schematics
        itemPaperSchematic = new ItemPaperSchematic(
                FemtocraftConfigs.paperSchematicID);
        LanguageRegistry.addName(itemPaperSchematic, "Paper Schematic");

        itemInterfaceDeviceMicro = new ItemMicroInterfaceDevice(
                FemtocraftConfigs.microInterfaceDeviceID)
                .setUnlocalizedName("itemInterfaceDeviceMicro");
        LanguageRegistry.addName(itemInterfaceDeviceMicro, "MicroInterface Device");

        itemInterfaceDeviceNano = new ItemNanoInterfaceDevice(
                FemtocraftConfigs.nanoInterfaceDeviceID)
                .setUnlocalizedName("itemInterfaceDeviceNano");
        LanguageRegistry.addName(itemInterfaceDeviceNano, "NanoInterface Device");

        itemInterfaceDeviceFemto = new ItemFemtoInterfaceDevice(
                FemtocraftConfigs.femtoInterfaceDeviceID)
                .setUnlocalizedName("itemInterfaceDeviceFemto");
        LanguageRegistry.addName(itemInterfaceDeviceFemto, "FemtoInterface Device");

        // Decomp
        // Femto
        itemCubit = new ItemBase(FemtocraftConfigs.CubitID, "ItemCubit");
        LanguageRegistry.addName(itemCubit, FemtocraftUtils.orangeify("Cubit"));
        GameRegistry.registerItem(itemCubit, "Cubit");

        itemRectangulon = new ItemBase(FemtocraftConfigs.RectangulonID,
                "ItemRectangulon");
        LanguageRegistry.addName(itemRectangulon,
                FemtocraftUtils.orangeify("Rectangulon"));
        GameRegistry.registerItem(itemRectangulon, "Rectangulon");

        itemPlaneoid = new ItemBase(FemtocraftConfigs.PlaneoidID,
                "ItemPlaneoid");
        LanguageRegistry.addName(itemPlaneoid,
                FemtocraftUtils.orangeify("Planeoid"));
        GameRegistry.registerItem(itemPlaneoid, "Planeoid");

        // Nano
        itemCrystallite = new ItemBase(FemtocraftConfigs.CrystalliteID,
                "ItemCrystallite");
        LanguageRegistry.addName(itemCrystallite,
                FemtocraftUtils.greenify("Crystallite"));
        GameRegistry.registerItem(itemCrystallite, "Crystallite");

        itemMineralite = new ItemBase(FemtocraftConfigs.MineraliteID,
                "ItemMineralite");
        LanguageRegistry.addName(itemMineralite,
                FemtocraftUtils.greenify("Mineralite"));
        GameRegistry.registerItem(itemMineralite, "Mineralite");

        itemMetallite = new ItemBase(FemtocraftConfigs.MetalliteID,
                "ItemMetallite");
        LanguageRegistry.addName(itemMetallite,
                FemtocraftUtils.greenify("Metallite"));
        GameRegistry.registerItem(itemMetallite, "Metallite");

        itemFaunite = new ItemBase(FemtocraftConfigs.FauniteID, "ItemFaunite");
        LanguageRegistry.addName(itemFaunite,
                FemtocraftUtils.greenify("Faunite"));
        GameRegistry.registerItem(itemFaunite, "Faunite");

        itemElectrite = new ItemBase(FemtocraftConfigs.ElectriteID,
                "ItemElectrite");
        LanguageRegistry.addName(itemElectrite,
                FemtocraftUtils.greenify("Electrite"));
        GameRegistry.registerItem(itemElectrite, "Electrite");

        itemFlorite = new ItemBase(FemtocraftConfigs.FloriteID, "ItemFlorite");
        LanguageRegistry.addName(itemFlorite,
                FemtocraftUtils.greenify("Florite"));
        GameRegistry.registerItem(itemFlorite, "Florite");

        // Micro
        itemMicroCrystal = new ItemBase(
                FemtocraftConfigs.MicroCrystalID, "ItemMicroCrystal");
        LanguageRegistry.addName(itemMicroCrystal,
                FemtocraftUtils.blueify("Micro Crystal"));
        GameRegistry.registerItem(itemMicroCrystal, "Micro Crystal");

        itemProteinChain = new ItemBase(
                FemtocraftConfigs.ProteinChainID, "ItemProteinChain");
        LanguageRegistry.addName(itemProteinChain,
                FemtocraftUtils.blueify("Protein Chain"));
        GameRegistry.registerItem(itemProteinChain, "Protein Chain");

        itemNerveCluster = new ItemBase(
                FemtocraftConfigs.NerveClusterID, "ItemNerveCluster");
        LanguageRegistry.addName(itemNerveCluster,
                FemtocraftUtils.blueify("Nerve Cluster"));
        GameRegistry.registerItem(itemNerveCluster, "Nerve Cluster");

        itemConductiveAlloy = new ItemBase(
                FemtocraftConfigs.ConductiveAlloyID, "ItemConductiveAlloy");
        LanguageRegistry.addName(itemConductiveAlloy,
                FemtocraftUtils.blueify("Conductive Alloy"));
        GameRegistry.registerItem(itemConductiveAlloy, "Conductive Alloy");

        itemMetalComposite = new ItemBase(
                FemtocraftConfigs.MetalCompositeID, "ItemMetalComposite");
        LanguageRegistry.addName(itemMetalComposite,
                FemtocraftUtils.blueify("Metal Composite"));
        GameRegistry.registerItem(itemMetalComposite, "Metal Composite");

        itemFibrousStrand = new ItemBase(
                FemtocraftConfigs.FibrousStrandID, "ItemFibrousStrand");
        LanguageRegistry.addName(itemFibrousStrand,
                FemtocraftUtils.blueify("Fibrous Strand"));
        GameRegistry.registerItem(itemFibrousStrand, "Fibrous Strand");

        itemMineralLattice = new ItemBase(
                FemtocraftConfigs.MineralLatticeID, "ItemMineralLattice");
        LanguageRegistry.addName(itemMineralLattice,
                FemtocraftUtils.blueify("Mineral Lattice"));
        GameRegistry.registerItem(itemMineralLattice, "Mineral Lattice");

        itemFungalSpores = new ItemBase(
                FemtocraftConfigs.FungalSporesID, "ItemFungalSpores");
        LanguageRegistry.addName(itemFungalSpores,
                FemtocraftUtils.blueify("Fungal Spores"));
        GameRegistry.registerItem(itemFungalSpores, "Fungal Spores");

        itemIonicChunk = new ItemBase(FemtocraftConfigs.IonicChunkID,
                "ItemIonicChunk");
        LanguageRegistry.addName(itemIonicChunk,
                FemtocraftUtils.blueify("Ionic Chunk"));
        GameRegistry.registerItem(itemIonicChunk, "Ionic Chunk");

        itemReplicatingMaterial = new ItemBase(
                FemtocraftConfigs.ReplicatingMaterialID,
                "ItemReplicatingMaterial");
        LanguageRegistry.addName(itemReplicatingMaterial,
                FemtocraftUtils.blueify("Replicating Material"));
        GameRegistry.registerItem(itemReplicatingMaterial,
                "Replicating Material");

        itemSpinyFilament = new ItemBase(
                FemtocraftConfigs.SpinyFilamentID, "ItemSpinyFilament");
        LanguageRegistry.addName(itemSpinyFilament,
                FemtocraftUtils.blueify("Spiny Filament"));
        GameRegistry.registerItem(itemSpinyFilament, "Spiny Filament");

        itemHardenedBulb = new ItemBase(
                FemtocraftConfigs.HardenedBulbID, "ItemHardenedBulb");
        LanguageRegistry.addName(itemHardenedBulb,
                FemtocraftUtils.blueify("Hardened Bulb"));
        GameRegistry.registerItem(itemHardenedBulb, "Hardened Bulb");

        itemMorphicChannel = new ItemBase(
                FemtocraftConfigs.MorphicChannelID, "ItemMorphicChannel");
        LanguageRegistry.addName(itemMorphicChannel,
                FemtocraftUtils.blueify("Morphic Channel"));
        GameRegistry.registerItem(itemMorphicChannel, "Morphic Channel");

        itemSynthesizedFiber = new ItemBase(
                FemtocraftConfigs.SynthesizedFiberID, "ItemSynthesizedFiber");
        LanguageRegistry.addName(itemSynthesizedFiber,
                FemtocraftUtils.blueify("Synthesized Fiber"));
        GameRegistry.registerItem(itemSynthesizedFiber, "Synthesized Fiber");

        itemOrganometallicPlate = new ItemBase(
                FemtocraftConfigs.OrganometallicPlateID,
                "ItemOrganometallicPlate");
        LanguageRegistry.addName(itemOrganometallicPlate,
                FemtocraftUtils.blueify("Organometallic Plate"));
        GameRegistry.registerItem(itemOrganometallicPlate,
                "Organometallic Plate");

        // End Decomp

        itemMicroPlating = new ItemBase(
                FemtocraftConfigs.microPlatingID, "ItemMicroPlating");
        LanguageRegistry.addName(itemMicroPlating, "Micro Plating");
        GameRegistry.registerItem(itemMicroPlating, "Micro Plating");

        ProxyClient.setCustomRenderers();
        // GameRegistry.registerTileEntity(TileEntity.class, "myTile");
        // GameRegistry.addRecipe(new ItemStack(itemId), new Object[] {});
        // EntityRegistry.registerModEntity(entity.class, "myEntity", 0, this,
        // 32, 10, true)
        recipeManager = new ManagerRecipe();
        researchManager = new ManagerResearch();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ManagerRecipe.assemblyRecipes.registerDefaultRecipes();
        researchManager.calculateGraph();
    }
}