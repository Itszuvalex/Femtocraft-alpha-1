package femtocraft;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import net.minecraftforge.common.Configuration;

public class FemtocraftConfigs {
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgId {
		public boolean block() default false;
	}
	@Retention(RetentionPolicy.RUNTIME)
	private static @interface CfgBool {}

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
	public static @CfgId(block=true) int FemtopowerMicroCubeID = 363;
	public static @CfgId(block=true) int FemtocraftVacuumTubeID = 364;
	
	public static @CfgId(block=true) int FemtocraftMassBlock = 400;
	
	//items
	public static @CfgId int ingotTitaniumID = 12000;
	public static @CfgId int ingotPlatinumID = 12001;
	public static @CfgId int ingotThoriumID = 12002;
	public static @CfgId int ingotFareniteID = 12003;
	
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
	public static @CfgBool boolean requirePlayersOnlineForTileEntityTicks = false;
	
	public static @CfgBool boolean worldGen = true;
	public static @CfgBool boolean titaniumGen = true;
	public static @CfgBool boolean platinumGen = true;
	public static @CfgBool boolean thoriumGen = true;
	public static @CfgBool boolean fareniteGen = true;
	
	public static @CfgBool boolean alloyGen = true;

	public static void  load(Configuration config) {
		try {
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
						boolean bool = field.getBoolean(null);
						bool = config.get(Configuration.CATEGORY_GENERAL,
								field.getName(), bool).getBoolean(bool);
						field.setBoolean(null, bool);
					}
				}
			}
		} catch(Exception e) {
			//failed to load configs log
		} finally {
			if(config.hasChanged())
				config.save();
		}
	}

}