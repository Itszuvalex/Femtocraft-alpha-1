package com.itszuvalex.femtocraft.api.multiblock

import net.minecraft.world.World

/**
 * @author Itszuvalex
 *         Interface for MultiBlock components for easy implementation.
 */
trait IMultiBlockComponent {
  /**
   * @return True if this is in valid MultiBlock
   */
  def isValidMultiBlock: Boolean

  /**
   * @param x
   * @param y
   * @param z
   * @return True if correctly forms, given controller block at x,y,z.
   */
  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @param x
   * @param y
   * @param z
   * @return True if breaks without errors, given controller block at x,y,z.
   */
  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean

  /**
   * @return MultiBlockInfo associated with this MultiBlockComponent
   */
  def getInfo: MultiBlockInfo
}
