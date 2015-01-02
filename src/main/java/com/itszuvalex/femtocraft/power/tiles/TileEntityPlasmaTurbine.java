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
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.IPowerTileContainer;
import com.itszuvalex.femtocraft.api.power.PowerContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.api.power.plasma.PlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class TileEntityPlasmaTurbine extends TileEntityPowerProducer implements
        IPlasmaContainer, IMultiBlockComponent {
    public static int powerStorage = 500000;
    public static int stabilityRating = 8000;
    public static int temperatureRating = 8000;
    @Saveable
    private PlasmaContainer plasma;
    @Saveable(desc = true)
    private MultiBlockInfo info;

    public TileEntityPlasmaTurbine() {
        super();
        plasma = new PlasmaContainer(5, stabilityRating, temperatureRating);
        info = new MultiBlockInfo();
    }

    @Override
    public PowerContainer defaultContainer() {
        return new PowerContainer(EnumTechLevel.FEMTO, powerStorage);
    }

    @Override
    public float getFillPercentageForCharging(ForgeDirection from) {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.getFillPercentageForCharging(from);
            }
            return c.getFillPercentageForCharging(from);
        }
        return 1.f;
    }

    @Override
    public float getFillPercentageForOutput(ForgeDirection to) {

        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.getFillPercentageForOutput(to);
            }
            return c.getFillPercentageForOutput(to);
        }
        return 0.f;
    }

    private IPowerTileContainer getController() {
        if (!isValidMultiBlock()) {
            return null;
        }
        TileEntity te = worldObj.getTileEntity(info.x(), info.y(),
                info.z());
        if (te instanceof IPowerTileContainer) {
            return (IPowerTileContainer) te;
        }
        return null;
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        if (info.formMultiBlock(world, x, y, z)) {
            setModified();
            setUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        if (info.breakMultiBlock(world, x, y, z)) {
            setModified();
            setUpdate();
            return true;
        }
        return false;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public boolean consume(int amount) {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.consume(amount);
            }
            return c.consume(amount);
        }
        return false;
    }

    @Override
    public int charge(ForgeDirection from, int amount) {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.charge(from, amount);
            }
            return c.charge(from, amount);
        }
        return 0;
    }

    @Override
    public int getMaxPower() {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return powerStorage;
            }
            return c.getMaxPower();
        }
        return 0;
    }

    @Override
    public boolean canAcceptPowerOfLevel(EnumTechLevel level, ForgeDirection from) {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.canAcceptPowerOfLevel(level, from);
            }
            return c.canAcceptPowerOfLevel(level, from);
        }
        return false;
    }

    @Override
    public int getCurrentPower() {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.getCurrentPower();
            }
            return c.getCurrentPower();
        }
        return 0;
    }

    @Override
    public float getFillPercentage() {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return super.getFillPercentage();
            }
            return c.getFillPercentage();
        }
        return 1.f;
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        update(this, worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public boolean canCharge(ForgeDirection from) {
        IPowerTileContainer c = getController();
        return c != null && (c == this || c.canCharge(from));
    }

    @Override
    public boolean canConnect(ForgeDirection from) {
        IPowerTileContainer c = getController();
        return c != null && (c == this || c.canConnect(from));
    }

    @Override
    public EnumTechLevel getTechLevel(ForgeDirection to) {
        IPowerTileContainer c = getController();
        if (c != null) {
            if (c == this) {
                return EnumTechLevel.FEMTO;
            }
            return c.getTechLevel(to);
        }
        return null;
    }

    @Override
    public IPlasmaContainer getInput() {
        return plasma.getInput();
    }

    @Override
    public IPlasmaContainer getOutput() {
        return plasma.getOutput();
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.setInput(container, dir)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.setOutput(container, dir)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public ForgeDirection getInputDir() {
        return plasma.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return plasma.getOutputDir();
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        if (plasma.addFlow(flow)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return plasma.getFlows();
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        if (plasma.removeFlow(flow)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public int getMaxFlows() {
        return plasma.getMaxFlows();
    }

    @Override
    public void update(IPlasmaContainer container, World world, int x, int y, int z) {
        plasma.update(this, world, x, y, z);
        setModified();
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {

    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {

    }

    @Override
    public int getTemperatureRating() {
        return temperatureRating;
    }

    @Override
    public int getStabilityRating() {
        return stabilityRating;
    }

    private boolean isController() {
        return info.isValidMultiBlock() && info.x() == xCoord &&
               info.y() == yCoord && info.z() == zCoord;
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        setRenderUpdate();
    }

}
