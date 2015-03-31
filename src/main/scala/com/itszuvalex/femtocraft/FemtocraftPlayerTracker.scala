package com.itszuvalex.femtocraft

import cpw.mods.fml.common.eventhandler.SubscribeEvent
import cpw.mods.fml.common.gameevent.PlayerEvent.{PlayerChangedDimensionEvent, PlayerLoggedInEvent, PlayerLoggedOutEvent}

/**
 * Created by Chris on 9/3/2014.
 */
class FemtocraftPlayerTracker {

  @SubscribeEvent
  def onPlayerLogin(event: PlayerLoggedInEvent) = {
    Femtocraft.uuidManager.addMapping(event.player.getUniqueID, event.player.getCommandSenderName)
    Femtocraft.researchManager.onPlayerLogin(event.player)
  }

  @SubscribeEvent
  def onPlayerLogout(event: PlayerLoggedOutEvent) = {
    if (Femtocraft.soundManager != null) Femtocraft.soundManager.stopAllSounds()
  }

  @SubscribeEvent
  def onPlayerChangedDimension(event: PlayerChangedDimensionEvent) = if (Femtocraft.soundManager != null) Femtocraft.soundManager.stopAllSounds()


}

