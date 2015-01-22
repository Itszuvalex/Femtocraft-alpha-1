package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.implicits.ItemStackImplicits._
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.industry.fabrication.traits.IResource
import net.minecraft.item.ItemStack

/**
 * Created by Christopher on 1/21/2015.
 */
class ResourceItems(private val stacks: Array[ItemStack]) extends IResource[Array[ItemStack], Array[Int]] {
  /**
   *
   * @param other Amount of this resource to consume.
   * @return IResource containing the amount utilized.
   */
  override def utilize(other: IResource[Array[ItemStack], Array[Int]]) = if (equivalent(other)) utilize(other.resource.map(i => if (i == null) 0 else i.stackSize).toArray) else null

  /**
   *
   * @param resource Amount of this resource to use.
   * @return IResource containing this amount, if possible.
   */
  override def utilize(resource: Array[Int]) = if (resource.length != stacks.length) null
  else new ResourceItems((0 until stacks.size).map { i =>
    if (resource(i) <= 0) null
    else if (stacks(i) == null) null
    else if (resource(i) == stacks(i).stackSize) {
      val itemStack = stacks(i)
      stacks(i) == null
      itemStack
    }
    else {
      stacks(i).splitStack(resource(i))
    }
                                                   }.toArray)


  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  override def equivalent(other: IResource[Array[ItemStack], Array[Int]]) = true

  /**
   *
   * @return Get the contained resource, directly.
   */
  override def resource = stacks

  /**
   *
   * @return Get a measurement of how much of this resource is present.
   */
  override def amount: Array[Int] = stacks.map(i => if (i == null) 0 else i.stackSize)

  override def resourceName: String = ???

  /**
   *
   * @param other
   * @return True if this is equivalent to other, and contains >= the amount of resources of other.
   */
  override def contains(other: IResource[Array[ItemStack], Array[Int]]): Boolean = {
    val copy = stacks.deepCopy
    other.resource.forall(FemtocraftUtils.removeItem(_, copy, null))
  }
}
