package com.itszuvalex.femtocraft.api.power;

import net.minecraft.world.World;

/**
 * Created by Chris on 9/8/2014.
 */
public interface ICryogenHandler {
    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return True if this handler will handle a block of this type;
     */
    boolean canHandleBlock(World world, int x, int y, int z);

    /**
     * @param world
     * @param x
     * @param y
     * @param z
     * @return Amount of power to generate for this block.  If passive, this will be called every tick.
     */
    float powerForBlock(World world, int x, int y, int z);

    /**
     * Called after a block has been used for power generation.  Use this to update the world, if needed.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     */
    void usedBlockForPower(World world, int x, int y, int z);
}
