package com.itszuvalex.femtocraft.api.power;

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import net.minecraft.world.World;

/**
 * Implemented by the Block class, not the TileEntity.
 */
public interface IAtmosphericChargingAddon {

    /**
     * Returns the amount of power generated this tick from this block.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    float powerPerTick(World world, int x, int y, int z);

    /**
     * What techlevel this addon is.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     * @return
     */
    EnumTechLevel techLevel(World world, int x, int y, int z);

    /**
     * Returns true if this can support addon, false otherwise.
     *
     * @param addon
     * @return
     */
    boolean canSupportAddon(IAtmosphericChargingAddon addon, World world,
                            int x, int y, int z);
}
