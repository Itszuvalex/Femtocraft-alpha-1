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
import com.itszuvalex.femtocraft.managers.assistant.AssistantPermissions;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

import java.util.Map;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
public class CommandAssistantList extends CommandBase {
    public CommandAssistantList() {
        super("list", null);
    }

    @Override
    public String getDescription() {
        return "List all of your assistants.";
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (icommandsender instanceof EntityPlayer) {
            String username = ((EntityPlayer) icommandsender).username;
            StringBuilder str = new StringBuilder(EnumChatFormatting.YELLOW + "Assistants:\n");
            Map<String, AssistantPermissions> assist = Femtocraft.assistantManager().getPlayerAssistants(username);
            if (assist == null || assist.isEmpty()) {
                str.append("None.");
            } else {
                for (String assistant : assist.keySet()) {
                    str.append(assistant).append("\n");
                }
            }
            str.append(EnumChatFormatting.RESET);
            icommandsender.sendChatToPlayer(ChatMessageComponent.createFromText(str.toString()));
        }
    }
}
