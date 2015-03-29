package com.itszuvalex.femtocraft.command

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearchSave extends CommandBase("save", null) {

  override def getDescription = "Forces player research to write to disc."

  override def processCommand(iCommandSender: ICommandSender, astring: Array[String]): Unit = {
    iCommandSender match {
      case player: EntityPlayer =>
        Femtocraft.researchManager.getPlayerResearch(player).save()
        FemtocraftUtils.sendMessageToPlayer(player, "Researched saved.")
      case _ =>
    }
  }

}
