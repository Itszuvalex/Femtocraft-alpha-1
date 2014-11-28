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
import com.itszuvalex.femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ContainerReconstructor<T extends TileEntityBaseEntityMicroReconstructor> extends ContainerInv<T> {
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerReconstructor(EntityPlayer player, InventoryPlayer par1InventoryPlayer,
                                  T inventory) {
        super(player, inventory, 10, 9);
        this.addSlotToContainer(new OutputSlot(inventory, 9, 122, 18));
        Slot schematic = new Slot(inventory, 10, 93, 54) {
            @Override
            public boolean isItemValid(ItemStack par1ItemStack) {
                return par1ItemStack.getItem() instanceof IAssemblerSchematic;
            }
        };
        schematic.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon);
        this.addSlotToContainer(schematic);
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new DisplaySlot(inventory(), x + y
                                                                       * 3, 32 + x * 18, 18 + y * 18) {
                    @Override
                    @SideOnly(Side.CLIENT)
                    public IIcon getBackgroundIconIndex() {
                        return this.inventory
                                       .getStackInSlot(10) != null ? null
                                : DisplaySlot.noPlaceDisplayIcon;
                    }
                });
            }
        }

        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(inventory(), 11 + x + y * 9,
                        8 + x * 18, 77 + y * 18));
            }
        }

        addPlayerInventorySlots(par1InventoryPlayer, 8, 122);
    }

    @Override
    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        sendUpdateToCrafter(this, par1ICrafting, 0, inventory().cookTime);
        sendUpdateToCrafter(this, par1ICrafting, 1, inventory().getCurrentPower());
        sendUpdateToCrafter(this, par1ICrafting, 2, inventory().getMassAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.inventory().cookTime) {
                sendUpdateToCrafter(this, icrafting, 0, inventory().cookTime);
            }
            if (this.lastPower != this.inventory().getCurrentPower()) {
                sendUpdateToCrafter(this, icrafting, 1, inventory().getCurrentPower());
            }
            if (this.lastMass != this.inventory().getMassAmount()) {
                sendUpdateToCrafter(this, icrafting, 2, inventory().getMassAmount());
            }
        }

        this.lastCookTime = this.inventory().cookTime;
        this.lastPower = this.inventory().getCurrentPower();
        this.lastMass = this.inventory().getMassAmount();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case 0:
                this.inventory().cookTime = par2;
                break;
            case 1:
                this.inventory().currentPower = par2;
                break;
            case 2:
                if (par2 > 0) {
                    this.inventory().setFluidAmount(par2);
                } else {
                    this.inventory().clearFluid();
                }
                break;
            default:
        }
    }

    @Override
    public boolean eligibleForInput(ItemStack item) {
        return item.getItem() instanceof IAssemblerSchematic;
    }
}