package com.itszuvalex.femtocraft.utility.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.utility.tiles.TileEntitySpatialCage
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Chris on 12/30/2014.
 */
class BlockSpatialCage extends TileContainer(Material.iron) {
  setCreativeTab(Femtocraft.femtocraftTab)

  var activeIcon      : IIcon = null
  var filledIcon      : IIcon = null
  var filledActiveIcon: IIcon = null

  override def canConnectRedstone(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = true

  override def createTileEntity(world: World, metadata: Int) = new TileEntitySpatialCage

  override def shouldCheckWeakPower(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int) = false

  override def getIcon(world: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = {
    world.getTileEntity(x, y, z) match {
      case alt: TileEntitySpatialCage if !alt.isFilled =>
        if (alt.isSideActive(side)) activeIcon else blockIcon
      case alt: TileEntitySpatialCage if alt.isFilled =>
        if (alt.isSideActive(side)) filledActiveIcon else filledIcon
      case _ => blockIcon
    }
  }

  override def registerBlockIcons(register: IIconRegister): Unit = {
    blockIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialCage")
    activeIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialCage_active")
    filledIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialCage_filled")
    filledActiveIcon = register.registerIcon(Femtocraft.ID + ":" + "BlockSpatialCage_filled_active")
  }
}
