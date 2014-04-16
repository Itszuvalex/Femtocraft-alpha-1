package femtocraft.power.tiles;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.core.multiblock.IMultiBlockComponent;
import femtocraft.core.multiblock.MultiBlockInfo;
import femtocraft.core.tiles.TileEntityBase;
import femtocraft.utils.FemtocraftDataUtils.Saveable;

public class TileEntityFemtoCubeFrame extends TileEntityBase implements
		IMultiBlockComponent {
	private @Saveable(desc = true)
	MultiBlockInfo info;

	public TileEntityFemtoCubeFrame() {
		info = new MultiBlockInfo();
	}
	
	@Override
	public boolean canUpdate() {
		return false;
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
