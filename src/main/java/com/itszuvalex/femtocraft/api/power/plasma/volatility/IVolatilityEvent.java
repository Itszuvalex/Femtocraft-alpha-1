package com.itszuvalex.femtocraft.api.power.plasma.volatility;

import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorComponent;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import net.minecraft.world.World;

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
public interface IVolatilityEvent {
    /**
     * @return Reference to the flow that caused this event.
     */
    IPlasmaFlow triggeringFlow();

    /**
     * @return Level of volatility inherent in this event.
     */
    int volatilityLevel();

    /**
     * @return Amount of energy invested in this event.
     */
    long volatilityEnergy();

    /**
     * Interact with a Reactor core, if this event is spawned from a Flow currently in the reactor.
     *
     * @param core
     */
    void interact(IFusionReactorCore core, World world, int x, int y, int z);

    /**
     * Interact with the reactor components, if this event is spawned from a Flow currently in the reactor.
     *
     * @param component
     */
    void interact(IFusionReactorComponent component, World world, int x,
                  int y, int z);

    /**
     * Interact with a plasma container, if this event is spawned from a Flow currently in a container.
     *
     * @param container
     */
    void interact(IPlasmaContainer container, World world, int x, int y, int z);

    /**
     * Interact with all plasma flows contained inside of the main block.
     *
     * @param flow
     */
    void interact(IPlasmaFlow flow, World world, int x, int y, int z);
}
