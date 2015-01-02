package com.itszuvalex.femtocraft.api.core;

import java.util.List;

/**
 * Created by Itszuvalex on 1/2/15.
 */
public interface IRecipeRegistry<Recipe> {
    public List<Recipe> getRecipes();

    public boolean registerRecipe(Recipe recipe);

    public boolean containsRecipe(Recipe recipe);
}
