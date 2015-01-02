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

import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.api.power.plasma.PlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class TileEntityPlasmaConduit extends TileEntityBase implements IPlasmaContainer {
    public static int capacity = 5;
    public static int temperatureRating = 4000;
    public static int stability = 3000;

    //For display purposes
    public boolean containsPlasma = false;
    @Saveable
    private PlasmaContainer plasma = new PlasmaContainer(capacity, stability, temperatureRating);

    private ForgeDirection input;
    private ForgeDirection output;

    private boolean needsCheckInput = false;
    private boolean needsCheckOutput = false;

    public TileEntityPlasmaConduit() {
        super();
        input = ForgeDirection.UNKNOWN;
        output = ForgeDirection.UNKNOWN;
    }

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        input = ForgeDirection.getOrientation(compound.getInteger("input"));
        output = ForgeDirection.getOrientation(compound.getInteger("output"));
        containsPlasma = compound.getBoolean("contain");
        setRenderUpdate();
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        return super.onSideActivate(par5EntityPlayer, side);
    }

    @Override
    public boolean hasDescription() {
        return true;
    }

    @Override
    public void femtocraftServerUpdate() {
        if (needsCheckInput || (getInputDir() != ForgeDirection.UNKNOWN
                                && getInput() == null)) {
            needsCheckInput = false;
            TileEntity te = worldObj.getTileEntity(
                    xCoord + getInputDir().offsetX,
                    yCoord + getInputDir().offsetY, zCoord + getInputDir().offsetZ);
            if (te instanceof IPlasmaContainer) {
                setInput((IPlasmaContainer) te, getInputDir());
            }
        }
        if (needsCheckOutput || (getOutputDir() != ForgeDirection.UNKNOWN
                                 && getOutput() == null)) {
            needsCheckOutput = false;
            TileEntity te = worldObj.getTileEntity(
                    xCoord + getOutputDir().offsetX,
                    yCoord + getOutputDir().offsetY, zCoord + getOutputDir().offsetZ);
            if (te instanceof IPlasmaContainer) {
                setOutput((IPlasmaContainer) te, getOutputDir());
            }
        }
        super.femtocraftServerUpdate();


        boolean prev = containsPlasma;
        update(this, worldObj, xCoord, yCoord, zCoord);
        containsPlasma = hasFlows();
        if (prev != containsPlasma) {
            setUpdate();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        if (getInputDir() != ForgeDirection.UNKNOWN) {
            needsCheckInput = true;
        }
        if (getOutputDir() != ForgeDirection.UNKNOWN) {
            needsCheckOutput = true;
        }
    }

    @Override
    public void saveToDescriptionCompound(NBTTagCompound compound) {
        super.saveToDescriptionCompound(compound);
        compound.setInteger("input", getInputDir().ordinal());
        compound.setInteger("output", getOutputDir().ordinal());
        compound.setBoolean("contain", containsPlasma);
    }

    public boolean hasFlows() {
        return worldObj.isRemote ? containsPlasma : getFlows() != null && !getFlows().isEmpty();
    }

    public void searchForConnections() {
        searchForInput();
        searchForOutput();
    }

    public boolean searchForInput() {
        if (getInput() != null) {
            return true;
        }
        for (int i = 0; i < 6; ++i) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ);
            if (te instanceof IPlasmaContainer) {
                IPlasmaContainer plasmaContainer = (IPlasmaContainer) te;
                if (plasmaContainer.getOutput() == null) {
                    if (plasmaContainer.setOutput(this, dir.getOpposite())) {
                        if (setInput(plasmaContainer, dir)) {
                            return true;
                        } else {
                            plasmaContainer.setOutput(null, ForgeDirection.UNKNOWN);
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean searchForOutput() {
        if (getOutput() != null) {
            return true;
        }
        for (int i = 0; i < 6; ++i) {
            ForgeDirection dir = ForgeDirection.getOrientation(i);
            TileEntity te = worldObj.getTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ);
            if (te instanceof IPlasmaContainer) {
                IPlasmaContainer plasmaContainer = (IPlasmaContainer) te;
                if (plasmaContainer.getInput() == null) {
                    if (plasmaContainer.setInput(this, dir.getOpposite())) {
                        if (setOutput(plasmaContainer, dir)) {
                            return true;
                        } else {
                            plasmaContainer.setInput(null, ForgeDirection.UNKNOWN);
                        }
                    }
                }
            }
        }
        return false;
    }

    public void clearInput() {
        if (getInput() == null) return;
        if (getInput() != this) {
            if (getInput().getOutput() == this) {
                IPlasmaContainer container = getInput();
                plasma.setInput(null, ForgeDirection.UNKNOWN);
                setModified();
                setUpdate();
                container.setOutput(null, ForgeDirection.UNKNOWN);
            }
        }
        setModified();
        setUpdate();
    }

    public void clearOutput() {
        if (getOutput() == null) return;
        if (getOutput() != this) {
            if (getOutput().getInput() == this) {
                IPlasmaContainer container = getOutput();
                plasma.setOutput(null, ForgeDirection.UNKNOWN);
                setModified();
                setUpdate();
                container.setInput(null, ForgeDirection.UNKNOWN);
            }
        }
        setModified();
        setUpdate();
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
        if (getInput() == container) return true;
        if (getOutput() == container) return false;
        clearInput();
        if (container != this && container != plasma && plasma.setInput(container, dir)) {
            if (getInput() != null && getInput().setOutput(this, dir.getOpposite())) {
                setModified();
                setUpdate();
                return true;
            } else {
                plasma.setInput(null, ForgeDirection.UNKNOWN);
            }
        }
        setModified();
        setUpdate();
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (getOutput() == container) return true;
        if (getInput() == container) return false;
        clearOutput();
        if (container != this && container != plasma && plasma.setOutput(container, dir)) {
            if (getOutput() != null && getOutput().setInput(this, dir.getOpposite())) {
                setModified();
                setUpdate();
                return true;
            } else {
                plasma.setOutput(null, ForgeDirection.UNKNOWN);
            }
        }
        setModified();
        setUpdate();
        return false;
    }

    @Override
    public ForgeDirection getInputDir() {
        return (worldObj != null && worldObj.isRemote) ? input : plasma.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return (worldObj != null && worldObj.isRemote) ? output : plasma.getOutputDir();
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
        plasma.update(container, world, x, y, z);
        setModified();
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        plasma.onVolatilityEvent(event);
        setModified();
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        plasma.onPostVolatilityEvent(event);
        setModified();
    }

    @Override
    public int getTemperatureRating() {
        return plasma.getTemperatureRating();
    }

    @Override
    public int getStabilityRating() {
        return plasma.getStabilityRating();
    }

    public void onBlockBreak() {
        clearInput();
        clearOutput();
    }
}
