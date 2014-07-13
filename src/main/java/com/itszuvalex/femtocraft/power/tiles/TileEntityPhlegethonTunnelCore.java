package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.api.IPhlegethonTunnelAddon;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelComponent;
import com.itszuvalex.femtocraft.api.IPhlegethonTunnelCore;
import com.itszuvalex.femtocraft.api.PowerContainer;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 */
public class TileEntityPhlegethonTunnelCore extends TileEntityBase implements IPhlegethonTunnelCore {
    public static float PowerGenBase = 150;
    public static int ContainerMax = 10000;
    @FemtocraftDataUtils.Saveable
    private boolean active = false;
    @FemtocraftDataUtils.Saveable
    private MultiBlockInfo info = new MultiBlockInfo();
    @FemtocraftDataUtils.Saveable
    private PowerContainer container = new PowerContainer();

    public TileEntityPhlegethonTunnelCore() {
        container.setTechLevel(EnumTechLevel.FEMTO);
        container.setMaxPower(ContainerMax);
    }

    @Override
    public boolean isActive() {
        return active;
    }

    @Override
    public float getPowerGenBase() {
        return (float) (PowerGenBase / Math.log(getHeight() + 1));
    }

    @Override
    public float getTotalPowerGen() {
        float power = getPowerGenBase();
        for (ForgeDirection dir :
                ForgeDirection.VALID_DIRECTIONS) {
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (te instanceof IPhlegethonTunnelAddon) {
                power += ((IPhlegethonTunnelAddon) te).getPowerContribution(this);
            }
        }
        return power;
    }

    @Override
    public int getHeight() {
        return yCoord;
    }

    @Override
    public boolean activate() {
        if (isActive()) {
            return false;
        }

        active = true;
        setModified();
        notifyTunnelOfChange(active);
        return true;
    }

    @Override
    public boolean deactivate() {
        if (!isActive()) {
            return false;
        }

        active = false;
        setModified();
        notifyTunnelOfChange(active);
        return true;
    }

    private void notifyTunnelOfChange(boolean status) {
        for (int x = -1; x <= 1; x++) {
            for (int y = -1; y <= 1; y++) {
                for (int z = -1; z <= 1; z++) {
                    TileEntity te = worldObj.getBlockTileEntity(xCoord + x, yCoord + y, zCoord + z);
                    if (te instanceof IPhlegethonTunnelComponent) {
                        ((IPhlegethonTunnelComponent) te).onCoreActivityChange(status);
                    }
                }
            }
        }
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (active) {
            charge((int) getTotalPowerGen());
        }
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        boolean val = info.formMultiBlock(world, x, y, z);
        if (val) {
            setModified();
        }
        return val;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        boolean val = info.breakMultiBlock(world, x, y, z);
        if (val) {
            if (isActive()) {
                worldObj.setBlock(xCoord, yCoord, zCoord, Block.lavaStill.blockID);
            }
            else {
                setModified();
            }
        }
        return val;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return container.canAcceptPowerOfLevel(level);
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return container.getTechLevel();
    }

    @Override
    public int getCurrentPower() {
        return container.getCurrentPower();
    }

    @Override
    public int getMaxPower() {
        return container.getMaxPower();
    }

    @Override
    public float getFillPercentage() {
        return container.getFillPercentage();
    }

    @Override
    public float getFillPercentageForCharging() {
        return container.getFillPercentageForCharging();
    }

    @Override
    public float getFillPercentageForOutput() {
        return container.getFillPercentageForOutput();
    }

    @Override
    public boolean canCharge() {
        return container.canCharge();
    }

    @Override
    public int charge(int amount) {
        int charge = container.charge(amount);
        if (charge >= 0) {
            setModified();
        }
        return charge;
    }

    @Override
    public boolean consume(int amount) {
        boolean result = container.consume(amount);
        if (result) {
            setModified();
        }
        return result;
    }

    @Override
    public void onCoreActivityChange(boolean active) {

    }
}
