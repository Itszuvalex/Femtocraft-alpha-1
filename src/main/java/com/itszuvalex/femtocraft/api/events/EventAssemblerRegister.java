package com.itszuvalex.femtocraft.api.events;

import com.itszuvalex.femtocraft.api.AssemblerRecipe;
import cpw.mods.fml.common.eventhandler.Cancelable;
import cpw.mods.fml.common.eventhandler.Event;

@Cancelable
public class EventAssemblerRegister extends Event {

    public final AssemblerRecipe recipe;

    public EventAssemblerRegister(AssemblerRecipe recipe) {
        this.recipe = recipe;
    }

    @Cancelable
    public static class Decomposition extends
            EventAssemblerRegister {
        public Decomposition(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }

    @Cancelable
    public static class Recomposition extends
            EventAssemblerRegister {
        public Recomposition(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }

    @Cancelable
    public static class Reversable extends
            EventAssemblerRegister {
        public Reversable(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }
}
