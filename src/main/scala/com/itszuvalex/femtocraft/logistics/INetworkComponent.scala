package com.itszuvalex.femtocraft.logistics

import com.itszuvalex.femtocraft.api.utils.WorldLocation

/**
 * Created by Christopher Harris (Itszuvalex) on 4/5/15.
 */
trait INetworkComponent[T] {

  def getNetworkID: Int

  def setNetworkID(id: Int)

  def getNetwork: T

  def getLoc: WorldLocation

  def refresh(): Unit

}
