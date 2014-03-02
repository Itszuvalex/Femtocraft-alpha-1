package femtocraft.research.tiles;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import femtocraft.FemtocraftDataUtils.Saveable;
import femtocraft.core.tiles.TileEntityBase;

public class TileResearchConsole extends TileEntityBase implements IInventory {
	private @Saveable(desc = true)
	String displayTech;
	private @Saveable(desc = true)
	String researchingTech;
	private @Saveable
	int progress;
	private @Saveable
	int progressMax;
	private @Saveable
	ItemStack[] inventory;

	public TileResearchConsole() {
		super();
		progress = 0;
		progressMax = 0;
		inventory = new ItemStack[10];
		displayTech = null;
		researchingTech = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.core.tiles.TileEntityBase#hasGUI()
	 */
	@Override
	public boolean hasGUI() {
		return true;
	}

	@Override
	public void onInventoryChanged() {
		super.onInventoryChanged();
		checkForTechnology();
	}

	private void checkForTechnology() {
		displayTech = null;

	}

	public void setResearchProgress(int progress) {
		this.progress = progress;
	}

	public int getResearchProgress() {
		return progress;
	}

	public int getResearchProgressScaled(int scale) {
		if (progressMax == 0)
			return 0;
		return (progress * scale) / progressMax;
	}

	public void setResearchMax(int progressMax) {
		this.progressMax = progressMax;
	}

	public int getResearchMax() {
		return progressMax;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		// displayTech = par1nbtTagCompound.getString("displayTech");
		// researchingTech = par1nbtTagCompound.getString("researchingTech");
		// progress = par1nbtTagCompound.getInteger("progress");
		// progressMax = par1nbtTagCompound.getInteger("progressMax");

	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		// par1nbtTagCompound.setString("displayTech", displayTech);
		// par1nbtTagCompound.setString("research", researchingTech);
		// par1nbtTagCompound.setInteger("progress", progress);
		// par1nbtTagCompound.setInteger("progressMax", progressMax);

	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
		// researchingTech = pkt.data.getString("research");
	}

	@Override
	public void saveToDescriptionCompound(NBTTagCompound compound) {
		super.saveToDescriptionCompound(compound);
		// compound.setString("research", researchingTech);
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
	public ItemStack decrStackSize(int i, int j) {
		if (this.inventory[i] != null) {
			ItemStack itemstack;

			if (this.inventory[i].stackSize <= j) {
				itemstack = this.inventory[i];
				this.inventory[i] = null;
				return itemstack;
			} else {
				itemstack = this.inventory[i].splitStack(j);

				if (this.inventory[i].stackSize == 0) {
					this.inventory[i] = null;
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
		return "Research Console";
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
	public void openChest() {
	}

	@Override
	public void closeChest() {
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		switch (i) {
		case 9:
			return false;
		default:
			return true;
		}
	}

}
