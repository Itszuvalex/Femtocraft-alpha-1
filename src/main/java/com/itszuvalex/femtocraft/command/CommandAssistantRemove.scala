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
import com.itszuvalex.femtocraft.utils.FemtocraftUtils
import net.minecraft.command.{ICommandSender, WrongUsageException}
import net.minecraft.entity.player.EntityPlayer
import net.minecraft.server.MinecraftServer
import net.minecraft.util.EnumChatFormatting

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
class CommandAssistantRemove extends CommandBase("remove", null) {

  override def addTabCompletionOptions(icommandsender: ICommandSender, astring: Array[String]): util.List[_] = {
    if (icommandsender.isInstanceOf[EntityPlayer]) {
      return new util.ArrayList[String](Femtocraft.assistantManager.getPlayerAssistants(icommandsender.getCommandSenderName).keySet)
    }
    util.Arrays.asList(MinecraftServer.getServer.getAllUsernames)
  }

  override def getDescription = "Remove a player as an assistant."

  override def getCommandUsage(icommandsender: ICommandSender) = "remove [username]"

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) {
    if (icommandsender.isInstanceOf[EntityPlayer]) {
      if (astring.length != 1) {
        throw new WrongUsageException(getCommandUsage(icommandsender))
      }
      val username = icommandsender.getCommandSenderName
      if (Femtocraft.assistantManager.removeAssistantFrom(username, astring(0))) {
        FemtocraftUtils.sendMessageToPlayer(username, astring(0) + " successfully removed as assistant!")
        FemtocraftUtils.sendMessageToPlayer(astring(0), username + " just removed you as his assistant!")
      }
      else {
        FemtocraftUtils.sendMessageToPlayer(username, EnumChatFormatting.RED + "Error removing " + astring(0) + " as assistant!")
      }
    }
  }

  override def isUsernameIndex(astring: Array[String], i: Int) = i == 0
}
