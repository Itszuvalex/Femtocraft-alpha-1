package com.itszuvalex.femtocraft.utility

import com.itszuvalex.femtocraft.api.core.ISaveable
import cpw.mods.fml.common.registry.GameRegistry
import net.minecraft.block.Block
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.world.World
import net.minecraftforge.common.DimensionManager

/**
 * Created by Chris on 12/30/2014.
 */
object BlockAndTileSnapshot {
  def apply(nBTTagCompound: NBTTagCompound) = loadFromNBT(nBTTagCompound)


  def loadFromNBT(compound: NBTTagCompound): BlockAndTileSnapshot = {
    new BlockAndTileSnapshot(compound.getInteger("dimension"),
                             compound.getInteger("posX"),
                             compound.getInteger("posY"),
                             compound.getInteger("posZ"),
                             compound.getString("modID"),
                             compound.getString("blockID"),
                             compound.getInteger("meta"),
                             if (compound.hasKey("nbt")) compound.getCompoundTag("nbt") else null)
  }
}

class BlockAndTileSnapshot(private var _dimensionID: Int, var x: Int, var y: Int, var z: Int, var modID: String,
                           var blockID: String, var metadata: Int, var te: NBTTagCompound) /*extends ISaveable*/{
  lazy val block = GameRegistry.findBlock(modID, blockID)

  def world = DimensionManager.getWorld(dimensionID)

  def world_=(world: World) = _dimensionID = world.provider.dimensionId

  def this(dimensionID: Int, x: Int, y: Int, z: Int, block: Block, metadata: Int, te: NBTTagCompound) =
    this(dimensionID,
         x,
         y,
         z,
         GameRegistry.findUniqueIdentifierFor(block).modId,
         GameRegistry.findUniqueIdentifierFor(block).name,
         metadata,
         te)

  def this(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int, te: NBTTagCompound) =
    this(world.provider.dimensionId,
         x,
         y,
         z,
         GameRegistry.findUniqueIdentifierFor(block).modId,
         GameRegistry.findUniqueIdentifierFor(block).name,
         metadata,
         te)

  def this(dimensionID: Int, x: Int, y: Int, z: Int, block: Block, metadata: Int, te: TileEntity) =
    this(dimensionID, x, y, z, block, metadata, if (te != null) {
      val nbt = new NBTTagCompound
      te.writeToNBT(nbt)
      nbt
    } else {
      null
    })

  def this(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int, te: TileEntity) =
    this(world, x, y, z, block, metadata, if (te != null) {
      val nbt = new NBTTagCompound
      te.writeToNBT(nbt)
      nbt
    } else {
      null
    })

  def this(world: World, x: Int, y: Int, z: Int) =
    this(world, x, y, z, world.getBlock(x, y, z), world.getBlockMetadata(x, y, z), world.getTileEntity(x, y, z))

  def saveToNBT(compound: NBTTagCompound): Unit = {
    compound.setInteger("dimension", dimensionID)
    compound.setInteger("posX", x)
    compound.setInteger("posY", y)
    compound.setInteger("posZ", z)
    compound.setString("modID", modID)
    compound.setString("blockID", blockID)
    compound.setInteger("meta", metadata)
    if (te != null) compound.setTag("nbt", te)
  }

  def dimensionID = _dimensionID

  def dimensionID_=(dim: Int) = _dimensionID = dim
/*
  override def loadFromNBT(compound: NBTTagCompound): Unit =  {
    _dimensionID = compound.getInteger("dimension")
    x = compound.getInteger("posX")
    y = compound.getInteger("posY")
    z = compound.getInteger("posZ")
    modID = compound.getString("modID")
    blockID = compound.getString("blockID")
    metadata = compound.getInteger("meta")
    te = if (compound.hasKey("nbt")) compound.getCompoundTag("nbt") else null
  }*/
}
