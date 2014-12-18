package com.itszuvalex.femtocraft.power.multiblock

import com.itszuvalex.femtocraft.api.multiblock.{IMultiBlock, IMultiBlockComponent}
import com.itszuvalex.femtocraft.power.tiles.TileEntityDecontaminationChamber
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * Created by Chris on 12/17/2014.
 */
object MultiBlockDecontaminationChamber extends IMultiBlock {
  override def canForm(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, false)

  private def checkComponents(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    for (i <- 0 to 1) {
      for (j <- 0 to 1) {
        for (k <- 0 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          if (te.isInstanceOf[TileEntityDecontaminationChamber]) {
            if (strict) {
              if (te.asInstanceOf[IMultiBlockComponent].getInfo.isValidMultiBlock) {
                return false
              }
            }
          } else {
            return false
          }
        }
      }
    }
    true
  }

  override def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- 0 to 1) {
      for (j <- 0 to 1) {
        for (k <- 0 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent]
                                                            .breakMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }

  override def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean = {
    (c_x == x || c_x == x - 1) && (c_y == y || c_y == y - 1) && (c_z == z || c_z == z - 1)
  }

  override def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (i <- -1 to 0) {
      for (j <- -1 to 0) {
        for (k <- -1 to 0) {
          if (canFormStrict(world, x + i, y + k, z + j)) {
            return formMultiBlock(world, x + i, y + k, z + j)
          }
        }
      }
    }
    false
  }

  override def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- 0 to 1) {
      for (j <- 0 to 1) {
        for (k <- 0 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent]
                                                            .formMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }

  override def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, true)
}
