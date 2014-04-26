package femtocraft.api;

import femtocraft.managers.research.EnumTechLevel;
import net.minecraft.world.World;

public interface IChargingCoil {

    float powerPerTick(World world, int x, int y, int z);

    EnumTechLevel techLevel(World world, int x, int y, int z);
}
