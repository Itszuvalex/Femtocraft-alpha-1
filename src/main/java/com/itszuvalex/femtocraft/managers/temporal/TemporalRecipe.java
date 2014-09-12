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
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.managers.research.EnumTechLevel;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import com.itszuvalex.femtocraft.utils.ISaveable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
public class TemporalRecipe implements Comparable, ISaveable {
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "ItemStack that is consumed to use this recipe.")
    public ItemStack input;
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "Null, ItemStack[3] or ItemStack[6] (can include nulls) that dictate configuration items" +
                            " required to induce creation. Note, ItemStack[3] is associated with Nano level Horologe," +
                            " ItemStack[6] is associated with Femto.")
    public ItemStack[] configurators;
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "ItemStack that is the result of this recipe.")
    public ItemStack output;
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "Ticks required to craft.")
    public int ticks;
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "TechLevel of this recipe.")
    public EnumTechLevel techLevel;
    @FemtocraftDataUtils.Saveable
    @Configurable(comment = "Null or name of Technology that must be researched before a player can utilize this " +
                            "recipe.")
    public String tech;

    public TemporalRecipe(ItemStack input, ItemStack[] configurators,
                          ItemStack output, int ticks,
                          EnumTechLevel level, ResearchTechnology tech) {
        this(input, configurators, output, ticks, level, tech == null ? null : tech.name);
    }

    public TemporalRecipe(ItemStack input, ItemStack[] configurators,
                          ItemStack output, int ticks,
                          EnumTechLevel level, String tech) {
        this.input = input;
        this.configurators = configurators;
        this.output = output;
        this.ticks = ticks;
        this.techLevel = level;
        this.tech = tech;
    }

    public ResearchTechnology getTechnology() {
        return Femtocraft.researchManager.getTechnology(tech);
    }

    @Override
    public int compareTo(Object o) {
        TemporalRecipe tr = (TemporalRecipe) o;
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
