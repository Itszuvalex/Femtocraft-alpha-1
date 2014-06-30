/*******************************************************************************
 * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 * Itszuvalex@gmail.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 ******************************************************************************/

package com.itszuvalex.femtocraft.managers.temporal;

import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class ManagerTemporalRecipe {

    private SortedMap<TemporalKey, TemporalRecipe> recipes;

    public ManagerTemporalRecipe() {
        recipes = new TreeMap<TemporalKey, TemporalRecipe>();
    }

    public TemporalRecipe getRecipe(ItemStack input, ItemStack[] configurators) {
        if (input == null) return null;
        return recipes.get(getTemporalKey(input, configurators));
    }

    public void addRecipe(TemporalRecipe recipe) {
        recipes.put(getTemporalKey(recipe.input, recipe.configurators), recipe);
    }

    private TemporalKey getTemporalKey(ItemStack input,
                                       ItemStack[] configurators) {
        if (input == null) return null;
        ItemStack ninput = input.copy();
        input.stackSize = 1;

        ItemStack[] configs = new ItemStack[configurators.length];
        if (configs != null) {
            for (int i = 0; i < configs.length; ++i) {
                if (configurators[i] == null) {
                    continue;
                }
                configs[i] = configurators[i].copy();
                configs[i].stackSize = 1;
            }
        }

        return new TemporalKey(ninput, configs);
    }

    private class TemporalKey implements Comparable {
        private final ItemStack input;
        private final ItemStack[] configurators;

        public TemporalKey(ItemStack input, ItemStack[] configurators) {
            this.input = input;
            this.configurators = configurators;
        }

        @Override
        public int compareTo(Object o) {
            TemporalKey tk = (TemporalKey) o;
            int result = FemtocraftUtils.compareItem(input, tk.input);
            if (result != 0) {
                return result;
            }

            if (configurators == null && tk.configurators != null) {
                return -1;
            }
            if (configurators != null && tk.configurators == null) {
                return 1;
            }
            if (configurators != null && tk.configurators != null) {
                if (configurators.length < tk.configurators.length) {
                    return -1;
                }
                if (configurators.length > tk.configurators.length) {
                    return 1;
                }

                for (int i = 0; i < configurators.length; ++i) {
                    result = FemtocraftUtils.compareItem
                            (configurators[i],
                                    tk.configurators[i]);
                    if (result != 0) {
                        return result;
                    }
                }
            }
            return 0;
        }
    }

}
