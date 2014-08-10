package com.itszuvalex.femtocraft.command;

import net.minecraft.command.ICommandSender;

import java.util.List;

// Sub-command interface
// Keep in mind that argument 0 for sub-commands is the command's name
// The sub-command's arguments start at 1
public interface ISubCommand {
    public List getCommandAliases();
    public void processCommand(ICommandSender player, String[] args);
}
