package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.power.IPowerContainer
import com.itszuvalex.femtocraft.industry.fabrication.traits._

/**
 * Created by Itszuvalex on 1/19/15.
 */
class ResourcePower(private val container: IPowerContainer, 
                    private val _provider: IProvider = null, 
                    private val _requester: IRequester = null) 
  extends IResource[IPowerContainer, Int] 
          with IProvidedResource[IPowerContainer, Int] 
          with IRequestedResource[IPowerContainer, Int] 
          with IConsumableResource[IPowerContainer, Int] 
          with IRenewableResource[IPowerContainer, Int]
{
  var promisedAmount : Int = 0
  var fulfilledAmount: Int = 0

  override def resourceName = container.synchronized(if (resource == null) "Power" else resource.getTechLevel.key + " Power")

  override def contains(other: IResource[IPowerContainer, Int]) =
    container.synchronized {
                             if (equivalent(other))
                               amount >= other.amount
                             else
                               false
                           }

  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  override def equivalent(other: IResource[IPowerContainer, Int]) =
    if (other.resource == null && resource != null) false
    else if (resource == null && other.resource != null) false
    else other.resource.getTechLevel == resource.getTechLevel

  override def provider = _provider

  /**
   *
   * @param iRequestedResource Resource to promise to
   * @param amount Amount to promise
   * @return True if resource is fulfilled after this promise.
   */
  override def promise(iRequestedResource: IRequestedResource[IPowerContainer, Int], amount: Int): Boolean =
    synchronized {
                   val t = Math.min(amount, promiseAvailable)
                   promises.put(iRequestedResource, t)
                   amountPromised += t
                   iRequestedResource.fulfill(this, t)
                 }

  override def promiseAvailable = synchronized(amount - amountPromised)

  /**
   *
   * @return True if all resources promised.
   */
  override def isEmptyPromise: Boolean = amount == amountPromised

  override def amountPromised = promisedAmount

  override def removePromise(iRequestedResource: IRequestedResource[IPowerContainer, Int]) =
    synchronized {
                   promisedAmount -= promises.remove(iRequestedResource)
                 }

  /**
   *
   * Promise resources to fulfill a request.
   *
   * @param iRequestedResource Resource to fulfill.
   * @return True if request fulfilled, false otherwise.
   */
  override def fulfillRequest(iRequestedResource: IRequestedResource[IPowerContainer, Int]): Boolean =
                promise(iRequestedResource, iRequestedResource.amount)

  /**
   *
   * @param iRequestedResource Remove the resourceas as promised to the requestedResource
   */
  override def commit(iRequestedResource: IRequestedResource[IPowerContainer, Int]): Unit = 
  synchronized{
    val amount = fulfillers.remove(iRequestedResource)
    container.synchronized{
      container.consume(amount)
                          }
    fulfilledAmount -= amount
              }

  /**
   *
   * @return True if amountFulfilled is equal to the amount required.
   */
  override def isFulfilled = synchronized(amount == amountFulfilled)

  /**
   *
   * @param iProvidedResource Resource to fulfill
   * @param amount Amount to fulfill
   * @return True this isFulfilled after the promise.
   */
  override def fulfill(iProvidedResource: IProvidedResource[IPowerContainer, Int], amount: Int): Boolean = 
  synchronized{
    fulfillers.put(iProvidedResource, amount)
    fulfilledAmount += amount
    isFulfilled
              }

  /**
   *
   * @return Pointer to Requester object.
   */
  override def requester = _requester

  override def amount = container.synchronized(resource.getCurrentPower)

  override def removeFulfillment(iProvidedResource: IProvidedResource[IPowerContainer, Int]) = 
  synchronized{
    amountFulfilled -= fulfillers.remove(iProvidedResource)
              }    
  
  override def resource = container

  override def fulfillmentRemaining =
    synchronized(amount - amountFulfilled)  
  
  override def amountFulfilled = fulfilledAmount

  /**
   *
   * @return View of this object as a provided resource.  Null if it isn't one.
   */
  override def asProvided = this

  /**
   *
   * @return View of this object as a requested resource.  Null if it isn't one.
   */
  override def asRequested = this

/**
   *
   * @return View of this object as a consumable resource.  Null if it isn't one.
   */
override def asConsumable = this
/**
   *
   * @return View of this object as a renewable resource.  Null if it isn't one.
   */
override def asRenewable = this

/**
   *
   * @param amount Amount to consume
   * @return True if resources consumed successfully.
   */
override def consume(amount: Int) = 
container.synchronized{container.consume(amount)}
/**
   *
   * @param source Source to renew from.
   * @return True if can accept resources from source.
   */
override def canRenewFrom(source: IPowerContainer): Boolean = source.getTechLevel == resource.getTechLevel

/**
   * Renew this resource with a given source.  Used for comparison against things like power type, fluid type, etc.
   *
   * @param source Source of incoming resources.
   * @param amount Amount to renew
   * @return True if amount renewed, false otherwise.
   */
override def renew(source: IPowerContainer, amount: Int):Int = 
if(canRenewFrom(source)) container.synchronized(renew(amount)) else 0

/**
   * Renew this resource, as is, replenishing only amount.
   *
   * @param amount Amount to renew
   * @return True if amount renewed, false otherwise.
   */
override def renew(amount: Int): Int = 
  container.synchronized(container.charge(amount))
}
