package com.itszuvalex.femtocraft.command

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearch extends CommandBase("research", null) {
  addSubCommand(new CommandResearchSave)
  addSubCommand(new CommandResearchLoad)
  addSubCommand(new CommandResearchSync)
  addSubCommand(new CommandResearchResearch)
  addSubCommand(new CommandResearchDiscover)

  override def getDescription = "Used for managing player research."
}
