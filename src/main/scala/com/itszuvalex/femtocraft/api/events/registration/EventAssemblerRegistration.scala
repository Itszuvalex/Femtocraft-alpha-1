package com.itszuvalex.femtocraft.api.events.registration

import java.util

import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import com.itszuvalex.femtocraft.api.managers.IAssemblerRecipeManager
import cpw.mods.fml.common.eventhandler.Event

import scala.beans.BeanProperty

/**
 * Created by Itszuvalex on 1/5/15.
 */
object EventAssemblerRegistration {

  /**
   * Posted when the recipe manager is about to begin registering recipes.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   */
  class PreRegistration(recipeManager: IAssemblerRecipeManager) extends EventAssemblerRegistration(recipeManager)

  /**
   * Posted when all custom recipes have been loaded from the /custom/ folder in Configs, but before they have been registered.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   * @param customRecipes Collection of all recipes in the custom folder.
   */
  class CustomRecipesLoaded(recipeManager: IAssemblerRecipeManager,
                            @BeanProperty val customRecipes: util.Collection[AssemblerRecipe]) extends EventAssemblerRegistration(recipeManager)

  /**
   * Posted after Custom recipes have been loaded, but before Femtocraft registers its default assembler recipes.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   */
  class PreFemtocraftRegistration(recipeManager: IAssemblerRecipeManager) extends EventAssemblerRegistration(recipeManager)

  /**
   * Posted after all Femtocraft default recipes have been gathered, but before they have been registered.
   * Gathered means either taken from defaults or loaded from the FemtocraftDefaults file in /autogen/.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   * @param defaultRecipes Collection of all default Femtocraft recipes.
   */
  class FemtocraftRecipesLoaded(recipeManager: IAssemblerRecipeManager,
                                @BeanProperty val defaultRecipes: util.Collection[AssemblerRecipe]) extends EventAssemblerRegistration(recipeManager)

  /**
   * Posted after all Femtocraft default recipes have been gathered.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   */
  class PostFemtocraftRegistration(recipeManager: IAssemblerRecipeManager) extends EventAssemblerRegistration(recipeManager)


  /**
   * Posted when the recipe manager has finished registering recipes.
   *
   * @param recipeManager The IAssemblerRecipeManager instance.
   */
  class PostRegistration(recipeManager: IAssemblerRecipeManager) extends EventAssemblerRegistration(recipeManager)

}

/**
 * Base class for all EventAssemblerRegistration events.
 *
 * @param recipeManager The IAssemblerRecipeManager instance.
 */
abstract class EventAssemblerRegistration(@BeanProperty val recipeManager: IAssemblerRecipeManager) extends Event
