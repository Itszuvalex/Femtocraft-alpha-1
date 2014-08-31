package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.FemtocraftGuiHandler;
import com.itszuvalex.femtocraft.api.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Chris on 8/26/2014.
 */
public class TileEntitySteamGenerator extends TileEntityPowerProducer implements IMultiBlockComponent,
                                                                                 IPowerBlockContainer, IFluidHandler {

    public static float steamGeneratorPercentageMultiplier = (1.f / 3.f);
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();

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

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentageForCharging(from);
            }
        }
        return 1f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentageForOutput(to);
            }
        }
        return 0f;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).canCharge(from);
            }
        }
        return false;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getTechLevel(to);
            }
        }
        return super.getTechLevel(to);
    }

    @Override
    public int getCurrentPower() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getCurrentPower();
            }
        }
        return super.getCurrentPower();
    }

    @Override
    public int getMaxPower() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getMaxPower();
            }
        }
        return super.getMaxPower();
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
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
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).charge(from, amount);
            }
        }
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).consume(amount);
            }
        }
        return false;
    }

    @Override
    public float getFillPercentage() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).getFillPercentage();
            }
        }
        return 1.f;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerBlockContainer) {
                return ((IPowerBlockContainer) te).canAcceptPowerOfLevel(level, from);
            }
        }
        return false;
    }

    public TileEntitySteamGenerator() {
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).fill(from, resource, doFill);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).drain(from, resource, doDrain);
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).drain(from, maxDrain, doDrain);
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).canFill(from, fluid);
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).canFill(from, fluid);
            }
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).getTankInfo(from);
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
}
