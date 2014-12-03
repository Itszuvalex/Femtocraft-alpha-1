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

package com.itszuvalex.femtocraft.api.industry;

import com.itszuvalex.femtocraft.api.AssemblerRecipe;
import net.minecraft.item.ItemStack;

/**
 * @author Itszuvalex
 */
public interface IAssemblerSchematic {

    /**
     * @param stack The IAssemblerSchematic stack
     * @return The AssemblerRecipe represented with this Schematic
     */
    AssemblerRecipe getRecipe(ItemStack stack);

    /**
     * @param stack  ItemStack to encode recipe into
     * @param recipe Recipe to encode into itemStack
     * @return True if recipe successfully encoded, false for any other reason (itemStack already has recipe?, failed to
     * read NBT, etc.)
     */
    boolean setRecipe(ItemStack stack, AssemblerRecipe recipe);

    /**
     * @param stack ItemStack to find uses remaining of - generally is remaining damage
     * @return How many uses remain, or -1 if infinite.
     */
    int usesRemaining(ItemStack stack);

    /**
     * @param recipe Recipe that will be imprinted upon this Schematic
     * @return Amount of fluidMass required to create this recipe, in mB.
     */
    int massRequired(AssemblerRecipe recipe);

    /**
     * @param stack - ItemStack of this schematic.
     * @return True if this schematic is still valid, false if schematic is no longer valid (i.e. damage).
     */
    boolean onAssemble(ItemStack stack);

    /**
     * @param stack ItemStack that is breaking down
     * @return ItemStack that takes the place of @stack, if onAssemble() returns false;
     */
    ItemStack resultOfBreakdown(ItemStack stack);

}
