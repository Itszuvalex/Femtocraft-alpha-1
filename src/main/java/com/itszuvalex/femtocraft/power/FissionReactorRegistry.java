package com.itszuvalex.femtocraft.power;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.HashMap;

/**
 * Created by Chris on 8/19/2014.
 */
public class FissionReactorRegistry {
    private static HashMap<Integer, FissionReactorReagent> thoriumMap = new HashMap<>();
    private static HashMap<Integer, FissionReactorReagent> saltMap = new HashMap<>();
    private static HashMap<Integer, FissionReactorReagent> heatMap = new HashMap<>();

    public static void init() {
        addFissionReactorHeatSource(new ItemStack(Blocks.snow), 0, -1);
        addFissionReactorHeatSource(new ItemStack(Blocks.ice), 0, -10);
        addFissionReactorHeatSource(new ItemStack(Items.lava_bucket), 0, 100);
        addFissionReactorHeatSource(new ItemStack(Items.fire_charge), 0, 20);
        addFissionReactorHeatSource(new ItemStack(Items.water_bucket), 0, -5);
        addFissionReactorSaltSource(new ItemStack(Femtocraft.itemIngotThFaSalt()), 1000, 10);
        addFissionReactorThoriumSource(new ItemStack(Femtocraft.itemIngotThorium()), 1000, 20);
    }


    public static void addFissionReactorThoriumSource(ItemStack item, int amount, float temp) {
        addFissionReactorThoriumSource(new FissionReactorReagent(item, amount, temp));
    }

    public static void addFissionReactorThoriumSource(FissionReactorReagent reagent) {
        thoriumMap.put(Item.getIdFromItem(reagent.item.getItem()), reagent);
    }

    public static void addFissionReactorSaltSource(ItemStack item, int amount, float temp) {
        addFissionReactorSaltSource(new FissionReactorReagent(item, amount, temp));
    }

    public static void addFissionReactorSaltSource(FissionReactorReagent reagent) {
        saltMap.put(Item.getIdFromItem(reagent.item.getItem()), reagent);
    }

    public static void addFissionReactorHeatSource(ItemStack item, int amount, float heat) {
        addFissionReactorHeatSource(new FissionReactorReagent(item, amount, heat));
    }

    public static void addFissionReactorHeatSource(FissionReactorReagent reagent) {
        heatMap.put(Item.getIdFromItem(reagent.item.getItem()), reagent);
    }

    public static FissionReactorReagent getThoriumSource(ItemStack item) {
        return thoriumMap.get(Item.getIdFromItem(item.getItem()));
    }

    public static FissionReactorReagent getSaltSource(ItemStack item) {
        return saltMap.get(Item.getIdFromItem(item.getItem()));
    }

    public static FissionReactorReagent getHeatSource(ItemStack item) {
        return heatMap.get(Item.getIdFromItem(item.getItem()));
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
