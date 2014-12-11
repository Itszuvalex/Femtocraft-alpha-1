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
package com.itszuvalex.femtocraft.research.gui.technology

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.AssemblerRecipe
import com.itszuvalex.femtocraft.managers.research.ResearchStatus
import com.itszuvalex.femtocraft.research.gui.GuiResearch

class GuiTechnologyMacroscopicStructure(guiResearch: GuiResearch, status: ResearchStatus) extends GuiTechnology(guiResearch, status) {
  private final val recipes = Femtocraft.recipeManager.assemblyRecipes.getRecipesForTechnology(status.tech)

  protected override def renderInformation(x: Int, y: Int, width: Int, height: Int, displayPage: Int, mouseX: Int, mouseY: Int, tooltip: util.List[_], isResearched: Boolean) {
    if (isResearched && recipes.size > 0) {
      var index: Int = (displayPage - 1) * 2
      val recipe: AssemblerRecipe = recipes(index)
      renderAssemblerRecipeWithInfo(x, y, width, height / 2, recipe, mouseX, mouseY, tooltip, recipe.output.getDisplayName)
      if ( {index += 1; index} < recipes.size) {
        val recipe1: AssemblerRecipe = recipes(index)
        renderAssemblerRecipeWithInfo(x, y + (height / 2), width, height / 2, recipe1, mouseX, mouseY, tooltip, recipe1.output.getDisplayName)
      }
    }
    else {
    }
  }

  protected override def getNumPages(researched: Boolean): Int = if (researched) Math.ceil(recipes.size / 2f).toInt else 1
}
