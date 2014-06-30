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

package com.itszuvalex.femtocraft.power.tiles;

import com.itszuvalex.femtocraft.core.multiblock.IMultiBlockComponent;
import com.itszuvalex.femtocraft.core.multiblock.MultiBlockInfo;
import com.itszuvalex.femtocraft.power.plasma.IPlasmaContainer;
import com.itszuvalex.femtocraft.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.utils.WorldLocation;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.power.plasma.IFusionReactorComponent;
import com.itszuvalex.femtocraft.power.plasma.IFusionReactorCore;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

public class TileEntityFemtoStellaratorOpticalMaser extends
                                                    TileEntityPowerConsumer
        implements IFusionReactorComponent, IMultiBlockComponent {

    public static int powerStorage = 10000000;
    public static int warmupThreshold = 1000000;
    public static int powerTransferPerTick = 50000;
    public static int temperatureRating = 6000;
    public static int stability = 6000;
    @FemtocraftDataUtils.Saveable
    private boolean igniting;
    @FemtocraftDataUtils.Saveable
    private boolean sustaining;
    @FemtocraftDataUtils.Saveable
    private boolean warmed;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;
    private IFusionReactorCore core;
    @FemtocraftDataUtils.Saveable
    private WorldLocation coreLocation;

    public TileEntityFemtoStellaratorOpticalMaser() {
        super();
        setTechLevel(EnumTechLevel.FEMTO);
        setMaxStorage(powerStorage);
        info = new MultiBlockInfo();
        igniting = false;
        sustaining = false;
        warmed = false;
        coreLocation = new WorldLocation();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        if (core == null && isValidMultiBlock()) {
            TileEntity te = coreLocation.getTileEntity();
            if (te instanceof IFusionReactorCore) {
                core = (IFusionReactorCore) te;
            }
        }

        if (igniting || sustaining) {
            if (warmed) {
                if (core == null || !consume(powerTransferPerTick)) {
                    warmed = false;
                    setModified();
                }
                else {
                    //Excess power is lost
                    core.contributeCoreEnergy(powerTransferPerTick);
                    setModified();
                }
            }
            else {
                if (getCurrentPower() >= warmupThreshold) {
                    warmed = true;
                    setModified();
                }
            }
        }
        else {
            warmed = false;
            setModified();
        }
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        if (info.formMultiBlock(world, x, y, z)) {
            setModified();
            coreLocation = new WorldLocation(world, x, y, z);
            core = (IFusionReactorCore) coreLocation.getTileEntity();
            return true;
        }
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        if (info.breakMultiBlock(world, x, y, z)) {
            setModified();
            core = null;
            coreLocation = null;
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
        igniting = true;
        sustaining = false;
        warmed = false;
        setModified();
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        igniting = false;
        sustaining = core.isSelfSustaining();
        setModified();
    }

    @Override
    public IFusionReactorCore getCore() {
        return core;
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
    public boolean setOutput(IPlasmaContainer container, ForgeDirection
            dir) {
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
        return stability;
    }
}
