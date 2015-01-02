package com.itszuvalex.femtocraft.api;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.core.Configurable;
import com.itszuvalex.femtocraft.api.core.ISaveable;
import com.itszuvalex.femtocraft.api.core.Saveable;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.utils.FemtocraftDataUtils;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
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
