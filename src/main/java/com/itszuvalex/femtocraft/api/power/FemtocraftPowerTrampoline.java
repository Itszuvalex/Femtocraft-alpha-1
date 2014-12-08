package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils;
import net.minecraft.world.World;

/**
 * Created by Chris on 11/14/2014.
 */
public class FemtocraftPowerTrampoline {
    /**
     * {@link com.itszuvalex.femtocraft.power.FemtocraftPowerUtils#distributePower(IPowerTileContainer, boolean[],
     * net.minecraft.world.World, int, int, int)}
     */
    public static void distributePower(IPowerTileContainer container, boolean[] connections, World world, int x,
                                       int y, int z) {
        FemtocraftPowerUtils.distributePower(container, connections, world, x, y, z);
    }
}
