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
package com.itszuvalex.femtocraft.power.plasma

import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.managers.IPlasmaManager
import com.itszuvalex.femtocraft.api.power.plasma._
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import com.itszuvalex.femtocraft.api.utils.WorldLocation
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 5/8/14.
 */
object FemtocraftPlasmaManager extends IPlasmaManager {
  val _temperatureToEnergy = 10000
  val _energyToTemperature = 1d / temperatureToEnergy

  override def loadFlow(nbt: NBTTagCompound): IPlasmaFlow = PlasmaFlow(nbt)

  override def energyToTemperature = _energyToTemperature

  override def generateFlow(reaction: IFusionReaction): IPlasmaFlow = new PlasmaFlow(reaction)

  override def applyEventToContainer(container: IPlasmaContainer, event: IVolatilityEvent, world: World, x: Int, y: Int,
                                     z: Int) {
    if (event == null) {
      return
    }
    if (container == null) {
      return
    }
    container.onVolatilityEvent(event)
    container match {
      case core: IFusionReactorCore           => event.interact(core, world, x, y, z)
      case component: IFusionReactorComponent => event.interact(component, world, x, y, z)
      case _                                  => event.interact(container, world, x, y, z)
    }
    val flows = container.getFlows
    if (flows != null) {
      for (flow <- flows) {
        flow.onVolatilityEvent(event)
        event.interact(flow, world, x, y, z)
        flow.onPostVolatilityEvent(event)
      }
    }
    container.onPostVolatilityEvent(event)
  }

  override def extractFlowsAndPurge(container: IPlasmaContainer, volatilityEnergy: Long, volatilityLevel: Int,
                                    energyToSegmentsDividend: Int, plasmaDuration: Int, world: World, x: Int, y: Int,
                                    z: Int): Unit = {
    var totalEnergy: Long = volatilityEnergy
    if (container.getFlows != null) {
      for (flow <- container.getFlows) {
        if (flow.isUnstable || flow.getVolatility > volatilityLevel) {
          totalEnergy += (flow.getTemperature * FemtocraftPlasmaManager.temperatureToEnergy).toLong
          container.removeFlow(flow)
        }
      }
    }
    val segments = (totalEnergy / energyToSegmentsDividend).toInt
    val segLoc = new WorldLocation(world, x, y, z)
    for (i <- 0 until segments) {
      if (!placeSegment(segLoc, plasmaDuration)) {
        return
      }
    }
  }

  override def temperatureToEnergy = _temperatureToEnergy

  override def purgeFlow(volatilityEnergy: Long, energyToSegmentsDividend: Int, plasmaDuration: Int, world: World,
                         x: Int, y: Int, z: Int) {
    val segments = (volatilityEnergy / energyToSegmentsDividend).toInt
    val segLoc = new WorldLocation(world, x, y, z)
    for (i <- 0 until segments) {
      if (!placeSegment(segLoc, plasmaDuration)) {
        return
      }
    }
  }

  private def placeSegment(loc: WorldLocation, plasmaDuration: Int): Boolean = {
    val random = new Random
    val dir = ForgeDirection.getOrientation(random.nextInt(6))
    val newX = loc.x + dir.offsetX
    val newY = loc.y + dir.offsetY
    val newZ = loc.z + dir.offsetZ
    if (loc.world.isAirBlock(newX, newY, newZ)) {
      loc.world.setBlock(newX, newY, newZ, Femtocraft.blockPlasma)
      loc.world.setBlockMetadataWithNotify(newX, newY, newZ, dir.ordinal, 2)
      val te: TileEntityPlasma = loc.world.getTileEntity(newX, newY, newZ).asInstanceOf[TileEntityPlasma]
      if (te != null) {
        te.setDuration(plasmaDuration)
      }
    }
    loc.x = loc.x + dir.offsetX
    loc.y = loc.y + dir.offsetY
    loc.z = loc.z + dir.offsetZ
    true
  }

}

