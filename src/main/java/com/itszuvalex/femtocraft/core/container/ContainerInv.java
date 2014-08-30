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

package com.itszuvalex.femtocraft.core.container;

import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */
public abstract class ContainerInv<T extends TileEntityBase> extends ContainerBase {
    protected T inventory;

    protected final EntityPlayer player;

    protected final int INPUT_SLOT;
    protected final int OUTPUT_SLOT;
    protected final int INV_SIZE;

    protected final int INV_START, INV_END, HOTBAR_START, HOTBAR_END;


    public ContainerInv(EntityPlayer parPlayer, T inventory, int input, int output) {
        this.inventory = inventory;
        this.player = parPlayer;
        INPUT_SLOT = input;
        OUTPUT_SLOT = output;
        INV_SIZE = inventory instanceof IInventory ? ((IInventory) inventory).getSizeInventory() - 1 : OUTPUT_SLOT;
        INV_START = INV_SIZE + 1;
        INV_END = INV_START + 26;
        HOTBAR_START = INV_END + 1;
        HOTBAR_END = HOTBAR_START + 8;
    }

    protected void addPlayerInventorySlots(InventoryPlayer inventoryPlayer) {
        addPlayerInventorySlots(inventoryPlayer, 8, 84);
    }

    protected void addPlayerInventorySlots(InventoryPlayer inventoryPlayer, int inventoryXStart, int inventoryYStart) {
        int i;

// PLAYER INVENTORY SLOTS
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer,
                        j + i * 9 + 9, inventoryXStart + j * 18, inventoryYStart + i * 18));
            }
        }

// PLAYER ACTION BAR SLOTS
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, inventoryXStart + i * 18, inventoryYStart + 58));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityplayer) {
        return inventory.canPlayerUse(entityplayer);
    }

    protected abstract boolean eligibleForInput(ItemStack item);

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < INV_START) {
                if (!this.mergeItemStack(itemstack1, INV_START, HOTBAR_END + 1, false)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else {
                if (eligibleForInput(itemstack1)) {
                    if (!this.mergeItemStack(itemstack1, INPUT_SLOT, INPUT_SLOT + 1, false)) {
                        return null;
                    }
                } else if (par2 >= INV_START && par2 <= INV_END) {
                    if (!this.mergeItemStack(itemstack1, HOTBAR_START, HOTBAR_END + 1, false)) {
                        return null;
                    }
                } else if (par2 >= HOTBAR_START && par2 <= HOTBAR_END) {
                    if (!this.mergeItemStack(itemstack1, INV_START, INV_END + 1, false)) {
                        return null;
                    }
                }
            }

            if (itemstack1.stackSize == 0) {
                slot.putStack(null);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.stackSize == itemstack.stackSize) {
                return null;
            }

            slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
        }

        return itemstack;
    }
}
