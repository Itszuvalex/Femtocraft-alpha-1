package femtocraft.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import femtocraft.utils.FemtocraftDataUtils.EnumSaveType;
import femtocraft.utils.FemtocraftDataUtils.Saveable;

public class BaseInventory implements IInventory, ISaveable {
	@Saveable
	protected ItemStack[] inventory;

	public BaseInventory(int size) {
		inventory = new ItemStack[size];
	}

	private BaseInventory() {

	}

	/**
	 * 
	 * @return ItemStack[] that backs this inventory class. Modifications to it
	 *         modify this.
	 */
	public ItemStack[] getInventory() {
		return inventory;
	}

	@Override
	public int getSizeInventory() {
		return inventory.length;
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory[i];
	}

	@Override
	public ItemStack decrStackSize(int i, int amount) {
		if (inventory[i] != null) {
			ItemStack itemstack;

			if (inventory[i].stackSize <= amount) {
				itemstack = inventory[i];
				inventory[i] = null;
				return itemstack;
			} else {
				itemstack = inventory[i].splitStack(amount);

				if (inventory[i].stackSize == 0) {
					inventory[i] = null;
				}

				return itemstack;
			}
		} else {
			return null;
		}
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return inventory[i];
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory[i] = itemstack;
	}

	@Override
	public String getInvName() {
		return "femto.BaseInventory.ImLazyAndDidntCodeThis";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public void onInventoryChanged() {
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		return true;
	}

	@Override
	public void openChest() {

	}

	@Override
	public void closeChest() {

	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		return true;
	}

	@Override
	public void saveToNBT(NBTTagCompound compound) {
		FemtocraftDataUtils.saveObjectToNBT(compound, this, EnumSaveType.WORLD);
	}

	@Override
	public void loadFromNBT(NBTTagCompound compound) {
		FemtocraftDataUtils.loadObjectFromNBT(compound, this,
				EnumSaveType.WORLD);
	}
}
