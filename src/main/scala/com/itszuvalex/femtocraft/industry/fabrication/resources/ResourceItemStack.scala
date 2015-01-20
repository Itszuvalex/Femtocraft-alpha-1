package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource
import net.minecraft.item.ItemStack

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourceItemStack(private val itemStack: ItemStack) extends IResource[ItemStack, Int] {
  override def utilize(resource: Int) = new ResourceItemStack(itemStack.splitStack(Math.max(amount, resource)))

  override def amount = itemStack.stackSize

  override def resourceName = if (itemStack == null) "Items" else itemStack.getDisplayName

  override def resource = itemStack
}
