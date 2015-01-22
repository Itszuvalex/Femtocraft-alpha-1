package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

/**
 * Created by Christopher on 1/19/2015.
 */
trait IResourceGroup[T <: IResource] {

  def getName: String

  def getType = classOf[T]

  def getResources: util.Collection[T]

  def getSize: Int


  /**
   * Promises resources to fulfill request using any of the available resources.
   *
   * @param iRequestedResource Request to fulfill.
   * @tparam C
   * @tparam Y
   * @return True if request fulfilled.
   */
  def fulfillRequest[C, Y](iRequestedResource: IRequestedResource[C, Y]): Boolean
}
