package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNanoCable extends TileEntityMicroCable {
    public TileEntityNanoCable() {
        super();
        setMaxStorage(2000);
        setTechLevel(EnumTechLevel.NANO);
    }
}
