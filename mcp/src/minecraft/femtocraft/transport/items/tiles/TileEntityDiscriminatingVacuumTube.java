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

package femtocraft.transport.items.tiles;

import femtocraft.utils.BaseInventory;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityDiscriminatingVacuumTube extends TileEntityVacuumTube
        implements IInventory {
    @Saveable
    BaseInventory filterItems = new BaseInventory(8);
    @Saveable
    boolean filterInclude = true;

    public TileEntityDiscriminatingVacuumTube() {
        super();
    }

    @Override
    protected boolean canAcceptItemStack(ItemStack item) {
        return filterCheck(item);
    }

    @Override
    public boolean canInsertItem(ItemStack item, ForgeDirection dir) {
        return filterCheck(item) && super.canInsertItem(item, dir)
                ;
    }

    private boolean filterCheck(ItemStack item) {
        boolean contained = false;

        for (ItemStack filterItem : filterItems.getInventory()) {
            if (filterItem == null) {
                continue;
            }

            if (filterItem.isItemEqual(item)) {
                contained = true;
                break;
            }
        }

        return filterInclude ? contained : !contained;
    }

    @Override
    public int getSizeInventory() {
        return filterItems.getSizeInventory();
    }

    @Override
    public ItemStack getStackInSlot(int i) {
        return filterItems.getStackInSlot(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int j) {
        return filterItems.decrStackSize(i, j);
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int i) {
        return filterItems.getStackInSlotOnClosing(i);
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemstack) {
        filterItems.setInventorySlotContents(i, itemstack);
    }

    @Override
    public String getInvName() {
        return null;
    }

    @Override
    public boolean isInvNameLocalized() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return filterItems.getInventoryStackLimit();
    }

    @Override
    public void openChest() {

    }

    @Override
    public void closeChest() {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemstack) {
        return filterItems.isItemValidForSlot(i, itemstack);
    }
}
