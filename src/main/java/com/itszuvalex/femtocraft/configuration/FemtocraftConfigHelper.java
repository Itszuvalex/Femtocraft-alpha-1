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

import java.lang.reflect.Array;
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
            fields = fieldsList.toArray(new Field[fieldsList.size()]);
        }
        for (Field field : fields) {
            boolean accessible = field.isAccessible();
            if (!field.isAccessible()) field.setAccessible(true);
            Configurable canno = field.getAnnotation(Configurable.class);
            if (canno != null) {
                FieldLoader loader = loaderMap.get(field.getType());
                if (loader != null) {
                    try {
                        loader.load(field, section + Configuration.CATEGORY_SPLITTER + key, canno, obj, configuration);
                    } catch (Exception e) {
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


    private static abstract class FieldLoader<T> {
        abstract void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                throws IllegalAccessException;

        abstract T getValue(String key, T def, String section, Configurable anno, Configuration config);
    }

    private static Map<Class, FieldLoader> loaderMap = new HashMap<Class, FieldLoader>();

    public static void init() {
        registerConfigLoaders();
        registerConfigurableClasses();
    }

    private static void registerConfigurableClasses() {
        registerConfigurableClass(ItemAssemblySchematic.class);
    }

    private static void registerConfigLoaders() {
        loaderMap.put(int.class, new FieldLoader<Integer>() {
            @Override
            void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setInt(obj, getValue(field.getName(), field.getInt(obj), section, anno, config));
            }

            @Override
            Integer getValue(String key, Integer def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, def, anno.comment()).getInt();
            }
        });
        loaderMap.put(int[].class, new FieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), field.get(obj), section, anno, config));
            }

            @Override
            Object getValue(String key, Object def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, (int[]) def,
                        anno.comment()).getIntList();
            }
        });
        loaderMap.put(String.class, new FieldLoader<String>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), (String) field.get(obj), section, anno, config));
            }

            @Override
            String getValue(String key, String def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, def,
                        anno.comment()).getString();
            }
        });
        loaderMap.put(String[].class, new FieldLoader<String[]>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), (String[]) field.get(obj), section, anno, config));
            }

            @Override
            String[] getValue(String key, String[] def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, def,
                        anno.comment()).getStringList();
            }
        });
        loaderMap.put(boolean.class, new FieldLoader<Boolean>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setBoolean(obj, getValue(field.getName(), field.getBoolean(obj), section, anno, config));
            }

            @Override
            Boolean getValue(String key, Boolean def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, def,
                        anno.comment()).getBoolean(def);
            }
        });
        loaderMap.put(boolean[].class, new FieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), field.get(obj), section, anno, config));
            }

            @Override
            Object getValue(String key, Object def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, (boolean[]) def,
                        anno.comment()).getBooleanList();
            }
        });
        loaderMap.put(double.class, new FieldLoader<Double>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setDouble(obj, getValue(field.getName(), field.getDouble(obj), section, anno, config));
            }

            @Override
            Double getValue(String key, Double def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, def,
                        anno.comment()).getDouble(def);
            }
        });
        loaderMap.put(double[].class, new FieldLoader() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), field.get(obj), section, anno, config));
            }

            @Override
            Object getValue(String key, Object def, String section, Configurable anno, Configuration config) {
                return config.get(section, key, (double[]) def,
                        anno.comment()).getDoubleList();
            }
        });
        loaderMap.put(float.class, new FieldLoader<Float>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.setFloat(obj, getValue(field.getName(), field.getFloat(obj), section, anno, config));
            }

            @Override
            Float getValue(String key, Float def, String section, Configurable anno, Configuration config) {
                return (float) config.get(section, key, def,
                        anno.comment()).getDouble(def);
            }
        });
        loaderMap.put(EnumTechLevel.class, new FieldLoader<EnumTechLevel>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), (EnumTechLevel) field.get(obj), section, anno, config));
            }

            @Override
            EnumTechLevel getValue(String key, EnumTechLevel def, String section, Configurable anno, Configuration config) {
                return EnumTechLevel.getTech(config.get(section, key,
                        def.key,
                        anno.comment()).getString());
            }
        });
        loaderMap.put(ArrayList.class, new FieldLoader<ArrayList>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                ConfigArrayList carray = field.getAnnotation(ConfigArrayList.class);
                if (carray != null) {
                    FieldLoader loader = loaderMap.get(carray.arrayType());
                    if (loader != null) {
                        ArrayList arrayList = (ArrayList) field.get(obj);
                        int arrayListSize = arrayList == null ? 0 : arrayList.size();
                        Class component = carray.arrayType().getComponentType();
                        Object array = Array.newInstance(component, arrayListSize);
                        for (int i = 0; i < arrayListSize; ++i) {
                            Object ao = arrayList.get(i);
                            Object in = component.cast(ao);
                            Array.set(array, i, in);
                        }
                        Object res = loader.getValue(field.getName(), array, section, anno, config);
                        field.set(obj, res == null ? new ArrayList() : new ArrayList(Arrays.asList(res)));
                    }
                }
            }

            @Override
            ArrayList getValue(String key, ArrayList def, String section, Configurable anno, Configuration config) {
                return null;
            }
        });
        loaderMap.put(ItemStack.class, new FieldLoader<ItemStack>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config)
                    throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), (ItemStack) field.get(obj), section, anno, config));
            }

            @Override
            ItemStack getValue(String key, ItemStack def, String section, Configurable anno, Configuration config) {
                return itemStackFromString(config.get(section, key,
                        itemStackToString(def),
                        anno.comment()).getString());
            }
        });
        loaderMap.put(ItemStack[].class, new FieldLoader<ItemStack[]>() {
            @Override
            public void load(Field field, String section, Configurable anno, Object obj, Configuration config) throws IllegalAccessException {
                field.set(obj, getValue(field.getName(), (ItemStack[]) field.get(obj), section, anno, config));
            }

            @Override
            ItemStack[] getValue(String key, ItemStack[] def, String section, Configurable anno, Configuration config) {
                String[] defsar = def == null ? new String[0] : new String[def.length];
                for (int i = 0; i < def.length; ++i) {
                    defsar[i] = itemStackToString(def[i]);
                }
                String[] sar = config.get(section, key, defsar, anno.comment()).getStringList();
                ItemStack[] ret = sar == null ? new ItemStack[0] : new ItemStack[sar.length];
                for (int i = 0; i < sar.length; ++i) {
                    ret[i] = itemStackFromString(sar[i]);
                }
                return ret;
            }
        });
    }


    public static ItemStack itemStackFromString(String s) {
        if (s == null || s.isEmpty()) return null;
        s = s.trim();
        try {
            String[] idDamage_stack = s.split("-");
            String[] id_damage = idDamage_stack[0].split(":");
            int id = Integer.parseInt(id_damage[0].trim());
            int damage = Integer.parseInt(id_damage[1].trim());
            int stackSize = Integer.parseInt(idDamage_stack[1].trim());
            return new ItemStack(id, stackSize, damage);
        } catch (Exception e) {
            return null;
        }
    }

    public static String itemStackToString(ItemStack s) {
        return s == null ? "" : s.itemID + ":" + s.getItemDamage() + "-" + s.stackSize;
    }
}