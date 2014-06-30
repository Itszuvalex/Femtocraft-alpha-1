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
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase;
import com.itszuvalex.femtocraft.power.plasma.*;
import com.itszuvalex.femtocraft.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/10/14.
 */
public class TileEntityFemtoStellaratorCore extends TileEntityBase implements
                                                                   IFusionReactorCore, IMultiBlockComponent {
    public static int maxContainedFlows = 10;
    public static int stability = 5000;
    public static int temperatureRating = 8000;
    public static int ignitionProcessWindow = 20 * 10;
    public static long reactionThreshold = 15000000;
    public static long reactionFailureThreshold = 2500000;
    public static int plasmaFlowTicksToGenerateMin = 20;
    public static int plasmaFlowTicksToGenerateMax = 200;
    @FemtocraftDataUtils.Saveable(desc = true)
    private FusionReactorCore core;
    @FemtocraftDataUtils.Saveable(desc = true)
    private MultiBlockInfo info;

    public TileEntityFemtoStellaratorCore() {
        super();
        core = new FusionReactorCore(maxContainedFlows, stability, temperatureRating, ignitionProcessWindow, reactionThreshold, reactionFailureThreshold, plasmaFlowTicksToGenerateMin, plasmaFlowTicksToGenerateMax);
        info = new MultiBlockInfo();
    }

    @Override
    public void femtocraftServerUpdate() {
        super.femtocraftServerUpdate();
        update(worldObj, xCoord, yCoord, zCoord);
    }

    @Override
    public int getGuiID() {
        return super.getGuiID();
    }

    @Override
    public boolean hasGUI() {
        return true;
    }

    @Override
    public boolean isSelfSustaining() {
        return core.isSelfSustaining();
    }

    @Override
    public boolean isIgniting() {
        return core.isIgniting();
    }

    @Override
    public int getIgnitionProcessWindow() {
        return core.getIgnitionProcessWindow();
    }

    @Override
    public int getReactionInstability() {
        return core.getReactionInstability();
    }

    @Override
    public long getReactionTemperature() {
        return core.getReactionTemperature();
    }

    @Override
    public long getCoreEnergy() {
        return core.getCoreEnergy();
    }

    @Override
    public boolean consumeCoreEnergy(long energy) {
        if (core.consumeCoreEnergy(energy)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public long contributeCoreEnergy(long energy) {
        long amount = core.contributeCoreEnergy(energy);
        if (amount > 0) {
            setModified();
        }
        return amount;
    }

    @Override
    public Collection<IFusionReactorComponent> getComponents() {
        return core.getComponents();
    }

    @Override
    public boolean addComponent(IFusionReactorComponent component) {
        if (core.addComponent(component)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public boolean removeComponent(IFusionReactorComponent component) {
        if (core.removeComponent(component)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public void beginIgnitionProcess(IFusionReactorCore core) {
        this.core.beginIgnitionProcess(core);
        for (IFusionReactorComponent component : core.getComponents()) {
            component.beginIgnitionProcess(this);
        }
        setModified();
        setUpdate();
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        this.core.endIgnitionProcess(core);
        for (IFusionReactorComponent component : core.getComponents()) {
            component.endIgnitionProcess(this);
        }
        setModified();
        setUpdate();
    }

    @Override
    public IFusionReactorCore getCore() {
        return this;
    }

    @Override
    public boolean isValidMultiBlock() {
        return info.isValidMultiBlock();
    }

    @Override
    public boolean formMultiBlock(World world, int x, int y, int z) {
        if (info.formMultiBlock(world, x, y, z)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public boolean breakMultiBlock(World world, int x, int y, int z) {
        if (info.breakMultiBlock(world, x, y, z)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public MultiBlockInfo getInfo() {
        return info;
    }

    @Override
    public IPlasmaContainer getInput() {
        return core.getInput();
    }

    @Override
    public IPlasmaContainer getOutput() {
        return core.getOutput();
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        if (core.setInput(container, dir)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (core.setOutput(container, dir)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public ForgeDirection getInputDir() {
        return core.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return core.getOutputDir();
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        if (core.addFlow(flow)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return core.getFlows();
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        if (core.removeFlow(flow)) {
            setModified();
            return true;
        }
        return false;
    }

    @Override
    public int getMaxFlows() {
        return core.getMaxFlows();
    }

    @Override
    public void update(World world, int x, int y, int z) {
        core.update(world, x, y, z);
        setModified();
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        core.onVolatilityEvent(event);
        setModified();
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        core.onPostVolatilityEvent(event);
        setModified();
    }

    @Override
    public int getTemperatureRating() {
        return core.getTemperatureRating();
    }

    @Override
    public int getStabilityRating() {
        return core.getStabilityRating();
    }
}
