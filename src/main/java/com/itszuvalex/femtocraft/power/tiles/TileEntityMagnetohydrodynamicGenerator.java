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

import com.itszuvalex.femtocraft.api.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
public class TileEntityMagnetohydrodynamicGenerator extends TileEntityBase implements IMultiBlockComponent, IPowerBlockContainer, IFluidHandler {
    @Saveable
    private MultiBlockInfo info = new MultiBlockInfo();

    public TileEntityMagnetohydrodynamicGenerator() {

    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        return new FluidTankInfo[0];
    }

    @Override
    public boolean isValidMultiBlock() {
        return false;
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        return false;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return null;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        return false;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return null;
    }

    @Override
    public int getCurrentPower() {
        return 0;
    }

    @Override
    public int getMaxPower() {
        return 0;
    }

    @Override
    public float getFillPercentage() {
        return 0;
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        return 0;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        return 0;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        return false;
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        return false;
    }
}
