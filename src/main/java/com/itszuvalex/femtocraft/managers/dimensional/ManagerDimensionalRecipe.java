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

package com.itszuvalex.femtocraft.managers.dimensional;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assembler.ComparatorItemStack;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class ManagerDimensionalRecipe {

    private SortedMap<DimensionalKey, DimensionalRecipe> recipesToOutput;
    private SortedMap<ItemStack, DimensionalRecipe> outputToRecipes;

    public ManagerDimensionalRecipe() {
        recipesToOutput = new TreeMap<DimensionalKey, DimensionalRecipe>();
        outputToRecipes = new TreeMap<ItemStack, DimensionalRecipe>(new ComparatorItemStack());
    }

    private void addRecipes() {
        addRecipe(new DimensionalRecipe(new ItemStack(Femtocraft.itemManagerCore),
                new ItemStack[]{new ItemStack(Femtocraft.itemNanochip), new ItemStack(Item.enderPearl),
                        new ItemStack(Femtocraft.itemCrossDimensionalCommunicator),
                        new ItemStack(Femtocraft.itemDimensionalMonopole)},
                new ItemStack(Femtocraft.itemPanLocationalComputer), 400, EnumTechLevel.NANO,
                FemtocraftTechnologies.DIMENSIONAL_BRAIDING));
        addRecipe(new DimensionalRecipe(new ItemStack(Block.chest), new ItemStack[]{new ItemStack(Femtocraft
                .itemPanLocationalComputer), new ItemStack(Item.enderPearl), new ItemStack(Item.enderPearl),
                new ItemStack(Femtocraft.itemDimensionalMonopole)}, new ItemStack(Femtocraft.itemPandoraCube), 400,
                EnumTechLevel.NANO, FemtocraftTechnologies.LOCALITY_ENTANGLER));
        addRecipe(new DimensionalRecipe(new ItemStack(Femtocraft.itemPandoraCube),
                new ItemStack[]{new ItemStack(Femtocraft.itemHerculesDrive),
                        new ItemStack(Femtocraft.itemPanLocationalComputer),
                        new ItemStack(Femtocraft.itemPanLocationalComputer),
                        new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Item.eyeOfEnder),
                        new ItemStack(Item.eyeOfEnder), new ItemStack(Item.eyeOfEnder),
                        new ItemStack(Item.eyeOfEnder), new ItemStack(Femtocraft.itemHerculesDrive),
                        new ItemStack(Femtocraft.itemInfallibleEstimator),
                        new ItemStack(Femtocraft.itemInfallibleEstimator),
                        new ItemStack(Femtocraft.itemHerculesDrive)},
                new ItemStack(Femtocraft.itemInfiniteVolumePolychora), 400, EnumTechLevel.FEMTO,
                FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS));
    }

    public DimensionalRecipe getRecipe(ItemStack input, ItemStack[] configurators) {
        if (input == null) return null;
        return recipesToOutput.get(getDimensionalKey(input, configurators));
    }

    public DimensionalRecipe getRecipe(ItemStack output) {
        return outputToRecipes.get(output);
    }


    private DimensionalKey getDimensionalKey(ItemStack input,
                                             ItemStack[] configurators) {
        if (input == null) return null;
        ItemStack ninput = input.copy();
        ninput.stackSize = 1;

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

        return new DimensionalKey(ninput, configs);
    }

    public void addRecipe(DimensionalRecipe recipe) {
        recipesToOutput.put(getDimensionalKey(recipe.input, recipe.configurators),
                recipe);
        outputToRecipes.put(recipe.output, recipe);
    }

    public void init() {
        addRecipes();
    }

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

}
