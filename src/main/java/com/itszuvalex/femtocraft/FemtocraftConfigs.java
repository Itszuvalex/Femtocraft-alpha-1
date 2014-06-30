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

import com.itszuvalex.femtocraft.industry.items.ItemAssemblySchematic;
import net.minecraftforge.common.Configuration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class FemtocraftConfigs {
    public static final String CATEGORY_GENERATION = "Generation";
    public static final String CATEGORY_MULTIPLAYER = "Multiplayer";
    public static final String CATEGORY_DEBUG = "Debug";
    public static final String CATEGORY_RECIPE_CONFIGURATION = "Recipe Configuration";
    public static final String CATEGORY_ORE_CONFIGURATION = "Ore Configuration";
    // blocks
    public static
    @CfgId(block = true)
    int oreTitaniumID = 350;
    public static
    @CfgId(block = true)
    int orePlatinumID = 351;
    public static
    @CfgId(block = true)
    int oreThoriumID = 352;
    public static
    @CfgId(block = true)
    int oreFareniteID = 353;
    public static
    @CfgId(block = true)
    int oreMaleniteID = 354;

    // example use
    // public static @CfgId int itemId = 12000;
    // public static @CfgId(block=true) int blockId = 350;
    // public static @CfgBool boolean booleanConfig = false;
    public static
    @CfgId(block = true)
    int microStoneID = 360;
    public static
    @CfgId(block = true)
    int nanoStoneID = 361;
    public static
    @CfgId(block = true)
    int femtoStoneID = 362;
    public static
    @CfgId(block = true)
    int unidentifiedAlloyID = 363;
    public static
    @CfgId(block = true)
    int FemtocraftResearchComputerID = 365;
    public static
    @CfgId(block = true)
    int FemtocraftResearchConsoleID = 366;
    // TODO: moved microcable to something else - need to fill id 370
    public static
    @CfgId(block = true)
    int testID = 370;
    public static
    @CfgId(block = true)
    int FemtopowerGeneratorTestID = 371;
    public static
    @CfgId(block = true)
    int FemtopowerConsumerTestBlockID = 372;
    public static
    @CfgId(block = true)
    int FemtocraftMicroFurnaceUnlitID = 373;
    public static
    @CfgId(block = true)
    int FemtocraftMicroFurnaceLitID = 374;
    public static
    @CfgId(block = true)
    int FemtocraftMicroDeconstructorID = 375;
    public static
    @CfgId(block = true)
    int FemtocraftMicroReconstructorID = 376;
    public static
    @CfgId(block = true)
    int FemtocraftMicroEncoderID = 377;
    public static
    @CfgId(block = true)
    int FemtocraftMicroCrystallizerPlatformID = 378;
    public static
    @CfgId(block = true)
    int FemtocraftMicroCrystallizerShieldingID = 379;
    public static
    @CfgId(block = true)
    int FemtocraftNanoInnervatorUnlitID = 380;
    public static
    @CfgId(block = true)
    int FemtocraftNanoInnervatorLitID = 381;
    public static
    @CfgId(block = true)
    int FemtocraftNanoDismantlerID = 382;
    public static
    @CfgId(block = true)
    int FemtocraftNanoFabricatorID = 383;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEnmesherID = 384;
    public static
    @CfgId(block = true)
    int FemtocraftNanoHorologeID = 385;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEfflorescerPlatformID = 386;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEfflorescerShieldingID = 387;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoImpulserUnlitID = 388;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoImpulserLitID = 389;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoRepurposerID = 390;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoCoagulatorID = 391;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoEntanglerID = 392;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoChronoshifterID = 393;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoQuantumUnravelerPlatformID = 394;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoQuantumUnravelerShieldingID = 395;
    public static
    @CfgId(block = true)
    int FemtocraftVacuumTubeID = 396;
    public static
    @CfgId(block = true)
    int FemtocraftSuctionPipeID = 397;
    public static
    @CfgId(block = true)
    int FemtopowerMicroCubeID = 400;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingBaseID = 401;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingCoilID = 402;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingCapacitorID = 403;
    public static
    @CfgId(block = true)
    int FemtopowerNanoCubeFrameID = 405;
    public static
    @CfgId(block = true)
    int FemtopowerNanoCubePortID = 406;
    public static
    @CfgId(block = true)
    int FemtopowerNanoFissionReactorHousingID = 407;
    public static
    @CfgId(block = true)
    int FemtopowerNanoFissionReactorCoreID = 408;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubeFrameID = 409;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubeChassisID = 410;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubePortID = 411;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStelleratorCoreID = 412;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStelleratorFocusID = 413;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStellaratorHousingID = 414;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStellaratorOpticalMaserID = 415;
    public static
    @CfgId(block = true)
    int FemtocraftMassBlockID = 450;
    public static
    @CfgId(block = true)
    int BlockPlasmaID = 451;
    public static
    @CfgId(block = true)
    int microCableID = 500;
    public static
    @CfgId(block = true)
    int nanoCableID = 501;
    public static
    @CfgId(block = true)
    int femtoCableID = 502;
    public static
    @CfgId(block = true)
    int orbitalEqualizerID = 505;
    public static
    @CfgId(block = true)
    int nullEqualizerID = 506;
    // items
    public static
    @CfgId
    int ingotTitaniumID = 12000;
    public static
    @CfgId
    int ingotPlatinumID = 12001;
    public static
    @CfgId
    int ingotThoriumID = 12002;
    public static
    @CfgId
    int ingotFareniteID = 12003;
    public static
    @CfgId
    int ingotMaleniteID = 12004;
    public static
    @CfgId
    int ingotTemperedTitaniumID = 12005;
    public static
    @CfgId
    int deconstructedIronID = 12010;
    public static
    @CfgId
    int deconstructedGoldID = 12011;
    public static
    @CfgId
    int deconstructedTitaniumID = 12012;
    public static
    @CfgId
    int deconstructedThoriumID = 12013;
    public static
    @CfgId
    int deconstructedPlatinumID = 12014;
    public static
    @CfgId
    int conductivePowderID = 12020;
    public static
    @CfgId
    int boardID = 12029;
    public static
    @CfgId
    int primedBoardID = 12030;
    public static
    @CfgId
    int dopedBoardID = 12031;
    public static
    @CfgId
    int microCircuitID = 12032;
    public static
    @CfgId
    int spoolID = 12033;
    public static
    @CfgId
    int spoolGoldID = 12034;
    public static
    @CfgId
    int spoolPlatinumID = 12035;
    public static
    @CfgId
    int itemMicroLogicCoreID = 12036;
    public static
    @CfgId
    int itemHeatingCoilID = 12037;
    public static
    @CfgId
    int itemMicroCoilID = 12038;
    public static
    @CfgId
    int itemPortableResearchComputerID = 12039;
    public static
    @CfgId
    int itemMicroTechnologyID = 12040;
    public static
    @CfgId
    int itemNanoTechnologyID = 12041;
    public static
    @CfgId
    int itemFemtoTechnologyID = 12042;
    public static
    @CfgId
    int itemAdvancedTechnologyID = 12043;
    public static
    @CfgId
    int paperSchematicID = 12044;
    public static
    @CfgId
    int metalSchematicID = 12045;
    public static
    @CfgId
    int advMetalSchematicID = 12046;
    public static
    @CfgId
    int microInterfaceDeviceID = 12047;
    public static
    @CfgId
    int nanoInterfaceDeviceID = 12048;
    public static
    @CfgId
    int femtoInterfaceDeviceID = 12049;
    // Decomp items 12046 - 12069
    // Femto
    public static
    @CfgId
    int CubitID = 12050;
    public static
    @CfgId
    int RectangulonID = 12051;
    public static
    @CfgId
    int PlaneoidID = 12052;
    // Nano
    public static
    @CfgId
    int CrystalliteID = 12053;
    public static
    @CfgId
    int MineraliteID = 12054;
    public static
    @CfgId
    int MetalliteID = 12055;
    public static
    @CfgId
    int FauniteID = 12056;
    public static
    @CfgId
    int ElectriteID = 12057;
    public static
    @CfgId
    int FloriteID = 12058;
    // Micro
    public static
    @CfgId
    int MicroCrystalID = 12059;
    public static
    @CfgId
    int ProteinChainID = 12060;
    public static
    @CfgId
    int NerveClusterID = 12061;
    public static
    @CfgId
    int ConductiveAlloyID = 12062;
    public static
    @CfgId
    int MetalCompositeID = 12063;
    public static
    @CfgId
    int FibrousStrandID = 12064;
    public static
    @CfgId
    int MineralLatticeID = 12065;
    public static
    @CfgId
    int FungalSporesID = 12066;
    public static
    @CfgId
    int IonicChunkID = 12067;
    public static
    @CfgId
    int ReplicatingMaterialID = 12068;
    public static
    @CfgId
    int SpinyFilamentID = 12069;
    public static
    @CfgId
    int HardenedBulbID = 12070;
    public static
    @CfgId
    int MorphicChannelID = 12071;
    public static
    @CfgId
    int SynthesizedFiberID = 12072;
    public static
    @CfgId
    int OrganometallicPlateID = 12073;
    public static
    @CfgId
    int microPlatingID = 12100;
    // Produce 12070 - 12150
    public static
    @CfgId
    int tomatoSeedID = 12200;
    public static
    @CfgId
    int tomatoID = 12001;
    // Cooking 12150 - 12300 items and 370-375
    public static
    @CfgId(block = true)
    int cuttingBoardID = 450;
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
    boolean alloyGen = true;
    // Recipes
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_DEBUG)
    boolean silentRecipeLoadAlerts = false;
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

    public static void load(Configuration config) {
        try {

            // FluidMass Loading

            config.load();
            Field[] fields = FemtocraftConfigs.class.getFields();
            for (Field field : fields) {
                CfgId annotation = field.getAnnotation(CfgId.class);
                if (annotation != null) {
                    int id = field.getInt(null);
                    if (annotation.block()) {
                        id = config.getBlock(field.getName(), id).getInt();
                    }
                    else {
                        id = config.getItem(field.getName(), id).getInt();
                    }
                    field.setInt(null, id);
                }
                else if (field.isAnnotationPresent(CfgBool.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    }
                    else {
                        category = cat.category();
                    }

                    boolean bool = field.getBoolean(null);
                    bool = config.get(category, field.getName(), bool)
                                 .getBoolean(bool);
                    field.setBoolean(null, bool);
                }
                else if (field.isAnnotationPresent(CfgInt.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    }
                    else {
                        category = cat.category();
                    }

                    int cint = field.getInt(null);
                    cint = config.get(category, field.getName(), cint).getInt(
                            cint);
                    field.setInt(null, cint);
                }
                else if (field.isAnnotationPresent(CfgFloat.class)) {
                    CfgCat cat = field.getAnnotation(CfgCat.class);
                    String category;
                    if (cat == null) {
                        category = Configuration.CATEGORY_GENERAL;
                    }
                    else {
                        category = cat.category();
                    }

                    float cint = field.getFloat(null);
                    cint = (float) config.get(category, field.getName(), cint)
                                         .getDouble(cint);
                    field.setFloat(null, cint);
                }
                else {

                }
            }

            // Specific loads
            schematicInfiniteUseMultiplier = (float) config
                    .get("Item Constants",
                         "SchematicInfiniteUseMultiplier",
                         200.f,
                         "When AssemblerSchematics have infinite uses, this number will be used instead of the # of uses the schematic would be good for, when calculating the fluidMass required to key the schematic to a recipe.")
                    .getDouble(200.f);
            ItemAssemblySchematic.infiniteUseMassMultiplier = schematicInfiniteUseMultiplier;

        } catch (Exception e) {
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