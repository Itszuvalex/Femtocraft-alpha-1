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
    int oreTitaniumID;
    public static
    @CfgId(block = true)
    int orePlatinumID;
    public static
    @CfgId(block = true)
    int oreThoriumID;
    public static
    @CfgId(block = true)
    int oreFareniteID;
    public static
    @CfgId(block = true)
    int oreMaleniteID;
    public static
    @CfgId(block = true)
    int microStoneID;
    public static
    @CfgId(block = true)
    int nanoStoneID;
    public static
    @CfgId(block = true)
    int femtoStoneID;
    public static
    @CfgId(block = true)
    int unidentifiedAlloyID;
    public static
    @CfgId(block = true)
    int FemtocraftResearchComputerID;
    public static
    @CfgId(block = true)
    int FemtocraftResearchConsoleID;
    public static
    @CfgId(block = true)
    int testID;
    public static
    @CfgId(block = true)
    int FemtopowerGeneratorTestID;
    public static
    @CfgId(block = true)
    int FemtopowerConsumerTestBlockID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroFurnaceUnlitID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroFurnaceLitID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroDeconstructorID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroReconstructorID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroEncoderID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroCrystallizerPlatformID;
    public static
    @CfgId(block = true)
    int FemtocraftMicroCrystallizerShieldingID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoInnervatorUnlitID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoInnervatorLitID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoDismantlerID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoFabricatorID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEnmesherID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoHorologeID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEfflorescerPlatformID;
    public static
    @CfgId(block = true)
    int FemtocraftNanoEfflorescerShieldingID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoImpulserUnlitID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoImpulserLitID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoRepurposerID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoCoagulatorID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoEntanglerID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoChronoshifterID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoQuantumUnravelerPlatformID;
    public static
    @CfgId(block = true)
    int FemtocraftFemtoQuantumUnravelerShieldingID;
    public static
    @CfgId(block = true)
    int FemtocraftVacuumTubeID;
    public static
    @CfgId(block = true)
    int FemtocraftSuctionPipeID;
    public static
    @CfgId(block = true)
    int FemtopowerMicroCubeID;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingBaseID;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingCoilID;
    public static
    @CfgId(block = true)
    int FemtopowerMicroChargingCapacitorID;
    public static
    @CfgId(block = true)
    int FemtopowerNanoCubeFrameID;
    public static
    @CfgId(block = true)
    int FemtopowerNanoCubePortID;
    public static
    @CfgId(block = true)
    int FemtopowerNanoFissionReactorHousingID;
    public static
    @CfgId(block = true)
    int FemtopowerNanoFissionReactorCoreID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubeFrameID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubeChassisID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoCubePortID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStelleratorCoreID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStelleratorFocusID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStellaratorHousingID;
    public static
    @CfgId(block = true)
    int FemtopowerFemtoStellaratorOpticalMaserID;
    public static
    @CfgId(block = true)
    int FemtocraftMassBlockID;
    public static
    @CfgId(block = true)
    int BlockPlasmaID;
    public static
    @CfgId(block = true)
    int microCableID;
    public static
    @CfgId(block = true)
    int nanoCableID;
    public static
    @CfgId(block = true)
    int femtoCableID;
    public static
    @CfgId(block = true)
    int orbitalEqualizerID;
    public static
    @CfgId(block = true)
    int nullEqualizerID;    // items
    public static
    @CfgId
    int ingotTitaniumID;
    public static
    @CfgId
    int ingotPlatinumID;
    public static
    @CfgId
    int ingotThoriumID;
    public static
    @CfgId
    int ingotFareniteID;
    public static
    @CfgId
    int ingotMaleniteID;
    public static
    @CfgId
    int ingotTemperedTitaniumID;
    public static
    @CfgId
    int conductivePowderID;
    public static
    @CfgId
    int boardID;
    public static
    @CfgId
    int primedBoardID;
    public static
    @CfgId
    int dopedBoardID;
    public static
    @CfgId
    int microCircuitID;
    public static
    @CfgId
    int spoolID;
    public static
    @CfgId
    int spoolGoldID;
    public static
    @CfgId
    int spoolPlatinumID;
    public static
    @CfgId
    int itemMicroLogicCoreID;
    public static
    @CfgId
    int itemHeatingCoilID;
    public static
    @CfgId
    int itemMicroCoilID;
    public static
    @CfgId
    int itemPortableResearchComputerID;
    public static
    @CfgId
    int itemMicroTechnologyID;
    public static
    @CfgId
    int itemNanoTechnologyID;
    public static
    @CfgId
    int itemFemtoTechnologyID;
    public static
    @CfgId
    int paperSchematicID;
    public static
    @CfgId
    int digitalSchematicID;
    public static
    @CfgId
    int quantumSchematicID;
    public static
    @CfgId
    int microInterfaceDeviceID;
    public static
    @CfgId
    int nanoInterfaceDeviceID;
    public static
    @CfgId
    int femtoInterfaceDeviceID;    // Decomp items 12046 - 12069
    // Femto
    public static
    @CfgId
    int CubitID;
    public static
    @CfgId
    int RectangulonID;
    public static
    @CfgId
    int PlaneoidID;    // Nano
    public static
    @CfgId
    int CrystalliteID;
    public static
    @CfgId
    int MineraliteID;
    public static
    @CfgId
    int MetalliteID;
    public static
    @CfgId
    int FauniteID;
    public static
    @CfgId
    int ElectriteID;
    public static
    @CfgId
    int FloriteID;    // Micro
    public static
    @CfgId
    int MicroCrystalID;
    public static
    @CfgId
    int ProteinChainID;
    public static
    @CfgId
    int NerveClusterID;
    public static
    @CfgId
    int ConductiveAlloyID;
    public static
    @CfgId
    int MetalCompositeID;
    public static
    @CfgId
    int FibrousStrandID;
    public static
    @CfgId
    int MineralLatticeID;
    public static
    @CfgId
    int FungalSporesID;
    public static
    @CfgId
    int IonicChunkID;
    public static
    @CfgId
    int ReplicatingMaterialID;
    public static
    @CfgId
    int SpinyFilamentID;
    public static
    @CfgId
    int HardenedBulbID;
    public static
    @CfgId
    int MorphicChannelID;
    public static
    @CfgId
    int SynthesizedFiberID;
    public static
    @CfgId
    int OrganometallicPlateID;
    public static
    @CfgId
    int microPlatingID;
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


    private static int baseItemID = 12000;
    private static int baseBlockID = 350;

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
                    }
                    else {
                        id = baseItemID;
                        id = config.getItem(field.getName(), id).getInt();
                        if (id == baseItemID) baseItemID++;
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