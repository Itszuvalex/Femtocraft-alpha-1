package femtocraft.industry.tiles;

import femtocraft.industry.blocks.BlockNanoInnervator;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoInnervator extends TileEntityBaseEntityMicroFurnace {

	public TileEntityNanoInnervator() {
		super();
		this.setTechLevel(EnumTechLevel.NANO);
		// TODO: Pull number from configs
		this.setMaxStorage(10000);
	}

	@Override
	protected int getMaxSimultaneousSmelt() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return 8;
	}

	@Override
	protected int getTicksToCook() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return 60;
	}

	@Override
	protected int getPowerToCook() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return 80;
	}

	@Override
	protected void updateBlockState(boolean working) {
		BlockNanoInnervator.updateFurnaceBlockState(working, worldObj, xCoord,
				yCoord, zCoord);
	}
}
