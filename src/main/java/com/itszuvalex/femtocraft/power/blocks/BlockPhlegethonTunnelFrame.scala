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

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.multiblock.MultiBlockInfo
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockPhlegethonTunnel
import com.itszuvalex.femtocraft.power.tiles.{TileEntityPhlegethonTunnelCore, TileEntityPhlegethonTunnelFrame}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Christopher Harris (Itszuvalex) on 7/12/14.
 */
class BlockPhlegethonTunnelFrame extends TileContainer(Material.iron) {
  private val sideIcons_inactive = makeEmptyIconArray
  private val topIcons_inactive  = makeEmptyIconArray
  private val botIcons_inactive  = makeEmptyIconArray
  private val sideIcons_active   = makeEmptyIconArray
  private val topIcons_active    = makeEmptyIconArray
  private val botIcons_active    = makeEmptyIconArray

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockPhlegethonTunnelFrame")

  private def makeEmptyIconArray: Array[Array[IIcon]] = {
    val newArray = new Array[Array[IIcon]](3)
    for (i <- 0 until newArray.length) {
      newArray(i) = new Array[IIcon](3)
    }
    newArray
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockPhlegethonTunnelFrame_unformed")
    registerIcons(sideIcons_active, "side_active", par1IconRegister)
    registerIcons(sideIcons_inactive, "side_inactive", par1IconRegister)
    registerIcons(topIcons_inactive, "top_inactive", par1IconRegister)
    registerIcons(topIcons_active, "top_active", par1IconRegister)
    registerIcons(botIcons_inactive, "bot_inactive", par1IconRegister)
    registerIcons(botIcons_active, "bot_active", par1IconRegister)
  }

  private def registerIcons(array: Array[Array[IIcon]], name: String, register: IIconRegister) {
    for (i <- 0 until array.length) {
      for (j <- 0 until array(i).length) {
        array(j)(i) = register.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockPhlegethonTunnel_" + name + "_" + i + "_" + j)
      }
    }
  }

  @SideOnly(Side.CLIENT) override def getIcon(par1iBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int, par5: Int): IIcon = par1iBlockAccess.getTileEntity(par2, par3, par4) match {
    case frame: TileEntityPhlegethonTunnelFrame if frame.isValidMultiBlock =>
      val info = frame.getInfo
      val dir = ForgeDirection.getOrientation(par5)
      val core = par1iBlockAccess.getTileEntity(info.x, info.y, info.z).asInstanceOf[TileEntityPhlegethonTunnelCore]
      val active: Boolean = core != null && core.isActive
      iconForSide(info, dir, active, par2, par3, par4)
    case _ => super.getIcon(par1iBlockAccess, par2, par3, par4, par5)
  }

  private def iconForSide(info: MultiBlockInfo, dir: ForgeDirection, active: Boolean, x: Int, y: Int, z: Int): IIcon = {
    val xdif = x - info.x
    val ydif = y - info.y
    val zdif = z - info.z
    dir match {
      case UP => iconFromGrid(dir, active, xdif, -zdif)
      case DOWN => iconFromGrid(dir, active, xdif, -zdif)
      case NORTH => iconFromGrid(dir, active, -xdif, ydif)
      case SOUTH => iconFromGrid(dir, active, xdif, ydif)
      case EAST => iconFromGrid(dir, active, -zdif, ydif)
      case WEST => iconFromGrid(dir, active, zdif, ydif)
      case _ => blockIcon
    }
  }

  private def iconFromGrid(side: ForgeDirection, active: Boolean, xdif: Int, ydif: Int): IIcon = try
    side match {
      case UP =>
        if (active) topIcons_active(xdif + 1)(ydif + 1) else topIcons_inactive(xdif + 1)(ydif + 1)
      case DOWN =>
        if (active) botIcons_active(xdif + 1)(ydif + 1) else botIcons_inactive(xdif + 1)(ydif + 1)
      case _ =>
        if (active) sideIcons_active(xdif + 1)(ydif + 1) else sideIcons_inactive(xdif + 1)(ydif + 1)
    }
  catch {
    case ignored: IndexOutOfBoundsException => blockIcon
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockPhlegethonTunnel.instance.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  override def createTileEntity(world: World, metadata: Int) = new TileEntityPhlegethonTunnelFrame

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case frame: TileEntityPhlegethonTunnelFrame =>
        val info = frame.getInfo
        MultiBlockPhlegethonTunnel.instance.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _ =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }
}