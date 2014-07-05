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

package com.itszuvalex.femtocraft.industry.containers;

import com.itszuvalex.femtocraft.common.gui.OutputSlot;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroDeconstructor;
import com.itszuvalex.femtocraft.managers.ManagerRecipe;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerMicroDeconstructor extends Container {
    private TileEntityBaseEntityMicroDeconstructor deconstructor;
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerMicroDeconstructor(InventoryPlayer par1InventoryPlayer,
                                       TileEntityBaseEntityMicroDeconstructor deconstructor) {
        this.deconstructor = deconstructor;
        this.addSlotToContainer(new Slot(deconstructor, 0, 38, 36));
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new OutputSlot(deconstructor, x + y * 3
                        + 1, 88 + x * 18, 18 + y * 18));
            }
        }

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
        par1ICrafting.sendProgressBarUpdate(this, 0,
                this.deconstructor.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1,
                this.deconstructor.getCurrentPower());
        par1ICrafting.sendProgressBarUpdate(this, 2,
                this.deconstructor.getMassAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.deconstructor.cookTime) {
                icrafting.sendProgressBarUpdate(this, 0,
                        this.deconstructor.cookTime);
            }
            if (this.lastPower != this.deconstructor.getCurrentPower()) {
                icrafting.sendProgressBarUpdate(this, 1,
                        this.deconstructor.getCurrentPower());
            }
            if (this.lastMass != this.deconstructor.getMassAmount()) {
                icrafting.sendProgressBarUpdate(this, 2,
                        this.deconstructor.getMassAmount());
            }
        }

        this.lastCookTime = this.deconstructor.cookTime;
        this.lastPower = this.deconstructor.getCurrentPower();
        this.lastMass = this.deconstructor.getMassAmount();
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

            if (par2 > 0 && par2 < 10) {
                if (!this.mergeItemStack(itemstack1, 10, 46, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 != 0) {
                if (ManagerRecipe.assemblyRecipes
                        .canCraft(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
                        return null;
                    }
                }
                else if (par2 >= 10 && par2 < 37) {
                    if (!this.mergeItemStack(itemstack1, 37, 46, false)) {
                        return null;
                    }
                }
                else if (par2 >= 37 && par2 < 46
                        && !this.mergeItemStack(itemstack1, 10, 37, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 10, 46, false)) {
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
            case 0:
                this.deconstructor.cookTime = par2;
                break;
            case 1:
                this.deconstructor.currentPower = par2;
                break;
            case 2:
                if (par2 > 0) {
                    this.deconstructor.setFluidAmount(par2);
                }
                else {
                    this.deconstructor.clearFluid();
                }
                break;
            default:
        }
        // if (par1 == 0)
        // {
        // this.deconstructor.cookTime = par2;
        // }
        // if(par1 == 1)
        // {
        // this.deconstructor.currentPower = par2;
        // }
    }

    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.deconstructor.canPlayerUse(par1EntityPlayer);
    }
}
