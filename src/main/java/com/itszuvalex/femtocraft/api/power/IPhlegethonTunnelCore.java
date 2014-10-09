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

package com.itszuvalex.femtocraft.api.power;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 */
public interface IPhlegethonTunnelCore extends IPhlegethonTunnelComponent, IPowerContainer {
    /**
     * @return True if the Phlegethon Tunnel is active.
     */
    boolean isActive();

    /**
     * @return Base amount of power this core generates.
     */
    float getPowerGenBase();

    /**
     * Be careful when calling this from IPhlegethonTunnelAddon.
     * This may cause a recursive loop as the core will request all addon's power contributions.
     *
     * @return Amount of power this core generates, including all 6 neighboring addons.
     */
    float getTotalPowerGen();

    /**
     * @return Returns height of the Phlegethon Tunnel core.
     */
    int getHeight();

    /**
     * @return True if it successfully activates.  If it's already active, it should return false.
     */
    boolean activate();

    /**
     * @return True if it successfully deactivates.  If it's already inactive, it should return false;
     */
    boolean deactivate();
}
