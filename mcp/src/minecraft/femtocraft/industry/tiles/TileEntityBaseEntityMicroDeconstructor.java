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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.Femtocraft;
import femtocraft.managers.ManagerRecipe;
import femtocraft.managers.assembler.AssemblerRecipe;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.BaseInventory;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.Arrays;

public class TileEntityBaseEntityMicroDeconstructor extends
                                                    TileEntityBaseEntityIndustry implements ISidedInventory, IFluidHandler {
    public static int powerToCook = 40;
    public static int ticksToCook = 100;
    public static int maxSmelt = 1;
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
        setMaxStorage(800);
        tank = new FluidTank(600);
        setTechLevel(EnumTechLevel.MICRO);
        deconstructorInventory = new BaseInventory(10);
    }

    public int getMassAmount() {
        return tank.getFluidAmount();
    }

    public int getMassCapacity() {
        return tank.getCapacity();
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

    protected EnumTechLevel getAssemblerTech() {
        return EnumTechLevel.MICRO;
    }

    /**
     * Returns the number of slots in the inventory.
     */

    public int getSizeInventory() {
        return deconstructorInventory.getSizeInventory();
    }

    /**
     * Returns the stack in slot i
     */
    public ItemStack getStackInSlot(int par1) {
        return deconstructorInventory.getStackInSlot(par1);
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number
     * (second arg) of items and returns them in a new stack.
     */
    public ItemStack decrStackSize(int par1, int par2) {
        ItemStack ret = deconstructorInventory.decrStackSize(par1, par2);
        this.onInventoryChanged();
        return ret;
    }

    /**
     * When some containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (deconstructorInventory.getInventory()[par1] != null) {
            ItemStack itemstack = deconstructorInventory.getInventory()[par1];
            deconstructorInventory.getInventory()[par1] = null;
            return itemstack;
        }
        else {
            return null;
        }
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be
     * crafting or armor sections).
     */
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        deconstructorInventory.setInventorySlotContents(par1, par2ItemStack);
        this.onInventoryChanged();
    }

    /**
     * Returns the name of the inventory.
     */
    public String getInvName() {
        return this.isInvNameLocalized() ? this.field_94130_e
                : "Microtech Deconstructor";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized
     * name, and translated into the player's language. Otherwise it will be
     * used directly.
     */
    public boolean isInvNameLocalized() {
        return this.field_94130_e != null && this.field_94130_e.length() > 0;
    }

    public void func_94129_a(String par1Str) {
        this.field_94130_e = par1Str;
    }

    /**
     * Returns the maximum stack size for a inventory slot. Seems to always be
     * 64, possibly will be extended. *Isn't this more of a set than a get?*
     */
    public int getInventoryStackLimit() {
        return deconstructorInventory.getInventoryStackLimit();
    }

    @SideOnly(Side.CLIENT)
    /**
     * Returns an integer between 0 and the passed value representing how close the current item is to being completely
     * cooked
     */
    public int getCookProgressScaled(int par1) {
        return this.cookTime * par1 / ticksToCook;
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.industry.tiles.TileEntityBaseEntityIndustry#isWorking()
     */
    @Override
    public boolean isWorking() {
        return deconstructingStack != null;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#canStartWork()
     */
    @Override
    protected boolean canStartWork() {
        if (getStackInSlot(0) == null || deconstructingStack != null
                || this.getCurrentPower() < getPowerToCook()) {
            return false;
        }
        else {
            AssemblerRecipe recipe = ManagerRecipe.assemblyRecipes
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

    @Override
    protected void startWork() {
        deconstructingStack = getStackInSlot(0).copy();
        AssemblerRecipe recipe = ManagerRecipe.assemblyRecipes
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

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#continueWork()
     */
    @Override
    protected void continueWork() {
        ++cookTime;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#canFinishWork()
     */
    @Override
    protected boolean canFinishWork() {
        return cookTime >= getTicksToCook();
    }

    /*
     * (non-Javadoc)
     *
     * @see femtocraft.industry.tiles.TileEntityBaseEntityIndustry#finishWork()
     */
    @Override
    protected void finishWork() {
        AssemblerRecipe recipe = ManagerRecipe.assemblyRecipes
                .getRecipe(deconstructingStack);
        int[] placementRestrictionArray = new int[]{0};
        for (int i = 0; i < deconstructingStack.stackSize; i += recipe.output.stackSize) {
            if (recipe != null) {
                for (ItemStack item : recipe.input) {
                    FemtocraftUtils.placeItem(item,
                                              deconstructorInventory.getInventory(),
                                              placementRestrictionArray);
                }
                // tank.fill(new FluidStack(Femtocraft.mass, recipe.mass),
                // true);
                if (tank.getFluid() == null) {
                    tank.setFluid(new FluidStack(Femtocraft.mass, recipe.mass));
                }
                else {
                    tank.getFluid().amount += recipe.mass;
                }
                // deconstructingStack = null;
            }
        }

        deconstructingStack = null;
        cookTime = 0;
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
    public boolean hasGUI() {
        return true;
    }

    public void openChest() {
    }

    public void closeChest() {
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring
     * stack size) into the given slot.
     */
    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 == 0;
    }

    /**
     * Get the size of the side inventory.
     */
    public int[] getSizeInventorySide(int par1) {
        if (par1 == 1) {
            return new int[]{0};
        }
        else {
            return new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9};
        }
    }

    public boolean func_102007_a(int par1, ItemStack par2ItemStack, int par3) {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }

    public boolean func_102008_b(int par1, ItemStack par2ItemStack, int par3) {
        return true;
    }

    /**
     * ********************************************************************************
     * This function is here for compatibilities sake, Modders should Check for
     * Sided before ContainerWorldly, Vanilla Minecraft does not follow the
     * sided standard that Modding has for a while.
     * <p/>
     * In vanilla:
     * <p/>
     * Top: Ores Sides: Fuel Bottom: Output
     * <p/>
     * Standard Modding: Top: Ores Sides: Output Bottom: Fuel
     * <p/>
     * The Modding one is designed after the GUI, the vanilla one is designed
     * because its intended use is for the hopper, which logically would take
     * things in from the top.
     * <p/>
     * This will possibly be removed in future updates, and make vanilla the
     * definitive standard.
     */

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return i == 0;
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
        // We don't want to be able to put mass into this from anywhere, only
        // withdraw
        // Otherwise, we'd also have to worry about mass being pumped in while a
        // decomposition is happening, which would result in an overflow of mass
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
        }
        else {
            tank.setFluid(new FluidStack(Femtocraft.mass, amount));
        }
    }

    public void clearFluid() {
        tank.setFluid(null);
    }
}
