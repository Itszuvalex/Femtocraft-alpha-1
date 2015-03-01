package com.itszuvalex.femtocraft.industry.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.industry.tiles.TileEntityPhotonEmitter
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Christopher Harris (Itszuvalex) on 2/28/15.
 */
class BlockPhotonEmitter extends TileContainer(Material.iron) {
  setCreativeTab(Femtocraft.femtocraftTab)
  var emitterIcon  : IIcon = null
  var extensionIcon: IIcon = null

  override def getIcon(p_149673_1_ : IBlockAccess, p_149673_2_ : Int, p_149673_3_ : Int, p_149673_4_ : Int, p_149673_5_ : Int): IIcon = {
    p_149673_1_.getTileEntity(p_149673_2_, p_149673_3_, p_149673_4_) match {
      case tile: TileEntityPhotonEmitter =>
        if (p_149673_5_ == tile.getEmissionSide) emitterIcon
        else if (p_149673_5_ == tile.getExtensionSide) extensionIcon
        else blockIcon
      case _ => blockIcon
    }
  }

  override def registerBlockIcons(p_149651_1_ : IIconRegister): Unit = {
    blockIcon = p_149651_1_.registerIcon(Femtocraft.ID + ":" + "BlockPhotonEmitter")
    emitterIcon = p_149651_1_.registerIcon(Femtocraft.ID + ":" + "BlockPhotonEmitter_emitter")
    extensionIcon = p_149651_1_.registerIcon(Femtocraft.ID + ":" + "BlockPhotonEmitter_extension")
  }

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int): TileEntity = new TileEntityPhotonEmitter
}
