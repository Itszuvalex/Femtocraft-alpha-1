package com.itszuvalex.femtocraft.command

import java.util

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.api.utils.FemtocraftUtils
import net.minecraft.command.{ICommandSender, WrongUsageException}
import net.minecraft.entity.player.EntityPlayerMP
import net.minecraft.server.MinecraftServer

import scala.collection.JavaConversions._

/**
 * Created by Christopher Harris (Itszuvalex) on 3/27/15.
 */
class CommandResearchResearch extends CommandBase("research", null) {

  override def getDescription = "Researches a tech for a given player.  OP only."

  override def processCommand(iCommandSender: ICommandSender, astring: Array[String]): Unit = {
    iCommandSender match {
      case player: EntityPlayerMP if MinecraftServer.getServer != null && MinecraftServer.getServer.getConfigurationManager.func_152596_g(player.getGameProfile) =>
        if (astring.length != 2) throw new WrongUsageException(getCommandUsage(iCommandSender))
        val UUID = Femtocraft.uuidManager.getUUID(astring(0))
        if (UUID == null) {
          FemtocraftUtils.sendMessageToPlayer(player, "Player: " + astring(0) + " not found.")
          return
        }
        val research = Femtocraft.researchManager.getPlayerResearch(UUID)
        if (research == null) {
          FemtocraftUtils.sendMessageToPlayer(player, "Player: " + astring(0) + "'s research not found.")
          return
        }

        astring(1) match {
          case "all" =>
            Femtocraft.researchManager.getTechnologies.filterNot(research.hasResearchedTechnology).map(_.getName).foreach(research.researchTechnology(_, true, true))
            FemtocraftUtils.sendMessageToPlayer(player, "Player: " + astring(0) + " just had all techs researched.")
          case tech =>
            research.discoverTechnology(astring(1).replaceAll("_", "\\ "), true)
            FemtocraftUtils.sendMessageToPlayer(player, "Player: " + astring(0) + " just had " + astring(1).replaceAll("_", "\\ ") + " researched.")
        }

      case player: EntityPlayerMP =>
        FemtocraftUtils.sendMessageToPlayer(player, "Must be opped to access this command.")
      case _ =>
    }
  }

  override def addTabCompletionOptions(icommandsender: ICommandSender,
                                       astring: Array[String]): util.List[_] = {
    icommandsender match {
      case player: EntityPlayerMP if MinecraftServer.getServer != null && MinecraftServer.getServer.getConfigurationManager.func_152596_g(player.getGameProfile) =>
        if (astring.length == 1)
          MinecraftServer.getServer.getAllUsernames.toList
        else if (astring.length == 2) {
          val UUID = Femtocraft.uuidManager.getUUID(astring(0))
          if (UUID == null) {
            return List()
          }
          val research = Femtocraft.researchManager.getPlayerResearch(UUID)
          if (research == null) {
            return List()
          }
          Femtocraft.researchManager.getTechnologies.filterNot(research.hasResearchedTechnology)
          .map(_.getName).map(_.replaceAll("\\ ", "_")).toList ::: List("all")
        }
        else List()
      case player: EntityPlayerMP =>
        FemtocraftUtils.sendMessageToPlayer(player, "Must be opped to access this command.")
        List()
      case _ => List()
    }
  }

}
