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

package femtocraft.industry.tiles;

import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.temporal.TemporalRecipe;
import femtocraft.utils.BaseInventory;
import femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;

import java.util.Arrays;

public class TileEntityBaseEntityNanoHorologe extends
                                              TileEntityBaseEntityIndustry
        implements IInventory, ISidedInventory {
    public static int powerToCook_default = 80;
    public static int ticksToCook_default = 8 * 20;

    @FemtocraftDataUtils.Saveable
    protected BaseInventory inventory;

    public static final int inputSlot = 0;
    public static final int outputSlot = 4;
    public static final int inventorySize = 5;

    @FemtocraftDataUtils.Saveable
    private int cookTime = 0;

    @FemtocraftDataUtils.Saveable
    public ItemStack[] chronoConfigStacks = null;
    @FemtocraftDataUtils.Saveable
    public ItemStack chronoStack = null;

    public TileEntityBaseEntityNanoHorologe() {
        super();
        inventory = new BaseInventory(inventorySize);
        setTechLevel(EnumTechLevel.NANO);
        setMaxStorage(10000);
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    protected int getTicksToCook() {
        return ticksToCook_default;
    }

    protected int getPowerToCook() {
        return ticksToCook_default;
    }

    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.NANO;
    }

    @Override
    public boolean isWorking() {
        return chronoStack != null;
    }

    @Override
    protected boolean canStartWork() {
        if (chronoStack != null) {
            return false;
        }
        if (getCurrentPower() < getPowerToCook()) {
            return false;
        }

        TemporalRecipe tr = Femtocraft.recipeManager.temporalRecipes
                .getRecipe(inventory.getStackInSlot(inputSlot),
                           Arrays.copyOfRange(inventory.getInventory(),
                                              inputSlot + 1, outputSlot - 1)
                );
        if (tr == null) {
            return false;
        }
        if (tr.techLevel.tier > getTechLevel().tier) {
            return false;
        }

        return true;
    }

    @Override
    protected void startWork() {
        cookTime = 0;
    }

    @Override
    protected void continueWork() {
        ++cookTime;
    }

    @Override
    protected boolean canFinishWork() {
        return cookTime >= getTicksToCook();
    }

    @Override
    protected void finishWork() {
        super.finishWork();
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return new int[0];
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return false;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return false;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        return inventory.decrStackSize(i, j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory.setInventorySlotContents(i, itemstack);
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return i != outputSlot;
    }
}
