package femtocraft.power.TileEntity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.api.IFemtopowerContainer;
import femtocraft.managers.research.TechLevel;

public class FemtopowerTile extends TileEntity implements IFemtopowerContainer {
	private int currentStorage;
	private int maxStorage;
	private float maxPowerPerTick;
	private float maxSizePackets;
	private float distributionBuffer;
	public boolean[] connections;
	private TechLevel level;
	
	public FemtopowerTile() {
		currentStorage = 0;
		maxStorage = 250;
		maxPowerPerTick = .05f;
		maxSizePackets = .05f;   //Yes this is the same as maxpertick, this breaks if it isn't, for some reason  TODO
		distributionBuffer = .01f;
		connections = new boolean[6];
		Arrays.fill(connections, false);
	}
	
	public void setMaxStorage(int maxStorage_) {
		maxStorage = maxStorage_;
	}
	
	public void setTechLevel(TechLevel level)
	{
		this.level = level;
	}
	
	public boolean isConnected(int i) {
		return connections[i];
	}
	
	public int numConnections() {
		int count = 0;
		for(int i =0; i < 6; ++i) if(connections[i]) ++count;
		return count;
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
	public float getFillPercentageForOutput(ForgeDirection to) {
		return getFillPercentage();
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		if(getFillPercentage()>=1.0f) return false;
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
	public boolean consume(int amount) {
		if(amount > currentStorage) 
			return false;
		
		currentStorage -= amount;
		return true;
	}
	
	@Override
	 public void updateEntity()
    {	
		checkConnections();
		
		//Don't do anything for empty containers
		if(currentStorage <= 0 || this.worldObj.isRemote) {
			return;
		}
		
        boolean[] willCharge = Arrays.copyOf(connections, 6);
		//boolean[] willCharge = new boolean[6];
		//Arrays.fill(willCharge, true);
       int numToFill = 0;
       for(int i = 0; i < 6; ++i) {if(willCharge[i]) ++numToFill;}
       float[] percentFilled = new float[6];
       Arrays.fill(percentFilled, 1.0f);
       int maxSpreadThisTick = (int)(((float)currentStorage) * maxPowerPerTick);
       
       while(maxSpreadThisTick >0 && numToFill > 0) {
		   for(int j = 0; j < 6; ++j) {
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
				   --numToFill;
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
				   
				   if((this.getFillPercentageForOutput(offset) - percentFilled[j]) < distributionBuffer) {
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
		   amountToFill = amountToFill < maxSpreadThisTick ? amountToFill : maxSpreadThisTick;
		   amountToFill = amountToFill < currentStorage ? amountToFill : currentStorage;
		   //Having passed initial check, and due to this now being this block's
		   //update function, can safely assume adjacent blocks remain the same (unless it does simultaneous updates)
		   TileEntity TE = this.worldObj.getBlockTileEntity(locx, locy, locz);
		   if(TE != null && TE instanceof IFemtopowerContainer) 
		   {
			   IFemtopowerContainer container = (IFemtopowerContainer)TE;
			   
			   if(container != null) {
				   int powerconsumed = container.charge(offset.getOpposite(), amountToFill);
				   this.currentStorage -= powerconsumed;
				   maxSpreadThisTick -= powerconsumed;
			   }
		   }
       }
		   
    }
	
	 public void readFromNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.readFromNBT(par1NBTTagCompound);
       currentStorage = par1NBTTagCompound.getInteger("currentStorage");
       maxStorage = par1NBTTagCompound.getInteger("maxStorage");
       
//       for(int i = 0; i < 6; i++) {
//    	  connections[i] = par1NBTTagCompound.getBoolean(String.format("connection%d", i));
//       }
    }

    /**
     * Writes a tile entity to NBT.
     */
    public void writeToNBT(NBTTagCompound par1NBTTagCompound)
    {
       super.writeToNBT(par1NBTTagCompound);
       par1NBTTagCompound.setInteger("currentStorage", currentStorage);
       par1NBTTagCompound.setInteger("maxStorage", maxStorage);

//       for(int i = 0; i < 6; i++) {
//    	   par1NBTTagCompound.setBoolean(String.format("connection%d", i), connections[i]);
//       }
    }
    
    
    public void checkConnections() {
    	boolean changed = false;
    	for(int j = 0; j < 6; ++j) {
    		boolean prev = connections[j];
    	   connections[j] = false;
 		   ForgeDirection offset = ForgeDirection.getOrientation(j);
 		   int locx = this.xCoord + offset.offsetX;
 		   int locy = this.yCoord + offset.offsetY;
 		   int locz = this.zCoord + offset.offsetZ;
 		   
 		   TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy, locz);
 		   
 		   if(checkTile != null && checkTile instanceof IFemtopowerContainer) {
 			   IFemtopowerContainer fc = (IFemtopowerContainer) checkTile;
 			   if(!fc.canConnect(offset.getOpposite())) continue;
 			   
 			   connections[j] = true;
 			   if(prev != connections[j])
 			   {
 				  changed = true;
 			   }
 		   }
 	   	}
    	if(changed) this.worldObj.markBlockForRenderUpdate(this.xCoord, this.yCoord, this.zCoord);
      }

	@Override
	public boolean canAcceptPowerOfLevel(TechLevel level, ForgeDirection from) {
		return level == this.level;
	}

	@Override
	public TechLevel getTechLevel(ForgeDirection to) {
		return level;
	}

	@Override
	public boolean canConnect(ForgeDirection from) {
		return true;
	}
}
