package femtocraft.power.TileEntity;

import net.minecraftforge.common.ForgeDirection;

public class FemtopowerProducerTest extends FemtopowerProducer {
	private int amountPerTick;
	
	
	public FemtopowerProducerTest() {
		super();
		amountPerTick = 10;
	}

	@Override
	 public void updateEntity() {
		if(!this.worldObj.isRemote)
			charge(ForgeDirection.UNKNOWN, amountPerTick);
		super.updateEntity();
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