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

import com.itszuvalex.femtocraft.{RenderConstants, Femtocraft}
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.power.{IAtmosphericChargingAddon, IAtmosphericChargingBase}
import com.itszuvalex.femtocraft.core.blocks.BlockBase
import com.itszuvalex.femtocraft.render.RenderUtils
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

class BlockAtmosphericChargingCoil extends BlockBase(Material.iron) with IAtmosphericChargingAddon {
  var coilConnector   : IIcon = null
  var coilConnectorTop: IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockAtmosphericChargingCoil")
  setHardness(1.0f)
  setStepSound(Block.soundTypeMetal)
  setBlockBounds(4f / 16f, 0, 4f / 16f, 12f / 16f, 1, 12f / 16f)
  setTickRandomly(true)

  override def randomDisplayTick(par1World: World, x: Int, y: Int, z: Int, par5Random: Random) {
    val spawnX = x + getBlockBoundsMinX + par5Random.nextFloat * (getBlockBoundsMaxX - getBlockBoundsMinX)
    val spawnY = y + getBlockBoundsMinY + par5Random.nextFloat * (getBlockBoundsMaxY - getBlockBoundsMinY)
    val spawnZ = z + getBlockBoundsMinZ + par5Random.nextFloat * (getBlockBoundsMaxZ - getBlockBoundsMinZ)
    RenderUtils.spawnParticle(par1World, RenderUtils.MICRO_POWER_PARTICLE, spawnX, spawnY, spawnZ)
  }

  override def renderAsNormalBlock = false

  override def getRenderType = RenderConstants.FemtocraftChargingCoilRenderID

  override def isBlockSolid(par1iBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int, par5: Int) = false

  override def isOpaqueCube = false

  override def onNeighborBlockChange(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block) {
    if (!canBlockStay(par1World, par2, par3, par4)) {
      dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0)
      par1World.setBlockToAir(par2, par3, par4)
    }
  }

  override def canBlockStay(par1World: World, par2: Int, par3: Int, par4: Int): Boolean = {
    val block = par1World.getBlock(par2, par3 - 1, par4)
    block != null && (block.isInstanceOf[IAtmosphericChargingAddon] && block.asInstanceOf[IAtmosphericChargingAddon].canSupportAddon(this, par1World, par2, par3, par4) || block.isInstanceOf[IAtmosphericChargingBase]) && par1World.isAirBlock(par2 - 1, par3, par4) && par1World.isAirBlock(par2 + 1, par3, par4) && par1World.isAirBlock(par2, par3, par4 - 1) && par1World.isAirBlock(par2, par3, par4 + 1)
  }

  override def canPlaceBlockAt(par1World: World, par2: Int, par3: Int, par4: Int) = canBlockStay(par1World, par2, par3, par4)

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = {coilConnector = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroChargingCoil_connector"); coilConnector}
    coilConnectorTop = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroChargingCoil_coilConnectorTop")
  }

  override def powerPerTick(world: World, x: Int, y: Int, z: Int) = .1f

  override def techLevel(world: World, x: Int, y: Int, z: Int) = EnumTechLevel.MICRO

  override def canSupportAddon(addon: IAtmosphericChargingAddon, world: World, x: Int, y: Int, z: Int) = true
}
