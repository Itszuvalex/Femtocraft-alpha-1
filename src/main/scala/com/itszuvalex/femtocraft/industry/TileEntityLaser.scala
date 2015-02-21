package com.itszuvalex.femtocraft.industry

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.core.traits.tile.DescriptionPacket
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Christopher on 2/20/2015.
 */
class TileEntityLaser extends TileEntity with ILaser with DescriptionPacket
{
  private
  @Saveable(desc = true) var direction = UNKNOWN
  private
  @Saveable(desc = true) var modulation = "Unmodulated"
  private var strength: Int = 0

  /**
   *
   * @return Strength of the laser.
   */
  override def getStrength = strength

  /**
   *
   * @return Direction of laser propagation.
   */
  override def getDirection = direction

  /**
   *
   * @return Modulation of the laser.
   */
  override def getModulation = modulation
}
