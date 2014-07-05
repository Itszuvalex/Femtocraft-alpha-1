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

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.temporal.TemporalRecipe;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import java.util.Arrays;

public class TileEntityBaseEntityNanoHorologe extends
                                              TileEntityBaseEntityIndustry
        implements ISidedInventory {
    public static final int inputSlot = 0;
    public static final int outputSlot = 4;
    public static final int inventorySize = 5;
    public static int powerToCook_default = 80;
    public static float tickMultiplier_default = 1.f;
    @FemtocraftDataUtils.Saveable
    public ItemStack[] chronoConfigStacks = null;
    @FemtocraftDataUtils.Saveable
    public ItemStack chronoStack = null;
    @FemtocraftDataUtils.Saveable
    protected BaseInventory inventory;
    @FemtocraftDataUtils.Saveable
    private int cookTime = 0;
    @FemtocraftDataUtils.Saveable
    private int ticksToCook;
    public TileEntityBaseEntityNanoHorologe() {
        super();
        inventory = new BaseInventory(inventorySize);
        setTechLevel(EnumTechLevel.NANO);
        setMaxStorage(10000);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoHorologeGuiID;
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    public int getCookProgressScaled(int i) {
        if (ticksToCook == 0) {
            return 0;
        }
        return (cookTime * i) / ticksToCook;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return ForgeDirection.getOrientation(var1) == ForgeDirection.UP ? new
                int[]{inputSlot} : new int[]{getOutputSlotIndex()};
    }    protected float getTickMultiplier() {
        return tickMultiplier_default;
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return i != getOutputSlotIndex();
    }    protected int getPowerToCook() {
        return powerToCook_default;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
    }    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.NANO;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }    protected int getOutputSlotIndex() {
        return outputSlot;
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.getStackInSlot(i);
    }    protected ItemStack[] getConfigurators() {
        return Arrays.copyOfRange(inventory.getInventory(),
                inputSlot + 1, getOutputSlotIndex() - 1);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        return inventory.decrStackSize(i, j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return inventory.getStackInSlotOnClosing(i);
    }    @Override
    public boolean isWorking() {
        return chronoStack != null;
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory.setInventorySlotContents(i, itemstack);
    }    @Override
    protected boolean canStartWork() {
        if (isWorking()) {
            return false;
        }

        if (getCurrentPower() < getPowerToCook()) {
            return false;
        }

        TemporalRecipe tr = Femtocraft.recipeManager.temporalRecipes
                .getRecipe(inventory.getStackInSlot(inputSlot),
                        getConfigurators()
                );
        if (tr == null) {
            return false;
        }
        if (tr.techLevel.tier > getTechLevel().tier) {
            return false;
        }
        if (
                !Femtocraft.researchManager.hasPlayerResearchedTechnology(getOwner(),
                        tr.getTechnology())) {
            return false;
        }

        //If not enough of input
        if (inventory.getStackInSlot(inputSlot).stackSize < tr.input.stackSize) {
            return false;
        }

        //If output item mismatch
        ItemStack output = inventory.getStackInSlot(getOutputSlotIndex());
        if (!output.isItemEqual(tr.output)) {
            return false;
        }
        //If not enough room in output stack for output of recipe
        if ((output.getMaxStackSize() - output.stackSize) < tr.output.stackSize) {
            return false;
        }

        return true;
    }

    @Override
    public String getInvName() {
        return inventory.getInvName();
    }    @Override
    protected void startWork() {
        TemporalRecipe tr = Femtocraft.recipeManager.temporalRecipes
                .getRecipe(inventory.getStackInSlot(inputSlot),
                        Arrays.copyOfRange(inventory.getInventory(),
                                inputSlot + 1, getOutputSlotIndex() - 1)
                );

        chronoStack = inventory.decrStackSize(inputSlot, tr.input.stackSize);
        ItemStack[] configurators = getConfigurators();
        chronoConfigStacks = new ItemStack[configurators.length];
        for (int i = 0; i < chronoConfigStacks.length; ++i) {
            chronoConfigStacks[i] = configurators[i] == null ? null :
                    configurators[i].copy();
        }
        consume(getPowerToCook());
        ticksToCook = (int) (tr.ticks * getTickMultiplier());
        cookTime = 0;
        onInventoryChanged();
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }    @Override
    protected void continueWork() {
        ++cookTime;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }    @Override
    protected boolean canFinishWork() {
        return cookTime >= ticksToCook;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }    @Override
    protected void finishWork() {
        TemporalRecipe tr = Femtocraft.recipeManager.temporalRecipes
                .getRecipe(chronoStack, chronoConfigStacks);
        if (tr != null) {
            int[] placeRestrictions = new int[inventory.getSizeInventory() - 1];
            for (int i = 0; i < placeRestrictions.length; ++i) {
                placeRestrictions[i] = i + (i > getOutputSlotIndex() ? 1 : 0);
            }

            FemtocraftUtils.placeItem(tr.output, inventory.getInventory(),
                    placeRestrictions);
            onInventoryChanged();
        }
        cookTime = 0;
        ticksToCook = 0;
        chronoStack = null;
        chronoConfigStacks = null;
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return i != getOutputSlotIndex();
    }

    public int getProgress() {
        return cookTime;
    }

    public void setProgress(int progress) {
        cookTime = progress;
    }






















}