package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util
import java.util.UUID

import scala.collection.JavaConversions._

/**
 * Created by Christopher on 1/19/2015.
 */
trait IProvider extends IFabricationSuiteNode {
  override def onEvent(event: IFabricationSuiteEvent): Unit = ???

  override def ID: UUID = ???

  /**
   *
   * @return Collection of classes that this Provider can provide.
   */
  def provisionTypes: util.Collection[Class[IResource[_,_]]]

  /**
   *
   * Do NOT forward provision groups.  Provide only what this provider itself can give, not what any other one can.
   *
   * @tparam T Class of resource being provided.
   * @return Provided resources for the class.
   */
  def provisionGroups[T <: IResource[_,_]](clazz: Class[T]): util.Collection[T]

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
  def fulfillRequest[R <: IRequestedResource[_, _]](iRequestedResource: R): Boolean =
    provisionGroups(classOf[R]).map(_.asProvided).filterNot(_ == null).exists(_.fulfillRequest(iRequestedResource))
}
