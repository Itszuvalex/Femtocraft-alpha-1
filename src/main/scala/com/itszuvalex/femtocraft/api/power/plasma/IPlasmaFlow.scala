package com.itszuvalex.femtocraft.api.power.plasma

import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 * <p/>
 * The Evil propagates.
 * <p/>
 * PlasmaFlows are responsible for moving themselves down the container circuit.  This deviates from normal management
 * where the container moves what it contains.  This is more to simulate how the plasma containers exist simply to guide
 * and contain the immense energies being released. They cannot hope to do much more than that.
 * <p/>
 * There is no class registry for implementors of IPlasmaFlow.  This is due to laziness on my part.  If someone is
 * interested in creating custom implementers of IPlasmaFlow and wants them usable in reactors other than those they
 * themselves write, shoot me an email and I'll whip something up.
 */
trait IPlasmaFlow extends ISaveable {
  /**
   * Should be called every tick by the container for the plasma.
   *
   * @param container Container currently containing the plasma.
   */
  def update(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int)

  /**
   * @return Container currently holding this plasma flow
   */
  def getContainer: IPlasmaContainer

  /**
   * @param container Container to set as owner of this plasma flow
   */
  def setContainer(container: IPlasmaContainer)

  /**
   * @return How many ticks needed before the flow moves down the circuit.
   */
  def getFrequency: Int

  /**
   * @return Temperature of the flow.  Bad things happen if this exceeds the temperature rating of the container.
   */
  def getTemperature: Long

  /**
   * @param temperature To set temperature of the flow to.  For use by other flows / Plasma prewarmers / Cores
   */
  def setTemperature(temperature: Long)

  /**
   * @return Volatility of the flow.  Bad things happen if this exceeds the stability rating of the container.
   */
  def getVolatility: Int

  /**
   * @return True if ths flow can be recharged by returning to the core. If not, it will be vented.
   */
  def isRechargeable: Boolean

  /**
   * @return True if flow is unstable.  Unstable flows always return higher volatility.
   */
  def isUnstable: Boolean

  /**
   * @param isUnstable Set the unstable flag of this flow.
   */
  def setUnstable(isUnstable: Boolean)

  /**
   * Called by the core when it recharges this flow.
   *
   * @param reaction The reaction doing the recharging.
   */
  def recharge(reaction: IFusionReaction)

  /**
   * @param event Event occurring on this plasma flow.
   */
  def onVolatilityEvent(event: IVolatilityEvent)

  /**
   * @param event Event that just resolved on this plasma flow.
   */
  def onPostVolatilityEvent(event: IVolatilityEvent)

  /**
   * @param container Container containing this flow.
   * @return Volatility event that occurs #IPlasmaContainer addFlow returns false.  Can return NULL if plasma is
   *         particularly stable.
   */
  def onPlasmaOverflow(container: IPlasmaContainer): IVolatilityEvent

  /**
   * @param container Container containing this flow.
   * @return Volatility event that occurs when the Flow fails to move down the circuit due to #IPlasmaContainer
   *         getOutput returning null;
   */
  def onIncompleteCircuit(container: IPlasmaContainer): IVolatilityEvent

  /**
   * @param container Container containing this flow.
   * @return Volatility event that occurs when the flow's temperature exceeds the temperature rating of its container.
   *         This likely increases the temperature of all flows in the container.
   */
  def onOverheat(container: IPlasmaContainer): IVolatilityEvent

  /**
   * @param container Container containing this flow.
   * @return The exact means by which flows are recharged is unknown. However, it's a volatile act which could result
   *         in consequences.  This mainly returns NULL, but could potentially return something nasty.
   */
  def onRecharge(container: IPlasmaContainer): IVolatilityEvent

  /**
   * @param container Container containing this flow.
   * @return Volatility event that occurs when this flow's volatility is greater than its container's stability.  This
   *         could be anything.
   */
  def onSpontaneousEvent(container: IPlasmaContainer): IVolatilityEvent
}
