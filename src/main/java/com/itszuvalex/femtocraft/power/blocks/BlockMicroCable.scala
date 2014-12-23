/*
 * ******************************************************************************
 *  * Copyright (C) 2013  Christopher Harris (Itszuvalex)
 *  * Itszuvalex@gmail.com
 *  *
 *  * This program is free software; you can redistribute it and/or
 *  * modify it under the terms of the GNU General Public License
 *  * as published by the Free Software Foundation; either version 2
 *  * of the License, or (at your option) any later version.
 *  *
 *  * This program is distributed in the hope that it will be useful,
 *  * but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  * GNU General Public License for more details.
 *  *
 *  * You should have received a copy of the GNU General Public License
 *  * along with this program; if not, write to the Free Software
 *  * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 *  *****************************************************************************
 */
package com.itszuvalex.femtocraft.power.blocks

import java.util
import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCable
import com.itszuvalex.femtocraft.proxy.ProxyClient
import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{AxisAlignedBB, IIcon}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

class BlockMicroCable() extends BlockPowerContainer(Material.iron) {
  var coreBorder: IIcon = null
  var connector : IIcon = null
  var coil      : IIcon = null
  var coilEdge  : IIcon = null
  var border    : IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("blockMicroCable")
  setHardness(1.0f)
  setStepSound(Block.soundTypeMetal)
  setBlockBounds()
  setTickRandomly(true)

  override def randomDisplayTick(par1World: World, x: Int, y: Int, z: Int, par5Random: Random) {
    val spawnX = x + getBlockBoundsMinX + par5Random.nextFloat * (getBlockBoundsMaxX - getBlockBoundsMinX)
    val spawnY = y + getBlockBoundsMinY + par5Random.nextFloat * (getBlockBoundsMaxY - getBlockBoundsMinY)
    val spawnZ = z + getBlockBoundsMinZ + par5Random.nextFloat * (getBlockBoundsMaxZ - getBlockBoundsMinZ)
    RenderUtils.spawnParticle(par1World, RenderUtils.MICRO_POWER_PARTICLE, spawnX, spawnY, spawnZ)
  }

  def setBlockBounds() {
    this.minX = {this.minY = {this.minZ = 4.0D / 16.0D; this.minZ}; this.minY}
    this.maxX = {this.maxY = {this.maxZ = 12.0D / 16.0D; this.maxZ}; this.maxY}
  }

  override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileEntityMicroCable

  override def renderAsNormalBlock = false

  override def getRenderType = ProxyClient.microCableRenderID

  override def addCollisionBoxesToList(par1World: World, x: Int, y: Int, z: Int, par5AxisAlignedBB: AxisAlignedBB, par6List: util.List[_], par7Entity: Entity) {
    super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB, par6List, par7Entity)
    par1World.getTileEntity(x, y, z) match {
      case cable: TileEntityMicroCable =>
        for (i <- 0 until 6) {
          if (cable.isConnected(i)) {
            val bb = boundingBoxForDirection(ForgeDirection.getOrientation(i), x, y, z)
            if (par5AxisAlignedBB.intersectsWith(bb)) {
              par6List.asInstanceOf[util.List[AxisAlignedBB]].add(bb)
            }
          }
        }
      case _                           =>
    }
  }

  protected def boundingBoxForDirection(dir: ForgeDirection, x: Int, y: Int, z: Int): AxisAlignedBB = {
    var minX = 4f / 16d
    var minY = 4f / 16d
    var minZ = 4f / 16d
    var maxX = 12f / 16d
    var maxY = 12f / 16d
    var maxZ = 12f / 16d
    dir match {
      case UP    => maxY = 1
      case DOWN  => minY = 0
      case NORTH => minZ = 0
      case SOUTH => maxZ = 1
      case EAST  => maxX = 1
      case WEST  => minX = 0
      case _     =>
    }
    AxisAlignedBB.getBoundingBox(x.toDouble + minX, y.toDouble + minY, z.toDouble + minZ, x.toDouble + maxX, y.toDouble + maxY, z.toDouble + maxZ)
  }

  @SideOnly(Side.CLIENT) override def getSelectedBoundingBoxFromPool(par1World: World, x: Int, y: Int, z: Int): AxisAlignedBB = {
    var box = AxisAlignedBB.getBoundingBox(x.toDouble + 4f / 16f, y.toDouble + 4f / 16f, z.toDouble + 4f / 16f, x.toDouble + 12f / 16f, y.toDouble + 12f / 16f, z.toDouble + 12f / 16f)
    par1World.getTileEntity(x, y, z) match {
      case cable: TileEntityMicroCable =>
        for (i <- 0 until 6) {
          if (cable.isConnected(i)) {
            box = box.func_111270_a(boundingBoxForDirection(ForgeDirection.getOrientation(i), x, y, z))
          }
        }
      case _                           =>
    }
    box
  }

  @SideOnly(Side.CLIENT) override def getCollisionBoundingBoxFromPool(par1World: World, x: Int, y: Int, z: Int): AxisAlignedBB = {
    AxisAlignedBB.getBoundingBox(x.toDouble + 4f / 16f, y.toDouble + 4f / 16f, z.toDouble + 4f / 16f, x.toDouble + 12f / 16f, y.toDouble + 12f / 16f, z.toDouble + 12f / 16f)
  }

  override def isOpaqueCube = false

  override def setBlockBoundsBasedOnState(par1iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int) {
    var minX = 4d / 16d
    var minY = 4d / 16d
    var minZ = 4d / 16d
    var maxX = 12d / 16d
    var maxY = 12d / 16d
    var maxZ = 12d / 16d
    par1iBlockAccess.getTileEntity(x, y, z) match {
      case cable: TileEntityMicroCable =>
        for (i <- 0 until 6) {
          if (cable.isConnected(i)) {
            val dir = ForgeDirection.getOrientation(i)
            dir match {
              case UP    => maxY = 1
              case DOWN  => minY = 0
              case NORTH => minZ = 0
              case SOUTH => maxZ = 1
              case EAST  => maxX = 1
              case WEST  => minX = 0
              case _     =>
            }
          }
        }
      case _                           =>
    }
    setBlockBounds(minX.toFloat, minY.toFloat, minZ.toFloat, maxX.toFloat, maxY.toFloat, maxZ.toFloat)
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableCoil")
    this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableCoreBorder")
    this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableConnector")
    this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableCoil")
    this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableCoilEdge")
    this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "FemtopowerCableBorder")
  }
}
