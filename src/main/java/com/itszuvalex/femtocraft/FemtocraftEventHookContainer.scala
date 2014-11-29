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
package com.itszuvalex.femtocraft

import com.itszuvalex.femtocraft.api.items.ItemAssemblySchematic
import com.itszuvalex.femtocraft.common.gui.DisplaySlot
import com.itszuvalex.femtocraft.core.MagnetRegistry
import com.itszuvalex.femtocraft.player.PlayerProperties
import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumChatFormatting
import net.minecraftforge.client.event.TextureStitchEvent
import net.minecraftforge.event.entity.player.ItemTooltipEvent
import net.minecraftforge.event.entity.{EntityEvent, EntityJoinWorldEvent}
import net.minecraftforge.event.world.WorldEvent

class FemtocraftEventHookContainer {
  @SubscribeEvent def load(event: WorldEvent.Load) {
    if (event.world.isRemote) return

    Femtocraft.researchManager.load(event.world)
    Femtocraft.assistantManager.load(event.world)
  }

  @SubscribeEvent def save(event: WorldEvent.Save) {
    if (event.world.isRemote) return

    Femtocraft.researchManager.save(event.world)
    Femtocraft.assistantManager.save(event.world)
  }

  @SideOnly(value = Side.CLIENT)
  @SubscribeEvent def preTextureStitch(event: TextureStitchEvent.Pre) {
    if (event.map.getTextureType == 1) {
      ItemAssemblySchematic.placeholderIcon = event.map.registerIcon(Femtocraft.ID.toLowerCase + ":" + "ItemAssemblySchematic")
      DisplaySlot.noPlaceDisplayIcon = event.map.registerIcon(Femtocraft.ID.toLowerCase + ":" + "NoPlaceSlot")
    }
  }

  @SubscribeEvent def onEntityConstructing(event: EntityEvent.EntityConstructing) {
    event.entity match {
      case player: EntityPlayer if event.entity.getExtendedProperties(PlayerProperties.PROP_TAG) == null =>
        PlayerProperties.register(player)
      case _                                                                                             =>
    }
  }

  @SubscribeEvent def onEntityJoinWorld(event: EntityJoinWorldEvent) {
    event.entity match {
      case player: EntityPlayer if !event.entity.worldObj.isRemote =>
        PlayerProperties.get(player).sync()
      case _                                                       =>
    }
  }

  @SubscribeEvent def onTooltip(event: ItemTooltipEvent) {
    if (MagnetRegistry.showMagnetismTooltip && MagnetRegistry.isMagnet(event.itemStack) && !MagnetRegistry.magnetismTooltipIsAdvanced || (MagnetRegistry.magnetismTooltipIsAdvanced && event.showAdvancedItemTooltips)) {
      event.toolTip.add(EnumChatFormatting.GOLD + "Magnet Strength: " + EnumChatFormatting.RESET + String.valueOf(MagnetRegistry.getMagnetStrength(event.itemStack)))
    }
  }
}
