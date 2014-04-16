package femtocraft.industry.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityFemtoRepurposer extends
		TileEntityBaseEntityMicroDeconstructor {
	public static int maxSmelt_default = 32;
	public static int ticksToCook_default = 20;
	public static int powerToCook_default = 160;

	public TileEntityFemtoRepurposer() {
		super();
		this.setTechLevel(EnumTechLevel.FEMTO);
		// TODO: Pull number from configs
		this.setMaxStorage(100000);
	}

	@Override
	protected int getMaxSimultaneousSmelt() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return maxSmelt_default;
	}

	@Override
	protected int getTicksToCook() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return ticksToCook_default;
	}

	@Override
	protected int getPowerToCook() {
		// TODO: Check for modifying researches
		// TODO: Pull number from configs
		return powerToCook_default;
	}
}