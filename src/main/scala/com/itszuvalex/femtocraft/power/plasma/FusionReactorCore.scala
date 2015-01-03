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
import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
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

  def getReactionTemperature = (reaction.getReactionEnergy * FemtocraftPlasmaManager.energyToTemperature).toLong

  def getCoreEnergy = reaction.getReactionEnergy

  def consumeCoreEnergy(energy: Long) = reaction.consumeReactionEnergy(energy)

  def contributeCoreEnergy(energy: Long) = reaction.contributeReactionEnergy(energy)

  override def getComponents: util.Collection[IFusionReactorComponent] = components

  override def addComponent(component: IFusionReactorComponent) = !components.contains(component) && components.add(component)

  override def removeComponent(component: IFusionReactorComponent) = components.contains(component) && components.remove(component)

  override def beginIgnitionProcess(core: IFusionReactorCore) {
    reaction.beginIgnitionProcess()
  }

  override def getCore = this

  override def getInput = plasmaContainer.getInput

  override def getOutput = plasmaContainer.getOutput

  override def setInput(container: IPlasmaContainer, dir: ForgeDirection) = plasmaContainer.setInput(container, dir)

  override def setOutput(container: IPlasmaContainer, dir: ForgeDirection) = plasmaContainer.setOutput(container, dir)

  override def getInputDir = plasmaContainer.getInputDir

  override def getOutputDir = plasmaContainer.getOutputDir

  override def addFlow(flow: IPlasmaFlow) = plasmaContainer.addFlow(flow)

  override def getFlows: util.Collection[IPlasmaFlow] = plasmaContainer.getFlows

  override def removeFlow(flow: IPlasmaFlow) = plasmaContainer.removeFlow(flow)

  override def getMaxFlows = plasmaContainer.getMaxFlows

  override def update(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int) {
    reaction.update(container.asInstanceOf[IFusionReactorCore], world, x, y, z)
    plasmaContainer.update(container, world, x, y, z)
  }

  override def onVolatilityEvent(event: IVolatilityEvent) {
    plasmaContainer.onVolatilityEvent(event)
  }

  override def onPostVolatilityEvent(event: IVolatilityEvent) {
    plasmaContainer.onPostVolatilityEvent(event)
  }

  override def getTemperatureRating = plasmaContainer.getTemperatureRating

  override def getStabilityRating = plasmaContainer.getStabilityRating

  override def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  override def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
    reaction.setCore(this)
  }
}
