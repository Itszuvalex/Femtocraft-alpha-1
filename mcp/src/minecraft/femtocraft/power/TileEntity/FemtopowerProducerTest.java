package femtocraft.power.TileEntity;

import net.minecraftforge.common.ForgeDirection;
import femtocraft.managers.research.TechLevel;

public class FemtopowerProducerTest extends FemtopowerProducer {
	private int amountPerTick;
	
	
	public FemtopowerProducerTest() {
		super();
		amountPerTick = 10;
		setTechLevel(TechLevel.MICRO);
	}

	@Override
	public void femtocraftServerUpdate()
	{
		super.femtocraftServerUpdate();
		charge(ForgeDirection.UNKNOWN, amountPerTick);
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
