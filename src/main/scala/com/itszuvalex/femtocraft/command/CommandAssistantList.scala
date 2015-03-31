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
package com.itszuvalex.femtocraft.command

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.util.EnumChatFormatting

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
class CommandAssistantList extends CommandBase("list", null) {

  override def getDescription = "List all of your assistants."

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) {
    icommandsender match {
      case player: EntityPlayer =>
        val uuid = player.getUniqueID
        FemtocraftUtils.sendMessageToPlayer(player, EnumChatFormatting.YELLOW + "Assistants:")
        val assist = Femtocraft.assistantManager.getPlayerAssistants(uuid)
        if (assist == null || assist.isEmpty) {
          FemtocraftUtils.sendMessageToPlayer(player, "None.")
        }
        else {
          for (assistant <- assist) {
            FemtocraftUtils.sendMessageToPlayer(player, Femtocraft.uuidManager.getUsername(assistant))
          }
        }
      case _ =>
    }
  }
}
