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

package femtocraft.industry.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.api.IAssemblerSchematic;
import femtocraft.common.gui.DisplaySlot;
import femtocraft.common.gui.OutputSlot;
import femtocraft.industry.items.ItemAssemblySchematic;
import femtocraft.industry.tiles.TileEntityBaseEntityMicroReconstructor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;

public class ContainerMicroReconstructor extends Container {
    private TileEntityBaseEntityMicroReconstructor reconstructor;
    private int lastCookTime = 0;
    private int lastPower = 0;
    private int lastMass = 0;

    public ContainerMicroReconstructor(InventoryPlayer par1InventoryPlayer,
                                       TileEntityBaseEntityMicroReconstructor reconstructor) {
        this.reconstructor = reconstructor;
        this.addSlotToContainer(new OutputSlot(reconstructor, 9, 122, 18));
        Slot schematic = new Slot(reconstructor, 10, 93, 54) {
            public boolean isItemValid(ItemStack par1ItemStack) {
                return par1ItemStack.getItem() instanceof IAssemblerSchematic;
            }
        };
        schematic.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon);
        this.addSlotToContainer(schematic);
        for (int y = 0; y < 3; ++y) {
            for (int x = 0; x < 3; ++x) {
                this.addSlotToContainer(new DisplaySlot(reconstructor, x + y
                        * 3, 32 + x * 18, 18 + y * 18) {
                    @Override
                    @SideOnly(Side.CLIENT)
                    public Icon getBackgroundIconIndex() {
                        return this.inventory
                                .getStackInSlot(10) != null ? null
                                : DisplaySlot.noPlaceDisplayIcon;
                    }
                });
            }
        }

        for (int y = 0; y < 2; ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlotToContainer(new Slot(reconstructor, 11 + x + y * 9,
                                                 8 + x * 18, 77 + y * 18));
            }
        }

        int i;

        // Bind player inventory
        for (i = 0; i < 3; ++i) {
            for (int j = 0; j < 9; ++j) {
                this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9
                        + 9, 8 + j * 18, 122 + i * 18));
            }
        }
        // Bind player actionbar
        for (i = 0; i < 9; ++i) {
            this.addSlotToContainer(new Slot(par1InventoryPlayer, i,
                                             8 + i * 18, 180));
        }
    }

    public void addCraftingToCrafters(ICrafting par1ICrafting) {
        super.addCraftingToCrafters(par1ICrafting);
        par1ICrafting.sendProgressBarUpdate(this, 0,
                                            this.reconstructor.cookTime);
        par1ICrafting.sendProgressBarUpdate(this, 1,
                                            this.reconstructor.getCurrentPower());
        par1ICrafting.sendProgressBarUpdate(this, 2,
                                            this.reconstructor.getMassAmount());
    }

    /**
     * Looks for changes made in the container, sends them to every listener.
     */
    public void detectAndSendChanges() {
        super.detectAndSendChanges();

        for (Object crafter : this.crafters) {
            ICrafting icrafting = (ICrafting) crafter;

            if (this.lastCookTime != this.reconstructor.cookTime) {
                icrafting.sendProgressBarUpdate(this, 0,
                                                this.reconstructor.cookTime);
            }
            if (this.lastPower != this.reconstructor.getCurrentPower()) {
                icrafting.sendProgressBarUpdate(this, 1,
                                                this.reconstructor.getCurrentPower());
            }
            if (this.lastMass != this.reconstructor.getMassAmount()) {
                icrafting.sendProgressBarUpdate(this, 2,
                                                this.reconstructor.getMassAmount());
            }
        }

        this.lastCookTime = this.reconstructor.cookTime;
        this.lastPower = this.reconstructor.getCurrentPower();
        this.lastMass = this.reconstructor.getMassAmount();
    }

    @SideOnly(Side.CLIENT)
    public void updateProgressBar(int par1, int par2) {
        switch (par1) {
            case 0:
                this.reconstructor.cookTime = par2;
                break;
            case 1:
                this.reconstructor.currentPower = par2;
                break;
            case 2:
                if (par2 > 0) {
                    this.reconstructor.setFluidAmount(par2);
                }
                else {
                    this.reconstructor.clearFluid();
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

    public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
        return this.reconstructor.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or
     * you will crash when someone does that.
     */
    public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
        ItemStack itemstack = null;
        Slot slot = (Slot) this.inventorySlots.get(par2);

        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();

            if (par2 < 9) {
                return null;
            }

            if (par2 >= 9 && par2 <= 10) {
                if (!this.mergeItemStack(itemstack1, 11, 28, true)) {
                    return null;
                }

                slot.onSlotChange(itemstack1, itemstack);
            }
            else if (par2 > 10 && par2 < 29) {
                if (!this.mergeItemStack(itemstack1, 29, 64, true)) {
                    return null;
                }
            }
            else if (par2 >= 29) {
                if (par2 >= 29 && par2 < 55) {
                    if (!this.mergeItemStack(itemstack1, 55, 64, false)) {
                        return null;
                    }
                }
                else if (par2 >= 55 && par2 < 64
                        && !this.mergeItemStack(itemstack1, 29, 55, false)) {
                    return null;
                }
            }
            else if (!this.mergeItemStack(itemstack1, 29, 64, false)) {
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
}
