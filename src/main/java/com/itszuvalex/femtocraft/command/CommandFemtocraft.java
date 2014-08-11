package com.itszuvalex.femtocraft.command;

public class CommandFemtocraft extends CommandBase {
    public CommandFemtocraft() {
        super("femtocraft", new String[]{"femto", "fc"});
        this.addSubCommand(new CommandAssistant());
    }
}
