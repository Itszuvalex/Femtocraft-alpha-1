package com.itszuvalex.femtocraft.command

class CommandFemtocraft extends CommandBase("femtocraft", Array[String]("femto", "fc")) {
  addSubCommand(new CommandAssistant)
  addSubCommand(new CommandResearch)
}
