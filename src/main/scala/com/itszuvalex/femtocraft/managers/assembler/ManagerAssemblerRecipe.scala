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
import com.itszuvalex.femtocraft.configuration.{AssemblerXMLLoader, AutoGenConfig, XMLAssemblerRecipes}
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
import scala.collection.immutable
import scala.collection.mutable.ArrayBuffer


/** @author chris
  *
  *         This manager is responsible for all [[com.itszuvalex.femtocraft.Femtocraft]] [[AssemblerRecipe]].
  *
  *         All Assembler/Dissassemblers look to this manager for recipe lookup. Recipes can be specified to only be disassemble-able, or only reassemble-able.
  *         Dissassemblers simply break down items, Reassembles must use schematics to specify the recipe to follow. <br> All
  *         recipes are ordered according to their signature in the inventory. The entire 9 slots are used for the input
  *         signature. ItemStack stackSize does not matter for ordering. When reconstructing, items must conform to the input signature, and all 9 slots are
  *         important. Slots that are null in the recipe must not contain any items, and vice versa. This will be separately
  *         enforced in the schematic-creating TileEntities, but it it is also stated here for reference.*/
object ManagerAssemblerRecipe {
  val shapelessPermuteTimeMillis: Long = 100
  private val assemblerRecipeList = new ArrayBuffer[AssemblerRecipe]
  private val assemblerRecompTree = new util.TreeMap[RecompositionKey, AssemblerRecipe]
  private val assemblerDecompTree = new util.TreeMap[DecompositionKey, AssemblerRecipe]

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

  def getDecompositionCounts(i: ItemStack) = DecompositionManager.getDecompositionCounts(i)

  @throws(classOf[IllegalArgumentException])
  def addRecipe(recipe: AssemblerRecipe): Boolean =
    try {
      val result = recipe.`type` match {
        case AssemblerRecipe.RecipeType.Reversible    => addReversableRecipe(recipe)
        case AssemblerRecipe.RecipeType.Decomposition => addDecompositionRecipe(recipe)
        case AssemblerRecipe.RecipeType.Recomposition => addRecompositionRecipe(recipe)
      }
      if (result) Femtocraft.log(Level.INFO, "Added assembler recipe for " + recipe.getRecipeName)
      result
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

  def removeAnyRecipe(recipe: AssemblerRecipe) = { val result = removeDecompositionRecipe(recipe); removeRecompositionRecipe(recipe) || result }

  def removeDecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (checkDecomposition(recipe)) return true
    assemblerDecompTree.remove(new DecompositionKey(recipe))
    if (checkRecomposition(recipe)) assemblerRecipeList -= recipe
    true
  }

  def removeRecompositionRecipe(recipe: AssemblerRecipe): Boolean = {
    if (checkRecomposition(recipe)) return true
    assemblerRecompTree.remove(new RecompositionKey(recipe))
    if (checkDecomposition(recipe)) assemblerRecipeList -= recipe
    true
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

  def getRecipesForTechnology(techName: String): ArrayBuffer[AssemblerRecipe] = assemblerRecipeList
                                                                                .filter(_
                                                                                        .tech
                                                                                        .equalsIgnoreCase(techName))

  def hasResearchedRecipe(recipe: AssemblerRecipe, username: String) = Femtocraft
                                                                       .researchManager
                                                                       .hasPlayerResearchedTechnology(username,
                                                                                                      recipe.tech)

  private def registerShapedRecipe(sr: ShapedRecipes): Boolean = {
    try {
      Femtocraft
      .log(Level.INFO, "Attempting to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + ".")
      val valid = registerShapedRecipe(sr.recipeItems, sr.getRecipeOutput, sr.recipeWidth, sr.recipeHeight)
      if (!valid) {
        Femtocraft
        .log(Level.WARN, "Failed to register shaped assembler recipe for " + sr.getRecipeOutput.getDisplayName + "!")
      } else {
        Femtocraft
        .log(Level.INFO,
             "Loaded Vanilla Minecraft shaped recipe as assembler recipe for " + sr
                                                                                 .getRecipeOutput
                                                                                 .getDisplayName + ".")
      }
      valid
    }
    catch {
      case e: Exception => false
    }
  }

  private def registerShapedOreRecipe(orecipe: ShapedOreRecipe): Boolean = {

    try {
      Femtocraft
      .log(Level.INFO,
           "Attempting to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
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
        Femtocraft
        .log(Level.WARN,
             "Failed to register shaped assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + "!")
      } else {
        Femtocraft
        .log(Level.INFO,
             "Loaded Forge shaped ore recipe as assembler recipe for " + orecipe.getRecipeOutput.getDisplayName + ".")
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
  private def registerShapelessRecipe(recipe: ShapelessRecipes): Boolean = {
    try {
      Femtocraft
      .log(Level.INFO,
           "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
      val valid: Boolean = registerShapelessRecipe(recipe.recipeItems, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft
        .log(Level.WARN,
             "Failed to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
      } else {
        Femtocraft
        .log(Level.INFO,
             "Loaded Vanilla Minecraft shapeless recipe as assembler recipe for + " + recipe
                                                                                      .getRecipeOutput
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
      Femtocraft
      .log(Level.INFO,
           "Attempting to register shapeless assembler recipe for " + recipe.getRecipeOutput.getDisplayName + ".")
      val valid: Boolean = registerShapelessOreRecipe(recipe.getInput, recipe.getRecipeOutput)
      if (!valid) {
        Femtocraft
        .log(Level.WARN,
             "Failed to register shapeless ore assembler recipe for " + recipe.getRecipeOutput.getDisplayName + "!")
      } else {
        Femtocraft
        .log(Level.INFO,
             "Loaded Forge shapeless ore recipe as assembler recipe for + " + recipe
                                                                              .getRecipeOutput
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

    val femtocraftRecipes = new
        XMLAssemblerRecipes(new File(FemtocraftFileUtils.autogenConfigPath, "AssemblerRecipes.xml"))
    if (!AutoGenConfig.shouldRegenFile(femtocraftRecipes.file) && femtocraftRecipes.initialized) {
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
      AutoGenConfig.markFileRegenerated(femtocraftRecipes.file)
    }
    Femtocraft.log(Level.INFO, "Finished registering Femtocraft assembler recipes.")

    val database = new
        XMLAssemblerRecipes(new File(FemtocraftFileUtils.autogenConfigPath, "ScrapedAssemblerRecipes.xml"))
    Femtocraft.log(Level.INFO, "Scraping Minecraft recipe registries for assembler recipe mappings.")
    if (!AutoGenConfig.shouldRegenFile(database.file) && database.initialized) {
      Femtocraft.log(Level.INFO, "Scraped Recipes Database already exists.  Loading from file.")
      database.loadCustomRecipes().view.foreach(addRecipe)
    } else {
      Femtocraft
      .log(Level.WARN,
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
      AutoGenConfig.markFileRegenerated(database.file)
    }
  }

  private def registerRecomposition(recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.Recomposition(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      assemblerRecompTree.put(new RecompositionKey(recipe), recipe)
      assemblerRecipeList += recipe
      return true
    }
    false
  }

  private def registerDecomposition(recipe: AssemblerRecipe): Boolean = {
    val event = new EventAssemblerRegister.Decomposition(recipe)
    Femtocraft.assemblerConfigs.loadAssemblerRecipe(recipe)
    if (!MinecraftForge.EVENT_BUS.post(event)) {
      assemblerDecompTree.put(new DecompositionKey(recipe), recipe)
      if (checkRecomposition(recipe)) /* lg n   compared to n from assemblerRecipeList.contains() */ {
        assemblerRecipeList += recipe
      }
      return true
    }
    false
  }

  private def checkDecomposition(recipe: AssemblerRecipe) = getRecipe(recipe.output) == null

  private def checkRecomposition(recipe: AssemblerRecipe) = getRecipe(recipe.input) == null

  //  private def registerMacroDecompositionRecipes() {
  //  }
  //
  private def registerShapedOreRecipe(recipeInput: Array[AnyRef], recipeOutput: ItemStack, recipeWidth: Int,
                                      recipeHeight: Int): Boolean = {
    try {
      val recipeItems = Array.fill[ItemStack](recipeInput.length)(null)
      recipeInput.map { case i: ItemStack => i.copy
      case list: util.ArrayList[_]        => try list.asInstanceOf[util.ArrayList[ItemStack]].get(0).copy
      catch {
        case exc: IndexOutOfBoundsException =>
          Femtocraft
          .log(Level.ERROR,
               "Ore recipe with nothing registered in ore dictionary for " + (if (recipeOutput == null) {
                 "null"
               } else {
                 recipeOutput.getDisplayName
               }) + ".")
          return false
      }
      case _                              => null
                      }.copyToArray(recipeItems)
      for (xoffset <- 0 to (3 - recipeWidth)) {
        for (yoffset <- 0 to (3 - recipeHeight)) {
          val input = Array.fill[ItemStack](9)(null)
          for (i <- 0 until Math.min(recipeItems.length, 9)) {
            val item = recipeItems(i)
            input(((i + xoffset) % recipeWidth) + 3 * (yoffset + ((i + xoffset) / recipeWidth))) = if (item == null) {
              null
            } else {
              //Has to be stackSize of 1, or weird things happen.
              new ItemStack(item.getItem, 1, item.getItemDamage)
            }
          }
          val recipe = new AssemblerRecipe(input,
                                           0,
                                           recipeOutput.copy,
                                           EnumTechLevel.MACRO,
                                           FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
          //          if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
          //            recipe.output.setItemDamage(0)
          //          }
          if (addReversableRecipe(recipe)) return true
        }
      }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR, "An error occurred while registering a shaped ore recipe for " + (if (recipeOutput == null) {
          "null"
        } else {
          recipeOutput.getDisplayName
        }))
    }
    false
  }

  private def registerShapedRecipe(recipeItems: Array[ItemStack], recipeOutput: ItemStack, recipeWidth: Int,
                                   recipeHeight: Int): Boolean = {
    try {
      for (xoffset <- 0 to (3 - recipeWidth)) {
        for (yoffset <- 0 to (3 - recipeHeight)) {
          val input = Array.fill[ItemStack](9)(null)
          for (i <- 0 until Math.min(recipeItems.length, 9)) {
            val item = recipeItems(i)
            input(((i + xoffset) % recipeWidth) + 3 * (yoffset + ((i + xoffset) / recipeWidth))) = if (item == null) {
              null
            } else {
              //Has to be stackSize of 1, or weird things happen.
              new ItemStack(item.getItem, 1, item.getItemDamage)
            }
          }
          val recipe = new AssemblerRecipe(input,
                                           0,
                                           recipeOutput.copy,
                                           EnumTechLevel.MACRO,
                                           FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)
          if (addReversableRecipe(recipe)) return true
        }
      }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR, "An error occurred while registering shaped recipe for " + (if (recipeOutput == null) {
          "null"
        } else {
          recipeOutput.getDisplayName
        }) + ".  Width: " + recipeWidth + " Height: " + recipeHeight)
    }
    false
  }

  private def registerShapelessOreRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      val timeStart = System.currentTimeMillis
      val input = Array.fill[ItemStack](9)(null)
      recipeItems.map { case i: ItemStack => i.copy
      case list: util.ArrayList[_]        => try list.asInstanceOf[util.ArrayList[ItemStack]].get(0).copy
      catch {
        case exc: IndexOutOfBoundsException =>
          Femtocraft
          .log(Level.ERROR,
               "Ore recipe with nothing registered in ore dictionary for " + (if (recipeOutput == null) {
                 "null"
               } else {
                 recipeOutput.getDisplayName
               }) + ".")
          return false
      }
      case _ => null
                      }.copyToArray(input)
      input.permutations.exists { permute =>
        val recipe = new AssemblerRecipe(permute,
                                         0,
                                         recipeOutput.copy,
                                         EnumTechLevel.MACRO,
                                         FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)

        //        if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
        //          recipe.output.setItemDamage(0)
        //        }
        //        if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
        //          return false
        //        }
        checkDecomposition(recipe) && checkRecomposition(recipe) && addReversableRecipe(recipe)
                                }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR,
             "An error occured while registering a shapeless ore recipe for " + (if (recipeOutput == null) {
               "null"
             } else {
               recipeOutput.getDisplayName
             }))
        false
    }
  }

  private def registerShapelessRecipe(recipeItems: util.List[_], recipeOutput: ItemStack): Boolean = {
    try {
      val timeStart = System.currentTimeMillis
      val input = Array.fill[ItemStack](9)(null)
      recipeItems.map { case i: ItemStack => i.copy()
      case _                              => null
                      }.copyToArray(input)
      input.permutations.exists { permute =>
        val recipe = new AssemblerRecipe(permute,
                                         0,
                                         recipeOutput.copy,
                                         EnumTechLevel.MACRO,
                                         FemtocraftTechnologies.MACROSCOPIC_STRUCTURES)

        //        if (recipe.output.getItemDamage == OreDictionary.WILDCARD_VALUE) {
        //          recipe.output.setItemDamage(0)
        //        }
        //        if ((System.currentTimeMillis - timeStart) > ManagerAssemblerRecipe.shapelessPermuteTimeMillis) {
        //          return false
        //        }
        checkDecomposition(recipe) && checkRecomposition(recipe) && addReversableRecipe(recipe)

                                }
    }
    catch {
      case e: Exception =>
        Femtocraft
        .log(Level.ERROR, "An error occured while registering a shapeless recipe for " + (if (recipeOutput == null) {
          "null"
        } else {
          recipeOutput.getDisplayName
        }))
        false
    }
  }

  //
  //  private def permute(slots: Array[Int]): Array[Int] = {
  //    val k = findHighestK(slots)
  //    val i = findHigherI(slots, k)
  //    val prev = slots(k)
  //    slots(k) = slots(i)
  //    slots(i) = prev
  //    val remaining = Math.ceil((slots.length - k + 1) / 2f).toInt
  //    var r = k + 1
  //    var n = 0
  //    while ((r < slots.length) && (n < remaining)) {
  //      val pr = slots(r)
  //      slots(r) = slots(slots.length - n - 1)
  //      slots(slots.length - n - 1) = pr
  //      r += 1
  //      n += 1
  //    }
  //    slots
  //  }
  //
  //  private def findHighestK(slots: Array[Int]): Int = {
  //    var ret: Int = 0
  //    for (i <- 0 until (slots.length - 1)) {
  //      if ((slots(i) < slots(i + 1)) && (ret < i)) {
  //        ret = i
  //      }
  //    }
  //    ret
  //  }
  //
  //  private def findHigherI(slots: Array[Int], k: Int): Int = {
  //    var ret: Int = 0
  //    for (i <- 0 until slots.length) {
  //      if ((slots(k) < slots(i)) && (ret < i)) {
  //        ret = i
  //      }
  //    }
  //    ret
  //  }
  class RecompositionKey(val input: Array[ItemStack]) extends Comparable[RecompositionKey] {
    def this(recipe: AssemblerRecipe) = this(recipe.input)

    override def compareTo(o: RecompositionKey): Int = {
      for (i <- 0 until input.length) {
        FemtocraftUtils.compareItem(input(i), o.input(i)) match {case 0 => ; case t => return t}
      }
      0
    }
  }

  class DecompositionKey(val output: ItemStack) extends Comparable[DecompositionKey] {
    def this(recipe: AssemblerRecipe) = this(recipe.output)

    override def compareTo(o: DecompositionKey): Int = FemtocraftUtils.compareItem(output, o.output)
  }

  private object DecompositionManager {
    private val decompCountMemo = new util.TreeMap[ComponentKey, (Float, Float, Float, Float)]

    def getDecompositionCounts(i: ItemStack): (Int, Int, Int, Int) = {
      val (mo, at, pa, ma) = getDecompositionCounts(i, immutable.TreeSet[ComponentKey]())
      (mo.toInt, at.toInt, pa.toInt, ma.toInt)
    }

    private def getDecompositionCounts(i: ItemStack,
                                       s: immutable.TreeSet[ComponentKey]): (Float, Float, Float, Float) = {
      if (i == null) return (0, 0, 0, 0)

      var molecules = 0f
      var atoms = 0f
      var particles = 0f
      var mass = 0f

      decompCountMemo.get(new ComponentKey(i)) match {
        case (mol, at, pa, ma) => return (mol * i.stackSize, at * i.stackSize, pa * i.stackSize, ma * i.stackSize)
        case _                 =>
      }

      i match {
        case micro if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.MICRO) => molecules = 1
        case nano if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.NANO)   => atoms = 1
        case femto if ComponentRegistry.isItemComponent(i.getItem, EnumTechLevel.FEMTO) => particles = 1
        case _                                                                          =>
      }

      if (!s.contains(new ComponentKey(i))) {
        val recipe = getRecipe(i)
        var ret: (Float, Float, Float, Float) = null
        if (recipe != null && recipe.output != null) {
          val stackSize = Math.max(recipe.output.stackSize, 1)
          mass += recipe.mass / stackSize
          recipe.input.foreach { case null =>
          case in: ItemStack               =>
            val (m, a, p, ma) = getDecompositionCounts(in, s + new ComponentKey(i))
            molecules += m
            atoms += a
            particles += p
            mass += ma
                               }

          ret = (molecules / stackSize, atoms / stackSize, particles / stackSize, mass / stackSize)
        } else {
          ret = (molecules / i.stackSize, atoms / i.stackSize, particles / i.stackSize, mass / i.stackSize)
        }
        decompCountMemo.put(new ComponentKey(i), ret)
        val (mo, at, pa, ma) = ret
        return (mo * i.stackSize, at * i.stackSize, pa * i.stackSize, ma * i.stackSize)
      }
      (0, 0, 0, 0)
    }

    class ComponentKey(val id: Int, val damage: Int) extends Comparable[ComponentKey] {
      def this(i: ItemStack) = this(i.itemID, i.getItemDamage)

      override def compareTo(o: ComponentKey): Int = {
        if (id > o.id) return 1
        if (id < o.id) return -1
        if (damage == OreDictionary.WILDCARD_VALUE) return 0
        if (o.damage == OreDictionary.WILDCARD_VALUE) return 0
        if (damage < o.damage) return 1
        if (damage > o.damage) return -1
        0
      }
    }

  }

}
