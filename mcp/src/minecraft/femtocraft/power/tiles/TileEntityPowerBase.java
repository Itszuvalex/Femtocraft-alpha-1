/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package femtocraft.power.tiles;

import femtocraft.api.IPowerBlockContainer;
import femtocraft.api.PowerContainer;
import femtocraft.core.tiles.TileEntityBase;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.utils.FemtocraftDataUtils.Saveable;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.ForgeDirection;

import java.util.Arrays;

public class TileEntityPowerBase extends TileEntityBase implements
        IPowerBlockContainer {
    public boolean[] connections;
    private
    @Saveable(item = true)
    PowerContainer container;
    private float maxPowerPerTick;
    private float maxSizePackets;
    private float distributionBuffer;

    public TileEntityPowerBase() {
        super();
        container = new PowerContainer(EnumTechLevel.MACRO, 250);
        maxPowerPerTick = .05f;
        maxSizePackets = .05f;
        distributionBuffer = .01f;
        connections = new boolean[6];
        Arrays.fill(connections, false);
    }

    public void setMaxStorage(int maxStorage_) {
        container.setMaxPower(maxStorage_);
        setModified();
    }

    public void setCurrentStorage(int currentStorage) {
        container.setCurrentPower(currentStorage);
        setModified();
    }

    public void setTechLevel(EnumTechLevel level) {
        container.setTechLevel(level);
        setModified();
    }

    public boolean isConnected(int i) {
        return connections[i];
    }

    public int numConnections() {
        int count = 0;
        for (int i = 0; i < 6; ++i) {
            if (connections[i]) {
                ++count;
            }
        }
        return count;
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
    public float getFillPercentageForCharging(ForgeDirection from) {
        return getFillPercentage();
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {
        return getFillPercentage();
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        return getFillPercentage() < 1.0f;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        int ret = container.charge(amount);
        if (ret > 0) {
            setModified();
        }
        return ret;
    }

    @Override
    public boolean consume(int amount) {
        if (container.consume(amount)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public void updateEntity() {
        checkConnections();
        super.updateEntity();
    }

    @Override
    public void femtocraftServerUpdate() {
        // Don't do anything for empty containers
        if (getCurrentPower() <= 0) {
            return;
        }

        float percentDifferenceTotal = 0.f;
        int maxSpreadThisTick = (int) ((float) getCurrentPower() * maxPowerPerTick) * numConnections();
        float[] fillPercentages = new float[6];
        for (int i = 0; i < 6; ++i) {
            fillPercentages[i] = getFillPercentageForOutput(ForgeDirection.getOrientation(i));
        }

        //Sum % differences
        for (int i = 0; i < 6; ++i) {
            if (!connections[i]) {
                continue;
            }

            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (te instanceof IPowerBlockContainer) {

                IPowerBlockContainer container = (IPowerBlockContainer) te;
                float percentDif = fillPercentages[i] - container.getFillPercentageForCharging(dir.getOpposite());
                if (percentDif > distributionBuffer) {
                    percentDifferenceTotal += percentDif;
                }
            }
        }

        //Distribute
        for (int i = 0; i < 6; ++i) {
            if (!connections[i]) {
                continue;
            }

            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ);
            if (te instanceof IPowerBlockContainer) {
                IPowerBlockContainer container = (IPowerBlockContainer) te;
                float percentDif = fillPercentages[i] - container.getFillPercentageForCharging(dir.getOpposite());
                if (percentDif > distributionBuffer) {
                    int amountToCharge = (int) Math.ceil(maxSpreadThisTick * percentDif / percentDifferenceTotal);
                    consume(container.charge(dir.getOpposite(), amountToCharge));
                }
            }
        }

        setModified();
    }


    public void checkConnections() {
        boolean changed = false;
        for (int j = 0; j < 6; ++j) {
            boolean prev = connections[j];
            connections[j] = false;
            ForgeDirection offset = ForgeDirection.getOrientation(j);
            int locx = this.xCoord + offset.offsetX;
            int locy = this.yCoord + offset.offsetY;
            int locz = this.zCoord + offset.offsetZ;

            TileEntity checkTile = this.worldObj.getBlockTileEntity(locx, locy,
                    locz);

            if (checkTile instanceof IPowerBlockContainer) {
                IPowerBlockContainer fc = (IPowerBlockContainer) checkTile;
                if (!fc.canConnect(offset.getOpposite())) {
                    continue;
                }
                if (!fc.canAcceptPowerOfLevel(
                        getTechLevel(offset.getOpposite()),
                        offset.getOpposite())) {
                    continue;
                }

                connections[j] = true;
                if (prev != connections[j]) {
                    changed = true;
                }
            }
        }
        if (changed) {
            setModified();
            setRenderUpdate();
        }
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level,
                                         ForgeDirection from) {
        return container.canAcceptPowerOfLevel(level);
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        return container.getTechLevel();
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        return true;
    }
}
