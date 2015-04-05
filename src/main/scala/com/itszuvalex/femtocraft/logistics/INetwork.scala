package com.itszuvalex.femtocraft.logistics

import java.util

/**
 * Created by Christopher on 4/5/2015.
 */
trait INetwork[T] {

  def ID: Int

  def getNodes: util.Collection[T]

  def addNode(node: T): Unit

  def removeNode(node: T): Unit

  def split(pivot: T): Unit

  def merge(iNetwork: INetwork[T]): Unit

}
