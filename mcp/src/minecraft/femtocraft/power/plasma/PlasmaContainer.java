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

package femtocraft.power.plasma;

import femtocraft.utils.FemtocraftDataUtils;
import femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 * <p/>
 * Class intended for internal use by a TileEntity implementing
 * IPlasmaContainer.  This takes control of the nitty gritty bits,
 * without worrying about input or output.
 */
public class PlasmaContainer implements IPlasmaContainer, ISaveable {
    private static final String flowListKey = "Flows";
    @FemtocraftDataUtils.Saveable
    private int maxCapacity;
    @FemtocraftDataUtils.Saveable
    private int stability;
    @FemtocraftDataUtils.Saveable
    private int temperatureRating;
    private ArrayList<IPlasmaFlow> flows;
    private ArrayList<IPlasmaFlow> pendingRemove;

    public PlasmaContainer(int capacity, int stability, int temperature) {
        maxCapacity = capacity;
        this.stability = stability;
        temperatureRating = temperature;
        flows = new ArrayList<IPlasmaFlow>(capacity);
        pendingRemove = new ArrayList<IPlasmaFlow>(capacity);
    }

    @Override
    public IPlasmaContainer getInput() {
        return null;
    }

    @Override
    public IPlasmaContainer getOutput() {
        return null;
    }

    @Override
    public ForgeDirection getInputDir() {
        return null;
    }

    @Override
    public ForgeDirection getOutputDir() {
        return null;
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        if (flows.size() >= maxCapacity) {
            return false;
        }
        else {
            flows.add(flow);
            return true;
        }
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return flows;
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        if (flows.contains(flow)) {
            pendingRemove.add(flow);
            return true;
        }
        return false;
    }

    @Override
    public int getMaxFlows() {
        return maxCapacity;
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        for (IPlasmaFlow flow : flows) {
            flow.onUpdate(this);
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
    public void updateFlows() {
        for (IPlasmaFlow flow : flows) {
            flow.onUpdate(this);
        }
        if (pendingRemove.size() > 0) {
            flows.removeAll(pendingRemove);
            pendingRemove.clear();
        }
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);

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
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                                              FemtocraftDataUtils.EnumSaveType.WORLD);

        //Load array
        NBTTagList list = compound.getTagList(flowListKey);
        for (int i = 0; i < list.tagCount(); ++i) {
            PlasmaFlow flow = new PlasmaFlow();
            flow.loadFromNBT((NBTTagCompound) list.tagAt(i));
            flows.add(flow);
        }
    }
}
