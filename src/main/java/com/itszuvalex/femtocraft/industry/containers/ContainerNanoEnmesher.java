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
import com.itszuvalex.femtocraft.core.container.ContainerInv;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityNanoEnmesher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNanoEnmesher extends ContainerInv<TileEntityBaseEntityNanoEnmesher> {
    private int lastCookTime = 0;
    private int lastCookMax = 0;
    private int lastPower = 0;

    private static final int cookTimeID = 0;
    private static final int cookTimeMaxID = 1;
    private static final int powerID = 2;

    public ContainerNanoEnmesher(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                 TileEntityBaseEntityNanoEnmesher par2TileEntityFurnace) {
        super(player, par2TileEntityFurnace, 0, 5);
        this.addSlotToContainer(new Slot(inventory(), 0, 89, 35));
        this.addSlotToContainer(new Slot(inventory(), 1, 89, 8));
        this.addSlotToContainer(new Slot(inventory(), 2, 62, 35));
        this.addSlotToContainer(new Slot(inventory(), 3, 116, 35));
        this.addSlotToContainer(new Slot(inventory(), 4, 89, 62));
        this.addSlotToContainer(new OutputSlot(inventory(), 5, 147, 35));
        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        sendUpdateToCrafter(this, par1ICrafting, cookTimeID, inventory().getProgress());
        sendUpdateToCrafter(this, par1ICrafting, cookTimeMaxID, inventory().getProgressMax());
        sendUpdateToCrafter(this, par1ICrafting, powerID, inventory().getCurrentPower());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.inventory().getProgress()) {
                sendUpdateToCrafter(this, icrafting, cookTimeID, inventory().getProgress());
            }
            if (this.lastCookMax != this.inventory().getProgressMax()) {
                sendUpdateToCrafter(this, icrafting, cookTimeMaxID, inventory().getProgressMax());
            }
            if (this.lastPower != this.inventory().getCurrentPower()) {
                sendUpdateToCrafter(this, icrafting, powerID, inventory().getCurrentPower());
            }
        }

        this.lastCookTime = this.inventory().getProgress();
        this.lastCookMax = inventory().getProgressMax();
        this.lastPower = this.inventory().getCurrentPower();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case cookTimeID:
                this.inventory().setProgress(par2);
                break;
            case cookTimeMaxID:
                this.inventory().setProgressMax(par2);
                break;
            case powerID:
                this.inventory().setCurrentStorage(par2);
                break;
        }
    }

    @Override
    public boolean eligibleForInput(ItemStack item) {
        return true;
    }
}
