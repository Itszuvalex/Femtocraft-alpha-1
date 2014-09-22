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
import net.minecraftforge.common.Configuration;

/**
 * Created by Christopher Harris (Itszuvalex) on 9/22/14.
 */
public class FemtocraftConfigAssembler {
    public static final String ENABLE_SECTION_KEY = "assembler_recipe_enabled";
    public static final String SECTION_KEY = "assembler_recipes";
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

    public AssemblerRecipe loadAssemblerRecipe(AssemblerRecipe recipe, String key) {
        FemtocraftConfigHelper.loadClassInstanceFromConfig(config, SECTION_KEY, key,
                recipe.getClass(), recipe);
        isEnabled(recipe);
        return recipe;
    }

    public AssemblerRecipe loadAssemblerRecipe(AssemblerRecipe recipe) {
        return loadAssemblerRecipe(recipe, recipe.output.getUnlocalizedName());
    }

    public boolean isEnabled(AssemblerRecipe recipe) {
        boolean result = recipe != null && config.get(ENABLE_SECTION_KEY, recipe.output.getUnlocalizedName(), true).getBoolean(true);
        if (config.hasChanged() && !batchLoading) {
            config.save();
        }
        return result;
    }
}
