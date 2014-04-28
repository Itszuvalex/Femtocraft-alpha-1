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
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

import java.util.Arrays;

public class TileEntityBaseEntityNanoHorologe extends
                                              TileEntityBaseEntityIndustry
        implements IInventory, ISidedInventory {
    public static int powerToCook_default = 80;
    public static float tickMultiplier_default = 1.f;

    @FemtocraftDataUtils.Saveable
    protected BaseInventory inventory;

    public static final int inputSlot = 0;
    public static final int outputSlot = 4;
    public static final int inventorySize = 5;

    @FemtocraftDataUtils.Saveable
    private int cookTime = 0;
    @FemtocraftDataUtils.Saveable
    private int ticksToCook;

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

    protected float getTickMultiplier() {
        return tickMultiplier_default;
    }

    protected int getPowerToCook() {
        return powerToCook_default;
    }

    protected EnumTechLevel getTechLevel() {
        return EnumTechLevel.NANO;
    }

    protected int getOutputSlotIndex() {
        return outputSlot;
    }

    protected ItemStack[] getConfigurators() {
        return Arrays.copyOfRange(inventory.getInventory(),
                                  inputSlot + 1, getOutputSlotIndex() - 1);
    }

    public int getCookProgressScaled(int i) {
        if (ticksToCook == 0) return 0;
        return (cookTime * i) / ticksToCook;
    }

    @Override
    public boolean isWorking() {
        return chronoStack != null;
    }

    @Override
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
    protected void continueWork() {
        ++cookTime;
    }

    @Override
    protected boolean canFinishWork() {
        return cookTime >= ticksToCook;
    }

    @Override
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
    public int[] getAccessibleSlotsFromSide(int var1) {
        return ForgeDirection.getOrientation(var1) == ForgeDirection.UP ? new
                int[]{inputSlot} : new int[]{getOutputSlotIndex()};
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return i != getOutputSlotIndex();
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
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
        return i != getOutputSlotIndex();
    }
}
