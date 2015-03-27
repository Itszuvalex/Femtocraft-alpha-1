package com.itszuvalex.femtocraft.command

import com.itszuvalex.femtocraft.Femtocraft
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearchSave extends CommandBase("save", null) {

  override def getDescription = "Forces player research to write to disc."

  override def processCommand(iCommandSender: ICommandSender, astring: Array[String]): Unit = {
    if (iCommandSender.isInstanceOf[EntityPlayer]) {
      Femtocraft.researchManager.getPlayerResearch(iCommandSender.getCommandSenderName).save()
    }
  }

}
