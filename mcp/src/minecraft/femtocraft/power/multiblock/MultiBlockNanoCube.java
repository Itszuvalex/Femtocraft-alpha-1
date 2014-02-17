package femtocraft.power.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.FemtocraftConfigs;
import femtocraft.core.multiblock.IMultiBlock;
import femtocraft.core.multiblock.IMultiBlockComponent;

public class MultiBlockNanoCube implements IMultiBlock {

	public MultiBlockNanoCube() {
	}

	@Override
	public boolean canForm(World world, int x, int y, int z) {
		if (!checkGroundLevel(world, x, y, z))
			return false;
		if (!checkMidLevel(world, x, y, z))
			return false;
		if (!checkTopLevel(world, x, y, z))
			return false;

		return true;
	}

	private boolean checkGroundLevel(World world, int x, int y, int z) {
		if (world.getBlockId(x, y, z) != FemtocraftConfigs.FemtopowerNanoCubePortID)
			return false;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0)
					continue;

				if (world.getBlockId(x + i, y, z + j) != FemtocraftConfigs.FemtopowerNanoCubeFrameID)
					return false;
			}
		}
		return true;
	}

	private boolean checkMidLevel(World world, int x, int y, int z) {
		if (world.getBlockId(x, y + 1, z) != FemtocraftConfigs.FemtopowerMicroCubeID)
			return false;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0)
					continue;
				if (i == 0 || j == 0) {
					if (world.getBlockId(x + i, y + 1, z + j) != FemtocraftConfigs.FemtopowerNanoCubePortID)
						return false;
				} else {
					if (world.getBlockId(x + i, y + 1, z + j) != FemtocraftConfigs.FemtopowerNanoCubeFrameID)
						return false;
				}
			}
		}
		return true;
	}

	private boolean checkTopLevel(World world, int x, int y, int z) {
		if (world.getBlockId(x, y + 2, z) != FemtocraftConfigs.FemtopowerNanoCubePortID)
			return false;
		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0)
					continue;

				if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerNanoCubeFrameID)
					return false;
			}
		}
		return true;
	}

	@Override
	public boolean formMultiBlock(World world, int x, int y, int z) {
		boolean result = true;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				for (int k = 0; k <= 2; ++k) {
					TileEntity te = world.getBlockTileEntity(x + i, y + k, z
							+ j);
					if (te == null || !(te instanceof IMultiBlockComponent)) {
						result = false;
					} else {
						result = result
								&& ((IMultiBlockComponent) te).formMultiBlock(
										world, x, y, z);
					}
				}
			}
		}

		return result;
	}

	@Override
	public boolean breakMultiBlock(World world, int x, int y, int z) {
		boolean result = true;

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				for (int k = 0; k <= 2; ++k) {
					TileEntity te = world.getBlockTileEntity(x + i, y + k, z
							+ j);
					if (te == null || !(te instanceof IMultiBlockComponent)) {
						result = false;
					} else {
						result = result
								&& ((IMultiBlockComponent) te).breakMultiBlock(
										world, x, y, z);
					}
				}
			}
		}

		return result;
	}

}
