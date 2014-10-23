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

import java.util.Arrays;
import java.util.List;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
public class CommandAssistantAdd extends CommandBase {
    public CommandAssistantAdd() {
        super("add", null);
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
        return Arrays.asList(MinecraftServer.getServer().getAllUsernames());
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return i == 0;
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return "add [username]";
    }

    @Override
    public String getDescription() {
        return "Add a player as an assistant";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (icommandsender instanceof EntityPlayer) {
            if (astring.length != 1) {
                throw new WrongUsageException(getCommandUsage(icommandsender));
            }
            String username = icommandsender.getCommandSenderName();
            if (Femtocraft.assistantManager().addAssistantTo(username, astring[0])) {
                FemtocraftUtils.sendMessageToPlayer(username, astring[0] + " successfully added as assistant!");
                FemtocraftUtils.sendMessageToPlayer(astring[0], username + " just added you as an assistant!");
            } else {
                FemtocraftUtils.sendMessageToPlayer(username,
                        EnumChatFormatting.RED + "Error adding " + astring[0] + " as assistant!"
                );
            }
        }
    }
}
