package femtocraft.transport.liquids;

import femtocraft.api.ISuctionPipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Arrays;

public class SuctionPipeTile extends TileEntity implements ISuctionPipe {
	private FluidTank tank;
	public boolean[] tankconnections;
	private boolean[] pipeconnections;
	private int[] neighborCapacity;
	private boolean output;
	private int pressure;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * net.minecraftforge.liquids.ITankContainer#fill(net.minecraftforge.common
	 * .ForgeDirection, net.minecraftforge.liquids.LiquidStack, boolean)
	 */
	SuctionPipeTile() {
		tank = new FluidTank(2000);
		neighborCapacity = new int[6];
		Arrays.fill(neighborCapacity, 0);
		Arrays.fill(tankconnections, false);
		Arrays.fill(pipeconnections, false);
		output = true;
		pressure = 0;
	}

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
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		return new FluidTankInfo[] { tank.getInfo() };
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this.worldObj.isRemote)
			return;

		checkConnections();
		calculatePressure();
		distributeLiquid();
	}

	private void checkConnections() {
		Arrays.fill(neighborCapacity, 0);
		Arrays.fill(tankconnections, false);
		Arrays.fill(pipeconnections, false);

		for (int i = 0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);

			int locx = this.xCoord + dir.offsetX;
			int locy = this.yCoord + dir.offsetY;
			int locz = this.zCoord + dir.offsetZ;

			TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy,
					locz);

			if (checkTile != null) {
				if (checkTile instanceof ISuctionPipe) {
					pipeconnections[i] = true;
				} else if (checkTile instanceof IFluidHandler) {
					tankconnections[i] = true;

				}
			}
		}
	}

	private void calculatePressure() {
		pressure = 0;
		int totalPressure = 0;
		int pipeCount = 0;
		for (int i = 0; i < 6; ++i) {
			// If connected to a tank
			// If outputting, negative pressure equal to remaining space
			// If inputting, positive pressure equal to amount of fluid in the
			// tank
			if (tankconnections[i]) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				IFluidHandler tank = (IFluidHandler) worldObj
						.getBlockTileEntity(xCoord + dir.offsetX, yCoord
								+ dir.offsetY, zCoord + dir.offsetZ);
				FluidTankInfo[] info = tank.getTankInfo(dir.getOpposite());
				++pipeCount;

				// TODO: smarter tank selection
				totalPressure += getTankPressure(info[0], output);
			}

			// Otherwise, ask pipe for its pressure
			if (pipeconnections[i]) {
				ForgeDirection dir = ForgeDirection.getOrientation(i);
				ISuctionPipe pipe = (ISuctionPipe) worldObj.getBlockTileEntity(
						xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord
								+ dir.offsetZ);

				++pipeCount;
				totalPressure += pipe.getPressure();
			}
		}

		pressure = totalPressure / pipeCount;
	}

	/**
	 * 
	 * @param tank
	 * @param output
	 * @return Negative if outputting into tank, equal to space remaining in
	 *         tank. If input, positive pressure equal to amount of fluid
	 *         remaining in tank.
	 */
	private int getTankPressure(FluidTankInfo tank, boolean output) {
		return output ? -(tank.capacity - tank.fluid.amount)
				: tank.fluid.amount;
	}

	private void distributeLiquid() {
		int distributeCount = 0;
		int ratioMax = 0;
		int amountToRemove = 0;
		int[] ratio = new int[6];

		// for(int i=0; i < 6; i++) {
		// ratio[i] = this.tank.getFluidAmount() - neighborPressure[i];
		// ratioMax += ratio[i];
		// }
		//
		// for(int i=0; i < 6; i++) {
		// if(ratio[i] <=0) {
		// ratio[i] = 0;
		// continue;
		// }
		//
		// ForgeDirection dir = ForgeDirection.getOrientation(i);
		//
		// int locx = this.xCoord + dir.offsetX;
		// int locy = this.yCoord + dir.offsetY;
		// int locz = this.zCoord + dir.offsetZ;
		//
		// IFluidHandler tankTile =
		// (IFluidHandler)this.worldObj.getBlockTileEntity(locx, locy, locz);
		//
		// FluidStack fillStack = this.tank.getFluid();
		// fillStack.amount = (fillStack.amount * ratio[i])/ratioMax;
		//
		// if(tankTile != null)
		// amountToRemove += tankTile.fill(dir, fillStack, true);
		// }
		//
		this.tank.getFluid().amount -= amountToRemove;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readFromNBT(par1nbtTagCompound);
		tank.readFromNBT(par1nbtTagCompound);
		output = par1nbtTagCompound.getBoolean("output");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeToNBT(par1nbtTagCompound);
		tank.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setBoolean("output", output);
	}

	@Override
	public int getPressure() {
		return pressure;
	}
}
