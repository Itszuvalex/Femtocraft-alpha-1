package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

/**
 * Created by Christopher on 1/19/2015.
 */
trait IProvider extends IFabricationSuiteNode {
  /**
   *
   * @return Collection of classes that this Provider can provide.
   */
  def provisionTypes: util.Collection[Class[IResource]]

  /**
   *
   * @tparam T Class of resource being provided.
   * @return Map of names to Provision Groups being provided by this class.
   */
  def provisionGroups[T <: IResource](clazz: Class[T]): util.Map[String, IResourceGroup[T]]

  /**
   *
   * @return Collection of all machines this provider is providing to.
   */
  def requesters: util.Collection[IRequester]

  def addRequester(requester: IRequester)

  def removeRequester(requester: IRequester)

  /**
   * Promise resources to fulfill request using any resources available.
   *
   * @param iRequestedResource Resource to fulfill
   * @return True if resource fulfilled, false if not.
   */
  def fulfillRequest[C, T](iRequestedResource: IRequestedResource[C, T]): Boolean
}
