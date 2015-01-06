package com.itszuvalex.femtocraft.api.managers

import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import com.itszuvalex.femtocraft.api.power.plasma.{IFusionReaction, IPlasmaContainer, IPlasmaFlow}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 * Created by Chris on 1/3/2015.
 */
trait IPlasmaManager {

  /**
   *
   * @return Multiplier to multiply plasma flow energy by to get temperature.
   */
  def energyToTemperature: Double

  /**
   *
   * @return Multiplier to multiply plasma flow temperature by to get energy.
   */
  def temperatureToEnergy: Double

  /**
   *
   * @param reaction Fusion reaction which will be used to generate the flow.
   * @return Factory method for new plasma flows.  May return null if no flow could be generated out of the reaction.
   */
  def generateFlow(reaction: IFusionReaction): IPlasmaFlow

  /**
   *
   * @param nbt
   * @return Plasma flow saved to this nbt compound.
   */
  def loadFlow(nbt: NBTTagCompound): IPlasmaFlow

  /**
   * Performs the dirty work of applying the correct event functions to the correct containers.
   * Calls the correct onVolatilityEvent functions, and applies to all flows in the container as well.
   *
   * @param container Container to apply event to.
   * @param event Volatility event that is occurring.
   * @param world World containing the plasma container.
   * @param x X location in world of the plasma container.
   * @param y Y position in world of the plasma container.
   * @param z Z position in world of the plasma container.
   */
  def applyEventToContainer(container: IPlasmaContainer, event: IVolatilityEvent, world: World, x: Int, y: Int, z: Int)

  /**
   * Usually triggered with a plasma leakage volatility event.
   * Grabs all unstable flows, or flows whose volatility is greater than the volatilityLevel.
   * These flows are then added to the total energy for the entire event, and then spawned in the world as
   * one large plasma leak.
   *
   * @param container Container to extract flows from.
   * @param volatilityEnergy Energy of the triggering volatility event.
   * @param volatilityLevel Amount of instability in the overall reaction.
   * @param energyToSegmentsDividend Number of segments = total energy / this
   * @param plasmaDuration Number of ticks for the plasma flow to persist in the world.
   * @param world World containing the plasma container.
   * @param x X location in world of the plasma container.
   * @param y Y location in world of the plasma container.
   * @param z Z location in world of the plasma container.
   */
  def extractFlowsAndPurge(container: IPlasmaContainer, volatilityEnergy: Long, volatilityLevel: Int,
                           energyToSegmentsDividend: Int, plasmaDuration: Int, world: World, x: Int, y: Int, z: Int)

  /**
   *
   * Performs the actual work of spawning segments in the world.
   *
   * @param volatilityEnergy Energy of the triggering volatility event.
   * @param energyToSegmentsDividend Number of segments = total energy / this
   * @param plasmaDuration Number of ticks for the plasma flow to persist in the world.
   * @param world World containing the plasma container.
   * @param x X location in world of the plasma container.
   * @param y Y location in world of the plasma container.
   * @param z Z location in world of the plasma container.
   */
  def purgeFlow(volatilityEnergy: Long, energyToSegmentsDividend: Int, plasmaDuration: Int, world: World, x: Int,
                y: Int, z: Int)

}
