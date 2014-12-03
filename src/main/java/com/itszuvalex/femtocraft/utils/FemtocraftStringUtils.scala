package com.itszuvalex.femtocraft.utils

import java.util.regex.{Matcher, Pattern}

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.RecipeType
import com.itszuvalex.femtocraft.research.gui.technology.GuiTechnologyRenderer
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemBlock, ItemStack}
import org.apache.logging.log4j.Level

/**
 * Created by Chris on 9/15/2014.
 */
object FemtocraftStringUtils {
  val itemModIDGroup     = "modID"
  val itemNameGroup      = "itemName"
  val itemIDGroup        = "itemID"
  val itemDamageGroup    = "itemDamage"
  val itemStackSizeGroup = "itemStackSize"
  val itemIDRegex        = "(?<" + itemIDGroup + ">\\d+)"
  val modIDRegex         = "(?<" + itemModIDGroup + ">[^:-]+)"
  val itemNameRegex      = "(?<" + itemNameGroup + ">[^:-]+)"
  val itemStackRegex     = "(?:-(?<" + itemStackSizeGroup + ">\\d+?))?"
  val itemDamageRegex    = "(?::(?<" + itemDamageGroup + ">\\d+?))?"
  val itemStackPattern   = Pattern.compile("(?:" + itemIDRegex + "|(?:" + modIDRegex + ":" + itemNameRegex + "))" + itemDamageRegex + itemStackRegex)

  def itemStackFromString(s: String): ItemStack = {
    if (s == null || s.isEmpty) return null
    val itemMatcher: Matcher = itemStackPattern.matcher(s)
    if (itemMatcher.matches) {
      try {
        val itemID = itemMatcher.group(itemIDGroup)
        val modID = itemMatcher.group(itemModIDGroup)
        var name = itemMatcher.group(itemNameGroup)
        val sdam = itemMatcher.group(itemDamageGroup)
        val ssize = itemMatcher.group(itemStackSizeGroup)
        val damage = if (sdam == null) 0 else sdam.toInt
        val stackSize = if (ssize == null) 1 else ssize.toInt
        if (itemID != null) {
          val id = itemID.toInt
          return new ItemStack(Item.getItemById(id), stackSize, damage)
        }
        val typeName = name.split("\\.")
        name = typeName(typeName.length - 1)
        val item = GameRegistry.findItem(modID, name)
        if (item != null) {
          return new ItemStack(item, stackSize, damage)
        }
        val block = GameRegistry.findBlock(modID, name)
        if (block != null) {
          return new ItemStack(block, stackSize, damage)
        }
      }
      catch {
        case e: Exception =>
          Femtocraft.log(Level.ERROR, "Error parsing ItemStack string \"" + s + "\"")
          e.printStackTrace()
          return null
      }
    }
    null
  }

  def formatItemStackForTechnologyDisplay(r: RecipeType, s: ItemStack, info: String): String = {
    "__Recipe." + (if (r eq RecipeType.CRAFTING) GuiTechnologyRenderer.recipeCraftingParam else if (r eq RecipeType.ASSEMBLER) GuiTechnologyRenderer.recipeAssemblerParam else if (r eq RecipeType.TEMPORAL) GuiTechnologyRenderer.recipeTemporalParam else if (r eq RecipeType.DIMENSIONAL) GuiTechnologyRenderer.recipeDimensionalParam else "UNKNOWN") + ":" + FemtocraftStringUtils.itemStackToString(s) + "--" + info + "__"
  }

  def itemStackToString(s: ItemStack): String = {
    var id: GameRegistry.UniqueIdentifier = null
    if (s != null) {
      if (s.getItem.isInstanceOf[ItemBlock]) {
        id = GameRegistry.findUniqueIdentifierFor(Block.getBlockFromItem(s.getItem))
      }
      else {
        id = GameRegistry.findUniqueIdentifierFor(s.getItem)
      }
    }
    if (s == null) {
      ""
    }
    else {
      var result: String = null
      if (id == null) {
        result = String.valueOf(Item.getIdFromItem(s.getItem))
      }
      else {
        result = id.modId + ":" + id.name
      }
      result + ":" + s.getItemDamage + "-" + s.stackSize
    }
  }

}

