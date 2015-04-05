package com.itszuvalex.femtocraft.logistics

import scala.actors.threadpool.AtomicInteger
import scala.collection.mutable

/**
 * Created by Christopher on 4/5/2015.
 */
object ManagerNetwork {
  private val nextID = new AtomicInteger(0)
  private val networkMap = mutable.HashMap[Int, INetwork[_]]()

  def getNextID = nextID.getAndIncrement

  def addNetwork[T](network: INetwork[T]) = synchronized {
    networkMap(network.ID) = network
                                                         }

  def removeNetwork[T](network: INetwork[T]) = synchronized {
    networkMap.remove(network.ID)
                                                            }


}
