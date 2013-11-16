package femtocraft.power.TileEntity;

import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.ForgeDirection;

public class FemtopowerMicroCubeTile extends FemtopowerTile {
	Boolean[] outputs = new Boolean[6];
	
	public FemtopowerMicroCubeTile() {
		super();
		setMaxStorage(10000);
	}

	public void onSideActivate(ForgeDirection side)
	{
		int i = Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(side);
		outputs[i] = !outputs[i];
	}

	@Override
	public float getFillPercentageForCharging(ForgeDirection from) {
		return outputs[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(from)] ? 0.f : 1.f;
	}

	@Override
	public float getFillPercentageForOutput(ForgeDirection to) {
		return outputs[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(to)] ? 1.f : 0.f;
	}

	@Override
	public boolean canCharge(ForgeDirection from) {
		return (outputs[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(from)] ? false : true) && super.canCharge(from);
	}

	@Override
	public int charge(ForgeDirection from, int amount) {
		if(outputs[Arrays.asList(ForgeDirection.VALID_DIRECTIONS).indexOf(from)])
		{
			return super.charge(from, amount);
		}
		
		return 0;
	}

	@Override
	public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readFromNBT(par1nbtTagCompound);
		for(int i = 0; i < 6; i++) {
			outputs[i] = par1nbtTagCompound.getBoolean(String.format("output%d", i));
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeToNBT(par1nbtTagCompound);
		
		for(int i = 0; i < 6; i++) {
			par1nbtTagCompound.setBoolean(String.format("output%d", i), outputs[i]);
		}
	}

	
}
