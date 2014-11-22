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

package com.itszuvalex.femtocraft.api;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import com.itszuvalex.femtocraft.api.core.ISaveable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class DimensionalRecipe implements Comparable, ISaveable {
    @Saveable
    @Configurable(comment = "ItemStack that is consumed to use this recipe.")
    public ItemStack input;
    @Saveable
    @Configurable(comment = "Null, ItemStack[4] or ItemStack[12] (can include nulls) that dictate configuration items" +
                            " required to induce creation. Note, ItemStack[4] is associated with Nano level Enmesher," +
                            " ItemStack[12] is associated with Femto.")
    public ItemStack[] configurators;
    @Saveable
    @Configurable(comment = "ItemStack that is the result of this recipe.")
    public ItemStack output;
    @Saveable
    @Configurable(comment = "Ticks required to craft.")
    public int ticks;
    @Saveable
    @Configurable(comment = "TechLevel of this recipe.")
    public EnumTechLevel techLevel;
    @Saveable
    @Configurable(comment = "Null or name of Technology that must be researched before a player can utilize this " +
                            "recipe.")
    public String tech;

    public DimensionalRecipe(ItemStack input, ItemStack[] configurators,
                             ItemStack output, int ticks,
                             EnumTechLevel level, ITechnology tech) {
        this(input, configurators, output, ticks, level, tech == null ? null : tech.getName());
    }

    public DimensionalRecipe(ItemStack input, ItemStack[] configurators,
                             ItemStack output, int ticks,
                             EnumTechLevel level, String tech) {
        this.input = input;
        this.configurators = configurators;
        this.output = output;
        this.ticks = ticks;
        this.techLevel = level;
        this.tech = tech;
    }

    public ITechnology getTechnology() {
        return Femtocraft.researchManager().getTechnology(tech);
    }

    @Override
    public int compareTo(Object o) {
        DimensionalRecipe tr = (DimensionalRecipe) o;
        int result = FemtocraftUtils.compareItem(this.input, tr.input);
        if (result != 0) {
            return result;
        }

        if (configurators == null && tr.configurators != null) {
            return -1;
        }
        if (tr.configurators == null && configurators != null) {
            return 1;
        }

        if (tr.configurators != null && configurators != null) {
            if (configurators.length < tr.configurators.length) {
                return -1;
            }
            if (configurators.length > tr.configurators.length) {
                return 1;
            }

            for (int i = 0; i < configurators.length; ++i) {
                result = FemtocraftUtils.compareItem(configurators[i],
                        tr.configurators[i]);
                if (result != 0) {
                    return result;
                }
            }
        }

        result = FemtocraftUtils.compareItem(output, tr.output);
        if (result != 0) {
            return result;
        }

        return 0;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD);
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        FemtocraftDataUtils.loadObjectFromNBT(compound, this,
                FemtocraftDataUtils.EnumSaveType.WORLD);
    }
}
