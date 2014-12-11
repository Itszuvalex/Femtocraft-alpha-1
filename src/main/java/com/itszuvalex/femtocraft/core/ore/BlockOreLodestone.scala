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
package com.itszuvalex.femtocraft.core.ore

import java.util.Random

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.core.Configurable
import com.itszuvalex.femtocraft.core.MagnetRegistry
import com.itszuvalex.femtocraft.core.blocks.TileContainer
import com.itszuvalex.femtocraft.core.tiles.TileEntityOreLodestone
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.block.material.Material
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.world.World

object BlockOreLodestone {
  @Configurable(comment = "Maximum amount Lodestone nuggets to drop.")
  val DROP_AMOUNT_MAX        = 4
  @Configurable(comment = "Minimum amount of Lodestone nuggets to drop.")
  val DROP_AMOUNT_MIN        = 2
  @Configurable(comment = "Set to false to prevent tile entity ticks, and prevent Magnetism from pulling items from your inventory.")
  val MAGNETIC               = true
  @Configurable(comment = "Set to false to prevent the ore from automatically pulling magnetic items from the hands of players who left click this block.")
  val PULL_MAGNETS_FROM_HAND = true
}

@Configurable class BlockOreLodestone extends TileContainer(Material.rock) {
  setCreativeTab(Femtocraft.femtocraftTab)
  setBlockTextureName(Femtocraft.ID.toLowerCase + ":" + "BlockOreLodestone")
  setBlockName("BlockOreLodestone")
  setHardness(3.0f)
  setStepSound(Block.soundTypeStone)
  setResistance(1f)

  override def createNewTileEntity(p_149915_1_ : World, p_149915_2_ : Int) = new TileEntityOreLodestone

  @SideOnly(Side.CLIENT) override def registerBlockIcons(par1IconRegister: IIconRegister) {
    blockIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "BlockOreLodestone")
  }

  override def getItemDropped(p_149650_1_ : Int, p_149650_2_ : Random, p_149650_3_ : Int) = Femtocraft.itemNuggetLodestone

  override def onBlockClicked(par1World: World, par2: Int, par3: Int, par4: Int, par5EntityPlayer: EntityPlayer) {
    val item = par5EntityPlayer.getHeldItem
    if (BlockOreLodestone.PULL_MAGNETS_FROM_HAND && MagnetRegistry.isMagnet(item)) {
      if (!par1World.isRemote) {
        val ei = par5EntityPlayer.entityDropItem(item, par5EntityPlayer.height / 2f)
        ei.delayBeforeCanPickup = 20
      }
      par5EntityPlayer.inventory.setInventorySlotContents(par5EntityPlayer.inventory.currentItem, null)
    }
  }

  override def quantityDropped(random: Random) = random.nextInt(BlockOreLodestone.DROP_AMOUNT_MAX + 1 - BlockOreLodestone.DROP_AMOUNT_MIN) + BlockOreLodestone.DROP_AMOUNT_MIN
}
