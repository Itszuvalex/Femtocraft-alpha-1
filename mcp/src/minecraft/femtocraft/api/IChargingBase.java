package femtocraft.api;

import femtocraft.research.TechLevel;
import net.minecraft.world.World;


public interface IChargingBase {
	
	int maxCoilsSupported(World world, int x, int y, int z);
	
	TechLevel maxTechSupported(World world, int x, int y, int z);
	
	boolean addCoil(IChargingCoil coil, World world, int x, int y, int z);
}
