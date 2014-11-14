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
import net.minecraftforge.common.config.Configuration;
import org.apache.logging.log4j.Level;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;

public class FemtocraftConfigs {
    public static final String CATEGORY_GENERATION = "Generation";
    public static final String CATEGORY_MULTIPLAYER = "Multiplayer";
    public static final String CATEGORY_DEBUG = "Debug";
    public static final String CATEGORY_RECIPE_CONFIGURATION = "Recipe Configuration";
    public static final String CATEGORY_ORE_CONFIGURATION = "Ore Configuration";
    public static final String CATEGORY_TECHNOLOGY = "Technology Configuration";
    // bool
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_MULTIPLAYER)
    boolean requirePlayersOnlineForTileEntityTicks = false;
    public static
    @CfgInt
    @CfgCat(category = CATEGORY_GENERATION)
    int GENERATION_WEIGHT = 1;
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

    public static
    @CfgBool
    @CfgCat(category = CATEGORY_TECHNOLOGY)
    boolean useCustomTechnologies = false;
    public static
    @CfgBool
    @CfgCat(category = CATEGORY_TECHNOLOGY)
    boolean useXMLFile = true;

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
                if (field.isAnnotationPresent(CfgBool.class)) {
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

            ConfigurableClassFinder ccf = new ConfigurableClassFinder("com.itszuvalex.femtocraft");
            ccf.registerConfigurableClasses();
            ccf.loadClassConstants(config);

        } catch (Exception e) {
            Femtocraft.log(Level.ERROR, "Error occured when attempting to load from configs.");
            // failed to load configs log
        } finally {
            if (config.hasChanged()) {
                config.save();
            }
        }
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