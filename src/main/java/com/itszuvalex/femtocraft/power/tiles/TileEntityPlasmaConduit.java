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

import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.power.plasma.PlasmaContainer;
import com.itszuvalex.femtocraft.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class TileEntityPlasmaConduit extends TileEntityBase implements IPlasmaContainer {
    public static int capacity;
    public static int temperatureRating;
    public static int stability;
    //For display purposes
    @FemtocraftDataUtils.Saveable(desc = true)
    public boolean containsPlasma;
    @FemtocraftDataUtils.Saveable
    private PlasmaContainer plasma;
    @FemtocraftDataUtils.Saveable(desc = true)
    private ForgeDirection input;
    @FemtocraftDataUtils.Saveable(desc = true)
    private ForgeDirection output;

    public TileEntityPlasmaConduit() {
        super();
        plasma = new PlasmaContainer(capacity, stability, temperatureRating);
    }

    public void clearInput() {
        plasma.setInput(null, ForgeDirection.UNKNOWN);
        input = ForgeDirection.UNKNOWN;
        setModified();
        setUpdate();
    }

    public void clearOutput() {
        plasma.setOutput(null, ForgeDirection.UNKNOWN);
        output = ForgeDirection.UNKNOWN;
        setModified();
        setUpdate();
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
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ);
            if (te instanceof IPlasmaContainer) {
                IPlasmaContainer plasmaContainer = (IPlasmaContainer) te;
                if (plasmaContainer.getOutput() == null) {
                    plasmaContainer.setOutput(this, dir.getOpposite());
                    return setInput(plasmaContainer, dir);
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
            TileEntity te = worldObj.getBlockTileEntity(xCoord + dir.offsetX,
                    yCoord + dir.offsetY,
                    zCoord + dir.offsetZ);
            if (te instanceof IPlasmaContainer) {
                IPlasmaContainer plasmaContainer = (IPlasmaContainer) te;
                if (plasmaContainer.getInput() == null) {
                    plasmaContainer.setInput(this, dir.getOpposite());
                    return setOutput(plasmaContainer, dir);
                }
            }
        }
        return false;
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        boolean prev = hasFlows();
        update(worldObj, xCoord, yCoord, zCoord);
        containsPlasma = hasFlows();
        if (prev != containsPlasma) {
            setUpdate();
        }
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        return super.onSideActivate(par5EntityPlayer, side);
    }

    private boolean hasFlows() {
        return plasma.getFlows() != null && plasma.getFlows()
                                                  .size() > 0;
    }

    @Override
    public IPlasmaContainer getInput() {
        return plasma.getInput();
    }

    public ForgeDirection getRenderInputDir() {
        return input;
    }

    @Override
    public IPlasmaContainer getOutput() {
        return plasma.getOutput();
    }

    public ForgeDirection getRenderOutputDir() {
        return output;
    }

    public boolean getRenderHasPlasma() {
        return containsPlasma;
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.setInput(container, dir)) {
            input = dir;
            setModified();
            setUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.setOutput(container, dir)) {
            output = dir;
            setModified();
            setUpdate();
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
            containsPlasma = hasFlows();
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
        containsPlasma = hasFlows();
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
}
