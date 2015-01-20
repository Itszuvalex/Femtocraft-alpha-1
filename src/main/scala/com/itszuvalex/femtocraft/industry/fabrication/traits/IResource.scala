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
   * @param resource Amount of this resource to use.
   * @return IResource containing this amount, if possible.
   */
  def utilize(resource: T): this.type
}
