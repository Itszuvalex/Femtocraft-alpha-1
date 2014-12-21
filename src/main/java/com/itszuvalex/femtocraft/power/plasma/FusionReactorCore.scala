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

import java.util
import java.util.ArrayList

import com.itszuvalex.femtocraft.api.core.{ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.power.plasma._
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 5/7/14.
 */
class FusionReactorCore(maxContainedFlows: Int, stability: Int, temperatureRating: Int, ignitionProcessWindow: Int, reactionThreshold: Long, reactionFailureThreshold: Long, plasmaFlowTicksToGenerateMin: Int, plasmaFlowTicksToGenerateMax: Int) extends IFusionReactorCore with ISaveable {
  @Saveable private val plasmaContainer = new PlasmaContainer(maxContainedFlows, stability, temperatureRating)
  @Saveable private val reaction        = new FusionReaction(this, ignitionProcessWindow, reactionThreshold, reactionFailureThreshold, plasmaFlowTicksToGenerateMin, plasmaFlowTicksToGenerateMax)
  private           val components      = new ArrayList[IFusionReactorComponent]

  def stopReaction() {
    if (isIgniting) {
      endIgnitionProcess(this)
    }
    else if (isSelfSustaining) {
      reaction.endSelfSustainingReaction()
    }
    onReactionStop(this)
  }

  def isSelfSustaining = reaction.isSelfSustaining

  def isIgniting = reaction.isIgniting

  def endIgnitionProcess(core: IFusionReactorCore) {
    reaction.endIgnitionProcess()
  }

  def onReactionStop(core: IFusionReactorCore) {
    core.getComponents.foreach(_.onReactionStop(this))
  }

  def getIgnitionProcessWindow = reaction.getIgnitionProcessWindow

  def getReactionInstability = reaction.getReactionInstability

  def getReactionTemperature = (reaction.getReactionEnergy * FemtocraftPlasmaUtils.energyToTemperature).toLong

  def getCoreEnergy = reaction.getReactionEnergy

  def consumeCoreEnergy(energy: Long) = reaction.consumeReactionEnergy(energy)

  def contributeCoreEnergy(energy: Long) = reaction.contributeReactionEnergy(energy)

  def getComponents: util.Collection[IFusionReactorComponent] = components

  def addComponent(component: IFusionReactorComponent) = !components.contains(component) && components.add(component)

  def removeComponent(component: IFusionReactorComponent) = components.contains(component) && components.remove(component)

  def beginIgnitionProcess(core: IFusionReactorCore) {
    reaction.beginIgnitionProcess()
  }

  def getCore = this

  def getInput = plasmaContainer.getInput

  def getOutput = plasmaContainer.getOutput

  def setInput(container: IPlasmaContainer, dir: ForgeDirection) = plasmaContainer.setInput(container, dir)

  def setOutput(container: IPlasmaContainer, dir: ForgeDirection) = plasmaContainer.setOutput(container, dir)

  def getInputDir = plasmaContainer.getInputDir

  def getOutputDir = plasmaContainer.getOutputDir

  def addFlow(flow: IPlasmaFlow) = plasmaContainer.addFlow(flow)

  def getFlows: util.Collection[IPlasmaFlow] = plasmaContainer.getFlows

  def removeFlow(flow: IPlasmaFlow) = plasmaContainer.removeFlow(flow)

  def getMaxFlows = plasmaContainer.getMaxFlows

  def update(world: World, x: Int, y: Int, z: Int) {
    reaction.update(this, world, x, y, z)
    plasmaContainer.update(world, x, y, z)
  }

  def onVolatilityEvent(event: IVolatilityEvent) {
    plasmaContainer.onVolatilityEvent(event)
  }

  def onPostVolatilityEvent(event: IVolatilityEvent) {
    plasmaContainer.onPostVolatilityEvent(event)
  }

  def getTemperatureRating = plasmaContainer.getTemperatureRating

  def getStabilityRating = plasmaContainer.getStabilityRating

  def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
    reaction.setCore(this)
  }
}
