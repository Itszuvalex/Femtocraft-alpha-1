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
import com.itszuvalex.femtocraft.power.tiles.TileEntityNanoCable
import com.itszuvalex.femtocraft.proxy.ProxyClient
import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.world.World

class BlockNanoCable() extends BlockMicroCable() {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("blockNanoCable")
  setHardness(1.0f)
  setStepSound(Block.soundTypeMetal)
  setBlockBounds()
  setTickRandomly(true)

  override def randomDisplayTick(par1World: World, x: Int, y: Int, z: Int, par5Random: Random) {
    val spawnX: Double = x + getBlockBoundsMinX + par5Random.nextFloat * (getBlockBoundsMaxX - getBlockBoundsMinX)
    val spawnY: Double = y + getBlockBoundsMinY + par5Random.nextFloat * (getBlockBoundsMaxY - getBlockBoundsMinY)
    val spawnZ: Double = z + getBlockBoundsMinZ + par5Random.nextFloat * (getBlockBoundsMaxZ - getBlockBoundsMinZ)
    RenderUtils.spawnParticle(par1World, RenderUtils.NANO_POWER_PARTICLE, spawnX, spawnY, spawnZ)
  }

  override def getRenderType = ProxyClient.nanoCableRenderID

  override def setBlockBounds() {
    this.minX = {this.minY = {this.minZ = 4.0D / 16.0D; this.minZ}; this.minY}
    this.maxX = {this.maxY = {this.maxZ = 12.0D / 16.0D; this.maxZ}; this.maxY}
  }

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityNanoCable

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    this.blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableCoil")
    this.coreBorder = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableCoreBorder")
    this.connector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableConnector")
    this.coil = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableCoil")
    this.coilEdge = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableCoilEdge")
    this.border = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "nanoCableBorder")
  }
}
