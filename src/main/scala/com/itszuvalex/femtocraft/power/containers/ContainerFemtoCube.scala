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
package com.itszuvalex.femtocraft.power.containers

import com.itszuvalex.femtocraft.core.container.ContainerBase
import com.itszuvalex.femtocraft.power.tiles.TileEntityFemtoCubePort
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ICrafting

import scala.collection.JavaConversions._

class ContainerFemtoCube(private val controller: TileEntityFemtoCubePort) extends ContainerBase {
  private var lastPower: Int = 0

  override def addCraftingToCrafters(par1iCrafting: ICrafting) {
    super.addCraftingToCrafters(par1iCrafting)
    sendUpdateToCrafter(this, par1iCrafting, 0, controller.getCurrentPower)
  }

  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    val power: Int = controller.getCurrentPower

    for (crafter <- crafters) {
      val icrafting: ICrafting = crafter.asInstanceOf[ICrafting]
      if (lastPower != power) {
        sendUpdateToCrafter(this, icrafting, 0, power)
      }
    }
    lastPower = power
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    super.updateProgressBar(par1, par2)
    if (par1 == 0) {
      controller.setCurrentStorage(par2)
    }
  }

  def canInteractWith(entityplayer: EntityPlayer): Boolean = controller.canPlayerUse(entityplayer)
}
