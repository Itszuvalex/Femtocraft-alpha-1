package com.itszuvalex.femtocraft.managers.assembler

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.{Item, ItemStack}

import scala.collection.JavaConversions._
import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

/**
 * Created by Christopher Harris (Itszuvalex) on 10/10/14.
 */
object ComponentRegistry {
  private val componentMap = new mutable.HashMap[EnumTechLevel, ArrayBuffer[Item]]

  def registerComponent(item: Item, tech: EnumTechLevel) = getOrMake(tech) append item

  def getComponents(tech: EnumTechLevel) = getOrMake(tech)

  def getComponentsAssemblerRecipeDisplayString(tech: EnumTechLevel) = getComponents(tech).map(i => FemtocraftStringUtils.formatItemStackForTechnologyDisplay
                                                                                                    (FemtocraftStringUtils.RecipeType.ASSEMBLER, new ItemStack(i), i.getItemStackDisplayName(new ItemStack(i)))).aggregate("")(_ + _, _ + _)

  def getComponentsInAssemblerRecipeDisplayString(tech: EnumTechLevel) = {
    Femtocraft.recipeManager.assemblyRecipes.getAllRecipes.filter(p => {
      p.input match {
        case null => false
        case _    =>
          p.input.exists {
                           case null => false
                           case i    =>
                             val it = i.getItem
                             if (it == null) false else getComponents(tech).contains(it)
                         }
      }
    }).map(i => i.output.getItem).map(i => "__Recipe.Assembler:" + FemtocraftStringUtils.itemStackToString(new ItemStack(i)) + "--" + i.getItemStackDisplayName(new ItemStack(i)) + "__").aggregate("")(_ + _, _ + _)
  }

  private def getOrMake(tech: EnumTechLevel) = componentMap.get(tech) match {
    case Some(a) => a
    case None    => val b = new ArrayBuffer[Item]; componentMap.put(tech, b); b
  }
}
