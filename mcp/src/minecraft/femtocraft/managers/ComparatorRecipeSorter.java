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

package femtocraft.managers;

import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.item.crafting.ShapelessRecipes;

import java.util.Comparator;

public class ComparatorRecipeSorter implements Comparator {
    final ManagerCrafting craftingManager;

    ComparatorRecipeSorter(ManagerCrafting par1CraftingManager) {
        this.craftingManager = par1CraftingManager;
    }

    public int compareRecipes(IRecipe par1IRecipe, IRecipe par2IRecipe) {
        return par1IRecipe instanceof ShapelessRecipes
                && par2IRecipe instanceof ShapedRecipes ? 1
                : (par2IRecipe instanceof ShapelessRecipes
                && par1IRecipe instanceof ShapedRecipes ? -1
                : (par2IRecipe.getRecipeSize() < par1IRecipe
                .getRecipeSize() ? -1
                : (par2IRecipe.getRecipeSize() > par1IRecipe
                .getRecipeSize() ? 1 : 0)));
    }

    @Override
    public int compare(Object par1Obj, Object par2Obj) {
        return this.compareRecipes((IRecipe) par1Obj, (IRecipe) par2Obj);
    }
}
