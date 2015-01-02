package com.itszuvalex.femtocraft.core.traits.tile

import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.multiblock.{IMultiBlockComponent, MultiBlockInfo}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.world.World

/**
 * Created by Chris on 12/7/2014.
 */
trait MultiBlockComponent extends TileEntityBase with IMultiBlockComponent {
  @Saveable(desc = true) val info = new MultiBlockInfo

  def isValidMultiBlock = info.isValidMultiBlock

  def formMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    val result = info.formMultiBlock(world, x, y, z)
    getWorldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    getWorldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getWorldObj.getBlock(xCoord, yCoord, zCoord))
    result
  }

  override def handleDescriptionNBT(compound: NBTTagCompound): Unit = {
    super.handleDescriptionNBT(compound)
    setRenderUpdate()
  }

  def breakMultiBlock(world: World, x: Int, y: Int, z: Int): Boolean = {
    val result = info.breakMultiBlock(world, x, y, z)
    getWorldObj.markBlockForUpdate(xCoord, yCoord, zCoord)
    getWorldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, getWorldObj.getBlock(xCoord, yCoord, zCoord))
    result
  }

  def getInfo = info
}
