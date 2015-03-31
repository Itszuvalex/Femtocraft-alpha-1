package com.itszuvalex.femtocraft.network.messages

import java.io.{ByteArrayInputStream, IOException}
import java.util.UUID

import com.itszuvalex.femtocraft.Femtocraft
import com.itszuvalex.femtocraft.managers.research.PlayerResearch
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft
import net.minecraft.nbt.{CompressedStreamTools, NBTTagCompound}
import org.apache.logging.log4j.Level

/**
 * Created by Christopher Harris (Itszuvalex) on 7/4/14.
 */
class MessageResearchPlayer() extends IMessage with IMessageHandler[MessageResearchPlayer, IMessage] {
  private var researchData: NBTTagCompound = null
  private var playerUUID  : UUID         = null

  def this(rp: PlayerResearch) {
    this()
    researchData = new NBTTagCompound
    rp.saveToNBT(researchData)
    playerUUID = rp.getUUID
  }

  def fromBytes(buf: ByteBuf) {
    val nlength = buf.readInt
    val name = new Array[Byte](nlength)
    buf.readBytes(name)
    playerUUID = UUID.fromString(new String(name))
    val dlength = buf.readInt
    val data = new Array[Byte](dlength)
    buf.readBytes(data)

    try {
      researchData = CompressedStreamTools.readCompressed(new ByteArrayInputStream(data))
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Femtocraft.log(Level.ERROR, "Error decompressing research" + " data.")
    }
  }

  def toBytes(buf: ByteBuf) {
    var rdata: Array[Byte] = null
    try {
      rdata = CompressedStreamTools.compress(researchData)
    }
    catch {
      case e: IOException =>
        e.printStackTrace()
        Femtocraft.log(Level.ERROR, "Error compressing " + " research data.  No data will be sent")
        return
    }
    buf.writeInt(playerUUID.toString.getBytes.length)
    buf.writeBytes(playerUUID.toString.getBytes)
    buf.writeInt(rdata.length)
    buf.writeBytes(rdata)
  }

  def onMessage(message: MessageResearchPlayer, ctx: MessageContext): IMessage = {
    if (message.playerUUID.equals(Minecraft.getMinecraft.thePlayer.getUniqueID)) {
      val rp = new PlayerResearch(message.playerUUID)
      rp.loadFromNBT(message.researchData)
      Femtocraft.researchManager.syncLocal(rp)
    }
    null
  }
}
