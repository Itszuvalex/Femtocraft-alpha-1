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

import com.itszuvalex.femtocraft.api.multiblock.{IMultiBlock, IMultiBlockComponent}
import com.itszuvalex.femtocraft.api.power.plasma.{IFusionReactorComponent, IFusionReactorCore}
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
object MultiBlockFemtoStellarator extends IMultiBlock {

  def canForm(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, false)

  private def checkComponents(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    {
      for (i <- -2 to 2) {
        for (j <- -2 to 2) {
          for (k <- -2 to 2) {
            val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
            if (if (i == 0 && j == 0 & k == 0) te.isInstanceOf[IFusionReactorCore] else (te.isInstanceOf[IFusionReactorComponent] && !te.isInstanceOf[IFusionReactorCore]) && te.isInstanceOf[IMultiBlockComponent]) {
              if (strict) {
                if (te.asInstanceOf[IMultiBlockComponent].getInfo.isValidMultiBlock) {
                  return false
                }
              }
            }
            else {
              return false
            }
          }
        }
      }
    }
    true
  }

  def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean = {
    x >= (c_x - 2) && x <= (c_x + 2) && y >= (c_y - 2) && y <= (c_y + 2) && z >= (c_z - 2) && z <= (c_z + 2)
  }

  def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- -2 to 2) {
          if (canFormStrict(world, x + i, y + k, z + j)) {
            return formMultiBlock(world, x + i, y + k, z + j)
          }
        }
      }
    }
    false
  }

  def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, true)

  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- -2 to 2) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent].formMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }

  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- -2 to 2) {
      for (j <- -2 to 2) {
        for (k <- -2 to 2) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent].breakMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }
}
