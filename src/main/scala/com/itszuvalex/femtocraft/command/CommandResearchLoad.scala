package com.itszuvalex.femtocraft.command

import com.itszuvalex.femtocraft.Femtocraft
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearchLoad extends CommandBase("load", null) {

  override def getDescription = "Loads research from file.  WARNING:  MAY ROLLBACK PROGRESS"

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) {
    if (icommandsender.isInstanceOf[EntityPlayer]) {
      Femtocraft.researchManager.getPlayerResearch(icommandsender.getCommandSenderName).load()
    }
  }
}
