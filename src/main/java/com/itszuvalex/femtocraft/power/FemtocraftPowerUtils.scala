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
package com.itszuvalex.femtocraft.power

import java.util

import com.itszuvalex.femtocraft.api.power.IPowerBlockContainer
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Chris on 5/13/2014.
 */
object FemtocraftPowerUtils {
  val distributionBuffer = .01f
  val maxPowerPerTick    = .05f
  private val sconnections    = new Array[Boolean](6)
  private val sneighbors      = new Array[IPowerBlockContainer](6)
  private val fillPercentages = new Array[Float](6)

  /**
   * This algorithm distributes temp from the given container, located in the given world, at the given coordinates,
   * in a manner consistent with intended behaviors.  It distributes temp to IPowerBlockContainers, where the
   * difference in the given container's currentPowerPercentageForOutput() and the adjacent container's
   * currentPowerPercentageForInput() is greater than the distributionBuffer.  It will attempt to distribute at most
   * maxPowerPerTick*currentPower() temp from the given container.
   *
   * @param container   IPowerBlockContainer to pull temp from
   * @param connections Array of size 6, where true represents that this algorithm should attempt to distribute temp
   *                    in this ForgeDirection.ordinal().  If null, will attempt every direction.
   * @param world       World for the algorithm to search for IPowerBlockContainers in
   * @param x           X coordinate for the algorithm to center its distribution search upon
   * @param y           Y coordinate for the algorithm to center its distribution search upon
   * @param z           Z coordinate for the algorithm to center its distribution search upon
   */
  def distributePower(container: IPowerBlockContainer, connections: Array[Boolean], world: World, x: Int, y: Int, z: Int) {
    if (container.getCurrentPower <= 0) {
      return
    }
    if (connections == null) {
      util.Arrays.fill(sconnections, true)
    }
    else if (connections.length != 6) {
      return
    }
    else {
      Array.copy(connections, 0, sconnections, 0, 6)
    }
    util.Arrays.fill(sneighbors.asInstanceOf[Array[AnyRef]], null)
    var numConnections = 0
    for (i <- 0 until 6) {
      if (sconnections(i)) {
        val dir = ForgeDirection.getOrientation(i)
        if (world.getChunkProvider.chunkExists((x + dir.offsetX) >> 4, (z + dir.offsetZ) >> 4)) {
          world.getTileEntity(x + dir.offsetX, y + dir.offsetY, z + dir.offsetZ) match {
            case container1: IPowerBlockContainer =>
              sneighbors(i) = container1
              numConnections += 1
            case _ =>
          }
        }
      }
    }

    var percentDifferenceTotal = 0f
    val maxSpreadThisTick = (container.getCurrentPower.toFloat * maxPowerPerTick).toInt * numConnections
    for (i <- 0 until 6) {
      fillPercentages(i) = container.getFillPercentageForOutput(ForgeDirection.getOrientation(i))
    }


    for (i <- 0 until 6) {
      if (sconnections(i) && !(sneighbors(i) == null)) {
        val dir = ForgeDirection.getOrientation(i)
        val percentDif = fillPercentages(i) - sneighbors(i).getFillPercentageForCharging(dir.getOpposite)
        if (percentDif > distributionBuffer) {
          percentDifferenceTotal += percentDif
        }
      }
    }


    for (i <- 0 until 6) {
      if (sconnections(i) && !(sneighbors(i) == null)) {
        val dir = ForgeDirection.getOrientation(i)
        val percentDif = fillPercentages(i) - sneighbors(i).getFillPercentageForCharging(dir.getOpposite)
        if (percentDif > distributionBuffer) {
          val amountToCharge = Math.ceil(maxSpreadThisTick * percentDif / percentDifferenceTotal).toInt
          container.consume(sneighbors(i).charge(dir.getOpposite, amountToCharge))
        }
      }
    }
  }

  def distributePower(container: IPowerBlockContainer, contacts: Array[IPowerBlockContainer]) {
    var percentDifferenceTotal = 0f
    val maxSpreadThisTick = (container.getCurrentPower.toFloat * maxPowerPerTick).toInt * contacts.length
    val fillPercentages = new Array[Float](contacts.length)
    for (i <- 0 until fillPercentages.length) {
      fillPercentages(i) = container.getFillPercentageForOutput(ForgeDirection.UNKNOWN)
    }

    for (i <- 0 until fillPercentages.length) {
      if (contacts(i) != null) {
        val percentDif = fillPercentages(i) - contacts(i).getFillPercentageForCharging(ForgeDirection.UNKNOWN)
        if (percentDif > distributionBuffer) {
          percentDifferenceTotal += percentDif
        }
      }
    }

    for (i <- 0 until fillPercentages.length) {
      if (contacts(i) != null) {
        val percentDif = fillPercentages(i) - contacts(i).getFillPercentageForCharging(ForgeDirection.UNKNOWN)
        if (percentDif > distributionBuffer) {
          val amountToCharge = Math.ceil(maxSpreadThisTick * percentDif / percentDifferenceTotal).toInt
          container.consume(contacts(i).charge(ForgeDirection.UNKNOWN, amountToCharge))
        }
      }
    }
  }
}

