package femtocraft.api;

import net.minecraft.world.World;
import femtocraft.research.TechLevel;

public interface IChargingCoil {
	
	float powerPerTick(World world, int x, int y, int z);
	
	TechLevel techLevel(World world, int x, int y, int z);
}
