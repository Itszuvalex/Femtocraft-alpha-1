package femtocraft.api;

import femtocraft.managers.research.TechLevel;
import net.minecraft.world.World;

public interface IChargingCoil {
	
	float powerPerTick(World world, int x, int y, int z);
	
	TechLevel techLevel(World world, int x, int y, int z);
}
