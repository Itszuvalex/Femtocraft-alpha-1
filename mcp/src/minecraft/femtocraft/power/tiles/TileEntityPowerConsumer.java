package femtocraft.power.tiles;

import net.minecraftforge.common.ForgeDirection;

public class TileEntityPowerConsumer extends TileEntityPowerBase {

	public TileEntityPowerConsumer() {
		super();
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		float val = getFillPercentage();
		return val < .25f ? val : .25f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		float val = getFillPercentage();
		return val < .25f ? val : .25f;
	}
}
