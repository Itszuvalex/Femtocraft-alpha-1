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

import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.EnumSaveType;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.Arrays;

public class BaseInventory implements IInventory, ISaveable {
    @Saveable
    protected ItemStack[] inventory;

    public BaseInventory(int size) {
        inventory = new ItemStack[size];
    }

    /**
     * @return ItemStack[] that backs this inventory class. Modifications to it
     * modify this.
     */
    public ItemStack[] getInventory() {
        return inventory;
    }

    @Override
    public int getSizeInventory() {
        return inventory.length;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory[i];
    }

    @Override
    public ItemStack decrStackSize(int i, int amount) {
        if (inventory[i] != null) {
            ItemStack itemstack;

            if (inventory[i].stackSize <= amount) {
                itemstack = inventory[i];
                inventory[i] = null;
                return itemstack;
            }
            else {
                itemstack = inventory[i].splitStack(amount);

                if (inventory[i].stackSize == 0) {
                    inventory[i] = null;
                }

                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory[i];
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory[i] = itemstack;
    }

    @Override
    public String getInvName() {
        return "femto.BaseInventory.ImLazyAndDidntCodeThis";
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void onInventoryChanged() {
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return true;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return true;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                EnumSaveType.WORLD);
    }

    /**
     * Changes size of the inventory to be equal to size.  Keeps current
     * inventory from slots 0 -> (size-1), and will drop extra itemstacks.
     *
     * @param size new size of inventory
     */
    public void setInventorySize(int size) {
        inventory = Arrays.copyOfRange(inventory, 0, size);
    }

    public int getComparatorInputOverride() {
        return Container.calcRedstoneFromInventory(this);
    }
}
