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

package com.itszuvalex.femtocraft.industry.tiles;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.temporal.TemporalRecipe;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Arrays;

@Configurable
public class TileEntityBaseEntityNanoHorologe extends
        TileEntityBaseEntityIndustry
        implements ISidedInventory {
    public static final int inputSlot = 0;
    public static final int outputSlot = 4;
    public static final int inventorySize = 5;
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel TECH_LEVEL = EnumTechLevel.NANO;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 10000;
    @Configurable(comment = "Power per item to begin processing.")
    public static int POWER_TO_COOK = 80;
    @Configurable(comment = "Multiplier for tick processing time of Temporal Recipes.")
    public static float TICKS_TO_COOK_MULTIPLIER = 1.f;
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

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(TECH_LEVEL, POWER_STORAGE);
    }

    public TileEntityBaseEntityNanoHorologe() {
        super();
        inventory = new BaseInventory(inventorySize);
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoHorologeGuiID();
    }

    public int getCookProgressScaled(int i) {
        if (ticksToCook == 0) {
            return 0;
        }
        return cookTime * i / ticksToCook;
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        return ForgeDirection.getOrientation(var1) == ForgeDirection.UP ? new
                int[]{inputSlot} : new int[]{getOutputSlotIndex()};
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return i != getOutputSlotIndex();
    }

    protected float getTickMultiplier() {
        return TICKS_TO_COOK_MULTIPLIER;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
    }

    protected int getOutputSlotIndex() {
        return outputSlot;
    }

    protected int getPowerToCook() {
        return POWER_TO_COOK;
    }

    @Override
    public int getSizeInventory() {
        return inventory.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return inventory.getStackInSlot(i);
    }

    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.NANO;
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
    public String getInventoryName() {
        return inventory.getInventoryName();
    }

    protected ItemStack[] getConfigurators() {
        return Arrays.copyOfRange(inventory.getInventory(),
                inputSlot + 1, getOutputSlotIndex());
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return inventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }

    @Override
    public boolean isWorking() {
        return chronoStack != null;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    protected boolean canStartWork() {
        if (isWorking()) {
            return false;
        }

        if (getCurrentPower() < getPowerToCook()) {
            return false;
        }

        TemporalRecipe tr = Femtocraft.recipeManager().temporalRecipes
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
                !Femtocraft.researchManager().hasPlayerResearchedTechnology(getOwner(),
                        tr.getTechnology())) {
            return false;
        }

        //If not enough of input
        if (inventory.getStackInSlot(inputSlot).stackSize < tr.input.stackSize) {
            return false;
        }

        //If output item mismatch
        ItemStack output = inventory.getStackInSlot(getOutputSlotIndex());
        if (output != null) {
            if (!output.isItemEqual(tr.output)) {
                return false;
            }
            //If not enough room in output stack for output of recipe
            if ((output.getMaxStackSize() - output.stackSize) < tr.output.stackSize) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return i != getOutputSlotIndex();
    }

    public int getProgress() {
        return cookTime;
    }

    @Override
    protected void startWork() {
        TemporalRecipe tr = Femtocraft.recipeManager().temporalRecipes
                .getRecipe(inventory.getStackInSlot(inputSlot),
                        getConfigurators());

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

    public void setProgress(int progress) {
        cookTime = progress;
    }

    public void setProgressMax(int progressMax) {
        ticksToCook = progressMax;
    }

    @Override
    protected void continueWork() {
        ++cookTime;
    }


    @Override
    protected boolean canFinishWork() {
        return cookTime >= ticksToCook;
    }


    @Override
    protected void finishWork() {
        TemporalRecipe tr = Femtocraft.recipeManager().temporalRecipes
                .getRecipe(chronoStack, chronoConfigStacks);
        if (tr != null) {
            int[] placeRestrictions = new int[inventory.getSizeInventory() - 1];
            for (int i = 0; i < placeRestrictions.length; ++i) {
                placeRestrictions[i] = i;
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


    public int getProgressMax() {
        return ticksToCook;
    }
}
