package femtocraft.power.TileEntity;

import femtocraft.managers.research.TechLevel;
import net.minecraftforge.common.ForgeDirection;

public class FemtopowerConsumerTest extends FemtopowerConsumer {
	private int amountPerTick;

	public FemtopowerConsumerTest() {
		super();
		amountPerTick = 10;
		setTechLevel(TechLevel.MICRO);
	}

	@Override
	public void femtocraftServerUpdate() {
		super.femtocraftServerUpdate();
		consume(amountPerTick);
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
