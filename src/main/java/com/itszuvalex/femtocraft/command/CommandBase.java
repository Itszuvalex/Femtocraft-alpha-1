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

import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.EnumChatFormatting;

import java.util.*;

/**
 * Created by Christopher Harris (Itszuvalex) on 8/10/14.
 */
public abstract class CommandBase implements ICommand {
    protected final List<String> aliases;
    protected final Map<String, ICommand> subcmds;
    protected final String name;

    public CommandBase(String name, String[] aliases) {
        this.aliases = aliases == null ? new ArrayList<String>() : Arrays.asList(aliases);
        this.name = name;
        subcmds = new HashMap<>();
    }

    public boolean addSubCommand(ICommand subcommand) {
        subcmds.put(subcommand.getCommandName(), subcommand);
        for (Object alias : subcommand.getCommandAliases()) {
            subcmds.put((String) alias, subcommand);
        }
        return true;
    }


    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        StringBuilder output = new StringBuilder(EnumChatFormatting.YELLOW + getCommandName() + ": subcommands\n");
        for (String subcommand : subcmds.keySet()) {
            output.append(subcommand + "\n");
        }
        output.append(EnumChatFormatting.RESET);
        return output.toString();
    }

    @Override
    public List getCommandAliases() {
        return aliases;
    }

    @Override
    public void processCommand(ICommandSender icommandsender, String[] astring) {
        if (astring.length > 0) {
            ICommand subcommand = subcmds.get(astring[0]);
            if (subcommand != null) {
                subcommand.processCommand(icommandsender, Arrays.copyOfRange(astring, 1, astring.length));
                return;
            }
        }
        throw new WrongUsageException(getCommandUsage(icommandsender));
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender icommandsender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender icommandsender, String[] astring) {
        if (astring.length > 0) {
            ICommand subcommand = subcmds.get(astring[0]);
            if (subcommand != null) {
                return subcommand.addTabCompletionOptions(icommandsender, Arrays.copyOfRange(astring, 1, astring.length));
            }
        }
        return new ArrayList(subcmds.keySet());
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        if (astring.length > 0) {
            ICommand subcommand = subcmds.get(astring[0]);
            if (subcommand != null) {
                return subcommand.isUsernameIndex(Arrays.copyOfRange(astring, 1, astring.length), i - 1);
            }
        }
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return getCommandName().compareTo(((ICommand) o).getCommandName());
    }
}
