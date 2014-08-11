package com.itszuvalex.femtocraft.command;

public class CommandFemtocraft extends CommandBase {
    public static final CommandFemtocraft instance = new CommandFemtocraft();

    private CommandFemtocraft() {
        super("femtocraft", new String[]{"femto", "fc"});
        this.addSubCommand(new CommandAssistant());
    }
}
