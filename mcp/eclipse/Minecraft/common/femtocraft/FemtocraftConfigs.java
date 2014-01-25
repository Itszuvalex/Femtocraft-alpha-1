package femtocraft;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import femtocraft.industry.items.AssemblySchematic;

import net.minecraftforge.common.Configuration;

public class FemtocraftConfigs {
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgId {
		public boolean block() default false;
	}
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgBool {}
	
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgCat {
		public String category() default Configuration.CATEGORY_GENERAL;
	}

	public static final String CATEGORY_GENERATION = "Generation";
	public static final String CATEGORY_MULTIPLAYER = "Multiplayer";
	public static final String CATEGORY_DEBUG = "Debug";
	public static final String CATEGORY_RECIPE_CONFIGURATION = "Recipe Configuration";
	public static final String CATEGORY_ORE_CONFIGURATION = "Ore Configuration";
	
	//example use
	//public static @CfgId int itemId = 12000;
	//public static @CfgId(block=true) int blockId = 350;
	//public static @CfgBool boolean booleanConfig = false;
	
	//blocks
	public static @CfgId(block=true) int oreTitaniumID = 350;
	public static @CfgId(block=true) int orePlatinumID = 351;
	public static @CfgId(block=true) int oreThoriumID = 352;
	public static @CfgId(block=true) int oreFareniteID = 353;
	public static @CfgId(block=true) int microStoneID = 354;
	public static @CfgId(block=true) int nanoStoneID = 355;
	public static @CfgId(block=true) int femtoStoneID = 356;
	public static @CfgId(block=true) int unidentifiedAlloyID = 357;
	public static @CfgId(block=true) int FemtopowerCableID = 358;
	public static @CfgId(block=true) int FemtopowerGeneratorTestID = 359;
	public static @CfgId(block=true) int FemtopowerConsumerTestBlockID = 360;
	public static @CfgId(block=true) int FemtocraftMicroFurnaceUnlitID = 361;
	public static @CfgId(block=true) int FemtocraftMicroFurnaceLitID = 362;
	public static @CfgId(block=true) int FemtocraftMicroDeconstructorID = 363;
	public static @CfgId(block=true) int FemtocraftMicroReconstructorID = 364;
	public static @CfgId(block=true) int FemtopowerMicroCubeID = 365;
	public static @CfgId(block=true) int FemtocraftVacuumTubeID = 366;
	
	public static @CfgId(block=true) int FemtocraftMassBlock = 400;
	
	//items
	public static @CfgId int ingotTitaniumID = 12000;
	public static @CfgId int ingotPlatinumID = 12001;
	public static @CfgId int ingotThoriumID = 12002;
	public static @CfgId int ingotFareniteID = 12003;
	
	public static @CfgId int deconstructedIronID = 12004;
	public static @CfgId int deconstructedGoldID = 12005;
	public static @CfgId int deconstructedTitaniumID = 12006;
	public static @CfgId int deconstructedThoriumID = 12007;
	public static @CfgId int deconstructedPlatinumID = 12008;
	
	public static @CfgId int conductivePowderID = 12012;
	public static @CfgId int boardID = 12013;
	public static @CfgId int primedBoardID = 12014;
	public static @CfgId int dopedBoardID = 12015;
	public static @CfgId int microCircuitID = 12016;
	
	public static @CfgId int paperSchematicID = 12009;
	public static @CfgId int metalSchematicID = 12010;
	public static @CfgId int advMetalSchematicID = 12011;
	
	public static @CfgId int microInterfaceDeviceID = 12043;
	public static @CfgId int nanoInterfaceDeviceID = 12044;
	public static @CfgId int femtoInterfaceDeviceID = 12045;
	
	//Decomp items    12046 - 12069
	//Femto
	public static @CfgId int CubitID = 12046;
	public static @CfgId int RectangulonID = 12047;
	public static @CfgId int PlaneoidID = 12048;
	//Nano
	public static @CfgId int CrystalliteID = 12049;
	public static @CfgId int MineraliteID = 12050;
	public static @CfgId int MetalliteID = 12051;
	public static @CfgId int FauniteID = 12052;
	public static @CfgId int ElectriteID = 12053;
	public static @CfgId int FloriteID = 12054;
	//Micro
	public static @CfgId int MicroCrystalID = 12055;
	public static @CfgId int ProteinChainID = 12056;
	public static @CfgId int NerveClusterID = 12057;
	public static @CfgId int ConductiveAlloyID = 12058;
	public static @CfgId int MetalCompositeID = 12059;
	public static @CfgId int FibrousStrandID = 12060;
	public static @CfgId int MineralLatticeID = 12061;
	public static @CfgId int FungalSporesID = 12062;
	public static @CfgId int IonicChunkID = 12063;
	public static @CfgId int ReplicatingMaterialID = 12064;
	public static @CfgId int SpinyFilamentID = 12065;
	public static @CfgId int HardenedBulbID = 12066;
	public static @CfgId int MorphicChannelID = 12067;
	public static @CfgId int SynthesizedFiberID = 12068;
	public static @CfgId int OrganometallicPlateID = 12069;
	
	
	//Produce  12070 - 12150
	public static @CfgId int tomatoSeedID = 12070;
	public static @CfgId int tomatoID = 12071;
	
	//Cooking 12150 - 12300 items and 370-375
	public static @CfgId(block=true) int cuttingBoardID = 370;
	
	//bool
	public static @CfgBool @CfgCat(category = CATEGORY_MULTIPLAYER) boolean requirePlayersOnlineForTileEntityTicks = false;
	
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean worldGen = true;
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean titaniumGen = true;
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean platinumGen = true;
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean thoriumGen = true;
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean fareniteGen = true;
	
	public static @CfgBool @CfgCat(category = CATEGORY_GENERATION) boolean alloyGen = true;
	
	//Recipes
	public static @CfgBool @CfgCat(category = CATEGORY_DEBUG) boolean silentRecipeLoadAlerts = false;
	
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeCrystallite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMineralite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMetallite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeFaunite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeElectrite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeFlorite = true;
	
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMicroCrystal = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeProteinChain = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeNerveCluster = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeConductiveAlloy = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMetalComposite = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeFibrousStrand = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMineralLattice = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeFungalSpores = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeIonicChunk = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeReplicatingMaterial = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeSpinyFilament = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeHardenedBulb = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeMorphicChannel = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeSynthesizedFiber = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeOrganometallicPlate = true;
	
	
	
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeIronOre = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeGoldOre = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeTitaniumOre = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipeThoriumOre = true;
	public static @CfgBool @CfgCat(category = CATEGORY_RECIPE_CONFIGURATION) boolean recipePlatinumOre = true;
	
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerTitaniumOreInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerThoriumOreInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerPlatinumOreInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerTitaniumDustInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerThoriumDustInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerPlatinumDustInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerTitaniumIngotInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerThoriumIngotInOreDictionary = true;
	public static @CfgBool @CfgCat(category = CATEGORY_ORE_CONFIGURATION) boolean registerPlatinumIngotInOreDictionary = true;
	
	public static float schematicInfiniteUseMultiplier = 200.f;

	public static void  load(Configuration config) {
		try {
			
			//Mass Loading
			
			config.load();
			Field[] fields = FemtocraftConfigs.class.getFields();
			for(Field field : fields) {
				CfgId annotation = field.getAnnotation(CfgId.class);
				if(annotation != null) {
					int id = field.getInt(null);
					if(annotation.block()){
						id = config.getBlock(field.getName(), id).getInt();
					}else{
						id = config.getItem(field.getName(), id).getInt();
					}
					field.setInt(null, id);
				} else {
					if(field.isAnnotationPresent(CfgBool.class)){
						CfgCat cat = field.getAnnotation(CfgCat.class);
						String category;
						if(cat == null)
						{
							category = Configuration.CATEGORY_GENERAL;
						}
						else
						{
							category = cat.category();
						}
						
						boolean bool = field.getBoolean(null);
						bool = config.get(category,
								field.getName(), bool).getBoolean(bool);
						field.setBoolean(null, bool);
					}
				}
			}
			
			//Specific loads
			schematicInfiniteUseMultiplier = (float) config.get("Item Constants", "SchematicInfiniteUseMultiplier", 200.f, "When AssemblerSchematics have infinite uses, this number will be used instead of the # of uses the schematic would be good for, when calculating the mass required to key the schematic to a recipe.").getDouble(200.f);
			AssemblySchematic.infiniteUseMassMultiplier = schematicInfiniteUseMultiplier;
			
			
		} catch(Exception e) {
			//failed to load configs log
		} finally {
			if(config.hasChanged())
				config.save();
		}
	}

}