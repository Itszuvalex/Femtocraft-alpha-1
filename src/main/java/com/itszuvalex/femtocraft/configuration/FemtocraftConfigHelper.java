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
import com.itszuvalex.femtocraft.industry.items.ItemAssemblySchematic;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/10/14.
 */
public class FemtocraftConfigHelper {
    public static final String CLASS_CONSTANTS_KEY = "Class Constants";
    private static List<Class> configurableClasses = new ArrayList<Class>();

    /**
     * @param clazz Class to load all @Configurable annotated public/private fields from.
     * @return True if class successfully added.
     */
    public static boolean registerConfigurableClass(Class clazz) {
        return configurableClasses.add(clazz);
    }

    public static void loadClassConstants(Configuration configuration) {
        for (Class clazz : configurableClasses) {
            Field[] fields = clazz.getFields();
            for (Field field : fields) {
                boolean accessible = field.isAccessible();
                if (!field.isAccessible()) field.setAccessible(true);
                Configurable canno = field.getAnnotation(Configurable.class);
                if (canno != null) {
                    IFieldLoader loader = loaderMap.get(field.getType());
                    if (loader != null) {
                        try {
                            loader.load(field, CLASS_CONSTANTS_KEY + Configuration.CATEGORY_SPLITTER + clazz.getSimpleName(), canno, configuration);
                        } catch (IllegalAccessException e) {
                            Femtocraft.logger.log(Level.SEVERE, "Error loading @Configurable field " + field.getName() + " in class " + clazz.getName() + ".");
                            e.printStackTrace();
                        }
                    }
                }
                if (!accessible) {
                    field.setAccessible(false);
                }
            }
        }
    }


    private static interface IFieldLoader {
        void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException;
    }

    private static Map<Class, IFieldLoader> loaderMap = new HashMap<Class, IFieldLoader>();

    public static void init() {
        registerConfigLoaders();
        registerConfigurableClasses();
    }

    private static void registerConfigurableClasses() {
        registerConfigurableClass(ItemAssemblySchematic.class);
    }

    private static void registerConfigLoaders() {
        loaderMap.put(int.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                field.setInt(null, config.get(section, field.getName(), field.getInt(null), anno.comment()).getInt());
            }
        });
        loaderMap.put(int[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(null, prop.getIntList());
            }
        });
        loaderMap.put(String.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                field.set(null, config.get(section, field.getName(), (String) field.get(null), anno.comment()).getString());
            }
        });
        loaderMap.put(String[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(null, prop.getStringList());
            }
        });
        loaderMap.put(boolean.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                field.setBoolean(null, config.get(section, field.getName(), field.getBoolean(null), anno.comment()).getBoolean(field.getBoolean(null)));
            }
        });
        loaderMap.put(boolean[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(null, prop.getBooleanList());
            }
        });
        loaderMap.put(double.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                field.setDouble(null, config.get(section, field.getName(), field.getDouble(null), anno.comment()).getDouble(field.getDouble(null)));
            }
        });
        loaderMap.put(double[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(null, prop.getDoubleList());
            }
        });
        loaderMap.put(float.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Configuration config) throws IllegalAccessException {
                field.setFloat(null, (float) config.get(section, field.getName(), (double) field.getFloat(null), anno.comment()).getDouble((double) field.getFloat(null)));
            }
        });
    }

}
