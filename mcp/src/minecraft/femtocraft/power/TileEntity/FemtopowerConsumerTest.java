package femtocraft.power.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import femtocraft.managers.research.TechLevel;

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
