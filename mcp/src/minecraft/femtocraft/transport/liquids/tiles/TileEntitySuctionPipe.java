package femtocraft.transport.liquids.tiles;

import femtocraft.api.ISuctionPipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

import java.util.Arrays;

public class TileEntitySuctionPipe extends TileEntity implements ISuctionPipe {
	private FluidTank tank;
	public boolean[] tankconnections;
	public boolean[] pipeconnections;
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
	TileEntitySuctionPipe() {
		tank = new FluidTank(2000);
		neighborCapacity = new int[6];
		Arrays.fill(neighborCapacity, 0);
		Arrays.fill(tankconnections, false);
		Arrays.fill(pipeconnections, false);
		output = true;
		pressure = 0;
	}

	/**
	 * 
	 * @return True if this is an 'output' pipe. This means it will
	 *         automatically attempt to output liquid into neighboring
	 *         IFluidHandlers. If it is an input pipe, it will attempt to
	 *         automatically pull liquid from neighboring IFluidHandlers. In
	 *         either case, it will distribute liquids to neighboring
	 *         ISuctionPipe implementors that have less pressure.
	 */
	public boolean isOutput() {
		return output;
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
	 * @see net.minecraft.tileentity.tiles#updateEntity()
	 */
	@Override
	public void updateEntity() {
		super.updateEntity();

		if (this.worldObj.isRemote)
			return;

		int[] pressures = new int[6];
		Arrays.fill(pressures, 0);

		IFluidHandler[] neighbors = new IFluidHandler[6];
		Arrays.fill(neighbors, null);

		checkConnections(neighbors);
		calculatePressure(pressures, neighbors);
		distributeLiquid(pressures, neighbors);
		if (!output)
			requestLiquid(pressures, neighbors);
	}

	private void checkConnections(IFluidHandler[] neighbors) {
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
					neighbors[i] = (IFluidHandler) checkTile;
				} else if (checkTile instanceof IFluidHandler) {
					tankconnections[i] = true;
					neighbors[i] = (IFluidHandler) checkTile;
				}
			}
		}
	}

	private void calculatePressure(int[] pressures, IFluidHandler[] neighbors) {
		pressure = 0;
		int totalPressure = 0;
		int pipeCount = 0;
		for (int i = 0; i < 6; ++i) {
			if (neighbors[i] == null)
				continue;

			// If connected to a tank
			// If outputting, negative pressure equal to remaining space
			// If inputting, positive pressure equal to amount of fluid in the
			// tank
			if (tankconnections[i]) {
				FluidTankInfo[] infoArray = neighbors[i]
						.getTankInfo(ForgeDirection.getOrientation(i)
								.getOpposite());
				++pipeCount;

				FluidTankInfo info = chooseTank(infoArray);
				if (info == null)
					continue;
				pressures[i] = getTankPressure(info, output);
				totalPressure += pressures[i];
			}

			// Otherwise, ask pipe for its pressure
			if (pipeconnections[i]) {
				++pipeCount;
				pressures[i] = ((ISuctionPipe) neighbors[i]).getPressure();
				totalPressure += pressures[i];
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

	private void distributeLiquid(int[] pressures, IFluidHandler[] neighbors) {
		int distributeCount = 0;
		int ratioMax = 0;
		int amountToRemove = 0;

		if (tank.getFluid() == null)
			return;

		// Sum pressure differences for tanks with less pressure than us
		for (int i = 0; i < 6; ++i) {
			if (neighbors[i] == null)
				continue;

			ForgeDirection dir = ForgeDirection.getOrientation(i);

			if (tankconnections[i]
					&& pressures[i] < pressure
					&& neighbors[i].canFill(dir.getOpposite(), tank.getFluid()
							.getFluid())) {
				ratioMax += Math.abs(pressures[i] - pressure);
			}
			if (pipeconnections[i]
					&& pressures[i] < pressure
					&& neighbors[i].canFill(dir.getOpposite(), tank.getFluid()
							.getFluid())) {
				ratioMax += Math.abs(pressures[i] - pressure);
			}
		}

		for (int i = 0; i < 6; ++i) {
			if (neighbors[i] == null)
				continue;

			ForgeDirection dir = ForgeDirection.getOrientation(i);

			int rationedAmount = (int) (tank.getFluidAmount() * (((float) Math
					.abs(pressures[i] - pressure)) / ((float) ratioMax)));
			amountToRemove += neighbors[i].fill(dir.getOpposite(),
					new FluidStack(tank.getFluid().getFluid(), rationedAmount),
					true);
		}

		tank.drain(amountToRemove, true);
	}

	private void requestLiquid(int[] pressures, IFluidHandler[] neighbors) {
		int ratioMax = 0;
		int amountToAdd = 0;
		int space = tank.getCapacity() - tank.getFluidAmount();

		for (int i = 0; i < 6; ++i) {
			if (tankconnections[i]
					&& pressure < pressures[i]
					&& (tank.getFluid() == null || neighbors[i].canDrain(
							ForgeDirection.getOrientation(i).getOpposite(),
							tank.getFluid().getFluid()))) {
				ratioMax += pressures[i] - pressure;
			}
		}

		for (int i = 0; i < 6; ++i) {
			if (!(tankconnections[i] && (tank.getFluid() == null || neighbors[i]
					.canDrain(ForgeDirection.getOrientation(i).getOpposite(),
							tank.getFluid().getFluid()))))
				continue;
			FluidTankInfo[] infoArray = neighbors[i].getTankInfo(ForgeDirection
					.getOrientation(i).getOpposite());

			FluidTankInfo info = chooseTank(infoArray);
			if (info == null)
				continue;

			int rationedAmount = (int) (space * (((float) info.fluid.amount) / ((float) ratioMax)));
			tank.fill(neighbors[i].drain(ForgeDirection.getOrientation(i)
					.getOpposite(), rationedAmount, true), true);
		}
	}

	FluidTankInfo chooseTank(FluidTankInfo[] infoArray) {
		if (infoArray.length == 0)
			return null;

		for (FluidTankInfo info : infoArray) {
			if (tank.getFluid() == null)
				return info;

			if (info.fluid.getFluid() == tank.getFluid().getFluid()) {
				return info;
			}
		}

		return infoArray[0];
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		tank.readFromNBT(par1nbtTagCompound);
		output = par1nbtTagCompound.getBoolean("output");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		tank.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setBoolean("output", output);
	}

	@Override
	public int getPressure() {
		return pressure;
	}
}
