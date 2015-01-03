package com.itszuvalex.femtocraft.api.industry

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.ISaveable
import com.itszuvalex.femtocraft.api.research.ITechnology
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.item.ItemStack
import net.minecraft.nbt.{NBTTagCompound, NBTTagList}
import org.apache.logging.log4j.{Level, LogManager}

import scala.beans.BeanProperty

object AssemblerRecipe {
  def loadFromNBTTagCompound(compound: NBTTagCompound): AssemblerRecipe = {
    val recipe = new AssemblerRecipe
    recipe.loadFromNBT(compound)
    recipe
  }
}


/**
 *
 * @param input Array of size 9.
 * @param mass Amount of mass required to assemble / generated when dissassembled.
 * @param output ItemStack that is created when assembled / Required to decompose into inputs.  Stacksize and damage value matter.
 * @param enumTechLevel TechLevel of Assembler/Dissassembler required to handle this recipe.
 * @param tech Name of technology that the player must have researched before his machines can handle this recipe.
 * @param `type` Type of AssemblerRecipe.   Decomp is Input -> Output, Recomp is Output -> input, Reversible is both.
 */
class AssemblerRecipe(@BeanProperty var input: Array[ItemStack], @BeanProperty var mass: Int,
                      @BeanProperty var output: ItemStack, @BeanProperty var enumTechLevel: EnumTechLevel,
                      @BeanProperty var tech: String, @BeanProperty var `type`: AssemblerRecipeType)
  extends Comparable[AssemblerRecipe] with ISaveable {

  /**
   * AssemblerRecipeType by default is Reversible
   */
  def this(input: Array[ItemStack], mass: Int, output: ItemStack, enumTechLevel: EnumTechLevel, techName: String) =
    this(input, mass, output, enumTechLevel, techName, AssemblerRecipeType.Reversible)


  def this(input: Array[ItemStack], mass: Int, output: ItemStack, enumTechLevel: EnumTechLevel, tech: ITechnology,
           `type`: AssemblerRecipeType) =
    this(input, mass, output, enumTechLevel, tech.getName, `type`)

  /**
   * AssemblerRecipeType by default is Reversible
   */
  def this(input: Array[ItemStack], mass: Int, output: ItemStack, enumTechLevel: EnumTechLevel, tech: ITechnology) =
    this(input, mass, output, enumTechLevel, tech.getName, AssemblerRecipeType.Reversible)


  /**
   * DO NOT USE, must be public for ISaveable
   */
  def this() {
    this(null, -1, null, null, "", null)
  }

  /**
   *
   * @return Name to represent the recipe.  Null check to allow for automatically generated recipes with a null itemstack output.
   */
  def getRecipeName = if (output == null) "null" else output.getDisplayName

  def compareTo(o: AssemblerRecipe): Int = {
    for (i <- 0 until 9) {
      val comp = FemtocraftUtils.compareItem(input(i), o.input(i))
      if (comp != 0) {
        return comp
      }
    }

    if (mass < o.mass) {
      return -1
    }
    if (mass > o.mass) {
      return 1
    }
    FemtocraftUtils.compareItem(output, o.output)
  }

  def saveToNBT(compound: NBTTagCompound) {
    val inputList = new NBTTagList
    for (i <- 0 until input.length) {
      val itemCompound = new NBTTagCompound
      itemCompound.setByte("Slot", i.toByte)
      if (input(i) != null) {
        val item = new NBTTagCompound
        input(i).writeToNBT(item)
        itemCompound.setTag("item", item)
      }
      inputList.appendTag(itemCompound)
    }

    compound.setTag("input", inputList)
    compound.setInteger("fluidMass", mass)
    val outputCompound = new NBTTagCompound
    output.writeToNBT(outputCompound)
    compound.setTag("output", outputCompound)
    compound.setString("enumTechLevel", enumTechLevel.key)
    if (tech != null) {
      compound.setString("researchTechnology", tech)
    }
    compound.setString("type", `type`.getValue)
  }

  def loadFromNBT(compound: NBTTagCompound) {
    input = new Array[ItemStack](9)
    val inputList = compound.getTagList("input", 10)

    for (i <- 0 until inputList.tagCount) {
      val itemCompound = inputList.getCompoundTagAt(i)
      val slot: Byte = itemCompound.getByte("Slot")
      if (slot != i.toByte) {
        LogManager.getLogger("Femtocraft").log(Level.WARN, "Slot mismatch occurred while loading AssemblerRecipe.")
      }
      if (itemCompound.hasKey("item")) {
        input(i) = ItemStack.loadItemStackFromNBT(itemCompound.getTag("item").asInstanceOf[NBTTagCompound])
      }
    }


    mass = compound.getInteger("fluidMass")
    output = ItemStack.loadItemStackFromNBT(compound.getTag("output").asInstanceOf[NBTTagCompound])
    enumTechLevel = EnumTechLevel.getTech(compound.getString("enumTechLevel"))
    if (compound.hasKey("researchTechnology")) {
      tech = compound.getString("researchTechnology")
    }
    `type` = AssemblerRecipeType.getRecipe(compound.getString("type"))
  }
}
