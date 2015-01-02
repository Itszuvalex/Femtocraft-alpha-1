package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.world.World;

/**
 * Implemented by the Block class, not the TileEntity
 */
public interface IAtmosphericChargingBase {

    /**
     * Maximum number of addons supported by this charging base.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    int maxAddonsSupported(World world, int x, int y, int z);

    /**
     * Maximum techLevel of addons supportable by this charging base.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    EnumTechLevel maxTechSupported(World world, int x, int y, int z);
}
