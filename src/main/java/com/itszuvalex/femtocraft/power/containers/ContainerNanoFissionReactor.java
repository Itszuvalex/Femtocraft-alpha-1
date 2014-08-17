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

package com.itszuvalex.femtocraft.power.containers;

import com.itszuvalex.femtocraft.core.container.ContainerInv;
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerNanoFissionReactor<T extends TileEntityNanoFissionReactorCore> extends ContainerInv<T> {
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerNanoFissionReactor(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                       T inventory) {
        super(player, inventory, 0, 2);
        this.addSlotToContainer(new Slot(inventory, 0, 8, 8));
        this.addSlotToContainer(new Slot(inventory, 1, 112, 9));
        this.addSlotToContainer(new Slot(inventory, 2, 112, 29));
        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0,
                this.inventory.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1,
                this.inventory.getCurrentPower());
        par1ICrafting.sendProgressBarUpdate(this, 2,
                this.inventory.getMassAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.inventory.cookTime) {
                icrafting.sendProgressBarUpdate(this, 0,
                        this.inventory.cookTime);
            }
            if (this.lastPower != this.inventory.getCurrentPower()) {
                icrafting.sendProgressBarUpdate(this, 1,
                        this.inventory.getCurrentPower());
            }
            if (this.lastMass != this.inventory.getMassAmount()) {
                icrafting.sendProgressBarUpdate(this, 2,
                        this.inventory.getMassAmount());
            }
        }

        this.lastCookTime = this.inventory.cookTime;
        this.lastPower = this.inventory.getCurrentPower();
        this.lastMass = this.inventory.getMassAmount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case 0:
                this.inventory.cookTime = par2;
                break;
            case 1:
                this.inventory.currentPower = par2;
                break;
            case 2:
                if (par2 > 0) {
                    this.inventory.setFluidAmount(par2);
                }
                else {
                    this.inventory.clearFluid();
                }
                break;
            default:
        }
        // if (par1 == 0)
        // {
        // this.inventory.cookTime = par2;
        // }
        // if(par1 == 1)
        // {
        // this.inventory.currentPower = par2;
        // }
    }

    @Override
    protected boolean eligibleForInput(ItemStack item) {
        return false;
    }
}


