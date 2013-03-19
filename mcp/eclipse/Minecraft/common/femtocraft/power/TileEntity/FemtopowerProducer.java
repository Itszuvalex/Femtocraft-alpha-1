package femtocraft.power.TileEntity;

import net.minecraftforge.common.ForgeDirection;

public class FemtopowerProducer extends FemtopowerTile {
	private int amountPerTick;
	
	
	public FemtopowerProducer(int storage, int perTick) {
		super(storage);
		amountPerTick = perTick;
	}

	@Override
	 public void updateEntity() {
		charge(ForgeDirection.UNKNOWN, amountPerTick);
		super.updateEntity();
	}
	
	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		return 1.f;
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		return false;
	}
}
