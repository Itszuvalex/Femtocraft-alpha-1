package com.itszuvalex.femtocraft.implicits

import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}

/**
 * Created by Itszuvalex on 12/27/14.
 */
object ConversionImplicits {
  def ItemToItemStack(i: Item): ItemStack = new ItemStack(i)

  def BlockToItemStack(b: Block): ItemStack = new ItemStack(b)

}
