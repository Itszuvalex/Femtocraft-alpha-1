package com.itszuvalex.femtocraft.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.BlockBase
import com.itszuvalex.femtocraft.utility.SpatialRelocation
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

/**
 * Created by Chris on 12/30/2014.
 */
class BlockSnapshotTest extends BlockBase(Material.ground) {
  setCreativeTab(Femtocraft.femtocraftTab)

  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, playerX: Float,
                                playerY: Float, playerZ: Float): Boolean = {

    if (!world.isRemote) {
      val top = SpatialRelocation.extractBlock(world, x, y + 1, z)
      val bot = SpatialRelocation.extractBlock(world, x, y - 1, z)
      SpatialRelocation.applySnapshot(top, world, x, y - 1, z)
      SpatialRelocation.applySnapshot(bot, world, x, y + 1, z)
    }
    true
  }
}
