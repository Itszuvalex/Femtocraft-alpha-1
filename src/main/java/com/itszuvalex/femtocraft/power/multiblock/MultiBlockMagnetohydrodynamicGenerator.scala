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
import com.itszuvalex.femtocraft.power.tiles.{TileEntityMagnetohydrodynamicGenerator, TileEntitySteamGenerator}
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
object MultiBlockMagnetohydrodynamicGenerator extends IMultiBlock {

  def canForm(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, false)

  def isBlockInMultiBlock(world: World, x: Int, y: Int, z: Int, c_x: Int, c_y: Int, c_z: Int): Boolean = {
    x >= (c_x - 1) && x <= (c_x + 1) && y >= (c_y - 1) && y <= (c_y + 1) && z >= (c_z - 1) && z <= (c_z + 1)
  }

  def formMultiBlockWithBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- -1 to 1) {
          if (canFormStrict(world, x + i, y + k, z + j)) {
            return formMultiBlock(world, x + i, y + k, z + j)
          }
        }
      }
    }
    false
  }

  def canFormStrict(world: World, x: Int, y: Int, z: Int): Boolean = checkComponents(world, x, y, z, true)

  private def checkComponents(world: World, x: Int, y: Int, z: Int, strict: Boolean): Boolean = {
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- -1 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          if (te.isInstanceOf[TileEntityMagnetohydrodynamicGenerator] || (isSteamGeneratorSlot(i, k, j) && te.isInstanceOf[TileEntitySteamGenerator])) {
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
    true
  }

  private def isSteamGeneratorSlot(x: Int, y: Int, z: Int): Boolean = {
    ((x == -1 || x == 1) && y == 0 && z == 0) || ((y == -1 || y == 1) && x == 0 && z == 0) || ((z == -1 || z == 1) && x == 0 && y == 0)
  }

  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- -1 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent].formMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }

  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    var result: Boolean = true
    for (i <- -1 to 1) {
      for (j <- -1 to 1) {
        for (k <- -1 to 1) {
          val te: TileEntity = world.getTileEntity(x + i, y + k, z + j)
          result = te.isInstanceOf[IMultiBlockComponent] && te.asInstanceOf[IMultiBlockComponent].breakMultiBlock(world, x, y, z) && result
        }
      }
    }
    result
  }
}
