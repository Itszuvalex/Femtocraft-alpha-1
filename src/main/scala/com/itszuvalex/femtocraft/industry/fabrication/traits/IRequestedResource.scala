package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

import scala.collection.JavaConversions._

/**
 * Created by Christopher on 1/22/2015.
 */
trait IRequestedResource[C, T] extends IResource[C, T] {
  private val fulfillMap = new scala.collection.mutable.HashMap[IProvidedResource[C, T], T]()

  /**
   *
   * @return Pointer to Requester object.
   */
  def requester: IRequester

  def amountFulfilled: T

  def isFulfilled: Boolean

  def fulfill(iProvidedResource: IProvidedResource[C, T], amount: T) = fulfillMap.put(iProvidedResource, amount)

  def removeFulfillment(iProvidedResource: IProvidedResource[C, T]) = fulfillMap.remove(iProvidedResource)

  /**
   * Commit removal of resources from all providers.
   */
  def commit() = fulfillers.keys.foreach(_.commit(this))

  def fulfillers: util.Map[IProvidedResource[C, T], T] = fulfillMap
}
