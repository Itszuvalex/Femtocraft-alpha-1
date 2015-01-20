package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource
import net.minecraft.item.ItemStack

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourceItemStack(private val itemStack: ItemStack) extends IResource[ItemStack, Int] {
  override def resourceName = if (resource == null) "Items" else resource.getDisplayName

  override def contains(other: IResource[ItemStack, Int]): Boolean =
    if (equivalent(other))
      amount >= other.amount
    else false

  override def utilize(other: IResource[ItemStack, Int]) = if (equivalent(other)) utilize(other.amount) else utilize(0)

  override def equivalent(other: IResource[ItemStack, Int]) = FemtocraftUtils.compareItem(resource, other.resource) == 0

  override def resource = itemStack

  override def utilize(resource: Int) = new ResourceItemStack(itemStack.splitStack(Math.max(amount, resource)))

  override def amount = resource.stackSize
}
