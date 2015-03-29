package com.itszuvalex.femtocraft.api.core

import scala.xml.Node

/**
 * Created by Christopher Harris (Itszuvalex) on 3/29/15.
 */
trait XMLSaveable {

  def saveAsNode: Node

  def loadFromNode(node: Node): Unit

}
