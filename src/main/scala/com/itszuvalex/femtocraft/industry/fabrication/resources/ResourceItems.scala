package com.itszuvalex.femtocraft.industry.fabrication.resources

import com.itszuvalex.femtocraft.api.implicits.ItemStackImplicits._
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import com.itszuvalex.femtocraft.industry.fabrication.traits._
import net.minecraft.item.ItemStack

/**
 * Created by Christopher on 1/21/2015.
 */
class ResourceItems(private val stacks: Array[ItemStack],
                    private val _provider: IProvider = null,
                    private val _requester: IRequester = null) extends IResource[Array[ItemStack], Array[Int]]
                                                                  with IRequestedResource[Array[ItemStack], Array[Int]]
                                                                  with IProvidedResource[Array[ItemStack], Array[Int]]
                                                                  with IConsumableResource[Array[ItemStack], Array[Int]]
                                                                  with IRenewableResource[Array[ItemStack], Array[Int]] {

  //  /**
  //   *
  //   * @param other Amount of this resource to consume.
  //   * @return IResource containing the amount utilized.
  //   */
  //  override def utilize(other: IResource[Array[ItemStack], Array[Int]]) = if (equivalent(other)) utilize(other.resource.map(i => if (i == null) 0 else i.stackSize).toArray) else null

  //  /**
  //   *
  //   * @param resource Amount of this resource to use.
  //   * @return IResource containing this amount, if possible.
  //   */
  //  override def utilize(resource: Array[Int]) = if (resource.length != stacks.length) null
  //  else new ResourceItems((0 until stacks.size).map { i =>
  //    if (resource(i) <= 0) null
  //    else if (stacks(i) == null) null
  //    else if (resource(i) == stacks(i).stackSize) {
  //      val itemStack = stacks(i)
  //      stacks(i) == null
  //      itemStack
  //    }
  //    else {
  //      stacks(i).splitStack(resource(i))
  //    }
  //                                                   }.toArray)


  /**
   *
   * @param amount Amount to consume
   * @return True if resources consumed successfully.
   */
  override def consume(amount: Array[Int]): Boolean = {
    if (stacks.length != amount.length) false
    else (0 until stacks.length).
         forall { i =>
      if (stacks(i) == null) {
        amount(i) == 0
      }
      else if (stacks(i).stackSize == amount(i)) {
        stacks(i) = null
        true
      }
      else if (stacks(i).stackSize < amount(i)) false
      else {
        stacks(i).stackSize -= amount(i)
        true
      }
                }
  }

  override def resourceName = "Items"

  /**
   *
   * @return Get the contained resource, directly.
   */
  override def resource = stacks

  /**
   *
   * @return Get a measurement of how much of this resource is present.
   */
  override def amount: Array[Int] = stacks.map {
                                                 case i: ItemStack => i.stackSize;
                                                 case _            => 0
                                               }

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
   * @return View of this object as a renewable resource.  Null if it isn't one.
   */
  override def asRenewable = this

  /**
   *
   * @return View of this object as a consumable resource.  Null if it isn't one.
   */
  override def asConsumable = this

  /**
   *
   * @param other
   * @return True if this is equivalent with resource.  Example, power storage with same tier, item stack with same id and damage, etc.
   */
  override def equivalent(other: IResource[Array[ItemStack], Array[Int]]): Boolean = true

  /**
   *
   * @param other
   * @return True if this is equivalent to other, and contains >= the amount of resources of other.
   */
  override def contains(other: IResource[Array[ItemStack], Array[Int]]): Boolean = {
    val copy = stacks.deepCopy
    other.resource.forall(FemtocraftUtils.removeItem(_, copy, null))
  }

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
  override def requester = _requester

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

  override def provider = _provider

  override def removePromise(iRequestedResource: IRequestedResource[Array[ItemStack], Array[Int]]): Unit = ???

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
  override def canRenewFrom(source: Array[ItemStack]): Boolean = true

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

