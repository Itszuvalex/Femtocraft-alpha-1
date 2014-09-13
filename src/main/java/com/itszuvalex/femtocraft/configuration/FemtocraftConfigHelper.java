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
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import java.lang.reflect.Field;
import java.util.*;
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
            loadClassFromConfig(configuration,
                    CLASS_CONSTANTS_KEY, clazz.getSimpleName(), clazz);
        }
    }

    private static void loadClassFromConfig(Configuration configuration, String section, String key, Class clazz) {
        loadClassInstanceFromConfig(configuration, section, key, clazz, null);
    }

    public static void loadClassInstanceFromConfig(Configuration configuration, String section, String key,
                                                   Class clazz, Object obj) {
        Field[] fields = clazz.getFields();
        if (obj != null) {
            ArrayList<Field> fieldsList = new ArrayList<Field>(Arrays.asList(fields));
            Class superclass = clazz.getSuperclass();
            while (superclass != null) {
                fieldsList.addAll(Arrays.asList(clazz.getFields()));
                superclass = superclass.getSuperclass();
            }
            fields = (Field[]) fieldsList.toArray();
        }
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            if (!field.isAccessible()) field.setAccessible(true);
            Configurable canno = field.getAnnotation(Configurable.class);
            if (canno != null) {
                IFieldLoader loader = loaderMap.get(field.getType());
                if (loader != null) {
                    try {
                        loader.load(field, section + Configuration.CATEGORY_SPLITTER + key, canno, obj, configuration);
                    } catch (IllegalAccessException e) {
                        Femtocraft.logger.log(Level.SEVERE,
                                "Error loading @Configurable field " + field.getName() + " in class " +
                                clazz.getName() + ".");
                        e.printStackTrace();
                    }
                }
            }
            if (!accessible) {
                field.setAccessible(false);
            }
        }
    }


    private static interface IFieldLoader {
        void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                throws IllegalAccessException;
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
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setInt(obj, config.get(section, field.getName(), field.getInt(obj), anno.comment()).getInt());
            }
        });
        loaderMap.put(int[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(obj, prop.getIntList());
            }
        });
        loaderMap.put(String.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, config.get(section, field.getName(), (String) field.get(obj),
                        anno.comment()).getString());
            }
        });
        loaderMap.put(String[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(obj, prop.getStringList());
            }
        });
        loaderMap.put(boolean.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setBoolean(obj, config.get(section, field.getName(), field.getBoolean(obj),
                        anno.comment()).getBoolean(field.getBoolean(obj)));
            }
        });
        loaderMap.put(boolean[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(obj, prop.getBooleanList());
            }
        });
        loaderMap.put(double.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setDouble(obj, config.get(section, field.getName(), field.getDouble(obj),
                        anno.comment()).getDouble(field.getDouble(obj)));
            }
        });
        loaderMap.put(double[].class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(obj, prop.getDoubleList());
            }
        });
        loaderMap.put(float.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setFloat(obj, (float) config.get(section, field.getName(), (double) field.getFloat(obj),
                        anno.comment()).getDouble((double) field.getFloat(obj)));
            }
        });
        loaderMap.put(EnumTechLevel.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, EnumTechLevel.getTech(config.get(section, field.getName(),
                        EnumTechLevel.valueOf((String) field.get(obj)).key,
                        anno.comment()).getString()));
            }
        });
        loaderMap.put(ArrayList.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                Property prop = config.getCategory(section).get(field.getName());
                prop.comment = anno.comment();
                field.set(obj, new ArrayList<String>(Arrays.asList(prop.getStringList())));
            }
        });
        loaderMap.put(ItemStack.class, new IFieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, itemStackFromString(config.get(section, field.getName(),
                        itemStackToString((ItemStack) field.get(obj)),
                        anno.comment()).getString()));
            }
        });
    }


    public static ItemStack itemStackFromString(String s) {
        s = s.trim();
        if (s.charAt(0) == '(') {
            s = s.substring(1);
        }
        if (s.charAt(s.length() - 1) == ')') {
            s = s.substring(0, s.length() - 2);
        }
        try {
            String[] idDamage_stack = s.split(",");
            String[] id_damage = idDamage_stack[0].split(":");
            int id = Integer.parseInt(id_damage[0]);
            int damage = Integer.parseInt(id_damage[1]);
            int stackSize = Integer.parseInt(idDamage_stack[1]);
            return new ItemStack(id, stackSize, damage);
        } catch (Exception e) {
            return null;
        }
    }

    public static String itemStackToString(ItemStack s) {
        return "(" + s.itemID + ":" + s.getItemDamage() + ", " + s.stackSize + ')';
    }
}
