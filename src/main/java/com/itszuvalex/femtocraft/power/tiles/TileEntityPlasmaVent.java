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

import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.IInterfaceDevice;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.power.plasma.FemtocraftPlasmaUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/10/14.
 */
public class TileEntityPlasmaVent extends TileEntityPlasmaConduit {
    @Saveable
    private ForgeDirection ventDirection;
    @Saveable
    private boolean ventingUnstable;
    @Saveable
    private boolean ventingTemperature;
    @Saveable
    private boolean ventingStability;
    @Saveable
    private double temperatureThreshold;
    @Saveable
    private int stabilityThreshold;
    @Saveable
    private int ventDuration;

    public TileEntityPlasmaVent() {
        super();
        ventDirection = ForgeDirection.UNKNOWN;
        ventingUnstable = false;
        ventingTemperature = false;
        ventingStability = false;
        temperatureThreshold = 0;
        stabilityThreshold = 0;
    }

    public boolean isVentingUnstable() {
        return ventingUnstable;
    }

    public void setVentingUnstable(boolean ventingUnstable) {
        this.ventingUnstable = ventingUnstable;
        setModified();
    }

    public boolean isVentingTemperature() {
        return ventingTemperature;
    }

    public void setVentingTemperature(boolean ventingTemperature) {
        this.ventingTemperature = ventingTemperature;
        setModified();
    }

    public boolean isVentingStability() {
        return ventingStability;
    }

    public void setVentingStability(boolean ventingStability) {
        this.ventingStability = ventingStability;
        setModified();
    }

    public double getTemperatureThreshold() {
        return temperatureThreshold;
    }

    public void setTemperatureThreshold(double temperatureThreshold) {
        this.temperatureThreshold = temperatureThreshold;
        setModified();
    }

    public int getStabilityThreshold() {
        return stabilityThreshold;
    }

    public void setStabilityThreshold(int stabilityThreshold) {
        this.stabilityThreshold = stabilityThreshold;
        setModified();
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        setRenderUpdate();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (ventDuration > 0 || ventDirection == ForgeDirection.UNKNOWN) {
            return;
        }
        for (IPlasmaFlow flow : getFlows()) {
            boolean venting = false;
            if (ventingUnstable && flow.isUnstable()) {
                venting = true;
            }
            if (ventingTemperature && flow.getTemperature() >
                                      temperatureThreshold) {
                venting = true;
            }
            if (ventingStability && flow.getVolatility() >
                                    stabilityThreshold) {
                venting = true;
            }

            if (venting) {
                removeFlow(flow);
                ventFlow(flow);
                setModified();
                return;
            }
        }
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        ItemStack pitem = par5EntityPlayer.getItemInUse();
        if (canPlayerUse(par5EntityPlayer) && pitem != null && pitem
                .getItem() instanceof
                IInterfaceDevice) {
            if (((IInterfaceDevice) pitem.getItem())
                        .getInterfaceLevel().tier >= EnumTechLevel.FEMTO.tier) {
                rotateVent();
                return true;
            }
        }
        return super.onSideActivate(par5EntityPlayer, side);
    }

    public void rotateVent() {
        int dir = ventDirection.ordinal();
        for (int i = 0; i < 6; ++i) {
            ForgeDirection testDir = ForgeDirection.getOrientation((i + dir) % 6);
            if (worldObj.isAirBlock(xCoord + testDir.offsetX,
                    yCoord + testDir.offsetY,
                    zCoord + testDir.offsetZ)) {
                ventDirection = testDir;
                setModified();
                setUpdate();
                return;
            }
        }
        ventDirection = ForgeDirection.UNKNOWN;
        setModified();
        setUpdate();
    }

    private void ventFlow(IPlasmaFlow flow) {
        FemtocraftPlasmaUtils.purgeFlow(flow.getTemperature()
                                        * FemtocraftPlasmaUtils
                        .temperatureToEnergy, 500, flow.getVolatility() / 500,
                worldObj, xCoord + ventDirection
                        .offsetX, yCoord + ventDirection.offsetY,
                zCoord + ventDirection.offsetZ
        );
    }
}
