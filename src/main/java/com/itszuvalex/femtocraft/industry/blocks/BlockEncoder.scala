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
package com.itszuvalex.femtocraft.industry.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.DroppableInventory
import com.itszuvalex.femtocraft.industry.tiles.TileEntityEncoder
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection

class BlockEncoder extends TileContainer(Material.iron) with DroppableInventory {
  var top : IIcon = null
  var side: IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setHardness(3.5f)
  setStepSound(Block.soundTypeMetal)

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int): IIcon = {
    ForgeDirection.getOrientation(par1) match {
      case ForgeDirection.UP   => top
      case ForgeDirection.DOWN => blockIcon
      case _                   => side
    }
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockEncoder_top")
    side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockEncoder_side")
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "MicroMachineBlock_side")
  }

  override def createTileEntity(world: World, metadata: Int) = new TileEntityEncoder
}
