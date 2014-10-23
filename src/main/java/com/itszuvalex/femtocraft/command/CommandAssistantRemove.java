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

package com.itszuvalex.femtocraft.command;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.utils.FemtocraftUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
public class CommandAssistantRemove extends CommandBase {
    public CommandAssistantRemove() {
        super("remove", null);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
        if (icommandsender instanceof EntityPlayer) {
            return new ArrayList(Femtocraft.assistantManager().getPlayerAssistants(icommandsender
                    .getCommandSenderName()).keySet());
        }
        return Arrays.asList(MinecraftServer.getServer().getAllUsernames());
    }

    @Override
    public String getDescription() {
        return "Remove a player as an assistant.";
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "remove [username]";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (icommandsender instanceof EntityPlayer) {
            if (astring.length != 1) {
                throw new WrongUsageException(getCommandUsage(icommandsender));
            }
            String username = icommandsender.getCommandSenderName();
            if (Femtocraft.assistantManager().removeAssistantFrom(username, astring[0])) {
                FemtocraftUtils.sendMessageToPlayer(username, astring[0] + " successfully removed as assistant!");
                FemtocraftUtils.sendMessageToPlayer(astring[0], username + " just removed you as his assistant!");
            } else {
                FemtocraftUtils.sendMessageToPlayer(username,
                        EnumChatFormatting.RED + "Error removing " + astring[0] + " as assistant!"
                );
            }
        }
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }
}
