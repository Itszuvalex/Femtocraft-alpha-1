package com.itszuvalex.femtocraft.power;

import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by Chris on 8/19/2014.
 */
public class FissionReactorRegistry {
    private static HashMap<Integer, FissionReactorReagent> thoriumMap = new HashMap<Integer, FissionReactorReagent>();
    private static HashMap<Integer, FissionReactorReagent> saltMap = new HashMap<Integer, FissionReactorReagent>();
    private static HashMap<Integer, FissionReactorReagent> heatMap = new HashMap<Integer, FissionReactorReagent>();

    public static void addFissionReactorThoriumSource(ItemStack item, int amount, float temp) {
        addFissionReactorThoriumSource(new FissionReactorReagent(item, amount, temp));
    }

    public static void addFissionReactorThoriumSource(FissionReactorReagent reagent) {
        thoriumMap.put(reagent.item.itemID, reagent);
    }

    public static void addFissionReactorSaltSource(ItemStack item, int amount, float temp) {
        addFissionReactorSaltSource(new FissionReactorReagent(item, amount, temp));
    }

    public static void addFissionReactorSaltSource(FissionReactorReagent reagent) {
        saltMap.put(reagent.item.itemID, reagent);
    }

    public static void addFissionReactorHeatSource(ItemStack item, int amount, float heat) {
        addFissionReactorHeatSource(new FissionReactorReagent(item, amount, heat));
    }

    public static void addFissionReactorHeatSource(FissionReactorReagent reagent) {
        heatMap.put(reagent.item.itemID, reagent);
    }

    public static FissionReactorReagent getThoriumSource(ItemStack item) {
        return thoriumMap.get(item.itemID);
    }

    public static FissionReactorReagent getSaltSource(ItemStack item) {
        return saltMap.get(item.itemID);
    }

    public static FissionReactorReagent getHeatSource(ItemStack item) {
        return heatMap.get(item.itemID);
    }

    public static class FissionReactorReagent {
        public final ItemStack item;
        public final int amount;
        public final float temp;

        public FissionReactorReagent(ItemStack item, int amount, float temp) {
            this.item = item;
            this.amount = amount;
            this.temp = temp;
        }
    }
}
