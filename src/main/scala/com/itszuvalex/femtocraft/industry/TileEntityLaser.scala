package com.itszuvalex.femtocraft.industry

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.core.traits.tile.DescriptionPacket
import com.itszuvalex.femtocraft.industry.TileEntityLaser._
import net.minecraft.tileentity.TileEntity
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Christopher on 2/20/2015.
 */
object TileEntityLaser {
  @Configurable val LASER_STRENGTH_FALLOFF_MULTIPLIER = .95
}

@Configurable class TileEntityLaser extends TileEntity with ILaser with DescriptionPacket {

  @Saveable(desc = true) var direction     = UNKNOWN
  @Saveable(desc = true) var modulation    = "Unmodulated"
                         var strength: Int = 0
                         var distance: Int = 0
  @Saveable              var sustained     = true

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

  /**
   *
   * @param dir Direction laser is coming from.
   * @param strength Strength of laser at this block.
   * @param modulation Modulation of laser.
   * @param distance Distance of laser at this block.
   */
  override def interact(dir: ForgeDirection, strength: Int, modulation: String, distance: Int): Unit = {
    if (dir != direction && strength > this.strength) {
      direction = dir
      this.strength = strength
      this.modulation = modulation
      this.distance = distance
      worldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
      sustained = true
    }
    else if (dir == direction) {
      this.strength = strength
      this.modulation = modulation
      this.distance = distance
      sustained = true
    }
  }

  override def updateEntity(): Unit = {
    if (worldObj.isRemote) return


    if (!sustained) {worldObj.setBlockToAir(xCoord, yCoord, zCoord); return}
    else sustained = false

    if (distance <= 0) return

    val x = xCoord + direction.offsetX
    val y = yCoord + direction.offsetY
    val z = zCoord + direction.offsetZ
    if (worldObj.blockExists(x, y, z)) {
      worldObj.getTileEntity(x, y, z) match {
        case int: ILaserInteractable =>
          int.interact(direction, (strength.toFloat * LASER_STRENGTH_FALLOFF_MULTIPLIER).toInt, modulation, distance - 1)
        case _ if worldObj.isAirBlock(x, y, z) =>
          worldObj.setBlock(x, y, z, Femtocraft.blockLaser)
          worldObj.getTileEntity(x, y, z) match {
            case int: ILaserInteractable =>
              int.interact(direction, (strength.toFloat * LASER_STRENGTH_FALLOFF_MULTIPLIER).toInt, modulation, distance - 1)
            case _ =>
          }
        case _ =>
      }
    }
  }

}
