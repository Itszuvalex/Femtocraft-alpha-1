package com.itszuvalex.femtocraft.industry.fabrication.resources

import java.util

import com.itszuvalex.femtocraft.api.power.IPowerContainer
import com.itszuvalex.femtocraft.industry.fabrication.traits.{IProvidedResource, IProvider, IRequestedResource}

/**
 * Created by Christopher on 1/22/2015.
 */
class ProvidedPower(private val _provider: IProvider, container: IPowerContainer) extends ResourcePower(container)
                                                                                          with IProvidedResource[IPowerContainer, Int] {
  override def provider: IProvider = _provider

  override def promise(iRequestedResource: IRequestedResource[IPowerContainer, Int], amount: Int): Unit = ???

  /**
   *
   * @return True if all resources promised.
   */
  override def isEmptyPromise: Boolean = ???

  override def removePromise(iRequestedResource: IRequestedResource[IPowerContainer, Int]): Unit = ???

  override def amountPromised: Int = ???

  override def promises: util.Collection[(IRequestedResource[IPowerContainer, Int], Int)] = ???

  /**
   *
   * Promise resources to fulfill a request.
   *
   * @param iRequestedResource Resource to fulfill.
   * @return True if request fulfilled, false otherwise.
   */
  override def fulfillRequest(iRequestedResource: IRequestedResource[IPowerContainer, Int]): Boolean = ???

  override def commit(iRequestedResource: IRequestedResource[IPowerContainer, Int]): Unit = ???
}
