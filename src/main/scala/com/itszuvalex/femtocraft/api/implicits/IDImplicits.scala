package com.itszuvalex.femtocraft.api.implicits

import net.minecraft.block.Block
import net.minecraft.item.{Item, ItemStack}

/**
 * Created by Christopher Harris (Itszuvalex) on 10/19/14.
 */
object IDImplicits {

  implicit class ItemIDImplicits(i: Item) {
    def itemID = Item.getIdFromItem(i)

    def getBlock = Block.getBlockFromItem(i)
  }

  implicit class BlockIDImplicits(b: Block) {
    def getItem = Item.getItemFromBlock(b)

    def blockID = Block.getIdFromBlock(b)

  }

  implicit class ItemStackImplicits(i: ItemStack) {
    def itemID = Item.getIdFromItem(i.getItem)
  }

  implicit class IntegerIDImplicits(i: Int) {
    def getItem = Item.getItemById(i)

    def getBlock = Block.getBlockById(i)
  }

  implicit class StringIDImplicits(s: String) {
    def getBlock = Block.getBlockFromName(s)
  }

}
