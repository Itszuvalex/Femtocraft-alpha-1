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

package com.itszuvalex.femtocraft.utils;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.FluidTank;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;

public class FemtocraftDataUtils {
    private static HashMap<Class<?>, SaveManager> managerMap = new HashMap<Class<?>, SaveManager>();
    private static SaveManager iSaveableSaveManager = new SaveManager() {
        @Override
        public void saveToNBT(NBTTagCompound compound, Field saveable,
                              Object obj) throws IllegalArgumentException,
                                                 IllegalAccessException {
            ISaveable sobj = (ISaveable) saveable.get(obj);
            if (sobj == null) {
                return;
            }
            NBTTagCompound info = new NBTTagCompound();
            sobj.saveToNBT(info);
            compound.setCompoundTag(saveable.getName(), info);
        }

        @Override
        public void readFromNBT(NBTTagCompound compound, Field saveable,
                                Object obj) throws IllegalArgumentException,
                                                   IllegalAccessException {
            if (!compound.hasKey(saveable.getName())) {
                saveable.set(obj, null);
                return;
            }

            NBTTagCompound container = compound.getCompoundTag(saveable
                    .getName());
            ISaveable info = (ISaveable) saveable.get(obj);
            info.loadFromNBT(container);
//			saveable.set(obj, info);
        }
    };

    public static void registerSaveManager(Class<?> saveClass,
                                           SaveManager saveManager) {
        managerMap.put(saveClass, saveManager);
    }

    static {
        // int
        registerSaveManager(int.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setInteger(saveable.getName(), saveable.getInt(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setInt(obj, compound.getInteger(saveable.getName()));
            }
        });

        // int[]
        registerSaveManager(int[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setIntArray(saveable.getName(),
                        (int[]) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.set(obj, compound.getIntArray(saveable.getName()));
            }
        });

        // boolean
        registerSaveManager(boolean.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setBoolean(saveable.getName(),
                        saveable.getBoolean(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setBoolean(obj,
                        compound.getBoolean(saveable.getName()));
            }
        });

        // boolean[]
        registerSaveManager(boolean[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                boolean[] barr = (boolean[]) saveable.get(obj);
                array.setInteger("size", barr.length);
                int post = 0;
                for (boolean bool : barr) {
                    array.setBoolean(saveable.getName() + post, bool);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                boolean[] ret = new boolean[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getBoolean(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // float
        registerSaveManager(float.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setFloat(saveable.getName(), saveable.getFloat(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setFloat(obj, compound.getFloat(saveable.getName()));
            }
        });

        // float[]
        registerSaveManager(float[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                float[] farr = (float[]) saveable.get(obj);
                array.setInteger("size", farr.length);
                int post = 0;
                for (float floa : farr) {
                    array.setFloat(saveable.getName() + post, floa);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                float[] ret = new float[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getFloat(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // double
        registerSaveManager(double.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setDouble(saveable.getName(), saveable.getDouble(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setDouble(obj, compound.getDouble(saveable.getName()));
            }
        });

        // double[]
        registerSaveManager(double[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                double[] darr = (double[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (double doub : darr) {
                    array.setDouble(saveable.getName() + post, doub);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                double[] ret = new double[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getDouble(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // short
        registerSaveManager(short.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setShort(saveable.getName(), saveable.getShort(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setShort(obj, compound.getShort(saveable.getName()));
            }
        });

        // short[]
        registerSaveManager(short[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                short[] darr = (short[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (short str : darr) {
                    array.setShort(saveable.getName() + post, str);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                short[] ret = new short[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getShort(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // long
        registerSaveManager(long.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setLong(saveable.getName(), saveable.getLong(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setLong(obj, compound.getLong(saveable.getName()));
            }
        });

        // long[]
        registerSaveManager(long[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                long[] darr = (long[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (long str : darr) {
                    array.setLong(saveable.getName() + post, str);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                long[] ret = new long[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getLong(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // byte
        registerSaveManager(byte.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setByte(saveable.getName(), saveable.getByte(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.setByte(obj, compound.getByte(saveable.getName()));
            }
        });

        // byte[]
        registerSaveManager(byte[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setByteArray(saveable.getName(),
                        (byte[]) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.set(obj, compound.getByteArray(saveable.getName()));
            }
        });

        // String
        registerSaveManager(String.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                String str = (String) saveable.get(obj);
                if (str == null) {
                    return;
                }
                compound.setString(saveable.getName(), str);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                if (!compound.hasKey(saveable.getName())) {
                    saveable.set(obj, null);
                    return;
                }
                saveable.set(obj, compound.getString(saveable.getName()));
            }
        });

        // String[]
        registerSaveManager(String[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                String[] darr = (String[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (String str : darr) {
                    if (str == null) {
                        continue;
                    }
                    array.setString(saveable.getName() + post, str);
                }
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                String[] ret = new String[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    if (!array.hasKey(saveable.getName() + i)) {
                        ret[i] = null;
                        continue;
                    }
                    ret[i] = array.getString(saveable.getName() + i);
                }
                saveable.set(obj, ret);
            }
        });

        // NBTTagCompound
        registerSaveManager(NBTTagCompound.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setCompoundTag(saveable.getName(),
                        (NBTTagCompound) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.set(obj, compound.getCompoundTag(saveable.getName()));
            }
        });

        // NBTTagList
        registerSaveManager(NBTTagList.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                compound.setTag(saveable.getName(),
                        (NBTTagList) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                saveable.set(obj, compound.getTagList(saveable.getName()));
            }
        });

        // ItemStack
        registerSaveManager(ItemStack.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound item = new NBTTagCompound();
                ItemStack stack = (ItemStack) saveable.get(obj);
                if (stack == null) {
                    return;
                }
                stack.writeToNBT(item);
                compound.setCompoundTag(saveable.getName(), item);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound item = compound.getCompoundTag(saveable
                        .getName());
                saveable.set(obj, ItemStack.loadItemStackFromNBT(item));
            }
        });

        // ItemStack[]
        registerSaveManager(ItemStack[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound array = new NBTTagCompound();
                NBTTagList list = new NBTTagList();
                ItemStack[] isarr = (ItemStack[]) saveable.get(obj);

                if (isarr == null) {
                    return;
                }
                array.setInteger("size", isarr.length);

                for (int i = 0; i < isarr.length; ++i) {
                    ItemStack stack = isarr[i];
                    if (stack == null) {
                        continue;
                    }

                    NBTTagCompound scomp = new NBTTagCompound();
                    scomp.setInteger("index", i);
                    stack.writeToNBT(scomp);
                    list.appendTag(scomp);
                }
                array.setTag("items", list);
                compound.setCompoundTag(saveable.getName(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                if (!compound.hasKey(saveable.getName())) {
                    saveable.set(obj, null);
                    return;
                }

                NBTTagCompound array = compound.getCompoundTag(saveable
                        .getName());
                NBTTagList list = array.getTagList("items");
                ItemStack[] retarray = new ItemStack[array.getInteger("size")];
                Arrays.fill(retarray, null);
                for (int i = 0; i < list.tagCount(); ++i) {
                    NBTTagCompound item = (NBTTagCompound) list.tagAt(i);
                    retarray[item.getInteger("index")] = ItemStack
                            .loadItemStackFromNBT(item);
                }
                saveable.set(obj, retarray);
            }
        });

        // // PowerContainer
        // registerSaveManager(PowerContainer.class, new SaveManager() {
        //
        // @Override
        // public void saveToNBT(NBTTagCompound compound, Field saveable,
        // Object obj) throws IllegalArgumentException,
        // IllegalAccessException {
        // PowerContainer pow = (PowerContainer) saveable.get(obj);
        // if (pow == null)
        // return;
        // NBTTagCompound container = new NBTTagCompound();
        // pow.saveToNBT(container);
        // compound.setCompoundTag(saveable.getName(), container);
        // }
        //
        // @Override
        // public void readFromNBT(NBTTagCompound compound, Field saveable,
        // Object obj) throws IllegalArgumentException,
        // IllegalAccessException {
        // if (!compound.hasKey(saveable.getName())) {
        // saveable.set(obj, null);
        // return;
        // }
        // saveable.set(obj, PowerContainer.createFromNBT(compound
        // .getCompoundTag(saveable.getName())));
        // }
        // });

        // ForgeDirection
        registerSaveManager(ForgeDirection.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {


                NBTTagCompound container = new NBTTagCompound();
                ForgeDirection direction = (ForgeDirection) saveable.get(obj);
                if (direction == null) {
                    return;
                }
                container.setInteger(saveable.getName(), direction.ordinal());
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                if (!compound.hasKey(saveable.getName())) {
                    saveable.set(obj, null);
                    return;
                }
                ForgeDirection direction = ForgeDirection.getOrientation
                        (compound.getInteger(saveable.getName()));
                saveable.set(obj, direction);
            }
        });

        // FluidTank
        registerSaveManager(FluidTank.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                NBTTagCompound container = new NBTTagCompound();
                FluidTank tank = (FluidTank) saveable.get(obj);
                container.setInteger("capacity", tank.getCapacity());
                tank.writeToNBT(container);
                compound.setCompoundTag(saveable.getName(), container);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                NBTTagCompound container = compound.getCompoundTag(saveable
                        .getName());
                FluidTank tank = new FluidTank(container.getInteger("capacity"));
                saveable.set(obj, tank.readFromNBT(container));
            }
        });
        //
        // // MultiBlockInfo
        // registerSaveManager(MultiBlockInfo.class, new SaveManager() {
        //
        // @Override
        // public void saveToNBT(NBTTagCompound compound, Field saveable,
        // Object obj) throws IllegalArgumentException,
        // IllegalAccessException {
        // NBTTagCompound info = new NBTTagCompound();
        // MultiBlockInfo mbi = (MultiBlockInfo) saveable.get(obj);
        // mbi.saveToNBT(info);
        // compound.setCompoundTag(saveable.getName(), info);
        // }
        //
        // @Override
        // public void readFromNBT(NBTTagCompound compound, Field saveable,
        // Object obj) throws IllegalArgumentException,
        // IllegalAccessException {
        // NBTTagCompound container = compound.getCompoundTag(saveable
        // .getName());
        // MultiBlockInfo info = new MultiBlockInfo();
        // info.loadFromNBT(container);
        // saveable.set(obj, info);
        // }
        // });

    }

    public static void saveObjectToNBT(NBTTagCompound compound, Object obj,
                                       EnumSaveType type) {
        ArrayList<Field> fields = new ArrayList<Field>();

        Class objclass = obj.getClass();

        while (objclass != null) {
            fields.addAll(Arrays.asList(objclass.getDeclaredFields()));
            objclass = objclass.getSuperclass();
        }

        for (Field field : fields) {
            boolean flag = field.isAccessible();
            if (!flag) {
                field.setAccessible(true);
            }
            saveToNBT(compound, field, obj, type);
            if (!flag) {
                field.setAccessible(false);
            }
        }
    }

    private static void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj, EnumSaveType type) {
        Saveable asave = saveable.getAnnotation(Saveable.class);
        if (asave == null) {
            return;
        }

        switch (type) {
            case WORLD:
                if (!asave.world()) {
                    return;
                }
                break;
            case DESCRIPTION:
                if (!asave.desc()) {
                    return;
                }
                break;
            case ITEM:
                if (!asave.item()) {
                    return;
                }
                break;
        }

        Class<?> clazz = saveable.getType();

        try {
            SaveManager man = managerMap.get(clazz);
            if (man != null) {
                man.saveToNBT(compound, saveable, obj);
            }
            else if (ISaveable.class.isAssignableFrom(clazz)) {
                iSaveableSaveManager.saveToNBT(compound, saveable, obj);
            }
            else {
                Femtocraft.logger
                        .log(Level.SEVERE,
                                "Field "
                                        + saveable.getName()
                                        + " in Class "
                                        + saveable.getDeclaringClass()
                                                  .getName()
                                        + " marked as Saveable is of unsupported class - "
                                        + clazz.getName() + "."
                        );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void loadObjectFromNBT(NBTTagCompound compound, Object obj,
                                         EnumSaveType type) {
        ArrayList<Field> fields = new ArrayList<Field>();

        Class objclass = obj.getClass();
        while (objclass != null) {
            fields.addAll(Arrays.asList(objclass.getDeclaredFields()));
            objclass = objclass.getSuperclass();
        }

        for (Field field : fields) {
            boolean flag = field.isAccessible();
            if (!flag) {
                field.setAccessible(true);
            }
            loadFromNBT(compound, field, obj, type);
            if (!flag) {
                field.setAccessible(false);
            }
        }
    }

    private static void loadFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj, EnumSaveType type) {
        Saveable asave = saveable.getAnnotation(Saveable.class);
        if (asave == null) {
            return;
        }

        switch (type) {
            case WORLD:
                if (!asave.world()) {
                    return;
                }
                break;
            case DESCRIPTION:
                if (!asave.desc()) {
                    return;
                }
                break;
            case ITEM:
                if (!asave.item()) {
                    return;
                }
                break;
        }

        Class<?> clazz = saveable.getType();

        try {
            SaveManager man = managerMap.get(clazz);
            if (man != null) {
                man.readFromNBT(compound, saveable, obj);
            }
            else if (ISaveable.class.isAssignableFrom(clazz)) {
                Object os = saveable.get(obj);
                if (os == null) {
                    Femtocraft.logger
                            .log(Level.SEVERE,
                                    "Field "
                                            + saveable.getName()
                                            + " in Class "
                                            + saveable.getDeclaringClass()
                                                      .getName()
                                            + " must not be null to be loaded from NBT."
                            );
                }
                else {
                    iSaveableSaveManager.readFromNBT(compound, saveable, obj);
                }
            }
            else {
                Femtocraft.logger
                        .log(Level.SEVERE,
                                "Field "
                                        + saveable.getName()
                                        + " in Class "
                                        + saveable.getDeclaringClass()
                                                  .getName()
                                        + " marked as Saveable is of unsupported class - "
                                        + clazz.getName() + "."
                        );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static enum EnumSaveType {
        WORLD, DESCRIPTION, ITEM
    }

    /**
     * @param world True by default. Field will be saved/loaded when the world
     *              saves/loads.
     * @param desc  False by default. If true, Field will be sent to client when
     *              description packets are sent, and loaded on client.
     * @param item  False by default. If true, Field will be saved to dropped
     *              ItemStack 's NBTTagCompound.
     * @author Itszuvalex
     */
    @Retention(RetentionPolicy.RUNTIME)
    public static @interface Saveable {
        public boolean world() default true;

        public boolean desc() default false;

        public boolean item() default false;
    }

    public static class SaveManager {
        public void saveToNBT(NBTTagCompound compound, Field saveable,
                              Object obj) throws IllegalArgumentException,
                                                 IllegalAccessException {
        }

        public void readFromNBT(NBTTagCompound compound, Field saveable,
                                Object obj) throws IllegalArgumentException,
                                                   IllegalAccessException {
        }

    }
}
