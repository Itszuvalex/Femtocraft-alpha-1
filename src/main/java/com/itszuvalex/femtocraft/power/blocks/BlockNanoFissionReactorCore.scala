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
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.DroppableInventory
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoFissionReactor
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoFissionReactorCore
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 7/6/14.
 */
class BlockNanoFissionReactorCore extends TileContainer(Material.iron) with DroppableInventory {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockNanoFissionReactorCore")

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockNanoFissionReactorCore")
  }

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityNanoFissionReactorCore

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case te: TileEntityNanoFissionReactorCore =>
        val info = te.getInfo
        MultiBlockNanoFissionReactor.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _                                    =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockNanoFissionReactor.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }
}