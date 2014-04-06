package femtocraft.power.tiles;

import net.minecraftforge.common.ForgeDirection;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityOrbitalEqualizer extends TileEntityPowerBase {
	public TileEntityOrbitalEqualizer() {
		super();
		setMaxStorage(2000);
		setTechLevel(EnumTechLevel.NANO);
	}

	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level,
			ForgeDirection from) {
		return (level == EnumTechLevel.MICRO || level == EnumTechLevel.NANO);
	}
}
