package com.itszuvalex.femtocraft.api.power.plasma

import java.util

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 * <p/>
 * The Evil starts here.
 * <p/>
 * The Fusion Reactor Core is the process by which Femtocraft Femto tier power is generated.
 * <p/>
 * Power is drained by the reactor when the ignition process starts.  At this point, there are #ignitionProcessWindow
 * ticks remaining for the core to acquire #ignitionProcessThreshold power.  At this point, the reactor is ignited and
 * it is self-sustaining.
 * <p/>
 * The core then generates plasma flows, likely into internal storage.  These flows complete the circuit and likely end
 * up back in the core.  If they do, then the core can recharge these flows in a positive feedback loop.
 */
trait IFusionReactorCore extends IFusionReactorComponent {
  /**
   * Stops the currently running reaction.  Does nothing if no reaction going.
   */
  def stopReaction()

  /**
   * @return True if the reactor is in self-sustaining state.
   */
  def isSelfSustaining: Boolean

  /**
   * @return True if the reactor is in the process of igniting.
   */
  def isIgniting: Boolean

  /**
   * @return Number of ticks from beginning to end of ignition process.
   */
  def getIgnitionProcessWindow: Int

  /**
   * @return Measure of how stable the reaction occurring in the core is.
   */
  def getReactionInstability: Int

  /**
   * @return Measure of the temperature of the reaction occurring in the core.
   */
  def getReactionTemperature: Long

  /**
   * @return Amount of energy currently invested in the reactor core.
   */
  def getCoreEnergy: Long

  /**
   * @param energy Amount of energy to consume
   * @return True if energy is wholly consumed, false otherwise.
   */
  def consumeCoreEnergy(energy: Long): Boolean

  /**
   * @param energy Amount of energy to add to the core
   * @return Returns the amount of energy used from #energy
   */
  def contributeCoreEnergy(energy: Long): Long

  /**
   * @return All fusion reactor components being utilized by this core.
   */
  def getComponents: util.Collection[IFusionReactorComponent]

  /**
   * @param component Component to add to this core
   * @return True if component successfully added.  False otherwise (I.E. duplicates)
   */
  def addComponent(component: IFusionReactorComponent): Boolean

  /**
   * @param component Component to remove from this core
   * @return True if successfully removed.  False otherwise (i.e. component not a member)
   */
  def removeComponent(component: IFusionReactorComponent): Boolean
}
