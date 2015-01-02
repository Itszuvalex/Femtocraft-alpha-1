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

import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.MultiBlockSpatialReactions
import com.itszuvalex.femtocraft.power.multiblock.MultiBlockNanoCube
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCubePort
import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

class BlockNanoCubePort extends TileContainer(Material.iron) with MultiBlockSpatialReactions {
  var portInput : IIcon = null
  var portOutput: IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockNanoCubePort")
  setTickRandomly(true)

  override def randomDisplayTick(par1World: World, x: Int, y: Int, z: Int, par5Random: Random) {
    par1World.getTileEntity(x, y, z) match {
      case port: TileEntityNanoCubePort if port.isValidMultiBlock && port.output =>
        val spawnX = x + getBlockBoundsMinX + par5Random.nextFloat * (getBlockBoundsMaxX - getBlockBoundsMinX)
        val spawnY = y + getBlockBoundsMinY + par5Random.nextFloat * (getBlockBoundsMaxY - getBlockBoundsMinY)
        val spawnZ = z + getBlockBoundsMinZ + par5Random.nextFloat * (getBlockBoundsMaxZ - getBlockBoundsMinZ)
        RenderUtils.spawnParticle(par1World, RenderUtils.NANO_POWER_PARTICLE, spawnX, spawnY, spawnZ)
      case _                                                                     =>
    }
  }

  @SideOnly(Side.CLIENT) override def getIcon(par1iBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int, par5: Int): IIcon = par1iBlockAccess.getTileEntity(par2, par3, par4) match {
    case port: TileEntityNanoCubePort => if (port.output) portOutput else portInput
    case _                            => super.getIcon(par1iBlockAccess, par2, par3, par4, par5)
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    MultiBlockNanoCube.formMultiBlockWithBlock(par1World, par2, par3, par4)
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    this.blockIcon = {portInput = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockNanoCubePort_input"); portInput}
    portOutput = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockNanoCubePort_output")
  }

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityNanoCubePort

  override def breakBlock(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block, par6: Int) {
    par1World.getTileEntity(par2, par3, par4) match {
      case port: TileEntityNanoCubePort =>
        val info = port.getInfo
        MultiBlockNanoCube.breakMultiBlock(par1World, info.x, info.y, info.z)
      case _                            =>
    }
    super.breakBlock(par1World, par2, par3, par4, par5, par6)
  }

  override def getMultiBlock = MultiBlockNanoCube
}
