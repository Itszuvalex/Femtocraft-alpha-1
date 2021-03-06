package com.itszuvalex.femtocraft.network.messages

import java.io.{ByteArrayInputStream, IOException}

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
  private var playerName  : String         = null

  def this(rp: PlayerResearch) {
    this()
    researchData = new NBTTagCompound
    rp.saveToNBTTagCompound(researchData)
    playerName = rp.username
  }

  def fromBytes(buf: ByteBuf) {
    val nlength = buf.readInt
    val name = new Array[Byte](nlength)
    buf.readBytes(name)
    playerName = new String(name)
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
    buf.writeInt(playerName.getBytes.length)
    buf.writeBytes(playerName.getBytes)
    buf.writeInt(rdata.length)
    buf.writeBytes(rdata)
  }

  def onMessage(message: MessageResearchPlayer, ctx: MessageContext): IMessage = {
    if (message.playerName.equalsIgnoreCase(Minecraft.getMinecraft.thePlayer.getCommandSenderName)) {
      val rp = new PlayerResearch(message.playerName)
      rp.loadFromNBTTagCompound(message.researchData)
      Femtocraft.researchManager.syncResearch(rp)
    }
    null
  }
}
