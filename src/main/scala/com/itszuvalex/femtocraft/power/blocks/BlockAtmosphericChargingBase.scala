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

import com.itszuvalex.femtocraft.{RenderConstants, Femtocraft}
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.power.IAtmosphericChargingBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityAtmosphericChargingBase
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.IIcon
import net.minecraft.world.World

class BlockAtmosphericChargingBase extends BlockPowerContainer(Material.iron) with IAtmosphericChargingBase {
  var side             : IIcon = null
  var top              : IIcon = null
  var bottom           : IIcon = null
  var side_inset       : IIcon = null
  var coil_inset       : IIcon = null
  var coil_column_inset: IIcon = null
  var top_inset        : IIcon = null
  var coil_top_inset   : IIcon = null
  var top_pillar_top   : IIcon = null
  var top_pillar_side  : IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockAtmosphericChargingBase")
  setHardness(2.0f)
  setStepSound(Block.soundTypeMetal)

  override def createNewTileEntity(world: World, metadata: Int): TileEntity = new TileEntityAtmosphericChargingBase

  override def getRenderType = RenderConstants.FemtocraftChargingBaseRenderID

  override def isOpaqueCube = false

  override def canPlaceBlockAt(par1World: World, par2: Int, par3: Int, par4: Int): Boolean = {
    canBlockStay(par1World, par2, par3, par4)
  }

  override def canBlockStay(par1World: World, par2: Int, par3: Int, par4: Int): Boolean = {
    var block = par1World.getBlock(par2 - 1, par3, par4)
    if (block.isInstanceOf[IAtmosphericChargingBase]) {
      return false
    }
    block = par1World.getBlock(par2 + 1, par3, par4)
    if (block.isInstanceOf[IAtmosphericChargingBase]) {
      return false
    }
    block = par1World.getBlock(par2, par3, par4 - 1)
    if (block.isInstanceOf[IAtmosphericChargingBase]) {
      return false
    }
    block = par1World.getBlock(par2, par3, par4 + 1)
    !block.isInstanceOf[IAtmosphericChargingBase]
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_side")
    top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_top")
    bottom = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroMachineBlock_side")
    side_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_side_inset")
    coil_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_coil_inset")
    coil_column_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_coil_column_inset")
    top_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_top_inset")
    coil_top_inset = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_coil_top_inset")
    top_pillar_top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_top_pillar_top")
    top_pillar_side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ChargingBase_top_pillar_side")
  }

  override def maxAddonsSupported(world: World, x: Int, y: Int, z: Int) = 10

  override def maxTechSupported(world: World, x: Int, y: Int, z: Int) = EnumTechLevel.MICRO
}
