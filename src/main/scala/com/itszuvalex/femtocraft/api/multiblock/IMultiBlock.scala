package com.itszuvalex.femtocraft.api.multiblock

import net.minecraft.world.World

/**
 * @author Itszuvalex Helper interface for better structure of MultiBlock behavior classes
 *
 *         All methods but formMultiBlockWithBlock assume coordinates given are for the controlling block.
 */
trait IMultiBlock {
  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock can form in the given world, with the block at x,y,z as its controller block.
   */
  def canForm(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock can form in the given world, with the block at x,y,z as its controller block.
   *         This will return false if any blocks that would be used are already in a MultiBlock.
   */
  def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @param c_x
   * @param c_y
   * @param c_z
   * @return True if the block at x, y, z is in the MultiBlock with the controller at c_x, c_y, c_z
   */
  def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock correctly forms in the given world, with the block at x,y,z as the controller
   *         block.
   */
  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock correctly forms in the given world, using the block given at x,y,z anywhere in
   *         the MultiBlock
   */
  def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock breaks with no errors in the given world, using the block at x,y,z as the
   *         controller block.
   */
  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean
}
