package com.itszuvalex.femtocraft.power;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Chris on 8/19/2014.
 */
public class CryogenRegistry {
    private static Map<Integer, CryoReactionReagent<ItemStack>> itemMap = new HashMap<Integer,
            CryoReactionReagent<ItemStack>>();
    private static Map<Integer, CryoReactionReagent<FluidStack>> fluidMap = new HashMap<Integer,
            CryoReactionReagent<FluidStack>>();

    public static class CryoReactionReagent<T> {
        public final T input;
        public final ItemStack output;
        public final float power;

        public CryoReactionReagent(T input, ItemStack output, float power) {
            this.input = input;
            this.output = output;
            this.power = power;
        }
    }

    public static void addCryogenSource(ItemStack input, ItemStack output, float power) {
        itemMap.put(input.itemID, new CryoReactionReagent<ItemStack>(input, output, power));
    }

    public static void addCryogenSource(Item input, ItemStack output, float power) {
        itemMap.put(input.itemID, new CryoReactionReagent<ItemStack>(new ItemStack(input), output, power));
    }

    public static void addCryogenSource(Block input, ItemStack output, float power) {
        itemMap.put(new ItemStack(input).itemID, new CryoReactionReagent<ItemStack>(new ItemStack(input), output,
                power));
    }

    public static void addCryogenSource(FluidStack input, ItemStack output, float power) {
        fluidMap.put(input.fluidID, new CryoReactionReagent<FluidStack>(input, output, power));
    }

    public static void addCryogenSource(Fluid input, ItemStack output, float power) {
        fluidMap.put(input.getID(), new CryoReactionReagent<FluidStack>(new FluidStack(input, 1000), output, power));
    }

    public static CryoReactionReagent<ItemStack> getCryogenSource(ItemStack input) {
        return itemMap.get(input.itemID);
    }

    public static CryoReactionReagent<ItemStack> getCryogenSource(Item input) {
        return itemMap.get(input.itemID);
    }

    public static CryoReactionReagent<ItemStack> getCryogenSource(Block input) {
        return itemMap.get(new ItemStack(input).itemID);
    }

    public static CryoReactionReagent<FluidStack> getCryogenSource(FluidStack input) {
        return fluidMap.get(input.fluidID);
    }

    public static CryoReactionReagent<FluidStack> getCryogenSource(Fluid input) {
        return fluidMap.get(input.getID());
    }
}
