package com.itszuvalex.femtocraft.sound

import com.itszuvalex.femtocraft.Femtocraft
import net.minecraft.client.Minecraft
import net.minecraft.client.audio.ISound
import net.minecraft.client.audio.ISound.AttenuationType
import net.minecraft.util.ResourceLocation

import scala.collection.mutable.ArrayBuffer


/**
 * Created by Chris on 9/3/2014.
 */
object FemtocraftSoundManager {

  private final val PhlegethonTunnelIdentifier = Femtocraft.ID.toLowerCase + ":" + "phlegethontunnel"

  def makePhlegethonSound(x: Int, y: Int, z: Int) = createSound(PhlegethonTunnelIdentifier, 1f, 1f, x.toInt, y.toInt, z.toInt, repeat = true, 0, AttenuationType.LINEAR)

  def createSound(identifier: String, volume: Float, pitch: Float, x: Int, y: Int, z: Int, repeat: Boolean, repeatDelay: Int, attenuation: ISound.AttenuationType) =
    new PositionedSoundLoopable(new ResourceLocation(identifier), volume, pitch, x, y, z, repeat, repeatDelay, attenuation)

  object SoundAction {

    def actionFromString(id: String): SoundAction = {
      id match {
        case STOP_ALL_SOUNDS.id => STOP_ALL_SOUNDS
      }
    }

    sealed trait SoundAction {def id: String}

    case object STOP_ALL_SOUNDS extends SoundAction {val id = "STOP_ALL_SOUNDS"}

  }
}

class FemtocraftSoundManager() {
  private val sounds = new ArrayBuffer[ISound]


  def playSound(sound: ISound) = {
    Minecraft.getMinecraft.getSoundHandler.playSound(sound)
    sounds += sound
    isSoundPlaying(sound)
  }

  def isSoundPlaying(sound: ISound) = Minecraft.getMinecraft.getSoundHandler.isSoundPlaying(sound)

  def stopAllSounds() = sounds.foreach(stopSound)

  def stopSound(sound: ISound): Boolean = {
    if (isSoundPlaying(sound)) {
      Minecraft.getMinecraft.getSoundHandler.stopSound(sound)
      val i = sounds.indexOf(sound)
      if (i == -1) return false
      sounds -= sound
      return true
    }
    false
  }

  //
  //  def getPacket(action: SoundAction): Packet250CustomPayload = {
  //    val bos = new ByteArrayOutputStream(17)
  //    val outputStream = new DataOutputStream(bos)
  //    try {
  //      outputStream.writeUTF(action.id)
  //    }
  //    catch {
  //      case ex: Exception => ex.printStackTrace()
  //    }
  //
  //    val packet = new Packet250CustomPayload
  //    packet.channel = FemtocraftSoundManager.PACKET_CHANNEL
  //    packet.data = bos.toByteArray
  //    packet.length = bos.size
  //    packet
  //  }
  //
  //  def handlePacket(istream: DataInputStream) {
  //    try {
  //      val actStr = istream.readUTF
  //      val action = SoundAction.actionFromString(actStr)
  //      action match {
  //        case STOP_ALL_SOUNDS => stopAllSounds()
  //        case _               => Femtocraft.log(Level.SEVERE, "Received unknown action-" + actStr + " at Sound Manager.")
  //      }
  //    }
  //    catch {
  //      case e: Exception => e.printStackTrace()
  //    }
  //  }
}

