package com.itszuvalex.femtocraft

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.{PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerLoggedOutEvent}
import net.minecraft.entity.player.EntityPlayerMP

/**
 * Created by Chris on 9/3/2014.
 */
class FemtocraftPlayerTracker {

  @SubscribeEvent
  def onPlayerLogin(event: PlayerLoggedInEvent) = {
    Femtocraft.researchManager.addPlayerResearch(event.player.getCommandSenderName).sync(event.player.asInstanceOf[EntityPlayerMP])
    Femtocraft.uuidManager.addMapping(event.player.getUniqueID.toString, event.player.getCommandSenderName)
  }

  @SubscribeEvent
  def onPlayerLogout(event: PlayerLoggedOutEvent) = {
    if (Femtocraft.soundManager != null) Femtocraft.soundManager.stopAllSounds()
  }

  @SubscribeEvent
  def onPlayerChangedDimension(event: PlayerChangedDimensionEvent) = if (Femtocraft.soundManager != null) Femtocraft.soundManager.stopAllSounds()


}

