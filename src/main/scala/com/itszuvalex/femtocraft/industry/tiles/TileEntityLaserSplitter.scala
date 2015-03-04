package com.itszuvalex.femtocraft.industry.tiles

import com.itszuvalex.femtocraft.api.core.{Configurable, Saveable}
import com.itszuvalex.femtocraft.api.industry.ILaserInteractable
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.industry.LaserRegistry
import com.itszuvalex.femtocraft.industry.tiles.TileEntityLaserReflector._
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection


@Configurable class TileEntityLaserSplitter extends TileEntityBase with ILaserInteractable {
  @Saveable              var sustainedMerge     = true
  @Saveable              var sustainedSplit     = true
  @Saveable              var sustainedSplitOpp  = true
  @Saveable              var strengthMerge      = 0
  @Saveable              var strengthSplit      = 0
  @Saveable              var strengthSplitOpp   = 0
  @Saveable              var distanceMerge      = 0
  @Saveable              var distanceSplit      = 0
  @Saveable              var distanceSplitOpp   = 0
  @Saveable              var modulationMerge    = LaserRegistry.MODULATION_DEFAULT
  @Saveable              var modulationSplit    = LaserRegistry.MODULATION_DEFAULT
  @Saveable              var modulationSplitOpp = LaserRegistry.MODULATION_DEFAULT
  @Saveable(desc = true) var dirMerge           = ForgeDirection.UNKNOWN.ordinal()
  @Saveable(desc = true) var dirSplit           = ForgeDirection.UNKNOWN.ordinal()

  def getMergeDir = dirMerge

  def getSplitDir = dirSplit

  /**
   * Gated update call. This will only be called on the server, and only if the tile's {@link #shouldTick()} returns
   * true. This should be used instead of updateEntity() for heavy computation, unless the tile absolutely needs to
   * update.
   */
  override def femtocraftServerUpdate(): Unit = {
    val laserIn = sustainedMerge
    val laserOut = sustainedSplit
    val laserOutSplit = sustainedSplitOpp


    if (!(laserIn || laserOut || laserOutSplit)) return

    if (laserIn)
      sustainedMerge = false
    else {
      strengthMerge = 0
      distanceMerge = 0
      modulationMerge = LaserRegistry.MODULATION_DEFAULT
    }

    if (laserOut)
      sustainedSplit = false
    else {
      strengthSplit = 0
      distanceSplit = 0
      modulationSplit = LaserRegistry.MODULATION_DEFAULT
    }

    if (laserOutSplit)
      sustainedSplitOpp = false
    else {
      strengthSplitOpp = 0
      distanceSplitOpp = 0
      modulationSplitOpp = LaserRegistry.MODULATION_DEFAULT
    }


    if (strengthMerge > (strengthSplit + strengthSplitOpp)) {
      if (distanceMerge <= 0) return
      val odir = ForgeDirection.getOrientation(dirSplit)
      if (odir == ForgeDirection.UNKNOWN) return
      LaserRegistry.makeLaser(worldObj, xCoord + odir.offsetX, yCoord + odir.offsetY, zCoord + odir.offsetZ,
                              modulationMerge, odir, (strengthMerge / 2f).toInt, distanceMerge - 1)
      LaserRegistry.makeLaser(worldObj, xCoord + odir.getOpposite.offsetX, yCoord + odir.getOpposite.offsetY, zCoord + odir.getOpposite.offsetZ,
                              modulationMerge, odir.getOpposite, (strengthMerge / 2f).toInt, distanceMerge - 1)
    }
    else if ((strengthSplit + strengthSplitOpp) > strengthMerge) {
      if (((distanceSplit + distanceSplitOpp) / 2f).toInt <= 0) return
      val odir = ForgeDirection.getOrientation(dirMerge)
      if (odir == ForgeDirection.UNKNOWN) return
      LaserRegistry.makeLaser(worldObj, xCoord + odir.offsetX, yCoord + odir.offsetY, zCoord + odir.offsetZ,
                              LaserRegistry.mergeModulations(modulationSplit, modulationSplitOpp), odir, strengthSplit + strengthSplitOpp, ((distanceSplit + distanceSplitOpp) / 2f).toInt - 1)
    }
  }


  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val itemstack = par5EntityPlayer.getCurrentEquippedItem
    if (itemstack == null) return false
    if (worldObj.isRemote) return true
    itemstack.getItem match {
      case inter: IInterfaceDevice if inter.getInterfaceLevel.tier >= INTERACTION_TIER.tier =>
        if (par5EntityPlayer.isSneaking) {
          dirSplit = ForgeDirection.getOrientation(side).ordinal()
          if (dirSplit == dirMerge || ForgeDirection.getOrientation(dirSplit).getOpposite.ordinal() == dirMerge) dirMerge = ForgeDirection.UNKNOWN.ordinal()
        } else {
          dirMerge = ForgeDirection.getOrientation(side).ordinal()
          if (dirMerge == dirSplit || ForgeDirection.getOrientation(dirSplit).getOpposite.ordinal() == dirMerge) dirSplit = ForgeDirection.UNKNOWN.ordinal()
        }
        setUpdate()
        setModified()
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
    if (dir.getOpposite.ordinal() == dirMerge) {
      sustainedMerge = true
      this.strengthMerge = strength
      this.distanceMerge = distance
      this.modulationMerge = modulation
    } else if (dir.getOpposite.ordinal() == dirSplit) {
      sustainedSplit = true
      this.strengthSplit = strength
      this.distanceSplit = distance
      this.modulationSplit = modulation
    } else if (dir.ordinal() == dirSplit) {
      sustainedSplitOpp = true
      this.strengthSplitOpp = strength
      this.distanceSplitOpp = distance
      this.modulationSplitOpp = modulation
    }
  }
}
