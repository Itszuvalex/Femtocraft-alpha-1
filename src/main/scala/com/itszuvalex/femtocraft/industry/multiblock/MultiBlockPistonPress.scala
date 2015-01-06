package com.itszuvalex.femtocraft.industry.multiblock

import com.itszuvalex.femtocraft.api.multiblock.{IMultiBlockComponent, IMultiBlock}
import net.minecraft.world.World

/**
 * Created by Chris on 1/4/2015.
 */
object MultiBlockPistonPress extends IMultiBlock {
  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock can form in the given world, with the block at x,y,z as its controller block.
   */
  override def canForm(world: World, x: Int, y: Int, z: Int): Boolean = {
    false
  }

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock correctly forms in the given world, with the block at x,y,z as the controller
   *         block.
   */
  override def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- 0 to 2) {
          world.getTileEntity(x + i, y + k, z + j) match {
            case component: IMultiBlockComponent =>
              result = component.formMultiBlock(world, x, y, z) && result
            case _                               =>
              if (!(i == 0 && j == 0 && k == 1)) {
                result = false
              }
          }
        }
      }
    }
    result
  }

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock can form in the given world, with the block at x,y,z as its controller block.
   *         This will return false if any blocks that would be used are already in a MultiBlock.
   */
  override def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = {
    false
  }

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock breaks with no errors in the given world, using the block at x,y,z as the
   *         controller block.
   */
  override def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- 0 to 2) {
          world.getTileEntity(x + i, y + k, z + j) match {
            case component: IMultiBlockComponent =>
              result = component.breakMultiBlock(world, x, y, z) && result
            case _                               =>
              if (!(i == 0 && j == 0 && k == 1)) {
                result = false
              }
          }
        }
      }
    }
    result
  }

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
  override def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean =
    y >= c_y && y <= (c_y + 2) && x >= (c_x - 1) && x <= (c_x + 1) && z >= (c_z - 1) && z <= (c_z + 1)

  /**
   * @param world
   * @param x
   * @param y
   * @param z
   * @return True if this MultiBlock correctly forms in the given world, using the block given at x,y,z anywhere in
   *         the MultiBlock
   */
  override def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- -2 to 0) {
          if (canFormStrict(world, x + i, y + k, z + j)) {
            return formMultiBlock(world, x + i, y + k, z + j)
          }
        }
      }
    }
    false
  }
}
