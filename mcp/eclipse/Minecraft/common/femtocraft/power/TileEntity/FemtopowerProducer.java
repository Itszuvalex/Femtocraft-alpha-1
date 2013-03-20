package femtocraft.power.TileEntity;

import net.minecraftforge.common.ForgeDirection;

public class FemtopowerProducer extends FemtopowerTile {
	private int amountPerTick;
	
	
	public FemtopowerProducer() {
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
		return 1.0f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		return 1.0f;
	}
	
	@Override
	public boolean canCharge(ForgeDirection from) {
		return false;
	}
}
