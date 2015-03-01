package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.FemtocraftGuiConstants
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.utils.BaseInventory
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.core.traits.tile.Inventory
import com.itszuvalex.femtocraft.industry.{ILaserInteractable, ILaserModulator, LaserRegistry}
import net.minecraft.item.ItemStack
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
class TileEntityModulationLensMount extends TileEntityBase with Inventory with ILaserInteractable {
  override def hasGUI = true

  /**
   * @return GuiID, if GUI handler uses ids and not checking instanceof
   */
  override def getGuiID = FemtocraftGuiConstants.ModulationLensMountGuiID

  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  override def femtocraftServerUpdate(): Unit = {
    if (!sustained) return
    else sustained = false

    if (distance <= 0) return
    val direction = ForgeDirection.getOrientation(dir)
    if (direction == ForgeDirection.UNKNOWN) return


    val mod = getModulator
    if (mod == null) {
      LaserRegistry.makeLaser(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ,
                              modulation, direction, strength, distance - 1)
    } else {
      LaserRegistry.makeLaser(worldObj, xCoord + direction.offsetX, yCoord + direction.offsetY, zCoord + direction.offsetZ,
                              mod.getModulation, direction,
                              (strength.toFloat * mod.getStrengthMultiplier).toInt,
                              (distance.toFloat * mod.getDistanceMultiplier).toInt)
    }
  }

  @Saveable var sustained  = true
  @Saveable var strength   = 0
  @Saveable var distance   = 0
  @Saveable var modulation = LaserRegistry.MODULATION_DEFAULT
  @Saveable var dir        = ForgeDirection.UNKNOWN.ordinal()

  override def defaultInventory = new BaseInventory(1)


  override def isItemValidForSlot(slot: Int, item: ItemStack) = if (item == null) true else item.getItem.isInstanceOf[ILaserModulator]

  def getModulator: ILaserModulator = {
    val item = getStackInSlot(0)
    if (item == null) null
    else {
      item.getItem match {
        case m: ILaserModulator => m
        case _ => null
      }
    }
  }

  /**
   *
   * @param dir Direction laser is coming from.
   * @param strength Strength of laser at this block.
   * @param modulation Modulation of laser.
   * @param distance Distance of laser at this block.
   */
  override def interact(dir: ForgeDirection, strength: Int, modulation: String, distance: Int) = {
    if (this.dir == dir.ordinal() || (this.dir != dir.ordinal() && strength > this.strength)) {
      sustained = true
      this.dir = dir.ordinal()
      this.strength = strength
      this.distance = distance
      this.modulation = modulation
    }
  }
}
