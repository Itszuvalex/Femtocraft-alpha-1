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

package com.itszuvalex.femtocraft.power.plasma;

import com.itszuvalex.femtocraft.power.plasma.volatility.IVolatilityEvent;
import com.itszuvalex.femtocraft.power.plasma.volatility.VolatilityEventMagneticFluctuation;
import com.itszuvalex.femtocraft.power.plasma.volatility.VolatilityEventPlasmaLeak;
import com.itszuvalex.femtocraft.power.plasma.volatility.VolatilityEventTemperatureSpike;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.ISaveable;
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
    public static final double unstableMultiplier = 1.25;
    /**
     * Kelvin
     */
    public static final int energyRequirementMin = 500000;
    public static final int temperatureMin = energyRequirementMin / FemtocraftPlasmaUtils.temperatureToEnergy;
    public static final int getEnergyRequirementMax = 10000000;
    public static final int temperatureMax = getEnergyRequirementMax /
            FemtocraftPlasmaUtils.temperatureToEnergy;
    private static final int volatilityMin = 100;
    private static final int volatilityMax = 10000;
    private Random random = new Random();
    @FemtocraftDataUtils.Saveable
    private int frequency;
    @FemtocraftDataUtils.Saveable
    private long temperature;
    @FemtocraftDataUtils.Saveable
    private int volatility;
    @FemtocraftDataUtils.Saveable
    private int freqPos;

    @FemtocraftDataUtils.Saveable
    private boolean unstable;

    private IPlasmaContainer owner;

    PlasmaFlow() {
        //To be used only for loading from NBT
    }


    public PlasmaFlow(IFusionReaction reaction) {
        unstable = false;
        long energy = random.nextLong() * (getEnergyRequirementMax -
                energyRequirementMin) +
                energyRequirementMin;

        if (energyRequirementMin > reaction.getReactionEnergy()) {
            energy = reaction.getReactionEnergy();
            unstable = true;
        }
        else if (energy > reaction.getReactionEnergy()) {
            energy = reaction.getReactionEnergy();

        }

        reaction.consumeReactionEnergy(energy);

        temperature = energy / FemtocraftPlasmaUtils.temperatureToEnergy;

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
    public void update(IPlasmaContainer container, World world, int x, int y, int z) {
        if (container.getStabilityRating() < getVolatility()) {
            FemtocraftPlasmaUtils.applyEventToContainer(container,
                    onSpontaneousEvent
                            (container),
                    world, x, y, z
            );
        }
        if (container.getTemperatureRating() < getTemperature()) {
            FemtocraftPlasmaUtils.applyEventToContainer(container,
                    onOverheat(container)
                    , world, x, y, z);
        }

        if (freqPos-- <= 0) {
            //Reset freqPos.  We don't want it flooding the game every single
            // tick with bad events.  Despite likely being realistic...
            freqPos = getFrequency();


            if (container.getOutput() == null) {
                FemtocraftPlasmaUtils.applyEventToContainer(container,
                        onIncompleteCircuit(container), world, x, y, z);
            }
            else {
                if (container.getOutput().addFlow(this)) {
                    container.removeFlow(this);
                }
                else {
                    FemtocraftPlasmaUtils.applyEventToContainer(container,
                            onPlasmaOverflow(container), world, x, y, z);
                }
            }
        }

        long prev = temperature;
        temperature *= random.nextFloat() * (temperatureDecayMax -
                temperatureDecayMin) + temperatureDecayMin;
        temperature = temperature < temperatureMin ?
                temperatureMin : temperature;
        updateFreqAndVolatility(prev);
    }

    @Override
    public IPlasmaContainer getContainer() {
        return owner;
    }

    @Override
    public void setContainer(IPlasmaContainer container) {
        owner = container;
    }

    /**
     * @return Frequency of the particles this packet of plasma is composed of
     */
    @Override
    public int getFrequency() {
        return frequency;
    }

    @Override
    public long getTemperature() {
        return temperature;
    }

    @Override
    public void setTemperature(long temperature) {
        this.temperature = temperature;
        this.temperature = this.temperature > temperatureMax ? temperatureMax
                : temperature;
        this.temperature = this.temperature < temperatureMin ? temperatureMin
                : temperature;
        updateFreqAndVolatility(temperature);
    }

    private void updateFreqAndVolatility(long temperaturePrev) {
        frequency *= temperature / temperaturePrev;

        frequency = frequency > frequencyMax ? frequencyMax : frequency;
        frequency = frequency < frequencyMin ? frequencyMin : frequency;

        volatility /= temperature / temperaturePrev;

        volatility = unstable ? (int) (volatility * unstableMultiplier) : volatility;

        volatility = volatility > volatilityMax ? volatilityMax : volatility;
        volatility = volatility < volatilityMin ? volatilityMin : volatility;
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
    public boolean isUnstable() {
        return unstable;
    }

    @Override
    public void setUnstable(boolean isUnstable) {
        unstable = isUnstable;
    }

    @Override
    public void recharge(IFusionReaction reaction) {
        long energy = random.nextInt(getEnergyRequirementMax -
                energyRequirementMin) +
                energyRequirementMin;
        energy -= getTemperature() * FemtocraftPlasmaUtils.temperatureToEnergy;

        if (energy > reaction.getReactionEnergy()) {
            energy = reaction.getReactionEnergy();
            unstable = true;
        }

        reaction.consumeReactionEnergy(energy);

        long prev = temperature;
        temperature = energy / FemtocraftPlasmaUtils.temperatureToEnergy;

        updateFreqAndVolatility(prev);

    }

    @Override
    public void onVolatilityEvent(IVolatilityEvent event) {

    }

    @Override
    public void onPostVolatilityEvent(IVolatilityEvent event) {

    }

    @Override
    public IVolatilityEvent onPlasmaOverflow(IPlasmaContainer container) {
        return new VolatilityEventPlasmaLeak(this, volatility, getVolatilityEventTemperature());
    }

    @Override
    public IVolatilityEvent onIncompleteCircuit(IPlasmaContainer container) {
        return new VolatilityEventPlasmaLeak(this, volatility, getVolatilityEventTemperature());
    }

    @Override
    public IVolatilityEvent onOverheat(IPlasmaContainer container) {
        return new VolatilityEventTemperatureSpike(this, volatility,
                getVolatilityEventTemperature()
        );
    }

    @Override
    public IVolatilityEvent onRecharge(IPlasmaContainer container) {
        return new VolatilityEventMagneticFluctuation(this, volatility, getVolatilityEventTemperature());
    }

    @Override
    public IVolatilityEvent onSpontaneousEvent(IPlasmaContainer container) {
        switch (random.nextInt(4)) {
            case 0:
                return onPlasmaOverflow(container);
            case 1:
                return onIncompleteCircuit(container);
            case 2:
                return onOverheat(container);
            case 3:
                return onRecharge(container);
        }
        return null;
    }

    private int getVolatilityEventTemperature() {
        return (int) (temperature * (
                (random.nextFloat() * .5f) + .5f) * FemtocraftPlasmaUtils.temperatureToEnergy);
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
