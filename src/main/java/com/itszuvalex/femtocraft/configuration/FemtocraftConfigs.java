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

package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraftforge.common.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.logging.Level;

public class FemtocraftConfigs {
    public static final String CATEGORY_GENERATION = "Generation";
    public static final String CATEGORY_MULTIPLAYER = "Multiplayer";
    public static final String CATEGORY_DEBUG = "Debug";
    public static final String CATEGORY_RECIPE_CONFIGURATION = "Recipe Configuration";
    public static final String CATEGORY_ORE_CONFIGURATION = "Ore Configuration";
    // blocks
    public static
    @CfgId(block = true)
    int BlockOreTitaniumID;
    public static
    @CfgId(block = true)
    int BlockOrePlatinumID;
    public static
    @CfgId(block = true)
    int BlockOreThoriumID;
    public static
    @CfgId(block = true)
    int BlockOreFareniteID;
    public static
    @CfgId(block = true)
    int BlockOreMaleniteID;
    public static
    @CfgId(block = true)
    int BlockOreLodestoneID;
    public static
    @CfgId(block = true)
    int BlockMicroStoneID;
    public static
    @CfgId(block = true)
    int BlockNanoStoneID;
    public static
    @CfgId(block = true)
    int BlockFemtoStoneID;
    public static
    @CfgId(block = true)
    int BlockUnidentifiedAlloyID;
    public static
    @CfgId(block = true)
    int BlockResearchComputerID;
    public static
    @CfgId(block = true)
    int BlockResearchConsoleID;
    public static
    @CfgId(block = true)
    int BlockGeneratorTestID;
    public static
    @CfgId(block = true)
    int BlockConsumerTestID;
    public static
    @CfgId(block = true)
    int BlockMicroFurnaceUnlitID;
    public static
    @CfgId(block = true)
    int BlockMicroFurnaceLitID;
    public static
    @CfgId(block = true)
    int BlockMicroDeconstructorID;
    public static
    @CfgId(block = true)
    int BlockMicroReconstructorID;
    public static
    @CfgId(block = true)
    int BlockMicroEncoderID;
    public static
    @CfgId(block = true)
    int BlockNanoInnervatorUnlitID;
    public static
    @CfgId(block = true)
    int BlockNanoInnervatorLitID;
    public static
    @CfgId(block = true)
    int BlockNanoDismantlerID;
    public static
    @CfgId(block = true)
    int BlockNanoFabricatorID;
    public static
    @CfgId(block = true)
    int BlockNanoEnmesherID;
    public static
    @CfgId(block = true)
    int BlockNanoHorologeID;
    public static
    @CfgId(block = true)
    int BlockFemtoImpulserUnlitID;
    public static
    @CfgId(block = true)
    int BlockFemtoImpulserLitID;
    public static
    @CfgId(block = true)
    int BlockFemtoRepurposerID;
    public static
    @CfgId(block = true)
    int BlockFemtoCoagulatorID;
    public static
    @CfgId(block = true)
    int BlockFemtoEntanglerID;
    public static
    @CfgId(block = true)
    int BlockFemtoChronoshifterID;
    public static
    @CfgId(block = true)
    int BlockVacuumTubeID;
    public static
    @CfgId(block = true)
    int BlockSuctionPipeID;
    public static
    @CfgId(block = true)
    int BlockMicroCubeID;
    public static
    @CfgId(block = true)
    int BlockMicroChargingBaseID;
    public static
    @CfgId(block = true)
    int BlockMicroChargingCoilID;
    public static
    @CfgId(block = true)
    int BlockMicroChargingCapacitorID;
    public static
    @CfgId(block = true)
    int BlockElectrostaticGeneratorID;
    public static
    @CfgId(block = true)
    int BlockNanoCubeFrameID;
    public static
    @CfgId(block = true)
    int BlockNanoCubePortID;
    public static
    @CfgId(block = true)
    int BlockCryoEndothermalChargingBaseID;
    public static
    @CfgId(block = true)
    int BlockCryoEndothermalChargingCoilID;
    public static
    @CfgId(block = true)
    int BlockFissionReactorCoreID;
    public static
    @CfgId(block = true)
    int BlockFissionReactorHousingID;
    public static
    @CfgId(block = true)
    int BlockMagnetohydrodynamicGeneratorID;
    public static
    @CfgId(block = true)
    int BlockSteamGeneratorID;
    public static
    @CfgId(block = true)
    int BlockDecontaminationChamberID;
    public static
    @CfgId(block = true)
    int BlockFemtoCubeFrameID;
    public static
    @CfgId(block = true)
    int BlockFemtoCubeChassisID;
    public static
    @CfgId(block = true)
    int BlockFemtoCubePortID;
    public static
    @CfgId(block = true)
    int BlockPhlegethonTunnelCoreID;
    public static
    @CfgId(block = true)
    int BlockPhlegethonTunnelFrameID;
    public static
    @CfgId(block = true)
    int BlockSisyphusStabilizerID;
    public static
    @CfgId(block = true)
    int BlockFemtoStelleratorCoreID;
    public static
    @CfgId(block = true)
    int BlockFemtoStelleratorFocusID;
    public static
    @CfgId(block = true)
    int BlockFemtoStellaratorHousingID;
    public static
    @CfgId(block = true)
    int BlockFemtoStellaratorOpticalMaserID;
    public static
    @CfgId(block = true)
    int BlockPlasmaConduitID;
    public static
    @CfgId(block = true)
    int BlockPlasmaVentID;
    public static
    @CfgId(block = true)
    int BlockPlasmaTurbineID;
    public static
    @CfgId(block = true)
    int BlockPlasmaCondenserID;
    public static
    @CfgId(block = true)
    int BlockMassID;
    public static
    @CfgId(block = true)
    int BlockFluidMoltenSaltID;
    public static
    @CfgId(block = true)
    int BlockFluidCooledMoltenSaltID;
    public static
    @CfgId(block = true)
    int BlockFluidCooledContaminatedMoltenSaltID;
    public static
    @CfgId(block = true)
    int BlockPlasmaID;
    public static
    @CfgId(block = true)
    int BlockMicroCableID;
    public static
    @CfgId(block = true)
    int BlockNanoCableID;
    public static
    @CfgId(block = true)
    int BlockFemtoCableID;
    public static
    @CfgId(block = true)
    int BlockOrbitalEqualizerID;
    public static
    @CfgId(block = true)
    int BlockNullEqualizerID;
    // items
    public static
    @CfgId
    int ItemIngotTitaniumID;
    public static
    @CfgId
    int ItemIngotPlatinumID;
    public static
    @CfgId
    int ItemIngotThoriumID;
    public static
    @CfgId
    int ItemIngotFareniteID;
    public static
    @CfgId
    int ItemIngotMaleniteID;
    public static
    @CfgId
    int ItemIngotTemperedTitaniumID;
    public static
    @CfgId
    int ItemIngotThFaSaltID;
    public static
    @CfgId
    int ItemNuggetLodestoneID;
    public static
    @CfgId
    int ItemChunkLodestoneID;
    public static
    @CfgId
    int ItemConductivePowderID;
    public static
    @CfgId
    int ItemBoardID;
    public static
    @CfgId
    int ItemPrimedBoardID;
    public static
    @CfgId
    int ItemDopedBoardID;
    public static
    @CfgId
    int ItemMicrochipID;
    public static
    @CfgId
    int ItemSpoolID;
    public static
    @CfgId
    int ItemSpoolGoldID;
    public static
    @CfgId
    int ItemSpoolPlatinumID;
    public static
    @CfgId
    int ItemMicroLogicCoreID;
    public static
    @CfgId
    int ItemKineticPulverizerID;
    public static
    @CfgId
    int ItemArticutingArmID;
    public static
    @CfgId
    int ItemDissassemblyArrayID;
    public static
    @CfgId
    int ItemAssemblyArrayID;
    public static
    @CfgId
    int ItemHeatingCoilID;
    public static
    @CfgId
    int ItemMicroCoilID;
    public static
    @CfgId
    int ItemBatteryID;
    public static
    @CfgId
    int ItemVacuumCoreID;
    public static
    @CfgId
    int ItemPortableResearchComputerID;
    public static
    @CfgId
    int ItemThoriumRodID;
    public static
    @CfgId
    int ItemNanochipID;
    public static
    @CfgId
    int ItemNanoCalculatorID;
    public static
    @CfgId
    int ItemNanoRegulatorID;
    public static
    @CfgId
    int ItemNanoSimulatorID;
    public static
    @CfgId
    int ItemBasicAICoreID;
    public static
    @CfgId
    int ItemLearningCoreID;
    public static
    @CfgId
    int ItemSchedulerCoreID;
    public static
    @CfgId
    int ItemManagerCoreID;
    public static
    @CfgId
    int ItemFluidicConductorID;
    public static
    @CfgId
    int ItemNanoCoilID;
    public static
    @CfgId
    int ItemTemporalResonatorID;
    public static
    @CfgId
    int ItemDimensionalMonopoleID;
    public static
    @CfgId
    int ItemSelfFulfillingOracleID;
    public static
    @CfgId
    int ItemCrossDimensionalCommunicatorID;
    public static
    @CfgId
    int ItemInfallibleEstimatorID;
    public static
    @CfgId
    int ItemPanLocationalComputerID;
    public static
    @CfgId
    int ItemPandoraCubeID;
    public static
    @CfgId
    int ItemNanoPlatingID;
    public static
    @CfgId
    int ItemFissionReactorPlatingID;
    public static
    @CfgId
    int ItemMinosGateID;
    public static
    @CfgId
    int ItemCharosGateID;
    public static
    @CfgId
    int ItemCerberusGateID;
    public static
    @CfgId
    int ItemErinyesCircuitID;
    public static
    @CfgId
    int ItemMinervaComplexID;
    public static
    @CfgId
    int ItemAtlasMountID;
    public static
    @CfgId
    int ItemHermesBusID;
    public static
    @CfgId
    int ItemHerculesDriveID;
    public static
    @CfgId
    int ItemOrpheusProcessorID;
    public static
    @CfgId
    int ItemFemtoPlatingID;
    public static
    @CfgId
    int ItemStyxValveID;
    public static
    @CfgId
    int ItemFemtoCoilID;
    public static
    @CfgId
    int ItemPhlegethonTunnelPrimerID;
    public static
    @CfgId
    int ItemStellaratorPlatingID;
    public static
    @CfgId
    int ItemInfinitelyRecursiveALUID;
    public static
    @CfgId
    int ItemInfiniteVolumePolychoraID;
    public static
    @CfgId
    int ItemNucleationCoreID;
    public static
    @CfgId
    int ItemInhibitionCoreID;
    public static
    @CfgId
    int ItemMicroTechnologyID;
    public static
    @CfgId
    int ItemNanoTechnologyID;
    public static
    @CfgId
    int ItemFemtoTechnologyID;
    public static
    @CfgId
    int ItemPaperSchematicID;
    public static
    @CfgId
    int ItemDigitalSchematicID;
    public static
    @CfgId
    int ItemQuantumSchematicID;
    public static
    @CfgId
    int ItemMicroInterfaceDeviceID;
    public static
    @CfgId
    int ItemNanoInterfaceDeviceID;
    public static
    @CfgId
    int ItemFemtoInterfaceDeviceID;    // Decomp items 12046 - 12069
    // Femto
    public static
    @CfgId
    int ItemCubitID;
    public static
    @CfgId
    int ItemRectangulonID;
    public static
    @CfgId
    int ItemPlaneoidID;    // Nano
    public static
    @CfgId
    int ItemCrystalliteID;
    public static
    @CfgId
    int ItemMineraliteID;
    public static
    @CfgId
    int ItemMetalliteID;
    public static
    @CfgId
    int ItemFauniteID;
    public static
    @CfgId
    int ItemElectriteID;
    public static
    @CfgId
    int ItemFloriteID;    // Micro
    public static
    @CfgId
    int ItemMicroCrystalID;
    public static
    @CfgId
    int ItemProteinChainID;
    public static
    @CfgId
    int ItemNerveClusterID;
    public static
    @CfgId
    int ItemConductiveAlloyID;
    public static
    @CfgId
    int ItemMetalCompositeID;
    public static
    @CfgId
    int ItemFibrousStrandID;
    public static
    @CfgId
    int ItemMineralLatticeID;
    public static
    @CfgId
    int ItemFungalSporesID;
    public static
    @CfgId
    int ItemIonicChunkID;
    public static
    @CfgId
    int ItemReplicatingMaterialID;
    public static
    @CfgId
    int ItemSpinyFilamentID;
    public static
    @CfgId
    int ItemHardenedBulbID;
    public static
    @CfgId
    int ItemMorphicChannelID;
    public static
    @CfgId
    int ItemSynthesizedFiberID;
    public static
    @CfgId
    int ItemOrganometallicPlateID;
    public static
    @CfgId
    int ItemMicroPlatingID;
    // bool
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_MULTIPLAYER)
    boolean requirePlayersOnlineForTileEntityTicks = false;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean worldGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean retroGen = false;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean titaniumGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean platinumGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean thoriumGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean fareniteGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean maleniteGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean lodestoneGen = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_GENERATION)
    boolean alloyGen = true;
    // Recipes
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_DEBUG)
    boolean silentRecipeLoadAlerts = false;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_DEBUG)
    boolean retrogenAlerts = false;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemCrystallite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMineralite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMetallite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemFaunite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemElectrite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemFlorite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMicroCrystal = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemProteinChain = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemNerveCluster = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemConductiveAlloy = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMetalComposite = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemFibrousStrand = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMineralLattice = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemFungalSpores = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemIonicChunk = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemReplicatingMaterial = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemSpinyFilament = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemHardenedBulb = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemMorphicChannel = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemSynthesizedFiber = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeItemOrganometallicPlate = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeStone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGrass = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeDirt = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCobblestone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeWoodPlank = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSapling = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSand = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeLeaves = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCobweb = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeDeadBush = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeDandelion = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRose = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMushroomBrown = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMushroomRed = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMossStone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeObsidian = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeIce = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCactus = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePumpkin = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeNetherrack = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSoulSand = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGlowstone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMelon = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeVine = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMycelium = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeLilyPad = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeEnderStone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCocoa = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeApple = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCoal = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeDiamond = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeIronIngot = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGoldIngot = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeStick = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeString = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeFeather = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGunpowder = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSeeds = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeWheat = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeFlint = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRawPorkchop = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePorkchop = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRedstone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSnowball = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeLeather = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeClay = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSugarCane = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSlimeball = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeEgg = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRawFish = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCookedFish = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeDye = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeBone = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePumpkinSeeds = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeMelonSeeds = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRawBeef = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCookedBeef = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRawChicken = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCookedChicken = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeRottenFlesh = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeEnderPearl = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGhastTear = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeNetherWart = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeSpiderEye = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeBlazePowder = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeEmerald = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCarrot = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePotato = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeBakedPotato = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePoisonPotato = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeCake = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeNetherStar = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeIronOre = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeGoldOre = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeTitaniumOre = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipeThoriumOre = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION)
    boolean recipePlatinumOre = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerTitaniumOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int titaniumOreVeinsPerChunkCount = 7;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int titaniumOreBlockPerVeinCount = 6;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int titaniumOreYHeightMax = 40;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int titaniumOreYHeightMin = 0;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerThoriumOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int thoriumOreVeinsPerChunkCount = 8;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int thoriumOreBlockPerVeinCount = 6;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int thoriumOreYHeightMax = 50;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int thoriumOreYHeightMin = 0;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerPlatinumOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int platinumOreVeinsPerChunkCount = 5;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int platinumOreBlockPerVeinCount = 5;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int platinumOreYHeightMax = 30;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int platinumOreYHeightMin = 0;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerFareniteOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int fareniteOreVeinsPerChunkCount = 10;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int fareniteOreBlockPerVeinCount = 6;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int fareniteOreYHeightMax = 40;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int fareniteOreYHeightMin = 0;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerMaleniteOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int maleniteOreVeinsPerChunkCount = 14;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int maleniteOreBlockPerVeinCount = 10;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int maleniteOreYHeightMax = 118;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int maleniteOreYHeightMin = 10;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerLodestoneOreInOreDictionary = true;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int lodestoneOreVeinsPerChunkCount = 10;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int lodestoneOreBlockPerVeinCount = 7;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int lodestoneOreYHeightMax = 140;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    int lodestoneOreYHeightMin = 60;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerTitaniumDustInOreDictionary = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerThoriumDustInOreDictionary = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerPlatinumDustInOreDictionary = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerTitaniumIngotInOreDictionary = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerThoriumIngotInOreDictionary = true;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_ORE_CONFIGURATION)
    boolean registerPlatinumIngotInOreDictionary = true;
    public static float schematicInfiniteUseMultiplier = 200.f;


    private static int baseItemID = 12000;
    private static int baseBlockID = 350;

    static {
        FemtocraftConfigHelper.init();
    }

    public static void load(Configuration config) {
        try {

            // FluidMass Loading

            config.load();
            Field[] fields = FemtocraftConfigs.class.getFields();
            for (Field field : fields) {
                CfgId annotation = field.getAnnotation(CfgId.class);
                if (annotation != null) {
                    int id;
                    if (annotation.block()) {
                        id = baseBlockID;
                        id = config.getBlock(field.getName(), id).getInt();
                        if (id == baseBlockID) baseBlockID++;
                    } else {
                        id = baseItemID;
                        id = config.getItem(field.getName(), id).getInt();
                        if (id == baseItemID) baseItemID++;
                    }
                    field.setInt(null, id);
                } else if (field.isAnnotationPresent(CfgBool.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    } else {
                        category = cat.category();
                    }

                    boolean bool = field.getBoolean(null);
                    bool = config.get(category, field.getName(), bool)
                            .getBoolean(bool);
                    field.setBoolean(null, bool);
                } else if (field.isAnnotationPresent(CfgInt.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    } else {
                        category = cat.category();
                    }

                    int cint = field.getInt(null);
                    cint = config.get(category, field.getName(), cint).getInt(
                            cint);
                    field.setInt(null, cint);
                } else if (field.isAnnotationPresent(CfgFloat.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    } else {
                        category = cat.category();
                    }

                    float cint = field.getFloat(null);
                    cint = (float) config.get(category, field.getName(), cint)
                            .getDouble(cint);
                    field.setFloat(null, cint);
                } else {

                }
            }

//            // Specific loads
//            schematicInfiniteUseMultiplier = (float) config
//                    .get("Item Constants",
//                            "SchematicInfiniteUseMultiplier",
//                            200.f,
//                            "When AssemblerSchematics have infinite uses, this number will be used instead of the #
// " +
//                                    "of uses the schematic would be good for,
// when calculating the fluidMass required to key " +
//                                    "the schematic to a recipe.")
//                    .getDouble(200.f);
//            ItemAssemblySchematic.infiniteUseMassMultiplier = schematicInfiniteUseMultiplier;

            FemtocraftConfigHelper.loadClassConstants(config);
        } catch (Exception e) {
            Femtocraft.logger.log(Level.SEVERE, "Error occured when attempting to load from configs.");
            // failed to load configs log
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgId {
        public boolean block() default false;
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgBool {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgInt {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgFloat {
    }

    @Retention(RetentionPolicy.RUNTIME)
    private static @interface CfgCat {
        public String category() default Configuration.CATEGORY_GENERAL;
    }

}