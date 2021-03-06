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

package com.itszuvalex.femtocraft.api.power.plasma;

import com.itszuvalex.femtocraft.api.core.ISaveable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.power.plasma.volatility.IVolatilityEvent;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 * <p/>
 * Class intended for internal use by a TileEntity implementing IPlasmaContainer.
 */
public class PlasmaContainer implements IPlasmaContainer, ISaveable {
    private static final String flowListKey = "Flows";
    @Saveable
    private int maxCapacity;
    @Saveable
    private int stability;
    @Saveable
    private int temperatureRating;
    private ArrayList<IPlasmaFlow> flows;
    private ArrayList<IPlasmaFlow> pendingRemove;
    private IPlasmaContainer input;
    private IPlasmaContainer output;
    private ForgeDirection inputDir;
    private ForgeDirection outputDir;

    public PlasmaContainer(int capacity, int stability, int temperature) {
        maxCapacity = capacity;
        this.stability = stability;
        temperatureRating = temperature;
        flows = new ArrayList<>(capacity);
        pendingRemove = new ArrayList<>(capacity);
        inputDir = ForgeDirection.UNKNOWN;
        outputDir = ForgeDirection.UNKNOWN;
    }

    @Override
    public IPlasmaContainer getInput() {
        return input;
    }

    @Override
    public IPlasmaContainer getOutput() {
        return output;
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        if (container == this) {
            return false;
        }
        input = container;
        inputDir = dir;
        return true;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        if (container == this) {
            return false;
        }
        output = container;
        outputDir = dir;
        return true;
    }

    @Override
    public ForgeDirection getInputDir() {
        return inputDir;
    }

    @Override
    public ForgeDirection getOutputDir() {
        return outputDir;
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        if (flows.size() >= maxCapacity) {
            return false;
        } else {
            flows.add(flow);
            flow.setContainer(this);
            return true;
        }
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        List<IPlasmaFlow> ret = new ArrayList<IPlasmaFlow>();
        Collections.copy(flows, ret);
        ret.removeAll(pendingRemove);
        return ret;
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        if (flows.contains(flow)) {
            pendingRemove.add(flow);
            if (flow.getContainer() == this) {
                flow.setContainer(null);
            }
            return true;
        }
        return false;
    }

    @Override
    public int getMaxFlows() {
        return maxCapacity;
    }

    @Override
    public void update(World world, int x, int y, int z) {
        for (IPlasmaFlow flow : flows) {
            flow.update(this, world, x, y, z);
        }
        if (pendingRemove.size() > 0) {
            flows.removeAll(pendingRemove);
            pendingRemove.clear();
        }
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        for (IPlasmaFlow flow : flows) {
            flow.onVolatilityEvent(event);
        }
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        for (IPlasmaFlow flow : flows) {
            flow.onPostVolatilityEvent(event);
        }
    }

    @Override
    public int getTemperatureRating() {
        return temperatureRating;
    }

    @Override
    public int getStabilityRating() {
        return stability;
    }


    @Override
    public void saveToNBT(NBTTagCompound compound) {
        if (pendingRemove.size() > 0) {
            flows.removeAll(pendingRemove);
            pendingRemove.clear();
        }

        //Save array
        NBTTagList list = new NBTTagList();
        for (IPlasmaFlow flow : flows) {
            NBTTagCompound fc = new NBTTagCompound();
            ((PlasmaFlow) flow).saveToNBT(fc);
            list.appendTag(fc);
        }
        compound.setTag(flowListKey, list);


        compound.setInteger("inputDir", inputDir.ordinal());
        compound.setInteger("outputDir", outputDir.ordinal());
        compound.setInteger("capacity", maxCapacity);
        compound.setInteger("stability", stability);
        compound.setInteger("temperature", temperatureRating);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        //Load array
        NBTTagList list = compound.getTagList(flowListKey, 10);
        for (int i = 0; i < list.tagCount(); ++i) {
            PlasmaFlow flow = new PlasmaFlow();
            flow.loadFromNBT(list.getCompoundTagAt(i));
            addFlow(flow);
        }

        inputDir = ForgeDirection.getOrientation(compound.getInteger("inputDir"));
        outputDir = ForgeDirection.getOrientation(compound.getInteger("outputDir"));
        maxCapacity = compound.getInteger("capacity");
        stability = compound.getInteger("stability");
        temperatureRating = compound.getInteger("temperature");
    }
}
