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
import net.minecraftforge.common.ForgeDirection;

import java.util.Collection;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/7/14.
 */
public class FusionReactorCore implements IFusionReactorCore, IPowerContainer {
    public static int corePowerMax = 25000000;
    public static int ignitionThreshold = 15000000;
    public static int maxContainedFlows = 10;
    public static int stability = 8000;
    public static int temperatureRating = 8500;
    public static int ignitionProcessWindow = 20 * 10;

    @FemtocraftDataUtils.Saveable
    private boolean selfSustaining;
    @FemtocraftDataUtils.Saveable
    private boolean igniting;

    @FemtocraftDataUtils.Saveable
    private PlasmaContainer plasmaContainer;
    @FemtocraftDataUtils.Saveable
    private PowerContainer powerContainer;

    public FusionReactorCore() {
        plasmaContainer = new PlasmaContainer(maxContainedFlows, stability,
                                              temperatureRating);
        powerContainer = new PowerContainer(EnumTechLevel.FEMTO, corePowerMax);
        selfSustaining = false;
        igniting = false;
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
        return 0;
    }

    @Override
    public int getReactionTemperature() {
        return 0;
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
    public void beginIgnitionProcess(IFusionReactorCore core) {
        igniting = true;
    }

    @Override
    public void endIgnitionProcess(IFusionReactorCore core) {
        igniting = false;
    }

    @Override
    public IFusionReactorCore getCore() {
        return this;
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
    public boolean setInput(IPlasmaContainer container, ForgeDirection dir) {
        return false;
    }

    @Override
    public boolean setOutput(IPlasmaContainer container, ForgeDirection dir) {
        return false;
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
    public void updateFlows() {
        plasmaContainer.updateFlows();
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
}
