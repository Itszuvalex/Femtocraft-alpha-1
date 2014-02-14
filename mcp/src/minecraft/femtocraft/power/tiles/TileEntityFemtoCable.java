package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityFemtoCable extends TileEntityMicroCable {

	public TileEntityFemtoCable() {
		setMaxStorage(10000);
		setTechLevel(EnumTechLevel.FEMTO);
	}

	public boolean connectedAcross() {
		if (numConnections() == 2) {
			if (connections[0] && connections[1]) return true;
			if (connections[2] && connections[3]) return true;
			if (connections[4] && connections[5]) return true;
		}
		return false;
	}

}
