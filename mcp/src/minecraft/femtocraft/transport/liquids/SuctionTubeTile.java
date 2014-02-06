package femtocraft.transport.liquids;

import java.util.Arrays;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;

public class SuctionTubeTile extends TileEntity implements IFluidHandler {
	private FluidTank tank;
	public boolean[] connections;
	private int[] neighborCapacity;
	private boolean output;
	private int pressure;
	
	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#fill(net.minecraftforge.common.ForgeDirection, net.minecraftforge.liquids.LiquidStack, boolean)
	 */
	SuctionTubeTile() {
		tank = new FluidTank(2000);
		neighborCapacity = new int[6];
		Arrays.fill(neighborCapacity, 0);
		Arrays.fill(connections, false);
		output = false;
	}
	

	@Override
	public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
		// TODO Auto-generated method stub
		return tank.fill(resource, doFill);
	}

	@Override
	public FluidStack drain(ForgeDirection from, FluidStack resource,
			boolean doDrain) {
		// TODO Auto-generated method stub
        if (resource == null || !resource.isFluidEqual(tank.getFluid()))
        {
            return null;
        }
		return tank.drain(resource.amount, doDrain);
	}

	@Override
	public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		// TODO Auto-generated method stub
		return tank.drain(maxDrain, doDrain);
	}

	@Override
	public boolean canFill(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean canDrain(ForgeDirection from, Fluid fluid) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public FluidTankInfo[] getTankInfo(ForgeDirection from) {
		// TODO Auto-generated method stub
		return new FluidTankInfo[]{tank.getInfo()};
	}
	
	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {		
		// TODO Auto-generated method stub
		super.updateEntity();
		
		if(this.worldObj.isRemote) return;

		checkConnections();
		
		distributeLiquid();
	}
	
	private void checkConnections() {
		Arrays.fill(neighborCapacity, 0);
		Arrays.fill(connections, false);
		
//		for(int i=0; i < 6; i++) {
//			ForgeDirection dir = ForgeDirection.getOrientation(i);
//			
//			int locx = this.xCoord + dir.offsetX;
//			int locy = this.yCoord + dir.offsetY;
//			int locz = this.zCoord + dir.offsetZ;
//			
//			TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz);
//	 		   
//	 		if(checkTile != null && (checkTile instanceof IFluidHandler)) {
//	 			connections[i] = true;
//	 			
//	 			IFluidHandler fluidTank = (IFluidHandler)checkTile;
//	 			fluidTank.d
//	 			
//	 			FluidTankInfo[] tanks = fluidTank.getTankInfo(dir.getOpposite());
//	 			
//	 			for(FluidTankInfo info : tanks)
//	 			{
//	 				if
//	 			}
//	 		}
//		}
	}
	
	private void distributeLiquid() {
		int distributeCount = 0;
		int ratioMax = 0;
		int amountToRemove = 0;
		int[] ratio = new int[6];
		
//		for(int i=0; i < 6; i++) {
//			ratio[i] = this.tank.getFluidAmount() - neighborPressure[i];
//			ratioMax += ratio[i];
//		}
//		
//		for(int i=0; i < 6; i++) {
//			if(ratio[i] <=0) {
//				ratio[i] = 0;
//				continue;
//			}
//			
//			ForgeDirection dir = ForgeDirection.getOrientation(i);
//			
//			int locx = this.xCoord + dir.offsetX;
//			int locy = this.yCoord + dir.offsetY;
//			int locz = this.zCoord + dir.offsetZ;
//			
//			IFluidHandler tankTile = (IFluidHandler)this.worldObj.getBlockTileEntity(locx, locy, locz);
//			
//			FluidStack fillStack = this.tank.getFluid();
//			fillStack.amount = (fillStack.amount * ratio[i])/ratioMax;
//			
//			if(tankTile != null)
//				amountToRemove += tankTile.fill(dir, fillStack, true);
//	 	}
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
}
