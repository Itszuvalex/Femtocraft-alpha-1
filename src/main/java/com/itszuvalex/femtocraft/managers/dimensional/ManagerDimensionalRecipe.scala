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
package com.itszuvalex.femtocraft.managers.dimensional

import java.util
import java.util.TreeMap

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.{DimensionalRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.managers.assembler.ComparatorItemStack
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
class ManagerDimensionalRecipe {
  private val recipesToOutput = new TreeMap[DimensionalKey, DimensionalRecipe]
  private val outputToRecipes = new TreeMap[ItemStack, DimensionalRecipe](new ComparatorItemStack)

  private def addRecipes() {
    addRecipe(new DimensionalRecipe(new ItemStack(Femtocraft.itemManagerCore), Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Items.ender_pearl), new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), new ItemStack(Femtocraft.itemDimensionalMonopole)), new ItemStack(Femtocraft.itemPanLocationalComputer), 400, EnumTechLevel.NANO, FemtocraftTechnologies.DIMENSIONAL_BRAIDING))
    addRecipe(new DimensionalRecipe(new ItemStack(Blocks.chest), Array[ItemStack](new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Items.ender_pearl), new ItemStack(Items.ender_pearl), new ItemStack(Femtocraft.itemDimensionalMonopole)), new ItemStack(Femtocraft.itemPandoraCube), 400, EnumTechLevel.NANO, FemtocraftTechnologies.LOCALITY_ENTANGLER))
    addRecipe(new DimensionalRecipe(new ItemStack(Femtocraft.itemPandoraCube), Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Items.ender_eye), new ItemStack(Items.ender_eye), new ItemStack(Items.ender_eye), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemHerculesDrive)), new ItemStack(Femtocraft.itemInfiniteVolumePolychora), 400, EnumTechLevel.FEMTO, FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS))
  }

  def getRecipe(input: ItemStack, configurators: Array[ItemStack]): DimensionalRecipe = {
    if (input == null) return null
    recipesToOutput.get(getDimensionalKey(input, configurators))
  }

  def getRecipe(output: ItemStack): DimensionalRecipe = {
    outputToRecipes.get(output)
  }

  def getRecipesForTech(tech: ITechnology): util.Collection[DimensionalRecipe] = if (tech == null) recipesToOutput.values().filter(_.getTechnology == null).asJavaCollection else getRecipesForTech(tech.getName)

  def getRecipesForTech(name: String): util.Collection[DimensionalRecipe] = {
    recipesToOutput.values().filter(_.tech != null).filter(_.tech equalsIgnoreCase name).asJavaCollection
  }

  private def getDimensionalKey(input: ItemStack, configurators: Array[ItemStack]): DimensionalKey = {
    if (input == null) return null
    val ninput = input.copy
    ninput.stackSize = 1
    val configs = new Array[ItemStack](configurators.length)
    if (configs != null) {
      for (i <- 0 until configs.length) {

        if (configurators(i) != null) {
          configs(i) = configurators(i).copy
          configs(i).stackSize = 1
        }
      }

    }
    new DimensionalKey(ninput, configs)
  }

  def addRecipe(recipe: DimensionalRecipe) {
    recipesToOutput.put(getDimensionalKey(recipe.input, recipe.configurators), recipe)
    outputToRecipes.put(recipe.output, recipe)
  }

  def init() {
    addRecipes()
  }

  private class DimensionalKey(val input: ItemStack, val configurators: Array[ItemStack]) extends Comparable[DimensionalKey] {

    def compareTo(dk: DimensionalKey): Int = {
      var result: Int = FemtocraftUtils.compareItem(input, dk.input)
      if (result != 0) {
        return result
      }
      if (configurators == null && dk.configurators != null) {
        return -1
      }
      if (configurators != null && dk.configurators == null) {
        return 1
      }
      if (configurators != null && dk.configurators != null) {
        if (configurators.length < dk.configurators.length) {
          return -1
        }
        if (configurators.length > dk.configurators.length) {
          return 1
        }

        for (i <- 0 until configurators.length) {
          result = FemtocraftUtils.compareItem(configurators(i), dk.configurators(i))
          if (result != 0) {
            return result
          }
        }

      }
      0
    }
  }

}
