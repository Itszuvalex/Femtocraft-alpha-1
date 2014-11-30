package com.itszuvalex.femtocraft.core.traits.block

import net.minecraft.block.Block
import net.minecraft.entity.EntityLivingBase
import net.minecraft.item.ItemStack
import net.minecraft.util.MathHelper
import net.minecraft.world.World

/**
 * Created by Itszuvalex on 11/28/14.
 */
trait RotateOnPlace extends Block {
  /**
   * Called whenever the block is added into the world. Args: world, x, y, z
   */
  override def onBlockAdded(world: World, x: Int, y: Int, z: Int) {
    super.onBlockAdded(world, x, y, z)
    setDefaultDirection(world, x, y, z)
  }


  /**
   * set a blocks direction
   */
  private def setDefaultDirection(world: World, x: Int, y: Int, z: Int) {
    if (!world.isRemote) {
      val south = world.getBlock(x, y, z - 1)
      val north = world.getBlock(x, y, z + 1)
      val west = world.getBlock(x - 1, y, z)
      val east = world.getBlock(x + 1, y, z)
      var metadata: Byte = 3
      if (south.isOpaqueCube && !north.isOpaqueCube) {
        metadata = 3
      }
      if (north.isOpaqueCube && !south.isOpaqueCube) {
        metadata = 2
      }
      if (west.isOpaqueCube && !east.isOpaqueCube) {
        metadata = 5
      }
      if (east.isOpaqueCube && !west.isOpaqueCube) {
        metadata = 4
      }
      world.setBlockMetadataWithNotify(x, y, z, metadata, 2)
    }
  }

  /**
   * Called when the block is placed in the world.
   */
  override def onBlockPlacedBy(world: World, x: Int, y: Int, z: Int, entity: EntityLivingBase, itemStack: ItemStack) {
    super.onBlockPlacedBy(world, x, y, z, entity, itemStack)
    val mask = MathHelper.floor_double((entity.rotationYaw * 4.0F / 360.0F).toDouble + 0.5D) & 3
    if (mask == 0) {
      world.setBlockMetadataWithNotify(x, y, z, 2, 2)
    }
    if (mask == 1) {
      world.setBlockMetadataWithNotify(x, y, z, 5, 2)
    }
    if (mask == 2) {
      world.setBlockMetadataWithNotify(x, y, z, 3, 2)
    }
    if (mask == 3) {
      world.setBlockMetadataWithNotify(x, y, z, 4, 2)
    }
  }
}
