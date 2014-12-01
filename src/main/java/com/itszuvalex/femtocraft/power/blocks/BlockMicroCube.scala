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
import com.itszuvalex.femtocraft.power.tiles.TileEntityMicroCube
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

class BlockMicroCube extends BlockPowerContainer(Material.iron) {
  var outputSide: IIcon = null
  var inputSide : IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("blockMicroCube")
  setHardness(3f)
  setStepSound(Block.soundTypeMetal)

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityMicroCube

  @SideOnly(Side.CLIENT) override def getIcon(access: IBlockAccess, x: Int, y: Int, z: Int, side: Int): IIcon = access.getTileEntity(x, y, z) match {
    case cube: TileEntityMicroCube => if (cube.outputs(side)) outputSide else inputSide
    case _ => this.blockIcon
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    this.blockIcon = {inputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroCube_input"); inputSide}
    outputSide = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroCube_output")
  }
}
