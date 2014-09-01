package com.itszuvalex.femtocraft.power.items;

import com.itszuvalex.femtocraft.power.CryogenRegistry.CryoReactionReagent;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

/**
 * Created by Chris on 8/31/2014.
 */
public class ItemNucleationCore extends ItemCryogenCore {
    public static int durability = 1000;

    public ItemNucleationCore(int par1, String unlocalizedName) {
        super(par1, unlocalizedName);
        setMaxDamage(durability);
    }

    @Override
    public int getItemDurabilityCost(CryoReactionReagent<ItemStack> item) {
        return 1;
    }

    @Override
    public int getFluidDurabilityCost(CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.WATER.getName()) ? 1 : 10;
    }

    @Override
    public float getItemPowerMultiplier(CryoReactionReagent<ItemStack> item) {
        return 1;
    }

    @Override
    public float getFluidPowerMultiplier(CryoReactionReagent<FluidStack> fluid) {
        return FluidRegistry.getFluidName(fluid.input).equals(FluidRegistry.WATER.getName()) ? 1 : .5f;
    }
}
