package com.itszuvalex.femtocraft.logistics

import java.util

import io.netty.buffer.ByteBuf

/**
 * Created by Christopher on 4/5/2015.
 */
trait INetwork[C <: INetworkComponent[T], T <: INetwork[C, T]] {

  def ID: Int

  def create(nodes: util.Collection[C]): T

  def getNodes: util.Collection[C]

  def addNode(node: C): Unit

  def removeNode(node: C): Unit

  def clear(): Unit

  def refresh(): Unit

  def split(pivot: C): Unit

  def merge(iNetwork: INetwork[C, T]): Unit

  def handlePacketData(data: ByteBuf): Unit

}
