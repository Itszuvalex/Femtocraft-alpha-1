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

package com.itszuvalex.femtocraft.industry.items;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.api.IAssemblerSchematic;
import com.itszuvalex.femtocraft.configuration.Configurable;
import com.itszuvalex.femtocraft.core.items.ItemBase;
import com.itszuvalex.femtocraft.managers.assembler.AssemblerRecipe;
import com.itszuvalex.femtocraft.managers.research.ResearchTechnology;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.Icon;

import java.util.List;
import java.util.Random;

/**
 * @author Itszuvalex
 * @placeholderIcon <i>static</i> - Icon for use in display slots when no schematic is there to draw reference from.
 * @infiniteUseMassMultipler <i>static</i> - Multiplier to use instead of use count when calculating fluidMass
 * requirements.
 * @keyedIcon Icon to use instead of itemIcon, when the itemStack has a recipe associated with it.
 * @Info This is a base class for Schematics with most of the hard work already done. This includes tooltip parsing,
 * damage behaviors, support for infinite use schematics and support for a separate Icon for keyed Schematics.
 */
@Configurable
public class ItemAssemblySchematic extends ItemBase implements IAssemblerSchematic {
    public static final int INFINITE_USE_DAMAGE = -1;
    public static Icon placeholderIcon;

    @Configurable(comment = "How many uses does an infinite use schematic count for when calculating mass costs? " +
                            "(This is a float to allow finer tuning - it will be cast to integer where it matters.")
    public static float infiniteUseMassMultiplier = 200;

    @SideOnly(value = Side.CLIENT)
    public Icon keyedIcon;

    public ItemAssemblySchematic(int itemID, String unlocalizedName) {
        super(itemID, unlocalizedName);
        setCreativeTab(Femtocraft.femtocraftTab);
        setMaxStackSize(64);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIconIndex(ItemStack par1ItemStack) {
        return hasRecipe(par1ItemStack) ? keyedIcon : this.itemIcon;
    }

    @Override
    public boolean isDamageable() {
        return true;
    }

    @Override
    public boolean getShareTag() {
        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack par1ItemStack,
                               EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
        // This is called when displaying the tooltip for an item. par4 is
        // whether it's extended or not, I believe.
        int uses = usesRemaining(par1ItemStack);
        String useString;
        if (uses == INFINITE_USE_DAMAGE) {
            useString = "Infinite Uses";
        } else {
            useString = String.valueOf(uses);
        }

        NBTTagCompound itemCompound = par1ItemStack.stackTagCompound;
        if (itemCompound == null || !itemCompound.hasKey("recipe")) {
            par3List.add(String.format(EnumChatFormatting.YELLOW
                                       + "Uses good for:" + EnumChatFormatting.RESET + " %s.",
                    useString
            ));
            return;
        }

        AssemblerRecipe recipe = AssemblerRecipe
                .loadFromNBTTagCompound((NBTTagCompound) itemCompound
                        .getTag("recipe"));
        if (recipe == null) {
            par3List.add("Invalid Recipe");
            return;
        }

        String useLine = String.format(EnumChatFormatting.YELLOW + "%s"
                                       + EnumChatFormatting.RESET + " %s", "Uses Remaining:",
                useString
        );
        par3List.add(useLine);

        String outputLine = String.format(EnumChatFormatting.YELLOW + "Output:"
                                          + recipe.enumTechLevel.getTooltipEnum() + " %d"
                                          + EnumChatFormatting.GRAY + "x" + EnumChatFormatting.RESET
                                          + "%s" + EnumChatFormatting.RESET, recipe.output.stackSize,
                recipe.output.getDisplayName()
        );
        par3List.add(outputLine);

        // End short
        if (!par4) {
            return;
        }
        // Begin long
        par3List.add("");

        for (int i = 0; i < 9; ++i) {
            ItemStack item = recipe.input[i];
            String inputString;
            if (item == null) {
                inputString = "empty";
            } else {
                inputString = String.format("%d" + EnumChatFormatting.GRAY
                                            + "x" + EnumChatFormatting.RESET + "%s",
                        item.stackSize, item.getDisplayName()
                );
            }
            String inputLine = String.format(EnumChatFormatting.YELLOW
                                             + "Input %d:" + EnumChatFormatting.RESET + " %s", i,
                    inputString
            );
            par3List.add(inputLine);
        }

        par3List.add("");

        String massLine = String.format(EnumChatFormatting.YELLOW + "Mass:"
                                        + EnumChatFormatting.DARK_PURPLE + " %d"
                                        + EnumChatFormatting.RESET, recipe.mass);
        par3List.add(massLine);

        String techLevelLine = String.format(EnumChatFormatting.YELLOW
                                             + "TechLevel:" + recipe.enumTechLevel.getTooltipEnum() + " %s"
                                             + EnumChatFormatting.RESET,
                FemtocraftUtils.capitalize(recipe.enumTechLevel.key)
        );
        par3List.add(techLevelLine);

        ResearchTechnology tech = Femtocraft.researchManager.getTechnology(recipe.tech);
        EnumChatFormatting formatting;
        String techString;
        if (tech == null) {
            formatting = EnumChatFormatting.BLACK;
            techString = "none";
        } else {
            formatting = tech.level.getTooltipEnum();
            techString = FemtocraftUtils.capitalize(tech.name);
        }

        String techLine = String.format(EnumChatFormatting.YELLOW
                                        + "Technology Required:" + formatting + " %s"
                                        + EnumChatFormatting.RESET, techString);
        par3List.add(techLine);
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public Icon getIcon(ItemStack stack, int pass) {
        return hasRecipe(stack) ? keyedIcon : this.itemIcon;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        NBTTagCompound stacks = stack.stackTagCompound;
        if (stacks == null) {
            return this.maxStackSize;
        }
        return stack.stackTagCompound.hasNoTags() ? this.maxStackSize : 1;
    }

    public boolean hasRecipe(ItemStack stack) {
        return stack.stackTagCompound != null
               && stack.stackTagCompound.hasKey("recipe");
    }

    @Override
    public AssemblerRecipe getRecipe(ItemStack stack) {
        return getRecipeFromNBT(stack.stackTagCompound);
    }

    @Override
    public boolean setRecipe(ItemStack stack, AssemblerRecipe recipe) {
        return addRecipeToNBT(stack, recipe);
    }

    private boolean addRecipeToNBT(ItemStack stack, AssemblerRecipe recipe) {
        if (stack.stackTagCompound == null) {
            stack.stackTagCompound = new NBTTagCompound();
        } else if (stack.stackTagCompound.hasKey("recipe")) {
            return false;
        }

        NBTTagCompound recipeCompound = new NBTTagCompound();
        recipe.saveToNBT(recipeCompound);
        stack.stackTagCompound.setTag("recipe", recipeCompound);

        return true;
    }

    @Override
    public int usesRemaining(ItemStack stack) {
        return stack.getMaxDamage() - stack.getItemDamage();
    }

    @Override
    public int massRequired(AssemblerRecipe recipe) {
        float amount = getMaxDamage();
        amount = amount == -1 ? infiniteUseMassMultiplier : amount;
        return (int) (recipe.enumTechLevel.tier * amount);
    }

    @Override
    public boolean onAssemble(ItemStack stack) {
        if (usesRemaining(stack) == -1) {
            return true;
        }
        stack.attemptDamageItem(1, new Random());
        return !(usesRemaining(stack) == 0);
    }

    @Override
    public ItemStack resultOfBreakdown(ItemStack stack) {
        return new ItemStack(this, 1);
    }

    private AssemblerRecipe getRecipeFromNBT(NBTTagCompound compound) {
        if (compound == null) {
            return null;
        }
        if (!compound.hasKey("recipe")) {
            return null;
        }
        return AssemblerRecipe.loadFromNBTTagCompound((NBTTagCompound) compound
                .getTag("recipe"));
    }
}
