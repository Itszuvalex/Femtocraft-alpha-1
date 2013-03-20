package femtocraft.power.TileEntity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.api.IFemtopowerContainer;

public class FemtopowerTile extends TileEntity implements IFemtopowerContainer {
	private int currentStorage;
	private int maxStorage;
	private float maxPowerPerTick;
	private float maxSizePackets;
	private float distributionBuffer;
	private boolean[] connections;
	
	public FemtopowerTile(int storage) {
		currentStorage = 0;
		maxStorage = storage;
		maxPowerPerTick = .05f;
		maxSizePackets = .05f;   //Yes this is the same as maxpertick, this is for testing purposes only
		distributionBuffer = .01f;
		connections = new boolean[6];
		Arrays.fill(connections, false);
	}

	@Override
	public int getCurrentPower() {
		return currentStorage;
	}

	@Override
	public int getMaxPower() {
		return maxStorage;
	}

	@Override
	public float getFillPercentage() {
		return (float)currentStorage/(float)maxStorage;
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		return getFillPercentage();
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		return true;
	}

	@Override
	public int charge(ForgeDirection from, int amount) {
		int room = maxStorage - currentStorage;
		amount = room < amount ? room : amount;
		currentStorage += amount;
		return amount;
	}
	
	@Override
	 public void updateEntity()
    {
		//Don't do anything for empty containers
		if(currentStorage <= 0 || this.worldObj.isRemote) {
			return;
		}
		
     //  boolean[] willCharge = Arrays.copyOf(connections, 6);
		boolean[] willCharge = new boolean[6];
		Arrays.fill(willCharge, true);
       int numToFill = 6;
       //for(int i = 0; i < 6; i++) {if(willCharge[i]) numToFill++;}
       float[] percentFilled = new float[6];
       Arrays.fill(percentFilled, 1.0f);
    //   int maxSpreadThisTick = (int)((float)currentStorage * maxPowerPerTick);
       
       while(/*maxSpreadThisTick >0 &&*/ numToFill > 0) {
		   for(int j = 0; j < 6; j++) {
			   //Once it won't accept power, nothing will happen this tick,
			   //Inside this update, that could make it accept power again
			   if(!willCharge[j]) {
				   continue;
			   }
			   
			   ForgeDirection offset = ForgeDirection.getOrientation(j);
			   int locx = this.xCoord + offset.offsetX;
			   int locy = this.yCoord + offset.offsetY;
			   int locz = this.zCoord + offset.offsetZ;
			   
	 		   TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz);
	 		   
	 		   if(checkTile == null || ! (checkTile instanceof IFemtopowerContainer)) {
				   willCharge[j] = false;
				   numToFill--;
				   percentFilled[j] = 1.f;
				   continue;
	 		   }
	 			   
			   
			   //Having passed initial check, and due to this now being this block's
			   //update function, can safely assume adjacent blocks remain the same (unless it does simultaneous updates)
			   IFemtopowerContainer container = (IFemtopowerContainer)this.worldObj.getBlockTileEntity(locx, locy, locz);
			   
			   if(container != null && container.canCharge(offset.getOpposite())) {
				   //Check for within buffer range - if so, this pipe will only get less filled from here on out
				   //So it will never attempt to fill that pipe again
				   percentFilled[j] = container.getFillPercentageForCharging(offset.getOpposite());
				   
				   if((this.getFillPercentage() - percentFilled[j]) < distributionBuffer) {
					   willCharge[j] = false;
					   numToFill--;
					   percentFilled[j] = 1.f;
					   continue;
				   }
			   }
			   else {
				   //Update as we fill
				   willCharge[j] = false;
				   numToFill--;
				   percentFilled[j] = 1.f;
				   continue;
			   }
		   }
		   
		   //Find lowest % filled from
		   int lowest = 0;
		   float lowestAmt = 1.f;
		   
		   for(int i = 0; i < 6; i++) {
			   if(willCharge[i] && percentFilled[i] < lowestAmt) {
				   lowestAmt = percentFilled[i];
				   lowest = i;
			   }
		   }
		   
		   ForgeDirection offset = ForgeDirection.getOrientation(lowest);
		   int locx = this.xCoord + offset.offsetX;
		   int locy = this.yCoord + offset.offsetY;
		   int locz = this.zCoord + offset.offsetZ;
		   
		   int amountToFill = (int)((float)currentStorage * maxSizePackets);
		//   amountToFill = amountToFill < maxSpreadThisTick ? amountToFill : maxSpreadThisTick;
		   amountToFill = 10;
		   //Having passed initial check, and due to this now being this block's
		   //update function, can safely assume adjacent blocks remain the same (unless it does simultaneous updates)
		   IFemtopowerContainer container = (IFemtopowerContainer)this.worldObj.getBlockTileEntity(locx, locy, locz);
		   
		   
		   int powerconsumed = 0;
		   if(container != null)
			   powerconsumed = container.charge(offset.getOpposite(), amountToFill);
			   this.currentStorage -= powerconsumed;
			   maxPowerPerTick -= powerconsumed;
       }
		   
    }
	
	 public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.readFromNBT(par1NBTTagCompound);
       this.currentStorage = par1NBTTagCompound.getInteger("currentStorage");
       this.maxStorage = par1NBTTagCompound.getInteger("maxStorage");
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.writeToNBT(par1NBTTagCompound);
       par1NBTTagCompound.setInteger("currentStorage", currentStorage);
       par1NBTTagCompound.setInteger("maxStorage", maxStorage);
    }
    
    
    public void checkConnections() {
    	for(int j = 0; j < 6; j++) {
 		   ForgeDirection offset = ForgeDirection.getOrientation(j);
 		   int locx = this.xCoord + offset.offsetX;
 		   int locy = this.yCoord + offset.offsetY;
 		   int locz = this.zCoord + offset.offsetZ;
 		   
 		   TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz);
 		   
 		   if(checkTile != null && checkTile instanceof IFemtopowerContainer) {
 			   connections[j] = true;
 		   }
 	   }
    }
}
