package femtocraft.power.TileEntity;

import net.minecraft.block.Block;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.api.IChargingBase;
import femtocraft.api.IChargingCoil;
import femtocraft.managers.research.TechLevel;

public class MicroChargingBaseTile extends FemtopowerProducer {
	public int numCoils;
	public float powerPerTick;
	public float storedPowerIncrement;

	public MicroChargingBaseTile() {
		powerPerTick = 0;
		setTechLevel(TechLevel.MICRO);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();
		
		if(!worldObj.isRemote)
		{
			numCoils = 0;
			powerPerTick = 0;
	
			Block base = Block.blocksList[worldObj.getBlockId(xCoord, yCoord, zCoord)];
			
			boolean searching = true;
			for(int i = 0; searching && (i < ((IChargingBase)base).maxCoilsSupported(worldObj, xCoord, yCoord, zCoord)); ++i)
			{
				Block block = Block.blocksList[worldObj.getBlockId(xCoord, yCoord + i + 1, zCoord)];
				
				if((block == null) || (!(block instanceof IChargingCoil)))
				{
					searching = false;
					continue;
				}
			
				IChargingCoil coil = (IChargingCoil)block;
				powerPerTick += coil.powerPerTick(worldObj, xCoord, yCoord + i + 1, zCoord);
				numCoils++;
			}
			
			storedPowerIncrement += powerPerTick;
			while(storedPowerIncrement > 1.0f)
			{
				storedPowerIncrement-=1.0f;
				charge(ForgeDirection.UNKNOWN, 1);
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		par1nbtTagCompound.getFloat("powerIncrement");
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setFloat("powerIncrement", storedPowerIncrement);
	}

	@Override
	public boolean canConnect(ForgeDirection from) {
		return !(from == ForgeDirection.UP);
	}

}
