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

object MultiBlockFemtoCube extends IMultiBlock {

  def canForm(world: World, x: Int, y: Int, z: Int): Boolean = canForm(world, x, y, z, false)

  def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- -4 to 0) {
          if (canFormStrict(world, x + i, y + k, z + j)) {
            return formMultiBlock(world, x + i, y + k, z + j)
          }
        }
      }
    }
    false
  }

  def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = canForm(world, x, y, z, true)

  private def canForm(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    MultiBlockNanoCube.canForm(world, x, y + 1, z) && checkBotTopLayer(world, x, y, z, 0, strict) && checkBetweenLayer(world, x, y, z, 1, strict) && checkMiddleLayer(world, x, y, z, strict) && checkBetweenLayer(world, x, y, z, 3, strict) && checkBotTopLayer(world, x, y, z, 4, strict)
  }

  private def checkBotTopLayer(world: World, x: Int, y: Int, z: Int, yoffset: Int, strict: Boolean): Boolean = {

    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        if (!isBlockInMultiBlock(world, x + i, y + yoffset, z + j, x, y, z)) {
          return false
        }
        if (i == -2 || j == -2 || i == 2 || j == 2) {
          if (world.getBlock(x + i, y + yoffset, z + j) ne Femtocraft.blockFemtoCubeFrame) {
            return false
          }
        }
        else if (i == 0 && j == 0) {
          if (world.getBlock(x + i, y + yoffset, z + j) ne Femtocraft.blockFemtoCubePort) {
            return false
          }
        }
        else {
          if (world.getBlock(x + i, y + yoffset, z + j) ne Femtocraft.blockFemtoCubeChassis) {
            return false
          }
        }
        if (strict) {
          val mbc = world.getTileEntity(x + i, y + yoffset, z + j).asInstanceOf[IMultiBlockComponent]
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

  def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean = {
    if (y < c_y || y > (c_y + 4)) return false
    if (x < (c_x - 2) || x > (c_x + 2)) return false
    if (z < (c_z - 2) || z > (c_z + 2)) return false
    true
  }

  private def checkBetweenLayer(world: World, x: Int, y: Int, z: Int, yoffset: Int, strict: Boolean): Boolean = {
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        if (!isBlockInMultiBlock(world, x + i, y + yoffset, z + j, x, y, z)) {
          return false
        }
        if (!(i > -2 && i < 2 && j > -2) || !(j < 2)) {
          if (((i == -2 || i == 2) && (j > -2 && j < 2)) || (j == -2 || j == 2) && (i > -2 && i < 2)) {
            if (world.getBlock(x + i, y + yoffset, z + j) ne Femtocraft.blockFemtoCubeChassis) {
              return false
            }
          }
          else {
            if (world.getBlock(x + i, y + yoffset, z + j) ne Femtocraft.blockFemtoCubeFrame) {
              return false
            }
          }
          if (strict) {
            val mbc = world.getTileEntity(x + i, y + yoffset, z + j).asInstanceOf[IMultiBlockComponent]
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

  private def checkMiddleLayer(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        if (!isBlockInMultiBlock(world, x + i, y + 2, z + j, x, y, z)) {
          return false
        }
        if (!(i > -2 && i < 2 && j > -2) || !(j < 2)) {
          if (((i == -2 || i == 2) && (j > -2 && j < 2)) || (j == -2 || j == 2) && (i > -2 && i < 2)) {
            if ((i == 0) || (j == 0)) {
              if (world.getBlock(x + i, y + 2, z + j) ne Femtocraft.blockFemtoCubePort) {
                return false
              }
            }
            else {
              if (world.getBlock(x + i, y + 2, z + j) ne Femtocraft.blockFemtoCubeChassis) {
                return false
              }
            }
          }
          else {
            if (world.getBlock(x + i, y + 2, z + j) ne Femtocraft.blockFemtoCubeFrame) {
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
    }
    true
  }

  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result = true
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- 0 to 4) {
          if (!(i > -2 && i < 2 && j > -2 && j < 2 && k > 0) || !(k < 4)) {
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
    }
    result
  }

  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- 0 to 4) {
          if (!(i > -2 && i < 2 && j > -2 && j < 2 && k > 0) || !(k < 4)) {
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
    }
    result
  }
}
