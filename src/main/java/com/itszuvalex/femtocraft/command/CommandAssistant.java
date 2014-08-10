package com.itszuvalex.femtocraft.command;

import com.itszuvalex.femtocraft.Femtocraft;
import com.itszuvalex.femtocraft.managers.assistant.AssistantPermissions;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CommandAssistant implements ISubCommand {
    private List<String> aliases;
    private static String help = EnumChatFormatting.YELLOW + "/femtocraft assistant usage:\n" +
            "help - Displays this message." +
            "add <player> - Adds a player to your assistants.\n" +
            "remove <player> - Removes a player from your assistants.\n" +
            "list - Lists your current assistants." +
            "clear - Clears all of your assistants" + EnumChatFormatting.RESET;

    public CommandAssistant() {
        this.aliases = new ArrayList<String>();
        this.aliases.add("assistants");
        this.aliases.add("assistant");
        this.aliases.add("assist");
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        // Command only valid for players
        if (sender instanceof EntityPlayer) {
            String username = ((EntityPlayer) sender).username;
            if (args.length == 1 || (args.length == 2 && args[1].equalsIgnoreCase("list"))) {
                StringBuilder str = new StringBuilder(EnumChatFormatting.YELLOW + "Assistants:\n");
                Map<String, AssistantPermissions> assist = Femtocraft.assistantManager.getPlayerAssistants(username);
                if (assist == null || assist.isEmpty()) {
                    str.append("None.");
                }
                else {
                    for (String assistant : assist.keySet()) {
                        str.append(assistant + "\n");
                    }
                }
                str.append(EnumChatFormatting.RESET);
                sender.sendChatToPlayer(ChatMessageComponent.createFromText(str.toString()));
            }
            else if (args.length == 2) {
                if (args[1].equalsIgnoreCase("help")) {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText(help));
                }
                else {
                    throw new WrongUsageException(help);
                }
            }
            else if (args.length == 3) {
                if (args[1].equalsIgnoreCase("add")) {
                    if (Femtocraft.assistantManager.addAssistantTo(username, args[2])) {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + args[2] + " successfully added!" + EnumChatFormatting.RESET));
                    }
                    else {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Error adding " + args[2] + " as assistant!" + EnumChatFormatting.RESET));
                    }
                }
                else if (args[1].equalsIgnoreCase("remove")) {
                    if (Femtocraft.assistantManager.removeAssistantFrom(username, args[2])) {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.YELLOW + args[2] + " successfully removed!" + EnumChatFormatting.RESET));
                    }
                    else {
                        sender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Error removing " + args[2] + " as assistant!" + EnumChatFormatting.RESET));
                    }
                }
                else {
                    throw new WrongUsageException(help);
                }
            }
            else {
                throw new WrongUsageException(help);
            }
        }
        else {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText(EnumChatFormatting.RED + "Command only valid for players." + EnumChatFormatting.RESET));
        }
    }
}
