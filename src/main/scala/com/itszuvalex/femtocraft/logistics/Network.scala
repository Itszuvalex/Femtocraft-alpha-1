package com.itszuvalex.femtocraft.logistics

import java.util
import java.util.concurrent.ConcurrentHashMap

import com.itszuvalex.femtocraft.api.utils.WorldLocation

import scala.collection.JavaConversions._
import scala.collection.JavaConverters._

/**
 * Created by Christopher Harris (Itszuvalex) on 4/5/15.
 */
abstract class Network[C <: INetworkComponent[N], N <: Network[C, N]](val id: Int) extends INetwork[C, N] {
  def nodeMap = new ConcurrentHashMap[WorldLocation, C]().asScala

  override def addNode(node: C) = nodeMap(node.getLoc) = node

  override def ID = id

  override def clear(): Unit = {
    nodeMap.clear()
  }

  override def refresh(): Unit = {
    val nodes = getNodes
    nodeMap.clear()
    nodes.foreach(_.refresh())
    if (getNodes.size() == 0) {
      ManagerNetwork.removeNetwork(this)
    }
  }

  override def merge(iNetwork: INetwork[C, N]): Unit = {
    iNetwork.getNodes.foreach(_.setNetworkID(ID))
    iNetwork.clear()
    iNetwork.refresh()
  }

  override def split(pivot: C): Unit = {

  }

  override def removeNode(node: C) = nodeMap.remove(node.getLoc)


  override def getNodes: util.Collection[C] = nodeMap.values
}
