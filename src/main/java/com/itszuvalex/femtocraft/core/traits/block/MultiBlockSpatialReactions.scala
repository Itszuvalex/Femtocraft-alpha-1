package com.itszuvalex.femtocraft.core.traits.block

import com.itszuvalex.femtocraft.api.multiblock.IMultiBlockComponent
import net.minecraft.world.World

/**
 * Created by Itszuvalex on 1/1/15.
 */
trait MultiBlockSpatialReactions extends MultiBlock with SpatialReactions {
  override def onPickup(world: World, x: Int, y: Int, z: Int): Unit = {
    world.getTileEntity(x, y, z) match {
      case m: IMultiBlockComponent if m.getInfo.isValidMultiBlock => getMultiBlock.breakMultiBlock(world, m.getInfo.x, m.getInfo.y, m.getInfo.z)
      case _                                                      =>
    }
  }

  override def onPlacement(world: World, x: Int, y: Int, z: Int): Unit = {
    getMultiBlock.formMultiBlockWithBlock(world, x, y, z)
  }
}
