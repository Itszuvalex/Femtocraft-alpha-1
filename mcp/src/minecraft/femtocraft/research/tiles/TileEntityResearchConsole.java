package femtocraft.research.tiles;

import java.util.logging.Level;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet132TileEntityData;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftDataUtils.Saveable;
import femtocraft.api.ITechnologyCarrier;
import femtocraft.core.tiles.TileEntityBase;
import femtocraft.managers.research.ResearchTechnology;

public class TileEntityResearchConsole extends TileEntityBase implements IInventory {
	public static final String PACKET_CHANNEL = Femtocraft.ID +"." + "rcon";
	
	public @Saveable(desc = true)
	String displayTech = null;
	private @Saveable(desc = true)
	String researchingTech = null;
	private @Saveable
	int progress = 0;
	private @Saveable
	int progressMax = 0;
	private @Saveable
	ItemStack[] inventory = new ItemStack[10];

	private static final int ticksToResearch = 400;

	public TileEntityResearchConsole() {
		super();
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
		if (worldObj == null)
			return;
		if (worldObj.isRemote)
			return;

		boolean hadTech = displayTech != null;
		displayTech = null;

		for (ResearchTechnology tech : Femtocraft.researchManager
				.getTechnologies()) {
			if (Femtocraft.researchManager.hasPlayerDiscoveredTechnology(
					getOwner(), tech)) {
				if (matchesTechnology(tech)) {
					displayTech = tech.name;
					if (worldObj != null)
						worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					return;
				}
			}
		}

		if (hadTech && displayTech == null) {
			if (worldObj != null)
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
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

	public boolean isResearching() {
		return researchingTech != null;
	}

	public void setResearchMax(int progressMax) {
		this.progressMax = progressMax;
	}

	public int getResearchMax() {
		return progressMax;
	}

	public String getResearchingName() {
		return researchingTech;
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
	}

	@Override
	public void femtocraftServerUpdate() {
		super.femtocraftServerUpdate();
		if (researchingTech != null) {
			if (progress++ >= progressMax) {
				finishWork();
			}
		}
	}

	public void startWork() {
		if (!canWork())
			return;

		researchingTech = displayTech;
		progressMax = ticksToResearch;
		progress = 0;

		ResearchTechnology tech = Femtocraft.researchManager
				.getTechnology(displayTech);

		for (int i = 0; i < 9; ++i) {
			decrStackSize(i, tech.researchMaterials.get(i).stackSize);
		}
		
		this.onInventoryChanged();
	}

	private boolean canWork() {
		if (displayTech == null || displayTech.isEmpty())
			return false;
		if (researchingTech != null && !researchingTech.isEmpty())
			return false;
		ResearchTechnology tech = Femtocraft.researchManager
				.getTechnology(displayTech);

		return matchesTechnology(tech);
	}

	private boolean matchesTechnology(ResearchTechnology tech) {
		if (tech == null)
			return false;
		if (tech.researchMaterials == null)
			return false;
		for (int i = 0; i < 9; ++i) {
			if (!compareItemStack(tech.researchMaterials.get(i), inventory[i]))
				return false;
		}

		return true;
	}

	private boolean compareItemStack(ItemStack tech, ItemStack inv) {
		if (tech == null && inv == null)
			return true;
		if (tech == null && inv != null)
			return false;
		if (tech != null && inv == null)
			return false;

		if (tech.itemID != inv.itemID)
			return false;
		if (tech.getItemDamage() != inv.getItemDamage())
			return false;
		if (tech.stackSize > inv.stackSize)
			return false;

		return true;
	}

	private void finishWork() {
		progress = 0;
		progressMax = 0;

		ResearchTechnology tech = Femtocraft.researchManager
				.getTechnology(researchingTech);
		if (tech == null) {
			researchingTech = null;
			return;
		}

		Item techItem = null;
		switch (tech.level) {
		case MACRO:
		case MICRO:
			techItem = Femtocraft.itemMicroTechnology;
			break;
		case NANO:
			techItem = Femtocraft.itemNanoTechnology;
			break;
		case DIMENSIONAL:
		case TEMPORAL:
		case FEMTO:
			techItem = Femtocraft.itemFemtoTechnology;
			break;
		}
		ItemStack techstack = new ItemStack(techItem, 1);
		((ITechnologyCarrier) techItem).setTechnology(techstack,
				researchingTech);
		researchingTech = null;
		inventory[inventory.length - 1] = techstack;
		onInventoryChanged();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		checkForTechnology();
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
	}

	@Override
	public void onDataPacket(INetworkManager net, Packet132TileEntityData pkt) {
		super.onDataPacket(net, pkt);
	}

	@Override
	public void saveToDescriptionCompound(NBTTagCompound compound) {
		super.saveToDescriptionCompound(compound);
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
