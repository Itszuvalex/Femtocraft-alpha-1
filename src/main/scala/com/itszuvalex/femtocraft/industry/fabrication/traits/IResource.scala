package com.itszuvalex.femtocraft.industry.fabrication.traits

/**
 * Created by Christopher on 1/19/2015.
 *
 * @tparam C Class type of the contained resource
 * @tparam T Type of measurement of this resource.  I.E. Fluids, ItemStacks, Power are Int.
 */
trait IResource[C, T] {
  def resourceName: String

  def resourceClass : Class[C] = classOf[C]

  def valueClass : Class[T] = classOf[T]

  /**
   *
   * @return View of this object as a provided resource.  Null if it isn't one.
   */
  def asProvided: IProvidedResource[C, T]

  /**
   *
   * @return View of this object as a requested resource.  Null if it isn't one.
   */
  def asRequested: IRequestedResource[C, T]

  /**
   *
   * @return View of this object as a consumable resource.  Null if it isn't one.
   */
  def asConsumable: IConsumableResource[C, T]

  /**
   *
   * @return View of this object as a renewable resource.  Null if it isn't one.
   */
  def asRenewable: IRenewableResource[C, T]

  /**
   *
   * @return Get the contained resource, directly.
   */
  def resource: C

  /**
   *
   * @return Get a measurement of how much of this resource is present.
   */
  def amount: T

  /**
   *
   * @param other
   * @return True if this is equivalent to other, and contains >= the amount of resources of other.
   */
  def contains(other: IResource[C, T]): Boolean

  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  def equivalent(other: IResource[C, T]): Boolean
}
