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

package com.itszuvalex.femtocraft.core.fluids;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.item.EnumRarity;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;

public class FluidMass extends Fluid {

    public FluidMass() {
        super("Mass");
        setUnlocalizedName("FluidMass");
        setLuminosity(1);
        setDensity(5000);
        setTemperature(600);
        setViscosity(3000);
        setGaseous(false);
        setRarity(EnumRarity.rare);
    }

    @Override
    public IIcon getStillIcon() {
        return Femtocraft.blockFluidMass().getBlockTextureFromSide(ForgeDirection.UP.ordinal());
    }

    @Override
    public IIcon getFlowingIcon() {
        return Femtocraft.blockFluidMass().getBlockTextureFromSide(ForgeDirection.NORTH.ordinal());
    }
}
