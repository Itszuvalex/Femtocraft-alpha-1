package com.itszuvalex.femtocraft.api.core

import scala.xml.Node

/**
 * Created by Christopher Harris (Itszuvalex) on 3/29/15.
 */
trait XMLSaveable {

  def saveToNode(node: Node): Unit

  def loadFromNode(node: Node): Unit

}
