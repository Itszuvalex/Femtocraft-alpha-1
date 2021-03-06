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

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public interface IFusionReactorComponent extends IPlasmaContainer {

    /**
     * Called when the ignition process begins.  Likely started by a player.
     *
     * @param core Core this is a component of.
     */
    void beginIgnitionProcess(IFusionReactorCore core);

    /**
     * Called when the ignition process ends, whether it was successful or not.
     *
     * @param core Core this is a component of.
     */
    void endIgnitionProcess(IFusionReactorCore core);

    /**
     * @return Core this is a component of
     */
    IFusionReactorCore getCore();

    /**
     * Called when a self-sustaining reaction is ended, for any reason.
     *
     * @param core Core this is a component of.
     */
    void onReactionStop(IFusionReactorCore core);

}
