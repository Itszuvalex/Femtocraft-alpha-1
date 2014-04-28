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

package femtocraft.managers.dimensional;

import femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class ManagerDimensionalRecipe {

    private class DimensionalKey implements Comparable {
        private final ItemStack input;
        private final ItemStack[] configurators;

        public DimensionalKey(ItemStack input, ItemStack[] configurators) {
            this.input = input;
            this.configurators = configurators;
        }

        @Override
        public int compareTo(Object o) {
            DimensionalKey dk = (DimensionalKey) o;
            int result = FemtocraftUtils.compareItem(input, dk.input);
            if (result != 0) {
                return result;
            }

            if (configurators == null && dk.configurators != null) {
                return -1;
            }
            if (configurators != null && dk.configurators == null) {
                return 1;
            }
            if (configurators != null && dk.configurators != null) {
                if (configurators.length < dk.configurators.length) {
                    return -1;
                }
                if (configurators.length > dk.configurators.length) {
                    return 1;
                }

                for (int i = 0; i < configurators.length; ++i) {
                    result = FemtocraftUtils.compareItem
                            (configurators[i],
                             dk.configurators[i]);
                    if (result != 0) {
                        return result;
                    }
                }
            }
            return 0;
        }
    }

    private SortedMap<DimensionalKey, DimensionalRecipe> recipes;

    public ManagerDimensionalRecipe() {
        recipes = new TreeMap<DimensionalKey, DimensionalRecipe>();
    }

    public DimensionalRecipe getRecipe(ItemStack input, ItemStack[] configurators) {
        return recipes.get(new DimensionalKey(input, configurators));
    }

    public void addRecipe(DimensionalRecipe recipe) {
        recipes.put(new DimensionalKey(recipe.input, recipe.configurators),
                    recipe);
    }

}
