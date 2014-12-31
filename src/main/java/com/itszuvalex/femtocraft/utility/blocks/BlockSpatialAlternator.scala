package com.itszuvalex.femtocraft.utility.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.utility.tiles.TileEntitySpatialAlternator
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Chris on 12/30/2014.
 */
class BlockSpatialAlternator extends TileContainer(Material.ground) {
  setCreativeTab(Femtocraft.femtocraftTab)

  var activeIcon      : IIcon = null
  var activeFaultyIcon: IIcon = null
  var faultyIcon      : IIcon = null

  override def canConnectRedstone(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = true

  override def createTileEntity(world: World, metadata: Int) = new TileEntitySpatialAlternator

  override def shouldCheckWeakPower(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = false

  override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
    world.getTileEntity(x, y, z) match {
      case alt: TileEntitySpatialAlternator if alt.isFaulty  =>
        if (alt.isSideActivated(side)) activeFaultyIcon else faultyIcon
      case alt: TileEntitySpatialAlternator if !alt.isFaulty =>
        if (alt.isSideActivated(side)) activeIcon else blockIcon
      case _                                                 => faultyIcon
    }
  }

  override def registerBlockIcons(register: IIconRegister): Unit = {
    blockIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialAlternator")
    activeIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialAlternator_active")
    activeFaultyIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialAlternator_active_faulty")
    faultyIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialAlternator_faulty")
  }
}
