package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityFemtoCable extends TileEntityMicroCable {
    public TileEntityFemtoCable() {
        super();
        setMaxStorage(10000);
        setTechLevel(EnumTechLevel.FEMTO);
    }
}
