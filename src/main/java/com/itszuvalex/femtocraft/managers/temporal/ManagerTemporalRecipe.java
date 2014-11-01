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

package com.itszuvalex.femtocraft.managers.temporal;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.TemporalRecipe;
import com.itszuvalex.femtocraft.managers.assembler.ComparatorItemStack;
import com.itszuvalex.femtocraft.api.EnumTechLevel;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class ManagerTemporalRecipe {

    private SortedMap<TemporalKey, TemporalRecipe> recipesToOutput;
    private SortedMap<ItemStack, TemporalRecipe> outputToRecipes;

    public ManagerTemporalRecipe() {
        recipesToOutput = new TreeMap<TemporalKey, TemporalRecipe>();
        outputToRecipes = new TreeMap<ItemStack, TemporalRecipe>(new ComparatorItemStack());
    }

    public void init() {
        addRecipes();
    }

    private void addRecipes() {
        addRecipe(new TemporalRecipe(new ItemStack(Femtocraft.itemSelfFulfillingOracle()),
                new ItemStack[]{new ItemStack(Femtocraft.itemTemporalResonator()),
                        new ItemStack(Femtocraft.itemSchedulerCore()), new ItemStack(Items.clock)},
                new ItemStack(Femtocraft.itemInfallibleEstimator()), 200, EnumTechLevel.NANO,
                FemtocraftTechnologies.TEMPORAL_PIPELINING));
        addRecipe(new TemporalRecipe(new ItemStack(Femtocraft.itemInfallibleEstimator()),
                new ItemStack[]{new ItemStack(Femtocraft.itemInfallibleEstimator()),
                        new ItemStack(Femtocraft.itemOrpheusProcessor()),
                        new ItemStack(Femtocraft.itemPanLocationalComputer()),
                        new ItemStack(Femtocraft.itemInfallibleEstimator()),
                        new ItemStack(Femtocraft.itemOrpheusProcessor()),
                        new ItemStack(Femtocraft.itemPanLocationalComputer())},
                new ItemStack(Femtocraft.itemInfinitelyRecursiveALU()), 800, EnumTechLevel.FEMTO,
                FemtocraftTechnologies.TEMPORAL_THREADING));

    }

    public TemporalRecipe getRecipe(ItemStack input, ItemStack[] configurators) {
        if (input == null) return null;
        return recipesToOutput.get(getTemporalKey(input, configurators));
    }

    public TemporalRecipe getRecipe(ItemStack output) {
        return outputToRecipes.get(output);
    }

    private TemporalKey getTemporalKey(ItemStack input,
                                       ItemStack[] configurators) {
        if (input == null) return null;
        ItemStack ninput = input.copy();
        ninput.stackSize = 1;

        ItemStack[] configs = new ItemStack[configurators.length];
        for (int i = 0; i < configs.length; ++i) {
            if (configurators[i] == null) {
                continue;
            }
            configs[i] = configurators[i].copy();
            configs[i].stackSize = 1;
        }

        return new TemporalKey(ninput, configs);
    }

    public void addRecipe(TemporalRecipe recipe) {
        recipesToOutput.put(getTemporalKey(recipe.input, recipe.configurators), recipe);
        outputToRecipes.put(recipe.output, recipe);
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
