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
import com.itszuvalex.femtocraft.power.containers.ContainerDecontaminationChamber._
import com.itszuvalex.femtocraft.power.tiles.TileEntityDecontaminationChamber
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ICrafting

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 8/27/14.
 */
object ContainerDecontaminationChamber {
  private val powerIndex            = 0
  private val cooledSaltIndex       = 1
  private val contaminatedSaltIndex = 2
}

class ContainerDecontaminationChamber(private val chamber: TileEntityDecontaminationChamber) extends ContainerBase {
  private var lastPower            = 0
  private var lastMoltenSalt       = 0
  private var lastContaminatedSalt = 0

  override def addCraftingToCrafters(par1iCrafting: ICrafting) {
    super.addCraftingToCrafters(par1iCrafting)
    sendUpdateToCrafter(this, par1iCrafting, powerIndex, chamber.getCurrentPower)
    sendUpdateToCrafter(this, par1iCrafting, cooledSaltIndex, chamber.getCooledSaltTank.getFluidAmount)
    sendUpdateToCrafter(this, par1iCrafting, contaminatedSaltIndex, chamber.getContaminatedSaltTank.getFluidAmount)
  }

  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    val power = chamber.getCurrentPower
    val moltenSalt = chamber.getCooledSaltTank.getFluidAmount
    val contaminatedSalt = chamber.getContaminatedSaltTank.getFluidAmount

    for (crafter <- this.crafters) {
      val icrafting: ICrafting = crafter.asInstanceOf[ICrafting]
      if (lastPower != power) {
        sendUpdateToCrafter(this, icrafting, powerIndex, power)
      }
      if (lastMoltenSalt != moltenSalt) {
        sendUpdateToCrafter(this, icrafting, cooledSaltIndex, moltenSalt)
      }
      if (lastContaminatedSalt != contaminatedSalt) {
        sendUpdateToCrafter(this, icrafting, contaminatedSaltIndex, contaminatedSalt)
      }
    }
    lastPower = power
    lastMoltenSalt = moltenSalt
    lastContaminatedSalt = contaminatedSalt
  }

  @SideOnly(Side.CLIENT) override def updateProgressBar(par1: Int, par2: Int) {
    super.updateProgressBar(par1, par2)
    par1 match {
      case `powerIndex`            => chamber.setCurrentStorage(par2)
      case `cooledSaltIndex`       => chamber.setCooledSalt(par2)
      case `contaminatedSaltIndex` => chamber.setContaminatedSalt(par2)
      case _                       =>
    }
  }

  def canInteractWith(entityplayer: EntityPlayer): Boolean = chamber.canPlayerUse(entityplayer)
}
