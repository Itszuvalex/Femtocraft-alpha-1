package com.itszuvalex.femtocraft.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.EnumChatFormatting;

public class CommandAssistant extends CommandBase {
    private static String help = EnumChatFormatting.YELLOW + "/femtocraft assistant usage:\n" +
            "help - Displays this message." +
            "add <player> - Adds a player to your assistants.\n" +
            "remove <player> - Removes a player from your assistants.\n" +
            "list - Lists your current assistants." +
            "clear - Clears all of your assistants" + EnumChatFormatting.RESET;

    public CommandAssistant() {
        super("assistants", new String[]{"assistant", "assist"});
        addSubCommand(new CommandAssistantList());
        addSubCommand(new CommandAssistantAdd());
        addSubCommand(new CommandAssistantRemove());
    }

    @Override
    public String getCommandUsage(ICommandSender icommandsender) {
        return help;
    }
}
