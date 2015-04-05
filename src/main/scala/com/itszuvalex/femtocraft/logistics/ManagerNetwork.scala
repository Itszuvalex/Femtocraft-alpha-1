package com.itszuvalex.femtocraft.logistics

import java.util.concurrent.ConcurrentHashMap

import com.itszuvalex.femtocraft.Femtocraft
import cpw.mods.fml.common.network.NetworkRegistry

import scala.actors.threadpool.AtomicInteger
import scala.collection.JavaConverters._

/**
 * Created by Christopher on 4/5/2015.
 */
object ManagerNetwork {
  val INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(Femtocraft.ID.toLowerCase + "|" + "logistics")

  private val nextID     = new AtomicInteger(0)
  private val networkMap = new ConcurrentHashMap[Int, INetwork[_, _]].asScala

  def getNextID = nextID.getAndIncrement

  def addNetwork(network: INetwork[_, _]) = networkMap(network.ID) = network

  def removeNetwork(network: INetwork[_, _]) = networkMap.remove(network.ID)

  def init(): Unit = {
    /*Register Network Messages*/
  }

  def getNetwork(id: Int) = networkMap.get(id)

}
