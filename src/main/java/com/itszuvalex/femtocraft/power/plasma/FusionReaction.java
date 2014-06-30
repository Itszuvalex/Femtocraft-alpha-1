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

import com.itszuvalex.femtocraft.power.plasma.volatility.VolatilityEventMagneticFluctuation;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.ISaveable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

import java.util.Random;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/8/14.
 */
public class FusionReaction implements IFusionReaction, ISaveable {
    public static int overChargeMultiplier = 10;
    public static int stabilityMultiplier = 1000;
    public static double ignitionSuccessfulPowerMultiplier = .5d;
    public static double reactionLossPerTickMultiplier = .99d;
    //References
    IFusionReactorCore core;
    //Random
    private Random random = new Random();
    //Energy
    @FemtocraftDataUtils.Saveable
    private long energy;
    //State
    @FemtocraftDataUtils.Saveable
    private boolean selfSustaining;
    @FemtocraftDataUtils.Saveable
    private boolean igniting;
    //Parameters
    @FemtocraftDataUtils.Saveable
    private int ignitionProcessWindow;
    @FemtocraftDataUtils.Saveable
    private long reactionThreshold;
    @FemtocraftDataUtils.Saveable
    private long reactionFailureThreshold;
    @FemtocraftDataUtils.Saveable
    private int plasmaFlowTicksToGenerateMin;
    @FemtocraftDataUtils.Saveable
    private int plasmaFlowTicksToGenerateMax;
    //Ongoing processes
    @FemtocraftDataUtils.Saveable
    private int ignitionProcessTicks;
    @FemtocraftDataUtils.Saveable
    private int ticksToGeneratePlasmaFlow;

    public FusionReaction(IFusionReactorCore core, int ignitionProcessWindow, long reactionThreshold, long reactionFailureThreshold, int plasmaFlowTicksToGenerateMin, int plasmaFlowTicksToGenerateMax) {
        this.core = core;
        this.ignitionProcessWindow = ignitionProcessWindow;
        this.reactionThreshold = reactionThreshold;
        this.reactionFailureThreshold = reactionFailureThreshold;
        this.plasmaFlowTicksToGenerateMin = plasmaFlowTicksToGenerateMin;
        this.plasmaFlowTicksToGenerateMax = plasmaFlowTicksToGenerateMax;
        energy = 0;

    }


    @Override
    public void update(IFusionReactorCore core, World world, int x, int y,
                       int z) {
        if (selfSustaining) {
            energy *= reactionLossPerTickMultiplier;
            if (ticksToGeneratePlasmaFlow-- <= 0) {
                core.addFlow(generateFlow());
                generateTicksToPlasmaFlow();
            }
            //Overcharged, add more flows
            if (energy > (getReactionThreshold() * overChargeMultiplier)) {
                IPlasmaFlow flow = generateFlow();
                flow.setUnstable(true);
                core.addFlow(flow);
            }
            //If, even after this, core is unstable, Volatility!!
            if (getReactionInstability() > core.getStabilityRating()) {
                long eventEnergy = (long) (random.nextDouble() * this.energy *
                        .25d);
                FemtocraftPlasmaUtils.applyEventToContainer(core, new VolatilityEventMagneticFluctuation(null, getReactionInstability() - core.getStabilityRating(), eventEnergy), world, x, y, z
                );
                energy -= eventEnergy;
            }
            //Reaction collapses, all energy invested is gone.
            if (energy < getReactionFailureThreshold()) {
                selfSustaining = false;
                energy = 0;
            }

        }
        else if (igniting) {
            ++ignitionProcessTicks;
            if (energy > getReactionThreshold()) {
                energy -= (int) (energy *
                        ignitionSuccessfulPowerMultiplier);
                selfSustaining = true;
                generateTicksToPlasmaFlow();
                endIgnitionProcess();
            }
            else if (ignitionProcessTicks > getIgnitionProcessWindow()) {
                energy = 0;
                endIgnitionProcess();
            }
        }
    }

    @Override
    public IFusionReactorCore getCore() {
        return core;
    }

    @Override
    public void setCore(IFusionReactorCore core) {
        this.core = core;
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
    public long getReactionFailureThreshold() {
        return reactionFailureThreshold;
    }

    @Override
    public long getReactionThreshold() {
        return reactionThreshold;
    }

    @Override
    public int getReactionInstability() {
        return (int) ((energy * stabilityMultiplier) / getReactionThreshold());
    }

    @Override
    public IPlasmaFlow generateFlow() {
        return new PlasmaFlow(this);
    }

    @Override
    public long getReactionEnergy() {
        return energy;
    }

    @Override
    public boolean consumeReactionEnergy(long energy) {
        if (this.energy > energy) {
            this.energy -= energy;
            return true;
        }
        return false;
    }

    @Override
    public long contributeReactionEnergy(long energy) {
        if (!(isIgniting() || isSelfSustaining())) {
            return 0;
        }
        this.energy += energy;
        return energy;
    }

    @Override
    public void beginIgnitionProcess() {
        igniting = true;
        ignitionProcessTicks = 0;
    }

    @Override
    public void endIgnitionProcess() {
        igniting = false;
        ignitionProcessTicks = 0;
    }

    @Override
    public void endSelfSustainingReaction() {
        if (!selfSustaining) {
            return;
        }
        selfSustaining = false;
        energy = 0;
    }


    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                                              FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    private void generateTicksToPlasmaFlow() {
        ticksToGeneratePlasmaFlow = random.nextInt(plasmaFlowTicksToGenerateMax - plasmaFlowTicksToGenerateMin) + plasmaFlowTicksToGenerateMin;
    }
}
