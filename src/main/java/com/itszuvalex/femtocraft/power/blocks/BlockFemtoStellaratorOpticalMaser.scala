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
import com.itszuvalex.femtocraft.api.power.plasma.IFusionReactorCore
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.MultiBlockSpatialReactions
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockFemtoStellarator
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorOpticalMaser
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}
import net.minecraftforge.common.util.ForgeDirection

class BlockFemtoStellaratorOpticalMaser extends TileContainer(Material.iron) with MultiBlockSpatialReactions {
  var activeIcon: IIcon = null

  setBlockName("BlockStellaratorOpticalMaser")
  setCreativeTab(Femtocraft.femtocraftTab)

  override def getIcon(par1IBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int, par5: Int): IIcon = {
    par1IBlockAccess.getTileEntity(par2, par3, par4) match {
      case maser: TileEntityFemtoStellaratorOpticalMaser =>
        if (maser.isIgniting || maser.isSustaining) {
          return activeIcon
        }
      case _                                             =>
    }
    blockIcon
  }

  override def onNeighborBlockChange(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block) {
    if (!canBlockStay(par1World, par2, par3, par4)) {
      breakBlock(par1World, par2, par3, par4, par5, par1World.getBlockMetadata(par2, par3, par4))
      par1World.setBlockToAir(par2, par3, par4)
    }
    super.onNeighborBlockChange(par1World, par2, par3, par4, par5)
  }

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case maser: TileEntityFemtoStellaratorOpticalMaser =>
        val info = maser.getInfo
        MultiBlockFemtoStellarator.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _                                             =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }

  override def onBlockActivated(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityPlayer: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9)

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityFemtoStellaratorOpticalMaser

  override def canPlaceBlockAt(par1World: World, par2: Int, par3: Int, par4: Int) = canBlockStay(par1World, par2, par3, par4)

  override def canBlockStay(par1World: World, par2: Int, par3: Int, par4: Int) = hasFocusAsNeighbor(par1World, par2, par3, par4)

  private def hasFocusAsNeighbor(par1World: World, par2: Int, par3: Int, par4: Int): Boolean = {
    for (dir <- ForgeDirection.VALID_DIRECTIONS) {
      if (par1World.getBlock(par2 + dir.offsetX, par3 + dir.offsetY, par4 + dir.offsetZ) eq Femtocraft.blockStellaratorFocus) {
        val te: TileEntity = par1World.getTileEntity(par2 + 2 * dir.offsetX, par3 + 2 * dir.offsetY, par4 + 2 * dir.offsetZ)
        if (te.isInstanceOf[IFusionReactorCore]) {
          return true
        }
      }
    }
    false
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockFemtoStellarator.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockFemtoStellaratorOpticalMaser")
    activeIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockFemtoStellaratorOpticalMaser_active")
  }

  override def getMultiBlock = MultiBlockFemtoStellarator
}
