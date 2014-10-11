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

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.api.power.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.*;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/25/14.
 */
public class TileEntityMagnetohydrodynamicGenerator extends TileEntityPowerProducer implements IMultiBlockComponent,
        IPowerBlockContainer, IFluidHandler {
    public static final int powerPerMoltenSaltMB = 10;
    public static int contaminatedSaltTankStorage = 100000;
    public static int moltenSaltTankStorage = 100000;
    public static int powerStorage = 200000;
    public static int maxMoltenSaltProcessingPerTick = 100;
    public static double moltenSaltToContaminatedProcessingRatio = .9;
    public static boolean processFluidsWithFullPower = true;
    @Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();
    @Saveable
    private FluidTank moltenSaltTank;
    @Saveable
    private FluidTank contaminatedSaltTank;

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.getFillPercentageForCharging(from);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).getFillPercentageForCharging(from);
                }
            }
        }
        return 1f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.getFillPercentageForOutput(to);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).getFillPercentageForOutput(to);
                }
            }
        }
        return 0f;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.canCharge(from);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).canCharge(from);
                }
            }
        }
        return false;
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.canConnect(from);
            } else {
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
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).canConnect(from);
                }
            }
        }
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.charge(from, amount);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).charge(from, amount);
                }
            }
        }
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.consume(amount);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).consume(amount);
                }
            }
        }
        return false;
    }

    @Override
    public float getFillPercentage() {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.getFillPercentage();
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).getFillPercentage();
                }
            }
        }
        return 1.f;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.canAcceptPowerOfLevel(level, from);
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).canAcceptPowerOfLevel(level, from);
                }
            }
        }
        return false;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return EnumTechLevel.NANO;
    }

    @Override
    public void femtocraftServerUpdate() {
        if (!info.isValidMultiBlock()) {
            return;
        }
        if (info.isController(xCoord, yCoord, zCoord)) {
            if (moltenSaltTank.getFluidAmount() > 0) {
                int amount = Math.min(moltenSaltTank.getFluidAmount(), maxMoltenSaltProcessingPerTick);
                amount = (int) Math.min(
                        amount * moltenSaltToContaminatedProcessingRatio,
                        (contaminatedSaltTank.getCapacity() - contaminatedSaltTank.getFluidAmount()) /
                        moltenSaltToContaminatedProcessingRatio);
                if (!processFluidsWithFullPower) {
                    amount = Math.min(amount, (getMaxPower() - getCurrentPower()) / powerPerMoltenSaltMB);
                }

                moltenSaltTank.drain(amount, true);
                contaminatedSaltTank.fill(new FluidStack(Femtocraft.fluidCooledContaminatedMoltenSalt(), (int) (
                        amount *
                        moltenSaltToContaminatedProcessingRatio)), true);
                float steamGenerators = 0.f;
                for (int i = 0; i < 6; ++i) {
                    ForgeDirection dir = ForgeDirection.getOrientation(i);
                    TileEntity te = worldObj.getBlockTileEntity(
                            xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
                    if (te instanceof TileEntitySteamGenerator) {
                        steamGenerators += TileEntitySteamGenerator.steamGeneratorPercentageMultiplier;
                    }
                }
                charge(ForgeDirection.UNKNOWN, (int) (amount * powerPerMoltenSaltMB * (1. + steamGenerators)));
            }
        } else {
            super.femtocraftServerUpdate();
        }
    }

    public TileEntityMagnetohydrodynamicGenerator() {
        moltenSaltTank = new FluidTank(moltenSaltTankStorage);
        contaminatedSaltTank = new FluidTank(contaminatedSaltTankStorage);
        setMaxStorage(powerStorage);
        setTechLevel(EnumTechLevel.NANO);
    }

    @Override
    public int getCurrentPower() {
        if (isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return super.getCurrentPower();
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).getCurrentPower();
                }
            }
        }
        return 0;
    }

    @Override
    public int getMaxPower() {
        if (isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return powerStorage;
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IPowerBlockContainer) {
                    return ((IPowerBlockContainer) te).getMaxPower();
                }
            }
        }
        return powerStorage;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (resource.getFluid() != Femtocraft.fluidMoltenSalt()) {
            return 0;
        }
        if (isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                int ret = moltenSaltTank.fill(resource, doFill);
                if (ret > 0) {
                    setModified();
                }
                return ret;
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).fill(from, resource, doFill);
                }
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (resource.getFluid() != Femtocraft.fluidCooledContaminatedMoltenSalt()) {
            return null;
        }
        if (isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                FluidStack ret = contaminatedSaltTank.drain(resource.amount, doDrain);
                if (ret != null) {
                    setModified();
                }
                return ret;
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).drain(from, resource, doDrain);
                }
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                FluidStack ret = contaminatedSaltTank.drain(maxDrain, doDrain);
                if (ret != null) {
                    setModified();
                }
                return ret;
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).drain(from, maxDrain, doDrain);
                }
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return fluid == Femtocraft.fluidMoltenSalt();
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).canFill(from, fluid);
                }
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return fluid == Femtocraft.fluidCooledContaminatedMoltenSalt();
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).canFill(from, fluid);
                }
            }
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            if (info.isController(xCoord, yCoord, zCoord)) {
                return new FluidTankInfo[]{moltenSaltTank.getInfo(), contaminatedSaltTank.getInfo()};
            } else {
                TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
                if (te instanceof IFluidHandler) {
                    return ((IFluidHandler) te).getTankInfo(from);
                }
            }
        }
        return new FluidTankInfo[0];
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.formMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean ret = info.breakMultiBlock(world, x, y, z);
        if (ret) {
            setModified();
            setUpdate();
        }
        return ret;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    public IFluidTank getMoltenSaltTank() {
        return moltenSaltTank;
    }

    public IFluidTank getContaminatedSaltTank() {
        return contaminatedSaltTank;
    }

    public void setMoltenSalt(int moltenSalt) {
        if (moltenSaltTank.getFluid() != null) {
            moltenSaltTank.getFluid().amount = moltenSalt;
        } else {
            moltenSaltTank.fill(new FluidStack(Femtocraft.fluidMoltenSalt(), moltenSalt), true);
        }
    }

    public void setContaminatedSalt(int contaminatedSalt) {
        if (contaminatedSaltTank.getFluid() != null) {
            contaminatedSaltTank.getFluid().amount = contaminatedSalt;
        } else {
            contaminatedSaltTank.fill(new FluidStack(Femtocraft.fluidCooledContaminatedMoltenSalt(),
                    contaminatedSalt), true);
        }
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        if (info.isValidMultiBlock() && canPlayerUse(par5EntityPlayer)) {
            par5EntityPlayer.openGui(getMod(), getGuiID(), worldObj, info.x(),
                    info.y(), info.z());
            return true;
        }
        return false;
    }

    @Override
    public int getGuiID() {
        return FemtocraftGuiHandler.NanoMagnetohydrodynamicGeneratorGuiID;
    }
}
