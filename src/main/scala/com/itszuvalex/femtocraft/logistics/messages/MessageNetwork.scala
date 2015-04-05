package com.itszuvalex.femtocraft.logistics.messages

import com.itszuvalex.femtocraft.logistics.ManagerNetwork
import cpw.mods.fml.common.network.simpleimpl.{IMessage, IMessageHandler, MessageContext}
import io.netty.buffer.ByteBuf

/**
 * Created by Christopher Harris (Itszuvalex) on 4/5/15.
 */
abstract class MessageNetwork(var id: Int, var data: ByteBuf) extends IMessage with IMessageHandler[MessageNetwork, IMessage] {

  override def fromBytes(buf: ByteBuf): Unit = {
    id = buf.readInt()
    data = buf
  }

  override def toBytes(buf: ByteBuf): Unit = {
    buf.writeInt(id)
    buf.writeBytes(data)
  }

  override def onMessage(message: MessageNetwork, ctx: MessageContext): IMessage = {
    val network = ManagerNetwork.getNetwork(id).get
    if(network != null) network.handlePacketData(data)
    null
  }
}
