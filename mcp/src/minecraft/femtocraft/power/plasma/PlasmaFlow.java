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

import femtocraft.power.plasma.volatility.VolatilityEventTemperatureSpike;
import femtocraft.utils.FemtocraftDataUtils;
import femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/2/14.
 */
public class PlasmaFlow implements IPlasmaFlow, ISaveable {
    public static final float temperatureDecayMin = .9f;
    public static final float temperatureDecayMax = .99f;
    public static final int frequencyMin = 5;
    public static final int frequencyMax = 200;
    /**
     * Kelvin
     */
    public static final int energyRequirementMin = 50000;
    public static final int getEnergyRequirementMax = 100000;
    public static final int temperatureToEnergy = 10000;
    public static final int temperatureMin = energyRequirementMin / temperatureToEnergy;
    public static final int temperatureMax = getEnergyRequirementMax /
            temperatureToEnergy;
    private static final int volatilityMin = 100;
    private static final int volatilityMax = 10000;
    private Random random = new Random();
    @FemtocraftDataUtils.Saveable
    private int frequency;
    @FemtocraftDataUtils.Saveable
    private int temperature;
    @FemtocraftDataUtils.Saveable
    private int volatility;
    @FemtocraftDataUtils.Saveable
    private int freqPos;

    PlasmaFlow() {
        //To be used only for loading from NBT
    }


    public PlasmaFlow(IFusionReactorCore core) {
        boolean unstableFlow = false;
        int energy = random.nextInt(getEnergyRequirementMax -
                                            energyRequirementMin) +
                energyRequirementMin;

        if (energyRequirementMin > core.getCoreEnergy()) {
            energy = core.getCoreEnergy();
            unstableFlow = true;
        }
        else if (energy > core.getCoreEnergy()) {
            energy = core.getCoreEnergy();

        }

        core.consumeCoreEnergy(energy);

        temperature = energy / 10000;

        //Initialize frequency and volatility
        double ratio = random.nextDouble();
        frequency = (int) ((ratio * (frequencyMax - frequencyMin) +
                frequencyMin) * temperature / temperatureMin);
        volatility = (int) (((1 - ratio) * (volatilityMax - volatilityMin) +
                volatilityMin) * temperature / temperatureMin);


        //Flow is at random location in the wave
        freqPos = random.nextInt(frequency + 1);
    }

    @Override
    public void onUpdate(IPlasmaContainer container) {
        if (container.getStabilityRating() < getVolatility()) {
            container.onVolatilityEvent(onSpontaneousEvent(container));
        }
        if (container.getTemperatureRating() < getTemperature()) {
            container.onVolatilityEvent(onOverheat(container));
        }

        if (freqPos-- <= 0) {
            //Reset freqPos.  We don't want it flooding the game every single
            // tick with bad events.  Despite likely being realistic...
            freqPos = getFrequency();

            if (container.getOutput() == null) {
                container.onVolatilityEvent(onIncompleteCircuit(container));
            }
            else {
                if (!container.getOutput().addFlow(this)) {
                    container.onVolatilityEvent(onPlasmaOverflow(container));
                }
                else {
                    container.removeFlow(this);
                }
            }
        }

        int prev = temperature;
        temperature *= random.nextFloat() * (temperatureDecayMax -
                temperatureDecayMin) + temperatureDecayMin;
        temperature = temperature < temperatureMin ?
                temperatureMin : temperature;
        updateFreqAndVolatility(prev);
    }

    private void updateFreqAndVolatility(int temperaturePrev) {
        frequency *= temperature / temperaturePrev;

        frequency = frequency > frequencyMax ? frequencyMax : frequency;
        frequency = frequency < frequencyMin ? frequencyMin : frequency;

        volatility /= temperature / temperaturePrev;

        volatility = volatility > volatilityMax ? volatilityMax : volatility;
        volatility = volatility < volatilityMin ? volatilityMin : volatility;
    }

    /**
     * @return Frequency of the particles this packet of plasma is composed of
     */
    @Override
    public int getFrequency() {
        return frequency;
    }

    @Override
    public int getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(int temperature) {
        int prev = temperature;
        this.temperature = temperature;
        this.temperature = this.temperature > temperatureMax ? temperatureMax
                : temperature;
        this.temperature = this.temperature < temperatureMin ? temperatureMin
                : temperature;
        updateFreqAndVolatility(prev);
    }

    @Override
    public int getVolatility() {
        return volatility;
    }

    @Override
    public boolean isRechargeable() {
        return true;
    }

    @Override
    public void recharge(IFusionReactorCore core) {

    }

    @Override
    public void onPurge(World world, int x, int y, int z) {
        //TODO: Bad things
    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {

    }

    @Override
    public IVolatilityEvent onPlasmaOverflow(IPlasmaContainer container) {
        return null;
    }

    @Override
    public IVolatilityEvent onIncompleteCircuit(IPlasmaContainer container) {
        return null;
    }

    @Override
    public IVolatilityEvent onOverheat(IPlasmaContainer container) {
        return new VolatilityEventTemperatureSpike(this, volatility,
                                                   temperature * temperatureToEnergy);
    }

    @Override
    public IVolatilityEvent onRecharge(IPlasmaContainer container) {
        return null;
    }

    @Override
    public IVolatilityEvent onSpontaneousEvent(IPlasmaContainer container) {
        return null;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this,
                                            FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                                              FemtocraftDataUtils.EnumSaveType.WORLD);
    }
}
