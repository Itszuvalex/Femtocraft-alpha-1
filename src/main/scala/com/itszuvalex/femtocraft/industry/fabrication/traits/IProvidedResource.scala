package com.itszuvalex.femtocraft.industry.fabrication.traits

import java.util

import scala.collection.JavaConversions._

/**
 * Created by Christopher on 1/22/2015.
 */
trait IProvidedResource[C, T] extends IResource[C, T] {
  private val promiseMap = new scala.collection.mutable.HashMap[IRequestedResource[C, T], T]()

  def provider: IProvider

  def amountPromised: T

  def promises: util.Map[IRequestedResource[C, T], T] = promiseMap

  /**
   *
   * @return True if all resources promised.
   */
  def isEmptyPromise: Boolean

  def promise(iRequestedResource: IRequestedResource[C, T], amount: T) = promiseMap.put(iRequestedResource, amount)

  def removePromise(iRequestedResource: IRequestedResource[C, T]) = promiseMap.remove(iRequestedResource)

  /**
   *
   * Promise resources to fulfill a request.
   *
   * @param iRequestedResource Resource to fulfill.
   * @return True if request fulfilled, false otherwise.
   */
  def fulfillRequest(iRequestedResource: IRequestedResource[C, T]): Boolean

  def commit(iRequestedResource: IRequestedResource[C, T])
}
