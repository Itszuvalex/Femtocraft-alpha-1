package com.itszuvalex.femtocraft.api.power.plasma

import java.util

import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 * <p/>
 * Interface for TileEntities that wish to be able to handle PlasmaFlows.
 * <p/>
 * These TileEntities will be expected to handle these flows in a fair manner, and except for great reason, apply
 * volatility events fairly.
 */
trait IPlasmaContainer {
  /**
   * @return Plasma travels in a circuit.  This can return NULL, i.e. while the player is placing conduits.  If this
   *         returns NULL while in use, expect Volatility Events.
   */
  def getInput: IPlasmaContainer

  /**
   * @return Plasma travels in a circuit.  This can return NULL, i.e. while the player is placing conduits.  If this
   *         returns NULL while in use, expect Volatility Events.
   */
  def getOutput: IPlasmaContainer

  /**
   * @param container Container to set as input to this container.
   * @param dir       Direction of input.
   * @return True if input successfully set.
   */
  def setInput(container: IPlasmaContainer, dir: ForgeDirection): Boolean

  /**
   * @param container Container to set as output to this container.
   * @param dir       Direction of output.
   * @return True if output successfully set.
   */
  def setOutput(container: IPlasmaContainer, dir: ForgeDirection): Boolean

  /**
   * @return Direction of input, or UNKNOWN if NULL.  Helper function for rendering, basically.
   */
  def getInputDir: ForgeDirection

  /**
   * @return Direction of output, or UNKNOWN if NULL.  Helper function for rendering, basically.
   */
  def getOutputDir: ForgeDirection

  /**
   * @param flow
   * @return True if flow successfully added, false otherwise.  Expect Volatility Events if this returns false.
   */
  def addFlow(flow: IPlasmaFlow): Boolean

  /**
   * @return All the flows being contained.
   */
  def getFlows: util.Collection[IPlasmaFlow]

  /**
   * @param flow Flow to add
   * @return True if flow contained in the container.
   */
  def removeFlow(flow: IPlasmaFlow): Boolean

  /**
   * @return Maximum number of flows this can contain.
   */
  def getMaxFlows: Int

  /**
   * Update, which by proxy updates everything inside of it
   *
   * @param container World containing the core
   * @param world     World containing the core
   * @param x         x coordinate of the core
   * @param y         y coordinate of the core
   */
  def update(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int)

  /**
   * @param event Called when a volatility event occurs.
   */
  def onVolatilityEvent(event: IVolatilityEvent)

  /**
   * Called once a volatility event has resolved.  This could be used to prevent meltdowns, normalize PlasmaFlows,
   * etc.
   *
   * @param event The event that just resolved
   */
  def onPostVolatilityEvent(event: IVolatilityEvent)

  /**
   * @return A measurement of how high a temperature this can handle.
   */
  def getTemperatureRating: Int

  /**
   * @return A measurement of what level of instability this can handle.
   */
  def getStabilityRating: Int
}
