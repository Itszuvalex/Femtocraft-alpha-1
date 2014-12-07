package com.itszuvalex.femtocraft.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.traits.block.MultiSided
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Chris on 12/7/2014.
 */
class BlockSidedTest extends Block(Material.iron) with MultiSided {
  override def rotateBlock(worldObj: World, x: Int, y: Int, z: Int, axis: ForgeDirection): Boolean = super.rotateBlock(worldObj, x, y, z, axis)


  override def onBlockActivated(world: World, x: Int, y: Int, z: Int, player: EntityPlayer, side: Int, playerX: Float, playerY: Float, playerZ: Float): Boolean = {
    world.setBlockMetadataWithNotify(x, y, z, world.getBlockMetadata(x, y, z) + 1, 2)
  }

  override def getTextureNameForSide(side: ForgeDirection) = side match {
    case NORTH   => Femtocraft.ID + ":" + "SidedTest" + "_north"
    case SOUTH   => Femtocraft.ID + ":" + "SidedTest" + "_south"
    case EAST    => Femtocraft.ID + ":" + "SidedTest" + "_east"
    case WEST    => Femtocraft.ID + ":" + "SidedTest" + "_west"
    case UP      => Femtocraft.ID + ":" + "SidedTest" + "_up"
    case DOWN    => Femtocraft.ID + ":" + "SidedTest" + "_down"
    case UNKNOWN => ""
  }
}
