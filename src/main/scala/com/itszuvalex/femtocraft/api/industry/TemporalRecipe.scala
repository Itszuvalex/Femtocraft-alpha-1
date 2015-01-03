package com.itszuvalex.femtocraft.api.industry

import com.itszuvalex.femtocraft.api.core.{Configurable, ISaveable, Saveable}
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.api.{EnumTechLevel, FemtocraftAPI}
import com.itszuvalex.femtocraft.api.utils.{FemtocraftDataUtils, FemtocraftUtils}
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound

/**
 * Created by Christopher Harris (Itszuvalex) on 4/27/14.
 */
class TemporalRecipe(@Saveable
                     @Configurable(comment = "ItemStack that is consumed to use this recipe.")
                     var input: ItemStack, @Saveable
                     @Configurable(comment = "Null, ItemStack[3] or ItemStack[6] (can include nulls) that dictate configuration items" + " required to induce creation. Note, ItemStack[3] is associated with Nano level Horologe," + " ItemStack[6] is associated with Femto.")
                     var configurators: Array[ItemStack], @Saveable
                     @Configurable(comment = "ItemStack that is the result of this recipe.")
                     var output: ItemStack, @Saveable
                     @Configurable(comment = "Ticks required to craft.")
                     var ticks: Int, @Saveable
                     @Configurable(comment = "TechLevel of this recipe.")
                     var techLevel: EnumTechLevel, @Saveable
                     @Configurable(comment = "Null or name of Technology that must be researched before a player can utilize this " + "recipe.")
                     var tech: String)
  extends Comparable[TemporalRecipe] with ISaveable {

  def this(input: ItemStack, configurators: Array[ItemStack], output: ItemStack, ticks: Int, level: EnumTechLevel,
           tech: ITechnology) =
    this(input, configurators, output, ticks, level, if (tech == null) null else tech.getName)

  def getTechnology = FemtocraftAPI.getResearchManager.getTechnology(tech)

  def compareTo(tr: TemporalRecipe): Int = {
    var result: Int = FemtocraftUtils.compareItem(this.input, tr.input)
    if (result != 0) {
      return result
    }
    if (configurators == null && tr.configurators != null) {
      return -1
    }
    if (tr.configurators == null && configurators != null) {
      return 1
    }
    if (tr.configurators != null && configurators != null) {
      if (configurators.length < tr.configurators.length) {
        return -1
      }
      if (configurators.length > tr.configurators.length) {
        return 1
      }

      for (i <- 0 until configurators.length) {
        result = FemtocraftUtils.compareItem(configurators(i), tr.configurators(i))
        if (result != 0) {
          return result
        }
      }

    }
    result = FemtocraftUtils.compareItem(output, tr.output)
    if (result != 0) {
      return result
    }
    0
  }

  def saveToNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.saveObjectToNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    FemtocraftDataUtils.loadObjectFromNBT(compound, this, FemtocraftDataUtils.EnumSaveType.WORLD)
  }
}
