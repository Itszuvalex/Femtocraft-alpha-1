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

package femtocraft.research.gui.technology;

import femtocraft.Femtocraft;
import femtocraft.managers.assembler.AssemblerRecipe;
import femtocraft.managers.research.EnumTechLevel;
import femtocraft.managers.research.ResearchTechnologyStatus;
import femtocraft.research.gui.GuiResearch;
import femtocraft.research.gui.GuiTechnology;

import java.util.ArrayList;
import java.util.List;

public class GuiTechnologyMacroscopicStructure extends GuiTechnology {
    private final ArrayList<AssemblerRecipe> recipes;

    public GuiTechnologyMacroscopicStructure(GuiResearch guiResearch,
                                             ResearchTechnologyStatus status) {
        super(guiResearch, status);
        recipes = Femtocraft.recipeManager.assemblyRecipes
                .getRecipesForTechLevel(EnumTechLevel.MACRO);
    }

    @Override
    protected void renderInformation(int x, int y, int width, int height,
                                     int displayPage, int mouseX, int mouseY, List tooltip,
                                     boolean isResearched) {
        if (isResearched) {
            int index = (displayPage - 1) * 2;
            AssemblerRecipe recipe = recipes.get(index);

            renderAssemblerRecipeWithInfo(x, y, width, height / 2, recipe,
                                          mouseX, mouseY, tooltip, recipe.output.getDisplayName());
            if (++index < recipes.size()) {
                AssemblerRecipe recipe1 = recipes.get(index);
                renderAssemblerRecipeWithInfo(x, y + (height / 2), width,
                                              height / 2,
                                              recipe1, mouseX, mouseY, tooltip,
                                              recipe1.output.getDisplayName());
            }
        }
        else {

        }
    }

    @Override
    protected int getNumPages(boolean researched) {
        return researched ? (int) Math.ceil(recipes.size() / 2.f) : 1;
    }
}
