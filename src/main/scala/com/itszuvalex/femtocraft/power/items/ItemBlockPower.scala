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
package com.itszuvalex.femtocraft.power.items

import java.util

import com.itszuvalex.femtocraft.api.implicits.SugarImplicits._
import com.itszuvalex.femtocraft.api.power.{PowerContainer, PowerStackWrapper}
import com.itszuvalex.femtocraft.core.items.CoreItemBlock
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.ItemStack
import net.minecraft.world.World

abstract class ItemBlockPower(block: Block) extends CoreItemBlock(block) {

  @SideOnly(Side.CLIENT) override def addInformation(par1ItemStack: ItemStack, par2EntityPlayer: EntityPlayer,
                                                     par3List: util.List[_], par4: Boolean) {
    super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4)
    val wrapper = PowerStackWrapper.getWrapperForStack(par1ItemStack, false)
    if (wrapper.hasPowerData) {
      wrapper.addInformationToTooltip(par3List)
    } else {
      getDefaultContainer.addInformationToTooltip(par3List)
    }
  }

  def getDefaultContainer: PowerContainer

  override def onCreated(par1ItemStack: ItemStack, par2World: World, par3EntityPlayer: EntityPlayer) {
    super.onCreated(par1ItemStack, par2World, par3EntityPlayer)
    val wrapper = PowerStackWrapper.getWrapperForStack(par1ItemStack)
    wrapper DoIfNotNull wrapper.copyFromPowerContainer(getDefaultContainer)
  }
}
