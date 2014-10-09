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

import com.itszuvalex.femtocraft.api.industry.IAssemblerSchematic;
import com.itszuvalex.femtocraft.common.gui.DisplaySlot;
import com.itszuvalex.femtocraft.common.gui.OutputSlot;
import com.itszuvalex.femtocraft.core.container.ContainerInv;
import com.itszuvalex.femtocraft.industry.items.ItemAssemblySchematic;
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerEncoder extends ContainerInv<TileEntityEncoder> {
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerEncoder(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                            TileEntityEncoder par2Encoder) {
        super(player, par2Encoder, 10, 11);

        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new Slot(inventory, x + 3 * y,
                        31 + 18 * x, 16 + 18 * y));
            }
        }

        DisplaySlot recipeOutput = new DisplaySlot(inventory, 9, 87, 34);
        recipeOutput.setBackgroundIcon(DisplaySlot.noPlaceDisplayIcon);
        this.addSlotToContainer(recipeOutput);

        Slot schematicInput = new Slot(inventory, 10, 120, 8);
        schematicInput.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon);
        this.addSlotToContainer(schematicInput);

        this.addSlotToContainer(new OutputSlot(inventory, 11, 120, 50));


        addPlayerInventorySlots(par1InventoryPlayer);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        sendUpdateToCrafter(this, par1ICrafting, 0, inventory.timeWorked);
        sendUpdateToCrafter(this, par1ICrafting, 1, inventory.getCurrentPower());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != inventory.timeWorked) {
                sendUpdateToCrafter(this, icrafting, 0, inventory.timeWorked);
            }
            if (this.lastPower != inventory.getCurrentPower()) {
                sendUpdateToCrafter(this, icrafting, 1, inventory.getCurrentPower());
            }
            if (this.lastMass != inventory.getMassAmount()) {
                sendUpdateToCrafter(this, icrafting, 2, inventory.getMassAmount());
            }
        }

        this.lastCookTime = inventory.timeWorked;
        this.lastPower = inventory.getCurrentPower();
        this.lastMass = inventory.getMassAmount();
    }

    @Override
    protected boolean eligibleForInput(ItemStack item) {
        return item.getItem() instanceof IAssemblerSchematic;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        if (par1 == 0) {
            inventory.timeWorked = par2;
        }
        if (par1 == 1) {
            inventory.setCurrentStorage(par2);
        }
        if (par1 == 2) {
            if (par2 > 0) {
                inventory.setFluidAmount(par2);
            } else {
                inventory.clearFluid();
            }
        }
    }
}
