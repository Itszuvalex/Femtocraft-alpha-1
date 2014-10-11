package com.itszuvalex.femtocraft.implicits

import com.itszuvalex.femtocraft.utils.FemtocraftStringUtils
import net.minecraft.item.ItemStack

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


}
