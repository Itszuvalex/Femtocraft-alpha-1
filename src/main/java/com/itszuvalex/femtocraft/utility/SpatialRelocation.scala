package com.itszuvalex.femtocraft.utility

import com.itszuvalex.femtocraft.Femtocraft
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.{World, WorldServer}
import net.minecraftforge.common.MinecraftForge
import net.minecraftforge.common.util.{BlockSnapshot, ForgeDirection}
import net.minecraftforge.event.world.BlockEvent.{BreakEvent, PlaceEvent}

import scala.util.Random

/**
 * Created by Chris on 12/30/2014.
 */
object SpatialRelocation {
  val shiftElseRemake = false

  def shiftBlock(world: World, x: Int, y: Int, z: Int, direction: ForgeDirection): Unit = {
    val newX = x + direction.offsetX
    val newY = y + direction.offsetY
    val newZ = z + direction.offsetZ
    moveBlock(world, x, y, z, world, newX, newY, newZ)
  }

  def moveBlock(world: World, x: Int, y: Int, z: Int, destWorld: World, destX: Int, destY: Int, destZ: Int,
                replace: Boolean = false): Unit = {
    if (!replace && !destWorld.isAirBlock(destX, destY, destZ)) return
    applySnapshot(extractBlock(world, x, y, z), destWorld, destX, destY, destZ)
    world.playSoundEffect(x, y, z, "mob.endermen.portal", 1.0F, 1.0F)
    if (world != destWorld || (((x - destX) * (x - destX) + (y - destY) * (y - destY) + (z - destZ) * (z - destZ)) > 64)) {
      destWorld.playSoundEffect(destX, destY, destZ, "mob.endermen.portal", 1.0F, 1.0F)
    }
  }

  def extractBlock(world: World, x: Int, y: Int, z: Int): BlockAndTileSnapshot = {
    world match {
      case world1: WorldServer =>
        if (MinecraftForge
            .EVENT_BUS
            .post(new BreakEvent(x,
                                 y,
                                 z,
                                 world,
                                 world.getBlock(x, y, z),
                                 world.getBlockMetadata(x, y, z),
                                 Femtocraft.getFakePlayer(world1)))) {
          return null
        }
      case _                   =>
    }
    val snapshot = new BlockAndTileSnapshot(world, x, y, z)
    world.removeTileEntity(x, y, z)
    world.setBlockToAir(x, y, z)
    snapshot
  }

  def applySnapshot(s: BlockAndTileSnapshot): Unit = applySnapshot(s, s.world, s.x, s.y, s.z)

  def applySnapshot(s: BlockAndTileSnapshot, destWorld: World, destX: Int, destY: Int, destZ: Int): Unit = {
    if (s == null) return
    if (!s.block.canPlaceBlockAt(destWorld, destX, destY, destZ)) return
    destWorld match {
      case world1: WorldServer =>
        if (MinecraftForge
            .EVENT_BUS
            .post(new PlaceEvent(new BlockSnapshot(destWorld,
                                                   destX,
                                                   destY,
                                                   destZ,
                                                   s.block,
                                                   destWorld.getBlockMetadata(destX, destY, destZ)),
                                 destWorld.getBlock(destX, destY, destZ),
                                 Femtocraft.getFakePlayer(world1)))) {
          return
        }
      case _                   =>
    }

    destWorld.setBlock(destX, destY, destZ, s.block, s.metadata, 2)
    if (s.te != null) {
      if (s.x != destX) s.te.setInteger("x", destX)
      if (s.y != destY) s.te.setInteger("y", destY)
      if (s.z != destZ) s.te.setInteger("z", destZ)
      val newTile = if (s.world == destWorld) {
        TileEntity.createAndLoadEntity(s.te)
      } else {
        val tile = s.block.createTileEntity(destWorld, s.metadata)
        tile.readFromNBT(s.te)
        tile
      }
      newTile.blockType = s.block
      destWorld.setTileEntity(destX, destY, destZ, newTile)
    }
  }
}
