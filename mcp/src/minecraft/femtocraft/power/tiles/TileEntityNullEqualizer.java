package femtocraft.power.tiles;

import net.minecraftforge.common.ForgeDirection;
import femtocraft.managers.research.EnumTechLevel;

public class TileEntityNullEqualizer extends TileEntityPowerBase {
	public TileEntityNullEqualizer() {
		super();
		setMaxStorage(10000);
		setTechLevel(EnumTechLevel.FEMTO);
	}

	@Override
	public boolean canAcceptPowerOfLevel(EnumTechLevel level,
			ForgeDirection from) {
		return (level == EnumTechLevel.NANO || level == EnumTechLevel.FEMTO);
	}
}
