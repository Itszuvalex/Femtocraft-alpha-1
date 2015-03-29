package com.itszuvalex.femtocraft.command

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.command.ICommandSender
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearchSync extends CommandBase("sync", null) {

  override def getDescription = "Syncs research from server to client."

  override def processCommand(icommandsender: ICommandSender, astring: Array[String]) {
    icommandsender match {
      case player: EntityPlayer =>
        Femtocraft.researchManager.getPlayerResearch(player).sync()
        FemtocraftUtils.sendMessageToPlayer(player, "Research data synced.");
      case _ =>
    }
  }
}
