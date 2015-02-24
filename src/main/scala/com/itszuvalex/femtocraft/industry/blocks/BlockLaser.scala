package com.itszuvalex.femtocraft.industry.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.industry.TileEntityLaser
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.entity.Entity
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Christopher Harris (Itszuvalex) on 2/24/15.
 */
class BlockLaser extends BlockContainer(Material.fire) {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockUnbreakable()

  override def getBlocksMovement(p_149655_1_ : IBlockAccess, p_149655_2_ : Int, p_149655_3_ : Int, p_149655_4_ : Int): Boolean = {
    super.getBlocksMovement(p_149655_1_, p_149655_2_, p_149655_3_, p_149655_4_)
  }

  override def renderAsNormalBlock()=  false

  override def getRenderType: Int = {
    super.getRenderType
  }

  override def onEntityCollidedWithBlock(p_149670_1_ : World, p_149670_2_ : Int, p_149670_3_ : Int, p_149670_4_ : Int, p_149670_5_ : Entity): Unit = super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_)

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int) = new TileEntityLaser
}
