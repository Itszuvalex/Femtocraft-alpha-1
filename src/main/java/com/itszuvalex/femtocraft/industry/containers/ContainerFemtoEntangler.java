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

package com.itszuvalex.femtocraft.industry.containers;

import com.itszuvalex.femtocraft.common.gui.OutputSlot;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityFemtoEntangler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerFemtoEntangler extends Container {
    private TileEntityFemtoEntangler entangler;
    private int lastCookTime = 0;
    private int lastCookMax = 0;
    private int lastPower = 0;

    private static final int cookTimeID = 0;
    private static final int cookTimeMaxID = 1;
    private static final int powerID = 2;

    public ContainerFemtoEntangler(InventoryPlayer par1InventoryPlayer,
                                   TileEntityFemtoEntangler par2TileEntityFurnace) {
        this.entangler = par2TileEntityFurnace;
        this.addSlotToContainer(new Slot(entangler, 0, 89, 35));
        this.addSlotToContainer(new Slot(entangler, 1, 62, 8));
        this.addSlotToContainer(new Slot(entangler, 2, 80, 8));
        this.addSlotToContainer(new Slot(entangler, 3, 98, 8));
        this.addSlotToContainer(new Slot(entangler, 4, 116, 8));
        this.addSlotToContainer(new Slot(entangler, 5, 62, 26));
        this.addSlotToContainer(new Slot(entangler, 6, 116, 26));
        this.addSlotToContainer(new Slot(entangler, 7, 62, 44));
        this.addSlotToContainer(new Slot(entangler, 8, 116, 44));
        this.addSlotToContainer(new Slot(entangler, 9, 62, 62));
        this.addSlotToContainer(new Slot(entangler, 10, 80, 62));
        this.addSlotToContainer(new Slot(entangler, 11, 98, 62));
        this.addSlotToContainer(new Slot(entangler, 12, 116, 62));
        this.addSlotToContainer(new OutputSlot(entangler, 13, 147, 35));
        int i;

        // Bind player inventory
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9
                        + 9, 8 + j * 18, 84 + i * 18));
            }
        }
        // Bind player actionbar
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i,
                    8 + i * 18, 142));
        }
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, cookTimeID,
                this.entangler.getProgress());
        par1ICrafting.sendProgressBarUpdate(this, cookTimeMaxID, this.entangler.getProgressMax());
        par1ICrafting.sendProgressBarUpdate(this, powerID,
                this.entangler.getCurrentPower());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.entangler.getProgress()) {
                icrafting.sendProgressBarUpdate(this, cookTimeID,
                        this.entangler.getProgress());
            }
            if (this.lastCookMax != this.entangler.getProgressMax()) {
                icrafting.sendProgressBarUpdate(this, cookTimeMaxID, this.entangler.getProgressMax());
            }
            if (this.lastPower != this.entangler.getCurrentPower()) {
                icrafting.sendProgressBarUpdate(this, powerID,
                        this.entangler.getCurrentPower());
            }
        }

        this.lastCookTime = this.entangler.getProgress();
        this.lastPower = this.entangler.getCurrentPower();
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 == 1) {
                if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 != 0) {
                if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                }
                else if (par2 >= 2 && par2 < 29) {
                    if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
                        return null;
                    }
                }
                else if (par2 >= 29 && par2 < 38
                        && !this.mergeItemStack(itemstack1, 2, 29, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
                return null;
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            }
            else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case cookTimeID:
                this.entangler.setProgress(par2);
                break;
            case cookTimeMaxID:
                this.entangler.setProgressMax(par2);
                break;
            case powerID:
                this.entangler.setCurrentStorage(par2);
                break;
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.entangler.canPlayerUse(par1EntityPlayer);
    }
}

