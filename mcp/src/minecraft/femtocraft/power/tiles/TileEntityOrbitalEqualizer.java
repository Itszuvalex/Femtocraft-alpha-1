package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityOrbitalEqualizer extends TileEntityPowerBase {
	public TileEntityOrbitalEqualizer() {
		super();
		setMaxStorage(2000);
		setTechLevel(EnumTechLevel.NANO);
	}

	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
		return (level.tier == EnumTechLevel.MICRO.tier || level.tier == EnumTechLevel.NANO.tier);
	}
}
