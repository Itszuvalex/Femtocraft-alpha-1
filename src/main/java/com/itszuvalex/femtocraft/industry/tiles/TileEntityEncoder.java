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
import com.itszuvalex.femtocraft.api.industry.IAssemblerSchematic;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Arrays;

@Configurable
public class TileEntityEncoder extends TileEntityBaseEntityIndustry implements
        IInventory, IFluidHandler {
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel TECH_LEVEL = EnumTechLevel.MICRO;
    @Configurable(comment = "Mass storage maximum.")
    public static int MASS_STORAGE = 1000;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 1200;
    @Configurable(comment = "Power per item to begin processing.")
    public static int POWER_TO_ENCODE = 100;
    @Configurable(comment = "Ticks required to process.")
    public static int TICKS_TO_ENCODE = 200;
    @Saveable
    public int timeWorked = 0;
    @Saveable
    BaseInventory inventory;
    @Saveable
    FluidTank massTank;
    @Saveable
    private AssemblerRecipe encodingRecipe;
    @Saveable
    private ItemStack encodingSchematic;

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return TECH_LEVEL;
    }

    @Override
    public int getMaxPower() {
        return POWER_STORAGE;
    }

    public TileEntityEncoder() {
        inventory = new BaseInventory(12);
        massTank = new FluidTank(MASS_STORAGE);
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBase#hasGUI()
     */
    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.EncoderGuiID();
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
        ItemStack ret = inventory.decrStackSize(i, j);
        this.onInventoryChanged();
        return ret;
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return i == 9 ? null : inventory.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        inventory.setInventorySlotContents(i, itemstack);
        this.onInventoryChanged();
    }

    @Override
    public String getInvName() {
        return Femtocraft.ID().toLowerCase() + "." + "InventoryEncoder";
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
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
    }

    @Override
    public void openChest() {
        inventory.openChest();
    }

    @Override
    public void closeChest() {
        inventory.closeChest();
    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        switch (i) {
            case 9:
                return false;
            case 10:
                return itemstack.getItem() instanceof IAssemblerSchematic;
        }
        return inventory.isItemValidForSlot(i, itemstack);
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.fluidID != Femtocraft.fluidMass().getID()) {
            return 0;
        }
        return massTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        return massTank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return massTank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{massTank.getInfo()};
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBaseEntityIndustry#isWorking()
     */
    @Override
    public boolean isWorking() {
        return encodingRecipe != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#canStartWork()
     */
    @Override
    protected boolean canStartWork() {
        AssemblerRecipe recipe = getRecipe();
        return recipe != null
               && getStackInSlot(11) == null
               && getStackInSlot(10) != null
               && getCurrentPower() >= POWER_TO_ENCODE
               && massTank.getFluidAmount() >= ((IAssemblerSchematic) getStackInSlot(
                10).getItem()).massRequired(recipe)
               && getStackInSlot(10).getItem() instanceof IAssemblerSchematic;
    }

    private AssemblerRecipe getRecipe() {
        AssemblerRecipe recipe = Femtocraft.recipeManager().assemblyRecipes
                .getRecipe(Arrays.copyOfRange(inventory.getInventory(), 0, 9));
        if (recipe == null) {
            return null;
        }
        boolean researched = Femtocraft.researchManager()
                .hasPlayerResearchedTechnology(getOwner(), recipe.tech);
        return researched ? recipe : null;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBaseEntityIndustry#startWork()
     */
    @Override
    protected void startWork() {
        encodingSchematic = decrStackSize(10, 1);
        encodingRecipe = getRecipe();
        timeWorked = 0;
        consume(POWER_TO_ENCODE);
        massTank.drain(((IAssemblerSchematic) encodingSchematic.getItem())
                .massRequired(encodingRecipe), true);
    }

    @Override
    public void onInventoryChanged() {
        AssemblerRecipe recipe = getRecipe();
        // Do inventory directly to avoid recursive onInventoryChanged() calls

        inventory.setInventorySlotContents(9, recipe == null ? null
                : recipe.output.copy());

        super.onInventoryChanged();

    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#continueWork()
     */
    @Override
    protected void continueWork() {
        ++timeWorked;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#canFinishWork()
     */
    @Override
    protected boolean canFinishWork() {
        return timeWorked >= TICKS_TO_ENCODE;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBaseEntityIndustry#finishWork()
     */
    @Override
    protected void finishWork() {
        timeWorked = 0;
        ((IAssemblerSchematic) encodingSchematic.getItem()).setRecipe(
                encodingSchematic, encodingRecipe);
        setInventorySlotContents(11, encodingSchematic);
        encodingSchematic = null;
        encodingRecipe = null;
    }

    public int getProgressScaled(int i) {
        return (timeWorked * i) / TICKS_TO_ENCODE;
    }

    public int getMassAmount() {
        return massTank.getFluidAmount();
    }

    public void setFluidAmount(int amount) {
        if (massTank.getFluid() != null) {
            massTank.setFluid(new FluidStack(massTank.getFluid().fluidID,
                    amount));
        } else {
            massTank.setFluid(new FluidStack(Femtocraft.fluidMass(), amount));
        }
    }

    public void clearFluid() {
        massTank.setFluid(null);
    }

    public int getMassCapacity() {
        return massTank.getCapacity();
    }
}
