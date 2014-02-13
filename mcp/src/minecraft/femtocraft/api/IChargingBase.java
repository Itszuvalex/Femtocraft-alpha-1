package femtocraft.api;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.world.World;

public interface IChargingBase {

	int maxCoilsSupported(World world, int x, int y, int z);

	EnumTechLevel maxTechSupported(World world, int x, int y, int z);
}
