package com.itszuvalex.femtocraft.industry.fabrication.traits

/**
 * Created by Christopher on 1/19/2015.
 *
 * @tparam C Class type of the contained resource
 * @tparam T Type of measurement of this resource.  I.E. Fluids, ItemStacks, Power are Int.
 */
trait IResource[C, T] {
  def resourceName: String

  def resourceClass = classOf[C]

  def valueClass = classOf[T]

  /**
   *
   * @param iProvider Provider to host this resource. Must not be null.
   * @return A Provided resource wrapper for this resource, or null if this resource cannot be provided.
   */
  def asProvided(iProvider: IProvider): IProvidedResource[C, T] = null

  /**
   *
   * @param iRequestedResource Requester to give this resource to.  Must not be null.
   * @return A Requested resource wrapper for this resource, or null if this resource cannot be requested.
   */
  def asRequested(iRequestedResource: IRequester): IRequestedResource[C, T] = null

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

  /**
   *
   * @param resource Amount of this resource to use.
   * @return IResource containing this amount, if possible.
   */
  def utilize(resource: T): IResource[C, T]

  /**
   *
   * @param other Amount of this resource to consume.
   * @return IResource containing the amount utilized.
   */
  def utilize(other: IResource[C, T]): IResource[C, T]
}
