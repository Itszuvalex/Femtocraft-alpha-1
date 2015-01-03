package com.itszuvalex.femtocraft.api.events

import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import cpw.mods.fml.common.eventhandler.{Cancelable, Event}

import scala.beans.BeanProperty

object EventAssemblerRegister {

  @Cancelable class Decomposition(recipe: AssemblerRecipe) extends EventAssemblerRegister(recipe)

  @Cancelable class Recomposition(recipe: AssemblerRecipe) extends EventAssemblerRegister(recipe)

  @Cancelable class Reversible(recipe: AssemblerRecipe) extends EventAssemblerRegister(recipe)

}

@Cancelable
abstract class EventAssemblerRegister(@BeanProperty val recipe: AssemblerRecipe) extends Event
