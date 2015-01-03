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
package com.itszuvalex.femtocraft.transport.items.blocks

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.items.IInterfaceDevice
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.proxy.ProxyClient
import com.itszuvalex.femtocraft.transport.items.tiles.TileEntityVacuumTube
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.Entity
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.{AxisAlignedBB, IIcon}
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

class BlockVacuumTube extends TileContainer(Material.iron) {
  var indicatorIcon             : IIcon = null
  var straightIcon              : IIcon = null
  var straightInsetIcon         : IIcon = null
  var straightInsetIcon_blackout: IIcon = null
  var turnIcon                  : IIcon = null
  var turnInsetIcon             : IIcon = null
  var turnInsetIcon_blackout    : IIcon = null
  var endIcon                   : IIcon = null
  var endInsetIcon              : IIcon = null

  setBlockName("BlockVacuumTube")
  setHardness(3.5f)
  setStepSound(Block.soundTypeMetal)
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockBounds()

  def setBlockBounds() {
    this.minX = {this.minY = {this.minZ = 4.0D / 16.0D; this.minZ}; this.minY}
    this.maxX = {this.maxY = {this.maxZ = 12.0D / 16.0D; this.maxZ}; this.maxY}
  }

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case tube: TileEntityVacuumTube =>
        tube.onBlockBreak()
      case _                          =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }

  override def onBlockActivated(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityPlayer: EntityPlayer,
                                par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (par1World.isRemote) {
      return true
    }
    par1World.getTileEntity(par2, par3, par4) match {
      case tube: TileEntityVacuumTube =>
        val itemStack = par5EntityPlayer.getCurrentEquippedItem
        if (itemStack == null) return false
        itemStack.getItem match {
          case _: IInterfaceDevice =>
            tube.searchForConnection()
            true
          case _                   =>
            false
        }
      case _                          =>
        false
    }
  }

  override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileEntityVacuumTube


  override def renderAsNormalBlock = false

  override def getRenderType = ProxyClient.FemtocraftVacuumTubeRenderID

  override def addCollisionBoxesToList(par1World: World, x: Int, y: Int, z: Int, par5AxisAlignedBB: AxisAlignedBB,
                                       par6List: util.List[_], par7Entity: Entity) {
    super.addCollisionBoxesToList(par1World, x, y, z, par5AxisAlignedBB, par6List, par7Entity)
    par1World.getTileEntity(x, y, z) match {
      case tube: TileEntityVacuumTube =>
        val inputBB = boundingBoxForDirection(tube.getInput, x, y, z)
        if (par5AxisAlignedBB.intersectsWith(inputBB)) {
          par6List.asInstanceOf[util.List[AxisAlignedBB]].add(inputBB)
        }
        val outputBB = boundingBoxForDirection(tube.getOutput, x, y, z)
        if (par5AxisAlignedBB.intersectsWith(outputBB)) {
          par6List.asInstanceOf[util.List[AxisAlignedBB]].add(outputBB)
        }
      case _                          =>
    }
  }

  override def getCollisionBoundingBoxFromPool(par1World: World, x: Int, y: Int, z: Int) =
    AxisAlignedBB
    .getBoundingBox(x.toDouble + 4d / 16d,
                    y.toDouble + 4d / 16d,
                    z.toDouble + 4d / 16d,
                    x.toDouble + 12d / 16d,
                    y.toDouble + 12d / 16d,
                    z.toDouble + 12d / 16d)

  @SideOnly(Side.CLIENT) override def getSelectedBoundingBoxFromPool(par1World: World, x: Int, y: Int,
                                                                     z: Int): AxisAlignedBB = {
    val box: AxisAlignedBB = AxisAlignedBB
                             .getBoundingBox(x.toDouble + getBlockBoundsMinX,
                                             y.toDouble + getBlockBoundsMinY,
                                             z.toDouble + getBlockBoundsMinZ,
                                             x.toDouble + getBlockBoundsMaxX,
                                             y.toDouble + getBlockBoundsMaxY,
                                             z.toDouble + getBlockBoundsMaxZ)
    par1World.getTileEntity(x, y, z) match {
      case tube: TileEntityVacuumTube =>
        val inputBB = boundingBoxForDirection(tube.getInput, x, y, z)
        val outputBB = boundingBoxForDirection(tube.getOutput, x, y, z)
        box.func_111270_a(inputBB).func_111270_a(outputBB)
      case _                          => box
    }
  }

  private def boundingBoxForDirection(dir: ForgeDirection, x: Int, y: Int, z: Int): AxisAlignedBB = {
    var minX = getBlockBoundsMinX
    var minY = getBlockBoundsMinY
    var minZ = getBlockBoundsMinZ
    var maxX = getBlockBoundsMaxX
    var maxY = getBlockBoundsMaxY
    var maxZ = getBlockBoundsMaxZ
    dir match {
      case UP    => maxY = 1
      case DOWN  => minY = 0
      case NORTH => minZ = 0
      case SOUTH => maxZ = 1
      case EAST  => maxX = 1
      case WEST  => minX = 0
      case _     =>
    }
    AxisAlignedBB
    .getBoundingBox(x.toDouble + minX,
                    y.toDouble + minY,
                    z.toDouble + minZ,
                    x.toDouble + maxX,
                    y.toDouble + maxY,
                    z.toDouble + maxZ)
  }

  override def isOpaqueCube = false

  override def setBlockBoundsBasedOnState(par1iBlockAccess: IBlockAccess, x: Int, y: Int, z: Int) {
    par1iBlockAccess.getTileEntity(x, y, z) match {
      case tube: TileEntityVacuumTube =>
        boundingBoxForDirection(tube.getInput, x, y, z)
        boundingBoxForDirection(tube.getOutput, x, y, z)
        var minX: Double = 4d / 16d
        var minY: Double = 4d / 16d
        var minZ: Double = 4d / 16d
        var maxX: Double = 12d / 16d
        var maxY: Double = 12d / 16d
        var maxZ: Double = 12d / 16d
        tube.getInput match {
          case UP    => maxY = 1
          case DOWN  => minY = 0
          case NORTH => minZ = 0
          case SOUTH => maxZ = 1
          case EAST  => maxX = 1
          case WEST  => minX = 0
          case _     =>
        }
        tube.getOutput match {
          case UP    => maxY = 1
          case DOWN  => minY = 0
          case NORTH => minZ = 0
          case SOUTH => maxZ = 1
          case EAST  => maxX = 1
          case WEST  => minX = 0
          case _     =>
        }
        setBlockBounds(minX.toFloat, minY.toFloat, minZ.toFloat, maxX.toFloat, maxY.toFloat, maxZ.toFloat)
      case _                          =>
    }
  }

  override def onEntityCollidedWithBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5Entity: Entity) {
    (par1World.getTileEntity(par2, par3, par4), par5Entity) match {
      case (tube: TileEntityVacuumTube, item: EntityItem) =>
        tube.OnItemEntityCollision(item)
      case _                                              =>
    }
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case tube: TileEntityVacuumTube =>
        tube.searchForFullConnections()
      case _                          =>
    }
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    indicatorIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_indicator")
    straightIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_side_straight")
    straightInsetIcon = par1IconRegister
                        .registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_side_straight_inset")
    straightInsetIcon_blackout = par1IconRegister
                                 .registerIcon(Femtocraft
                                               .ID
                                               .toLowerCase + ":" + "VacuumTube_side_straight_inset_blackout")
    turnIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_side_curved")
    turnInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_side_curved_inset")
    turnInsetIcon_blackout = par1IconRegister
                             .registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_side_curved_inset_blackout")
    endIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_end")
    endInsetIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "VacuumTube_end_inset")
  }

  override def onNeighborChange(world: IBlockAccess, x: Int, y: Int, z: Int, tileX: Int, tileY: Int, tileZ: Int) {
    world.getTileEntity(x, y, z) match {
      case tube: TileEntityVacuumTube =>
        tube.onNeighborTileChange()
      case _                          =>
    }
    super.onNeighborChange(world, x, y, z, tileX, tileY, tileZ)
  }
}
