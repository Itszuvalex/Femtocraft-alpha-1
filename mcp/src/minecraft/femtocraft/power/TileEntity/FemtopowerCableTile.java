package femtocraft.power.TileEntity;

import femtocraft.managers.research.EnumTechLevel;

public class FemtopowerCableTile extends FemtopowerTile {

	public FemtopowerCableTile() {
		setTechLevel(EnumTechLevel.MICRO);
	}

	public boolean connectedAcross() {
		if (numConnections() == 2) {
			if (connections[0] && connections[1])
				return true;
			if (connections[2] && connections[3])
				return true;
			if (connections[4] && connections[5])
				return true;
		}
		return false;
	}

}
