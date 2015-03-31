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

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.command.{ICommandSender, WrongUsageException}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.EnumChatFormatting

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
class CommandAssistantAdd extends CommandBase("add", null) {

  override def addTabCompletionOptions(icommandsender: ICommandSender,
                                       astring: Array[String]): util.List[_] = MinecraftServer
                                                                               .getServer
                                                                               .getAllUsernames
                                                                               .toList

  override def isUsernameIndex(astring: Array[String], i: Int) = i == 0

  override def getDescription = "Add a player as an assistant"

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) {
    icommandsender match {
      case player: EntityPlayer =>
        if (astring.length != 1) {
          throw new WrongUsageException(getCommandUsage(icommandsender))
        }
        val uuid = player.getUniqueID
        val UUID = Femtocraft.uuidManager.getUUID(astring(0))
        if (UUID == null) {
          FemtocraftUtils.sendMessageToPlayer(player, "Player: " + astring(0) + " not found.")
          return
        }
        Femtocraft.assistantManager.addAssistantTo(uuid, UUID)
        if (Femtocraft.assistantManager.isPlayerAssistant(uuid, UUID)) {
          FemtocraftUtils.sendMessageToPlayer(player, astring(0) + " successfully added as assistant!")
          FemtocraftUtils.sendMessageToPlayer(astring(0), player.getCommandSenderName + " just added you as an assistant!")
        } else {
          FemtocraftUtils
          .sendMessageToPlayer(player, EnumChatFormatting.RED + "Error adding " + astring(0) + " as assistant!")
        }
      case _ =>
    }
  }

  override def getCommandUsage(icommandsender: ICommandSender) = "add [username]"
}
