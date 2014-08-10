package com.itszuvalex.femtocraft.command;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.lang.StringBuilder;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.command.WrongUsageException;

public class CommandHandler implements ICommand {
    private List aliases;
    private HashMap<String,ISubCommand> subcmds;

    public static final CommandHandler instance = new CommandHandler();

    // Registers a sub-command
    public void registerSubCommand(ISubCommand command) {
        // Add all names of a sub-command
        List<String> alias = command.getCommandAliases();
        int nAlias = alias.size();
        for(int i=0;i<nAlias;i++) {
            this.subcmds.put(alias.get(i), command);
        }
    }

    public CommandHandler() {
        this.aliases = new ArrayList();
        this.aliases.add("femto");
        this.aliases.add("fc");

        this.subcmds = new HashMap<String,ISubCommand>();
        registerSubCommand(new CommandAssistant());
    }

    @Override
    public String getCommandName() {
        return "femtocraft";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "femtocraft <subcommand>";
    }

    @Override
    public List getCommandAliases() {
        return this.aliases;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if(args.length > 0) {
            // Sub-command reroute to sub-command handler
            if (this.subcmds.containsKey(args[0])) {
                System.out.println("Has command");
                this.subcmds.get(args[0]).processCommand(sender, args);
            }
        } else {
            // Empty argument list. Display sub-commands
            StringBuilder output = new StringBuilder("Sub-command list:\n");
            for (Map.Entry<String,ISubCommand> entry: this.subcmds.entrySet()) {
                String key = entry.getKey();
                System.out.print(key + "\n");
                output.append(key + "\n");
            }
            sender.sendChatToPlayer(ChatMessageComponent.createFromText(output.toString()));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }

    @Override
    public List addTabCompletionOptions(ICommandSender sender, String[] args) {
        return null;
    }

    @Override
    public boolean isUsernameIndex(String[] astring, int i) {
        return false;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
