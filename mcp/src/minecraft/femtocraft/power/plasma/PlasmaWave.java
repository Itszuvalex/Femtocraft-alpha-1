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

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class PlasmaWave {
    private int frequency;
    private int charge;
    private int temperature;

    public PlasmaWave() {

    }

    /*TODO:  Some clever system that supports different levels of Fusion
     Reactor Laser generation, some reason for a Plasma Vent,
     some other reason to form a complete loop back to the reactor...
    */

    /**
     * @return Frequency of the particles this packet of plasma is composed of
     */
    int getFrequency() {
        return 0;
    }

    int getCharge() {
        return 0;
    }

    void merge(PlasmaWave alt) {

    }
}
