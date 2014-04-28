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
import femtocraft.api.IAssemblerSchematic;
import femtocraft.managers.ManagerRecipe;
import femtocraft.managers.assembler.AssemblerRecipe;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import femtocraft.utils.FemtocraftUtils;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

public class TileEntityBaseEntityMicroReconstructor extends
                                                    TileEntityBaseEntityIndustry implements ISidedInventory, IFluidHandler {
    // TODO: Load from configs
    public static int powerToCook = 40;
    // TODO: Load from configs
    public static int ticksToCook = 100;
    // TODO: Load from configs
    public static int maxSmelt = 1;
    // TODO: Load from configs
    public static EnumTechLevel assemblerTech = EnumTechLevel.MICRO;
    /**
     * The number of ticks that the current item has been cooking for
     */
    public
    @Saveable
    int cookTime = 0;
    public
    @Saveable
    int currentPower = 0;
    public
    @Saveable
    ItemStack[] reconstructingStacks = null;

    /**
     * The ItemStacks that hold the items currently being used in the furnace
     */
    private
    @Saveable
    FluidTank tank;
    private boolean hasItems = true;
    /**
     * Slots 0-8 are for recipe area - these are dummy items, and should never
     * be touched except when setting for display purposes Slot 9 is for output
     * Slot 10 is for schematic card Slots 11-28 are internal inventory, to pull
     * from when building
     */
    private
    @Saveable
    ItemStack[] reconstructorItemStacks = new ItemStack[29];
    private
    @Saveable
    String field_94130_e;

    public TileEntityBaseEntityMicroReconstructor() {
        super();
        setMaxStorage(800);
        tank = new FluidTank(600);
        setTechLevel(EnumTechLevel.MICRO);
    }

    protected int getMaxSimultaneousSmelt() {
        return maxSmelt;
    }

    protected int getTicksToCook() {
        return ticksToCook;
    }

    protected int getPowerToCook() {
        return powerToCook;
    }

    protected EnumTechLevel getAssemblerTech() {
        return assemblerTech;
    }

    public int getMassAmount() {
        return tank.getFluidAmount();
    }

    public int getMassCapacity() {
        return tank.getCapacity();
    }

    private IAssemblerSchematic getSchematic() {
        ItemStack is = reconstructorItemStacks[10];
        if (is == null) {
            return null;
        }
        if (is.getItem() instanceof IAssemblerSchematic) {
            return (IAssemblerSchematic) is.getItem();
        }

        return null;
    }

    @Override
    public void onInventoryChanged() {
        IAssemblerSchematic as = getSchematic();
        if (as != null) {
            AssemblerRecipe recipe = as.getRecipe(reconstructorItemStacks[10]);
            if (recipe == null) {
                for (int i = 0; i < 9; ++i) {
                    reconstructorItemStacks[i] = null;
                }
            }
            else {
                for (int i = 0; i < recipe.input.length; ++i) {
                    ItemStack is = recipe.input[i];
                    reconstructorItemStacks[i] = (is == null) ? null : is
                            .copy();
                }
            }
        }
        else {
            for (int i = 0; i < 9; ++i) {
                reconstructorItemStacks[i] = null;
            }
        }

        hasItems = true;

        super.onInventoryChanged();
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return this.reconstructorItemStacks.length;
    }

    /**
     * Returns the stack in slot i
     */
    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.reconstructorItemStacks[par1];
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number
     * (second arg) of items and returns them in a new stack.
     */
    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (par1 < 9) {
            return null;
        }

        if (this.reconstructorItemStacks[par1] != null) {
            ItemStack itemstack;

            if (this.reconstructorItemStacks[par1].stackSize <= par2) {
                itemstack = this.reconstructorItemStacks[par1];
                this.reconstructorItemStacks[par1] = null;
                this.onInventoryChanged();
                return itemstack;
            }
            else {
                itemstack = this.reconstructorItemStacks[par1].splitStack(par2);

                if (this.reconstructorItemStacks[par1].stackSize == 0) {
                    this.reconstructorItemStacks[par1] = null;
                }

                this.onInventoryChanged();
                return itemstack;
            }
        }
        else {
            return null;
        }
    }

    /**
     * When some containers are closed they call this on each slot, then drop
     * whatever it returns as an EntityItem - like when you close a workbench
     * GUI.
     */
    @Override
    public ItemStack getStackInSlotOnClosing(int par1) {
        if (par1 < 9) {
            return null;
        }

        if (this.reconstructorItemStacks[par1] != null) {
            ItemStack itemstack = this.reconstructorItemStacks[par1];
            this.reconstructorItemStacks[par1] = null;
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
    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack) {
        this.reconstructorItemStacks[par1] = par2ItemStack;

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
        return this.isInvNameLocalized() ? this.field_94130_e
                : "Microtech Reconstructor";
    }

    /**
     * If this returns false, the inventory name will be used as an unlocalized
     * name, and translated into the player's language. Otherwise it will be
     * used directly.
     */
    @Override
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
        return this.cookTime * par1 / getTicksToCook();
    }

    @Override
    public boolean isWorking() {
        return reconstructingStacks != null;
    }

    @Override
    protected boolean canStartWork() {
        if (reconstructingStacks != null
                || (reconstructorItemStacks[0] == null
                && reconstructorItemStacks[1] == null
                && reconstructorItemStacks[2] == null
                && reconstructorItemStacks[3] == null
                && reconstructorItemStacks[4] == null
                && reconstructorItemStacks[5] == null
                && reconstructorItemStacks[6] == null
                && reconstructorItemStacks[7] == null
                && reconstructorItemStacks[8] == null)
                || getSchematic() == null
                || this.getCurrentPower() < getPowerToCook()
                || cookTime > 0) {
            return false;
        }
        else {
            if (!hasItems) {
                return false;
            }
            AssemblerRecipe recipe = getSchematic().getRecipe(
                    reconstructorItemStacks[10]);
            if (recipe == null) {
                return false;
            }
            if (!hasItems(recipe.input)) {
                hasItems = false;
                return false;
            }

            if (recipe.enumTechLevel.tier > getAssemblerTech().tier) {
                return false;
            }

            return tank.getFluidAmount() >= recipe.mass
                    && roomForItem(recipe.output);
        }
    }

    @Override
    protected void startWork() {
        IAssemblerSchematic as = getSchematic();
        AssemblerRecipe recipe = as.getRecipe(reconstructorItemStacks[10]);

        if (recipe == null) {
            return;
        }

        ItemStack[] icopy = new ItemStack[9];
        for (int i = 0; i < recipe.input.length; ++i) {
            icopy[i] = recipe.input[i] == null ? null : recipe.input[i].copy();
        }

        int maxSimul = (reconstructorItemStacks[9] == null ? recipe.output.getMaxStackSize() :
                reconstructorItemStacks[9]
                        .stackSize) / recipe.output
                .stackSize;

        for (int i = 0; i < icopy.length; ++i) {
            ItemStack is = icopy[i];
            if (is == null) continue;

            int iter = is.getMaxStackSize() / is.stackSize;
            maxSimul = Math.min(iter, maxSimul);
        }

        reconstructingStacks = null;

        int i = 0;
        int[] exclusions = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        do {
            //Can do?
            as = getSchematic();
            if (as == null) {
                break;
            }
            recipe = as.getRecipe(reconstructorItemStacks[10]);
            if (recipe == null) {
                break;
            }

            if (as.usesRemaining(reconstructorItemStacks[10]) == 0) {
                break;
            }

            if (getCurrentPower() < getPowerToCook()) {
                break;
            }
            if (getMassAmount() < recipe.mass) {
                break;
            }

            if (!hasItems(recipe.input)) {
                break;
            }

            //Do

            if (i == 0) {
                reconstructingStacks = icopy;
            }
            else {
                for (int j = 0; j < recipe.input.length; ++j) {
                    if (recipe.input[j] == null) continue;
                    reconstructingStacks[j].stackSize += recipe.input[j]
                            .stackSize;
                }
            }

            for (ItemStack item : recipe.input) {
                FemtocraftUtils.removeItem(item, reconstructorItemStacks,
                                           exclusions);
                onInventoryChanged();
            }

            consume(getPowerToCook());
            tank.drain(recipe.mass, true);

            if (!as.onAssemble(reconstructorItemStacks[10])) {
                reconstructorItemStacks[10] = as.resultOfBreakdown
                        (reconstructorItemStacks[10]);
                onInventoryChanged();
            }

            ++i;
        } while (i < getMaxSimultaneousSmelt() && i < maxSimul);

        if (reconstructingStacks != null) {
            cookTime = 0;
        }
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
        boolean empty = false;
        int[] placeRestrictions = new int[]{0, 1, 2, 3, 4, 5, 6,
                7, 8, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
                21, 22, 23, 24, 25, 26, 27, 28};
        while (reconstructingStacks != null && !empty) {
            // Cannot look off schematic, cause schematic can change mid-build
            AssemblerRecipe recipe = ManagerRecipe.assemblyRecipes
                    .getRecipe(reconstructingStacks);
            if (recipe == null) {
                break;
            }
            empty = true;
            for (int i = 0; i < 9 && i < recipe.input.length; ++i) {
                if (reconstructingStacks[i] == null) continue;

                if ((reconstructingStacks[i].stackSize -= recipe.input[i].stackSize) <= 0) {
                    reconstructingStacks[i] = null;
                }
                else {
                    empty = false;
                }

            }

            FemtocraftUtils.placeItem(recipe.output,
                                      reconstructorItemStacks, placeRestrictions
            );
        }
        reconstructingStacks = null;
        cookTime = 0;
    }

    private boolean roomForItem(ItemStack item) {
        ItemStack[] fake = new ItemStack[1];
        ItemStack output = reconstructorItemStacks[9];
        fake[0] = output == null ? null : output.copy();
        return FemtocraftUtils.placeItem(item, fake, null);
    }

    private boolean hasItems(ItemStack[] items) {
        int offset = 10;
        int size = reconstructorItemStacks.length;
        ItemStack[] inv = new ItemStack[size - offset];
        for (int i = offset; i < size; ++i) {
            ItemStack it = reconstructorItemStacks[i];
            inv[i - offset] = it == null ? null : it.copy();
        }

        for (ItemStack item : items) {
            if (!FemtocraftUtils.removeItem(item, inv, null)) {
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
        return par1 > 10
                || (par1 == 10 && par2ItemStack.getItem() instanceof IAssemblerSchematic);
    }

    /**
     * Get the size of the side inventory.
     */
    public int[] getSizeInventorySide(int par1) {
        // Regular inventory
        if (par1 == 1) {
            return new int[]{11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
                    23, 24, 25, 26, 27, 28};
        }
        // Output
        else {
            return new int[]{9};
        }
    }

    public boolean func_102007_a(int par1, ItemStack par2ItemStack, int par3) {
        return this.isStackValidForSlot(par1, par2ItemStack);
    }

    public boolean func_102008_b(int par1, ItemStack par2ItemStack, int par3) {
        return true;
    }

    /**
     * *************************************************************************
     * ******* This function is here for compatibilities sake, Modders should
     * Check for Sided before ContainerWorldly, Vanilla Minecraft does not
     * follow the sided standard that Modding has for a while.
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
        return i > 10
                || (i == 10 && itemstack.getItem() instanceof IAssemblerSchematic);
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int var1) {
        switch (var1) {
            case (1):
                return new int[]{11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22,
                        23, 24, 25, 26, 27, 28};
            case (0):
            case (2):
            case (3):
            case (4):
            case (5):
                return new int[]{9};
            default:
                return new int[]{};
        }
    }

    @Override
    public boolean canInsertItem(int i, ItemStack itemstack, int j) {
        return i > 10;
    }

    @Override
    public boolean canExtractItem(int i, ItemStack itemstack, int j) {
        return i == 9 || i > 10;
    }

    // IFluidHandler

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return tank.fill(resource, doFill);
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
        return fluid == Femtocraft.mass;
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
            tank.setFluid(new FluidStack(tank.getFluid().getFluid(), amount));
        }
        else {
            tank.setFluid(new FluidStack(Femtocraft.mass, amount));
        }
    }

    public void clearFluid() {
        tank.setFluid(null);
    }
}
