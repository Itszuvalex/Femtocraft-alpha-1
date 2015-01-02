package com.itszuvalex.femtocraft.api.power.plasma.volatility;

import com.itszuvalex.femtocraft.api.power.plasma.IPlasmaFlow;
import com.itszuvalex.femtocraft.power.plasma.FemtocraftPlasmaUtils;

/**
 * Created by Christopher Harris (Itszuvalex) on 5/6/14.
 */
public abstract class VolatilityEvent implements IVolatilityEvent {
    protected final IPlasmaFlow creator;
    protected int volatilityLevel;
    protected long volatilityEnergy;

    public VolatilityEvent(IPlasmaFlow creator,
                           int volatilityLevel, long volatilityEnergy) {
        this.creator = creator;
        this.volatilityLevel = volatilityLevel;
        this.volatilityEnergy = volatilityEnergy;
        creator.setTemperature(creator.getTemperature() -
                               volatilityEnergy / FemtocraftPlasmaUtils
                                       .temperatureToEnergy);
    }

    @Override
    public IPlasmaFlow triggeringFlow() {
        return creator;
    }

    @Override
    public int volatilityLevel() {
        return volatilityLevel;
    }

    @Override
    public long volatilityEnergy() {
        return volatilityEnergy;
    }
}
