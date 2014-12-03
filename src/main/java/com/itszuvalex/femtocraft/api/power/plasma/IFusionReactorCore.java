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

package com.itszuvalex.femtocraft.api.power.plasma;

import java.util.Collection;

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
public interface IFusionReactorCore extends IFusionReactorComponent {

    /**
     * Stops the currently running reaction.  Does nothing is no reaction going.
     */
    void stopReaction();

    /**
     * @return True if the reactor is in self-sustaining state.
     */
    boolean isSelfSustaining();

    /**
     * @return True if the reactor is in the process of igniting.
     */
    boolean isIgniting();

    /**
     * @return Number of ticks from beginning to end of ignition process.
     */
    int getIgnitionProcessWindow();

    /**
     * @return Measure of how stable the reaction occurring in the core is.
     */
    int getReactionInstability();

    /**
     * @return Measure of the temperature of the reaction occurring in the core.
     */
    long getReactionTemperature();

    /**
     * @return Amount of energy currently invested in the reactor core.
     */
    long getCoreEnergy();

    /**
     * @param energy Amount of energy to consume
     * @return True if energy is wholly consumed, false otherwise.
     */
    boolean consumeCoreEnergy(long energy);

    /**
     * @param energy Amount of energy to add to the core
     * @return Returns the amount of energy used from #energy
     */
    long contributeCoreEnergy(long energy);

    /**
     * @return All fusion reactor components being utilized by this core.
     */
    Collection<IFusionReactorComponent> getComponents();

    /**
     * @param component Component to add to this core
     * @return True if component successfully added.  False otherwise (I.E. duplicates
     */
    boolean addComponent(IFusionReactorComponent component);

    /**
     * @param component Component to remove from this core
     * @return True if successfully removed.  False otherwise (i.e. component not a member)
     */
    boolean removeComponent(IFusionReactorComponent component);
}
