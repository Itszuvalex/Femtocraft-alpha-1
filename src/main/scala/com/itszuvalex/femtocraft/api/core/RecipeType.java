package com.itszuvalex.femtocraft.api.core;

/**
 * Created by Chris on 12/1/2014.
 */
public enum RecipeType {
    CRAFTING("Crafting"),
    ASSEMBLER("Assembler"),
    TEMPORAL("Temporal"),
    DIMENSIONAL("Dimensional");

    public final String key;

    RecipeType(String key) {
        this.key = key;
    }


    public static RecipeType getRecipe(String key) {
        switch (key) {
            case "Crafting":
                return CRAFTING;
            case "Assembler":
                return ASSEMBLER;
            case "Temporal":
                return TEMPORAL;
            case "Dimensional":
                return DIMENSIONAL;
            default:
                return null;
        }
    }

}
