/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */

package com.itszuvalex.femtocraft.configuration;

import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import net.minecraftforge.common.ConfigCategory;
import net.minecraftforge.common.Configuration;

import java.util.ArrayList;
import java.util.Set;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/22/14.
 */
public class FemtocraftConfigAssembler {
    public static final String ENABLE_SECTION_KEY = "assembler_recipe_enabled";
    public static final String SECTION_KEY = "assembler_recipes";
    public static final String CUSTOM_RECIPE_SECTION_KEY = "custom_assembler_recipes";
    private final Configuration config;

    private boolean batchLoading = false;

    public void setBatchLoading(boolean val) {
        batchLoading = val;
        if (!batchLoading && config.hasChanged()) {
            config.save();
        }
    }

    public FemtocraftConfigAssembler(Configuration config) {
        this.config = config;
    }

    public ArrayList<AssemblerRecipe> loadCustomRecipes() {
        ArrayList<AssemblerRecipe> ret = new ArrayList<AssemblerRecipe>();
        ConfigCategory cat = config.getCategory(CUSTOM_RECIPE_SECTION_KEY);
        cat.setComment("Any assembler recipes in this section will be registered first.  Use this to preempt recipe default recipe mappings, if you don't want to change them in the normal section.  Just make sure that the keys in this section are unique, as the Configuration files use key lookups and I make no guarantees about what happens when two objects share the same key.");
        Set<ConfigCategory> techs = cat.getChildren();
        for (ConfigCategory cc : techs) {
            String[] name = cc.getQualifiedName().split("\\" + Configuration.CATEGORY_SPLITTER);
            ret.add(loadAssemblerRecipe(new AssemblerRecipe(), name[name.length - 1]));
        }

        return ret;
    }

    public AssemblerRecipe loadAssemblerRecipe(AssemblerRecipe recipe, String key) {
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, FemtocraftConfigHelper.escapeCategorySplitter(key),
                recipe.getClass(), recipe);
        isEnabled(recipe);
        return recipe;
    }

    public AssemblerRecipe loadAssemblerRecipe(AssemblerRecipe recipe) {
        return loadAssemblerRecipe(recipe, recipe.output.getUnlocalizedName());
    }

    public boolean isEnabled(AssemblerRecipe recipe) {
        boolean result = recipe != null && config.get(ENABLE_SECTION_KEY, FemtocraftConfigHelper.escapeCategorySplitter(recipe.output.getUnlocalizedName()), true).getBoolean(true);
        if (config.hasChanged() && !batchLoading) {
            config.save();
        }
        return result;
    }
}
