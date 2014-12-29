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
/**
 *
 */
package com.itszuvalex.femtocraft.power.blocks

import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.power.traits.PowerTileContainer
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.entity.EntityLivingBase
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.util.ChatComponentText
import net.minecraft.world.World

/**
 * @author Itszuvalex
 */
class BlockPowerContainer(material: Material) extends TileContainer(material) {
  override def onBlockPlacedBy(par1World: World, par2: Int, par3: Int, par4: Int,
                               par5EntityLivingBase: EntityLivingBase, par6ItemStack: ItemStack) {
    super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack)
    par1World.getTileEntity(par2, par3, par4) match {
      case container: PowerTileContainer => container.checkConnections()
      case _                             =>
    }
  }

  override def onBlockActivated(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityPlayer: EntityPlayer,
                                par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    if (super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9)) {
      par1World.getTileEntity(par2, par3, par4) match {
        case container: PowerTileContainer => par5EntityPlayer
                                              .addChatMessage(new ChatComponentText(String
                                                                                    .valueOf(container
                                                                                             .getCurrentPower) + "/" + String
                                                                                                                       .valueOf(
            container.getMaxPower)))
        case _                             =>
      }
      true
    } else {
      false
    }
  }

  override def onNeighborBlockChange(par1World: World, par2: Int, par3: Int, par4: Int, par5: Block) {
    super.onNeighborBlockChange(par1World, par2, par3, par4, par5)
    par1World.getTileEntity(par2, par3, par4) match {
      case container: PowerTileContainer => container.checkConnections()
      case _                             =>
    }
  }

  override def onBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int, par6: Float, par7: Float,
                             par8: Float, par9: Int): Int = {
    val `val` = super.onBlockPlaced(par1World, par2, par3, par4, par5, par6, par7, par8, par9)
    par1World.getTileEntity(par2, par3, par4) match {
      case container: PowerTileContainer => container.checkConnections()
      case _                             =>
    }
    par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par1World.getBlock(par2, par3, par4))
    `val`
  }

  override def onPostBlockPlaced(par1World: World, par2: Int, par3: Int, par4: Int, par5: Int) {
    super.onPostBlockPlaced(par1World, par2, par3, par4, par5)
    par1World.notifyBlocksOfNeighborChange(par2, par3, par4, par1World.getBlock(par2, par3, par4))
    par1World.getTileEntity(par2, par3, par4) match {
      case container: PowerTileContainer => container.checkConnections()
      case _                             =>
    }
  }
}
