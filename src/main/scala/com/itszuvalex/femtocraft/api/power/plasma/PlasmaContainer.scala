package com.itszuvalex.femtocraft.api.power.plasma

import java.util

import com.itszuvalex.femtocraft.api.FemtocraftAPI
import com.itszuvalex.femtocraft.api.core.{ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.power.plasma.PlasmaContainer._
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 * <p/>
 * Class intended for internal use by a TileEntity implementing IPlasmaContainer.
 */
object PlasmaContainer {
  private val flowListKey = "Flows"
}

class PlasmaContainer(@Saveable private var maxCapacity: Int, @Saveable private var stability: Int,
                      @Saveable private var temperatureRating: Int) extends IPlasmaContainer with ISaveable {
  private val flows                    = ArrayBuffer[IPlasmaFlow]()
  private val pendingRemove            = ArrayBuffer[IPlasmaFlow]()
  private var input : IPlasmaContainer = null
  private var output: IPlasmaContainer = null
  private var inputDir                 = ForgeDirection.UNKNOWN
  private var outputDir                = ForgeDirection.UNKNOWN

  override def getInput = input

  override def getOutput = output

  override def setInput(container: IPlasmaContainer, dir: ForgeDirection) =
    if (container == this) {
      false
    } else {
      input = container
      inputDir = dir
      true
    }

  override def setOutput(container: IPlasmaContainer, dir: ForgeDirection) =
    if (container == this) {
      false
    } else {
      output = container
      outputDir = dir
      true
    }

  override def getInputDir = inputDir

  override def getOutputDir = outputDir

  override def getFlows: util.Collection[IPlasmaFlow] = flows -- pendingRemove


  override def removeFlow(flow: IPlasmaFlow): Boolean = {
    if (flows.contains(flow)) {
      pendingRemove.add(flow)
      if (flow.getContainer == this) {
        flow.setContainer(null)
      }
      return true
    }
    false
  }

  override def getMaxFlows = maxCapacity

  override def update(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int) {
    flows.foreach(_.update(container, world, x, y, z))
    if (pendingRemove.size > 0) {
      flows --= pendingRemove
      pendingRemove.clear()
    }
  }

  override def onVolatilityEvent(event: IVolatilityEvent) = flows.foreach(_.onVolatilityEvent(event))

  override def onPostVolatilityEvent(event: IVolatilityEvent) = flows.foreach(_.onPostVolatilityEvent(event))

  override def getTemperatureRating = temperatureRating

  override def getStabilityRating = stability

  def saveToNBT(compound: NBTTagCompound) {
    if (pendingRemove.size > 0) {
      flows --= pendingRemove
      pendingRemove.clear()
    }
    val list = new NBTTagList
    for (flow <- flows) {
      val fc = new NBTTagCompound
      flow.saveToNBT(fc)
      list.appendTag(fc)
    }
    compound.setTag(flowListKey, list)
    compound.setInteger("inputDir", inputDir.ordinal)
    compound.setInteger("outputDir", outputDir.ordinal)
    compound.setInteger("capacity", maxCapacity)
    compound.setInteger("stability", stability)
    compound.setInteger("temperature", temperatureRating)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    val list = compound.getTagList(flowListKey, 10)

    (0 until list.tagCount).foreach(i => addFlow(FemtocraftAPI.getPlasmaManager.loadFlow(list.getCompoundTagAt(i))))

    inputDir = ForgeDirection.getOrientation(compound.getInteger("inputDir"))
    outputDir = ForgeDirection.getOrientation(compound.getInteger("outputDir"))
    maxCapacity = compound.getInteger("capacity")
    stability = compound.getInteger("stability")
    temperatureRating = compound.getInteger("temperature")
  }

  override def addFlow(flow: IPlasmaFlow) =
    if (flows.size >= maxCapacity) {
      false
    } else {
      flows.add(flow)
      true
    }
}
