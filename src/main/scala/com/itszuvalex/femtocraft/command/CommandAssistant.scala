package com.itszuvalex.femtocraft.command

class CommandAssistant extends CommandBase("assistants", Array[String]("assistant", "assist")) {
  addSubCommand(new CommandAssistantList)
  addSubCommand(new CommandAssistantAdd)
  addSubCommand(new CommandAssistantRemove)

  override def getDescription = "Used for managing assistants, those capable of accessing your machines."
}
