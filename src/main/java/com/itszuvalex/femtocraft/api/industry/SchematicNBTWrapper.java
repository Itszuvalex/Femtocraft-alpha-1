package com.itszuvalex.femtocraft.api.industry;

import com.itszuvalex.femtocraft.api.AssemblerRecipe;
import com.itszuvalex.femtocraft.api.items.IAssemblerSchematic;
import net.minecraft.item.ItemStack;

/**
 * Created by Itszuvalex on 1/2/15.
 */
public class SchematicNBTWrapper implements IAssemblerSchematic {

    @Override
    public AssemblerRecipe getRecipe(ItemStack stack) {
        return null;
    }

    @Override
    public boolean hasRecipe(ItemStack stack) {
        return false;
    }

    @Override
    public boolean setRecipe(ItemStack stack, AssemblerRecipe recipe) {
        return false;
    }

    @Override
    public int usesRemaining(ItemStack stack) {
        return 0;
    }

    @Override
    public int massRequired(AssemblerRecipe recipe) {
        return 0;
    }

    @Override
    public boolean onAssemble(ItemStack stack) {
        return false;
    }

    @Override
    public ItemStack resultOfBreakdown(ItemStack stack) {
        return null;
    }
}
