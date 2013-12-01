package femtocraft.industry.TileEntity;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Arrays;
import java.util.List;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.Femtocraft;
import femtocraft.FemtocraftUtils;
import femtocraft.render.Point;

public class VacuumTubeTile extends TileEntity {
	//hasItem array for client-side rendering only
	//Server will update this array and this alone to save bytes
	//Player has no need to know WHAT is in the pipes, anyways
	public static final String packetChannel = Femtocraft.ID + ".VTube";
	
	public boolean[] hasItem = new boolean[4];
	
	private ItemStack[] items = new ItemStack[4];

	ForgeDirection inputDir = ForgeDirection.NORTH;
	ForgeDirection outputDir = ForgeDirection.SOUTH;
	
	private ISidedInventory inputSidedInv = null;
	private ISidedInventory outputSidedInv = null;
	private IInventory inputInv = null;
	private IInventory outputInv = null;
	private VacuumTubeTile inputTube = null;
	private VacuumTubeTile outputTube = null;
	
	public ItemStack queuedItem = null;
	
	private int lastOutputOrientation = 0;
	private int lastInputOrientation = 0;
	
	
	public VacuumTubeTile() {
		super();
		Arrays.fill(hasItem, false);
		Arrays.fill(items, null);
	}
	
	public boolean isEndpoint()
	{
		return (missingInput() || missingOutput());
	}
	
	public boolean missingInput()
	{
		return (inputSidedInv == null) && (inputTube == null) && (inputInv == null);
	}
	
	public boolean missingOutput()
	{
		return (outputSidedInv == null) && (outputTube == null) && (outputInv == null);
	}
	
	public boolean isOverflowing()
	{
		return outputTube != null &&
				outputTube.queuedItem != null &&
				hasItem[0] && hasItem[1] && hasItem[2] && hasItem[3] && 
				queuedItem != null;
	}
	
	public ForgeDirection getInputDir()
	{
		return inputDir;
	}
	
	public ForgeDirection getOutputDir()
	{
		return outputDir;
	}
	
	public void onBlockBreak()
	{
		if(inputTube != null) removeReceiveFromTube(inputTube);
		if(outputTube != null) removeSendToTube(outputTube);
		
		for(int i = 0; i < items.length; i++)
		{
			ejectItem(i);
		}
		
		ejectItemStack(queuedItem);
	}
	
	public void searchForMissingConnection()
	{
		if(missingInput()) searchForInput();
		if(missingOutput()) searchForOutput();
	}
	
	public void searchForFullConnections()
	{
		searchForInput();
		searchForOutput();
	}
	
	public boolean searchForInput()
	{
		if(lastInputOrientation >= 6) lastInputOrientation = 0;
		int begin = lastInputOrientation;
		
		do
		{
			if(lastInputOrientation >= 6) lastInputOrientation = 0;
			if(checkOutput(lastInputOrientation++)) return true;
			
		}while(begin != lastInputOrientation);
		
		return false;
	}
	
	public boolean searchForOutput()
	{
		if(lastOutputOrientation >= 6) lastOutputOrientation = 0;
		int begin = lastOutputOrientation;
		
		do
		{
			if(lastOutputOrientation >= 6) lastOutputOrientation = 0;
			if(checkOutput(lastOutputOrientation++)) return true;
			
		}while(begin != lastOutputOrientation);
		
		return false;
	}
	
	public boolean searchForConnection()
	{
		int beginInputOrient = lastInputOrientation;
		int beginOutputOrient = lastOutputOrientation;
		
		//Do a full loop around.  If no other settings are available, stop once there.
		do
		{
			cycleSearch();
			
			boolean input = checkInput(lastInputOrientation);
			boolean output = checkOutput(lastOutputOrientation);
			
			if(input && output) return true;
			
		} while (beginInputOrient != lastInputOrientation && beginOutputOrient != lastOutputOrientation);
		
		return false;
	}

	private boolean checkInput(int dir) {
		ForgeDirection test = ForgeDirection.getOrientation(dir);
		if(worldObj == null) return false;
		if(test == outputDir) return false;
		TileEntity tile = worldObj.getBlockTileEntity(xCoord + test.offsetX, yCoord + test.offsetY, zCoord + test.offsetZ);
		if(tile == null) return false;
		if(tile == this) return false;
		boolean out = missingOutput();
		if(tile instanceof ISidedInventory)
		{
			inputSidedInv = (ISidedInventory)tile;
			inputInv = null;
			inputTube = null;
			inputDir = test;
			if(out) outputDir = test.getOpposite();
			return true;
		}
		
		if(tile instanceof VacuumTubeTile)
		{
			VacuumTubeTile tube = (VacuumTubeTile)tile;
			if(!tube.missingOutput()) return false;
			
			inputTube = tube;
			inputDir = test;
			inputSidedInv = null;
			inputInv = null;
			setupReceiveFromTube(inputTube, test);
			if(out) outputDir = test.getOpposite();
			return true;
		}
		
		if(tile instanceof IInventory)
		{
			inputTube = null;
			inputDir = test;
			inputSidedInv = null;
			inputInv = (IInventory)tile;
			if(out) outputDir = test.getOpposite();
			return true;
		}
		
		return false;
	}

	private boolean checkOutput(int dir) {
		ForgeDirection test = ForgeDirection.getOrientation(dir);
		if(worldObj == null) return false;
		if(test == inputDir) return false;
		TileEntity tile = worldObj.getBlockTileEntity(xCoord + test.offsetX, yCoord + test.offsetY, zCoord + test.offsetZ);
		if(tile == null) return false;
		if(tile == this) return false;
		boolean in = missingInput();
		if(tile instanceof ISidedInventory)
		{
			outputSidedInv = (ISidedInventory)tile;
			outputInv = null;
			outputTube = null;
			outputDir = test;
			if(in) inputDir = test.getOpposite();
			return true;
		}
		
		if(tile instanceof VacuumTubeTile)
		{
			VacuumTubeTile tube = (VacuumTubeTile)tile;
			if(!tube.missingInput()) return false;
			
			outputTube = tube;
			outputDir = test;
			outputSidedInv = null;
			outputInv = null;
			setupSendToTube(outputTube, test);
			if(in) inputDir = test.getOpposite();
			return true;
		}
		
		if(tile instanceof IInventory)
		{
			outputTube = null;
			outputDir = test;
			outputSidedInv = null;
			outputInv = (IInventory)tile;
			if(in) inputDir = test.getOpposite();
			return true;
		}
		
		return false;
	}
	
	private void cycleSearch()
	{
		lastOutputOrientation+=1;
		removeLastOutput();
		if(lastOutputOrientation >= 6)
		{
			lastOutputOrientation = 0;
			
			lastInputOrientation+=1;
			removeLastInput();
			if(lastInputOrientation >= 6)
			{
				lastInputOrientation = 0;
			}
		}
	}
	
	private void removeLastInput()
	{
		if(inputTube == null) return;
		removeReceiveFromTube(inputTube);
	}
	
	private void removeLastOutput()
	{
		if(outputTube == null) return;
		removeSendToTube(outputTube);
	}
	
	private void setupReceiveFromTube(VacuumTubeTile tube, ForgeDirection dir)
	{
		tube.outputTube = this;
		tube.outputDir = dir.getOpposite();
	}
	
	private void setupSendToTube(VacuumTubeTile tube, ForgeDirection dir)
	{
		tube.inputTube = this;
		tube.inputDir = dir.getOpposite();
	}
	
	private void removeReceiveFromTube(VacuumTubeTile tube)
	{
		if(tube.outputTube != this) return;
		
		tube.outputTube = null;
		tube.outputDir = tube.inputDir.getOpposite();
	}
	
	private void removeSendToTube(VacuumTubeTile tube)
	{
		if(tube.inputTube != this) return;
		
		tube.inputTube = null;
		tube.inputDir = tube.outputDir.getOpposite();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		
		NBTTagList nbttaglist = par1nbtTagCompound.getTagList("Items");
        items = new ItemStack[items.length];
        hasItem = new boolean[hasItem.length];
        Arrays.fill(items, null);
        Arrays.fill(hasItem, false);

        for (int i = 0; i < nbttaglist.tagCount()-1; ++i)
        {
            NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
            byte b0 = nbttagcompound1.getByte("Slot");

            if (b0 >= 0 && b0 < items.length)
            {
                items[b0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
                hasItem[b0] = true;
            }
        }
        
        NBTTagCompound nbttagcompoundsmelt = (NBTTagCompound)nbttaglist.tagAt(nbttaglist.tagCount()-1);
        if(nbttagcompoundsmelt.getBoolean("Queued")) {
        	queuedItem = ItemStack.loadItemStackFromNBT(nbttagcompoundsmelt);
        }
        else {
        	queuedItem = null;
        }
        
        byte connections = par1nbtTagCompound.getByte("Connections");
        parseConnectionMask(connections);
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		
		NBTTagList taglist = new NBTTagList();
		for (int i = 0; i < items.length; ++i)
        {
            if (items[i] != null)
            {
                NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                nbttagcompound1.setByte("Slot", (byte)i);
                this.items[i].writeToNBT(nbttagcompound1);
                taglist.appendTag(nbttagcompound1);
            }
        }
		
		NBTTagCompound queueCompound = new NBTTagCompound();
		boolean queued = queuedItem != null;
		queueCompound.setBoolean("Queued", queued);
		if(queued) queuedItem.writeToNBT(queueCompound);
		taglist.appendTag(queueCompound);
		
		par1nbtTagCompound.setTag("Items", taglist);
		
		par1nbtTagCompound.setByte("Connections", generateConnectionMask());
	}

	@Override
	public void updateEntity() {
		
		if(worldObj.isRemote) return;
		
		if(items[3] != null)
		{
			//If next tube has a slot
			if(outputTube != null)
			{
				if(outputTube.queuedItem == null)
				{
					//enque item, if there is one
					outputTube.queuedItem = items[3].copy();
					items[3] = null;
					hasItem[3] = false;
				}
			}
			else if(outputSidedInv != null)
			{
				int side = FemtocraftUtils.indexOfForgeDirection(outputDir.getOpposite());
				int[] slots = outputSidedInv.getAccessibleSlotsFromSide(side);
				int invMax = outputSidedInv.getInventoryStackLimit();
				for(int i = 0; i < slots.length && items[3] != null; i++)
				{
					if(outputSidedInv.canInsertItem(slots[i], items[3], side))
					{
						if(outputSidedInv.isItemValidForSlot(slots[i], items[3]))
						{
							ItemStack slotStack = outputSidedInv.getStackInSlot(slots[i]);
							
							//If no items in that slot
							if(slotStack == null)
							{
								outputSidedInv.setInventorySlotContents(slots[i], items[3].copy());
								items[3] = null;
								hasItem[3] = false;
								outputSidedInv.onInventoryChanged();
							}
							//Combine items
							else
							{
								//Account for possible mismatch
								if(items[3].itemID != slotStack.itemID) continue;
								if(!items[3].isStackable()) continue;
								
								int itemMax = slotStack.getMaxStackSize();
								int max = invMax > itemMax ? itemMax : invMax;
								int room = max - slotStack.stackSize;
								int amount = room > items[3].stackSize ? items[3].stackSize : room;
								slotStack.stackSize += amount;
								items[3].stackSize -= amount;
								if(items[3].stackSize <= 0)	
								{
									items[3] = null;
									hasItem[3] = false;
								}
								outputSidedInv.onInventoryChanged();
							}
						}
					}
				}
			}
			else if(outputInv != null)
			{
				int size = outputInv.getSizeInventory();
				int invMax = outputInv.getInventoryStackLimit();
				for(int i = 0; i < size && items[3] != null; i++)
				{
					if(outputInv.isItemValidForSlot(i, items[3]))
					{
						ItemStack slotStack = outputInv.getStackInSlot(i);
						
						//If no items in that slot
						if(slotStack == null)
						{
							outputInv.setInventorySlotContents(i, items[3].copy());
							items[3] = null;
							hasItem[3] = false;
							outputInv.onInventoryChanged();
						}
						//Combine items
						else
						{
							//Account for possible mismatch
							if(items[3].itemID != slotStack.itemID) continue;
							if(!items[3].isStackable()) continue;
							
							int itemMax = slotStack.getMaxStackSize();
							int max = invMax > itemMax ? itemMax : invMax;
							int room = max - slotStack.stackSize;
							int amount = room > items[3].stackSize ? items[3].stackSize : room;
							slotStack.stackSize += amount;
							items[3].stackSize -= amount;
							if(items[3].stackSize <= 0)	
							{
								items[3] = null;
								hasItem[3] = false;
							}
							outputInv.onInventoryChanged();
						}
					}
				}
			}
			else
			{
				ejectItem(3);
			}
		}
		
		//Move up rest of items, starting from end
		for(int i = items.length-2; i >= 0; i--)
		{
			//Move up only if room
			if(items[i+1] == null)
			{
				hasItem[i+1] = hasItem[i];
				hasItem[i] = false;
				items[i+1] = items[i];
				items[i] = null;
			}
		}
		
		//Blockage.  We're done
		if(items[0] != null) return;

		//If being loaded by another VacuumTube
		if(queuedItem != null)
		{
			items[0] = queuedItem;
			hasItem[0] = true;
			queuedItem = null;
		}
		//Pull from inventory
		else if (inputSidedInv != null)
		{
			int side = FemtocraftUtils.indexOfForgeDirection(inputDir.getOpposite());
			int[] slots = inputSidedInv.getAccessibleSlotsFromSide(side);
			for(int i = 0; i < slots.length; i++)
			{
				ItemStack stack = inputSidedInv.getStackInSlot(slots[i]);
				if(stack != null)
				{
					if(!inputSidedInv.canExtractItem(slots[i], stack, side)) continue;
					
					items[0] = inputSidedInv.decrStackSize(slots[i], 64);
					hasItem[0] = true;
					inputSidedInv.onInventoryChanged();
					return;
				}
			}
			
		}
		else if(inputInv != null)
		{
			int size = inputInv.getSizeInventory();
			for(int i = 0; i < size; i++)
			{
				ItemStack stack = inputInv.getStackInSlot(i);
				if(stack != null)
				{
					items[0] = inputInv.decrStackSize(i, 64);
					hasItem[0] = true;
					inputInv.onInventoryChanged();
					return;
				}
			}
		}
		//Suck them in, only if room
		else
		{
			float x = xCoord + outputDir.offsetX;
			float y = yCoord + outputDir.offsetY;
			float z = zCoord + outputDir.offsetZ;
			
			List<EntityItem> items = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getAABBPool().getAABB(x,y,z,x+1.f, y+1.f, z+1.f));
			for(EntityItem item : items)
			{
				item.addVelocity((xCoord + .5) - item.posX, (yCoord + .5) - item.posY, (zCoord + .5) - item.posZ);
			}
		}
	}

	private void ejectItem(int slot) {
		ItemStack dropItem = items[slot];
		ejectItemStack(dropItem);
		items[slot] = null;
		hasItem[slot] = false;
	}
	
	private void ejectItemStack(ItemStack dropItem)
	{
		if(dropItem == null) return;
		if(worldObj.isRemote) return;
		
		//Throw the item out into the world!
		EntityItem entityitem = new EntityItem(worldObj, xCoord + .5, yCoord + .5, zCoord+ .5, dropItem.copy());

		entityitem.motionX = outputDir.offsetX;
		entityitem.motionY = outputDir.offsetY;
		entityitem.motionZ = outputDir.offsetZ;
		worldObj.spawnEntityInWorld(entityitem);
	}

	@Override
	public Packet getDescriptionPacket() {
		return generatePacket();
	}
	
	private Packet250CustomPayload generatePacket()
	{
	    ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
	    DataOutputStream outputStream = new DataOutputStream(bos);
	    try {
	        outputStream.writeInt(xCoord);
	        outputStream.writeInt(yCoord);
	        outputStream.writeInt(zCoord);
	        //write the relevant information here... exemple:
	       outputStream.writeByte(generateItemMask());
	       outputStream.writeByte(generateConnectionMask());
	    } catch (Exception ex) {
	        ex.printStackTrace();
	    }
	               
	    Packet250CustomPayload packet = new Packet250CustomPayload();
	    packet.channel = packetChannel;
	    packet.data = bos.toByteArray();
	    packet.length = bos.size();
	    
	    return packet;
	}
	
	public byte generateConnectionMask()
	{
		byte output = 0;
		output += FemtocraftUtils.indexOfForgeDirection(inputDir);
		output += FemtocraftUtils.indexOfForgeDirection(outputDir) << 4;
		return output;
	}
	
	public void parseConnectionMask(byte mask)
	{
		byte temp = mask;
		int input = mask & 15;
		int output = (temp >> 4) & 15;
		inputDir = ForgeDirection.getOrientation(input);
		outputDir = ForgeDirection.getOrientation(output);
		checkInput(input);
		checkOutput(output);
	}
	
	public byte generateItemMask()
	{
		byte output = 0;
		
		for(int i = 0; i < hasItem.length; i++)
		{
			if(hasItem[i])
				output += 1 << i;
		}
		return output;
	}
	
	public void parseIteMMask(byte mask)
	{
		byte temp;

		for(int i = 0; i < hasItem.length; i++)
		{
			temp = mask;
			hasItem[i] = (((temp >> i) & 1) == 1) ? true : false;
		}
	}

}
