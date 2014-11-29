package com.itszuvalex.femtocraft.core.traits

import java.util.Random

import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.block.Block
import net.minecraft.inventory.IInventory
import net.minecraft.world.World

/**
 * Created by Itszuvalex on 11/28/14.
 */
trait DroppableInventory extends Block {
  var shouldDrop = true

  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int): Unit = {
    if (shouldDrop) {
      world.getTileEntity(x, y, z) match {
        case ti: IInventory =>
          val random = new Random
          for (i <- 0 until ti.getSizeInventory) {
            val itemstack = ti.getStackInSlotOnClosing(i)
            FemtocraftUtils.dropItem(itemstack, world, x, y, z, random)
          }
          world.func_147453_f(x, y, z, block)
        case _              =>
      }
    }
    super.breakBlock(world, x, y, z, block, metadata)
  }
}
