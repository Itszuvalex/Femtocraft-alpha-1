package femtocraft.power.items;

import femtocraft.api.FemtopowerContainer;
import femtocraft.power.tiles.TileEntityPowerMicroCube;

public class ItemBlockMicroCube extends ItemBlockPower {

	public ItemBlockMicroCube(int par1) {
		super(par1);
	}

	@Override
	public FemtopowerContainer getDefaultContainer() {
		return TileEntityPowerMicroCube.getDefaultContainer();
	}
}
