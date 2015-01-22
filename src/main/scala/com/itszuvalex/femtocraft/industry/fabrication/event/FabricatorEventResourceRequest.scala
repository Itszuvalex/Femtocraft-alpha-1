package com.itszuvalex.femtocraft.industry.fabrication.event

import java.util

import com.itszuvalex.femtocraft.industry.fabrication.traits.{IFabricationSuiteEvent, IRequestedResource}

import scala.collection.JavaConversions._

/**
 * Created by Christopher on 1/22/2015.
 */
class FabricatorEventResourceRequest(val requests: util.Collection[IRequestedResource]) extends IFabricationSuiteEvent {

  def fulfilledRequests = requests.filter(_.isFulfilled)

  def unfulfilledRequests = requests.filterNot(_.isFulfilled)

  def isRequestFulfilled = requests.forall(_.isFulfilled)

  def commit() = requests.foreach(commit)

}
