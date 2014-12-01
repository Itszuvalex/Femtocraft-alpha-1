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
package com.itszuvalex.femtocraft.research.blocks

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.traits.block.DroppableInventory
import com.itszuvalex.femtocraft.research.tiles.TileEntityResearchConsole
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.util.IIcon
import net.minecraft.world.World
import net.minecraftforge.common.util.ForgeDirection
import net.minecraftforge.common.util.ForgeDirection._

class BlockResearchConsole extends TileContainer(Material.iron) with DroppableInventory {
  var side: IIcon = null
  var top : IIcon = null
  var bot : IIcon = null

  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockName("BlockResearchConsole")

  @SideOnly(Side.CLIENT) override def getIcon(par1: Int, par2: Int) = ForgeDirection.getOrientation(par1) match {
    case UP => top
    case DOWN => bot
    case _ => side
  }

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = {side = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockResearchConsole_side"); side}
    top = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockResearchConsole_top")
    bot = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockResearchConsole_bot")
  }

  override def createNewTileEntity(world: World, metadata: Int) = new TileEntityResearchConsole

}
