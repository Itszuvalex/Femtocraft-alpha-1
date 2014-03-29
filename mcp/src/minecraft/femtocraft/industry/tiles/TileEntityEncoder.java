package femtocraft.industry.tiles;

import java.util.Arrays;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import femtocraft.Femtocraft;
import femtocraft.api.IAssemblerSchematic;
import femtocraft.managers.assembler.AssemblerRecipe;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.BaseInventory;
import femtocraft.utils.FemtocraftDataUtils.Saveable;

public class TileEntityEncoder extends TileEntityBaseEntityIndustry implements
		IInventory, IFluidHandler {
	@Saveable
	BaseInventory inventory;
	@Saveable
	FluidTank massTank;
	public static int powerToEncode = 100;
	public static int timeToEncode = 200;
	@Saveable
	public int timeWorked = 0;
	@Saveable
	private AssemblerRecipe encodingRecipe;
	@Saveable
	private ItemStack encodingSchematic;

	public TileEntityEncoder() {
		inventory = new BaseInventory(12);

		massTank = new FluidTank(1000);

		setTechLevel(EnumTechLevel.MICRO);
		setMaxStorage(1200);
	}

	private AssemblerRecipe getRecipe() {
		return Femtocraft.recipeManager.assemblyRecipes.getRecipe(Arrays
				.copyOfRange(inventory.getInventory(), 0, 9));
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
		AssemblerRecipe recipe = getRecipe();
		// Do inventory directly to avoid recursive onInventoryChanged() calls

		inventory.setInventorySlotContents(9, recipe == null ? null
				: recipe.output.copy());

		super.onInventoryChanged();

	}

	@Override
	public int getSizeInventory() {
		return inventory.getSizeInventory();
	}

	@Override
	public ItemStack getStackInSlot(int i) {
		return inventory.getStackInSlot(i);
	}

	@Override
	public ItemStack decrStackSize(int i, int j) {
		ItemStack ret = inventory.decrStackSize(i, j);
		this.onInventoryChanged();
		return ret;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i) {
		return i == 9 ? null : inventory.getStackInSlotOnClosing(i);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) {
		inventory.setInventorySlotContents(i, itemstack);
		this.onInventoryChanged();
	}

	@Override
	public String getInvName() {
		return Femtocraft.ID.toLowerCase() + "." + "InventoryEncoder";
	}

	@Override
	public boolean isInvNameLocalized() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return inventory.getInventoryStackLimit();
	}

	@Override
	public void openChest() {
		inventory.openChest();
	}

	@Override
	public void closeChest() {
		inventory.closeChest();
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {
		switch (i) {
		case 9:
			return false;
		case 10:
			return itemstack.getItem() instanceof IAssemblerSchematic;
		}
		return inventory.isItemValidForSlot(i, itemstack);
	}

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		if (resource.fluidID != Femtocraft.mass.getID())
			return 0;
		return massTank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		return massTank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return massTank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { massTank.getInfo() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.industry.tiles.TileEntityBaseEntityIndustry#isWorking()
	 */
	@Override
	public boolean isWorking() {
		return encodingRecipe != null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#canStartWork()
	 */
	@Override
	protected boolean canStartWork() {
		AssemblerRecipe recipe = getRecipe();
		return recipe != null
				&& getStackInSlot(11) == null
				&& getStackInSlot(10) != null
				&& getCurrentPower() >= powerToEncode
				&& massTank.getFluidAmount() >= ((IAssemblerSchematic) getStackInSlot(
						10).getItem()).massRequired(recipe)
				&& getStackInSlot(10).getItem() instanceof IAssemblerSchematic;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.industry.tiles.TileEntityBaseEntityIndustry#startWork()
	 */
	@Override
	protected void startWork() {
		encodingSchematic = decrStackSize(10, 1);
		encodingRecipe = getRecipe();
		timeWorked = 0;
		consume(powerToEncode);
		massTank.drain(((IAssemblerSchematic) encodingSchematic.getItem())
				.massRequired(encodingRecipe), true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#continueWork()
	 */
	@Override
	protected void continueWork() {
		++timeWorked;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.industry.tiles.TileEntityBaseEntityIndustry#canFinishWork()
	 */
	@Override
	protected boolean canFinishWork() {
		return timeWorked >= timeToEncode;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.industry.tiles.TileEntityBaseEntityIndustry#finishWork()
	 */
	@Override
	protected void finishWork() {
		timeWorked = 0;
		((IAssemblerSchematic) encodingSchematic.getItem()).setRecipe(
				encodingSchematic, encodingRecipe);
		setInventorySlotContents(11, encodingSchematic);
		encodingSchematic = null;
		encodingRecipe = null;
	}

	public int getProgressScaled(int i) {
		return (timeWorked * i) / timeToEncode;
	}

	public int getMassAmount() {
		return massTank.getFluidAmount();
	}

	public void setFluidAmount(int amount) {
		if (massTank.getFluid() != null) {
			massTank.setFluid(new FluidStack(massTank.getFluid().fluidID,
					amount));
		} else {
			massTank.setFluid(new FluidStack(Femtocraft.mass, amount));
		}
	}

	public void clearFluid() {
		massTank.setFluid(null);
	}

	public int getMassCapacity() {
		return massTank.getCapacity();
	}
}