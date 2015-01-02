package com.itszuvalex.femtocraft.api;

public enum AssemblerRecipeType {
    Reversible, Decomposition, Recomposition;

    private static final String reverseKey = "Reversible";
    private static final String decompKey = "Decomposition";
    private static final String recompKey = "Recomposition";

    public String getValue() {
        switch (this) {
            case Reversible:
                return reverseKey;
            case Decomposition:
                return decompKey;
            case Recomposition:
                return recompKey;
        }
        return "unknown";
    }

    public static AssemblerRecipeType getRecipe(String key) {
        if (key.equalsIgnoreCase(reverseKey)) return Reversible;
        if (key.equalsIgnoreCase(decompKey)) return Decomposition;
        if (key.equalsIgnoreCase(recompKey)) return Recomposition;
        return null;
    }
}