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

import com.itszuvalex.femtocraft.FemtocraftGuiConstants;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.core.Configurable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.IPhlegethonTunnelAddon;
import com.itszuvalex.femtocraft.api.power.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.api.power.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
@Configurable
public class TileEntitySisyphusStabilizer extends TileEntityBase implements IPhlegethonTunnelAddon,
        IPowerBlockContainer {
    @Configurable(comment = "Raises the base power generated by the core to this exponent.")
    public static float Exponent = 0.4f;
    @Configurable(comment = "Offset for height.  In this case, 1 is added to the height for the purposes of " +
                            "calculation.")
    public static int Offset = 1;
    @Configurable(comment = "Flat amount of power generated by tick.")
    public static int FlatBonus = 5;

    @Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();

    public TileEntitySisyphusStabilizer() {

    }

    @Override
    public float getPowerContribution(IPhlegethonTunnelCore core) {
        float amount = 0;
        amount += Math.pow(core.getPowerGenBase(), Exponent);
        amount *= Math.log(worldObj.getActualHeight() + Offset - yCoord);
        amount /= 6;
        amount += FlatBonus;
        return amount;
    }

    @Override
    public void onCoreActivityChange(boolean active) {
        setUpdate();
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (isValidMultiBlock() && canPlayerUse(par5EntityPlayer)) {
            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
                    info.y(), info.z());
            return true;
        }
        return false;
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        FemtocraftPowerUtils.distributePower(this, null, worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiConstants.PhlegethonTunnelGuiID();
    }

    @Override
    public boolean isValidMultiBlock() {
        return info != null && info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean result = info.formMultiBlock(world, x, y, z);
        if (result) {
            setModified();
            setUpdate();
        }
        return result;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean result = info.breakMultiBlock(world, x, y, z);
        if (result) {
            setModified();
            setUpdate();
        }
        return result;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).canAcceptPowerOfLevel(level, from);
            }
        }
        return false;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getTechLevel(to);
            }
        }
        return null;
    }

    @Override
    public int getCurrentPower() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getCurrentPower();
            }
        }
        return 0;
    }

    @Override
    public int getMaxPower() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getMaxPower();
            }
        }
        return 0;
    }

    @Override
    public float getFillPercentage() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentage();
            }
        }
        return 1.f;
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentageForCharging(from);
            }
        }
        return 1f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentageForOutput(to);
            }
        }
        return 0f;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).canCharge(from);
            }
        }
        return false;
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        if (isValidMultiBlock()) {
            switch (from) {
                case UP:
                    if (yCoord - info.y() != 1) return false;
                    break;
                case DOWN:
                    if (yCoord - info.y() != -1) return false;
                    break;
                case NORTH:
                    if (zCoord - info.z() != -1) return false;
                    break;
                case SOUTH:
                    if (zCoord - info.z() != 1) return false;
                    break;
                case EAST:
                    if (xCoord - info.x() != 1) return false;
                    break;
                case WEST:
                    if (xCoord - info.x() != -1) return false;
                    break;
            }
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).canConnect(from);
            }
        }
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).charge(from, amount);
            }
        }
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).consume(amount);
            }
        }
        return false;
    }
}
