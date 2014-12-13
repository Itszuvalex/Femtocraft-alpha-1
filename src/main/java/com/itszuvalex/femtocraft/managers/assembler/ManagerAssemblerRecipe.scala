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

import java.io.File
import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.events.EventAssemblerRegister
import com.itszuvalex.femtocraft.api.{AssemblerRecipe, EnumTechLevel}
import com.itszuvalex.femtocraft.configuration.{AssemblerXMLLoader, XMLAssemblerRecipes}
import com.itszuvalex.femtocraft.implicits.IDImplicits._
import com.itszuvalex.femtocraft.managers.research.Technology
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import com.itszuvalex.femtocraft.utils.{FemtocraftFileUtils, FemtocraftUtils}
import net.minecraft.item.ItemStack
import net.minecraft.item.crafting.{CraftingManager, IRecipe, ShapedRecipes, ShapelessRecipes}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.oredict.{OreDictionary, ShapedOreRecipe, ShapelessOreRecipe}
import org.apache.logging.log4j.Level

import scala.collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer
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
  private lazy val decompCountMemo = new mutable.HashMap[Int, (Float, Float, Float, Float)]

  private val assemblerRecipeList = new ArrayBuffer[AssemblerRecipe]
  private val assemblerRecompTree = new util.TreeMap[RecompositionKey, AssemblerRecipe]()
  private val assemblerDecompTree = new util.TreeMap[DecompositionKey, AssemblerRecipe]()

  def init() {
    registerRecipes()
  }

  @throws(classOf[IllegalArgumentException])
  def addReversableRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    if (recipe.output == null) {
      throw new IllegalArgumentException("AssemblerRecipe - Output ItemStack cannot be null.")
    }
    if (!checkDecomposition(recipe) || !checkRecomposition(recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.getRecipeName + ".")
      return false
    }
    registerRecomposition(recipe) && registerDecomposition(recipe)
  }

  def getAllRecipes = assemblerRecipeList

  def getDecompositionCounts(i: ItemStack): (Int, Int, Int, Int) = {
    val (mo, at, pa, ma) = getDecompositionCounts(i, immutable.TreeSet[Int]())
    (mo.toInt, at.toInt, pa.toInt, ma.toInt)
  }

  @throws(classOf[IllegalArgumentException])
  def addRecipe(recipe: AssemblerRecipe): Boolean =
    try {
      recipe.`type` match {
        case AssemblerRecipe.RecipeType.Reversable    => addReversableRecipe(recipe)
        case AssemblerRecipe.RecipeType.Decomposition => addDecompositionRecipe(recipe)
        case AssemblerRecipe.RecipeType.Recomposition => addRecompositionRecipe(recipe)
      }
    }
    catch {
      case e: Throwable => false
    }


  @throws(classOf[IllegalArgumentException])
  def addRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    if (recipe.output == null) {
      throw new IllegalArgumentException("AssemblerRecipe - Output ItemStack cannot be null.")
    }
    if (!checkRecomposition(recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.getRecipeName + ".")
      return false
    }
    registerRecomposition(recipe)
  }

  @throws(classOf[IllegalArgumentException])
  def addDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (recipe.input.length != 9) {
      throw new IllegalArgumentException("AssemblerRecipe - Invalid Input Array Length!  Must be 9!")
    }
    if (recipe.output == null) {
      throw new IllegalArgumentException("AssemblerRecipe - Output ItemStack cannot be null.")
    }
    if (!checkDecomposition(recipe)) {
      Femtocraft.log(Level.WARN, "Assembler recipe already exists for " + recipe.getRecipeName + ".")
      return false
    }
    registerDecomposition(recipe)
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
    assemblerRecompTree.get(new RecompositionKey(input))
  }

  def canCraft(input: ItemStack) = {
    val recipe = getRecipe(input)
    recipe != null && input.stackSize >= recipe.output.stackSize && FemtocraftUtils
                                                                    .compareItem(recipe.output, input) == 0
  }

  def getRecipe(output: ItemStack): AssemblerRecipe = assemblerDecompTree.get(new DecompositionKey(output))

  def getRecipesForTechLevel(level: EnumTechLevel) = assemblerRecipeList.filter(_.enumTechLevel == level)

  def getRecipesForTechnology(tech: Technology): ArrayBuffer[AssemblerRecipe] = getRecipesForTechnology(tech.getName)

  def getRecipesForTechnology(techName: String): ArrayBuffer[AssemblerRecipe] = assemblerRecipeList.filter(_.tech
                                                                                                           .equalsIgnoreCase(
      techName))

  def hasResearchedRecipe(recipe: AssemblerRecipe, username: String) = Femtocraft.researchManager
                                                                       .hasPlayerResearchedTechnology(username,
                                                                                                      recipe.tech)

  private def registerShapedRecipe(sr: ShapedRecipes): Boolean = {
    try {
      Femtocraft.log(Level.INFO,
                     "Attempting to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
      val valid = registerShapedRecipe(sr.recipeItems, sr.getRecipeOutput, sr.recipeWidth, sr.recipeHeight)
      if (!valid) {
        Femtocraft
        .log(Level.WARN, "Failed to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + "!")
      }
      else {
        Femtocraft.log(Level.INFO,
                       "Loaded Vanilla Minecraft shaped recipe as assembler recipe for " + sr.getRecipeOutput
                                                                                           .getDisplayName + ".")
      }
      valid
    }
    catch {
      case e: Exception => false
    }
  }

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

  private def registerShapedOreRecipe(orecipe: ShapedOreRecipe): Boolean = {

    try {
      Femtocraft.log(Level.INFO, "Attempting to register shaped assembler recipe for " + orecipe.getRecipeOutput
                                                                                         .getDisplayName + ".")
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
        Femtocraft.log(Level.WARN, "Failed to register shaped assembler recipe for " + orecipe.getRecipeOutput
                                                                                       .getDisplayName + "!")
      }
      else {
        Femtocraft.log(Level.INFO, "Loaded Forge shaped ore recipe as assembler recipe for " + orecipe.getRecipeOutput
                                                                                               .getDisplayName + ".")
      }
      valid
    }
    catch {
      case e: Exception => false
    }
  }

  private def registerShapelessRecipe(recipe: ShapelessRecipes): Boolean = {
    try {
      Femtocraft.log(Level.INFO, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput
                                                                                            .getDisplayName + ".")
      val valid: Boolean = registerShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft.log(Level.WARN, "Failed to register shapeless assembler recipe for " + recipe.getRecipeOutput
                                                                                          .getDisplayName + "!")
        Femtocraft.log(Level.WARN,
                       "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
      }
      else {
        Femtocraft.log(Level.INFO,
                       "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + " + recipe.getRecipeOutput
                                                                                                .getDisplayName + ".")
      }
      valid
    }
    catch {
      case e: Exception => false
    }
  }

  private def registerShapelessOreRecipe(recipe: ShapelessOreRecipe): Boolean = {
    try {
      Femtocraft.log(Level.INFO, "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput
                                                                                            .getDisplayName + ".")
      val valid: Boolean = registerShapelessOreRecipe(recipe.getInput, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft.log(Level.WARN, "Failed to register shapeless ore assembler recipe for " + recipe.getRecipeOutput
                                                                                              .getDisplayName + "!")
        Femtocraft.log(Level.WARN,
                       "I have no clue how this would happen...as the search space is literally " + "thousands of configurations.  Sorry for the wait.")
      }
      else {
        Femtocraft.log(Level.INFO,
                       "Loaded Forge shapeless ore recipe as assembler recipe for + " + recipe.getRecipeOutput
                                                                                        .getDisplayName + ".")
      }
      valid
    }
    catch {
      case e: Exception => false
    }

  }

  private def registerRecipes() {
    Femtocraft.log(Level.INFO, "Registering Femtocraft assembler recipes.")

    Femtocraft.log(Level.INFO, "Registering custom assembler recipes.")
    val customRecipes = new AssemblerXMLLoader(new File(FemtocraftFileUtils.customConfigPath + "Assembler/"))
                        .loadItems()
    customRecipes.view.foreach(addRecipe)
    Femtocraft.log(Level.INFO, "Finished registering custom assembler recipes.")

    val defaults = new ArrayBuffer[AssemblerRecipe]()

    val femtocraftRecipes = new XMLAssemblerRecipes(new File(FemtocraftFileUtils.configFolder, "AssemblerRecipes.xml"))
    if (femtocraftRecipes.initialized) {
      defaults ++= femtocraftRecipes.loadCustomRecipes()
      defaults.view.foreach(addRecipe)
    } else {
      Femtocraft.assemblerConfigs.setBatchLoading(true)
      defaults ++= AssemblerDefaults.getFemtoDefaults
      defaults ++= AssemblerDefaults.getNanoDefaults
      defaults ++= AssemblerDefaults.getMicroDefaults
      defaults ++= AssemblerDefaults.getMacroDefaults
      defaults ++= AssemblerDefaults.getFemtocraftDefaults
      defaults.view.foreach(addRecipe)
      Femtocraft.assemblerConfigs.setBatchLoading(false)
      Femtocraft.log(Level.INFO, "Saving Femtocraft default recipes to file.")
      femtocraftRecipes.seedInitialRecipes(defaults)
      Femtocraft.log(Level.INFO, "Finished saving Femtocraft default recipes.")
    }
    Femtocraft.log(Level.INFO, "Finished registering Femtocraft assembler recipes.")

    val database = new XMLAssemblerRecipes(new File(FemtocraftFileUtils.configFolder, "ScrapedAssemblerRecipes.xml"))
    Femtocraft.log(Level.INFO, "Scraping Minecraft recipe registries for assembler recipe mappings.")
    if (database.initialized) {
      Femtocraft.log(Level.INFO, "Scraped Recipes Database already exists.  Loading from file.")
      database.loadCustomRecipes().view.foreach(addRecipe)
    }
    else {
      Femtocraft.log(Level.WARN,
                     "Registering assembler recipes from Vanilla Minecraft's Crafting Manager.\t This may take " + "awhile.")
      val recipes = CraftingManager.getInstance.getRecipeList.view.collect { case t: IRecipe => t}
      Femtocraft.log(Level.WARN, "Registering shaped recipes from Vanilla Minecraft's Crafting Manager.")
      recipes.view.collect { case t: ShapedRecipes => t}.foreach(registerShapedRecipe)
      Femtocraft.log(Level.WARN, "Registering shaped ore recipes from Forge.")
      recipes.view.collect { case t: ShapedOreRecipe => t}.foreach(registerShapedOreRecipe)
      Femtocraft.log(Level.WARN, "Registering shapeless recipes from Vanilla Minecraft's Crafting Manager.")
      recipes.view.collect { case t: ShapelessRecipes => t}.foreach(registerShapelessRecipe)
      Femtocraft.log(Level.WARN, "Registering shapeless ore recipes from Forge.")
      recipes.view.collect { case t: ShapelessOreRecipe => t}.foreach(registerShapelessOreRecipe)
      Femtocraft.log(Level.INFO, "Finished mapping Minecraft recipes to assembler recipes.")
      Femtocraft.log(Level.INFO, "Saving scraped recipes to file.")
      database.seedInitialRecipes(getAllRecipes -- customRecipes -- defaults)
      Femtocraft.log(Level.INFO, "Finished saving scraped recipes.")
    }
  }

  private def registerRecomposition(recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerRecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      assemblerRecompTree.put(new RecompositionKey(recipe), recipe)
      assemblerRecipeList += recipe
      return true
    }
    false
  }

  private def registerDecomposition(recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.AssemblerDecompositionRegisterEvent(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      assemblerDecompTree.put(new DecompositionKey(recipe), recipe)
      if (getRecipe(recipe.input) == null) /* lg n   compared to n from assemblerRecipeList.contains() */ {
        assemblerRecipeList += recipe
      }
      return true
    }
    false
  }


  private def checkDecomposition(recipe: AssemblerRecipe) = getRecipe(recipe.output) == null

  private def checkRecomposition(recipe: AssemblerRecipe) = getRecipe(recipe.input) == null

  private def getDecompositionCounts(i: ItemStack, s: immutable.TreeSet[Int]): (Float, Float, Float, Float) = {
    if (i == null) return (0, 0, 0, 0)

    var molecules = 0f
    var atoms = 0f
    var particles = 0f
    var mass = 0f

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
      var ret: (Float, Float, Float, Float) = null
      if (recipe != null) {
        mass += (recipe.mass * i.stackSize).toFloat / recipe.output.stackSize
        recipe.input.foreach {
                               case in if !ItemStack.areItemStacksEqual(i, in) =>
                                 val (m, a, p, ma) = getDecompositionCounts(in, s + i.itemID)
                                 molecules += m
                                 atoms += a
                                 particles += p
                                 mass += ma
                               case _                                          =>
                             }

        ret = (molecules / recipe.output.stackSize / i.stackSize,
          atoms / recipe.output.stackSize / i.stackSize,
          particles / recipe.output.stackSize / i.stackSize,
          mass / recipe.output.stackSize / i.stackSize)
      }
      else {
        ret = (molecules, atoms, particles, mass)
      }
      decompCountMemo.put(i.itemID, ret)
      val (mo, at, pa, ma) = ret
      return (mo * i.stackSize, at * i.stackSize, pa * i.stackSize, ma * i.stackSize)
    }
    (0, 0, 0, 0)
  }

  //  private def registerMacroDecompositionRecipes() {
  //  }
  //
  private def registerShapedOreRecipe(recipeInput: Array[AnyRef], recipeOutput: ItemStack, width: Int,
                                      height: Int): Boolean = {
    try {
      var done = false
      var xOffset = 0
      var yOffset = 0
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO,
                                       FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
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
                  Femtocraft.log(Level.ERROR,
                                 "Ore recipe with nothing registered in " + "ore dictionary for " + recipe.getRecipeName + ".")
                  return false
              }
            }
            else {
              item = obj.asInstanceOf[ItemStack]
            }
            input(((i + xOffset) % width) + 3 * (yOffset + ((i + xOffset) / width))) = if (item == null) {
              null
            }
                                                                                       else {
                                                                                         new
                                                                                             ItemStack(item.getItem, 1,
                                                                                                       item
                                                                                                       .getItemDamage)
                                                                                       }
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
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR,
                       "An error occured while registering a shaped orec recipe for " + (if (recipeOutput == null) {
                         "null"
                       }
                                                                                         else {
                                                                                           recipeOutput
                                                                                           .getDisplayName
                                                                                         }))
        false
    }
  }


  private def registerShapedRecipe(recipeItems: Array[ItemStack], recipeOutput: ItemStack, recipeWidth: Int,
                                   recipeHeight: Int): Boolean = {
    try {
      var done = false
      var xoffset = 0
      var yoffset = 0
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO,
                                       FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
      while ((!done) && ((xoffset + recipeWidth) <= 3) && ((yoffset + recipeHeight) <= 3)) {
        for (i <- 0 until Math.min(recipeItems.length, 9)) {
          val item = recipeItems(i)
          input(((i + xoffset) % recipeWidth) + 3 * (yoffset + ((i + xoffset) / recipeWidth))) = if (item == null) {
            null
          }
                                                                                                 else {
                                                                                                   new ItemStack(item
                                                                                                                 .getItem,
                                                                                                                 1, item
                                                                                                                    .getItemDamage)
                                                                                                 }
        }
        if (addReversableRecipe(recipe)) {
          done = true
        }
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
        Femtocraft.log(Level.ERROR,
                       "An error occured while registering shaped recipe for " + (if (recipeOutput == null) {
                         "null"
                       }
                                                                                  else {
                                                                                    recipeOutput
                                                                                    .getDisplayName
                                                                                  }) + ".  Width: " + recipeWidth + " Height: " + recipeHeight)
        false
    }
  }

  private def registerShapelessOreRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      var valid = false
      var slots = new Array[Int](recipeItems.size)
      val input = Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO,
                                       FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
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
                  Femtocraft.log(Level.ERROR,
                                 "Ore recipe with nothing registered in " + "ore dictionary for " + recipe
                                                                                                    .getRecipeName + ".")
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

          if (addReversableRecipe(recipe)) {
            valid = true
          }
          else {
            slots = permute(slots)
            valid = false
          }
        }
      }

      valid
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR,
                       "An error occured while registering a shapeless ore recipe for " + (if (recipeOutput == null) {
                         "null"
                       }
                                                                                           else {
                                                                                             recipeOutput
                                                                                             .getDisplayName
                                                                                           }))
        false
    }
  }

  private def registerShapelessRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      var valid = false
      val slots = new Array[Int](recipeItems.size)
      val input = scala.Array.fill[ItemStack](9)(null)
      val recipe = new AssemblerRecipe(input, 0, recipeOutput.copy, EnumTechLevel.MACRO,
                                       FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
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
    }
    catch {
      case e: Exception =>
        Femtocraft.log(Level.ERROR,
                       "An error occured while registering a shapeless recipe for " + (if (recipeOutput == null) {
                         "null"
                       }
                                                                                       else {
                                                                                         recipeOutput
                                                                                         .getDisplayName
                                                                                       }))
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

  class RecompositionKey(val input: Array[ItemStack]) extends Comparable[RecompositionKey] {
    def this(recipe: AssemblerRecipe) = this(recipe.input)

    override def compareTo(o: RecompositionKey): Int = {
      (input zip o.input)
      .foreach { case (a, b) => FemtocraftUtils.compareItem(a, b) match {case 0 => ; case t => return t}}
      0
    }
  }

  class DecompositionKey(val output: ItemStack) extends Comparable[DecompositionKey] {
    def this(recipe: AssemblerRecipe) = this(recipe.output)

    override def compareTo(o: DecompositionKey): Int = FemtocraftUtils.compareItem(output, o.output)
  }

}
