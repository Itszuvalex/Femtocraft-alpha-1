package com.itszuvalex.femtocraft.api.events.registration

import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import cpw.mods.fml.common.eventhandler.{Cancelable, Event}

import scala.beans.BeanProperty

object EventAssemblerRecipeRegister {

  /**
   * Posted when an AssemblerRecipe is registered for Decomposition.
   * @param recipe
   */
  @Cancelable class Decomposition(recipe: AssemblerRecipe) extends EventAssemblerRecipeRegister(recipe)

  /**
   * Posted when an AssemblerRecipe is registered for Recomposition.
   * @param recipe
   */
  @Cancelable class Recomposition(recipe: AssemblerRecipe) extends EventAssemblerRecipeRegister(recipe)

  /**
   * Posted when an AssemblerRecipe is registered for Reversible.
   * @param recipe
   */
  @Cancelable class Reversible(recipe: AssemblerRecipe) extends EventAssemblerRecipeRegister(recipe)

}

/**
 * Base class for all AssemblerRecipeRegister events.
 *
 * These events are for finely controlling recipe registration.
 *
 * @param recipe
 */
@Cancelable
abstract class EventAssemblerRecipeRegister(@BeanProperty val recipe: AssemblerRecipe) extends Event
