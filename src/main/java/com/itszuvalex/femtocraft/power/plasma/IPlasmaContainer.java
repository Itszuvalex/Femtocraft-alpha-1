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

package com.itszuvalex.femtocraft.power.plasma;

import com.itszuvalex.femtocraft.power.plasma.volatility.IVolatilityEvent;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 * <p/>
 * Interface for TileEntities that wish to be able to handle PlasmaFlows.
 * <p/>
 * These TileEntities will be expected to handle these flows in a fair
 * manner, and except for great reason, apply volatility events fairly.
 */
public interface IPlasmaContainer {
    /**
     * @return Plasma travels in a circuit.  This can return NULL,
     * i.e. while the player is placing conduits.  If this returns NULL while
     * in use, expect Volatility Events.
     */
    IPlasmaContainer getInput();

    /**
     * @return Plasma travels in a circuit.  This can return NULL,
     * i.e. while the player is placing conduits.  If this returns NULL while
     * in use, expect Volatility Events.
     */
    IPlasmaContainer getOutput();

    /**
     * @param container Container to set as input to this container.
     * @param dir       Direction of input.
     * @return True if input successfully set.
     */
    boolean setInput(IPlasmaContainer container, ForgeDirection dir);

    /**
     * @param container Container to set as output to this container.
     * @param dir       Direction of output.
     * @return True if output successfully set.
     */
    boolean setOutput(IPlasmaContainer container, ForgeDirection dir);

    /**
     * @return Direction of input, or UNKNOWN if NULL.  Helper function for
     * rendering, basically.
     */
    ForgeDirection getInputDir();

    /**
     * @return Direction of output, or UNKNOWN if NULL.  Helper function for
     * rendering, basically.
     */
    ForgeDirection getOutputDir();

    /**
     * @param flow
     * @return True if flow successfully added, false otherwise.  Expect
     * Volatility Events if this returns false.
     */
    boolean addFlow(IPlasmaFlow flow);

    /**
     * @return All the flows being contained.
     */
    Collection<IPlasmaFlow> getFlows();

    /**
     * @param flow Flow to add
     * @return True if flow contained in the container.
     */
    boolean removeFlow(IPlasmaFlow flow);

    /**
     * @return Maximum number of flows this can contain.
     */
    int getMaxFlows();

    /**
     * Update, which by proxy updates everything inside of it
     *
     * @param world World containing the core
     * @param x     x coordinate of the core
     * @param y     y coordinate of the core
     * @param z     z coordinate of the core
     */
    void update(World world, int x, int y, int z);

    /**
     * @param event Called when a volatility event occurs.
     */
    void onVolatilityEvent(IVolatilityEvent event);

    /**
     * Called once a volatility event has resolved.  This could be used to
     * prevent meltdowns, normalize PlasmaFlows, etc.
     *
     * @param event The event that just
     *              resolved
     */
    void onPostVolatilityEvent(IVolatilityEvent event);

    /**
     * @return A measurement of how high a temperature this can handle.
     */
    int getTemperatureRating();

    /**
     * @return A measurement of what level of instability this can handle.
     */
    int getStabilityRating();
}
