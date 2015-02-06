package com.itszuvalex.femtocraft.core.traits.tile

import java.util

import com.itszuvalex.femtocraft.api.core.ISegmentedInventory

import scala.collection.JavaConversions._
import scala.collection._

/**
 * Created by Christopher on 2/5/2015.
 */
trait SegmentedInventory extends Inventory with ISegmentedInventory {

  /**
   *
   * @return Map of segments mapped by segment name.  Array[Int] is the array of all indexes of inventory that are accessible by this segment.
   */
  override def getSegments: util.Map[String, Array[Int]] = Map("Inventory" -> (0 until inventory.getSizeInventory).toArray)
}
