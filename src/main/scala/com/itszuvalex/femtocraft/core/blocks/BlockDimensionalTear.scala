package com.itszuvalex.femtocraft.core.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.tiles.TileEntityDimensionalTear
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Christopher on 2/6/2015.
 */
class BlockDimensionalTear extends BlockContainer(Material.portal) {
  setCreativeTab(Femtocraft.femtocraftTab)

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int) = new TileEntityDimensionalTear

  override def getBlocksMovement(p_149655_1_ : IBlockAccess, p_149655_2_ : Int, p_149655_3_ : Int, p_149655_4_ : Int): Boolean = false

  override def isOpaqueCube = false

  override def renderAsNormalBlock = false

  override def getRenderType = -1
}
