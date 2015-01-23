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

  def promiseAvailable: T

  def promises: util.Map[IRequestedResource[C, T], T] = promiseMap

  /**
   *
   * @return True if all resources promised.
   */
  def isEmptyPromise: Boolean

  /**
   *
   * @param iRequestedResource Resource to promise to
   * @param amount Amount to promise
   * @return True if resource is fulfilled after this promise.
   */
  def promise(iRequestedResource: IRequestedResource[C, T], amount: T): Boolean

  def removePromise(iRequestedResource: IRequestedResource[C, T])

  /**
   *
   * Promise resources to fulfill a request.
   *
   * @param iRequestedResource Resource to fulfill.
   * @return True if request fulfilled, false otherwise.
   */
  def fulfillRequest(iRequestedResource: IRequestedResource[C, T]): Boolean

  /**
   *
   * @param iRequestedResource Remove the resourceas as promised to the requestedResource
   */
  def commit(iRequestedResource: IRequestedResource[C, T])
}
