package com.itszuvalex.femtocraft.managers.assembler

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.implicits.ItemStackImplicits._
import com.itszuvalex.femtocraft.implicits.StringImplicits._
import com.itszuvalex.femtocraft.research.FemtocraftTechnologies
import net.minecraft.item.{Item, ItemStack}

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher Harris (Itszuvalex) on 10/10/14.
 */
@Configurable object ComponentRegistry {
  @Configurable val showComponentTooltips           = true
  @Configurable val componentTooltipsAreAdvanced    = true
  @Configurable val microComponentTooltipTechnology = FemtocraftTechnologies.BASIC_CHEMISTRY
  @Configurable val nanoComponentTooltipTechnology  = FemtocraftTechnologies.ADVANCED_CHEMISTRY
  @Configurable val femtoComponentTooltipTechnology = FemtocraftTechnologies.APPLIED_PARTICLE_PHYSICS


  private val componentMap = new mutable.HashMap[EnumTechLevel, ArrayBuffer[Item]]

  def registerComponent(item: Item, tech: EnumTechLevel) = getOrMake(tech) append item

  def isItemComponent(item: Item, tech: EnumTechLevel) = componentMap.get(tech).exists(_.contains(item))

  def getComponentsAssemblerRecipeDisplayString(tech: EnumTechLevel) = getComponents(tech).map(_.toAssemblerString)
                                                                       .aggregate("")(_ + _, _ + _)

  def getComponents(tech: EnumTechLevel) = getOrMake(tech)

  private def getOrMake(tech: EnumTechLevel) = componentMap.get(tech) match {
    case Some(a) => a
    case None    => val b = new ArrayBuffer[Item]; componentMap.put(tech, b); b
  }

  def getComponentsInAssemblerRecipeDisplayString(tech: EnumTechLevel) = {
    Femtocraft.recipeManager.assemblyRecipes.getAllRecipes.filter(p => {
      p.input match {
        case null  => false
        case input =>
          input.exists {
                         case i: ItemStack if i.getItem != null => getComponents(tech).contains(i.getItem)
                         case _                                 => false
                       }
      }
    }).map(i => i.output).map(i => i.toAssemblerString).aggregate("")(_ + _, _ + _)
  }
}
