package femtocraft.power.items;

import femtocraft.api.FemtopowerContainer;
import femtocraft.power.TileEntity.FemtopowerMicroCubeTile;

public class FemtopowerMicroCubeItemBlock extends FemtopowerItemBlock {

	public FemtopowerMicroCubeItemBlock(int par1) {
		super(par1);
	}

	@Override
	public FemtopowerContainer getDefaultContainer() {
		return FemtopowerMicroCubeTile.getDefaultContainer();
	}

}
