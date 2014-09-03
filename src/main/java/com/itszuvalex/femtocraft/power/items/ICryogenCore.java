package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry.CryoReactionReagent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public interface ICryogenCore {

    /**
     * @param fluid
     * @return Power multiplier when transforming fluid into power.
     */
    float getFluidPowerMultiplier(CryoReactionReagent<FluidStack> fluid);

    /**
     *
     * @param fluid
     * @return True if this core can accept this fluid.
     */
    boolean canAcceptFluid(Fluid fluid);
}
