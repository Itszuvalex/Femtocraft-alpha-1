package com.itszuvalex.femtocraft.utility.tiles

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.utility.{BlockAndTileSnapshot, SpatialRelocation}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.nbt.NBTTagCompound
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._
import org.apache.logging.log4j.Level

import scala.util.Random

/**
 * Created by Chris on 12/31/2014.
 */
class TileEntitySpatialCage extends TileEntityBase {
  @Saveable(desc = true, tag = "direction")
  var direction                      = UNKNOWN.ordinal()
  @Saveable
  var powered                        = false
  @Saveable
  var snapshot: BlockAndTileSnapshot = null
  @Saveable(desc = true)
  var filled                         = false

  override def updateEntity(): Unit = {
    val wasPowered = powered
    powered = worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord)
    if (!wasPowered && powered) pickUp()
    else if (snapshot != null && !powered) putDown()
  }

  def pickUp(): Unit = {
    if (direction == UNKNOWN.ordinal()) return
    val dir = ForgeDirection.getOrientation(direction)
    if (!worldObj.isRemote) {
      if (snapshot != null) return

      snapshot = SpatialRelocation
                 .extractBlock(worldObj, xCoord + dir.offsetX, yCoord + dir.offsetY, zCoord + dir.offsetZ)
      worldObj.playSoundEffect(xCoord, yCoord, zCoord, "mob.endermen.portal", 1.0F, 1.0F)
      filled = true
      setUpdate()
    } else {
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

  def putDown(): Unit = {
    if (direction == UNKNOWN.ordinal()) return
    val dir = ForgeDirection.getOrientation(direction)
    if (!worldObj.isRemote) {
      if (snapshot == null) return
      val x = xCoord + dir.offsetX
      val y = yCoord + dir.offsetY
      val z = zCoord + dir.offsetZ
      if (worldObj.blockExists(x, y, z) && worldObj.isAirBlock(x, y, z)) {
        SpatialRelocation.applySnapshot(snapshot,
                                        worldObj,
                                        x,
                                        y,
                                        z)
        snapshot = null
        worldObj.playSoundEffect(xCoord, yCoord, zCoord, "mob.endermen.portal", 1.0F, 1.0F)
        filled = false
        setUpdate()
      }
    } else {
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

  override def handleDescriptionNBT(compound: NBTTagCompound): Unit = {
    super.handleDescriptionNBT(compound)
    Femtocraft.log(Level.INFO, "received Direction: " + direction)
    setRenderUpdate()
  }

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    val itemstack = par5EntityPlayer.getCurrentEquippedItem
    if (itemstack == null) return false
    if (worldObj.isRemote) return true
    itemstack.getItem match {
      case _: IInterfaceDevice =>
        if (par5EntityPlayer.isSneaking) {
          setSide(ForgeDirection.getOrientation(side).getOpposite)
        } else {
          setSide(ForgeDirection.getOrientation(side))
        }
        true
      case _ => false
    }
  }

  def isFilled = filled

  def isSideActive(side: Int) = if (direction == UNKNOWN.ordinal()) false else side == direction

  def setSide(side: ForgeDirection) = {
    Femtocraft.log(Level.INFO, "Direction: " + side)
    if (direction == side) direction = UNKNOWN.ordinal()
    else direction = side.ordinal()
    setModified()
    setUpdate()
  }
}
