package com.itszuvalex.femtocraft.utility.tiles

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.utility.{BlockAndTileSnapshot, SpatialRelocation}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection

import scala.util.Random

/**
 * Created by Chris on 12/31/2014.
 */
class TileEntitySpatialAlternator extends TileEntityBase {
  @Saveable(desc = true)
  var active: Int = 0
  @Saveable
  var powered     = false

  override def updateEntity(): Unit = {
    val wasPowered = powered
    powered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)
    if (!wasPowered && powered) swap()
  }

  def swap(): Unit = {
    if (isFaulty) return

    if (!worldObj.isRemote) {
      var first: BlockAndTileSnapshot = null
      var second: BlockAndTileSnapshot = null
      (0 until 6).filter(isSideActivated).map(ForgeDirection.getOrientation).foreach { dir =>
        val snap = SpatialRelocation
                   .extractBlock(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
        if (first == null) first = snap else second = snap
                                                                                     }
      (0 until 6).filter(isSideActivated).map(ForgeDirection.getOrientation).foreach { dir =>
        SpatialRelocation
        .applySnapshot(if (second != null) {val s = second; second = null; s} else first,
                       worldObj,
                       xCoord + dir.offsetX,
                       yCoord + dir.offsetY,
                       zCoord + dir.offsetZ)
                                                                                     }
      worldObj.playSoundEffect(xCoord, yCoord, zCoord, "mob.endermen.portal", 1.0F, 1.0F)
    } else {
      (0 until 6).filter(isSideActivated).map(ForgeDirection.getOrientation).foreach { dir =>
        (0 until 8).foreach { i =>
          worldObj
          .spawnParticle("portal",
                         xCoord + dir.offsetX + (Random.nextDouble - 0.5D),
                         yCoord + dir.offsetY + Random.nextDouble - 0.25D,
                         zCoord + dir.offsetZ + (Random.nextDouble - 0.5D),
                         (Random.nextDouble - 0.5D) * 2.0D,
                         -Random.nextDouble,
                         (Random.nextDouble - 0.5D) * 2.0D)
                            }
                                                                                     }
    }
  }

  def isFaulty = (0 until 6).count(isSideActivated) != 2

  def isSideActivated(side: Int): Boolean = {
    ((active >> side) & 1) > 0
  }

  override def handleDescriptionNBT(compound: NBTTagCompound): Unit = {
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val itemstack = par5EntityPlayer.getCurrentEquippedItem
    if (itemstack == null) return false
    if(worldObj.isRemote) return true
    itemstack.getItem match {
      case _: IInterfaceDevice =>
        if (par5EntityPlayer.isSneaking) {
          toggleSide(ForgeDirection.getOrientation(side).getOpposite.ordinal())
        } else {
          toggleSide(side)
        }
        true
      case _                   => false
    }
  }

  def toggleSide(side: Int) = {
    active ^= 1 << side
    setModified()
    setUpdate()
  }
}
