package com.itszuvalex.femtocraft.implicits

import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.ItemStack
import net.minecraftforge.oredict.OreDictionary

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 10/11/14.
 */
object ItemStackImplicits {

  implicit class ItemStackImplicits(i: ItemStack) {
    def toModQualifiedString: String = FemtocraftStringUtils.itemStackToString(i)
  }

  implicit class StringItemStackImplicits(i: String) {
    def toItemStack: ItemStack = FemtocraftStringUtils.itemStackFromString(i)
  }


  implicit class ItemStackArrayImplicits(i: Array[ItemStack]) {
    def deepCopy: Array[ItemStack] = i.map(f => if (f == null) null else f.copy)
  }

  implicit class ItemStackOreDictionaryComparison(item: ItemStack) {
    def ==(oreDictionary: String) = isOre(oreDictionary)

    def isOre(oreDictionary: String) = OreDictionary.getOres(oreDictionary).exists(ItemStack.areItemStacksEqual(_, item))
  }

}
