package com.itszuvalex.femtocraft

import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager
import cpw.mods.fml.common.IPlayerTracker
import cpw.mods.fml.common.network.{PacketDispatcher, Player}
import net.minecraft.entity.player.EntityPlayer

/**
 * Created by Chris on 9/3/2014.
 */
class FemtocraftPlayerTracker extends IPlayerTracker {
  def onPlayerLogin(player: EntityPlayer) {
  }

  def onPlayerLogout(player: EntityPlayer) {
  }

  def onPlayerChangedDimension(player: EntityPlayer) {
    PacketDispatcher.sendPacketToPlayer(Femtocraft.soundManager.getPacket(FemtocraftSoundManager.SoundAction.STOP_ALL_SOUNDS), player.asInstanceOf[Player])
  }

  def onPlayerRespawn(player: EntityPlayer) {
  }
}

