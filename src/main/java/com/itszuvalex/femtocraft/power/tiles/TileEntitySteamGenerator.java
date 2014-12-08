package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.FemtocraftGuiConstants;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.IPowerTileContainer;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidHandler;

/**
 * Created by Chris on 8/26/2014.
 */
public class TileEntitySteamGenerator extends TileEntityBase implements IMultiBlockComponent,
        IPowerTileContainer, IFluidHandler {

    public static float steamGeneratorPercentageMultiplier = (1.f / 3.f);
    @Saveable(desc = true)
    private MultiBlockInfo info = new MultiBlockInfo();

    public TileEntitySteamGenerator() {
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
        return FemtocraftGuiConstants.NanoMagnetohydrodynamicGeneratorGuiID();
    }

    @Override
    public boolean isValidMultiBlock() {
        return info != null && info.isValidMultiBlock();
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

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).canAcceptPowerOfLevel(level, from);
            }
        }
        return false;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getTechLevel(to);
            }
        }
        return null;
    }

    @Override
    public int getCurrentPower() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getCurrentPower();
            }
        }
        return 0;
    }

    @Override
    public int getMaxPower() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getMaxPower();
            }
        }
        return 0;
    }

    @Override
    public float getFillPercentage() {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getFillPercentage();
            }
        }
        return 1.f;
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getFillPercentageForCharging(from);
            }
        }
        return 1f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).getFillPercentageForOutput(to);
            }
        }
        return 0f;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).canCharge(from);
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
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).canConnect(from);
            }
        }
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).charge(from, amount);
            }
        }
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPowerTileContainer) {
                return ((IPowerTileContainer) te).consume(amount);
            }
        }
        return false;
    }

    @Override
    public int fill(ForgeDirection from, FluidStack resource, boolean doFill) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).fill(from, resource, doFill);
            }
        }
        return 0;
    }

    @Override
    public FluidStack drain(ForgeDirection from, FluidStack resource, boolean doDrain) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).drain(from, resource, doDrain);
            }
        }
        return null;
    }

    @Override
    public FluidStack drain(ForgeDirection from, int maxDrain, boolean doDrain) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).drain(from, maxDrain, doDrain);
            }
        }
        return null;
    }

    @Override
    public boolean canFill(ForgeDirection from, Fluid fluid) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).canFill(from, fluid);
            }
        }
        return false;
    }

    @Override
    public boolean canDrain(ForgeDirection from, Fluid fluid) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).canFill(from, fluid);
            }
        }
        return false;
    }

    @Override
    public FluidTankInfo[] getTankInfo(ForgeDirection from) {
        if (isValidMultiBlock()) {
            TileEntity te = worldObj.getTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IFluidHandler) {
                return ((IFluidHandler) te).getTankInfo(from);
            }
        }
        return new FluidTankInfo[0];
    }
}

