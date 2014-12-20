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
import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorComponent;
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.utils.WorldLocation;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/10/14.
 * <p/>
 * This assumes the IFusionReactorCore is the MultiBlock controller. When the multiblock is formed/broken, it sets the
 * IFusionCore reference from the controller location.
 * <p/>
 * If the core isn't loaded yet when this loads, it sets an internal flag. In the FemtocraftServerUpdate function, it
 * queries the saved coreLocation and attempts to set up the core reference again.
 */
public class TileEntityFemtoStellaratorHousing extends TileEntityBase
        implements IFusionReactorComponent, IMultiBlockComponent {
    public static int temperatureRating;

    @Override
    public void handleDescriptionNBT(NBTTagCompound compound) {
        super.handleDescriptionNBT(compound);
        setRenderUpdate();
    }

    public static int stability;
    private IFusionReactorCore core;
    @Saveable(desc = true)
    private MultiBlockInfo info;
    @Saveable
    private WorldLocation coreLocation;

    public TileEntityFemtoStellaratorHousing() {
        super();
        info = new MultiBlockInfo();
        coreLocation = new WorldLocation();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (core == null && isValidMultiBlock()) {
            TileEntity te = coreLocation.getTileEntity();
            if (te instanceof IFusionReactorCore) {
                core = (IFusionReactorCore) te;
                core.addComponent(this);
            }
        }
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        if (info.formMultiBlock(world, x, y, z)) {
            coreLocation = new WorldLocation(worldObj, info.x(), info.y(),
                    info.z());
            core = (IFusionReactorCore) coreLocation.getTileEntity();
            core.addComponent(this);
            setModified();
            setUpdate();
            return true;
        }
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        if (info.breakMultiBlock(world, x, y, z)) {
            core = null;
            coreLocation = null;
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
    public void beginIgnitionProcess(IFusionReactorCore core) {
        setModified();
        setUpdate();
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        setModified();
        setUpdate();
    }

    @Override
    public IFusionReactorCore getCore() {
        return core;
    }

    @Override
    public void onReactionStop(IFusionReactorCore core) {
        setModified();
        setUpdate();
    }

    @Override
    public IPlasmaContainer getInput() {
        return core != null ? core.getInput() : null;
    }

    @Override
    public IPlasmaContainer getOutput() {
        return core != null ? core.getOutput() : null;
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        return core != null && core.setInput(container, dir);
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        return core != null && core.setOutput(container, dir);
    }

    @Override
    public ForgeDirection getInputDir() {
        return core != null ? core.getInputDir() : null;
    }

    @Override
    public ForgeDirection getOutputDir() {
        return core != null ? core.getOutputDir() : null;
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        return core != null && core.addFlow(flow);
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return core != null ? core.getFlows() : null;
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        return core != null && core.removeFlow(flow);
    }

    @Override
    public int getMaxFlows() {
        return core != null ? core.getMaxFlows() : 0;
    }

    @Override
    public void update(World world, int x, int y, int z) {

    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        setUpdate();
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        setUpdate();
    }

    @Override
    public int getTemperatureRating() {
        return temperatureRating;
    }

    @Override
    public int getStabilityRating() {
        return stability;
    }
}
