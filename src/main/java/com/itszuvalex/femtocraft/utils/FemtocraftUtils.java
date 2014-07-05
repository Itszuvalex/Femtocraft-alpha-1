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

package com.itszuvalex.femtocraft.utils;

import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Arrays;

public class FemtocraftUtils {

    public static int indexOfForgeDirection(ForgeDirection dir) {
//        return Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(dir);
        // switch(dir)
        return dir.ordinal();
        // {
        // case UP:
        // return 1;
        // case DOWN:
        // return 0;
        // case NORTH:
        // return 2;
        // case SOUTH:
        // return 3;
        // case EAST:
        // return 5;
        // case WEST:
        // return 4;
        // default:
        // return -1;
        // }
    }

    public static boolean isPlayerOnline(String username) {
        return Arrays.asList(MinecraftServer.getServer().getAllUsernames())
                     .contains(username);
    }

    public static EntityPlayer getPlayer(String username) {
        return Minecraft.getMinecraft().theWorld
                .getPlayerEntityByName(username);
    }

    public static int compareItem(ItemStack cur, ItemStack in) {
        if (cur == null && in != null) {
            return -1;
        }
        if (cur != null && in == null) {
            return 1;
        }
        if (cur == null && in == null) {
            return 0;
        }

        if (cur.itemID < in.itemID) {
            return -1;
        }
        if (cur.itemID > in.itemID) {
            return 1;
        }

        int damage = cur.getItemDamage();
        int indamage = in.getItemDamage();
        if ((damage != OreDictionary.WILDCARD_VALUE)
                || (indamage != OreDictionary.WILDCARD_VALUE)) {
            if (damage < indamage) {
                return -1;
            }
            if (damage > indamage) {
                return 1;
            }
        }

        return 0;
    }

    public static boolean placeItem(ItemStack item, ItemStack[] slots,
                                    int[] restrictions) {
        if (item == null) {
            return true;
        }
        // Attempt to combine, first
        int amount = item.stackSize;
        if (restrictions != null) {
            Arrays.sort(restrictions);
        }

        for (int i = 0; i < slots.length; ++i) {
            if (restrictions != null && Arrays.binarySearch(restrictions,
                    i) >= 0) {
                continue;
            }

            if (slots[i] != null && slots[i].isItemEqual(item)) {
                ItemStack slot = slots[i];
                int room = (slot.getMaxStackSize() - slot.stackSize);
                if (room < amount) {
                    slot.stackSize += room;
                    amount -= room;
                }
                else {
                    slot.stackSize += amount;
                    return true;
                }
            }
        }

        // Place into null locations
        for (int i = 0; i < slots.length; ++i) {
            if (restrictions != null && Arrays.binarySearch(restrictions,
                    i) >= 0) {
                continue;
            }

            if (slots[i] == null) {
                slots[i] = item.copy();
                slots[i].stackSize = amount;
                return true;
            }
        }

        return false;
    }

    public static boolean removeItem(ItemStack item, ItemStack[] slots,
                                     int[] restrictions) {
        if (item == null) {
            return true;
        }
        // Attempt to combine, first
        int amountLeftToRemove = item.stackSize;
        if (restrictions != null) {
            Arrays.sort(restrictions);
        }

        for (int i = 0; i < slots.length; ++i) {
            if (restrictions != null && Arrays.binarySearch(restrictions,
                    i) >= 0) {
                continue;
            }

            if (slots[i] != null && slots[i].isItemEqual(item)) {
                ItemStack slot = slots[i];
                int amount = slot.stackSize;
                if (amount <= amountLeftToRemove) {
                    slots[i] = null;
                    amountLeftToRemove -= amount;
                    if (amountLeftToRemove <= 0) {
                        return true;
                    }
                }
                else {
                    slot.stackSize -= amountLeftToRemove;
                    return true;
                }
            }
        }
        return false;
    }

    public static String capitalize(String input) {
        return input.substring(0, 1).toUpperCase() + input.substring(1);
    }

    public static int blueColor() {
        return colorFromARGB(0, 0, 0, 255);
    }

    public static int colorFromARGB(int a, int r, int g, int b) {
        int r1 = 0;
        r1 += (a & 255) << 24;
        r1 += (r & 255) << 16;
        r1 += (g & 255) << 8;
        r1 += b & 255;
        return r1;
    }

    public static int greenColor() {
        return colorFromARGB(0, 0, 255, 0);
    }

    public static int orangeColor() {
        return colorFromARGB(0, 255, 140, 0);
    }

    public static String blueify(String name) {
        return EnumTechLevel.MICRO.getTooltipEnum() + name
                + EnumChatFormatting.RESET;
    }

    public static String greenify(String name) {
        return EnumTechLevel.NANO.getTooltipEnum() + name
                + EnumChatFormatting.RESET;
    }

    public static String orangeify(String name) {
        return EnumTechLevel.FEMTO.getTooltipEnum() + name
                + EnumChatFormatting.RESET;
    }

    public static String formatIntegerToString(int i) {
        return formatIntegerString(String.valueOf(i));
    }


    public static String formatIntegerString(String number) {
        return formatIntegerString_recursive(number);
    }

    private static String formatIntegerString_recursive(String number) {
        if (number.length() <= 3) {
            return number;
        }
        else {
            return formatIntegerString_recursive(number.substring(0,
                    number.length() - 3)) + "," + number.substring
                    (number.length() - 3);
        }
    }
}
