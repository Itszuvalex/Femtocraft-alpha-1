package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.industry.fabrication.traits._
import net.minecraft.item.ItemStack

/**
 * Created by Christopher on 1/22/2015.
 */
class ResourceItemSpace extends IResource[Array[ItemStack], Array[Int]]
with IRequestedResource[Array[ItemStack], Array[Int]]
with IProvidedResource[Array[ItemStack], Array[Int]]
with IRenewableResource[Array[ItemStack], Array[Int]]{
  override def resourceName: String = ???

  /**
   *
   * @return Get the contained resource, directly.
   */
  override def resource: Array[ItemStack] = ???

  /**
   *
   * @return Get a measurement of how much of this resource is present.
   */
  override def amount: Array[Int] = ???

  /**
   *
   * @return View of this object as a provided resource.  Null if it isn't one.
   */
  override def asProvided: IProvidedResource[Array[ItemStack], Array[Int]] = ???

  /**
   *
   * @return View of this object as a requested resource.  Null if it isn't one.
   */
  override def asRequested: IRequestedResource[Array[ItemStack], Array[Int]] = ???

  /**
   *
   * @return View of this object as a renewable resource.  Null if it isn't one.
   */
  override def asRenewable: IRenewableResource[Array[ItemStack], Array[Int]] = ???

  /**
   *
   * @return View of this object as a consumable resource.  Null if it isn't one.
   */
  override def asConsumable: IConsumableResource[Array[ItemStack], Array[Int]] = ???

  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  override def equivalent(other: IResource[Array[ItemStack], Array[Int]]): Boolean = ???

  /**
   *
   * @param other
   * @return True if this is equivalent to other, and contains >= the amount of resources of other.
   */
  override def contains(other: IResource[Array[ItemStack], Array[Int]]): Boolean = ???

  /**
   *
   * @return True if amountFulfilled is equal to the amount required.
   */
  override def isFulfilled: Boolean = ???

  /**
   *
   * @param iProvidedResource Resource to fulfill
   * @param amount Amount to fulfill
   * @return True this isFulfilled after the promise.
   */
  override def fulfill(iProvidedResource: IProvidedResource[Array[ItemStack], Array[Int]], amount: Array[Int]): Boolean = ???

  override def fulfillmentRemaining: Array[Int] = ???

  /**
   *
   * @return Pointer to Requester object.
   */
  override def requester: IRequester = ???

  override def removeFulfillment(iProvidedResource: IProvidedResource[Array[ItemStack], Array[Int]]): Unit = ???

  override def amountFulfilled: Array[Int] = ???

  /**
   *
   * @param iRequestedResource Resource to promise to
   * @param amount Amount to promise
   * @return True if resource is fulfilled after this promise.
   */
  override def promise(iRequestedResource: IRequestedResource[Array[ItemStack], Array[Int]], amount: Array[Int]): Boolean = ???

  /**
   *
   * @return True if all resources promised.
   */
  override def isEmptyPromise: Boolean = ???

  override def removePromise(iRequestedResource: IRequestedResource[Array[ItemStack], Array[Int]]): Unit = ???

  override def provider: IProvider = ???

  override def amountPromised: Array[Int] = ???

  override def promiseAvailable: Array[Int] = ???

  /**
   *
   * Promise resources to fulfill a request.
   *
   * @param iRequestedResource Resource to fulfill.
   * @return True if request fulfilled, false otherwise.
   */
  override def fulfillRequest(iRequestedResource: IRequestedResource[Array[ItemStack], Array[Int]]): Boolean = ???

  /**
   *
   * @param iRequestedResource Remove the resourceas as promised to the requestedResource
   */
  override def commit(iRequestedResource: IRequestedResource[Array[ItemStack], Array[Int]]): Unit = ???

  /**
   *
   * @param source Source to renew from.
   * @return True if can accept resources from source.
   */
  override def canRenewFrom(source: Array[ItemStack]): Boolean = ???

  /**
   * Renew this resource, as is, replenishing only amount.
   *
   * @param amount Amount to renew
   * @return Amount of resources used to renew.
   */
  override def renew(amount: Array[Int]): Array[Int] = ???

  /**
   * Renew this resource with a given source.  Used for comparison against things like power type, fluid type, etc.
   *
   * @param source Source of incoming resources.
   * @param amount Amount to renew
   * @return Amount of resources used to renew.
   */
  override def renew(source: Array[ItemStack], amount: Array[Int]): Array[Int] = ???
}
