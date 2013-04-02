package femtocraft;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

import net.minecraftforge.common.Configuration;

public class Configs {
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
	public static @CfgId(block=true) int FemtopowerGeneratorID = 359;
	public static @CfgId(block=true) int FemtopowerConsumerBlockID = 360;
	
	//items
	public static @CfgId int ingotTitaniumID = 12000;
	public static @CfgId int ingotPlatinumID = 12001;
	public static @CfgId int ingotThoriumID = 12002;
	public static @CfgId int ingotFareniteID = 12003;
	
	//Produce  12070 - 12150
	public static @CfgId int tomatoSeedID = 12070;
	public static @CfgId int tomatoID = 12071;
	
	//Cooking 12150 - 12300 items and 370-375
	public static @CfgId(block=true) int cuttingBoardID = 370;
	
	//bool
	public static @CfgBool boolean worldGen = true;
	public static @CfgBool boolean titaniumGen = true;
	public static @CfgBool boolean platinumGen = true;
	public static @CfgBool boolean thoriumGen = true;
	public static @CfgBool boolean fareniteGen = true;
	
	public static @CfgBool boolean alloyGen = true;

	public static void  load(Configuration config) {
		try {
			config.load();
			Field[] fields = Configs.class.getFields();
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