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
package com.itszuvalex.femtocraft.power.tiles

import java.util.Random

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.power.PowerContainer
import com.itszuvalex.femtocraft.core.MagnetRegistry
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagneticInductionGenerator._
import com.itszuvalex.femtocraft.power.traits.PowerProducer
import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraftforge.common.util.ForgeDirection

@Configurable object TileEntityMagneticInductionGenerator {
  @Configurable var POWER_MAX                      : Int           = 1000
  @Configurable var POWER_LEVEL                    : EnumTechLevel = EnumTechLevel.MICRO
  @Configurable var MAG_DIFFERENCE_POWER_MULTIPLIER: Float         = .1f
}

@Configurable class TileEntityMagneticInductionGenerator extends TileEntityBase with PowerProducer {
  @Saveable private val neighborMagnetStrength: Array[Int] = new Array[Int](6)
  private           val random                : Random     = new Random

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()

    for (i <- 0 until 6) {
      val `val`: Int = magStrengthOfNeighborForDir(i)
      val old: Int = neighborMagnetStrength(i)
      if (`val` != old) {
        charge(ForgeDirection.UNKNOWN, Math.abs((`val` - old) * MAG_DIFFERENCE_POWER_MULTIPLIER).toInt)
        neighborMagnetStrength(i) = `val`
      }
    }

  }

  override def updateEntity() {
    super.updateEntity()
    if (worldObj.isRemote) {
      var charged: Boolean = false
      for (i <- 0 until 6) {
        val `val`: Int = magStrengthOfNeighborForDir(i)
        val old: Int = neighborMagnetStrength(i)
        if (`val` != old) {
          charged = true
          neighborMagnetStrength(i) = `val`
        }
      }
      if (charged) {
        for (i <- 0 until 4) {
          spawnParticle()
        }

      }
    }
  }

  private def magStrengthOfNeighborForDir(i: Int): Int = {
    val dir = ForgeDirection.getOrientation(i)
    val block = worldObj.getBlock(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
    if (MagnetRegistry.isMagnet(block)) MagnetRegistry.getMagnetStrength(block) else 0
  }

  @SideOnly(Side.CLIENT) private def spawnParticle() {
    val x: Float = xCoord
    val y: Float = yCoord
    val z: Float = zCoord
    val side: Int = random.nextInt(6)
    val dir: ForgeDirection = ForgeDirection.getOrientation(side)
    var xOffset: Float = dir.offsetX
    var yOffset: Float = dir.offsetY
    var zOffset: Float = dir.offsetZ
    if (xOffset == 0) {
      xOffset = random.nextFloat
    }
    if (yOffset == 0) {
      yOffset = random.nextFloat
    }
    if (zOffset == 0) {
      zOffset = random.nextFloat
    }
    if (xOffset < 0) xOffset = 0
    if (yOffset < 0) yOffset = 0
    if (zOffset < 0) zOffset = 0
    RenderUtils.spawnParticle(worldObj, RenderUtils.MICRO_POWER_PARTICLE, x + xOffset, y + yOffset, z + zOffset)
  }

  def defaultContainer = new PowerContainer(POWER_LEVEL, POWER_MAX)
}

