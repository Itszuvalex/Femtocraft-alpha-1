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

import femtocraft.power.plasma.*;
import net.minecraft.world.World;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public class VolatilityEvent implements IVolatilityEvent {
    protected final IPlasmaFlow creator;
    protected int volatilityLevel;
    protected long volatilityEnergy;

    public VolatilityEvent(IPlasmaFlow creator,
                           int volatilityLevel, long volatilityEnergy) {
        this.creator = creator;
        this.volatilityLevel = volatilityLevel;
        this.volatilityEnergy = volatilityEnergy;
        creator.setTemperature(creator.getTemperature() -
                                       volatilityEnergy / FemtocraftPlasmaUtils
                                               .temperatureToEnergy);
    }

    @Override
    public IPlasmaFlow triggeringFlow() {
        return creator;
    }

    @Override
    public int volatilityLevel() {
        return volatilityLevel;
    }

    @Override
    public long volatilityEnergy() {
        return volatilityEnergy;
    }

    @Override
    public void interact(IFusionReactorCore core, World world, int x, int y,
                         int z) {

    }

    @Override
    public void interact(IFusionReactorComponent component, World world,
                         int x, int y, int z) {

    }

    @Override
    public void interact(IPlasmaContainer container, World world, int x,
                         int y, int z) {

    }

    @Override
    public void interact(IPlasmaFlow flow, World world, int x, int y, int z) {

    }
}
