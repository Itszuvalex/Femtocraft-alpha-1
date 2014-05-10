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
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEventPlasmaLeak extends VolatilityEvent {
    public VolatilityEventPlasmaLeak(IPlasmaFlow creator,
                                     int volatilityLevel,
                                     long volatilityEnergy) {
        super(creator, volatilityLevel, volatilityEnergy);
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y, int z) {
        core.removeFlow(creator);
    }

    @Override
    public void interact(IFusionReactorComponent component, World world, int x, int y, int z) {
        component.removeFlow(creator);
    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x, int y, int z) {
        container.removeFlow(creator);
    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {

    }
}
