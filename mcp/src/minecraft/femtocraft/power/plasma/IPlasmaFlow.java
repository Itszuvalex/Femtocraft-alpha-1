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

package femtocraft.power.plasma;

import femtocraft.power.plasma.volatility.IVolatilityEvent;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 * <p/>
 * The Evil propagates.
 * <p/>
 * PlasmaFlows are responsible for moving themselves down the container
 * circuit.  This deviates from normal management where the container moves
 * what it contains.  This is more to simulate how the plasma containers
 * exist simply to guide and contain the immense energies being released.
 * They cannot hope to do much more than that.
 * <p/>
 * There is no class registry for implementors of IPlasmaFlow.  This is due
 * to laziness on my part.  If someone is interested in creating custom
 * implementers of IPlasmaFlow and wants them usable in reactors other than
 * those they themselves write, shoot me an email and I'll whip something up.
 */
public interface IPlasmaFlow {
    public static final int temperatureToEnergy = 10000;

    /**
     * Should be called every tick by the container for the plasma.
     *
     * @param container Container currently containing the plasma.
     */
    void onUpdate(IPlasmaContainer container);

    /**
     * @return Container currently holding this plasma flow
     */
    IPlasmaContainer getContainer();

    /**
     * @param container Container to set as owner of this plasma flow
     */
    void setContainer(IPlasmaContainer container);

    /**
     * @return How many ticks needed before the flow moves down the circuit.
     */
    int getFrequency();

    /**
     * @return Temperature of the flow.  Bad things happen if this exceeds
     * the temperature rating of the container.
     */
    int getTemperature();

    /**
     * @param temperature To set temperature of the flow to.  For use by
     *                    other flows / Plasma prewarmers / Cores
     */
    void setTemperature(int temperature);

    /**
     * @return Volatility of the flow.  Bad things happen if this exceeds the
     * stability rating of the container.
     */
    int getVolatility();

    /**
     * @return True if ths flow can be recharged by returning to the core.
     * If not, it will be vented.
     */
    boolean isRechargeable();

    /**
     * Called by the core when it recharges this flow.
     *
     * @param core The core doing the recharging.
     */
    void recharge(IFusionReactorCore core);

    /**
     * Called when the PlasmaFlow is purged from the system,
     * either through a volatility event, Plasma Vent, etc.
     *
     * @param world
     * @param x
     * @param y
     * @param z
     */
    void onPurge(World world, int x, int y, int z);

    /**
     * @param event Event occurring on this plasma flow.
     */
    void onVolatilityEvent(IVolatilityEvent event);

    /**
     * @param event Event that just resolved on this plasma flow.
     */
    void onPostVolatilityEvent(IVolatilityEvent event);

    /**
     * @param container Container containing this flow.
     * @return Volatility event that occurs #IPlasmaContainer addFlow returns
     * false.  Can return NULL if plasma is particularly stable.
     */
    IVolatilityEvent onPlasmaOverflow(IPlasmaContainer container);

    /**
     * @param container Container containing this flow.
     * @return Volatility event that occurs when the Flow fails to move down
     * the circuit do to no #IPlasmaContainer getOutput returning null;
     */
    IVolatilityEvent onIncompleteCircuit(IPlasmaContainer container);

    /**
     * @param container Container containing this flow.
     * @return Volatility event that occurs when the flow's temperature
     * exceeds the temperature rating of its container.  This likely
     * increases the temperature of all flows in the container.
     */
    IVolatilityEvent onOverheat(IPlasmaContainer container);

    /**
     * @param container Container containing this flow.
     * @return The exact means by which flows are recharged is unknown.
     * However, it's a volatile act which could result in consequences.  This
     * mainly returns NULL, but could potentially return something nasty.
     */
    IVolatilityEvent onRecharge(IPlasmaContainer container);

    /**
     * @param container Container containing this flow.
     * @return Volatility event that occurs when this flow's volatility is
     * greater than its container's stability.  This could be anything.
     */
    IVolatilityEvent onSpontaneousEvent(IPlasmaContainer container);
}
