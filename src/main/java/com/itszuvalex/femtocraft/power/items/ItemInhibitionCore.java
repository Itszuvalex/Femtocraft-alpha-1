package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public class ItemInhibitionCore extends ItemCryogenCore {

    public static final int durability = 1000;

    public ItemInhibitionCore(int par1, String unlocalizedName) {
        super(par1, unlocalizedName);
        setMaxDamage(durability);
    }

    @Override
    public int getItemDurabilityCost(CryogenRegistry.CryoReactionReagent<ItemStack> item) {
        return 1;
    }

    @Override
    public int getFluidDurabilityCost(CryogenRegistry.CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.LAVA.getName()) ? 1 : 10;
    }

    @Override
    public float getItemPowerMultiplier(CryogenRegistry.CryoReactionReagent<ItemStack> item) {
        return 1;
    }

    @Override
    public float getFluidPowerMultiplier(CryogenRegistry.CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.LAVA.getName()) ? 1 : .5f;
    }
}
