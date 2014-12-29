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
package com.itszuvalex.femtocraft.core.blocks

import java.util.Random

import com.itszuvalex.femtocraft.core.items.CoreItemBlock
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import net.minecraft.block.material.Material
import net.minecraft.block.{Block, BlockContainer}
import net.minecraft.entity.item.EntityItem
import net.minecraft.entity.player.{EntityPlayer, EntityPlayerMP}
import net.minecraft.entity.{Entity, EntityLivingBase}
import net.minecraft.item.{Item, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.tileentity.TileEntity
import net.minecraft.util.ChatComponentText
import net.minecraft.world.{IBlockAccess, World}

object TileContainer {
  var shouldDrop = true
}

class TileContainer(material: Material) extends BlockContainer(material) {
  setHardness(3f)
  setResistance(3f)

  def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int): TileEntity = new TileEntityBase

  override def breakBlock(world: World, x: Int, y: Int, z: Int, block: Block, metadata: Int) {
    if (TileContainer.shouldDrop) {
      world.getTileEntity(x, y, z) match {
        case tile: TileEntityBase =>
          val stack = new ItemStack(block.getItemDropped(metadata, world.rand, 0))
          val item = stack.getItem
          item match {
            case block: CoreItemBlock if block.hasItemNBT =>
              stack.stackTagCompound = new NBTTagCompound
              tile.saveInfoToItemNBT(stack.stackTagCompound)
            case _                                        =>
          }
          val spawn = new EntityItem(world, x + .5d, y + .5d, z + .5d, stack)
          spawn.delayBeforeCanPickup = 10
          world.spawnEntityInWorld(spawn)
        case _                    =>
      }
    }
    super.breakBlock(world, x, y, z, block, metadata)
  }

  override def quantityDropped(par1Random: Random) = 0

  override def onBlockActivated(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityPlayer: EntityPlayer, par6: Int, par7: Float, par8: Float, par9: Float): Boolean = {
    par1World.getTileEntity(par2, par3, par4) match {
      case base: TileEntityBase =>
        if (base.canPlayerUse(par5EntityPlayer)) {
          return base.onSideActivate(par5EntityPlayer, par6)
        }
        else {
          par5EntityPlayer.addChatMessage(new ChatComponentText(base.getOwner + " is the owner of this block."))
          return true
        }
      case _                    =>
    }
    super.onBlockActivated(par1World, par2, par3, par4, par5EntityPlayer, par6, par7, par8, par9)
  }

  override def onBlockPlacedBy(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityLivingBase: EntityLivingBase, par6ItemStack: ItemStack) {
    if (!par1World.isRemote) {
      par1World.getTileEntity(par2, par3, par4) match {
        case base: TileEntityBase =>
          if (par5EntityLivingBase == null) {
            return
          }
          if (par5EntityLivingBase.isInstanceOf[EntityPlayerMP]) {
            val item: Item = par6ItemStack.getItem
            item match {
              case block: CoreItemBlock if block.hasItemNBT =>
                base.loadInfoFromItemNBT(par6ItemStack.stackTagCompound)
              case _                                        =>
            }
            if (base.getOwner == null || base.getOwner.isEmpty) {
              base.setOwner(par5EntityLivingBase.getCommandSenderName)
            }
          }
        case _                    =>
      }
    }
    super.onBlockPlacedBy(par1World, par2, par3, par4, par5EntityLivingBase, par6ItemStack)
  }

  override def removedByPlayer(world: World, player: EntityPlayer, x: Int, y: Int, z: Int, willHarvest: Boolean): Boolean = {
    val te: TileEntity = world.getTileEntity(x, y, z)
    te match {
      case base: TileEntityBase =>
        if (!base.canPlayerUse(player)) {
          player.addChatMessage(new ChatComponentText(base.getOwner + " is the owner of this block."))
          return false
        }
      case _                    =>
    }
    super.removedByPlayer(world, player, x, y, z, willHarvest)
  }

  override def canEntityDestroy(world: IBlockAccess, x: Int, y: Int, z: Int, entity: Entity): Boolean = {
    (world.getTileEntity(x, y, z), entity) match {
      case (te: TileEntityBase, player: EntityPlayer) => te.canPlayerUse(player) && super.canEntityDestroy(world, x, y, z, entity)
      case (_, _)                                     => false
    }
  }
}
