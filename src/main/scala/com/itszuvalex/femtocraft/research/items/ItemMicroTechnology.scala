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

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.research.ItemTechnologyCarrier
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.renderer.texture.IIconRegister
import net.minecraft.item.ItemStack

class ItemMicroTechnology extends ItemTechnologyCarrier("ItemMicroTechnology") {
  setCreativeTab(Femtocraft.femtocraftTab)
  setUnlocalizedName("itemMicroTechnology")

  def getTechnologyLevel(stack: ItemStack) = EnumTechLevel.MICRO

  @SideOnly(Side.CLIENT) override def registerIcons(par1IconRegister: IIconRegister) {
    itemIcon = par1IconRegister.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ItemMicroTechnology")
  }
}
