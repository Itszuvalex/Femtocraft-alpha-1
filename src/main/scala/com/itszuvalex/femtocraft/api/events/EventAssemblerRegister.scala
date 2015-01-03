package com.itszuvalex.femtocraft.api.events

import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import cpw.mods.fml.common.eventhandler.{Cancelable, Event}

import scala.beans.BeanProperty

object EventAssemblerRegister {

  @Cancelable class Decomposition(@BeanProperty val recipe: AssemblerRecipe) extends Event with EventAssemblerRegister

  @Cancelable class Recomposition(@BeanProperty val recipe: AssemblerRecipe) extends Event with EventAssemblerRegister

  @Cancelable class Reversible(@BeanProperty val recipe: AssemblerRecipe) extends Event with EventAssemblerRegister

}

trait EventAssemblerRegister
