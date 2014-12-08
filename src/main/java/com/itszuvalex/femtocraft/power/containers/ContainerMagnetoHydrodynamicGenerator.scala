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
import com.itszuvalex.femtocraft.power.containers.ContainerMagnetoHydrodynamicGenerator._
import com.itszuvalex.femtocraft.power.tiles.TileEntityMagnetohydrodynamicGenerator
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.inventory.ICrafting

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 8/27/14.
 */
object ContainerMagnetoHydrodynamicGenerator {
  private val powerIndex            = 0
  private val moltenSaltIndex       = 1
  private val contaminatedSaltIndex = 2
}

class ContainerMagnetoHydrodynamicGenerator(private val generator: TileEntityMagnetohydrodynamicGenerator) extends ContainerBase {
  private var lastPower            = 0
  private var lastMoltenSalt       = 0
  private var lastContaminatedSalt = 0

  override def addCraftingToCrafters(par1iCrafting: ICrafting) {
    super.addCraftingToCrafters(par1iCrafting)
    sendUpdateToCrafter(this, par1iCrafting, powerIndex, generator.getCurrentPower)
    sendUpdateToCrafter(this, par1iCrafting, moltenSaltIndex, generator.getMoltenSaltTank.getFluidAmount)
    sendUpdateToCrafter(this, par1iCrafting, contaminatedSaltIndex, generator.getContaminatedSaltTank.getFluidAmount)
  }

  override def detectAndSendChanges() {
    super.detectAndSendChanges()
    val power = generator.getCurrentPower
    val moltenSalt = generator.getMoltenSaltTank.getFluidAmount
    val contaminatedSalt = generator.getContaminatedSaltTank.getFluidAmount

    for (crafter <- this.crafters) {
      val icrafting: ICrafting = crafter.asInstanceOf[ICrafting]
      if (lastPower != power) {
        sendUpdateToCrafter(this, icrafting, powerIndex, power)
      }
      if (lastMoltenSalt != moltenSalt) {
        sendUpdateToCrafter(this, icrafting, moltenSaltIndex, moltenSalt)
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
      case `powerIndex`            => generator.setCurrentStorage(par2)
      case `moltenSaltIndex`       => generator.setMoltenSalt(par2)
      case `contaminatedSaltIndex` => generator.setContaminatedSalt(par2)
      case _                       =>
    }
  }

  def canInteractWith(entityplayer: EntityPlayer): Boolean = generator.canPlayerUse(entityplayer)
}
