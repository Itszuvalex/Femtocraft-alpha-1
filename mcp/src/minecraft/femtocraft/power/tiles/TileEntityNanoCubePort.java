package femtocraft.power.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import femtocraft.api.IInterfaceDevice;
import femtocraft.api.IPowerBlockContainer;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoCubePort extends TileEntityPowerBase implements
		IMultiBlockComponent {
	private static int storage = 500000;
	private MultiBlockInfo info;
	public boolean output;

	public TileEntityNanoCubePort() {
		super();
		info = new MultiBlockInfo();
		setMaxStorage(storage);
		setTechLevel(EnumTechLevel.NANO);
		output = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#canCharge(net.minecraftforge
	 * .common.ForgeDirection)
	 */
	@Override
	public boolean canCharge(ForgeDirection from) {
		if (!info.isValidMultiBlock() || output)
			return false;
		return super.canCharge(from);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#canAcceptPowerOfLevel(femtocraft
	 * .managers.research.EnumTechLevel,
	 * net.minecraftforge.common.ForgeDirection)
	 */
	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level,
			ForgeDirection from) {
		if (!info.isValidMultiBlock())
			return false;
		return super.canAcceptPowerOfLevel(level, from);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#canConnect(net.minecraftforge
	 * .common.ForgeDirection)
	 */
	@Override
	public boolean canConnect(ForgeDirection from) {
		if (!info.isValidMultiBlock())
			return false;
		return super.canConnect(from);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.power.tiles.TileEntityPowerBase#getCurrentPower()
	 */
	@Override
	public int getCurrentPower() {
		if (info.isValidMultiBlock()) {
			if (isController())
				return super.getCurrentPower();

			IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
					.getBlockTileEntity(info.x(), info.y(), info.z());
			if (fc != null) {
				return fc.getCurrentPower();
			}
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.power.tiles.TileEntityPowerBase#getMaxPower()
	 */
	@Override
	public int getMaxPower() {
		if (info.isValidMultiBlock()) {
			if (isController())
				return super.getMaxPower();

			IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
					.getBlockTileEntity(info.x(), info.y(), info.z());
			if (fc != null) {
				return fc.getMaxPower();
			}
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.power.tiles.TileEntityPowerBase#getFillPercentage()
	 */
	@Override
	public float getFillPercentage() {
		if (info.isValidMultiBlock()) {
			if (isController())
				return super.getFillPercentage();

			IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
					.getBlockTileEntity(info.x(), info.y(), info.z());
			if (fc != null) {
				return fc.getFillPercentage();
			}
		}
		return super.getFillPercentage();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#getFillPercentageForCharging
	 * (net.minecraftforge.common.ForgeDirection)
	 */
	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		if (info.isValidMultiBlock()) {
			return output ? 1.f : 0.f;
		}
		return 1.f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#getFillPercentageForOutput
	 * (net.minecraftforge.common.ForgeDirection)
	 */
	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		if (info.isValidMultiBlock()) {
			return output ? 1.f : 0.f;
		}
		return 0.f;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#charge(net.minecraftforge.
	 * common.ForgeDirection, int)
	 */
	@Override
	public int charge(ForgeDirection from, int amount) {
		if (info.isValidMultiBlock() && !output) {
			if (isController())
				return super.charge(from, amount);

			IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
					.getBlockTileEntity(info.x(), info.y(), info.z());
			if (fc != null) {
				return fc.charge(from, amount);
			}
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see femtocraft.power.tiles.TileEntityPowerBase#consume(int)
	 */
	@Override
	public boolean consume(int amount) {
		if (info.isValidMultiBlock() && output) {
			if (isController())
				return super.consume(amount);

			IPowerBlockContainer fc = (IPowerBlockContainer) worldObj
					.getBlockTileEntity(info.x(), info.y(), info.z());
			if (fc != null) {
				return fc.consume(amount);
			}
		}
		return false;
	}

	private boolean isController() {
		return info.isValidMultiBlock()
				&& ((info.x() == xCoord) && (info.y() == yCoord) && (info.z() == zCoord));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.core.tiles.TileEntityBase#onSideActivate(net.minecraft.entity
	 * .player.EntityPlayer, int)
	 */
	@Override
	public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
		if (isUseableByPlayer(par5EntityPlayer) && info.isValidMultiBlock()) {
			ItemStack item = par5EntityPlayer.getCurrentEquippedItem();
			if (item != null
					&& item.getItem() instanceof IInterfaceDevice
					&& ((IInterfaceDevice) item.getItem()).getInterfaceLevel().tier >= EnumTechLevel.NANO.tier) {
				output = !output;
				worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
				return true;
			}
		}
		return super.onSideActivate(par5EntityPlayer, side);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#readFromNBT(net.minecraft.
	 * nbt.NBTTagCompound)
	 */
	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		info.loadFromNBT(par1nbtTagCompound.getCompoundTag("info"));
		output = par1nbtTagCompound.getBoolean("output");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.power.tiles.TileEntityPowerBase#writeToNBT(net.minecraft.nbt
	 * .NBTTagCompound)
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);

		NBTTagCompound infoC = new NBTTagCompound();
		info.saveToNBT(infoC);
		par1nbtTagCompound.setTag("info", infoC);
		par1nbtTagCompound.setBoolean("output", output);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.core.tiles.TileEntityBase#handleDescriptionNBT(net.minecraft
	 * .nbt.NBTTagCompound)
	 */
	@Override
	public void handleDescriptionNBT(NBTTagCompound compound) {
		super.handleDescriptionNBT(compound);
		info.loadFromNBT(compound.getCompoundTag("info"));
		output = compound.getBoolean("output");

		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * femtocraft.core.tiles.TileEntityBase#saveToDescriptionCompound(net.minecraft
	 * .nbt.NBTTagCompound)
	 */
	@Override
	public void saveToDescriptionCompound(NBTTagCompound compound) {
		super.saveToDescriptionCompound(compound);
		NBTTagCompound infoC = new NBTTagCompound();
		info.saveToNBT(infoC);
		compound.setTag("info", infoC);
		compound.setBoolean("output", output);
	}

	@Override
	public boolean hasGUI() {
		return info.isValidMultiBlock();
	}

	@Override
	public boolean isValidMultiBlock() {
		return info.isValidMultiBlock();
	}

	@Override
	public boolean formMultiBlock(World world, int x, int y, int z) {
		boolean result = info.formMultiBlock(world, x, y, z);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
				worldObj.getBlockId(xCoord, yCoord, zCoord));
		return result;
	}

	@Override
	public boolean breakMultiBlock(World world, int x, int y, int z) {
		boolean result = info.breakMultiBlock(world, x, y, z);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord,
				worldObj.getBlockId(xCoord, yCoord, zCoord));
		return result;
	}

	@Override
	public MultiBlockInfo getInfo() {
		return info;
	}
}
