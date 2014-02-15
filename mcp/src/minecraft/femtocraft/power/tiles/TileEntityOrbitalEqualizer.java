package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;

public class TileEntityOrbitalEqualizer extends TileEntityPowerBase {
	public TileEntityOrbitalEqualizer() {
		super();
		setMaxStorage(2000);
		setTechLevel(EnumTechLevel.NANO);
	}
}
