/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.power.multiblock

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.multiblock.{IMultiBlock, IMultiBlockComponent}
import net.minecraft.world.World

object MultiBlockNanoCube extends IMultiBlock {

  def canForm(world: World, x: Int, y: Int, z: Int): Boolean = this.canForm(world, x, y, z, false)

  def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = this.canForm(world, x, y, z, true)

  def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean = y >= c_y && y <= (c_y + 2) && x >= (c_x - 1) && x <= (c_x + 1) && z >= (c_z - 1) && z <= (c_z + 1)

  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- 0 to 2) {
          world.getTileEntity(x + i, y + k, z + j) match {
            case component: IMultiBlockComponent =>
              result = component.formMultiBlock(world, x, y, z) && result
            case _ =>
              if (!(i == 0 && j == 0 && k == 1)) {
                result = false
              }
          }
        }
      }
    }
    result
  }

  def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
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

  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- 0 to 2) {
          world.getTileEntity(x + i, y + k, z + j) match {
            case component: IMultiBlockComponent =>
              result = component.breakMultiBlock(world, x, y, z) && result
            case _ =>
              if (!(i == 0 && j == 0 && k == 1)) {
                result = false
              }
          }
        }
      }
    }
    result
  }

  private def canForm(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = checkGroundLevel(world, x, y, z, strict) && checkMidLevel(world, x, y, z, strict) && checkTopLevel(world, x, y, z, strict)

  private def checkGroundLevel(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        if (!isBlockInMultiBlock(world, x + i, y, z + j, x, y, z)) {
          return false
        }
        if (i == 0 && j == 0) {
          if (world.getBlock(x + i, y, z + j) ne Femtocraft.blockNanoCubePort) {
            return false
          }
        }
        else {
          if (world.getBlock(x + i, y, z + j) ne Femtocraft.blockNanoCubeFrame) {
            return false
          }
        }
        if (strict) {
          val mbc = world.getTileEntity(x + i, y, z + j).asInstanceOf[IMultiBlockComponent]
          if (mbc == null) {
            return false
          }
          if (mbc.isValidMultiBlock) {
            return false
          }
        }
      }
    }
    true
  }

  private def checkMidLevel(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    if (world.getBlock(x, y + 1, z) ne Femtocraft.blockMicroCube) {
      return false
    }

    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        if (!isBlockInMultiBlock(world, x + i, y + 1, z + j, x, y, z)) {
          return false
        }
        if (!(i == 0) || !(j == 0)) {
          if (i == 0 || j == 0) {
            if (world.getBlock(x + i, y + 1, z + j) ne Femtocraft.blockNanoCubePort) {
              return false
            }
          }
          else {
            if (world.getBlock(x + i, y + 1, z + j) ne Femtocraft.blockNanoCubeFrame) {
              return false
            }
          }
          if (strict) {
            val mbc = world.getTileEntity(x + i, y + 1, z + j).asInstanceOf[IMultiBlockComponent]
            if (mbc == null) {
              return false
            }
            if (mbc.isValidMultiBlock) {
              return false
            }
          }
        }
      }
    }
    true
  }

  private def checkTopLevel(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        if (!isBlockInMultiBlock(world, x + i, y + 2, z + j, x, y, z)) {
          return false
        }
        if (i == 0 && j == 0) {
          if (world.getBlock(x + i, y + 2, z + j) ne Femtocraft.blockNanoCubePort) {
            return false
          }
        }
        else {
          if (world.getBlock(x + i, y + 2, z + j) ne Femtocraft.blockNanoCubeFrame) {
            return false
          }
        }
        if (strict) {
          val mbc = world.getTileEntity(x + i, y + 2, z + j).asInstanceOf[IMultiBlockComponent]
          if (mbc == null) {
            return false
          }
          if (mbc.isValidMultiBlock) {
            return false
          }
        }
      }
    }
    true
  }
}
