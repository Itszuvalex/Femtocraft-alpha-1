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
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockDecontaminationChamber
import com.itszuvalex.femtocraft.power.tiles.{TileEntityDecontaminationChamber, TileEntityMagnetohydrodynamicGenerator}
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

/**
 * Created by Christopher Harris (Itszuvalex) on 7/9/14.
 */
class BlockDecontaminationChamber extends TileContainer(Material.iron) {
  private val formedSides = new Array[Array[IIcon]](2)
  for (i <- 0 until formedSides.length) {
    formedSides(i) = new Array[IIcon](2)
  }

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockDecontaminationChamber")

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int) = new TileEntityDecontaminationChamber

  @SideOnly(Side.CLIENT) override def getIcon(par1iBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int,
                                              par5: Int): IIcon = par1iBlockAccess
                                                                  .getTileEntity(par2, par3, par4) match {
    case frame: TileEntityDecontaminationChamber if frame.isValidMultiBlock
    =>
      val info = frame.getInfo
      val dir = ForgeDirection.getOrientation(par5)
      iconForSide(info, dir, par2, par3, par4)
    case _
    => super.getIcon(par1iBlockAccess, par2, par3, par4, par5)
  }

  private def iconForSide(info: MultiBlockInfo, dir: ForgeDirection, x: Int, y: Int, z: Int): IIcon = {
    val xdif = x - info.x // 1 or 0
    val ydif = y - info.y // 1 or 0
    val zdif = z - info.z // 1 or 0
    dir match {
      case UP    => iconFromGrid(xdif, -zdif)
      case DOWN  => iconFromGrid(xdif, -zdif)
      case NORTH => iconFromGrid(-xdif, ydif)
      case SOUTH => iconFromGrid(xdif, ydif)
      case EAST  => iconFromGrid(-zdif, ydif)
      case WEST  => iconFromGrid(zdif, ydif)
      case _     => blockIcon
    }
  }

  override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockDecontaminationChamber_unformed")
    for (i <- 0 until formedSides.length) {
      for (j <- 0 until formedSides(i).length) {
        formedSides(i)(j) = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockDecontaminationChamber_" + i + "_" + j)
      }
    }
  }

  private def iconFromGrid(xdif: Int, ydif: Int): IIcon = try
    formedSides(xdif)(ydif)
  catch {
    case ignored: IndexOutOfBoundsException => blockIcon
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockDecontaminationChamber.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case te: TileEntityDecontaminationChamber =>
        val info = te.getInfo
        MultiBlockDecontaminationChamber.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _                                    =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }
}