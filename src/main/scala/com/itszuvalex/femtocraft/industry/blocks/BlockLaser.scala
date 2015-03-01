package com.itszuvalex.femtocraft.industry.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.industry.tiles.TileEntityLaser
import com.itszuvalex.femtocraft.proxy.ProxyClient
import net.minecraft.block.BlockContainer
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.util.{AxisAlignedBB, IIcon}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Christopher Harris (Itszuvalex) on 2/24/15.
 */
class BlockLaser extends BlockContainer(Material.fire) {

  var laserIcon: IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockUnbreakable()
  setBlockBounds()

  def setBlockBounds() {
    this.minX = {this.minY = {this.minZ = 7.0D / 16.0D; this.minZ}; this.minY}
    this.maxX = {this.maxY = {this.maxZ = 9.0D / 16.0D; this.maxZ}; this.maxY}
  }

  override def getBlocksMovement(p_149655_1_ : IBlockAccess, p_149655_2_ : Int, p_149655_3_ : Int, p_149655_4_ : Int): Boolean = false

  override def getCollisionBoundingBoxFromPool(p_149668_1_ : World, p_149668_2_ : Int, p_149668_3_ : Int, p_149668_4_ : Int): AxisAlignedBB = null

  override def renderAsNormalBlock() = false

  override def isReplaceable(world: IBlockAccess, x: Int, y: Int, z: Int) = true

  override def isOpaqueCube = false

  override def getRenderType = ProxyClient.FemtocraftLaserRenderID

  override def onEntityCollidedWithBlock(p_149670_1_ : World, p_149670_2_ : Int, p_149670_3_ : Int, p_149670_4_ : Int, p_149670_5_ : Entity): Unit = super.onEntityCollidedWithBlock(p_149670_1_, p_149670_2_, p_149670_3_, p_149670_4_, p_149670_5_)

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int) = new TileEntityLaser


  override def setBlockBoundsBasedOnState(par1iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int) {
    var minX = 7d / 16d
    var minY = 7d / 16d
    var minZ = 7d / 16d
    var maxX = 9d / 16d
    var maxY = 9d / 16d
    var maxZ = 9d / 16d
    par1iBlockAccess.getTileEntity(x, y, z) match {
      case cable: TileEntityLaser =>
        val dir = cable.getDirection
        dir match {
          case UP | DOWN => minY = 0; maxY = 1
          case NORTH | SOUTH => minZ = 0; maxZ = 1
          case EAST | WEST => minX = 0; maxX = 1
          case _ =>
        }
      case _ =>
    }
    setBlockBounds(minX.toFloat, minY.toFloat, minZ.toFloat, maxX.toFloat, maxY.toFloat, maxZ.toFloat)
  }

  override def registerBlockIcons(p_149651_1_ : IIconRegister): Unit = {
    blockIcon = p_149651_1_.registerIcon(Femtocraft.ID + ":" + "BlockLaser")
    laserIcon = blockIcon
  }

}
