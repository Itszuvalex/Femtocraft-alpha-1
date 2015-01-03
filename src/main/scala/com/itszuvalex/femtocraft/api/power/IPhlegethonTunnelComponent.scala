package com.itszuvalex.femtocraft.api.power

import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent

/**
 * Created by Christopher Harris (Itszuvalex) on 7/13/14.
 *
 * Use on any TileEntity that wishes to be able to become part of a PhlegethonTunnel Multiblock.
 */
trait IPhlegethonTunnelComponent extends IMultiBlockComponent {
  /**
   * Called when the state of the core changes.
   *
   * @param active The state the core is changing into.
   */
  def onCoreActivityChange(active: Boolean)
}
