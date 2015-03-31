package com.itszuvalex.femtocraft.api.managers

import java.util
import java.util.UUID

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import com.itszuvalex.femtocraft.api.research.ITechnology
import net.minecraft.item.ItemStack

/**
 * Created by Itszuvalex on 1/5/15.
 */
trait IAssemblerRecipeManager {

  /**
   *
   * @param recipe
   * @return Attempts to add recipe to both decomposition and recomposition lists.
   *         Returns false and modifies neither if either mapping is found in either list.
   */
  def addReversibleRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Attempts to add recipe to the recomposition list.  Returns
   *         false if its input mapping is already present.
   */
  def addRecompositionRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Attempts to add recipe to the decomposition list.  Returns false
   *         if its output mapping is already present.
   */
  def addDecompositionRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Forwards to the equivalent addRecipe function based
   *         on the AssemblerRecipe type of recipe.
   */
  def addRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @return All recipes present in either decomposition or recomposition lists.
   */
  def getAllRecipes: util.Collection[AssemblerRecipe]

  /**
   *
   * @return Returns all recipes in the decomposition list.
   */
  def getDecompositionRecipes: util.Collection[AssemblerRecipe]

  /**
   *
   * @return Returns all recipes in the recomposition list.
   */
  def getRecompositionRecipes: util.Collection[AssemblerRecipe]

  /**
   *
   * @return Returns all recipes present in both recomposition and
   *         decomposition lists.
   *
   *         This is calculated on the fly and is not cached.
   */
  def getReversibleRecipes: util.Collection[AssemblerRecipe]

  /**
   *
   * @param recipe
   * @return Removes recipe if it is present from either decomposition
   *         or recomposition trees.  Returns true if found in either list.
   */
  def removeAnyRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Removes recipe if it is present in the decomposition tree.  Returns true
   *         if a matching recipe is found.
   */
  def removeDecompositionRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Removes recipe if it is present in the recomposition tree.  Returns true
   *         if a matching recipe is found.
   */
  def removeRecompositionRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param recipe
   * @return Removes recipe if it is present in both the decomposition tree and recomposition tree.  Returns true
   *         if a matching recipe is found in both.  Returns false and modifies neither if recipe
   *         is not found in both trees.
   */
  def removeReversibleRecipe(recipe: AssemblerRecipe): Boolean

  /**
   *
   * @param input Array[9]
   * @return Get the AssemblerRecipe matching the input mapping.
   */
  def getRecipe(input: Array[ItemStack]): AssemblerRecipe

  /**
   *
   * @param input
   * @return Get the AssemblerRecipe matching the output mapping.``
   */
  def getRecipe(input: ItemStack): AssemblerRecipe

  /**
   *
   * @param level
   * @return Get all recipes of the given tech level.
   */
  def getRecipesForTechLevel(level: EnumTechLevel): util.Collection[AssemblerRecipe]

  /**
   *
   * @param tech
   * @return Get all recipes registered for the given technology.
   */
  def getRecipesForTechnology(tech: ITechnology): util.Collection[AssemblerRecipe]

  /**
   *
   * @param tech
   * @return Get all recipes registered for the given technology.
   */
  def getRecipesForTechnology(tech: String): util.Collection[AssemblerRecipe]

  /**
   *
   * @param recipe
   * @param uuid
   * @return Helper function to compare the given recipe's technology
   *         to the player's research status.
   */
  def hasResearchedRecipe(recipe: AssemblerRecipe, uuid: UUID): Boolean
}
