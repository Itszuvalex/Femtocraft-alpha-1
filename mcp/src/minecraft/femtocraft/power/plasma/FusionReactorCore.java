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

import femtocraft.api.IPowerContainer;
import femtocraft.api.PowerContainer;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.power.plasma.volatility.IVolatilityEvent;
import femtocraft.utils.FemtocraftDataUtils;
import femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/7/14.
 */
public class FusionReactorCore implements IFusionReactorCore,
        IPowerContainer, ISaveable {
    public static double ignitionSucessfulPowerMultiplier = .5d;
    public static double reactionLossPerTickMultiplier = .01d;
    //    public static int corePowerMax = 25000000;
//    public static int ignitionThreshold = 15000000;
//    public static int maxContainedFlows = 10;
//    public static int stability = 8000;
//    public static int temperatureRating = 8500;
//    public static int ignitionProcessWindow = 20 * 10;

    private Random random = new Random();

    //State
    @FemtocraftDataUtils.Saveable
    private boolean selfSustaining;
    @FemtocraftDataUtils.Saveable
    private boolean igniting;

    //Parameters
    @FemtocraftDataUtils.Saveable
    private PlasmaContainer plasmaContainer;
    @FemtocraftDataUtils.Saveable
    private PowerContainer powerContainer;
    @FemtocraftDataUtils.Saveable
    private int ignitionProcessWindow;
    @FemtocraftDataUtils.Saveable
    private int ignitionThreshold;
    @FemtocraftDataUtils.Saveable
    private int reactionFailureThreshold;
    @FemtocraftDataUtils.Saveable
    private int plasmaFlowTicksToGenerateMin;
    @FemtocraftDataUtils.Saveable
    private int plasmaFlowTicksToGenerateMax;

    //Ongoing processes
    @FemtocraftDataUtils.Saveable
    private int ignitionProcessTicks;
    @FemtocraftDataUtils.Saveable
    private int ticksToGeneratePlasmaFlow;

    private ArrayList<IFusionReactorComponent> components;

    public FusionReactorCore(int maxContainedFlows, int stability, int temperatureRating, int corePowerMax, int ignitionProcessWindow, int ignitionThreshold, int reactionFailureThreshold, int plasmaFlowTicksToGenerateMin, int plasmaFlowTicksToGenerateMax) {
        this.ignitionProcessWindow = ignitionProcessWindow;
        this.ignitionThreshold = ignitionThreshold;
        this.reactionFailureThreshold = reactionFailureThreshold;
        this.plasmaFlowTicksToGenerateMin = plasmaFlowTicksToGenerateMin;
        this.plasmaFlowTicksToGenerateMax = plasmaFlowTicksToGenerateMax;
        components = new ArrayList<IFusionReactorComponent>();
        plasmaContainer = new PlasmaContainer(maxContainedFlows, stability,
                temperatureRating);
        powerContainer = new PowerContainer(EnumTechLevel.FEMTO, corePowerMax);
        selfSustaining = false;
        igniting = false;
        ignitionProcessTicks = 0;
        ticksToGeneratePlasmaFlow = 0;
    }


    @Override
    public boolean isSelfSustaining() {
        return selfSustaining;
    }

    @Override
    public boolean isIgniting() {
        return igniting;
    }

    @Override
    public int getIgnitionProcessWindow() {
        return ignitionProcessWindow;
    }

    @Override
    public int getReactionThreshold() {
        return ignitionThreshold;
    }

    @Override
    public int getReactionStability() {
        return 0; //TODO
    }

    @Override
    public int getReactionTemperature() {
        return 0;       //TODO
    }

    @Override
    public IPlasmaFlow generateFlow() {
        return new PlasmaFlow(this);
    }

    @Override
    public int getCoreEnergy() {
        return powerContainer.getCurrentPower();
    }

    @Override
    public boolean consumeCoreEnergy(int energy) {
        return powerContainer.consume(energy);
    }

    @Override
    public int contributeCoreEnergy(int energy) {
        return powerContainer.charge(energy);
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
        igniting = true;
        ignitionProcessTicks = 0;
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        igniting = false;
        ignitionProcessTicks = 0;
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
        plasmaContainer.update(world, x, y, z);
        //TODO Reactor reactions
        if (selfSustaining) {
            consume((int) (powerContainer.getCurrentPower() * reactionLossPerTickMultiplier));
            if (ticksToGeneratePlasmaFlow-- <= 0) {
                addFlow(generateFlow());
                generateTicksToPlasmaFlow();
            }
            if (powerContainer.getCurrentPower() < getReactorFailureThreshold()) {
                selfSustaining = false;
                powerContainer.setCurrentPower(0);
            }

        } else if (igniting) {
            ++ignitionProcessTicks;
            if (powerContainer.getCurrentPower() > getReactionThreshold()) {
                powerContainer.consume((int) (powerContainer.getCurrentPower() * ignitionSucessfulPowerMultiplier));
                selfSustaining = true;
                generateTicksToPlasmaFlow();
                endIgnitionProcess(this);
            } else if (ignitionProcessTicks > getIgnitionProcessWindow()) {
                powerContainer.setCurrentPower(0);
                endIgnitionProcess(this);
            }
        }
    }

    private int getReactorFailureThreshold() {
        return reactionFailureThreshold;
    }

    private void generateTicksToPlasmaFlow() {
        ticksToGeneratePlasmaFlow = random.nextInt(plasmaFlowTicksToGenerateMax - plasmaFlowTicksToGenerateMin) + plasmaFlowTicksToGenerateMin;
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
    public boolean canAcceptPowerOfLevel(EnumTechLevel level) {
        return powerContainer.canAcceptPowerOfLevel(level);
    }

    @Override
    public EnumTechLevel getTechLevel() {
        return powerContainer.getTechLevel();
    }

    @Override
    public int getCurrentPower() {
        return powerContainer.getCurrentPower();
    }

    @Override
    public int getMaxPower() {
        return powerContainer.getMaxPower();
    }

    @Override
    public float getFillPercentage() {
        return powerContainer.getFillPercentage();
    }

    @Override
    public float getFillPercentageForCharging() {
        return powerContainer.getFillPercentageForCharging();
    }

    @Override
    public float getFillPercentageForOutput() {
        return powerContainer.getFillPercentageForOutput();
    }

    @Override
    public boolean canCharge() {
        return powerContainer.canCharge();
    }

    @Override
    public int charge(int amount) {
        return powerContainer.charge(amount);
    }

    @Override
    public boolean consume(int amount) {
        return powerContainer.consume(amount);
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this,
                FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);
    }
}
