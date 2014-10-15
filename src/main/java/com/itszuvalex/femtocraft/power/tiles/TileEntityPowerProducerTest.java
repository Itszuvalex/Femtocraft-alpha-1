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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import net.minecraftforge.common.ForgeDirection;

public class TileEntityPowerProducerTest extends TileEntityPowerProducer {
    private int amountPerTick;

    public TileEntityPowerProducerTest() {
        super();
        amountPerTick = 10;
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        charge(ForgeDirection.UNKNOWN, amountPerTick);
    }

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(EnumTechLevel.MICRO, 250);
    }


    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        float val = getFillPercentage();
        return val > .75f ? val : .25f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        return 1.0f;
    }
}
