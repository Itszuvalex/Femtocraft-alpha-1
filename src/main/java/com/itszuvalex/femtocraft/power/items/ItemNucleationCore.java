package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry.CryoReactionReagent;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public class ItemNucleationCore extends ItemCryogenCore {

    public ItemNucleationCore(int par1, String unlocalizedName) {
        super(par1, unlocalizedName);
    }


    @Override
    public float getFluidPowerMultiplier(CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.WATER.getName()) ? 1 : .1f;
    }

    @Override
    public boolean canAcceptFluid(Fluid fluid) {
        return fluid.getName().equals(FluidRegistry.WATER.getName());
    }
}
