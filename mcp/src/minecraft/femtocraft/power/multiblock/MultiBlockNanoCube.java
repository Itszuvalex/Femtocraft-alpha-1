package femtocraft.power.multiblock;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import femtocraft.FemtocraftConfigs;
import femtocraft.core.multiblock.IMultiBlock;
import femtocraft.core.multiblock.IMultiBlockComponent;

public class MultiBlockNanoCube implements IMultiBlock {
	public static MultiBlockNanoCube instance = new MultiBlockNanoCube();

	private MultiBlockNanoCube() {
	}

	@Override
	public boolean canForm(World world, int x, int y, int z) {
		return this.canForm(world, x, y, z, false);
	}

	@Override
	public boolean canFormStrict(World world, int x, int y, int z) {
		return this.canForm(world, x, y, z, true);
	}

	private boolean canForm(World world, int x, int y, int z, boolean strict) {
		if (!checkGroundLevel(world, x, y, z, strict))
			return false;
		if (!checkMidLevel(world, x, y, z, strict))
			return false;
		if (!checkTopLevel(world, x, y, z, strict))
			return false;

		return true;
	}

	private boolean checkGroundLevel(World world, int x, int y, int z,
			boolean strict) {

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0)
					if (world.getBlockId(x + i, y, z + j) != FemtocraftConfigs.FemtopowerNanoCubePortID)
						return false;

				if (world.getBlockId(x + i, y, z + j) != FemtocraftConfigs.FemtopowerNanoCubeFrameID)
					return false;

				if (strict) {
					IMultiBlockComponent mbc = (IMultiBlockComponent) world
							.getBlockTileEntity(x + i, y, z + j);
					if (mbc == null)
						return false;
					if (mbc.isValidMultiBlock())
						return false;
				}
			}
		}
		return true;
	}

	private boolean checkMidLevel(World world, int x, int y, int z,
			boolean strict) {
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

				if (strict) {
					IMultiBlockComponent mbc = (IMultiBlockComponent) world
							.getBlockTileEntity(x + i, y + 1, z + j);
					if (mbc == null)
						return false;
					if (mbc.isValidMultiBlock())
						return false;
				}
			}
		}
		return true;
	}

	private boolean checkTopLevel(World world, int x, int y, int z,
			boolean strict) {

		for (int i = -1; i <= 1; ++i) {
			for (int j = -1; j <= 1; ++j) {
				if (i == 0 && j == 0)
					if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerNanoCubePortID)
						return false;

				if (world.getBlockId(x + i, y + 2, z + j) != FemtocraftConfigs.FemtopowerNanoCubeFrameID)
					return false;

				if (strict) {
					IMultiBlockComponent mbc = (IMultiBlockComponent) world
							.getBlockTileEntity(x + i, y + 2, z + j);
					if (mbc == null)
						return false;
					if (mbc.isValidMultiBlock())
						return false;
				}
			}
		}
		return true;
	}

	@Override
	public boolean isBlockInMultiBlock(World world, int x, int y, int z,
			int c_x, int c_y, int c_z) {
		if (y < c_y || y > (c_y + 2))
			return false;
		if (x < (c_x - 1) || x > (c_x + 1))
			return false;
		if (z < (c_z - 1) || z > (c_z + 1))
			return false;

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
	public boolean formMultiBlockWithBlock(World world, int x, int y, int z) {
		// x
		for (int i = -2; i <= 2; i++) {
			// z
			for (int j = -2; j <= 2; j++) {
				// y
				for (int k = -2; k <= 0; k++) {
					if (canFormStrict(world, x + i, y + k, z + j)) {
						return formMultiBlock(world, x + i, y + k, z + j);
					}
				}
			}
		}
		return false;
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
