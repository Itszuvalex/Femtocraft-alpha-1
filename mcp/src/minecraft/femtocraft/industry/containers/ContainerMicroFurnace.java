package femtocraft.industry.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.industry.TileEntity.MicroFurnaceTile;

public class ContainerMicroFurnace extends Container {
	private MicroFurnaceTile furnace;
	private int lastCookTime = 0;
	private int lastPower = 0;

	public ContainerMicroFurnace(InventoryPlayer par1InventoryPlayer,
			MicroFurnaceTile par2TileEntityFurnace) {
		this.furnace = par2TileEntityFurnace;
		this.addSlotToContainer(new Slot(par2TileEntityFurnace, 0, 56, 35));
		this.addSlotToContainer(new SlotFurnace(par1InventoryPlayer.player,
				par2TileEntityFurnace, 1, 116, 35));
		int i;

		// Bind player inventory
		for (i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				this.addSlotToContainer(new Slot(par1InventoryPlayer, j + i * 9
						+ 9, 8 + j * 18, 84 + i * 18));
			}
		}
		// Bind player actionbar
		for (i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(par1InventoryPlayer, i,
					8 + i * 18, 142));
		}
	}

	public void addCraftingToCrafters(ICrafting par1ICrafting) {
		super.addCraftingToCrafters(par1ICrafting);
		par1ICrafting.sendProgressBarUpdate(this, 0,
				this.furnace.furnaceCookTime);
		par1ICrafting.sendProgressBarUpdate(this, 1,
				this.furnace.getCurrentPower());
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < this.crafters.size(); ++i) {
			ICrafting icrafting = (ICrafting) this.crafters.get(i);

			if (this.lastCookTime != this.furnace.furnaceCookTime) {
				icrafting.sendProgressBarUpdate(this, 0,
						this.furnace.furnaceCookTime);
			}
			if (this.lastPower != this.furnace.getCurrentPower()) {
				icrafting.sendProgressBarUpdate(this, 1,
						this.furnace.getCurrentPower());
			}
		}

		this.lastCookTime = this.furnace.furnaceCookTime;
		this.lastPower = this.furnace.getCurrentPower();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.furnace.furnaceCookTime = par2;
		}
		if (par1 == 1) {
			this.furnace.currentPower = par2;
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.furnace.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called when a player shift-clicks on a slot. You must override this or
	 * you will crash when someone does that.
	 */
	public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(par2);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (par2 == 1) {
				if (!this.mergeItemStack(itemstack1, 2, 38, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (par2 != 0) {
				if (FurnaceRecipes.smelting().getSmeltingResult(itemstack1) != null) {
					if (!this.mergeItemStack(itemstack1, 0, 1, false)) {
						return null;
					}
				} else if (par2 >= 2 && par2 < 29) {
					if (!this.mergeItemStack(itemstack1, 29, 38, false)) {
						return null;
					}
				} else if (par2 >= 29 && par2 < 38
						&& !this.mergeItemStack(itemstack1, 2, 29, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 2, 38, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (itemstack1.stackSize == itemstack.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(par1EntityPlayer, itemstack1);
		}

		return itemstack;
	}
}
