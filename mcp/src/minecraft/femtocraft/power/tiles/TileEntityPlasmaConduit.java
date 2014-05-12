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

import femtocraft.core.tiles.TileEntityBase;
import femtocraft.power.plasma.IPlasmaContainer;
import femtocraft.power.plasma.IPlasmaFlow;
import femtocraft.power.plasma.PlasmaContainer;
import femtocraft.power.plasma.volatility.IVolatilityEvent;
import femtocraft.utils.FemtocraftDataUtils;
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

    public TileEntityPlasmaConduit() {
        plasma = new PlasmaContainer(capacity, stability, temperatureRating);
    }

    @Override
    public boolean onSideActivate(EntityPlayer par5EntityPlayer, int side) {
        return super.onSideActivate(par5EntityPlayer, side);
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
                    plasma.setInput(plasmaContainer, dir);
                    setModified();
                    setUpdate();
                    setRenderUpdate();
                    return true;
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
                    plasma.setOutput(plasmaContainer, dir);
                    setModified();
                    setUpdate();
                    setRenderUpdate();
                    return true;
                }
            }
        }
        return false;
    }

    public void clearInput() {
        plasma.setInput(null, ForgeDirection.UNKNOWN);
        setModified();
        setUpdate();
        setRenderUpdate();
    }

    public void clearOutput() {
        plasma.setOutput(null, ForgeDirection.UNKNOWN);
        setModified();
        setUpdate();
        setRenderUpdate();
    }

    public void searchForConnections() {
        searchForInput();
        searchForOutput();
    }


    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        boolean prev = hasFlows();
        update(worldObj, xCoord, yCoord, zCoord);
        containsPlasma = hasFlows();
        if (prev != containsPlasma) {
            setUpdate();
            setRenderUpdate();
        }
    }

    private boolean hasFlows() {
        return plasma.getFlows() != null && plasma.getFlows()
                                                  .size() > 0;
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
            setRenderUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (plasma.setOutput(container, dir)) {
            setModified();
            setRenderUpdate();
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
}
