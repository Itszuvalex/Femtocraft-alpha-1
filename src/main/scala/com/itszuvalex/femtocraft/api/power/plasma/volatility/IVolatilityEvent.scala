package com.itszuvalex.femtocraft.api.power.plasma.volatility

import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorComponent
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 * <p/>
 * Volatility events are what will make or break a good Fusion Reactor setup. Obviously, if you're here, you're likely
 * implementing new Volatility Events, or looking to see how you have to handle them.
 * <p/>
 * As you can see, some of these things I can't easily enforce.  Yes, you can make reactors that never experience
 * volatility events.  Or ones that keep their stored flows safe, etc.  That's fine, I just ask that you take that to
 * heart and balance accordingly.
 * <p/>
 * All interaction calls are handled by the FemtocraftPlasmaUtils.apply function.  You do not need to loop through the
 * flows in a container passed in the interact() call and call interact upon those flows yourself.  It will be taken
 * care of for you.
 */
trait IVolatilityEvent {
  /**
   * @return Reference to the flow that caused this event.
   */
  def triggeringFlow: IPlasmaFlow

  /**
   * @return Level of volatility inherent in this event.
   */
  def volatilityLevel: Int

  /**
   * @return Amount of energy invested in this event.
   */
  def volatilityEnergy: Long

  /**
   * Interact with a Reactor core, if this event is spawned from a Flow currently in the reactor.
   *
   * @param core
   */
  def interact(core: IFusionReactorCore, world: World, x: Int, y: Int, z: Int)

  /**
   * Interact with the reactor components, if this event is spawned from a Flow currently in the reactor.
   *
   * @param component
   */
  def interact(component: IFusionReactorComponent, world: World, x: Int, y: Int, z: Int)

  /**
   * Interact with a plasma container, if this event is spawned from a Flow currently in a container.
   *
   * @param container
   */
  def interact(container: IPlasmaContainer, world: World, x: Int, y: Int, z: Int)

  /**
   * Interact with all plasma flows contained inside of the main block.
   *
   * @param flow
   */
  def interact(flow: IPlasmaFlow, world: World, x: Int, y: Int, z: Int)
}
