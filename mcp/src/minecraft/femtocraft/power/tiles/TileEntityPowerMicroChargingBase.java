package femtocraft.power.tiles;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.api.IChargingBase;
import femtocraft.api.IChargingCoil;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityPowerMicroChargingBase extends TileEntityPowerProducer {
	public int numCoils;
	public float powerPerTick;
	public float storedPowerIncrement;

	public TileEntityPowerMicroChargingBase() {
		powerPerTick = 0;
		setTechLevel(EnumTechLevel.MICRO);
	}

	@Override
	public void updateEntity() {
		super.updateEntity();

		if (!worldObj.isRemote) {
			numCoils = 0;
			powerPerTick = 0;

			Block base = Block.blocksList[worldObj.getBlockId(xCoord, yCoord,
					zCoord)];

			boolean searching = true;
			for (int i = 0; searching
					&& (i < ((IChargingBase) base).maxCoilsSupported(worldObj,
							xCoord, yCoord, zCoord)); ++i) {
				Block block = Block.blocksList[worldObj.getBlockId(xCoord,
						yCoord + i + 1, zCoord)];

				if ((block == null) || (!(block instanceof IChargingCoil))) {
					searching = false;
					continue;
				}

				IChargingCoil coil = (IChargingCoil) block;
				powerPerTick += coil.powerPerTick(worldObj, xCoord, yCoord + i
						+ 1, zCoord);
				numCoils++;
			}

			storedPowerIncrement += powerPerTick;
			while (storedPowerIncrement > 1.0f) {
				storedPowerIncrement -= 1.0f;
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

	@Override
	public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
		if (!isUseableByPlayer(par5EntityPlayer))
			return false;

		ItemStack item = par5EntityPlayer.getHeldItem();
		if (item != null
				&& (item.getItem() instanceof ItemBlock)
				&& Block.blocksList[((ItemBlock) item.getItem()).getBlockID()] instanceof IChargingCoil) {
			return true;
		}

		return super.onSideActivate(par5EntityPlayer, side);
	}

}
