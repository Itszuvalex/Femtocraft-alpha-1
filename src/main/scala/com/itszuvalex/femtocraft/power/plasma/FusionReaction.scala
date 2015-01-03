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

import com.itszuvalex.femtocraft.api.core.{Configurable, ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.power.plasma.{IFusionReaction, IFusionReactorCore}
import com.itszuvalex.femtocraft.power.plasma.FusionReaction._
import com.itszuvalex.femtocraft.power.plasma.volatility.VolatilityEventMagneticFluctuation
import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/8/14.
 */
object FusionReaction {
  @Configurable val overChargeMultiplier             : Int    = 10
  @Configurable val stabilityMultiplier              : Int    = 1000
  @Configurable val ignitionSuccessfulPowerMultiplier: Double = .5d
  @Configurable val reactionLossPerTickMultiplier    : Double = .99d
}

@Configurable class FusionReaction(private var core: IFusionReactorCore,
                                   private val ignitionProcessWindow: Int,
                                   private val reactionThreshold: Long,
                                   private val reactionFailureThreshold: Long,
                                   private val plasmaFlowTicksToGenerateMin: Int,
                                   private val plasmaFlowTicksToGenerateMax: Int) extends IFusionReaction with ISaveable {
  private           val random                   : Random  = new Random
  @Saveable private var energy                   : Long    = 0L
  @Saveable private var selfSustaining           : Boolean = false
  @Saveable private var igniting                 : Boolean = false
  @Saveable private var ignitionProcessTicks     : Int     = 0
  @Saveable private var ticksToGeneratePlasmaFlow: Int     = 0

  def update(core: IFusionReactorCore, world: World, x: Int, y: Int, z: Int) {
    if (selfSustaining) {
      energy = (energy.toDouble * reactionLossPerTickMultiplier).toLong
      if ( {ticksToGeneratePlasmaFlow -= 1; ticksToGeneratePlasmaFlow + 1} <= 0) {
        core.addFlow(generateFlow)
        generateTicksToPlasmaFlow()
      }
      if (energy > (getReactionThreshold * overChargeMultiplier)) {
        val flow = generateFlow
        flow.setUnstable(true)
        core.addFlow(flow)
      }
      if (getReactionInstability > core.getStabilityRating) {
        val eventEnergy: Long = (random.nextFloat * this.energy * .25d).toLong
        FemtocraftPlasmaManager.applyEventToContainer(core, new VolatilityEventMagneticFluctuation(null, getReactionInstability - core.getStabilityRating, eventEnergy), world, x, y, z)
        energy -= eventEnergy
      }
      if (energy < getReactionFailureThreshold) {
        selfSustaining = false
        energy = 0
      }
    }
    else if (igniting) {
      ignitionProcessTicks += 1
      if (energy > getReactionThreshold) {
        energy -= (energy * ignitionSuccessfulPowerMultiplier).toInt
        selfSustaining = true
        generateTicksToPlasmaFlow()
        endIgnitionProcess()
      }
      else if (ignitionProcessTicks > getIgnitionProcessWindow) {
        energy = 0
        endIgnitionProcess()
      }
    }
  }

  def getIgnitionProcessWindow = ignitionProcessWindow

  def getReactionFailureThreshold = reactionFailureThreshold

  def getReactionInstability = ((energy * stabilityMultiplier) / getReactionThreshold).toInt

  def getReactionThreshold = reactionThreshold

  def generateFlow = new PlasmaFlow(this)

  def endIgnitionProcess() {
    igniting = false
    ignitionProcessTicks = 0
  }

  private def generateTicksToPlasmaFlow() {
    ticksToGeneratePlasmaFlow = random.nextInt(plasmaFlowTicksToGenerateMax - plasmaFlowTicksToGenerateMin) + plasmaFlowTicksToGenerateMin
  }

  def getCore = core

  def setCore(core: IFusionReactorCore) {
    this.core = core
  }

  def getReactionEnergy = energy

  def consumeReactionEnergy(energy: Long): Boolean = {
    if (this.energy > energy) {
      this.energy -= energy
      return true
    }
    false
  }

  def contributeReactionEnergy(energy: Long): Long = {
    if (!(isIgniting || isSelfSustaining)) {
      return 0
    }
    this.energy += energy
    energy
  }

  def isSelfSustaining = selfSustaining

  def isIgniting = igniting

  def beginIgnitionProcess() {
    igniting = true
    ignitionProcessTicks = 0
  }

  def endSelfSustainingReaction() {
    if (!selfSustaining) {
      return
    }
    selfSustaining = false
    energy = 0
  }

  def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }
}
