package com.itszuvalex.femtocraft.core.tiles

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.core.FemtocraftMagnetUtils
import com.itszuvalex.femtocraft.core.ore.BlockOreLodestone
import net.minecraft.tileentity.TileEntity

/**
 * Created by Chris on 9/21/2014.
 */
object TileEntityOreLodestone {
  @Configurable var numTicksPerUpdate                      : Int   = 4
  @Configurable var minimumDistanceToNearestPlayerForUpdate: Float = 30
}

@Configurable class TileEntityOreLodestone extends TileEntity {
  private var count = 0

  override def canUpdate = BlockOreLodestone.MAGNETIC

  override def updateEntity() {
    count += 1
    if (count > TileEntityOreLodestone.numTicksPerUpdate) {
      if (worldObj.getClosestPlayer(xCoord + .5d, yCoord + .5d, zCoord + .5d, TileEntityOreLodestone.minimumDistanceToNearestPlayerForUpdate) != null) {
        FemtocraftMagnetUtils.applyMagnetismFromBlock(Femtocraft.blockOreLodestone, worldObj, xCoord, yCoord, zCoord, TileEntityOreLodestone.numTicksPerUpdate.toDouble / 20.toDouble)
      }
      count = 0
    }
  }
}
