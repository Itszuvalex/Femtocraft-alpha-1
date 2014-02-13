package femtocraft.power.tiles;

import net.minecraftforge.common.ForgeDirection;

public class TileEntityPowerProducer extends TileEntityPowerBase {

	public TileEntityPowerProducer() {
		super();
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		float val = getFillPercentage();
		return val > .75f ? val : .25f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		return 1.0f;
	}
}
