package femtocraft.transport.liquids;

import java.util.Arrays;

import femtocraft.api.IFemtopowerContainer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.liquids.ILiquidTank;
import net.minecraftforge.liquids.ITankContainer;
import net.minecraftforge.liquids.LiquidStack;
import net.minecraftforge.liquids.LiquidTank;

public class SuctionTubeTile extends TileEntity implements ITankContainer {
	private LiquidTank tank;
	private int[] neighborPressure;
	
	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#fill(net.minecraftforge.common.ForgeDirection, net.minecraftforge.liquids.LiquidStack, boolean)
	 */
	SuctionTubeTile() {
		tank = new LiquidTank(null, 200, this);
		int[] pressure = new int[6];
		Arrays.fill(pressure, 0);
	}
	
	@Override
	public int fill(ForgeDirection from, LiquidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#fill(int, net.minecraftforge.liquids.LiquidStack, boolean)
	 */
	@Override
	public int fill(int tankIndex, LiquidStack resource, boolean doFill) {
		return tank.fill(resource, doFill);
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#drain(net.minecraftforge.common.ForgeDirection, int, boolean)
	 */
	@Override
	public LiquidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#drain(int, int, boolean)
	 */
	@Override
	public LiquidStack drain(int tankIndex, int maxDrain, boolean doDrain) {
		return tank.drain(maxDrain, doDrain);
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#getTanks(net.minecraftforge.common.ForgeDirection)
	 */
	@Override
	public ILiquidTank[] getTanks(ForgeDirection direction) {
		return new ILiquidTank[]{tank};
	}

	/* (non-Javadoc)
	 * @see net.minecraftforge.liquids.ITankContainer#getTank(net.minecraftforge.common.ForgeDirection, net.minecraftforge.liquids.LiquidStack)
	 */
	@Override
	public ILiquidTank getTank(ForgeDirection direction, LiquidStack type) {
		// TODO Auto-generated method stub
		return tank;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#readFromNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.readFromNBT(par1nbtTagCompound);
		tank.getLiquid().writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setInteger("pressure", tank.getTankPressure());
		//par1nbtTagCompound.setIntArray("neighborPressure", neighborPressure);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#writeToNBT(net.minecraft.nbt.NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		// TODO Auto-generated method stub
		super.writeToNBT(par1nbtTagCompound);
		tank.setLiquid(LiquidStack.loadLiquidStackFromNBT(par1nbtTagCompound));
		tank.setTankPressure(par1nbtTagCompound.getInteger("pressure"));
		//neighborPressure = par1nbtTagCompound.getIntArray("neighborPressure");
	}

	/* (non-Javadoc)
	 * @see net.minecraft.tileentity.TileEntity#updateEntity()
	 */
	@Override
	public void updateEntity() {
		// TODO Auto-generated method stub
		super.updateEntity();
	
		equalizePressure();
		
		distributeLiquid();
	}
	
	private void equalizePressure() {
		int tankCount = 0;
		int pressuretotal = 0;
		
		for(int i=0; i < 6; i++) {
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			
			int locx = this.xCoord + dir.offsetX;
			int locy = this.yCoord + dir.offsetY;
			int locz = this.zCoord + dir.offsetZ;
			
			TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz);
	 		   
	 		if(checkTile != null && (checkTile instanceof ITankContainer)) {
	 			ITankContainer tank = (ITankContainer)checkTile;
	 			tankCount++;
	 			neighborPressure[i] = tank.getTank(dir.getOpposite(), this.tank.getLiquid()).getTankPressure();
	 			pressuretotal += neighborPressure[i];
	 		}
		}

		this.tank.setTankPressure(pressuretotal/tankCount);
	}
	
	private void distributeLiquid() {
		int distributeCount = 0;
		int ratioMax = 0;
		float[] ratio = new float[6];
		
		for(int i=0; i < 6; i++) {
			ratio[i] = this.tank.getTankPressure() - neighborPressure[i];
			ratioMax += ratio[i];
		}
		
		for(int i=0; i < 6; i++) {
			if(ratio[i] <=0) ratio[i] = 0;
			else ratio[i] = ratio[i]/(float)ratioMax;
			
			ForgeDirection dir = ForgeDirection.getOrientation(i);
			
			int locx = this.xCoord + dir.offsetX;
			int locy = this.yCoord + dir.offsetY;
			int locz = this.zCoord + dir.offsetZ;
			
			ITankContainer tankTile = (ITankContainer)this.worldObj.getBlockTileEntity(locx, locy, locz);
	 		tankTile.getTank(dir.getOpposite(), this.tank.getLiquid()).fill(this.tank.drain((int)((float)this.tank.getLiquid().amount * ratio[i]), true), true);
		}
	}
}
