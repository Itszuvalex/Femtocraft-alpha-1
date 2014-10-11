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
        subcmds = new HashMap<String, ICommand>();
    }

    public boolean addSubCommand(ICommand subcommand) {
        subcmds.put(subcommand.getCommandName(), subcommand);
        return true;
    }

    public String getDescription() {
        return "";
    }


    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        StringBuilder output = new StringBuilder();
        output.append(EnumChatFormatting.YELLOW);
        output.append(getCommandName());
        output.append('\n');
        output.append(EnumChatFormatting.BOLD).append("aliases\n").append(EnumChatFormatting.RESET).append
                (EnumChatFormatting.YELLOW);
        for (String alias : aliases) {
            output.append(alias);
            output.append('\n');
        }
        output.append(EnumChatFormatting.BOLD).append("subcommands\n").append(EnumChatFormatting.RESET).append
                (EnumChatFormatting.YELLOW);
        for (String subcommand : subcmds.keySet()) {
            ICommand com = getSubCommand(subcommand);
            output.append(EnumChatFormatting.RED).append(subcommand).append(EnumChatFormatting.YELLOW);
            for (Object alias : com.getCommandAliases()) {
                output.append(EnumChatFormatting.BLUE).append("|").append(EnumChatFormatting.GRAY);
                output.append((String) alias);
            }
            if (com instanceof CommandBase) {
                output.append(EnumChatFormatting.WHITE).append(" - ").append(((CommandBase) com).getDescription())
                        .append(EnumChatFormatting.YELLOW);
            }
            output.append('\n');
        }
        output.deleteCharAt(output.length() - 1);
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
            ICommand subcommand = getSubCommand(astring[0]);
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
            ICommand subcommand = getSubCommand(astring[0]);
            if (subcommand != null) {
                return subcommand.addTabCompletionOptions(icommandsender, Arrays.copyOfRange(astring, 1,
                        astring.length));
            }
        }
        return new ArrayList(subcmds.keySet());
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        if (astring.length > 0) {
            ICommand subcommand = getSubCommand(astring[0]);
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

    ICommand getSubCommand(String name) {
        ICommand sub = subcmds.get(name);
        if (sub != null) return sub;

        for (ICommand subc : subcmds.values()) {
            for (Object alias : subc.getCommandAliases()) {
                if (((String) alias).compareToIgnoreCase(name) == 0) {
                    return subc;
                }
            }
        }
        return null;
    }

}
