package femtocraft.api;

import femtocraft.managers.research.TechLevel;
import net.minecraft.world.World;

public interface IChargingBase {

	int maxCoilsSupported(World world, int x, int y, int z);

	TechLevel maxTechSupported(World world, int x, int y, int z);
}
