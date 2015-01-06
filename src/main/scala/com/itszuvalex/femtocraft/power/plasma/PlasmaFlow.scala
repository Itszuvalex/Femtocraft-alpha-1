package com.itszuvalex.femtocraft.power.plasma

import java.util.Random

import com.itszuvalex.femtocraft.api.core.{ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import com.itszuvalex.femtocraft.api.power.plasma.{IFusionReaction, IPlasmaContainer, IPlasmaFlow}
import com.itszuvalex.femtocraft.api.utils.FemtocraftDataUtils
import com.itszuvalex.femtocraft.power.plasma.PlasmaFlow._
import com.itszuvalex.femtocraft.power.plasma.volatility.{VolatilityEventMagneticFluctuation, VolatilityEventPlasmaLeak, VolatilityEventTemperatureSpike}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
object PlasmaFlow {
  val temperatureDecayMin    : Float  = .9f
  val temperatureDecayMax    : Float  = .99f
  val frequencyMin           : Int    = 20
  val frequencyMax           : Int    = 200
  val unstableMultiplier     : Double = 1.25
  /**
   * Kelvin
   */
  val energyRequirementMin   : Int    = 500000
  val temperatureMin         : Int    = (energyRequirementMin / ManagerPlasma.temperatureToEnergy).toInt
  val getEnergyRequirementMax: Int    = 1000000
  val temperatureMax         : Int    = (getEnergyRequirementMax / ManagerPlasma.temperatureToEnergy).toInt
  private val volatilityMin: Int = 100
  private val volatilityMax: Int = 10000

  def apply(nbt: NBTTagCompound): PlasmaFlow = {
    val plasma = new PlasmaFlow()
    plasma.loadFromNBT(nbt)
    plasma
  }
}

class PlasmaFlow(reaction: IFusionReaction) extends IPlasmaFlow with ISaveable {
  private           val random     : Random           = new Random
  @Saveable private var frequency  : Int              = 0
  @Saveable private var temperature: Long             = 0L
  @Saveable private var volatility : Int              = 0
  @Saveable private var freqPos    : Int              = 0
  @Saveable private var unstable                      = false
  private           var owner      : IPlasmaContainer = null

  calculateValues()

  def update(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int) {
    if (container.getStabilityRating < getVolatility) {
      ManagerPlasma.applyEventToContainer(container, onSpontaneousEvent(container), world, x, y, z)
    }
    if (container.getTemperatureRating < getTemperature) {
      ManagerPlasma.applyEventToContainer(container, onOverheat(container), world, x, y, z)
    }
    if ( {freqPos -= 1; freqPos + 1} <= 0) {
      freqPos = getFrequency
      if (container.getOutput == null) {
        ManagerPlasma.applyEventToContainer(container, onIncompleteCircuit(container), world, x, y, z)
      } else {
        if (container.getOutput.addFlow(this)) {
          setContainer(container.getOutput)
          container.removeFlow(this)
        } else {
          ManagerPlasma.applyEventToContainer(container, onPlasmaOverflow(container), world, x, y, z)
        }
      }
    }
    val prev = temperature
    temperature = (temperature.toDouble * (random
                                           .nextFloat * (temperatureDecayMax - temperatureDecayMin) + temperatureDecayMin))
                  .toLong
    temperature = Math.max(temperature, temperatureMin)
    updateFreqAndVolatility(prev)
  }

  override def getContainer = owner

  override def setContainer(container: IPlasmaContainer) {
    owner = container
  }

  /**
   * @return Frequency of the particles this packet of plasma is composed of
   */
  override def getFrequency = frequency

  override def getVolatility = volatility

  override def isRechargeable = true

  override def isUnstable = unstable

  override def setUnstable(isUnstable: Boolean) {
    unstable = isUnstable
  }

  override def recharge(reaction: IFusionReaction) {
    var energy: Long = random.nextInt(getEnergyRequirementMax - energyRequirementMin) + energyRequirementMin
    energy -= (getTemperature * ManagerPlasma.temperatureToEnergy).toLong
    if (energy > reaction.getReactionEnergy) {
      energy = reaction.getReactionEnergy
      unstable = true
    }
    reaction.consumeReactionEnergy(energy)
    val prev: Long = temperature
    temperature = (energy / ManagerPlasma.temperatureToEnergy).toLong
    updateFreqAndVolatility(prev)
  }

  override def getTemperature = temperature

  override def setTemperature(temperature: Long) {
    val prevTemp: Long = this.temperature
    this.temperature = temperature
    this.temperature = if (this.temperature > temperatureMax) temperatureMax else this.temperature
    this.temperature = if (this.temperature < temperatureMin) temperatureMin else this.temperature
    updateFreqAndVolatility(prevTemp)
  }

  private def updateFreqAndVolatility(temperaturePrev: Long) {
    frequency = (frequency * (temperature / temperaturePrev)).toInt
    frequency = if (frequency > frequencyMax) frequencyMax else frequency
    frequency = if (frequency < frequencyMin) frequencyMin else frequency
    volatility = (volatility / (temperature.toDouble / temperaturePrev.toDouble)).toInt
    volatility = if (unstable) (volatility * unstableMultiplier).toInt else volatility
    volatility = if (volatility > volatilityMax) volatilityMax else volatility
    volatility = if (volatility < volatilityMin) volatilityMin else volatility
  }

  def onVolatilityEvent(event: IVolatilityEvent) {
  }

  def onPostVolatilityEvent(event: IVolatilityEvent) {
  }

  def onPlasmaOverflow(container: IPlasmaContainer) = new
      VolatilityEventPlasmaLeak(this, volatility, getVolatilityEventTemperature)

  def onIncompleteCircuit(container: IPlasmaContainer) = new
      VolatilityEventPlasmaLeak(this, volatility, getVolatilityEventTemperature)

  def onOverheat(container: IPlasmaContainer) = new
      VolatilityEventTemperatureSpike(this, volatility, getVolatilityEventTemperature)

  def onRecharge(container: IPlasmaContainer) = new
      VolatilityEventMagneticFluctuation(this, volatility, getVolatilityEventTemperature)

  def onSpontaneousEvent(container: IPlasmaContainer) =
    random.nextInt(4) match {
      case 0 => onPlasmaOverflow(container)
      case 1 => onIncompleteCircuit(container)
      case 2 => onOverheat(container)
      case 3 => onRecharge(container)
      case _ => null
    }

  def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  private def calculateValues() {
    if (reaction == null) return

    var energy: Long = (random.nextFloat * (getEnergyRequirementMax - energyRequirementMin) + energyRequirementMin)
                       .toLong
    if (energyRequirementMin > reaction.getReactionEnergy) {
      energy = reaction.getReactionEnergy
      unstable = true
    } else if (energy > reaction.getReactionEnergy) {
      energy = reaction.getReactionEnergy
    }
    reaction.consumeReactionEnergy(energy)
    temperature = (energy / ManagerPlasma.temperatureToEnergy).toLong
    val ratio: Double = random.nextDouble
    frequency = ((ratio * (frequencyMax - frequencyMin) + frequencyMin) * temperature / temperatureMin).toInt
    volatility = (((1 - ratio) * (volatilityMax - volatilityMin) + volatilityMin) * temperature / temperatureMin).toInt
    freqPos = random.nextInt(frequency + 1)
  }

  private[plasma] def this() = this(null)

  private def getVolatilityEventTemperature: Int = {
    (temperature * ((random.nextFloat * .5f) + .5f) * ManagerPlasma.temperatureToEnergy).toInt
  }
}
