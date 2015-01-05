package com.itszuvalex.femtocraft.industry.multiblock

import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent

/**
 * Created by Chris on 1/4/2015.
 */
trait IPistonPressController extends IMultiBlockComponent {

  /**
   * Notify the controller when a neighboring block changes.  It might be the piston!
   */
  def onMultiBlockNeighborChange() : Unit
}
