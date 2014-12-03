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
        if (needsCheckInput || plasma.getInputDir() != ForgeDirection.UNKNOWN
                               && plasma.getInput() == null) {
            needsCheckInput = false;
            TileEntity te = worldObj.getTileEntity(
                    xCoord + plasma.getInputDir().offsetX,
                    yCoord + plasma.getInputDir().offsetY, zCoord + plasma.getInputDir().offsetZ);
            if (te instanceof IPlasmaContainer) {
                setInput((IPlasmaContainer) te, plasma.getInputDir());
            }
        }
        if (needsCheckOutput || plasma.getOutputDir() != ForgeDirection.UNKNOWN
                                && plasma.getOutput() == null) {
            needsCheckOutput = false;
            TileEntity te = worldObj.getTileEntity(
                    xCoord + plasma.getOutputDir().offsetX,
                    yCoord + plasma.getOutputDir().offsetY, zCoord + plasma.getOutputDir().offsetZ);
            if (te instanceof IPlasmaContainer) {
                setOutput((IPlasmaContainer) te, plasma.getOutputDir());
            }
        }
        super.femtocraftServerUpdate();


        boolean prev = hasFlows();
        update(worldObj, xCoord, yCoord, zCoord);
        if (prev != hasFlows()) {
            setUpdate();
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound par1nbtTagCompound) {
        super.readFromNBT(par1nbtTagCompound);
        if (plasma.getInputDir() != ForgeDirection.UNKNOWN) {
            needsCheckInput = true;
        }
        if (plasma.getOutputDir() != ForgeDirection.UNKNOWN) {
            needsCheckOutput = true;
        }
    }

    @Override
    public void saveToDescriptionCompound(NBTTagCompound compound) {
        super.saveToDescriptionCompound(compound);
        compound.setInteger("input", plasma.getInputDir().ordinal());
        compound.setInteger("output", plasma.getOutputDir().ordinal());
        compound.setBoolean("contain", hasFlows());
    }

    public boolean hasFlows() {
        return worldObj.isRemote ? containsPlasma : plasma.getFlows() != null && plasma.getFlows()
                                                                                         .size() > 0;
    }

    public void searchForConnections() {
        searchForInput();
        searchForOutput();
    }

    public boolean searchForInput() {
        if (plasma.getInput() != null) {
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
        if (plasma.getOutput() != null) {
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
        if (plasma.getInput() == null) return;
        if (plasma.getInput() != this) {
            if (plasma.getInput().getOutput() == this) {
                IPlasmaContainer container = plasma.getInput();
                plasma.setInput(null, ForgeDirection.UNKNOWN);
                container.setOutput(null, ForgeDirection.UNKNOWN);
            }
        }
        setModified();
        setUpdate();
    }

    public void clearOutput() {
        if (plasma.getOutput() == null) return;
        if (plasma.getOutput() != this) {
            if (plasma.getOutput().getInput() == this) {
                IPlasmaContainer container = plasma.getOutput();
                plasma.setOutput(null, ForgeDirection.UNKNOWN);
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
        if (plasma.getInput() == container) return true;
        if (plasma.getOutput() == container) return false;
        clearInput();
        if (container != this && container != plasma && plasma.setInput(container, dir)) {
            if (plasma.getInput() != null && plasma.getInput().setOutput(this, dir.getOpposite())) {
                setModified();
                setUpdate();
                return true;
            } else {
                plasma.setInput(null, ForgeDirection.UNKNOWN);
            }
        }
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.getOutput() == container) return true;
        if (plasma.getInput() == container) return false;
        clearOutput();
        if (container != this && container != plasma && plasma.setOutput(container, dir)) {
            if (plasma.getOutput() != null && plasma.getOutput().setInput(this, dir.getOpposite())) {
                setModified();
                setUpdate();
                return true;
            } else {
                plasma.setOutput(null, ForgeDirection.UNKNOWN);
            }
        }
        return false;
    }

    @Override
    public ForgeDirection getInputDir() {
        return worldObj.isRemote ? input : plasma.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return worldObj.isRemote ? output : plasma.getOutputDir();
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        if (plasma.addFlow(flow)) {
            containsPlasma = hasFlows();
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
    public void update(World world, int x, int y, int z) {
        plasma.update(world, x, y, z);
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
