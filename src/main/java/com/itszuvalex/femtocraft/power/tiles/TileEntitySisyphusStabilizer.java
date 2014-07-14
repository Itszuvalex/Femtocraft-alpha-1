package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.IPhlegethonTunnelAddon;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.api.IPowerBlockContainer;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public class TileEntitySisyphusStabilizer extends TileEntityBase implements IPhlegethonTunnelAddon, IPowerBlockContainer {
    public static float Exponent = 0.4f;
    public static int Offset = 1;
    public static int FlatBonus = 5;

    @FemtocraftDataUtils.Saveable(desc = true)
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
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
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
        return info.isValidMultiBlock() && level == EnumTechLevel.FEMTO;
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return EnumTechLevel.FEMTO;
    }

    @Override
    public int getCurrentPower() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).getCurrentPower();
            }
        }
        return 0;
    }

    @Override
    public int getMaxPower() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).getMaxPower();
            }
        }
        return 0;
    }

    @Override
    public float getFillPercentage() {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).getFillPercentage();
            }
        }
        return 1;
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).getFillPercentageForCharging();
            }
        }
        return 0;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).getFillPercentageForOutput();
            }
        }
        return 0;
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).canCharge();
            }
        }
        return false;
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return info.isValidMultiBlock();
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).charge(amount);
            }
        }
        return 0;
    }

    @Override
    public boolean consume(int amount) {
        if (info.isValidMultiBlock()) {
            TileEntity te = worldObj.getBlockTileEntity(info.x(), info.y(), info.z());
            if (te instanceof IPhlegethonTunnelCore) {
                return ((IPhlegethonTunnelCore) te).consume(amount);
            }
        }
        return false;
    }
}
