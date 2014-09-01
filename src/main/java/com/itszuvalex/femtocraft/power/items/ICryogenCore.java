package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry.CryoReactionReagent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public interface ICryogenCore {
    /**
     * @param item
     * @return Durability cost to transform item into power.
     */
    int getItemDurabilityCost(CryoReactionReagent<ItemStack> item);

    /**
     * @param fluid
     * @return Durability cost to transform item into power.
     */
    int getFluidDurabilityCost(CryoReactionReagent<FluidStack> fluid);

    /**
     * @param item
     * @return Power multiplier when transforming item into power.
     */
    float getItemPowerMultiplier(CryoReactionReagent<ItemStack> item);

    /**
     * @param fluid
     * @return Power multiplier when transforming fluid into power.
     */
    float getFluidPowerMultiplier(CryoReactionReagent<FluidStack> fluid);
}
