package com.itszuvalex.femtocraft.power.traits

import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.FemtocraftPowerUtils

/**
 * Created by Itszuvalex on 12/7/14.
 */
trait PowerBlockDistributor extends TileEntityBase with PowerBlockContainer {
  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  override def femtocraftServerUpdate(): Unit = {
    //TODO: Smart connections based on updates.
    FemtocraftPowerUtils.distributePower(this, null, worldObj, xCoord, yCoord, zCoord);
    super.femtocraftServerUpdate()
  }
}
