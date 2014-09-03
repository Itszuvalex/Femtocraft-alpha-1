package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public class ItemInhibitionCore extends ItemCryogenCore {
    public ItemInhibitionCore(int par1, String unlocalizedName) {
        super(par1, unlocalizedName);
    }

    @Override
    public float getFluidPowerMultiplier(CryogenRegistry.CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.LAVA.getName()) ? 1 : .1f;
    }

    @Override
    public boolean canAcceptFluid(Fluid fluid) {
        return fluid.getName().equals(FluidRegistry.LAVA.getName());
    }
}
