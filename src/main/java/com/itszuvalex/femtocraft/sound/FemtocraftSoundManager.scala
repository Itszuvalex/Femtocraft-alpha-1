package com.itszuvalex.femtocraft.sound

import java.io.{ByteArrayOutputStream, DataInputStream, DataOutputStream}
import java.util.logging.Level

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager.SoundAction
import com.itszuvalex.femtocraft.sound.FemtocraftSoundManager.SoundAction.{STOP_ALL_SOUNDS, SoundAction}
import com.itszuvalex.femtocraft.utils.WorldLocation
import cpw.mods.fml.relauncher.{Side, SideOnly}
import net.minecraft.client.Minecraft
import net.minecraft.network.packet.Packet250CustomPayload
import net.minecraft.world.World
import net.minecraftforge.client.event.sound.{PlaySoundSourceEvent, SoundLoadEvent}
import net.minecraftforge.event.ForgeSubscribe

import scala.collection.mutable


/**
 * Created by Chris on 9/3/2014.
 */
@SideOnly(value = Side.CLIENT)
object FemtocraftSoundManager {

  object SoundAction {

    def actionFromString(id: String): SoundAction = {
      id match {
        case STOP_ALL_SOUNDS.id => STOP_ALL_SOUNDS
      }
    }

    sealed trait SoundAction {def id: String}

    case object STOP_ALL_SOUNDS extends SoundAction {val id = "STOP_ALL_SOUNDS"}

  }


  final val PACKET_CHANNEL            : String = Femtocraft.SOUND_CHANNEL
  final val PhlegethonTunnelIdentifier: String = Femtocraft.ID.toLowerCase + ":" + "PhlegethonTunnel"
}

@SideOnly(value = Side.CLIENT)
class FemtocraftSoundManager() {

  private val locToSoundIDMap  = new mutable.HashMap[WorldLocation, String]
  private val soundIDToLocMap  = new mutable.HashMap[String, WorldLocation]
  private val waitForEventList = new mutable.HashSet[WorldLocation]

  def getSoundIDForLocation(x: Int, y: Int, z: Int) = locToSoundIDMap.get(new WorldLocation(null, x, y, z)).orNull

  def getLocationForSoundID(id: String) = soundIDToLocMap.get(id).orNull


  def addSoundIDForLocation(id: String, x: Int, y: Int, z: Int) {
    val loc = new WorldLocation(null, x, y, z)
    locToSoundIDMap.put(loc, id) != null && soundIDToLocMap.put(id, loc) != null
  }

  def pauseSound(id: String): Boolean = {
    if (isSoundPlaying(id)) {
      Minecraft.getMinecraft.sndManager.sndSystem.pause(id)
      true
    }
    else
      false
  }

  def playSound(id: String) = {
    Minecraft.getMinecraft.sndManager.sndSystem.play(id)
    isSoundPlaying(id)
  }

  def playSound(world: World, xCoord: Double, yCoord: Double, zCoord: Double, soundName: String, volume: Float, pitch: Float, b: Boolean) {
    world.playSound(xCoord, yCoord, zCoord, soundName, volume, pitch, b)
    waitForEventList.add(new WorldLocation(null, xCoord.asInstanceOf[Int], yCoord.asInstanceOf[Int], zCoord.asInstanceOf[Int]))
  }

  def isSoundPlaying(id: String) = Minecraft.getMinecraft.sndManager.sndSystem.playing(id)


  def stopSound(id: String): Boolean = {
    if (isSoundPlaying(id)) {
      Minecraft.getMinecraft.sndManager.sndSystem.stop(id)
      locToSoundIDMap.remove(soundIDToLocMap.remove(id).orNull)
      return true
    }
    false
  }

  def setSoundLooping(id: String, looping: Boolean): Boolean = {
    if (isSoundPlaying(id)) {
      Minecraft.getMinecraft.sndManager.sndSystem.setLooping(id, looping)
      return true
    }
    false
  }

  def stopAllSounds() = soundIDToLocMap.keySet.foreach(id => stopSound(id))

  @SideOnly(value = Side.CLIENT)
  @ForgeSubscribe def onSoundLoad(event: SoundLoadEvent) {
    event.manager.addSound(FemtocraftSoundManager.PhlegethonTunnelIdentifier + ".wav")
  }

  @ForgeSubscribe def onSoundPlay(event: PlaySoundSourceEvent) {
    val loc = new WorldLocation(null, event.x.asInstanceOf[Int], event.y.asInstanceOf[Int], event.z.asInstanceOf[Int])
    if (waitForEventList.contains(loc)) {
      addSoundIDForLocation(event.name, event.x.asInstanceOf[Int], event.y.asInstanceOf[Int], event.z.asInstanceOf[Int])
      waitForEventList.remove(loc)
    }
  }

  def getPacket(action: SoundAction): Packet250CustomPayload = {
    val bos = new ByteArrayOutputStream(17)
    val outputStream = new DataOutputStream(bos)
    try {
      outputStream.writeUTF(action.id)
    }
    catch {
      case ex: Exception => ex.printStackTrace()
    }

    val packet = new Packet250CustomPayload
    packet.channel = FemtocraftSoundManager.PACKET_CHANNEL
    packet.data = bos.toByteArray
    packet.length = bos.size
    packet
  }

  def handlePacket(istream: DataInputStream) {
    try {
      val actStr = istream.readUTF
      val action = SoundAction.actionFromString(actStr)
      action match {
        case STOP_ALL_SOUNDS => stopAllSounds()
        case _               => Femtocraft.log(Level.SEVERE, "Received unknown action-" + actStr + " at Sound Manager.")
      }
    }
    catch {
      case e: Exception => e.printStackTrace()
    }
  }
}

