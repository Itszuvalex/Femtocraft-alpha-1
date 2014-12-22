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
import com.itszuvalex.femtocraft.api.core.ISaveable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import org.apache.logging.log4j.Level;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

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
            compound.setTag(saveable.getName(), info);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setInteger(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getInt(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setInt(obj, compound.getInteger(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // int[]
        registerSaveManager(int[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setIntArray(anno.tag().isEmpty() ? saveable.getName() : anno.tag(),
                        (int[]) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.set(obj, compound.getIntArray(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // boolean
        registerSaveManager(boolean.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setBoolean(anno.tag().isEmpty() ? saveable.getName() : anno.tag(),
                        saveable.getBoolean(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setBoolean(obj,
                        compound.getBoolean(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // boolean[]
        registerSaveManager(boolean[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                boolean[] barr = (boolean[]) saveable.get(obj);
                array.setInteger("size", barr.length);
                int post = 0;
                for (boolean bool : barr) {
                    array.setBoolean((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, bool);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                boolean[] ret = new boolean[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getBoolean((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setFloat(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getFloat(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setFloat(obj, compound.getFloat(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // float[]
        registerSaveManager(float[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                float[] farr = (float[]) saveable.get(obj);
                array.setInteger("size", farr.length);
                int post = 0;
                for (float floa : farr) {
                    array.setFloat((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, floa);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                float[] ret = new float[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getFloat((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setDouble(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getDouble(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setDouble(obj, compound.getDouble(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // double[]
        registerSaveManager(double[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                double[] darr = (double[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (double doub : darr) {
                    array.setDouble((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, doub);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                double[] ret = new double[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getDouble((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setShort(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getShort(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setShort(obj, compound.getShort(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // short[]
        registerSaveManager(short[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                short[] darr = (short[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (short str : darr) {
                    array.setShort((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, str);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                short[] ret = new short[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getShort((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setLong(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getLong(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setLong(obj, compound.getLong(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // long[]
        registerSaveManager(long[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                long[] darr = (long[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (long str : darr) {
                    array.setLong((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, str);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                long[] ret = new long[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    ret[i] = array.getLong((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setByte(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), saveable.getByte(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.setByte(obj, compound.getByte(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // byte[]
        registerSaveManager(byte[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setByteArray(anno.tag().isEmpty() ? saveable.getName() : anno.tag(),
                        (byte[]) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.set(obj, compound.getByteArray(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // String
        registerSaveManager(String.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                String str = (String) saveable.get(obj);
                if (str == null) {
                    return;
                }
                compound.setString(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), str);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                if (!compound.hasKey(anno.tag().isEmpty() ? saveable.getName() : anno.tag())) {
                    saveable.set(obj, null);
                    return;
                }
                saveable.set(obj, compound.getString(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // String[]
        registerSaveManager(String[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = new NBTTagCompound();
                String[] darr = (String[]) saveable.get(obj);
                array.setInteger("size", darr.length);
                int post = 0;
                for (String str : darr) {
                    if (str == null) {
                        continue;
                    }
                    array.setString((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + post, str);
                }
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                String[] ret = new String[array.getInteger("size")];
                for (int i = 0; i < ret.length; ++i) {
                    if (!array.hasKey(saveable.getName() + i)) {
                        ret[i] = null;
                        continue;
                    }
                    ret[i] = array.getString((anno.tag().isEmpty() ? saveable.getName() : anno.tag()) + i);
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
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(),
                        (NBTTagCompound) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.set(obj, compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
            }
        });

        // NBTTagList
        registerSaveManager(NBTTagList.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(),
                        (NBTTagList) saveable.get(obj));
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                saveable.set(obj, compound.getTagList(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), 10));
            }
        });

        // ItemStack
        registerSaveManager(ItemStack.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound item = new NBTTagCompound();
                ItemStack stack = (ItemStack) saveable.get(obj);
                if (stack == null) {
                    return;
                }
                stack.writeToNBT(item);
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), item);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound item = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                saveable.set(obj, ItemStack.loadItemStackFromNBT(item));
            }
        });

        // ItemStack[]
        registerSaveManager(ItemStack[].class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
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
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), array);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                if (!compound.hasKey(anno.tag().isEmpty() ? saveable.getName() : anno.tag())) {
                    saveable.set(obj, null);
                    return;
                }

                NBTTagCompound array = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag());
                NBTTagList list = array.getTagList("items", 10);
                ItemStack[] retarray = new ItemStack[array.getInteger("size")];
                Arrays.fill(retarray, null);
                for (int i = 0; i < list.tagCount(); ++i) {
                    NBTTagCompound item = list.getCompoundTagAt(i);
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


                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound container = new NBTTagCompound();
                ForgeDirection direction = (ForgeDirection) saveable.get(obj);
                if (direction == null) {
                    return;
                }
                container.setInteger(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), direction.ordinal());
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                if (!compound.hasKey(anno.tag().isEmpty() ? saveable.getName() : anno.tag())) {
                    saveable.set(obj, null);
                    return;
                }
                ForgeDirection direction = ForgeDirection.getOrientation
                        (compound.getInteger(anno.tag().isEmpty() ? saveable.getName() : anno.tag()));
                saveable.set(obj, direction);
            }
        });

        // FluidTank
        registerSaveManager(FluidTank.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound container = new NBTTagCompound();
                FluidTank tank = (FluidTank) saveable.get(obj);
                container.setInteger("capacity", tank.getCapacity());
                tank.writeToNBT(container);
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), container);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound container = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno
                        .tag());
                FluidTank tank = new FluidTank(container.getInteger("capacity"));
                saveable.set(obj, tank.readFromNBT(container));
            }
        });
        // FluidStack
        registerSaveManager(FluidStack.class, new SaveManager() {

            @Override
            public void saveToNBT(NBTTagCompound compound, Field saveable,
                                  Object obj) throws IllegalArgumentException,
                                                     IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound container = new NBTTagCompound();
                FluidStack stack = (FluidStack) saveable.get(obj);
                if (stack == null) return;
                stack.writeToNBT(container);
                compound.setTag(anno.tag().isEmpty() ? saveable.getName() : anno.tag(), container);
            }

            @Override
            public void readFromNBT(NBTTagCompound compound, Field saveable,
                                    Object obj) throws IllegalArgumentException,
                                                       IllegalAccessException {
                Saveable anno = saveable.getAnnotation(Saveable.class);
                NBTTagCompound container = compound.getCompoundTag(anno.tag().isEmpty() ? saveable.getName() : anno
                        .tag());
                if (container == null) {
                    saveable.set(obj, null);
                    return;
                }
                FluidStack stack = FluidStack.loadFluidStackFromNBT(container);
                saveable.set(obj, stack);
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
            } else if (ISaveable.class.isAssignableFrom(clazz)) {
                iSaveableSaveManager.saveToNBT(compound, saveable, obj);
            } else {
                Femtocraft.log(Level.ERROR,
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
            } else if (ISaveable.class.isAssignableFrom(clazz)) {
                Object os = saveable.get(obj);
                if (os == null) {
                    Femtocraft.log(Level.ERROR,
                            "Field "
                            + saveable.getName()
                            + " in Class "
                            + saveable.getDeclaringClass()
                                    .getName()
                            + " must not be null to be loaded from NBT."
                    );
                } else {
                    iSaveableSaveManager.readFromNBT(compound, saveable, obj);
                }
            } else {
                Femtocraft.log(Level.ERROR,
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
