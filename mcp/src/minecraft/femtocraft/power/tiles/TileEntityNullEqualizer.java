package femtocraft.power.tiles;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityNullEqualizer extends TileEntityPowerBase {
	public TileEntityNullEqualizer() {
		super();
		setMaxStorage(10000);
		setTechLevel(EnumTechLevel.FEMTO);
	}

	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
		return (level.tier == EnumTechLevel.NANO.tier || level.tier == EnumTechLevel.FEMTO.tier);
	}
}
