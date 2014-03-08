package femtocraft.research.containers;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.common.gui.OutputSlot;
import femtocraft.research.tiles.TileEntityResearchConsole;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class ContainerResearchConsole extends Container {
	private final TileEntityResearchConsole console;
	private int lastProgress = 0;
	private int lastProgressMax = 0;

	private static final int progressID = 0;
	private static final int progressMaxID = 1;

	public ContainerResearchConsole(InventoryPlayer par1InventoryPlayer,
			TileEntityResearchConsole console) {
		this.console = console;

		this.addSlotToContainer(new OutputSlot(console, 9, 147, 60));

		for (int i = 0; i < 9; ++i) {
			this.addSlotToContainer(new Slot(console, i, 8 + 18 * (i % 3),
					16 + 18 * (i / 3)));
		}

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

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer) {
		return console.isUseableByPlayer(entityplayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		super.updateProgressBar(par1, par2);

		switch (par1) {
		case progressID:
			console.setResearchProgress(par2);
			break;
		case progressMaxID:
			console.setResearchMax(par2);
			break;
		}
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object crafter : this.crafters) {
			ICrafting icrafting = (ICrafting) crafter;

			if (this.lastProgress != this.console.getResearchProgress()) {
				icrafting.sendProgressBarUpdate(this, progressID,
						this.console.getResearchProgress());
			}
			if (this.lastProgressMax != this.console.getResearchMax()) {
				icrafting.sendProgressBarUpdate(this, progressMaxID,
						this.console.getResearchMax());
			}
		}

		this.lastProgress = this.console.getResearchProgress();
		this.lastProgressMax = this.console.getResearchMax();
	}

	@Override
	public void addCraftingToCrafters(ICrafting par1iCrafting) {
		super.addCraftingToCrafters(par1iCrafting);
		par1iCrafting.sendProgressBarUpdate(this, progressID,
				console.getResearchProgress());
		par1iCrafting.sendProgressBarUpdate(this, progressMaxID,
				console.getResearchMax());
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
				slot.putStack(null);
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
