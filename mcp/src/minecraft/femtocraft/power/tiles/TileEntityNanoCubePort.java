package femtocraft.power.tiles;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoCubePort extends TileEntityPowerBase implements
		IMultiBlockComponent {
	private MultiBlockInfo info;
	public boolean output;

	public TileEntityNanoCubePort() {
		info = new MultiBlockInfo();
		setTechLevel(EnumTechLevel.NANO);
		output = false;
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
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
		output = compound.getBoolean("output");
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
		return result;
	}

	@Override
	public boolean breakMultiBlock(World world, int x, int y, int z) {
		boolean result = info.breakMultiBlock(world, x, y, z);
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		return result;
	}

	@Override
	public MultiBlockInfo getInfo() {
		return info;
	}

}
