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
package com.itszuvalex.femtocraft.configuration

import java.util

import com.itszuvalex.femtocraft.api.AssemblerRecipe
import net.minecraftforge.common.config.Configuration

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 9/22/14.
 */
object FemtocraftAssemblerConfig {
  val ENABLE_SECTION_KEY        = "assembler_recipe_enabled"
  val SECTION_KEY               = "assembler_recipes"
  val CUSTOM_RECIPE_SECTION_KEY = "custom_assembler_recipes"
}

class FemtocraftAssemblerConfig(private val config: Configuration) {
  private var batchLoading = false

  def setBatchLoading(`val`: Boolean) {
    batchLoading = `val`
    if (!batchLoading && config.hasChanged) {
      config.save()
    }
  }

  def loadCustomRecipes: util.ArrayList[AssemblerRecipe] = {
    val ret = new util.ArrayList[AssemblerRecipe]
    val cat = config.getCategory(FemtocraftAssemblerConfig.CUSTOM_RECIPE_SECTION_KEY)
    cat.setComment("Any assembler recipes in this section will be registered first.  Use this to preempt recipe " + "default recipe mappings, if you don't want to change them in the normal section.  Just make " + "sure that the keys in this section are unique, as the Configuration files use key lookups and" + " I make no guarantees about what happens when two objects share the same key.")
    cat.getChildren.foreach { cc =>
      val name = cc.getQualifiedName.split("\\" + Configuration.CATEGORY_SPLITTER)
      ret.add(loadAssemblerRecipe(new AssemblerRecipe, name(name.length - 1)))
                            }
    ret
  }

  def loadAssemblerRecipe(recipe: AssemblerRecipe, key: String): AssemblerRecipe = {
    FemtocraftConfigHelper.loadClassInstanceFromConfig(config, FemtocraftAssemblerConfig.SECTION_KEY, FemtocraftConfigHelper.escapeCategorySplitter(key), recipe.getClass, recipe)
    isEnabled(recipe)
    recipe
  }

  def isEnabled(recipe: AssemblerRecipe): Boolean = {
    try {
      val result = recipe != null && config.get(FemtocraftAssemblerConfig.ENABLE_SECTION_KEY, FemtocraftConfigHelper.escapeCategorySplitter(recipe.output.getItem.getUnlocalizedName(recipe.output)), true).getBoolean(true)
      if (config.hasChanged && !batchLoading) {
        config.save()
      }
      result
    }
    catch {
      case e: Exception => false
    }
  }

  def loadAssemblerRecipe(recipe: AssemblerRecipe): AssemblerRecipe = loadAssemblerRecipe(recipe, recipe.output.getUnlocalizedName)
}
