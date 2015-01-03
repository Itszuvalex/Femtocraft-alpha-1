package com.itszuvalex.femtocraft.api.items

import java.util
import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.industry.AssemblerRecipe
import com.itszuvalex.femtocraft.api.items.IAssemblerSchematic._
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.{EnumChatFormatting, IIcon}

/**
 * Created by Chris on 1/3/2015.
 */
/**
 * Default implementations for IAssemblerSchematic
 *
 * Use and forward calls if you don't want to worry about nitty gritty details.
 */
trait AssemblerSchematic extends IAssemblerSchematic {
  @SideOnly(Side.CLIENT) def keyedIcon: IIcon

  @SideOnly(Side.CLIENT) def blankIcon: IIcon

  @SideOnly(Side.CLIENT) override def getIconIndex(par1ItemStack: ItemStack) = if (hasRecipe(par1ItemStack)) {
    keyedIcon
  } else {
    blankIcon
  }

  override def isDamageable = true

  override def getShareTag = true

  override def isRepairable = false

  override def getIcon(stack: ItemStack, pass: Int) = if (hasRecipe(stack)) keyedIcon else blankIcon

  @SideOnly(Side.CLIENT)
  override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_],
                              advanced: Boolean) {
    val tooltip = par3List.asInstanceOf[util.List[String]]
    val uses: Int = usesRemaining(par1ItemStack)
    val useString = if (uses == INFINITE_USE_DAMAGE) "Infinite Uses" else String.valueOf(uses)
    val itemCompound = par1ItemStack.stackTagCompound
    if (itemCompound == null || !itemCompound.hasKey("recipe")) {
      tooltip.add(EnumChatFormatting.YELLOW + "Uses good for:" + EnumChatFormatting.RESET + " " + useString)
      return
    }
    val recipe = AssemblerRecipe.loadFromNBTTagCompound(itemCompound.getCompoundTag("recipe"))
    if (recipe == null) {
      tooltip.add("Invalid Recipe")
      return
    }
    tooltip.add(EnumChatFormatting.YELLOW + "Uses Remaining:" + EnumChatFormatting.RESET + " " + useString)
    try {
      tooltip.add(EnumChatFormatting.YELLOW + "Output:" + recipe.enumTechLevel.getTooltipEnum + " " +
                  recipe.getRecipeName + EnumChatFormatting.GRAY + "x" + EnumChatFormatting.RESET +
                  recipe.output.stackSize + EnumChatFormatting.RESET)
    }
    catch {
      case e: Exception =>
    }
    if (!advanced) {
      return
    }
    tooltip.add("")
    for (i <- 0 until 9) {
      val item = recipe.input(i)
      val inputString = if (item == null) {
        "empty"
      } else {
        item.stackSize.toString + EnumChatFormatting.GRAY + "x" + EnumChatFormatting.RESET + item.getDisplayName
      }
      tooltip.add(EnumChatFormatting.YELLOW + "Input " + i.toString + EnumChatFormatting.RESET + " " + inputString)
    }

    tooltip.add("")
    tooltip.add(EnumChatFormatting.YELLOW + "Mass:" + EnumChatFormatting.DARK_PURPLE + " " +
                recipe.mass + EnumChatFormatting.RESET)
    tooltip.add(EnumChatFormatting.YELLOW + "TechLevel:" + recipe.enumTechLevel.getTooltipEnum + " "
                + FemtocraftUtils.capitalize(recipe.enumTechLevel.key) + EnumChatFormatting.RESET)
    val tech = Femtocraft.researchManager.getTechnology(recipe.tech)
    val (formatting, techString) = if (tech == null) {
      (EnumChatFormatting.BLACK, "none")
    } else {
      (tech.getLevel.getTooltipEnum, FemtocraftUtils.capitalize(tech.getName))
    }
    tooltip
    .add(EnumChatFormatting.YELLOW + "Technology Required:" + formatting + " " + techString + EnumChatFormatting.RESET)
  }

  override def usesRemaining(stack: ItemStack) = if (stack == null) {
    0
  } else {
    stack.getMaxDamage - stack.getItemDamage
  }

  override def getItemStackLimit(stack: ItemStack): Int = if (stack.stackTagCompound == null ||
                                                              stack.stackTagCompound.hasNoTags) {
    super.getItemStackLimit(stack)
  } else {
    1
  }

  override def hasRecipe(stack: ItemStack) = stack.stackTagCompound != null && stack.stackTagCompound.hasKey("recipe")

  override def getRecipe(stack: ItemStack) = getRecipeFromNBT(stack.stackTagCompound)

  private def getRecipeFromNBT(compound: NBTTagCompound): AssemblerRecipe = {
    if (compound == null) {
      return null
    }
    if (!compound.hasKey("recipe")) {
      return null
    }
    AssemblerRecipe.loadFromNBTTagCompound(compound.getTag("recipe").asInstanceOf[NBTTagCompound])
  }

  override def setRecipe(stack: ItemStack, recipe: AssemblerRecipe) = addRecipeToNBT(stack, recipe)

  private def addRecipeToNBT(stack: ItemStack, recipe: AssemblerRecipe): Boolean = {
    if (stack.stackTagCompound == null) {
      stack.stackTagCompound = new NBTTagCompound
    } else if (stack.stackTagCompound.hasKey("recipe")) {
      return false
    }
    val recipeCompound: NBTTagCompound = new NBTTagCompound
    recipe.saveToNBT(recipeCompound)
    stack.stackTagCompound.setTag("recipe", recipeCompound)
    true
  }

  override def massRequired(recipe: AssemblerRecipe): Int = {
    var amount: Float = getMaxDamage
    amount = if (amount == -1) infiniteUseMassMultiplier else amount
    (recipe.enumTechLevel.tier * amount).toInt
  }

  override def onAssemble(stack: ItemStack): Boolean = {
    if (usesRemaining(stack) == -1) {
      return true
    }
    stack.attemptDamageItem(1, new Random)
    !(usesRemaining(stack) == 0)
  }

  override def resultOfBreakdown(stack: ItemStack) = new ItemStack(this, 1)
}
