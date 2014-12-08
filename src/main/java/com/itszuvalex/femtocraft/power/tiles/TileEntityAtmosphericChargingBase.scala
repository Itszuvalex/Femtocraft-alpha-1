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
package com.itszuvalex.femtocraft.power.tiles

import com.itszuvalex.femtocraft.api.EnumTechLevel
import com.itszuvalex.femtocraft.api.core.Saveable
import com.itszuvalex.femtocraft.api.power.{IAtmosphericChargingAddon, IAtmosphericChargingBase, PowerContainer}
import com.itszuvalex.femtocraft.core.tiles.TileEntityBase
import com.itszuvalex.femtocraft.power.traits.PowerProducer
import net.minecraft.block.Block
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.item.{ItemBlock, ItemStack}
import net.minecraftforge.common.util.ForgeDirection

class TileEntityAtmosphericChargingBase extends TileEntityBase with PowerProducer {
  var numCoils             = 0
  var powerPerTick         = 0f
  @Saveable
  var storedPowerIncrement = 0f

  def defaultContainer = new PowerContainer(EnumTechLevel.MICRO, 250)

  override def femtocraftServerUpdate() {
    super.femtocraftServerUpdate()
    numCoils = 0
    powerPerTick = 0
    val base = worldObj.getBlock(xCoord, yCoord, zCoord)
    var searching = true

    var i: Int = 0
    while (searching && (i < base.asInstanceOf[IAtmosphericChargingBase].maxAddonsSupported(worldObj, xCoord, yCoord, zCoord))) {

      val block: Block = worldObj.getBlock(xCoord, yCoord + i + 1, zCoord)
      if (!block.isInstanceOf[IAtmosphericChargingAddon]) {
        searching = false
      }
      else {
        val addon = block.asInstanceOf[IAtmosphericChargingAddon]
        powerPerTick += addon.powerPerTick(worldObj, xCoord, yCoord + i + 1, zCoord)
        numCoils += 1
      }
      i += 1
    }

    storedPowerIncrement += powerPerTick
    while (storedPowerIncrement > 1.0f) {
      storedPowerIncrement -= 1.0f
      charge(ForgeDirection.UNKNOWN, 1)
    }
  }

  override def canConnect(from: ForgeDirection) = from != ForgeDirection.UP

  override def onSideActivate(par5EntityPlayer: EntityPlayer, side: Int): Boolean = {
    if (!canPlayerUse(par5EntityPlayer)) {
      return false
    }
    val item: ItemStack = par5EntityPlayer.getHeldItem
    if (item != null && item.getItem.isInstanceOf[ItemBlock] && Block.getBlockFromItem(item.getItem).isInstanceOf[IAtmosphericChargingAddon]) {
      return true
    }
    super.onSideActivate(par5EntityPlayer, side)
  }
}
