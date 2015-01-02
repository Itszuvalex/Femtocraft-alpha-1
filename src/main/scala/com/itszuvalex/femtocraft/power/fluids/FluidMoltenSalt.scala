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
package com.itszuvalex.femtocraft.power.fluids

import com.itszuvalex.femtocraft.Femtocraft
import net.minecraft.item.EnumRarity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.fluids.Fluid

/**
 * Created by Christopher Harris (Itszuvalex) on 8/2/14.
 */
class FluidMoltenSalt extends Fluid("Molten Salt") {
  setUnlocalizedName("FluidMoltenSalt")
  setLuminosity(4)
  setDensity(5000)
  setTemperature(1200)
  setViscosity(3000)
  setGaseous(false)
  setRarity(EnumRarity.rare)

  override def getStillIcon = Femtocraft.blockFluidMoltenSalt.getBlockTextureFromSide(ForgeDirection.UP.ordinal)

  override def getFlowingIcon = Femtocraft.blockFluidMoltenSalt.getBlockTextureFromSide(ForgeDirection.NORTH.ordinal)
}
