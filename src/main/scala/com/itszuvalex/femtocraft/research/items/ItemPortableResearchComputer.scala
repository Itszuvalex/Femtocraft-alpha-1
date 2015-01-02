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
package com.itszuvalex.femtocraft.research.items

import com.itszuvalex.femtocraft.core.items.ItemBase
import com.itszuvalex.femtocraft.{Femtocraft, FemtocraftGuiConstants}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

/**
 * Created by Christopher Harris (Itszuvalex) on 5/5/14.
 */
class ItemPortableResearchComputer extends ItemBase("ItemPortableResearchComputer") {
  setCreativeTab(Femtocraft.femtocraftTab)
  setMaxStackSize(1)
  setUnlocalizedName("ItemPortableResearchComputer")

  override def onItemRightClick(par1ItemStack: ItemStack, par2World: World, par3EntityPlayer: EntityPlayer): ItemStack = {
    par3EntityPlayer.openGui(Femtocraft, FemtocraftGuiConstants.ResearchComputerGuiID, par2World, par3EntityPlayer.posX.toInt, par3EntityPlayer.posY.toInt, par3EntityPlayer.posZ.toInt)
    par1ItemStack
  }

  override def registerIcons(par1IconRegister: IIconRegister) {
    itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ItemPortableResearchComputer")
  }
}
