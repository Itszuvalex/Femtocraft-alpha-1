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
package com.itszuvalex.femtocraft.managers.temporal

import java.io.File
import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.industry.TemporalRecipe
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.configuration.{AutoGenConfig, TemporalXMLLoader, XMLTemporalRecipes}
import com.itszuvalex.femtocraft.managers.assembler.ComparatorItemStack
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.api.utils.{FemtocraftFileUtils, FemtocraftUtils}
import net.minecraft.init.Items
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
object ManagerTemporalRecipe {
  private val recipesToOutput = new util.TreeMap[TemporalKey, TemporalRecipe]
  private val outputToRecipes = new util.TreeMap[ItemStack, TemporalRecipe](new ComparatorItemStack)
  private val recipes         = new ArrayBuffer[TemporalRecipe]()

  def init() {
    addRecipes()
  }

  private def addRecipes() {
    val customRecipes = new TemporalXMLLoader(new File(FemtocraftFileUtils.customConfigPath + "Temporal/")).loadItems()
    customRecipes.view.foreach(addRecipe)

    val recipes = new XMLTemporalRecipes(new File(FemtocraftFileUtils.autogenConfigPath, "TemporalRecipes.xml"))
    if (!AutoGenConfig.shouldRegenFile(recipes.file) && recipes.initialized) {
      recipes.loadCustomRecipes().view.foreach(addRecipe)
    }
    else {
      val defaults = getDefaultRecipes
      recipes.seedInitialRecipes(defaults)
      defaults.view.foreach(addRecipe)
      AutoGenConfig.markFileRegenerated(recipes.file)
    }
  }

  private def getDefaultRecipes: ArrayBuffer[TemporalRecipe] = {
    val ret = new ArrayBuffer[TemporalRecipe]()
    ret += new TemporalRecipe(new ItemStack(Femtocraft.itemSelfFulfillingOracle), Array[ItemStack](new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Items.clock)), new ItemStack(Femtocraft.itemInfallibleEstimator), 200, EnumTechLevel.NANO, FemtocraftTechnologies.TEMPORAL_PIPELINING)
    ret += new TemporalRecipe(new ItemStack(Femtocraft.itemInfallibleEstimator), Array[ItemStack](new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemPanLocationalComputer)), new ItemStack(Femtocraft.itemInfinitelyRecursiveALU), 800, EnumTechLevel.FEMTO, FemtocraftTechnologies.TEMPORAL_THREADING)
    ret
  }

  def addRecipe(recipe: TemporalRecipe) {
    recipesToOutput.put(getTemporalKey(recipe.input, recipe.configurators), recipe)
    outputToRecipes.put(recipe.output, recipe)
    recipes += recipe
  }

  private def getTemporalKey(input: ItemStack, configurators: Array[ItemStack]): TemporalKey = {
    if (input == null) return null
    val ninput = input.copy
    ninput.stackSize = 1
    val configs = new Array[ItemStack](configurators.length)
    for (i <- 0 until configs.length) {
      if (configurators(i) != null) {
        configs(i) = configurators(i).copy
        configs(i).stackSize = 1
      }
    }

    new TemporalKey(ninput, configs)
  }

  def getRecipes = recipes

  def getRecipe(input: ItemStack, configurators: Array[ItemStack]): TemporalRecipe = {
    if (input == null) return null
    recipesToOutput.get(getTemporalKey(input, configurators))
  }

  def getRecipe(output: ItemStack): TemporalRecipe = {
    outputToRecipes.get(output)
  }

  def getRecipesForTech(tech: ITechnology): util.Collection[TemporalRecipe] = if (tech == null)
    recipesToOutput.values().filter(_.getTechnology == null).asJavaCollection
  else getRecipesForTech(tech.getName)

  def getRecipesForTech(name: String): util.Collection[TemporalRecipe] = {
    recipesToOutput.values().filter(_.tech != null).filter(_.tech equalsIgnoreCase name).asJavaCollection
  }

  private class TemporalKey(private val input: ItemStack, private val configurators: Array[ItemStack]) extends Comparable[TemporalKey] {

    override def compareTo(tk: TemporalKey): Int = {
      var result: Int = FemtocraftUtils.compareItem(input, tk.input)
      if (result != 0) {
        return result
      }
      if (configurators == null && tk.configurators != null) {
        return -1
      }
      if (configurators != null && tk.configurators == null) {
        return 1
      }
      if (configurators != null && tk.configurators != null) {
        if (configurators.length < tk.configurators.length) {
          return -1
        }
        if (configurators.length > tk.configurators.length) {
          return 1
        }

        for (i <- 0 until configurators.length) {
          result = FemtocraftUtils.compareItem(configurators(i), tk.configurators(i))
          if (result != 0) {
            return result
          }
        }
      }
      0
    }
  }

}
