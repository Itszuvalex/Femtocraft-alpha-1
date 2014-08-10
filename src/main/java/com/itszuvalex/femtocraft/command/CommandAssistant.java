package com.itszuvalex.femtocraft.command;

import com.itszuvalex.femtocraft.Femtocraft;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;

import java.util.List;
import java.util.ArrayList;

public class CommandAssistant implements ISubCommand {
    private List aliases;
    private static String help = "/femtocraft assistant usage:\n" +
            "help - Displays this message." +
            "add <player> - Adds a player to your assistants.\n" +
            "remove <player> - Removes a player from your assistants.\n" +
            "list - Lists your current assistants." +
            "clear - Clears all of your assistants";

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
        if(sender instanceof EntityPlayer) {
            String username = ((EntityPlayer) sender).username;
            if(args.length == 1 || (args.length == 2 && args[1].equalsIgnoreCase("list"))) {
                StringBuilder str = new StringBuilder("Assistants:\n");
                List<String> assist = Femtocraft.assistantManager.getPlayerAssistants(username);
                if(assist == null || assist.isEmpty()) {
                    str.append("None.");
                } else {
                    for(String assistant : assist) {
                        str.append(assistant + "\n");
                    }
                }
                sender.sendChatToPlayer(ChatMessageComponent.createFromText(str.toString()));
            } else if(args.length == 2) {
                if(args[1].equalsIgnoreCase("help")) {
                    sender.sendChatToPlayer(ChatMessageComponent.createFromText(help));
                } else {
                    throw new WrongUsageException(help);
                }
            } else if(args.length == 3) {
                if(args[1].equalsIgnoreCase("add")) {
                    System.out.print(args[2]);
                    Femtocraft.assistantManager.addAssistantTo(username,args[2]);
                } else if(args[1].equalsIgnoreCase("remove")) {
                    Femtocraft.assistantManager.removeAssistantFrom(username,args[2]);
                } else {
                    throw new WrongUsageException(help);
                }
            } else {
                throw new WrongUsageException(help);
            }
        } else {
            sender.sendChatToPlayer(ChatMessageComponent.createFromText("Command only valid for players."));
        }
    }
}
