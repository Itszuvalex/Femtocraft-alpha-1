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

package femtocraft.managers.assembler;

import femtocraft.Femtocraft;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnology;
import femtocraft.utils.FemtocraftUtils;
import femtocraft.utils.ISaveable;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

import java.util.logging.Level;

public class AssemblerRecipe implements Comparable, ISaveable {
    public ItemStack[] input;
    public Integer mass;
    public ItemStack output;
    public EnumTechLevel enumTechLevel;
    public ResearchTechnology tech;

    public AssemblerRecipe(ItemStack[] input, Integer mass, ItemStack output,
                           EnumTechLevel enumTechLevel, ResearchTechnology tech) {
        this.input = input;
        this.mass = mass;
        this.output = output;
        this.enumTechLevel = enumTechLevel;
        this.tech = tech;
    }

    /**
     * DO NOT USE, must be public for ISaveable
     */
    public AssemblerRecipe() {
    }

    public static AssemblerRecipe loadFromNBTTagCompound(NBTTagCompound compound) {
        AssemblerRecipe recipe = new AssemblerRecipe();
        recipe.loadFromNBT(compound);
        return recipe;
    }

    @Override
    public int compareTo(Object o) {
        AssemblerRecipe ir = (AssemblerRecipe) o;
        for (int i = 0; i < 9; i++) {
            int comp = FemtocraftUtils.compareItem(input[i], ir.input[i]);
            if (comp != 0) {
                return comp;
            }
        }

        if (mass < ir.mass) {
            return -1;
        }
        if (mass > ir.mass) {
            return 1;
        }

        int comp = FemtocraftUtils.compareItem(output, ir.output);
        if (comp != 0) {
            return comp;
        }

        return 0;
    }

    @Override
    public void saveToNBT(NBTTagCompound compound) {
        // Input
        NBTTagList inputList = new NBTTagList();
        for (int i = 0; i < input.length; ++i) {
            NBTTagCompound itemCompound = new NBTTagCompound();
            itemCompound.setByte("Slot", (byte) i);
            if (input[i] != null) {
                NBTTagCompound item = new NBTTagCompound();
                input[i].writeToNBT(item);
                itemCompound.setTag("item", item);
            }
            inputList.appendTag(itemCompound);
        }
        compound.setTag("input", inputList);

        // FluidMass
        compound.setInteger("fluidMass", mass);

        // Output
        NBTTagCompound outputCompound = new NBTTagCompound();
        output.writeToNBT(outputCompound);

        compound.setCompoundTag("output", outputCompound);

        // EnumTechLevel
        compound.setString("enumTechLevel", enumTechLevel.key);

        // ResearchTechnology
        if (tech != null) {
            compound.setString("researchTechnology", tech.name);
        }
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        input = new ItemStack[9];

        // Input
        NBTTagList inputList = compound.getTagList("input");
        for (int i = 0; i < inputList.tagCount(); ++i) {
            NBTTagCompound itemCompound = (NBTTagCompound) inputList.tagAt(i);
            byte slot = itemCompound.getByte("Slot");
            if (slot != (byte) i) {
                Femtocraft.logger
                        .log(Level.WARNING,
                             "Slot mismatch occurred while loading AssemblerRecipe.");
            }
            if (itemCompound.hasKey("item")) {
                input[i] = ItemStack
                        .loadItemStackFromNBT((NBTTagCompound) itemCompound
                                .getTag("item"));
            }
        }

        // FluidMass
        mass = compound.getInteger("fluidMass");

        // Output
        output = ItemStack.loadItemStackFromNBT((NBTTagCompound) compound
                .getTag("output"));

        // EnumTechLevel
        enumTechLevel = EnumTechLevel.getTech(compound
                                                      .getString("enumTechLevel"));

        // ResearchTechnology
        if (compound.hasKey("researchTechnology")) {
            tech = Femtocraft.researchManager.getTechnology(compound
                                                                    .getString("researchTechnology"));
        }
    }
}
