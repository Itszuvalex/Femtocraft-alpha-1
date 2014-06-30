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

package com.itszuvalex.femtocraft.industry.tiles;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.industry.blocks.BlockMicroFurnace;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class TileEntityBaseEntityMicroFurnace extends
        TileEntityBaseEntityIndustry implements ISidedInventory {
    public static int powerToCook = 40;
    public static int ticksToCook = 100;
    public static int maxSmelt = 1;
    /**
     * The number of ticks that the current item has been cooking for
     */
    public
    @Saveable
    int furnaceCookTime = 0;
    public
    @Saveable
    int currentPower = 0;
    public
    @Saveable
    ItemStack smeltingStack = null;
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    protected
    @Saveable
    ItemStack[] furnaceItemStacks = new ItemStack[2];
    private
    @Saveable
    String field_94130_e;

    public TileEntityBaseEntityMicroFurnace() {
        super();
        setMaxStorage(800);
        setTechLevel(EnumTechLevel.MICRO);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.MicroFurnaceGuiID;
    }

    protected int getMaxSimultaneousSmelt() {
        return maxSmelt;
    }

    protected int getTicksToCook() {
        // TODO: Load from configs
        return ticksToCook;
    }

    protected int getPowerToCook() {
        // TODO: Load from configs
        return powerToCook;
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return this.furnaceItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.furnaceItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.furnaceItemStacks[par1] != null) {
            ItemStack itemstack;

            if (this.furnaceItemStacks[par1].stackSize <= par2) {
                itemstack = this.furnaceItemStacks[par1];
                this.furnaceItemStacks[par1] = null;
                return itemstack;
            } else {
                itemstack = this.furnaceItemStacks[par1].splitStack(par2);

                if (this.furnaceItemStacks[par1].stackSize == 0) {
                    this.furnaceItemStacks[par1] = null;
                }

                return itemstack;
            }
        } else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (this.furnaceItemStacks[par1] != null) {
            ItemStack itemstack = this.furnaceItemStacks[par1];
            this.furnaceItemStacks[par1] = null;
            return itemstack;
        } else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     */
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.furnaceItemStacks[par1] = par2ItemStack;

        if (par2ItemStack != null
            && par2ItemStack.stackSize > this.getInventoryStackLimit()) {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94130_e : "MicroFurnace";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized name, and translated into the player's
     * language. Otherwise it will be used directly.
     */
    @Override
    public boolean isInvNameLocalized() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void func_94129_a(String par1Str) {
        this.field_94130_e = par1Str;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1) {
        if (getTicksToCook() == 0) {
            return 0;
        }
        return this.furnaceCookTime * par1 / getTicksToCook();
    }

    @Override
    public boolean isWorking() {
        return smeltingStack != null;
    }

    @Override
    protected boolean canStartWork() {
        if (this.furnaceItemStacks[0] == null) {
            return false;
        }
        if (smeltingStack != null) {
            return false;
        } else if (getCurrentPower() < getPowerToCook()) {
            return false;
        } else {
            ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
                    this.furnaceItemStacks[0]);
            if (itemstack == null) {
                return false;
            }
            if (this.furnaceItemStacks[1] == null) {
                return true;
            }
            if (!this.furnaceItemStacks[1].isItemEqual(itemstack)) {
                return false;
            }
            int result = furnaceItemStacks[1].stackSize + itemstack.stackSize;
            return result <= getInventoryStackLimit() && result <= itemstack
                    .getMaxStackSize();
        }
    }

    @Override
    protected void startWork() {
        this.smeltingStack = this.furnaceItemStacks[0].copy();
        this.smeltingStack.stackSize = 0;

        int i = 0;
        do {

            if (furnaceItemStacks[1] != null
                && ((smeltingStack.stackSize + i) > furnaceItemStacks[1]
                    .getMaxStackSize())) {
                break;
            }

            if (furnaceItemStacks[0] == null) {
                break;
            }

            if (!consume(getPowerToCook())) {
                break;
            }

            ++smeltingStack.stackSize;
            --this.furnaceItemStacks[0].stackSize;

            if (this.furnaceItemStacks[0].stackSize <= 0) {
                this.furnaceItemStacks[0] = null;
            }
        } while (++i < getMaxSimultaneousSmelt());

        updateBlockState(true);

        this.onInventoryChanged();
        furnaceCookTime = 0;
    }

    @Override
    protected void continueWork() {
        ++furnaceCookTime;
    }

    @Override
    protected boolean canFinishWork() {
        return furnaceCookTime == getTicksToCook();
    }

    @Override
    protected void finishWork() {
        ItemStack itemstack = FurnaceRecipes.smelting().getSmeltingResult(
                this.smeltingStack);

        if (itemstack != null) {
            if (this.furnaceItemStacks[1] == null) {
                this.furnaceItemStacks[1] = itemstack.copy();
                this.furnaceItemStacks[1].stackSize = smeltingStack.stackSize;
            } else if (this.furnaceItemStacks[1].isItemEqual(itemstack)) {
                furnaceItemStacks[1].stackSize += smeltingStack.stackSize;
            }

            smeltingStack = null;
            updateBlockState(false);
            this.onInventoryChanged();
        }

        furnaceCookTime = 0;
    }

    protected void updateBlockState(boolean working) {
        BlockMicroFurnace.updateFurnaceBlockState(working, this.worldObj,
                this.xCoord, this.yCoord, this.zCoord);
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public void openChest() {
    }

    @Override
    public void closeChest() {
    }


    /**
     * ******************************************************************************** This function is here for
     * compatibilities sake, Modders should Check for Sided before ContainerWorldly, Vanilla Minecraft does not follow
     * the sided standard that Modding has for a while.
     * <p/>
     * In vanilla:
     * <p/>
     * Top: Ores Sides: Fuel Bottom: Output
     * <p/>
     * Standard Modding: Top: Ores Sides: Output Bottom: Fuel
     * <p/>
     * The Modding one is designed after the GUI, the vanilla one is designed because its intended use is for the
     * hopper, which logically would take things in from the top.
     * <p/>
     * This will possibly be removed in future updates, and make vanilla the definitive standard.
     */

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        switch (i) {
            case (1):
                return false;
            default:
                return true;
        }
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        switch (var1) {
            case (1):
                return new int[]{0};
            case (0):
            case (2):
            case (3):
            case (4):
            case (5):
                return new int[]{1};
            default:
                return new int[]{};
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        switch (i) {
            case (1):
                return false;
            default:
                return true;
        }
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
    }
}
