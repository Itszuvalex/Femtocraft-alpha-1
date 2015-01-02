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
package com.itszuvalex.femtocraft.core.items

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraft.nbt.NBTTagCompound
import net.minecraft.util.EnumChatFormatting
import net.minecraft.world.World

class CoreItemBlock(block: Block) extends ItemBlock(block) {
  setCreativeTab(Femtocraft.femtocraftTab)

  @SideOnly(Side.CLIENT) override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer, par3List: util.List[_], par4: Boolean) {
    super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
    if (hasItemNBT) {
      var nbt = par1ItemStack.getTagCompound
      if (nbt == null) {
        par1ItemStack.stackTagCompound = new NBTTagCompound
        nbt = par1ItemStack.stackTagCompound
      }
      val owner = nbt.getString("owner")
      val ownerLabelString = EnumChatFormatting.GRAY + "Owner:" + EnumChatFormatting.RESET
      var ownerString: String = null
      if (owner.isEmpty) {
        ownerString = EnumChatFormatting.ITALIC + "unassigned" + EnumChatFormatting.RESET
      }
      else {
        ownerString = owner
      }
      par3List.asInstanceOf[util.List[String]].add(String.format("%s %s", ownerLabelString, ownerString))
    }
  }

  /**
   * @return true if this block, when in item form, should have NBTData. If you want this block to be stackable in
   *         item form, this must return false. Otherwise, Femtocraft will addInput NBT data automatically.
   */
  def hasItemNBT = true

  override def func_150936_a(world: World, x: Int, y: Int, z: Int, side: Int, player: EntityPlayer, itemStack: ItemStack) = canPlayerPlace(player) && super.func_150936_a(world, x, y, z, side, player, itemStack)

  override def placeBlockAt(stack: ItemStack, player: EntityPlayer, world: World, x: Int, y: Int, z: Int, side: Int, hitX: Float, hitY: Float, hitZ: Float, metadata: Int) = canPlayerPlace(player) && super.placeBlockAt(stack, player, world, x, y, z, side, hitX, hitY, hitZ, metadata)

  private def canPlayerPlace(player: EntityPlayer) = player != null
}
