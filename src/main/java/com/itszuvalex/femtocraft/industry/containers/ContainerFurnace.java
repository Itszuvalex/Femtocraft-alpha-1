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

import com.itszuvalex.femtocraft.core.container.ContainerInv;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroFurnace;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */

public class ContainerFurnace<T extends TileEntityBaseEntityMicroFurnace> extends ContainerInv<T> {
    private int lastCookTime = 0;
    private int lastPower = 0;

    public ContainerFurnace(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                            T par2TileEntityFurnace) {
        super(player, par2TileEntityFurnace, 0, 1);
        this.addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 35));
        this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
                par2TileEntityFurnace, 1, 116, 35));
        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        sendUpdateToCrafter(this, par1ICrafting, 0, inventory().furnaceCookTime());
        sendUpdateToCrafter(this, par1ICrafting, 1, inventory().getCurrentPower());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.inventory().furnaceCookTime()) {
                sendUpdateToCrafter(this, icrafting, 0, inventory().furnaceCookTime());
            }
            if (this.lastPower != this.inventory().getCurrentPower()) {
                sendUpdateToCrafter(this, icrafting, 1, inventory().getCurrentPower());
            }
        }

        this.lastCookTime = this.inventory().furnaceCookTime();
        this.lastPower = this.inventory().getCurrentPower();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            this.inventory().furnaceCookTime_$eq(par2);
        }
        if (par1 == 1) {
            this.inventory().currentPower_$eq(par2);
        }
    }

    @Override
    public boolean eligibleForInput(ItemStack item) {
        return FurnaceRecipes.smelting().getSmeltingResult(item) != null;
    }
}
