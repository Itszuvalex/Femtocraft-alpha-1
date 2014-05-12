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

import femtocraft.power.plasma.volatility.IVolatilityEvent;
import femtocraft.utils.FemtocraftDataUtils;
import femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/7/14.
 */
public class FusionReactorCore implements IFusionReactorCore,
                                          ISaveable {
    //    public static int corePowerMax = 25000000;
//    public static int ignitionThreshold = 15000000;
//    public static int maxContainedFlows = 10;
//    public static int stability = 8000;
//    public static int temperatureRating = 8500;
//    public static int ignitionProcessWindow = 20 * 10;

    //Parameters
    @FemtocraftDataUtils.Saveable
    private PlasmaContainer plasmaContainer;
    @FemtocraftDataUtils.Saveable
    private FusionReaction reaction;


    private ArrayList<IFusionReactorComponent> components;

    public FusionReactorCore(int maxContainedFlows, int stability, int temperatureRating, int ignitionProcessWindow, long reactionThreshold, long reactionFailureThreshold, int plasmaFlowTicksToGenerateMin, int plasmaFlowTicksToGenerateMax) {

        components = new ArrayList<IFusionReactorComponent>();
        plasmaContainer = new PlasmaContainer(maxContainedFlows, stability,
                                              temperatureRating);
        reaction = new FusionReaction(this, ignitionProcessWindow,
                                      reactionThreshold,
                                      reactionFailureThreshold,
                                      plasmaFlowTicksToGenerateMin,
                                      plasmaFlowTicksToGenerateMax);
    }


    @Override
    public boolean isSelfSustaining() {
        return reaction.isSelfSustaining();
    }

    @Override
    public boolean isIgniting() {
        return reaction.isIgniting();
    }

    @Override
    public int getIgnitionProcessWindow() {
        return reaction.getIgnitionProcessWindow();
    }

    @Override
    public int getReactionInstability() {
        return reaction.getReactionInstability();
    }

    @Override
    public long getReactionTemperature() {
        return (long) (reaction.getReactionEnergy() * FemtocraftPlasmaUtils
                .energyToTemperature);
    }

    @Override
    public long getCoreEnergy() {
        return reaction.getReactionEnergy();
    }

    @Override
    public boolean consumeCoreEnergy(long energy) {
        return reaction.consumeReactionEnergy(energy);
    }

    @Override
    public long contributeCoreEnergy(long energy) {
        return reaction.contributeReactionEnergy(energy);
    }

    @Override
    public Collection<IFusionReactorComponent> getComponents() {
        return components;
    }

    @Override
    public boolean addComponent(IFusionReactorComponent component) {
        return !components.contains(component) && components.add(component);
    }

    @Override
    public boolean removeComponent(IFusionReactorComponent component) {
        return components.contains(component) && components.remove(component);
    }


    @Override
    public void beginIgnitionProcess(IFusionReactorCore core) {
        reaction.beginIgnitionProcess();
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        reaction.endIgnitionProcess();
    }

    @Override
    public IFusionReactorCore getCore() {
        return this;
    }

    @Override
    public IPlasmaContainer getInput() {
        return plasmaContainer.getInput();
    }

    @Override
    public IPlasmaContainer getOutput() {
        return plasmaContainer.getOutput();
    }

    @Override
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        return plasmaContainer.setInput(container, dir);
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        return plasmaContainer.setOutput(container, dir);
    }

    @Override
    public ForgeDirection getInputDir() {
        return plasmaContainer.getInputDir();
    }

    @Override
    public ForgeDirection getOutputDir() {
        return plasmaContainer.getOutputDir();
    }

    @Override
    public boolean addFlow(IPlasmaFlow flow) {
        return plasmaContainer.addFlow(flow);
    }

    @Override
    public Collection<IPlasmaFlow> getFlows() {
        return plasmaContainer.getFlows();
    }

    @Override
    public boolean removeFlow(IPlasmaFlow flow) {
        return plasmaContainer.removeFlow(flow);
    }

    @Override
    public int getMaxFlows() {
        return plasmaContainer.getMaxFlows();
    }

    @Override
    public void update(World world, int x, int y, int z) {
        reaction.update(this, world, x, y, z);
        plasmaContainer.update(world, x, y, z);
    }


    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {
        plasmaContainer.onVolatilityEvent(event);
    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {
        plasmaContainer.onPostVolatilityEvent(event);
    }

    @Override
    public int getTemperatureRating() {
        return plasmaContainer.getTemperatureRating();
    }

    @Override
    public int getStabilityRating() {
        return plasmaContainer.getStabilityRating();
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this,
                                            FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);
        reaction.setCore(this);
    }
}
