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
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockFemtoStellarator
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoStellaratorCore
import com.itszuvalex.femtocraft.proxy.ProxyClient
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockFemtoStellaratorCore extends TileContainer(Material.iron) {
  var outsideIcon: IIcon = null
  var insideIcon : IIcon = null
  var coreIcon   : IIcon = null

  setBlockName("BlockStellaratorCore")
  setCreativeTab(Femtocraft.femtocraftTab)

  override def renderAsNormalBlock = false

  override def getRenderType = ProxyClient.FemtocraftStellaratorCoreRenderID

  override def isOpaqueCube = false

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockFemtoStellarator.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    outsideIcon = {blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockFemtoStellaratorCore"); blockIcon}
    insideIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockFemtoStellaratorEnclosedInternals")
    coreIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockFemtoStellaratorCore_core")
  }

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case core: TileEntityFemtoStellaratorCore =>
        val info: MultiBlockInfo = core.getInfo
        MultiBlockFemtoStellarator.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _                                    =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityFemtoStellaratorCore
}
