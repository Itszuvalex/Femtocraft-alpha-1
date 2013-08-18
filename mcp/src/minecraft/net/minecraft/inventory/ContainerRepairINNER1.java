package net.minecraft.inventory;

import net.minecraft.item.ItemStack;

class ContainerRepairINNER1 extends InventoryBasic
{
    final ContainerRepair field_135010_a;

    ContainerRepairINNER1(ContainerRepair par1ContainerRepair, String par2Str, boolean par3, int par4)
    {
        super(par2Str, par3, par4);
        this.field_135010_a = par1ContainerRepair;
    }

    /**
     * Called when an the contents of an Inventory change, usually
     */
    public void onInventoryChanged()
    {
        super.onInventoryChanged();
        this.field_135010_a.onCraftMatrixChanged(this);
    }

    /**
     * Returns true if automation is allowed to insert the given stack (ignoring stack size) into the given slot.
     */
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack)
    {
        return true;
    }
}
