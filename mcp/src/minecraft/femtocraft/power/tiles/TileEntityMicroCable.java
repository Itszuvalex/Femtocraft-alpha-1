package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityMicroCable extends TileEntityPowerBase {

    public TileEntityMicroCable() {
        super();
        setMaxStorage(250);
        setTechLevel(EnumTechLevel.MICRO);
    }

    public boolean connectedAcross() {
        if (numConnections() == 2) {
            if (connections[0] && connections[1] || connections[2] && connections[3] || connections[4] &&
                    connections[5]) {
                return true;
            }
        }
        return false;
    }

}
