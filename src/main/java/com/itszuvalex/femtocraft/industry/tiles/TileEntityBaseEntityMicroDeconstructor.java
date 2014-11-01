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
import com.itszuvalex.femtocraft.api.AssemblerRecipe;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.BaseInventory;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.Arrays;

@Configurable
public class TileEntityBaseEntityMicroDeconstructor extends
        TileEntityBaseEntityIndustry implements ISidedInventory, IFluidHandler {

    @Configurable(comment = "Assembler recipe tech level maximum.")
    public static EnumTechLevel ASSEMBLER_TECH_LEVEL = EnumTechLevel.MICRO;
    @Configurable(comment = "Power tech level.")
    public static EnumTechLevel POWER_LEVEL = EnumTechLevel.MICRO;
    @Configurable(comment = "Power storage maximum.")
    public static int POWER_STORAGE = 800;
    @Configurable(comment = "Mass storage maximum.")
    public static int MASS_STORAGE = 600;
    @Configurable(comment = "Power per item to begin processing.")
    public static int POWER_TO_COOK = 40;
    @Configurable(comment = "Ticks required to process.")
    public static int TICKS_TO_COOK = 100;
    @Configurable(comment = "Maximum number of items allowed at a time.")
    public static int MAX_SMELT = 1;
    /**
     * The number of ticks that the current item has been cooking for
     */
    public
    @Saveable
    int cookTime = 0;
    public
    @Saveable
    int currentPower = 0;
    // ItemStack[] deconstructorItemStacks = new ItemStack[10];
    public
    @Saveable
    ItemStack deconstructingStack = null;
    private
    @Saveable
    FluidTank tank;
    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private
    @Saveable
    BaseInventory deconstructorInventory;
    private
    @Saveable
    String field_94130_e;

    public TileEntityBaseEntityMicroDeconstructor() {
        super();
        tank = new FluidTank(MASS_STORAGE);
        deconstructorInventory = new BaseInventory(10);
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.MicroDeconstructorGuiID();
    }

    public int getMassAmount() {
        return tank.getFluidAmount();
    }

    public int getMassCapacity() {
        return tank.getCapacity();
    }

    /**
     * Returns the number of slots in the inventory.
     */

    @Override
    public int getSizeInventory() {
        return deconstructorInventory.getSizeInventory();
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1) {
        return deconstructorInventory.getStackInSlot(par1);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        ItemStack ret = deconstructorInventory.decrStackSize(par1, par2);
        this.onInventoryChanged();
        return ret;
    }

    /**
     * When some containers are closed they call this on each slot, then drop whatever it returns as an EntityItem -
     * like when you close a workbench GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (deconstructorInventory.getInventory()[par1] != null) {
            ItemStack itemstack = deconstructorInventory.getInventory()[par1];
            deconstructorInventory.getInventory()[par1] = null;
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
        deconstructorInventory.setInventorySlotContents(par1, par2ItemStack);
        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    @Override
    public String getInventoryName() {
        return this.hasCustomInventoryName() ? this.field_94130_e
                : "Microtech Deconstructor";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be 64, possibly will be extended. *Isn't
     * this more of a set than a get?*
     */
    @Override
    public int getInventoryStackLimit() {
        return deconstructorInventory.getInventoryStackLimit();
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer entityplayer) {
        return canPlayerUse(entityplayer);
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
        return i == 0;
    }

    public void func_94129_a(String par1Str) {
        this.field_94130_e = par1Str;
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
        return this.cookTime * par1 / getTicksToCook();
    }

    protected int getTicksToCook() {
        return TICKS_TO_COOK;
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBaseEntityIndustry#isWorking()
     */
    @Override
    public boolean isWorking() {
        return deconstructingStack != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#canStartWork()
     */
    @Override
    protected boolean canStartWork() {
        if (getStackInSlot(0) == null || deconstructingStack != null
            || this.getCurrentPower() < getPowerToCook()) {
            return false;
        } else {
            AssemblerRecipe recipe = Femtocraft.recipeManager().assemblyRecipes
                    .getRecipe(deconstructorInventory.getStackInSlot(0));
            return recipe != null &&
                   recipe.enumTechLevel.tier <= getAssemblerTech().tier &&
                   (tank
                            .getCapacity() -
                    tank
                            .getFluidAmount())
                   >=
                   recipe.mass
                   && getStackInSlot(0).stackSize >= recipe.output.stackSize
                   && roomForItems(recipe.input);
        }
    }

    protected int getPowerToCook() {
        // TODO: Load from configs
        return POWER_TO_COOK;
    }

    protected EnumTechLevel getAssemblerTech() {
        return ASSEMBLER_TECH_LEVEL;
    }

    private boolean roomForItems(ItemStack[] items) {
        ItemStack[] fake = new ItemStack[deconstructorInventory
                .getSizeInventory()];
        for (int i = 0; i < fake.length; ++i) {
            ItemStack it = deconstructorInventory.getStackInSlot(i);
            fake[i] = it == null ? null : it.copy();
        }
        for (ItemStack item : items) {
            if (!FemtocraftUtils.placeItem(item == null ? null : item.copy(),
                    fake, null)) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected void startWork() {
        deconstructingStack = getStackInSlot(0).copy();
        AssemblerRecipe recipe = Femtocraft.recipeManager().assemblyRecipes
                .getRecipe(getStackInSlot(0));
        deconstructingStack.stackSize = 0;

        int massReq = 0;
        ArrayList<ItemStack> items = new ArrayList<ItemStack>();

        int i = 0;
        do {
            if (deconstructingStack.stackSize >= this.getInventoryStackLimit()) {
                break;
            }
            if (getStackInSlot(0) == null) {
                break;
            }

            items.addAll(Arrays.asList(recipe.input));

            ItemStack[] ita = new ItemStack[items.size()];
            items.toArray(ita);
            if (!roomForItems(ita)) {
                break;
            }

            if ((massReq += recipe.mass) > (tank.getCapacity() - tank
                    .getFluidAmount())) {
                break;
            }

            if (!consume(getPowerToCook())) {
                break;
            }

            deconstructingStack.stackSize += recipe.output.stackSize;
            decrStackSize(0, recipe.output.stackSize);
        } while (++i < getMaxSimultaneousSmelt());
        cookTime = 0;
        onInventoryChanged();
    }

    protected int getMaxSimultaneousSmelt() {
        return MAX_SMELT;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#continueWork()
     */
    @Override
    protected void continueWork() {
        ++cookTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * TileEntityBaseEntityIndustry#canFinishWork()
     */
    @Override
    protected boolean canFinishWork() {
        return cookTime >= getTicksToCook();
    }

    /*
     * (non-Javadoc)
     *
     * @see TileEntityBaseEntityIndustry#finishWork()
     */
    @Override
    protected void finishWork() {
        AssemblerRecipe recipe = Femtocraft.recipeManager().assemblyRecipes
                .getRecipe(deconstructingStack);
        int[] placementRestrictionArray = new int[]{0};
        for (int i = 0; i < deconstructingStack.stackSize; i += recipe.output.stackSize) {
            if (recipe != null) {
                for (ItemStack item : recipe.input) {
                    FemtocraftUtils.placeItem(item,
                            deconstructorInventory.getInventory(),
                            placementRestrictionArray);
                }
                // tank.fill(new FluidStack(Femtocraft.fluidMass, recipe.fluidMass),
                // true);
                if (tank.getFluid() == null) {
                    tank.setFluid(new FluidStack(Femtocraft.fluidMass(), recipe.mass));
                } else {
                    tank.getFluid().amount += recipe.mass;
                }
                // deconstructingStack = null;
            }
        }

        deconstructingStack = null;
        cookTime = 0;
        onInventoryChanged();
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
                return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
            default:
                return new int[]{};
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return i == 0;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return true;
    }

    // IFluidHandler

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        // We don't want to be able to put fluidMass into this from anywhere, only
        // withdraw
        // Otherwise, we'd also have to worry about fluidMass being pumped in while a
        // decomposition is happening, which would result in an overflow of fluidMass
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource,
                            boolean doDrain) {
        if (resource == null || !resource.isFluidEqual(tank.getFluid())) {
            return null;
        }
        return tank.drain(resource.amount, doDrain);
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return tank.drain(maxDrain, doDrain);
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return true;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[]{tank.getInfo()};
    }

    public void setFluidAmount(int amount) {
        if (tank.getFluid() != null) {
            tank.setFluid(new FluidStack(tank.getFluid().fluidID, amount));
        } else {
            tank.setFluid(new FluidStack(Femtocraft.fluidMass(), amount));
        }
    }

    public void clearFluid() {
        tank.setFluid(null);
    }

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(POWER_LEVEL, POWER_STORAGE);
    }
}
