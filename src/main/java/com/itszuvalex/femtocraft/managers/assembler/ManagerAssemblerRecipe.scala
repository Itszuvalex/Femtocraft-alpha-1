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
package com.itszuvalex.femtocraft.managers.assembler

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.events.EventAssemblerRegister
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.implicits.IDImplicits._
import com.itszuvalex.femtocraft.managers.research.Technology
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.init.{Blocks, Items}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.{CraftingManager, IRecipe, ShapedRecipes, ShapelessRecipes}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.oredict.{OreDictionary, ShapedOreRecipe, ShapelessOreRecipe}
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.{immutable, mutable}


/** @author chris
  *
  *         This manager is responsible for all [[com.itszuvalex.femtocraft.Femtocraft]] [[AssemblerRecipe]].
  *
  *         All Assembler/Dissassemblers look to this manager for recipe lookup. Recipes can be specified to only be disassemble-able, or only reassemble-able.
  *         Dissassemblers simply break down items, Reassembles must use schematics to specify the recipe to follow. <br> All
  *         recipes are ordered according to their signature in the inventory. The entire 9 slots are used for the input
  *         signature. ItemStack stackSize does not matter for ordering. When reconstructing, items must conform to the input signature, and all 9 slots are
  *         important. Slots that are null in the recipe must not contain any items, and vice versa. This will be separately
  *         enforced in the schematic-creating TileEntities, but it it is also stated here for reference.
  */

object ManagerAssemblerRecipe {
  val shapelessPermuteTimeMillis = 10
}

class ManagerAssemblerRecipe {
  private val ard = new AssemblerRecipeDatabase

  def init() {
    registerRecipes()
  }

  private lazy val decompCountMemo = new mutable.HashMap[Int, (Int, Int, Int, Int)]

  def getDecompositionCounts(i: ItemStack): (Int, Int, Int, Int) = {
    getDecompositionCounts(i, immutable.TreeSet[Int]())
  }

  private def getDecompositionCounts(i: ItemStack, s: immutable.TreeSet[Int]): (Int, Int, Int, Int) = {
    var molecules = 0
    var atoms = 0
    var particles = 0
    var mass = 0


    if (i != null) {
      decompCountMemo.get(i.itemID) match {
        case Some((mol, at, pa, ma)) => return (mol * i.stackSize, at * i.stackSize, pa * i.stackSize, ma * i.stackSize)
        case _                       =>
      }



      i match {
        case micro if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.MICRO) => molecules = i.stackSize
        case nano if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.NANO)   => atoms = i.stackSize
        case femto if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.FEMTO) => particles = i.stackSize
        case _                                                                          =>
      }

      if (!s.contains(i.itemID)) {
        val recipe = getRecipe(i)
        if (recipe != null) {
          mass += (recipe.mass * i.stackSize)
          recipe.input.foreach {
                                 case in if !ItemStack.areItemStacksEqual(i, in) =>
                                   val (m, a, p, ma) = getDecompositionCounts(in, s + i.itemID)
                                   molecules += m
                                   atoms += a
                                   particles += p
                                   mass += ma
                                 case _                                          =>
                               }
        }
        decompCountMemo.put(i.itemID, (molecules / i.stackSize, atoms / i.stackSize, particles / i.stackSize, mass / i.stackSize))
      }
      (molecules * i.stackSize, atoms * i.stackSize, particles * i.stackSize, mass * i.stackSize)
    }
    else
      (molecules, atoms, particles, mass)
  }

  def registerDefaultRecipes() {
    Femtocraft.log(Level.INFO, "Scraping Minecraft recipe registries for assembler recipe mappings.")
    if (!ard.shouldRegister) {
      Femtocraft.log(Level.INFO, "Database already exists.  " + "Skipping item registration.")
      return
    }
    Femtocraft.log(Level.WARN, "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take " + "awhile ._.")
    val recipes = CraftingManager.getInstance.getRecipeList.filter(i => i != null && i.isInstanceOf[IRecipe]).map(_.asInstanceOf[IRecipe])
    Femtocraft.log(Level.WARN, "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.")
    recipes.filter(i => i.isInstanceOf[ShapedRecipes] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapedRecipes])
    .foreach(sr => {
      try {
        Femtocraft.log(Level.INFO, "Attempting to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
        val valid = registerShapedRecipe(sr.recipeItems, sr.getRecipeOutput, sr.recipeWidth, sr.recipeHeight)
        if (!valid) {
          Femtocraft.log(Level.WARN, "Failed to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + "!")
        }
        else {
          Femtocraft.log(Level.INFO, "Loaded Vanilla Minecraft shaped recipe as assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
        }
      }
      catch {
        case e: Exception =>
      }
    })
    Femtocraft.log(Level.WARN, "Registering shaped ore recipes from Forge.")
    recipes.filter(i => i.isInstanceOf[ShapedOreRecipe] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapedOreRecipe])
    .foreach(orecipe => {
      try {
        Femtocraft.log(Level.INFO, "Attempting to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
        var width = 0
        var height = 0
        try {
          val width_field = classOf[ShapedOreRecipe].getDeclaredField("width")
          var prev = width_field.isAccessible
          if (!prev) {
            width_field.setAccessible(true)
          }
          width = width_field.getInt(orecipe)
          if (!prev) {
            width_field.setAccessible(prev)
          }


          val height_field = classOf[ShapedOreRecipe].getDeclaredField("height")
          prev = height_field.isAccessible
          if (!prev) {
            height_field.setAccessible(true)
          }
          height = height_field.getInt(orecipe)
          if (!prev) {
            height_field.setAccessible(prev)
          }

        }
        catch {
          case e: SecurityException        => e.printStackTrace()
          case e: NoSuchFieldException     => e.printStackTrace()
          case e: IllegalArgumentException => e.printStackTrace()
          case e: IllegalAccessException   => e.printStackTrace()
        }
        val valid = registerShapedOreRecipe(orecipe.getInput, orecipe.getRecipeOutput, width, height)
        if (!valid) {
          Femtocraft.log(Level.WARN, "Failed to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + "!")
        }
        else {
          Femtocraft.log(Level.INFO, "Loaded Forge shaped ore recipe as assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
        }
      }
      catch {
        case e: Exception =>
      }
    })
    Femtocraft.log(Level.WARN, "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.")
    recipes.filter(i => i.isInstanceOf[ShapelessRecipes] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapelessRecipes])
    .foreach(recipe => {
      try {
        Femtocraft.log(Level.INFO, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
        val valid: Boolean = registerShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput)
        if (!valid) {
          Femtocraft.log(Level.WARN, "Failed to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
          Femtocraft.log(Level.WARN, "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
        }
        else {
          Femtocraft.log(Level.INFO, "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + " + recipe.getRecipeOutput.getDisplayName + ".")
        }
      }
      catch {
        case e: Exception =>
      }
    })

    Femtocraft.log(Level.WARN, "Registering shapeless ore recipes from Forge.")
    recipes.filter(i => i.isInstanceOf[ShapelessOreRecipe] && getRecipe(i.getRecipeOutput) == null)
    .map(_.asInstanceOf[ShapelessOreRecipe])
    .foreach(recipe => {
      try {
        Femtocraft.log(Level.INFO, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
        val valid: Boolean = registerShapelessOreRecipe(recipe.getInput, recipe.getRecipeOutput)
        if (!valid) {
          Femtocraft.log(Level.WARN, "Failed to register shapeless ore assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
          Femtocraft.log(Level.WARN, "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
        }
        else {
          Femtocraft.log(Level.INFO, "Loaded Forge shapeless ore recipe as assembler recipe for + " + recipe.getRecipeOutput.getDisplayName + ".")
        }
      }
      catch {
        case e: Exception =>
      }
    })
    Femtocraft.log(Level.INFO, "Finished mapping Minecraft recipes to assembler recipes.")
  }

  @throws(classOf[IllegalArgumentException])
  def addReversableRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normalArray = normalizedInput(recipe)
    if (normalArray == null) {
      return false
    }
    val normal = normalizedOutput(recipe)
    if (!checkDecomposition(normal, recipe) || !checkRecomposition(normalArray, recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerRecomposition(normalArray, recipe) && registerDecomposition(normal, recipe)
  }

  @throws(classOf[IllegalArgumentException])
  def addRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normal = normalizedInput(recipe)
    if (normal == null) {
      return false
    }
    if (!checkRecomposition(normal, recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerRecomposition(normal, recipe)
  }

  @throws(classOf[IllegalArgumentException])
  def addDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    val normal = normalizedOutput(recipe)
    if (!checkDecomposition(normal, recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.output.getUnlocalizedName + ".")
      return false
    }
    registerDecomposition(normal, recipe)
  }

  def removeAnyRecipe(recipe: AssemblerRecipe) = removeDecompositionRecipe(recipe) || removeRecompositionRecipe(recipe)

  def removeDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    false
  }

  def removeRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    false
  }

  def removeReversableRecipe(recipe: AssemblerRecipe) = removeDecompositionRecipe(recipe) && removeRecompositionRecipe(recipe)

  def canCraft(input: Array[ItemStack]): Boolean = {
    if (input.length != 9) {
      return false
    }
    val recipe: AssemblerRecipe = getRecipe(input)
    if (recipe == null) {
      return false
    }
    for (i <- 0 until 9) {
      val rec = recipe.input(i)
      if (!(input(i) == null || rec == null)) {
        if (input(i).stackSize < input(i).stackSize) {
          return false
        }
        if (FemtocraftUtils.compareItem(rec, input(i)) != 0) {
          return false
        }
      }
    }
    true
  }

  def getRecipe(input: Array[ItemStack]): AssemblerRecipe = {
    val normal = normalizedInput(input)
    if (normal == null) {
      return null
    }
    ard.getRecipe(input)
  }

  private def normalizedInput(input: Array[ItemStack]): Array[ItemStack] = {
    if (input.length != 9) {
      return null
    }
    val ret: Array[ItemStack] = new Array[ItemStack](9)
    for (i <- 0 until 9) {
      ret(i) = normalizedItem(input(i))
    }
    ret
  }

  private def normalizedItem(original: ItemStack) = if (original == null) null else new ItemStack(original.getItem, 1, original.getItemDamage)

  def canCraft(input: ItemStack) = {
    val recipe = getRecipe(input)
    recipe != null && input.stackSize >= recipe.output.stackSize && FemtocraftUtils.compareItem(recipe.output, input) == 0
  }

  def getRecipe(output: ItemStack): AssemblerRecipe = ard.getRecipe(output)

  def getRecipesForTechLevel(level: EnumTechLevel) = ard.getRecipesForLevel(level)

  //  private def testRecipes() {
  //    var test= getRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null))
  //    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
  //    if (test != null) {
  //      Femtocraft.log(Level.WARNING, "Output " + (if (test.output.isItemEqual(new ItemStack(Femtocraft.itemFlorite))) "matches" else "does not match") + ".")
  //    }
  //    test = getRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null))
  //    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
  //    test = getRecipe(new ItemStack(Femtocraft.itemFlorite))
  //    Femtocraft.log(Level.WARNING, "Recipe " + (if (test != null) "found" else "not found") + ".")
  //  }

  def getAllRecipes = ard.getAllRecipes

  def getRecipesForTechnology(tech: Technology) = ard.getRecipesForTech(tech)

  def getRecipesForTechnology(techName: String) = ard.getRecipesForTech(techName)

  def hasResearchedRecipe(recipe: AssemblerRecipe, username: String) = Femtocraft.researchManager.hasPlayerResearchedTechnology(username, recipe.tech)

  private def registerRecipes() {
    Femtocraft.log(Level.INFO, "Registering Femtocraft assembler recipes.")
    if (ard.shouldRegister) {
      Femtocraft.assemblerConfigs.setBatchLoading(true)
      registerCustomRecipes()
      registerFemtoDecompositionRecipes()
      registerNanoDecompositionRecipes()
      registerMicroDecompositionRecipes()
      registerMacroDecompositionRecipes()
      registerFemtocraftAssemblerRecipes()
      Femtocraft.assemblerConfigs.setBatchLoading(false)
    }
    Femtocraft.log(Level.INFO, "Finished registering Femtocraft assembler recipes.")
  }

  private def registerCustomRecipes() {
    Femtocraft.log(Level.INFO, "Registering custom assembler recipes.")
    Femtocraft.assemblerConfigs.loadCustomRecipes
    Femtocraft.log(Level.INFO, "Finished registering custom assembler recipes.")
  }

  private def registerFemtoDecompositionRecipes() {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3, new ItemStack(Femtocraft.itemCrystallite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemCubit), null, null, null), 3, new ItemStack(Femtocraft.itemMineralite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemCubit), null, null, null), 3, new ItemStack(Femtocraft.itemMetallite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemRectangulon), null, null, null), 3, new ItemStack(Femtocraft.itemFaunite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemCubit), new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3, new ItemStack(Femtocraft.itemElectrite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemPlaneoid), new ItemStack(Femtocraft.itemRectangulon), new ItemStack(Femtocraft.itemPlaneoid), null, null, null), 3, new ItemStack(Femtocraft.itemFlorite), EnumTechLevel.FEMTO, FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS))
  }

  private def registerNanoDecompositionRecipes() {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2), new ItemStack(Femtocraft.itemElectrite, 2), new ItemStack(Femtocraft.itemCrystallite, 2)), 2, new ItemStack(Femtocraft.itemMicroCrystal), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemMineralite), new ItemStack(Femtocraft.itemFaunite), null, null, null), 2, new ItemStack(Femtocraft.itemProteinChain), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemFaunite), null, null, null), 2, new ItemStack(Femtocraft.itemNerveCluster), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemElectrite), null, new ItemStack(Femtocraft.itemMetallite), null), 2, new ItemStack(Femtocraft.itemConductiveAlloy), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralite), null, new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemMineralite), null), 2, new ItemStack(Femtocraft.itemMetalComposite), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), null, new ItemStack(Femtocraft.itemMineralite), null, null, null), 2, new ItemStack(Femtocraft.itemFibrousStrand), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralite), null, new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemMineralLattice), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemFungalSpores), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemMineralite), new ItemStack(Femtocraft.itemElectrite), null, null, null), 2, new ItemStack(Femtocraft.itemIonicChunk), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemReplicatingMaterial), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemSpinyFilament), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemCrystallite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemCrystallite), null, null, null), 2, new ItemStack(Femtocraft.itemHardenedBulb), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemElectrite), new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemElectrite), null, null, null), 2, new ItemStack(Femtocraft.itemMorphicChannel), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFlorite), new ItemStack(Femtocraft.itemMetallite), new ItemStack(Femtocraft.itemFlorite), null, null, null), 2, new ItemStack(Femtocraft.itemSynthesizedFiber), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMetallite), null, new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFaunite), new ItemStack(Femtocraft.itemFaunite), null, new ItemStack(Femtocraft.itemMetallite), null), 2, new ItemStack(Femtocraft.itemOrganometallicPlate), EnumTechLevel.NANO, FemtocraftTechnologies.ADVANCED_CHEMISTRY))
  }

  private def registerMicroDecompositionRecipes() {
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.stone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.grass), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null), 1, new ItemStack(Blocks.dirt), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.cobblestone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.planks), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1, new ItemStack(Blocks.sapling), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.sand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.leaves), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.web), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null), 1, new ItemStack(Blocks.deadbush), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null), 1, new ItemStack(Blocks.yellow_flower), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null), 1, new ItemStack(Blocks.red_flower), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.brown_mushroom), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.red_mushroom), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), new ItemStack(Femtocraft.itemFungalSpores), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.mossy_cobblestone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.obsidian), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.ice), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null), 1, new ItemStack(Blocks.cactus), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.pumpkin), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null), 1, new ItemStack(Blocks.netherrack), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.soul_sand), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Items.glowstone_dust), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.melon_block), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null), 1, new ItemStack(Blocks.vine), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.mycelium), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null), 1, new ItemStack(Blocks.waterlily), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemConductiveAlloy), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Blocks.end_stone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null, null, null), 1, new ItemStack(Blocks.cocoa), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFibrousStrand), null, new ItemStack(Femtocraft.itemMorphicChannel), null, null, null, null, null, null), 1, new ItemStack(Items.apple), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMineralLattice), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Items.coal), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8), new ItemStack(Femtocraft.itemIonicChunk, 8), new ItemStack(Femtocraft.itemMicroCrystal, 8)), 1, new ItemStack(Items.diamond), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), null, null, null, null, null, null, null, null), 1, new ItemStack(Items.iron_ingot), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Items.gold_ingot), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null, null), 1, new ItemStack(Items.stick), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null, null), 1, new ItemStack(Items.string), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null), 1, new ItemStack(Items.feather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Items.gunpowder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Items.wheat_seeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null, null), 1, new ItemStack(Items.wheat), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Items.flint), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Items.porkchop), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null, null, null), 1, new ItemStack(Items.cooked_porkchop), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemMineralLattice), null, null, null, null, null, null, null), 1, new ItemStack(Items.redstone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null, null, null, new ItemStack(Femtocraft.itemMineralLattice)), 1, new ItemStack(Items.snowball), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Items.leather), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null, null), 1, new ItemStack(Items.clay_ball), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Items.reeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null, null), 1, new ItemStack(Items.slime_ball), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Items.egg), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null), 1, new ItemStack(Items.fish), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemProteinChain), new ItemStack(Femtocraft.itemSpinyFilament), null, null, null, null, null, null, null), 1, new ItemStack(Items.cooked_fished), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null, null, null), 1, new ItemStack(Items.dye, 1, OreDictionary.WILDCARD_VALUE), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemHardenedBulb), null, null, null, null, null, null, null), 1, new ItemStack(Items.bone), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Items.pumpkin_seeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemHardenedBulb), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null), 1, new ItemStack(Items.melon_seeds), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null), 1, new ItemStack(Items.beef), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null, null), 1, new ItemStack(Items.cooked_beef), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), null, null, new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null), 1, new ItemStack(Items.chicken), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, new ItemStack(Femtocraft.itemProteinChain), null, null, null, null, null, null), 1, new ItemStack(Items.cooked_chicken), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpinyFilament), new ItemStack(Femtocraft.itemNerveCluster), null, null, null, null, null, null, null), 1, new ItemStack(Items.rotten_flesh), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemIonicChunk), null, new ItemStack(Femtocraft.itemIonicChunk), new ItemStack(Femtocraft.itemOrganometallicPlate), new ItemStack(Femtocraft.itemIonicChunk), null, new ItemStack(Femtocraft.itemIonicChunk), null), 1, new ItemStack(Items.ender_pearl), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemIonicChunk), null, null, null, null, null, null, null), 1, new ItemStack(Items.ghast_tear), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFungalSpores), new ItemStack(Femtocraft.itemReplicatingMaterial), null, null, null, null, null, null, null), 1, new ItemStack(Items.nether_wart), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNerveCluster), new ItemStack(Femtocraft.itemOrganometallicPlate), null, null, null, null, null, null, null), 1, new ItemStack(Items.spider_eye), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMorphicChannel), new ItemStack(Femtocraft.itemMicroCrystal), null, null, null, null, null, null, null), 1, new ItemStack(Items.blaze_powder), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMetalComposite), new ItemStack(Femtocraft.itemConductiveAlloy), null, null, null, null, null, null, null), 1, new ItemStack(Items.emerald), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null, null, null), 1, new ItemStack(Items.carrot), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemReplicatingMaterial), new ItemStack(Femtocraft.itemFibrousStrand), null, null, null, null), 1, new ItemStack(Items.potato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, null, null, null, new ItemStack(Femtocraft.itemFibrousStrand), null, null), 1, new ItemStack(Items.baked_potato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, null, null, new ItemStack(Femtocraft.itemSynthesizedFiber), null, null, null, null, null), 1, new ItemStack(Items.poisonous_potato), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CHEMISTRY))
    addDecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.egg), new ItemStack(Items.sugar, 2), new ItemStack(Items.wheat, 3), null, null, null, null, null, null), 1, new ItemStack(Items.cake), EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Items.diamond, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64), new ItemStack(Femtocraft.itemOrganometallicPlate, 64), new ItemStack(Femtocraft.itemHardenedBulb, 64)), 1, new ItemStack(Items.nether_star), EnumTechLevel.MICRO, FemtocraftTechnologies.NETHER_STAR_FABRICATION))
  }

  private def registerMacroDecompositionRecipes() {
  }

  private def registerShapedOreRecipe(recipeInput: Array[AnyRef], recipeOutput: ItemStack, width: Int, height: Int): Boolean = {
    try {
      var done = false
      var xOffset = 0
      var yOffset = 0
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
      if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
        recipe.output.setItemDamage(0)
      }
      while ((!done) && (xOffset < 3) && (yOffset < 3)) {
        for (i <- 0 until Math.min(recipeInput.length, 9)) {
          try {
            var item: ItemStack = null
            val obj: AnyRef = recipeInput(i)
            if (obj.isInstanceOf[util.ArrayList[_]]) {
              try {
                item = obj.asInstanceOf[util.ArrayList[ItemStack]].get(0)
              }
              catch {
                case exc: IndexOutOfBoundsException =>
                  Femtocraft.log(Level.ERROR, "Ore recipe with nothing registered in " + "ore dictionary for " + recipe.output.getDisplayName + ".")
                  return false
              }
            }
            else {
              item = obj.asInstanceOf[ItemStack]
            }
            input(((i + xOffset) % width) + 3 * (yOffset + ((i + xOffset) / width))) = if (item == null) null else new ItemStack(item.getItem, 1, item.getItemDamage)
          }
          catch {
            case e: ArrayIndexOutOfBoundsException =>
              if ( {xOffset += 1; xOffset} >= 3) {
                xOffset = 0
                yOffset += 1
              }
          }
        }

        for (i <- input) {
          if (i != null) {
            if (i.getItemDamage == OreDictionary.WILDCARD_VALUE) {
              i.setItemDamage(0)
            }
          }
        }
        if (addReversableRecipe(recipe)) {
          done = true
        }
        else {
          if ( {xOffset += 1; xOffset} >= 3) {
            xOffset = 0
            yOffset += 1
          }
          done = false
        }
      }
      done
    } catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "An error occured while registering a shaped orec recipe for " + (if (recipeOutput == null) "null" else recipeOutput.getDisplayName))
        false
    }
  }

  private def registerShapedRecipe(recipeItems: Array[ItemStack], recipeOutput: ItemStack, recipeWidth: Int, recipeHeight: Int): Boolean = {
    try {
      var done = false
      var xoffset = 0
      var yoffset = 0
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
      while ((!done) && ((xoffset + recipeWidth) <= 3) && ((yoffset + recipeHeight) <= 3)) {
        for (i <- 0 until Math.min(recipeItems.length, 9)) {
          val item = recipeItems(i)
          input(((i + xoffset) % recipeWidth) + 3 * (yoffset + ((i + xoffset) / recipeWidth))) = if (item == null) null else new ItemStack(item.getItem, 1, item.getItemDamage)
        }
        if (addReversableRecipe(recipe))
          done = true
        else {
          if (({xoffset += 1; xoffset} + recipeWidth) > 3) {
            xoffset = 0
            yoffset += 1
          }
          done = false
        }
      }
      done
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "An error occured while registering shaped recipe for " + (if (recipeOutput == null) "null" else recipeOutput.getDisplayName) + ".  Width: " + recipeWidth + " Height: " + recipeHeight)
        false
    }
  }

  private def registerShapelessOreRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      var valid = false
      var slots = new Array[Int](recipeItems.size)
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
      val timeStart = System.currentTimeMillis
      if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
        recipe.output.setItemDamage(0)
      }
      val offset = 0
      while (!valid && ((offset + recipeItems.size) <= 9)) {
        for (i <- 0 until slots.length) {
          slots(i) = i
        }
        while (!valid) {
          for (i <- 0 until Math.min(slots.length, 9)) {
            var item: ItemStack = null
            val obj = recipeItems.get(i)
            if (obj.isInstanceOf[util.ArrayList[_]]) {
              try {
                item = obj.asInstanceOf[util.ArrayList[ItemStack]].get(0)
              }
              catch {
                case exc: IndexOutOfBoundsException =>
                  Femtocraft.log(Level.ERROR, "Ore recipe with nothing registered in " + "ore dictionary for " + recipe.output.getDisplayName + ".")
                  return false
              }
            }
            else {
              item = obj.asInstanceOf[ItemStack]
            }
            input(slots(i) + offset) = if (item == null) null else item.copy
          }

          for (i <- input) {
            if (i != null) {
              if (i.getItemDamage == OreDictionary.WILDCARD_VALUE) {
                i.setItemDamage(0)
              }
            }
          }
          if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
            return false
          }

          if (addReversableRecipe(recipe))
            valid = true
          else {
            slots = permute(slots)
            valid = false
          }
        }
      }

      valid
    } catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "An error occured while registering a shapeless ore recipe for " + (if (recipeOutput == null) "null" else recipeOutput.getDisplayName))
        false
    }
  }

  private def registerShapelessRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      var valid = false
      val slots = new Array[Int](recipeItems.size)
      val input = scala.Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO, FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
      val timeStart = System.currentTimeMillis
      val offset = 0
      while (!valid && ((offset + recipeItems.size) <= 9)) {
        for (i <- 0 until slots.length) {
          slots(i) = i
        }

        while (!valid) {
          for (i <- 0 until Math.min(slots.length, 9)) {
            val item = recipeItems.get(i).asInstanceOf[ItemStack]
            input(slots(i) + offset) = if (item == null) null else item.copy
          }

          if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
            return false
          }
          addReversableRecipe(recipe)
          valid = true
        }
      }
      valid
    } catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR, "An error occured while registering a shapeless recipe for " + (if (recipeOutput == null) "null" else recipeOutput.getDisplayName))
        false
    }
  }

  private def permute(slots: Array[Int]): Array[Int] = {
    val k = findHighestK(slots)
    val i = findHigherI(slots, k)
    val prev = slots(k)
    slots(k) = slots(i)
    slots(i) = prev
    val remaining = Math.ceil((slots.length - k + 1) / 2f).toInt
    var r = k + 1
    var n = 0
    while ((r < slots.length) && (n < remaining)) {
      val pr = slots(r)
      slots(r) = slots(slots.length - n - 1)
      slots(slots.length - n - 1) = pr
      r += 1
      n += 1
    }
    slots
  }

  private def findHighestK(slots: Array[Int]): Int = {
    var ret: Int = 0
    for (i <- 0 until (slots.length - 1)) {
      if ((slots(i) < slots(i + 1)) && (ret < i)) {
        ret = i
      }
    }
    ret
  }

  private def findHigherI(slots: Array[Int], k: Int): Int = {
    var ret: Int = 0
    for (i <- 0 until slots.length) {
      if ((slots(k) < slots(i)) && (ret < i)) {
        ret = i
      }
    }
    ret
  }

  private def registerFemtocraftAssemblerRecipes() {
    addDecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.paper, 3), null, null, null, null, null, null, null, null), 0, new ItemStack(Femtocraft.itemPaperSchematic), EnumTechLevel.MACRO, FemtocraftTechnologies.ALGORITHMS))
    addRecompositionRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemSpoolGold), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Femtocraft.itemConductivePowder), new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE), new ItemStack(Blocks.planks, 1, OreDictionary.WILDCARD_VALUE)), 0, new ItemStack(Femtocraft.itemMicrochip, 6), EnumTechLevel.MICRO, FemtocraftTechnologies.BASIC_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.blockMicroChargingCoil), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockMicroChargingCapacitor), EnumTechLevel.MICRO, FemtocraftTechnologies.POTENTIAL_HARVESTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Items.gold_ingot), new ItemStack(Femtocraft.itemIngotTemperedTitanium), null, new ItemStack(Femtocraft.itemVacuumCore), null, new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Items.gold_ingot), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockVacuumTube, 16), EnumTechLevel.MICRO, FemtocraftTechnologies.VACUUM_TUBES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemDopedBoard), new ItemStack(Femtocraft.itemMicrochip), new ItemStack(Femtocraft.itemDopedBoard), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.quartz)), 0, new ItemStack(Femtocraft.itemNanochip, 2), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.comparator), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.redstone), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.redstone), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.comparator)), 0, new ItemStack(Femtocraft.itemNanoCalculator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.redstone), new ItemStack(Items.comparator), new ItemStack(Items.redstone), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Blocks.redstone_torch), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.redstone), new ItemStack(Items.comparator)), 0, new ItemStack(Femtocraft.itemNanoRegulator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.repeater), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.repeater), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.comparator), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Items.repeater)), 0, new ItemStack(Femtocraft.itemNanoSimulator), EnumTechLevel.MICRO, FemtocraftTechnologies.NANO_CIRCUITS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz), null, null, new ItemStack(Femtocraft.itemIngotFarenite), null, null, new ItemStack(Items.quartz), null, null), 0, new ItemStack(Femtocraft.itemFluidicConductor), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemMicroPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.itemNanoPlating, 3), EnumTechLevel.MICRO, FemtocraftTechnologies.ARTIFICIAL_MATERIALS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemMicroLogicCore), new ItemStack(Femtocraft.itemNanoCalculator), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.itemBasicAICore), EnumTechLevel.MICRO, FemtocraftTechnologies.ADVANCED_PROGRAMMING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemNanoSimulator), null, new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemBasicAICore), new ItemStack(Femtocraft.itemNanoCalculator), null, new ItemStack(Femtocraft.itemNanoSimulator), null), 0, new ItemStack(Femtocraft.itemLearningCore), EnumTechLevel.MICRO, FemtocraftTechnologies.PATTERN_RECOGNITION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, new ItemStack(Femtocraft.itemBasicAICore), null, null, new ItemStack(Femtocraft.itemNanoCalculator), null, null), 0, new ItemStack(Femtocraft.itemSchedulerCore), EnumTechLevel.MICRO, FemtocraftTechnologies.WORKLOAD_SCHEDULING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, null, new ItemStack(Femtocraft.itemBasicAICore), null, null, null, new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.itemManagerCore), EnumTechLevel.MICRO, FemtocraftTechnologies.RESOURCE_OPTIMIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.itemNanoCoil, 6), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockNanoCable, 8), EnumTechLevel.MICRO, FemtocraftTechnologies.FARENITE_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockMicroFurnaceUnlit), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockNanoInnervatorUnlit), EnumTechLevel.MICRO, FemtocraftTechnologies.KINETIC_DISSOCIATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockMicroDeconstructor), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium)), 0, new ItemStack(Femtocraft.blockNanoDismantler), EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockMicroReconstructor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemIngotTemperedTitanium), new ItemStack(Femtocraft.itemManagerCore)), 0, new ItemStack(Femtocraft.blockNanoFabricator), EnumTechLevel.MICRO, FemtocraftTechnologies.ATOMIC_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.blockNanoCable)), 0, new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoCoil), new ItemStack(Femtocraft.itemNanoPlating)), 0, new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil), EnumTechLevel.MICRO, FemtocraftTechnologies.GEOTHERMAL_HARNESSING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Items.quartz), new ItemStack(Items.clock), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemNanoCalculator)), 0, new ItemStack(Femtocraft.itemTemporalResonator), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Items.ender_pearl), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Items.ender_pearl), new ItemStack(Items.compass), new ItemStack(Items.ender_pearl), new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Items.ender_pearl), new ItemStack(Femtocraft.itemNanoSimulator)), 0, new ItemStack(Femtocraft.itemDimensionalMonopole), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.iron_ingot), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Items.iron_ingot), new ItemStack(Items.iron_ingot), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Items.iron_ingot)), 0, new ItemStack(Femtocraft.itemSelfFulfillingOracle), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum)), 0, new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), EnumTechLevel.NANO, FemtocraftTechnologies.SPACETIME_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemSelfFulfillingOracle), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemTemporalResonator), new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.blockNanoHorologe), EnumTechLevel.NANO, FemtocraftTechnologies.TEMPORAL_PIPELINING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Femtocraft.itemCrossDimensionalCommunicator), new ItemStack(Femtocraft.itemNanoSimulator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Femtocraft.itemNanoRegulator)), 0, new ItemStack(Femtocraft.blockNanoEnmesher), EnumTechLevel.NANO, FemtocraftTechnologies.DIMENSIONAL_BRAIDING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum), new ItemStack(Femtocraft.itemIngotPlatinum)), 0, new ItemStack(Femtocraft.itemDigitalSchematic, 8), EnumTechLevel.NANO, FemtocraftTechnologies.DIGITIZED_WORKLOADS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCable), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.blockNanoCable), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.blockOrbitalEqualizer), EnumTechLevel.NANO, FemtocraftTechnologies.POTENTIALITY_TRANSFORMATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoRegulator), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.blockMicroCube), new ItemStack(Femtocraft.itemNanoCalculator), new ItemStack(Femtocraft.blockMicroCube)), 0, new ItemStack(Femtocraft.blockNanoCubeFrame), EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.blockNanoCubeFrame), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.blockNanoCable)), 0, new ItemStack(Femtocraft.blockNanoCubePort), EnumTechLevel.NANO, FemtocraftTechnologies.INDUSTRIAL_STORAGE))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemFluidicConductor), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemNanochip)), 0, new ItemStack(Femtocraft.itemFissionReactorPlating), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanoRegulator), null, null, new ItemStack(Femtocraft.itemFissionReactorPlating), null, null, new ItemStack(Femtocraft.itemNanoRegulator), null, null), 0, new ItemStack(Femtocraft.blockFissionReactorHousing), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemLearningCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemSchedulerCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Items.diamond), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemManagerCore), new ItemStack(Femtocraft.blockFissionReactorHousing), new ItemStack(Femtocraft.itemManagerCore)), 0, new ItemStack(Femtocraft.blockFissionReactorCore), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFissionReactorPlating), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.blockOrbitalEqualizer), new ItemStack(Blocks.chest), new ItemStack(Femtocraft.blockOrbitalEqualizer), new ItemStack(Femtocraft.itemNanochip), new ItemStack(Femtocraft.itemFissionReactorPlating), new ItemStack(Femtocraft.itemNanochip)), 0, new ItemStack(Femtocraft.blockDecontaminationChamber), EnumTechLevel.NANO, FemtocraftTechnologies.HARNESSED_NUCLEAR_DECAY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemIngotFarenite), null, new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemIngotThorium), new ItemStack(Femtocraft.itemIngotFarenite), new ItemStack(Femtocraft.itemIngotThorium), new ItemStack(Femtocraft.itemIngotFarenite), null, new ItemStack(Femtocraft.itemIngotFarenite)), 0, new ItemStack(Femtocraft.itemIngotThFaSalt, 2), EnumTechLevel.NANO, FemtocraftTechnologies.THORIUM_FISSIBILITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.ender_eye)), 0, new ItemStack(Femtocraft.itemMinosGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.magma_cream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.magma_cream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.magma_cream), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.magma_cream)), 0, new ItemStack(Femtocraft.itemCharosGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.fire_charge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.fire_charge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.quartz), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.fire_charge), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.fire_charge)), 0, new ItemStack(Femtocraft.itemCerberusGate, 2), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_INTERACTIVITY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemMinosGate), null, new ItemStack(Femtocraft.itemCharosGate), new ItemStack(Items.ghast_tear), new ItemStack(Femtocraft.itemCharosGate), null, new ItemStack(Femtocraft.itemMinosGate), null), 0, new ItemStack(Femtocraft.itemErinyesCircuit, 3), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](null, new ItemStack(Femtocraft.itemCerberusGate), null, new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Items.book), new ItemStack(Femtocraft.itemCharosGate), null, new ItemStack(Femtocraft.itemMinosGate), null), 0, new ItemStack(Femtocraft.itemMinervaComplex), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.slime_ball), null, new ItemStack(Items.slime_ball), new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Blocks.sticky_piston), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemAtlasMount), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.feather), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemHermesBus), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.gunpowder), new ItemStack(Items.ghast_tear), new ItemStack(Items.gunpowder), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Blocks.piston), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemHerculesDrive), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_ROBOTICS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Items.comparator), new ItemStack(Femtocraft.itemPanLocationalComputer), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Femtocraft.itemErinyesCircuit)), 0, new ItemStack(Femtocraft.itemOrpheusProcessor), EnumTechLevel.NANO, FemtocraftTechnologies.QUANTUM_COMPUTING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Items.diamond), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemNanoPlating), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Items.diamond), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Items.diamond)), 0, new ItemStack(Femtocraft.itemFemtoPlating, 2), EnumTechLevel.NANO, FemtocraftTechnologies.ELEMENT_MANUFACTURING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemErinyesCircuit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoInnervatorUnlit), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCerberusGate)), 0, new ItemStack(Femtocraft.blockFemtoImpulserUnlit), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_EXCITATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoDismantler), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCharosGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemCharosGate)), 0, new ItemStack(Femtocraft.blockFemtoRepurposer), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoFabricator), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemMinosGate), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemMinosGate)), 0, new ItemStack(Femtocraft.blockFemtoCoagulator), EnumTechLevel.NANO, FemtocraftTechnologies.PARTICLE_MANIPULATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.netherbrick), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.netherbrick), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemIngotMalenite), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.itemStyxValve), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.itemFemtoCoil, 6), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCable, 8), EnumTechLevel.NANO, FemtocraftTechnologies.DEMONIC_PARTICULATES))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), new ItemStack(Items.nether_star), new ItemStack(Femtocraft.blockCryoEndothermalChargingBase), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemPandoraCube), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockPhlegethonTunnelCore), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Items.ender_eye), new ItemStack(Items.diamond), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemDimensionalMonopole), new ItemStack(Items.ender_eye), new ItemStack(Femtocraft.itemDimensionalMonopole)), 0, new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockCryoEndothermalChargingCoil), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.blockPhlegethonTunnelFrame), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Blocks.piston), new ItemStack(Blocks.iron_block), new ItemStack(Blocks.piston), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockSisyphusStabilizer), EnumTechLevel.NANO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Items.nether_star), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemMinervaComplex), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Femtocraft.itemMinervaComplex)), 0, new ItemStack(Femtocraft.itemQuantumSchematic, 8), EnumTechLevel.FEMTO, FemtocraftTechnologies.SPIN_RETENTION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoHorologe), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemInfallibleEstimator), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemInfallibleEstimator)), 0, new ItemStack(Femtocraft.blockFemtoChronoshifter), EnumTechLevel.FEMTO, FemtocraftTechnologies.TEMPORAL_THREADING))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHerculesDrive), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockNanoEnmesher), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemOrpheusProcessor)), 0, new ItemStack(Femtocraft.blockFemtoEntangler), EnumTechLevel.FEMTO, FemtocraftTechnologies.DIMENSIONAL_SUPERPOSITIONS))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockNanoCable), new ItemStack(Femtocraft.itemStyxValve), new ItemStack(Femtocraft.blockFemtoCable), null, null, null, null, null, null), 0, new ItemStack(Femtocraft.blockNullEqualizer), EnumTechLevel.FEMTO, FemtocraftTechnologies.SPONTANEOUS_GENERATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemAtlasMount), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.blockFemtoCable), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCubePort), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.blockFemtoCubeFrame), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemCerberusGate), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Femtocraft.itemHermesBus), new ItemStack(Femtocraft.itemFemtoPlating)), 0, new ItemStack(Femtocraft.blockFemtoCubeChassis), EnumTechLevel.FEMTO, FemtocraftTechnologies.CORRUPTION_STABILIZATION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.diamond), new ItemStack(Items.netherbrick), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Femtocraft.itemFemtoPlating), new ItemStack(Items.diamond), new ItemStack(Items.diamond), new ItemStack(Items.netherbrick), new ItemStack(Items.diamond)), 0, new ItemStack(Femtocraft.itemStellaratorPlating), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.nether_star), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer)), 0, new ItemStack(Femtocraft.blockStellaratorCore), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Blocks.glass), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Blocks.glass), new ItemStack(Items.diamond), new ItemStack(Blocks.glass), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Blocks.glass), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockStellaratorFocus), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Blocks.nether_brick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.blockStellaratorHousing), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemPhlegethonTunnelPrimer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.blockStellaratorOpticalMaser), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod), new ItemStack(Items.blaze_rod), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockPlasmaConduit, 8), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Items.blaze_rod), new ItemStack(Blocks.dispenser), new ItemStack(Items.netherbrick), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Items.netherbrick)), 0, new ItemStack(Femtocraft.blockPlasmaVent), EnumTechLevel.FEMTO, FemtocraftTechnologies.STELLAR_MIMICRY))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.itemFemtoCoil), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockPlasmaConduit)), 0, new ItemStack(Femtocraft.blockPlasmaTurbine), EnumTechLevel.FEMTO, FemtocraftTechnologies.ENERGY_CONVERSION))
    addReversableRecipe(new AssemblerRecipe(Array[ItemStack](new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Blocks.diamond_block), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating), new ItemStack(Femtocraft.blockSisyphusStabilizer), new ItemStack(Femtocraft.itemStellaratorPlating)), 0, new ItemStack(Femtocraft.blockPlasmaCondenser), EnumTechLevel.FEMTO, FemtocraftTechnologies.MATTER_CONVERSION))
  }

  private def registerRecomposition(normal: Array[ItemStack], recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerRecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      ard.insertRecipe(recipe)
      addRecipeToTechLevelMap(recipe)
      addRecipeToTechnologyMap(recipe)
      return true
    }
    false
  }

  private def registerDecomposition(normal: ItemStack, recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerDecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      ard.insertRecipe(recipe)
      addRecipeToTechLevelMap(recipe)
      addRecipeToTechnologyMap(recipe)
      return true
    }
    false
  }

  private def addRecipeToTechLevelMap(recipe: AssemblerRecipe) {
  }

  private def addRecipeToTechnologyMap(recipe: AssemblerRecipe) {
  }

  private def checkDecomposition(normal: ItemStack, recipe: AssemblerRecipe) = ard.getRecipe(normal) == null

  private def checkRecomposition(normal: Array[ItemStack], recipe: AssemblerRecipe) = ard.getRecipe(normal) == null

  private def normalizedOutput(recipe: AssemblerRecipe): ItemStack = normalizedItem(recipe.output)

  private def normalizedInput(recipe: AssemblerRecipe): Array[ItemStack] = normalizedInput(recipe.input)
}
