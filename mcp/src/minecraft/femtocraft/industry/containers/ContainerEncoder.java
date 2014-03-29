package femtocraft.industry.containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import femtocraft.api.IAssemblerSchematic;
import femtocraft.common.gui.DisplaySlot;
import femtocraft.common.gui.OutputSlot;
import femtocraft.industry.items.ItemAssemblySchematic;
import femtocraft.industry.tiles.TileEntityEncoder;

public class ContainerEncoder extends Container {
	private TileEntityEncoder encoder;
	private int lastCookTime = 0;
	private int lastPower = 0;
	private int lastMass = 0;

	public ContainerEncoder(InventoryPlayer par1InventoryPlayer,
			TileEntityEncoder par2Encoder) {
		this.encoder = par2Encoder;

		for (int y = 0; y < 3; ++y) {
			for (int x = 0; x < 3; ++x) {
				this.addSlotToContainer(new Slot(encoder, x + 3 * y,
						31 + 18 * x, 16 + 18 * y));
			}
		}

		DisplaySlot recipeOutput = new DisplaySlot(encoder, 9, 87, 34);
		recipeOutput.setBackgroundIcon(DisplaySlot.noPlaceDisplayIcon);
		this.addSlotToContainer(recipeOutput);

		Slot schematicInput = new Slot(encoder, 10, 120, 8);
		schematicInput.setBackgroundIcon(ItemAssemblySchematic.placeholderIcon);
		this.addSlotToContainer(schematicInput);

		this.addSlotToContainer(new OutputSlot(encoder, 11, 120, 50));
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
		par1ICrafting.sendProgressBarUpdate(this, 0, this.encoder.timeWorked);
		par1ICrafting.sendProgressBarUpdate(this, 1,
				this.encoder.getCurrentPower());
	}

	/**
	 * Looks for changes made in the container, sends them to every listener.
	 */
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (Object crafter : this.crafters) {
			ICrafting icrafting = (ICrafting) crafter;

			if (this.lastCookTime != this.encoder.timeWorked) {
				icrafting.sendProgressBarUpdate(this, 0,
						this.encoder.timeWorked);
			}
			if (this.lastPower != this.encoder.getCurrentPower()) {
				icrafting.sendProgressBarUpdate(this, 1,
						this.encoder.getCurrentPower());
			}
			if (this.lastMass != this.encoder.getMassAmount()) {
				icrafting.sendProgressBarUpdate(this, 2,
						this.encoder.getMassAmount());
			}
		}

		this.lastCookTime = this.encoder.timeWorked;
		this.lastPower = this.encoder.getCurrentPower();
		this.lastMass = this.encoder.getMassAmount();
	}

	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int par1, int par2) {
		if (par1 == 0) {
			this.encoder.timeWorked = par2;
		}
		if (par1 == 1) {
			this.encoder.setCurrentStorage(par2);
		}
		if (par1 == 2) {
			if (par2 > 0) {
				this.encoder.setFluidAmount(par2);
			} else {
				this.encoder.clearFluid();
			}
		}
	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer) {
		return this.encoder.isUseableByPlayer(par1EntityPlayer);
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

			if (par2 < encoder.getSizeInventory()) {
				if (!this.mergeItemStack(itemstack1,
						encoder.getSizeInventory(),
						encoder.getSizeInventory() + 36, true)) {
					return null;
				}

				slot.onSlotChange(itemstack1, itemstack);
			} else if (par2 >= encoder.getSizeInventory()) {
				if (itemstack1.getItem() instanceof IAssemblerSchematic) {
					if (!this.mergeItemStack(itemstack1, 10, 11, false)) {
						return null;
					}
				} else if (par2 >= (encoder.getSizeInventory())
						&& par2 < (encoder.getSizeInventory() + 27)) {
					if (!this.mergeItemStack(itemstack1,
							encoder.getSizeInventory() + 27,
							encoder.getSizeInventory() + 36, false)) {
						return null;
					}
				} else if (par2 >= (encoder.getSizeInventory() + 27)
						&& par2 < (encoder.getSizeInventory() + 36)
						&& !this.mergeItemStack(itemstack1,
								encoder.getSizeInventory(),
								encoder.getSizeInventory() + 27, false)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1,
					encoder.getSizeInventory(),
					encoder.getSizeInventory() + 36, false)) {
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
