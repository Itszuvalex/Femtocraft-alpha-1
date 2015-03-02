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
package com.itszuvalex.femtocraft.industry.containers

import com.itszuvalex.femtocraft.api.industry.ILaserModulator
import com.itszuvalex.femtocraft.core.container.ContainerInv
import com.itszuvalex.femtocraft.industry.tiles.TileEntityModulationLensMount
import net.minecraft.entity.player.{EntityPlayer, InventoryPlayer}
import net.minecraft.inventory.Slot
import net.minecraft.item.ItemStack

/**
 * Created by Christopher Harris (Itszuvalex) on 7/27/14.
 */
class ContainerModulationLensMount[T <: TileEntityModulationLensMount](player: EntityPlayer, par1InventoryPlayer: InventoryPlayer, lensMount: T) extends ContainerInv[T](player, lensMount, 0, 0) {
  addSlotToContainer(new Slot(lensMount, 0, 80, 34))
  addPlayerInventorySlots(par1InventoryPlayer)


  def eligibleForInput(item: ItemStack) = item == null || item.getItem.isInstanceOf[ILaserModulator]
}
