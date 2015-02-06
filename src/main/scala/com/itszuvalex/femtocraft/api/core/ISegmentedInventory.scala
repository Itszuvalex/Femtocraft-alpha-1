package com.itszuvalex.femtocraft.api.core

import java.util
import net.minecraft.inventory.IInventory

/**
 * Created by Christopher on 2/5/2015.
 */
trait ISegmentedInventory extends IInventory {

  def getSegments: util.Map[String, Array[Int]]
}
