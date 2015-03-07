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
import com.itszuvalex.femtocraft.api.power.{ICryoEndothermalChargingAddon, ICryoEndothermalChargingBase}
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.power.tiles.TileEntityCryoEndothermalChargingCoil
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.{IBlockAccess, World}

/**
 * Created by Christopher Harris (Itszuvalex) on 7/6/14.
 */
class BlockCryoEndothermalChargingCoil extends TileContainer(Material.iron) {
  var coilConnector   : IIcon = null
  var coilConnectorTop: IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockCryoEndothermalChargingCoil")
  setBlockBounds(4f / 16f, 0, 4f / 16f, 12f / 16f, 1, 12f / 16f)

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = {coilConnector = par1IconRegister
                                 .registerIcon(Femtocraft
                                               .ID
                                               .toLowerCase + ":" + "BlockCryoEndothermalChargingCoil_connector"); coilConnector
    }
    coilConnectorTop = par1IconRegister
                       .registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockCryoEndothermalChargingCoil_connector_top")
  }

  override def renderAsNormalBlock = false

  override def getRenderType = RenderConstants.FemtocraftCryoEndothermalChargingCoilRenderID

  override def isBlockSolid(par1iBlockAccess: IBlockAccess, par2: Int, par3: Int, par4: Int, par5: Int) = false

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityCryoEndothermalChargingCoil

  override def isOpaqueCube = false

  override def onNeighborBlockChange(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block) {
    if (!canBlockStay(par1World, par2, par3, par4)) {
      dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0)
      par1World.setBlockToAir(par2, par3, par4)
    }
  }

  override def canPlaceBlockAt(par1World: World, par2: Int, par3: Int, par4: Int) = canBlockStay(par1World,
                                                                                                 par2,
                                                                                                 par3,
                                                                                                 par4)

  override def canBlockStay(world: World, x: Int, y: Int, z: Int): Boolean = {
    val block = world.getBlock(x, y, z)

    for (height <- y + 1 until 256) {
      world.getTileEntity(x, height, z) match {
        case addon: ICryoEndothermalChargingAddon                                             =>
        case base: ICryoEndothermalChargingBase if base.maximumDepthSupported >= (height - y) => return true
        case _                                                                                => return false
      }
    }

    true
  }
}