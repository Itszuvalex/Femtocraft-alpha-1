package femtocraft.api;

import net.minecraft.world.World;
import femtocraft.research.TechLevel;


public interface IChargingBase {
	
	int maxCoilsSupported(World world, int x, int y, int z);
	
	TechLevel maxTechSupported(World world, int x, int y, int z);
	
	boolean addCoil(IChargingCoil coil, World world, int x, int y, int z);
}
