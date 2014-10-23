package com.itszuvalex.femtocraft.network.messages

import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf
import net.minecraft.client.Minecraft

/**
 * Created by Christopher Harris (Itszuvalex) on 10/19/14.
 */
class MessageContainerUpdate(private var index: Int, private var value: Int) extends IMessage with IMessageHandler[MessageContainerUpdate, IMessage] {
  def this() = this(0, 0)

  override def fromBytes(buf: ByteBuf) {
    index = buf.readInt
    value = buf.readInt
  }

  override def toBytes(buf: ByteBuf) {
    buf.writeInt(index)
    buf.writeInt(value)
  }

  override def onMessage(message: MessageContainerUpdate, ctx: MessageContext) = {
    Minecraft.getMinecraft.thePlayer.openContainer.updateProgressBar(message.index, message.value)
    null
  }
}
