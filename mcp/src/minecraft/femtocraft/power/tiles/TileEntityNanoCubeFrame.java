package femtocraft.power.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.core.tiles.TileEntityBase;

public class TileEntityNanoCubeFrame extends TileEntityBase implements
		IMultiBlockComponent {
	private MultiBlockInfo info;

	public TileEntityNanoCubeFrame() {
		info = new MultiBlockInfo();
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		info.loadFromNBT(par1nbtTagCompound.getCompoundTag("info"));
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		NBTTagCompound infoC = new NBTTagCompound();
		info.saveToNBT(infoC);
		par1nbtTagCompound.setTag("info", infoC);
	}

	@Override
	public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
		if (isValidMultiBlock()) {
			TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(),
					info.z());
			// Big Oops? Or chunk unloaded...despite having player activating it
			// >.>
			if (te == null)
				return false;

			par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
					info.y(), info.z());
			return true;
		}
		return false;
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
		return info.isValidMultiBlock();
	}

	@Override
	public boolean breakMultiBlock(World world, int x, int y, int z) {
		return info.isValidMultiBlock();
	}

	@Override
	public MultiBlockInfo getInfo() {
		return info;
	}

}
