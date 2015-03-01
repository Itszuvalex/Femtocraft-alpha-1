package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.industry.tiles.TileEntityLaserReflector._
import com.itszuvalex.femtocraft.industry.{ILaserInteractable, LaserRegistry}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

/**
 * Created by Christopher Harris (Itszuvalex) on 3/1/15.
 */
object TileEntityLaserReflector {
  @Configurable val INTERACTION_TIER = EnumTechLevel.NANO
}

@Configurable class TileEntityLaserReflector extends TileEntityBase with ILaserInteractable {
  @Saveable              var sustainedOne  = true
  @Saveable              var sustainedTwo  = true
  @Saveable              var strengthOne   = 0
  @Saveable              var strengthTwo   = 0
  @Saveable              var distanceOne   = 0
  @Saveable              var distanceTwo   = 0
  @Saveable              var modulationOne = LaserRegistry.MODULATION_DEFAULT
  @Saveable              var modulationTwo = LaserRegistry.MODULATION_DEFAULT
  @Saveable(desc = true) var dirOne        = ForgeDirection.UNKNOWN.ordinal()
  @Saveable(desc = true) var dirTwo        = ForgeDirection.UNKNOWN.ordinal()

  def getDirOne = dirOne

  def getDirTwo = dirTwo

  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  override def femtocraftServerUpdate(): Unit = {
    if (!(sustainedOne || sustainedTwo)) return
    else {sustainedOne = false; sustainedTwo = false}

    if (strengthOne > strengthTwo) {
      if (distanceOne <= 0) return
      val odir = ForgeDirection.getOrientation(dirTwo)
      if (odir == ForgeDirection.UNKNOWN) return
      LaserRegistry.makeLaser(worldObj, xCoord + odir.offsetX, yCoord + odir.offsetY, zCoord + odir.offsetZ,
                              modulationOne, odir, strengthOne, distanceOne - 1)
    }
    else if (strengthTwo > strengthOne) {
      if (distanceTwo <= 0) return
      val odir = ForgeDirection.getOrientation(dirOne)
      if (odir == ForgeDirection.UNKNOWN) return
      LaserRegistry.makeLaser(worldObj, xCoord + odir.offsetX, yCoord + odir.offsetY, zCoord + odir.offsetZ,
                              modulationTwo, odir, strengthTwo, distanceTwo - 1)
    }
  }


  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val itemstack = par5EntityPlayer.getCurrentEquippedItem
    if (itemstack == null) return false
    if (worldObj.isRemote) return true
    itemstack.getItem match {
      case inter: IInterfaceDevice if inter.getInterfaceLevel.tier >= INTERACTION_TIER.tier =>
        if (par5EntityPlayer.isSneaking) {
          dirTwo = ForgeDirection.getOrientation(side).ordinal()
          if (dirTwo == dirOne) dirOne = ForgeDirection.UNKNOWN.ordinal()
        } else {
          dirOne = ForgeDirection.getOrientation(side).ordinal()
          if (dirOne == dirTwo) dirTwo = ForgeDirection.UNKNOWN.ordinal()
        }
        setUpdate()
        true
      case _ => false
    }
  }

  override def handleDescriptionNBT(compound: NBTTagCompound): Unit = {
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
  }

  /**
   *
   * @param dir Direction laser is coming from.
   * @param strength Strength of laser at this block.
   * @param modulation Modulation of laser.
   * @param distance Distance of laser at this block.
   */
  override def interact(dir: ForgeDirection, strength: Int, modulation: String, distance: Int): Unit = {
    if (dir.getOpposite.ordinal() == dirOne) {
      sustainedOne = true
      this.strengthOne = strength
      this.distanceOne = distance
      this.modulationOne = modulation
    } else if (dir.getOpposite.ordinal() == dirTwo) {
      sustainedTwo = true
      this.strengthTwo = strength
      this.distanceTwo = distance
      this.modulationTwo = modulation
    }
  }
}
