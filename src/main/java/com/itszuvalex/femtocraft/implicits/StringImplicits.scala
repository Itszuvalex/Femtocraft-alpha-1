package com.itszuvalex.femtocraft.implicits

import com.itszuvalex.femtocraft.api.core.RecipeType
import com.itszuvalex.femtocraft.implicits.ItemStackImplicits._
import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}

/**
 * Created by Chris on 12/12/2014.
 */
object StringImplicits {

  implicit class ItemToTechnologyRenderer(item: Item) {
    def toAssemblerString = new ItemStack(item).toAssemblerString

    def toRecipeString(recipeType: RecipeType) = new ItemStack(item).toRecipeString(recipeType)

    def toRecipeWithInfoString(recipeType: RecipeType, string: String) = new ItemStack(item)
                                                                         .toRecipeWithInfoString(recipeType, string)
  }

  implicit class BlockToTechnologyRenderer(block: Block) {
    def toAssemblerString = new ItemStack(block).toAssemblerString

    def toRecipeString(recipeType: RecipeType) = new ItemStack(block).toRecipeString(recipeType)

    def toRecipeWithInfoString(recipeType: RecipeType, string: String) = new ItemStack(block)
                                                                         .toRecipeWithInfoString(recipeType, string)
  }

}
