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
    public static class AssemblerDecompositionRegisterEvent extends
            EventAssemblerRegister {
        public AssemblerDecompositionRegisterEvent(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }

    @Cancelable
    public static class AssemblerRecompositionRegisterEvent extends
            EventAssemblerRegister {
        public AssemblerRecompositionRegisterEvent(
                AssemblerRecipe recipe) {
            super(recipe);
        }
    }
}
