package com.itszuvalex.femtocraft.api.power

import net.minecraft.world.World

/**
 * Created by Chris on 9/8/2014.
 *
 * Implement this trait to register this class with the CryogenRegistry.  This will allow you to add custom world
 * interactions when anything using the CryogenRegistry attempts to gain power from a world block.
 */
trait ICryogenHandler {
  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this handler will handle a block of this type;
   */
  def canHandleBlock(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return Amount of power to generate for this block.  If passive, this will be called every tick.
   */
  def powerForBlock(world: World, x: Int, y: Int, z: Int): Float

  /**
   * Called after a block has been used for power generation.  Use this to update the world, if needed.
   *
   * @param world
   * @param x
   * @param y
   * @param z
   */
  def usedBlockForPower(world: World, x: Int, y: Int, z: Int)
}
