package com.itszuvalex.femtocraft.api;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.core.ISaveable;
import com.itszuvalex.femtocraft.api.research.ITechnology;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.apache.logging.log4j.Level;


public class AssemblerRecipe implements Comparable<AssemblerRecipe>, ISaveable {
    public ItemStack[] input;
    public int mass;
    public ItemStack output;
    public EnumTechLevel enumTechLevel;
    public String tech;
    public RecipeType type;

    public AssemblerRecipe(ItemStack[] input, int mass, ItemStack output,
                           EnumTechLevel enumTechLevel, ITechnology tech) {
        this(input, mass, output, enumTechLevel, tech, RecipeType.Reversible);
    }

    public AssemblerRecipe(ItemStack[] input, int mass, ItemStack output,
                           EnumTechLevel enumTechLevel, ITechnology tech, RecipeType type) {
        this(input, mass, output, enumTechLevel, tech.getName());
    }

    public AssemblerRecipe(ItemStack[] input, int mass, ItemStack output, EnumTechLevel enumTechLevel,
                           String techName) {
        this(input, mass, output, enumTechLevel, techName, RecipeType.Reversible);
    }

    public AssemblerRecipe(ItemStack[] input, int mass, ItemStack output, EnumTechLevel enumTechLevel,
                           String techName, RecipeType type) {
        this.input = input;
        this.mass = mass;
        this.output = output;
        this.enumTechLevel = enumTechLevel;
        this.tech = techName;
        this.type = type;
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

    public String getRecipeName() {
        if (output == null) { return "null"; } else return output.getDisplayName();
    }

    @Override
    public int compareTo(AssemblerRecipe o) {
        for (int i = 0; i < 9; i++) {
            int comp = FemtocraftUtils.compareItem(input[i], o.input[i]);
            if (comp != 0) {
                return comp;
            }
        }

        if (mass < o.mass) {
            return -1;
        }
        if (mass > o.mass) {
            return 1;
        }

        int comp = FemtocraftUtils.compareItem(output, o.output);
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

        compound.setTag("output", outputCompound);

        // EnumTechLevel
        compound.setString("enumTechLevel", enumTechLevel.key);

        // ResearchTechnology
        if (tech != null) {
            compound.setString("researchTechnology", tech);
        }

        compound.setString("type", type.getValue());
    }

    @Override
    public void loadFromNBT(NBTTagCompound compound) {
        input = new ItemStack[9];

        // Input
        NBTTagList inputList = compound.getTagList("input", 10);
        for (int i = 0; i < inputList.tagCount(); ++i) {
            NBTTagCompound itemCompound = inputList.getCompoundTagAt(i);
            byte slot = itemCompound.getByte("Slot");
            if (slot != (byte) i) {
                Femtocraft.log(Level.WARN,
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
            tech = compound
                    .getString("researchTechnology");
        }

        type = RecipeType.getRecipe(compound.getString("type"));
    }

    public enum RecipeType {

        Reversible, Decomposition, Recomposition;

        private static final String reverseKey = "Reversible";
        private static final String decompKey = "Decomposition";
        private static final String recompKey = "Recomposition";

        public String getValue() {
            switch (this) {
                case Reversible:
                    return reverseKey;
                case Decomposition:
                    return decompKey;
                case Recomposition:
                    return recompKey;
            }
            return "unknown";
        }

        public static RecipeType getRecipe(String key) {
            if (key.equalsIgnoreCase(reverseKey)) return Reversible;
            if (key.equalsIgnoreCase(decompKey)) return Decomposition;
            if (key.equalsIgnoreCase(recompKey)) return Recomposition;
            return null;
        }
    }
}
