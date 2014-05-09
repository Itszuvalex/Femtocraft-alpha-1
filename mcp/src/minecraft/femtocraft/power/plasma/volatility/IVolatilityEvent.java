/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.power.plasma.volatility;

import femtocraft.power.plasma.IFusionReactorComponent;
import femtocraft.power.plasma.IFusionReactorCore;
import femtocraft.power.plasma.IPlasmaContainer;
import femtocraft.power.plasma.IPlasmaFlow;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 * <p/>
 * Volatility events are what will make or break a good Fusion Reactor setup.
 * Obviously, if you're here, you're likely implementing new Volatility
 * Events, or looking to see how you have to handle them.
 * <p/>
 * As you can see, some of these things I can't easily enforce.  Yes,
 * you can make reactors that never experience volatility events.  Or ones
 * that keep their stored flows safe, etc.  That's fine,
 * I just ask that you take that to heart and balance accordingly.
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
     * Interact with a Reactor core, if this event is spawned from a Flow
     * currently in the reactor.
     *
     * @param core
     */
    void interact(IFusionReactorCore core, World world, int x, int y, int z);

    /**
     * Interact with the reactor components, if this event is spawned from a
     * Flow currently in the reactor.
     *
     * @param component
     */
    void interact(IFusionReactorComponent component, World world, int x,
                  int y, int z);

    /**
     * Interact with a plasma container, if this event is spawned from a Flow
     * currently in a container.
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
